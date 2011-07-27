package com.xpsoft.oa.dao.info.impl;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.info.NoticeNewsCommentDao;
import com.xpsoft.oa.model.info.NoticeNewsComment;

public class NoticeNewsCommentDaoImpl extends BaseDaoImpl<NoticeNewsComment>
		implements NoticeNewsCommentDao {
	public NoticeNewsCommentDaoImpl() {
		super(NoticeNewsComment.class);
	}
}
