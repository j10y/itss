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
	public void delete(Department department) {
		// TODO Auto-generated method stub
		ldapTemplate.unbind(buildDn(department));
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
				dn = new DistinguishedName(sameLevelLastDn.substring(0, sameLevelLastDn.length()-2)+ (new Integer(sameLevelLastDn.substring(sameLevelLastDn.length()-2)).intValue()+1));
				dn.prepend(new DistinguishedName(parentDN));
			}
		}
		return dn;
	}

	/*
	 * (non-Javadoc)
	 * @see net.shopin.ldap.dao.DeptDao#delete(net.shopin.ldap.entity.Department)
	 */
	public Department findByPrimaryKey(String deptNo) {

		return null;
	}

	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DeptDao#findSubDeptsByParentNo(java.lang.String)
	 */
	public List<Department> findSubDeptsByParentNo(String parentNo) {
		// TODO Auto-generated method stub
		return null;
	}

	private void mapToContext(Department department, DirContextAdapter context) {
		context.setAttributeValues("objectclass", new String[] { "top", "organizationalUnit", "extensibleObject"});
		context.setAttributeValue("description", department.getDeptName());
	}
	
	/* (non-Javadoc)
	 * @see net.shopin.ldap.dao.DeptDao#findDeptsByParam(java.lang.String)
	 */
	public List<Department> findDeptsByParam(String param) {
		// TODO Auto-generated method stub
		String filters = null;
		DirContextAdapter context = new DirContextAdapter(DistinguishedName.EMPTY_PATH);
		String filter=null;
		if(param != null && !param.equals("")){
			filter="(|(ou=*" + param + "*)(description=*"+ param + "*))";
		}else{
			filter="(|(ou=*)(description=*))";
		}
		List<Department> depts = ldapTemplate.search("o=orgnizations", filter, getContextMapper());

		return depts.size()> 20 ? depts.subList(0, 21) : depts;
	}
	
	private ContextMapper getContextMapper() {
		// TODO Auto-generated method stub
		return new DeptContextMapper();
	}
	
	private static class DeptContextMapper implements ContextMapper {

		public Object mapFromContext(Object ctx) {
			DirContextAdapter context = (DirContextAdapter) ctx;
			//DistinguishedName dn = new DistinguishedName(context.getDn());
			Department dept = new Department();
			dept.setDeptNo(context.getStringAttribute("ou"));
			dept.setDeptName(context.getStringAttribute("description"));
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
