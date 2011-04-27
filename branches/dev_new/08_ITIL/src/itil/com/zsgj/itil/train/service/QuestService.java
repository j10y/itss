package com.zsgj.itil.train.service;

import java.util.List;

import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.train.entity.QuestResult;

/**
 * 问卷服务接口
 * @Class Name QuestService
 * @Author lee
 * @Create In Sep 22, 2009
 */
public interface QuestService {
	/**
	 * 获得问卷答案
	 * @Methods Name getResult
	 * @Create In Sep 22, 2009 By lee
	 * @param user 	答卷用户
	 * @param surveyId	问卷
	 * @param objId		关联主实体ID
	 * @return List<QuestResult>
	 */
	List<QuestResult> getResult(UserInfo user, Long surveyId, Long objId);

	/**
	 * 根据servey和objId来查询问卷结果
	 * @Methods Name getResultByServeyAndObjId
	 * @Create In Aug 12, 2010 By huzh
	 * @param valueOf
	 * @param objId
	 * @return 
	 * @Return List<QuestResult>
	 */
	List<QuestResult> getResultBySurveyAndObjId(Long surveyId, Long objId);
}
