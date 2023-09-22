package com.jobs.app.dto;

public class AppointmentRatingDTO {

    Integer id;

    Integer appointmentId;

    Integer consultantRating;

    Integer appointmentRating;

    String consultantRemarks;

    String appointmentRemarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getConsultantRating() {
        return consultantRating;
    }

    public void setConsultantRating(Integer consultantRating) {
        this.consultantRating = consultantRating;
    }

    public Integer getAppointmentRating() {
        return appointmentRating;
    }

    public void setAppointmentRating(Integer appointmentRating) {
        this.appointmentRating = appointmentRating;
    }

    public String getConsultantRemarks() {
        return consultantRemarks;
    }

    public void setConsultantRemarks(String consultantRemarks) {
        this.consultantRemarks = consultantRemarks;
    }

    public String getAppointmentRemarks() {
        return appointmentRemarks;
    }

    public void setAppointmentRemarks(String appointmentRemarks) {
        this.appointmentRemarks = appointmentRemarks;
    }

}
