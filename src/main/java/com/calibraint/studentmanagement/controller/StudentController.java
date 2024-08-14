package com.calibraint.studentmanagement.controller;

import com.calibraint.studentmanagement.dto.StudentDTO;
import com.calibraint.studentmanagement.model.Student;
import com.calibraint.studentmanagement.response.SuccessResponse;
import com.calibraint.studentmanagement.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing students.
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Retrieves a list of all students.
     *
     * This method handles HTTP GET requests to retrieve a list of all students.
     * It returns a SuccessResponse containing the list of students.
     *
     * @return SuccessResponse object containing the list of all students.
     */
    @GetMapping
    public SuccessResponse getAllStudents() {
        logger.info("Received request to retrieve all students.");
        List<Student> students = studentService.getAllStudents();
        logger.info("Returning {} students in response.", students.size());

        return SuccessResponse.success(students);
    }

    /**
     * Retrieves the names and IDs of all students.
     *
     * This method handles HTTP GET requests to retrieve a list of student names
     * and their corresponding IDs. It returns a SuccessResponse containing this data.
     *
     * @return SuccessResponse object containing the list of student names and IDs.
     */
    @GetMapping("/names")
    public SuccessResponse getStudentNameAndId() {
        logger.info("Received request to retrieve names and IDs of all students.");
        return SuccessResponse.success(studentService.getStudentNameAndId());
    }

    /**
     * Retrieves a student by their ID.
     *
     * This method handles HTTP GET requests to retrieve a student with the given ID.
     * It returns a SuccessResponse containing the student's details if found.
     *
     * @param id the ID of the student to retrieve.
     * @return SuccessResponse object containing the student details.
     */
    @GetMapping("/{id}")
    public SuccessResponse getStudentById(@PathVariable Long id) {
        logger.info("Received request to retrieve student with ID: {}", id);
        Student student = studentService.getStudentById(id);
        logger.info("Returning student details for ID: {}", id);

        return SuccessResponse.success(student);
    }

    /**
     * Creates a new student.
     *
     * This method handles HTTP POST requests to create a new student based on the provided
     * student data. The data is validated before creating a new student record.
     *
     * @param studentDTO the data transfer object containing student details.
     * @return SuccessResponse object indicating the creation success and containing the created student details.
     */
    @PostMapping
    public SuccessResponse createStudent(@Validated @RequestBody StudentDTO studentDTO) {
        logger.info("Received request to create student with details: {}", studentDTO);
        Student student = new Student();
        student.setName(studentDTO.name());
        student.setSurname(studentDTO.surname());
        student.setAge(studentDTO.age());
        student.setEmail(studentDTO.email());
        student.setDepartment(studentDTO.department());
        student.setYear(studentDTO.year());

        return SuccessResponse.success(studentService.createStudent(student));
    }

    /**
     * Updates an existing student.
     *
     * This method handles HTTP PUT requests to update an existing student based on the provided
     * student data and the student's ID. The data is validated before updating the student record.
     *
     * @param id the ID of the student to be updated.
     * @param studentDTO the data transfer object containing updated student details.
     * @return SuccessResponse object indicating the update success and containing the updated student details.
     */
    @PutMapping("/{id}")
    public SuccessResponse updateStudent(@PathVariable Long id, @Validated @RequestBody StudentDTO studentDTO) {
        logger.info("Received request to update student with ID: {} and details: {}", id, studentDTO);
        Student student = new Student();
        student.setName(studentDTO.name());
        student.setSurname(studentDTO.surname());
        student.setAge(studentDTO.age());
        student.setEmail(studentDTO.email());
        student.setDepartment(studentDTO.department());
        student.setYear(studentDTO.year());

        Student student1 = studentService.updateStudent(id, student);
        logger.info("Successfully updated student with ID: {}", id);

        return SuccessResponse.success(student1);
    }

    /**
     * Deletes a student by ID.
     *
     * This method handles HTTP DELETE requests to remove a student record from the database
     * based on the provided student ID.
     *
     * @param id the ID of the student to be deleted.
     * @return a ResponseEntity with no content indicating that the deletion was successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        logger.info("Received request to delete student with ID: {}", id);

        studentService.deleteStudent(id);
        logger.info("Successfully deleted student with ID: {}", id);

        return ResponseEntity.noContent().build();
    }
}
