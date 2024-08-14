package com.calibraint.studentmanagement.service;

import com.calibraint.studentmanagement.model.Student;

import java.util.List;

/**
 * Service interface for managing {@link Student} entities.
 */
public interface StudentService {
    List<Student> getAllStudents();

    List<Object> getStudentNameAndId();

    Student getStudentById(Long id);
    Student createStudent(Student student);
    Student updateStudent(Long id, Student student);
    void deleteStudent(Long id);
}
