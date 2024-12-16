package com.travel.leave.domain.schedule.controller.socket.messageFormat.travel;

public record UpdateTravelContentMessage(
        Long travelCode,
        String travelContent
) {
}
