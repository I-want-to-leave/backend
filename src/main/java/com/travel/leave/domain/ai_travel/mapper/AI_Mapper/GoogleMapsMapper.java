package com.travel.leave.domain.ai_travel.mapper.AI_Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.leave.domain.ai_travel.exception.AiExceptionMessage;
import com.travel.leave.domain.ai_travel.exception.GoogleMapsResponseException;
import com.travel.leave.domain.ai_travel.service.trip_enum.TransportType;
import com.travel.leave.domain.ai_travel.dto.ai_recommend.LatLngDTO;

import java.sql.Timestamp;

public class GoogleMapsMapper {

    public static LatLngDTO ofLatLngDTO(JsonNode response, String placeName, int stepOrder, Timestamp startAt, Timestamp endAt, TransportType transportType) {
        if (response != null && "OK".equals(response.path("status").asText())) {
            JsonNode location = response.path("results").get(0).path("geometry").path("location");
            return new LatLngDTO(
                    placeName,
                    location.path("lat").asDouble(),
                    location.path("lng").asDouble(),
                    stepOrder,
                    startAt,
                    endAt,
                    transportType
            );
        }
        throw new GoogleMapsResponseException(AiExceptionMessage.GOOGLE_MAPS_PLACE_NOT_FOUND);
    }
}
