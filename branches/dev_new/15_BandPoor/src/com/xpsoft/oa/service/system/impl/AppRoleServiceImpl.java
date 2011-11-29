package com.xpsoft.oa.service.system.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.AppRoleDao;
import com.xpsoft.oa.model.system.AppRole;
import com.xpsoft.oa.service.system.AppRoleService;
import java.util.HashMap;
import java.util.Set;

public class AppRoleServiceImpl extends BaseServiceImpl<AppRole> implements
		AppRoleService {
	private AppRoleDao dao;

	public AppRoleServiceImpl(AppRoleDao dao) {
		super(dao);
		this.dao = dao;
	}

	public AppRole getByRoleName(String roleName) {
		return this.dao.getByRoleName(roleName);
	}

	public HashMap<String, Set<String>> getSecurityDataSource() {
		return this.dao.getSecurityDataSource();
	}
}
