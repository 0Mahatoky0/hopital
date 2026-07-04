package com.groupe25.hopital.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import org.locationtech.jts.geom.MultiPolygon;

import com.fasterxml.jackson.annotation.JsonRawValue;

@Entity
@Table(name = "v_communes_ratio_sante")
@Immutable
public class CommuneRatioSante {

    @Id
    private Integer gid;

    @Column(name = "nom_commune")
    private String nomCommune;

    @Column(name = "district")
    private String district;

    @Column(name = "region")
    private String region;

    private Integer population;

    @Column(name = "nombre_centres")
    private Long nombreCentres;

    @Column(name = "ratio_pour_10000_hab")
    private Double ratioPour10000Hab;

    @Column(name = "geom", columnDefinition = "text")
    @JsonRawValue
    private String geom;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Long getNombreCentres() {
        return nombreCentres;
    }

    public void setNombreCentres(Long nombreCentres) {
        this.nombreCentres = nombreCentres;
    }

    public Double getRatioPour10000Hab() {
        return ratioPour10000Hab;
    }

    public void setRatioPour10000Hab(Double ratioPour10000Hab) {
        this.ratioPour10000Hab = ratioPour10000Hab;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }
    
}