package com.revature.rkiesling.bankmodel;

public interface AuthLevel {

	public static final int AUTH_NONE = 0;
	public static final int AUTH_GUEST = 1;
	public static final int AUTH_CUSTOMER = 2;
	public static final int AUTH_EMPLOYEE = 3;
	public static final int AUTH_ADMIN = 4;
	
	// some return constants, also declared in Postable interface.
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	
	// The default admin name and password, in case they're not present in:
	// 
	//   The properties, "bank_admin_id," *and*, "bank_admin_pwd," or,
	//   1.) The properties file, "$HOME/.bank_properties," or,
	//   2.) As environment variables with the property names.
	//
	//   The program works without setting the admin and password,
	//   so if they're not present, the method just handles any
	//   exceptions silently.
	//
	public static final String propFilename = ".bank_properties";
	
	public static final String adminDefaultUsername = "admin";
	public static final String adminDefaultPassword = "admin";
	
	public static final String adminUsernameProp = "admin_user_id";
	public static final String adminPasswordProp = "admin_user_pwd";
	
	// The number of tries a user has to log in (0... n - 1).
	public static final Integer maxRetries = 2;
}
