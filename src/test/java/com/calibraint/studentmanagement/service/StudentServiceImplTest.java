package com.calibraint.studentmanagement.service;


import com.calibraint.studentmanagement.exception.StudentNotFoundException;
import com.calibraint.studentmanagement.model.Student;
import com.calibraint.studentmanagement.repository.StudentRepository;
import com.calibraint.studentmanagement.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void getAllStudents_success() {
        List<Student> students = TestData.buildStudentsData();
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        assertEquals(students.get(0), result.get(0));
        assertEquals(students.get(1), result.get(1));

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void getAllStudents_withNoRecords() {
        List<Student> students = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();
        assertEquals(0, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void getStudentNameAndId_success() {
        List<Student> students = TestData.buildStudentsData();
        when(studentRepository.findAll()).thenReturn(students);

        List<Object> result = studentService.getStudentNameAndId();

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_success() {
        Student student = TestData.buildStudentData();
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(student.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(student.getId(), result.getId());
        Assertions.assertEquals(student.getName(), result.getName());

        verify(studentRepository, times(1)).findById(student.getId());
    }

    @Test
    void getStudentById_throws_studentNotFoundException() {
        Long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(studentId));
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void createStudent_success() {
        Student student = TestData.buildStudentData();
        Student savedStudent = student;
        student.setId(null);

        when(studentRepository.save(student)).thenReturn(savedStudent);
        Student result = studentService.createStudent(student);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(student.getName(), result.getName());

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void updateStudent_success() {
        Long studentId = 1L;

        Student updatedStudent = new Student();
        updatedStudent.setName("Test3");
        updatedStudent.setSurname("Bean3");

        when(studentRepository.existsById(studentId)).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        Student result = studentService.updateStudent(studentId, updatedStudent);

        assertNotNull(result);
        assertEquals(studentId, result.getId());
        assertEquals("Test3", result.getName());
        assertEquals("Bean3", result.getSurname());

        verify(studentRepository, times(1)).existsById(studentId);
        verify(studentRepository, times(1)).save(updatedStudent);
    }

    @Test
    void updateStudent_throws_studentNotFound() {
        Long studentId = 1L;
        Student updatedStudent = new Student();
        updatedStudent.setName("Jane");
        updatedStudent.setSurname("Doe");
        when(studentRepository.existsById(studentId)).thenReturn(false);

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> {
            studentService.updateStudent(studentId, updatedStudent);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, times(1)).existsById(studentId);
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void deleteStudent_success() {
        Long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(true);

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).existsById(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void deleteStudent_throws_studentNotFound() {
        Long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(false);

        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class, () -> {
            studentService.deleteStudent(studentId);
        });

        assertEquals("Student not found", exception.getMessage());
        verify(studentRepository, times(1)).existsById(studentId);
        verify(studentRepository, never()).deleteById(studentId);
    }
}
