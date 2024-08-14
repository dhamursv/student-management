package com.calibraint.studentmanagement.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SuccessResponse {
    private int status;
    public Object data;
    public String message;

    public SuccessResponse(HttpStatus status, String message, Object data) {
        this.message = message;
        this.status = status.value();
        this.data = data;
    }

    public static SuccessResponse success(Object data) {
        return new SuccessResponse(HttpStatus.OK, "success", data);
    }
}

