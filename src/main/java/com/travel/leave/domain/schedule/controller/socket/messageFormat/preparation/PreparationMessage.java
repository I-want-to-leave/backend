package com.travel.leave.domain.schedule.controller.socket.messageFormat.preparation;

public record PreparationMessage(
        Long code,
        String itemName,
        Integer quantity,
        Boolean isDeleted
) {
}
