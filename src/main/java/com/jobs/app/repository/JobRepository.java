package com.jobs.app.repository;

import com.jobs.app.domain.Job;
import com.jobs.app.dto.JobDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    @Query("select new com.jobs.app.dto.JobDTO(job) from Job job where job.sector.id=:sector and job.isActive=true")
    List<JobDTO> findAllActiveBySector(int sector);

}
