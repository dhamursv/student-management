package com.calibraint.studentmanagement.repository;

import com.calibraint.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Student} entities.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {}
