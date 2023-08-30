package com.jobs.app.service;

import com.jobs.app.domain.Country;
import com.jobs.app.dto.CountryDTO;
import com.jobs.app.repository.CountryRepository;
import com.jobs.app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryRepository countryRepository;

    public CountryDTO saveCountry(CountryDTO createCountryDto) {
        LOGGER.info("Save country: {} - Started", createCountryDto.getCountry());

        createCountryDto.setCountry(
            Utils.capitalizeEachLetter(createCountryDto.getCountry())
        );

        Country country = new Country(createCountryDto);
        return new CountryDTO(countryRepository.save(country));
    }

    public List<CountryDTO> findCountries() {
        LOGGER.info("Find countries - Started");
        return countryRepository.findAllActiveCountries();
    }

}
