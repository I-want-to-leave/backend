package com.travel.leave.schedule.controller.socket.messageFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.leave.schedule.controller.socket.messageFormat.preparation.UpdateTravelPreparationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.timeline.UpdateTravelLocationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.travel.UpdateTravelContentMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.travel.UpdateTravelNameMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageFormatMapper {
    private final ObjectMapper objectMapper;

    public Long getTravelCode(String message) throws JsonProcessingException {
        JsonNode node = objectMapper.readTree(message);
        return node.get("travelCode").asLong();
    }

    public String getServiceType(String message) throws JsonProcessingException {
        JsonNode node = objectMapper.readTree(message);
        return node.get("serviceType").asText();
    }


    public UpdateTravelPreparationMessage mapToUpdateTravelPreparationMessage(String message){
        return objectMapper.convertValue(message, UpdateTravelPreparationMessage.class);
    }

    public UpdateTravelContentMessage mapToUpdateTravelContentMessage(String message){
        return objectMapper.convertValue(message, UpdateTravelContentMessage.class);
    }

    public UpdateTravelNameMessage mapToUpdateTravelNameMessage(String message){
        return objectMapper.convertValue(message, UpdateTravelNameMessage.class);
    }

    public UpdateTravelLocationMessage mapToUpdateTravelLocationMessage(String message){
        return objectMapper.convertValue(message, UpdateTravelLocationMessage.class);
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
