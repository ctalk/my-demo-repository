package com.revature.dao;
import com.revature.model.User;
import java.util.List;
import com.revature.exceptions.UserNotFoundException;

public interface UserDAO {
	public List<User> getAllUsers ();
	public User getUserByUsername (String username) throws UserNotFoundException;
	public int insertUser (User user);

}
