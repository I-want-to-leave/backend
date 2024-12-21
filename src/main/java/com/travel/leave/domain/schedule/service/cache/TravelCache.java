package com.travel.leave.domain.schedule.service.cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.PreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.GeoGraphicMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.ScheduleMessage;
import com.travel.leave.domain.schedule.service.mapper.LocalDateArrayDeserializer;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelCache {
    private Long travelCode;
    private String title;
    private String information;
    @JsonDeserialize(using = LocalDateArrayDeserializer.class)
    private LocalDate startDate;
    @JsonDeserialize(using = LocalDateArrayDeserializer.class)
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
