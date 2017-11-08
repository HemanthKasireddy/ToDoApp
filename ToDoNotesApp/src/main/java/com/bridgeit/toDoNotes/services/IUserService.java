package com.bridgeit.toDoNotes.services;

import com.bridgeit.toDoNotes.model.User;

public interface IUserService {
	public long insertUser(User user);
	public boolean updateUser(User user);
	public User getUser(String email, String password);
	public void deleteUser();
	public User getUserById(int id);
}
