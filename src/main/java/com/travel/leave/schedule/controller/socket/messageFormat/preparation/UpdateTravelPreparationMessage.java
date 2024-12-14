package com.travel.leave.schedule.controller.socket.messageFormat.preparation;

import java.util.List;

public record UpdateTravelPreparationMessage(
        Long travelCode,
        List<PreparationMessage> preparations
) {
}
