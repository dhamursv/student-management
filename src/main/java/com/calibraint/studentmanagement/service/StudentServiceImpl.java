package com.calibraint.studentmanagement.service;

import com.calibraint.studentmanagement.exception.StudentNotFoundException;
import com.calibraint.studentmanagement.model.Student;
import com.calibraint.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link StudentService} for managing student data.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll().stream().toList();
    }

    /**
     * Retrieves student names and IDs from the repository.
     *
     * @return a list of maps, each containing student ID and full name.
     */
    @Override
    public List<Object> getStudentNameAndId() {
        return studentRepository.findAll().stream().mapMulti((student, consumer) -> {
            String fullName = student.getName() + " " + student.getSurname();
            Map<String, String> studentInfo = Map.of("id", student.getId().toString(), "name", fullName);
            consumer.accept(studentInfo);
        }).toList();
    }

    /**
     * Retrieves a student by ID from the repository.
     *
     * @param id the ID of the student to retrieve.
     * @return the student with the specified ID.
     * @throws StudentNotFoundException if no student with the given ID is found.
     */
    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found"));
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Updates an existing student in the repository.
     *
     * @param id the ID of the student to update.
     * @param student the updated student data.
     * @return the updated student.
     * @throws StudentNotFoundException if no student with the given ID is found.
     */
    @Override
    public Student updateStudent(Long id, Student student) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found");
        }
        student.setId(id);
        return studentRepository.save(student);
    }


    /**
     * Deletes a student by ID from the repository.
     *
     * @param id the ID of the student to delete.
     * @throws StudentNotFoundException if no student with the given ID is found.
     */
    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found");
        }
        studentRepository.deleteById(id);
    }
}
