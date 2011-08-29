package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.AppRole;
import java.util.HashMap;
import java.util.Set;

public abstract interface AppRoleService extends BaseService<AppRole> {
	public abstract AppRole getByRoleName(String paramString);

	public abstract HashMap<String, Set<String>> getSecurityDataSource();
}
