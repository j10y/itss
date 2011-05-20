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
		String projectName = PropertiesUtil.getProperties("project.name");// 获取项目根路径
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
	 * 发送邮件信息
	 * 
	 * @param dataId
	 *            （业务参数）
	 * @param reqClass
	 *            （业务参数）
	 * @param goStartState
	 *            （回退到开始节点key）
	 * @param processInstanceId
	 *            （真实流程Id）
	 * @param taskId
	 *            （任务ID）
	 * @param nodeName
	 *            （流程节点名称）
	 * @param vDesc
	 *            （虚拟流程描述）
	 * @param virtualDefinintionId
	 *            （虚拟流程Id）
	 * @param creator
	 *            (流程创建者)
	 * @param bizParam
	 *            (流程业务参数)
	 * @param node
	 *            （流程节点）
	 * @param nodeId
	 *            （流程节点Id）
	 */
	@SuppressWarnings("static-access")
	private void sendMailMessage(String nodeName, String pageUrl,
			String dataId, String reqClass, String goStartState,
			Long processInstanceId, Long taskId, String applyType,
			String vDesc, Long virtualDefinintionId, String creator,
			Map bizParam, Node node, String nodeId, String[] auditPers,
			Map counterSignAuditMegs, String[] browsePers) {
		// 保存当前节点的nodeName，以便于流程回退；
		String virualDesc = "";
//		String nowNodeDesc = "";
//		String nowNodeName = "";
		String subject = null;
		String content = null;
//		String[] ccEmail = null;// 抄送人的email地址
//		String[] configEmail = null;// 后台配置，如果有就复制进入，没有就为空值
		/********************************* 后台配置相应邮件模板的固有“类型名称”和“类型编号” ********************************************************************/
		String typeNum = (String) bizParam.get("eventCisn");// eventCisn
		String subTypeName = (String) bizParam.get("eventName");// eventName
		String typeName = "<STRONG>" + (String) bizParam.get("eventName")
				+ "</STRONG>";//  
		String hurryFlag = (String) bizParam.get("hurryFlag"); // 这是某些需求中需要的特殊参数
		/********************************* 后台配置相应邮件模板的固有“类型名称”和“类型编号” ********************************************************************/
		log.info(virualDesc + "在" + nodeName + "的进入事件中给指派的人审批发邮件!");
		// add by guangsa for sendComplexMail in 2009-07-15 begin
//		String auditMeg = "点击此链接，查看仔细并请审批！链接：----------------------------";
		String workflowEntity = (String) bizParam.get("workflowHistory");
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity,
				processInstanceId);// 查找出来的是所有的按流程顺序排列的节点信息
		// add by guangsa for sendComplexMail in 2009-07-15 end

		/************************* 再次调用其他方法 *****************************************/
		Map<String, String> auditUserEmail = new HashMap<String, String>();
		Map<String, String> browseUserEmail = new HashMap<String, String>();
