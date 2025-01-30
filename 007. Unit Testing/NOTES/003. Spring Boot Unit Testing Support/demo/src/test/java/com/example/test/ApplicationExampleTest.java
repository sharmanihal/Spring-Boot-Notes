package com.example.test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.models.CollegeStudent;
import com.example.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
public class ApplicationExampleTest {

    private static int count = 0;

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.school.name}")
    private String schoolName;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    CollegeStudent collegeStudent;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    public void beforeEach() {
        count += 1;
        System.out.println("Testing: " + appName + " which is " + appDescription + " Version: " + appVersion + ". Execution of test method " + count);
        collegeStudent.setFirstname("Eric");
        collegeStudent.setLastname("Roby");
        collegeStudent.setEmailAddress("eric.roby@gmail.com");

        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
        collegeStudent.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("Equals test for addGradeResultsForSingleClass() method")
    void addGradeResultsForStudentGrades() {

        double expected = 353.25;

        double result = studentGrades.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Not Equals test for addGradeResultsForSingleClass() method")
    void addGradeResultsForStudentGradesNotEquals() {

        double expected = 0;

        double result = studentGrades.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults());

        assertNotEquals(expected, result);
    }

    @Test
    @DisplayName("True test for isGradeGreater() method")
    void isGradeGreaterStudentGradesTrueTest() {
        boolean result = studentGrades.isGradeGreater(90, 75);
        assertTrue(result, "Failure - should be true");
    }

    @Test
    @DisplayName("False test for isGradeGreater() method")
    void isGradeGreaterStudentGradesFalseTest() {
        boolean result = studentGrades.isGradeGreater(75, 90);
        assertFalse(result, "Failure - should be false");
    }

    @Test
    @DisplayName("Check Null for Student Grades")
    void checkNullForStudentGrades() {

        Object res = studentGrades.checkNull(collegeStudent.getStudentGrades().getMathGradeResults());

        assertNotNull(res);
    }

    @Test
    @DisplayName("Create student without grade init")
    void createStudentWithoutGradesInit() {

        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("John");
        studentTwo.setLastname("Doe");
        studentTwo.setEmailAddress("john.doe@gmail.com");

        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());

        assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));

        assertNotSame(collegeStudent, studentTwo);
    }

    @Test
    @DisplayName("Find Grade Point Average")
    void findGradePointAverage() {

        assertAll("Testing all assertEquals",


                () -> assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults())),
                () -> assertEquals(88.31, studentGrades.findGradePointAverage(collegeStudent.getStudentGrades().getMathGradeResults()))
        );
    }
}
