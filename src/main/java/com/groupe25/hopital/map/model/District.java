package com.groupe25.hopital.map.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "district")
public class District {

    @Id
    @Column(name = "gid")
    private Long id;

    @Column(name = "adm2_en")
    private String nom;

    @Column(name = "adm1_en")
    private String nomProvince; // nom de la région parente (pas de FK, juste le texte)

    @Column(name = "geom", columnDefinition = "geometry(MultiPolygon,4326)")
    private Geometry geom;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getNomProvince() { return nomProvince; }
    public void setNomProvince(String nomProvince) { this.nomProvince = nomProvince; }

    public Geometry getGeom() { return geom; }
    public void setGeom(Geometry geom) { this.geom = geom; }
}