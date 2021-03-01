package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.ui.Menu;
import com.revature.rkiesling.ui.DisplayUserRecord;
import com.revature.rkiesling.bankmodel.dao.UserDAO;
import com.revature.rkiesling.bankmodel.exception.UserNotFoundException;

import org.apache.log4j.Logger;

public class NewUser extends User {

    private static Logger log = Logger.getLogger(UserDAO.class);

    public NewUser () {
	super ();
    }

    public NewUser (String username, String firstname, String lastname, Integer authlvl, 
			String zipcode, String address, String comment) {
		super ();
		userName = username;
		firstName = firstname;
		lastName = lastname;
		auth = authlvl;
		this.zipCode = Integer.parseInt (zipcode);
		this.address = address;
		this.comment = comment;
	}


    public void newUserMenu (NewUser user) {
	Menu m = new Menu ();
	UserDAO dao = new UserDAO ();
	m.add("View your account information");
	m.add("Exit");
	while (true) {
	    switch (m.display("Please select an option:"))
		{
		case 1:
		    System.out.println ("Your Account:");
		    System.out.println ("---- -------");
		    try {
		    	User nuser = dao.getLoginInfo (user.userName ());
		    	DisplayUserRecord.printRec(nuser);
			System.out.println ("");
		    } catch (UserNotFoundException e) {
			log.error ("newUserMenu: " + e.getMessage ());
		    }
		    break;
		case 2:
		    System.out.println ("\nExiting - goodbye.");
		    System.exit(AuthLevel.SUCCESS);
		    break;
		}
	}
    }

    
}
