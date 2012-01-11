package net.shopin.ldap.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.SearchControls;

import net.shopin.ldap.entity.Role;
import net.shopin.ldap.entity.System;
import net.shopin.util.PropertiesUtil;
import net.shopin.util.SpringContextUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;

/**
 * @see net.shopin.ldap.dao.SystemDao
 * @author wchao
 *
 */
public class SystemDaoImpl implements SystemDao {

	private LdapTemplate ldapTemplate;
	
	public void create(System system) {
		// TODO Auto-generated method stub
		Name dn = buildDn(system);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(system, context);
		ldapTemplate.bind(dn, context, null);
	}
	
	public void update(System system) {
		// TODO Auto-generated method stub
		Name dn = buildDn(system);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(system, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}
	
	/* 
	 * 删除部门，如果部门下存在人员信息需要将其及其子部门下所有人员转移
	 * @see net.shopin.ldap.dao.SystemDao#delete(net.shopin.ldap.entity.System)
	 */
	public void remove(System system) {
		ldapTemplate.unbind(buildDn(system));
	}
	
	public void deleteByDN(String systemDN) {
		
		if(systemDN.contains(PropertiesUtil.getProperties("base"))){
			systemDN = systemDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(systemDN);
		context.setAttributeValue("status", System.SATAL_NOT_NORMAL.toString());
		ldapTemplate.modifyAttributes(systemDN, context.getModificationItems());
	}
	
	private Name buildDn(System system) {
		return new DistinguishedName("cn=" + system.getCn() + ",ou=systems");
	}

	public System findByDN(String systemDN) {

		if(systemDN.contains(PropertiesUtil.getProperties("base"))){
			systemDN = systemDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		
		return (System)ldapTemplate.lookup(systemDN, new SystemContextMapper());
		
	}

	public List<System> findSubSystemsByParentDN(String parentDN) {
		
		if(parentDN.contains(PropertiesUtil.getProperties("base"))){
			parentDN = parentDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}

		SearchControls controls  = new SearchControls();
		controls.setCountLimit(Integer.MAX_VALUE);
		controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		controls.setReturningObjFlag(true);
		String filter=null;
		filter="(&(objectClass=shopin-system)(status=0)|(displayName=*))";
		List<System> depts = ldapTemplate.search(parentDN, filter, controls, getContextMapper());
		return depts;
	}

	private void mapToContext(System system, DirContextAdapter context) {
		
		context.setAttributeValues("objectclass", new String[] { "top", "shopin-system"});
		context.setAttributeValue("displayName", system.getDisplayName());
		context.setAttributeValue("description", StringUtils.isNotEmpty(system.getDescription()) ? system.getDescription() : null);
		context.setAttributeValue("status", system.getStatus().toString());
		context.setAttributeValue("o", StringUtils.isNotEmpty(system.getO()) ? system.getO() : null);
		context.setAttributeValues("systemOccupant", system.getSystemOccupant());
	}
	
	public List<System> findSystemsByParam(String param) {

		String filter=null;
		if(StringUtils.isNotEmpty(param)){
			filter="(&(objectClass=shopin-system)(status=0)(displayName=*" + param + "*))";
		}else{
			filter="(&(objectClass=shopin-system)(status=0)(displayName=*))";
		}
		List<System> systems = ldapTemplate.search("ou=systems", filter, getContextMapper());

		return systems;
	}
	
	public List<System> findSystemsByParam(String param, String userDN, boolean isRelation) {

		String filter=null;
		if(param != null && !param.equals("")){
			if(isRelation){
				filter="(&(objectClass=shopin-system)&(status=0)|(cn=*" + param + "*)(displayName=*" + param + "*))";
			}else{
				filter="(&(objectClass=shopin-system)&(status=0)|(cn=*" + param + "*)(displayName=*" + param + "*))";
			}			
		}else{
			if(isRelation){
				filter="(&(objectClass=shopin-system)&(status=0)|(cn=*)(displayName=*))";
			}else{
				filter="(&(objectClass=shopin-system)&(status=0)|(cn=*)(displayName=*))";
			}
		}
		List<System> systems = ldapTemplate.search("ou=systems", filter, getContextMapper());

		return systems;
	}
	
	public ContextMapper getContextMapper() {
		return new SystemContextMapper();
	}
	
	private static class SystemContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			System system = new System();
			//system.setDn(context.getDn().toString());
			system.setDn(context.getDn().toString() + (StringUtils.isNotEmpty(PropertiesUtil.getProperties("base")) ? ',' + PropertiesUtil.getProperties("base") : ""));
			system.setCn(context.getStringAttribute("cn"));
			system.setDisplayName(context.getStringAttribute("displayName"));
			system.setDescription(context.getStringAttribute("description"));
			if(context.getStringAttribute("status")!=null)
				system.setStatus(Integer.valueOf(context.getStringAttribute("status")));
			system.setSystemOccupant(context.getStringAttributes("systemOccupant"));
			return system;
		}
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public List<Role> listMembers(String systemDN) {
		
		if(systemDN.contains(PropertiesUtil.getProperties("base"))){
			systemDN = systemDN.replace(("," + PropertiesUtil.getProperties("base")), "");
		}
		
		List<Role> roleList = new ArrayList();
		System system = (System)ldapTemplate.lookup(systemDN, new SystemContextMapper());
		String [] systemOccupants = system.getSystemOccupant();
		RoleDao roleDao = (RoleDao) SpringContextUtils.getBean("roleDao");
		if(systemOccupants!=null){
			for(String member : systemOccupants){
				Role role = roleDao.findByDN(member.contains(PropertiesUtil.getProperties("base")) ? member.replace(("," + PropertiesUtil.getProperties("base")), "") : member);
				if(role!=null){
					roleList.add(role);
				}
			}
		}
		return roleList;
	}
	

}
