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

	/** **********************************************************�������¼�����*************************************************************** */
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
		// "�¼��ύ", "����ύ��һ���¼�");eventHtmlContent
		ms.sendMimeMail(UserContext.getUserInfo().getEmail(), null, null,
				"���Ѿ��ɹ��ύһ�����⣬���Ϊ��" + event.getEventCisn()
						+ "�������ǻᾡ����������������лл��", this.eventHtmlContent(
						UserContext.getUserInfo(), "", event), null);
	}

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
			event.setEventStatus(userconfirmStatus);
		} else {
			event.setEventStatus(dealingStatus);
		}
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		// result�滻next
		return result;
	}

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
		event.setEventStatus(dealingStatus);
		this.saveEventHis(nodeId, nodeName, processId, result, "", event);
		service.save(event);
		// result�滻next
		return result;
	}

	public String userConfirmFlag(String dataId, String nodeId,
			String nodeName, String processId, String result, String comment)
			throws Exception {
		Event event = (Event) service.find(Event.class, dataId);
		// ����Event״̬
		EventStatus dealingStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "dealing");
//		EventStatus reAssignStatus = (EventStatus) service.findUnique(
//				EventStatus.class, "keyword", "confirm");
		EventStatus endStatus = (EventStatus) service.findUnique(
				EventStatus.class, "keyword", "finish");
