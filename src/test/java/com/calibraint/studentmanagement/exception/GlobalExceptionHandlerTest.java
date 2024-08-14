package com.calibraint.studentmanagement.exception;

import com.calibraint.studentmanagement.response.Error;
import com.calibraint.studentmanagement.response.ErrorResponse;
import com.calibraint.studentmanagement.response.ErrorsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerTest {

    @Mock
    private Logger logger;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    @Test
    void test_handleMethodArgumentNotValidException() {
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(new FieldError("student", "name", "Name cannot be empty"), new FieldError("student", "email", "Email is mandatory")));

        ErrorsResponse response = globalExceptionHandler.handleValidationExceptions(exception);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.status);
        Error error1 = response.getErrors().get(0);

        assertEquals("name", error1.getField());
        assertEquals("Name cannot be empty", error1.getMessage());
        verify(bindingResult, times(1)).getFieldErrors();
    }

    @Test
    void test_handleStudentNotFoundException() {
        String errorMessage = "Student not found";
        StudentNotFoundException exception = new StudentNotFoundException(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleStudentNotFoundException(exception);
        assertNotNull(response);
        assertEquals(errorMessage, response.getError());
    }

    @Test
    void test_handleIntegrityViolationException() {
        String errorMessage = "Data integrity violation";
        DataIntegrityViolationException exception = new DataIntegrityViolationException(errorMessage);

        ErrorResponse response = globalExceptionHandler.handleIntegrityViolationException(exception);
        assertNotNull(response);
        assertEquals(errorMessage, response.getError());
    }
}
