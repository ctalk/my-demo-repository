package com.revature.rkiesling.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.rkiesling.bankmodel.AuthLevel;

public class BankDBUtil implements AuthLevel {
	
	public static final String schemaName = "bank_app";
	public static final String userTableName = "users";
	
	private static void printSQLException (String msg, SQLException e) {
		// This allows us to avoid printing the "query produced no results
		// exceptions.
		if (e.getErrorCode () != 0) {
			System.out.println (msg + e.getMessage ());
		}
	}
	
	public static void makeBankSchema () {
		try (Connection conSchema = JDBCConnection.getConnection ()) {
			String sql = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?";
			PreparedStatement p = conSchema.prepareStatement(sql);
			p.setString(1, schemaName);
			ResultSet rs = p.executeQuery ();
			if (rs.next ()) {
				System.out.println ("Found, \"" + schemaName + ",\"schema.");
			} else {
				System.out.println ("Schema, \"" + schemaName + ",\" not found - creating.");
				try {
					sql = "create schema " + schemaName;
					p = conSchema.prepareStatement (sql);
					rs = p.executeQuery ();
				} catch (SQLException e) {
					System.out.println ("makeBankSchema: " + e.getMessage ());
				}
			}
			
		} catch (SQLException e) {
			System.out.println ("makeBankSchema: " + e.getMessage ());
			e.printStackTrace ();
		}
	}
	
	public static void makeBankTables () {
		try (Connection conTable = JDBCConnection.getConnection ()) {
			
			// Default is: bankapp.users
			String qUserTableName = schemaName + "." + userTableName;
			
			// Default is: "drop table if exists bank_app.users"
			String sql = "drop table if exists " + qUserTableName;
			PreparedStatement p = conTable.prepareStatement(sql);
			ResultSet rs;
			try {
				rs = p.executeQuery ();
			} catch (SQLException e) {
				printSQLException ("makeBankTables: " + sql + " : ", e);
			}
			
			// build query: 
			// create table bank_app.users (
			//		id serial primary key not null,
			//		username varchar(64) not null,
			//		firstName varchar(64) not null,
			//		lastName varchar(64) not null,
			//		password varchar(64) not null,
			//		authLevel int not null,
			//		comment text
			//		) 
		
			StringBuffer query = new StringBuffer (1024);
			query.insert(0, "create table " + qUserTableName + " (");
			query.append("id serial primary key not null,");
			query.append("username varchar(64) not null,");
			query.append("firstName varchar(64) not null,");
			query.append("lastName varchar(64) not null,");
			query.append("password varchar(64) not null,");
			query.append("authLevel int not null,");
			query.append("comment text)");
			p = conTable.prepareStatement(query.toString());
			try {
				rs = p.executeQuery ();
			} catch (SQLException e) {
				printSQLException ("makeBankTables: " + query.toString () + " : ", e);
			}

			query.delete(0,  query.length ());
			
			// Create admin user
			// insert into bank_app.users (username, 
			//							   firstName, 
			//                             lastName, 
			//                             password, 
			//                             authLevel, 
			//                             comment) 
			// values (
			// 'admin', 
			// 'adminFirstName', 
			// 'adminLastName', 
			// 'admin', 
			// AuthLevel.AUTH_ADMIN,
			// 'Machine-generated user.')
			query.insert(0,  "insert into " + qUserTableName + " (");
			query.append("username, firstName, lastName, password, authLevel, comment)");
			query.append(" values ('admin', 'adminFirstName', 'adminLastName', 'admin', " + AuthLevel.AUTH_ADMIN
					+ ", ");
			query.append ("'Machine-generated user.')");
			p = conTable.prepareStatement(query.toString());
			try {
				rs = p.executeQuery ();
			} catch (SQLException e) {
				printSQLException ("makeBankTables: " + query.toString () + " : ", e);
			}
			
		} catch (SQLException e) {
			System.out.println ("makeBankTables: " + e.getMessage ());
			e.printStackTrace ();
			
		}
	
	}

}
