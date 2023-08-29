package com.jobs.app.repository;

import com.jobs.app.domain.Consultant;
import com.jobs.app.dto.ConsultantDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Integer> {

    @Query("select new com.jobs.app.dto.ConsultantDTO(consultant) from Consultant consultant")
    List<ConsultantDTO> findAllConsultants();

    @Query("select new com.jobs.app.dto.ConsultantDTO(consultant) from Consultant consultant where consultant.id=:consultantId")
    ConsultantDTO findConsultantById(Integer consultantId);

}
