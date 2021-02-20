package com.revature.model;

import java.util.List;

public class User {
	private String username;
	private String firstName;
	private String lastName;
	
	private List<Post> posts;
	
	public User () {
		super ();
	}
	
	public User (String u, String f, String l) {
		super ();
		this.username = u;
		this.firstName = f;
		this.lastName = l;
	}
	
	public String getUserName () {
		return this.username;
	}
	
	public void setUserNsame (String u) {
		this.username = u;
	}
	
	public String getFirstName () {
		return this.firstName;
	}
	
	public void setFirstName (String f) {
		this.firstName = f;
	}
	
	public String getLastName () {
		return this.lastName;
	}
	
	public void setLastName (String l) {
		this.lastName = l;
	}
	
	public List<Post> getPosts () {
		return posts;
	}
	
	public void setPosts (List<Post> l) {
		this.posts = l;
	}
	
	@Override
	public String toString () {
		return "User [username=" + username + ", firstName=" +
				", lastName =" + lastName + "]";
	}
	
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode ());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((posts == null) ? 0 : posts.hashCode ());
		result = prime * result + ((username == null) ? 0 : username.hashCode ());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (posts == null) {
			if (other.posts != null)
				return false;
		} else if (!posts.equals(other.posts))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	}
		
