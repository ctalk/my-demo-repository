package com.revature.rkiesling.bankmodel.dao;

import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.bankmodel.User;

import com.revature.rkiesling.util.JDBCConnection;
import com.revature.rkiesling.bankmodel.AuthLevel;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.StringBuffer;


public class UserDAOImpl implements AuthLevel {

	private static Logger log = Logger.getLogger(UserDAOImpl.class);
	
	public User getLoginInfo (String userName, String password) throws UserNotFoundException {
		Connection c = null;
		try {
			c = JDBCConnection.getConnection ();
		} catch (SQLException e) {
			System.out.println ("Connection error: " + e.getMessage ());
			log.error ("JDBCConnection error: " + e.getMessage ());
			System.exit(AuthLevel.FAIL);
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
					log.info ("User " + userName + ": Login successful.");
					return user;
				}
			}
			
			log.info ("Login for, \"" + userName + ",\" failed.");
			throw new UserNotFoundException ("Login for, \"" + userName + ",\" failed.");
			
		} catch (SQLException e) {
			log.error("Bad SQL query: " + sql);
		} 
	
		try { 
			c.close ();
		} catch (SQLException e) {
			log.error("JDBCConnection.close(): " + e.getMessage()); 
		}
		
		return null;

	}
	
	public void addUser (User user) {
		Connection c = null;
		try {
			c = JDBCConnection.getConnection ();
		} catch (SQLException e) {
			System.out.println ("Connection error: " + e.getMessage ());
			log.error ("JDBCConnection error: " + e.getMessage ());
			System.exit(AuthLevel.FAIL);
		}
		StringBuffer sql = 
				new StringBuffer ("insert into bank_app.users (username, firstname, lastname, password, authlevel, address, zipcode, comment) values (");
		sql.append("'" + user.userName () + "', ");
		sql.append("'" + user.firstName () + "', ");
		sql.append("'" + user.lastName () + "', ");
		sql.append("'" + user.passWord() + "', ");
		sql.append(user.auth () + ", ");
		sql.append("'" + user.address() + "', ");
		sql.append(user.zipCode() + ", ");
		sql.append("'" + user.comment() + "')");
		
		// System.out.println (sql);
		try {
			Statement stmt = c.createStatement ();
			Integer nUpdates = stmt.executeUpdate(sql.toString ());
		} catch (SQLException e) {
			log.error("Bad SQL query: " + sql);
		} 
		try { 
			c.close ();
		} catch (SQLException e) {
			log.error("JDBCConnection.close(): " + e.getMessage()); 
		}


	}
}
