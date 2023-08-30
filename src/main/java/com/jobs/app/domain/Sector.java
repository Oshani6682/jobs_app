package com.jobs.app.domain;

import com.jobs.app.dto.SectorDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sector")
public class Sector implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    public String sector;

    @Column(name = "is_active")
    public Boolean isActive = true;

    public Sector() {}

    public Sector(SectorDTO sectorDTO) {
        sector = sectorDTO.getSector();
    }

}
