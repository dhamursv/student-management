package com.calibraint.studentmanagement.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorsResponse {
    public String message;
    public int status;
    public List<Error> errors;

    public ErrorsResponse(HttpStatus status, String message, List<Error> errorList) {
        this.message = message;
        this.status = status.value();
        this.errors = errorList;
    }

    public static ErrorsResponse errors(HttpStatus status, List<Error> errorList) {
        return new ErrorsResponse(status, "failed", errorList);
    }
}
