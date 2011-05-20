package com.zsgj.info.framework.security.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.zsgj.info.appframework.menu.entity.UserExtraMenuItem;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.dao.SecurityManageDao;
import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Resource;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.SecurityManageService;

public class SecurityManageServiceImpl extends BaseDao implements SecurityManageService {

	private SecurityManageDao securityManageDao;

	
	public List findRoleByDept(Department dept) {
		Criteria c = super.getCriteria(Role.class);
		c.add(Restrictions.eq("department", dept));
		List list = c.list();
		return list;
	}

	public void setSecurityManageDao(SecurityManageDao securityManageDao) {
		this.securityManageDao = securityManageDao;
	}

	public List findModuleTopLevel() {
		List list = null;
		try {
			list = securityManageDao.selectModulesHasNoChild();
		} catch (DataAccessException e) {
			throw new ServiceException("dd");
		}		
		return list;
	}
	
	public List findModuleTopLevelForPage(){
		List results = null;
		List<Object> returnList = new ArrayList<Object>();
		results = securityManageDao.selectModulesHasNoChild();
		
		if(results != null && results.size() != 0){
			for(int i=0;i<results.size();i++){
				HashMap<String,Object> hm = new HashMap<String,Object> ();
				Module item = (Module)results.get(i);
				hm.put("id", item.getId() !=null ? item.getId():0);
				hm.put("name", item.getName()!= null ? item.getName():"");
				
				returnList.add(hm);
			}
		}
		
		return returnList;
	}
	
	public List findModuleByIdForPage(String moduleId){
		List<Object> returnList = new ArrayList<Object>();
		Module result = null;
		HashMap<String,Object> hm = new HashMap<String,Object> ();
		result = securityManageDao.selectModuleById(Long.valueOf(moduleId));
		hm.put("id", result.getId() !=null ? result.getId():0);
		hm.put("name", result.getName()!= null ? result.getName():"");
		hm.put("serviceKeyName", result.getServiceKeyName()!= null ? result.getServiceKeyName():"");
		hm.put("url", result.getUrl()!= null ? result.getUrl():"");
		hm.put("descn", result.getDescn()!= null ? result.getDescn():"");
		if(result.getParentModule()!=null){
//			hm.put("pModuleId", result.getParentModule().getId()!= null ? result.getParentModule().getId():"");
			hm.put("pModuleName", result.getParentModule().getName()!= null ? result.getParentModule().getName():"");
		}
		returnList.add(hm);
		
		return returnList;
	}
	
	public void removeModule(String moduleId) {
		securityManageDao.deleteModuleById(Long.valueOf(moduleId));
	}

	public Module saveModule(Module module) {
		Module result = null;
		result = securityManageDao.insertOrUpdateModule(module);
		return result;
	}

	public void removeResource(String resourceId) {
		securityManageDao.deleteResourceById(Long.valueOf(resourceId));
	}

	public Resource saveResource(Resource res) {
		Resource result = null;
		result = securityManageDao.insertOrUpdateResource(res);
		return result;
}

	public Module findModuleById(String moduleId) {
		Module result = null;
		result = securityManageDao.selectModuleById(Long.valueOf(moduleId));
		return result;
	}
	
	public List findModulesAllForPage(){
		List results = securityManageDao.selectModulesAll();
		if(results != null && results.size() != 0){
			List<Object> returnlist = new ArrayList<Object>();
			for(int i=0;i<results.size();i++){
				HashMap<String,Object> hm = new HashMap<String,Object> ();
				Module item = (Module)results.get(i);
				hm.put("id", item.getId() !=null ? item.getId():0);
				hm.put("name", item.getName()!= null ? item.getName():"");
				hm.put("serviceKeyName", item.getServiceKeyName() != null ?item.getServiceKeyName():"");
				hm.put("url", item.getUrl() !=null ? item.getUrl():"");
				hm.put("descn", item.getDescn() !=null ? item.getDescn():"");
				if(item.getParentModule() != null){
					hm.put("pModuleName", item.getParentModule().getName() !=null ? item.getParentModule().getName():"" );
					hm.put("pModuleId", item.getParentModule().getId() !=null ? item.getParentModule().getId():"" );
				}
				returnlist.add(hm);
			}
			return returnlist;
		}
		return results;
	}
	
