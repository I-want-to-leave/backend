package com.travel.leave.domain.ai_travel.service.recommend.travel_batch;

import com.travel.leave.domain.ai_travel.dto.ai_recommend.RecommendDTO;
import com.travel.leave.domain.ai_travel.mapper.AI_Mapper.AI_TripMapper;
import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travel.repository.TravelRepository;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcBatchTravelService {

    private final TravelRepository travelRepository;
    private final JdbcTemplate jdbcTemplate;

    public Travel saveTravel(RecommendDTO recommendDTO) {
        Travel travel = AI_TripMapper.toTravelEntity(recommendDTO);
        return travelRepository.save(travel);
    }

    public void saveTravelLocations(List<TravelLocation> travelLocations) {
        String sql = "INSERT INTO travel_location (travel_location_content, travel_location_end_time, " +
                "travel_location_latitude, travel_location_longitude, travel_location_name, " +
                "travel_location_start_time, travel_location_step, travel_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TravelLocation location = travelLocations.get(i);
                ps.setString(1, location.getScheduleDetails().getContent());
                ps.setTimestamp(2, location.getScheduleDetails().getEndTime());
                ps.setBigDecimal(3, location.getScheduleDetails().getGeographicDetails().getLatitude());
                ps.setBigDecimal(4, location.getScheduleDetails().getGeographicDetails().getLongitude());
                ps.setString(5, location.getScheduleDetails().getName());
                ps.setTimestamp(6, location.getScheduleDetails().getStartTime());
                ps.setInt(7, location.getScheduleDetails().getStep());
                ps.setLong(8, location.getTravelCode());
            }

            @Override
            public int getBatchSize() {
                return travelLocations.size();
            }
        });
    }

    public void saveTravelPreparations(List<TravelPreparation> travelPreparations) {
        String sql = "INSERT INTO travel_preparation (travel_preparation_is_deleted, travel_preparation_name, " +
                "travel_preparation_quantity, travel_code) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TravelPreparation preparation = travelPreparations.get(i);
                ps.setBoolean(1, preparation.isDeleted());
                ps.setString(2, preparation.getName());
                ps.setInt(3, preparation.getQuantity());
                ps.setLong(4, preparation.getTravelCode());
            }

            @Override
            public int getBatchSize() {
                return travelPreparations.size();
            }
        });
    }
}