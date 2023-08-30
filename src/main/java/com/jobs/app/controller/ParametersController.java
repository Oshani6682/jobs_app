package com.jobs.app.controller;

import com.jobs.app.dto.CountryDTO;
import com.jobs.app.dto.CreateJobDTO;
import com.jobs.app.dto.JobDTO;
import com.jobs.app.dto.SectorDTO;
import com.jobs.app.service.CountryService;
import com.jobs.app.service.JobService;
import com.jobs.app.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/parameters", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParametersController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private JobService jobService;

    @PostMapping(value = "/countries", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<CountryDTO> saveCountry(
        @RequestBody @Validated CountryDTO countryDTO
    ) {
        return new ResponseEntity<CountryDTO>(
            countryService.saveCountry(countryDTO),
            HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/countries")
    private ResponseEntity<List<CountryDTO>> findCountries() {
        return ResponseEntity
            .ok()
            .body(countryService.findCountries());
    }

    @PostMapping(value = "/sectors", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<SectorDTO> saveSector(
        @RequestBody @Validated SectorDTO sectorDTO
    ) {
        return new ResponseEntity<SectorDTO>(
            sectorService.saveSector(sectorDTO),
            HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/sectors")
    private ResponseEntity<List<SectorDTO>> findSectors() {
        return ResponseEntity
            .ok()
            .body(sectorService.findSectors());
    }

    @PostMapping(value = "/sector/{sectorId}/jobs", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<JobDTO> saveJob(
        @PathVariable Integer sectorId,
        @RequestBody @Validated CreateJobDTO jobDTO
    ) {
        jobDTO.setSector(sectorId);
        return new ResponseEntity<>(
            jobService.saveJob(jobDTO),
            HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/sector/{sectorId}/jobs")
    private ResponseEntity<List<JobDTO>> findJobsBySector(
        @PathVariable Integer sectorId
    ) {
        return ResponseEntity
            .ok()
            .body(jobService.findJobsBySector(sectorId));
    }

}
