package com.digitalchina.itil.event.service;

import java.util.List;

import com.digitalchina.info.framework.message.mail.service.MailSenderService;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.event.entity.CCCallInfo;

public interface ICCTelInfoService {
	/**
	 * ��ȡδ�����ʼ���������Ϣ
	 * @Methods Name getUnEmailCCCallInfo
	 * @Create In Aug 14, 2009 By lee
	 * @return List<CCCallInfo>
	 */
	List<CCCallInfo> getUnEmailCCCallInfo();
	/**
	 * �Ƿ������߷���
	 * @Methods Name isFeedback
	 * @Create In Aug 14, 2009 By lee
	 * @param callId
	 * @return boolean
	 */
	boolean isFeedback(String callId);
	/**
	 * ͨ��ITCODE��ȡ�û���Ϣ
	 * @Methods Name getUserInfoByItCode
	 * @Create In Aug 14, 2009 By lee
	 * @param itCode
	 * @return UserInfo
	 */
	UserInfo getUserInfoByItCode(String itCode);
	/**
	 * ƴ���ʼ����ݷ���
	 * @Methods Name htmlContent
	 * @Create In Aug 14, 2009 By lee
	 * @param realUrl
	 * @return String
	 */
	String htmlContent(String realUrl);
	/**
	 * ����
	 * @Methods Name save
	 * @Create In Aug 14, 2009 By lee
	 * @param obj void
	 */
	Object save(Object obj);
	/**
	 * ���δ���е绰����Ⱥ��ʼ�����ȵ����CCCallInfo
	 * @Methods Name getNoFeedBackofCCCall
	 * @Create In Sep 8, 2010 By huzh
	 * @return 
	 * @Return List<CCCallInfo>
	 */
	List<CCCallInfo> getNoFeedBackofCCCall();
}
