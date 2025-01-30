package com.example.junitdemo;


import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;

public class CustomClassOrder implements ClassOrderer {
    @Override
    public void orderClasses(ClassOrdererContext context) {
        context.getClassDescriptors().sort(
                (ClassDescriptor c1, ClassDescriptor c2) -> c1.getDisplayName().length() - c2.getDisplayName().length()
        );
    }
}
