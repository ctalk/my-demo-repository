package com.revature.rkiesling.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBCConnection {
	
	// Creates a PostgreSQL connection using the following environment variables:
	//   db_user
	//   db_password
	//   db_url
	
	public static Connection getConnection () throws SQLException {
		// use DriverManager
		DriverManager.registerDriver(new Driver ());
		
		String url = System.getenv ("db_url");
		// For testing if needed.
		// System.out.print ("Connecting to " + url + "... ");
		System.out.flush ();
		String username = System.getenv ("db_user");
		String password = System.getenv ("db_password");
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}

}
