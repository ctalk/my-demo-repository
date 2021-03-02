package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.dao.PostDAO;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.ui.DisplayUserRecord;
import com.revature.rkiesling.ui.ScreenUtil;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Scanner;

public class Employee implements AuthLevel, BalanceTable, Postable {

    private static Logger log = Logger.getLogger(UserDAO.class);

    public static void employeeMenu (User user) {
        Menu m = new Menu ();
        UserDAO dao = new UserDAO ();
        PostDAO pdao = new PostDAO ();
        @SuppressWarnings("resource")
        Scanner s = new Scanner (System.in);
        String ans = "";

        m.add("Approve an Account Application");
        m.add("View an account");
	m.add("View transactions");
        m.add("Exit");

        while (true) {
            switch (m.display("Please select an option:"))
                {
                case 1:
                    ArrayList<User> newUsers = dao.getUsers (AuthLevel.AUTH_GUEST);
                    System.out.println ("There are " + newUsers.size () + " new user accounts(s) pending.");
                    System.out.print ("Read now (Y/n)? ");
                    ans = s.nextLine ();
                    if (ans.equals("") || ans.equals("y") || ans.equals("Y")) {
                        for (User u: newUsers) {
                            pdao.getBalanceForUser (u);
                            DisplayUserRecord.printRec (u);
                            System.out.print ("(A)pprove or (S)kip:? ");
                            ans = s.nextLine ();
                            if (ans.equals("a") || ans.equals("A")) {
                                dao.update (u, AuthLevel.AUTH_CUSTOMER);
                                pdao.updateBalance (u, BalanceTable.BAL_AUTH);
                                pdao.postApprovedAppl (u);
                                // Set the application's posting in the transaction tale to 'COMPLETE'.
                                String sql = "update " + TransactionTable.transactionTableName +
                                    " set completed = " + Postable.COMPLETE +
                                    " where username = '" + u.userName + "' and ttype = " +
                                    Postable.POST_NEW_ACCOUNT_APPL;
                                pdao.postSQLUpdate (sql);
                                                    
                            }
                        }
                    }
                    System.out.println ("");
                    break;
                case 2:
                    System.out.print ("User name of account to view: ");
                    try {
                        User u = dao.getLoginInfo (s.nextLine ());
                        pdao.getBalanceForUser (u);
                        DisplayUserRecord.printRec (u);
                        
                    } catch (UserNotFoundException e) {
                        log.error ("Employee.employeeMenu (option 2) : " + e.getMessage ());
                    }
                    ScreenUtil.pause ();
                    break;
		case 3:
		    break;
                case 4:
                    System.out.println ("\nExiting - goodbye.");
                    System.exit(AuthLevel.SUCCESS);
                    break;
                }
        }
    }
}
