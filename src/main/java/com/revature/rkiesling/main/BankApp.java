package com.revature.rkiesling.main;

import com.revature.rkiesling.util.BankDBUtil;
import com.revature.rkiesling.ui.Menu;

import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.User;

import com.revature.rkiesling.ui.ScreenUtil;
import com.revature.rkiesling.ui.Login;
import com.revature.rkiesling.bankmodel.dao.UserDAOImpl;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import com.revature.rkiesling.util.JDBCConnection;

import java.sql.SQLException;

public class BankApp implements AuthLevel {
	
	public static void main (String[] args) {
		
		// Get the admin's credentials from the system
		// before we check for the DBMS.
		Login.getAdminCreds ();

		// JDBCConnection con = new JDBCConnection ();

		try {
			JDBCConnection.getConnection ();
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

		Login login = new Login ();
		UserDAOImpl userRec = new UserDAOImpl ();
		User user = null;
		//boolean haveUser = false;

		login.promptForLogin();

		while ((user = userRec.getLoginInfo(login.userName (), login.password ())) == null) {
			login.promptForLogin("User " + login.userName () + " not found.  Please try again");
		}
		System.out.println ("\nWelcome, " + user.firstName () + " " + user.lastName () + ".\n");
		
		switch (user.auth ())
		{
		case AuthLevel.AUTH_ADMIN:
			Menu m = new Menu ();
			m.add("Truncate the DBMS. (Caution: DELETES DATA!)");
			m.add("Generate Test Data");
			m.add("Add Employee Account");
				m.display  ("Please select an option:");
			break;
		}
		
		
		// ScreenUtil.clear ();
		// ScreenUtil.pause ();
		
		
	}
}
