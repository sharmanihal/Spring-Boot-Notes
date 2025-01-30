package com.myapp.cruddemo;

import com.myapp.cruddemo.dao.StudentDAO;
import com.myapp.cruddemo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication implements CommandLineRunner {

	private StudentDAO studentDAO;

	@Autowired
	public CruddemoApplication(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		insertMultipleStudents();
//		readStudent(5);
//		readStudent(6);

//		getAllStudents();
//
//		System.out.println("Students with last name as 'stark': ");
//		List<Student> students = studentDAO.findByLastName("stark");
//		students.forEach(System.out::println);

//		Student student = studentDAO.findById(5);
//
//		System.out.println("Before update, the student is: " + student);
//
//		student.setLastName("Test");
//		studentDAO.update(student);
//
//		System.out.println("After update, the student is: " + student);

//		Student student = studentDAO.findById(5);
//		studentDAO.delete(student);

//		studentDAO.deleteAll();

		insertMultipleStudents();
	}

	public void insertMultipleStudents() {

		Student tempStudent1 = new Student("Paul", "Doe", "paul@google.com");
		Student tempStudent2 = new Student("John", "Snow", "john@google.com");
		Student tempStudent3 = new Student("Rob", "Stark", "stark@google.com");
		Student tempStudent4 = new Student("James", "Rodriguez", "james@google.com");

		studentDAO.save(tempStudent1);
		studentDAO.save(tempStudent2);
		studentDAO.save(tempStudent3);
		studentDAO.save(tempStudent4);
	}

	public void readStudent(Integer id) {
//		// Create a student object
//		System.out.println("Creating a new student object....");
//		Student tempStudent = new Student("Paul", "Doe", "paul@google.com");
//
//		// Save the student object in the database
//		System.out.println("Saving the student....");
//		studentDAO.save(tempStudent);
//
//		// Display the id of the saved student
//		System.out.println("Saved student. Generated id: "+ tempStudent.getId());

		// Read the user's details from the database
		System.out.println("Retrieving student with id: "+ id);
		System.out.println("Student details: "+ studentDAO.findById(id));
	}

	public void getAllStudents() {
		List<Student> students = studentDAO.findAll();
		System.out.println("These are all the students");
		students.forEach(student -> System.out.println("Student details: "+ student));
	}
}
