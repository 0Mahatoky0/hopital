package com.groupe25.hopital.map.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "province")
public class Province {

    @Id
    @Column(name = "gid")
    private Long id;

    @Column(name = "adm1_en")
    private String nom;

    @Column(name = "geom", columnDefinition = "geometry(MultiPolygon,4326)")
    private Geometry geom;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Geometry getGeom() { return geom; }
    public void setGeom(Geometry geom) { this.geom = geom; }
}