package com.travel.leave.schedule.controller.socket.messageFormat.timeline;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record TimeLineMessage(
        Long id,
        Timestamp time,
        String title,
        String content,
        BigDecimal longitude,
        BigDecimal latitude
) {
}
