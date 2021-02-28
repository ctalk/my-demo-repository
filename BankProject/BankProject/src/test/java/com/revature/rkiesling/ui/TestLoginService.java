package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// import static org.mockito.Mockito.*;


public class TestLoginService {
	public LoginService login;
	
	@Before
	public void setUp () {
		login = new LoginService ();
	}
	
	// These are typically run with the DBMS login (in environment variables) as:
	//  - db_user=postgres
	//  - db_password=postgres
	//  - db_url=jdbc:postgresql://localhost:5433/postgres
	
	@Test
	public void testValidLogin () {
		User user = null;
		login.userName("marion");
		login.userPassword("musicman");
		
		try {
			user = login.getUserLogin ();
		} catch (UserNotFoundException e) {
			//
		}
		
		assertEquals (user.userName(), login.userName ());
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testBadUserName () {
		login.userName("mario");
		login.userPassword("musicman");
		
		try {
			@SuppressWarnings("unused")
			User user = login.getUserLogin ();
		} catch (UserNotFoundException e) {
		//
		}	
		
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testBadPassword () {
		login.userName("marion");
		login.userPassword("musicmana");
		
		try {
			@SuppressWarnings("unused")
			User user = login.getUserLogin ();
		} catch (UserNotFoundException e) {
			
		}
		
		
	}

}
