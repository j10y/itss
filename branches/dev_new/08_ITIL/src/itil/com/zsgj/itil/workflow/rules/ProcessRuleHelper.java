package com.zsgj.itil.workflow.rules;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventAuditHis;
import com.zsgj.itil.event.entity.EventRelation;
import com.zsgj.itil.event.entity.EventStatus;
import com.zsgj.itil.event.entity.EventSulotion;
import com.zsgj.itil.knowledge.entity.KnowContract;
import com.zsgj.itil.knowledge.entity.KnowContractAuditHis;
import com.zsgj.itil.knowledge.entity.KnowFile;
import com.zsgj.itil.knowledge.entity.KnowFileAuditHis;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.knowledge.entity.KnowledgeAuditHis;
import com.zsgj.itil.knowledge.entity.KnowledgeType;
import com.zsgj.itil.service.service.SCIRelationShipService;

/**
 * 
 * @Class Name ProcessRuleHelper
 * @Author YangTao
 * @Create In Mar 4, 2009
 */
public class ProcessRuleHelper {
	ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	Service service = (Service) ContextHolder.getBean("baseService");
	SCIRelationShipService sciRelationShipService = (SCIRelationShipService) ContextHolder.getBean("sciRelationShipService");
//	private EventService eventService = (EventService) ContextHolder.getBean("EventService");
	private MailSenderService ms = (MailSenderService) ContextHolder.getBean("mailSenderService");

	/** **********************************************************问题与事件流程*************************************************************** */
	public void saveEventHis(String nodeId, String nodeName, String processId,
			String result, String comment, Event event) {

		EventAuditHis eah = new EventAuditHis();
		eah.setResultFlag(result);
		eah.setProcessId(Long.valueOf(processId));
		eah.setApprover(UserContext.getUserInfo());
		eah.setApproverDate(Calendar.getInstance().getTime());
		eah.setEvent(event);
		eah.setNodeName(nodeName);
		eah.setComment(comment);
		eah.setNodeId(String.valueOf(nodeId));
		try {
			service.save(eah);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eventStartFlag(String dataId, String nodeId, String nodeName,
			String processId,Map busMap) throws Exception {
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
//		EventStatus reAssignStatus = (EventStatus) service.findUnique(
//				EventStatus.class, "keyword", "confirm");
//		EventStatus endStatus = (EventStatus) service.findUnique(
//				EventStatus.class, "keyword", "finish");
//		EventStatus userconfirmStatus = (EventStatus) service.findUnique(
//				EventStatus.class, "keyword", "userconfirm");
		Event event = (Event) service.find(Event.class, dataId, true);
		event.setEventStatus(dealingStatus);
		this.saveEventHis(nodeId, nodeName, processId, "", "", event);
		service.save(event);
		// ms.sendSimplyMail(UserContext.getUserInfo().getEmail(), null, null,
		// "事件提交", "你刚提交了一个事件");eventHtmlContent
		ms.sendMimeMail(UserContext.getUserInfo().getEmail(), null, null,
				"您已经成功提交一个问题，编号为（" + event.getEventCisn()
						+ "）”我们会尽快解决并向您反馈。谢谢！", this.eventHtmlContent(
						UserContext.getUserInfo(), "", event), null);
	}

	public String engineerProcessFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment)
			throws Exception {
		Event event = (Event) service.find(Event.class, dataId);
		// 设置Event状态
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
		EventStatus reAssignStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "confirm");
		EventStatus endStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "finish");
		EventStatus userconfirmStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "userconfirm");
		if (result.equals("cannotresolve")) {
			event.setEventStatus(reAssignStatus);
		} else if (result.equals("resolveByuserself")) {
			event.setEventStatus(endStatus);
		} else if (result.equals("touserconfirm")) {
			event.setEventStatus(userconfirmStatus);
		} else {
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		// result替换next
		return result;
	}

	public String headerProcessFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment)
			throws Exception {
		Event event = (Event) service.find(Event.class, dataId);
		// 设置Event状态
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
		if (result.equals("reAssign")) {
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		// result替换next
		return result;
	}

	public String otherOrgProcessFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment)
			throws Exception {
		Event event = (Event) service.find(Event.class, dataId);
		// 设置Event状态
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
		EventStatus reAssignStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "confirm");
		EventStatus endStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "finish");
		EventStatus userconfirmStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "userconfirm");
		if (result.equals("cannotresolve")) {
			event.setEventStatus(reAssignStatus);
		} else if (result.equals("resolveByuserself")) {
			event.setEventStatus(endStatus);
		} else if (result.equals("touserconfirm")) {
			event.setEventStatus(userconfirmStatus);
		} else {
			event.setEventStatus(dealingStatus);
		}
		event.setEventStatus(dealingStatus);
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		// result替换next
		return result;
	}

	public String userConfirmFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment)
			throws Exception {
		Event event = (Event) service.find(Event.class, dataId);
		// 设置Event状态
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
//		EventStatus reAssignStatus = (EventStatus) service.findUnique(
//				EventStatus.class, "keyword", "confirm");
		EventStatus endStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "finish");
