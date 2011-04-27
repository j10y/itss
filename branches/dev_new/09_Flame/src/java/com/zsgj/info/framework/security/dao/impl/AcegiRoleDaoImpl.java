package com.zsgj.info.framework.security.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.security.dao.AcegiRoleDao;
import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Resource;
import com.zsgj.info.framework.security.entity.ResourceDetail;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.RoleAuthoriz;
import com.zsgj.info.framework.security.entity.UserInfo;

public class AcegiRoleDaoImpl extends BaseDao implements AcegiRoleDao {
	
	
	public List selectResourcesByRole(Role role) {
		List list = new ArrayList();
		Criteria c = this.getCriteria(RoleAuthoriz.class);
		c.add(Restrictions.eq("role", role));
		c.setFetchMode("resource", FetchMode.JOIN);
		String hql="select distinct ra.authorization from RoleAuthoriz ra where ra.role=?";
		List auths = this.find(hql, new Object[]{role});
		Iterator iter = auths.iterator();
		while(iter.hasNext()){
			Authorization auth = (Authorization) iter.next();
			Resource resource = auth.getResource();
			list.add(resource);
		}
		return list;
	}


	/**
	 * 由role转为GrantedAuthority
	 */
	private static GrantedAuthority[] role2authorities(Collection roleNames) {
		List authorities = new ArrayList();
		for (Iterator iter = roleNames.iterator(); iter.hasNext();) {
			String roleName = (String) iter.next();
			GrantedAuthority g = new GrantedAuthorityImpl(roleName);//ROLE_开始的角色名称
			authorities.add(g);
		}
		return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[0]);
	}
	
	
	public GrantedAuthority[] selectAllRolesForSysman() {
		List roleNames = new ArrayList();
		//DetachedCriteria dc = DetachedCriteria.forClass(Right.class);
		//dc.add(Restrictions.isNotNull("keyName"));
		//List list = getDaoSupport().selectByCriteria(dc);
		String hql = "from Right r where r.keyName is not null";
		Session session = this.getSession();
		Query query = super.createQuery(session, hql, null);
		query.setCacheable(true);
		List list =query.list();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			Right item = (Right)iter.next();
			String keyName = item.getKeyName();
			roleNames.add(keyName);
		}
		//System.out.println("###-- close selectAllRolesForSysman----"+ session.hashCode());
		//session.close();
		return role2authorities(roleNames);
	}


	public GrantedAuthority[] selectAcegiRoleNamesByUser(UserInfo user) {
		Session session = this.getSession();
		Set authories = new HashSet();
		Set roles = user.getRoles();
		Iterator iter = roles.iterator();
		while(iter.hasNext()){
			Role role = (Role) iter.next();
//			DetachedCriteria dcr = DetachedCriteria.forClass(Role.class);
//			dcr.add(Restrictions.eq("id",role.getId()));
//			dcr.setFetchMode("authorizations", FetchMode.JOIN);
			
			StringBuffer buff=new StringBuffer();
			buff.append("select distinct r from Role r ");
			buff.append("left outer join fetch r.authorizations ");
			buff.append("where r.id=? ");
			
			Query query = super.createQuery(session, buff.toString(), new Object[]{role.getId()});
			query.setCacheable(true);
			Role role2 = (Role) query.uniqueResult();
			//Role role2 = (Role) getDaoSupport().uniqueResult(dcr);
			Set tempSet = role2.getAuthorizations();
			authories.addAll(tempSet);
		}
		//----------------
		List roleNames = new ArrayList();
		Iterator itera = authories.iterator();
		while(itera.hasNext()){
			Authorization auth = (Authorization)itera.next();
			Right right = auth.getRight();
			if(right!=null) {
				roleNames.add(right.getKeyName());
			}
		}
		//System.out.println("###-- close selectAcegiRoleNamesByUser----"+ session.hashCode());
		//session.close();
		return role2authorities(roleNames);
	}


	public ResourceDetail selectResourceDetailByFunctionName(String functionName) {
		ResourceDetail detail = new ResourceDetail();
		List roles = new ArrayList();
	
		int lastDotIndex = functionName.lastIndexOf('.');
		String className = functionName.substring(0, lastDotIndex);
		String methodName = functionName.substring(lastDotIndex + 1);
		
		StringBuffer buff=new StringBuffer();
		buff.append("select distinct res from Resource res ");
		buff.append("left outer join fetch res.authorizations ");
		buff.append("where res.type='FUNCTION' ");
		buff.append("and res.className=? ");
		buff.append("and res.methodName=? ");
		Session session = this.getSession();
		Query query = super.createQuery(session, buff.toString(), new Object[]{className, methodName});
		query.setCacheable(true);
		Resource resource = (Resource) query.uniqueResult();
		
		Set set = resource.getAuthorizations();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			Authorization auth = (Authorization) iter.next();
			String keyName = auth.getRight().getKeyName();
			roles.add(keyName);
		}
		detail.setResString(resource.getName());
		detail.setResType(resource.getType());
		detail.setAuthorities(role2authorities(roles));
		System.out.println("###-- close selectResourceDetailByFunctionName----"+ session.hashCode());
		session.close();
		return detail;
	}
	
	

	public ResourceDetail selectResourceDetailByUrl(String url) {
		ResourceDetail detail = new ResourceDetail();
		List roles = new ArrayList();
	
		int lastDotIndex = url.lastIndexOf('/');
		String className = url.substring(0, lastDotIndex);
		String methodName = url.substring(lastDotIndex + 1);
		
		StringBuffer buff=new StringBuffer();
		buff.append("select distinct res from Resource res ");
		buff.append("left outer join fetch res.authorizations ");
		buff.append("where res.type='URL' ");
		buff.append("and res.className=? ");
		buff.append("and res.methodName=? ");
		Session session = this.getSession();
		Query query = super.createQuery(session, buff.toString(), new Object[]{className, methodName});
		query.setCacheable(true);
		Resource resource = (Resource) query.uniqueResult();

		Set set = resource.getAuthorizations();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			Authorization auth = (Authorization) iter.next();
			String keyName = auth.getRight().getKeyName();
			roles.add(keyName);
		}
		detail.setResString(resource.getName());
		detail.setResType(resource.getType());
		detail.setAuthorities(role2authorities(roles));
		System.out.println("###-- close selectResourceDetailByUrl----"+ session.hashCode());
		session.close();
		return detail;
	}


	public List selectResources() {
		List resources = new ArrayList();

		StringBuffer buff=new StringBuffer();
		buff.append("select res from Resource res ");
		//buff.append("where res.type='FUNCTION' ");
		Session session = this.getSession();
		Query query = super.createQuery(session,buff.toString(), null);
		query.setCacheable(true);
		List list = query.list();

		for(int i=0; i<list.size(); i++){
			Resource res = (Resource)list.get(i);
			String className = res.getClassName();
			String methodName = res.getMethodName();
			String type = res.getType();
			if(type.equalsIgnoreCase("FUNCTION")){
				resources.add(className+"."+methodName);
			}else{
				String url = className+(methodName!=null?"/"+methodName:"");
				resources.add(url);
			}
			
		}
		System.out.println("###-- close selectResources----"+ session.hashCode());
		session.close();
		return resources;
	}


	public List selectFunctionNames() {
		List funcs = new ArrayList();

		StringBuffer buff=new StringBuffer();
		buff.append("select res from Resource res ");
		buff.append("where res.type='FUNCTION' ");
		Session session = this.getSession();
		Query query = super.createQuery(session, buff.toString(), null);
		//query.setCacheable(true);
		List list = query.list();

		
		//System.out.println("###-- close selectFunctionNames not close----"+ session.hashCode());
		//session.close();
		return list;
	}

	public List selectUrls() {
		List urls = new ArrayList();
		
		StringBuffer buff=new StringBuffer();
		buff.append("select res from Resource res ");
		buff.append("where res.type='URL' ");
		Session session = this.getSession();
		Query query = super.createQuery(session, buff.toString(), null);
		//Query query = super.createQuery(buff.toString(), null);
		query.setCacheable(true);
		List list = query.list();
		
		for(int i=0; i<list.size(); i++){
			Resource res = (Resource)list.get(i);
			String className = res.getClassName();
			String methodName = res.getMethodName();

			String url = className+(methodName!=null?"/"+methodName:"");
			urls.add(url);
		}
		System.out.println("###-- close selectUrls ----"+ session.hashCode());
		session.close();
		return urls;
	}


	public Set selectResourcesByUser(UserInfo user) {
		Set authories = new HashSet();
		Set roles = user.getRoles();
		Iterator iterRole = roles.iterator();
		while(iterRole.hasNext()){
			Role role = (Role) iterRole.next();
			Set authes = role.getAuthorizations();
			authories.addAll(authes);
		}
		return authories;
	}

	public Resource insertOrUpdateResource(Resource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	public Resource selectResourceByModule(Module module) {
		// TODO Auto-generated method stub
		return null;
	}

	public Resource selectResourceByParam(String className, String methodName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Resource selectResourceByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Right selectRightByAcegiRoleKeyName(String acegiRoleKeyName) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
