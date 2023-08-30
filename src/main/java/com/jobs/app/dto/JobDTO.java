package com.jobs.app.dto;

import com.jobs.app.domain.Job;

public class JobDTO {

    int id;
    String job;
    String sector;

    public JobDTO(Job job) {
        setId(job.id);
        setJob(job.job);
        setSector(job.sector.sector);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
