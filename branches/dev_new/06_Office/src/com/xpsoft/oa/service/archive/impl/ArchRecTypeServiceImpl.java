package com.xpsoft.oa.service.archive.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.archive.ArchRecTypeDao;
import com.xpsoft.oa.model.archive.ArchRecType;
import com.xpsoft.oa.service.archive.ArchRecTypeService;

public class ArchRecTypeServiceImpl extends BaseServiceImpl<ArchRecType>
		implements ArchRecTypeService {
	private ArchRecTypeDao dao;

	public ArchRecTypeServiceImpl(ArchRecTypeDao dao) {
		super(dao);
		this.dao = dao;
	}
}
