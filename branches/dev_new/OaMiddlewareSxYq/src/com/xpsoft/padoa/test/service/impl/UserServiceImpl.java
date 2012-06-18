package com.xpsoft.padoa.test.service.impl;

import java.util.List;

import com.xpsoft.padoa.test.dao.UserDao;
import com.xpsoft.padoa.test.entity.User;
import com.xpsoft.padoa.test.service.UserService;


/**
 * 
 * @Class Name UserServiceImpl
 * @Author likang
 * @Create In Jul 20, 2010
 */
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void addUser(User user) {
		// TODO Auto-generated method stub
		userDao.insertUser(user);
	}

	
	public User findSingleUser(User user) {
		// TODO Auto-generated method stub
		return userDao.selectSingleUser(user);
	}

	
	public List findUser() {
		// TODO Auto-generated method stub
		return userDao.selectUser();
	}

	
	public void modifyUser(User user) {
		// TODO Auto-generated method stub
		userDao.updateUser(user);
	}

	
	public void removeUser(User user) {
		// TODO Auto-generated method stub
		userDao.deleteUser(user);
	}
	
}
