package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.util.BankDBUtil;
import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.ui.NewAccountForm;
import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.dao.UserDAOImpl;
import com.revature.rkiesling.ui.ScreenUtil;

public class BankTasks implements Postable, AuthLevel {

	public static void performTasks (User user) {
		
		UserDAOImpl dao = new UserDAOImpl ();
		
		
		switch (user.auth ())
		{
		case AuthLevel.AUTH_ADMIN:
			while (true) {
				Menu m = new Menu ();
				m.add("Truncate DBMS (CAUTION: Erases data)");
				m.add("Create test data");
				m.add("Create employee account");
				m.add("Create admin account");
				m.add("Exit");
				switch (m.display("Please select an option:"))
				{
				case 1:
					BankDBUtil.clearTables ();
					BankDBUtil.createAdminUser();
					break;
				case 2:
					BankDBUtil.makeTestUserData ();
					break;
				case 3:
					@SuppressWarnings("unused")
					User newUser = NewAccountForm.createUser("\nPlease enter the new employee information.", AuthLevel.AUTH_EMPLOYEE);
					break;
				case 4:
					User newAdminUser = NewAccountForm.createUser("\nPlease enter the new admin information.", AuthLevel.AUTH_ADMIN);
					dao.addUser (newAdminUser);
					break;
				case 5:
					System.out.println ("\nExiting.  For more information about this app, please visit:\nhttps://github.com/ctalk/my-demo-repository/.");
					System.exit(AuthLevel.SUCCESS);
					break;
				}
			}
			// break;   // notreached
		case AuthLevel.AUTH_EMPLOYEE:
			break;
			
		}
		

	}
}
