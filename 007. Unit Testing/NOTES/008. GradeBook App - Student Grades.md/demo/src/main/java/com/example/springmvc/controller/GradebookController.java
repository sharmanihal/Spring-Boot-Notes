package com.example.springmvc.controller;

import com.example.springmvc.models.CollegeStudent;
import com.example.springmvc.models.Grade;
import com.example.springmvc.models.Gradebook;
import com.example.springmvc.models.StudentGrades;
import com.example.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GradebookController {

	private Gradebook gradebook;

	private StudentAndGradeService studentAndGradeService;

	@Autowired
	public GradebookController(Gradebook gradebook, StudentAndGradeService studentAndGradeService) {
		this.gradebook = gradebook;
		this.studentAndGradeService = studentAndGradeService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getStudents(Model m) {
		Iterable<CollegeStudent> collegeStudentIterable = this.studentAndGradeService.getGradebook();
		m.addAttribute("students", collegeStudentIterable);
		return "index";
	}

	@PostMapping("/")
	public String createStudent(@ModelAttribute("student") CollegeStudent student, Model m) {
		studentAndGradeService.createStudent(student.getFirstname(), student.getLastname(), student.getEmailAddress());

		Iterable<CollegeStudent> collegeStudentIterable = this.studentAndGradeService.getGradebook();
		m.addAttribute("students", collegeStudentIterable);

		return "index";
	}

	@GetMapping("/delete/student/{id}")
	public String deleteStudent(@PathVariable int id, Model m) {

		if(!studentAndGradeService.checkIfStudentExists(id)) return "error";

		studentAndGradeService.deleteStudent(id);

		Iterable<CollegeStudent> collegeStudentIterable = this.studentAndGradeService.getGradebook();
		m.addAttribute("students", collegeStudentIterable);

		return "index";
	}


	@GetMapping("/studentInformation/{id}")
	public String studentInformation(@PathVariable int id, Model m) {
		if(!studentAndGradeService.checkIfStudentExists(id)) return "error";

		// Add data for the model
		studentAndGradeService.configureStudentInformationModel(id, m);
		return "studentInformation";
	}

	@PostMapping("/grades")
	public String createGrades(@RequestParam("grade") double grade,
							   @RequestParam("gradeType") String gradeType,
							   @RequestParam("studentId") int id,
							   Model m) {
			if(!studentAndGradeService.checkIfStudentExists(id)) return "error";

			boolean success = studentAndGradeService.createGrade(grade, id, gradeType);

			if(!success) return "error";

			studentAndGradeService.configureStudentInformationModel(id, m);

		return "studentInformation";
	}

	@GetMapping("/grades/{id}/{gradeType}")
	public String deleteGrade(@PathVariable("id") int id,
							   @PathVariable("gradeType") String gradeType,
							   Model m) {

		int studentId = studentAndGradeService.deleteGrade(id, gradeType);

		if(studentId == 0) return "error";

		studentAndGradeService.configureStudentInformationModel(studentId, m);

		return "studentInformation";
	}


}
