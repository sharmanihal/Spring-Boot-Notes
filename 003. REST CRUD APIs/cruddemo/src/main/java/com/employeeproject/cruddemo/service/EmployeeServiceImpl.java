package com.employeeproject.cruddemo.service;

import com.employeeproject.cruddemo.entity.Employee;
import com.employeeproject.cruddemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName);
    }
}
