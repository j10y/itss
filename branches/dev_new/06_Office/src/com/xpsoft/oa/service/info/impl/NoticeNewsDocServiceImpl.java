package com.xpsoft.oa.service.info.impl;

import java.util.List;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.info.NoticeNewsDocDao;
import com.xpsoft.oa.model.info.NoticeNewsDoc;
import com.xpsoft.oa.service.info.NoticeNewsDocService;

public class NoticeNewsDocServiceImpl extends BaseServiceImpl<NoticeNewsDoc>
		implements NoticeNewsDocService {

	private NoticeNewsDocDao noticeNewsDocDao;
	public List<NoticeNewsDoc> findByNid(Long paramLong) {
		return noticeNewsDocDao.findByNoticeId(paramLong);
	}
	public NoticeNewsDocServiceImpl(NoticeNewsDocDao dao) {
		super(dao);
		this.noticeNewsDocDao = dao;
	}
	
	
}
