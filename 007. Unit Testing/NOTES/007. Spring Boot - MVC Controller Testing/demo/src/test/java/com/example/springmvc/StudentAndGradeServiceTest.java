package com.example.springmvc;

import com.example.springmvc.models.CollegeStudent;
import com.example.springmvc.models.HistoryGrade;
import com.example.springmvc.models.MathGrade;
import com.example.springmvc.models.ScienceGrade;
import com.example.springmvc.repository.HistoryGradeRepository;
import com.example.springmvc.repository.MathGradeRepository;
import com.example.springmvc.repository.ScienceGradeRepository;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    StudentAndGradeService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    MathGradeRepository mathGradeRepository;

    @Autowired
    ScienceGradeRepository scienceGradeRepository;

    @Autowired
    HistoryGradeRepository historyGradeRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setupDatabase () {
        CollegeStudent student = new CollegeStudent("Eric", "Roby","ericroby@gmail.com");
        studentRepository.save(student);

        MathGrade mathGrade = new MathGrade();
        mathGrade.setStudentId(1);
        mathGrade.setGrade(100.00);
        mathGradeRepository.save(mathGrade);

        ScienceGrade scienceGrade = new ScienceGrade();
        scienceGrade.setStudentId(1);
        scienceGrade.setGrade(100.00);
        scienceGradeRepository.save(scienceGrade);

        HistoryGrade historyGrade = new HistoryGrade();
        historyGrade.setStudentId(1);
        historyGrade.setGrade(100.00);
        historyGradeRepository.save(historyGrade);
    }

    @AfterEach
    public void cleanDatabase() {
        studentRepository.deleteAll();
        mathGradeRepository.deleteAll();
        scienceGradeRepository.deleteAll();
        historyGradeRepository.deleteAll();

        jdbcTemplate.execute("ALTER TABLE student ALTER COLUMN ID RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE math_grade ALTER COLUMN ID RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE science_grade ALTER COLUMN ID RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE history_grade ALTER COLUMN ID RESTART WITH 1");
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

        Optional<MathGrade> mathGrade = mathGradeRepository.findById(1);
        Optional<ScienceGrade> scienceGrade = scienceGradeRepository.findById(1);
        Optional<HistoryGrade> historyGrade = historyGradeRepository.findById(1);

        assertTrue(mathGrade.isPresent());
        assertTrue(scienceGrade.isPresent());
        assertTrue(historyGrade.isPresent());

        studentService.deleteStudent(1);

        mathGrade = mathGradeRepository.findById(1);
        scienceGrade = scienceGradeRepository.findById(1);
        historyGrade = historyGradeRepository.findById(1);

        assertFalse(mathGrade.isPresent());
        assertFalse(scienceGrade.isPresent());
        assertFalse(historyGrade.isPresent());
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

    @Test
    public void createGradeServiceTest() {
        // Create the Grade
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(80.50, 1, "science"));
        assertTrue(studentService.createGrade(80.50, 1, "history"));

        // Get all the grades with a studentId
        Iterable<MathGrade> mathGrades = mathGradeRepository.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradeRepository.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradeRepository.findGradeByStudentId(1);

        // Verify there are grades
        assertTrue(mathGrades.iterator().hasNext(), "Student should have math grades");
        assertTrue(scienceGrades.iterator().hasNext(), "Student should have science grades");
        assertTrue(historyGrades.iterator().hasNext(), "Student should have history grades");

        // Verify how many grades are there (There should be 2)
        assertEquals(2, ((Collection<MathGrade>) mathGrades).size(), "Student should have 2 math grade entries");
        assertEquals(2, ((Collection<ScienceGrade>) scienceGrades).size(), "Student should have 2 science grade entries");
        assertEquals(2, ((Collection<HistoryGrade>) historyGrades).size(), "Student should have 2 history grade entries");

    }

    @Test
    public void createGradeServiceInvalidTest() {

        // Invalid Grade
        assertFalse(studentService.createGrade(-10, 1, "math"));
        assertFalse(studentService.createGrade(105, 1, "math"));

        // Invalid Student id
        assertFalse(studentService.createGrade(80.50, 123, "math"));

        // Invalid Grade Subject
        assertFalse(studentService.createGrade(80.50, 1, "geography"));
    }

    @Test
    public void deleteGradeTest() {
        assertEquals(1, studentService.deleteGrade(1, "math"), "Returns student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "science"), "Returns student id after delete");
        assertEquals(1, studentService.deleteGrade(1, "history"), "Returns student id after delete");
    }

    @Test
    public void deleteGradeInvalidTest() {

        // Invalid Grade Id
        assertEquals(0, studentService.deleteGrade(123, "math"), "Returns 0 as the passed student id is not present in database");

        // Invalid Grade type
        assertEquals(0, studentService.deleteGrade(1, "geography"), "Returns 0 as the passed grade type is invalid");
    }

    @Test
    public void studentInformationTest() {

        CollegeStudent student = studentService.getStudentInformation(1);

        assertNotNull(student);
        assertEquals(1, student.getId());
        assertEquals("Eric", student.getFirstname());
        assertEquals("Roby", student.getLastname());
        assertEquals("ericroby@gmail.com", student.getEmailAddress());

        assertEquals(1,student.getMathGrades().size());
        assertEquals(1,student.getScienceGrades().size());
        assertEquals(1,student.getHistoryGrades().size());

    }

    @Test
    public void studentInformationInvalidTest() {
        CollegeStudent student = studentService.getStudentInformation(123);

        assertNull(student);
    }
}
