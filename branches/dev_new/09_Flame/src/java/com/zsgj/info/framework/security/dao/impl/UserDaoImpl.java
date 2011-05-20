package com.zsgj.info.framework.security.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.security.dao.UserInfoDao;
import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.RoleAuthoriz;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.entity.UserRole;


@SuppressWarnings("deprecation")
public class UserDaoImpl extends BaseDao implements UserInfoDao {

	public void clearUser(UserInfo userInfo) {
		super.evict(userInfo);		
	}

	public UserInfo insertOrUpdateUser(UserInfo userInfo) {
		return (UserInfo) this.save(userInfo);
		
	}

	public UserInfo selectUserInfo(String userName, String password) {
		Criteria dc = this.getCriteria(UserInfo.class);
		dc.add(Restrictions.eq("userName", userName));
		dc.add(Restrictions.eq("password", password));
		UserInfo user = (UserInfo) dc.uniqueResult();
		return user;
	}

	public UserInfo selectUserByUserName(String userName) {
		Criteria dc = this.createCriteria(UserInfo.class);
		dc.add(Restrictions.ilike("userName", userName, MatchMode.EXACT));
		dc.setFetchMode("roles", FetchMode.JOIN);
		dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		UserInfo user = (UserInfo) dc.uniqueResult();
		//add by duxh in 20100521 for 有角色的用户，抓取角色的部门及父部门 ----begin----
		if(user!=null&&!user.getRoles().isEmpty()){
			Criteria c = this.createCriteria(UserInfo.class);
			c.add(Restrictions.ilike("userName", userName, MatchMode.EXACT));
			dc.createAlias("roles", "rol");
			dc.createAlias("rol.department", "dep");
			c.setFetchMode("roles", FetchMode.JOIN);
			dc.setFetchMode("rol.department", FetchMode.JOIN);
			dc.setFetchMode("dep.parentDepartment", FetchMode.JOIN);
			c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
			user = (UserInfo) dc.uniqueResult();
		}
		//add by duxh in 20100521 for 有角色的用户，抓取角色的部门及父部门 ----end----
		return user;
	}

	public List selectAllUsers() {
		List list = null;
		list = super.getAll(UserInfo.class);
		return list;
	}

	public List selectUserByRight(Right right) {
		String hql="select auth.id from Authorization auth where auth.right=?";
		Query query = super.createQuery(hql, new Object[]{right});
		List authIds = query.list();
		
		Criteria c = this.createCriteria(RoleAuthoriz.class);
		if(!authIds.isEmpty()){
			c.add(Restrictions.in("authorization.id", authIds));
		}
		c.setProjection(Projections.property("role"));
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List roleIds = c.list();
		
		Criteria c2 = this.createCriteria(UserRole.class);
		if(!roleIds.isEmpty()){
			c2.add(Restrictions.in("role", roleIds));
		}
		c2.setProjection(Projections.property("userInfo"));
		c2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List userInfos = c2.list();
		//session.close();
		
		return userInfos;
	}

	public List selectUserByAuthorization(Authorization authr) {
		Criteria c = this.createCriteria(RoleAuthoriz.class);
		c.add(Restrictions.eq("authorization", authr));
		c.setProjection(Projections.property("role"));
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List roleIds = c.list();
		
		Criteria c2 = this.createCriteria(UserRole.class);
		if(!roleIds.isEmpty()){
			c2.add(Restrictions.in("role", roleIds));
		}
		c2.setProjection(Projections.property("userInfo"));
		c2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List userInfos = c2.list();
		//session.close();
		
		return userInfos;
	}

}
