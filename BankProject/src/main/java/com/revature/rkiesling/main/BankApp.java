package com.revature.rkiesling.main;

import com.revature.rkiesling.util.BankDBUtil;

import com.revature.rkiesling.util.JDBCConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class BankApp {
	public static void main (String[] args) {
		JDBCConnection con = new JDBCConnection ();

		try {
			// getConnection prints the connection message
			con.getConnection ();
			System.out.println ("Connected.");
		} catch (SQLException e) {
			System.out.println ("Failed: " + e.getMessage () + ".");
		}
		BankDBUtil.makeBankSchema ();
		BankDBUtil.makeBankTables ();
	}
}
