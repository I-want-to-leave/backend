package com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline;

import java.util.List;

//하루 일정을 나타내는 메시지
public record UpdateTravelLocationMessage(
        Long travelCode,
        String serviceType,
        List<ScheduleMessage> schedule
) {

}
