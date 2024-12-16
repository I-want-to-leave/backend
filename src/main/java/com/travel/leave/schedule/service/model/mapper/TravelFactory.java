package com.travel.leave.schedule.service.model.mapper;

import com.travel.leave.ai_travel.entity.Travel;
import com.travel.leave.schedule.dto.get.TravelRequestDTO;
import com.travel.leave.utility.ImageProcessor;

import java.sql.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class TravelFactory {
    public Travel generate(TravelRequestDTO travelRequestDTO) {
        String rawImage = getRepresentativeImage(travelRequestDTO.image());

        if(isImagePath(rawImage)){  //만약 민규에게 요청받은 이미지가 경로가 아니면(사진파일자체일시), 이미지 저장로직까지
            return Travel.of(
                    null,
                    travelRequestDTO.title(),
                    travelRequestDTO.information(),
                    Date.valueOf(travelRequestDTO.startDate()),
                    Date.valueOf(travelRequestDTO.endDate()),
                    null,
                    null,
                    ImageProcessor.saveImage(rawImage)
            );
        }
        return Travel.of(   //이미지 경로일 시 그냥 넣어줌
                null,
            travelRequestDTO.title(),
            travelRequestDTO.information(),
                Date.valueOf(travelRequestDTO.startDate()),
                Date.valueOf(travelRequestDTO.endDate()),
            null,
            null,
            rawImage
        );
    }

    private String getRepresentativeImage(List<String> image) {
        return image.get(0);
    }

    private boolean isImagePath(String rawImage) {
        return rawImage.contains(".png") || rawImage.contains(".jpg") || rawImage.contains(".img");
    }
}
