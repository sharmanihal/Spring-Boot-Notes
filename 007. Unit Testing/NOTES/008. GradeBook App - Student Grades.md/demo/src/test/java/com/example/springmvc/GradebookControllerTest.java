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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring6.expression.Mvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

    private static MockHttpServletRequest mockHttpServletRequest;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentAndGradeServiceMock;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setup() {
        mockHttpServletRequest = new MockHttpServletRequest();

        mockHttpServletRequest.setParameter("firstname", "John");
        mockHttpServletRequest.setParameter("lastname", "Doe");
        mockHttpServletRequest.setParameter("emailAddress", "johndoe@gmail.com");
    }

    @BeforeEach
    public void init() {
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
    public void cleanup() {
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

        CollegeStudent student1 = new CollegeStudent("Eric", "Roby", "ericroby@gmail.com");

        CollegeStudent student2 = new CollegeStudent("John", "Doe", "johndoe@gmail.com");

        List<CollegeStudent> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentAndGradeServiceMock.getGradebook()).thenReturn(studentList);

        assertIterableEquals(studentList, studentAndGradeServiceMock.getGradebook());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if (modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "index");
        }
    }


    @Test
    public void createStudentHttpRequestTest() throws Exception {

        CollegeStudent studentOne = new CollegeStudent("Eric", "Roby", "ericroby@gmail.com");

        List<CollegeStudent> studentList = new ArrayList<>();
        studentList.add(studentOne);

        when(studentAndGradeServiceMock.getGradebook()).thenReturn(studentList);

        assertIterableEquals(studentList, studentAndGradeServiceMock.getGradebook());

        MvcResult result = mockMvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", mockHttpServletRequest.getParameterValues("firstname"))
                        .param("lastname", mockHttpServletRequest.getParameterValues("lastname"))
                        .param("emailAddress", mockHttpServletRequest.getParameterValues("emailAddress"))
        ).andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if (modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "index");
        }

        CollegeStudent verifyStudent = studentRepository.findByEmailAddress("johndoe@gmail.com");

        assertNotNull(verifyStudent, "Student should be present in the database!");
    }

    @Test
    public void deleteStudentHttpRequestTest() throws Exception {

        assertTrue(studentRepository.findById(1).isPresent());

        MvcResult result = mockMvc.perform(
                get("/delete/student/{id}", 1))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if (modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "index");
        }

        assertFalse(studentRepository.findById(1).isPresent());
    }

    @Test
    public void deleteStudentHttpRequestErrorPageTest() throws Exception {

        assertFalse(studentRepository.findById(123).isPresent());

        MvcResult result = mockMvc.perform(
                        get("/delete/student/{id}", 123))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if (modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "error");
        }
    }

    @Test
    public void getStudentInformationHttpRequestTest() throws Exception {
        assertTrue(studentRepository.findById(1).isPresent());

        MvcResult result = mockMvc.perform(get("/studentInformation/{id}", 1))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if(modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");
        }

    }

    @Test
    public void getStudentInformationInvalidHttpRequestTest() throws Exception {
        assertFalse(studentRepository.findById(0).isPresent());

        MvcResult result = mockMvc.perform(get("/studentInformation/{id}", 0))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if(modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "error");
        }

    }

    @Test
    public void createValidGradeHttpRequestTest() throws Exception {
        assertTrue(studentRepository.findById(1).isPresent());

        CollegeStudent student = studentAndGradeService.getStudentInformation(1);

        assertEquals(1, student.getMathGrades().size());

       MvcResult result = mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("gradeType", "math")
                .param("studentId", "1"))
                .andExpect(status().isOk()).andReturn();

       ModelAndView modelAndView = result.getModelAndView();

       if(modelAndView != null) {
           ModelAndViewAssert.assertViewName(modelAndView,"studentInformation");
       }

       student = studentAndGradeService.getStudentInformation(1);
       assertEquals(2, student.getMathGrades().size());
    }

    @Test
    public void createGradeWithInvalidStudentIdHttpRequestTest() throws Exception {
        assertFalse(studentRepository.findById(123).isPresent());

        MvcResult result = mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("gradeType", "history")
                .param("studentId", "123"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if(modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "error");
        }

    }

    @Test
    public void createGradeWithInvalidSubjectHttpRequestTest() throws Exception {
        assertTrue(studentRepository.findById(1).isPresent());

        MvcResult result = mockMvc.perform(post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85.00")
                        .param("gradeType", "geography")
                        .param("studentId", "1"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if(modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "error");
        }

    }


    @Test
    public void deleteGradeHttpRequestTest() throws Exception {

        // Assert that the grade exists before deleting it
        assertTrue(mathGradeRepository.findById(1).isPresent());

        // Make a call to delete the grade (It will be a GET request)
        MvcResult result = mockMvc.perform(get("/grades/{id}/{gradeType}", 1, "math"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if (modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "studentInformation");
        }

        // Assert that the grade does not exist after deleting it
        assertFalse(mathGradeRepository.findById(1).isPresent());
    }

    @Test
    public void deleteGradeWithInvalidGradeIdHttpRequestTest() throws Exception {
        // Assert that the grade does not exist before deleting it
        assertFalse(mathGradeRepository.findById(123).isPresent());

        // Make a call to delete the grade (It will be a GET request)
        MvcResult result = mockMvc.perform(get("/grades/{id}/{gradeType}", 123, "math"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if (modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "error");
        }
    }

    @Test
    public void deleteGradeWithInvalidGradeTypeHttpRequestTest() throws Exception {
        // Assert that the grade exists before deleting it
        assertTrue(mathGradeRepository.findById(1).isPresent());

        // Make a call to delete the grade (It will be a GET request)
        MvcResult result = mockMvc.perform(get("/grades/{id}/{gradeType}", 123, "geography"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        if (modelAndView != null) {
            ModelAndViewAssert.assertViewName(modelAndView, "error");
        }
    }
}
