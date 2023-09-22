package com.jobs.app.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "appointment_rating")
public class AppointmentRating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Integer id;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    public Appointment appointment;

    @Column(name = "appointment_rating")
    public Integer appointmentRating;

    @Column(name = "appointment_remarks")
    public String appointmentRemarks;

    @Column(name = "consultant_rating")
    public Integer consultantRating;

    @Column(name = "consultant_remarks")
    public String consultantRemarks;

}
