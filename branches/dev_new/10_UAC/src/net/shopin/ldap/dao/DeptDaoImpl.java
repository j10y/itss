package net.shopin.ldap.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import net.shopin.ldap.entity.Department;
import net.shopin.ldap.entity.User;

import org.apache.commons.lang.StringUtils;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;

/**
 * @see net.shopin.ldap.dao.DeptDao
 * @author wchao
 *
 */
public class DeptDaoImpl implements DeptDao {

	private LdapTemplate ldapTemplate;
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DeptDao#create(net.shopin.ldap.entity.Department)
	 */
	public void create(Department department) {
		// TODO Auto-generated method stub
		Name dn = buildDn(department);
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(department, context);
		ldapTemplate.bind(dn, context, null);
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DeptDao#update(net.shopin.ldap.entity.Department)
	 */
	public void update(Department department) {
		// TODO Auto-generated method stub
		Name dn = buildDn(department);
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(department, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}
	
	/* 
	 * 删除部门，如果部门下存在人员信息需要将其及其子部门下所有人员转移
	 * @see net.shopin.ldap.dao.DeptDao#delete(net.shopin.ldap.entity.Department)
	 */
	public void remove(Department department) {
		ldapTemplate.unbind(buildDn(department));
	}
	
	public void deleteByRDN(String deptRDN) {
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(deptRDN);
		context.setAttributeValue("status", Department.SATAL_NOT_NORMAL);
		ldapTemplate.modifyAttributes(deptRDN, context.getModificationItems());
	}
	
	private Name buildDn(Department department) {
		// TODO Auto-generated method stub
		DistinguishedName dn = null;
		if(StringUtils.isNotEmpty(department.getDeptNo())){
			dn = new DistinguishedName(department.getDeptNo());
		}else{//添加子部门
			String parentDN = department.getParentNo();
			final List<String> children = new ArrayList();
			ldapTemplate.listBindings(parentDN,
					new AbstractContextMapper() {
						@Override
						protected Object doMapFromContext(DirContextOperations ctx) {
							DistinguishedName dn = (DistinguishedName) ctx.getDn();
							children.add(dn.toString());
							return null;
						}
					});
			if(children.size()==0){
				dn = new DistinguishedName(parentDN.split(",", -1)[0]+"01" + "," + parentDN);
			}else{
				Collections.sort(children,new Comparator(){
					public int compare(Object arg0, Object arg1) {
						// TODO Auto-generated method stub
						return -(arg0.toString().compareTo(arg1.toString()));
					}					
				});
				String sameLevelLastDn = children.get(0);
				dn = new DistinguishedName(sameLevelLastDn.substring(0, sameLevelLastDn.length()-2) + 
						((new Integer(sameLevelLastDn.substring(sameLevelLastDn.length()-2)).intValue()+1) < 10 ? "0" + (new Integer(sameLevelLastDn.substring(sameLevelLastDn.length()-2)).intValue()+1) : (new Integer(sameLevelLastDn.substring(sameLevelLastDn.length()-2)).intValue()+1)));
				dn.prepend(new DistinguishedName(parentDN));
			}
		}
		return dn;
	}

	/*
	 * (non-Javadoc)
	 * @see net.shopin.ldap.dao.DeptDao#delete(net.shopin.ldap.entity.Department)
	 */
	public Department findByRDN(String deptRDN) {

		DirContextAdapter context = new DirContextAdapter(DistinguishedName.EMPTY_PATH);
		return (Department)ldapTemplate.lookup(deptRDN, new DeptContextMapper());
		
	}

	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DeptDao#findSubDeptsByParentNo(java.lang.String)
	 */
	public List<Department> findSubDeptsByParentRDN(String parentRDN) {
		// TODO Auto-generated method stub
		return ldapTemplate.listBindings(parentRDN, getContextMapper());
	}

	private void mapToContext(Department department, DirContextAdapter context) {

		//modified by awen for extend openldap's schema on 2011-11-21 begin
		
			/*context.setAttributeValues("objectclass", new String[] { "top", "organizationalUnit", "extensibleObject"});
			context.setAttributeValue("description", department.getDeptName());*/
		
		context.setAttributeValues("objectclass", new String[] { "top", "shopin-organization"});
		context.setAttributeValue("displayName", department.getDeptName());
		context.setAttributeValue("description", StringUtils.isNotEmpty(department.getDeptDesc()) ? department.getDeptDesc() : null);
		context.setAttributeValue("status", department.getStatus());
		context.setAttributeValue("displayOrder", department.getDisplayOrder());
		context.setAttributeValue("parentNo", StringUtils.isNotEmpty(department.getParentNo()) ? department.getParentNo() : null);		
		context.setAttributeValue("erpId", StringUtils.isNotEmpty(department.getErpId()) ? department.getErpId() : null);		
		//modified by awen for extend openldap's schema on 2011-11-21 end
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DeptDao#findDeptsByParam(java.lang.String)
	 */
	public List<Department> findDeptsByParam(String param) {
		// TODO Auto-generated method stub
		String filter=null;
		if(param != null && !param.equals("")){
			filter="(&(objectClass=shopin-organization)|(o=*" + param + "*)(displayName=*" + param + "*)(description=*"+ param + "*))";
		}else{
			filter="(&(objectClass=shopin-organization)|(o=*)(displayName=*)(description=*))";
		}
		List<Department> depts = ldapTemplate.search("ou=orgnizations", filter, getContextMapper());

		return depts;
	}
	
	public ContextMapper getContextMapper() {
		// TODO Auto-generated method stub
		return new DeptContextMapper();
	}
	
	private static class DeptContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			//DistinguishedName dn = new DistinguishedName(context.getDn());
			Department dept = new Department();
			dept.setDeptNo(context.getStringAttribute("o"));
			dept.setDeptName(context.getStringAttribute("displayName"));
			dept.setDeptDesc(context.getStringAttribute("description"));
			if(context.getStringAttribute("displayOrder")!=null)
				dept.setDisplayOrder(Integer.valueOf(context.getStringAttribute("displayOrder")));
			if(context.getStringAttribute("status")!=null)
				dept.setStatus(Integer.valueOf(context.getStringAttribute("status")));
			dept.setErpId(context.getStringAttribute("erpId"));
			dept.setParentNo(context.getStringAttribute("parentNo"));
			return dept;
		}
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

}