	public List findModulesAll() {
		List results = null;
		results = securityManageDao.selectModulesAll();
		return results;
	}

	public Resource findResourceById(String resourceId) {
		Resource result = null;
		result = securityManageDao.selectResourceById(Long.valueOf(resourceId));
		return result;
	}
	
	public List findResourceByIdForPage(String resourceId) {
		List<Object> list = new ArrayList<Object>();
		Resource result = null;
		HashMap<String,Object> hm = new HashMap<String,Object>();
		result = securityManageDao.selectResourceById(Long.valueOf(resourceId));
		
		hm.put("rid", result.getId() != null ? result.getId() : 0);
		hm.put("rname",result.getName() != null ? result.getName() : "");
		hm.put("className",result.getClassName() != null ? result.getClassName() : "");
		hm.put("methodName", result.getMethodName() != null ? result.getMethodName() : "");
		hm.put("type",result.getType() != null ? result.getType() : "");
		hm.put("rdescn",result.getDescn() != null ? result.getDescn() : "");
		
		if(result.getModule() != null){
			hm.put("mid",result.getModule().getId() != null ? result.getModule().getId() : 0);
			hm.put("mname",result.getModule().getName() != null ? result.getModule().getName() : "");
			hm.put("serviceKeyName",result.getModule().getServiceKeyName() != null ? result.getModule().getServiceKeyName() : "");
			hm.put("url",result.getModule().getUrl() != null ? result.getModule().getUrl() : "");
			hm.put("mdescn",result.getModule().getDescn() != null ? result.getModule().getDescn() : "");
			
			if(result.getModule().getParentModule() != null){
				hm.put("mpid",result.getModule().getParentModule().getId() != null ? result.getModule().getParentModule().getId() : 0);
				hm.put("mpname",result.getModule().getParentModule().getName() != null ? result.getModule().getParentModule().getName() :"");
				hm.put("mpserviceKeyName",result.getModule().getParentModule().getServiceKeyName() != null ? result.getModule().getParentModule().getServiceKeyName() : "");
				hm.put("url",result.getModule().getParentModule().getUrl() != null ? result.getModule().getParentModule().getUrl() : "");
				hm.put("mdescn",result.getModule().getParentModule().getDescn() != null ? result.getModule().getParentModule().getDescn() : "");
			}
		}
		list.add(hm);
		return list;
	}
	
	public List findResourcesAllForPage() {
		List<Object> results = null;
		List<Object> returnList = null;
		
		results = securityManageDao.selectResourcesAll();
		if(results != null && results.size()!=0){
			returnList = new ArrayList<Object>();
			
			for(int i=0; i<results.size(); i++){
				HashMap<String,Object> hm = new HashMap<String, Object>();
				Resource item = (Resource)results.get(i);
				
				hm.put("id", item.getId() != null ? item.getId() : 0);
				hm.put("name",item.getName() != null ? item.getName() : "");
				hm.put("className",item.getClassName() != null ? item.getClassName() : "");
				hm.put("methodName", item.getMethodName() != null ? item.getMethodName() : "");
				hm.put("type",item.getType() != null ? item.getType() : "");
				hm.put("descn",item.getDescn() != null ? item.getDescn() : "");
				
				if(item.getModule() != null){
					hm.put("mid",item.getModule().getId() != null ? item.getModule().getId() : 0);
					hm.put("mname",item.getModule().getName() != null ? item.getModule().getName() : "");
					hm.put("serviceKeyName",item.getModule().getServiceKeyName() != null ? item.getModule().getServiceKeyName() : "");
					hm.put("url",item.getModule().getUrl() != null ? item.getModule().getUrl() : "");
					hm.put("mdescn",item.getModule().getDescn() != null ? item.getModule().getDescn() : "");
					
					if(item.getModule().getParentModule() != null){
						hm.put("pmid",item.getModule().getParentModule().getId() != null ? item.getModule().getParentModule().getId() : 0);
						hm.put("pmname",item.getModule().getParentModule().getName() != null ? item.getModule().getParentModule().getName() :"");
						hm.put("pmserviceKeyName",item.getModule().getParentModule().getServiceKeyName() != null ? item.getModule().getParentModule().getServiceKeyName() : "");
						hm.put("purl",item.getModule().getParentModule().getUrl() != null ? item.getModule().getParentModule().getUrl() : "");
						hm.put("pmdescn",item.getModule().getParentModule().getDescn() != null ? item.getModule().getParentModule().getDescn() : "");
					}
				}
				returnList.add(hm);
			}
			
			return returnList;
		}
		
		return results;
	}
	
