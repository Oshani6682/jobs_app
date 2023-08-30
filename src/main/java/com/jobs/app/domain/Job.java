package com.jobs.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "job")
public class Job {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column
    public String job;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    public Sector sector;

    @Column(name = "is_active")
    public Boolean isActive = true;

}
