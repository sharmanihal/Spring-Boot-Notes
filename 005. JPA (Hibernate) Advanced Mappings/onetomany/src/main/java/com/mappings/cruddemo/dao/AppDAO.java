package com.mappings.cruddemo.dao;

import com.mappings.cruddemo.entity.Course;
import com.mappings.cruddemo.entity.Instructor;
import com.mappings.cruddemo.entity.InstructorDetail;

import java.util.List;

public interface AppDAO {
    void save (Instructor instructor);
    Instructor findInstructorById(int id);
    InstructorDetail findInstructorDetailById(int id);
    void deleteInstructorById(int id);
    void deleteInstructorDetailById(int id);
    List<Course> findCoursesByInstructorId(int id);
    Instructor findInstructorByIdJoinFetch(int id);
    void update(Instructor instructor);
    Course findCourseById(int id);
    void update(Course course);
    void deleteCourseById(int id);
    void save(Course course);
    Course findCourseAndReviewsByCourseId(int id);
}
