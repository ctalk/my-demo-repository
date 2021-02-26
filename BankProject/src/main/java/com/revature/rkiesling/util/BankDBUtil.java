package com.revature.rkiesling.util;

import com.revature.rkiesling.ui.LoginService;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.rkiesling.bankmodel.AuthLevel;

public class BankDBUtil implements AuthLevel {
	
	public static final String schemaName = "bank_app";
	public static final String userTableName = "users";
	
	private static Logger log = Logger.getLogger(BankDBUtil.class);
	
	private static void reportSQLException (String msg, SQLException e) {
		// This allows us to avoid printing the "query produced no results,"
		// messages, and or other exceptions where the error code == 0.
		if (e.getErrorCode () != 0) {
			System.out.println (msg + e.getMessage ());
			log.error(msg + e.getMessage ());
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
			reportSQLException ("haveBankSchema exception: ", e);
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
				log.info("Found, \"" + schemaName + ",\"schema.");
			} else {
				log.info("Schema, \"" + schemaName + ",\" not found - creating...");
				try {
					sql = "create schema " + schemaName;
					p = conSchema.prepareStatement (sql);
					p.executeUpdate ();
					conSchema.close ();
					log.info("Success.");
				} catch (SQLException e) {
					reportSQLException ("makeBankSchema: ", e);
				}
			}
			
		} catch (SQLException e) {
			reportSQLException ("makeBankSchema: ", e);
		}
	}
	
	public static void makeTestUserData () {
		
		try (Connection conTestUser = JDBCConnection.getConnection ()) {
			
			
			ArrayList<String> testUserNames = new ArrayList <String>(Arrays.asList("marion", "declan"));
			ArrayList<String> testPasswords = new ArrayList<String>(Arrays.asList("musicman", "bigCommittment"));
			String qUserTableName = schemaName + "." + userTableName;


			// Create test user
			// insert into bank_app.users (username, 
			//							   firstName, 
			//                             lastName, 
			//                             password, 
			//                             authLevel, 
			//                             comment) 
			// values (
			//     Values in testUsernames and testPasswords are unique - 
			//	   all of the other fields can be the same, because, at this time,  while we're only testing logins.
			// )
			
			String createTestSQL =  
					"insert into " + qUserTableName + " (" +
					 		"username, firstName, lastName, password, authLevel, address, zipcode, comment)" +
							" values (?, 'testFirstName', 'testLastName', ?, " + AuthLevel.AUTH_GUEST + ", 'testAddress', " + 
					      10000 + ", 'Machine generated test data.')";
			
			PreparedStatement p = conTestUser.prepareStatement(createTestSQL);
			
			log.info("Creating test user data...");

			for (int i = 0; i < testUserNames.size (); ++i) {
				
				p.setString(1, testUserNames.get(i));
				p.setString(2, testPasswords.get(i));
				p.addBatch ();
				log.info(" - Adding test user " + testUserNames.get(i) + ".");
					
			}
			try {
				p.executeBatch ();
				conTestUser.close ();
				log.info("Success.");
			} catch (SQLException e) {
				reportSQLException ("makeTestUserData: ", e);
			}
				
		} catch (SQLException e) {
			reportSQLException ("makeTestUserData: ", e);
		}
											
	}

	public static void createAdminUser () {
		try (Connection conAdminUser = JDBCConnection.getConnection ()) {
			String qUserTableName = schemaName + "." + userTableName;

			// Create admin user
			// insert into bank_app.users (username, 
			//							   firstName, 
			//                             lastName, 
			//                             password, 
			//                             authLevel, 
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
								" values ('" + LoginService.adminName () + "', 'adminFirstName', 'adminLastName', '" +
								LoginService.adminPassword () + "', " + AuthLevel.AUTH_ADMIN + ", 'adminAddress', " + 10000 + ", 'Machine generated user.')";
			
									
			PreparedStatement p = conAdminUser.prepareStatement(createAdminSQL);
			try {
				Integer nUpdatedRows = p.executeUpdate ();
				if (nUpdatedRows == 1) {
					log.info("Admin user created.");
				} else {
					log.error("Failed to create admin user.");
				}
				conAdminUser.close ();
				log.info ("Create admin user.");
			} catch (SQLException e) {
				reportSQLException ("createAdminUser: " + createAdminSQL + " : ", e);
			}
			
		} catch (SQLException e) {
			reportSQLException ("createAdminUser: ", e);
		}
	}
	
	public static void clearTables () {
		String qUserTableName = schemaName + "." + userTableName;
		String sql = "truncate table " + qUserTableName;
		try (Connection clrTable = JDBCConnection.getConnection ()) {
			PreparedStatement p = clrTable.prepareStatement(sql);
			p.executeUpdate ();
			log.info("Tables truncated.");
		} catch (SQLException e) {
			reportSQLException ("clearTables: " + sql + " : ", e);
		}
	}
	
	public static void makeBankTables () {
		String queryActual = "";
		try (Connection conTable = JDBCConnection.getConnection ()) {
			
			// Default is: bankapp.users
			String qUserTableName = schemaName + "." + userTableName;
			
			// Default is: "drop table if exists bank_app.users"
			String sql = "drop table if exists " + qUserTableName;
			PreparedStatement p = conTable.prepareStatement(sql);
			try {
				p.executeUpdate ();
				log.info("makeBankTales: removed old bank_app.user table");
			} catch (SQLException e) {
				reportSQLException ("makeBankTables: " + sql + " : ", e);
				log.error ("makeBankTables: " + sql + " : " + e.getMessage ());
			}
			
			// build query: 
			// create table bank_app.users (
			//		id serial primary key not null,
			//		username varchar(64) not null,
			//		firstName varchar(64) not null,
			//		lastName varchar(64) not null,
			//		password varchar(64) not null,
			//		authLevel int not null,
			//      address varchar(255),
			//      zipCode Integer,
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
			query.append("address varchar(255),");
			query.append("zipCode int,");
			query.append("comment text)");
			queryActual = query.toString ();
			p = conTable.prepareStatement(query.toString());
			try {
				p.executeUpdate ();
				conTable.close ();
				log.info("makeBankTables: created bank_app.user table");
			} catch (SQLException e) {
				reportSQLException ("makeBankTables: " + queryActual + " : ", e);
			}
						
		} catch (SQLException e) {
			reportSQLException ("makeBankTables: " + queryActual + " : ", e);
		}
	
	}

}
