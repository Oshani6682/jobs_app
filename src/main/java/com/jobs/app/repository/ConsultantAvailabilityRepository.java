package com.jobs.app.repository;

import com.jobs.app.domain.ConsultantAvailability;
import com.jobs.app.dto.ConsultantAvailabilityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantAvailabilityRepository extends JpaRepository<ConsultantAvailability, Integer> {

    @Query("select new com.jobs.app.dto.ConsultantAvailabilityDTO(availability, true) " +
            "from ConsultantAvailability availability where availability.consultant=:consultant and " +
            "availability.day.id=:day")
    List<ConsultantAvailabilityDTO> findAllByConsultantAndDayIncludingCode(int consultant, int day);

    @Query("select new com.jobs.app.dto.ConsultantAvailabilityDTO(availability, false) " +
            "from ConsultantAvailability availability where availability.consultant=:consultant")
    List<ConsultantAvailabilityDTO> findAllByConsultantAndDayExcludingCode(int consultant);

}
