package com.example.aopdemo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class MembershipDAOImpl implements MembershipDAO{

    @Override
    public String addMember() {
        System.out.println(getClass() + ": DOING DB WORK: ADDING A MEMBER");
        return "Hello World";
    }
}
