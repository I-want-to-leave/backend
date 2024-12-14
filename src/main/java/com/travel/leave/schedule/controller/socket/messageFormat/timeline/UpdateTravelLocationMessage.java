package com.travel.leave.schedule.controller.socket.messageFormat.timeline;

import java.time.LocalDate;
import java.util.List;

//하루 일정을 나타내는 메시지
public record UpdateTravelLocationMessage(
        Long travelCode,
        List<ScheduleMessage> schedule
) {

}
