package com.revature.rkiesling.util;

import com.revature.rkiesling.ui.Login;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.rkiesling.bankmodel.AuthLevel;

public class BankDBUtil implements AuthLevel {
	
	public static final String schemaName = "bank_app";
	public static final String userTableName = "users";
	
	private static void printSQLException (String msg, SQLException e) {
		// This allows us to avoid printing the "query produced no results,"
		// messages, and or other exceptions where the error code == 0.
		if (e.getErrorCode () != 0) {
			System.out.println (msg + e.getMessage ());
		}
	}
	
	public static boolean haveBankSchema () {
		try (Connection conSchema = JDBCConnection.getConnection ()) {
			String sql = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?";
			PreparedStatement p = conSchema.prepareStatement(sql);
			p.setString(1, schemaName);
			ResultSet rs = p.executeQuery ();
			if (rs.next ()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println ("haveBankSchema exception: " + e.getErrorCode ());
			return false;
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
			
		} catch (SQLException e) {// System.out.println ("makeBankTables: " + query.toString () + " : " + e.getMessage ());
			
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
			query.append("address varchar (255),");
			query.append("zipcode int,");
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
			//                             address,
			//                             zipcode,
			//                             comment) 
			// values (
			// 'admin',          - Determined by Login.getAdminCreds () when we started.
			// 'adminFirstName', 
			// 'adminLastName', 
			// 'admin',          - Also determined by Login.getAdminCreds ().
			// AuthLevel.AUTH_ADMIN,
			// 'Machine-generated user.')
			
			String createAdminSQL = "insert into " + qUserTableName + " (" +
								"username, firstName, lastName, password, authLevel, address, zipcode, comment)" +
								" values ('" + Login.adminName () + "', 'adminFirstName', 'adminLastName', '" +
								Login.adminPassword () + "', " + AuthLevel.AUTH_ADMIN + ", 'adminAddress', " + 10000 + ", 'Machine generated user.')";
			System.out.println (createAdminSQL);
									
			p = conTable.prepareStatement(createAdminSQL);
			try {
				rs = p.executeQuery ();
			} catch (SQLException e) {
				printSQLException ("makeBankTables: " + query.toString () + " : ", e);
			}
			conTable.close ();
		} catch (SQLException e) {
			System.out.println ("makeBankTables: " + e.getMessage ());
			e.printStackTrace ();
			
		}
	
	}

}
