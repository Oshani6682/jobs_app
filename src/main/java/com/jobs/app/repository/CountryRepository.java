package com.jobs.app.repository;

import com.jobs.app.domain.Country;
import com.jobs.app.domain.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
