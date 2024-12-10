package com.travel.leave.schedule.controller;

import com.travel.leave.schedule.dto.request.initialize.ScheduleInitializeRequestDTO;
import com.travel.leave.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsersForInvite(@RequestParam String emailKeyword) {
        return ResponseEntity.ok(scheduleService.getUsersForInvite(emailKeyword));
    }

    @PostMapping("/initialize")
    public ResponseEntity<?> initializeSchedule(@AuthenticationPrincipal Long userCode,
                                                @RequestBody ScheduleInitializeRequestDTO scheduleInitializeRequestDTO){
        scheduleService.initializeSchedule(userCode, scheduleInitializeRequestDTO);
        return ResponseEntity.ok("저장되었습니다.");
    }
}
