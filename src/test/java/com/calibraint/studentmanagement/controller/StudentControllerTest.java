package com.calibraint.studentmanagement.controller;

import com.calibraint.studentmanagement.dto.StudentDTO;
import com.calibraint.studentmanagement.exception.StudentNotFoundException;
import com.calibraint.studentmanagement.model.Student;
import com.calibraint.studentmanagement.response.SuccessResponse;
import com.calibraint.studentmanagement.service.StudentServiceImpl;
import com.calibraint.studentmanagement.util.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class StudentControllerTest {

    @Mock
    StudentServiceImpl studentService;

    @InjectMocks
    StudentController studentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void getAllStudents_success() {
        List<Student> students = TestData.buildStudentsData();
        when(studentService.getAllStudents()).thenReturn(students);

        SuccessResponse response = studentController.getAllStudents();

        assertEquals(students, response.getData());
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void getStudentNameAndId_success() {
        List<Object> studentNamesAndIds = TestData.studentNamesAndIds();

        when(studentService.getStudentNameAndId()).thenReturn(studentNamesAndIds);
        SuccessResponse response = studentController.getStudentNameAndId();

        assertEquals(studentNamesAndIds, response.getData());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(studentService, times(1)).getStudentNameAndId();
    }

    @Test
    void getStudentById_success() {
        Student student = TestData.buildStudentData();
        when(studentService.getStudentById(student.getId())).thenReturn(student);

        SuccessResponse response = studentController.getStudentById(student.getId());
        assertEquals(student, response.getData());
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        verify(studentService, times(1)).getStudentById(student.getId());
    }

    @Test
    void getStudentById_throws_studentNotFound() {
        Long studentId = 1L;
        when(studentService.getStudentById(studentId)).thenThrow(new StudentNotFoundException("Student not found"));

        try {
            studentController.getStudentById(studentId);
        } catch(StudentNotFoundException exception) {
            assertEquals("Student not found", exception.getMessage());
        }

        verify(studentService, times(1)).getStudentById(studentId);
    }

    @Test
    void createStudent_success() throws Exception {
        StudentDTO studentDTO = TestData.buildStudentDTO();
        Student student = TestData.buildStudentData();

        when(studentService.createStudent(student)).thenReturn(student);

        MvcResult result = mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\",\"surname\":\"Bean\",\"age\":20," +
                                "\"email\":\"test@gmail.com\",\"department\":\"CSE\",\"year\":2}"))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void createStudent_throws_invalidData() throws Exception {
        MvcResult result = mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"surname\":\"Bean\",\"age\":20,\"email\":\"\"," +
                                "\"department\":\"CSE\",\"year\":2}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void updateStudent_success() throws Exception {
        Long studentId = 1L;
        StudentDTO studentDTO = TestData.buildStudentDTO();
        Student updatedStudent = TestData.buildStudentData();

        when(studentService.updateStudent(studentId, updatedStudent)).thenReturn(updatedStudent);
        MvcResult result = mockMvc.perform(put("/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\",\"surname\":\"Bean\",\"age\":21,\"email\":\"test@gamil.com\"," +
                                "\"department\":\"CSE\",\"year\":3}"))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void updateStudent_throws_invalidData() throws Exception {
        MvcResult result = mockMvc.perform(put("/students/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"surname\":\"Bean\",\"age\":21," +
                                "\"email\":\"invalid_email\",\"department\":\"CSE\",\"year\":3}"))
                .andExpect(status().isBadRequest())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    void deleteStudent_success() throws Exception {
        Long studentId = 1L;
        doNothing().when(studentService).deleteStudent(studentId);

        MvcResult result = mockMvc.perform(delete("/students/{id}", studentId))
                .andExpect(status().isNoContent())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
        verify(studentService).deleteStudent(studentId);
    }
}
