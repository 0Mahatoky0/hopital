package com.groupe25.hopital.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteDTO {
    private double distance;
    private double duration;
    private Object geometry;
}