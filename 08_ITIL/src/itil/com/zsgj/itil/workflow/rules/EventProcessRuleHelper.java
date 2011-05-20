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

	/** **********************************************************�������¼�����*************************************************************** */
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
	 * �¼���ʼ�ڵ����
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
//		"�¼��ύ", "����ύ��һ���¼�");eventHtmlContent
	   //2010-05-05 modified by huzh for Ч���Ż� begin
		//ms.sendMimeMail(event.getSubmitUser().getEmail(), null, null, "���Ѿ��ɹ��ύһ�����⣬���Ϊ��"+event.getEventCisn()+"�������ǻᾡ����������������лл��", this.eventHtmlContent(event.getSubmitUser(), "", event), null);
		SendMailThred smthread =new SendMailThred(ms,event.getSubmitUser().getEmail(), null, null, "IT��ܰ��ʾ:"+event.getSubmitUser().getRealName()+"/"+event.getSubmitUser().getUserName()+"�ɹ��ύ��һ���¼����¼���ţ�"+event.getEventCisn()+",�¼����ƣ�"+event.getSummary()+"�������ǻᾡ����������������лл��", this.eventHtmlContent(event.getSubmitUser(), "", event), null);
		 Thread st = new Thread(smthread);
	          st.start();
	   //2010-05-05 modified by huzh for Ч���Ż� end
	}
	
	/**
	 * ����ʦ����ڵ����
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
		// ����Event״̬
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
		
		//2010-08-11 add by huzh for ���2�������պ��û�δȷ���¼��Զ�����Ĺ�����û�ȷ���ʼ���ʽ�޸ĺ��ٴ˴����� begin
		if(result.equals("touserconfirm")){//������ʷ������ٷ����ʼ�
//			Long taskId=eventService.findTaskId(dataId,nodeId,processId);
			String subject="IT��ܰ��ʾ:���ύ��"+event.getSummary()+"���¼����Ϊ"+event.getEventCisn()+"���Ѵ������,��ȷ�ϲ���������ȡ�лл!";
			String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") + "/user/event/transactionFlow/userconfirm.jsp?dataId="+dataId+"&processId="+processId;
			SendMailThred mthread =new SendMailThred(ms,event.getSubmitUser().getEmail(), "", null, subject, this.eventNoSatHtmlContent(event.getCreateUser(), url, event,processId), null);
			Thread std = new Thread(mthread);
		    std.start();
		}
		//2010-08-11 add by huzh for ���2�������պ��û�δȷ���¼��Զ�����Ĺ�����û�ȷ���ʼ���ʽ�޸ĺ��ٴ˴����� begin
		// result�滻next
		return result;
	}

	/**
	 * ֧���鳤�������
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
		// ����Event״̬
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
		if (result.equals("reAssign")) {
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		// result�滻next
		return result;
	}

	/**
	 * ��������ʦ�������
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
		// ����Event״̬
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
		
		//2010-08-11 add by huzh for ���2�������պ��û�δȷ���¼��Զ�����Ĺ�����û�ȷ���ʼ���ʽ�޸ĺ��ٴ˴����� begin
		if(result.equals("touserconfirm")){//������ʷ������ڷ����ʼ�
//			Long taskId=eventService.findTaskId(dataId,nodeId,processId);
			String subject="IT��ܰ��ʾ:���ύ��"+event.getSummary()+"���¼����Ϊ"+event.getEventCisn()+"���Ѵ�����ϣ���ȷ�ϲ���������ȡ�лл��";
			String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") + "/user/event/transactionFlow/userconfirm.jsp?dataId="+dataId+"&processId="+processId;
			SendMailThred mthread =new SendMailThred(ms,event.getSubmitUser().getEmail(), "", null, subject, this.eventNoSatHtmlContent(event.getCreateUser(), url, event,processId), null);
			Thread std = new Thread(mthread);
		    std.start();
		}
		//2010-08-11 add by huzh for ���2�������պ��û�δȷ���¼��Զ�����Ĺ�����û�ȷ���ʼ���ʽ�޸ĺ��ٴ˴����� begin
		// result�滻next
		return result;
	}

	/**
	 * �û�ȷ�Ͻ׶ι���
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
		//�������������������δȷ�ϣ���ϵͳ�Զ�����Ĭ��Ϊȷ��,schedule�ǹ̶�Timer�ı��
		}else if("schedule".equals(result)){
			event.setClosedDate(new Date());
			event.setType(Integer.valueOf(1));
			event.setEventStatus(endStatus);
			result="over";
			comment="ȷ�ϳ�ʱ��ϵͳ�Զ��رգ�";
		//add by lee for timer in 20100723 end
		} else {
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, comment, event);
		service.save(event);
		// result�滻next
		return result;
	}
	/**
	 * �¼������ڵ㣬��Ҫ��Ϊ���ڱ��¼�����ʱ�����������¼���ͬһ�¼�
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
		if (eventList.size() > 0) {// ����������¼�
			for (Event childEvent : eventList) {
				if (!"finish".equals(childEvent.getEventStatus()
						.getKeyword())) {// �������е�
					EventAuditHis eventAuditHis = (EventAuditHis) service
							.find(EventAuditHis.class, "event", childEvent)
							.get(0);
					Long instanceId = eventAuditHis.getProcessId();
					if (instanceId != null) {
						childEvent.setEventStatus(userconfirmStatus);// ���Ƚ����¼�����Ϊ�û�ȷ��״̬
						EventSulotion eventSulotion = (EventSulotion) service
								.findUnique(EventSulotion.class, "event",
										childEvent);
						if (eventSulotion == null) {// ������¼�û�н������,�����¼��Ľ�������������¼�
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
						//add by guangsa for �������Ļ�ת in 20090910 begin
						WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordByProcessId(instanceId);
						Long childTaskId = workflowRecordTaskInfo.getTaskId();//���¼�����ID
						String childNodeDesc = workflowRecordTaskInfo.getNodeDesc();//���¼���ǰ�ڵ�����

						if(!"userConfirm".equals(childNodeDesc)){//�����ǰ�ڵ㲻���û�ȷ�Ͻڵ㻰
							TaskInstance taskInstance = jbpmContext.getTaskInstance(childTaskId);
							taskInstance.setSignalling(false);
							taskInstance.end();
							Token childToken = taskInstance.getToken();
							service.remove(workflowRecordTaskInfo);

							service.save(childEvent);
							//add by guangsa for ���¼��û�ȷ������������ in 20090911 end
							Node node = processInstance.getProcessDefinition().getNode("�û�ȷ��");
							ExecutionContext ec = new ExecutionContext(childToken); 
							node.enter(ec);
						}else{
							//������û�ȷ�Ͻڵ㻰��ʲô����������
						}
						//add by guangsa for �������Ļ�ת in 20090910 end
						
						jbpmContext.save(processInstance);
		/************************************add by guangsa in 2009/09/07 end********************************************/
						
						// �����¼�
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
		sb.append("<div align=\"left\">���ɹ��ύ��һ���¼����¼���ţ�<font style='font-weight: bold'>"+event.getEventCisn()+"</font>���¼����ƣ�<font style='font-weight: bold'>"+event.getSummary()+"</font>�������ǻᰲ��ר�˽��������ڴ������һʱ������������лл��</div></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT������������������κ�����ͽ��飬���Է����ʼ���it-manage@zsgj.com�����߲���IT�����鼰Ͷ������7888-0��"); 
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
				+ "�����ã�</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("���ύ���¼���"+"�¼����ƣ�<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"���¼���ţ�<font style='font-weight: bold'>"+event.getEventCisn()+"</font>���������Ѿ�������ϣ�������2����������ȷ�ϴ�����������2�������պ�ϵͳ���Զ�Ĭ�Ͻ�����رա�");
		sb.append("<br>ͬʱ����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ���������������������" + "<a href=" + url + ">"
				+ "���������</a>" + "���ʡ�");
		sb.append("<br>Ϊ�˰����������IT����������������ȷ�Ϻ���20-30���ʱ������ǵ�IT���������������ۣ�лл��");
		sb.append("</td>");
		sb.append("</tr>");
		
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>���̣�</div></td>");
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
						auditMeg= nodeMeg+"����"+" "+userName+" "+timeString+" "+"��أ�";
					}else{
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
		sb.append("<br>��л��ʹ�ü���IT������������������κ�����ͽ��飬���Է����ʼ���it-manage@zsgj.com�����߲���IT�����鼰Ͷ������7888-0��"); 
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
//	
//	//�ʼ���ʽ2
//	private String eventEndHtmlContent(UserInfo creator, String url,Event event) {
//
//		StringBuilder sb = new StringBuilder();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String dateString = dateFormat.format(new Date());
//		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		sb.append("<html>");
//		sb.append("<head>");
//		sb.append("<title> ���Ѿ��ɹ��ύһ�����⣬���Ϊ("+event.getEventCisn()+"�������ǻᾡ����������������лл</title>");
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
//		sb.append("font-family: '����_GB2312'");
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
//		sb.append("<div align=\"center\">�ʼ�֪ͨ</div>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>�𾴵�"+creator.getRealName()+"/"+creator.getItcode()+"����:");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("&nbsp; &nbsp; &nbsp; &nbsp; ");
//		sb.append("���ύ���¼����¼����ƣ�"+"<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"���¼���ţ�<font style='font-weight: bold'>"+event.getEventCisn()+"</font>���������Ѿ�������ϣ�������˲�ȷ�ϣ�"+"<a href=" + url + ">"
//		+ "�������</a>��");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td style=\"font-family:����\">");
//		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; "); 
//		sb.append("����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ���������������");
//		sb.append("<br>");
//		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; "); 
//		sb.append("Ϊ�˰����������IT����������������ȷ�Ϻ���20-30���ʱ������ǵ�IT���������������ۣ�лл��"); 
//        sb.append("��л��ʹ�ü���IT������������������κ�����ͽ�����Է����ʼ���it-manage@zsgj.com�����߲���IT�����鼰Ͷ������7888-0��");
//        sb.append("</td>");	
//        sb.append("<tr>");
//        sb.append("<td>");
//        sb.append("</td>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" align=\"right\">");
//		sb.append("<br>��Ϣϵͳ��");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" align=\"right\">");
//		sb.append(dateString);
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
//		sb.append("<br>���ʼ��ɼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("			</table>");
//		sb.append("		</div>");
//		sb.append("	</body>");
//		sb.append("</html>");
//		return sb.toString();
//	}
	 //2010-05-05 modified by huzh for Ч���Ż� begin
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
	 //2010-05-05 modified by huzh for Ч���Ż� end
}

	

