package com.revature.service;

import com.revature.exceptions.LoginException;
import com.revature.model.User;

public class MyService {
	
	// This is basically a single unit of code 
	// We write unit tests to test these units
	// Unit tests are committed inside classes known as a "test case"
	public boolean isValidEvenNumber (int n) {
		// Add logic later ... TDD (test driven development)
		// Write the tests first then write the code to get those tests to pass
		// This is a paradigm in software development that a lot of
		// companies follow
		return n % 2 == 0;
	}
	
	public User login (String username, String password) throws LoginException {
		if (username.equals("abc123") && password.equals("12345")) {
			return new User ("abc123", "12345");
		} else {
			throw new LoginException ("Username or password was incorrect");
		}
	}

}
