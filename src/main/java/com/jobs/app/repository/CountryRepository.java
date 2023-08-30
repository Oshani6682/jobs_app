package com.jobs.app.repository;

import com.jobs.app.domain.Country;
import com.jobs.app.dto.CountryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("select new com.jobs.app.dto.CountryDTO(country) from Country country where country.isActive=true")
    List<CountryDTO> findAllActiveCountries();

}
