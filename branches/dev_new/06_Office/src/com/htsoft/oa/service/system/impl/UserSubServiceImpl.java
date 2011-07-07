package com.htsoft.oa.service.system.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.oa.dao.system.UserSubDao;
import com.htsoft.oa.model.system.UserSub;
import com.htsoft.oa.service.system.UserSubService;

public class UserSubServiceImpl extends BaseServiceImpl<UserSub> implements
		UserSubService {
	private UserSubDao dao;

	public UserSubServiceImpl(UserSubDao dao) {
		/* 21 */super(dao);
		/* 22 */this.dao = dao;
	}

	public Set<Long> findAllUpUser(Long userId) {
		/* 27 */List<Long> list = this.dao.upUser(userId);
		/* 28 */Set set = new HashSet();
		/* 29 */for (Long l : list) {
			/* 30 */set.add(l);
			/* 31 */List<Long> newlist = this.dao.upUser(l);
			/* 32 */Set sets = new HashSet();
			/* 33 */for (Long lon : newlist) {
				/* 34 */set.add(lon);
				/* 35 */sets.add(lon);
			}
			/* 37 */findUp(set, sets);
		}
		/* 39 */return set;
	}

	public void findUp(Set<Long> setOld, Set<Long> setNew) {
		/* 49 */Iterator it = setNew.iterator();
		/* 50 */while (it.hasNext()) {
			/* 51 */Long userId = (Long) it.next();
			/* 52 */List<Long> newlist = this.dao.upUser(userId);
			/* 53 */setOld.add(userId);
			/* 54 */Set set = new HashSet();
			/* 55 */for (Long lon : newlist) {
				/* 56 */if (!setOld.contains(lon)) {
					/* 57 */set.add(lon);
				}
			}
			/* 60 */findUp(setOld, set);
		}
	}

	public List<Long> subUsers(Long userId) {
		/* 66 */return this.dao.subUsers(userId);
	}

	public List<Long> upUser(Long userId) {
		/* 71 */return this.dao.upUser(userId);
	}
}