package com.restcrudapp.demo.controller;


import com.restcrudapp.demo.exception.StudentNotFoundException;
import com.restcrudapp.demo.pojo.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    // Load the student data using @PostContruct as it is called only once
    @PostConstruct
    public void loadData() {
        students = new ArrayList<>();

        students.add(new Student("John", "Doe"));
        students.add(new Student("James", "Cook"));
        students.add(new Student("Robb", "Stark"));
    }

    // Define endpoint for "/students" - return a list of students
    @GetMapping("/students")
    public List<Student> getStudents(@RequestHeader(value = "test", required = false) String test) {
        System.out.println("Value of request header named test is: " + test);
        return students;
    }


    // Define endpoint for retrieving a single student
    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable(value = "studentId") int id) {
        if (id >= students.size() || id < 0) {
            throw new StudentNotFoundException("Student id not found - " + id);
        }
        return students.get(id);
    }

    // Define endpoint for retrieving students with names starting with a specific letter
    // This letter value is sent as a query parameter when the request is made
    @GetMapping("/studentsbyname")
    public List<Student> getStudentByMatchingLetter(@RequestParam(value = "startingLetter", required = false) String startingLetter) {

        // If query parameter is not passed
        if (startingLetter == null) return students;

        return students.stream().filter(student -> student.getFirstName().toLowerCase().startsWith(startingLetter.toLowerCase())).toList();
    }

    // Add a new student
    @PostMapping("/addstudent")
    public List<Student> addStudent(@RequestBody Student student) {
        students.add(student);
        return students;
    }
}
