package com.revature.rkiesling.bankmodel.dao;

import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import org.postgresql.Driver;


import com.revature.rkiesling.util.JDBCConnection;

public class UserDAOImpl {


	
	public User getLoginInfo (String userName, String password) {
		Connection c = null;
		try {
			c = JDBCConnection.getConnection ();
		} catch (SQLException e) {
			System.out.println ("Connection error: " + e.getMessage ());
			System.exit(1);
		}
		String sql = "select * from bank_app.users where username = '" + userName + "'";
		try {
			Statement stmt = c.createStatement ();
			ResultSet rs = stmt.executeQuery(sql);
			User user = null;
			if (rs.next ()) {
				String dbpass = null;
				dbpass = rs.getString("password");
				if (dbpass.equals(password)) {
					user = new User (rs.getString("username"), rs.getString("firstName"),
							rs.getString("lastName"), rs.getInt ("authlevel"));
					c.close ();
					return user;
				}
			}
		
		} catch (SQLException e) {
			
		} 
		
		try { c.close ();} catch (SQLException e) { return null; }
		
		return null;

	}
}
