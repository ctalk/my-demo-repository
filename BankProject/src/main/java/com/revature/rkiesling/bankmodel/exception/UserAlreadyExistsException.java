package com.revature.rkiesling.bankmodel.exception;

import java.lang.Exception;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException (String msg) {
                super (msg);
        }

}
