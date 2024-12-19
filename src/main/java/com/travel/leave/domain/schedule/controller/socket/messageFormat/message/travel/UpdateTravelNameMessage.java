package com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel;

public record UpdateTravelNameMessage(
        Long travelCode,
        String serviceType,
        String title
) {
}
