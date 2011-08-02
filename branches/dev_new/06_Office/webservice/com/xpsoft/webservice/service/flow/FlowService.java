package com.xpsoft.webservice.service.flow;

public interface FlowService {
	public String getDycyList(String userId,String passwd);
	
	public String getDbsxList(String userId, String passwd);
	
	public String getDbsxDetail(String userId, String passwd,String activityName,String taskId);
	
	
}
