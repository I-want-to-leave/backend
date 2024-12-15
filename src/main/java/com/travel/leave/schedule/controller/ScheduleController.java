package com.travel.leave.schedule.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.travel.leave.schedule.controller.socket.SocketMessageHandler;
import com.travel.leave.schedule.dto.get.TravelRequestDTO;
import com.travel.leave.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);
    private final ScheduleService scheduleService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SocketMessageHandler socketMessageHandler;

    @GetMapping("/users")
    public ResponseEntity<?> getUsersForInvite(@RequestParam String emailKeyword) {
        return ResponseEntity.ok(scheduleService.getUsersForInvite(emailKeyword));
    }

    @PostMapping("/schedule/initialize")
    public ResponseEntity<?> initializeSchedule(@AuthenticationPrincipal Long userCode, TravelRequestDTO travelRequestDTO) {
        System.out.println(userCode);
        System.out.println(travelRequestDTO.toString());
        return ResponseEntity.ok(scheduleService.initializeTravel(userCode, travelRequestDTO));
    }

    //이거 실행해서 캐시에 받아올 수 있음 소켓 접속 전 해야함
    @GetMapping("/schedule/{travelCode}")
    public ResponseEntity<?> getSchedule(@PathVariable Long travelCode) {
        return ResponseEntity.ok(scheduleService.loadTravel(travelCode));
    }


    @MessageMapping("/schedule/{travelCode}")
    public void buildSchedule(@PathVariable Long travelCode, String message) throws JsonProcessingException {
        String socketMessage = socketMessageHandler.progress(message);
        simpMessagingTemplate.convertAndSend("/topic/main-schedule/" + travelCode, socketMessage);
    }
}
