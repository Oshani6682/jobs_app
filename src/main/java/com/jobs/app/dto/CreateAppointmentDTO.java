package com.jobs.app.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CreateAppointmentDTO {

    @NotNull(
        message = "JobSeekerNotFound#Job seeker not found"
    )
    @Min(
        value = 1,
        message = "JobSeekerNotFound#Job seeker not found"
    )
    Integer jobSeeker;


    @NotNull(
        message = "ConsultantNotFound#Consultant not found"
    )
    @Min(
        value = 1,
        message = "ConsultantNotFound#Consultant not found"
    )
    Integer consultant;

    @NotBlank(
        message = "DateInvalid#Appointment date not found"
    )
    @Pattern(
        regexp = "^(202[3-9])-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$",
        message = "DateInvalid#Appointment date is invalid. Date format should be in YYYY-MM-DD format"
    )
    String appointmentDate;

    @NotBlank(
        message = "TimeInvalid#Appointment start time not found"
    )
    @Pattern(
        regexp = "^([0-1][0-9]|2[0-4]):([0-5][0-9])$",
        message = "TimeInvalid#Appointment start time is invalid. Time format should be in 24 hrs (HH:mm)"
    )
    String fromTime;

    @NotBlank(
        message = "TimeInvalid#Appointment end time from not found"
    )
    @Pattern(
        regexp = "^([0-1][0-9]|2[0-4]):([0-5][0-9])$",
        message = "TimeInvalid#Appointment end time is invalid. Time format should be in 24 hrs (HH:mm)"
    )
    String toTime;

    public Integer getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(Integer jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public Integer getConsultant() {
        return consultant;
    }

    public void setConsultant(Integer consultant) {
        this.consultant = consultant;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

}
