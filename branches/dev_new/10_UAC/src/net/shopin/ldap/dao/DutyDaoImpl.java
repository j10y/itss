package net.shopin.ldap.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.SearchControls;

import net.shopin.ldap.entity.Duty;

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
public class DutyDaoImpl implements DutyDao {

	private LdapTemplate ldapTemplate;
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DutyDao#create(net.shopin.ldap.entity.Duty)
	 */
	public void create(Duty duty) {
		// TODO Auto-generated method stub
		Name dn = buildDn(duty);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(duty, context);
		ldapTemplate.bind(dn, context, null);
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DutyDao#update(net.shopin.ldap.entity.Duty)
	 */
	public void update(Duty duty) {
		// TODO Auto-generated method stub
		Name dn = buildDn(duty);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(duty, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}
	
	/* 
	 * 删除部门，如果部门下存在人员信息需要将其及其子部门下所有人员转移
	 * @see net.shopin.ldap.dao.DutyDao#delete(net.shopin.ldap.entity.Duty)
	 */
	public void remove(Duty duty) {
		ldapTemplate.unbind(buildDn(duty));
	}
	
	public void deleteByRDN(String dutyRDN) {
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dutyRDN);
		context.setAttributeValue("status", Duty.SATAL_NOT_NORMAL.toString());
		ldapTemplate.modifyAttributes(dutyRDN, context.getModificationItems());
	}
	
	private Name buildDn(Duty duty) {
		return new DistinguishedName("cn=" + duty.getCn() + ",ou=duties");
	}

	/*
	 * (non-Javadoc)
	 * @see net.shopin.ldap.dao.DutyDao#delete(net.shopin.ldap.entity.Duty)
	 */
	public Duty findByRDN(String dutyRDN) {

		return (Duty)ldapTemplate.lookup(dutyRDN, new GroupContextMapper());
		
	}

	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DutyDao#findSubDeptsByParentNo(java.lang.String)
	 */
	public List<Duty> findSubDutysByParentRDN(String parentRDN) {
		// TODO Auto-generated method stub
		//return ldapTemplate.listBindings(parentRDN, getContextMapper());
		SearchControls controls  = new SearchControls();
		controls.setCountLimit(Integer.MAX_VALUE);
		controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		controls.setReturningObjFlag(true);
		String filter=null;
		filter="(&(objectClass=shopin-duty)(status=0)|(title=*))";
		List<Duty> depts = ldapTemplate.search(parentRDN, filter, controls, getContextMapper());
		return depts;
	}

	private void mapToContext(Duty duty, DirContextAdapter context) {
		
		context.setAttributeValues("objectclass", new String[] { "top", "shopin-duty"});
		context.setAttributeValue("titile", duty.getTitle());
		context.setAttributeValue("description", StringUtils.isNotEmpty(duty.getDescription()) ? duty.getDescription() : null);
		context.setAttributeValue("status", duty.getStatus().toString());
		context.setAttributeValue("o", StringUtils.isNotEmpty(duty.getO()) ? duty.getO() : null);
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DutyDao#findDeptsByParam(java.lang.String)
	 */
	public List<Duty> findDutysByParam(String param) {
		// TODO Auto-generated method stub
		String filter=null;
		if(param != null && !param.equals("")){
			filter="(&(objectClass=shopin-duty)&(status=0)|(cn=*" + param + "*)(title=*" + param + "*))";
		}else{
			filter="(&(objectClass=shopin-duty)&(status=0)|(cn=*)(title=*))";
		}
		List<Duty> groups = ldapTemplate.search("ou=duties", filter, getContextMapper());

		return groups;
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DutyDao#findDeptsByParam(java.lang.String)
	 */
	public List<Duty> findDutysByParam(String param, String userRDN, boolean isRelation) {
		// TODO Auto-generated method stub
		String filter=null;
		if(param != null && !param.equals("")){
			if(isRelation){
				filter="(&(objectClass=shopin-duty)&(status=0)|(cn=*" + param + "*)(title=*" + param + "*))";
			}else{
				filter="(&(objectClass=shopin-duty)&(status=0)|(cn=*" + param + "*)(title=*" + param + "*))";
			}			
		}else{
			if(isRelation){
				filter="(&(objectClass=shopin-duty)&(status=0)|(cn=*)(title=*))";
			}else{
				filter="(&(objectClass=shopin-duty)&(status=0)|(cn=*)(title=*))";
			}
		}
		List<Duty> groups = ldapTemplate.search("ou=duties", filter, getContextMapper());

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
			Duty group = new Duty();
			group.setCn(context.getStringAttribute("cn"));
			group.setTitle(context.getStringAttribute("title"));
			group.setDescription(context.getStringAttribute("description"));
			if(context.getStringAttribute("status")!=null)
				group.setStatus(Integer.valueOf(context.getStringAttribute("status")));
			context.getDn();
			return group;
		}
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

}
