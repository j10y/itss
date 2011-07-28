package com.xpsoft.oa.service.archive.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.archive.ArchivesDocDao;
import com.xpsoft.oa.model.archive.ArchivesDoc;
import com.xpsoft.oa.service.archive.ArchivesDocService;
import java.util.List;

public class ArchivesDocServiceImpl extends BaseServiceImpl<ArchivesDoc>
		implements ArchivesDocService {
	private ArchivesDocDao dao;

	public ArchivesDocServiceImpl(ArchivesDocDao dao) {
		super(dao);
		this.dao = dao;
	}

	public List<ArchivesDoc> findByAid(Long archivesId) {
		return this.dao.findByAid(archivesId);
	}
}
