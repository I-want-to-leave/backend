package com.travel.leave.domain.schedule.service.mapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateArrayDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext text) throws IOException {
        int[] dateArray = p.readValueAs(int[].class);
        return LocalDate.of(dateArray[0], dateArray[1], dateArray[2]);
    }
}