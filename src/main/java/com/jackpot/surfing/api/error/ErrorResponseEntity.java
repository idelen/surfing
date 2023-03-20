package com.jackpot.surfing.api.error;

import java.util.Map;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {
    private int errorCode;
    private String errorType;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(HttpStatus status, String errorType, String message){
        return ResponseEntity
            .status(status)
            .body(ErrorResponseEntity.builder()
                .errorCode(status.value())
                .errorType(errorType)
                .message(message)
                .build());
    }
}
