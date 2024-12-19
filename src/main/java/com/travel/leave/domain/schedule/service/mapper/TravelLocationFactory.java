package com.travel.leave.domain.schedule.service.mapper;

import com.travel.leave.subdomain.travellocaion.entity.ScheduleDetails;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.domain.schedule.dto.get.TravelLocationRequestDTO;
import com.travel.leave.domain.schedule.dto.get.TravelLocationRequestDTOs;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * schedule-> TravelLocation
 */
@Component
@RequiredArgsConstructor
public class TravelLocationFactory {
    public List<TravelLocation> generate(Long travelCode, List<TravelLocationRequestDTOs> schedule) {
        List<TravelLocation> travelLocations = new ArrayList<>();
        for(TravelLocationRequestDTOs travelLocationRequestDTOs : schedule) {
            travelLocations.addAll(getOneDayTravelLocations(travelCode, travelLocationRequestDTOs));
        }
        return travelLocations;
    }

    private List<TravelLocation> getOneDayTravelLocations(Long travelCode, TravelLocationRequestDTOs schedule) {
        List<TravelLocation> oneDayTravelLocations = new ArrayList<>();

        for(int i = 0; i < schedule.timelines().size(); i++){
            TravelLocationRequestDTO tempTimeLine = schedule.timelines().get(i);
            if(isLastTimeLine(i+1, schedule.timelines().size())){
                oneDayTravelLocations.add(getOneDayOneTravelLocation(travelCode, schedule.date(), tempTimeLine, tempTimeLine.time(),i+1));
                continue;
            }
            TravelLocationRequestDTO nextTimeLine = schedule.timelines().get(i+1);
            oneDayTravelLocations.add(getOneDayOneTravelLocation(travelCode, schedule.date(), tempTimeLine, nextTimeLine.time(), i+1));
        }

        return oneDayTravelLocations;
    }

    private TravelLocation getOneDayOneTravelLocation(Long travelCode,
                                                      String date,
                                                      TravelLocationRequestDTO tempTimeLine,
                                                      String nextTime,
                                                      int step) {
        return TravelLocation.of(travelCode, getScheduleDetails(date, tempTimeLine, nextTime, step));
    }

    private ScheduleDetails getScheduleDetails(String date, TravelLocationRequestDTO tempTimeLine, String nextTime, int step) {
        String startTime = date + " " + tempTimeLine.time() + ":00.0";
        String endTime = date + " " + nextTime + ":00.0";
        return ScheduleDetails.of(tempTimeLine.title(), tempTimeLine.description(), Timestamp.valueOf(startTime), Timestamp.valueOf(endTime), step, null);
    }

    private boolean isLastTimeLine(int step, int size){
        return step >= size;
    }
}

/*
 * step 은 날마다 바뀜
 */



/*
 * TravelLocationRequestDTO(
 *         Long code,
 *         Timestamp time,
 *         String title,
 *         String description
 * )
 */

/*
 * TravelLocationRequestDTOs(
 *     LocalDate travelDate,
 *     List<TravelLocationRequestDTO> timeLines
 * )
 */

/*
 *     private Long travelLocationCode;
 *
 *     @Embedded
 *     private ScheduleDetails scheduleDetails;
 *
 *     @Column(name = "travel_code")
 *     private Long travelCode;
 */