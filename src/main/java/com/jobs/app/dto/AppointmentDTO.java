package com.jobs.app.dto;

import com.jobs.app.domain.Appointment;
import com.jobs.app.util.Utils;

public class AppointmentDTO {

    Integer id;
    String consultant;
    String jobSeeker;
    String appointmentDate;
    String startTime;
    String endTime;
    String status;

    public AppointmentDTO(Appointment appointment) {
        setId(appointment.id);
        setConsultant(Utils.getDisplayName(appointment.consultant.user));
        setJobSeeker(Utils.getDisplayName(appointment.jobSeeker));
        setAppointmentDate(Utils.convertDateBigIntValueToDate(appointment.appointmentDate));
        setStartTime(Utils.convertTimeIntValueToTime(appointment.fromTime));
        setEndTime(Utils.convertTimeIntValueToTime(appointment.toTime));
        setStatus(appointment.status.name());
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

    public String getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(String jobSeeker) {
        this.jobSeeker = jobSeeker;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
