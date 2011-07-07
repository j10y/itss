package com.htsoft.oa.dao.system.impl;

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
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.htsoft.core.Constants;
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.dao.system.AppUserDao;
import com.htsoft.oa.model.system.AppRole;
import com.htsoft.oa.model.system.AppUser;
import com.htsoft.oa.model.system.Department;

public class AppUserDaoImpl extends BaseDaoImpl<AppUser> implements AppUserDao,
		UserDetailsService {
	public AppUserDaoImpl() {
		/* 35 */super(AppUser.class);
	}

	public AppUser findByUserName(String username) {
		/* 40 */String hql = "from AppUser au where au.username=?";
		/* 41 */Object[] params = { username };
		/* 42 */List list = findByHql(hql, params);
		/* 43 */AppUser user = null;
		/* 44 */if (list.size() != 0) {
			/* 45 */user = (AppUser) list.get(0);
			/* 46 */String hql2 = "select count(*) from AppUser";
			/* 47 */Object obj = findUnique(hql2, null);
			/* 48 */if (new Integer(obj.toString()).intValue() > 11) {
				/* 49 */user.setStatus(Short.valueOf((short) 0));
			}
		}

		/* 53 */return user;
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		final String uName = username;
		AppUser user = (AppUser) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				/* 64 */String hql = "from AppUser ap where ap.username=? and ap.delFlag = ?";
				/* 65 */Query query = session.createQuery(hql);
				/* 66 */query.setString(0, uName);
				/* 67 */query.setShort(1, Constants.FLAG_UNDELETED.shortValue());
				/* 68 */AppUser user = null;
				try {
					/* 71 */user = (AppUser) query.uniqueResult();

					/* 73 */if (user != null) {
						/* 74 */Hibernate.initialize(user.getRoles());
						/* 75 */Hibernate.initialize(user.getDepartment());

						/* 78 */Set roleSet = user.getRoles();
						/* 79 */Iterator it = roleSet.iterator();

						/* 81 */while (it.hasNext()) {
							/* 82 */AppRole role = (AppRole) it.next();
							/* 83 */if (role.getRoleId().equals(
									AppRole.SUPER_ROLEID)) {
								/* 84 */user.getRights().clear();
								/* 85 */user.getRights().add("__ALL");
								/* 86 */break;
							}
							/* 88 */if (StringUtils.isNotEmpty(role.getRights())) {
								/* 89 */String[] items = role.getRights()
										.split("[,]");
								/* 90 */for (int i = 0; i < items.length; i++) {
									/* 91 */if (!user.getRights().contains(
											items[i])) {
										/* 92 */user.getRights().add(items[i]);
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					/* 101 */AppUserDaoImpl.this.logger.warn("user:"
							+ uName +
							/* 102 */" can't not loding rights:" +
							/* 103 */ex.getMessage());
				}
				/* 105 */return user;
			}
		});
		/* 108 */String hql2 = "select count(*) from AppUser";
		/* 109 */Object obj = findUnique(hql2, null);
		/* 110 */if (new Integer(obj.toString()).intValue() > 11) {
			/* 111 */user.setStatus(Short.valueOf((short) 0));
		}
		/* 113 */return user;
	}

	public List findByDepartment(String path, PagingBean pb) {
		/* 121 */List list = new ArrayList();
		/* 122 */String hql = new String();
		/* 123 */if ("0.".equals(path)) {
			/* 124 */hql = "from AppUser vo2 where vo2.delFlag = ?";
			/* 125 */list.add(Constants.FLAG_UNDELETED);
		} else {
			/* 127 */hql = "select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department and vo1.path like ? and vo2.delFlag = ?";
			/* 128 */list.add(path + "%");
			/* 129 */list.add(Constants.FLAG_UNDELETED);
		}
		/* 131 */return findByHql(hql, list.toArray(), pb);
	}

	public List findByDepartment(Department department) {
		/* 136 */String hql = "select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department and vo1.path like ? and vo2.delFlag = ?";
		/* 137 */Object[] params = { department.getPath() + "%",
				Constants.FLAG_UNDELETED };
		/* 138 */return findByHql(hql, params);
	}

	public List findByRole(Long roleId) {
		/* 143 */String hql = "select vo from AppUser vo join vo.roles roles where roles.roleId=? and vo.delFlag = ?";
		/* 144 */Object[] objs = { roleId, Constants.FLAG_UNDELETED };
		/* 145 */return findByHql(hql, objs);
	}

	public List findByRole(Long roleId, PagingBean pb) {
		/* 150 */String hql = "select vo from AppUser vo join vo.roles roles where roles.roleId=? and vo.delFlag = ?";
		/* 151 */Object[] objs = { roleId, Constants.FLAG_UNDELETED };
		/* 152 */return findByHql(hql, objs, pb);
	}

	public List<AppUser> findByDepartment(String path) {
		/* 157 */String hql = "select vo2 from Department vo1,AppUser vo2 where vo1.depId=vo2.depId and vo1.path like ? and vo2.delFlag =?";
		/* 158 */Object[] params = { path + "%", Constants.FLAG_UNDELETED };
		/* 159 */return findByHql(hql, params);
	}

	public List findByRoleId(Long roleId) {
		/* 163 */String hql = "select vo from AppUser vo join vo.roles as roles where roles.roleId=? and vo.delFlag =?";
		/* 164 */return findByHql(hql, new Object[] { roleId,
				Constants.FLAG_UNDELETED });
	}

	public List findByUserIds(Long[] userIds) {
		/* 168 */String hql = "select vo from AppUser vo where vo.delFlag=? ";

		/* 170 */if ((userIds == null) || (userIds.length == 0))
			return null;
		/* 171 */hql = hql + " where vo.userId in (";
		/* 172 */int i = 0;
		/* 173 */for (Long userId : userIds) {
			/* 174 */if (i++ > 0) {
				/* 175 */hql = hql + ",";
			}
			/* 177 */hql = hql + "?";
		}
		/* 179 */hql = hql + " )";

		/* 181 */return findByHql(hql, new Object[] { Constants.FLAG_UNDELETED,
				userIds });
	}

	public List<AppUser> findSubAppUser(String path, Set<Long> userIds,
			PagingBean pb) {
		/* 186 */String st = "";
		/* 187 */if (userIds.size() > 0) {
			/* 188 */Iterator it = userIds.iterator();
			/* 189 */StringBuffer sb = new StringBuffer();
			/* 190 */while (it.hasNext()) {
				/* 191 */sb.append(((Long) it.next()).toString() + ",");
			}
			/* 193 */sb.deleteCharAt(sb.length() - 1);
			/* 194 */st = sb.toString();
		}

		/* 197 */List list = new ArrayList();
		/* 198 */StringBuffer hql = new StringBuffer();
		/* 199 */if (path != null) {
			/* 200 */hql
					.append("select vo2 from Department vo1,AppUser vo2 where vo1=vo2.department ");
			/* 201 */hql.append(" and vo1.path like ?");
			/* 202 */list.add(path + "%");
		} else {
			/* 204 */hql.append("from AppUser vo2 where 1=1 ");
		}
		/* 206 */if (st != "") {
			/* 207 */hql.append(" and vo2.userId not in (" + st + ")");
		}
		/* 209 */hql.append(" and vo2.delFlag = ?");
		/* 210 */list.add(Constants.FLAG_UNDELETED);
		/* 211 */return findByHql(hql.toString(), list.toArray(), pb);
	}

	public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds,
			PagingBean pb) {
		/* 217 */String st = "";
		/* 218 */if (userIds.size() > 0) {
			/* 219 */Iterator it = userIds.iterator();
			/* 220 */StringBuffer sb = new StringBuffer();
			/* 221 */while (it.hasNext()) {
				/* 222 */sb.append(((Long) it.next()).toString() + ",");
			}
			/* 224 */sb.deleteCharAt(sb.length() - 1);
			/* 225 */st = sb.toString();
		}
		/* 227 */StringBuffer hql = new StringBuffer(
				"select vo from AppUser vo join vo.roles roles where roles.roleId=?");
		/* 228 */List list = new ArrayList();
		/* 229 */list.add(roleId);
		/* 230 */if (st != "") {
			/* 231 */hql.append(" and vo.userId not in (" + st + ")");
		}
		/* 233 */hql.append(" and vo.delFlag =?");
		/* 234 */list.add(Constants.FLAG_UNDELETED);
		/* 235 */return findByHql(hql.toString(), list.toArray(), pb);
	}

	public List<AppUser> findByDepId(Long depId) {
		/* 240 */String hql = "from AppUser vo where vo.delFlag=0 and vo.department.depId=?";
		/* 241 */Object[] objs = { depId };
		/* 242 */return findByHql(hql, objs);
	}
}