//		int f = 0;
		if (counterSignAuditMegs.size() != 0 && counterSignAuditMegs != null
				&& !"".equals(counterSignAuditMegs)) {
			// 如果为动态会签审批人时候
			Set set = counterSignAuditMegs.keySet();
			Iterator counterMegs = set.iterator();
			while (counterMegs.hasNext()) {
				// add by gaowen for 节点特定格式 邮件主题 20090927 begin
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
				if (configUnitMail != null && !"".equals(configUnitMail)) {// 相应节点配置了相应邮件内容
					// 配置邮件的主题
					subject = configUnitMail.getSubject();
					String[] paramSub = new String[] { subTypeName, typeNum };
					subject = PropertiesUtil.format(subject, paramSub);
					// 这是配置邮件的内容
					content = HttpUtil.ConverUnicode(configUnitMail
							.getContent());
					String url = "<a href="
							+ PropertiesUtil.getProperties("system.web.url",
									"localhost:8080")
							+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
							+ "taskId=" + taskId + "&dataId=" + dataId
							+ "&reqClass=" + "&goStartState=" + goStartState
							+ "&taskName=" + "&applyType=" + applyType
							+ "&browseFlag=" + false + ">" + "审批链接</a>";
					String[] paramContent = new String[] { userMeg, typeName,
							typeNum, url, year, month, day };
					String[] paramArray = new String[7];// 包装参数的数组spl.length
					for (int i = 0; i < paramArray.length; i++) {
						paramArray[i] = paramContent[i];
					}
					content = PropertiesUtil.format(content, paramArray);
				} else {// 相应节点未配置相应邮件内容
					if (subject == null || "".equals(subject)) {
						subject = creator + "提交了" + vDesc + "请审批!";// "审批通知";
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
						log.info(virualDesc + "在" + nodeName
								+ "(节点)给审批人发送邮件成功！");
					}
				} catch (Exception e) {
					log.info(virualDesc + "(流程)在" + nodeName
							+ "(节点)给三部分的审批人发送邮件时发生异常！");
					e.printStackTrace();
				}
			}
		} else {
			// 如果为其他指派发邮件时候
			// modify by guangsa for 这种是审批人的邮件地址 in 20090826 begin

			// add by guangsa for 节点特定格式 in 20090827 begin
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
			if (configUnitMail != null && !"".equals(configUnitMail)) {// 相应节点配置了相应邮件内容
				// 配置邮件的主题
				subject = configUnitMail.getSubject();
				String[] paramSub = new String[] { subTypeName, typeNum };
				subject = PropertiesUtil.format(subject, paramSub);
				// 这是配置邮件的内容
				content = HttpUtil.ConverUnicode(configUnitMail.getContent());
				String url = "<a href="
						+ PropertiesUtil.getProperties("system.web.url",
								"localhost:8080")
						+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
						+ "taskId=" + taskId + "&dataId=" + dataId
						+ "&reqClass=" + "&goStartState=" + goStartState
						+ "&taskName=" + "&applyType=" + applyType
						+ "&browseFlag=" + false + ">" + "审批链接</a>";
				String[] paramContent = new String[] { userMeg, typeName,
						typeNum, url, year, month, day };
				String[] paramArray = new String[7];// 包装参数的数组spl.length
				for (int i = 0; i < paramArray.length; i++) {
					paramArray[i] = paramContent[i];
				}
				content = PropertiesUtil.format(content, paramArray);
			} else {// 相应节点未配置相应邮件内容
				if (subject == null || "".equals(subject)) {
					subject = creator + "提交了" + vDesc + "请审批!";// "审批通知";
				}
			}
			// add by guangsa for 节点特定格式 in 20090827 end
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
					log.info(virualDesc + "在" + nodeName + "(节点)给审批人发送邮件成功！");
				}
			} catch (Exception e) {
				log.info(virualDesc + "(流程)在" + nodeName
						+ "(节点)给三部分的审批人发送邮件时发生异常！");
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
						log.info(virualDesc + "在" + nodeName
								+ "(节点)给审批人发送邮件成功！");
					}
				} catch (Exception e) {
					log.info(virualDesc + "(流程)在" + nodeName
							+ "(节点)给三部分的审批人发送邮件时发生异常！");
					e.printStackTrace();
				}
			}
		}
		log.info(virualDesc + "在" + nodeName + "(节点)给审批人发送邮件成功！");
	}

	/**
	 * 发送邮件信息 ITIL专用
	 * 
	 * @param dataId
	 *            （业务参数）
	 * @param reqClass
	 *            （业务参数）
	 * @param goStartState
	 *            （回退到开始节点key）
	 * @param processInstanceId
	 *            （真实流程Id）
	 * @param taskId
	 *            （任务ID）
	 * @param nodeName
	 *            （流程节点名称）
	 * @param vDesc
	 *            （虚拟流程描述）
	 * @param virtualDefinintionId
	 *            （虚拟流程Id）
	 * @param creator
	 *            (流程创建者)
	 * @param bizParam
	 *            (流程业务参数)
	 * @param node
	 *            （流程节点）
	 * @param nodeId
	 *            （流程节点Id）
	 * @author gaowen 2009-11-27 ITIL 邮件发送样式
	 */
	@SuppressWarnings("static-access")
	private void sendMailMessageToITIL(String nodeName, String pageUrl,
			String dataId, String reqClass, String goStartState,
			Long processInstanceId, Long taskId, String applyType,
			String vDesc, Long virtualDefinintionId, String creator,
			Map bizParam, Node node, String nodeId, String[] auditPers,
			Map counterSignAuditMegs, String[] browsePers) {
		// 保存当前节点的nodeName，以便于流程回退；
		String virualDesc = "";
//		String nowNodeDesc = "";
//		String nowNodeName = "";
		String subject = null;
		String content = null;
		String ccEmail = null;// 抄送人的email地址
//		String[] configEmail = null;// 后台配置，如果有就复制进入，没有就为空值
		/********************************* 后台配置相应邮件模板的固有“类型名称”和“类型编号” ********************************************************************/
		String typeNum = (String) bizParam.get("eventCisn");// eventCisn
		String subTypeName = (String) bizParam.get("eventName");// eventName
		String typeName = "<STRONG>" + (String) bizParam.get("eventName")
				+ "</STRONG>";//  
		String hurryFlag = (String) bizParam.get("hurryFlag"); // 这是某些需求中需要的特殊参数
		String reqFlag = "";
		if ("1".equals(hurryFlag)) {
			reqFlag = "加急";
		}
		/********************************* 后台配置相应邮件模板的固有“类型名称”和“类型编号” ********************************************************************/
		log.info(virualDesc + "在" + nodeName + "的进入事件中给指派的人审批发邮件!");
		// add by guangsa for sendComplexMail in 2009-07-15 begin
//		String auditMeg = "点击此链接，查看仔细并请审批！链接：----------------------------";
		String workflowEntity = (String) bizParam.get("workflowHistory");
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity,
				processInstanceId);// 查找出来的是所有的按流程顺序排列的节点信息
		// add by guangsa for sendComplexMail in 2009-07-15 end

		/************************* 再次调用其他方法 *****************************************/

		// add by guangsa for avoidAuditpers in 20090729 begin
		// remove by lee for 去掉垃圾抄送 in 20091221 begin
		// if(ccEmail==null||"".equals(ccEmail)){
		// ccEmail = "guangshunan0813@163.com";
		// }
		// remove by lee for 去掉垃圾抄送 in 20091221 end
		// add by guangsa for counterSignAuditTaskId in 20090729 begin
		// List auditUserEmail = new ArrayList();
		// List browseUserEmail = new ArrayList();
