package com.jobs.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Min;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateConsultantDTO extends CreateUserDTO {

    @Min(
        value = 1, message = "CountryInvalid#Country id invalid"
    )
    Integer country = 0;

    @Min(
        value = 1, message = "SectorInvalid#Sector id invalid"
    )
    Integer sector = 0;

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getSector() {
        return sector;
    }

    public void setSector(Integer sector) {
        this.sector = sector;
    }

}
