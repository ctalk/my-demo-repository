package com.revature.model;

public class User {
	private String username;
	private String password;
	
	public User(String username, String password) {
		super ();
		this.username = username;
		this.password = password;
	}
	
	public User () {
		super ();
	}
	
	public String getUsername () {
		return username;
	}
	
	public void setUsername (String username) {
		this.username = username;
	}
	
	public String getPassword () {
		return password;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}
	
	@Override
	public int hashCode () {
		final int prime = 32;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode ());
		result = prime * result + ((username == null) ? 0 : username.hashCode ());
		return result;
	}
	
	@Override 
	public boolean equals (Object obj) {
		if (this == obj)
			return true;
		else
			return false;
	}
	
	@Override
	public String toString () {
		return "User [username=" + username + ", password=" + password + "]";
	}
}
