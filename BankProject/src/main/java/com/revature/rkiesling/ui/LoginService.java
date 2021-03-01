package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.bankmodel.User;
import com.revature.rkiesling.bankmodel.NewUser;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;
import com.revature.rkiesling.bankmodel.exception.UserAlreadyExistsException;
import com.revature.rkiesling.bankmodel.exception.NewUserException;

import org.apache.log4j.Logger;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class LoginService implements AuthLevel {
	
	// These are typically run with the DBMS login (in environment variables) as:
	//  - db_user=postgres
	//  - db_password=postgres
	//  - db_url=jdbc:postgresql://localhost:5433/postgres
	
	private static String adminName = adminDefaultUsername;
	private static String adminPassword = adminDefaultPassword;
    static Logger log = Logger.getLogger (LoginService.class);
	
	private String userName;
	private String userPassword;
	
	public static int getAdminCreds () {
	
		// Returns SUCCESS if the method retrieves the admin name and 
		// password from the system's properties, or FAIL if it uses
		// the default name and password (defined in AuthLevel).
		
		String homeDir = System.getenv("HOME");
		String propPath = homeDir + "/" + propFilename;
		try {
			FileInputStream in = new FileInputStream(propPath);
			Properties adminProps = new Properties ();

			try {
				adminProps.load(in);
				adminName = adminProps.getProperty (adminUsernameProp, adminDefaultUsername);
				adminPassword = adminProps.getProperty(adminPasswordProp, adminDefaultPassword);
			} catch (IOException e) {
			} finally {
				in.close ();
			}
			if (adminName == adminDefaultUsername || adminPassword == adminDefaultPassword) {
				return AuthLevel.FAIL;
			} else {
				return AuthLevel.SUCCESS;
			}
				
		} catch (FileNotFoundException e) {
		} catch (IOException eClose) {
		}

		// We didn't find a properties file, so check the
		// environment.
		String adminEnvName = "";
		String adminEnvPasswd = "";
		if (((adminEnvName = System.getenv (adminUsernameProp)) != null)
				&& ((adminEnvPasswd = System.getenv (adminPasswordProp)) != null)) {
			adminName = adminEnvName;
			adminPassword = adminEnvPasswd;
			return SUCCESS;
		} 
		return FAIL;
	}
	
	public static String adminName () {
		return adminName;
	}
	public static String adminPassword () {
		return adminPassword;
	}
		
	@SuppressWarnings("resource")
	// We can't close System.in.
	public void getLoginInfoFromForm (String title)
	    throws NewUserException {
		Scanner s = new Scanner (System.in);
		System.out.println (title);
		System.out.print("User name: ");
		this.userName = s.nextLine ();
		if (this.userName().equals("new")) {
		    throw new NewUserException ("new user login");
		}
		System.out.print("Password: ");
		this.userPassword = s.nextLine ();		
	}
	
	public void userName (String name) {
		this.userName = name;
	}
	
	public String userName () {
		return this.userName;
	}
	
	public void userPassword (String password) {
		this.userPassword = password;
	}
	
	public String userPassword () {
		return this.userPassword;
	}
	
	public User getUserLogin () throws UserNotFoundException {
		
		User user = null;
		NewUser newUser = null;
		UserDAO u = new UserDAO ();
		
		try {
			this.getLoginInfoFromForm ("");
			user = u.getLoginInfo (this.userName (),
					       this.userPassword ());
		} catch (UserNotFoundException e) {
			throw e;
		} catch (NewUserException e) {
		    newUser =
			NewAccountService.createUser
			("Please enter the following information:",
			 AuthLevel.AUTH_GUEST);
		    log.info ("New user: " + newUser.userName ());
		    return newUser;
		}
		return user;
	}
}

