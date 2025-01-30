package com.example.junitdemo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class ConditionalTests {

    @Test
    @Disabled("Don't run until JIRA #123 is fixed!")
    void disabledTest() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }

    @Test
    @EnabledOnOs({OS.MAC, OS.WINDOWS})
    void testForMacAndWindowsOnly() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testForLinuxOnly() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testForJava17Only() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }

    @Test
    @EnabledOnJre(JRE.JAVA_13)
    void testForJavaRange13Only() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_13, max=JRE.JAVA_22)
    void testForJavaRange13to22Only() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_17)
    void testForMinVersionJava17Only() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }

    @Test
    @EnabledIfSystemProperty(named = "HELLO", matches = "WORLD")
    void testForSystemProperty() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "HELLO", matches = "WORLD")
    void testForEnvironmentVariable() {

        // Execute Method and perform asserts
        assertEquals(1, 1);
    }
}
