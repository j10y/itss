package com.xpsoft.oa.dao.system.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.xpsoft.core.Constants;
import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.system.AppUserDao;
import com.xpsoft.oa.model.system.AppRole;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

public class AppUserDaoImpl extends BaseDaoImpl<AppUser> implements AppUserDao,
		UserDetailsService {
	
	public AppUserDaoImpl() {
		super(AppUser.class);
	}

	public AppUser findByUserName(String username) {
		String hql = "from AppUser au where au.username=?";
		Object[] params = { username };
		List list = findByHql(hql, params);
		AppUser user = null;
		if (list.size() != 0) {
			if (!AppUtil.getSysConfig().get("limit").toString().equals("-1")) {
				user = (AppUser) list.get(0);
				String hql2 = "select count(*) from AppUser";
				Object obj = findUnique(hql2, null);
				//if (new Integer(obj.toString()).intValue() > 11) {
				if (new Integer(obj.toString()).intValue() > new Integer(
						AppUtil.getSysConfig().get("limit").toString())
						.intValue()) {
					user.setStatus(Short.valueOf((short) 0));
				}
			}
		}

		return user;
	}
	
	public AppUser findByFullName(String fullName) {
		String hql = "from AppUser au where au.fullname=?";
		Object[] params = { fullName };
		List list = findByHql(hql, params);
		AppUser user = null;
		if (list.size() != 0) {
			if (!AppUtil.getSysConfig().get("limit").toString().equals("-1")) {
				user = (AppUser) list.get(0);
				String hql2 = "select count(*) from AppUser";
				Object obj = findUnique(hql2, null);
				if (new Integer(obj.toString()).intValue() > new Integer(
						AppUtil.getSysConfig().get("limit").toString())
						.intValue()) {
					user.setStatus(Short.valueOf((short) 0));
				}
			}
		}

		return user;
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		final String uName = username;
		AppUser user = (AppUser) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from AppUser ap where ap.username=? and ap.delFlag = ?";
				Query query = session.createQuery(hql);
				query.setString(0, uName);
				query.setShort(1, Constants.FLAG_UNDELETED.shortValue());
				AppUser user = null;
				try {
					user = (AppUser) query.uniqueResult();

					if (user != null) {
						Hibernate.initialize(user.getRoles());
						Hibernate.initialize(user.getDepartment());

						Set roleSet = user.getRoles();
						Iterator it = roleSet.iterator();

						while (it.hasNext()) {
							AppRole role = (AppRole) it.next();
							if (role.getRoleId().equals(
									AppRole.SUPER_ROLEID)) {
								user.getRights().clear();
								user.getRights().add("__ALL");
								break;
							}
							if (StringUtils.isNotEmpty(role.getRights())) {
								String[] items = role.getRights()
										.split("[,]");
								for (int i = 0; i < items.length; i++) {
									if (!user.getRights().contains(
											items[i])) {
										user.getRights().add(items[i]);
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					AppUserDaoImpl.this.logger.warn("user:"
							+ uName +
							" can't not loding rights:" +
							ex.getMessage());
				}
				
				if(user == null)
					throw new UsernameNotFoundException(uName + " 不存在，请申请该系统使用权限！");
				return user;
			}
		});
		String hql2 = "select count(*) from AppUser";
		Object obj = findUnique(hql2, null);
		if (!AppUtil.getSysConfig().get("limit").toString().equals("-1")) {
			//if (new Integer(obj.toString()).intValue() > new Integer(AppUtil.getSysConfig().get("limit").toString())) {
			if (new Integer(obj.toString()).intValue() > new Integer(AppUtil
					.getSysConfig().get("limit").toString()).intValue()) {
				user.setStatus(Short.valueOf((short) 0));
			}
		}
		return user;
	}

	public List findByDepartment(String path, PagingBean pb) {
		List list = new ArrayList();
		String hql = new String();
		if ("0.".equals(path)) {
			hql = "from AppUser vo2 where vo2.delFlag = ?";
			list.add(Constants.FLAG_UNDELETED);
		} else {
			hql = "select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department and vo1.path like ? and vo2.delFlag = ?";
			list.add(path + "%");
			list.add(Constants.FLAG_UNDELETED);
		}
		return findByHql(hql, list.toArray(), pb);
	}

	public List findByDepartment(Department department) {
		String hql = "select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department and vo1.path like ? and vo2.delFlag = ?";
		Object[] params = { department.getPath() + "%",
				Constants.FLAG_UNDELETED };
		return findByHql(hql, params);
	}

	public List findByRole(Long roleId) {
		String hql = "select vo from AppUser vo join vo.roles roles where roles.roleId=? and vo.delFlag = ?";
		Object[] objs = { roleId, Constants.FLAG_UNDELETED };
		return findByHql(hql, objs);
	}

	public List findByRole(Long roleId, PagingBean pb) {
		String hql = "select vo from AppUser vo join vo.roles roles where roles.roleId=? and vo.delFlag = ?";
		Object[] objs = { roleId, Constants.FLAG_UNDELETED };
		return findByHql(hql, objs, pb);
	}

	public List<AppUser> findByDepartment(String path) {
		String hql = "select vo2 from Department vo1,AppUser vo2 where vo1.depId=vo2.depId and vo1.path like ? and vo2.delFlag =?";
		Object[] params = { path + "%", Constants.FLAG_UNDELETED };
		return findByHql(hql, params);
	}

	public List findByRoleId(Long roleId) {
		String hql = "select vo from AppUser vo join vo.roles as roles where roles.roleId=? and vo.delFlag =?";
		return findByHql(hql, new Object[] { roleId,
				Constants.FLAG_UNDELETED });
	}

	public List findByUserIds(Long[] userIds) {
		String hql = "select vo from AppUser vo where vo.delFlag=? ";

		if ((userIds == null) || (userIds.length == 0))
			return null;
		hql = hql + " where vo.userId in (";
		int i = 0;
		for (Long userId : userIds) {
			if (i++ > 0) {
				hql = hql + ",";
			}
			hql = hql + "?";
		}
		hql = hql + " )";

		return findByHql(hql, new Object[] { Constants.FLAG_UNDELETED,
				userIds });
	}

	public List<AppUser> findSubAppUser(String path, Set<Long> userIds,
			PagingBean pb) {
		String st = "";
		if (userIds.size() > 0) {
			Iterator it = userIds.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				sb.append(((Long) it.next()).toString() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			st = sb.toString();
		}

		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		if (path != null) {
			hql.append("select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department ");
			hql.append(" and vo1.path like ?");
			list.add(path + "%");
		} else {
			hql.append("from AppUser vo2 where 1=1 ");
		}
		if (st != "") {
			hql.append(" and vo2.userId not in (" + st + ")");
		}
		hql.append(" and vo2.delFlag = ?");
		list.add(Constants.FLAG_UNDELETED);
		return findByHql(hql.toString(), list.toArray(), pb);
	}

	public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds,
			PagingBean pb) {
		String st = "";
		if (userIds.size() > 0) {
			Iterator it = userIds.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				sb.append(((Long) it.next()).toString() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			st = sb.toString();
		}
		StringBuffer hql = new StringBuffer(
				"select vo from AppUser vo join vo.roles roles where roles.roleId=?");
		List list = new ArrayList();
		list.add(roleId);
		if (st != "") {
			hql.append(" and vo.userId not in (" + st + ")");
		}
		hql.append(" and vo.delFlag =?");
		list.add(Constants.FLAG_UNDELETED);
		return findByHql(hql.toString(), list.toArray(), pb);
	}

	public List<AppUser> findByDepId(Long depId) {
		String hql = "from AppUser vo where vo.delFlag=0 and vo.department.depId=?";
		Object[] objs = { depId };
		return findByHql(hql, objs);
	}
	
	public List<AppUser> findByDepIdAndPostName(Long depId, String postName) {
		final String hql = "from AppUser where department.depId=? and position=?";
		final long item1 = depId;
		final String item2 = postName;
		
		return (List)this.getHibernateTemplate().execute(
			new HibernateCallback() {
				public Object doInHibernate(Session session)
				throws HibernateException, SQLException {
					Query query = session.createQuery(hql);
					query.setLong(0, item1);
					query.setString(1, item2);
					return query.list();
				}
			}
		);
	}
	
	public List findByRoleIds(Long[] roleIds) {
		String ids = "";
		for(int i=0; i< roleIds.length; i++){
			ids += roleIds[i];
			if(i<roleIds.length-1){
				ids += ",";				
			}
		}
		String hql = "select vo from AppUser vo join vo.roles as roles where roles.roleId in ("+ids+") and vo.delFlag =?";
		return findByHql(hql, new Object[] {
				Constants.FLAG_UNDELETED });
	}
	
	public AppUser getByUserName(String username) {
		String hql = "from AppUser au where au.username=?";
		Object[] params = { username };
		List list = findByHql(hql, params);
		AppUser user = null;
		if (list.size() != 0) {
			user = (AppUser) list.get(0);
		}

		return user;
	}

}
