package com.groupe25.hopital.data.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.groupe25.hopital.data.model.OSRMResponse;

@Component
public class OSRMClient {

    private final WebClient webClient;

    public OSRMClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public OSRMResponse getRoute(double lat1, double lon1, double lat2, double lon2) {
        String url = "/route/v1/driving/" + lon1 + "," + lat1 + ";" + lon2 + "," + lat2 + "?overview=full&geometries=geojson";

        return webClient.get().uri(url).retrieve().bodyToMono(OSRMResponse.class).block();
    }
}
