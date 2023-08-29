package com.jobs.app.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "consultant_available_days")
public class ConsultantAvailability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Integer id;

    @Column
    public Integer consultant;

    @ManyToOne
    @JoinColumn(name = "day")
    public Day day;

    @Column(name = "available_from")
    public Integer availableFrom;

    @Column(name = "available_to")
    public Integer availableTo;

}
