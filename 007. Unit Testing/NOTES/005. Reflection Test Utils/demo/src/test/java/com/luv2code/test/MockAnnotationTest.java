package com.luv2code.test;

import com.example.component.MvcTestingExampleApplication;
import com.example.component.dao.ApplicationDao;
import com.example.component.models.CollegeStudent;
import com.example.component.models.StudentGrades;
import com.example.component.service.ApplicationService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent collegeStudent;

    @Autowired
    StudentGrades studentGrades;

    @MockBean
    private ApplicationDao applicationDao;

    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        collegeStudent.setFirstname("Eric");
        collegeStudent.setLastname("Roby");
        collegeStudent.setEmailAddress("eric.roby@gmail.com");
        collegeStudent.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(collegeStudent, "id", 1);
        ReflectionTestUtils.setField(collegeStudent, "studentGrades",
                new StudentGrades(new ArrayList<>(Arrays.asList(
                        100.0, 85.0, 76.50, 91.75
                ))));
    }

    @Test
    @DisplayName("When and Verify")
    public void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults()))
                .thenReturn(100.00);

        double result = applicationService.addGradeResultsForSingleClass(collegeStudent.getStudentGrades().getMathGradeResults());

        assertEquals(100.00, result);

        verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());

        ApplicationDao second = context.getBean("applicationDao", ApplicationDao.class);
        assertSame(applicationDao, second);
    }

    @Test
    @DisplayName("Find GPA")
    public void assertEqualsTestFindGpa() {
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults()))
                .thenReturn(88.31);

        double result = applicationService.findGradePointAverage(collegeStudent.getStudentGrades().getMathGradeResults());

        assertEquals(88.31, result);
    }

    @Test
    @DisplayName("Not Null")
    public void testAssertNotNull() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenReturn(true);

        Object result = applicationService.checkNull(collegeStudent.getStudentGrades().getMathGradeResults());

        assertNotNull(result, "Object should not be null");
    }


    @Test
    @DisplayName("Throws an Exception")
    public void testThrowException() {

        // Since it is a prototype bean, we will get a new instance each time we request it
        CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);

        when(applicationDao.checkNull(nullStudent)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));
    }

    @Test
    @DisplayName("Throws an Exception with two calls")
    public void testThrowExceptionTwoCalls() {

        // Since it is a prototype bean, we will get a new instance each time we request it
        CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw exception the second time");

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(nullStudent));

        assertEquals("Do not throw exception the second time", applicationService.checkNull(nullStudent));
    }

    @Test
    @DisplayName("Access data from a private field using ReflectionTestUtils")
    public void testGetDataFromPrivateField() {

        Object result = ReflectionTestUtils.getField(collegeStudent, "id");

        assertEquals(1, result);
    }


    @Test
    @DisplayName("Invoke a private method using ReflectionTestUtils")
    public void testInvokePrivateMethod() {

        String returnValue = ReflectionTestUtils.invokeMethod(collegeStudent, "getFirstNameAndId");

        String expected = "Eric 1";

        assertEquals(expected, returnValue, "Failed - Private method was not called!");
    }
}
