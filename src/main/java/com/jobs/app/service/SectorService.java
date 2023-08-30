package com.jobs.app.service;

import com.jobs.app.domain.Sector;
import com.jobs.app.dto.SectorDTO;
import com.jobs.app.repository.SectorRepository;
import com.jobs.app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SectorService.class);

    @Autowired
    private SectorRepository sectorRepository;

    public SectorDTO saveSector(SectorDTO createSectorDto) {
        LOGGER.info("Save sector: {} - Started", createSectorDto.getSector());

        createSectorDto.setSector(
            Utils.capitalizeEachLetter(createSectorDto.getSector())
        );

        Sector sector = new Sector(createSectorDto);
        return new SectorDTO(sectorRepository.save(sector));
    }

    public List<SectorDTO> findSectors() {
        LOGGER.info("Find sectors - Started");
        return sectorRepository.findAllActiveSectors();
    }

}
