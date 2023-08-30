package com.jobs.app.domain;

import com.jobs.app.dto.CreateAppointmentDTO;
import com.jobs.app.enums.AppointmentStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id")
    public User jobSeeker;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    public Consultant consultant;

    @Column(name = "appointment_date")
    public BigInteger appointmentDate;

    @Column(name = "from_time")
    public Integer fromTime;

    @Column(name = "to_time")
    public Integer toTime;

    @Column
    @Enumerated(value = EnumType.STRING)
    public AppointmentStatus status;

}
