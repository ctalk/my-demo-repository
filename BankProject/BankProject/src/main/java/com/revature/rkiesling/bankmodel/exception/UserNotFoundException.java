package com.revature.rkiesling.bankmodel.exception;

import java.lang.Exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {
	public UserNotFoundException (String msg) {
		super (msg);
	}
}
