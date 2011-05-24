package net.shopin.ldap.ws.service;

import java.util.Iterator;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;

import net.shopin.ldap.dao.DeptDao;
import net.shopin.ldap.dao.UserDao;
import net.shopin.ldap.entity.Department;
import net.shopin.ldap.entity.User;

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

	/**
	 * @see net.shopin.ldap.ws.service.SystemWS#findUserList()
	 */
	@WebMethod
	public List<User> findUserList() {
		// TODO Auto-generated method stub

		return userDao.findUserList("");
	}

	/**
	 * @see net.shopin.ldap.ws.service.SystemWS#findUserList(java.lang.String)
	 */
	@WebMethod
	public List<User> findUserListByParam(String uidORName) {
		// TODO Auto-generated method stub
		return userDao.findUserList(uidORName);
	}

	/**
	 * @see net.shopin.ldap.ws.service.SystemWS#getUserDetailByUid(java.lang.String)
	 */
	@WebMethod
	public User getUserDetailByUid(String uid) {
		// TODO Auto-generated method stub
		return userDao.findByPrimaryKey(uid);
	}

	/**
	 * @see net.shopin.ldap.ws.service.SystemWS#getDeptList(java.lang.String)
	 */
	@WebMethod
	public List<Department> getDeptList() {
		// TODO Auto-generated method stub
		List<Department> depts = deptDao.findDeptsByParam("");
		Iterator<Department> iterator = depts.iterator();
		while(iterator.hasNext()){
			Department dept = iterator.next();
			if(dept.getDeptNo()!=null&&dept.getDeptNo().length()>4)
			dept.setParentNo(dept.getDeptNo().substring(0,dept.getDeptNo().length()-2));
		}
		return depts;
	}

}
