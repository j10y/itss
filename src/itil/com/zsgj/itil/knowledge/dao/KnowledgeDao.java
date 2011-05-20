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
 * ֪ʶ�����ݷ��ʽӿڡ�
 * @Class Name KnowledgeDao
 * @Author duxh
 * @Create In Oct 26, 2009
 */
public interface KnowledgeDao{
	
	/**
	 * ��ѯ֪ʶ������ʷ���й���ĳ������֪ʶ���ļ������������ͬ��������processId
	 * @Methods Name selectProcessIdOfLatestProcess
	 * @Create In Nov 5, 2009 By duxh
	 * @return Long
	 */
	Long selectProcessIdOfLatestProcess(Long kId, Long kType);
	
	/**
	 * ���ݽ��������idʹ���������ʹ�ô�����һ��
	 * @Methods Name updateSolutionUseTime
	 * @Create In Oct 26, 2009 By duxh
	 * @param id �������id
	 * @return void
	 */
	public void updateSolutionUseTime(Long id) throws DaoException;
	/**
	 * ͨ���¼�Id��ѯ�������
	 * @Methods Name selectKnowLedgeByEventId
	 * @Create In Oct 29, 2009 By duxh
	 * @return Knowledge
	 */
	public Knowledge selectKnowledgeByEventId(Long eventId) throws DaoException;
   /**
    * ͨ��������id��ѯ�������
    * @Methods Name selectKnowledgeBySiId
    * @Create In Apr 15, 2010 By huzh
    * @param siId
    * @return 
    * @Return List<Knowledge>
    */
	Page selectKnowledgeBySiId(String serviceItemId,String[] summary,int pageNo, int pageSize);
	/**
	 * �޸Ľ��������������ɾ����ǣ���δ�õ���
	 * @Methods Name updateProblmeTypesStatus
	 * @Create In Apr 16, 2010 By huzh
	 * @param problemTypesId
	 * @param delete_false 
	 * @Return void
	 */
	  public void updateProblmeTypesStatus(Long[] problemTypesId, int delete_false);
	  /**
	   * ͨ���¼����Ͳ�ѯ�������
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
	 * ͨ������ʦ��������
	 * @Methods Name selectSupportGroupByEngineer
	 * @Create In Jun 23, 2010 By huzh
	 * @param userInfo
	 * @return 
	 * @Return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> selectSupportGroupByEngineer(UserInfo userInfo);
	/**
	 * ���ݲ�����ѯ������
	 * @param serviceItemId 
	 * @Methods Name selectServiceItemInSupportGroup
	 * @Create In Jul 5, 2010 By huzh
	 * @param userInfo
	 * @return 
	 * @Return List<ServiceItem>
	 */
	List<SupportGroupServiceItem> selectServiceItemInSupportGroup(Long serviceItemId, UserInfo userInfo);
	/**
	 * ���ݵ�ǰ��¼�˲�ѯ��������
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
