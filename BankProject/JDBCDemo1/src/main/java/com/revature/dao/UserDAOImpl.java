package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.util.ConnectionUtil;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.User;

public class UserDAOImpl implements UserDAO {

	@Override
	public List<User> getAllUsers () {
		List<User> users = new ArrayList<>();
			
		// special form of try: try-with-resources
		try (Connection con = ConnectionUtil.getConnection()) {
			// 1. get a connection object
			// 2. Obtain some sort of statement object
			// 3. Execute the query
			Statement stmt = con.createStatement ();
			String sql = "SELECT * FROM users";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next ()) {
				String username = rs.getString ("username");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				User user = new User (username, firstName,
						lastName);
				users.add(user);
						
			}
		} catch (SQLException e) {
			System.out.println ("getAllUsers: " + e.getMessage());
		}
			
		return users;
			
	}
	
	
	@Override
	public User getUserByUsername (String username) throws UserNotFoundException {
		User user = null;
		
		try (Connection con = ConnectionUtil.getConnection ()) {
			// a prepared statement is safer
			String sql = "SELECT * FROM users WHERE username = ?";
			PreparedStatement pstmt = con.prepareStatement (sql);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery ();
			
			if (rs.next ()) {
				String uname = rs.getString("username");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				
				user = new User (uname, firstName, lastName);
			} else {
				throw new UserNotFoundException("A user with username '" + username + 
						"' was not found.");
			}
		} catch (SQLException e) {
			System.out.println ("getUserByName: " + e.getMessage ());
		}
		return user;
	}
	@Override
	public int insertUser (User user) {
		int updateCount = 0;
		
		try (Connection con = ConnectionUtil.getConnection ()) {
			String sql = 
					"INSERT INTO users (username, first_name, last_name) VALUES (?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement (sql);
			pstmt.setString(1, user.getUsername ());
			pstmt.setString(2, user.getFirstName ());
			pstmt.setString(3, user.getLastName ());
			updateCount = pstmt.executeUpdate ();
		} catch (SQLException e) {
			System.out.println ("insertUser: " + e.getMessage ());
		}
		
		return updateCount;
	}
		
}
