package com.revature.rkiesling.bankmodel.dao;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.revature.rkiesling.util.JDBCConnection;
import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.dao.PostDAO;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

//import static org.mockito.Mockito.*;

public class TestAddUserDAOMocked implements AuthLevel {
        
    public static UserDAO userDAO;
    public static PostDAO postDAO;
    public static Connection mockConnection;
        
    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        userDAO = mock (UserDAO.class);
        postDAO = mock (PostDAO.class);
        mockConnection = mock (Connection.class);

        when(userDAO.getLoginInfo(eq("marion3"))).thenReturn(new User("marion3", "Marion", "Librarian",
                                                                      AuthLevel.AUTH_CUSTOMER, "12000", "123 abc st.",
                                                                      "no comment"));
                
    }

    @AfterClass
    public static void afterClass () throws Exception {
    }

    @Before
    public void setup () {
    }

    // These are typically run with the DBMS login (in environment variables) as:
        //  - db_user=postgres
        //  - db_password=postgres
        //  - db_url=jdbc:postgresql://localhost:5433/postgres
        
        // First create the test data after logging in as admin in
        // the bank app.
        @Test
        public void testValidLogin () throws UserNotFoundException  {
                
            try (MockedStatic<JDBCConnection> mockedStatic = Mockito.mockStatic (JDBCConnection.class)) {

                mockedStatic.when(JDBCConnection::getConnection).thenReturn(mockConnection);

                User actual = userDAO.getLoginInfo ("marion");

                User expected = new User ("marion", "testFirstName", "testLastName", 
                                          AuthLevel.AUTH_CUSTOMER, "testAddress", "10000",
                                          "Machine generated test data.");
                // expected.getLoginInfo ("marion");

                assertEquals(expected, actual);
                                          
            }
        }
}
