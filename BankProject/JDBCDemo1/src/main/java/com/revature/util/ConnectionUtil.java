package com.revature.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class ConnectionUtil {
	// this is where we actually obtain the connection.
	// utility class, which means it just contains static methods
	private ConnectionUtil () {
		super ();
	}
	
	public static Connection getConnection () throws SQLException {
		// use DriverManager
		DriverManager.registerDriver(new Driver ());
		
		String url = System.getenv ("db_url");
		String username = System.getenv("db_user");
		String password = System.getenv ("db_password");
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
}
