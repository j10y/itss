package com.xpsoft.oa.service.info.impl;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.info.NoticeNewsCommentDao;
import com.xpsoft.oa.model.info.NoticeNewsComment;
import com.xpsoft.oa.service.info.NoticeNewsCommentService;

public class NoticeNewsCommentServiceImpl extends BaseServiceImpl<NoticeNewsComment>
		implements NoticeNewsCommentService {
	private NoticeNewsCommentDao dao;

	public NoticeNewsCommentServiceImpl(NoticeNewsCommentDao dao) {
		super(dao);
		this.dao = dao;
	}
}
