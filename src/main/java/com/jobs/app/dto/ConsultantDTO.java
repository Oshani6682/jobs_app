package com.jobs.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobs.app.domain.Consultant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultantDTO extends UserDTO {

    String country;

    String sector;

    public ConsultantDTO(Consultant consultant) {
        setId(consultant.user.id);
        setFirstName(consultant.user.firstName);
        setLastName(consultant.user.lastName);
        setAddress(consultant.user.address);
        setEmail(consultant.user.email);
        setActive(consultant.user.isActive);
        setSector(consultant.sector.sector);
        setCountry(consultant.country.country);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

}
