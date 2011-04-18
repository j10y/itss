package com.digitalchina.itil.actor.synchronize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.itil.actor.dao.UserInfoSynDao;

/**
 * 同步人员信息
 * @Class Name UserInfoSynJob
 * @Author lee
 * @Create In Jun 29, 2010
 */
public class UserInfoSynJob extends QuartzJobBean{
	private UserInfoSynDao userInfoSynDao;
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		updateDepartment();
		updateUserInfo();
	}
	/**
	 * 同步人员信息
	 * @Methods Name updateUserInfo
	 * @Create In Jun 29, 2010 By lee void
	 */
	public void updateUserInfo(){
		List list = new ArrayList();//人员信息
		Set<String> userTypes = new HashSet<String>();//用户类型
		Map<String,String> personnelScopeMap = new HashMap<String,String>();//人事子范围
		Map<String,String> flatMap = new HashMap<String,String>();//平台信息
		InitialLdapContext ctx = null;//上下文环境
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, PropertiesUtil.getProperties("ladp.notes.url"));
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, PropertiesUtil.getProperties("ladp.notes.principal"));
		env.put(Context.SECURITY_CREDENTIALS, PropertiesUtil.getProperties("ladp.notes.credentials"));
		try {
			ctx = new InitialLdapContext(env, null);
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);//搜索范围
			NamingEnumeration namingenumeration = null;
			String searchroot = "cn=employees,o=dc";
			namingenumeration =ctx.search(searchroot,"(&(objectclass=dcPerson)(dcstatus=0))",constraints);
			while(namingenumeration != null && namingenumeration.hasMore()) {
				SearchResult sr = (SearchResult) namingenumeration.next();
				String dn = sr.getName() + "," + searchroot;
				String attArrays[] = {
						"employeeNumber",	//员工编号
						"uid",//用户名
						"cn",//中文名
						"departmentNumber",//部门编号
						"dcTitleCode",//岗位编号
						"title",
						"mail",//邮件
						"dcFlatName",//平台名称
						"dcFlatCode",//平台编号
						"dcCostCenterCode",//成本中心编号
						"dcCostCenterName",//成本中心名称
						"dcStatus",//是否离职,0- 正常,1- 已禁用
						"employeeType",//业务范围编号
						"dcPersonalSubArea",//人事子范围名称
						"dcPersonalSubAreaCode",//人事子范围编号
						"dcPosition"//职位名称
						};
				Attributes ar = ctx.getAttributes(dn, attArrays);
				if (ar == null) {
					System.out.print("对应的uid没有多余的属性");
					// 对应的uid没有多余的属性
					//log.error("Entry "+dn+ " has none of the specified attributes\n");
				}
				else{
					Hashtable user = new Hashtable();
					String personalSubArea = null;
					String personalSubAreaCode = null;
					String flatName = null;
					String flatCode = null;
					Attribute attr = ar.get("employeeNumber");//用户编号
					if (attr != null) {
						user.put("employeeNumber",attr.get());
					}
					attr = ar.get("uid");//员工名称
					if (attr != null) {
						user.put("uid",attr.get());
					}
					attr = ar.get("cn");//姓氏
					if (attr != null){ 
						user.put("cn",attr.get());
					}
					attr = ar.get("departmentNumber");//中文用户名字
					if (attr != null){ 
						user.put("departmentNumber",attr.get());
					}
					attr = ar.get("dcTitleCode");//岗位编号
					if (attr != null){ 
						user.put("dcTitleCode",attr.get());
					}
					attr = ar.get("title");//岗位名称
					if (attr != null) {
						user.put("title",attr.get());
					}
					attr = ar.get("mail");//电子邮件
					if(attr != null){
						user.put("mail",attr.get());
					}
					attr = ar.get("dcFlatName");//平台名称
					if(attr != null){
						flatName = (String) attr.get();
						user.put("dcFlatName",attr.get());
					}
					attr = ar.get("dcFlatCode");//平台编号
					if (attr != null) {
						flatCode = (String) attr.get();
						user.put("dcFlatCode",attr.get());
					}
					attr = ar.get("dcCostCenterCode");//公司代码
					if (attr != null){ 
						user.put("dcCostCenterCode",attr.get());
					}
					attr = ar.get("dcCostCenterName");//部门名称
					if (attr != null){ 
						user.put("dcCostCenterName",attr.get());
					}
					attr = ar.get("dcStatus");//状态
					if (attr != null){ 
						user.put("dcStatus",attr.get());
					}
					attr = ar.get("employeeType");//用户类型
					if (attr != null) {
						String typeStr = (String) attr.get();
						user.put("employeeType",typeStr.substring(0, 1));
						userTypes.add(typeStr);
					}
					attr = ar.get("dcPersonalSubArea");//人事子范围
					if (attr != null) {
						user.put("dcPersonalSubArea",attr.get());
						personalSubArea = (String) attr.get();
					}
					attr = ar.get("dcPersonalSubAreaCode");//人事子范围编号
					if (attr != null) {
						user.put("dcPersonalSubAreaCode",attr.get());//
						personalSubAreaCode = (String) attr.get();
					}
					attr = ar.get("dcPosition");//职位名称
					if (attr != null) {
						user.put("dcPosition",attr.get());
					}
					if(personalSubAreaCode!=null&&personalSubArea!=null){
						personnelScopeMap.put(personalSubAreaCode, personalSubArea);
					}
					if(flatName!=null&&flatCode!=null){
						flatMap.put(flatCode, flatName);
					}
					list.add(user);
				}
			}
			userInfoSynDao.updateUserType(userTypes);
			userInfoSynDao.updatePlatform(flatMap);
			userInfoSynDao.updatePersonnelScope(personnelScopeMap);
		}catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("用户目录服务连接中断，请与管理员联系!");
		}finally{
			try {
				ctx.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("用户目录服务连接中断，请与管理员联系!");
			}
		}
		userInfoSynDao.updateUserInfo(list);
	}
	/**
	 * 同步部门信息
	 * @Methods Name updateDepartment
	 * @Create In Jun 29, 2010 By lee void
	 */
	public void updateDepartment(){
		List list = new ArrayList();//部门
		InitialLdapContext ctx = null;//上下文环境
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, PropertiesUtil.getProperties("ladp.notes.url"));
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, PropertiesUtil.getProperties("ladp.notes.principal"));
		env.put(Context.SECURITY_CREDENTIALS, PropertiesUtil.getProperties("ladp.notes.credentials"));
		try {
			ctx = new InitialLdapContext(env, null);
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);//搜索范围
			NamingEnumeration namingenumeration = null;
			String searchroot = "cn=orgs,o=dc";
			namingenumeration =ctx.search(searchroot,"(&(objectclass=dcOrganization)(dcStatus=0))",constraints);
			while(namingenumeration != null && namingenumeration.hasMore()) {
				SearchResult sr = (SearchResult) namingenumeration.next();
				String dn = sr.getName() + "," + searchroot;
				String attArrays[] = {
						"ou",	//部门编号
						"name",//部门名称
						"dcSupervisoryDepartment"//上级部门编号
						};
				Attributes ar = ctx.getAttributes(dn, attArrays);
				if (ar == null) {
					System.out.print("对应的uid没有多余的属性");
				}
				else{
					Hashtable deptment = new Hashtable();
					Attribute attr = ar.get("ou");//部门编号
					if (attr != null) {
						deptment.put("dpetCode",attr.get());
					}
					attr = ar.get("name");//部门名称
					if (attr != null) {
						deptment.put("deptName",attr.get());
					}
					attr = ar.get("dcSupervisoryDepartment");//父部门
					if (attr != null) {
						String parentCode = (String) attr.get();
						if(!parentCode.equals("dummy")){
							deptment.put("parentCode",parentCode);
						}
					}
					list.add(deptment);
				}
			}
		}catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("用户目录服务连接中断，请与管理员联系!");
		}finally{
			try {
				ctx.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("用户目录服务连接中断，请与管理员联系!");
			}
		}
		userInfoSynDao.updateDeptment(list);
	}
	public UserInfoSynDao getUserInfoSynDao() {
		return userInfoSynDao;
	}
	public void setUserInfoSynDao(UserInfoSynDao userInfoSynDao) {
		this.userInfoSynDao = userInfoSynDao;
	}
}
