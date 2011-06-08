package com.zsgj.itil.workflow.rules;

import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.entity.SystemFile;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.exception.RuleFileException;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.PersonnelScope;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.base.JbpmContextFactory;
import com.zsgj.itil.account.entity.AccountSBUOfficer;
import com.zsgj.itil.account.entity.DCContacts;
import com.zsgj.itil.account.entity.MobileTelephoneApply;
import com.zsgj.itil.account.entity.PersonFormalAccount;
import com.zsgj.itil.account.entity.SpecialAccount;
import com.zsgj.itil.account.service.AccountService;
import com.zsgj.itil.account.webservice.HrInfoService;
import com.zsgj.itil.account.webservice.HrInfoServiceLocator;
import com.zsgj.itil.account.webservice.HrInfoServiceSoap_PortType;
import com.zsgj.itil.account.webservice.SenseServicesUitl;
import com.zsgj.itil.config.extci.entity.DeliveryTeam;
import com.zsgj.itil.config.extci.entity.ServiceEngineer;
import com.zsgj.itil.config.extlist.entity.ErpReqScopes;
import com.zsgj.itil.config.extlist.entity.MailForwardApply;
import com.zsgj.itil.config.extlist.entity.MailGroup;
import com.zsgj.itil.config.extlist.entity.NotesIDFile;
import com.zsgj.itil.config.service.ConfigItemService;
import com.zsgj.itil.finance.entity.RequirementFinanceType;
import com.zsgj.itil.require.entity.AccountApplyMainTable;
import com.zsgj.itil.require.entity.ERP_NormalNeed;
import com.zsgj.itil.require.entity.ErpEngineerFeedback;
import com.zsgj.itil.require.entity.RequireApplyDefaultAudit;
import com.zsgj.itil.require.entity.SRServiceProviderInfo;
import com.zsgj.itil.require.entity.SpecialRequirement;
import com.zsgj.itil.require.entity.SpecialRequirementInfo;
import com.zsgj.itil.require.service.RequireService;
import com.zsgj.itil.require.service.SRService;
import com.zsgj.itil.service.entity.Constants;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;
import com.zsgj.itil.service.entity.ServiceItemProcess;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.service.ServiceItemProcessService;

/**
 * ��������˺Ź�������ļ�
 * 
 * @Class Name RequireProcessRuleHelper
 * @Author lee
 * @Create In Sep 8, 2009
 */
public class RequireProcessRuleHelper {
	private String mainPath=System.getProperty("mainPath");//add by liuying at 20100602 for ȡ�÷�����·��
	Service service = (Service) ContextHolder.getBean("baseService");
	ConfigItemService configItemService = (ConfigItemService) ContextHolder.getBean("configItemService");
	MailSenderService ms = (MailSenderService) ContextHolder.getBean("mailSenderService");
	RequireService requireService = (RequireService) ContextHolder.getBean("requireService");
	AccountService accountService = (AccountService) ContextHolder.getBean("accountService");
	SRService srService = (SRService) ContextHolder.getBean("srService");
	ServiceItemProcessService serviceItemProcessServcie = (ServiceItemProcessService) ContextHolder
			.getBean("serviceItemProcessService");
	private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");



	/**
	 * �����������ʷ����
	 * 
	 * @Methods Name saveRequireHis
	 * @Create In Mar 23, 2009 By Administrator
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @param applyUser
	 *            //add by lee for ����ϵͳ�Զ���ת�ڵ�Ҳ����Ϊ�ǵ�ǰ�����˴����BUG
	 * @param configItem
	 *            void
	 */
	public void saveRequireHis(String dataId, String nodeId, String processId,
			String nodeName, String reqClass, String result, String comment,
			UserInfo applyUser, ServiceItem serviceItem) {

		ServiceItemApplyAuditHis siaah = new ServiceItemApplyAuditHis();
		siaah.setResultFlag(result);
		siaah.setProcessId(Long.valueOf(processId));
		siaah.setApprover(applyUser);
		siaah.setApproverDate(Calendar.getInstance().getTime());
		siaah.setServiceItem(serviceItem);
		siaah.setNodeName(nodeName);
		siaah.setComment(comment);
		siaah.setNodeId(nodeId);
		siaah.setRequirementClass(reqClass);
		siaah.setRequirementId(Long.valueOf(dataId));

		try {
			service.save(siaah);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * �������ʼ�ڵ�״̬�ı�(��Ҫ������״̬Ϊ�ͱ�����ʷ�Ȳ���)
	 * 
	 * @Methods Name configStartFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 *            void
	 */
	public void requireStartFlag(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass)
			throws Exception {

		Object result = this.findObjectByClassName(reqClass, dataId);
		BeanWrapper obj = new BeanWrapperImpl(result);
		obj.setPropertyValue("status", Constants.STATUS_APPROVING);
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass, "Y",
				"", curUser, serviceItem);
		service.save(result);

	}
	/**
	 * ����������ʼ�ڵ����
	 * @Methods Name requireStartForDev
	 * @Create In Nov 26, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @throws Exception void
	 */
	public void requireStartForDev(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass)
			throws Exception {

		Object result = this.findObjectByClassName(reqClass, dataId);
		BeanWrapper obj = new BeanWrapperImpl(result);
		obj.setPropertyValue("status", Constants.STATUS_APPROVING);
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass, "Y","", curUser, serviceItem);
		srService.initSRAttachment(dataId);
		service.save(result);

	}

