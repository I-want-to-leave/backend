package com.travel.leave.travel.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.exception.BadReqeust.GoogleMapsResponseException;
import com.travel.leave.travel.TransportType;
import com.travel.leave.travel.dto.ai.LatLngDTO;

public class GoogleMapsMapper {

    public static LatLngDTO ofLatLngDTO(JsonNode response, String placeName, TransportType transportType) {
        if (response != null && "OK".equals(response.path("status").asText())) {
            JsonNode location = response.path("results").get(0).path("geometry").path("location");
            return new LatLngDTO(
                    placeName,
                    location.path("lat").asDouble(),
                    location.path("lng").asDouble(),
                    transportType
            );
        }
        throw new GoogleMapsResponseException("파악할 수 없는 장소");
    }
}
