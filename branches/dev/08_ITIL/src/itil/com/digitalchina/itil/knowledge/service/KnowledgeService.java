package com.digitalchina.itil.knowledge.service;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.SupportGroupEngineer;
import com.digitalchina.itil.knowledge.entity.Knowledge;

public interface KnowledgeService {
	/**
	 * 查询知识审批历史表中关于某个审批知识（文件或解决方案或合同）的最新processId
	 * @Methods Name findProcessIdOfLatestProcess
	 * @Create In Nov 5, 2009 By duxh
	 * @return Long
	 */
	Long findProcessIdOfLatestProcess(Long kId, Long kType);
	/**
	 * 使用解决方案。 根据解决方案的id使解决方案的使用次数加一。
	 * 
	 * @Methods Name updateSolutionUseTime
	 * @Create In Oct 26, 2009 By duxh
	 * @param id
	 *            解决方案id
	 * @throws ServiceException
	 * @return void
	 */
	public void modifySolutionUseTime(Long id) throws ServiceException;

	/**
	 * 通过事件Id查询解决方案
	 * 
	 * @Methods Name findKnowLedgeByEventId
	 * @Create In Oct 29, 2009 By duxh
	 * @return Knowledge
	 */
	Knowledge findKnowledgeByEventId(Long eventId);
	/**
	 * 通过服务项id查询解决方案
	 * @Methods Name findKnowledgeBySiId
	 * @Create In Apr 15, 2010 By huzh
	 * @param serviceItemId
	 * @return 
	 * @Return List<Knowledge>
	 */
	public Page findKnowledgeBySiId(String serviceItemId,String[] summarykeyWord,int pageNo, int pageSize);
	/**
	 * 对解决方案问题类型进行逻辑删除
	 * @Methods Name removeProblemType
	 * @Create In Apr 16, 2010 By huzh
	 * @param problemTypesId 
	 * @Return void
	 */
	public void removeProblemType(Long[] problemTypesId);
	/**
	 * 通过事件类型查询解决方案
	 * @Methods Name findKnowledgeByEventTypeId
	 * @Create In May 19, 2010 By huzh
	 * @param eventtypeId
	 * @param pageSize 
	 * @param pageNo 
	 * @return 
	 * @Return List<Knowledge>
	 */
	Page findKnowledgeByEventTypeId(String eventtypeId, int pageNo, int pageSize);
	/**
	 * 通过工程师查找所在的支持组
	 * @Methods Name findSupportGroupByEngineer
	 * @Create In Jun 23, 2010 By huzh
	 * @param userInfo
	 * @return 
	 * @Return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> findSupportGroupByEngineer(UserInfo userInfo);
	/**
	 * 确认服务项是否包含在以当前人为组长的支持组中
	 * @Methods Name confirmServiceItemInSupportGroup
	 * @Create In Jul 5, 2010 By huzh
	 * @param valueOf
	 * @param userInfo
	 * @return 
	 * @Return String
	 */
	String confirmServiceItemInSupportGroup(Long serviceItemId, UserInfo userInfo);
	/**
	 *  根据当前登录人查询问题类型
	 * @Methods Name findAllProblemType
	 * @Create In Jul 15, 2010 By huzh
	 * @param userInfo
	 * @param adminFlag
	 * @param name
	 * @param serviceItem
	 * @param start
	 * @param pageSize
	 * @return 
	 * @Return Page
	 */
	Page findAllProblemType(UserInfo userInfo, String adminFlag, String name,
			String serviceItem, int start, int pageSize);
	
}
