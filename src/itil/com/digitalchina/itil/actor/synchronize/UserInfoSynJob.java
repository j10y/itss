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
 * ͬ����Ա��Ϣ
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
	 * ͬ����Ա��Ϣ
	 * @Methods Name updateUserInfo
	 * @Create In Jun 29, 2010 By lee void
	 */
	public void updateUserInfo(){
		List list = new ArrayList();//��Ա��Ϣ
		Set<String> userTypes = new HashSet<String>();//�û�����
		Map<String,String> personnelScopeMap = new HashMap<String,String>();//�����ӷ�Χ
		Map<String,String> flatMap = new HashMap<String,String>();//ƽ̨��Ϣ
		InitialLdapContext ctx = null;//�����Ļ���
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, PropertiesUtil.getProperties("ladp.notes.url"));
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, PropertiesUtil.getProperties("ladp.notes.principal"));
		env.put(Context.SECURITY_CREDENTIALS, PropertiesUtil.getProperties("ladp.notes.credentials"));
		try {
			ctx = new InitialLdapContext(env, null);
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);//������Χ
			NamingEnumeration namingenumeration = null;
			String searchroot = "cn=employees,o=dc";
			namingenumeration =ctx.search(searchroot,"(&(objectclass=dcPerson)(dcstatus=0))",constraints);
			while(namingenumeration != null && namingenumeration.hasMore()) {
				SearchResult sr = (SearchResult) namingenumeration.next();
				String dn = sr.getName() + "," + searchroot;
				String attArrays[] = {
						"employeeNumber",	//Ա�����
						"uid",//�û���
						"cn",//������
						"departmentNumber",//���ű��
						"dcTitleCode",//��λ���
						"title",
						"mail",//�ʼ�
						"dcFlatName",//ƽ̨����
						"dcFlatCode",//ƽ̨���
						"dcCostCenterCode",//�ɱ����ı��
						"dcCostCenterName",//�ɱ���������
						"dcStatus",//�Ƿ���ְ,0- ����,1- �ѽ���
						"employeeType",//ҵ��Χ���
						"dcPersonalSubArea",//�����ӷ�Χ����
						"dcPersonalSubAreaCode",//�����ӷ�Χ���
						"dcPosition"//ְλ����
						};
				Attributes ar = ctx.getAttributes(dn, attArrays);
				if (ar == null) {
					System.out.print("��Ӧ��uidû�ж��������");
					// ��Ӧ��uidû�ж��������
					//log.error("Entry "+dn+ " has none of the specified attributes\n");
				}
				else{
					Hashtable user = new Hashtable();
					String personalSubArea = null;
					String personalSubAreaCode = null;
					String flatName = null;
					String flatCode = null;
					Attribute attr = ar.get("employeeNumber");//�û����
					if (attr != null) {
						user.put("employeeNumber",attr.get());
					}
					attr = ar.get("uid");//Ա������
					if (attr != null) {
						user.put("uid",attr.get());
					}
					attr = ar.get("cn");//����
					if (attr != null){ 
						user.put("cn",attr.get());
					}
					attr = ar.get("departmentNumber");//�����û�����
					if (attr != null){ 
						user.put("departmentNumber",attr.get());
					}
					attr = ar.get("dcTitleCode");//��λ���
					if (attr != null){ 
						user.put("dcTitleCode",attr.get());
					}
					attr = ar.get("title");//��λ����
					if (attr != null) {
						user.put("title",attr.get());
					}
					attr = ar.get("mail");//�����ʼ�
					if(attr != null){
						user.put("mail",attr.get());
					}
					attr = ar.get("dcFlatName");//ƽ̨����
					if(attr != null){
						flatName = (String) attr.get();
						user.put("dcFlatName",attr.get());
					}
					attr = ar.get("dcFlatCode");//ƽ̨���
					if (attr != null) {
						flatCode = (String) attr.get();
						user.put("dcFlatCode",attr.get());
					}
					attr = ar.get("dcCostCenterCode");//��˾����
					if (attr != null){ 
						user.put("dcCostCenterCode",attr.get());
					}
					attr = ar.get("dcCostCenterName");//��������
					if (attr != null){ 
						user.put("dcCostCenterName",attr.get());
					}
					attr = ar.get("dcStatus");//״̬
					if (attr != null){ 
						user.put("dcStatus",attr.get());
					}
					attr = ar.get("employeeType");//�û�����
					if (attr != null) {
						String typeStr = (String) attr.get();
						user.put("employeeType",typeStr.substring(0, 1));
						userTypes.add(typeStr);
					}
					attr = ar.get("dcPersonalSubArea");//�����ӷ�Χ
					if (attr != null) {
						user.put("dcPersonalSubArea",attr.get());
						personalSubArea = (String) attr.get();
					}
					attr = ar.get("dcPersonalSubAreaCode");//�����ӷ�Χ���
					if (attr != null) {
						user.put("dcPersonalSubAreaCode",attr.get());//
						personalSubAreaCode = (String) attr.get();
					}
					attr = ar.get("dcPosition");//ְλ����
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
			System.out.println("�û�Ŀ¼���������жϣ��������Ա��ϵ!");
		}finally{
			try {
				ctx.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("�û�Ŀ¼���������жϣ��������Ա��ϵ!");
			}
		}
		userInfoSynDao.updateUserInfo(list);
	}
	/**
	 * ͬ��������Ϣ
	 * @Methods Name updateDepartment
	 * @Create In Jun 29, 2010 By lee void
	 */
	public void updateDepartment(){
		List list = new ArrayList();//����
		InitialLdapContext ctx = null;//�����Ļ���
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, PropertiesUtil.getProperties("ladp.notes.url"));
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, PropertiesUtil.getProperties("ladp.notes.principal"));
		env.put(Context.SECURITY_CREDENTIALS, PropertiesUtil.getProperties("ladp.notes.credentials"));
		try {
			ctx = new InitialLdapContext(env, null);
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);//������Χ
			NamingEnumeration namingenumeration = null;
			String searchroot = "cn=orgs,o=dc";
			namingenumeration =ctx.search(searchroot,"(&(objectclass=dcOrganization)(dcStatus=0))",constraints);
			while(namingenumeration != null && namingenumeration.hasMore()) {
				SearchResult sr = (SearchResult) namingenumeration.next();
				String dn = sr.getName() + "," + searchroot;
				String attArrays[] = {
						"ou",	//���ű��
						"name",//��������
						"dcSupervisoryDepartment"//�ϼ����ű��
						};
				Attributes ar = ctx.getAttributes(dn, attArrays);
				if (ar == null) {
					System.out.print("��Ӧ��uidû�ж��������");
				}
				else{
					Hashtable deptment = new Hashtable();
					Attribute attr = ar.get("ou");//���ű��
					if (attr != null) {
						deptment.put("dpetCode",attr.get());
					}
					attr = ar.get("name");//��������
					if (attr != null) {
						deptment.put("deptName",attr.get());
					}
					attr = ar.get("dcSupervisoryDepartment");//������
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
			System.out.println("�û�Ŀ¼���������жϣ��������Ա��ϵ!");
		}finally{
			try {
				ctx.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("�û�Ŀ¼���������жϣ��������Ա��ϵ!");
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
