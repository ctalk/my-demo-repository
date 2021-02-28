package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.util.BankDBUtil;
import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.ui.NewAccountForm;
import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.dao.UserDAOImpl;
import com.revature.rkiesling.ui.ScreenUtil;

public class PostMenu implements Postable, AuthLevel {

	public static void tasks (User user) {
		
		UserDAOImpl ui = new UserDAOImpl ();
		
		while (true) {
			switch (user.auth ())
			{
			case AuthLevel.AUTH_ADMIN:
				Menu m = new Menu ();
				m.add("Truncate DBMS (CAUTION: Erases data)");
				m.add("Create test data");
				m.add("Create employee account");
				m.add("Exit");
				switch (m.display("Please select an option:"))
				{
				case 1:
					BankDBUtil.clearTables ();
					BankDBUtil.createAdminUser();
					break;
				case 2:
					break;
				case 3:
					User newUser = NewAccountForm.getUserInfoByForm(AuthLevel.AUTH_EMPLOYEE);
					ui.addUser (newUser);
					ScreenUtil.pause ();
					break;
				case 4:
					System.exit(AuthLevel.SUCCESS);
					break;
				}
				break;
			}
		}

	}
}
