package com.jobs.app.repository;

import com.jobs.app.domain.Sector;
import com.jobs.app.dto.SectorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Integer> {

    @Query("select new com.jobs.app.dto.SectorDTO(sector) from Sector sector where sector.isActive=true")
    List<SectorDTO> findAllActiveSectors();

}
