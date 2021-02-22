package com.revature.rkiesling.main;

import com.revature.rkiesling.util.BankDBUtil;
import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.ui.ScreenUtil;
import com.revature.rkiesling.ui.Login;

import com.revature.rkiesling.util.JDBCConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class BankApp implements AuthLevel {
	
	// Start with no one logged in.
	private int userAuthLevel = AUTH_NONE;
	
	public static void main (String[] args) {
		
		// Get the admin's credentials from the system
		// before we check for the DBMS.
		Login.getAdminCreds ();

		JDBCConnection con = new JDBCConnection ();

		try {
			// getConnection prints the connection message
			con.getConnection ();
			System.out.println ("Connected.");
		} catch (SQLException e) {
			System.out.println ("Failed: " + e.getMessage () + ".");
		}
		if (!BankDBUtil.haveBankSchema ()) {
			System.out.println ();
			System.out.println ("Creating new DB.");
			BankDBUtil.makeBankSchema ();
			BankDBUtil.makeBankTables ();
			ScreenUtil.pause ();
		}
		
		ScreenUtil.pause ();
		ScreenUtil.clear ();
		ScreenUtil.pause ();
		
		
	}
}
