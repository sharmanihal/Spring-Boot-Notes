package com.example.springmvc.controller;

import com.example.springmvc.exceptionhandling.StudentOrGradeErrorResponse;
import com.example.springmvc.exceptionhandling.StudentOrGradeNotFoundException;
import com.example.springmvc.models.CollegeStudent;
import com.example.springmvc.models.Gradebook;
import com.example.springmvc.models.GradebookCollegeStudent;
import com.example.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class GradebookController {

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private Gradebook gradebook;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<CollegeStudent> getStudents() {
        return (List<CollegeStudent>) studentService.getGradebook();
    }


    @GetMapping("/studentInformation/{id}")
    public CollegeStudent studentInformation(@PathVariable int id) {

        if (!studentService.checkIfStudentExists(id)) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        return studentService.getStudentInformation(id);
    }


    @PostMapping(value = "/")
    public List<CollegeStudent> createStudent(@RequestBody CollegeStudent student) {

        studentService.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());
        return (List<CollegeStudent>) studentService.getGradebook();
    }


    @DeleteMapping("/student/{id}")
    public List<CollegeStudent> deleteStudent(@PathVariable int id) {

        if (!studentService.checkIfStudentExists(id)) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        studentService.deleteStudent(id);
        return (List<CollegeStudent>) studentService.getGradebook();
    }


    @PostMapping(value = "/grades")
    public CollegeStudent createGrade(@RequestParam("grade") double grade,
                                               @RequestParam("gradeType") String gradeType,
                                               @RequestParam("studentId") int studentId) {

        if (!studentService.checkIfStudentExists(studentId)) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        boolean success = studentService.createGrade(grade, studentId, gradeType);

        if (!success) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        CollegeStudent studentEntity = studentService.getStudentInformation(studentId);

        if (studentEntity == null) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        return studentEntity;
    }

    @DeleteMapping("/grades/{id}/{gradeType}")
    public CollegeStudent deleteGrade(@PathVariable int id, @PathVariable String gradeType) {

        int studentId = studentService.deleteGrade(id, gradeType);

        if (studentId == 0) {
            throw new StudentOrGradeNotFoundException("Student or Grade was not found");
        }

        return studentService.getStudentInformation(studentId);
    }

    @ExceptionHandler
    public ResponseEntity<StudentOrGradeErrorResponse> handleException(StudentOrGradeNotFoundException exc) {

        StudentOrGradeErrorResponse error = new StudentOrGradeErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentOrGradeErrorResponse> handleException(Exception exc) {

        StudentOrGradeErrorResponse error = new StudentOrGradeErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
