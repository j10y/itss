package com.xpsoft.oa.service.bandpoor.impl;

import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.bandpoor.ScoreManageDao;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.service.bandpoor.ScoreManageService;

public class ScoreManageServiceImpl extends BaseServiceImpl<InfoPoor> implements ScoreManageService{
	private ScoreManageDao dao;
	
	public ScoreManageServiceImpl(ScoreManageDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		// TODO Auto-generated method stub
		return false;
	}
}
