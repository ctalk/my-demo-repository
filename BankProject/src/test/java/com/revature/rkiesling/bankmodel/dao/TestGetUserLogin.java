package com.revature.rkiesling.bankmodel.dao;

import org.junit.Test;

import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

public class TestGetUserLogin {
        
    /// UserDAO dao = new UserDAO ();
        
    // These are typically run with the DBMS login (in environment variables) as:
    //  - db_user=postgres
    //  - db_password=postgres
    //  - db_url=jdbc:postgresql://localhost:5432/postgres
        
    // First create the test data after logging in as
    //  admin (password: admin) in the bank app, and
    // selecting the "Create test data" option.

    @Test(expected=UserNotFoundException.class)
    public void testBadUserName () throws UserNotFoundException {
                
	UserDAO dao = new UserDAO ();
	dao.getLoginInfo("marion3");
    }

    @Test(expected=UserNotFoundException.class)
    public void testBadPassword () throws UserNotFoundException {
                
	UserDAO dao = new UserDAO ();
	dao.getLoginInfo("marion3", "marion3");
    }

    @Test
    public void testUserName () throws UserNotFoundException {
                
	UserDAO dao = new UserDAO ();
	dao.getLoginInfo("marion");
    }

    @Test
    public void testUserNameAndPassword () throws UserNotFoundException {
                
	UserDAO dao = new UserDAO ();
	dao.getLoginInfo("marion", "musicman");
    }

}
