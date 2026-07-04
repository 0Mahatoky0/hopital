package com.groupe25.hopital.data.dto;

import com.groupe25.hopital.data.model.AmenityModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthCenterDTO {

    private Integer gid;
    private AmenityModel amenity;
    private String healthcare;
    private String name;
    private String operator;
    private String speciality;
    private String uuid;
    private Object geometry;
}
