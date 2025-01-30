package com.mappings.cruddemo;

import com.mappings.cruddemo.dao.AppDAO;
import com.mappings.cruddemo.entity.Instructor;
import com.mappings.cruddemo.entity.InstructorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CruddemoApplication implements CommandLineRunner {

	private final AppDAO appDAO;

	@Autowired
	public CruddemoApplication(AppDAO appDAO) {
		this.appDAO = appDAO;
	}

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		createInstructor(appDAO);

//		System.out.println("Querying the Instructor with id = 1");
//		System.out.println(appDAO.findInstructorById(1));

//		System.out.println("Deleting the Instructor with id = 1");
//		appDAO.deleteInstructorById(1);
//		System.out.println("Instructor with id = 1 deleted!");

//		System.out.println("Quering the Instructor Detail with id = 2");
//		InstructorDetail instructorDetail = appDAO.findInstructorDetailById(2);
//		System.out.println("Instructor Detail -> " + instructorDetail);
//		System.out.println("Associdated Instructor -> " + instructorDetail.getInstructor());

		System.out.println("Deleting the Instructor Detail with id = 1");
		appDAO.deleteInstructorDetailById(1);
		System.out.println("Instructor Detail with id = 1 deleted!");
	}

	private void createInstructor(AppDAO appDAO) {

		// Create the Instructor
		Instructor instructor = new Instructor("John", "Doe", "john@gmail.com");

		// Create the instructor detail
		InstructorDetail instructorDetail = new InstructorDetail("https://www.youtube.com/xyz", "Code!!");

		// Associate the objects
		instructor.setInstructorDetail(instructorDetail);

		// Save the instructor to the database
		// This will also save the instructor details due to "CascadeType.ALL".
		System.out.println("Saving Instructor: " + instructor);
		appDAO.save(instructor);
		System.out.println("Instructor Saved!");
	}
}
