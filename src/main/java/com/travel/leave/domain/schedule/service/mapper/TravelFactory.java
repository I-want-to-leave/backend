package com.travel.leave.domain.schedule.service.mapper;

import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.domain.schedule.dto.get.TravelRequestDTO;
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

        log.info(travelRequestDTO.toString());

        if(isImagePath(rawImage)){  //만약 민규에게 요청받은 이미지가 경로가 아니면(사진파일자체일시), 이미지 저장로직까지
            return Travel.builder()
                    .name(travelRequestDTO.title())
                    .content(travelRequestDTO.information())
                    .startDate(Date.valueOf(travelRequestDTO.startDate().split("T")[0]))
                    .endDate(Date.valueOf(travelRequestDTO.startDate().split("T")[0]))
                    .imageUrl(ImageProcessor.saveImage(rawImage))
                    .build();
        }
        return Travel.builder()
                .name(travelRequestDTO.title())
                .content(travelRequestDTO.information())
                .startDate(Date.valueOf(travelRequestDTO.startDate()))
                .endDate(Date.valueOf(travelRequestDTO.endDate()))
                .imageUrl(rawImage)
                .build();
    }

    private String getRepresentativeImage(List<String> image) {
        return image.get(0);
    }

    private boolean isImagePath(String rawImage) {
        return rawImage.contains(".png") || rawImage.contains(".jpg") || rawImage.contains(".img");
    }
}
