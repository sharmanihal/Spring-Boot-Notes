package com.example.aopdemo.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TrafficFortuneServiceImpl implements TrafficFortuneService{

    @Override
    public String getFortune(boolean tripWire) throws Exception {

        // Throw an exception if flag is true
        if (tripWire) throw new Exception("Major accident! Highway is closed!");

        // Simulate a delay
        TimeUnit.SECONDS.sleep(5);

        // Return a fortune
        return "Expect heavy traffic this morning!";
    }
}