	/**
	 * ������������ڵ�ı�״̬
	 * 
	 * @Methods Name requireAuditFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 *            void
	 */
	public void requireAuditFlag(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) {

		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
				result, comment, curUser, serviceItem);

	}

	/**
	 * �Ƿ���ҪBASIS����ʦ����
	 * 
	 * @Methods Name selectBASISEngineer
	 * @Create In Apr 10, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String selectBASISEngineer(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) {
		ERP_NormalNeed need = (ERP_NormalNeed) service.find(
				ERP_NormalNeed.class, dataId);
		ErpEngineerFeedback erpEngineerFeedback = (ErpEngineerFeedback) service
				.findUnique(ErpEngineerFeedback.class, "erpNeed", need);
		Integer basisFlag = erpEngineerFeedback.getBasisFlag();
		if (basisFlag == null || ErpEngineerFeedback.NOTNEED.equals(basisFlag)) {
			return "N";
		}
		return "Y";
	}

	/**
	 * �Ƿ���ҪEB����ʦ����
	 * 
	 * @Methods Name selectEBEngineer
	 * @Create In Apr 10, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String selectEBEngineer(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) {
		ERP_NormalNeed need = (ERP_NormalNeed) service.find(
				ERP_NormalNeed.class, dataId, true);
		List<ErpReqScopes> erpReqScopes = service.find(ErpReqScopes.class,
				"erpReq", need);
		for (ErpReqScopes erpReqScope : erpReqScopes) {
			String keyWord = erpReqScope.getUseScope().getKeyWord();
			if ("QJT".equals(keyWord)// ȫ�������
					|| "XF".equals(keyWord)// ����SBU
					|| "SY".equals(keyWord)// ����SBU
					|| "GYL".equals(keyWord)) {// ��Ӧ��SBU
				return "Y";
			}
		}
		return "N";
	}

	/**
	 * �Ƿ���Ҫ����
	 * @Methods Name isTransmis
	 * @Create In Dec 17, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String isTransmis(String dataId) {
		SpecialRequirement sr = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);
		SpecialRequirementInfo srInfo = (SpecialRequirementInfo) service.findUnique(SpecialRequirementInfo.class, "specialRequire", sr);
		Integer needOrNot = srInfo.getIsTransmis();
		if (needOrNot != null && needOrNot.equals(SpecialRequirementInfo.TRUE)) {
			return "Y";
		}
		return "N";
	}
	
	/**
	 * �Ƿ���Ҫ�����ŶӸ�����ָ����������
	 * 
	 * @Methods Name needSelectEngineer
	 * @Create In Jun 26, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String needSelectEngineer(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String result,
			String comment) {
		SpecialRequirement sr = (SpecialRequirement) service.find(
				SpecialRequirement.class, dataId);
		SRServiceProviderInfo spInfo = (SRServiceProviderInfo) service
				.findUnique(SRServiceProviderInfo.class, "specialRequire", sr);
		if (spInfo.getMainEngineer() == null) {
			return "Y";// ��������δѡ�񡰼����ܼ���������ת���������߸�����ָ������������
		} else {
			return "N";// ѡ�񡰽��������������ܼ���������ת��������������дʵʩ������
		}
	}

	/**
	 * �Ƿ���Ҫ��ҵ������
	 * 
	 * @Methods Name needDivisionOrNot
	 * @Create In Nov 11, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String needDivisionOrNot(String dataId) {
		SpecialRequirement sr = (SpecialRequirement) service.find(
				SpecialRequirement.class, dataId);
		if (sr.getConfirmUser() != null) {
			return "Y";// ����ҵ��������ʱ��ת����ҵ������
		} else {
			return "N";// ����ҵ��������ʱ��ͨ����ҵ������
		}
	}

	/**
	 * �ڲ��ͻ���չ�ж�
	 * 
	 * @Methods Name requireRemarkFlag
	 * @Create In Apr 15, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String requireRemarkFlag(String dataId) {
		SpecialRequirement sr = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);
		RequirementFinanceType financeType = sr.getFinanceType();
		financeType = (RequirementFinanceType) service.find(RequirementFinanceType.class, financeType.getId().toString());
		if (RequirementFinanceType.TYPE_REMARK.equals(financeType.getKeyWord())) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * �ж��Ƿ���������������ת
	 * 
	 * @Methods Name isBatch
	 * @Create In Jul 14, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String isBatch(String dataId) {
		SpecialRequirement sr = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);
		RequirementFinanceType financeType = sr.getFinanceType();
		financeType = (RequirementFinanceType) service.find(RequirementFinanceType.class, financeType.getId().toString());
		if (RequirementFinanceType.TYPE_BATCH.equals(financeType.getKeyWord())) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * �ж��Ƿ���Ϊ�½�������������ת
	 * 
	 * @Methods Name isNewFactory
	 * @Create In Jul 14, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String isNewFactory(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment,String sbuMailRoleId) {
		SpecialRequirement sr = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);
		Integer isNewFactory = sr.getIsNewFactory();
		if (Integer.valueOf(1).equals(isNewFactory)) {
			ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,serviceItemId);
			BeanWrapper bw = new BeanWrapperImpl(sr);			
			UserInfo applyUser = (UserInfo) bw.getPropertyValue("createUser");	
			String reqName = (String) bw.getPropertyValue("name");			
			String reqCode = "";
			if(bw.isReadableProperty("applyNum")){
				reqCode = (String) bw.getPropertyValue("applyNum");
			}	
			Integer processType = (Integer) bw.getPropertyValue("processType");
			ServiceItemProcess process = serviceItemProcessServcie
			.findProcessesByServiceItemAndType(serviceItem, processType);			
			PageModel pm = process.getEndPageModel();	
			String definitionName=process.getDefinitionName();			
			String endUrl = pm.getPagePath();
			String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
			String curUrl = rootPath + endUrl + "?dataId=" + dataId;
			String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
			List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ			
			//�½����������˷���
			if (bw.isReadableProperty("flat")) {
//				RequireApplyDefaultAudit flat = (RequireApplyDefaultAudit) bw.getPropertyValue("flat"); //�õ�SBU��Χ
//				String flatId = flat.getId().toString();
				if(StringUtils.isNotBlank(sbuMailRoleId)){//������㴥��Ҫ��
					Role role = (Role) service.find(Role.class, sbuMailRoleId);
					String sendMailTatle = "IT��ܰ��ʾ��"+applyUser.getUserName()+"/"+applyUser.getRealName()+"�ύ������(����ţ�"+reqCode+"���������ƣ�"+reqName+")��ͨ������";
					//���½�����������ʦ�ʼ�
					if(role!=null){
						Set<UserInfo> mailUsers = role.getUserInfos();
						for(UserInfo user : mailUsers){
							String sendMail = user.getEmail();
							ms.sendMimeMail(sendMail, null, null, sendMailTatle, this.erpReqMsHtmlContent(
									applyUser, user, curUrl, reqName,reqCode,auditHis,definitionName), null);
						}
					}
				}
			}	
			return "N";//���ͻ�Ҫ�󲻽��롰�½�������ǩ����������ֻ���ʼ����鿴
		} else {
			return "N";
		}
	}
	/**
	 * �Ƿ���ERP�࿪����������
	 * @Methods Name isERP
	 * @Create In Nov 25, 2009 By lee
	 * @param dataId
	 * @param sbuId��������д��������SBUID
	 * @return String
	 */
	public String isERP(String dataId,String sbuId) {
		boolean reslut = srService.isToFinancial(dataId,sbuId);
		if (reslut) {
			return "Y";
		} else {
			return "N";
		}
	}
	/**
	 * �Ƿ�ֱ��ָ�ɽ�������
	 * @Methods Name isToEngineer
	 * @Create In Nov 25, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String isToEngineer(String dataId) {
		SpecialRequirement sp = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);
		if (sp.getMainEngineer()!=null) {
			return "N";
		} else {
			return "Y";
		}
	}
	
	/**
	 * �Ƿ���Ҫҵ����������������������
	 * @Methods Name isApp
	 * @Create In Nov 25, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String isApp(String dataId) {
		SpecialRequirement sp = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);
		if (sp.getAppManager()!=null) {
			return "Y";
		} else {
			return "N";
		}
	}
	/**
	 * �Ƿ񵥶���̯������������
	 * @Methods Name isShare
	 * @Create In Nov 25, 2009 By lee
	 * @param dataId
	 * @return String
	 */
	public String isShare(String dataId) {
		SpecialRequirement sp = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);
		if (Integer.valueOf(1).equals(sp.getIsShare())) {
			return "Y";
		} else {
			return "N";
		}
	}
	
	/**
	 * �Ƿ��з����ܼࣨ����������
	 * @Methods Name hasServiceManager
	 * @Create In  2010-6-18 By zhangzy
	 * @param dataId
	 * @return String
	 */
	public String hasServiceManager(String dataId) {
		SpecialRequirement sp = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);
		DeliveryTeam spi = sp.getDeliveryTeam();
		UserInfo majordomo = spi.getMajordomo();
		if (majordomo != null) {			//��������ܼ಻Ϊ�գ����ء�Y��
			return "Y";
		} else {
			return "N";
		}
	}
	
	
	/**
	 * ��ʼ����ʵʩ�׶ι���
	 * 
	 * @Methods Name toRealStartDate
	 * @Create In Nov 10, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @throws Exception
	 *             void
	 */
	public void toRealStartDate(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) throws Exception {
		Object obj = this.findObjectByClassName(reqClass, dataId);// ��ȡ��ǰ������ʵ��
		BeanWrapper bw = new BeanWrapperImpl(obj);

		if (bw.isReadableProperty("realStartDate")) {
			bw.setPropertyValue("realStartDate", new Date());
		}
		service.save(obj);
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
				result, comment, curUser, serviceItem);
	}

	/**
	 * ��ʼ������Խ׶ι���
	 * 
	 * @Methods Name toRealTestBeginDate
	 * @Create In Nov 10, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @throws Exception
	 *             void
	 */
	public void toRealTestBeginDate(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) throws Exception {
		Object obj = this.findObjectByClassName(reqClass, dataId);// ��ȡ��ǰ������ʵ��
		BeanWrapper bw = new BeanWrapperImpl(obj);
		if (bw.isReadableProperty("realTestBeginDate")) {
			bw.setPropertyValue("realTestBeginDate", new Date());
		}
		service.save(obj);
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
				result, comment, curUser, serviceItem);
	}

	/**
	 * ����ʱ���������
	 * 
	 * @Methods Name toRealTestEndDate
	 * @Create In Nov 10, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @throws Exception
	 *             void
	 */
	public void toRealTestEndDate(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) throws Exception {
		Object obj = this.findObjectByClassName(reqClass, dataId);// ��ȡ��ǰ������ʵ��
		BeanWrapper bw = new BeanWrapperImpl(obj);
		if (bw.isReadableProperty("realTestEndDate")) {
			bw.setPropertyValue("realTestEndDate", new Date());
		}
		service.save(obj);
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
				result, comment, curUser, serviceItem);
	}
	
	/**
	 * 
	 * @Methods Name toERPRealTestEndDate
	 * @Create In Dec 10, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @param sbus	//�����ʼ�SBU
	 * @param sbuMailRoleId	//SBU�ʼ���ɫ
	 * @param typeMailMap	�������������ʼ�Map<list,role>
	 * 						listΪ���ͣ�roleΪ�����ʹ����ʼ���ɫ
	 * @throws Exception void
	 */
	public void toERPRealTestEndDate(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment,List sbus,String sbuMailRoleId,Map typeMailMap) throws Exception {
		SpecialRequirement sr = (SpecialRequirement) service.find(SpecialRequirement.class, dataId);// ��ȡ��ǰ������ʵ��
		BeanWrapper bw = new BeanWrapperImpl(sr);
		if (bw.isReadableProperty("realTestEndDate")) {
			bw.setPropertyValue("realTestEndDate", new Date());
		}
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("createUser");
		service.save(sr);
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
				result, comment, curUser, serviceItem);
		String reqName = (String) bw.getPropertyValue("name");
		String reqCode = "";
		if(bw.isReadableProperty("applyNum")){
			reqCode = (String) bw.getPropertyValue("applyNum");
		}
		Integer processType = (Integer) bw.getPropertyValue("processType");
		ServiceItemProcess process = serviceItemProcessServcie
		.findProcessesByServiceItemAndType(serviceItem, processType);
		PageModel pm = process.getEndPageModel();
		String endUrl = pm.getPagePath();
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String curUrl = rootPath + endUrl + "?dataId=" + dataId;
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String definitionName=process.getDefinitionName();
		//����SUB
		if (bw.isReadableProperty("flat")) {
			RequireApplyDefaultAudit flat = (RequireApplyDefaultAudit) bw.getPropertyValue("flat"); //�õ�SBU��Χ
			String flatId = flat.getId().toString();
			if(sbus.contains(flatId)&&StringUtils.isNotBlank(sbuMailRoleId)){//������㴥��Ҫ��
				Role role = (Role) service.find(Role.class, sbuMailRoleId);
				String sendMailTatle = "IT��ܰ��ʾ��"+applyUser.getUserName()+"/"+applyUser.getRealName()+"�ύ������(����ţ�"+reqCode+"���������ƣ�"+reqName+")��ͨ������";
				//��EB����ʦ�ʼ�
				if(role!=null){
					Set<UserInfo> mailUsers = role.getUserInfos();
					for(UserInfo user : mailUsers){
						String sendMail = user.getEmail();
						ms.sendMimeMail(sendMail, null, null, sendMailTatle, this.erpReqMsHtmlContent(
								applyUser, user, curUrl, reqName,reqCode,auditHis,definitionName), null);
					}
				}
				//�����������������ʼ�
				UserInfo homeFinanceManager = (UserInfo) service.find(UserInfo.class, sr.getHomeFinanceManager().getId().toString(), true);
				ms.sendMimeMail(homeFinanceManager.getEmail(), null, null, sendMailTatle, this.erpReqMsHtmlContent(
						applyUser, homeFinanceManager, curUrl, reqName,reqCode,auditHis,definitionName), null);
			}
		}
		//��ȡERP�ǳ�������С����Ϣ
		if(sr.getSmallType()!=null){
			String smaillTypeId = sr.getSmallType().toString();
			Map<List<String>,String> dataMap = typeMailMap;
			Set<List<String>> mapSet = dataMap.keySet();
			for(List<String> list : mapSet){
				if(list.contains(smaillTypeId)){
//					String roleId = dataMap.get(list);
					Role role = (Role) service.find(Role.class, sbuMailRoleId);
					if(role!=null){
						Set<UserInfo> mailUsers = role.getUserInfos();
						String sendMailTatle = "IT��ܰ��ʾ��"+applyUser.getUserName()+"/"+applyUser.getRealName()+"�ύ������(����ţ�"+reqCode+"���������ƣ�"+reqName+")��ͨ������";
						for(UserInfo user : mailUsers){
							String sendMail = user.getEmail();
							ms.sendMimeMail(sendMail, null, null, sendMailTatle, this.erpReqMsHtmlContent(
									applyUser, user, curUrl, reqName,reqCode,auditHis,definitionName), null);
						}
					}
				}
			}
		}
	}
	
	/**
	 * ERP�ǳ�����Խ�������
	 * @Methods Name erpSrToRealTestEndDate
	 * @Create In Dec 9, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @throws Exception void
	 */
	public void erpSrToRealTestEndDate(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) throws Exception {
		Object obj = this.findObjectByClassName(reqClass, dataId);// ��ȡ��ǰ������ʵ��
		BeanWrapper bw = new BeanWrapperImpl(obj);
		if (bw.isReadableProperty("realTestEndDate")) {
			bw.setPropertyValue("realTestEndDate", new Date());
		}
		service.save(obj);
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
				result, comment, curUser, serviceItem);
	}
	/**
	 * ����ʦ�������
	 * 
	 * @Methods Name transmitByEngineer
	 * @Create In 28 01, 2010 By zhangzy
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @throws Exception 
	 *             void
	 */
