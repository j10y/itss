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
	 * 根据部门名称和审批人获取ERP需求默认节点审批人
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
	 * 获取服务项对应的模板面板
	 * @Methods Name findPanelsByServiceItem
	 * @Create In Feb 27, 2009 By Administrator
	 * @param pageModel
	 * @param serviceItem
	 * @return List<PageModelPanel>
	 */
	List<PageModelPanel> findPanelsByServiceItem(String pageModel, ServiceItem serviceItem);
	/**
	 * 获取需求对应面板信息
	 * @Methods Name getRequirePanelJson
	 * @Create In Feb 27, 2009 By lee
	 * @param pageModel
	 * @param serviceItem
	 * @return String
	 */
	String getRequirePanelJson(String pageModel, ServiceItem serviceItem);
	/**
	 * 获取在需求主表中处于不同流程不同状态的数据
	 * @Methods Name forQuerry
	 * @Create In Mar 2, 2009 By lee
	 * @param className
	 * @param sidProcessType
	 * @param i
	 * @return String
	 */
	String forQuerry(String className, int sidProcessType, int i);
	/**
	 * 根据实体类和实体保存需求实体到历史表
	 * @Methods Name saveEntityToEvent
	 * @Create In Mar 30, 2009 By lee
	 * @param entityClass
	 * @param entityObject void
	 */
	void saveEntityToEvent(String entityClass,BaseObject entityObject);
	/**
	 * 根据实体类和实体获取对应的引用实体
	 * @Methods Name getOldApplyObject
	 * @Create In Mar 31, 2009 By lee
	 * @param entityClass
	 * @param entityObject
	 * @return Object
	 */
	Object getOldApplyObject(String entityClass,BaseObject entityObject);
	
	/**
	 * 查询用户满意度调查问卷
	 * @author gaowen
	 * @Create In May 7, 2009 
	 * @return
	 */
	 Survey findSpecialRequireSurvey();
	 /**
    * 查询用户反馈试题
    * @Create In May 7, 2009 by gaowen
    * @return
	*/
		List<Quest> findQuest(Long surveyId);
	/**
	 * 查看问卷是否作答
	 * @param userInfoId
	 * @param eventId
	 * @param surveyId
	 * @return
	 */
    boolean findIsUserFeedbackOrNot(Long userInfoId, Long eventId, Long surveyId);

    public List<QuestOption> findQuestOption(Quest quest);
    /**
     * 模糊查询所有需求。
	 * @Methods Name findRequireByName
	 * @Create In Nov 9, 2009 By duxh
     * @param pageNo
     * @param size
     * @return Page
     */
    public Page findRequireByName(String name,int pageNo,int size);
    
    /**
     * 模糊查询需求应用系统
     * @param name
     * @param configItem
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page findAppByPage(ConfigItem configItem,UserInfo appManager, int pageNo, int pageSize);
    
    /**
     * 如果是其他用户审批拒绝后到当前审批人审批待办，则返回“true”
     * @Methods Name isRefuseFlag
     * @param dataId
     * @param processId
     * @param nodeId
     * @return 
     */
    public String isRefuseFlag(String dataId,String processId,String nodeId);
    
    /**
     * 逻辑删除RequireApplyDefaultAudit表中信息
     * @Methods Name removeRequireAudit
     * @param id
     * @return 
     */
    
    public String removeRequireAudit(String id);
}
