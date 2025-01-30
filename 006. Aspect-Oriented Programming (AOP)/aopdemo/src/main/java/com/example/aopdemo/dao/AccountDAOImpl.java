package com.example.aopdemo.dao;


import com.example.aopdemo.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDAOImpl implements AccountDAO {

    private String name;
    private String serviceCode;

    @Override
    public void addAccount() {
        System.out.println(getClass() + ": DOING DB WORK: ADDING AN ACCOUNT WITHOUT TAKING ANY ARGUMENTS");
    }

    @Override
    public void addAccount(Account account) {
        System.out.println(getClass() + ": DOING DB WORK: ADDING AN ACCOUNT BY TAKING ACCOUNT AS ARGUMENT");
    }

    @Override
    public void addAccount(Account account, boolean vip) {
        System.out.println(getClass() + ": DOING DB WORK: ADDING AN ACCOUNT BY TAKING ACCOUNT & VIP AS ARGUMENT");
    }

    @Override
    public List<Account> findAccounts(boolean tripWire) throws Exception {

        // Throw an exception to test afterThrowing advice
        if (tripWire) throw new Exception("Exception thrown from findAccounts() method!");

        List<Account> accounts = new ArrayList<>();

        Account account1 = new Account("Silver", "John");
        Account account2 = new Account("Platinum", "Mary");
        Account account3 = new Account("Gold", "Susan");

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        return accounts;

    }

    public String getName() {
        System.out.println(getClass() + ": getName()");
        return name;
    }

    public void setName(String name) {
        System.out.println(getClass() + ": setName()");
        this.name = name;
    }

    public String getServiceCode() {
        System.out.println(getClass() + ": getServiceCode()");
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        System.out.println(getClass() + ": setServiceCode()");
        this.serviceCode = serviceCode;
    }

    @Override
    public String toString() {
        return "AccountDAOImpl{" +
                "name='" + name + '\'' +
                ", serviceCode='" + serviceCode + '\'' +
                '}';
    }
}
