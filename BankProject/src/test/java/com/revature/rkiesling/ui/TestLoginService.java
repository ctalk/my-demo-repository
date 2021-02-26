package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


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
		login.userName("marion");
		login.userPassword("musicman");
		
		User user = login.getUserLogin ();
		
		assertEquals (user.userName(), login.userName ());
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testBadUserName () {
		login.userName("mario");
		login.userPassword("musicman");
		
		@SuppressWarnings("unused")
		User user = login.getUserLogin ();
		
		// assertEquals (user.userName(), login.userName ());
	}
	@Test(expected=UserNotFoundException.class)
	public void testBadPassword () {
		login.userName("marion");
		login.userPassword("musicmana");
		
		@SuppressWarnings("unused")
		User user = login.getUserLogin ();
		
		// assertEquals (user.userName(), login.userName ());
	}


}
