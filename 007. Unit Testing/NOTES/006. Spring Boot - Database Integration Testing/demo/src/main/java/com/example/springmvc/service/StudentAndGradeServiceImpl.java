package com.example.springmvc.service;

import com.example.springmvc.models.CollegeStudent;
import com.example.springmvc.models.Student;
import com.example.springmvc.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeServiceImpl implements StudentAndGradeService{

    StudentRepository studentRepository;

    @Autowired
    public StudentAndGradeServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void createStudent(String firstName, String lastName, String email) {

        CollegeStudent student = new CollegeStudent(firstName, lastName, email);

        studentRepository.save(student);
    }

    @Override
    public boolean checkIfStudentExists(int id) {
        Optional<CollegeStudent> student = studentRepository.findById(id);
        return student.isPresent();
    }

    @Override
    public void deleteStudent(int id) {
        if (checkIfStudentExists(id)) {
            this.studentRepository.deleteById(id);
        }
    }

    @Override
    public Iterable<CollegeStudent> getGradebook() {
        return studentRepository.findAll();
    }
}
