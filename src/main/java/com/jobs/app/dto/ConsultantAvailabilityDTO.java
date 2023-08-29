package com.jobs.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobs.app.domain.ConsultantAvailability;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultantAvailabilityDTO {

    Integer id;

    String consultant;

    @NotNull(
        message = "DayNotFound#Availability day not found"
    )
    String day;

    @NotBlank(
        message = "TimeInvalid#Time available from not found"
    )
    @Pattern(
        regexp = "^([0-1][0-9]|2[0-4]):([0-5][0-9])$",
        message = "TimeInvalid#Available from time is invalid. Time format should be in 24 hrs (HH:MM)"
    )
    String availableFrom;

    Integer availableFromCode = null;

    @NotBlank(
        message = "TimeInvalid#Time available to not found"
    )
    @Pattern(
        regexp = "^([0-1][0-9]|2[0-4]):([0-5][0-9])$",
        message = "TimeInvalid#Available to time is invalid. Time format should be in 24 hrs (HH:MM)"
    )
    String availableTo;

    Integer availableToCode = null;

    public ConsultantAvailabilityDTO() {}

    public ConsultantAvailabilityDTO(ConsultantAvailability availability, boolean includeTimeIntValue) {
        setId(availability.id);
        setConsultant(availability.consultant.toString());
        setDay(availability.day.day);

        Integer availableFrom = availability.availableFrom;
        Integer availableTo = availability.availableTo;

        setAvailableFrom(availableFrom.toString().substring(0, 2) + ":" + availableFrom.toString().substring(2, 4));
        setAvailableTo(availableTo.toString().substring(0, 2) + ":" + availableTo.toString().substring(2, 4));

        if (includeTimeIntValue) {
            setAvailableFromCode(availableFrom);
            setAvailableToCode(availableTo);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Integer getAvailableFromCode() {
        return availableFromCode;
    }

    public void setAvailableFromCode(Integer availableFromCode) {
        this.availableFromCode = availableFromCode;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    public Integer getAvailableToCode() {
        return availableToCode;
    }

    public void setAvailableToCode(Integer availableToCode) {
        this.availableToCode = availableToCode;
    }
}
