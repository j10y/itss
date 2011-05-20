package com.zsgj.itil.workflow.rules;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.base.JbpmContextFactory;
import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventAuditHis;
import com.zsgj.itil.event.entity.EventStatus;
import com.zsgj.itil.event.entity.EventSulotion;
import com.zsgj.itil.event.service.EventService;

/**
 * 
 * @Class Name ProcessRuleHelper
 * @Author YangTao
 * @Create In Mar 4, 2009
 */
public class EventProcessRuleHelper {
	ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	Service service = (Service) ContextHolder.getBean("baseService");
	private ConfigUnitService cs = (ConfigUnitService) ContextHolder.getBean("configUnitService");
	private EventService eventService = (EventService) ContextHolder.getBean("EventService");
	private MailSenderService ms = (MailSenderService) ContextHolder.getBean("mailSenderService");

	/** **********************************************************问题与事件流程*************************************************************** */
	public void saveEventHis(String nodeId, String nodeName, String processId,
			String result, String comment, Event event) {

		EventAuditHis eah = new EventAuditHis();
		eah.setResultFlag(result);
		eah.setProcessId(Long.valueOf(processId));
		eah.setApprover(UserContext.getUserInfo());
		eah.setComment(comment);
		eah.setApproverDate(Calendar.getInstance().getTime());
		eah.setEvent(event);
		eah.setNodeName(nodeName);
		eah.setNodeId(String.valueOf(nodeId));
		try {
			service.save(eah);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 事件开始节点规则
	 * @Methods Name eventStartFlag
	 * @Create In Jul 23, 2010 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @throws Exception void
	 */
	public void eventStartFlag(String dataId, String nodeId, String nodeName,
			String processId) throws Exception {
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
		Event event = (Event) service.find(Event.class, dataId, true);
		event.setEventStatus(dealingStatus);
//		this.saveEventHis(nodeId, nodeName, processId, "", "", event);
		EventAuditHis eah = new EventAuditHis();
		eah.setProcessId(Long.valueOf(processId));
		if(event.getSubmitUser()!=null){
			eah.setApprover(event.getSubmitUser());
		}
		eah.setApproverDate(Calendar.getInstance().getTime());
		eah.setEvent(event);
		eah.setNodeName(nodeName);
		eah.setNodeId(String.valueOf(nodeId));
		service.save(eah);
		
		service.save(event);
//		ms.sendSimplyMail(UserContext.getUserInfo().getEmail(), null, null,
//		"事件提交", "你刚提交了一个事件");eventHtmlContent
	   //2010-05-05 modified by huzh for 效率优化 begin
		//ms.sendMimeMail(event.getSubmitUser().getEmail(), null, null, "您已经成功提交一个问题，编号为（"+event.getEventCisn()+"）”我们会尽快解决并向您反馈。谢谢！", this.eventHtmlContent(event.getSubmitUser(), "", event), null);
		SendMailThred smthread =new SendMailThred(ms,event.getSubmitUser().getEmail(), null, null, "IT温馨提示:"+event.getSubmitUser().getRealName()+"/"+event.getSubmitUser().getUserName()+"成功提交了一个事件（事件编号："+event.getEventCisn()+",事件名称："+event.getSummary()+"），我们会尽快解决并向您反馈。谢谢！", this.eventHtmlContent(event.getSubmitUser(), "", event), null);
		 Thread st = new Thread(smthread);
	          st.start();
	   //2010-05-05 modified by huzh for 效率优化 end
	}
	
	/**
	 * 工程师处理节点规则
	 * @Methods Name engineerProcessFlag
	 * @Create In Jul 23, 2010 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return
	 * @throws Exception String
	 */
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
			//add by guoxl in 2009/09/11 begin 
			event.setPraCloseDate(new Date());
			//add by guoxl in 2009/09/11 end
			event.setEventStatus(userconfirmStatus);
//			event.setDealuser(event.getSubmitUser());
		} else {
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		
		//2010-08-11 add by huzh for 添加2个工作日后用户未确认事件自动解决的规则后，用户确认邮件格式修改后再此处发送 begin
		if(result.equals("touserconfirm")){//审批历史保存后再发送邮件
//			Long taskId=eventService.findTaskId(dataId,nodeId,processId);
			String subject="IT温馨提示:您提交的"+event.getSummary()+"（事件编号为"+event.getEventCisn()+"）已处理完毕,请确认并反馈满意度。谢谢!";
			String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") + "/user/event/transactionFlow/userconfirm.jsp?dataId="+dataId+"&processId="+processId;
			SendMailThred mthread =new SendMailThred(ms,event.getSubmitUser().getEmail(), "", null, subject, this.eventNoSatHtmlContent(event.getCreateUser(), url, event,processId), null);
			Thread std = new Thread(mthread);
		    std.start();
		}
		//2010-08-11 add by huzh for 添加2个工作日后用户未确认事件自动解决的规则后，用户确认邮件格式修改后再此处发送 begin
		// result替换next
		return result;
	}

	/**
	 * 支持组长处理规则
	 * @Methods Name headerProcessFlag
	 * @Create In Jul 23, 2010 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return
	 * @throws Exception String
	 */
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

	/**
	 * 其他工程师处理规则
	 * @Methods Name otherOrgProcessFlag
	 * @Create In Jul 23, 2010 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return
	 * @throws Exception String
	 */
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
		//event.setEventStatus(dealingStatus); //2010-06-01 deleted by huzh 
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		
		//2010-08-11 add by huzh for 添加2个工作日后用户未确认事件自动解决的规则后，用户确认邮件格式修改后再此处发送 begin
		if(result.equals("touserconfirm")){//审批历史保存后在发送邮件
//			Long taskId=eventService.findTaskId(dataId,nodeId,processId);
			String subject="IT温馨提示:您提交的"+event.getSummary()+"（事件编号为"+event.getEventCisn()+"）已处理完毕，请确认并反馈满意度。谢谢！";
			String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") + "/user/event/transactionFlow/userconfirm.jsp?dataId="+dataId+"&processId="+processId;
			SendMailThred mthread =new SendMailThred(ms,event.getSubmitUser().getEmail(), "", null, subject, this.eventNoSatHtmlContent(event.getCreateUser(), url, event,processId), null);
			Thread std = new Thread(mthread);
		    std.start();
		}
		//2010-08-11 add by huzh for 添加2个工作日后用户未确认事件自动解决的规则后，用户确认邮件格式修改后再此处发送 begin
		// result替换next
		return result;
	}

