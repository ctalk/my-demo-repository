package com.revature.rkiesling.main;

import com.revature.rkiesling.util.BankDBUtil;
import com.revature.rkiesling.bankmodel.BankTasks;

import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.dao.UserDAOImpl;
import com.revature.rkiesling.ui.*;
// import com.revature.rkiesling.ui.Login;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.util.JDBCConnection;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;


public class BankApp implements AuthLevel {
	
	private static Logger log = Logger.getLogger(BankApp.class);
	
	public static void main (String[] args) {
		
		int retries = 0;
		
		// Get the admin's credentials from the system
		// before we check for the DBMS.
		LoginService.getAdminCreds ();

		try {
			// Make sure we have a connection.  getConnection prints the "Connected" message
			Connection c = JDBCConnection.getConnection ();
			System.out.println ("Connected.");
			log.info ("Connected to bank RDBMS");
			c.close ();
		} catch (SQLException e) {
			System.out.println ("Bank data connection failed: " + e.getMessage () + ".");
			log.error ("Bank data connection failed: " + e.getMessage () + ".");
			System.exit (AuthLevel.FAIL);
		}
		if (!BankDBUtil.haveBankSchema ()) {
			System.out.println ();
			System.out.println ("Creating new DB.");
			BankDBUtil.makeBankSchema ();
			BankDBUtil.makeBankTables ();
			BankDBUtil.createAdminUser ();
		}
		
		LoginService l = new LoginService ();
		l.getLoginInfoFromForm ("Please log in: ");
		User user = l.getUserLogin ();

		System.out.println ("\nWelcome, " + user.firstName () + " " + user.lastName () + ".\n");
		
		BankTasks.performTasks(user);
								
	}
}
