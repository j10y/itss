package com.xpsoft.oa.service.info.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.info.NoticeNewsDao;
import com.xpsoft.oa.model.info.NoticeNews;
import com.xpsoft.oa.service.info.NoticeNewsService;
import java.util.List;

public class NoticeNewsServiceImpl extends BaseServiceImpl<NoticeNews> implements
	NoticeNewsService {
	private NoticeNewsDao newsDao;

	public NoticeNewsServiceImpl(NoticeNewsDao dao) {
		super(dao);
		this.newsDao = dao;
	}

	public List<NoticeNews> findByTypeId(Long typeId, PagingBean pb) {
		return this.newsDao.findByTypeId(typeId, pb);
	}

	public List<NoticeNews> findBySearch(String searchContent, PagingBean pb) {
		return this.newsDao.findBySearch(searchContent, pb);
	}

	public List<NoticeNews> findImageNews(PagingBean pb) {
		return this.newsDao.findImageNews(pb);
	}
}
