package com.jobs.app.service;

import com.jobs.app.domain.ConsultantAvailability;
import com.jobs.app.domain.Day;
import com.jobs.app.dto.ConsultantAvailabilityDTO;
import com.jobs.app.enums.ErrorCodes;
import com.jobs.app.exception.JobApiException;
import com.jobs.app.repository.ConsultantAvailabilityRepository;
import com.jobs.app.repository.DayRepository;
import com.jobs.app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConsultantService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultantService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ConsultantAvailabilityRepository consultantAvailabilityRepository;

    @Autowired
    private DayRepository dayRepository;

    public ConsultantAvailabilityDTO saveConsultantAvailability(Model model, ConsultantAvailabilityDTO availabilityDTO) {
        LOGGER.info("Save consultant availability: Consultant {}", availabilityDTO.getConsultant());

        int consultant = Integer.parseInt(availabilityDTO.getConsultant());
        int day = Integer.parseInt(availabilityDTO.getDay());
        int availableFromCode = Utils.convertTimeToIntValue(availabilityDTO.getAvailableFrom());
        int availableToCode = Utils.convertTimeToIntValue(availabilityDTO.getAvailableTo());

        if (availableFromCode >= availableToCode) {
            if (includeError(model, "Availability from time is ahead of available till time")) {
                return null;
            }
            throw new JobApiException(
                "Availability times are invalid", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
            );
        }

        List<ConsultantAvailabilityDTO> availabilities = consultantAvailabilityRepository.findAllByConsultantAndDayIncludingCode(
            consultant, day
        );

        for (ConsultantAvailabilityDTO availability : availabilities) {
            int fromCode = availability.getAvailableFromCode();
            int toCode = availability.getAvailableToCode();
            if (availableFromCode <= fromCode && availableToCode > fromCode) {
                if (includeError(model, "Overlaps existing available time")) {
                    return null;
                }
                throw new JobApiException(
                    "Overlaps existing available time", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
                );
            } else if (availableFromCode >= fromCode && availableFromCode < toCode) {
                if (includeError(model, "Overlaps existing available time")) {
                    return null;
                }
                throw new JobApiException(
                    "Overlaps existing available time", ErrorCodes.TIME_INVALID, HttpStatus.BAD_REQUEST
                );
            }
        }

        Optional<Day> matchedDay = dayRepository.findById(day);

        ConsultantAvailability availability = new ConsultantAvailability();
        availability.consultant = consultant;
        availability.day = matchedDay.orElseThrow(() -> new JobApiException(
            "Day not found", ErrorCodes.DAY_NOT_FOUND, HttpStatus.BAD_REQUEST
        ));
        availability.availableFrom = availableFromCode;
        availability.availableTo = availableToCode;

        availability = consultantAvailabilityRepository.save(availability);
        LOGGER.info("Save consultant availability: Consultant {} - Successful", availabilityDTO.getConsultant());

        return new ConsultantAvailabilityDTO(availability, false);
    }

    public List<ConsultantAvailabilityDTO> findConsultantAvailability(Integer consultantId) {
        LOGGER.info("Find consultant availability: {} - Started", consultantId);
        userService.findConsultantById(consultantId, HttpStatus.BAD_REQUEST);
        return consultantAvailabilityRepository.findAllByConsultantAndDayExcludingCode(consultantId);
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
