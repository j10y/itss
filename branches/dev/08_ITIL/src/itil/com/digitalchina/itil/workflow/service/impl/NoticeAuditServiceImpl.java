package com.digitalchina.itil.workflow.service.impl;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.itil.notice.entity.NewNotice;
import com.digitalchina.itil.notice.entity.NoticeAuditHis;
import com.digitalchina.itil.workflow.service.NoticeAuditService;

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
