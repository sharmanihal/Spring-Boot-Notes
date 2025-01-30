package com.employeeproject.cruddemo.service;

import com.employeeproject.cruddemo.entity.Employee;

public interface EmployeeService {
    Employee findByFirstName(String firstName);
}
