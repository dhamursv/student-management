package com.calibraint.studentmanagement.util;

import com.calibraint.studentmanagement.common.Department;
import com.calibraint.studentmanagement.dto.StudentDTO;
import com.calibraint.studentmanagement.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TestData {

    private TestData() {
    }

    public static List<Student> buildStudentsData() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Test");
        student1.setSurname("Bean");
        student1.setEmail("test@gmail.com");
        student1.setAge(20);
        student1.setDepartment(Department.CSE);
        student1.setYear(2);

        Student student2 = new Student();
        student2.setId(1L);
        student2.setName("Test1");
        student2.setSurname("Bean1");
        student2.setEmail("test1@gmail.com");
        student2.setAge(21);
        student2.setDepartment(Department.CIVIL);
        student2.setYear(3);

        students.add(student1);
        students.add(student2);
        return students;
    }

    public static Student buildStudentData() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Test");
        student.setSurname("Bean");
        student.setEmail("test@gmail.com");
        student.setAge(20);
        student.setDepartment(Department.CSE);
        student.setYear(2);
        return student;
    }

    public static List<Object> studentNamesAndIds() {
        List<Object> studentNamesAndIds = List.of(
                Map.of("id", "1", "name", "Test"),
                Map.of("id", "2", "name", "Test1")
        );

        return studentNamesAndIds;
    }

    public static StudentDTO buildStudentDTO() {
        StudentDTO studentDTO = new StudentDTO("Test", "Bean", 20,
                "test@gmail.com", Department.CSE, 2);

        return studentDTO;
    }
}