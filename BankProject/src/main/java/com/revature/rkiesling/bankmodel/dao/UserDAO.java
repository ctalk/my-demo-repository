package com.revature.rkiesling.bankmodel.dao;

import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import com.revature.rkiesling.bankmodel.User;

import com.revature.rkiesling.util.JDBCConnection;
import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.UserTable;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.StringBuffer;


public class UserDAO implements AuthLevel, UserTable {

	private static Logger log = Logger.getLogger(UserDAO.class);
	

    // When used with two args, matches both the user name and password,
    // when used with one arg, matches the username only, and is here
    // mainly to check whether a user record exists.
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
							rs.getString("lastName"), rs.getInt ("authlevel"), 
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
			rs.getString("zipcode"), rs.getString("address"), rs.getString("comment"));
					c.close ();
					log.info ("User " + userName + ": Login successful.");
					return user;
				}
			}
			
			log.info ("getLoginInfo: Login info for, \"" + userName + ",\" not found.");
			throw new UserNotFoundException ("getLoginInfo: Login info for, \"" + userName + ",\" not found.");
			
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
	
	public User getLoginInfo (String userName) throws UserNotFoundException {
		Connection c = null;
		try {
			c = J	@Test(expected=UserNotFoundException.class)
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
DBCConnection.getConnection ();
		} catch (SQLException e) {
			System.out.println ("Connection error: " + e.getMessage ());
			log.error ("JDBCConnection error: " + e.getMessage ());
			System.exit(AuthLevel.FAIL);
		}
		String sql = "select * from bank_app.users where username = '" + userName + "'";
		try {
			Statement stmt = c.createStatement ();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next ()) {
			   User user = new User (rs.getString("username"), rs.getString("firstName"),
							rs.getString("lastName"), rs.getInt ("authlevel"), 
							rs.getString("zipcode"), rs.getString("address"), rs.getString("comment"));
			   return user;
			}
			
			log.info ("getLoginInfo: Login info for, \"" + userName + ",\" not found.");
			throw new UserNotFoundException ("getLoginInfo: Login info for, \"" + userName + ",\" not found.");
			
		} catch (SQLException e) {
			log.error("Bad SQL query: " + sql);
		} 
	
		try { 
			c.close ();
		} catch (	@Test(expected=UserNotFoundException.class)
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
SQLException e) {
			log.error("JDBCConnection.close(): " + e.getMessage()); 
		}
		
		return null;

	}
	
	public void addUser (User user) throws SQLException {
		Connection c = null;
		try {
			c = JDBCConnection.getConnection ();
		} catch (SQLException e) {
			System.out.println ("Connection error: " + e.getMessage ());
			log.error ("JDBCConnection error: " + e.getMessage ());
			System.exit(AuthLevel.FAIL);
		}
		StringBuffer sql = new StringBuffer ("insert into " + UserTable.userTableName + "(");
		// sql.append(UserTable.userTableName + "(");
		for (String col: UserTable.colNames) {
			sql.append (col);
		}
		sql.append("values (");
		sql.append("'" + user.userName () + "', ");
		sql.append("'" + user.firstName () + "', ");
		sql.append("'" + user.lastName () + "', ");
		sql.append("'" + user.passWord() + "', ");
		sql.append(user.auth () + ", ");
		sql.append("'" + user.address() + "', ");
		sql.append(user.zipCode() + ", ");
		sql.append("'" + user.comment() + "')");
		
		System.out.println (sql);
		
		// System.out.println (sql);
		try {
			Statement stmt = c.createStatement ();
			Integer nUpdates = stmt.executeUpdate(sql.toString ());
			System.out.println ("nUpdates = " + nUpdates);
		} catch (SQLException e) {
			log.error("Bad SQL query: " + sql);
			throw e;
		} 
		try { 
			c.close ();
		} catch (SQLException e) {
			log.error("JDBCConnection.close(): " + e.getMessage()); 
		}


	}
}
