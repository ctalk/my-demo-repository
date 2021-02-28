package com.revature.rkiesling.util;

import com.revature.rkiesling.ui.LoginService;

import com.revature.rkiesling.bankmodel.UserTable;
import com.revature.rkiesling.bankmodel.TransactionTable;
import com.revature.rkiesling.bankmodel.BalanceTable;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.ResultSet;

import com.revature.rkiesling.bankmodel.AuthLevel;

public class BankDBUtil implements AuthLevel, UserTable, TransactionTable, BalanceTable {
	
	// public static final String schemaName = "bank_app";
	// public static final String userTableName = "users";
	
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
			p.setString(1, UserTable.schemaName);
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
			p.setString(1, UserTable.schemaName);
			ResultSet rs = p.executeQuery ();
			if (rs.next ()) {
				log.info("Found, \"" + UserTable.schemaName + ",\"schema.");
			} else {
				log.info("Schema, \"" + UserTable.schemaName + ",\" not found - creating...");
				try {
					sql = "create schema " + UserTable.schemaName;
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
					"insert into " + UserTable.userTableName + " (" +
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
			// 'Machine-generated user.')		String userQueryActual = "";

			
			String createAdminSQL = "insert into " + UserTable.userTableName + " (" +
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
		String sqlUser = "truncate table " + UserTable.userTableName;
		String sqlTrans = "truncate table " + TransactionTable.transactionTableName;
		String sqlBalance = "truncate table " + BalanceTable.balanceTableName;
		try (Connection clrTable = JDBCConnection.getConnection ()) {
			Statement p = clrTable.createStatement();
			p.addBatch (sqlUser);
			p.addBatch (sqlTrans);
			p.addBatch (sqlBalance);
			p.executeBatch ();
			log.info("Tables truncated.");
			clrTable.close();
		} catch (SQLException e) {
			reportSQLException ("clearTables: ", e);
		}
	}
	
	public static void makeBankTables () {
		try (Connection conTable = JDBCConnection.getConnection ()) {
			
			// Default is: "drop table if exists bank_app.users"
			String sqlUser = "drop table if exists " + UserTable.userTableName;
			PreparedStatement pUser = conTable.prepareStatement(sqlUser);
			try {
				pUser.executeUpdate ();
				log.info("makeBankTables: removed old " + UserTable.userTableName + ".");
			} catch (SQLException e) {
				reportSQLException ("makeBankTables: " + sqlUser + " : ", e);
			}

			//
			// create table SQL: see UserTable.java, TransactionTable.java, and BalanceTable.java:
			//
		
			StringBuffer queryUser = new StringBuffer (1024);
			queryUser.insert(0, "create table " + UserTable.userTableName + " (");
			for (String spec: UserTable.colDefs) {
				queryUser.append(spec);
			}
			pUser = conTable.prepareStatement(queryUser.toString());
			try {
				pUser.executeUpdate ();
				// conTable.close ();
				log.info("makeBankTables: created bank_app.user table.");
			} catch (SQLException e) {
				reportSQLException ("makeBankTables: " + queryUser.toString () + " : ", e);
			}
			
			// Now create bank_app.transactions
			String sqlTrans = "drop table if exists " + TransactionTable.transactionTableName;
			PreparedStatement pTrans = conTable.prepareStatement(sqlTrans);
			try {
				pTrans.executeUpdate ();
				log.info("makeBankTables: removed old " + TransactionTable.transactionTableName + ".");
			} catch (SQLException e) {
				reportSQLException ("makeBankTables: " + sqlTrans + " : ", e);
			}
			StringBuffer queryTrans = new StringBuffer (1024);
			queryTrans.insert(0, "create table " + TransactionTable.transactionTableName + " (");
			for (String spec: TransactionTable.colDefs) {
				queryTrans.append(spec);
			}
			
			pTrans = conTable.prepareStatement(queryTrans.toString());
			try {
				pTrans.executeUpdate ();
				// conTable.close ();
				log.info("makeBankTables: created " + TransactionTable.transactionTableName + " table.");
			} catch (SQLException e) {
				reportSQLException ("makeBankTables: " + queryTrans.toString () + " : ", e);
			}

			// Now create bank_app.balances
			String sqlBalance = "drop table if exists " + BalanceTable.balanceTableName;
			PreparedStatement pBal = conTable.prepareStatement(sqlBalance);
			try {
				pBal.executeUpdate ();
				log.info("makeBankTables: removed old " + BalanceTable.balanceTableName + ".");
			} catch (SQLException e) {
				reportSQLException ("makeBankTables: " + sqlBalance + " : ", e);
			}
			StringBuffer queryBalance = new StringBuffer (1024);
			queryBalance.insert(0, "create table " + BalanceTable.balanceTableName + " (");
			for (String spec: BalanceTable.colDefs) {
				queryBalance.append(spec);
			}
			
			pBal = conTable.prepareStatement(queryBalance.toString());
			try {
				pBal.executeUpdate ();
				log.info("makeBankTables: created " + BalanceTable.balanceTableName + " table.");
			} catch (SQLException e) {
				reportSQLException ("makeBankTables: " + queryBalance.toString () + " : ", e);
			}
			
			conTable.close ();
						
		} catch (SQLException e) {
			reportSQLException ("makeBankTables: ", e);
		}
	
	}

}
