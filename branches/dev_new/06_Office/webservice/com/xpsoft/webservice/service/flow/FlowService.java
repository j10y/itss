package com.xpsoft.webservice.service.flow;

public interface FlowService {
	public String getDycyList(String userId,String passwd);
	
	public String getDbsxList(String userId, String passwd);
	
	public String getDbsxDetail(String userId, String passwd,String activityName,String taskId);
	
	public String getYycyList(String userId, String passwd,String passType,String title,String pageNum,String pageSize);
	
	public String getYycyDetail(String userId, String passwd,String id);
	/**
	 * 
	 * @param userId 登录人id
	 * @param passwd登录人密码
	 * @param id 文件id
	 * @param taskId 任务id
	 * @param activityName
	 * @param signalName
	 * @param commentDesc 审批意见
	 * @param nextuser 下一个审批人
	 * @param checkboxvalue 单选框的值
	 * @param ispass 是否通过，或驳回
	 * @param gdlx 归档类型
	 * @param bh 编号
	 * @param fgld 分管领导得审批人
	 * @return
	 */
	public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName,String signalName,String commentDesc,String nextuser,String checkboxvalue,String ispass,String gdlx,String bh,String fgld,String fjry);
	/**
	 * 归档类型
	 * @return
	 */
	public String getGdlx();

	/**
	 * 待阅分发列表
	 * @param userId
	 * @param passwd
	 * @param title
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getDyffList(String userId, String passwd,String title, String pageNum, String pageSize);
	/**
	 * 待阅分发详细信息
	 * @param userId
	 * @param passwd
	 * @param id
	 * @return
	 */
	public String getDyffDetail(String userId, String passwd, String id,String distid);
	/**
	 * 保存待阅信息
	 * @param userId
	 * @param passwd
	 * @param id
	 * @return
	 */
	public String saveDyffDetail(String userId, String passwd, String id );
	/**
	 * 查询归档类型
	 * @param userId
	 * @param passwd
	 * @return
	 */
	public String findFgld(String userId,String passwd) ;
	/**
	 * 选择局长或分管局长
	 * @param userId
	 * @param passwd
	 * @return
	 */
	public String findJld(String userId,String passwd);
}
