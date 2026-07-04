package com.groupe25.hopital.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "centre_sante")
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

    // Constructeur par défaut obligatoire
    public HealthCenterModel() {
    }

    // Getters et Setters
    public Integer getGid() { return gid; }
    public void setGid(Integer gid) { this.gid = gid; }

    public Double getOsmId() { return osmId; }
    public void setOsmId(Double osmId) { this.osmId = osmId; }

    public String getOsmType() { return osmType; }
    public void setOsmType(String osmType) { this.osmType = osmType; }

    public BigDecimal getCompletene() { return completene; }
    public void setCompletene(BigDecimal completene) { this.completene = completene; }

 public AmenityModel getAmenity() {
     return amenity;
 }public void setAmenity(AmenityModel amenity) {
     this.amenity = amenity;
 }

    public String getHealthcare() { return healthcare; }
    public void setHealthcare(String healthcare) { this.healthcare = healthcare; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getSpeciality() { return speciality; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }

    public String getOperatorT() { return operatorT; }
    public void setOperatorT(String operatorT) { this.operatorT = operatorT; }

    public String getOperationa() { return operationa; }
    public void setOperationa(String operationa) { this.operationa = operationa; }

    public String getOpeningHo() { return openingHo; }
    public void setOpeningHo(String openingHo) { this.openingHo = openingHo; }

    public String getBeds() { return beds; }
    public void setBeds(String beds) { this.beds = beds; }

    public String getStaffDoct() { return staffDoct; }
    public void setStaffDoct(String staffDoct) { this.staffDoct = staffDoct; }

    public String getStaffNurs() { return staffNurs; }
    public void setStaffNurs(String staffNurs) { this.staffNurs = staffNurs; }

    public String getHealthAme() { return healthAme; }
    public void setHealthAme(String healthAme) { this.healthAme = healthAme; }

    public String getDispensing() { return dispensing; }
    public void setDispensing(String dispensing) { this.dispensing = dispensing; }

    public String getWheelchair() { return wheelchair; }
    public void setWheelchair(String wheelchair) { this.wheelchair = wheelchair; }

    public String getEmergency() { return emergency; }
    public void setEmergency(String emergency) { this.emergency = emergency; }

    public String getInsurance() { return insurance; }
    public void setInsurance(String insurance) { this.insurance = insurance; }

    public String getWaterSour() { return waterSour; }
    public void setWaterSour(String waterSour) { this.waterSour = waterSour; }

    public String getElectricity() { return electricit; }
    public void setElectricity(String electricit) { this.electricit = electricit; }

    public String getIsInHeal() { return isInHeal; }
    public void setIsInHeal(String isInHeal) { this.isInHeal = isInHeal; }

    public String getIsInHe1() { return isInHe1; }
    public void setIsInHe1(String isInHe1) { this.isInHe1 = isInHe1; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getAddrHouse() { return addrHouse; }
    public void setAddrHouse(String addrHouse) { this.addrHouse = addrHouse; }

    public String getAddrStree() { return addrStree; }
    public void setAddrStree(String addrStree) { this.addrStree = addrStree; }

    public String getAddrPostc() { return addrPostc; }
    public void setAddrPostc(String addrPostc) { this.addrPostc = addrPostc; }

    public String getAddrCity() { return addrCity; }
    public void setAddrCity(String addrCity) { this.addrCity = addrCity; }

    public BigDecimal getChangeset() { return changeset; }
    public void setChangeset(BigDecimal changeset) { this.changeset = changeset; }

    public BigDecimal getChangese1() { return changese1; }
    public void setChangese1(BigDecimal changese1) { this.changese1 = changese1; }

    public LocalDate getChangese2() { return changese2; }
    public void setChangese2(LocalDate changese2) { this.changese2 = changese2; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public Geometry getGeom() { return geom; }
    public void setGeom(Geometry geom) { this.geom = geom; }
}