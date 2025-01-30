package com.example.springmvc.repository;

import com.example.springmvc.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryGradeRepository extends CrudRepository<HistoryGrade, Integer> {

    public Iterable<HistoryGrade> findGradeByStudentId (int id);

    public void deleteByStudentId(int id);
}
