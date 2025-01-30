package com.example.springmvc.repository;

import com.example.springmvc.models.MathGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathGradeRepository extends CrudRepository<MathGrade, Integer> {

    public Iterable<MathGrade> findGradeByStudentId (int id);

    public void deleteByStudentId(int id);
}
