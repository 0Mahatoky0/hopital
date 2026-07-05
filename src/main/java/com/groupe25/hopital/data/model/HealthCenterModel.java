package com.groupe25.hopital.data.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.locationtech.jts.geom.Geometry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "centre_sante")
@Getter
@Setter
@NoArgsConstructor
public class HealthCenterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private Integer gid;

    @Column(name = "osm_id")
    private Double osmId;

    @Column(name = "osm_type", length = 80)
    private String osmType;

    @Column(name = "completene")
    private BigDecimal completene;

    @ManyToOne
    @JoinColumn(name = "amenity")
    private AmenityModel amenity;

    @Column(name = "healthcare", length = 80)
    private String healthcare;

    @Column(name = "name", length = 80)
    private String name;

    @Column(name = "operator", length = 80)
    private String operator;

    @Column(name = "source", length = 80)
    private String source;

    @Column(name = "speciality", length = 80)
    private String speciality;

    @Column(name = "operator_t", length = 80)
    private String operatorT;

    @Column(name = "operationa", length = 80)
    private String operationa;

    @Column(name = "opening_ho", length = 80)
    private String openingHo;

    @Column(name = "beds", length = 80)
    private String beds;

    @Column(name = "staff_doct", length = 80)
    private String staffDoct;

    @Column(name = "staff_nurs", length = 80)
    private String staffNurs;

    @Column(name = "health_ame", length = 80)
    private String healthAme;

    @Column(name = "dispensing", length = 80)
    private String dispensing;

    @Column(name = "wheelchair", length = 80)
    private String wheelchair;

    @Column(name = "emergency", length = 80)
    private String emergency;

    @Column(name = "insurance", length = 80)
    private String insurance;

    @Column(name = "water_sour", length = 80)
    private String waterSour;

    @Column(name = "electricit", length = 80)
    private String electricit;

    @Column(name = "is_in_heal", length = 80)
    private String isInHeal;

    @Column(name = "is_in_he_1", length = 80)
    private String isInHe1;

    @Column(name = "url", length = 80)
    private String url;

    @Column(name = "addr_house", length = 80)
    private String addrHouse;

    @Column(name = "addr_stree", length = 80)
    private String addrStree;

    @Column(name = "addr_postc", length = 80)
    private String addrPostc;

    @Column(name = "addr_city", length = 80)
    private String addrCity;

    @Column(name = "changeset_")
    private BigDecimal changeset;

    @Column(name = "changese_1")
    private BigDecimal changese1;

    @Column(name = "changese_2")
    private LocalDate changese2;

    @Column(name = "uuid", length = 80)
    private String uuid;

    @Column(name = "geom", columnDefinition = "Geometry")
    private Geometry geom;

}