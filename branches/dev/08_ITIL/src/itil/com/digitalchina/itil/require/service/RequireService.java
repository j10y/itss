package com.digitalchina.itil.require.service;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanel;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.extlist.entity.SRExpendPlan;
import com.digitalchina.itil.config.extlist.entity.SRIncomePlan;
import com.digitalchina.itil.require.entity.BusinessAccount;
import com.digitalchina.itil.require.entity.RealPayment;
import com.digitalchina.itil.require.entity.RealIncome;
import com.digitalchina.itil.require.entity.UpDatePlan;
import com.digitalchina.itil.service.entity.ServiceItem;
import com.digitalchina.itil.service.entity.ServiceItemProcess;
import com.digitalchina.itil.train.entity.Quest;
import com.digitalchina.itil.train.entity.QuestOption;
import com.digitalchina.itil.train.entity.Survey;

public interface RequireService {
	
	/**
	 * ���ݲ������ƺ������˻�ȡERP����Ĭ�Ͻڵ�������
	 * @Methods Name findAuditsByPage
	 * @Create In Jun 2, 2009 By lee
	 * @param departmentName
	 * @param audit
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findAuditsByPage(String departmentName,UserInfo audit,int pageNo,int pageSize);
	/**
	 * ��ȡ�������Ӧ��ģ�����
	 * @Methods Name findPanelsByServiceItem
	 * @Create In Feb 27, 2009 By Administrator
	 * @param pageModel
	 * @param serviceItem
	 * @return List<PageModelPanel>
	 */
	List<PageModelPanel> findPanelsByServiceItem(String pageModel, ServiceItem serviceItem);
	/**
	 * ��ȡ�����Ӧ�����Ϣ
	 * @Methods Name getRequirePanelJson
	 * @Create In Feb 27, 2009 By lee
	 * @param pageModel
	 * @param serviceItem
	 * @return String
	 */
	String getRequirePanelJson(String pageModel, ServiceItem serviceItem);
	/**
	 * ��ȡ�����������д��ڲ�ͬ���̲�ͬ״̬������
	 * @Methods Name forQuerry
	 * @Create In Mar 2, 2009 By lee
	 * @param className
	 * @param sidProcessType
	 * @param i
	 * @return String
	 */
	String forQuerry(String className, int sidProcessType, int i);
	/**
	 * ����ʵ�����ʵ�屣������ʵ�嵽��ʷ��
	 * @Methods Name saveEntityToEvent
	 * @Create In Mar 30, 2009 By lee
	 * @param entityClass
	 * @param entityObject void
	 */
	void saveEntityToEvent(String entityClass,BaseObject entityObject);
	/**
	 * ����ʵ�����ʵ���ȡ��Ӧ������ʵ��
	 * @Methods Name getOldApplyObject
	 * @Create In Mar 31, 2009 By lee
	 * @param entityClass
	 * @param entityObject
	 * @return Object
	 */
	Object getOldApplyObject(String entityClass,BaseObject entityObject);
	
	/**
	 * ��ѯ�û�����ȵ����ʾ�
	 * @author gaowen
	 * @Create In May 7, 2009 
	 * @return
	 */
	 Survey findSpecialRequireSurvey();
	 /**
    * ��ѯ�û���������
    * @Create In May 7, 2009 by gaowen
    * @return
	*/
		List<Quest> findQuest(Long surveyId);
	/**
	 * �鿴�ʾ��Ƿ�����
	 * @param userInfoId
	 * @param eventId
	 * @param surveyId
	 * @return
	 */
    boolean findIsUserFeedbackOrNot(Long userInfoId, Long eventId, Long surveyId);

    public List<QuestOption> findQuestOption(Quest quest);
    /**
     * ģ����ѯ��������
	 * @Methods Name findRequireByName
	 * @Create In Nov 9, 2009 By duxh
     * @param pageNo
     * @param size
     * @return Page
     */
    public Page findRequireByName(String name,int pageNo,int size);
    
    /**
     * ģ����ѯ����Ӧ��ϵͳ
     * @param name
     * @param configItem
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page findAppByPage(ConfigItem configItem,UserInfo appManager, int pageNo, int pageSize);
    
    /**
     * ����������û������ܾ��󵽵�ǰ�������������죬�򷵻ء�true��
     * @Methods Name isRefuseFlag
     * @param dataId
     * @param processId
     * @param nodeId
     * @return 
     */
    public String isRefuseFlag(String dataId,String processId,String nodeId);
    
    /**
     * �߼�ɾ��RequireApplyDefaultAudit������Ϣ
     * @Methods Name removeRequireAudit
     * @param id
     * @return 
     */
    
    public String removeRequireAudit(String id);
}
