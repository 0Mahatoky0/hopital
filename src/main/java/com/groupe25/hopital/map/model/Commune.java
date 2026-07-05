package com.groupe25.hopital.map.model;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "commune")
public class Commune {

    @Id
    @Column(name = "gid")
    private Long id;

    @Column(name = "adm3_en")
    private String nom;

    @Column(name = "adm2_en")
    private String nomDistrict; // nom du district parent (pas de FK, juste le texte)

    @Column(name = "geom", columnDefinition = "geometry(MultiPolygon,4326)")
    private Geometry geom;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getNomDistrict() { return nomDistrict; }
    public void setNomDistrict(String nomDistrict) { this.nomDistrict = nomDistrict; }

    public Geometry getGeom() { return geom; }
    public void setGeom(Geometry geom) { this.geom = geom; }
}