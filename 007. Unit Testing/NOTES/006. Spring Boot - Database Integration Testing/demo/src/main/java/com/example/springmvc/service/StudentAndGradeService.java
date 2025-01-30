package com.example.springmvc.service;

import com.example.springmvc.models.CollegeStudent;

public interface StudentAndGradeService {
    public void createStudent(String firstName, String lastName, String email);
    public boolean checkIfStudentExists(int id);
    public void deleteStudent(int id);
    public Iterable<CollegeStudent> getGradebook();
}
