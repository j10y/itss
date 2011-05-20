package com.zsgj.itil.train.service;

import java.util.List;

import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.train.entity.QuestResult;

/**
 * �ʾ����ӿ�
 * @Class Name QuestService
 * @Author lee
 * @Create In Sep 22, 2009
 */
public interface QuestService {
	/**
	 * ����ʾ��
	 * @Methods Name getResult
	 * @Create In Sep 22, 2009 By lee
	 * @param user 	����û�
	 * @param surveyId	�ʾ�
	 * @param objId		������ʵ��ID
	 * @return List<QuestResult>
	 */
	List<QuestResult> getResult(UserInfo user, Long surveyId, Long objId);

	/**
	 * ����servey��objId����ѯ�ʾ���
	 * @Methods Name getResultByServeyAndObjId
	 * @Create In Aug 12, 2010 By huzh
	 * @param valueOf
	 * @param objId
	 * @return 
	 * @Return List<QuestResult>
	 */
	List<QuestResult> getResultBySurveyAndObjId(Long surveyId, Long objId);
}
