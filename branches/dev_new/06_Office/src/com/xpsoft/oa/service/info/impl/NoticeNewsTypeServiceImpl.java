package com.xpsoft.oa.service.info.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.info.NoticeNewsTypeDao;
import com.xpsoft.oa.model.info.NoticeNewsType;
import com.xpsoft.oa.service.info.NoticeNewsTypeService;
import java.util.List;

public class NoticeNewsTypeServiceImpl extends BaseServiceImpl<NoticeNewsType>
		implements NoticeNewsTypeService {
	private NoticeNewsTypeDao newsTypeDao;

	public NoticeNewsTypeServiceImpl(NoticeNewsTypeDao dao) {
		super(dao);
		this.newsTypeDao = dao;
	}

	public Short getTop() {
		return this.newsTypeDao.getTop();
	}

	public NoticeNewsType findBySn(Short sn) {
		return this.newsTypeDao.findBySn(sn);
	}

	public List<NoticeNewsType> getAllBySn() {
		return this.newsTypeDao.getAllBySn();
	}

	public List<NoticeNewsType> getAllBySn(PagingBean pb) {
		return this.newsTypeDao.getAllBySn(pb);
	}

	public List<NoticeNewsType> findBySearch(NoticeNewsType newsType, PagingBean pb) {
		return this.newsTypeDao.findBySearch(newsType, pb);
	}
}
