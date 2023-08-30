package com.jobs.app.service;

import com.jobs.app.domain.Appointment;
import com.jobs.app.dto.AppointmentDTO;
import com.jobs.app.dto.CreateAppointmentDTO;
import com.jobs.app.enums.AppointmentStatus;
import com.jobs.app.enums.ErrorCodes;
import com.jobs.app.exception.JobApiException;
import com.jobs.app.repository.AppointmentRepository;
import com.jobs.app.repository.ConsultantRepository;
import com.jobs.app.repository.UserRepository;
import com.jobs.app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentService.class);

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public AppointmentDTO saveAppointment(CreateAppointmentDTO createAppointmentDTO) {
        LOGGER.info("Save appointment - Started");

        BigInteger appointmentDate = Utils.convertDateToBigIntValue(createAppointmentDTO.getAppointmentDate());
        BigInteger todayDate = Utils.getDateIntCode(new Date());

        int startTimeCode = Utils.convertTimeToIntValue(createAppointmentDTO.getFromTime());
        int endTimeCode = Utils.convertTimeToIntValue(createAppointmentDTO.getToTime());

        if (appointmentDate.compareTo(todayDate) < 0) {
            throw new JobApiException(
                "Appointment date is invalid", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
            );
        } else if (startTimeCode >= endTimeCode) {
            throw new JobApiException(
                "Appointment times are invalid", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
            );
        }

        appointmentRepository.findAllAppointmentsOfConsultantAndDate(createAppointmentDTO.getConsultant(), appointmentDate).forEach(
            appointmentDTO -> {
                AppointmentStatus status = AppointmentStatus.valueOf(appointmentDTO.getStatus());

                if (
                    status == AppointmentStatus.scheduled ||
                    status == AppointmentStatus.started ||
                    status == AppointmentStatus.finished
                ) {
                    int fromCode = Utils.convertTimeToIntValue(appointmentDTO.getStartTime());
                    int toCode = Utils.convertTimeToIntValue(appointmentDTO.getEndTime());
                    if (startTimeCode <= fromCode && endTimeCode > fromCode) {
                        throw new JobApiException(
                                "Overlaps existing appointment time", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
                        );
                    } else if (startTimeCode >= fromCode && startTimeCode < toCode) {
                        throw new JobApiException(
                                "Overlaps existing appointment time", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
                        );
                    }
                }
            }
        );

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

}
