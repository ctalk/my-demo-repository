package com.revature.model;

public class Post {
	private int id;
	private String content;
	
	public Post (int id, String content) {
		super ();
		this.id = id;
		this.content = content;
	}
	public Post () {
		super ();
	}
	public int getId () {
		return id;
	}
	public void setId (int id) {
		this.id = id;
	}
	public String getContent () {
		return content;
	}
	public void setContent (String c) {
		this.content = c;
	}
	@Override
	public String toString () {
		return "Post [id=" + id + ", content=" + content + 
				"]";
	}
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime + result + ((content == null) ? 0 : content.hashCode ());
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals (Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass () != obj.getClass ())
			return false;
		Post other = (Post) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
				return false;
		if (id != other.id) 
			return false;
		return true;
	}
	
	
}
