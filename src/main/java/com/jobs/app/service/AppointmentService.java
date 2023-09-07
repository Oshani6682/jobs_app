package com.jobs.app.service;

import com.jobs.app.domain.Appointment;
import com.jobs.app.domain.User;
import com.jobs.app.dto.AppointmentDTO;
import com.jobs.app.dto.ConsultantAvailabilityDTO;
import com.jobs.app.dto.CreateAppointmentDTO;
import com.jobs.app.enums.AppointmentStatus;
import com.jobs.app.enums.ErrorCodes;
import com.jobs.app.enums.UserRole;
import com.jobs.app.exception.JobApiException;
import com.jobs.app.repository.AppointmentRepository;
import com.jobs.app.repository.ConsultantAvailabilityRepository;
import com.jobs.app.repository.ConsultantRepository;
import com.jobs.app.repository.UserRepository;
import com.jobs.app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigInteger;
import java.util.*;

@Service
public class AppointmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private ConsultantAvailabilityRepository consultantAvailabilityRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public AppointmentDTO cancelAppointment(Model model, int userId, int appointmentId) {
        LOGGER.info("Cancel appointment {} - Started", appointmentId);

        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (!appointment.isPresent()) {
            includeError(model, "Appointment not found");
            return null;
        }

        Appointment result = appointment.get();
        if (result.status != AppointmentStatus.scheduled) {
            includeError(model, "Cannot cancel the appointment as it is in " + result.status + " state");
            return null;
        }

        result.status = AppointmentStatus.cancelled;
        result = appointmentRepository.save(result);
        LOGGER.info("Cancel appointment {} - Ended", appointmentId);
        return new AppointmentDTO(result);
    }

    public AppointmentDTO saveAppointment(Model model, CreateAppointmentDTO createAppointmentDTO) {
        LOGGER.info("Save appointment - Started");

        BigInteger appointmentDate = Utils.convertDateToBigIntValue(createAppointmentDTO.getAppointmentDate());
        BigInteger todayDate = Utils.getDateIntCode(new Date());

        int startTimeCode = Utils.convertTimeToIntValue(createAppointmentDTO.getFromTime());
        int endTimeCode = Utils.convertTimeToIntValue(createAppointmentDTO.getToTime());

        if (appointmentDate.compareTo(todayDate) <= 0) {
            if (includeError(model, "Appointment date must be a future date")) {
                return null;
            }
            throw new JobApiException(
                "Appointment date must be a future date", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
            );
        } else if (startTimeCode >= endTimeCode) {
            if (includeError(model, "Appointment times are invalid")) {
                return null;
            }
            throw new JobApiException(
                "Appointment times are invalid", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
            );
        }

        Date date = Utils.convertStringToDate(Utils.convertDateBigIntValueToDate(appointmentDate));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        List<ConsultantAvailabilityDTO> availabilityDTOS = consultantAvailabilityRepository.findAllByConsultantAndDayIncludingCode(
            createAppointmentDTO.getConsultant(), calendar.get(Calendar.DAY_OF_WEEK)
        );

        if (availabilityDTOS.isEmpty()) {
            if (includeError(model, "Consultant not available on selected day")) {
                return null;
            }
            throw new JobApiException(
                "Consultant not available on selected day", ErrorCodes.CONSULTANT_NOT_AVAILABLE, HttpStatus.BAD_REQUEST
            );
        }

        boolean matchFound = false;
        for (ConsultantAvailabilityDTO availability : availabilityDTOS) {
            int fromCode = availability.getAvailableFromCode();
            int toCode = availability.getAvailableToCode();

            if (startTimeCode >= fromCode &&
                endTimeCode <= toCode
            ) {
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            if (includeError(model, "Appointment time not in between consultant available time")) {
                return null;
            }

            throw new JobApiException(
                "Appointment time not in between consultant available time", ErrorCodes.APPOINTMENT_TIME_INVALID, HttpStatus.BAD_REQUEST
            );
        }

        for (AppointmentDTO appointmentDTO : appointmentRepository.findAllAppointmentsOfConsultantAndDate(createAppointmentDTO.getConsultant(), appointmentDate)) {
            AppointmentStatus status = AppointmentStatus.valueOf(appointmentDTO.getStatus());

            if (status == AppointmentStatus.scheduled ||
                status == AppointmentStatus.started ||
                status == AppointmentStatus.finished
            ) {
                int fromCode = Utils.convertTimeToIntValue(appointmentDTO.getStartTime());
                int toCode = Utils.convertTimeToIntValue(appointmentDTO.getEndTime());
                if (startTimeCode <= fromCode && endTimeCode > fromCode) {
                    if (includeError(model, "Overlaps existing appointment time")) {
                        return null;
                    }
                    throw new JobApiException(
                        "Overlaps existing appointment time", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
                    );
                } else if (startTimeCode >= fromCode && startTimeCode < toCode) {
                    if (includeError(model, "Overlaps existing appointment time")) {
                        return null;
                    }
                    throw new JobApiException(
                        "Overlaps existing appointment time", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
                    );
                }
            }
        }

        Appointment appointment = new Appointment();
        appointment.jobSeeker = userRepository.findById(createAppointmentDTO.getJobSeeker())
            .orElseThrow(() ->
                new JobApiException(
                    "Job seeker not found", ErrorCodes.JOB_SEEKER_NOT_FOUND, HttpStatus.BAD_REQUEST
                )
            );
        appointment.consultant = consultantRepository.findById(createAppointmentDTO.getConsultant())
            .orElseThrow(() ->
                new JobApiException(
                    "Consultant not found", ErrorCodes.CONSULTANT_NOT_FOUND, HttpStatus.BAD_REQUEST
                )
            );
        appointment.appointmentDate = appointmentDate;
        appointment.fromTime = startTimeCode;
        appointment.toTime = endTimeCode;
        appointment.status = AppointmentStatus.scheduled;

        appointment = appointmentRepository.save(appointment);

        LOGGER.info("Save appointment {} - Successful", appointment.id);
        return new AppointmentDTO(appointment);
    }

    public List<AppointmentDTO> findAppointments() {
        LOGGER.info("Find appointments - Started");
        return appointmentRepository.findAllAppointments();
    }

    public List<AppointmentDTO> findAppointments(int userId) {
        LOGGER.info("Find appointments - Started");
        User user = userRepository.findById(userId).get();
        if (user.userRole == UserRole.JOB_SEEKER) {
            return appointmentRepository.findAllAppointmentsOfJobSeeker(userId);
        } else if (user.userRole == UserRole.CONSULTANT) {
            return appointmentRepository.findAllAppointmentsOfConsultant(userId);
        }
        return appointmentRepository.findAllAppointments();
    }

    private boolean includeError(Model model, String errorMsg) {
        if (Objects.isNull(model)) {
            return false;
        }

        model.addAttribute("isError", true);
        model.addAttribute("errorMsg", errorMsg);
        return true;
    }

}
