package com.revature.services;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.dao.PostDAO;
import com.revature.dao.PostDAOImpl;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.exceptions.UserNotFoundException;
import com.revature.model.Post;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class PostService {
	
	// Here, too, PostDAOImpl instead of PostDAO
	public PostDAOImpl postDAO;
	public UserDAOImpl userDAO;
	
	public PostService () {
		this.postDAO = new PostDAOImpl ();
		this.userDAO = new UserDAOImpl ();
	}
	
	public Post createPost (String username, Post post) throws SQLException, UserNotFoundException {
		try (Connection con = ConnectionUtil.getConnection ()) {
			con.setAutoCommit(false); // we'll control it ourselves
			User user = userDAO.getUserByUsername (username, con);
			if (user == null) {
				throw new UserNotFoundException ("User " + username + " not found.");
			}
			Post postWithID = postDAO.insertPost (post, con, username);
			con.commit ();
			
			return postWithID;
		}
	}
	

}
