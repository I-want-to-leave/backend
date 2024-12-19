package com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline;

import java.math.BigDecimal;
import java.util.List;

public record UpdateTravelLocationGeographicMessage(
        Long travelCode,
        Long travelLocationCode,
        String serviceType,
        List<GeoGraphicMessage> geoGraphicMessages
) {
}
