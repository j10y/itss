package com.xpsoft.padoa.test.dao.impl;

import java.util.List;

import com.xpsoft.padoa.test.dao.UserDao;
import com.xpsoft.padoa.test.entity.User;
import com.xpsoft.framework.dao.BaseDao;

public class UserDaoImpl extends BaseDao implements UserDao {

	public void insertUser(User user) {
		getSqlMapClientTemplate().insert("User.insertUser", user);
		
	}

	public List selectUser() {

		try {
			List list = (List) getSqlMapClientTemplate().queryForList(
					"User.selectUser");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		try {
			getSqlMapClientTemplate().delete("User.deleteUser", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User selectSingleUser(User user) {
		try {
			User userTest = (User) getSqlMapClientTemplate().queryForObject(
					"User.selectSingleUser", user);
			return userTest;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		try {
			getSqlMapClientTemplate().update("User.updateUser", user);
			System.out.println("update success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
