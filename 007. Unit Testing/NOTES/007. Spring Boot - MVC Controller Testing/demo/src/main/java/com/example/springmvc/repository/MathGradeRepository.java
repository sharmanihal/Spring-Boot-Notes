package com.example.springmvc.repository;

import com.example.springmvc.models.MathGrade;
import org.springframework.data.repository.CrudRepository;

public interface MathGradeRepository extends CrudRepository<MathGrade, Integer> {
    public Iterable<MathGrade> findGradeByStudentId(int id);
}
