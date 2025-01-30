package com.example.springmvc.repository;

import com.example.springmvc.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;

public interface HistoryGradeRepository extends CrudRepository<HistoryGrade, Integer> {
    public Iterable<HistoryGrade> findGradeByStudentId(int id);
}
