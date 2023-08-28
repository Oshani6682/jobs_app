package com.jobs.app.controller;

import com.jobs.app.enums.UserRole;
import com.jobs.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/jobSeekers")
    private ResponseEntity findJobSeekers() {
        return ResponseEntity
            .ok()
            .body(userService.findAllUsers(UserRole.JOB_SEEKER));
    }

    @GetMapping("/consultants")
    private ResponseEntity findConsultants() {
        return ResponseEntity
            .ok()
            .body(userService.findAllConsultants());
    }

}
