package com.revature.main;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.User;

public class Application {
	
//  This just tests if we can make a connection to the DBMS.
//	public static void main (String[] args) {
//		try {
//			Connection con = ConnectionUtil.getConnection ();
//			System.out.println (con);
//			con.close ();
//			
//		} catch (SQLException e) {
//			e.printStackTrace ();
//		}
//	}
	
	public static void main (String[] args) {
		UserDAO dao = new UserDAOImpl (); 
		System.out.println (dao.getAllUsers ());
		System.out.println ("done");
//	
//	try {
//		System.out.println (dao.getUserByUsername ("abc1234"));
//	} catch (UserNotFoundException e) {
//		System.out.println (e.getMessage ());
//	}
	
		//System.out.println (dao.getAllUsers ());
	}

}