	public List findResourcesAll() {
		List<Object> results = null;
		results = securityManageDao.selectResourcesAll();
		return results;
	}

	public void addRoleToUser(UserInfo userInfo, Role role) {
		userInfo.getRoles().add(role);
		securityManageDao.saveUserInfoWithRoles(userInfo);
		
	}

	public Authorization findAuthorizationById(String authId) {
		Authorization result = null;
		result = securityManageDao.selectAuthorizById(Long.valueOf(authId));
		return result;
	}
	
	public List findAuthorizationByIdForPage(String authId) {
		Authorization result = null;
		List<Object> returnList = new ArrayList<Object>();
		result = securityManageDao.selectAuthorizById(Long.valueOf(authId));
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("id", result.getId() != null ? result.getId() : 0);
		hm.put("name",result.getName() != null ? result.getName() : "");
		//right:keyName,rightId
		if(result.getRight()!=null){
			hm.put("keyName",result.getRight().getKeyName() != null ? result.getRight().getKeyName() : "");
			hm.put("rightId",result.getRight().getId() != null ? result.getRight().getId(): 0);
		}
		//resource:resourceName,resourceId
		if(result.getResource()!=null){
			hm.put("rname", result.getResource().getName() != null ? result.getResource().getName() : "");
			hm.put("rid", result.getResource().getId() != null ? result.getResource().getId() : 0);
		}
		returnList.add(hm);
		
		return returnList;
	}
	
	public List findAuthorizationsAll() {
		List results = null;
		results = securityManageDao.selectAuthorizationsAll();
		return results;
	}
	
	public List<Object> findAuthorizationsAllForPage(){
		List<Object> results = null;
		List<Object> returnList = null;
		
		results = securityManageDao.selectAuthorizationsAll();
		if(results != null && results.size()!=0){
			returnList = new ArrayList<Object>();
			
			for(int i=0; i<results.size(); i++){
				HashMap<String,Object> hm = new HashMap<String, Object>();
				Authorization item = (Authorization)results.get(i);
				
				hm.put("id", item.getId() != null ? item.getId() : 0);
				hm.put("name",item.getName() != null ? item.getName() : "");
				hm.put("checked",item.isChecked() !=false ?item.isChecked(): false);
				//权限(授权管理时使用数据)
				if(item.getRight()!= null){
					hm.put("rightId",item.getRight().getId() != null ? item.getRight().getId() : 0);
					hm.put("rightName",item.getRight().getName() != null ? item.getRight().getName() : "");
					hm.put("keyName",item.getRight().getKeyName() != null ? item.getRight().getKeyName() : "");
					hm.put("rdescn",item.getRight().getDescn() != null ? item.getRight().getDescn() : "");
				}
				//资源(授权管理时使用的数据)
				if(item.getResource()!= null){
						hm.put("resourceId",item.getResource().getId() != null ? item.getResource().getId() : 0);
						hm.put("resourceName",item.getResource().getName() != null ? item.getResource().getName() :"");
						hm.put("type",item.getResource().getType()!= null ? item.getResource().getType() : "");
						hm.put("className",item.getResource().getClassName() != null ? item.getResource().getClassName() : "");
						hm.put("methodName",item.getResource().getMethodName() != null ? item.getResource().getMethodName() : "");
//						hm.put("shortClassMethodName",item.getResource().getShortClassMethodName() != null ? item.getResource().getShortClassMethodName() : "");
				}
				//模块(角色管理时新建角色时使用的数据)
				if(item.getModule() != null){
					hm.put("moduleId", item.getModule().getId() != null ? item.getModule().getId() : 0);
					hm.put("moduleName", item.getModule().getName() != null ? item.getModule().getName() : "");
					hm.put("serviceKeyName", item.getModule().getServiceKeyName() != null ? item.getModule().getServiceKeyName() : "");
					hm.put("url", item.getModule().getUrl()!= null ? item.getModule().getUrl() : "");
				}
				returnList.add(hm);
			}
		}
		return returnList;
	}
	
