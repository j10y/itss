package com.xpsoft.oa.service.archive.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.archive.ArchRecFiledTypeDao;
import com.xpsoft.oa.model.archive.ArchRecFiledType;
import com.xpsoft.oa.service.archive.ArchRecFiledTypeService;

public class ArchRecFiledTypeServiceImpl extends BaseServiceImpl<ArchRecFiledType>
		implements ArchRecFiledTypeService {
	private ArchRecFiledTypeDao dao;

	public ArchRecFiledTypeServiceImpl(ArchRecFiledTypeDao dao) {
		super(dao);
		this.dao = dao;
	}
}
