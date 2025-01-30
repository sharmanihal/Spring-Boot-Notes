package com.example.springmvc.service;

import com.example.springmvc.models.*;
import com.example.springmvc.repository.HistoryGradeRepository;
import com.example.springmvc.repository.MathGradeRepository;
import com.example.springmvc.repository.ScienceGradeRepository;
import com.example.springmvc.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeServiceImpl implements StudentAndGradeService{

    private final StudentRepository studentRepository;
    private final MathGradeRepository mathGradeRepository;
    private final ScienceGradeRepository scienceGradeRepository;
    private final HistoryGradeRepository historyGradeRepository;

    private final EntityManager entityManager;

    @Autowired
    public StudentAndGradeServiceImpl(
            StudentRepository studentRepository,
            MathGradeRepository mathGradeRepository,
            ScienceGradeRepository scienceGradeRepository,
            HistoryGradeRepository historyGradeRepository,
            EntityManager entityManager
    ) {
        this.studentRepository = studentRepository;
        this.mathGradeRepository = mathGradeRepository;
        this.scienceGradeRepository = scienceGradeRepository;
        this.historyGradeRepository = historyGradeRepository;
        this.entityManager = entityManager;
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

    @Override
    public boolean createGrade(double grade, int id, String gradeType) {
        if (checkIfStudentExists(id) && grade >= 0 && grade <= 100) {
            switch (gradeType) {
                case "math" -> {
                    MathGrade mathGrade = new MathGrade();
                    mathGrade.setGrade(grade);
                    mathGrade.setStudentId(id);
                    mathGradeRepository.save(mathGrade);
                    return true;
                }
                case "science" -> {
                    ScienceGrade scienceGrade = new ScienceGrade();
                    scienceGrade.setGrade(grade);
                    scienceGrade.setStudentId(id);
                    scienceGradeRepository.save(scienceGrade);
                    return true;
                }
                case "history" -> {
                    HistoryGrade historyGrade = new HistoryGrade();
                    historyGrade.setGrade(grade);
                    historyGrade.setStudentId(id);
                    historyGradeRepository.save(historyGrade);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int deleteGrade(int id, String gradeType) {
        int deletedGradeId = 0;

        switch (gradeType) {
            case "math" -> {
                Optional<MathGrade> grade = mathGradeRepository.findById(id);
                if (grade.isEmpty()) return deletedGradeId;
                deletedGradeId = grade.get().getStudentId();
                mathGradeRepository.deleteById(id);
            }
            case "science" -> {
                Optional<ScienceGrade> grade = scienceGradeRepository.findById(id);
                if (grade.isEmpty()) return deletedGradeId;
                deletedGradeId = grade.get().getStudentId();
                scienceGradeRepository.deleteById(id);
            }
            case "history" -> {
                Optional<HistoryGrade> grade = historyGradeRepository.findById(id);
                if (grade.isEmpty()) return deletedGradeId;
                deletedGradeId = grade.get().getStudentId();
                historyGradeRepository.deleteById(id);
            }
        }

        return deletedGradeId;
    }

    @Override
    public CollegeStudent getStudentInformation(int id) {
        Optional<CollegeStudent> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            return this.getStudentDetailsWithGrades(id);
        }
        return null;
    }

    public CollegeStudent getStudentDetailsWithGrades(int id) {
        CollegeStudent student = entityManager.createQuery("FROM CollegeStudent s JOIN FETCH s.mathGrades WHERE s.id = :id", CollegeStudent.class)
                .setParameter("id", id).getSingleResult();

        student = entityManager.createQuery("FROM CollegeStudent s JOIN FETCH s.scienceGrades WHERE s.id = :id", CollegeStudent.class)
                .setParameter("id", id).getSingleResult();

        student = entityManager.createQuery("FROM CollegeStudent s JOIN FETCH s.historyGrades WHERE s.id = :id", CollegeStudent.class)
                .setParameter("id", id).getSingleResult();

        return student;
    }


}
