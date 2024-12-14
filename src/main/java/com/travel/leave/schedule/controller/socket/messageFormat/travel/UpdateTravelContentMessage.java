package com.travel.leave.schedule.controller.socket.messageFormat.travel;

public record UpdateTravelContentMessage(
        Long travelCode,
        String travelContent
) {
}
