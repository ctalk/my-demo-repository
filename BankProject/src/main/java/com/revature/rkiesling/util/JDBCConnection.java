package com.revature.rkiesling.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.postgresql.Driver;

public class JDBCConnection {
        
    private static Logger log = Logger.getLogger(JDBCConnection.class);

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
                connection.setAutoCommit (true);
                return connection;
        }

    public static void close (Connection c) {
        try {
            c.close ();
        } catch (SQLException e) {
            log.error ("JDBCConnection : close : " + e.getMessage ());
        }
    }

}