	/**
	 * 用户确认阶段规则
	 * @Methods Name userConfirmFlag
	 * @Create In Jul 23, 2010 By lee
	 * @param dataId
	 * @param nodeId
	 * @param nodeName
	 * @param processId
	 * @param result
	 * @param comment
	 * @return
	 * @throws Exception String
	 */
	public String userConfirmFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment)
			throws Exception {
		Event event = (Event) service.find(Event.class, dataId,true);
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
		EventStatus endStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "finish");
		if (result.equals("over")) {
			event.setClosedDate(new Date());
			event.setEventStatus(endStatus);
		//add by lee for timer in 20100723 begin
		//如果超过两个工作日仍未确认，则系统自动处理，默认为确认,schedule是固定Timer的标记
		}else if("schedule".equals(result)){
			event.setClosedDate(new Date());
			event.setType(Integer.valueOf(1));
			event.setEventStatus(endStatus);
			result="over";
			comment="确认超时，系统自动关闭！";
		//add by lee for timer in 20100723 end
		} else {
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, comment, event);
		service.save(event);
		// result替换next
		return result;
	}
	/**
	 * 事件结束节点，主要是为了在本事件结束时结束他的子事件或同一事件
	 * @Methods Name endFlag
	 * @Create In Sep 7, 2009 By guoxl void
	 */
	public void endFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment){
		Event event = (Event) service.find(Event.class, dataId,true);
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		EventStatus userconfirmStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "userconfirm");
		List<Event> eventFirst = eventService.findSameAndChild(event);
		List<Event> eventList = new ArrayList<Event>();
		if(eventFirst.size()>0){
			eventList.addAll(eventFirst);
		}
		if (eventList.size() > 0) {// 如果存在子事件
			for (Event childEvent : eventList) {
				if (!"finish".equals(childEvent.getEventStatus()
						.getKeyword())) {// 是审批中的
					EventAuditHis eventAuditHis = (EventAuditHis) service
							.find(EventAuditHis.class, "event", childEvent)
							.get(0);
					Long instanceId = eventAuditHis.getProcessId();
					if (instanceId != null) {
						childEvent.setEventStatus(userconfirmStatus);// 首先将子事件设置为用户确认状态
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
							eventS.setCreatName(parentEventSulotion.getCreatName());
							eventS.setCreateDate(new Date());
							service.save(eventS);
						}
		/*********************add by guangsa in 2009/09/07 begin ***********************************************************/
						
						JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
						ProcessInstance processInstance = jbpmContext.loadProcessInstance(instanceId);
						//add by guangsa for 自由流的回转 in 20090910 begin
						WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordByProcessId(instanceId);
						Long childTaskId = workflowRecordTaskInfo.getTaskId();//子事件任务ID
						String childNodeDesc = workflowRecordTaskInfo.getNodeDesc();//子事件当前节点描述

						if(!"userConfirm".equals(childNodeDesc)){//如果当前节点不是用户确认节点话
							TaskInstance taskInstance = jbpmContext.getTaskInstance(childTaskId);
							taskInstance.setSignalling(false);
							taskInstance.end();
							Token childToken = taskInstance.getToken();
							service.remove(workflowRecordTaskInfo);

							service.save(childEvent);
							//add by guangsa for 子事件用户确认流程审批人 in 20090911 end
							Node node = processInstance.getProcessDefinition().getNode("用户确认");
							ExecutionContext ec = new ExecutionContext(childToken); 
							node.enter(ec);
						}else{
							//如果是用户确认节点话，什么操作都不做
						}
						//add by guangsa for 自由流的回转 in 20090910 end
						
						jbpmContext.save(processInstance);
		/************************************add by guangsa in 2009/09/07 end********************************************/
						
						// 结束事件
					}
				}
			}

		}
	}

	private String eventHtmlContent(UserInfo creator, String url,Event event) {
		StringBuilder sb = new StringBuilder();
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
		sb.append("font-family:'楷体';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");
        sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>邮件通知</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">尊敬的"
				+ creator.getRealName() + "/" + creator.getUserName()
				+ "，您好:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">您成功提交了一个事件（事件编号：<font style='font-weight: bold'>"+event.getEventCisn()+"</font>，事件名称：<font style='font-weight: bold'>"+event.getSummary()+"</font>），我们会安排专人进行受理，在处理后会第一时间向您反馈。谢谢！</div></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:楷体\">");
		sb.append("<br>感谢您使用集团IT服务，如果您对我们有任何意见和建议，可以发送邮件到it-manage@zsgj.com，或者拨打IT服务建议及投诉热线7888-0。"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>信息系统部");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>本邮件由集团IT服务系统（ITSS）自动发送，请勿直接回复。");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}	
	
	private String eventNoSatHtmlContent(UserInfo creator, String url,Event event,String processId) {
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
		sb.append("font-family:'楷体';");
		sb.append("font-size: 14px;");
		sb.append("}");
		sb.append("-->");
		sb.append("</style>");
		sb.append("	</head>");
        sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"900\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\"><h3>邮件通知</h3></div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("                <td class=\"STYLE1\">尊敬的"
				+ creator.getRealName() + "/" + creator.getUserName()
				+ "，您好：</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("您提交的事件（"+"事件名称：<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"，事件编号：<font style='font-weight: bold'>"+event.getEventCisn()+"</font>），现在已经处理完毕，请您在2个工作日内确认处理结果，超过2个工作日后系统会自动默认解决并关闭。");
		sb.append("<br>同时我们为您编写了该问题的处理文档，以期对您日后的工作中有所帮助！您可以" + "<a href=" + url + ">"
				+ "点击此链接</a>" + "访问。");
		sb.append("<br>为了帮助我们提高IT服务质量，请您在确认后抽出20-30秒的时间对我们的IT服务进行满意度评价，谢谢！");
		sb.append("</td>");
		sb.append("</tr>");
		
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>过程：</div></td>");
    	sb.append("				 </tr>");
    	List<EventAuditHis> historyList=eventService.findAllEventHistory(event,processId);
		if(historyList!=null&&!"".equals(historyList)){
			for (int i=0;i<historyList.size();i++) {
				EventAuditHis his=historyList.get(i);
				String nodeMeg = his.getNodeName();
				UserInfo approver = his.getApprover();
				Date approverDate = his.getApproverDate();
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg="";
				if(approver!=null){
					String userName = approver.getRealName();
					String resultFlag = his.getResultFlag();
					if(resultFlag!=null&&"unconfirm".equals(resultFlag)==true){
						auditMeg= nodeMeg+"环节"+" "+userName+" "+timeString+" "+"打回；";
					}else{
						auditMeg= nodeMeg+"环节"+" "+userName+" "+timeString+" "+"审批通过；";
					}
			        sb.append("				 <tr>");
		        	sb.append("					<td><div class=\"STYLE1\" align=\"left\">" + auditMeg + "</div></td>");
		        	sb.append("				 </tr>");
				}
	        }
		}
		
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:楷体\">");
		sb.append("<br>感谢您使用集团IT服务，如果您对我们有任何意见和建议，可以发送邮件到it-manage@zsgj.com，或者拨打IT服务建议及投诉热线7888-0。"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>信息系统部");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append(dateString);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>本邮件由集团IT服务系统（ITSS）自动发送，请勿直接回复。");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}
