package com.revature.service;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.exceptions.LoginException;
import com.revature.model.User;

public class MyServiceTest {

		public MyService myService;
		
		/*
		 * A method annotated with @BeforeClass before the test class is instantiated.
		 * This is primarily used to set up some sort of "global" static variables that
		 * all of your tests might make use of.
		 */
		
		@BeforeClass
		public static void setupBeforeClass () {
			
		}
		
		/* Methods annotate with @Before will be invoked BEFORE EACH INDIVIDUAL test case
		 * Typically used to setup specific test environment
		 * (or to reset it after each test)
		 * 
		 */
		@Before
		public void setUp () {
			myService = new MyService ();
		}
		
		/* Methods annotated with @After will be invoked after each individual test case
		 * User to clean up specific test environment
		 */
		@After
		public void tearDown () {
			
		}
		
		@Test
		public void testPassingInAnEvenNumber () {
			boolean expected = true;
			boolean actual = myService.isValidEvenNumber (10);
			assertEquals(expected, actual);
		}
		
		@Test
		public void testPassingInAnOddNumber () {
			// boolean expcted = false
			boolean actual = myService.isValidEvenNumber(9);
//			asserEquals(expected,actual);
			assertFalse(actual);
		}
		
		// This is an example of a positive test (happy path)
		// We're testing to see if, when a user uses the application properly and provides correct credentials
		// To actually do what we want it to do
		// Test login method
		@Test
		public void testValidUsernameAndPassword () throws LoginException {
			String username = "abc123";
			String password = "12345";
			
			User actual = myService.login (username, password);
			User expected = new User ("abc123", "12345");
			
			assertEquals(expected, actual);
		}
		
		// This is the concept of a negative test
		// In other words, we want to test for behavior we want to happen if a user does something that
		// is "negative" like putting in an incorrect password
		@Test(expected = LoginException.class)
		public void testValidUsernameInvalidPassword () throws LoginException {
			String username = "abc123";
			String password = "123456";
			
			User actual = myService.login (username, password);
		}
		
		@Test(expected = LoginException.class)
		public void testInvalidUserNameValidPassword () throws LoginException {
			String username = "abc1234";
			String password = "12345";
			
			User actual = myService.login(username, password);
		}
		
		
		
		
}
