package com.xpsoft.padoa.test.dao;

import java.util.List;

import com.xpsoft.padoa.test.entity.User;

public interface UserDao {
	public void insertUser(User user);
	public List selectUser();
	public void deleteUser(User user);
	public User selectSingleUser(User user);
	public void updateUser(User user);
}
