package com.groupe25.hopital.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NearestHealthCenterDTO {
    private HealthCenterDTO center;
    private RouteDTO route;
}