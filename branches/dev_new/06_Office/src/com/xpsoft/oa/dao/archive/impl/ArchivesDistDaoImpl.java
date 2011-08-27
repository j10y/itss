package com.xpsoft.oa.dao.archive.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.archive.ArchivesDistDao;
import com.xpsoft.oa.model.archive.ArchivesDist;

public class ArchivesDistDaoImpl extends BaseDaoImpl<ArchivesDist> implements
		ArchivesDistDao {
	public ArchivesDistDaoImpl() {
		super(ArchivesDist.class);
	}
}