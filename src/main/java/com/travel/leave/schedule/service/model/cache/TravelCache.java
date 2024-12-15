package com.travel.leave.schedule.service.model.cache;

import com.travel.leave.schedule.controller.socket.messageFormat.preparation.PreparationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.timeline.ScheduleMessage;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TravelCache {
    private Long travelCode;
    private String title;
    private String information;
    private LocalDate startDate;
    private LocalDate endDate;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private List<ScheduleMessage> schedule;    //1번째날 스케쥴, 2번째날 스케쥴...
    private List<PreparationMessage> preparation;
    private List<String> imageUrl;
    private Boolean isDeleted;
    private List<String> usernames;

    public static TravelCache of(Long travelCode,
                                 String title,
                                 String information,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 Timestamp createdAt,
                                 Timestamp deletedAt,
                                 List<ScheduleMessage> schedule,
                                 List<PreparationMessage> preparation,
                                 String imageUrl,
                                 List<String> usernames){
        return new TravelCache(travelCode, title, information, startDate, endDate, createdAt, deletedAt, schedule, preparation, List.of(imageUrl), false, usernames);
    }
}
