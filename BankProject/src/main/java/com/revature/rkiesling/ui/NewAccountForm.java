package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.dao.UserDAOImpl;
import com.revature.rkiesling.bankmodel.exception.UserAlreadyExistsException;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.bankmodel.AuthLevel;

import java.util.Scanner;
import org.apache.log4j.Logger;

public class NewAccountForm implements AuthLevel {

	private static Scanner sc = new Scanner (System.in);
	private static Logger log = Logger.getLogger (NewAccountForm.class);

	public static User newUserForm (String title, Integer authlvl) throws UserAlreadyExistsException {
		User user = new User ();
		// User oldUser = new User ();
		UserDAOImpl dao = new UserDAOImpl ();
		System.out.println (title);
		user.auth(authlvl);
		
		System.out.println ("\nPlease enter the new account user name and password.");
		System.out.print("Login name: ");
		user.userName(sc.nextLine ());
		try {
			@SuppressWarnings("unused")
			User oldUser = dao.getLoginInfo(user.userName ());
			throw new UserAlreadyExistsException ("User " + user.userName () + " already exists.");			
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
	
	public static User createUser (String formTitle, Integer authlvl) {
		int retries = 0;
		UserDAOImpl dao = new UserDAOImpl ();
		User user = null;
		do {
			try {
				user = NewAccountForm.newUserForm (formTitle, authlvl);
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
		dao.addUser (user);
		return user;
	}
}
