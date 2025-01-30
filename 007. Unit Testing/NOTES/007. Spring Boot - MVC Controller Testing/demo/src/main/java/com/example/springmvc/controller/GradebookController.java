package com.example.springmvc.controller;

import com.example.springmvc.models.CollegeStudent;
import com.example.springmvc.models.Gradebook;
import com.example.springmvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
		return "studentInformation";
	}



}
