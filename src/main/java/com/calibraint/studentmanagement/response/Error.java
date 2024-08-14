package com.calibraint.studentmanagement.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private String field;
    private String message;

    public Error(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
