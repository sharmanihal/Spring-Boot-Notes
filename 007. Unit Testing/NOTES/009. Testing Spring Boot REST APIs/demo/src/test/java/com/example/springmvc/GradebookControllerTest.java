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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

    private static MockHttpServletRequest mockHttpServletRequest;

    @Mock
    StudentAndGradeService studentAndGradeServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MathGradeRepository mathGradeRepository;

    @Autowired
    private ScienceGradeRepository scienceGradeRepository;

    @Autowired
    private HistoryGradeRepository historyGradeRepository;

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @BeforeAll
    public static void setup() {
        mockHttpServletRequest = new MockHttpServletRequest();

        mockHttpServletRequest.setParameter("firstname", "John");
        mockHttpServletRequest.setParameter("lastname", "Doe");
        mockHttpServletRequest.setParameter("emailAddress", "johndoe@gmail.com");
    }

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

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
    public void getStudentsHttpRequestTest() throws Exception {

        CollegeStudent student = new CollegeStudent();
        student.setFirstname("John");
        student.setLastname("Doe");
        student.setEmailAddress("johndoe@gmail.com");

        studentRepository.save(student);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void createStudentHttpRequestTest() throws Exception {
        CollegeStudent student = new CollegeStudent();
        student.setFirstname("John");
        student.setLastname("Doe");
        student.setEmailAddress("johndoe@gmail.com");

        mockMvc.perform(
                post("/")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(student))
        ).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));

        CollegeStudent newStudent = studentRepository.findByEmailAddress("johndoe@gmail.com");
        assertNotNull(newStudent,"Student should be present in the database!");
    }

    @Test
    public void deleteStudentHttpRequestTest() throws Exception {

        assertTrue(studentRepository.findById(1).isPresent(), "Student should be present in the database before deletion");

        mockMvc.perform(delete("/student/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));

        assertFalse(studentRepository.findById(1).isPresent(), "Student should not be present in the database after deletion");

    }

    @Test
    public void deleteStudentWithInvalidIdHttpRequestTest() throws Exception {

        assertFalse(studentRepository.findById(123).isPresent(), "Student should not be present in the database before deletion");

        mockMvc.perform(delete("/student/{id}", 123))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));

    }

    @Test
    public void getStudentInformationHttpRequestTest() throws Exception {

        Optional<CollegeStudent> student = studentRepository.findById(1);

        assertTrue(student.isPresent());

        mockMvc.perform(get("/studentInformation/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Eric")))
                .andExpect(jsonPath("$.lastname", is("Roby")))
                .andExpect(jsonPath("$.emailAddress", is("ericroby@gmail.com")))
                .andExpect(jsonPath("$.mathGrades", hasSize(1)))
                .andExpect(jsonPath("$.scienceGrades", hasSize(1)))
                .andExpect(jsonPath("$.historyGrades", hasSize(1)));
    }

    @Test
    public void getStudentInformationWithInvalidIdHttpRequestTest() throws Exception {

        Optional<CollegeStudent> student = studentRepository.findById(123);

        assertFalse(student.isPresent());

        mockMvc.perform(get("/studentInformation/{id}", 123))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    @Test
    public void createGradeHttpRequestTest() throws Exception {
        mockMvc.perform(
                post("/grades")
                        .contentType(APPLICATION_JSON_UTF8)
                        .param("grade", "85.00")
                        .param("gradeType", "math")
                        .param("studentId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Eric")))
                .andExpect(jsonPath("$.lastname", is("Roby")))
                .andExpect(jsonPath("$.emailAddress", is("ericroby@gmail.com")))
                .andExpect(jsonPath("$.mathGrades", hasSize(2)));
    }

    @Test
    public void createGradeWithInvalidStudentIdHttpRequestTest() throws Exception {

        assertFalse(studentRepository.findById(123).isPresent(), "Student should not be present in the database before creating a grade");

        mockMvc.perform(
                        post("/grades")
                                .contentType(APPLICATION_JSON_UTF8)
                                .param("grade", "85.00")
                                .param("gradeType", "math")
                                .param("studentId", "123"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    @Test
    public void createGradeWithInvalidGradeTypeHttpRequestTest() throws Exception {

        assertTrue(studentRepository.findById(1).isPresent(), "Student should be present in the database before creating a grade");

        mockMvc.perform(
                        post("/grades")
                                .contentType(APPLICATION_JSON_UTF8)
                                .param("grade", "85.00")
                                .param("gradeType", "geography")
                                .param("studentId", "1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    @Test
    public void deleteMathGradeHttpRequestTest() throws Exception {

        assertTrue(mathGradeRepository.findById(1).isPresent(), "A Math Grade with Id 1 should be present in the database before deletion");

        mockMvc.perform(
                        delete("/grades/{id}/{gradeType}", 1, "math")
                                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Eric")))
                .andExpect(jsonPath("$.lastname", is("Roby")))
                .andExpect(jsonPath("$.emailAddress", is("ericroby@gmail.com")))
                .andExpect(jsonPath("$.mathGrades", hasSize(0)))
                .andExpect(jsonPath("$.scienceGrades", hasSize(1)))
                .andExpect(jsonPath("$.historyGrades", hasSize(1)));

        assertFalse(mathGradeRepository.findById(1).isPresent(), "A Math Grade with Id 1 should not be present in the database after deletion");
    }

    @Test
    public void deleteScienceGradeHttpRequestTest() throws Exception {

        assertTrue(scienceGradeRepository.findById(1).isPresent(), "A Science Grade with Id 1 should be present in the database before deletion");

        mockMvc.perform(
                        delete("/grades/{id}/{gradeType}", 1, "science")
                                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Eric")))
                .andExpect(jsonPath("$.lastname", is("Roby")))
                .andExpect(jsonPath("$.emailAddress", is("ericroby@gmail.com")))
                .andExpect(jsonPath("$.mathGrades", hasSize(1)))
                .andExpect(jsonPath("$.scienceGrades", hasSize(0)))
                .andExpect(jsonPath("$.historyGrades", hasSize(1)));

        assertFalse(scienceGradeRepository.findById(1).isPresent(), "A Science Grade with Id 1 should not be present in the database after deletion");
    }

    @Test
    public void deleteHistoryGradeHttpRequestTest() throws Exception {

        assertTrue(historyGradeRepository.findById(1).isPresent(), "A History Grade with Id 1 should be present in the database before deletion");

        mockMvc.perform(
                        delete("/grades/{id}/{gradeType}", 1, "history")
                                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Eric")))
                .andExpect(jsonPath("$.lastname", is("Roby")))
                .andExpect(jsonPath("$.emailAddress", is("ericroby@gmail.com")))
                .andExpect(jsonPath("$.mathGrades", hasSize(1)))
                .andExpect(jsonPath("$.scienceGrades", hasSize(1)))
                .andExpect(jsonPath("$.historyGrades", hasSize(0)));

        assertFalse(historyGradeRepository.findById(1).isPresent(), "A History Grade with Id 1 should not be present in the database after deletion");
    }

    @Test
    public void deleteMathGradeWithInvalidGradeIdHttpRequestTest() throws Exception {

        assertFalse(mathGradeRepository.findById(123).isPresent(), "A Math Grade with Id 123 should not be present in the database before deletion");

        mockMvc.perform(
                        delete("/grades/{id}/{gradeType}", 123, "math")
                                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    @Test
    public void deleteMathGradeWithInvalidGradeTypeHttpRequestTest() throws Exception {

        assertTrue(mathGradeRepository.findById(1).isPresent(), "A Math Grade with Id 1 should be present in the database before deletion");

        mockMvc.perform(
                        delete("/grades/{id}/{gradeType}", 1, "geography")
                                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }
}
