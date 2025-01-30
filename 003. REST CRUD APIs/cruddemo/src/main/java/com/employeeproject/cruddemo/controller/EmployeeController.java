package com.employeeproject.cruddemo.controller;

import com.employeeproject.cruddemo.entity.Employee;
import com.employeeproject.cruddemo.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Find employee by first name
    @GetMapping("employees/{firstName}")
    public Employee findByFirstName(@PathVariable(value = "firstName") String firstName){
        return employeeService.findByFirstName(firstName);
    }
}
