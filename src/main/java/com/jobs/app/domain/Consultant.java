package com.jobs.app.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "consultant")
public class Consultant implements Serializable {

    @Id
    @Column
    public Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "country_id")
    public Country country;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    public Sector sector;

}
