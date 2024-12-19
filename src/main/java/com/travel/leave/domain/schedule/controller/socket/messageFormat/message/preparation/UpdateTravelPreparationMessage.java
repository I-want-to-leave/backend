package com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation;

import java.util.List;

public record UpdateTravelPreparationMessage(
        Long travelCode,
        String serviceType,
        List<PreparationMessage> preparations
) {
}
