package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.ui.DisplayUserRecord;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.dao.PostDAO;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import java.util.Scanner;
import org.apache.log4j.Logger;

public class Customer {

    private static Logger log = Logger.getLogger(UserDAO.class);

    public static void customerMenu (User user) {
        Menu m = new Menu ();
        UserDAO dao = new UserDAO ();
        PostDAO pdao = new PostDAO ();
	//        @SuppressWarnings("resource")
	    // Scanner s = new Scanner (System.in);
	//        String ans = "";

        m.add("Review your account");
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
                    System.out.println ("\nExiting - goodbye.");
                    System.exit(AuthLevel.SUCCESS);
                    break;
                }
        }
    }

}
