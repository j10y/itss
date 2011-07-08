package com.xpsoft.oa.dao.system.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.system.UserSubDao;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.UserSub;
import java.util.ArrayList;
import java.util.List;

public class UserSubDaoImpl extends BaseDaoImpl<UserSub> implements UserSubDao {
	public UserSubDaoImpl() {
		/* 16 */super(UserSub.class);
	}

	public List<Long> upUser(Long userId) {
		/* 21 */String hql = "from UserSub vo where vo.subAppUser.userId=?";
		/* 22 */Object[] objs = { userId };
		/* 23 */List<UserSub> list = findByHql(hql, objs);
		/* 24 */List idList = new ArrayList();
		/* 25 */for (UserSub sb : list) {
			/* 26 */idList.add(sb.getUserId());
		}
		/* 28 */return idList;
	}

	public List<Long> subUsers(Long userId) {
		/* 33 */String hql = "from UserSub vo where vo.userId=?";
		/* 34 */Object[] objs = { userId };
		/* 35 */List<UserSub> list = findByHql(hql, objs);
		/* 36 */List idList = new ArrayList();
		/* 37 */for (UserSub sb : list) {
			/* 38 */idList.add(sb.getSubAppUser().getUserId());
		}
		/* 40 */return idList;
	}
}
