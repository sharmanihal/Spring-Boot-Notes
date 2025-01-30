package com.example.aopdemo.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutDeclarations {

    @Pointcut("execution(* com.example.aopdemo.dao.*.*(..))")
    public void matchAnyMethodInDaoPackage() {}

    @Pointcut("execution(* com.example.aopdemo.dao.*.get*(..))")
    public void matchGetterMethodsInDaoPackage() {}

    @Pointcut("execution(* com.example.aopdemo.dao.*.set*(..))")
    public void matchSetterMethodsInDaoPackage() {}

    @Pointcut("matchGetterMethodsInDaoPackage() || matchSetterMethodsInDaoPackage()")
    public void matchGetterAndSetterMethodsInDaoPackage() {}
}
