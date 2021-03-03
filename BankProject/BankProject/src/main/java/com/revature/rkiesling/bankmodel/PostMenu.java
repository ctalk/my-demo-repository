package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.util.BankDBUtil;
import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.ui.NewAccountService;
// import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.ui.ScreenUtil;

import java.sql.SQLException;

public class PostMenu implements Postable, AuthLevel {

	public static void tasks (User user) {
		
		UserDAO ui = new UserDAO ();
		
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
					User newUser = NewAccountService.createUser ("Add employee:", AuthLevel.AUTH_EMPLOYEE);
					try {
						ui.addUser (newUser);
					} catch (SQLException e) {
						// 
					}
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
