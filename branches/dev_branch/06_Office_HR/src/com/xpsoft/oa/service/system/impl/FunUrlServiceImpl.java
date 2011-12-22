package com.xpsoft.oa.service.system.impl;

import java.util.List;
import java.util.Set;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.FunUrlDao;
import com.xpsoft.oa.model.system.FunUrl;
import com.xpsoft.oa.service.system.FunUrlService;

public class FunUrlServiceImpl extends BaseServiceImpl<FunUrl> implements
		FunUrlService {
	private FunUrlDao dao;

	public FunUrlServiceImpl(FunUrlDao dao) {
		super(dao);
		this.dao = dao;
	}

	public FunUrl getByPathFunId(String path, Long funId) {
		return this.dao.getByPathFunId(path, funId);
	}

	public Set<String> getAdminDataSource() {
		return this.dao.getAdminDataSource();
	}

	public List<FunUrl> getByFunId() {
		// TODO Auto-generated method stub
		return null;// this.dao.getByFunId();
	}
}
