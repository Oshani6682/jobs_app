package com.jobs.app.controller;

import com.jobs.app.dto.ConsultantAvailabilityDTO;
import com.jobs.app.service.ConsultantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/consultants", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsultantController {

    @Autowired
    private ConsultantService consultantService;

    @PostMapping(value = "/availability/{consultantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ConsultantAvailabilityDTO> saveConsultantAvailability(
        @PathVariable Integer consultantId,
        @RequestBody @Validated ConsultantAvailabilityDTO availabilityDTO
    ) {
        availabilityDTO.setConsultant(String.valueOf(consultantId));
        return new ResponseEntity<ConsultantAvailabilityDTO>(
            consultantService.saveConsultantAvailability(availabilityDTO),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/availability/{consultantId}")
    private ResponseEntity<List<ConsultantAvailabilityDTO>> findConsultantAvailability(
        @PathVariable Integer consultantId
    ) {
        return ResponseEntity
            .ok()
            .body(consultantService.findConsultantAvailability(consultantId));
    }

}
