package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.AuthLevel;
import com.revature.rkiesling.ui.ScreenUtil;

import java.util.Scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Login implements AuthLevel {
	
	private static String adminName = adminDefaultUsername;
	private static String adminPassword = adminDefaultPassword;
	
	private String userName = "";
	private String password = "";
	
	public static int getAdminCreds () {
		// getConnection prints the connection message
		

		// Returns SUCCESS if the method retrieves the admin name and 
		// password from the system's properties, or FAIL if it uses
		// the default name and password (defined in AuthLevel).
		
		String propPath = "";
	
		String homeDir = System.getenv("HOME");
		propPath = homeDir + "/" + propFilename;
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
				return FAIL;
			} else {
				return SUCCESS;
			}
				
		} catch (FileNotFoundException e) {
		} catch (IOException eClose) {
		}

		// We didn't find a properties file,			
		// so check the
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
	
	public void promptForLogin () {
		Scanner sc = new Scanner (System.in);
		System.out.println ("Enter your user name and password:");
		System.out.print ("User name: ");
		userName = sc.nextLine ();
		System.out.print ("Password: ");
		password = sc.nextLine ();
		
	}
	
	public void promptForLogin (String message) {
		Scanner sc = new Scanner (System.in);
		System.out.println ("");
		System.out.println (message);
		System.out.println ("Enter your user name and password:");
		System.out.print ("User name: ");
		userName = sc.nextLine ();
		System.out.print ("Password: ");
		password = sc.nextLine ();
		
	}
	
	public String userName () {
		return this.userName;
	}
	public String password () {
		return this.password;
	}
		

}