	public Right findRightById(String rightId) {
		Right result = null;
		result = securityManageDao.selectRightById(Long.valueOf(rightId));
		return result;
	}

	public List findRightsAll() {
		List results = null;
		results = securityManageDao.selectRightsAll();
		return results;
	}

	public List findRightsAllForPage() {
		List<Object> results = null;
		List<Object> returnList = null;
			
		results = securityManageDao.selectRightsAll();
		if(results != null && results.size()!=0){
			returnList = new ArrayList<Object>();	
			for(int i=0; i<results.size(); i++){
				Right item = (Right)results.get(i);
				HashMap<String,Object> hm = new HashMap<String,Object>();
				hm.put("id", item.getId() != null ? item.getId() : 0);
				hm.put("name",item.getName() != null ? item.getName() : "");
				hm.put("keyName",item.getKeyName() != null ? item.getKeyName() : "");
				hm.put("descn", item.getDescn() != null ? item.getDescn() : "");
				returnList.add(hm);
			}
		}		
	    return returnList;
	}
	public List findRightByIdForPage(String rightId){
		Right result = null;
		List<Object> returnList = new ArrayList<Object>();
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		result = securityManageDao.selectRightById(Long.valueOf(rightId));
		hm.put("id", result.getId() != null ? result.getId() : 0);
		hm.put("name",result.getName() != null ? result.getName() : "");
		hm.put("keyName",result.getKeyName() != null ? result.getKeyName() : "");
		hm.put("descn", result.getDescn() != null ? result.getDescn() : "");
		returnList.add(hm);
		
		return returnList;
	}
	
	public List findRoleByIdForPage(String roleId){
		
		Role result = null;
		List<Object> returnList = new ArrayList<Object>();
		HashMap<String,Object> hm = new HashMap<String,Object>();
		
		result = securityManageDao.selectRoleById(Long.valueOf(roleId));
		hm.put("id", result.getId() != null ? result.getId() : 0);
		hm.put("name",result.getName() != null ? result.getName() : "");
		hm.put("descn",result.getDescn() != null ? result.getDescn() : "");
		hm.put("department", result.getDepartment() != null ? result.getDepartment().getId() : "");
		hm.put("dataView", result.getDataViewFlag() != null ? result.getDataViewFlag() : "");
		hm.put("deptMenu", result.getDeptMenu()!= null ? result.getDeptMenu().getId(): "");
		returnList.add(hm);
		
		return returnList;
	}
	
	public void removeAuthorization(String authId) {
		securityManageDao.deleteAuthorizById(Long.valueOf(authId));
	}

	public void removeRight(String rightId) {
		securityManageDao.deleteRightById(Long.valueOf(rightId));
	}

	public void removeRole(String roleId) {
		securityManageDao.deleteRoleById(Long.valueOf(roleId));
		
	}

	public void removeRoleFromUser(UserInfo userInfo, String roleId) {
		Role role = securityManageDao.selectRoleById(Long.valueOf(roleId));
		userInfo.getRoles().remove(role);
		securityManageDao.saveUserInfoWithRoles(userInfo);
		
	}

	public Authorization saveAuthorization(Authorization auth) {
		Authorization res = null;
		res = securityManageDao.insertOrUpdateAuthoriz(auth);
		return res;
	}

	public Right saveRight(Right right) {
		Right result = null;
		result = securityManageDao.insertOrUpdateRight(right);
		return result;
	}

