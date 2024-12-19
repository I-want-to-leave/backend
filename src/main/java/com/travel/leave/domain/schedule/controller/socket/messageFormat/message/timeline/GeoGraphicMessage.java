package com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline;

import java.math.BigDecimal;

public record GeoGraphicMessage(
        Long travelLocationCode,
        BigDecimal longitude,
        BigDecimal latitude
) {
}
