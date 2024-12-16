package com.travel.leave.domain.schedule.controller.socket.messageFormat.preparation;

import java.util.List;

public record UpdateTravelPreparationMessage(
        Long travelCode,
        List<PreparationMessage> preparations
) {
}
