package com.calibraint.studentmanagement.exception;

import com.calibraint.studentmanagement.response.Error;
import com.calibraint.studentmanagement.response.ErrorResponse;
import com.calibraint.studentmanagement.response.ErrorsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public ErrorResponse handleStudentNotFoundException(StudentNotFoundException ex) {
        return ErrorResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    public ErrorsResponse handleValidationExceptions(MethodArgumentNotValidException invalidArgument) {
        List<Error> errors = invalidArgument.getBindingResult().getFieldErrors().stream()
                .map(error -> new Error(error.getField(), error.getDefaultMessage())).toList();
        return ErrorsResponse.errors(HttpStatus.BAD_REQUEST, errors);
    }

    public ErrorResponse handleIntegrityViolationException
            (DataIntegrityViolationException integrityViolationException) {
        return ErrorResponse.error(integrityViolationException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleExceptions(Exception exception) {
        logger.error("Exception occurred: {}", exception);

        if (exception instanceof MethodArgumentNotValidException invalidArgument) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(handleValidationExceptions(invalidArgument));
        } else if (exception instanceof StudentNotFoundException studentNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(handleStudentNotFoundException(studentNotFoundException));
        } else if (exception instanceof DataIntegrityViolationException integrityViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).
                    body(handleIntegrityViolationException(integrityViolationException));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.error(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
