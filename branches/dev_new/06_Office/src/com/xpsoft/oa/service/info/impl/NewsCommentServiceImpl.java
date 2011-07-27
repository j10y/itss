package com.xpsoft.oa.service.info.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.info.NewsCommentDao;
import com.xpsoft.oa.model.info.NewsComment;
import com.xpsoft.oa.service.info.NewsCommentService;

public class NewsCommentServiceImpl extends BaseServiceImpl<NewsComment>
		implements NewsCommentService {
	private NewsCommentDao dao;

	public NewsCommentServiceImpl(NewsCommentDao dao) {
		super(dao);
		this.dao = dao;
	}
}
