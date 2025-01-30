package com.myapp.util;

public class TennisCoach implements Coach{
    public TennisCoach() {
        System.out.println("In Constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Practice tennis for 20 minutes!";
    }

}
