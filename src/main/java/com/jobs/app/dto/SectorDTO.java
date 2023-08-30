package com.jobs.app.dto;

import com.jobs.app.domain.Sector;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SectorDTO {

    int id;

    @NotBlank(
        message = "SectorInvalid#Sector name is invalid"
    )
    @Pattern(
        regexp = "^[A-Za-z ]+$",
        message = "SectorInvalid#Sector name can only contain letters"
    )
    String sector;

    public SectorDTO() {}

    public SectorDTO(Sector sector) {
        setId(sector.id);
        setSector(sector.sector);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
