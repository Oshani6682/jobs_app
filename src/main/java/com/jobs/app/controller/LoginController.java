package com.jobs.app.controller;

import com.jobs.app.dto.LoginDTO;
import com.jobs.app.dto.UserDTO;
import com.jobs.app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<UserDTO> login(
        @RequestBody @Validated LoginDTO loginDTO
    ) {
        return ResponseEntity
            .ok()
            .body(loginService.login(loginDTO));
    }

}
