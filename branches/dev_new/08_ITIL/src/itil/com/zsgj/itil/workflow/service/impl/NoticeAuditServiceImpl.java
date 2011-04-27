package com.zsgj.itil.workflow.service.impl;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.itil.notice.entity.NewNotice;
import com.zsgj.itil.notice.entity.NoticeAuditHis;
import com.zsgj.itil.workflow.service.NoticeAuditService;

public class NoticeAuditServiceImpl extends BaseDao implements NoticeAuditService{

	public NewNotice getNewNoticeById(String noticeId) {
		// TODO Auto-generated method stub
		return super.get(NewNotice.class, Long.valueOf(noticeId));
	}

	public void saveNoticeHis(NoticeAuditHis newNotice) {
		// TODO Auto-generated method stub
		super.save(newNotice);
	}

}
