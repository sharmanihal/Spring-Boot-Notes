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

}
