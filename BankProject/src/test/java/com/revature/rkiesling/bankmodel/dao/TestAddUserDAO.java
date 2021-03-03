package com.revature.rkiesling.bankmodel.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.bankmodel.exception.UserAlreadyExistsException;

public class TestAddUserDAO {
        
    UserDAO dao = new UserDAO ();
        
    // These are typically run with the DBMS login (in environment variables) as:
    //  - db_user=postgres
    //  - db_password=postgres
    //  - db_url=jdbc:postgresql://localhost:5432/postgres
        
    // First create the test data after logging in as
    //  admin (password: admin) in the bank app, and
    // selecting the "Create test data" option.

    @Test(expected=UserAlreadyExistsException.class)
    public void testDuplicateUserName () throws UserAlreadyExistsException, SQLException {
                
        User user = new User ();
        user.userName("marion");
        UserDAO dao = new UserDAO ();
        dao.addUser(user);
    }

    @Test
    public void uniqueUserName () throws UserAlreadyExistsException, SQLException {
        User user = new User ();
        user.userName("marion2");
        UserDAO dao = new UserDAO ();
        dao.addUser(user);
    }

}
