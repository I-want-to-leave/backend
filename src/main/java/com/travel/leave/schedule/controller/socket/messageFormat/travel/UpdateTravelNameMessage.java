package com.travel.leave.schedule.controller.socket.messageFormat.travel;

public record UpdateTravelNameMessage(
        Long travelCode,
        String title
) {
}
