package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.model.User;

public class UserDAOImpl {
	
	public User getUserByUsername (String username, Connection con) throws SQLException {
		User user = null;
		String sql = "SELECT * FROM users WHERE username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1,  username);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next ()) {
			String un = rs.getString("username");
			String firstName = rs.getString ("first_name");
			String lastName = rs.getString("las_name");
			user = new User (username, firstName, lastName);
		}
		
		
		
		return user;
	}
}
