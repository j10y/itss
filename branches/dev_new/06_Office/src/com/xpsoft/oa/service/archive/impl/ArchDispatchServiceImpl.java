package com.xpsoft.oa.service.archive.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.archive.ArchDispatchDao;
import com.xpsoft.oa.model.archive.ArchDispatch;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.archive.ArchDispatchService;
import java.util.List;

public class ArchDispatchServiceImpl extends BaseServiceImpl<ArchDispatch>
		implements ArchDispatchService {
	private ArchDispatchDao dao;

	public ArchDispatchServiceImpl(ArchDispatchDao dao) {
		/* 19 */super(dao);
		/* 20 */this.dao = dao;
	}

	public List<ArchDispatch> findByUser(AppUser user, PagingBean pb) {
		/* 25 */return this.dao.findByUser(user, pb);
	}

	public int countArchDispatch(Long archivesId) {
		/* 30 */return this.dao.findRecordByArc(archivesId).size();
	}
	
	public int countArchDispatch(Long archivesId, Short type) {
		/* 30 */return this.dao.findRecordByArc(archivesId, type).size();
	}
}
