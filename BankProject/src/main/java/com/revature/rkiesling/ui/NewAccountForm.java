package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.User;

import java.util.Scanner;

public class NewAccountForm {

	private static Scanner sc = new Scanner (System.in);

	public static User getUserInfoByForm (String title, Integer authlvl) {
		User user = new User ();
		System.out.println (title);
		user.auth(authlvl);
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
		System.out.println ("\nPlease enter the new account user name and password.");
		System.out.print("Login name: ");
		user.userName(sc.nextLine ());
		System.out.print("Password: ");
		user.passWord(sc.nextLine ());
		return user;
	}
}
