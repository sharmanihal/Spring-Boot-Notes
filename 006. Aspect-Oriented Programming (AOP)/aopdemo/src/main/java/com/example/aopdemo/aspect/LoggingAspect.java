package com.example.aopdemo.aspect;


import com.example.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2)
public class LoggingAspect extends PointcutDeclarations {

//    @Before("execution(public void addAccount())") <- THIS MATCHES ON ANY public void addAccount() method call
//    @Before("execution(public void com.example.aopdemo.dao.AccountDAO.addAccount())") <- THIS MATCHES ON the public void addAccount() method of the AccountDAO class only
//    @Before("execution(* add*())") <- THIS MATCHES ANY METHOD THAT STARTS WITH "add" AND HAS ANY RETURN TYPE
//    @Before("execution(* add*(com.example.aopdemo.Account))") <- THIS MATCHES ANY METHOD THAT STARTS WITH "add" AND TAKES A PARAMETER OF TYPE "Account"
//    @Before("execution(* add*(com.example.aopdemo.Account, boolean))"  <- THIS MATCHES ANY METHOD THAT STARTS WITH "add" AND TAKES TWO PARAMETERS OF TYPE "Account" & "boolean"
//    @Before("execution(* add*(..))")   <- THIS MATCHES ANY METHOD THAT STARTS WITH "add" AND TAKES 0 or MORE ARGUMENTS
//    @Before("execution(* com.example.aopdemo.dao.*.*(..))") <- THIS MATCHES ANY METHOD INSIDE "dao" PACKAGE THAT TAKES 0 OR MORE ARGUMENTS
    @Before("matchAnyMethodInDaoPackage() && !matchGetterAndSetterMethodsInDaoPackage()")
    public void beforeAddAccountAdvice(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Method Signature is :" + methodSignature);

        Object[] args = joinPoint.getArgs();
        System.out.println("Method arguments are: ");
        for(Object arg: args) {
            System.out.println(arg);
        }
        System.out.println("\n=====>>> Executing @Before advice on addAccount()");
    }


    @AfterReturning(
            pointcut = "execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result"
    )
    public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, List<Account> result) {

        // Print out which method we are advising on
        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);

        // Print out the results of the method call
        System.out.println("\n=====>>> Returned value is: " + result);

        // Updating the name of the first Account
        if (!result.isEmpty()) {
            Account account = result.get(0);
            account.setName("James");
        }
    }


    @AfterThrowing(
            pointcut = "execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "exception"
    )
    public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, Exception exception) {

        // Print out which method we are advising on
        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);

        // Print the exception thrown
        System.out.println("Exception thrown is: "+ exception);
    }

    @After("execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFindAccountsAdvice(JoinPoint joinPoint) {

        // Print out which method we are advising on
        String method = joinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @After on method: " + method);
    }


    @Around("execution(* com.example.aopdemo.service.*.getFortune(..))")
    public Object aroundGetFortuneAdvice(ProceedingJoinPoint pjp) throws Throwable {

        // Print out the method we are advising on
        String method = pjp.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @Around on method: " + method);

        // Get the begin timestamp
        long begin = System.currentTimeMillis();

        // Execute the method
        Object result = null;

        try {
            result = pjp.proceed();
        } catch (Exception e) {
            System.out.println("Exception thrown: " + e.getMessage());
//            result = "Major accident! But no worries, your private AOP helicoper is on the way!";
            throw e;
        }


        // Get the end timestamp
        long end = System.currentTimeMillis();

        // Compute the duration and display it
        long duration = end - begin;
        System.out.println("Time taken by the getFortune() method to execute: " + duration/1000.0 + " seconds");

        // Return the data back to the calling program
        return result;
    }

}
