package com.jobs.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column
    public int id;

    @Column
    public String country;

    @Column(name = "is_active")
    public Boolean isActive;

}