	public Role saveRole(Role role) {
		Role result = null;
		result = securityManageDao.insertOrUpdateRole(role);
		return result;
	}

	public Role findRoleById(String roleId) {
		Role role = null;
		role = securityManageDao.selectRoleById(Long.valueOf(roleId));
		return role;
	}

	public List findRolesAll() {
		List result = null;
		result = securityManageDao.selectRolesAll();
		return result;
	}

	public List<Object> findRolesAllForPage() {
		List<Role> results = null;
		List<Object> returnList = null;
		results = securityManageDao.selectRolesAll();
		
		if(results != null && results.size()!=0){
			returnList = new ArrayList<Object>();	
			for(int i=0; i<results.size(); i++){
				Role item = (Role)results.get(i);
				HashMap<String,Object> hm = new HashMap<String,Object>();
				hm.put("id", item.getId() != null ? item.getId() : 0);
				hm.put("name",item.getName() != null ? item.getName() : "");
				hm.put("descn", item.getDescn() != null ? item.getDescn() : "");
				hm.put("department", item.getDepartment() != null ? item.getDepartment().getDepartName() : "");
				returnList.add(hm);
			}
		}
		
		return returnList;
	}
	
	public List findAuthorizationsOrderByModule() {
		List authors = securityManageDao.selectAuthorizationsOrderByReourceModule();
		return authors;
	}

	public List findModuleWithAuthorizations() {
		List modules = null;
		modules = securityManageDao.selectModuleWithAuthorizations();
		return modules;
	}

	public UserInfo findUserById(String userId) {
		UserInfo user = null;
		user = securityManageDao.selectUserById(Long.valueOf(userId));
		return user;
	}
	
	public List findUserByIdForPage(String userId){
		UserInfo result = null;
		List<Object> returnList = new ArrayList<Object>();
//		Set roles = null;
		result = securityManageDao.selectUserById(Long.valueOf(userId));
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("id", result.getId() != null ? result.getId() : 0);
		hm.put("realName",result.getRealName()!= null ? result.getRealName() : "");
		hm.put("userName",result.getUserName()!= null ? result.getUserName() : "");
		hm.put("password",result.getPassword()!= null ? result.getPassword() : "");
		hm.put("email",result.getEmail()!= null ? result.getEmail() : "");
		hm.put("telephone",result.getTelephone()!= null ? result.getTelephone(): "");
		hm.put("mobilePhone",result.getMobilePhone()!= null ? result.getMobilePhone() : "");
		hm.put("enabled",result.getEnabled()!= null ? result.getEnabled(): "");
		hm.put("specialUser",result.getSpecialUser()!= null ? result.getSpecialUser() : "");
		hm.put("isLock",result.getIsLock()!= null ? result.getIsLock() : "");
		String deptCode = "";
		if(result.getDepartment()!=null){
			deptCode += result.getDepartment().getDepartName();
		}
		hm.put("name", deptCode);
		
		////////////////////////////////add by lee for add paltform in 090613 start//////////////////
		String paltName = "";
		if(result.getPlatform()!=null){
			paltName = result.getPlatform().getName();
		}
		hm.put("platName", paltName);
		////////////////////////////////add by lee for add paltform in 090613 end//////////////////
		
		if(result.getEnabled()==1){
			hm.put("enabled",true);
		}else {
			hm.put("enabled",false);
		}
		if(result.getSpecialUser()==1){
			hm.put("specialUser",true);
		}else {
			hm.put("specialUser",false);
		}
		if(result.getIsLock()==1){
			hm.put("isLock",true);
		}else {
			hm.put("isLock",false);
		}
		//用户关联的权限的集合
//		roles = result.getRoles();
//		Set<Long> roleIds = new HashSet<Long>();   //暂时不用
//		if(roles!=null && roles.size()!=0){
//			Iterator iter = roles.iterator();
//			while(iter.hasNext()){
//				Role item = (Role) iter.next();
//				roleIds.add(item.getId());
//			}
//		hm.put("roleIds", roleIds);
//		}
		returnList.add(hm);
		return returnList;
	}
	
