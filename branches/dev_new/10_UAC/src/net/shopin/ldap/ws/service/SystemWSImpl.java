package net.shopin.ldap.ws.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;

import net.shopin.ldap.dao.DeptDao;
import net.shopin.ldap.dao.GroupDao;
import net.shopin.ldap.dao.RoleDao;
import net.shopin.ldap.dao.SystemDao;
import net.shopin.ldap.dao.UserDao;
import net.shopin.ldap.entity.Department;
import net.shopin.ldap.entity.Role;
import net.shopin.ldap.entity.System;
import net.shopin.ldap.entity.User;
import net.shopin.ldap.entity.UserGroup;
import net.shopin.util.PropertiesUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * jax-ws使用MTOM实现附件传送，所以这里为使用SOAPBinding.Style.RPC而非BaseWebService的默认SOAPBinding.Style.DOCUMENT方式
 * @author wchao
 *
 */
@javax.jws.WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@Component("sysService")
public class SystemWSImpl implements SystemWS{
	

	@Autowired
	UserDao userDao;
	
	@Autowired
	DeptDao deptDao;
	
	@Autowired
	GroupDao groupDao;
	
	@Autowired
	RoleDao roleDao;

	@Autowired
	SystemDao systemDao;
	
	@WebMethod
	public List<User> findUserList() {
		return userDao.findUserList("");
	}

	@WebMethod
	public List<User> findUserListByParam(String uidORName) {
		return userDao.findUserList(uidORName);
	}

	@WebMethod
	public User getUserDetailByUid(String uid) {
		return userDao.findByPrimaryKey(uid);
	}

	@WebMethod
	public List<Department> getDeptList() {
		List<Department> depts = deptDao.findDeptsByParam("");
		return depts;
	}

	@WebMethod
	public void updateUser(User user) {
		 userDao.update(user);
	}

	@WebMethod
	public List<User> findUserListByRoleCN(String roleCN) {
		return roleDao.listMembers("cn=" + roleCN + ",ou=roles");
	}

	@WebMethod
	public List<User> findUserListByGroupCN(String groupCN) {
		return groupDao.listMembers("cn=" + groupCN + ",ou=groups");
	}

	@WebMethod
	public List<Role> findRoleListBySystemCN(String systemCN) {
		return systemDao.listMembers("cn=" + systemCN + ",ou=systems");
	}

	@Override
	public List<UserGroup> findGroupsByUserId(String userId) {
		User user = userDao.findByPrimaryKey(userId);
		return groupDao.findGroupsByUserDN(user.getDn());
	}

	@Override
	public List<Role> findRolesByUserId(String userId) {
		User user = userDao.findByPrimaryKey(userId);
		return roleDao.findRolesByUserDN(user.getDn());
	}

	@Override
	public List<System> findSystemsByRoleCN(String roleCN) {
		String roleDN = "cn=" + roleCN + ",ou=roles"  + (StringUtils.isNotEmpty(PropertiesUtil.getProperties("base")) ? ',' + PropertiesUtil.getProperties("base") : "");
		return systemDao.findSystemsByRoleDN(roleDN);
	}
	
	
}
