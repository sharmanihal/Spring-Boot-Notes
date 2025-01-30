package com.mappings.cruddemo.dao;

import com.mappings.cruddemo.entity.Course;
import com.mappings.cruddemo.entity.Instructor;
import com.mappings.cruddemo.entity.InstructorDetail;
import com.mappings.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {

    private final EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Instructor findInstructorById(int id) {
        return entityManager.find(Instructor.class, id);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int id) {
        return entityManager.find(InstructorDetail.class, id);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int id) {

        // Find the instructor by id
        Instructor instructor = entityManager.find(Instructor.class, id);

        // Get the courses
        List<Course> courses = instructor.getCourses();

        // Break the associdation of all the courses for the instructor
        courses.forEach(course -> course.setInstructor(null));

        // Delete the instructor
        entityManager.remove(instructor);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int id) {
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class, id);

        // Break the bi-directional link
        instructorDetail.getInstructor().setInstructorDetail(null);

        entityManager.remove(instructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int id) {
        TypedQuery<Course> query = entityManager.createQuery("FROM Course WHERE instructor.id = :id", Course.class);

        query.setParameter("id", id);

        return query.getResultList();
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int id) {
        TypedQuery<Instructor> query = entityManager.createQuery("FROM Instructor i JOIN FETCH i.instructorDetail JOIN FETCH i.courses WHERE i.id = :data", Instructor.class);

        query.setParameter("data", id);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Override
    public Course findCourseById(int id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    @Transactional
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    @Transactional
    public void deleteCourseById(int id) {

        // Find the course
        Course course = entityManager.find(Course.class, id);

        // Delete the course
        entityManager.remove(course);
    }

    @Override
    @Transactional
    public void save(Course course) {
        entityManager.persist(course);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int id) {

        // Create a Query
        TypedQuery<Course> query = entityManager.createQuery("FROM Course c JOIN FETCH c.reviews WHERE c.id = :id", Course.class);

        query.setParameter("id", id);

        // Execute the query
        return query.getSingleResult();


    }

    @Override
    public Course findCourseAndStudentsByCourseId(int id) {
        TypedQuery<Course> query = entityManager.createQuery("FROM Course c JOIN FETCH c.students WHERE c.id = :id", Course.class);

        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    public Student findStudentAndCourseByStudentId(int id) {
        TypedQuery<Student> query = entityManager.createQuery("FROM Student s JOIN FETCH s.courses WHERE s.id = :id", Student.class);

        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {

        // Find the student
        Student student = entityManager.find(Student.class, id);

        // Break associations with the courses
        List<Course> courses = student.getCourses();
        courses.forEach(course -> course.getStudents().remove(student));

        // Delete the student
        entityManager.remove(student);
    }
}
