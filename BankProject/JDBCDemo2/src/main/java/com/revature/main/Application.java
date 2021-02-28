package com.revature.main;

import com.revature.ui.MainMenu;
import com.revature.ui.Menu;
import com.revature.util.ConnectionUtil;

public class Application {
	
	public static void main (String[] args) {
		Menu mainMenu = new MainMenu ();
		mainMenu.display ();
		Menu.sc.close ();
		System.out.println ("Application closing!");
	}

}
