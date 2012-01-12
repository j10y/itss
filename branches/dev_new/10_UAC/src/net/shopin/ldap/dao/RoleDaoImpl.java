package net.shopin.ldap.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.SearchControls;

import net.shopin.ldap.entity.Role;
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
 * @see net.shopin.ldap.dao.RoleDao
 * @author wchao
 *
 */
public class RoleDaoImpl implements RoleDao {

	private LdapTemplate ldapTemplate;
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.RoleDao#create(net.shopin.ldap.entity.Role)
	 */
	public void create(Role role) {
		// TODO Auto-generated method stub
		Name dn = buildDn(role);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(role, context);
		ldapTemplate.bind(dn, context, null);
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.RoleDao#update(net.shopin.ldap.entity.Role)
	 */
	public void update(Role role) {
		// TODO Auto-generated method stub
		Name dn = buildDn(role);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(role, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}
	
	/* 
	 * 删除部门，如果部门下存在人员信息需要将其及其子部门下所有人员转移
	 * @see net.shopin.ldap.dao.RoleDao#delete(net.shopin.ldap.entity.Role)
	 */
	public void remove(Role role) {
		ldapTemplate.unbind(buildDn(role));
	}
	
	public void deleteByDN(String roleDN) {
		if(roleDN.contains(PropertiesUtil.getProperties("base"))){
			roleDN = roleDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(roleDN);
		context.setAttributeValue("status", Role.SATAL_NOT_NORMAL.toString());
		ldapTemplate.modifyAttributes(roleDN, context.getModificationItems());
	}
	
	private Name buildDn(Role role) {
		return new DistinguishedName("cn=" + role.getCn() + ",ou=roles");
	}

	/*
	 * (non-Javadoc)
	 * @see net.shopin.ldap.dao.RoleDao#delete(net.shopin.ldap.entity.Role)
	 */
	public Role findByDN(String roleDN) {
		if(roleDN.contains(PropertiesUtil.getProperties("base"))){
			roleDN = roleDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		return (Role)ldapTemplate.lookup(roleDN, new RoleContextMapper());
		
	}

	public List<Role> findSubRolesByParentDN(String parentDN) {
		if(parentDN.contains(PropertiesUtil.getProperties("base"))){
			parentDN = parentDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		SearchControls controls  = new SearchControls();
		controls.setCountLimit(Integer.MAX_VALUE);
		controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		controls.setReturningObjFlag(true);
		String filter=null;
		filter="(&(objectClass=shopin-organizationalRole)(status=0)|(displayName=*))";
		List<Role> depts = ldapTemplate.search(parentDN, filter, controls, getContextMapper());
		return depts;
	}

	private void mapToContext(Role role, DirContextAdapter context) {
		
		context.setAttributeValues("objectclass", new String[] { "top", "shopin-organizationalRole"});
		context.setAttributeValue("displayName", role.getDisplayName());
		context.setAttributeValue("description", StringUtils.isNotEmpty(role.getDescription()) ? role.getDescription() : null);
		context.setAttributeValue("status", role.getStatus().toString());
		context.setAttributeValues("roleOccupant", role.getRoleOccupant());
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.RoleDao#findDeptsByParam(java.lang.String)
	 */
	public List<Role> findRolesByParam(String param) {
		// TODO Auto-generated method stub
		String filter=null;
		if(StringUtils.isNotEmpty(param)){
			filter="(&(objectClass=shopin-organizationalRole)(status=0)(displayName=*" + param + "*))";
		}else{
			filter="(&(objectClass=shopin-organizationalRole)(status=0)(displayName=*))";
		}
		List<Role> roles = ldapTemplate.search("ou=roles", filter, getContextMapper());

		return roles;
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.RoleDao#findDeptsByParam(java.lang.String)
	 */
	public List<Role> findRolesByParam(String param, String userDN, boolean isRelation) {
		// TODO Auto-generated method stub
		String filter=null;
		if(param != null && !param.equals("")){
			if(isRelation){
				filter="(&(objectClass=shopin-organizationalRole)&(occupant=" + userDN + ")&(status=0)|(cn=*" + param + "*)(displayName=*" + param + "*))";
			}else{
				filter="(&(objectClass=shopin-organizationalRole)!(occupant=" + userDN + ")&(status=0)|(cn=*" + param + "*)(displayName=*" + param + "*))";
			}			
		}else{
			if(isRelation){
				filter="(&(objectClass=shopin-organizationalRole)&(occupant=" + userDN + ")&(status=0)|(cn=*)(displayName=*))";
			}else{
				filter="(&(objectClass=shopin-organizationalRole)!(occupant=" + userDN + ")&(status=0)|(cn=*)(displayName=*))";
			}
		}
		List<Role> roles = ldapTemplate.search("ou=roles", filter, getContextMapper());

		return roles;
	}
	
	public ContextMapper getContextMapper() {
		// TODO Auto-generated method stub
		return new RoleContextMapper();
	}
	
	private static class RoleContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			//DistinguishedName dn = new DistinguishedName(context.getDn());
			Role role = new Role();
//			role.setDn(context.getDn().toString());
			role.setDn(context.getDn().toString() + (StringUtils.isNotEmpty(PropertiesUtil.getProperties("base")) ? ',' + PropertiesUtil.getProperties("base") : ""));
			role.setCn(context.getStringAttribute("cn"));
			role.setDisplayName(context.getStringAttribute("displayName"));
			role.setDescription(context.getStringAttribute("description"));
			if(context.getStringAttribute("status")!=null)
				role.setStatus(Integer.valueOf(context.getStringAttribute("status")));
			role.setRoleOccupant(context.getStringAttributes("roleOccupant"));
			return role;
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
		Role role = this.findByDN("cn=SuperAdmin,ou=roles");
		if(role!=null){
			String [] roleOccupants = role.getRoleOccupant();
			if(ArrayUtils.isEmpty(roleOccupants))return false;
			for(String occupant : roleOccupants){
				if(userDN.equals(occupant.substring(4, occupant.indexOf(",")))){
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public List<User> listMembers(String roleDN) {
		
		if(roleDN.contains(PropertiesUtil.getProperties("base"))){
			roleDN = roleDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		
		List<User> userList = new ArrayList();
		Role role = (Role)ldapTemplate.lookup(roleDN, new RoleContextMapper());
		String [] roleOccupants = role.getRoleOccupant();
		UserDao userDao = (UserDao) SpringContextUtils.getBean("userDao");
		if(roleOccupants!=null){
			for(String member : roleOccupants){
				User user = userDao.findByDN(member.contains(PropertiesUtil.getProperties("base")) ? member.replace(("," + PropertiesUtil.getProperties("base")), "") : member);
				if(user!=null){
					userList.add(user);
				}
			}
		}
		return userList;
	}

	@Override
	public List<Role> findRolesByUserDN(String userDN) {
		String filter=null;
		filter="(&(objectClass=shopin-organizationalRole)(roleOccupant=" + userDN + "))";
		List<Role> roles = ldapTemplate.search("ou=roles", filter, getContextMapper());
		return roles;
	}

}
