package com.zsgj.itil.workflow.service;

import com.zsgj.itil.notice.entity.NewNotice;
import com.zsgj.itil.notice.entity.NoticeAuditHis;

public interface NoticeAuditService {
	/**
	 * ����id��ȡNewNotice
	 * @Methods Name getNewNoticeById
	 * @Create In Mar 16, 2009 By Administrator
	 * @param noticeId
	 * @return NewNotice
	 */
	NewNotice getNewNoticeById(String noticeId);
	
	void saveNoticeHis(NoticeAuditHis newNotice);
}
