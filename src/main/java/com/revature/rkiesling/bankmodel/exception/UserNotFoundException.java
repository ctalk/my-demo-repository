package com.revature.rkiesling.bankmodel.exception;

import java.lang.Exception;

public class UserNotFoundException extends Exception {
	
	public UserNotFoundException (String msg) {
		super (msg);
	}
}
