package com.travel.leave.schedule.controller.socket.messageFormat.preparation;

public record PreparationMessage(
        Long code,
        String itemName,
        Integer quantity,
        Boolean isDeleted
) {
}
