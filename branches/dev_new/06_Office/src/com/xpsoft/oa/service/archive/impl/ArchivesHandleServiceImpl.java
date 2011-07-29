package com.xpsoft.oa.service.archive.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.archive.ArchivesHandleDao;
import com.xpsoft.oa.model.archive.ArchivesHandle;
import com.xpsoft.oa.service.archive.ArchivesHandleService;
import java.util.List;

public class ArchivesHandleServiceImpl extends BaseServiceImpl<ArchivesHandle>
		implements ArchivesHandleService {
	private ArchivesHandleDao dao;

	public ArchivesHandleServiceImpl(ArchivesHandleDao dao) {
		/* 16 */super(dao);
		/* 17 */this.dao = dao;
	}

	public ArchivesHandle findByUAIds(Long userId, Long archiveId) {
		/* 22 */return this.dao.findByUAIds(userId, archiveId);
	}

	public List<ArchivesHandle> findByAid(Long archiveId) {
		/* 27 */return this.dao.findByAid(archiveId);
	}

	public int countHandler(Long archiveId) {
		/* 32 */return this.dao.findByAid(archiveId).size();
	}
}
