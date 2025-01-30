package com.example.aopdemo;

import com.example.aopdemo.dao.AccountDAO;
import com.example.aopdemo.dao.MembershipDAO;
import com.example.aopdemo.service.AccountService;
import com.example.aopdemo.service.TrafficFortuneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AopdemoApplication implements CommandLineRunner {

	private final AccountDAO accountDAO;
	private final AccountService accountService;
	private final MembershipDAO membershipDAO;
	private final TrafficFortuneService trafficFortuneService;

	public AopdemoApplication(AccountDAO accountDAO, AccountService accountService, MembershipDAO membershipDAO, TrafficFortuneService trafficFortuneService) {
		this.accountDAO = accountDAO;
		this.accountService = accountService;
		this.membershipDAO = membershipDAO;
		this.trafficFortuneService = trafficFortuneService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		methodCallsForBeforeAdvice();

//		methodCallsForAfterReturningAdvice();

//		methodCallsForAfterThrowingAdvice();

//		methodCallsForAfterAdvice();

		methodCallsForAroundAdvice();
	}

	void methodCallsForAroundAdvice() throws Exception {
		System.out.println("\n\nMain Program: methodCallsForAroundAdvice");

		System.out.println("Calling getFortune()");

		String data = this.trafficFortuneService.getFortune(true);

		System.out.println("\n My fortune is:  "+ data);

		System.out.println("Finished");
	}

	void methodCallsForAfterAdvice() {
		List<Account> accounts = null;

		try {
			accounts = this.accountDAO.findAccounts(true);
		} catch (Exception e) {
			System.out.println("\n\nMain Program: ... caught exception: " + e);
		}

		System.out.println("\n\nMain Program: methodCallsForAfterThrowingAdvice");
		System.out.println("Accounts are :" + accounts);
	}

	void methodCallsForAfterThrowingAdvice() {
		List<Account> accounts = null;

		try {
			accounts = this.accountDAO.findAccounts(true);
		} catch (Exception e) {
			System.out.println("\n\nMain Program: ... caught exception: " + e);
		}

		System.out.println("\n\nMain Program: methodCallsForAfterThrowingAdvice");
		System.out.println("Accounts are :" + accounts);
	}

	void methodCallsForAfterReturningAdvice() throws Exception {
		List<Account> accounts = this.accountDAO.findAccounts(false);
		System.out.println("Method findAccounts() called from the main Class.");
		System.out.println("Accounts are :" + accounts);
	}

	void methodCallsForBeforeAdvice() {
		this.accountDAO.addAccount();

		Account account = new Account("1", "John");
		this.accountDAO.addAccount(account);

		this.accountDAO.addAccount(account, true);

		this.accountDAO.setName("John");
		this.accountDAO.getName();

		this.accountDAO.setServiceCode("1234");
		this.accountDAO.getServiceCode();

		this.membershipDAO.addMember();
	}
}
