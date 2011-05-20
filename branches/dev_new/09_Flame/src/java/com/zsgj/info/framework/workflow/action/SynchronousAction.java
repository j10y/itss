package com.zsgj.info.framework.workflow.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jbpm.graph.def.Node;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMail;

public class SynchronousAction implements Runnable {
	private String nodeName;
	private String pageUrl;
	private String dataId;
	private String reqClass;
	private String goStartState;
	private Long processInstanceId;
	private Long taskId;
	private String applyType;
	private String vDesc;
	private Long virtualDefinintionId;
	private String creator;
	private Map bizParam;
	private Node node;
	private String nodeId;
	private String[] auditPers;
	private Map counterSignAuditMegs;
	private String[] browsePers;

	private Service service = (Service) ContextHolder.getBean("baseService");
	private MailSenderService ms = (MailSenderService) ContextHolder
			.getBean("mailSenderService");
	private ConfigUnitService cs = (ConfigUnitService) ContextHolder
			.getBean("configUnitService");
	private static Logger log;
	static {
		log = Logger.getLogger("workflowlog");
	}

	public SynchronousAction() {

	}

	public SynchronousAction(String nodeName, String pageUrl, String dataId,
			String reqClass, String goStartState, Long processInstanceId,
			Long taskId, String applyType, String vDesc,
			Long virtualDefinintionId, String creator, Map bizParam, Node node,
			String nodeId, String[] auditPers, Map counterSignAuditMegs,
			String[] browsePers) {
		this.nodeName = nodeName;
		this.pageUrl = pageUrl;
		this.dataId = dataId;
		this.reqClass = reqClass;
		this.goStartState = goStartState;
		this.processInstanceId = processInstanceId;
		this.taskId = taskId;
		this.applyType = applyType;
		this.vDesc = vDesc;
		this.virtualDefinintionId = virtualDefinintionId;
		this.creator = creator;
		this.bizParam = bizParam;
		this.node = node;
		this.nodeId = nodeId;
		this.auditPers = auditPers;
		this.counterSignAuditMegs = counterSignAuditMegs;
		this.browsePers = browsePers;
	}

	// public void sendMailMessage(){
	// SynchronousAction sa = new SynchronousAction();
	// Thread t = new Thread(sa);
	// t.start();
	// }

	public void run() {
		String projectName = PropertiesUtil.getProperties("project.name");// ��ȡ��Ŀ��·��
		if (projectName.equals("ITIL")) {
			this.sendMailMessageToITIL(nodeName, pageUrl, dataId, reqClass,
					goStartState, processInstanceId, taskId, applyType, vDesc,
					virtualDefinintionId, creator, bizParam, node, nodeId,
					auditPers, counterSignAuditMegs, browsePers);
		} else {
			this.sendMailMessage(nodeName, pageUrl, dataId, reqClass,
					goStartState, processInstanceId, taskId, applyType, vDesc,
					virtualDefinintionId, creator, bizParam, node, nodeId,
					auditPers, counterSignAuditMegs, browsePers);
		}
	}

