package com.revature.rkiesling.bankmodel.exception;

import java.lang.Exception;

@SuppressWarnings("serial")
public class NewUserException extends Exception {
	public NewUserException (String msg) {
		super (msg);
	}
}
