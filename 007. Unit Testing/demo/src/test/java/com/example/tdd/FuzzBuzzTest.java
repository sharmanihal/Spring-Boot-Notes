package com.example.tdd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FuzzBuzzTest {

    // If the number is divisible by 3, print Fizz
    @Test
    @DisplayName("Divisible by 3")
    @Order(1)
    void testForDivisibleByThree() {
        String expected = "Fizz";
        assertEquals(expected, FizBuzz.compute(3), "Should return Fizz");
    }

    // If the number is divisible by 5, print Fizz

    @Test
    @DisplayName("Divisible by 5")
    @Order(2)
    void testForDivisibleByFive() {
        String expected = "Buzz";
        assertEquals(expected, FizBuzz.compute(5), "Should return Fizz");
    }

    // If the number is divisible by 3 and 5, print FizzBuzz
    @Test
    @DisplayName("Divisible by 3 and 5")
    @Order(3)
    void testForDivisibleByThreeAndFive() {
        String expected = "FizzBuzz";
        assertEquals(expected, FizBuzz.compute(15), "Should return FuzzBuzz");
    }

    // If the number is not divisible by 3 or 5, print the number
    @Test
    @DisplayName("Not Divisible by 3 or 5")
    @Order(4)
    void testForNotDivisibleByThreeOrFive() {
        String expected = "11";
        assertEquals(expected, FizBuzz.compute(11), "Should return 11");
    }

    // Test the above conditions with a bunch of values
    @Test
    @DisplayName("Loop over array")
    @Order(5)
    void testLoopOverArray() {
        String[][] data = {
                {"1", "1"},
                {"2", "2"},
                {"3", "Fizz"},
                {"4", "4"},
                {"5", "Buzz"},
                {"6", "Fizz"},
                {"7", "7"},
                {"8", "8"},
                {"9", "Fizz"},
                {"10", "Buzz"}
        };

        Arrays.stream(data).forEach(val ->
            assertEquals(val[1], FizBuzz.compute(Integer.parseInt(val[0])), "Should return 11")
        );

    }

    // Using Parameterized Tests
    @ParameterizedTest(name = "For the input {0}, the expected output is {1}")
    @DisplayName("Using Parameterized Tests")
//    @CsvSource({"1,1", "2,2", "3,Fizz", "4,4", "5,Buzz", "6,Fizz", "7,7", "8,8", "9,Fizz", "10,Buzz"})
    @CsvFileSource(resources = "/test-data.csv")
    @Order(6)
    void testParameterized(int value, String expected) {
        assertEquals(expected, FizBuzz.compute(value), "Should return "+ expected);
    }
}
