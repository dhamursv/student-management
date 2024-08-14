package com.calibraint.studentmanagement.dto;

import com.calibraint.studentmanagement.common.Department;
import jakarta.validation.constraints.*;

/**
 * Data Transfer Object (DTO) representing the details of a student.
 */
public record StudentDTO(
        @NotEmpty(message = "Name cannot be empty") String name,
        @NotEmpty(message = "Surname cannot be empty") String surname,
        @NotNull(message = "Age cannot be null") Integer age,
        @Email(message = "Email should be valid") @NotBlank(message = "Email is mandatory") String email,
        @NotNull(message = "Department is mandatory") Department department,
        @Min(value = 1, message = "Year must be between 1 and 4")
        @Max(value = 4, message = "Year must be between 1 and 4") int year
) {}