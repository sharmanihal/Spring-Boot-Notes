package com.example.springmvc.repository;

import com.example.springmvc.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceGradeRepository extends CrudRepository<ScienceGrade, Integer> {

    public Iterable<ScienceGrade> findGradeByStudentId (int id);

    public void deleteByStudentId(int id);
}
