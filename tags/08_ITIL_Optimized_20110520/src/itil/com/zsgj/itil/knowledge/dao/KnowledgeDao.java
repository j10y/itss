package com.zsgj.itil.knowledge.dao;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.actor.entity.SupportGroupServiceItem;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.service.entity.ServiceItem;
/**
 * 知识的数据访问接口。
 * @Class Name KnowledgeDao
 * @Author duxh
 * @Create In Oct 26, 2009
 */
public interface KnowledgeDao{
	
	/**
	 * 查询知识审批历史表中关于某个审批知识（文件或解决方案或合同）的最新processId
	 * @Methods Name selectProcessIdOfLatestProcess
	 * @Create In Nov 5, 2009 By duxh
	 * @return Long
	 */
	Long selectProcessIdOfLatestProcess(Long kId, Long kType);
	
	/**
	 * 根据解决方案的id使解决方案的使用次数加一。
	 * @Methods Name updateSolutionUseTime
	 * @Create In Oct 26, 2009 By duxh
	 * @param id 解决方案id
	 * @return void
	 */
	public void updateSolutionUseTime(Long id) throws DaoException;
	/**
	 * 通过事件Id查询解决方案
	 * @Methods Name selectKnowLedgeByEventId
	 * @Create In Oct 29, 2009 By duxh
	 * @return Knowledge
	 */
	public Knowledge selectKnowledgeByEventId(Long eventId) throws DaoException;
   /**
    * 通过服务项id查询解决方案
    * @Methods Name selectKnowledgeBySiId
    * @Create In Apr 15, 2010 By huzh
    * @param siId
    * @return 
    * @Return List<Knowledge>
    */
	Page selectKnowledgeBySiId(String serviceItemId,String[] summary,int pageNo, int pageSize);
	/**
	 * 修改解决方案问题类型删除标记（暂未用到）
	 * @Methods Name updateProblmeTypesStatus
	 * @Create In Apr 16, 2010 By huzh
	 * @param problemTypesId
	 * @param delete_false 
	 * @Return void
	 */
	  public void updateProblmeTypesStatus(Long[] problemTypesId, int delete_false);
	  /**
	   * 通过事件类型查询解决方案
	   * @Methods Name selectKnowledgeByEventType
	   * @Create In May 19, 2010 By huzh
	   * @param eventtypeId
	 * @param pageSize 
	 * @param pageNo 
	   * @return 
	   * @Return List<Knowledge>
	   */
	Page selectKnowledgeByEventType(String eventtypeId, int pageNo, int pageSize);
	/**
	 * 通过工程师查所在组
	 * @Methods Name selectSupportGroupByEngineer
	 * @Create In Jun 23, 2010 By huzh
	 * @param userInfo
	 * @return 
	 * @Return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> selectSupportGroupByEngineer(UserInfo userInfo);
	/**
	 * 根据参数查询服务项
	 * @param serviceItemId 
	 * @Methods Name selectServiceItemInSupportGroup
	 * @Create In Jul 5, 2010 By huzh
	 * @param userInfo
	 * @return 
	 * @Return List<ServiceItem>
	 */
	List<SupportGroupServiceItem> selectServiceItemInSupportGroup(Long serviceItemId, UserInfo userInfo);
	/**
	 * 根据当前登录人查询问题类型
	 * @Methods Name selectAllProblemType
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
	Page selectAllProblemType(UserInfo userInfo, String adminFlag, String name,
			String serviceItem, int start, int pageSize);
}
