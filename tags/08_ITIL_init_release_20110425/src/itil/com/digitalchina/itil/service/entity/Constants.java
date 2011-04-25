package com.digitalchina.itil.service.entity;

/**
 * 常规服务与个性化需求用户表状态位
 * @Class Name Constants
 * @Author sa
 * @Create In 2008-11-11
 */
public class Constants {
	public static int RROCESS_TYPE_APPLY = 0; // 申请 
	public static int RROCESS_TYPE_MODIFY = 1;// 变更
	public static int RROCESS_TYPE_CANCLE = 2;// 撤销
	
	public static int STATUS_DRAFT = 0;// 草稿
	public static int STATUS_APPROVING = 1;// 提交审批中
	public static int STATUS_FINISHED = 2;// 通过
	public static int STATUS_DELETE = -1;// 已删除
	
	public static int DELETE_TRUE = 1;//删除
	public static int DELETE_FALSE = 0;//未删除
	
}
