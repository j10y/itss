package net.shopin.ldap.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.SearchControls;

import net.shopin.ldap.entity.User;
import net.shopin.ldap.entity.UserGroup;
import net.shopin.util.PropertiesUtil;
import net.shopin.util.SpringContextUtils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;

/**
 * @see net.shopin.ldap.dao.GroupDao
 * @author wchao
 *
 */
public class GroupDaoImpl implements GroupDao {

	private LdapTemplate ldapTemplate;
	
	public void create(UserGroup userGroup) {
		// TODO Auto-generated method stub
		Name dn = buildDn(userGroup);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(userGroup, context);
		ldapTemplate.bind(dn, context, null);
	}
	
	public void update(UserGroup userGroup) {
		Name dn = buildDn(userGroup);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(userGroup, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}
	
	/* 
	 * 删除用户组，如果用户组下存在人员信息需要将其及其子用户组下所有人员转移
	 * @see net.shopin.ldap.dao.UserGroupDao#delete(net.shopin.ldap.entity.UserGroup)
	 */
	public void remove(UserGroup userGroup) {
		ldapTemplate.unbind(buildDn(userGroup));
	}
	
	public void deleteByDN(String groupDN) {
		if(groupDN.contains(PropertiesUtil.getProperties("base"))){
			groupDN = groupDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(groupDN);
		context.setAttributeValue("status", UserGroup.SATAL_NOT_NORMAL.toString());
		ldapTemplate.modifyAttributes(groupDN, context.getModificationItems());
	}
	
	private Name buildDn(UserGroup userGroup) {
		return new DistinguishedName("cn=" + userGroup.getCn() + ",ou=groups");
	}

	public UserGroup findByDN(String groupDN) {
		if(groupDN.contains(PropertiesUtil.getProperties("base"))){
			groupDN = groupDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		return (UserGroup)ldapTemplate.lookup(groupDN, new GroupContextMapper());
		
	}

	public List<UserGroup> findSubGroupsByParentDN(String parentDN) {
		if(parentDN.contains(PropertiesUtil.getProperties("base"))){
			parentDN = parentDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		SearchControls controls  = new SearchControls();
		controls.setCountLimit(Integer.MAX_VALUE);
		controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		controls.setReturningObjFlag(true);
		String filter=null;
		filter="(&(objectClass=shopin-groupOfNames)(status=0)|(displayName=*))";
		List<UserGroup> depts = ldapTemplate.search(parentDN, filter, controls, getContextMapper());
		return depts;
	}

	private void mapToContext(UserGroup userGroup, DirContextAdapter context) {
		
		context.setAttributeValues("objectclass", new String[] { "top", "shopin-groupOfNames"});
		context.setAttributeValue("displayName", userGroup.getDisplayName());
		context.setAttributeValue("description", StringUtils.isNotEmpty(userGroup.getDescription()) ? userGroup.getDescription() : null);
		context.setAttributeValue("status", userGroup.getStatus().toString());
		context.setAttributeValues("member", userGroup.getMembers());
	}
	
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
	
	public List<UserGroup> findGroupsByParam(String param, String userDN, boolean isRelation) {
		// TODO Auto-generated method stub
		String filter=null;
		if(param != null && !param.equals("")){
			if(isRelation){
				filter="(&(objectClass=shopin-groupOfNames)&(member=" + userDN + ")&(status=0)|(cn=*" + param + "*)(displayName=*" + param + "*))";
			}else{
				filter="(&(objectClass=shopin-groupOfNames)!(member=" + userDN + ")&(status=0)|(cn=*" + param + "*)(displayName=*" + param + "*))";
			}			
		}else{
			if(isRelation){
				filter="(&(objectClass=shopin-groupOfNames)&(member=" + userDN + ")&(status=0)|(cn=*)(displayName=*))";
			}else{
				filter="(&(objectClass=shopin-groupOfNames)!(member=" + userDN + ")&(status=0)|(cn=*)(displayName=*))";
			}
		}
		List<UserGroup> groups = ldapTemplate.search("ou=groups", filter, getContextMapper());

		return groups;
	}
	
	public ContextMapper getContextMapper() {
		return new GroupContextMapper();
	}
	
	private static class GroupContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			//DistinguishedName dn = new DistinguishedName(context.getDn());
			UserGroup group = new UserGroup();
//			group.setDn(context.getDn().toString());
			group.setDn(context.getDn().toString() + (StringUtils.isNotEmpty(PropertiesUtil.getProperties("base")) ? ',' + PropertiesUtil.getProperties("base") : ""));
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
	 * param userDN dn(dn+searchBase=fullname)
	 * @return
	 */
	public boolean isSupserAdmin(String userDN){
		UserGroup group = this.findByDN("cn=GROUP_ADMIN,ou=groups");
		if(group!=null){
			String [] members = group.getMembers();
			if(ArrayUtils.isEmpty(members))return false;
			for(String member : members){
				if(userDN.equals(member.substring(4, member.indexOf(",")))){
					return true;
				}
			}
		}
		return false;
	}

	public List<User> listMembers(String groupDN) {
		
		if(groupDN.contains(PropertiesUtil.getProperties("base"))){
			groupDN = groupDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		
		List<User> userList = new ArrayList();
		UserGroup group = (UserGroup)ldapTemplate.lookup(groupDN, new GroupContextMapper());
		String [] members = group.getMembers();
		UserDao userDao = (UserDao) SpringContextUtils.getBean("userDao");
		if(members!=null){
			for(String member : members){
				User user = userDao.findByDN(member.contains(PropertiesUtil.getProperties("base")) ? member.replace(("," + PropertiesUtil.getProperties("base")), "") : member);
				if(user!=null){
					userList.add(user);
				}
			}
		}
		return userList;
	}
	
}
