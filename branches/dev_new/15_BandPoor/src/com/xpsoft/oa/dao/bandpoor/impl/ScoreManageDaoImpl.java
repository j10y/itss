package com.xpsoft.oa.dao.bandpoor.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.bandpoor.ScoreManageDao;
import com.xpsoft.oa.model.bandpoor.InfoPoor;

public class ScoreManageDaoImpl extends BaseDaoImpl<InfoPoor> implements ScoreManageDao{
	public ScoreManageDaoImpl() {
		super(InfoPoor.class);
	}
	
}
