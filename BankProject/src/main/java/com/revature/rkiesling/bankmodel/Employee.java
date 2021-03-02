package com.revature.rkiesling.bankmodel;

import com.revature.rkiesling.ui.Menu;

public class Employee {

    public void employeeMenu (User user) {
	Menu m = new Menu ();
	m.add("Approve an Account Application");
	m.add("Exit");
	while (true) {
	    switch (m.display("Please select an option:"))
		{
		case 1:
		    break;
		case 2:
		    System.out.println ("\nExiting - goodbye.");
		    System.exit(AuthLevel.SUCCESS);
		    break;
		}
	}
    }
}
