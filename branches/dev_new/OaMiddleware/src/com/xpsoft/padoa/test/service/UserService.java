package com.xpsoft.padoa.test.service;

import java.util.List;

import com.xpsoft.padoa.test.entity.User;

/**
 * 
 * @Class Name UserService
 * @Author likang
 * @Create In Jul 20, 2010
 */
public interface UserService {
	public void addUser(User user);
	public List findUser();
	public void removeUser(User user);
	public User findSingleUser(User user);
	public void modifyUser(User user);
}
