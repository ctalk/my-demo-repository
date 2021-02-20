package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.model.User;

public interface UserDAO {
	
	User getUserByUsername (String username, Connection con) throws SQLException;

}