//		int f = 0;
		if (counterSignAuditMegs.size() != 0 && counterSignAuditMegs != null
				&& !"".equals(counterSignAuditMegs)) {
			// 如果为动态会签审批人时候
			Set set = counterSignAuditMegs.keySet();
			Iterator counterMegs = set.iterator();
			while (counterMegs.hasNext()) {
				Long taskid = (Long) counterMegs.next();
				String[] users = (String[]) counterSignAuditMegs.get(taskid);
				// add by gaowen for 节点特定格式 邮件主题 20090927 begin
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
				if (configUnitMail != null && !"".equals(configUnitMail)) {// 相应节点配置了相应邮件内容
					// 配置邮件的主题
					subject = configUnitMail.getSubject();
					String[] paramSub = new String[] { subTypeName, typeNum };
					subject = PropertiesUtil.format(subject, paramSub);
					// 这是配置邮件的内容
					content = HttpUtil.ConverUnicode(configUnitMail
							.getContent());
					String url = "<a href="
							+ PropertiesUtil.getProperties("system.web.url",
									"localhost:8080")
							+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
							+ "taskId=" + taskId + "&dataId=" + dataId
							+ "&reqClass=" + "&goStartState=" + goStartState
							+ "&taskName=" + "&applyType=" + applyType
							+ "&browseFlag=" + false + ">" + "审批链接</a>";
					String[] paramContent = new String[] { userMeg, typeName,
							typeNum, url, year, month, day };
					String[] paramArray = new String[7];// 包装参数的数组spl.length
					for (int j = 0; j < paramArray.length; j++) {
						paramArray[j] = paramContent[j];
					}
					content = PropertiesUtil.format(content, paramArray);
				} else {// 相应节点未配置相应邮件内容
					if (subject == null || "".equals(subject)) {
						subject = "IT温馨提示:" + userMeg + "提交了" + reqFlag + vDesc
								+ "，请您及时审批。";// "审批通知";
					}
				}
				for (int i = 0; i < users.length; i++) {
					UserInfo userInfo = (UserInfo) service.findUnique(
							UserInfo.class, "userName", users[i]);
					String auditUserEmail = userInfo.getEmail();
					// add by gaowen for 节点特定格式 邮件主题 20090927 end
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
						log.info(virualDesc + "在" + nodeName
								+ "(节点)给审批人发送邮件成功！");
					} catch (Exception e) {
						log.info(virualDesc + "(流程)在" + nodeName
								+ "(节点)给三部分的审批人发送邮件时发生异常！");
						e.printStackTrace();
					}
				}

			}
		} else {
			// 如果为其他指派发邮件时候
			// modify by guangsa for 这种是审批人的邮件地址 in 20090826 begin
			// add by guangsa for 节点特定格式 in 20090827 begin
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
			if (configUnitMail != null && !"".equals(configUnitMail)) {// 相应节点配置了相应邮件内容
				// 配置邮件的主题
				subject = configUnitMail.getSubject();
				String[] paramSub = new String[] { subTypeName, typeNum };
				subject = PropertiesUtil.format(subject, paramSub);
				// 这是配置邮件的内容
				content = HttpUtil.ConverUnicode(configUnitMail.getContent());
				String url = "<a href="
						+ PropertiesUtil.getProperties("system.web.url",
								"localhost:8080")
						+ "/infoAdmin/workflow/configPage/auditFromMail.jsp?"
						+ "taskId=" + taskId + "&dataId=" + dataId
						+ "&reqClass=" + "&goStartState=" + goStartState
						+ "&taskName=" + "&applyType=" + applyType
						+ "&browseFlag=" + false + ">" + "审批链接</a>";
				String[] paramContent = new String[] { userMeg, typeName,
						typeNum, url, year, month, day };
				String[] paramArray = new String[7];// 包装参数的数组spl.length
				for (int i = 0; i < paramArray.length; i++) {
					paramArray[i] = paramContent[i];
				}
				content = PropertiesUtil.format(content, paramArray);
			} else {// 相应节点未配置相应邮件内容
				if (subject == null || "".equals(subject)) {
					subject = "IT温馨提示:" + userMeg + "提交了" + reqFlag + vDesc
							+ "，请您及时审批。";// "审批通知";
				}

			}
			// add by guangsa for 节点特定格式 in 20090827 end
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
					log.info(virualDesc + "(流程)在" + nodeName
							+ "(节点)给三部分的审批人发送邮件时发生异常！");
					e.printStackTrace();
				}
			}

			// modify by guangsa for 这种是审批人的邮件地址 in 20090826 end

			// modify by guangsa for 这种是查看人的邮件地址 in 20090826 begin
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
						log.info(virualDesc + "(流程)在" + nodeName
								+ "(节点)给三部分的审批人发送邮件时发生异常！");
						e.printStackTrace();
					}
				}
			}
			// modify by guangsa for 这种是查看人的邮件地址 in 20090826 end
		}
		// add by guangsa for counterSignAuditTaskId in 20090729 begin
		log.info(virualDesc + "在" + nodeName + "(节点)给审批人发送邮件成功！");
	}

}
