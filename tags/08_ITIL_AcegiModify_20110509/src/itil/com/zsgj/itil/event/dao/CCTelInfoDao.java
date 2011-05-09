package com.zsgj.itil.event.dao;

import java.util.List;

import com.zsgj.itil.event.entity.CCCallInfo;

public interface CCTelInfoDao {
	
	public List<CCCallInfo> selectUnEmailCCCallInfo();
	/**
	 * 查询既没进行电话反馈又没进行邮件反馈的CCCallInfo
	 * @Methods Name selectNoFeedBackofCCCall
	 * @Create In Sep 8, 2010 By huzh
	 * @return 
	 * @Return List<CCCallInfo>
	 */
	public List<CCCallInfo> selectNoFeedBackofCCCall();
}
