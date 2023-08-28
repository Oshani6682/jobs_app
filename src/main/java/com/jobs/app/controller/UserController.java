package com.jobs.app.controller;

import com.jobs.app.dto.ConsultantDTO;
import com.jobs.app.dto.CreateConsultantDTO;
import com.jobs.app.dto.CreateUserDTO;
import com.jobs.app.dto.UserDTO;
import com.jobs.app.enums.UserRole;
import com.jobs.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/jobSeekers")
    private ResponseEntity<List<UserDTO>> findJobSeekers() {
        return ResponseEntity
            .ok()
            .body(userService.findAllUsers(UserRole.JOB_SEEKER));
    }

    @PostMapping(value = "/jobSeekers", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<UserDTO> saveJobSeeker(
        @RequestBody @Validated CreateUserDTO userDTO
    ) {
        return new ResponseEntity<UserDTO>(
            userService.saveJobSeeker(userDTO),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/consultants")
    private ResponseEntity findConsultants() {
        return ResponseEntity
            .ok()
            .body(userService.findAllConsultants());
    }

    @PostMapping(value = "/consultants", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ConsultantDTO> saveConsultant(
        @RequestBody @Validated CreateConsultantDTO consultantDTO
    ) {
        return new ResponseEntity<ConsultantDTO>(
            userService.saveConsultant(consultantDTO),
            HttpStatus.CREATED
        );
    }

}
