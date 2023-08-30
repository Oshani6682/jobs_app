package com.jobs.app.domain;

import com.jobs.app.dto.CountryDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "country")
public class Country implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    public String country;

    @Column(name = "is_active")
    public Boolean isActive = true;

    public Country() {}

    public Country(CountryDTO countryDTO) {
        this.country = countryDTO.getCountry();
    }

}
