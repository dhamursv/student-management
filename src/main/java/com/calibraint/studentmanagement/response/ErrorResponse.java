package com.calibraint.studentmanagement.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    String message;
    String error;
    int status;


    public ErrorResponse(String message, String error, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.error = error;
    }

    public static ErrorResponse error(String error, HttpStatus status) {
        return new ErrorResponse("failed", error, status);
    }
}