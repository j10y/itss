package com.xpsoft.oa.dao.info.impl;

import java.util.List;

import com.xpsoft.core.dao.impl.BaseDaoImpl;
import com.xpsoft.oa.dao.info.NoticeNewsDocDao;
import com.xpsoft.oa.model.info.NoticeNewsDoc;

public class NoticeNewsDocDaoImpl extends BaseDaoImpl<NoticeNewsDoc> implements NoticeNewsDocDao {
	public NoticeNewsDocDaoImpl() {
		/* 19 */super(NoticeNewsDoc.class);
	}

	@Override
	public List<NoticeNewsDoc> findByNoticeId(Long paramLong) {
		String hql = "from NoticeNewsDoc vo where vo.noticeNews.newsId=?";
		Object[] objs = { paramLong };
		return findByHql(hql, objs);
	}
}
