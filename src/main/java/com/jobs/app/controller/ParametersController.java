package com.jobs.app.controller;

import com.jobs.app.dto.CountryDTO;
import com.jobs.app.service.CountryService;
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

}
