package com.travel.leave.domain.schedule.service.cache;

import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.PreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.GeoGraphicMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.ScheduleMessage;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TravelCache {
    private Long travelCode;
    private String title;
    private String information;
    private LocalDate startDate;
    private LocalDate endDate;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private List<ScheduleMessage> schedule;
    private List<PreparationMessage> preparation;
    private List<GeoGraphicMessage> geographicMessages;
    private List<String> imageUrl;
    private Boolean isDeleted;
    private List<String> usernames;
}