//	
//	//邮件格式2
//	private String eventEndHtmlContent(UserInfo creator, String url,Event event) {
//
//		StringBuilder sb = new StringBuilder();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String dateString = dateFormat.format(new Date());
//		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		sb.append("<html>");
//		sb.append("<head>");
//		sb.append("<title> 您已经成功提交一个问题，编号为("+event.getEventCisn()+"）”我们会尽快解决并向您反馈。谢谢</title>");
//		sb.append("<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
//		sb.append("<meta http-equiv=\"description\" content=\"this is my page\">");
//		sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
//		// sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\"
//		// href=\"./styles.css\">-->");
//		sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
//		// sb.append(" <style type=\"text/css\">");
//		sb.append("<style type=\"text/css\">");
//		sb.append("<!--");
//      //sb.append("<!--");
//		sb.append(".STYLE1 {");
//		sb.append("font-size: 20px;");
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
//		sb.append("<div align=\"center\">邮件通知</div>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>尊敬的"+creator.getRealName()+"/"+creator.getItcode()+"您好:");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("&nbsp; &nbsp; &nbsp; &nbsp; ");
//		sb.append("您提交的事件（事件名称："+"<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"，事件编号：<font style='font-weight: bold'>"+event.getEventCisn()+"</font>），现在已经处理完毕，请您审核并确认："+"<a href=" + url + ">"
//		+ "审核链接</a>。");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td style=\"font-family:楷体\">");
//		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; "); 
//		sb.append("我们为您编写了该问题的处理文档，以期对您日后的工作中有所帮助！");
//		sb.append("<br>");
//		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; "); 
//		sb.append("为了帮助我们提高IT服务质量，请您在确认后抽出20-30秒的时间对我们的IT服务进行满意度评价，谢谢！"); 
//        sb.append("感谢您使用集团IT服务，如果您对我们有任何意见和建议可以发送邮件到it-manage@zsgj.com，或者拨打IT服务建议及投诉热线7888-0。");
//        sb.append("</td>");	
//        sb.append("<tr>");
//        sb.append("<td>");
//        sb.append("</td>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" align=\"right\">");
//		sb.append("<br>信息系统部");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" align=\"right\">");
//		sb.append(dateString);
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
//		sb.append("<br>本邮件由集团IT服务系统（ITSS）自动发送，请勿直接回复。");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("			</table>");
//		sb.append("		</div>");
//		sb.append("	</body>");
//		sb.append("</html>");
//		return sb.toString();
//	}
	 //2010-05-05 modified by huzh for 效率优化 begin
	class SendMailThred implements Runnable{
		private MailSenderService mss;
		private String to;
		private String cc;
		private String bcc;
		private String subject;
		private String context;
		private String filePath;
		public SendMailThred() {
		}
		
		public SendMailThred(MailSenderService mss, String to, String cc,
				String bcc, String subject, String context, String filePath) {
			this.mss = mss;
			this.to = to;
			this.cc = cc;
			this.bcc = bcc;
			this.subject = subject;
			this.context = context;
			this.filePath = filePath;
		}

		public void run(){
			mss.sendMimeMail(to, cc, bcc, subject, context, filePath);
		}
	} 
	 //2010-05-05 modified by huzh for 效率优化 end
}

	

