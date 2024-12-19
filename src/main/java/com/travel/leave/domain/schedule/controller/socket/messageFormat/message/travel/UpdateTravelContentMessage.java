package com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel;

public record UpdateTravelContentMessage(
        Long travelCode,
        String serviceType,
        String travelContent
) {
}
