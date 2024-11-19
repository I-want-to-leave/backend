package com.travel.leave.join.controller;

import com.travel.leave.exception.UsernameDuplicationException;
import com.travel.leave.join.dto.JoinRequestDTO;
import com.travel.leave.join.model.validator.username.UsernameFormat;
import com.travel.leave.join.service.JoinService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequestDTO joinRequestDTO) {
            joinService.join(joinRequestDTO);
            return ResponseEntity.ok().build();
    }

    @PostMapping("/join/username/validate")
    public ResponseEntity<?> validateDuplication(@RequestParam String username) {
            joinService.validateDuplication(username);
            return ResponseEntity.ok().build();
    }
}
