package com.digitalchina.itil.knowledge.service;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.SupportGroupEngineer;
import com.digitalchina.itil.knowledge.entity.Knowledge;

public interface KnowledgeService {
	/**
	 * ��ѯ֪ʶ������ʷ���й���ĳ������֪ʶ���ļ������������ͬ��������processId
	 * @Methods Name findProcessIdOfLatestProcess
	 * @Create In Nov 5, 2009 By duxh
	 * @return Long
	 */
	Long findProcessIdOfLatestProcess(Long kId, Long kType);
	/**
	 * ʹ�ý�������� ���ݽ��������idʹ���������ʹ�ô�����һ��
	 * 
	 * @Methods Name updateSolutionUseTime
	 * @Create In Oct 26, 2009 By duxh
	 * @param id
	 *            �������id
	 * @throws ServiceException
	 * @return void
	 */
	public void modifySolutionUseTime(Long id) throws ServiceException;

	/**
	 * ͨ���¼�Id��ѯ�������
	 * 
	 * @Methods Name findKnowLedgeByEventId
	 * @Create In Oct 29, 2009 By duxh
	 * @return Knowledge
	 */
	Knowledge findKnowledgeByEventId(Long eventId);
	/**
	 * ͨ��������id��ѯ�������
	 * @Methods Name findKnowledgeBySiId
	 * @Create In Apr 15, 2010 By huzh
	 * @param serviceItemId
	 * @return 
	 * @Return List<Knowledge>
	 */
	public Page findKnowledgeBySiId(String serviceItemId,String[] summarykeyWord,int pageNo, int pageSize);
	/**
	 * �Խ�������������ͽ����߼�ɾ��
	 * @Methods Name removeProblemType
	 * @Create In Apr 16, 2010 By huzh
	 * @param problemTypesId 
	 * @Return void
	 */
	public void removeProblemType(Long[] problemTypesId);
	/**
	 * ͨ���¼����Ͳ�ѯ�������
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
	 * ͨ������ʦ�������ڵ�֧����
	 * @Methods Name findSupportGroupByEngineer
	 * @Create In Jun 23, 2010 By huzh
	 * @param userInfo
	 * @return 
	 * @Return List<SupportGroupEngineer>
	 */
	List<SupportGroupEngineer> findSupportGroupByEngineer(UserInfo userInfo);
	/**
	 * ȷ�Ϸ������Ƿ�������Ե�ǰ��Ϊ�鳤��֧������
	 * @Methods Name confirmServiceItemInSupportGroup
	 * @Create In Jul 5, 2010 By huzh
	 * @param valueOf
	 * @param userInfo
	 * @return 
	 * @Return String
	 */
	String confirmServiceItemInSupportGroup(Long serviceItemId, UserInfo userInfo);
	/**
	 *  ���ݵ�ǰ��¼�˲�ѯ��������
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