//		EventStatus userconfirmStatus = (EventStatus) service.findUnique(
//				EventStatus.class, "keyword", "userconfirm");
//		String url = PropertiesUtil.getProperties("cc.web.endUrl");// ��ȡ�ʼ�ҳ��
//		String rootPath = PropertiesUtil.getProperties("system.web.url");// ��ȡ��Ŀ��·��
//		String realUrl = rootPath + url + "?dataId=" + dataId + "&isExist=s";
		if (result.equals("over")) {
			event.setEventStatus(endStatus);
			// delete by guoxl in 2009/09/02 begin �˴������ʼ��ˣ��Ѿ��ڹ���ʦ������Ϻ󷢹��ʼ���
			// ms.sendMimeMail(UserContext.getUserInfo().getEmail(), null, null,
			// "���ύ��"+event.getEventName()+"���¼����Ϊ"+event.getEventCisn()+"���Ѵ�����ϣ���ȷ�ϲ���������ȡ�лл��",this.eventEndHtmlContent(UserContext.getUserInfo(),
			// "", event), null);
			// delete by guoxl in 2009/09/02 end

			// ------------------add by guoxl in 2009/08/13 begin
			// ���ҽ������¼��Ƿ���û�н��������¼����еĻ��ͽ���
			List<EventRelation> EventRelationList = service.find(
					EventRelation.class, "parentEvent", event);
			if (EventRelationList.size() > 0) {// ����������¼�
				for (EventRelation eventRel : EventRelationList) {
					Event childEvent = eventRel.getEvent();
					if (!"finish".equals(childEvent.getEventStatus()
							.getKeyword())) {// �������е�
						EventAuditHis eventAuditHis = (EventAuditHis) service
								.find(EventAuditHis.class, "event", childEvent)
								.get(0);
						Long instanceId = eventAuditHis.getProcessId();
						if (instanceId != null) {
							childEvent.setEventStatus(endStatus);// ���Ƚ����¼�����Ϊ����״̬
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
								eventS.setCreatName(UserContext.getUserInfo());
								eventS.setCreateDate(new Date());
								service.save(eventS);
							}
							ps.endProcess(instanceId);// �������¼�������

							// �����¼�
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
		// result�滻next
		return result;
	}

	// ****************************************************֪ʶ����*******************************************
	public void saveKnowledgeHis(BaseObject baseObject, String nodeId,
			String nodeName, String processId, String result, String comment) {
		if (baseObject instanceof KnowFile) {// �ļ�����
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
		} else if (baseObject instanceof Knowledge) {// �����������
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
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
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
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// �ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this
					.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
							"", "");
			knowFile.setStatus(new Integer("2"));// 2Ϊ�ύ������
			service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// �����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			knowledge.setStatus(new Integer("2"));// 2Ϊ�ύ������
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId, "",
					"");
			service.save(knowledge);
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					"", "");
			knowContract.setStatus(new Integer("2"));// 2Ϊ�ύ������
			service.save(knowContract);
		}
	}

	public String judgeTypeFlag(String dataId, String dataType, String nodeId,
			String nodeName, String processId, String result, String comment) {
		KnowledgeType knowledgeType = (KnowledgeType) service.find(
				KnowledgeType.class, dataType, true);
		String classNameString = knowledgeType.getClassName();
		if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// �ļ�����
			result = "tofa";
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// �����������
			result = "tosa";
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
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
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// �ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			if (result.equals("Y")) {// ͬ��
				knowFile.setStatus(new Integer("2"));
			} else {// ��ͬ�⣬�ݸ�
				knowFile.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// �����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
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
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// �ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// �����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			if (result.equals("Y")) {// ͬ��
				knowledge.setStatus(new Integer("1"));
			} else {// ��ͬ�⣬�ݸ�
				knowledge.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			service.save(knowledge);
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
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
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// �ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// �����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			if (result.equals("Y")) {// ͬ��
				knowContract.setStatus(new Integer("2"));
			} else {// ��ͬ�⣬�ݸ�
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
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// �ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			if (result.equals("Y")) {// ͬ��
				knowFile.setStatus(new Integer("1"));
			} else {// ��ͬ�⣬�ݸ�
				knowFile.setStatus(new Integer("0"));
			}
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// �����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
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
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// �ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// �����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			// service.save(knowledge);
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			if (result.equals("Y")) {// ͬ��
				knowContract.setStatus(new Integer("1"));
			} else {// ��ͬ�⣬�ݸ�
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
				.endsWith("com.zsgj.itil.knowledge.entity.KnowFile")) {// �ļ�����
			KnowFile knowFile = (KnowFile) service.find(KnowFile.class, dataId,
					true);
			this.saveKnowledgeHis(knowFile, nodeId, nodeName, processId,
					result, comment);
			knowFile.setStatus(new Integer("0"));
			service.save(knowFile);
		} else if (classNameString
				.endsWith("com.zsgj.itil.knowledge.entity.Knowledge")) {// �����������
			Knowledge knowledge = (Knowledge) service.find(Knowledge.class,
					dataId, true);
			knowledge.setStatus(new Integer("0"));
			this.saveKnowledgeHis(knowledge, nodeId, nodeName, processId,
					result, comment);
			service.save(knowledge);
		} else {// ��ͬ����com.zsgj.itil.knowledge.entity.KnowContract
			KnowContract knowContract = (KnowContract) service.find(
					KnowContract.class, dataId, true);
			knowContract.setStatus(new Integer("0"));
			this.saveKnowledgeHis(knowContract, nodeId, nodeName, processId,
					result, comment);
			service.save(knowContract);
		}
		return result;
	}

	// �ʼ���ʽ1EventHtmlContent()��
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
		sb.append("<title> ���Ѿ��ɹ��ύһ�����⣬���Ϊ(" + event.getEventCisn()
				+ "�������ǻᾡ����������������лл</title>");
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
		sb.append("font-family: '����_GB2312'");
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
		sb.append("<div align=\"center\">�¼�ȷ��֪ͨ</div>");
		sb.append("</div>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>�𾴵�" + creator.getRealName() + "/" + creator.getItcode()
				+ "����:");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("&nbsp; &nbsp; &nbsp; &nbsp; ���Ѿ��ɹ��ύ"
				+ "<font style='font-weight: bold'>" + event.getSummary()
				+ "</font>���¼����Ϊ" + event.getEventCisn()
				+ "�������ǻᰲ��ר�˽��������ڴ������һʱ������������");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE2\">");
		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; ");

		sb
				.append("��л��ʹ�ü���IT����");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<div align=\"right\">");
		sb.append("��Ϣϵͳ��");
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

	// �ʼ���ʽ2

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
//		sb.append("<title> ���Ѿ��ɹ��ύһ�����⣬���Ϊ(" + event.getEventCisn()
//				+ "�������ǻᾡ����������������лл</title>");
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
//		sb.append("<div align=\"center\">�¼�ȷ��֪ͨ</div>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>�𾴵�" + creator.getRealName() + "/" + creator.getItcode() + "����:");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("&nbsp; &nbsp; &nbsp; &nbsp; ");
//		sb.append("�������" + "<font style='font-weight: bold'>" + "" + event.getSummary() + "</font>���¼����Ϊ" + event.getEventCisn()
//				+ "���������Ѿ�������ϣ�������˲�ȷ�ϣ�" + "<a href=" + url + ">" + "�������</a>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; ");
//		sb.append("����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ���������������");
//		sb.append("<br>");
//		sb.append(" &nbsp; &nbsp; &nbsp; &nbsp; ");
//		sb.append("Ϊ�˰����������IT����������������ȷ�Ϻ���20-30���ʱ������ǵ�IT���������������ۣ�лл��");
//		sb.append("��л��ʹ�ü���IT����");
//		sb.append("</td>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("</td>");
//		sb.append("<tr>");
//		sb.append("<td>");
//		sb.append("<div align=\"right\">");
//		sb.append("��Ϣϵͳ��");
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