//		EventStatus userconfirmStatus = (EventStatus) service.findUnique(
//				EventStatus.class, "keyword", "userconfirm");
//		String url = PropertiesUtil.getProperties("cc.web.endUrl");// 获取邮件页面
//		String rootPath = PropertiesUtil.getProperties("system.web.url");// 获取项目根路径
//		String realUrl = rootPath + url + "?dataId=" + dataId + "&isExist=s";
		if (result.equals("over")) {
			event.setEventStatus(endStatus);
			// delete by guoxl in 2009/09/02 begin 此处不发邮件了，已经在工程师处理完毕后发过邮件了
			// ms.sendMimeMail(UserContext.getUserInfo().getEmail(), null, null,
			// "您提交的"+event.getEventName()+"（事件编号为"+event.getEventCisn()+"）已处理完毕，请确认并反馈满意度。谢谢！",this.eventEndHtmlContent(UserContext.getUserInfo(),
			// "", event), null);
			// delete by guoxl in 2009/09/02 end

			// ------------------add by guoxl in 2009/08/13 begin
			// 查找结束的事件是否有没有结束的子事件，有的话就结束
			List<EventRelation> EventRelationList = service.find(
					EventRelation.class, "parentEvent", event);
			if (EventRelationList.size() > 0) {// 如果存在子事件
				for (EventRelation eventRel : EventRelationList) {
					Event childEvent = eventRel.getEvent();
					if (!"finish".equals(childEvent.getEventStatus()
							.getKeyword())) {// 是审批中的
						EventAuditHis eventAuditHis = (EventAuditHis) service
								.find(EventAuditHis.class, "event", childEvent)
								.get(0);
						Long instanceId = eventAuditHis.getProcessId();
						if (instanceId != null) {
							childEvent.setEventStatus(endStatus);// 首先将子事件设置为结束状态
							EventSulotion eventSulotion = (EventSulotion) service
									.findUnique(EventSulotion.class, "event",
											childEvent);
							if (eventSulotion == null) {// 如果子事件没有解决方案,将父事件的解决方案赋给子事件
								EventSulotion eventS = new EventSulotion();
								EventSulotion parentEventSulotion = (EventSulotion) service
										.findUnique(EventSulotion.class,
												"event", event);
								eventS.setEvent(childEvent);
								eventS.setKnowledge(parentEventSulotion
										.getKnowledge());
								eventS.setCreatName(UserContext.getUserInfo());
								eventS.setCreateDate(new Date());
								service.save(eventS);
							}
							ps.endProcess(instanceId);// 结束子事件的流程

							// 结束事件
						}
					}
				}

			}
			// ------------------add by guoxl in 2009/08/13 end
		} else {
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		// result替换next
		return result;
	}

	// ****************************************************知识管理*******************************************
	public void saveKnowledgeHis(BaseObject baseObject, String nodeId,
			String nodeName, String processId, String result, String comment) {
		if (baseObject instanceof KnowFile) {// 文件类型
			KnowFileAuditHis kfah = new KnowFileAuditHis();
			kfah.setResultFlag(result);
			kfah.setProcessId(Long.valueOf(processId));
			kfah.setApprover(UserContext.getUserInfo());
			kfah.setApproverDate(Calendar.getInstance().getTime());
			kfah.setKnowFile((KnowFile) baseObject);
			// kfah.setObjType(kfah.OBJTYPE_KNOWLEDGEFILE);
			kfah.setNodeName(nodeName);
			kfah.setComment(comment);
			kfah.setNodeId(String.valueOf(nodeId));
			service.save(kfah);
		} else if (baseObject instanceof Knowledge) {// 解决方案类型
			KnowledgeAuditHis kah = new KnowledgeAuditHis();
			kah.setResultFlag(result);
			kah.setProcessId(Long.valueOf(processId));
			kah.setApprover(UserContext.getUserInfo());
			kah.setApproverDate(Calendar.getInstance().getTime());
			kah.setKnowledge((Knowledge) baseObject);
			// kah.setObjType(kah.OBJTYPE_KNOWLEDGE);
			kah.setNodeName(nodeName);
			kah.setComment(comment);
			kah.setNodeId(String.valueOf(nodeId));
			service.save(kah);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			KnowContractAuditHis kcah = new KnowContractAuditHis();
			kcah.setResultFlag(result);
			kcah.setProcessId(Long.valueOf(processId));
			kcah.setApprover(UserContext.getUserInfo());
			kcah.setApproverDate(Calendar.getInstance().getTime());
			kcah.setKnowContract((KnowContract) baseObject);
			// kcah.setObjType(kcah.OBJTYPE_KNOWLEDGECONTRACT);
			kcah.setNodeName(nodeName);
			kcah.setComment(comment);
			service.save(kcah);
		}
	}

	public void knowledgeStartFlag(String dataId, String dataType,
			String nodeId, String nodeName, String processId) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// 文件类型
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this
					.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
							"", "");
			knowFile.setStatus(new Integer("2"));// 2为提交审批中
			service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// 解决方案类型
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			knowledge.setStatus(new Integer("2"));// 2为提交审批中
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, "",
					"");
			service.save(knowledge);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					"", "");
			knowContract.setStatus(new Integer("2"));// 2为提交审批中
			service.save(knowContract);
		}
	}

	public String judgeTypeFlag(String dataId, String dataType, String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// 文件类型
			result = "tofa";
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// 解决方案类型
			result = "tosa";
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			result = "toca";
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowContract);
		}
		return result;
	}

	public String fileApprovalFlag(String dataId, String dataType,
			String nodeId, String nodeName, String processId, String result,
			String comment) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// 文件类型
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			if (result.equals("Y")) {// 同意
				knowFile.setStatus(new Integer("2"));
			} else {// 不同意，草稿
				knowFile.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// 解决方案类型
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowContract);
		}
		return result;
	}

	public String solutionApprovalFlag(String dataId, String dataType,
			String nodeId, String nodeName, String processId, String result,
			String comment) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// 文件类型
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// 解决方案类型
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			if (result.equals("Y")) {// 同意
				knowledge.setStatus(new Integer("1"));
			} else {// 不同意，草稿
				knowledge.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			service.save(knowledge);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowContract);
		}
		return result;
	}

	public String contractApprovalFlag(String dataId, String dataType,
			String nodeId, String nodeName, String processId, String result,
			String comment) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// 文件类型
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// 解决方案类型
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			if (result.equals("Y")) {// 同意
				knowContract.setStatus(new Integer("2"));
			} else {// 不同意，草稿
				knowContract.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					result, comment);
			service.save(knowContract);
		}
		return result;
	}

	public String serviceDeptServiceStationFlag(String dataId, String dataType,
			String nodeId, String nodeName, String processId, String result,
			String comment) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// 文件类型
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			if (result.equals("Y")) {// 同意
				knowFile.setStatus(new Integer("1"));
			} else {// 不同意，草稿
				knowFile.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// 解决方案类型
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowContract);
		}
		return result;
	}

	public String serviceDeptThreeStationFlag(String dataId, String dataType,
			String nodeId, String nodeName, String processId, String result,
			String comment) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// 文件类型
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// 解决方案类型
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			if (result.equals("Y")) {// 同意
				knowContract.setStatus(new Integer("1"));
			} else {// 不同意，草稿
				knowContract.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					result, comment);
			service.save(knowContract);
		}
		return result;
	}

	public String gobackFlag(String dataId, String dataType, String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// 文件类型
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			knowFile.setStatus(new Integer("0"));
			service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// 解决方案类型
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			knowledge.setStatus(new Integer("0"));
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			service.save(knowledge);
		} else {// 合同类型com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			knowContract.setStatus(new Integer("0"));
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					result, comment);
			service.save(knowContract);
		}
		return result;
	}

	// 邮件格式1EventHtmlContent()；
	private String eventHtmlContent(UserInfo creator, String url, Event event) {

		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateString = dateFormat.format(date);

		sb
				.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title> 您已经成功提交一个问题，编号为(" + event.getEventCisn()
				+ "）”我们会尽快解决并向您反馈。谢谢</title>");
		sb
				.append("<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb
				.append("<meta http-equiv=\"description\" content=\"this is my page\">");
		sb
				.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");

		sb
				.append("<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("<style type=\"text/css\">");
		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 24px;");
		sb.append("font-weight: bold;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family: '楷体_GB2312'");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div align=\"center\">");
		sb
				.append("<table width=\"1000\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("<tr>");
		sb.append("<td height=\"10\" colspan=\"3\" nowrap>");
		sb.append("<div align=\"center\" >");
		sb.append("<div align=\"center\">事件确认通知</div>");
		sb.append("</div>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>尊敬的" + creator.getRealName() + "/" + creator.getItcode()
				+ "您好:");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("&nbsp; &nbsp; &nbsp; &nbsp; 您已经成功提交"
				+ "<font style='font-weight: bold'>" + event.getSummary()
				+ "</font>（事件编号为" + event.getEventCisn()
				+ "），我们会安排专人进行受理，在处理后会第一时间向您反馈。");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE2\">");
		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; ");

		sb
				.append("感谢您使用集团IT服务");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<div align=\"right\">");
		sb.append("信息系统部");
		sb.append("</div>");
		sb.append("<br");
		sb.append("<div align=\"right\">");
		sb.append(dateString);

		sb.append("</div>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	// 邮件格式2

//	@SuppressWarnings("unused")
//	private String eventEndHtmlContent(UserInfo creator, String url, Event event) {
//
//		StringBuilder sb = new StringBuilder();
////		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String dateString = dateFormat.format(new Date());
//		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		sb.append("<html>");
//		sb.append("<head>");
//		sb.append("<title> 您已经成功提交一个问题，编号为(" + event.getEventCisn()
//				+ "）”我们会尽快解决并向您反馈。谢谢</title>");
//		sb.append("<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
//		sb.append("<meta http-equiv=\"description\" content=\"this is my page\">");
//		sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
//		// sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\"
//		// href=\"./styles.css\">-->");
//		sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
//		// sb.append(" <style type=\"text/css\">");
//		sb.append("<style type=\"text/css\">");
//		sb.append("<!--");
//		// sb.append("<!--");
//		sb.append(".STYLE1 {");
//		sb.append("font-size: 24px;");
//		sb.append("font-weight: bold;");
//		sb.append("}");
//		sb.append(".STYLE2 {");
//		sb.append("font-family: '楷体_GB2312'");
//		sb.append("}");
//		sb.append("	-->");
//		sb.append("</style>");
//		sb.append("</head>");
//		sb.append("<body>");
//		sb.append("<div align=\"center\">");
//		sb.append("<table width=\"1000\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
//		sb.append("<tr>");
//		sb.append("<td height=\"29\" colspan=\"3\" nowrap>");
//		sb.append("<div align=\"center\" class=\"STYLE1\">");
//		sb.append("<div align=\"center\">事件确认通知</div>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>尊敬的" + creator.getRealName() + "/" + creator.getItcode() + "您好:");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("&nbsp; &nbsp; &nbsp; &nbsp; ");
//		sb.append("您提出的" + "<font style='font-weight: bold'>" + "" + event.getSummary() + "</font>（事件编号为" + event.getEventCisn()
//				+ "），现在已经处理完毕，请您审核并确认：" + "<a href=" + url + ">" + "审核链接</a>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; ");
//		sb.append("我们为您编写了该问题的处理文档，以期对您日后的工作中有所帮助！");
//		sb.append("<br>");
//		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; ");
//		sb.append("为了帮助我们提高IT服务质量，请您在确认后抽出20-30秒的时间对我们的IT服务进行满意度评价，谢谢！");
//		sb.append("感谢您使用集团IT服务");
//		sb.append("</td>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("</td>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("<div align=\"right\">");
//		sb.append("信息系统部");
//		sb.append("</div>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<br>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("<div align=\"right\">");
//		sb.append(dateString);
//		sb.append("</div>");
//		sb.append("</td>");
//		sb.append("<tr>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("</tr>");
//		sb.append("</table>");
//		sb.append("</div>");
//		sb.append("</body>");
//		sb.append("</html>");
//		return sb.toString();
//	}
}
