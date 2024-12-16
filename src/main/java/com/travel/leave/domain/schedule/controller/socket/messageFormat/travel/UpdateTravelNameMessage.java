package com.travel.leave.domain.schedule.controller.socket.messageFormat.travel;

public record UpdateTravelNameMessage(
        Long travelCode,
        String title
) {
}
