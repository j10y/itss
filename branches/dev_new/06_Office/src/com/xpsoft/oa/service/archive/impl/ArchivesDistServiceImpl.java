package com.xpsoft.oa.service.archive.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.archive.ArchivesDistDao;
import com.xpsoft.oa.model.archive.ArchivesDist;
import com.xpsoft.oa.service.archive.ArchivesDistService;

public class ArchivesDistServiceImpl extends BaseServiceImpl<ArchivesDist>
		implements ArchivesDistService {
	private ArchivesDistDao dao;

	public ArchivesDistServiceImpl(ArchivesDistDao dao) {
		super(dao);
		this.dao = dao;
	}
}
