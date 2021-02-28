package com.revature.rkiesling.bankmodel.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

//import static org.mockito.Mockito.*;

public class TestAddUserDAO {
	
	UserDAO dao = new UserDAO ();
	
	// These are typically run with the DBMS login (in environment variables) as:
	//  - db_user=postgres
	//  - db_password=postgres
	//  - db_url=jdbc:postgresql://localhost:5433/postgres
	
	// First create the test data after logging in as admin in
	// the bank app.
	@Test(expected=SQLException.class)
	public void testDuplicateUserName () {
		
		try {
			@SuppressWarnings("unused")
			User user = new User ();
			user.userName("marion");
			UserDAO dao = new UserDAO ();
			dao.addUser(user);
			
		} catch (SQLException e) {
		//
		}	
	}

}
