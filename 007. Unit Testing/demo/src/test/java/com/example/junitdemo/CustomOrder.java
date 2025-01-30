package com.example.junitdemo;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;

public class CustomOrder implements MethodOrderer {

    @Override
    public void orderMethods(MethodOrdererContext context) {
        context.getMethodDescriptors().sort(
                (MethodDescriptor m1, MethodDescriptor m2) -> m1.getDisplayName().length() - m2.getDisplayName().length()

        );
    }
}
