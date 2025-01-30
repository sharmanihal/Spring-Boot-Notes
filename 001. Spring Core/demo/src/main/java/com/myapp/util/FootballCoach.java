package com.myapp.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class FootballCoach implements Coach{

    public FootballCoach() {
        System.out.println("In Constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Practice dribbling for 20 minutes!";
    }
}