	/**
	 * �����ʼ���Ϣ
	 * 
	 * @param dataId
	 *            ��ҵ�������
	 * @param reqClass
	 *            ��ҵ�������
	 * @param goStartState
	 *            �����˵���ʼ�ڵ�key��
	 * @param processInstanceId
	 *            ����ʵ����Id��
	 * @param taskId
	 *            ������ID��
	 * @param nodeName
	 *            �����̽ڵ����ƣ�
	 * @param vDesc
	 *            ����������������
	 * @param virtualDefinintionId
	 *            ����������Id��
	 * @param creator
	 *            (���̴�����)
	 * @param bizParam
	 *            (����ҵ�����)
	 * @param node
	 *            �����̽ڵ㣩
	 * @param nodeId
	 *            �����̽ڵ�Id��
	 */
	@SuppressWarnings("static-access")
	private void sendMailMessage(String nodeName, String pageUrl,
			String dataId, String reqClass, String goStartState,
			Long processInstanceId, Long taskId, String applyType,
			String vDesc, Long virtualDefinintionId, String creator,
			Map bizParam, Node node, String nodeId, String[] auditPers,
			Map counterSignAuditMegs, String[] browsePers) {
		// ���浱ǰ�ڵ��nodeName���Ա������̻��ˣ�
		String virualDesc = "";
//		String nowNodeDesc = "";
//		String nowNodeName = "";
		String subject = null;
		String content = null;
//		String[] ccEmail = null;// �����˵�email��ַ
//		String[] configEmail = null;// ��̨���ã�����о͸��ƽ��룬û�о�Ϊ��ֵ
		/********************************* ��̨������Ӧ�ʼ�ģ��Ĺ��С��������ơ��͡����ͱ�š� ********************************************************************/
		String typeNum = (String) bizParam.get("eventCisn");// eventCisn
		String subTypeName = (String) bizParam.get("eventName");// eventName
		String typeName = "<STRONG>" + (String) bizParam.get("eventName")
				+ "</STRONG>";//  
		String hurryFlag = (String) bizParam.get("hurryFlag"); // ����ĳЩ��������Ҫ���������
		/********************************* ��̨������Ӧ�ʼ�ģ��Ĺ��С��������ơ��͡����ͱ�š� ********************************************************************/
		log.info(virualDesc + "��" + nodeName + "�Ľ����¼��и�ָ�ɵ����������ʼ�!");
		// add by guangsa for sendComplexMail in 2009-07-15 begin
//		String auditMeg = "��������ӣ��鿴��ϸ�������������ӣ�----------------------------";
		String workflowEntity = (String) bizParam.get("workflowHistory");
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity,
				processInstanceId);// ���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		// add by guangsa for sendComplexMail in 2009-07-15 end

