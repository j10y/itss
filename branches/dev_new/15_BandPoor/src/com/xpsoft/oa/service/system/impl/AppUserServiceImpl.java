package com.xpsoft.oa.service.system.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.system.AppUserDao;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.system.AppUserService;
import java.util.List;
import java.util.Set;

public class AppUserServiceImpl extends BaseServiceImpl<AppUser> implements
		AppUserService {
	private AppUserDao dao;

	public AppUserServiceImpl(AppUserDao dao) {
		super(dao);
		this.dao = dao;
	}

	public AppUser findByUserName(String username) {
		return this.dao.findByUserName(username);
	}
	
	/**
	 * 新增涉及limit限制逻辑
	 */
	public AppUser getByUserName(String username) {
		return this.dao.getByUserName(username);
	}

	public List findByDepartment(String path, PagingBean pb) {
		return this.dao.findByDepartment(path, pb);
	}

	public List findByRole(Long roleId, PagingBean pb) {
		return this.dao.findByRole(roleId, pb);
	}

	public List findByRoleId(Long roleId) {
		return this.dao.findByRole(roleId);
	}

	public List<AppUser> findSubAppUser(String path, Set<Long> userIds,
			PagingBean pb) {
		return this.dao.findSubAppUser(path, userIds, pb);
	}

	public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds,
			PagingBean pb) {
		return this.dao.findSubAppUserByRole(roleId, userIds, pb);
	}

	public List<AppUser> findByDepId(Long depId) {
		return this.dao.findByDepId(depId);
	}

	public List<AppUser> findByDepIdAndPostName(Long depId, String postName) {
		return this.dao.findByDepIdAndPostName(depId, postName);
	}

	public List findByRoleIds(Long[] roleIds) {
		// TODO Auto-generated method stub
		return this.dao.findByRoleIds(roleIds);
	}

	public AppUser findByFullName(String paramString) {
		// TODO Auto-generated method stub
		return this.dao.findByFullName(paramString);
	}

}
