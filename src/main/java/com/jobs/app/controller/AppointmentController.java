package com.jobs.app.controller;

import com.jobs.app.dto.AppointmentDTO;
import com.jobs.app.dto.CreateAppointmentDTO;
import com.jobs.app.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<AppointmentDTO> saveAppointment(
        @RequestBody @Validated CreateAppointmentDTO appointmentDTO
    ) {
        return new ResponseEntity<AppointmentDTO>(
            appointmentService.saveAppointment(appointmentDTO),
            HttpStatus.CREATED
        );
    }

    @GetMapping
    private ResponseEntity<List<AppointmentDTO>> findAppointments() {
        return ResponseEntity
            .ok()
            .body(appointmentService.findAppointments());
    }

}
