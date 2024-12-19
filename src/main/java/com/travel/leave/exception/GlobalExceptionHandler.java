package com.travel.leave.exception;

import com.travel.leave.exception.common_exception.BaseRuntimeException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleBaseRuntimeException(BaseRuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ex.getStatusCode());
        response.put("message", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "정해진 enum 타입으로만 정렬이 가능합니다" + ex.getName());
        response.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } // EntityNotFoundException 한 가지 종속적인 상태코드를 가지기에 상태코드를 안 줌

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    } // AccessDeniedException 한 가지 종속적인 상태코드를 가지기에 상태코드를 안 줌

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        log.error("핸들러를 거치지 못하거나 예상치 못한 예외가 발생햇습니다: {}", ex.getMessage(), ex);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}