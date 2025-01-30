package com.restcrudapp.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoRestController {

    // Add code for '/hello' endpoint
    @GetMapping("/hello")
    public String sayHello() {
        throw new RuntimeException("Some random exception thrown from another controller");
    }
}