	public List findRoleIdsByUserIdForPage(String userId){
		UserInfo user = null;
		Set set = null;
		List<Object> rolesList = null;
		List<Object> returnList = null;
		
		rolesList = securityManageDao.selectRolesAll();
		user = securityManageDao.selectUserById(Long.valueOf(userId));	
		Set<Long> roleIds = new HashSet<Long>();
		set = user.getRoles();
		if(set!=null && set.size()!=0){
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				Role role = (Role) iter.next();
				roleIds.add(role.getId());
			}
		}
		if(rolesList!=null && rolesList.size()!=0){
			returnList = new ArrayList<Object>();
			for(int i=0; i<rolesList.size(); i++){
				HashMap<String,Object> hm = new HashMap<String,Object>();
				Role item = (Role) rolesList.get(i);
				Long roleId = item.getId();
				if(roleIds.contains(roleId)){
					hm.put("id", item.getId() != null ? item.getId() : 0);
					hm.put("name",item.getName() != null ? item.getName() : "");
					hm.put("descn", item.getDescn() != null ? item.getDescn() : "");
					returnList.add(hm);
				}
			}
		}
		
		return returnList;
	}
	
	public List findAuthsByRoleIdForPage(String roleId){
		Role role = null;
		Set set = null;
		List<Object> authsList = null;
		List<Object> returnList = null;
		
		authsList = securityManageDao.selectAuthorizationsAll();
		role = securityManageDao.selectRoleById(Long.valueOf(roleId));
		Set<Long> authIds = new HashSet<Long>();
		set = role.getAuthorizations();
		if(set!=null && set.size()!=0){
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				Authorization auth = (Authorization) iter.next();
				authIds.add(auth.getId());
			}
		}
		if(authsList!=null && authsList.size()!=0){
			returnList = new ArrayList<Object>();
			for(int i=0; i<authsList.size(); i++){
				HashMap<String,Object> hm = new HashMap<String,Object>();
				Authorization item = (Authorization) authsList.get(i);
				Long authId = item.getId();
				if(authIds.contains(authId)){
					hm.put("id", item.getId() != null ? item.getId() : 0);
					hm.put("name", item.getName() != null ? item.getName() : "");
					if(item.getModule() != null){
						hm.put("moduleName", item.getModule().getName() !=null ? item.getModule().getName():"" );
					}else{
						hm.put("moduleName","");
					}
					returnList.add(hm);
				}
			
			}
		}
		
		return returnList;
		
	}
	
	public List findUserByQueryParams(String userName, String realName) {
		List list = securityManageDao.selectUsersByUserNameOrRealName(userName, realName);
		return list;
	}
	
	public List findUserByQueryParamsForPage(String userName, String realName) {
		List<Object> results = null;
		List<Object> returnList = null;
		
		results = securityManageDao.selectUsersByUserNameOrRealName(userName, realName);
		if(results != null && results.size()!=0){
			returnList = new ArrayList<Object>();
			for(int i=0; i<results.size(); i++){
				HashMap<String,Object> hm = new HashMap<String, Object>();
				UserInfo item = (UserInfo)results.get(i);
				
				hm.put("id", item.getId() != null ? item.getId() : 0);
				hm.put("realName",item.getRealName() != null ? item.getRealName() : "");
				hm.put("userName",item.getUserName() != null ? item.getUserName() : "");
				hm.put("password", item.getPassword() != null ? item.getPassword() : "");
				hm.put("email",item.getEmail()!= null ? item.getEmail() : "");
				if(item.getEnabled()==1){
					hm.put("enabled","是");
				}else {
					hm.put("enabled","否");
				}
				if(item.getSpecialUser()==1){
					hm.put("specialUser","是");
				}else {
					hm.put("specialUser","否");
				}
				if(item.getIsLock()==1){
					hm.put("locked","是");
				}else {
					hm.put("locked","否");
				}
				String departName = "";
				if(item.getDepartment()!=null){
					departName += item.getDepartment().getDepartName();
				}
				hm.put("department", departName != null ? departName : "");
				returnList.add(hm);
			}
		}
		return returnList;
	}
	
	public Page findUser(Map params, int pageNo, int pageSize, String propName, boolean isAsc) {
		String username = (String) params.get("userName");
		String realname = (String) params.get("realName");
		Criteria dc = super.createCriteria(UserInfo.class);
		if(username!=null&& !username.equals("")){
			dc.add(Restrictions.ilike("userName", username, MatchMode.ANYWHERE));
		}
		if(realname!=null&& !realname.equals("")){
			dc.add(Restrictions.ilike("realName", realname, MatchMode.ANYWHERE));
		}
		if(propName!=null){
			if(isAsc){
				dc.addOrder(Order.asc(propName));
			}else{
				dc.addOrder(Order.desc(propName));
			}
		}
		Disjunction disj = Restrictions.disjunction(); //不是外部用户
		disj.add(Restrictions.isNull("externalFlag"));
		disj.add(Restrictions.eq("externalFlag", Integer.valueOf(0)));
		dc.add(disj);
		
		Disjunction disj2 = Restrictions.disjunction(); //非锁定用户
		disj2.add(Restrictions.isNull("isLock")); 
		disj2.add(Restrictions.eq("isLock", Integer.valueOf(0)));
		
		dc.add(disj);
		dc.add(disj2);
		
		Page page = super.pagedQuery(dc, pageNo, pageSize);
		return page;
	}
	

	public Page findUser(Map params, int pageNo, int pagesize, String propName, boolean isAsc, 
						String propName2, boolean isAsc2) {
		
		String username = (String) params.get("userName");
		String realname = (String) params.get("realname");
		Criteria dc = super.createCriteria(UserInfo.class, propName, isAsc);
		if(username!=null&& !username.equals("")){
			dc.add(Restrictions.ilike("userName", username, MatchMode.ANYWHERE));
		}
		if(realname!=null&& !realname.equals("")){
			dc.add(Restrictions.ilike("realName", realname, MatchMode.ANYWHERE));
		}
		if(propName2!=null){
			if(isAsc2){
				dc.addOrder(Order.asc(propName2));
			}else{
				dc.addOrder(Order.desc(propName2));
			}
		}
		Page page = super.pagedQuery(dc, pageNo, pagesize);
		return page;
	}
	

	public void removeUser(String userId) {
		String hql="select um from UserExtraMenuItem um where um.userInfo=? ";
		List userMenus = super.find(hql, new Object[]{new UserInfo(Long.valueOf(userId))});
		Iterator iter = userMenus.iterator();
		while(iter.hasNext()){
			UserExtraMenuItem um = (UserExtraMenuItem) iter.next();
			super.remove(um);
		}
		executeUpdate("delete UserInfo u where u.id=?", Long.valueOf(userId));
		executeUpdate("delete UserTableSetting uts where uts.userInfo.id=?", Long.valueOf(userId));
		
		executeUpdate("delete UserRole ur where ur.userInfo.id=?", Long.valueOf(userId));
		executeUpdate("delete UserTableQueryColumn utqc where utqc.userInfo.id=?", Long.valueOf(userId));
		
		try {
			executeUpdate("update Notice set createUser=null where createUser.id=?", Long.valueOf(userId));
			executeUpdate("update UploadFile n set uploadUser=null where uploadUser.id=?", Long.valueOf(userId));
			executeUpdate("update Ibm2A1PartImportLog n set importUser=null where importUser.id=?", Long.valueOf(userId));
			executeUpdate("update PortalContainer set userInfo=null where userInfo.id=?", Long.valueOf(userId));
			executeUpdate("update CustomerBrand set modifyUser=null where modifyUser.id=?", Long.valueOf(userId));
			
			executeUpdate("update Order set createUser=null where createUser.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyBid set createUser=null where createUser.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyBid set saleMan=null where saleMan.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyAAP set createUser=null where createUser.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyAAP set saleMan=null where saleMan.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyCancle set applyUser=null where applyUser.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyCancle set saleMan=null where saleMan.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyContract set createUser=null where createUser.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyContract set saleMan=null where saleMan.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyFile set uploadUser=null where uploadUser.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyStock set createUser=null where createUser.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApplyStock set saleMan=null where saleMan.id=?", Long.valueOf(userId));
			executeUpdate("update OrderApproveHis set approver=null where approver.id=?", Long.valueOf(userId));
			executeUpdate("update TaskPreAssign set actor=null where actor.id=?", Long.valueOf(userId));
			executeUpdate("update TaskPreAssign set proxy=null where proxy.id=?", Long.valueOf(userId));
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public UserInfo saveUserInfoWithRoles(UserInfo userInfo) {
		UserInfo user = null;
		try {
			user = securityManageDao.saveUserInfoWithRoles(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}		
		return user;
	}

	public UserInfo saveUserInfoPwdModify(UserInfo userInfo) {
		Long id = userInfo.getId();
		String password = userInfo.getPassword();
		super.executeUpdate("update UserInfo set password=? where id=?", password, id);
		return userInfo;
	}

	public UserInfo saveUserInfoStyleWithRoles(UserInfo userInfo) {
		UserInfo user = null;
		try {
			user = securityManageDao.saveUserInfoStyleWithRoles(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}		
		return user;
	}
	/**
	 * 查找所有角色数据
	 * @Methods Name findRols
	 * @Create In Apr 28, 2009 By Administrator
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param propName
	 * @param isAsc
	 * @return Page
	 */
	public Page findRols(Map params, int pageNo, int pageSize, String propName, boolean isAsc) {
		String name = (String) params.get("name");
		Criteria dc = super.createCriteria(Role.class);
		//add by awen for add property on entity called 'role' on june 14 2009 begin
		dc.setFetchMode("platform", FetchMode.JOIN);
		//add by awen for add property on entity called 'role' on june 14 2009 end
		if(name!=null&& !name.equals("")){
			dc.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		}
		if(propName!=null){
			if(isAsc){
				dc.addOrder(Order.asc(propName));
			}else{
				dc.addOrder(Order.desc(propName));
			}
		}
		Page page = super.pagedQuery(dc, pageNo, pageSize);
		return page;
	}
	
	public Page findLockUser(Map params, int pageNo, int pageSize, String propName, boolean isAsc) {
		String username = (String) params.get("userName");
		String realname = (String) params.get("realName");
		Criteria dc = super.createCriteria(UserInfo.class);
		if(username!=null&& !username.equals("")){
			dc.add(Restrictions.ilike("userName", username, MatchMode.ANYWHERE));
		}
		if(realname!=null&& !realname.equals("")){
			dc.add(Restrictions.ilike("realName", realname, MatchMode.ANYWHERE));
		}
		if(propName!=null){
			if(isAsc){
				dc.addOrder(Order.asc(propName));
			}else{
				dc.addOrder(Order.desc(propName));
			}
		}
		Disjunction disj = Restrictions.disjunction(); //不是外部用户
		disj.add(Restrictions.isNull("externalFlag"));
		disj.add(Restrictions.eq("externalFlag", Integer.valueOf(0)));
		dc.add(disj);
		
		Disjunction disj2 = Restrictions.disjunction(); //非锁定用户
		disj2.add(Restrictions.isNull("isLock")); 
		disj2.add(Restrictions.eq("isLock", Integer.valueOf(1)));
		
		dc.add(disj);
		dc.add(disj2);
		
		Page page = super.pagedQuery(dc, pageNo, pageSize);
		return page;
	}
	public Role findRole(Long roleId){
		return 	(Role) createCriteria(Role.class)
		.createAlias("department", "dep")
		.setFetchMode("department", FetchMode.JOIN)
		.setFetchMode("dep.parentDepartment", FetchMode.JOIN)
		.setFetchMode("deptMenuTemplate", FetchMode.JOIN)
		.add(Restrictions.eq("id", roleId))
		.uniqueResult();
	}

	public SecurityManageDao getSecurityManageDao() {
		return securityManageDao;
	}
}
