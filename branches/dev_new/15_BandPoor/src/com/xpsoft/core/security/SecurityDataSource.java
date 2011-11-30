package com.xpsoft.core.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.xpsoft.oa.model.system.AppRole;
import com.xpsoft.oa.service.system.AppRoleService;
import com.xpsoft.oa.service.system.FunUrlService;

public class SecurityDataSource {
	private AppRoleService appRoleService;
	private FunUrlService funUrlService;
	public FunUrlService getFunUrlService() {
		return funUrlService;
	}

	public void setFunUrlService(FunUrlService funUrlService) {
		this.funUrlService = funUrlService;
	}

	private HashSet<String> anonymousUrls = null;

	private HashSet<String> publicUrls = null;

	public void setAppRoleService(AppRoleService appRoleService) {
		this.appRoleService = appRoleService;
	}

	public Set<String> getAnonymousUrls() {
		return this.anonymousUrls;
	}

	public void setAnonymousUrls(Set<String> anonymousUrls) {
		this.anonymousUrls = ((HashSet) anonymousUrls);
	}

	public HashSet<String> getPublicUrls() {
		return this.publicUrls;
	}

	public void setPublicUrls(HashSet<String> publicUrls) {
		this.publicUrls = publicUrls;
	}

	public HashMap<String, Set<String>> getDataSource() {
		HashMap tmap = this.appRoleService.getSecurityDataSource();
		tmap.put(AppRole.ROLE_ANONYMOUS, this.anonymousUrls);
		tmap.put(AppRole.ROLE_PUBLIC, this.publicUrls);
		tmap.put(AppRole.ROLE_ADMIN, this.funUrlService.getAdminDataSource());
		return tmap;
	}
}
