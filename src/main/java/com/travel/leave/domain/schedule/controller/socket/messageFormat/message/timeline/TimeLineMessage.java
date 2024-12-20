package com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline;

import java.sql.Timestamp;

public record TimeLineMessage(
        Long id,
        String time,
        String title,
        String description
) {
}
