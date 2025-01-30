package com.example.aopdemo.dao;

import com.example.aopdemo.Account;

import java.util.List;

public interface AccountDAO {

    void addAccount();

    void addAccount(Account account);

    void addAccount(Account account, boolean vip);

    String getServiceCode();
    void setServiceCode(String message);

    String getName();
    void setName(String name);

    List<Account> findAccounts(boolean tripWire) throws Exception;
}
