package com.example.aopdemo.service;

import com.example.aopdemo.dao.AccountDAO;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public String addAccount() {
        System.out.println(getClass() + ": INSIDE ADD ACCOUNT METHOD IN SERVICE CLASS");
        return "Hello World";
    }
}
