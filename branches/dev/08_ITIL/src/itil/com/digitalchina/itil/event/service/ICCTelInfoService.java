package com.digitalchina.itil.event.service;

import java.util.List;

import com.digitalchina.info.framework.message.mail.service.MailSenderService;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.event.entity.CCCallInfo;

public interface ICCTelInfoService {
	/**
	 * 获取未发送邮件的热线信息
	 * @Methods Name getUnEmailCCCallInfo
	 * @Create In Aug 14, 2009 By lee
	 * @return List<CCCallInfo>
	 */
	List<CCCallInfo> getUnEmailCCCallInfo();
	/**
	 * 是否已热线反馈
	 * @Methods Name isFeedback
	 * @Create In Aug 14, 2009 By lee
	 * @param callId
	 * @return boolean
	 */
	boolean isFeedback(String callId);
	/**
	 * 通过ITCODE获取用户信息
	 * @Methods Name getUserInfoByItCode
	 * @Create In Aug 14, 2009 By lee
	 * @param itCode
	 * @return UserInfo
	 */
	UserInfo getUserInfoByItCode(String itCode);
	/**
	 * 拼接邮件内容方法
	 * @Methods Name htmlContent
	 * @Create In Aug 14, 2009 By lee
	 * @param realUrl
	 * @return String
	 */
	String htmlContent(String realUrl);
	/**
	 * 保存
	 * @Methods Name save
	 * @Create In Aug 14, 2009 By lee
	 * @param obj void
	 */
	Object save(Object obj);
	/**
	 * 获得未进行电话满意度和邮件满意度调查的CCCallInfo
	 * @Methods Name getNoFeedBackofCCCall
	 * @Create In Sep 8, 2010 By huzh
	 * @return 
	 * @Return List<CCCallInfo>
	 */
	List<CCCallInfo> getNoFeedBackofCCCall();
}
