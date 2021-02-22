package com.revature.rkiesling.ui;

import com.revature.rkiesling.bankmodel.AuthLevel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Login implements AuthLevel {
	
	private static String adminName = adminDefaultUsername;
	private static String adminPassword = adminDefaultPassword;
	
	public static int getAdminCreds () {
	
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
		

}
