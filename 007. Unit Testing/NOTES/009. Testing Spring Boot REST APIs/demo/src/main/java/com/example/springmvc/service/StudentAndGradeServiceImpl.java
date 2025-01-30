package com.example.springmvc.service;

import com.example.springmvc.models.*;
import com.example.springmvc.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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
        int studentIdOfDeletedGrade = 0;

        switch (gradeType) {
            case "math" -> {
                Optional<MathGrade> grade = mathGradeRepository.findById(id);
                if (grade.isEmpty()) return studentIdOfDeletedGrade;
                studentIdOfDeletedGrade = grade.get().getStudentId();
                mathGradeRepository.deleteById(id);
            }
            case "science" -> {
                Optional<ScienceGrade> grade = scienceGradeRepository.findById(id);
                if (grade.isEmpty()) return studentIdOfDeletedGrade;
                studentIdOfDeletedGrade = grade.get().getStudentId();
                scienceGradeRepository.deleteById(id);
            }
            case "history" -> {
                Optional<HistoryGrade> grade = historyGradeRepository.findById(id);
                if (grade.isEmpty()) return studentIdOfDeletedGrade;
                studentIdOfDeletedGrade = grade.get().getStudentId();
                historyGradeRepository.deleteById(id);
            }
        }

        return studentIdOfDeletedGrade;
    }

    @Override
    public CollegeStudent getStudentInformation(int id) {
        Optional<CollegeStudent> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            return this.getStudentDetailsWithGrades(studentOptional.get(), id);
        }
        return null;
    }

    private CollegeStudent getStudentDetailsWithGrades(CollegeStudent student, int id) {

        try {
            student = entityManager.createQuery("FROM CollegeStudent s JOIN FETCH s.mathGrades WHERE s.id = :id", CollegeStudent.class)
                    .setParameter("id", id).getSingleResult();
        } catch(Exception ignored) {}


        try {
        student = entityManager.createQuery("FROM CollegeStudent s JOIN FETCH s.scienceGrades WHERE s.id = :id", CollegeStudent.class)
                .setParameter("id", id).getSingleResult();
        } catch(Exception ignored) {}

        try {
        student = entityManager.createQuery("FROM CollegeStudent s JOIN FETCH s.historyGrades WHERE s.id = :id", CollegeStudent.class)
                .setParameter("id", id).getSingleResult();
        } catch(Exception ignored) {}

        return student;
    }

    public void configureStudentInformationModel(int id, Model m) {

        CollegeStudent student = getStudentInformation(id);

        m.addAttribute("student", student);

        List<Grade> mathGrades = new ArrayList<>(student.getMathGrades());
        List<Grade> scienceGrades = new ArrayList<>(student.getScienceGrades());
        List<Grade> historyGrades = new ArrayList<>(student.getHistoryGrades());
        StudentGrades studentGrades = new StudentGrades();
        if(!mathGrades.isEmpty()){
            m.addAttribute("mathAverage", studentGrades.findGradePointAverage(mathGrades));
        } else {
            m.addAttribute("mathAverage", "N/A");
        }

        if(!scienceGrades.isEmpty()){
            m.addAttribute("scienceAverage", studentGrades.findGradePointAverage(scienceGrades));
        } else {
            m.addAttribute("scienceAverage", "N/A");
        }

        if(!historyGrades.isEmpty()){
            m.addAttribute("historyAverage", studentGrades.findGradePointAverage(historyGrades));
        } else {
            m.addAttribute("historyAverage", "N/A");
        }
    }


}
