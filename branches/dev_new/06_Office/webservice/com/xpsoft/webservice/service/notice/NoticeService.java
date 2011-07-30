package com.xpsoft.webservice.service.notice;

public interface NoticeService {
	public String getDyNotice(String userId,String passwd,String pageNum,String pageSize,String title,String department);
	
	public String getYyNotice(String userId,String passwd,String pageNum,String pageSize,String title,String department,String noticeTypeId);
	
	public String getNoticeType(String userId,String passwd);
	
	public String getNoticeDetail(String noticeId,String userId,String passwd);
	
	public String saveNoticeComment(String userId,String passwd,String noticeId,String commentDesc);
}
