package com.jobs.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "day_type")
public class Day {

    @Id
    @Column
    public Integer id;

    @Column
    public String day;

}
