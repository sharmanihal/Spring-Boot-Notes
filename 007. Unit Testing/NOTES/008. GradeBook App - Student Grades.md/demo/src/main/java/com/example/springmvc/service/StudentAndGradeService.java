package com.example.springmvc.service;

import com.example.springmvc.models.CollegeStudent;
import org.springframework.ui.Model;

public interface StudentAndGradeService {
    public void createStudent(String firstName, String lastName, String email);
    public boolean checkIfStudentExists(int id);
    public void deleteStudent(int id);
    public Iterable<CollegeStudent> getGradebook();
    public boolean createGrade(double grade, int id, String gradeType);
    public int deleteGrade(int id, String gradeType);
    public CollegeStudent getStudentInformation(int id);
    public void configureStudentInformationModel(int id, Model m);
}