public void transmitByEngineer(String dataId, String serviceItemId,
		String nodeId, String nodeName, String processId, String reqClass,
		String result, String comment) throws Exception{
	
		Object obj = this.findObjectByClassName(reqClass, dataId);// ��ȡ��ǰ������ʵ��
		BeanWrapper bw = new BeanWrapperImpl(obj);
//		UserInfo applyUser = (UserInfo) bw.getPropertyValue("createUser");
		ServiceEngineer sei = (ServiceEngineer) bw.getPropertyValue("mainEngineer");
		String email = "";
		UserInfo engineerInfo = null;
		if(sei != null){
			engineerInfo = sei.getUserInfo();
			if(engineerInfo != null){
				email = engineerInfo.getEmail();
			}			
		}
		String reqName = (String) bw.getPropertyValue("name");
		String reqCode = "";
		if(bw.isReadableProperty("applyNum")){
			reqCode = (String) bw.getPropertyValue("applyNum");
		}		
		
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);		
		Integer processType = (Integer) bw.getPropertyValue("processType");
		ServiceItemProcess process = serviceItemProcessServcie
		.findProcessesByServiceItemAndType(serviceItem, processType);
		PageModel pm = process.getEndPageModel();
		String endUrl = pm.getPagePath();
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String curUrl = rootPath + endUrl + "?dataId=" + dataId;
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";
        List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String definitionName=process.getDefinitionName();
		
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
				result, comment, curUser, serviceItem);
		if("Y".equals(result)){	//������乤��ʦ����ͬ�� 
				SpecialRequirement sr = (SpecialRequirement)service.find(SpecialRequirement.class, dataId);
				SpecialRequirementInfo sri = (SpecialRequirementInfo) service.findUnique(SpecialRequirementInfo.class, "specialRequire", sr);
				UserInfo transmisEngineer = UserContext.getUserInfo();
				sri.setTransmisEngineer(transmisEngineer);
				service.save(sri);//��ȡ��ǰ�����˴����ֶΣ����乤��ʦ
				String subject="IT��ܰ��ʾ��������������(����ţ�"+reqCode+"���������ƣ�"+reqName+")����ʦ���������";
				ms.sendMimeMail(email, null, null, subject, this.reqEndHtmlContentToEngineer(
						engineerInfo, curUrl, reqName,reqCode,auditHis,definitionName), null);
		}
	
}
	/**
	 * ��������ؽڵ�״̬�ı�(��Ҫ������״̬Ϊ�ͱ�����ʷ�Ȳ���)
	 * 
	 * @Methods Name configBackFlag
	 * @Create In Mar 23, 2009 By Administrator
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 *            void
	 */
	public void requireBackFlag(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) throws Exception {

		Object obj = this.findObjectByClassName(reqClass, dataId);
		BeanWrapper bw = new BeanWrapperImpl(obj);
		bw.setPropertyValue("status", Constants.STATUS_DRAFT);
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("createUser");
		String email = applyUser.getEmail();
		String reqName = (String) bw.getPropertyValue("name");
		//modify by lee for ���Ӷ������ŵĴ��� in 20091126 begin
		String reqCode = "";
		if(bw.isReadableProperty("applyNum")){
			reqCode = (String) bw.getPropertyValue("applyNum");
		}
		//modify by lee for ���Ӷ������ŵĴ��� in 20091126 end
		Integer processType = (Integer) bw.getPropertyValue("processType");
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
				result, comment, null, serviceItem);

		service.save(obj);
		ServiceItemProcess process = serviceItemProcessServcie
				.findProcessesByServiceItemAndType(serviceItem, processType);
		PageModel pm = process.getEndPageModel();
		// modify by lee for ���ӶԿ�ҳ����ж� in 20091107 begin
		String endUrl = "";
		if (pm != null) {
			endUrl = pm.getPagePath();
		}
		// modify by lee for ���ӶԿ�ҳ����ж� in 20091107 begin
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String curUrl = rootPath + endUrl + "?dataId=" + dataId;
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
        List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String definitionName=process.getDefinitionName();
		String subject="IT��ܰ��ʾ�����ύ����������ţ�"+reqCode+"���������ƣ�"+reqName+"��δͨ������";
		ms.sendMimeMail(email, null, null, subject, this.reqBackHtmlContent(
				applyUser, curUrl, reqName,reqCode,auditHis,definitionName), null);
	}

	/**
	 * �����������ڵ�״̬�ı�(��Ҫ������״̬Ϊ�ͱ�����ʷ�Ȳ���)
	 * 
	 * @Methods Name configBackFlag
	 * @Create In Mar 23, 2009 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 *            void
	 */
	public void requireEndFlag(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass)
			throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		BaseObject obj = (BaseObject) this.findObjectByClassName(reqClass,
				dataId);
		BeanWrapper bw = new BeanWrapperImpl(obj);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		// add by lee for ��¼��Ŀ��ʵ����ʱ�� in 20091110 begin
		if (bw.isReadableProperty("realFinishDate")) {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			bw.setPropertyValue("realFinishDate", new Date());
		}
		// add by lee for ��¼��Ŀ��ʵ����ʱ�� in 20091110 end
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("createUser");
		String email = applyUser.getEmail();
		String reqName = (String) bw.getPropertyValue("name");
		//modify by lee for ���Ӷ������ŵĴ��� in 20091126 begin
		String reqCode = "";
		if(bw.isReadableProperty("applyNum")){
			reqCode = (String) bw.getPropertyValue("applyNum");
		}
		//modify by lee for ���Ӷ������ŵĴ��� in 20091126 end
		Integer processType = (Integer) bw.getPropertyValue("processType");
		service.save(obj);
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass, "",
				"", null, serviceItem);
		requireService.saveEntityToEvent(reqClass, obj);
		ServiceItemProcess process = serviceItemProcessServcie
				.findProcessesByServiceItemAndType(serviceItem, processType);
		PageModel pm = process.getEndPageModel();
		String endUrl = pm.getPagePath();
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String curUrl = rootPath + endUrl + "?dataId=" + dataId;
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
        List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String definitionName=process.getDefinitionName();
		String subject="IT��ܰ��ʾ�����ύ������(����ţ�"+reqCode+"���������ƣ�"+reqName+")�����";
		ms.sendMimeMail(email, null, null, subject, this.reqEndHtmlContent(
				applyUser, curUrl, reqName,reqCode,auditHis,definitionName), null);
	}
	
	/**
	 * 
	 * @Methods Name requireErpEndFlag
	 * @Create In Dec 9, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param sbus	�����ʼ���SBU
	 * @param sbuMailRoleId	SBU�����ʼ�ID
	 * @param otherMailRoleId	���������ʼ�ID
	 * @throws Exception void
	 */
	public void requireErpEndFlag(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,List sbus,String sbuMailRoleId, String otherMailRoleId)
			throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,serviceItemId);
		BaseObject obj = (BaseObject) this.findObjectByClassName(reqClass,dataId);
		RequireApplyDefaultAudit flat = null;	//SBU��Χ
		BeanWrapper bw = new BeanWrapperImpl(obj);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		if (bw.isReadableProperty("realFinishDate")) {
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			bw.setPropertyValue("realFinishDate", new Date());
		}
		
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("createUser");
		String email = applyUser.getEmail();
		String reqName = (String) bw.getPropertyValue("name");
		String reqCode = "";
		if(bw.isReadableProperty("applyNum")){
			reqCode = (String) bw.getPropertyValue("applyNum");
		}
		Integer processType = (Integer) bw.getPropertyValue("processType");
		service.save(obj);
		this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass, "","", null, serviceItem);
		requireService.saveEntityToEvent(reqClass, obj);
		ServiceItemProcess process = serviceItemProcessServcie
				.findProcessesByServiceItemAndType(serviceItem, processType);
		PageModel pm = process.getEndPageModel();
		String endUrl = pm.getPagePath();
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String curUrl = rootPath + endUrl + "?dataId=" + dataId;
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
        List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String definitionName=process.getDefinitionName();
		String subject="IT��ܰ��ʾ�����ύ������(����ţ�"+reqCode+"���������ƣ�"+reqName+")�����";
		ms.sendMimeMail(email, null, null, subject, this.reqEndHtmlContent(
				applyUser, curUrl, reqName,reqCode,auditHis,definitionName), null);
		
		if (bw.isReadableProperty("flat")) {
			flat = (RequireApplyDefaultAudit) bw.getPropertyValue("flat"); //�õ�SBU��Χ
			if(flat != null){
				String flatId = flat.getId().toString();
				if(sbus.contains(flatId)&&StringUtils.isNotBlank(sbuMailRoleId)){//������㴥��Ҫ��
					Role role = (Role) service.find(Role.class, sbuMailRoleId);
					if(role!=null){
						Set<UserInfo> mailUsers = role.getUserInfos();
						String sendMailTatle = "IT��ܰ��ʾ��"+applyUser.getUserName()+"/"+applyUser.getRealName()+"�ύ������(����ţ�"+reqCode+"���������ƣ�"+reqName+")�����";
						for(UserInfo user : mailUsers){
							String sendMail = user.getEmail();
							ms.sendMimeMail(sendMail, null, null, sendMailTatle, this.erpReqMsHtmlContent(
									applyUser, user, curUrl, reqName,reqCode,auditHis,definitionName), null);
						}
					}
				}
			}
		}
		//��������ɫ���ţ�Ŀǰֻ���ڿ�������ʱ���������㾭���ţ�
		if(StringUtils.isNotBlank(otherMailRoleId)){
			Role role = (Role) service.find(Role.class, sbuMailRoleId);
			if(role!=null){
				Set<UserInfo> mailUsers = role.getUserInfos();
				String sendMailTatle = "IT��ܰ��ʾ��"+applyUser.getUserName()+"/"+applyUser.getRealName()+"�ύ������(����ţ�"+reqCode+"���������ƣ�"+reqName+")�����";
				for(UserInfo user : mailUsers){
					String sendMail = user.getEmail();
					ms.sendMimeMail(sendMail, null, null, sendMailTatle, this.erpReqMsHtmlContent(
							applyUser, user, curUrl, reqName,reqCode,auditHis,definitionName), null);
				}
			}
		}
	}

	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("������" + className + "����ȷ��");
			e.printStackTrace();
		}
		return clazz;
	}

	/**
	 * ���ɶ���ʵ��
	 * 
	 * @Methods Name findObjectByClassName
	 * @Create In Mar 23, 2009 By lee
	 * @param reqClass
	 * @param dataId
	 * @return
	 * @throws Exception
	 */
	public Object findObjectByClassName(String reqClass, String dataId)
			throws Exception {

		Class clazzClass = getClass(reqClass);
		Object object = service.find(clazzClass, dataId, true);
		return object;

	}

	/**
	 * ����������ͨ�������û���������
	 * 
	 * @Methods Name reqEndHtmlContent
	 * @Create In Aug 29, 2009 By lee
	 * @param creator
	 * @param url
	 * @param reqNameInfo
	 * @return String
	 */
	private String reqEndHtmlContent(UserInfo creator, String url,
			String reqName,String reqCode,List auditHis,String definitionName) {

		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");
        sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
        sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");
        sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");
        sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"
				+ creator.getRealName() + "/" + creator.getUserName()
				+ "������:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">���ύ������(�����:"+reqCode+"�������ƣ�"+reqName+")�Ѵ������,<a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ��" + "</a>лл��</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
//				String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
//				String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("�ύ")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"�ύ��";
				}else {
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"����ͨ����";
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}
	
	
	/**
	 * ���������̹���ʦ������򽻸�����������
	 * 
	 * @Methods Name reqEndHtmlContent
	 * @Create In 29 01, 2010 By zhangzy
	 * @param creator
	 * @param url
	 * @param reqNameInfo
	 * @return String
	 */
	private String reqEndHtmlContentToEngineer(UserInfo creator, String url,
			String reqName,String reqCode,List auditHis,String definitionName) {

		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");
        sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
        sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");
        sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");
        sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"
				+ creator.getRealName() + "/" + creator.getUserName()
				+ "������:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">������������(�����:"+reqCode+"�������ƣ�"+reqName+")����ʦ���������,<a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ��" + "</a>лл��</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
//				String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
//				String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("�ύ")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"�ύ��";
				}else {
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"����ͨ����";
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}	

	/**
	 * ERP�����֪ͨ�˵��ʼ�
	 * @Methods Name erpReqMsHtmlContent
	 * @Create In Dec 9, 2009 By lee
	 * @param creator
	 * @param addressee
	 * @param url
	 * @param reqName
	 * @param reqCode
	 * @param auditHis
	 * @param definitionName
	 * @return String
	 */
	private String erpReqMsHtmlContent(UserInfo creator, UserInfo addressee, String url,
			String reqName,String reqCode,List auditHis,String definitionName) {

		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");
		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");
		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");
		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"+addressee.getRealName() + "/" + addressee.getUserName()+"������:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">"+creator.getRealName() + "/" + creator.getUserName()+"�ύ������(�����:"+reqCode+"�������ƣ�"+reqName+"),<a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ��" + "</a>лл��</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
//				String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
//				String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("�ύ")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"�ύ��";
				}else {
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"����ͨ����";
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}

	/**
	 * ���������̱��ܾ������û���������
	 * 
	 * @Methods Name reqBackHtmlContent
	 * @Create In Aug 29, 2009 By lee
	 * @param creator
	 * @param url
	 * @param reqNameInfo
	 * @return String
	 */
	private String reqBackHtmlContent(UserInfo creator, String url,
			String reqName,String reqCode,List auditHis,String definitionName) {

		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb
				.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");

		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");

		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");

		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");

		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"
				+ creator.getRealName() + "/" + creator.getUserName()
				+ "������:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">���ύ������(�����:"+reqCode+"��������:"+reqName+")<font color=#FF0000>δͨ������</font>,<a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ��" + "</a>лл��</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
				String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
				String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("�ύ")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"�ύ��";
				}else if(resultFlag.equals("Y")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"����ͨ����";
				}else{
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"<font color=#FF0000>�����ܾ�</font>���������:"+comment;
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}

	// //////////////////////////////////////////�˺Ź�����////BEGIN//////////////////////////////////////
	/**
	 * �˺Ź���ʼ�ڵ�״̬�ı�(��Ҫ������״̬Ϊ�ͱ�����ʷ�Ȳ���)
	 * 
	 * @Methods Name accountStart
	 * @Create In May 26, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void accountStart(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		Object object = this.findObjectByClassName(className, dataId);
		BeanWrapper obj = new BeanWrapperImpl(object);
		obj.setPropertyValue("status", Constants.STATUS_APPROVING);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
				"Y", "", curUser, serviceItem);
		service.save(object);
	}

	/**
	 * �˺Ź���ʼ�ڵ�״̬�ı�(��Ҫ������״̬Ϊ�ͱ�����ʷ�Ȳ���)
	 * 
	 * @Methods Name accountStartNewITAccount
	 * @Create In SEP 26, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void accountStartNewITAccount(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String hrJob)
			throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		Object object = this.findObjectByClassName(className, dataId);
		BeanWrapper obj = new BeanWrapperImpl(object);
		obj.setPropertyValue("status", Constants.STATUS_APPROVING);
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
				"Y", "", curUser, serviceItem);
		if (hrJob != null) {
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			ProcessInstance processInstance = jbpmContext
					.loadProcessInstance(Long.parseLong(processId));
			Token rootToken = processInstance.getRootToken();
			Node node = processInstance.getProcessDefinition().getNode(
					"�˺Ź���Ա����");
			ExecutionContext ec = new ExecutionContext(rootToken);
			node.enter(ec);
		}
		service.save(object);
	}

	/**
	 * �˺Ź���һ���ύ����״̬����
	 * 
	 * @Methods Name accountAudit
	 * @Create In May 26, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 *            void
	 */

	public void accountAudit(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String result,
			String comment) {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		// this.saveAccountHis(dataId,nodeId,processId,nodeName,"Y","",serviceItem);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		UserInfo curUser = UserContext.getUserInfo();
		this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
				result, comment, curUser, serviceItem);
	}



	/**
	 * �˺��������״̬����
	 * 
	 * @Methods Name accountEnd
	 * @Create In May 26, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 * void
	 */
	public void accountEnd(String dataId, String serviceItemId, String nodeId,
			String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<PersonFormalAccount> account = service.find(PersonFormalAccount.class,
				"applyId", object);
		BeanWrapper bw = new BeanWrapperImpl(object);
		ServiceItemProcess sip = (ServiceItemProcess) bw
		.getPropertyValue("serviceItemProcess");
		if(sip.getId().compareTo(313L)==0){
			for(PersonFormalAccount pfa:account){
				if (pfa.getAccountType().getAccountType().equals("WWWAccount")){
					SenseServicesUitl ssUtil = new SenseServicesUitl();
					String mes=ssUtil.deleteWWWAccount(pfa.getAccountowner().getItcode());
					if(!mes.equals("success")){
						throw new RuleFileException(mes);
					}
				} 
				if (pfa.getAccountType().getAccountType().equals("MSNAccount")){
					SenseServicesUitl ssUtil = new SenseServicesUitl();
					String mes=ssUtil.deleteMSNAccount(pfa.getAccountowner().getItcode());
					if(!mes.equals("success")){
						throw new RuleFileException(mes);
					}
				} 
			}
		}
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
//		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
//				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String definitionName=sip.getDefinitionName();
		//add by liuying for ��Ա����ְʱɾ��DCͨѶ¼��Ϣ in 20100423 begin
		if(definitionName.equals("Ա����ְ�ʺ�ע������")){
			if(applyUser!=null){
				String itCode = applyUser.getItcode();
				DCContacts employee = (DCContacts)service.findUnique(
						DCContacts.class, "itcode", itCode);
				if (employee != null) {
					this.updateDCContacts(employee.getEmployeeCode(),null,null,null,true);
					service.remove(employee);
				}
				//add by liuying on 20100629 for ��ְʱɾ�������ֻ����� start
				MobileTelephoneApply mobileTelephone=accountService.findMobileTelephone("�ֻ�", applyUser);
				if(mobileTelephone!=null){
					mobileTelephone.setAccountState("0");
					service.save(mobileTelephone);
				}
				//add by liuying on 20100629 for ��ְʱɾ�������ֻ����� end
			}
		}
		//add by liuying for ��Ա����ְʱɾ��DCͨѶ¼��Ϣ in 20100423 end
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {
               e.printStackTrace();
		}

//		if (oldObject != null) {
//			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
//			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
//			BeanUtils.copyProperties(object, oldObject);
//			oldBeanWrapper.setPropertyValue("id", oldId);
//			service.save(oldObject);
//			service.remove(object);
//			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
//					"Y", "", null, serviceItem);
//			requireService.saveEntityToEvent(className, oldObject);
//		} else {
//			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
//			requireService.saveEntityToEvent(className, object);
//		}
	}
	
	
	
	/**
	 * ���ű���������״̬����
	 * 
	 * @Methods Name accountDeptChange
	 * @Create In May 26, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void accountDeptChange(String dataId, String serviceItemId, String nodeId,
			String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		DCContacts employee = accountService.saveOrGetContacts(applyUser.getItcode());
		
		employee.setWorkSpace(applyUser.getWorkSpace());
		//modify by liuying at 20100322 for �޸ı����ʼ���������Ϣ�Ĵ��� start
		if(applyUser.getMailServer()!=null){
			//employee.setMailServer(applyUser.getMailServer().getId().toString());
			employee.setMailServer(applyUser.getMailServer().getName());
		}
		//modify by liuying at 20100322 for �޸ı����ʼ���������Ϣ�Ĵ��� end
		employee.setSameMailDept(applyUser.getSameMailDept());
		//add by liuying at 20100913 for ���ű�������粻������������ �����ͨѶ¼ start
		List<PersonFormalAccount> account = service.find(PersonFormalAccount.class,
				"applyId", object);
		if(account!=null&&account.size()!=0){
			for(PersonFormalAccount pfa:account){
				if(pfa.getAccountType().getAccountType().equals("Telephone")){
					if(pfa.getDepartTelephone().equals("0")&&pfa.getIfHold()==0){
						employee.setTelephone(null);
						employee.setVoipPhone(null);
						this.updateDCContacts(applyUser.getEmployeeCode(), null, employee.getMobilePhone(), null, false);
					}
				}
			}
		}
		//add by liuying at 20100913 for ���ű�������粻������������ �����ͨѶ¼ end
		service.save(employee);//111
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String definitionName=sip.getDefinitionName();
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {
               e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}


	/**
	 * �ʼ�ת���������״̬����
	 * 
	 * @Methods Name accountEnd
	 * @Create In May 26, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void mailForwardEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<MailForwardApply> account = service.find(MailForwardApply.class,
				"applyId", object);
		Date currentDate = DateUtil.getCurrentDate();
		for (MailForwardApply personAccount : account) {
			MailForwardApply mail = personAccount.getOldApply();
			if (mail != null) {
				mail.setAccountState("0");
				mail.setStopDate(currentDate);
				service.save(mail);
			} else {
				personAccount.setAccountState("1");
				personAccount.setCreateDate(currentDate);
				service.save(personAccount);
			}
		}
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String definitionName=sip.getDefinitionName();
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * �ʼ�Ⱥ���������״̬����
	 * 
	 * @Methods Name mailGroupEnd
	 * @Create In May 26, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void mailGroupEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<MailGroup> accounts = service.find(MailGroup.class, "applyId",
				object);
		for (MailGroup account : accounts) {
			MailGroup oldAccount = account.getOldApply();
			if (oldAccount != null) {
				oldAccount.setAccountState("0");
				service.save(oldAccount);
				account.setAccountState("1");
				service.save(account);
			} else {
				account.setAccountState("1");
				service.save(account);
			}
		}
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * �ʼ�Ⱥ��ɾ���������״̬����
	 * 
	 * @Methods Name mailGroupDeleteEnd
	 * @Create In May 26, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void mailGroupDeleteEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<MailGroup> accounts = service.find(MailGroup.class, "applyId",
				object);
		for (MailGroup account : accounts) {
			MailGroup oldAccount = account.getOldApply();
			oldAccount.setAccountState("0");
			service.save(oldAccount);

		}
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * �����ʺ��������״̬����
	 * 
	 * @Methods Name accountPersonEnd
	 * @Create In 11 13, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 * void
	 */
	public void accountPersonEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		PersonFormalAccount account = (PersonFormalAccount) service.findUnique(
				PersonFormalAccount.class, "applyId", object); // ��ȡ����ʵ��
		account.setAccountState("1");
		//add by lee for ���������������ͨѶ¼��Ϣ in 20100127 begin
		if(account.getAccountType().getAccountType().equals("Telephone")&&account.getDepartTelephone().equals("0")){
			UserInfo accountOwner = (UserInfo) service.find(UserInfo.class, account.getAccountowner().getId().toString());
			DCContacts employee = (DCContacts)service.findUnique(
					DCContacts.class, "itcode", accountOwner.getItcode());
			//modify by liuying for �޸ĸ���ͨѶ¼ʱ������������ at 20100423 start
			//employee.setTelephone(account.getTelephone());
			employee.setTelephone(account.getTelephoneNumber());
			//modify by liuying for �޸ĸ���ͨѶ¼ʱ������������ at 20100423 start
			employee.setVoipPhone(account.getVoip());
			employee = (DCContacts) service.save(employee);
			this.updateDCContacts(accountOwner.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
		}
		//add by lee for ���������������ͨѶ¼��Ϣ in 20100127 end
		service.save(account);
		
		PersonFormalAccount oldApplyAccount = account.getOlodApplyAccount();
		if (oldApplyAccount != null) {
			oldApplyAccount.setAccountState("0");
			service.save(oldApplyAccount);
		}
		BeanWrapper bw = new BeanWrapperImpl(object);
		ServiceItemProcess sip = (ServiceItemProcess) bw
		.getPropertyValue("serviceItemProcess");
		if(sip.getId().compareTo(156L)==0){
			SenseServicesUitl ssUtil = new SenseServicesUitl();
			String mes=ssUtil.addWWWAccount(account.getAccountowner().getItcode());
			if(!mes.equals("success")){
				throw new RuleFileException(mes);
			}
		}
		if(sip.getId().compareTo(157L)==0){
			SenseServicesUitl ssUtil = new SenseServicesUitl();
			String mes=ssUtil.addMSNAccount(account.getAccountowner().getItcode());
			if(!mes.equals("success")){
				throw new RuleFileException(mes);
			}
		}
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		String contextEB = this.htmlContentForEB(applyUser, realUrl,auditHis,definitionName);
		String subjectEB = "IT��ܰ��ʾ��"+applyUser.getRealName()+"/"+applyUser.getUserName()+"�ύ��" +definitionName+ "�Ѵ������,��鿴!";
		//add by liuying at 20100409 for ��������������߱����������ԭ������ͨѶ¼ start
		if(definitionName.equals("���������߱������")&&oldApplyAccount!=null){
			String isdept=oldApplyAccount.getDepartTelephone();
			if(isdept.equals("0")){
				UserInfo accountOwner = (UserInfo) service.find(UserInfo.class, oldApplyAccount.getAccountowner().getId().toString());
				DCContacts employee = (DCContacts)service.findUnique(
						DCContacts.class, "itcode", accountOwner.getItcode());
				employee.setTelephone("");
				employee.setVoipPhone("");
				employee = (DCContacts) service.save(employee);
				this.updateDCContacts(accountOwner.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
			}
		}
		//add by liuying at 20100409 for ��������������߱����������ԭ������ͨѶ¼ end
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
			if(definitionName.equals("E-Bridge�ʺ�����")||definitionName.equals("E-Bridge�ʺ�Ȩ�ޱ������")){
				//UserInfo eblookerUser = (UserInfo) this.service.findUnique(UserInfo.class, "userName", eblooker);
				//String eblookerMail = eblookerUser.getEmail();
				//ms.sendMimeMail(eblookerMail, null, null, subjectEB, contextEB, null);//modify by liuying at 20100315 
				ms.sendMimeMail("dcgou@zsgj.com", null, null, subjectEB, contextEB, null);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}
	

	/**
	 * �����ʺ��������״̬����
	 * 
	 * @Methods Name specialAccountEnd
	 * @Create In 11 13, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void specialAccountEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<SpecialAccount> account = service.find(SpecialAccount.class,
				"applyId", object);
		BeanWrapper bw = new BeanWrapperImpl(object);
		ServiceItemProcess sip = (ServiceItemProcess) bw
		.getPropertyValue("serviceItemProcess");
		for (SpecialAccount acc : account) {
			acc.setAccountState("1");
			SpecialAccount oldAccount = acc.getOlodApplyAccount();
			if (oldAccount != null) {
				if(oldAccount.getAccountState().equals("0")){
					acc.setAccountState("0");
				}
				oldAccount.setAccountState("0");
				service.save(oldAccount);
			}
			service.save(acc);
			if(sip.getId().compareTo(184L)==0){
				SenseServicesUitl ssUtil = new SenseServicesUitl();
				String mes=ssUtil.modifyTempUserManager(acc.getAccountName(),acc.getAccountNowUser().getItcode());
				if(!mes.equals("success")){
					throw new RuleFileException(mes);
				}
			}
			if(sip.getId().compareTo(174L)==0){
				SenseServicesUitl ssUtil = new SenseServicesUitl();
				String mes=ssUtil.addWWWAccount(acc.getAccountName());
				if(!mes.equals("success")){
					throw new RuleFileException(mes);
				}
			}
			if(sip.getId().compareTo(173L)==0){
				SenseServicesUitl ssUtil = new SenseServicesUitl();
				String mes=ssUtil.addMSNAccount(acc.getAccountName());
				if(!mes.equals("success")){
					throw new RuleFileException(mes);
				}
			}
		}
		
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		String contextEB = this.htmlContentForEB(applyUser, realUrl,auditHis,definitionName);
		String subjectEB = "IT��ܰ��ʾ��"+applyUser.getRealName()+"/"+applyUser.getUserName()+"�ύ��" +definitionName+ "�Ѵ������,��鿴!";
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
			if(definitionName.equals("��ʱ��ԱEB�ʺ�����")||definitionName.equals("��ʱ��ԱEB�ʺ������߱������")){
				//UserInfo eblookerUser = (UserInfo) this.service.findUnique(UserInfo.class, "userName", eblooker);
				//String eblookerMail = eblookerUser.getEmail();
				ms.sendMimeMail("dcgou@zsgj.com", null, null, subjectEB, contextEB, null);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * �����ʺ�ɾ���������״̬����
	 * 
	 * @Methods Name specialAccountDeleteEnd
	 * @Create In 11 13, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 * void
	 */
	public void specialAccountDeleteEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception{
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object=(BaseObject) this.findObjectByClassName(className,
					dataId);
		List<SpecialAccount> account = service.find(SpecialAccount.class,
				"applyId", object);
		BeanWrapper bw = new BeanWrapperImpl(object);
		ServiceItemProcess sip = (ServiceItemProcess) bw
		.getPropertyValue("serviceItemProcess");
		for (SpecialAccount acc : account) {
			SpecialAccount oldApplyAccount = acc.getOlodApplyAccount();
			oldApplyAccount.setAccountState("0");
			
			service.save(oldApplyAccount);
			
			if(sip.getId().compareTo(248L)==0){
				SenseServicesUitl ssUtil = new SenseServicesUitl();
				String mes=ssUtil.removeTempUser(acc.getAccountName());
				if(!mes.equals("success")){
					throw new RuleFileException(mes);
				}
			}
			if(sip.getId().compareTo(211L)==0){
				SenseServicesUitl ssUtil = new SenseServicesUitl();
				String mes=ssUtil.deleteWWWAccount(acc.getAccountName());
				if(!mes.equals("success")){
					throw new RuleFileException(mes);
				}
			}
			if(sip.getId().compareTo(209L)==0){
				SenseServicesUitl ssUtil = new SenseServicesUitl();
				String mes=ssUtil.deleteMSNAccount(acc.getAccountName());
				if(!mes.equals("success")){
					throw new RuleFileException(mes);
				}
			}
		}
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * �����ʺ�Ȩ�ޱ���������״̬����
	 * 
	 * @Methods Name specialAccountRightChangeEnd
	 * @Create In 11 13, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void specialAccountRightChangeEnd(String dataId,
			String serviceItemId, String nodeId, String nodeName,
			String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<SpecialAccount> account = service.find(SpecialAccount.class,
				"applyId", object);
		for (SpecialAccount acc : account) {
			SpecialAccount oldApplyAccount = acc.getOlodApplyAccount();
			oldApplyAccount.setAccountState("0");
			service.save(oldApplyAccount);
			acc.setAccountState("1");
			service.save(acc);
		}
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		String contextEB = this.htmlContentForEB(applyUser, realUrl,auditHis,definitionName);
		String subjectEB = "IT��ܰ��ʾ��"+applyUser.getRealName()+"/"+applyUser.getUserName()+"�ύ��" +definitionName+ "�Ѵ������,��鿴!";
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
			if(definitionName.equals("��ʱ��ԱEB�ʺ�Ȩ�ޱ������")){
				ms.sendMimeMail("dcgou@zsgj.com", null, null, subjectEB, contextEB, null);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * �����ʺ�ɾ������״̬����
	 * 
	 * @Methods Name accountDeleteEnd
	 * @Create In 11 13, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void accountDeleteEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		BeanWrapper bw = new BeanWrapperImpl(object);
		ServiceItemProcess sip = (ServiceItemProcess) bw.getPropertyValue("serviceItemProcess");
		
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		PersonFormalAccount account = (PersonFormalAccount) service.findUnique(
				PersonFormalAccount.class, "applyId", object); // ��ȡ����ʵ��
		PersonFormalAccount oldApplyAccount = account.getOlodApplyAccount();
		String accountType=oldApplyAccount.getAccountType().getAccountType();
		if (accountType.equals("WWWAccount")) {
			UserInfo accountOwner = oldApplyAccount.getAccountowner();
			PersonFormalAccount msnAccount = accountService.findPersonAccount(
					"MSN�ʺ�", accountOwner);
			if (msnAccount != null) {
				msnAccount.setAccountState("0");
				service.save(msnAccount);
//				SenseServicesUitl ssUtil = new SenseServicesUitl();
//				String mes=ssUtil.deleteMSNAccount(msnAccount.getAccountowner().getItcode());
//				if(!mes.equals("success")){
//					throw new RuleFileException(mes);
//				}
			}
		}
//		if(accountType.equals("Telephone")&&applyUser!=null){
//			DCAddressList dc=(DCAddressList) service.findUnique(
//					DCAddressList.class, "employeeCode", applyUser.getEmployeeCode());
//			if(dc!=null){
//				dc.setTelephone("");
//				dc.setVoip("");
//				service.save(dc);
//			}
//		}
		
		oldApplyAccount.setAccountState("0");
		PersonFormalAccount ac = (PersonFormalAccount) service.save(oldApplyAccount);
		if(sip.getId().compareTo(245L)==0){
			SenseServicesUitl ssUtil = new SenseServicesUitl();
			String mes=ssUtil.deleteWWWAccount(ac.getAccountowner().getItcode());
			if(!mes.equals("success")){
				throw new RuleFileException(mes);
			}
		}
		if(sip.getId().compareTo(220L)==0){
			SenseServicesUitl ssUtil = new SenseServicesUitl();
			String mes=ssUtil.deleteMSNAccount(ac.getAccountowner().getItcode());
			if(!mes.equals("success")){
				throw new RuleFileException(mes);
			}
		}
		//add by lee for ���������������ͨѶ¼��Ϣ in 20100127 begin
		if(account.getAccountType().getAccountType().equals("Telephone")&&account.getDepartTelephone().equals("0")){
			UserInfo accountOwner = (UserInfo) service.find(UserInfo.class, account.getAccountowner().getId().toString());
			DCContacts employee = (DCContacts)service.findUnique(
					DCContacts.class, "itcode", accountOwner.getItcode());
			employee.setTelephone(null);
			employee.setVoipPhone(null);
			employee = (DCContacts) service.save(employee);
			this.updateDCContacts(accountOwner.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
		}
		//add by lee for ���������������ͨѶ¼��Ϣ in 20100127 end 
		
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
//		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * �˺��������״̬������Ҫ�ʼ��������Ĺ���
	 * 
	 * @Methods Name accountIDFileEnd
	 * @Create In Sep 3, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void accountIDFileEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		AccountApplyMainTable object = (AccountApplyMainTable) this
				.findObjectByClassName(className, dataId);
		NotesIDFile idFile = (NotesIDFile) service.findUnique(
				NotesIDFile.class, "applyId", object);
		String DCMail=idFile.getDcMail();
		String webMail=idFile.getWebMail();
		String attachment = idFile.getAttachment();
		List<SystemFile> sysFile = service.find(SystemFile.class, "nowtime",
				attachment);
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = serviceItemProcessServcie
				.findProcessesByServiceItemAndType(serviceItem, processType);
		UserInfo applyUser = (UserInfo) bw
				.getPropertyValue("delegateApplyUser");// ��ȡ������
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		}
		

//		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
		String filePath = "";
		for (SystemFile systemFileName : sysFile) {
		
			filePath += mainPath+PropertiesUtil
					.getProperties("system.attachment.filePath")
					+ systemFileName.getSystemFileName() + ';';

		}
		filePath = filePath.substring(0, filePath.length() - 1);
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ��"+applyUser.getUserName()+"/"+applyUser.getRealName()+"�ύ��" +definitionName+ "�������(ID�ļ�������)";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(DCMail, null, null, subject, context, filePath);
			if(webMail!=null&&StringUtils.isNotBlank(webMail)){
				ms.sendMimeMail(webMail, null, null, subject, context, filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * ��Ա��IT�˺��������״̬����
	 * 
	 * @Methods Name newITAccountEnd
	 * @Create In Aug 6, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 * void
	 */
	public void newITAccountEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<PersonFormalAccount> account = service.find(
				PersonFormalAccount.class, "applyId", object); // ��ȡ����ʵ��
		String attachment = "";
		String filePath = "";
	
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = serviceItemProcessServcie
				.findProcessesByServiceItemAndType(serviceItem, processType);
        UserInfo delegateApplyUser=(UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
        UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		String itCode = applyUser.getItcode();
		DCContacts employee = (DCContacts)service.findUnique(
				DCContacts.class, "itcode", itCode);
		if (employee == null) {
			employee = new DCContacts();
		}
		employee.setCostCenterCode(applyUser.getCostCenterCode());
		employee.setDepartment(applyUser.getDepartment());
		employee.setEmployeeCode(applyUser.getEmployeeCode());
		employee.setUserType(applyUser.getUserType());
		employee.setEmail(applyUser.getEmail());
		employee.setItcode(applyUser.getItcode());
		employee.setRealName(applyUser.getRealName());
		employee.setDepartCode(applyUser.getDepartCode());
		employee.setSameMailDept(applyUser.getSameMailDept());
		if(applyUser.getMailServer()!=null){
			employee.setMailServer(applyUser.getMailServer().getName());
		}
		employee.setUserNames(applyUser.getUserName());
		employee.setPersonnelScope(applyUser.getPersonnelScope());
		employee.setPlatform(applyUser.getPlatform());
		employee.setWorkSpace(applyUser.getWorkSpace());
		employee.setMobilePhone(applyUser.getMobilePhone());
		
		
		
		for (PersonFormalAccount acc : account) {
			acc.setAccountState("1");
			service.save(acc);
			if (acc.getAccountType().getAccountType().equals("MailAccount")) {
				attachment = acc.getAttachment();
				if (attachment != null) {
					List<SystemFile> sysFile = service.find(SystemFile.class,
							"nowtime", attachment);
					for (SystemFile systemFileName : sysFile) {
						filePath += mainPath+PropertiesUtil
								.getProperties("system.attachment.filePath")
								+ systemFileName.getSystemFileName() + ';';
					}
					if(filePath!=null&&filePath.length()>0){
						filePath = filePath.substring(0, filePath.length() - 1);
					}
				}
			}
			if (acc.getAccountType().getAccountType().equals("Telephone")) {
				employee.setTelephone(acc.getTelephoneNumber());	//���µ绰����
				employee.setVoipPhone(acc.getVoip());	//����voip�绰
			}
			if (acc.getAccountType().getAccountType().equals("WWWAccount")) {
				SenseServicesUitl ssUtil = new SenseServicesUitl();
				String mes=ssUtil.addWWWAccount(acc.getAccountowner().getItcode());
				if(!mes.equals("success")){
					throw new RuleFileException(mes);
				}
			}
		}
		service.save(employee);
		//add by lee for ����DCͨѶ¼ in 20100127 begin
		this.updateDCContacts(employee.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
		//add by lee for ����DCͨѶ¼ in 20100127 end
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
		String combinEmail = delegateApplyUser.getEmail();

		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������(ID�ļ�������)";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(delegateApplyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(combinEmail, null, null, subject, context,filePath);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
//			UserInfo curUser = UserContext.getUserInfo();
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * Ա����ְ�������״̬����
	 * 
	 * @Methods Name employeeDimissionEnd
	 * @Create In Aug 6, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void employeeDimissionEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<PersonFormalAccount> account = service.find(
				PersonFormalAccount.class, "applyId", object); // ��ȡ����ʵ��

		for (PersonFormalAccount acc : account) {
			if (acc.getAccountType().getAccountType().equals("ERPAccount")
					&& acc.getIfHold().equals(1)) {
				PersonFormalAccount oldApplyAccount = acc.getOlodApplyAccount();
				oldApplyAccount.setAccountowner(acc.getAccountowner());
				oldApplyAccount.setIfHold(1);
				service.save(oldApplyAccount);
			} else {
				PersonFormalAccount oldApplyAccount = acc.getOlodApplyAccount();
				oldApplyAccount.setAccountState("0");
				oldApplyAccount.setIfHold(0);
				service.save(oldApplyAccount);
				
			}
		}

		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = serviceItemProcessServcie
				.findProcessesByServiceItemAndType(serviceItem, processType);

		UserInfo delegateApplyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser != null) {
			String itCode = applyUser.getItcode();
			DCContacts employee = (DCContacts)service.findUnique(
					DCContacts.class, "itcode", itCode);
			//add by lee for ��Ա����ְʱɾ��DCͨѶ¼��Ϣ in 20100204 begin
			this.updateDCContacts(employee.getEmployeeCode(),null,null,null,true);
			//add by lee for ��Ա����ְʱɾ��DCͨѶ¼��Ϣ in 20100204 end
			if (employee != null) {
				service.remove(employee);
			}
//			DCAddressList dc=(DCAddressList) service.findUnique(
//					DCAddressList.class, "employeeCode", applyUser.getEmployeeCode());
//			if(dc!=null){
//				service.remove(dc);
//			}
		}
		
		
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
		String combinEmail = delegateApplyUser.getEmail();

		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(delegateApplyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(combinEmail, null, null, subject, context, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			UserInfo curUser = UserContext.getUserInfo();
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", curUser, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * ��ʱ��Ա�ʼ�/���ʺ��������״̬����
	 * 
	 * @Methods Name tempMailAccountEnd
	 * @Create In Aug 6, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void tempMailAccountEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<SpecialAccount> account = service.find(SpecialAccount.class,
				"applyId", object); // ��ȡ����ʵ��
		String attachment = null;
		String filePath = null;
		for (SpecialAccount acc : account) {
			acc.setAccountState("1");
			service.save(acc);
			if (acc.getAccountType().getAccountType().equals("TempMailAccount")) {
				attachment = acc.getAttachment();
				List<SystemFile> sysFile = service.find(SystemFile.class,
						"nowtime", attachment);
				for (SystemFile systemFileName : sysFile) {
					filePath +=mainPath+ PropertiesUtil
							.getProperties("system.attachment.filePath")
							+ systemFileName.getSystemFileName() + ';';
				}
				filePath = filePath.substring(0, filePath.length() - 1);
				String accountName=acc.getAccountName();
				DCContacts employee = new DCContacts();
				employee.setUserNames(accountName);
				service.save(employee);
			}
		}
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = serviceItemProcessServcie
				.findProcessesByServiceItemAndType(serviceItem, processType);

		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
		String combinEmail = applyUser.getEmail();

		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms
					.sendMimeMail(combinEmail, null, null, subject, context,
							filePath);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			UserInfo curUser = UserContext.getUserInfo();
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", curUser, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * ���������ʼ��ʺ��������״̬����
	 * 
	 * @Methods Name deptMailAccountEnd
	 * @Create In Aug 6, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception
	 *             void
	 */
	public void deptMailAccountEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		List<SpecialAccount> account = service.find(SpecialAccount.class,
				"applyId", object); // ��ȡ����ʵ��
		

		String attachment = "";
		String filePath = "";
		for (SpecialAccount acc : account) {
			acc.setAccountState("1");
			service.save(acc);
			attachment = acc.getAttachment();
			List<SystemFile> sysFile = service.find(SystemFile.class,
					"nowtime", attachment);
			for (SystemFile systemFileName : sysFile) {
				filePath += mainPath+PropertiesUtil
						.getProperties("system.attachment.filePath")
						+ systemFileName.getSystemFileName() + ';';
			}
			filePath = filePath.substring(0, filePath.length() - 1);
			String accountName=acc.getAccountName();
			DCContacts employee = new DCContacts();
			employee.setUserNames(accountName);
			service.save(employee);
		}
	
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		BaseObject oldObject = (BaseObject) requireService.getOldApplyObject(
				className, object);// �õ�ԭʵ��
		Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = serviceItemProcessServcie
				.findProcessesByServiceItemAndType(serviceItem, processType);

		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
		String combinEmail = applyUser.getEmail();

		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(combinEmail, null, null, subject, context,
							filePath);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (oldObject != null) {
			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
			BeanUtils.copyProperties(object, oldObject);
			oldBeanWrapper.setPropertyValue("id", oldId);
			service.save(oldObject);
			service.remove(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
			requireService.saveEntityToEvent(className, oldObject);
		} else {
			service.save(object);
			UserInfo curUser = UserContext.getUserInfo();
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", curUser, serviceItem);
			requireService.saveEntityToEvent(className, object);
		}
	}

	/**
	 * �˺Ź���������״̬����
	 * 
	 * @Methods Name accountBack
	 * @Create In May 26, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @throws Exception
	 *             void
	 */
	public void accountBack(String dataId, String serviceItemId, String nodeId,
			String nodeName, String processId, String result, String comment)
			throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		Object object = this.findObjectByClassName(className, dataId);
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_DRAFT);
		this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
				"Y", "", null, serviceItem);
		service.save(object);
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");// �õ���������

		UserInfo applyUser = (UserInfo) bw
				.getPropertyValue("delegateApplyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("applyUser");// ��ȡ������delegateApplyUser
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getPageModel();
		String definitionName=sip.getDefinitionName();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//		String combinEmail = applyUser.getEmail();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "δͨ������";
		String context = this.htmlContentAccountBack(applyUser, realUrl,auditHis,definitionName);
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * �˺Ź����Ƿ���ҪWWW�˺Ź���Ա����ڵ������ת
	 * 
	 * @Methods Name wwwJump
	 * @Create In May 15, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param reqClass
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String wwwJump(String dataId, String serviceItemId, String nodeId,
			String nodeName, String processId, String result, String comment) {
		return "Y";
	}

	/**
	 * �˺Ź����Ƿ���ҪSBU��ǩ�˴���ڵ������ת
	 * 
	 * @Methods Name sbuSelect
	 * @Create In May 31, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String specailErpOrTempEB(String dataId, String serviceItemId,
			String processName) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		Object object = this.findObjectByClassName(className, dataId);
		BeanWrapper bw = new BeanWrapperImpl(object);
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		String personnelScope = applyUser.getPersonnelScope()
				.getPersonnelScopeCode();
		String tran = "A";
		List<SpecialAccount> account = service.find(SpecialAccount.class,
				"applyId", object);
		if (account != null) {
			for (SpecialAccount acc : account) {
				Boolean eb = (acc.getAccountType().getAccountType()
						.equals("TempEBAccount"))
						&& (acc.getIfHold() == 1);
				Boolean erp = (acc.getAccountType().getAccountType()
						.equals("SpecailERPAccount"))
						&& (acc.getIfHold() == 1);
				if (eb || erp) {
					if (personnelScope.equals("0035")
							|| personnelScope.equals("0036")
							|| personnelScope.equals("0037")) {
						tran = "S";
					} else if (acc.getAccountType().getAccountType().equals(
							"SpecailERPAccount")
							&& (personnelScope.equals("0004")
									|| personnelScope.equals("0039") || personnelScope
									.equals("0002"))) {
						tran = "S";
					} else {
						tran = "F";
					}
				}
			}
		}
		return tran;

	}

	/**
	 * �ʺŹ����Ƿ���ҪSBU��ǩ�˴���ڵ������ת
	 * 
	 * @Methods Name sbuSelect
	 * @Create In May 31, 2009 By lee
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String sbuSelect(String dataId, String serviceItemId,
			String processName) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		Object object = this.findObjectByClassName(className, dataId);
		BeanWrapper bw = new BeanWrapperImpl(object);
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		applyUser = (UserInfo) service.find(UserInfo.class, applyUser.getId().toString());
		PersonnelScope personnelScope = applyUser.getPersonnelScope();
		
		if (personnelScope != null) {
			String personScope = personnelScope.getPersonnelScopeCode();
			ServiceItemProcess sip = (ServiceItemProcess) bw
					.getPropertyValue("serviceItemProcess");
			String processNameDescription = sip.getDefinitionName();
			List<AccountSBUOfficer> confirmUsers = accountService.findOfficer(
					processNameDescription, personScope);
			if (confirmUsers.size() > 0) {
				//add by guangsa for �������ж��Ƿ���SBU��ǩ�ˣ����֮ǰ�о��滻sbu��ǩ�ˣ�û�������� in 20100901 begin
				JbpmContext jbpmContext = null;
				AccountSBUOfficer accountSBU = (AccountSBUOfficer)confirmUsers.get(0);
				String newUserList = "";
				try{
				List<ServiceItemApplyAuditHis> his=accountService.findServiceItemApplyAuditHis(dataId,serviceItemId);
				if(his!=null){
					jbpmContext = JbpmContextFactory.getJbpmContext();
					Map bizMap = (Map)jbpmContext.getProcessInstance(his.get(his.size()-1).getProcessId()).getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
					String userList = (String)bizMap.get("userList");
					if(userList!=null&&!"".equals(userList)&&userList.contains(accountSBU.getNodeName())){//�滻
						String[] userValue = userList.split(accountSBU.getNodeName());
						if(userValue[1].contains("$")){
							int index = userValue[1].indexOf("$");
							String temp = (String)userValue[1].substring(index,userValue[1].length()-1);
							newUserList += userValue[0]+accountSBU.getNodeName()+":"+accountSBU.getConfirmUser()+temp;
						}else{
							userValue[1]=accountSBU.getNodeName()+":"+accountSBU.getConfirmUser();
							newUserList += userValue[0]+userValue[1];
						}
					}else if(userList!=null&&!"".equals(userList)){//����
						userList+="$"+accountSBU.getNodeName()+":"+accountSBU.getConfirmUser();
						newUserList=userList;
					}
					bizMap.remove("userList");
					bizMap.put("userList", newUserList);
				}
				}catch(Exception e){
					e.printStackTrace();
				}
//				finally{
//					JbpmContextFactory.closeJbpmContext();
//				}
				//add by guangsa for �������ж��Ƿ���SBU��ǩ�ˣ����֮ǰ�о��滻sbu��ǩ�ˣ�û�������� in 20100901 end
				return "Y";
			} else {
				return "N";
			}
		} else {
			return "N";
		}
	}

	/**
	 * �ֻ������ǩ�˴���ڵ������ת
	 * 
	 * @Methods Name isSign
	 * @Create In May 31, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return String
	 */
	public String isSign(String dataId, String serviceItemId, String processName)
			throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		Object object = this.findObjectByClassName(className, dataId);
		MobileTelephoneApply tel = (MobileTelephoneApply) service.findUnique(
				MobileTelephoneApply.class, "applyId", object);
		Double allowance = tel.getDeptAllowance();
		if (allowance != null) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * �ʺŹ����Ƿ�HR��Ƹ������������ת
	 * 
	 * @Methods Name isHRJob
	 * @Create In SEP 28, 2009 By gaowen
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param hrJob
	 * @return String
	 */
	public String isHRJob(String dataId, String serviceItemId,
			String processName, String hrJob) throws Exception {
		String tran = "N";
//		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
//				serviceItemId);
//		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
//				ServiceItemUserTable.class, "serviceItem", serviceItem);
//		String className = siut.getClassName();
//		Object object = this.findObjectByClassName(className, dataId);
//		BeanWrapper bw = new BeanWrapperImpl(object);
//		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (hrJob != null) {
			tran = "Y";
		}
		return tran;
	}

	/**
	 * ��װHTML�ʼ����͸��ʺ�������
	 * 
	 * @Methods Name htmlContent
	 * @Create In 2009-7-17 By gaowen
	 * @param order
	 * @param opl
	 * @return String
	 */
	private String htmlContent(UserInfo creator, String url,List auditHis,String definitionName) {

		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");

		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");

		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");

		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");

		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"
				+ creator.getRealName() + "/" + creator.getUserName()
				+ "������:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">���ύ��"+definitionName+"�Ѵ������,<a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ��" + "</a>�������⣬�벦��IT��������7888��</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
//  String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
//		String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("�ύ")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"�ύ��";
				}else {
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"����ͨ����";
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	
	/**
	 * ��װHTML�ʼ����͸�EB�ʺŲ鿴��
	 * 
	 * @Methods Name htmlContent
	 * @Create In 2009-7-17 By gaowen
	 * @param order
	 * @param opl
	 * @return String
	 */
	private String htmlContentForEB(UserInfo creator, String url,List auditHis,String definitionName) {

		StringBuilder sb = new StringBuilder();
//NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");

		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");

		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");

		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");

		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"
				+ "������/ouhj" 
				+ "������:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">"+creator.getRealName()+"/"+creator.getUserName()+"�ύ��"+definitionName+"�Ѵ������,<a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ��" + "</a>�������⣬�벦��IT��������7888��</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
//		String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
//		String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("�ύ")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"�ύ��";
				}else {
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"����ͨ����";
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}

	/**
	 * ��װHTML�ʼ����͸��ʺ������� �����˲���
	 * 
	 * @Methods Name htmlContentAccountBack
	 * @Create In 2009-7-17 By gaowen
	 * @param order
	 * @param opl
	 * @return String
	 */
	private String htmlContentAccountBack(UserInfo creator, String url,List auditHis,String definitionName) {

		StringBuilder sb = new StringBuilder();
//NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");

		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/plain; charset=GBK\">");

		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");

		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");

		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"
				+ creator.getRealName() + "/" + creator.getUserName()
				+ "������:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">���ύ��"+definitionName+"δͨ������,<a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ��" + "</a>�������⣬����ϵ��Ӧ������Ա��</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
				String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
				String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("�ύ")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"�ύ��";
				}else if(resultFlag.equals("Y")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"����ͨ����";
				}else{
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"δͨ��������ԭ��:"+comment;
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}
	/**
	 * ���������߱����������
	 * @Methods Name telChangeEnd
	 * @Create In Apr 13, 2010 By liuying
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception void
	 */
	public void telChangeEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
//Integer processType = (Integer) bw.getPropertyValue("processType");// �õ���������
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("applyUser");
		if (applyUser == null) {
			applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ������
		}
		String email = applyUser.getEmail();
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
//String combinEmail = applyUser.getEmail();
		String definitionName=sip.getDefinitionName();
		List<PersonFormalAccount> accounts = service.find(
				PersonFormalAccount.class, "applyId", object); // ��ȡ����ʵ��
		for(PersonFormalAccount account:accounts){
			if(accounts.size()==1){
				account.setAccountState("1");
			}
			if(account.getAccountType().getAccountType().equals("Telephone")&&account.getDepartTelephone().equals("0")){
				UserInfo accountOwner = (UserInfo) service.find(UserInfo.class, account.getAccountowner().getId().toString());
				//modify by liuying at 20100517 for �޸�ͬ���û�ͨѶ¼���쳣 start
//				DCContacts employee = (DCContacts)service.findUnique(
//						DCContacts.class, "itcode", accountOwner.getItcode());
				DCContacts employee =accountService.saveOrGetContacts(accountOwner.getItcode());
				employee.setTelephone(account.getTelephoneNumber());
				//modify by liuying at 20100517 for �޸�ͬ���û�ͨѶ¼���쳣 end
				employee.setVoipPhone(account.getVoip());
				employee = (DCContacts) service.save(employee);
				this.updateDCContacts(accountOwner.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
			}
			service.save(account);
			PersonFormalAccount oldApplyAccount = account.getOlodApplyAccount();
			if (oldApplyAccount != null) {
				oldApplyAccount.setAccountState("0");
				service
				.save(oldApplyAccount);
			}
			if(definitionName.equals("���������߱������")&&oldApplyAccount!=null){
				String isdept=oldApplyAccount.getDepartTelephone();
				if(isdept.equals("0")){
					UserInfo accountOwner = (UserInfo) service.find(UserInfo.class, oldApplyAccount.getAccountowner().getId().toString());
					//modify by liuying at 20100517 for �޸�ͬ���û�ͨѶ¼���쳣 start
//					DCContacts employee = (DCContacts)service.findUnique(
//							DCContacts.class, "itcode", accountOwner.getItcode());
					DCContacts employee =accountService.saveOrGetContacts(accountOwner.getItcode());
					//modify by liuying at 20100517 for �޸�ͬ���û�ͨѶ¼���쳣 end
					employee.setTelephone("");
					employee.setVoipPhone("");
					employee = (DCContacts) service.save(employee);
					this.updateDCContacts(accountOwner.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
				}
			}
			
		}
		if(accounts.size()!=1){
			PersonFormalAccount pfa1=accounts.get(0);
			pfa1.setAccountState("1");
			service.save(pfa1);
		}
		
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
//		String contextEB = this.htmlContentForEB(applyUser, realUrl,auditHis,definitionName);
//		String subjectEB = "IT��ܰ��ʾ��"+applyUser.getRealName()+"/"+applyUser.getUserName()+"�ύ��" +definitionName+ "�Ѵ������,��鿴!";
	
		try {
			ms.sendMimeMail(email, null, null, subject, context, null);
		} catch (Exception e) {

			e.printStackTrace();
		}

//		if (oldObject != null) {
//			BeanWrapper oldBeanWrapper = new BeanWrapperImpl(oldObject);
//			Long oldId = (Long) oldBeanWrapper.getPropertyValue("id");
//			BeanUtils.copyProperties(object, oldObject);
//			oldBeanWrapper.setPropertyValue("id", oldId);
//			service.save(oldObject);
//			service.remove(object);
//			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
//					"Y", "", null, serviceItem);
//			requireService.saveEntityToEvent(className, oldObject);
//		} else {
//			service.save(object);
			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
//			requireService.saveEntityToEvent(className, object);
//		}
	}
	
	
	/**
	 * ���ø���DCͨѶ¼webservice
	 * @Methods Name updateDCContacts
	 * @Create In Jan 27, 2010 By lee
	 * @param employeeCode
	 * @param telephone
	 * @param mobilePhone
	 * @param voipPhone void
	 */
	private void updateDCContacts(String employeeCode,String telephone,String mobilePhone,String voipPhone,boolean isDelete){
		if(employeeCode!=null&&employeeCode.length()>0){
			UserInfo user=(UserInfo) service.findUnique(UserInfo.class, "employeeCode",employeeCode);
			if(user!=null){
				user.setTelephone(telephone);
				user.setMobilePhone(mobilePhone);
				service.save(user);
			}
		}
//		HrInfoService hs = new HrInfoServiceLocator();
//		try {
//			HrInfoServiceSoap_PortType hp = hs.getHrInfoServiceSoap();
//			int reslut = hp.updatePhone(employeeCode,telephone,mobilePhone,voipPhone,isDelete);
//			if(reslut==1){
//				System.out.println("����HR����ͨѶ¼[Ա����ţ�"+employeeCode+" ][�ֻ�:"+mobilePhone+"][ ����:"+telephone+" ][voip:"+voipPhone+"]��Ϣ�ɹ���");
//			}else{
//				System.out.println("����HR����ͨѶ¼"+employeeCode+"��Ϣʧ�ܣ�");
//				System.out.println("ERROR:"+(reslut==-1?"����ʧ��":"û��Ȩ��"));
//			}
//		} catch (ServiceException e) {
//			System.out.println("����HR����ͨѶ¼webservice����ʧ�ܣ�");
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			System.out.println("����HR����ͨѶ¼webservice����ʧ�ܣ�");
//			e.printStackTrace();
//		}
		
	}
	/**
	 * �����ʺŹ���Աָ����VPN�ʺ������������Ƿ����˺Ź���Ա��ǩ����
	 * @Methods Name isAMSign
	 * @Create In May 26, 2010 By liuying
	 * @param dataId
	 * @param serviceItemId
	 * @param processName
	 * @return
	 * @throws Exception String
	 */
	public String isAMSign(String dataId, String serviceItemId,
			String processName) throws Exception {
		AccountApplyMainTable aamt=(AccountApplyMainTable) service.find(AccountApplyMainTable.class, dataId);
		PersonFormalAccount pfa = (PersonFormalAccount) service.findUnique(
				PersonFormalAccount.class, "applyId", aamt);
		SpecialAccount sa=(SpecialAccount)service.findUnique(
				SpecialAccount.class, "applyId", aamt);
		if(pfa!=null||sa!=null){
			if(pfa!=null){
				if(pfa.getDrawSpace()!=null){
					return "Y";
				}else {
					return "N";
				}
			}else{
				if(sa.getDrawSpace()!=null){
					return "Y";
				}else{
					return "N";
				}
			}
		}else{
			return "N";
		}
		
	}
	/**
	 * ����Զ�̽����ʺŽ���
	 * @Methods Name vpnAccountEnd
	 * @Create In May 27, 2010 By liuying
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception void
	 */
	public void vpnAccountEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {

		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		PersonFormalAccount account = (PersonFormalAccount) service.findUnique(
				PersonFormalAccount.class, "applyId", object); // ��ȡ����ʵ��
		account.setAccountState("1");
		service.save(account);
		
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ��������
		
		String noticeMail=(String) bw.getPropertyValue("mail");
		String email = applyUser.getEmail();
		
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String vpnType=account.getVpnType();
		String context="";
		String filePath="";
		String attachment="";
		if(vpnType!=null){
			if(vpnType.equals("1")){
				String pingCode=account.getPingCode();
				context = this.vpnHtmlContent(applyUser, realUrl,auditHis,definitionName,pingCode);
				attachment = (String) bw.getPropertyValue("attachment");
				if (attachment != null) {
					List<SystemFile> sysFile = service.find(SystemFile.class,
							"nowtime", attachment);
					for (SystemFile systemFileName : sysFile) {
						filePath += mainPath+PropertiesUtil
								.getProperties("system.attachment.filePath")
								+ systemFileName.getSystemFileName() + ';';
					}
					filePath = filePath.substring(0, filePath.length() - 1);
					String czsc=PropertiesUtil.getProperties("system.attachment.czsc");//�����ʼ��м��븽�� �����ֲ�
					
					filePath=filePath+";"+mainPath+czsc;
				}
			}else{
				context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
			}
		}else{
			context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		}
		
		try {
			if(email.equals(noticeMail)){
				ms.sendMimeMail(email, null, null, subject, context, filePath);
			}else{
				ms.sendMimeMail(email, null, null, subject, context, filePath);
				ms.sendMimeMail(noticeMail, null, null, subject, context, filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
		
	
	}
	/**
	 * ������ʱ��ԱԶ�̽����ʺŽ���
	 * @Methods Name vpnAccountEnd
	 * @Create In May 27, 2010 By liuying
	 * @param dataId
	 * @param serviceItemId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception void
	 */
	public void tempVpnAccountEnd(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId) throws Exception {

		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
				serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(
				ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		BaseObject object = (BaseObject) this.findObjectByClassName(className,
				dataId);
		SpecialAccount account = (SpecialAccount) service.findUnique(
				SpecialAccount.class, "applyId", object); // ��ȡ����ʵ��
		account.setAccountState("1");
		service.save(account);
		
		BeanWrapper bw = new BeanWrapperImpl(object);
		bw.setPropertyValue("status", Constants.STATUS_FINISHED);
		
		ServiceItemProcess sip = (ServiceItemProcess) bw
				.getPropertyValue("serviceItemProcess");
		UserInfo applyUser = (UserInfo) bw.getPropertyValue("delegateApplyUser");// ��ȡ��������
		
		String noticeMail=(String) bw.getPropertyValue("mail");
		String email = applyUser.getEmail();
		
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // ��ȡҳ��·��
		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
		String realUrl = rootPath + url + "?dataId=" + dataId; // �õ���ʵ���ӣ�ȫ·����
		String definitionName=sip.getDefinitionName();
		String subject = "IT��ܰ��ʾ�����ύ��" +definitionName+ "�������";
		String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		String vpnType=account.getVpnType();
		String context="";
		String filePath="";
		String attachment="";
		if(vpnType!=null){
			if(vpnType.equals("1")){
				String pingCode=account.getPingCode();
				context = this.vpnHtmlContent(applyUser, realUrl,auditHis,definitionName,pingCode);
				attachment = (String) bw.getPropertyValue("attachment");
				if (attachment != null) {
					List<SystemFile> sysFile = service.find(SystemFile.class,
							"nowtime", attachment);
					for (SystemFile systemFileName : sysFile) {
						filePath += mainPath+PropertiesUtil
								.getProperties("system.attachment.filePath")
								+ systemFileName.getSystemFileName() + ';';
					}
					filePath = filePath.substring(0, filePath.length() - 1);
					
					String czsc=PropertiesUtil.getProperties("system.attachment.czsc");//�����ʼ��м��븽�� �����ֲ�
					
					filePath=filePath+";"+mainPath+czsc;
				}
			}else{
				context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
			}
		}else{
			context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		}
		
		try {
			if(email.equals(noticeMail)){
				if(vpnType.equals("1")){
					ms.sendMimeMail(email, null, null, subject, context, filePath);
				}else{
					ms.sendMimeMail(email, null, null, subject, context, null);
				}
			}else{
				if(vpnType.equals("1")){
					ms.sendMimeMail(email, null, null, subject, context, filePath);
					ms.sendMimeMail(noticeMail, null, null, subject, context, filePath);
				}else{
					ms.sendMimeMail(email, null, null, subject, context, null);
					ms.sendMimeMail(noticeMail, null, null, subject, context, null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

			this.saveRequireHis(dataId, nodeId, processId, nodeName, className,
					"Y", "", null, serviceItem);
		
	
	}
	private String vpnHtmlContent(UserInfo creator, String url,List auditHis,String definitionName,String pingCode) {

		StringBuilder sb = new StringBuilder();
//NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");

		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");

		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");

		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");

		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"
				+ creator.getRealName() + "/" + creator.getUserName()
				+ "������:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">���ύ��"+definitionName+"�Ѵ������,<font size=4 color='#FF0000'><b>��������"+pingCode+"�����Ʊ��ܡ�</b></font><a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ��" + "</a>�������⣬�벦��IT��������7888��</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
//		String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
//		String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("�ύ")){
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"�ύ��";
				}else {
					auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"����ͨ����";
				}
		        sb.append("				 <tr>");
	        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
	        	sb.append("				 </tr>");
				}
	        }
		}
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}
	/**
	 * �����ࡰ����������дʵʩ������
	 * 
	 * @Methods Name makeInfoByEngineer
	 * @Create In 23 07, 2010 By zhangzy
	 * @param creator
	 * @param url
	 * @param reqNameInfo
	 * @return String
	 */	
	public String makeInfoByEngineer(String dataId, String serviceItemId,
			String nodeId, String nodeName, String processId, String reqClass,
			String result, String comment) throws Exception{
		
			Object obj = this.findObjectByClassName(reqClass, dataId);// ��ȡ��ǰ������ʵ��
			BeanWrapper bw = new BeanWrapperImpl(obj);
//	UserInfo applyUser = (UserInfo) bw.getPropertyValue("createUser");
			ServiceEngineer sei = (ServiceEngineer) bw.getPropertyValue("mainEngineer");
			UserInfo mainEngineer = sei.getUserInfo();
			Map propertysNameAndValue = new HashMap();
			propertysNameAndValue.put("serviceItem.id",Long.valueOf(serviceItemId));
			propertysNameAndValue.put("requirementId",Long.valueOf(dataId));
			propertysNameAndValue.put("nodeId","1826");    //�����ܼ����� �ڵ�Id ��1826
			propertysNameAndValue.put("resultFlag","Y");
			List list = new ArrayList ();
			list.add("approver");				
			List<ServiceItemApplyAuditHis> dataList =configItemService.findObjects(ServiceItemApplyAuditHis.class, propertysNameAndValue, list);
			ServiceItemApplyAuditHis his = null;
			if(dataList.size()!=0){
				 his = dataList.get(0);
			}
			String email = "";
			UserInfo approver = null;
			if(his != null){
				approver = his.getApprover();
				if(approver != null){
					email = approver.getEmail();
				}			
			}
			String reqName = (String) bw.getPropertyValue("name");
			String reqCode = "";
			if(bw.isReadableProperty("applyNum")){
				reqCode = (String) bw.getPropertyValue("applyNum");
			}		
			
			ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class,
					serviceItemId);		
			Integer processType = (Integer) bw.getPropertyValue("processType");
			ServiceItemProcess process = serviceItemProcessServcie
			.findProcessesByServiceItemAndType(serviceItem, processType);
			PageModel pm = process.getEndPageModel();
			String endUrl = pm.getPagePath();
			String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
			String curUrl = rootPath + endUrl + "?dataId=" + dataId;
			String workflowEntity = "com.zsgj.itil.service.entity.ServiceItemApplyAuditHis";
	        List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.parseLong(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
			String definitionName=process.getDefinitionName();
			
			UserInfo curUser = UserContext.getUserInfo();
			this.saveRequireHis(dataId, nodeId, processId, nodeName, reqClass,
					result, comment, curUser, serviceItem);
			if("Y".equals(result)){	//�������������дʵʩ����������ͬ�� 
//			SpecialRequirement sr = (SpecialRequirement)service.find(SpecialRequirement.class, dataId);
//			SpecialRequirementInfo sri = (SpecialRequirementInfo) service.findUnique(SpecialRequirementInfo.class, "specialRequire", sr);
					String subject="IT��ܰ��ʾ������(����ţ�"+reqCode+"���������ƣ�"+reqName+")�ķ����Ѿ�ȷ��������ʦ�ѽ�����&�ƻ������ͻ�ȷ�ϣ����ע��";
					ms.sendMimeMail(email, null, null, subject, this.mailToTechnicManager(
							approver,mainEngineer, curUrl, reqName,reqCode,auditHis,definitionName), null);
			}
			
			return result;
		
	}	
	/**
	 * �����ࡰ����������дʵʩ�������󣬸������ܼ෢�ʼ�
	 * 
	 * @Methods Name mailToTechnicManager
	 * @Create In 23 07, 2010 By zhangzy
	 * @param creator
	 * @param url
	 * @param reqNameInfo
	 * @return String
	 */
	private String mailToTechnicManager(UserInfo toUser, UserInfo  mainEngineer, String url,
			String reqName,String reqCode,List auditHis,String definitionName) {

		StringBuilder sb = new StringBuilder();
//NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>PO Details</title>");
        sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
        sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");
        sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'����';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");
        sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>�ʼ�֪ͨ</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">�𾴵�"
				+ toUser.getRealName() + "/" + toUser.getUserName()
				+ "�����ã�</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" ");
		sb.append("<div align=\"left\">��������"+reqCode+"�������ƣ�"+reqName+"�ķ����Ѿ�ȷ���������Ѿ����͸��ͻ�ȷ�ϡ�������ȽϹ�ע������������ϸ�ڣ�<a href=" + url + ">" + "�������Ӳ鿴��ϸ��Ϣ" + "</a> �����ߺ͹���ʦ "+mainEngineer.getRealName()+"/"+mainEngineer.getUserName()+"��ϵ��лл��</div></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT����"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>��Ϣϵͳ��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}	
	
	// /////////////////////////////////////////�˺Ź�����////END////////////////////////////////////
}
