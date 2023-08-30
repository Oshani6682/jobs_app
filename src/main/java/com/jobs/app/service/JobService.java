package com.jobs.app.service;

import com.jobs.app.domain.Job;
import com.jobs.app.dto.CreateJobDTO;
import com.jobs.app.dto.JobDTO;
import com.jobs.app.enums.ErrorCodes;
import com.jobs.app.exception.JobApiException;
import com.jobs.app.repository.JobRepository;
import com.jobs.app.repository.SectorRepository;
import com.jobs.app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SectorRepository sectorRepository;

    public JobDTO saveJob(CreateJobDTO createJobDTO) {
        LOGGER.info("Save job: {} - Started", createJobDTO.getJob());

        Job job = new Job();
        job.job = Utils.capitalizeEachLetter(createJobDTO.getJob());
        job.sector = sectorRepository.findById(createJobDTO.getSector())
            .orElseThrow(() ->
                new JobApiException(
                    "Sector not found", ErrorCodes.SECTOR_INVALID, HttpStatus.BAD_REQUEST
                )
            );

        return new JobDTO(jobRepository.save(job));
    }

    public List<JobDTO> findJobsBySector(Integer sector) {
        LOGGER.info("Find jobs by sector: {} - Started", sector);
        return jobRepository.findAllActiveBySector(sector);
    }

}
