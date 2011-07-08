package com.xpsoft.oa.dao.admin.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.admin.AssetsTypeDao;
import com.xpsoft.oa.model.admin.AssetsType;

@SuppressWarnings("unchecked")
public class AssetsTypeDaoImpl extends BaseDaoImpl<AssetsType> implements
		AssetsTypeDao {
	public AssetsTypeDaoImpl(){
		super(AssetsType.class);
	}

}
