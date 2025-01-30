package com.example.springmvc;

import com.example.springmvc.models.CollegeStudent;
import com.example.springmvc.repository.StudentRepository;
import com.example.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    StudentAndGradeService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setupDatabase () {
        CollegeStudent student = new CollegeStudent("Eric", "Roby","ericroby@gmail.com");
        studentRepository.save(student);
    }

    @AfterEach
    public void cleanDatabase() {
        studentRepository.deleteAll();
        jdbcTemplate.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
    }


    @Test
    public void createStudentService() {
        studentService.createStudent("John", "Doe", "johndoe@gmail.com");

        CollegeStudent student = studentRepository.findByEmailAddress("johndoe@gmail.com");

        assertEquals("johndoe@gmail.com", student.getEmailAddress(), "Find by email");
    }


    @Test
    public void isStudentNullCheck() {
        assertTrue(studentService.checkIfStudentExists(1));
        assertFalse(studentService.checkIfStudentExists(0));
    }

    @Test
    public void deleteStudentTest() {
        assertTrue(studentService.checkIfStudentExists(1), "Student with id 1 should exist in the database");

        studentService.deleteStudent(1);

        assertFalse(studentService.checkIfStudentExists(1), "Student with id 1 should no longer exist in the database");
    }

    @Test
    @Sql("/insertData.sql")
    public void getGradebookService() {
        Iterable<CollegeStudent> iterableCollegeStudent = studentService.getGradebook();

        List<CollegeStudent> studentList = new ArrayList<>();

        iterableCollegeStudent.forEach(studentList::add);

        assertEquals(5, studentList.size(), "Only one student should be present in the database table");

    }
}
