package com.example.tdd;

public class FizBuzz {

    public static String compute(int i) {

        if (i % 3 == 0 && i % 5 == 0) {
            return "FizzBuzz";
        } else if (i % 3 == 0) {
            return "Fizz";
        } else if (i % 5 == 0) {
            return "Buzz";
        }

        return Integer.toString(i);
    }
}
