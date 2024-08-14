package com.calibraint.studentmanagement.model;

import com.calibraint.studentmanagement.common.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a student entity in the system.
 */
@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Surname cannot be empty")
    private String surname;

    @NotNull(message = "Age cannot be null")
    private Integer age;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email is mandatory")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Department is mandatory")
    @Enumerated(EnumType.STRING)
    private Department department;

    @Min(value = 1, message = "Year must be between 1 and 4")
    @Max(value = 4, message = "Year must be between 1 and 4")
    private int year;
}
