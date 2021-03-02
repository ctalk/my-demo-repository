package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.dao.PostDAO;
import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.ui.DisplayUserRecord;

import java.util.ArrayList;
import java.util.Scanner;

public class Employee {

    public static void employeeMenu (User user) {
        Menu m = new Menu ();
        UserDAO dao = new UserDAO ();
        PostDAO pdao = new PostDAO ();
        @SuppressWarnings("resource")
        Scanner s = new Scanner (System.in);
        String ans = "";

        m.add("Approve an Account Application");
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
                            }
                        }
                    }
                    System.out.println ("");
                    break;
                case 2:
                    System.out.println ("\nExiting - goodbye.");
                    System.exit(AuthLevel.SUCCESS);
                    break;
                }
        }
    }
}
