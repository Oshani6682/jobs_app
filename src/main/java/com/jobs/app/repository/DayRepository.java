package com.jobs.app.repository;

import com.jobs.app.domain.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {

    List<Day> findByOrderByIdAsc();

}
