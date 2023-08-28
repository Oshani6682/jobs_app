package com.jobs.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sector")
public class Sector {

    @Id
    @Column
    public int id;

    @Column
    public String sector;

    @Column(name = "is_active")
    public Boolean isActive;

}
