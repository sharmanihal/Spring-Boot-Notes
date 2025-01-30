package com.employeeproject.cruddemo.repository;

import com.employeeproject.cruddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
