package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.ui.NumericInput;
import com.revature.rkiesling.ui.DisplayUserRecord;
import com.revature.rkiesling.ui.ScreenUtil;
import com.revature.rkiesling.ui.NewAccountService;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.dao.PostDAO;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.bankmodel.exception.UserAlreadyExistsException;

import org.apache.log4j.Logger;
import java.sql.SQLException;

public class Customer implements Postable {

    private static Logger log = Logger.getLogger(UserDAO.class);

    private static void daoBalance (User u, Integer ttype) {
        PostDAO pdao = new PostDAO ();
        pdao.updateBalance (u, Postable.COMPLETE);
        pdao.postBalanceOp (u, ttype);
    }

    public static void customerWithdraw (User u) {
        PostDAO pdao = new PostDAO ();
        pdao.getBalanceForUser (u);
        double d = NumericInput.getDouble ("Enter the withdrawal amount: ");
        if (d > u.balance ()) {
            System.out.println ("There are not funds in this account.");
            ScreenUtil.pause ();
        } else if (d < 0.0) {
            System.out.println ("You can't withdraw a negative amount.");
            ScreenUtil.pause ();
        } else {
            double d1 = u.balance () - d;
            u.balance (d1);
            daoBalance (u, Postable.POST_WITHDRAWAL);
        }
    }

    public static void customerDeposit (User u) {
        PostDAO pdao = new PostDAO ();
        pdao.getBalanceForUser (u);
        double d = NumericInput.getDouble ("Enter the deposit amount: ");
        if (d < 0.0) {
            System.out.println ("You can't deposit a negative amount.");
            ScreenUtil.pause ();
        } else {
            double d1 = u.balance () + d;
            u.balance (d1);
            daoBalance (u, Postable.POST_DEPOSIT);
        }
    }

    public static void customerMenu (User user) {
        Menu m = new Menu ();
        UserDAO dao = new UserDAO ();
        PostDAO pdao = new PostDAO ();

        m.add("View your account");
        m.add("Withdraw money");
        m.add("Deposit money");
        m.add("Add another account");
        m.add("Exit");

        while (true) {
            switch (m.display("Please select an option:"))
                {
                case 1:
                    System.out.println ("Your Account:");
                    System.out.println ("---- -------");
                    try {
                        User nuser = dao.getLoginInfo (user.userName ());
                        pdao.getBalanceForUser (nuser);
                        DisplayUserRecord.printRec(nuser);
                        System.out.println ("");
                    } catch (UserNotFoundException e) {
                        log.error ("newUserMenu: " + e.getMessage ());
                    }
                    break;
                case 2:
                    customerWithdraw (user);
                    break;
                case 3:
                    customerDeposit (user);
                    break;
                case 4:
                    User newUser = null;
                    int retries = 0;
                    do {
                        try {
                            newUser = NewAccountService.newUserForm ("Please enter the new account information.", AuthLevel.AUTH_GUEST);
                        } catch (UserAlreadyExistsException e) {
                            if (retries == AuthLevel.maxRetries) {
                                System.out.println ("Login failed - too many retries.  Goodbye.");
                                log.info("User login unsuccessful - exiting.");
                                System.exit(AuthLevel.SUCCESS);                                 
                            } else {
                                System.out.println ("User not found. Please re-enter.");
                            }
                        }
                    } while ((newUser == null) && (++retries <= AuthLevel.maxRetries));

		    try {
			dao.addUser (newUser);
			//			if (authlvl == AuthLevel.AUTH_GUEST) {
			    newUser.balance(NumericInput.getDouble ("Opening balance: "));
			    pdao.addBalance (newUser, newUser.balance (),
					     BalanceTable.NEEDS_AUTH);
			    pdao.postPendingAppl (newUser);
			    //}
		    } catch (SQLException e) {
			// Don't print anything at the moment - there should only be
			// duplicate record exceptions, and the new user form has already
			// checked for them.
			log.info("Customer.customerMenu (): " + e.getMessage ());
		    }
		    
                    break;
                case 5:
                    System.out.println ("\nExiting - goodbye.");
                    System.exit(AuthLevel.SUCCESS);
                    break;
                }
        }
    }

}