		/************************* �ٴε����������� *****************************************/
		Map<String, String> auditUserEmail = new HashMap<String, String>();
		Map<String, String> browseUserEmail = new HashMap<String, String>();
//		int f = 0;
		if (counterSignAuditMegs.size() != 0 && counterSignAuditMegs != null
				&& !"".equals(counterSignAuditMegs)) {
			// ���Ϊ��̬��ǩ������ʱ��
			Set set = counterSignAuditMegs.keySet();
			Iterator counterMegs = set.iterator();
			while (counterMegs.hasNext()) {
				// add by gaowen for �ڵ��ض���ʽ �ʼ����� 20090927 begin
				Calendar nowCal = Calendar.getInstance();
				String year = String.valueOf(nowCal.get(nowCal.YEAR));
				String month = String.valueOf(nowCal.get(nowCal.MONTH) + 1);
				String day = String.valueOf(nowCal.get(nowCal.DAY_OF_MONTH));
				ConfigUnitMail configUnitMail = cs.findConfigUnitMailById(
						virtualDefinintionId, Long.valueOf(nodeId));
				UserInfo creatorMeg = (UserInfo) service.findUnique(
						UserInfo.class, "userName", creator);
				String userMeg = creatorMeg.getRealName();
				userMeg += "/" + creator;
				if (configUnitMail != null && !"".equals(configUnitMail)) {// ��Ӧ�ڵ���������Ӧ�ʼ�����
					// �����ʼ�������
					subject = configUnitMail.getSubject();
					String[] paramSub = new String[] { subTypeName, typeNum };
					subject = PropertiesUtil.format(subject, paramSub);
					// ���������ʼ�������
					content = HttpUtil.ConverUnicode(configUnitMail
							.getContent());
					String url = "<a href="
							+ PropertiesUtil.getProperties("system.web.url",
									"localhost:8080")
							+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
							+ "taskId=" + taskId + "&dataId=" + dataId
							+ "&reqClass=" + "&goStartState=" + goStartState
							+ "&taskName=" + "&applyType=" + applyType
							+ "&browseFlag=" + false + ">" + "��������</a>";
					String[] paramContent = new String[] { userMeg, typeName,
							typeNum, url, year, month, day };
					String[] paramArray = new String[7];// ��װ����������spl.length
					for (int i = 0; i < paramArray.length; i++) {
						paramArray[i] = paramContent[i];
					}
					content = PropertiesUtil.format(content, paramArray);
				} else {// ��Ӧ�ڵ�δ������Ӧ�ʼ�����
					if (subject == null || "".equals(subject)) {
						subject = creator + "�ύ��" + vDesc + "������!";// "����֪ͨ";
					}
				}
				Long taskid = (Long) counterMegs.next();
				String[] users = (String[]) counterSignAuditMegs.get(taskid);
				for (int i = 0; i < users.length; i++) {
					UserInfo userInfo = (UserInfo) service.findUnique(
							UserInfo.class, "userName", users[i]);
					String context = cs.htmlContent(virtualDefinintionId,
							nodeName, pageUrl, applyType, dataId, reqClass,
							goStartState, taskId, creatorMeg, vDesc, auditHis,
							hurryFlag, false, userInfo);

					auditUserEmail.put(userInfo.getEmail(), context);
				}
				try {
					for (Iterator<String> item = auditUserEmail.keySet()
							.iterator(); item.hasNext();) {
						String emailAdd = String.valueOf(item.next());
						ms.sendMimeMail(emailAdd, null, null, subject,
								auditUserEmail.get(emailAdd), null);
						log.info(virualDesc + "��" + nodeName
								+ "(�ڵ�)�������˷����ʼ��ɹ���");
					}
				} catch (Exception e) {
					log.info(virualDesc + "(����)��" + nodeName
							+ "(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
					e.printStackTrace();
				}
			}
		} else {
			// ���Ϊ����ָ�ɷ��ʼ�ʱ��
			// modify by guangsa for �����������˵��ʼ���ַ in 20090826 begin

			// add by guangsa for �ڵ��ض���ʽ in 20090827 begin
			Calendar nowCal = Calendar.getInstance();
			String year = String.valueOf(nowCal.get(nowCal.YEAR));
			String month = String.valueOf(nowCal.get(nowCal.MONTH) + 1);
			String day = String.valueOf(nowCal.get(nowCal.DAY_OF_MONTH));
			ConfigUnitMail configUnitMail = cs.findConfigUnitMailById(
					virtualDefinintionId, Long.valueOf(nodeId));
			UserInfo creatorMeg = (UserInfo) service.findUnique(UserInfo.class,
					"userName", creator);
			String userMeg = creatorMeg.getRealName();
			userMeg += "/" + creator;
			if (configUnitMail != null && !"".equals(configUnitMail)) {// ��Ӧ�ڵ���������Ӧ�ʼ�����
				// �����ʼ�������
				subject = configUnitMail.getSubject();
				String[] paramSub = new String[] { subTypeName, typeNum };
				subject = PropertiesUtil.format(subject, paramSub);
				// ���������ʼ�������
				content = HttpUtil.ConverUnicode(configUnitMail.getContent());
				String url = "<a href="
						+ PropertiesUtil.getProperties("system.web.url",
								"localhost:8080")
						+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
						+ "taskId=" + taskId + "&dataId=" + dataId
						+ "&reqClass=" + "&goStartState=" + goStartState
						+ "&taskName=" + "&applyType=" + applyType
						+ "&browseFlag=" + false + ">" + "��������</a>";
				String[] paramContent = new String[] { userMeg, typeName,
						typeNum, url, year, month, day };
				String[] paramArray = new String[7];// ��װ����������spl.length
				for (int i = 0; i < paramArray.length; i++) {
					paramArray[i] = paramContent[i];
				}
				content = PropertiesUtil.format(content, paramArray);
			} else {// ��Ӧ�ڵ�δ������Ӧ�ʼ�����
				if (subject == null || "".equals(subject)) {
					subject = creator + "�ύ��" + vDesc + "������!";// "����֪ͨ";
				}
			}
			// add by guangsa for �ڵ��ض���ʽ in 20090827 end
			for (int i = 0; i < auditPers.length; i++) {
				UserInfo userInfo = (UserInfo) service.findUnique(
						UserInfo.class, "userName", auditPers[i]);
				String context = cs.htmlContent(virtualDefinintionId, nodeName,
						pageUrl, applyType, dataId, reqClass, goStartState,
						taskId, creatorMeg, vDesc, auditHis, hurryFlag, false,
						userInfo);
				auditUserEmail.put(userInfo.getEmail(), context);
			}
			try {
				for (Iterator<String> item = auditUserEmail.keySet().iterator(); item
						.hasNext();) {
					String emailAdd = String.valueOf(item.next());
					ms.sendMimeMail(emailAdd, null, null, subject,
							auditUserEmail.get(emailAdd), null);
					log.info(virualDesc + "��" + nodeName + "(�ڵ�)�������˷����ʼ��ɹ���");
				}
			} catch (Exception e) {
				log.info(virualDesc + "(����)��" + nodeName
						+ "(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
				e.printStackTrace();
			}
			if (browsePers != null && !"".equals(browsePers)) {
				for (int j = 0; j < browsePers.length; j++) {
					UserInfo browseUser = (UserInfo) service.findUnique(
							UserInfo.class, "userName", browsePers[j]);
					String context = cs.htmlContent(virtualDefinintionId,
							nodeName, pageUrl, applyType, dataId, reqClass,
							goStartState, taskId, creatorMeg, vDesc, auditHis,
							hurryFlag, false, browseUser);
					browseUserEmail.put(browseUser.getEmail(), context);
				}

				try {
					for (Iterator<String> item = browseUserEmail.keySet()
							.iterator(); item.hasNext();) {
						String emailAdd = String.valueOf(item.next());
						ms.sendMimeMail(emailAdd, null, null, subject, browseUserEmail.get(emailAdd), null);
						log.info(virualDesc + "��" + nodeName
								+ "(�ڵ�)�������˷����ʼ��ɹ���");
					}
				} catch (Exception e) {
					log.info(virualDesc + "(����)��" + nodeName
							+ "(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
					e.printStackTrace();
				}
			}
		}
		log.info(virualDesc + "��" + nodeName + "(�ڵ�)�������˷����ʼ��ɹ���");
	}

	/**
	 * �����ʼ���Ϣ ITILר��
	 * 
	 * @param dataId
	 *            ��ҵ�������
	 * @param reqClass
	 *            ��ҵ�������
	 * @param goStartState
	 *            �����˵���ʼ�ڵ�key��
	 * @param processInstanceId
	 *            ����ʵ����Id��
	 * @param taskId
	 *            ������ID��
	 * @param nodeName
	 *            �����̽ڵ����ƣ�
	 * @param vDesc
	 *            ����������������
	 * @param virtualDefinintionId
	 *            ����������Id��
	 * @param creator
	 *            (���̴�����)
	 * @param bizParam
	 *            (����ҵ�����)
	 * @param node
	 *            �����̽ڵ㣩
	 * @param nodeId
	 *            �����̽ڵ�Id��
	 * @author gaowen 2009-11-27 ITIL �ʼ�������ʽ
	 */
	@SuppressWarnings("static-access")
	private void sendMailMessageToITIL(String nodeName, String pageUrl,
			String dataId, String reqClass, String goStartState,
			Long processInstanceId, Long taskId, String applyType,
			String vDesc, Long virtualDefinintionId, String creator,
			Map bizParam, Node node, String nodeId, String[] auditPers,
			Map counterSignAuditMegs, String[] browsePers) {
		// ���浱ǰ�ڵ��nodeName���Ա������̻��ˣ�
		String virualDesc = "";
//		String nowNodeDesc = "";
//		String nowNodeName = "";
		String subject = null;
		String content = null;
		String ccEmail = null;// �����˵�email��ַ
//		String[] configEmail = null;// ��̨���ã�����о͸��ƽ��룬û�о�Ϊ��ֵ
		/********************************* ��̨������Ӧ�ʼ�ģ��Ĺ��С��������ơ��͡����ͱ�š� ********************************************************************/
		String typeNum = (String) bizParam.get("eventCisn");// eventCisn
		String subTypeName = (String) bizParam.get("eventName");// eventName
		String typeName = "<STRONG>" + (String) bizParam.get("eventName")
				+ "</STRONG>";//  
		String hurryFlag = (String) bizParam.get("hurryFlag"); // ����ĳЩ��������Ҫ���������
		String reqFlag = "";
		if ("1".equals(hurryFlag)) {
			reqFlag = "�Ӽ�";
		}
		/********************************* ��̨������Ӧ�ʼ�ģ��Ĺ��С��������ơ��͡����ͱ�š� ********************************************************************/
		log.info(virualDesc + "��" + nodeName + "�Ľ����¼��и�ָ�ɵ����������ʼ�!");
		// add by guangsa for sendComplexMail in 2009-07-15 begin
//		String auditMeg = "��������ӣ��鿴��ϸ�������������ӣ�----------------------------";
		String workflowEntity = (String) bizParam.get("workflowHistory");
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity,
				processInstanceId);// ���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		// add by guangsa for sendComplexMail in 2009-07-15 end

		/************************* �ٴε����������� *****************************************/

		// add by guangsa for avoidAuditpers in 20090729 begin
		// remove by lee for ȥ���������� in 20091221 begin
		// if(ccEmail==null||"".equals(ccEmail)){
		// ccEmail = "guangshunan0813@163.com";
		// }
		// remove by lee for ȥ���������� in 20091221 end
		// add by guangsa for counterSignAuditTaskId in 20090729 begin
		// List auditUserEmail = new ArrayList();
		// List browseUserEmail = new ArrayList();
//		int f = 0;
		if (counterSignAuditMegs.size() != 0 && counterSignAuditMegs != null
				&& !"".equals(counterSignAuditMegs)) {
			// ���Ϊ��̬��ǩ������ʱ��
			Set set = counterSignAuditMegs.keySet();
			Iterator counterMegs = set.iterator();
			while (counterMegs.hasNext()) {
				Long taskid = (Long) counterMegs.next();
				String[] users = (String[]) counterSignAuditMegs.get(taskid);
				// add by gaowen for �ڵ��ض���ʽ �ʼ����� 20090927 begin
				Calendar nowCal = Calendar.getInstance();
				String year = String.valueOf(nowCal.get(nowCal.YEAR));
				String month = String.valueOf(nowCal.get(nowCal.MONTH) + 1);
				String day = String.valueOf(nowCal.get(nowCal.DAY_OF_MONTH));
				ConfigUnitMail configUnitMail = cs.findConfigUnitMailById(
						virtualDefinintionId, Long.valueOf(nodeId));
				UserInfo creatorMeg = (UserInfo) service.findUnique(
						UserInfo.class, "userName", creator);
				String userMeg = creatorMeg.getRealName();
				userMeg += "/" + creator;
				if (configUnitMail != null && !"".equals(configUnitMail)) {// ��Ӧ�ڵ���������Ӧ�ʼ�����
					// �����ʼ�������
					subject = configUnitMail.getSubject();
					String[] paramSub = new String[] { subTypeName, typeNum };
					subject = PropertiesUtil.format(subject, paramSub);
					// ���������ʼ�������
					content = HttpUtil.ConverUnicode(configUnitMail
							.getContent());
					String url = "<a href="
							+ PropertiesUtil.getProperties("system.web.url",
									"localhost:8080")
							+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
							+ "taskId=" + taskId + "&dataId=" + dataId
							+ "&reqClass=" + "&goStartState=" + goStartState
							+ "&taskName=" + "&applyType=" + applyType
							+ "&browseFlag=" + false + ">" + "��������</a>";
					String[] paramContent = new String[] { userMeg, typeName,
							typeNum, url, year, month, day };
					String[] paramArray = new String[7];// ��װ����������spl.length
					for (int j = 0; j < paramArray.length; j++) {
						paramArray[j] = paramContent[j];
					}
					content = PropertiesUtil.format(content, paramArray);
				} else {// ��Ӧ�ڵ�δ������Ӧ�ʼ�����
					if (subject == null || "".equals(subject)) {
						subject = "IT��ܰ��ʾ:" + userMeg + "�ύ��" + reqFlag + vDesc
								+ "��������ʱ������";// "����֪ͨ";
					}
				}
				for (int i = 0; i < users.length; i++) {
					UserInfo userInfo = (UserInfo) service.findUnique(
							UserInfo.class, "userName", users[i]);
					String auditUserEmail = userInfo.getEmail();
					// add by gaowen for �ڵ��ض���ʽ �ʼ����� 20090927 end
					// String context =
					// cs.htmlContent(nodeName,pageUrl,applyType,dataId,reqClass,goStartState,taskid,creatorMeg,
					// vDesc, auditHis,hurryFlag,false,userInfo);
					String context = "";
					if(content != null && !"".equalsIgnoreCase(content)){
						context = content;
					}else{
						context = cs.htmlContent(virtualDefinintionId,
								nodeName, pageUrl, applyType, dataId, reqClass,
								goStartState, taskid, creatorMeg, vDesc, auditHis,
								hurryFlag, false, userInfo);
					}
					try {
						ms.sendMimeMail(auditUserEmail, ccEmail, null, subject,
								context, null);
						log.info(virualDesc + "��" + nodeName
								+ "(�ڵ�)�������˷����ʼ��ɹ���");
					} catch (Exception e) {
						log.info(virualDesc + "(����)��" + nodeName
								+ "(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
						e.printStackTrace();
					}
				}

			}
		} else {
			// ���Ϊ����ָ�ɷ��ʼ�ʱ��
			// modify by guangsa for �����������˵��ʼ���ַ in 20090826 begin
			// add by guangsa for �ڵ��ض���ʽ in 20090827 begin
			Calendar nowCal = Calendar.getInstance();
			String year = String.valueOf(nowCal.get(nowCal.YEAR));
			String month = String.valueOf(nowCal.get(nowCal.MONTH) + 1);
			String day = String.valueOf(nowCal.get(nowCal.DAY_OF_MONTH));
			ConfigUnitMail configUnitMail = cs.findConfigUnitMailById(
					virtualDefinintionId, Long.valueOf(nodeId));
			UserInfo creatorMeg = (UserInfo) service.findUnique(UserInfo.class,
					"userName", creator);
			String userMeg = creatorMeg.getRealName();
			userMeg += "/" + creator;
			if (configUnitMail != null && !"".equals(configUnitMail)) {// ��Ӧ�ڵ���������Ӧ�ʼ�����
				// �����ʼ�������
				subject = configUnitMail.getSubject();
				String[] paramSub = new String[] { subTypeName, typeNum };
				subject = PropertiesUtil.format(subject, paramSub);
				// ���������ʼ�������
				content = HttpUtil.ConverUnicode(configUnitMail.getContent());
				String url = "<a href="
						+ PropertiesUtil.getProperties("system.web.url",
								"localhost:8080")
						+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
						+ "taskId=" + taskId + "&dataId=" + dataId
						+ "&reqClass=" + "&goStartState=" + goStartState
						+ "&taskName=" + "&applyType=" + applyType
						+ "&browseFlag=" + false + ">" + "��������</a>";
				String[] paramContent = new String[] { userMeg, typeName,
						typeNum, url, year, month, day };
				String[] paramArray = new String[7];// ��װ����������spl.length
				for (int i = 0; i < paramArray.length; i++) {
					paramArray[i] = paramContent[i];
				}
				content = PropertiesUtil.format(content, paramArray);
			} else {// ��Ӧ�ڵ�δ������Ӧ�ʼ�����
				if (subject == null || "".equals(subject)) {
					subject = "IT��ܰ��ʾ:" + userMeg + "�ύ��" + reqFlag + vDesc
							+ "��������ʱ������";// "����֪ͨ";
				}

			}
			// add by guangsa for �ڵ��ض���ʽ in 20090827 end
			for (int i = 0; i < auditPers.length; i++) {
				UserInfo userInfo = (UserInfo) service.findUnique(
						UserInfo.class, "userName", auditPers[i]);
				String auditUserEmail = userInfo.getEmail();
				String context = "";
				if(content != null && !"".equalsIgnoreCase(content)){
					context = content;
				}else{
					context  = cs.htmlContent(virtualDefinintionId, nodeName,
						pageUrl, applyType, dataId, reqClass, goStartState,
						taskId, creatorMeg, vDesc, auditHis, hurryFlag, false,
						userInfo);
				}
				try {
					ms.sendMimeMail(auditUserEmail, ccEmail, null, subject,
							context, null);
				} catch (Exception e) {
					log.info(virualDesc + "(����)��" + nodeName
							+ "(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
					e.printStackTrace();
				}
			}

			// modify by guangsa for �����������˵��ʼ���ַ in 20090826 end

			// modify by guangsa for �����ǲ鿴�˵��ʼ���ַ in 20090826 begin
			if (browsePers != null && !"".equals(browsePers)) {
				for (int j = 0; j < browsePers.length; j++) {
					UserInfo browseUser = (UserInfo) service.findUnique(
							UserInfo.class, "userName", browsePers[j]);
					String browseUserEmail = browseUser.getEmail();
					String context = "";
					if(content != null && !"".equalsIgnoreCase(content)){
						context = content;
					}else{
						context = cs.htmlContent(virtualDefinintionId,
							nodeName, pageUrl, applyType, dataId, reqClass,
							goStartState, taskId, creatorMeg, vDesc, auditHis,
							hurryFlag, false, browseUser);
					}
					try {
						ms.sendMimeMail(browseUserEmail, ccEmail, null,
								subject, context, null);
					} catch (Exception e) {
						log.info(virualDesc + "(����)��" + nodeName
								+ "(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
						e.printStackTrace();
					}
				}
			}
			// modify by guangsa for �����ǲ鿴�˵��ʼ���ַ in 20090826 end
		}
		// add by guangsa for counterSignAuditTaskId in 20090729 begin
		log.info(virualDesc + "��" + nodeName + "(�ڵ�)�������˷����ʼ��ɹ���");
	}

}
