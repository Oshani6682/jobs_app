package com.jobs.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CreateJobDTO {

    Integer sector;

    @NotBlank(
        message = "JobInvalid#Job name is invalid"
    )
    @Pattern(
        regexp = "^[A-Za-z ]+$",
        message = "JobInvalid#Job name can only contain letters"
    )
    String job;

    public Integer getSector() {
        return sector;
    }

    public void setSector(Integer sector) {
        this.sector = sector;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
