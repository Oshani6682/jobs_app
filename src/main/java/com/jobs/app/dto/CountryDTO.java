package com.jobs.app.dto;

import com.jobs.app.domain.Country;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CountryDTO {

    int id;

    @NotBlank(
        message = "CountryInvalid#Country name is invalid"
    )
    @Pattern(
        regexp = "^[A-Za-z ]+$",
        message = "CountryInvalid#Country name can only contain letters"
    )
    String country;

    public CountryDTO() {}

    public CountryDTO(Country country) {
        setId(country.id);
        setCountry(country.country);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
