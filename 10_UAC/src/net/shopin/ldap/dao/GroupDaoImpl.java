package net.shopin.ldap.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.SearchControls;

import net.shopin.ldap.entity.UserGroup;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;

/**
 * @see net.shopin.ldap.dao.GroupDao
 * @author wchao
 *
 */
public class GroupDaoImpl implements GroupDao {

	private LdapTemplate ldapTemplate;
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.UserGroupDao#create(net.shopin.ldap.entity.UserGroup)
	 */
	public void create(UserGroup userGroup) {
		// TODO Auto-generated method stub
		Name dn = buildDn(userGroup);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(userGroup, context);
		ldapTemplate.bind(dn, context, null);
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.UserGroupDao#update(net.shopin.ldap.entity.UserGroup)
	 */
	public void update(UserGroup userGroup) {
		// TODO Auto-generated method stub
		Name dn = buildDn(userGroup);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(userGroup, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}
	
	/* 
	 * 删除部门，如果部门下存在人员信息需要将其及其子部门下所有人员转移
	 * @see net.shopin.ldap.dao.UserGroupDao#delete(net.shopin.ldap.entity.UserGroup)
	 */
	public void remove(UserGroup userGroup) {
		ldapTemplate.unbind(buildDn(userGroup));
	}
	
	public void deleteByRDN(String deptRDN) {
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(deptRDN);
		context.setAttributeValue("status", UserGroup.SATAL_NOT_NORMAL.toString());
		ldapTemplate.modifyAttributes(deptRDN, context.getModificationItems());
	}
	
	private Name buildDn(UserGroup userGroup) {
		return new DistinguishedName("cn=" + userGroup.getCn() + ",ou=groups");
	}

	/*
	 * (non-Javadoc)
	 * @see net.shopin.ldap.dao.UserGroupDao#delete(net.shopin.ldap.entity.UserGroup)
	 */
	public UserGroup findByRDN(String deptRDN) {

		return (UserGroup)ldapTemplate.lookup(deptRDN, new GroupContextMapper());
		
	}

	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.UserGroupDao#findSubDeptsByParentNo(java.lang.String)
	 */
	public List<UserGroup> findSubGroupsByParentRDN(String parentRDN) {
		// TODO Auto-generated method stub
		//return ldapTemplate.listBindings(parentRDN, getContextMapper());
		SearchControls controls  = new SearchControls();
		controls.setCountLimit(Integer.MAX_VALUE);
		controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		controls.setReturningObjFlag(true);
		String filter=null;
		filter="(&(objectClass=shopin-groupOfNames)(status=0)|(displayName=*))";
		List<UserGroup> depts = ldapTemplate.search(parentRDN, filter, controls, getContextMapper());
		return depts;
	}

	private void mapToContext(UserGroup userGroup, DirContextAdapter context) {
		
		context.setAttributeValues("objectclass", new String[] { "top", "shopin-groupOfNames"});
		context.setAttributeValue("displayName", userGroup.getDisplayName());
		context.setAttributeValue("description", StringUtils.isNotEmpty(userGroup.getDescription()) ? userGroup.getDescription() : null);
		context.setAttributeValue("status", userGroup.getStatus().toString());
		context.setAttributeValues("member", userGroup.getMembers());
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.UserGroupDao#findDeptsByParam(java.lang.String)
	 */
	public List<UserGroup> findGroupsByParam(String param) {
		// TODO Auto-generated method stub
		String filter=null;
		if(StringUtils.isNotEmpty(param)){
			filter="(&(objectClass=shopin-groupOfNames)(status=0)(displayName=*" + param + "*))";
		}else{
			filter="(&(objectClass=shopin-groupOfNames)(status=0)(displayName=*))";
		}
		List<UserGroup> groups = ldapTemplate.search("ou=groups", filter, getContextMapper());

		return groups;
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.UserGroupDao#findDeptsByParam(java.lang.String)
	 */
	public List<UserGroup> findGroupsByParam(String param, String userRDN, boolean isRelation) {
		// TODO Auto-generated method stub
		String filter=null;
		if(param != null && !param.equals("")){
			if(isRelation){
				filter="(&(objectClass=shopin-groupOfNames)&(member=" + userRDN + ")&(status=0)|(cn=*" + param + "*)(displayName=*" + param + "*))";
			}else{
				filter="(&(objectClass=shopin-groupOfNames)!(member=" + userRDN + ")&(status=0)|(cn=*" + param + "*)(displayName=*" + param + "*))";
			}			
		}else{
			if(isRelation){
				filter="(&(objectClass=shopin-groupOfNames)&(member=" + userRDN + ")&(status=0)|(cn=*)(displayName=*))";
			}else{
				filter="(&(objectClass=shopin-groupOfNames)!(member=" + userRDN + ")&(status=0)|(cn=*)(displayName=*))";
			}
		}
		List<UserGroup> groups = ldapTemplate.search("ou=groups", filter, getContextMapper());

		return groups;
	}
	
	public ContextMapper getContextMapper() {
		// TODO Auto-generated method stub
		return new GroupContextMapper();
	}
	
	private static class GroupContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			//DistinguishedName dn = new DistinguishedName(context.getDn());
			UserGroup group = new UserGroup();
			group.setRdn(context.getDn().toString());
			group.setCn(context.getStringAttribute("cn"));
			group.setDisplayName(context.getStringAttribute("displayName"));
			group.setDescription(context.getStringAttribute("description"));
			if(context.getStringAttribute("status")!=null)
				group.setStatus(Integer.valueOf(context.getStringAttribute("status")));
			group.setMembers(context.getStringAttributes("member"));
			return group;
		}
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
	
	
	/**
	 * 是否超级管理员
	 * param userRDN dn(dn+searchBase=fullname)
	 * @return
	 */
	public boolean isSupserAdmin(String userRDN){
		UserGroup group = this.findByRDN("cn=SuperAdmin,ou=groups");
		if(group!=null){
			String [] members = group.getMembers();
			if(ArrayUtils.isEmpty(members))return false;
			for(String member : members){
				if(userRDN.equals(member.substring(4, member.indexOf(",")))){
					return true;
				}
			}
		}
		return false;
	}

}
