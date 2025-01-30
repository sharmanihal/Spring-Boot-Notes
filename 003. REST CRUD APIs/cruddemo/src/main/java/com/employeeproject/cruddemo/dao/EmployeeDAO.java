package com.employeeproject.cruddemo.dao;

import com.employeeproject.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    // Get a list of all the employees
    List<Employee> findAll();

    // Get a specific employee by id
    Employee findById(int id);

    // Add a new employee if it does not already exist or update if it does
    Employee save(Employee employee);

    // Delete an Employee
    void delete(Employee employee);
}
