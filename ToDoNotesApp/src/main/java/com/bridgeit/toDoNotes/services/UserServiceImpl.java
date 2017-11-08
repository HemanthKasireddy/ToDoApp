package com.bridgeit.toDoNotes.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgeit.toDoNotes.DAO.UserDAO;
import com.bridgeit.toDoNotes.model.User;
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserDAO userDAO;
	
	public long insertUser(User user) {
		long id=userDAO.createUser(user);
		System.out.println("inside service");
		if(id>0) {
			
			return id;	
		
		} else {
			
			return 0;
		}
	}

	@Override
	public boolean updateUser(User user) {
		if(userDAO.updateUser(user)>0) {
			
			return true;
		
		} else {
			
			return false;
		
		}

	}

	@Override
	public User getUser(String email, String password) {
		User user=userDAO.getUser(email,password);
		if(user!=null) {
		
			return user;
		
		} else {
			
			return null;
		
		}

	}

	@Override
	public void deleteUser() {
		

	}

	@Override
	public User getUserById(int id) {
		User user=userDAO.getUserById(id);
		return user;
	}

}
