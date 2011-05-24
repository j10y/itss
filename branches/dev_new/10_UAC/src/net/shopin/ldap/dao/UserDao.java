package net.shopin.ldap.dao;

import java.util.List;

import net.shopin.ldap.entity.Department;
import net.shopin.ldap.entity.User;

/**
 * 用户Dao接口
 * @author wchao
 *
 */
public interface UserDao {
	
	/**
	 * 新建用户<br>
	 * @param user
	 */
	void create(User user);
	
	/**
	 * 修改用户
	 * @param department
	 */
	void update(User user);
	
	/**
	 * 删除用户
	 * @param department
	 */
	void delete(User user);
	
	/**
	 * 
	 * @param uid
	 * @param userType
	 * @return
	 */
	User findByPrimaryKey(String uid, String userType);
	
	/**
	 * 
	 * @param uid
	 * @return
	 */
	User findByPrimaryKey(String uid);
	
	/**
	 * 从指定文件导入用户信息
	 * @param filePath
	 * @return 返回msg信息
	 */
	String importUsersFromFile(String filePath);
	
	/**
	 * 通过用户uid或者名称模糊查询用户列表
	 * @param uidORName
	 * @return
	 */
	public List<User> findUserList(String uidORName);
	
}
