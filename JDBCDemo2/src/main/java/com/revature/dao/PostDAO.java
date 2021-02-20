package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.model.Post;

public interface PostDAO {
	
	public List<Post> getPostsByUsername (String username, Connection c) throws SQLException;
	public Post insertPost (Post p, Connection c, String u) throws SQLException;
	

}
