package com.xpsoft.webservice.service.flow;

public interface FlowService {
	public String getDycyList(String userId,String passwd);
	
	public String getDbsxList(String userId, String passwd);
	
	public String getDbsxDetail(String userId, String passwd,String activityName,String taskId);
	
	public String getYycyList(String userId, String passwd,String passType,String title,String pageNum,String pageSize);
	
	public String getYycyDetail(String userId, String passwd,String id);
	
	public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName,String signalName,String commentDesc,String nextuser,String checkboxvalue,String ispass,String gdlx,String bh);
	
	public String getGdlx();

}
