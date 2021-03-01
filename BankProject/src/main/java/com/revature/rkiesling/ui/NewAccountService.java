package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.NewUser;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.dao.PostDAO;
import com.revature.rkiesling.bankmodel.exception.UserAlreadyExistsException;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.BalanceTable;

import java.util.Scanner;
import org.apache.log4j.Logger;
import java.sql.SQLException;


public class NewAccountService implements AuthLevel, BalanceTable {

        private static Scanner sc = new Scanner (System.in);
        private static Logger log = Logger.getLogger (NewAccountService.class);

        public static NewUser newUserForm (String title, Integer authlvl) throws UserAlreadyExistsException {
                NewUser user = new NewUser ();
                // User oldUser = new User ();
                UserDAO dao = new UserDAO ();
                System.out.println (title);
                user.auth(authlvl);
                
                System.out.println ("\nPlease enter the new account user name and password.");
                System.out.print("Login name: ");
                user.userName(sc.nextLine ());
                try {
                        @SuppressWarnings("unused")
                        User oldUser = dao.getLoginInfo(user.userName ());
                        throw new UserAlreadyExistsException ("The user name " + user.userName () + " already exists.");                        
                } catch (UserNotFoundException e) {
                        //
                }
                System.out.print("Password: ");
                user.passWord(sc.nextLine ());

                System.out.print("First name: ");
                user.firstName (sc.nextLine ());
                System.out.print("Last name: ");
                user.lastName(sc.nextLine ());
                System.out.print("Address: ");
                user.address(sc.nextLine ());
                System.out.print("Zip code: ");
                user.zipCode(sc.nextLine ());
                System.out.print("Comment (optional): ");
                user.comment(sc.nextLine ());
                return user;
        }
        
    public static NewUser createUser (String formTitle, Integer authlvl) {
        int retries = 0;
        UserDAO udao = new UserDAO ();
        PostDAO pdao = new PostDAO ();
        NewUser user = null;
        do {
            try {
                user = NewAccountService.newUserForm (formTitle, authlvl);
            } catch (UserAlreadyExistsException e) {
                System.out.print (e.getMessage () + " ");
                if (retries == AuthLevel.maxRetries) {
                    System.out.println ("Too many retries - exiting.");
                    log.info ("createUser: Too many retries - exiting");
                    System.exit(AuthLevel.SUCCESS);
                } else {
                    System.out.println ("Please retry.");
                }
            }
        } while ((user == null) && (++retries <= AuthLevel.maxRetries));
        
        try {
            udao.addUser (user);
            if (authlvl == AuthLevel.AUTH_GUEST) {
                @SuppressWarnings ("resource")
		    final Scanner sc = new Scanner (System.in);
		System.out.println ("Opening balance: ");
		user.balance(Double.parseDouble(sc.nextLine ()));
		pdao.addBalance (user, user.balance (),
				 BalanceTable.NEEDS_AUTH);
		pdao.postPendingAppl (user);
            }
        } catch (SQLException e) {
            // Don't print anything at the moment - there should only be
            // duplicate record exceptions, and the new user form has already
            // checked for them.
            log.info(e.getMessage ());
        }
        return user;
    }
}
