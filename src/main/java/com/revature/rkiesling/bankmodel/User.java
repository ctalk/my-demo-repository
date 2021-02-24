package com.revature.rkiesling.bankmodel;

public class User {
	
	private String userName = "";
	private String firstName = "";
	private String lastName = "";
	private Integer auth = 0;
	
	public User () {
		super ();
	}
	public User (String username, String firstname, String lastname, Integer authlvl) {
		super ();
		userName = username;
		firstName = firstname;
		lastName = lastname;
		auth = authlvl;
	}
	
	public String userName () {
		return this.userName;
	}
	public String firstName () {
		return this.firstName;
	}
	public String lastName () {
		return this.lastName;
	}
	public Integer auth () {
		return this.auth;
	}
}
