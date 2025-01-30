package com.springboot.demo.myapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {

    @Value("${first.name}")
    private String firstName;

    @Value("${last.name}")
    private String lastName;

    // expose "/" that returns "Hello World"
    @GetMapping("/")
    public String sayHello() {
        return "Hello " +  firstName + " " + lastName + "!";
    }

    @GetMapping("/test")
    public String sayTest() {
        return "Test!";
    }
}
