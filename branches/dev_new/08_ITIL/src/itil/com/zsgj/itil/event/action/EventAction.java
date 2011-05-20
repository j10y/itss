package com.zsgj.itil.event.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.extjs.servlet.CoderForList;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemFile;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.entity.UserRole;
import com.zsgj.info.framework.security.service.UserInfoService;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.event.entity.CCCallInfo;
import com.zsgj.itil.event.entity.Event;
import com.zsgj.itil.event.entity.EventAssign;
import com.zsgj.itil.event.entity.EventDealType;
import com.zsgj.itil.event.entity.EventFrequency;
import com.zsgj.itil.event.entity.EventPonderance;
import com.zsgj.itil.event.entity.EventProblem;
import com.zsgj.itil.event.entity.EventRelation;
import com.zsgj.itil.event.entity.EventRelationType;
import com.zsgj.itil.event.entity.EventStatus;
import com.zsgj.itil.event.entity.EventSulotion;
import com.zsgj.itil.event.entity.EventType;
import com.zsgj.itil.event.entity.EventTypeServiceItem;
import com.zsgj.itil.event.entity.Problem;
import com.zsgj.itil.event.entity.ProblemSort;
import com.zsgj.itil.event.service.EventService;
import com.zsgj.itil.event.service.SupportGroupService;
import com.zsgj.itil.knowledge.entity.KnowProblemType;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.knowledge.entity.KnowledgeType;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.train.entity.Quest;
import com.zsgj.itil.train.entity.QuestOption;
import com.zsgj.itil.train.entity.QuestResult;
import com.zsgj.itil.train.entity.Survey;
import com.zsgj.itil.train.service.QuestService;
import com.zsgj.itil.train.service.TrainPlanService;
public class EventAction extends BaseAction {
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private EventService eventService = (EventService) getBean("EventService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private UserInfoService userInfoService = (UserInfoService) ContextHolder.getBean("userInfoService");
	private TrainPlanService trainPlanService = (TrainPlanService) getBean("trainPlanService");
	private QuestService questService = (QuestService) getBean("questService");
	//2010-06-29 add by huzh begin
	private MailSenderService ms = (MailSenderService) ContextHolder.getBean("mailSenderService");
	//2010-06-29 add by huzh end
	private SupportGroupService supportGroupService;//ע���

	/**
	 * ��ת���¼�����ҳ�棨�����Ƿ���֧���鹤��ʦ����ҳ���ǣ�
	 * 
	 * @Methods Name toCreatePage
	 * @Create In Oct 13, 2009 By lee
	 * @return String
	 */
	public String toCreatePage() {
		HttpServletRequest request = getRequest();
		UserInfo userInfo = UserContext.getUserInfo();
		List<SupportGroupEngineer> list = getService().find(SupportGroupEngineer.class, "userInfo", userInfo);
		if (!list.isEmpty()) {
			request.setAttribute("isSupportEngineer", "yes");
		} else {
			request.setAttribute("isSupportEngineer", "no");
		}
		return "createPage";
	}

	/**
	 * �����¼�״̬
	 * 
	 * @Methods Name findEvents
	 * @Create In Apr 10, 2009 By daijf
	 * @return
	 * @throws IOException
	 */
	public String findEvents() throws IOException {
		HttpServletRequest request = super.getRequest();
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", true);
		// modify by lee for ����δ��ɵ��¼� begin
	
		String dataId = HttpUtil.getString(request, "id");
		Page page = null;
		if (StringUtils.isNotBlank(dataId)) {
			Map requestParams = HttpUtil.requestParam2Map(super.getRequest());
			page = metaDataManager.query(Event.class, requestParams, pageNo, pageSize, orderBy, isAsc);
		} else {
			String summary = HttpUtil.getString(request, "summary");
			page = eventService.getUnFinishEvents(summary, pageNo, pageSize);
		}
		// modify by lee for ����δ��ɵ��¼� begin
		Long total = page.getTotalCount();
		List queryList = page.list();
		List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(Event.class, queryList);
		List<UserTableSetting> userVisibleColumns = metaDataManager.getUserColumnForList(Event.class);
		String json = CoderForList.encode(userVisibleColumns, listData, total);
		super.getResponse().setContentType("text/plain");
		super.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * �����¼���ϵ��
	 * 
	 * @Methods Name createEventRelation
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String createEventRelation() throws IOException {
		HttpServletRequest httpServletRequest = super.getRequest();
		String relId = httpServletRequest.getParameter("curRelId");// ��ϵid,���ж������������޸�
		Long currentEventId = Long.parseLong(httpServletRequest.getParameter("curEventId"));// ��ǰ�¼�id
		Long parentEventId = Long.parseLong(httpServletRequest.getParameter("parentEventId"));// �����¼�id
		Long eventRelationTypeId = Long.parseLong(httpServletRequest.getParameter("eventRelationTypeId"));// �µ��¼���ϵid
		String json = "";
		
		if (!"".equals(relId)) {// �޸�
			eventService.modifyEventRelation(relId, currentEventId, parentEventId, eventRelationTypeId);
			json = "{success : true,isExist : 'true'}";
		} else {// ����
			if (!eventService.isExist(currentEventId, parentEventId)) {
				eventService.createEventRelation(currentEventId, parentEventId, eventRelationTypeId);
				json = "{success:true,isExist:'true'}";
			} else {
				json = "{success:true,isExist:'false'}";
			}
			
		}
		super.getResponse().setContentType("text/plain");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * �鳤�¼�����ָ�ɡ�
	 * 
	 * @Methods Name eventAssign
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String eventAssign() throws Exception {
		HttpServletRequest httpServletRequest = super.getRequest();
		Long eventId = Long.parseLong(httpServletRequest.getParameter("eventId"));
		Long userId = Long.parseLong(httpServletRequest.getParameter("userId"));
		String remark = httpServletRequest.getParameter("remark");
		// �¼�����
		Map eventAssignMap = new HashMap<String, Object>();
		Event event = new Event();
		event.setId(eventId);
		UserInfo assignUser = new UserInfo();
		assignUser.setId(userId);
		Date assignDate = new Date();
		UserRole userRole = (UserRole) getService().find(UserRole.class, "userInfo", UserContext.getUserInfo()).get(0);
		Role role = userRole.getRole();
		eventAssignMap.put("roleId", role);
		eventAssignMap.put("event", event);
		eventAssignMap.put("assignUser", assignUser);
		eventAssignMap.put("remark", remark);
		eventAssignMap.put("assignDate", assignDate);
		metaDataManager.saveEntityData(EventAssign.class, eventAssignMap);
		// eventService.createEventAssin(eventId, userId, remark);
		// �����¼���������Ϣ
		eventService.modifyDealerOfEvent(eventId, assignUser);
		super.getResponse().setContentType("text/plain");
		PrintWriter out = super.getResponse().getWriter();
		String json = "{success : true}";
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * �������µ��¼���ע��Ϣ--��id����
	 * 
	 * @author awen
	 * @Methods Name findLatestEventAssign Create In Mar 20, 2009 By awen
	 * @return
	 * @throws IOException
	 */
	public String findLatestEventAssign() throws IOException {
		HttpServletRequest httpServletRequest = super.getRequest();
		Long eventId = Long.parseLong(httpServletRequest.getParameter("eventId"));
		EventAssign eventAssign = eventService.findLatestEventAssign(eventId);
		String json = "";
		json = "{success : true, remark : '" + eventAssign.getRemark() + "'}";
		super.getResponse().setContentType("text/plain");
		super.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * �鿴��ǰ�ڵ�����
	 * 
	 * @Methods Name findCurrentNodeName
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	public String findCurrentNodeName() throws IOException {
		try {
			HttpServletRequest httpServletRequest = getRequest();
			Long taskId = Long.parseLong(httpServletRequest.getParameter("taskId"));
			// ͨ��taskId��õ�ǰNode������
			String currentNodeName = eventService.findCurrentNodeNameByTaskId(taskId);
			String json = "";
			json = "{success : true, currentNodeName : '" + currentNodeName + "'}";
			getResponse().setContentType("text/plain");
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * CCʹ���Ѿ�����ͨ���Ľ�����������¼�
	 * 
	 * @Methods Name endCCEventWithValidKnowledge
	 * @Create In Apr 2, 2009 By duxh
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String endCCEventWithValidKnowledge() throws IOException {
		HttpServletRequest request = super.getRequest();
		String knowledgeId = request.getParameter("knowledgeId");
		String customerItcode = request.getParameter("customerItcode");
		String callPhone = request.getParameter("callPhone");
		String callName = request.getParameter("callName");
		String department = request.getParameter("department");
		String mailFlag = request.getParameter("mailFlag");
		String submitUserItcode = request.getParameter("submitUserItcode");
		String callId = request.getParameter("callId");
		UserInfo engineer=(UserInfo) getService().findUnique(UserInfo.class, "itcode", submitUserItcode.toUpperCase());
		UserInfo customer=(UserInfo) getService().findUnique(UserInfo.class, "itcode", customerItcode.toUpperCase());
		Knowledge knowledge= (Knowledge) getService().find(Knowledge.class,knowledgeId);
		knowledge.setUseTime(knowledge.getUseTime()+1);
		Knowledge newKnowledge=(Knowledge) getService().save(knowledge);
		Map eventMap = new HashMap();
		eventMap.put("summary", knowledge.getSummary());
		eventMap.put("scidData", knowledge.getServiceItem());
		eventMap.put("scidType", knowledge.getServiceItem().getServiceItemType());
		eventMap.put("submitUser",customer);
		eventMap.put("createUser", engineer);
		eventMap.put("dealuser", engineer);//2010-06-28 modified by huzh for bug(�ֶ�������)
		eventMap.put("eventStatus", getService().findUnique(EventStatus.class, "keyword",EventStatus.FINISH));
		eventMap.put("submitDate", new Date());
		eventMap.put("closedDate", new Date());
//		//2010-07-21 add by huzh for ���ݴ�����ʦ���¼����֧���飨����һ����һ��û�еķ��ض�����begin
//		List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer);
//		if(sgList!=null&&sgList.size()!=0){
//			eventMap.put("supportGroup", sgList.get(0));
//		}
//		//2010-07-21 add by huzh for ���ݴ�����ʦ���¼����֧���飨����һ����һ��û�еķ��ض�����end
		//2010-08-09 add by huzh for ���ݷ��������ʦȥ��֧���� begin
		List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer,knowledge.getServiceItem());
		if(sgList!=null&&sgList.size()!=0){
			eventMap.put("supportGroup", sgList.get(0));
		}
		//2010-08-09 add by huzh for ���ݷ��������ʦȥ��֧����end
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� begin
		if(mailFlag!=null&&"yes".equals(mailFlag)==true){
			eventMap.put("knowSendFlag", Event.SEND_YES);
		}else{
			eventMap.put("knowSendFlag", Event.SEND_NO);
		}
		eventMap.put("knowSubmitFlag", Event.SUBMIT_NO);
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� end
		Event event = (Event) metaDataManager.saveEntityData(Event.class, eventMap);
		EventSulotion eventsulotion = new EventSulotion();
		eventsulotion.setCreateDate(new Date());
		eventsulotion.setCreatName(engineer);
		eventsulotion.setEvent(event);
		eventsulotion.setKnowledge(knowledge);
		super.getService().save(eventsulotion);
		CCCallInfo cccallinfo=new CCCallInfo();
		cccallinfo.setCustomerItcode(customerItcode);
		cccallinfo.setSubmitUserItcode(submitUserItcode);
		cccallinfo.setCallName(callName);
		cccallinfo.setCallPhone(callPhone);
		cccallinfo.setCreateDate(new Date());
		cccallinfo.setDepartment(department);
		cccallinfo.setEvent(event);
		cccallinfo.setKnowledge(knowledge);
		cccallinfo.setCallId(callId);
		cccallinfo.setMailFlag(CCCallInfo.MAILFLAG_NO);
		cccallinfo.setSelfFlag(CCCallInfo.SELFFLAG_YES);
		super.getService().save(cccallinfo);
		//2010-07-20 add by huzh for ���û����ͽ������ begin
		if(mailFlag!=null&&"yes".equals(mailFlag)==true){
			String subject="IT��ܰ��ʾ:���Ķ���"+newKnowledge.getSummary()+"�����������á�";
			String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") +"/ITcenter/knowledgeAction_getContentInfoAction.action?id="+knowledgeId;
			SendMailThred knowThread =new SendMailThred(ms,event.getSubmitUser().getEmail(), "", null, subject, this.KnowledgeHtmlContent(event.getSubmitUser(), url, event,newKnowledge), null);
			Thread th = new Thread(knowThread);
		    th.start();
		}
	    //2010-07-20 add by huzh for ���û����ͽ������ end
		return null;
	}

	/**
	 * ͨ��customerItcode���cc����
	 * @Methods Name selectCCallData
	 * @Create In Jan 18, 2010 By duxh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	@SuppressWarnings("unchecked")
	public String selectCCallData() throws IOException {
		String json = "";
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String customerItcode = request.getParameter("customerItcode");
		UserInfo userInfo = userInfoService.findUserInfoByUserName(customerItcode);
		String callName = "";
		String departments = "";
		String userTelephone="";
		String mobilePhone="";
		String platform="";
		String postName="";
		String personScope="";
		boolean success=false;
		if(userInfo!=null){
			success=true;
			List<Department> department = service.find(Department.class, "departCode", userInfo.getDepartCode());
			if (userInfo.getRealName() != null) {
				callName = userInfo.getRealName();
			}
			if (!department.isEmpty()&&department.get(0).getDepartName() != null) {
				departments = department.get(0).getDepartName();
			}
			if(userInfo.getTelephone()!=null){
				userTelephone=userInfo.getTelephone();
			}
			if(userInfo.getMobilePhone()!=null){
				mobilePhone=userInfo.getMobilePhone();
			}
			if(userInfo.getPlatform()!=null){
				platform=userInfo.getPlatform().getName();
			}
			if(userInfo.getPostName()!=null){
				postName=userInfo.getPostName();
			}
			if(userInfo.getPersonnelScope()!=null){
				personScope=userInfo.getPersonnelScope().getName();
			}
		}
		json += "callName:'" + callName + "',";
		json += "department:'" + departments + "',";
		json += "userTelephone:'" + userTelephone + "',";
		json += "mobilePhone:'" + mobilePhone + "',";
		json += "postCode:'" +  postName+ "',";
		json += "personScope:'" +  personScope+ "',";
		json += "platform:'" + platform + "'";
		json = "{success : "+success+"," + json + "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * ������ϯ��д�������
	 * 
	 * @Methods Name saveCCallSulotion
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveCCallSulotion() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		//2010-04-27 modified by huzh ҳ���������ĺ�̨�Ķ� begin
		String knowInfo = request.getParameter("knowparam");
		String resolvent = request.getParameter("resolvent");
		String eventInfo = request.getParameter("eventparam");
//		String serviceTypeId = request.getParameter("serviceType");//2010-05-19 delete by huzh for �û�����ȥ������������ 
		String serviceItemId = request.getParameter("serviceItem");
		String customerItcode = request.getParameter("customerItcode");
		String callPhone = request.getParameter("callPhone");
		String callName = request.getParameter("callName");
		String department = request.getParameter("department");
		String submitUserItcode = request.getParameter("submitUserItcode");
		String callId = request.getParameter("callId");
		UserInfo engineer=(UserInfo) getService().findUnique(UserInfo.class, "itcode", submitUserItcode);
		UserInfo customer=(UserInfo) getService().findUnique(UserInfo.class, "itcode", customerItcode);
		String knowSubmitFlag="";//add by huzh for �Ƿ��ύ֪ʶ����
		String knowMailFlag="";//2010-06-29 add by huzh for �Ƿ��������û����ͽ�������ʼ�
		String eventDealTypeId="";//add by huzh for �¼�����ʽID
		HashMap infoMap = new HashMap();
		JSONObject knowjo = JSONObject.fromObject(knowInfo);
		Iterator itInfo = knowjo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			// 2010-06-25 modified by huzh for ��ӡ��Ƿ��ύ֪ʶ�����¼�������ʽ���ĺ�̨���� begin
			if("enabled".equals(key)){//�Ƿ��ύ֪ʶ
				knowSubmitFlag=knowjo.getString(key);
				continue;
			}
			if("mailEnabled".equals(key)){//�Ƿ��������û����ͽ�������ʼ�
				knowMailFlag=knowjo.getString(key);
				continue;
			}
			String[] keyString = key.split("\\$");
			if(keyString[1].equals("engineer")
					||keyString[1].equals("scidData")
						||keyString[1].equals("SubmitFlag")
							||keyString[1].equals("resolvent_other")
								||keyString[1].equals("mailFlag")){
				continue;
			}
			if(keyString[1].equals("eventDealType")){//�¼�����ʽ
				eventDealTypeId=knowjo.getString(key);
				continue;
			}
			// 2010-06-25 modified by huzh for ��ӡ��Ƿ��ύ֪ʶ�����¼�������ʽ���ĺ�̨���� end
			String	value = knowjo.getString(key);
			infoMap.put(keyString[1], value);
		}
		infoMap.put("resolvent",resolvent);
		infoMap.put("createUser",engineer);
		infoMap.put("createDate", new Date());
		infoMap.put("useTime", 1);
		// 2010-06-25 modified by huzh for ���ύ֪ʶ��yes����֪ʶ״̬Ϊ���ݸ塱������Ϊ�������䡱״̬ begin
		if("no".equals(knowSubmitFlag)){
			infoMap.put("status", Knowledge.STATUS_RENEW);	
		}else{
			infoMap.put("status", Knowledge.STATUS_DRAFT);
		}
		// 2010-06-25 modified by huzh for ���ύ֪ʶ��yes����֪ʶ״̬Ϊ���ݸ塱������Ϊ�������䡱״̬ end
		// 2010-06-25 modified by huzh for ��ӡ��Ƿ��ύ֪ʶ�����¼�������ʽ���ĺ�̨���� begin
		ServiceItem si=(ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(serviceItemId));
		infoMap.put("serviceItem", si);
		Knowledge knowledge = (Knowledge) metaDataManager.saveEntityData(Knowledge.class, infoMap);
		//ServiceItem si=(ServiceItem) getService().find(ServiceItem.class, knowledge.getServiceItem().getId().toString());
		HashMap eventMap = new HashMap();
		JSONObject eventjo = JSONObject.fromObject(eventInfo);
		Iterator evIterator = eventjo.keys();
		while (evIterator.hasNext()) {
			String key = (String) evIterator.next();
			String[] keyString = key.split("\\$");
			String value = eventjo.getString(key);
			eventMap.put(keyString[1], value);
		}
		if("".equals(eventDealTypeId)==false){
			EventDealType dealtype= new EventDealType();
			dealtype.setId(Long.parseLong(eventDealTypeId));
			eventMap.put("eventDealType", dealtype);
		}
		eventMap.put("scidData", si);
		eventMap.put("scidType", si.getServiceItemType());
		eventMap.put("submitUser",customer);
		eventMap.put("createUser", engineer);
		eventMap.put("eventStatus", getService().findUnique(EventStatus.class, "keyword",EventStatus.FINISH));
		eventMap.put("submitDate", new Date());
		eventMap.put("closedDate", new Date());
		eventMap.put("selfResolveFlag", Event.EVENT_SELFRESOLVE_TRUE);
		eventMap.put("dealuser", engineer);
		
//		//2010-07-21 add by huzh for ���ݴ�����ʦ���¼����֧���飨����һ����һ��û�еķ��ض�����begin
//		List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer);
//		if(sgList!=null&&sgList.size()!=0){
//			eventMap.put("supportGroup", sgList.get(0));
//		}
//		//2010-07-21 add by huzh for ���ݴ�����ʦ���¼����֧���飨����һ����һ��û�еķ��ض�����end
		//2010-08-09 add by huzh for ���ݷ��������ʦȥ��֧���� begin
		List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer,si);
		if(sgList!=null&&sgList.size()!=0){
			eventMap.put("supportGroup", sgList.get(0));
		}
		//2010-08-09 add by huzh for ���ݷ��������ʦȥ��֧���� begin
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� begin
		if("no".equals(knowSubmitFlag)){
			eventMap.put("knowSubmitFlag", Event.SUBMIT_NO);
		}else{
			eventMap.put("knowSubmitFlag", Event.SUBMIT_YES);
		}
		if("yes".equals(knowMailFlag)){
			eventMap.put("knowSendFlag", Event.SEND_YES);
		}else{
			eventMap.put("knowSendFlag", Event.SEND_NO);
		}
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� end
		Event event = (Event) metaDataManager.saveEntityData(Event.class, eventMap);
		EventSulotion eventsulotion = new EventSulotion();
		eventsulotion.setCreateDate(new Date());
		eventsulotion.setCreatName(engineer);
		eventsulotion.setEvent(event);
		eventsulotion.setKnowledge(knowledge);
		super.getService().save(eventsulotion);
		CCCallInfo cccallinfo=new CCCallInfo();
		cccallinfo.setCustomerItcode(customerItcode);
		cccallinfo.setSubmitUserItcode(submitUserItcode);
		cccallinfo.setCallName(callName);
		cccallinfo.setCallPhone(callPhone);
		cccallinfo.setCreateDate(new Date());
		cccallinfo.setDepartment(department);
		cccallinfo.setEvent(event);
		cccallinfo.setKnowledge(knowledge);
		cccallinfo.setCallId(callId);
		cccallinfo.setMailFlag(CCCallInfo.MAILFLAG_NO);
		cccallinfo.setSelfFlag(CCCallInfo.SELFFLAG_YES);
		super.getService().save(cccallinfo);
		KnowledgeType knowledgeType = (KnowledgeType) getService().findUnique(KnowledgeType.class, "className",
										"com.zsgj.itil.knowledge.entity.Knowledge");
		String json = "{success:true,createUser:'" + engineer.getId() + "',knowledgeId:'"
		+ knowledge.getId() + "',knowledgeTypeId:'" + knowledgeType.getId() + "'}";
		
		//2010-06-29 add by huzh for �жϱ�ǿ��Ƿ���û������ʼ� begin
		if("yes".equals(knowMailFlag)){
			String subject="IT��ܰ��ʾ:"+event.getSubmitUser().getRealName()+"/"+event.getSubmitUser().getUserName()+"������ġ�"+event.getSummary()+"���Ľ��������ϣ���ܶ�������������";
			String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") + "/user/event/ccEntry/mailKnowledge.jsp?eventId="+event.getId()+"&knowledgeId="+knowledge.getId();
			SendMailThred knowThread =new SendMailThred(ms,event.getSubmitUser().getEmail(), "", null, subject, this.eventHtmlContent(event.getSubmitUser(), url, event), null);
			Thread th = new Thread(knowThread);
		    th.start();
		}
		//2010-06-29 add by huzh for �жϱ�ǿ��Ƿ���û������ʼ� begin
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(json);
		writer.flush();
		writer.close();
		return null;

	}
	
	/**
	 * �����Ƿ���ڵ�ǰ�����itcode,������ڷ�����������������
	 * 
	 * @Methods Name findITCODE
	 * @Create In Apr 12, 2009 By sujs
	 * @return
	 * @throws IOException
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String findITCODE() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String ITCode = request.getParameter("ITCode");
		List<UserInfo> userTable = service.find(UserInfo.class, "itcode", ITCode);
		String json = "";
		if (userTable.size() == 0) {
			json = "{success : true,uservalue:'noExit'}";
		} else {
			UserInfo userInfo = userTable.get(0);
			json = "{success : true,uservalue:'Exit',username:'" + (userInfo.getRealName()==null?"":userInfo.getRealName()) + 
					"',deparment:'"+ (userInfo.getDepartment()==null?"":(userInfo.getDepartment().getDepartName()==null?"":userInfo.getDepartment().getDepartName())) + 
				    "',userTelephone:'" + (userInfo.getTelephone()==null?"":userInfo.getTelephone()) +
				    "',mobilePhone:'"+(userInfo.getMobilePhone()==null?"":userInfo.getMobilePhone()) + "'}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;

	}

	/**
	 * ��ѯ�¼�������
	 * 
	 * @author awen
	 * @return
	 * @throws IOException
	 */
	public String findDealUserOfEvent() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String eventId = request.getParameter("eventId");
		Event event = (Event) service.find(Event.class, eventId, true);
		String json = "";
		json = "{success : true,dealUser : '" + event.getDealuser().getId() + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * ͨ��Event��systemMainTable�е�Id��ѯ�û�����ȵ�������
	 * 
	 * @author awen
	 * @return
	 * @throws IOException
	 */
	public String findEventSurvey() throws IOException {
		HttpServletResponse response = super.getResponse();
		Survey survey = eventService.findEventSurvey();
		String json = "";
		json = "{success : true , surveyId : '" + survey.getId() + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	
	public String showQuestions(){
		HttpServletRequest request = super.getRequest();
		Long eventId = Long.parseLong(request.getParameter("eventId"));
		Survey survey = eventService.findEventSurvey();
		List<Quest> questList = eventService.findQuest(survey.getId());
		Iterator<Quest> iter = questList.iterator();
		Map map = new LinkedHashMap();// linkedHashMap��set��ȥ�Ⱥ�˳�������
		while (iter.hasNext()) {
			Quest quest = iter.next();
			List<QuestOption> list = trainPlanService.findQuestOption(quest);
			map.put(quest, list);
		}
//		Event event=(Event) this.service.findUnique(Event.class, "id", eventId);
		//2010-08-12 modified by huzh for �޸Ĳ�ѯ���� begin
//		List questResultList = questService.getResult(event.getSubmitUser(), survey.getId(), eventId);
		List questResultList = questService.getResultBySurveyAndObjId(survey.getId(), eventId);
		//2010-08-12 modified by huzh for �޸Ĳ�ѯ���� begin
		super.getRequest().setAttribute("questOptions", map);
		super.getRequest().setAttribute("eventId", eventId);
		super.getRequest().setAttribute("survey", survey);
		super.getRequest().setAttribute("surveyId", survey.getId());
		if (questResultList.isEmpty()) {
			return "userFeedback_null";
		} else {
			super.getRequest().setAttribute("results", questResultList);
			return "userFeedback_show";
		}
	}
	/**
	 * ��ѯ�û����������б�
	 * 
	 * @author awen
	 * @return
	 * @throws IOException
	 */
	public String findQuestions() throws IOException {
		HttpServletRequest request = super.getRequest();
		Long surveyId = Long.parseLong(request.getParameter("surveyId"));
		Long eventId = Long.parseLong(request.getParameter("eventId"));
		Long taskId = null;
		Survey survey = (Survey) service.find(Survey.class, request.getParameter("surveyId"));
		if (request.getParameter("taskId") != null) {
			taskId = Long.parseLong(request.getParameter("taskId"));
		}
//		UserInfo userInfo = UserContext.getUserInfo();

		List<Quest> questList = eventService.findQuest(surveyId);
		Iterator<Quest> iter = questList.iterator();
		Map map = new LinkedHashMap();// linkedHashMap��set��ȥ�Ⱥ�˳�������
		while (iter.hasNext()) {
			Quest quest = iter.next();
			List<QuestOption> list = trainPlanService.findQuestOption(quest);
			map.put(quest, list);
		}

		//2010-08-12 modified by huzh for �޸Ĳ�ѯ���� begin
//		List questResultList = questService.getResult(userInfo, surveyId, eventId);
		List questResultList = questService.getResultBySurveyAndObjId(surveyId, eventId);
		//2010-08-12 modified by huzh for �޸Ĳ�ѯ���� end
		super.getRequest().setAttribute("questOptions", map);
		super.getRequest().setAttribute("eventId", eventId);

		if (taskId != null) {
			super.getRequest().setAttribute("taskId", taskId);
		}
		super.getRequest().setAttribute("survey", survey);
		super.getRequest().setAttribute("surveyId", surveyId);
		if (questResultList.isEmpty()) {
			return "userFeedback_view";
		} else {
			super.getRequest().setAttribute("results", questResultList);
			return "userFeedback_result";
		}
	}

	/**
	 * �����û����������
	 * 
	 * @return
	 * @throws IOException
	 */
	public String saveQuestResult() throws IOException {
		HttpServletRequest request = super.getRequest();
		Long objId = Long.parseLong(request.getParameter("objId"));
		String surveyId = request.getParameter("surveyId");
		UserInfo userInfo = UserContext.getUserInfo();
		Survey survey = (Survey) service.find(Survey.class, surveyId, true);
		// modify by lee for �û������һֱ�ɱ༭ in 20090922 begin
		// boolean isExist =
		// eventService.findIsUserFeedbackOrNot(userInfo.getId(), objId,
		// Long.valueOf(surveyId));
		// if(isExist){
		// return "userFeedback_success";
		// }
		//2010-08-12 modified by huzh for �޸Ĳ�ѯ���� begin
//		List<QuestResult> oldResults = questService.getResult(userInfo, Long.valueOf(surveyId), objId);// ��ȡ֮ǰ�Ĵ�
		List<QuestResult> oldResults = questService.getResultBySurveyAndObjId(Long.valueOf(surveyId), objId);
		//2010-08-12 modified by huzh for �޸Ĳ�ѯ���� end
		for (QuestResult result : oldResults) {
			this.getService().remove(result); // ɾ��֮ǰ�Ĵ�
		}
		// modify by lee for �û������һֱ�ɱ༭ in 20090922 end
		String[] quest_ids = super.getRequest().getParameterValues("quest_id");
		if (quest_ids != null) {
			for (int i = 0; i < quest_ids.length; i++) {
				String radio = super.getRequest().getParameter(quest_ids[i] + "_radio");
				Quest quest = (Quest) super.getService().find(Quest.class, quest_ids[i]);
				QuestOption questOption = (QuestOption) super.getService().find(QuestOption.class, radio);
				QuestResult questResult = new QuestResult();
				questResult.setQuest(quest);
				questResult.setQuestOption(questOption);
				questResult.setUserId(userInfo);
				questResult.setTrainCourse(null);
				questResult.setSurvey(survey);
				questResult.setObjId(objId);
				super.getService().save(questResult);
			}
		}
		String[] quest_ids2 = super.getRequest().getParameterValues("quest_id2");
		if (quest_ids2 != null) {
			for (int i = 0; i < quest_ids2.length; i++) {
				String[] radio = super.getRequest().getParameterValues(quest_ids2[i] + "_checkbox");
				Quest quest = (Quest) super.getService().find(Quest.class, quest_ids2[i]);
				for (int j = 0; j < radio.length; j++) {
					QuestOption questOption = (QuestOption) super.getService().find(QuestOption.class, radio[j]);
					QuestResult questResult = new QuestResult();
					questResult.setQuest(quest);
					questResult.setQuestOption(questOption);
					questResult.setUserId(userInfo);
					questResult.setTrainCourse(null);
					questResult.setSurvey(survey);
					questResult.setObjId(objId);
					super.getService().save(questResult);
				}
			}
		}

		String[] quest_ids3 = super.getRequest().getParameterValues("quest_id3");
		if (quest_ids3 != null) {
			for (int i = 0; i < quest_ids3.length; i++) {
				String radio = super.getRequest().getParameter(quest_ids3[i] + "_text");
				Quest quest = (Quest) super.getService().find(Quest.class, quest_ids3[i]);
				// QuestOption questOption = new QuestOption();
				// questOption.setContent(radio);
				// questOption.setQuest(quest);
				// super.getService().save(questOption);
				QuestResult questResult = new QuestResult();
				questResult.setQuest(quest);
				// questResult.setQuestOption(questOption);
				questResult.setUserId(userInfo);
				questResult.setTrainCourse(null);
				questResult.setSurvey(survey);
				questResult.setObjId(objId);
				questResult.setAnswer(radio);
				super.getService().save(questResult);
			}
		}
		String[] quest_ids4 = super.getRequest().getParameterValues("quest_id4");
		if (quest_ids4 != null) {
			for (int i = 0; i < quest_ids4.length; i++) {
				String radio = super.getRequest().getParameter(quest_ids4[i] + "_textArea");
				radio = HttpUtil.ConverUnicode(radio);// add by lee for ת�� in
				// 20090922
				Quest quest = (Quest) super.getService().find(Quest.class, quest_ids4[i]);
				// QuestOption questOption = new QuestOption();
				// questOption.setContent(radio);
				// questOption.setQuest(quest);
				// super.getService().save(questOption);
				QuestResult questResult = new QuestResult();
				questResult.setQuest(quest);
				// questResult.setQuestOption(questOption);
				questResult.setUserId(userInfo);
				questResult.setTrainCourse(null);
				questResult.setSurvey(survey);
				questResult.setObjId(objId);
				questResult.setAnswer(radio);
				super.getService().save(questResult);
			}
		}
		return "userFeedback_history";
	}

	/**
	 * ��ѯ���¼��ύ���Ƿ���д����û����������ʾ�
	 * 
	 * @return
	 * @throws IOException
	 */
	public String findIsUserFeedbackOrNot() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo userInfo = UserContext.getUserInfo();
		Long surveyId = Long.parseLong(request.getParameter("surveyId"));
		Long eventId = Long.parseLong(request.getParameter("eventId"));
		boolean isExist = eventService.findIsUserFeedbackOrNot(userInfo.getId(), eventId, surveyId);
		String json = "";
		json = "{success : false}";
		if (isExist) {
			json = "{success : true}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * ͨ���¼�id�õ�����¼���Ӧ�ķ��������ͺͷ�����
	 * 
	 * @Methods Name getEventServiceTypeAndItemByEventId
	 * @Create In Aug 4, 2009 By guoxl
	 * @return String
	 */
	public String getEventServiceTypeAndItemByEventId() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String eventId = request.getParameter("eventId");
		Event event = (Event) super.getService().find(Event.class, eventId, true);
		String json = "";
		//2010-05-21 modified by huzh begin
		if (event != null) {
			ServiceItem si=event.getScidData();
			if(si!=null){
				json = "serviceTypeId:'" + event.getScidType().getId() + "'," + "serviceItemId:'"
				+ si.getId() + "',";
			}
		}
		if (json.length() == 0) {
			json = "{success:true,serviceTypeId:'',serviceItemId:''}";
		} else {
			json = "{success:true," + json.substring(0, json.length() - 1) + "}";
		}
		//2010-05-21 modified by huzh end
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * �޸��¼���
	 * 
	 * @Methods Name modifyEvent
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String modifyEvent() {
		try {
			HttpServletRequest request = getRequest();
			String eventId = request.getParameter("eventId");
			//2010-05-20 modified by huzh for ����ʦ����ҳ��ѡ����������ͱ����¼� begin
			String serviceItemId = request.getParameter("serviceItemId");
			String eventFrequencyId = request.getParameter("eventFrequency");
			String eventPonderanceId = request.getParameter("eventPonderance");
			String desc = request.getParameter("desc");//2010-07-05 add by huzh for �¼�����ҳ������޸��¼�����
			String problemSortId = request.getParameter("problemSortId");//2010-07-23 add by huzh for �������
			Event event = (Event) getService().find(Event.class, eventId, true);
			ProblemSort problemSort = new ProblemSort();
			if (!"".equals(problemSortId.trim())&&problemSortId!=null) {
				problemSort.setId(Long.parseLong(problemSortId));
				event.setProblemSort(problemSort);//2010-07-23 add by huzh for �����¼����������
			}else{
				event.setProblemSort(null);
			}
			if (!"".equals(serviceItemId.trim())&&serviceItemId!=null) {
				ServiceItem serviceItem = (ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(serviceItemId));
				event.setScidData(serviceItem);
				event.setScidType(serviceItem.getServiceItemType());

			}
			//2010-05-20 modified by huzh for ����ʦ����ҳ��ѡ����������ͱ����¼� end
			if (!"".equals(eventFrequencyId)) {
				EventFrequency frequency = new EventFrequency();
				frequency.setId(Long.parseLong(eventFrequencyId));
				event.setFrequency(frequency);
			}
			if (!"".equals(eventPonderanceId)) {
				EventPonderance eventPonderance = new EventPonderance();
				eventPonderance.setId(Long.parseLong(eventPonderanceId));
				event.setPonderance(eventPonderance);
			}
			event.setDescription(desc);//2010-07-05 add by huzh for �����¼�����
			getService().save(event);
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print("{success:true}");
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * ͨ���¼�id���ʱ��Ĵ������ύ�˺ʹ�����
	 * 
	 * @Methods Name findEventByDataId
	 * @Create In Aug 23, 2009 By wanghao
	 * @return String
	 */
	public String findEventByDataId() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		Event event = (Event) service.find(Event.class, dataId, true);
		StringBuffer sb = new StringBuffer();
		sb.append("{success:true,");
		if (event != null) {
			sb.append("submitUser:'").append(event.getSubmitUser().getRealName()).append("/").append(
					event.getSubmitUser().getUserName()).append("/").append(
					event.getSubmitUser().getDepartment().getDepartName()).append("',");
			 //2010-06-23 add by huzh for ����¼��ύ�˵绰 begin
			String telPhone=event.getSubmitUser().getTelephone();
			String mobilePhone=event.getSubmitUser().getMobilePhone();
			String phone="";
			if((telPhone!=null&&"".equals(telPhone)==false)
					&&(mobilePhone!=null&&"".equals(mobilePhone)==false)){
				sb.append("sUserPhone:'").append(event.getSubmitUser().getTelephone()).append("/").append(
						event.getSubmitUser().getMobilePhone()).append("',");
			}else{
				if(telPhone!=null&&"".equals(telPhone)==false){
					phone=telPhone;
				}else if(mobilePhone!=null&&"".equals(mobilePhone)==false){
					phone=mobilePhone;
				}
				sb.append("sUserPhone:'").append(phone).append("',");
			}
			//2010-06-23 add by huzh for ����¼��ύ�˵绰 end
			sb.append("createUser:'").append(event.getCreateUser().getRealName()).append("/").append(
					event.getCreateUser().getUserName()).append("/").append(
					event.getCreateUser().getDepartment().getDepartName()).append("',");
			if (event.getDealuser() != null) {
				sb.append("dealuser:'").append(event.getDealuser().getRealName()).append("/").append(
						event.getDealuser().getUserName()).append("/").append(
						event.getDealuser().getDepartment().getDepartName()).append("',");
			} else {
				sb.append("dealuser:'").append("',");
			}
			// �����ȡʱ��������Ժ�Ƶ�ʵ����� -- by wanghao
			sb.append("frequency:'").append(event.getFrequency() == null ? "" : event.getFrequency().getId()).append(
					"',");
			sb.append("frequencyName:'").append(event.getFrequency() == null ? "" : event.getFrequency().getName())
					.append("',");
			sb.append("ponderanceName:'").append(event.getPonderance() == null ? "" : event.getPonderance().getName())
					.append("',");
			sb.append("ponderance:'").append(event.getPonderance() == null ? "" : event.getPonderance().getId())
					.append("',");
			//2010-07-23 add by huzh for �������
			sb.append("problemSort:'").append(event.getProblemSort() == null ? "" : event.getProblemSort().getId())
				.append("'");
		}
		sb.append("}");
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			out = response.getWriter();
			out.println(sb.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ɾ���¼���ϵ��
	 * 
	 * @Methods Name removeEventRelation
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String removeEventRelation() {
		String json = "{suceess:true}";
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String[] ids = request.getParameterValues("id");
		for (int i = 0; i < ids.length; i++) {
			eventService.removeDoubleRel(ids[i]);
		
		}
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �õ���ǰ�¼��Ĺ�ϵ�������¼�����Ϣ
	 * 
	 * @Methods Name getEventRelByCurrEvent
	 * @Create In Sep 16, 2009 By guoxl
	 * @return String
	 */
	public String getEventRelByCurrEvent() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String currEventId = request.getParameter("eventId");
		Event event = (Event) super.getService().find(Event.class, currEventId, true);
//		EventRelationType EventRelTypeA = (EventRelationType) super.getService().findUnique(EventRelationType.class,
//				"typeFlag", EventRelationType.SAME);// ͬһ�¼�
//		EventRelationType EventRelTypeC = (EventRelationType) super.getService().findUnique(EventRelationType.class,
//				"typeFlag", EventRelationType.CHILD);// ���¼�
//		EventRelationType EventRelTypeB = (EventRelationType) super.getService().findUnique(EventRelationType.class,
//				"typeFlag", EventRelationType.PARENT);// ���¼�
//		EventRelationType EventRelTypeD = (EventRelationType) super.getService().findUnique(EventRelationType.class,
//				"typeFlag", EventRelationType.RELATION);// �����¼�
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		Long total = 1L;
		Page page = eventService.getEventRelByCurrEvent(event, pageNo, pageSize);
		List<EventRelation> relList = new ArrayList<EventRelation>();
		String json = "";
		if (page != null) {
			total = page.getTotalCount();
			relList = page.list();
		}

		String relEventCisn = "";
		String relEventName = "";
		String relEventRelType = "";
		String eventStatus = "";
		String dealUser = "";
		String closeDate = "";

		if (relList.size() > 0) {
			for (EventRelation rel : relList) {
				// if((rel.getParentEvent().getId()!=event.getId())&&(rel.getEventRelationType().getId()==EventRelTypeA.getId()||rel.getEventRelationType().getId()==EventRelTypeC.getId()||rel.getEventRelationType().getId()==EventRelTypeD.getId())){
				if (rel.getParentEvent().getEventStatus() != null) {
					eventStatus = rel.getParentEvent().getEventStatus().getName();
				}
				if (rel.getParentEvent().getDealuser() != null) {
					dealUser = rel.getParentEvent().getDealuser().getRealName();
				}
				if (rel.getParentEvent().getClosedDate() != null) {
					closeDate = rel.getParentEvent().getClosedDate().toString();
				}
				relEventCisn = rel.getParentEvent().getEventCisn();
				relEventName = rel.getParentEvent().getSummary();
				relEventRelType = rel.getEventRelationType().getName();
				json += "{\"id\":" + rel.getId() + "," + "\"parentEventCisn\":'" + relEventCisn + "'," + "\"event\":'"
						+ relEventName + "'," + "\"typeName\":'" + relEventRelType + "'," + "\"eventStatus\":'"
						+ eventStatus + "'," + "\"dealUser\":'" + dealUser + "'," + "\"closeDate\":'" + closeDate
						+ "'},";

			}
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:[" + json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:[" + json.substring(0, json.length() - 1) + "]}";
		}
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ѯ�ϴ��ļ��б�
	 * 
	 * @Methods Name getFileList
	 * @Create In Nov 5, 2009 By duxh
	 * @return String
	 */
	public String getFileList() throws ClassNotFoundException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String clazz = request.getParameter("clazzName");
		Class clazzName = Class.forName(clazz);
		String dataId = request.getParameter("dataId");
		String columnName = request.getParameter("columnName");
		String hiddenId = request.getParameter("hiddenId");
		String columnid = request.getParameter("columnid");
		Object ob = service.find(clazzName, dataId);

		BeanWrapper bw = new BeanWrapperImpl(ob);
		Object value = bw.getPropertyValue(columnName);
		String json = "";
		List<SystemFile> systemfileList = service.find(SystemFile.class, "nowtime", value);
		String filesPathString = "";
		int k = 1;
		for (SystemFile filesTemp : systemfileList) {
			filesPathString += "<span id =su_"
					+ filesTemp.getId()
					+ "><img src='+webContext+'/images/other/file.gif >"
					+ "&nbsp;<a href='+webContext+'"
					+ "/fileDown.do?methodCall=downloadFile&id="
					+ filesTemp.getId()
					+ "&columnId="
					+ columnid
					+ ">"
					+ filesTemp.getFileName()
					+ "</a>&nbsp;&nbsp;"
					+ "<img style =\"display:none\"  src='+webContext+'/images/other/suremove.gif onClick=getRemoveFile(\""
					+ filesTemp.getId() + "\",\"" + hiddenId
					+ "\",\"com.zsgj.info.appframework.metadata.entity.SystemFile\")"
					+ " alt=\"ɾ������\" style=\"cursor:hand;margin:-1 0\">";
			if (k % 4 == 0) {
				filesPathString = filesPathString + "<br></span>";
			} else {
				filesPathString = filesPathString + "</span>";
			}
			k++;
		}
		json = "{file:'" + filesPathString + "'}";
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ȷ����ҳ��ҳ��
	 * 
	 * @Methods Name confirmPageNo
	 * @Create In Dec 23, 2008 By Administrator
	 * @param paramName
	 * @param size
	 * @return int
	 */
	public int confirmPageNo(String paramName, int size) {

		if (paramName == null || paramName.equals("")) {
			return size;
		}
		return Integer.parseInt(paramName);
	}

	/**
	 * ʹ�����ͨ���Ľ�����������¼���
	 * 
	 * @Methods Name endEventWithValidKnowledge
	 * @Create In Oct 20, 2009 By duxh
	 * @return String
	 * @throws IOException
	 */
	public String endEventWithValidKnowledge() throws IOException {
		try {
			String eventId=getRequest().getParameter("eventId");
			String knowledgeId=getRequest().getParameter("knowledgeId");
			String serviceItemId=getRequest().getParameter("serviceItemId");
			String problemSortId=getRequest().getParameter("problemSortId");//2010-07-23 add by huzh for �������
			String desc=getRequest().getParameter("desc");
			//2010-04-14 add by huzh for ��֮ǰ�ǹ���ʦ�Խ���������û�ȷ���Ǳ���أ�����ʱҪʹ�ý���������������Ҫ�޸��Խ�����Ϊnull begin
			Event event=(Event)service.findUnique(Event.class, "id", Long.parseLong(eventId));
			event.setSelfResolveFlag(null);
			event.setDescription(desc);
			if(serviceItemId!=null&&"".equals(serviceItemId.trim())==false){
				ServiceItem si=(ServiceItem) service.findUnique(ServiceItem.class, "id", Long.valueOf(serviceItemId));
				event.setScidData(si);
				event.setScidType(si.getServiceItemType());
			}
			if(problemSortId!=null&&"".equals(problemSortId.trim())==false){
				ProblemSort problemSort=new ProblemSort();
				problemSort.setId(Long.valueOf(problemSortId));
				event.setProblemSort(problemSort);//�����������
			}else{
				event.setProblemSort(null);
			}
			//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ�������� begin
			event.setKnowSendFlag(Event.SEND_YES);
			event.setKnowSubmitFlag(Event.SUBMIT_NO);
			//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ�������� end
			Event newevent=(Event) service.save(event);
			//2010-04-14 add by huzh end
			Knowledge knowledge = new Knowledge();
			knowledge.setId(Long.parseLong(knowledgeId));
			eventService.endEventWithValidKnowledge(newevent, knowledge);
			super.getResponse().setContentType("text/plain");
			PrintWriter writer = getResponse().getWriter();
			writer.print("{success:true}");
			writer.flush();
			writer.close();
			return null;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * ʹ����ͨ���Ľ����������ʼ��¼�
	 * @Methods Name endMailEventWithValidKnowledge
	 * @Create In May 10, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	public String endMailEventWithValidKnowledge() throws IOException {
		HttpServletRequest request = super.getRequest();
		String knowledgeId = request.getParameter("knowledgeId");
		String submitUserItcode = request.getParameter("submitUserItcode");
		//String submitUserItcode = request.getParameter("submitUserItcode");
		UserInfo submitUser=(UserInfo) getService().findUnique(UserInfo.class, "itcode", submitUserItcode.toUpperCase());
		Knowledge knowledge= (Knowledge) getService().find(Knowledge.class,knowledgeId);
		knowledge.setUseTime(knowledge.getUseTime()+1);
		getService().save(knowledge);
		Map eventMap = new HashMap();
		eventMap.put("summary", knowledge.getSummary());
		eventMap.put("scidData", knowledge.getServiceItem());
		eventMap.put("scidType", knowledge.getServiceItem().getServiceItemType());
		eventMap.put("submitUser",submitUser);
		eventMap.put("createUser", UserContext.getUserInfo());
		//2010-04-22 add by huzh for ��Ӵ����� begin
		eventMap.put("dealuser", UserContext.getUserInfo());
		//2010-04-22 add by huzh for ��Ӵ����� end
		eventMap.put("eventStatus", getService().findUnique(EventStatus.class, "keyword",EventStatus.FINISH));
		eventMap.put("submitDate", new Date());
		eventMap.put("closedDate", new Date());
		Event event = (Event) metaDataManager.saveEntityData(Event.class, eventMap);
		EventSulotion eventsulotion = new EventSulotion();
		eventsulotion.setCreateDate(new Date());
		eventsulotion.setCreatName(UserContext.getUserInfo());
		eventsulotion.setEvent(event);
		eventsulotion.setKnowledge(knowledge);
		super.getService().save(eventsulotion);
		return null;
	}
	/**
	 * ���������еĲ������ҷ���������
	 * 
	 * @Methods Name findServiceItemType
	 * @Create In Oct 27, 2009 By duxh
	 * @return String
	 * @throws IOException
	 */
	@SuppressWarnings( { "unchecked" })
	public String findServiceItemType() throws IOException {
		try {
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(getRequest(), "start", 0);
			int pageNo = start / pageSize + 1;
			String orderBy = HttpUtil.getString(getRequest(), "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(getRequest(), "isAsc", true);
			Map requestParams = HttpUtil.requestParam2Map(getRequest());
			//System.out.println("params="+requestParams.toString());
			requestParams.put("specialFlag", "0");
			Page page = metaDataManager.query(ServiceItemType.class, requestParams, pageNo, pageSize, orderBy, isAsc);
			Long total = page.getTotalCount();
			//System.out.println("page.list: "+page.list());
			List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(ServiceItemType.class, page
					.list());
			//System.out.println("listData: "+listData.toString());
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(getJSONWithIdAndName(listData, total));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}

	/**
	 * ���������еĲ������ҷ�����
	 * 
	 * @Methods Name findServiceItem
	 * @Create In Oct 27, 2009 By duxh
	 * @return String
	 * @throws IOException
	 */
	@SuppressWarnings( { "unchecked" }) 
	public String findServiceItem() {
		try {
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(getRequest(), "start", 0);
			int pageNo = start / pageSize + 1;
			String orderBy = HttpUtil.getString(getRequest(), "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(getRequest(), "isAsc", true);
			Map<String, Object> params = new HashMap<String, Object>();
			String id=getRequest().getParameter("id");
			if(id!=null&&"".equals(id)==false){
				params.put("id", Long.parseLong(id));
			}
			params.put("name", getRequest().getParameter("name"));
			params.put("official", getRequest().getParameter("official"));//2010-05-21 add by huzh for ��������
			params.put("serviceItemType", getRequest().getParameter("ServiceItemTypeID"));
			Page page = metaDataManager.query(ServiceItem.class, params, pageNo, pageSize, orderBy, isAsc);
			Long total = page.getTotalCount();
			List<Map<String, Object>> listData = metaDataManager
					.getEntityMapDataForList(ServiceItem.class, page.list());
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(getJSONWithIdAndName(listData, total));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}


	/**
	 * �¼�ָ�ɸ������顣
	 * 
	 * @Methods Name assignOtherGroup
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String assignOtherGroup() throws IOException {
		try {
			HttpServletResponse response = getResponse();
			HttpServletRequest request = getRequest();
			String json = "";
			int pageSize = HttpUtil.getInt(request, "pageSize", 10);
			int start = HttpUtil.getInt(request, "start", 0);
			int pageNo = start / pageSize + 1;
			String orderBy = HttpUtil.getString(request, "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
			Class clazz = SupportGroupEngineer.class;
			String params = request.getParameter("eid");
			Long para = Long.parseLong(params);
			String info = request.getParameter("eventassignparamother");
			HashMap myinfoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String[] keyString = key.split("\\$");
				String value = jo.getString(key);
				myinfoMap.put(keyString[1], value);
			}
			//2010-07-21 add by ����֧���� begin
			Event event=(Event) service.findUnique(Event.class, "id", para);
			SupportGroup group=new SupportGroup();
			group.setId(Long.valueOf((String) myinfoMap.get("supportGroup")));
			event.setSupportGroup(group);
			//2010-07-21 add by ����֧���� end
			if (myinfoMap.get("supportGroupEngineer").toString() == null
					|| myinfoMap.get("supportGroupEngineer").toString().equals("")) {
				Map paramgroup = new HashMap();
				paramgroup.put("supportGroup", myinfoMap.get("supportGroup").toString());
				Page page = metaDataManager.query(clazz, paramgroup, pageNo, pageSize, orderBy, isAsc);
//				Long total = page.getTotalCount();
				List cofigList = page.list();
//				int k = 1;
				if (cofigList.size() != 0) {
					service.save(event);//2010-07-21 add by huzh ����֧����
					eventService.modifyDealerOfEvent(para, null);
					Iterator iter = cofigList.iterator();
					while (iter.hasNext()) {
						SupportGroupEngineer cofig = (SupportGroupEngineer) iter.next();
						Long supportGroupEngineerId = cofig.getUserInfo().getId();

						json += supportGroupEngineerId + ",";
					}
					json = "{success:true,data:'" + json.substring(0, json.length() - 1) + "'}";
				} else {
					json = "{success:true,data:'nodata'}";
				}

			} else {
				service.save(event);//2010-07-21 add by huzh ����֧����
				eventService.modifyDealerOfEvent(para, null);
				json += myinfoMap.get("supportGroupEngineer") + ",";

				json = "{success:true,data:'" + json.substring(0, json.length() - 1) + "'}";
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * �����¼������
	 * 
	 * @Methods Name saveEventAssign
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveEventAssign() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String eventId = request.getParameter("eid");
		Long dataId = Long.parseLong(eventId);
		String info = request.getParameter("eventassignparam");
		String desc = request.getParameter("desc");//2010-07-05 add by huzh for �����޸��¼�����
		//2010-05-20 modified by huzh for ���¼�û�з�����(��ͷһ�δ����¼�)������ȥ������ͷ��������� begin
		String serviceItem = request.getParameter("serviceItem");
		String problemSortId = request.getParameter("problemSortId");
		Event event=(Event) service.findUnique(Event.class, "id", dataId);
		event.setDescription(desc);//2010-07-05 add by huzh for �����޸ĺ���¼�����
		if(serviceItem!=null&&"".equals(serviceItem.trim())==false){
			ServiceItem si=(ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(serviceItem));
			event.setId(dataId);
			event.setScidData(si);
			event.setScidType(si.getServiceItemType());
		}
		if(problemSortId!=null&&"".equals(problemSortId.trim())==false){
			ProblemSort problemSort=new ProblemSort();
			problemSort.setId(Long.valueOf(problemSortId));
			event.setProblemSort(problemSort);
		}
		service.save(event);
		//2010-05-20 modified by huzh for ���¼�û�з��������ȥ������ end
		HashMap myinfoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");//��"$"�����в��
			String value = jo.getString(key);
			myinfoMap.put(keyString[1], value);
		}
//		Event p = new Event();
//		p.setId(para);
		myinfoMap.put("event", event);
		UserRole userRole = (UserRole) getService().find(UserRole.class, "userInfo", UserContext.getUserInfo()).get(0);
		Role role = userRole.getRole();
		myinfoMap.put("roleId", role);
		myinfoMap.put("assignUser", UserContext.getUserInfo());
		myinfoMap.put("assignDate", new Date());
		metaDataManager.saveEntityData(EventAssign.class, myinfoMap);
		String json = "{success:true}";
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		writer.flush();
		return null;
	}

	/**
	 * �鿴�¼��ύ��
	 * 
	 * @Methods Name findEventSubmitUser
	 * @Create In Oct 30, 2009 By duxh
	 * @return String
	 */
	public String findEventSubmitUser() throws IOException {
		try {
			HttpServletRequest httpServletRequest = super.getRequest();
			String eventId = httpServletRequest.getParameter("eventId");
			Event event = (Event) service.find(Event.class, eventId);
			String json = "";
			json = "{success : true, eventSubmitUser : '" + event.getSubmitUser().getId() + "'}";
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	public String findUserInfoById() throws IOException {
		try {
			HttpServletRequest httpServletRequest = super.getRequest();
			String userId = httpServletRequest.getParameter("userId");
			UserInfo user  = (UserInfo) service.find(UserInfo.class, userId);
			String json = "";
			json = "{success : true, userInfo : '" + user.getRealName()+"/"+user.getUserName()+"/"+user.getDepartment().getDepartName()+"'}";
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * �����¼���
	 * 
	 * @Methods Name dealTheEvent
	 * @Create In Nov 2, 2009 By duxh
	 * @return String
	 */
	public String dealTheEvent() throws IOException {
		try {
			HttpServletRequest httpServletRequest = getRequest();
			Long eventId = Long.parseLong(httpServletRequest.getParameter("eventId"));
			Event event = (Event) service.find(Event.class, eventId.toString());

			eventService.modifyDealerOfEvent(eventId, UserContext.getUserInfo());
			EventStatus eventStatus = (EventStatus) eventService.findUnique(EventStatus.class, "keyword", "dealing");
			event.setEventStatus(eventStatus);
			
			//2010-08-09 add by huzh for ���з��������ݷ��������ʦȥ��֧���飻����ֻ���ݹ���ʦȥ��֧���� begin
			if(event.getScidData()!=null){
				List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(UserContext.getUserInfo(),event.getScidData());
				if(sgList!=null&&sgList.size()!=0){
					event.setSupportGroup(sgList.get(0));
				}
			}else{
				List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(UserContext.getUserInfo());
				if(sgList!=null&&sgList.size()!=0){
					event.setSupportGroup(sgList.get(0));
				}
			}
			//2010-08-09 add by huzh for ���з��������ݷ��������ʦȥ��֧���飻����ֻ���ݹ���ʦȥ��֧����end
			
			eventService.save(event);
			HashMap eventAssignMap = new HashMap();
			UserRole userRole = (UserRole) getService().find(UserRole.class, "userInfo", UserContext.getUserInfo())
					.get(0);
			Role role = userRole.getRole();
			eventAssignMap.put("roleId", role);
			eventAssignMap.put("event", event);
			eventAssignMap.put("assignUser", UserContext.getUserInfo());
			eventAssignMap.put("assignDate", new Date());
			eventAssignMap.put("remark", "���¼���" + UserContext.getUserInfo().getRealName() + "����!");
			metaDataManager.saveEntityData(EventAssign.class, eventAssignMap);
			super.getResponse().setContentType("text/plain");
			PrintWriter out = super.getResponse().getWriter();
			String json = "{success : true}";
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * �ж��¼��������С��޻����ǲ��ǵ�ǰ�����ˡ�
	 * 
	 * @Methods Name confirmTheEventDealUser
	 * @Create In Nov 2, 2009 By duxh
	 * @return String
	 */
	public String confirmTheEventDealUser() {
		try {
			String dealFlag = "";
			Event event = (Event) getService().find(Event.class, getRequest().getParameter("eventId"), true);
			UserInfo dealUser = event.getDealuser();
			UserInfo currentUser = UserContext.getUserInfo();
			if (dealUser == null)
				dealFlag = "none";
			else if (dealUser.getId().compareTo(currentUser.getId()) != 0) {
				dealFlag = "not";
			} else {
				dealFlag = "have";
			}
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = getResponse().getWriter();
			out.print("{success:true,dealFlag:'" + dealFlag + "'}");
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}

	/**
	 * ���ݹ�ϵ�¼�ȥ�¼�ID
	 * 
	 * @Methods Name relationEvent
	 * @Create In Mar 18, 2009 By daijf
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String relationEvent() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String evid = request.getParameter("eventId");//eventRelation��id
		Class clazz = EventRelation.class;
		EventRelation event = (EventRelation) service.find(clazz, evid);
//		String ev = event.getEvent().getId().toString();//event��id
		String pev = event.getParentEvent().getId().toString(); //parentEvent��id
		String pevalue = event.getParentEvent().getSummary();//parentEvent��summary
		String eventReletion = event.getEventRelationType().getId().toString();//eventRelationType��id
		String eventRelationValue = event.getEventRelationType().getName();//eventRelationType��name
		String result = "{success:true,ParentEventId:'" + pev + "',EventRelationId:'" + eventReletion + "',Pevalue:'"
				+ pevalue + "',EventRelationValue:'" + eventRelationValue + "'}";
		// System.out.println(result);
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		response.getWriter().write(result);
		writer.flush();
		return null;
	}

	/**
	 * ͨ�������id��ѯ��Ӧ���¼���
	 * 
	 * @Methods Name findEventByProblemId
	 * @Create In Nov 3, 2009 By duxh
	 * @return String
	 */
	public String findEventByProblemId() throws IOException {
		HttpServletRequest httpServletRequest = super.getRequest();
		String problemId = httpServletRequest.getParameter("problemId");
		Problem problem = (Problem) service.find(Problem.class, problemId, true);
		EventProblem eventProblem = (EventProblem) service.find(EventProblem.class, "problem", problem).get(0);
		String json = "";
		json = "{success : true, eventId : '" + eventProblem.getEvent().getId() + "'}";
		super.getResponse().setContentType("text/plain");
		super.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * ����������.
	 * 
	 * @Methods Name saveEventSolution
	 * @Create In Nov 3, 2009 By duxh
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveEventSolution() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelparam = request.getParameter("panelparam");
		String resolvent = request.getParameter("resolvent");
		String serviceItemId = request.getParameter("serviceItemId");
		String eventId = request.getParameter("eventId");
		Event event = new Event();
		event.setId(Long.parseLong(eventId));
		HashMap infoMap = new HashMap();
		String result = "";
		String eventDealTypeId="";
		String knowSubmitFlag="";//add by huzh for �Ƿ��ύ֪ʶ����
		JSONObject jo = JSONObject.fromObject(panelparam);
		Iterator itInfo = jo.keys();
		int k = 0;
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			//2010-06-25 add by huzh for  �¼�����ʽ begin
			if("Event$eventDealType".equals(key)){
				eventDealTypeId=jo.getString(key);
				continue;
			}
			if("enabled".equals(key)){//�Ƿ��ύ֪ʶ
				knowSubmitFlag=jo.getString(key);
				continue;
			}
			if("Know$SubmitFlag".equals(key)){
				continue;
			}
			//2010-06-25 add by huzh for  �¼�����ʽ end
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			if (k > 1) {
				infoMap.put(keyString[1], value);
			}
			k++;
		}
		ServiceItem si=new ServiceItem();
		si.setId(Long.valueOf(serviceItemId));
		infoMap.put("serviceItem", si);
		infoMap.put("createUser", UserContext.getUserInfo());
		infoMap.put("createDate", new Date());
		infoMap.put("resolvent", resolvent);
		infoMap.put("useTime", 0);
		
		// 2010-08-05 modified by huzh for ���ύ֪ʶ��yes����֪ʶ״̬Ϊ���ݸ塱������Ϊ�������䡱״̬ begin
		if("no".equals(knowSubmitFlag)){
			infoMap.put("status", Knowledge.STATUS_RENEW);	
		}else{
			infoMap.put("status", Knowledge.STATUS_DRAFT);
		}
		// 2010-08-05 modified by huzh for ���ύ֪ʶ��yes����֪ʶ״̬Ϊ���ݸ塱������Ϊ�������䡱״̬ end
		
		// 2010-04-14 add by huzh for ����¼��Խ����� begin
		Event newevent=(Event)service.findUnique(Event.class, "id", Long.parseLong(eventId));
		//2010-06-25 add by huzh for  ��ӡ��¼�����ʽ�� begin
		if("".equals(eventDealTypeId)==false){
			EventDealType dealType=new EventDealType();
			dealType.setId(Long.parseLong(eventDealTypeId));
			newevent.setEventDealType(dealType);
		}
		//2010-06-25 add by huzh for   ��ӡ��¼�����ʽ�� begin
		newevent.setSelfResolveFlag(Event.EVENT_SELFRESOLVE_TRUE);
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� begin
		if("no".equals(knowSubmitFlag)){
			newevent.setKnowSubmitFlag(Event.SUBMIT_NO);	
		}else{
			newevent.setKnowSubmitFlag(Event.SUBMIT_YES);
		}
		newevent.setKnowSendFlag(Event.SEND_YES);
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� end
		service.save(newevent);
		// 2010-04-14 add by huzh for ����¼��Խ����� end
		
		EventSulotion eventSulotion = (EventSulotion) service.findUnique(EventSulotion.class, "event", event);
		if (eventSulotion != null) {
			service.remove(eventSulotion);
			if (Knowledge.STATUS_DRAFT.equals(eventSulotion.getKnowledge().getStatus()))//2010-05-18 modified by huzh
				service.remove(eventSulotion.getKnowledge());
		}
		Knowledge knowledge = (Knowledge) metaDataManager.saveEntityData(Knowledge.class, infoMap);
		eventSulotion = new EventSulotion();
		eventSulotion.setEvent(event);
		eventSulotion.setKnowledge(knowledge);
		eventSulotion.setCreateDate(new Date());
		eventSulotion.setCreatName(UserContext.getUserInfo());
		eventService.save(eventSulotion);
		result = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}

	/**
	 * ��listData�е�map������ļ�ֵ��ת��Ϊjson��ʽ��ֻȡid��name
	 * 
	 * @Methods Name getJSONWithIdAndName
	 * @Create In Oct 27, 2009 By duxh
	 * @param listData
	 * @param total
	 * @return
	 * @return String
	 */
	public static String getJSONWithIdAndName(List<Map<String, Object>> listData, Long total) {
		StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
		for (int i = 0; i < listData.size(); i++) {
			if (i != 0)
				json.append(",");
			json.append("{");
			json.append("id:'" + listData.get(i).get("id") + "',");
			json.append("name:'" + listData.get(i).get("name") + "'");
			json.append("}");
		}
		json.append("]");
		json.append("}");
		return json.toString();
	}

	public void setSupportGroupService(SupportGroupService supportGroupService) {
		this.supportGroupService = supportGroupService;
	}

	/**
	 * cc�¼��ύ
	 * @Methods Name submitEventCC
	 * @Create In Jan 14, 2010 By duxh
	 * @return
	 * @throws Exception 
	 * @Return String
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String submitEventCC() throws Exception {
		String info = getRequest().getParameter("panelparam");
		String customerItcode = getRequest().getParameter("customerItcode");
		String callPhone = getRequest().getParameter("callPhone");
		String callName = getRequest().getParameter("callName");
		String department = getRequest().getParameter("department");
		String submitUserItcode = getRequest().getParameter("submitUserItcode");
		String callId = getRequest().getParameter("callId");
		String scidData = getRequest().getParameter("serviceItem");
		String engineerId = getRequest().getParameter("engineer");
		String result = null;
		try {
			UserInfo user=(UserInfo) getService().findUnique(UserInfo.class, "itcode", customerItcode.toUpperCase());
			UserInfo createUser=(UserInfo) getService().findUnique(UserInfo.class, "itcode", submitUserItcode.toUpperCase());
			HashMap infoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String[] keyString = key.split("\\$");
				String value = jo.getString(key);
				if("resolvent_other".equals(keyString[1])){
					infoMap.put("resolvent", value);
					continue;
				}
				infoMap.put(keyString[1], value);
			}
			ServiceItem sitem = (ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(scidData));
			infoMap.put("scidData", sitem);
			infoMap.put("scidType", sitem.getServiceItemType());
			infoMap.put("createUser", createUser);
			infoMap.put("submitUser", user);//����绰���û�
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			infoMap.put("submitDate", new Date());
			
			//2010-07-21 add by huzh for ���¼����֧���飺��ָ���˹���ʦ�����ָ������ʦ��֧���飬�������ѡ���ķ������ң�����һ����һ��û�еķ��ض�����begin
			if(engineerId!=null&&"".equals(engineerId.trim())==false){
				UserInfo engineer = new UserInfo();
				engineer.setId(Long.valueOf(engineerId));
				//2010-08-09 add by huzh for ���ݹ���ʦ�Լ�������ȥ��֧���� begin
//				List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer);
				List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer,sitem);
				if(sgList!=null&&sgList.size()!=0){
					infoMap.put("supportGroup", sgList.get(0));
				}
				//2010-08-09 add by huzh for ���ݹ���ʦ�Լ�������ȥ��֧���� end
			}else{
				List<SupportGroup> sgList=supportGroupService.findSupportGroupByServiceItem(sitem);
				if(sgList!=null&&sgList.size()!=0){
					infoMap.put("supportGroup", sgList.get(0));
				}
			}
			//2010-07-21 add by huzh for ���¼����֧���飺��ָ���˹���ʦ�����ָ������ʦ��֧���飬�������ѡ���ķ������ң�����һ����һ��û�еķ��ض�����end
			
			Event event = (Event) metaDataManager.saveEntityData(Event.class, infoMap);
			CCCallInfo cccallinfo=new CCCallInfo();
			cccallinfo.setCustomerItcode(customerItcode);
			cccallinfo.setSubmitUserItcode(submitUserItcode);
			cccallinfo.setCallName(callName);
			cccallinfo.setCallPhone(callPhone);
			cccallinfo.setCreateDate(new Date());
			cccallinfo.setDepartment(department);
			cccallinfo.setEvent(event);
			cccallinfo.setCallId(callId);
			cccallinfo.setMailFlag(CCCallInfo.MAILFLAG_NO);
			cccallinfo.setSelfFlag(CCCallInfo.SELFFLAG_NO);
			getService().save(cccallinfo);
			String userId="";
			if(engineerId==null||"".equals(engineerId)==true){
				List<SupportGroupEngineer> engineers = supportGroupService.findSupportGroupEngineer(sitem);
				if (engineers.size() == 0) {
					engineers = supportGroupService.findAllEngineerIn();
				} 
				for (SupportGroupEngineer supportEngineer : engineers) {
					userId += supportEngineer.getUserInfo().getId() + ",";
				}
				userId = userId.substring(0, userId.length() - 1);
			}else{
				userId=engineerId;
			}
			result = "{success:true,eventId:'" + event.getId() + "',userID:'" + userId + "',eventName:'"
					+ event.getSummary() + "',eventCisn:'" + event.getEventCisn() + "" + "',eventSubmitUser:'"
					+ event.getSubmitUser().getUserName() + "',eventSubmitDate:'"
					+ dateFormat.format(event.getSubmitDate()) + "'}";
		} catch (Exception e) {
			result = "{success:flase}";
			throw new ApplicationException();
		}
		super.getResponse().setContentType("text/plain");
		getResponse().setCharacterEncoding("gbk");
		PrintWriter writer = getResponse().getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}
	/**
	 * �ύ�¼�
	 * 
	 * @Methods Name commitEvent
	 * @Create In Oct 27, 2009 By duxh
	 * @throws IOException
	 * @return String
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	public String submitEvent() {
		StringBuilder json = new StringBuilder();
		String info = getRequest().getParameter("panelparam");
		//2010-04-15 add by huzh for �¼�����ҳ���޸� begin
//		String serviceTypeId = getRequest().getParameter("serviceType");
//		String serviceItemId = getRequest().getParameter("serviceItem");
		String eventtype = getRequest().getParameter("eventtype");
		String submitUserItcode = getRequest().getParameter("submitUserItcode");
		//2010-04-15 add by huzh for �¼�����ҳ���޸� end
		String scid = "";
		info = HttpUtil.ConverUnicode(info);
		Map infoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key).trim();
			infoMap.put(keyString[1], value);

		}
		infoMap.put("createUser", UserContext.getUserInfo());
		if(submitUserItcode!=null&&"".equals(submitUserItcode)==false){
			UserInfo submitUser=(UserInfo) getService().findUnique(UserInfo.class, "itcode", submitUserItcode.toUpperCase());
			infoMap.put("submitUser", submitUser);
		}else{
			infoMap.put("submitUser", UserContext.getUserInfo());
		}
		infoMap.put("submitDate", new Date());
		try {
			//2010-07-20 add by huzh for �����¼������ҵ����е�֧���飬����ֵΪ��һ�� begin
			if(eventtype!=null&&"".equals(eventtype.trim())==false){
				List<SupportGroup> groupList=supportGroupService.findSupportGroupByEventType(Long.valueOf(eventtype));
					if(groupList!=null&&groupList.size()!=0){
						infoMap.put("supportGroup", groupList.get(0));
				}
			}
			//2010-07-20 add by huzh for �����¼������ҵ����е�֧���飬����ֵΪ��һ�� end
			Event event = (Event) metaDataManager.saveEntityData(Event.class, infoMap);
			List<SupportGroupEngineer> engineers = supportGroupService.findSupportGroupEngineersByEventType(eventtype);
			String userId = "";
			if (engineers.size() == 0) {
				engineers = supportGroupService.findAllEngineerIn();
			} 
			for (SupportGroupEngineer supportEngineer : engineers) {
				userId += supportEngineer.getUserInfo().getId() + ",";
			}
			userId = userId.substring(0, userId.length() - 1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			json.append("{success:true,eventId:'" + event.getId());
			json.append("',userID:'" + userId + "',eventName:'" + event.getSummary());
			json.append("',eventCisn:'" + event.getEventCisn());
			json.append("',eventSubmitUser:'" + event.getSubmitUser().getUserName());
			json.append("',eventSubmitDate:'" + simpleDateFormat.format(event.getSubmitDate()) + "'}");
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}

	}
	/**
	 * �¼��ύ�����쳣�������ύ
	 * @Methods Name resubmitEvent
	 * @Create In Jun 4, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	public String resubmitEvent() {
		StringBuilder json = new StringBuilder();
		String info = getRequest().getParameter("panelparam");
		String serviceItemId = getRequest().getParameter("serviceItem");
		String eventtypeId = getRequest().getParameter("eventtype");
		String scid = "";
		info = HttpUtil.ConverUnicode(info);
		Map infoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key).trim();
			if (keyString[1].equals("eventType")||keyString[1].equals("scidData")) {
				continue;
			}
			infoMap.put(keyString[1], value);
		}
		try {
		ServiceItem si=new ServiceItem();
		List<SupportGroupEngineer> engineers=new ArrayList<SupportGroupEngineer>();
		if(serviceItemId!=null&&"".equals(serviceItemId)==false){
			Long siId=Long.parseLong(serviceItemId);
			 si=(ServiceItem) service.findUnique(ServiceItem.class, "id", siId);
			infoMap.put("scidData", siId);
			infoMap.put("scidType",si.getServiceItemType().getId());
			//2010-07-21 add by huzh for ��֧��������¼������з��������ݷ�������֧���� begin
			List<SupportGroup> sgList=supportGroupService.findSupportGroupByServiceItem(si);
			if(sgList!=null&&sgList.size()!=0){
				infoMap.put("supportGroup", sgList.get(0));
			}
			//2010-07-21 add by huzh for ��֧��������¼������з��������ݷ�������֧���� end
			engineers= supportGroupService.findSupportGroupEngineer(si);
		}else{
			//2010-07-20 add by huzh for �����¼�������֧���飬�������һ�� begin
			if(eventtypeId!=null&&"".equals(eventtypeId.trim())==false){
				List<SupportGroup> groupList=supportGroupService.findSupportGroupByEventType(Long.valueOf(eventtypeId));
					if(groupList!=null&&groupList.size()!=0){
						infoMap.put("supportGroup", groupList.get(0));
				}
			}
			//2010-07-20 add by huzh for �����¼�������֧���飬�������һ�� end
			engineers = supportGroupService.findSupportGroupEngineersByEventType(eventtypeId);
		}
			Event event = (Event) metaDataManager.saveEntityData(Event.class, infoMap);
			String userId = "";
			if (engineers.size() == 0) {
				engineers = supportGroupService.findAllEngineerIn();
			} 
			for (SupportGroupEngineer supportEngineer : engineers) {
				userId += supportEngineer.getUserInfo().getId() + ",";
			}
			userId = userId.substring(0, userId.length() - 1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			json.append("{success:true,eventId:'" + event.getId());
			json.append("',userID:'" + userId + "',eventName:'" + event.getSummary());
			json.append("',eventCisn:'" + event.getEventCisn());
			json.append("',eventSubmitUser:'" + event.getSubmitUser().getUserName());
			json.append("',eventSubmitDate:'" + simpleDateFormat.format(event.getSubmitDate()) + "'}");
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}

	}
	/**
	 * ����ʦ�����¼�
	 * @Methods Name engineerSubmitEvent
	 * @Create In May 19, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	@SuppressWarnings( { "unchecked" })
	public String engineerSubmitEvent() {
		StringBuilder json = new StringBuilder();
		String info = getRequest().getParameter("panelparam");
		//2010-05-19 modified by huzh begin
		String serviceItemId = getRequest().getParameter("serviceItem");
		//2010-05-19 modified by huzh end
		String submitUserItcode = getRequest().getParameter("submitUserItcode");
		info = HttpUtil.ConverUnicode(info);
		Map infoMap = new HashMap();
		JSONObject jo = JSONObject.fromObject(info);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			String[] keyString = key.split("\\$");
			String value = jo.getString(key).trim();
			infoMap.put(keyString[1], value);

		}
		//2010-05-19 delete by huzh 
		ServiceItem si=(ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(serviceItemId));
		infoMap.put("scidData", si);
		infoMap.put("scidType", si.getServiceItemType());
		//2010-05-19 delete by huzh 
		infoMap.put("createUser", UserContext.getUserInfo());
		if(submitUserItcode!=null&&"".equals(submitUserItcode)==false){
			String icString=submitUserItcode.toUpperCase();
			UserInfo submitUser=(UserInfo) service.findUnique(UserInfo.class, "itcode", icString.trim());
			infoMap.put("submitUser", submitUser);
		}else{
			infoMap.put("submitUser", UserContext.getUserInfo());
		}
		infoMap.put("submitDate", new Date());
		try {
			// �����¼�
			Event event = (Event) metaDataManager.saveEntityData(Event.class, infoMap);
//			// ���ҷ������Ӧ�Ĺ���ʦ
			List<SupportGroupEngineer> engineers = supportGroupService.findSupportGroupEngineer(si);//������Ҫ�Ż�(��ʱ��)
			// ׼����������ʦ����������������ݡ�
			String userId = "";
			if (engineers.size() == 0) {
				engineers = supportGroupService.findAllEngineerIn();
			} 
			for (SupportGroupEngineer supportEngineer : engineers) {
				userId += supportEngineer.getUserInfo().getId() + ",";
			}
			userId = userId.substring(0, userId.length() - 1);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			json.append("{success:true,eventId:'" + event.getId());
			json.append("',userID:'" + userId + "',eventName:'" + event.getSummary());
			json.append("',eventCisn:'" + event.getEventCisn());
			json.append("',eventSubmitUser:'" + event.getSubmitUser().getUserName());
			json.append("',eventSubmitDate:'" + simpleDateFormat.format(event.getSubmitDate()) + "'}");
			HttpServletResponse response = getResponse();
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}

	}
	public String saveServiceItemAndServiceItemType(){
		String serviceItem = getRequest().getParameter("serviceItemId");
		String eventId = getRequest().getParameter("eventId");
		String frequency = getRequest().getParameter("frequency");
		String ponderance = getRequest().getParameter("ponderance");
		Event event=(Event) service.findUnique(Event.class, "id", Long.parseLong(eventId));
		if(frequency!=null&&"".equals(frequency)==false){
	//	private EventPonderance
		EventFrequency fre=new EventFrequency();
			fre.setId(Long.parseLong(frequency));
			event.setFrequency(fre);
		}
		if(ponderance!=null&&"".equals(ponderance)==false){
			EventPonderance pon=new EventPonderance();
			pon.setId(Long.parseLong(ponderance));
			event.setPonderance(pon);
		}
		ServiceItem eventSI=event.getScidData();
		Long serviceItemId=Long.valueOf(serviceItem);
		if(eventSI==null||(eventSI!=null&&eventSI.getId().compareTo(serviceItemId)!=0)){
			ServiceItem si=(ServiceItem) service.findUnique(ServiceItem.class, "id", serviceItemId);
			event.setScidData(si);
			event.setScidType(si.getServiceItemType());
			service.save(event);
		}
		String json = "{success : true}";
		try {
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String findAllEventTypeByName(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String typeName = getRequest().getParameter("typeName");
		String ty = getRequest().getParameter("type");
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		Page page=null;
		if(ty!=null&&"combox".equals(ty)==true){//2010-05-31 modified by huzh for bug
			 page=eventService.findAllEventTypeByName(typeName,pageNo,pageSize);
		}else{
			page =eventService.findAllEventTypeByName(typeName,pageNo,pageSize);
		}
		Long total=page.getTotalCount();
		List typeList=page.list();
		StringBuilder json = new StringBuilder("{success: true,rowCount:"+total+",data:[");
		if(typeList!=null&&typeList.size()!=0){
			for(int i=0;i<typeList.size();i++){
				EventType type=(EventType) typeList.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + type.getId() + "',");
				json.append("name:'" + type.getName()+ "'");
				json.append("}");	
			}
		}
		json.append("]");
		json.append("}");
		response.setContentType("text/plain;charset=gbk");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String findAllEventTypeServiceItem() throws IOException {
		String dataId = super.getRequest().getParameter("dataId");
		 List<EventTypeServiceItem> list = eventService.findEventTypeServiceItem(dataId);
			try {
				String json = "";
				for (EventTypeServiceItem es : list) {
					json += "[";
					json += "'" + es.getServiceItem().getId() + "',";
					json += "'" + es.getServiceItem().getName() + "'";
					json += "],";
				}
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
				json = "[" + json + "]";
				HttpServletResponse response = super.getResponse();
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(json);
				writer.flush();
				return null;
			} catch (RuntimeException e) {
				HttpServletResponse response = super.getResponse();
				e.printStackTrace();
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write("{success:false}");
				writer.flush();
				return null;
			}

	}
	/**
	 * ��ѯ���еķ�����
	 * @Methods Name findAllServiceItem
	 * @Create In May 28, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	public String findAllServiceItem() throws IOException {
		String official = super.getRequest().getParameter("official");
		List<ServiceItem> list = eventService.findAllServiceItem(official);
		try {
			String json = "";
			for (ServiceItem serviceItem : list) {
				json += "[";
				json += "'" + serviceItem.getId() + "',";
				json += "'" + serviceItem.getName() + "'";
				json += "],";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "[" + json + "]";
			HttpServletResponse response = super.getResponse();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			HttpServletResponse response = super.getResponse();
			e.printStackTrace();
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}
	/**
	 * �����¼������������
	 * @Methods Name saveOrModifyEventType
	 * @Create In May 21, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	@SuppressWarnings("unchecked")
	public String saveOrModifyEventType() throws IOException {
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String name = request.getParameter("name");
			String serviceItemsIdString = request.getParameter("serviceItemsId");
			String dataString = request.getParameter("dataId");
			String json="";
			Long dataId=null;
			if(dataString.trim().length()!=0){
				dataId = Long.parseLong(dataString);
			}
			if(serviceItemsIdString==null||"".equals(serviceItemsIdString)==true){
				EventType et=new EventType();
				et.setName(name);
				if(dataId!=null){
					et.setId(dataId);
				}
				service.save(et);
			}else{
				String[] serviceItemsIdTemp=serviceItemsIdString.split(",");
				Long[] serviceItemsId=new Long[serviceItemsIdTemp.length];
				for(int i=0;i<serviceItemsIdTemp.length;i++){
					serviceItemsId[i]=Long.parseLong(serviceItemsIdTemp[i]);
				}
				List<String> serviceItemsName=eventService.findSIinEventTypeServiceItem(serviceItemsId,dataId);
				if(!serviceItemsName.isEmpty()){
					json=serviceItemsName.toString().substring(1,serviceItemsName.toString().length()-1);
				}else{
					EventType et=new EventType();
					et.setName(name);
					if(dataId!=null){
						et.setId(dataId);
						eventService.dropAllEventTypeServiceItem(dataId);
					}
					service.save(et);
				    for(int i=0;i<serviceItemsId.length;i++){
				         ServiceItem serviceItem=new ServiceItem();
				         serviceItem.setId(serviceItemsId[i]);
						EventTypeServiceItem etsi=new EventTypeServiceItem();
						etsi.setEventType(et);
						etsi.setServiceItem(serviceItem);
						service.save(etsi);
				    }
			    }
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			writer.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * ����ɾ���¼����ͼ��������Ĺ���ʵ��
	 * @Methods Name removeEventType
	 * @Create In May 22, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String removeEventType(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String typeId=request.getParameter("id");
		EventType eventType=(EventType) service.findUnique(EventType.class, "id", Long.parseLong(typeId));
		List<EventTypeServiceItem> etsiList=service.find(EventTypeServiceItem.class, "eventType", eventType);
		if(etsiList!=null&&etsiList.size()!=0){
			for(EventTypeServiceItem etsi:etsiList){
				service.remove(etsi);
			}
		}
		service.remove(eventType);
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write("{success:true}");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ѯ�������¼����͹���ʵ��
	 * @Methods Name findAllServiceItemEventType
	 * @Create In May 22, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findAllServiceItemEventType(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String typeName = getRequest().getParameter("typeName");
		String serviceItemId = getRequest().getParameter("siId");
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		Page page=eventService.findAllServiceItemEventType(typeName,serviceItemId,pageNo,pageSize);
		Long total=page.getTotalCount();
		List etsiList=page.list();
		StringBuilder json = new StringBuilder("{success: true,"+"rowCount:"+total+",data:[");
		if(etsiList!=null&&etsiList.size()!=0){
			for(int i=0;i<etsiList.size();i++){
				EventTypeServiceItem se=(EventTypeServiceItem) etsiList.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("siCode:'" + se.getServiceItem().getServiceItemCode()+ "',");
				json.append("siName:'" + se.getServiceItem().getName()+ "',");
				json.append("typeName:'" + se.getEventType().getName()+ "'");
				json.append("}");	
			}
		}
		json.append("]");
		json.append("}");
		response.setContentType("text/plain;charset=gbk");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name findAllEngineersByServiceItem
	 * @Create In May 19, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findAllEngineersByServiceItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String name = getRequest().getParameter("name");
		String serviceItemId = getRequest().getParameter("serviceItemId");
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		Page page=eventService.findAllEngineersByServiceItem(name,serviceItemId,pageNo, pageSize);
		Long total=page.getTotalCount();
		List engineers=page.list();
		StringBuilder json = new StringBuilder("{success: true,rowCount:"+total+",data:[");
		if(engineers!=null&&engineers.size()!=0){
//			int s=engineers.size();
			for(int i=0;i<engineers.size();i++){
				UserInfo engineer=(UserInfo) engineers.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + engineer.getId() + "',");
				json.append("name:'" + engineer.getRealName()+"/"+engineer.getUserName()+"/"+engineer.getDepartment().getDepartName()+"'");
				json.append("}");	
			}
			json.append(",");
			json.append("{");
			json.append("id:'',");
			json.append("name:'ȫ��'");
			json.append("}");
		}
		json.append("]");
		json.append("}");
		response.setContentType("text/plain;charset=gbk");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ͨ���¼����Ͳ�ѯ������
	 * @Methods Name findServiceItemByEventType
	 * @Create In Jun 4, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findServiceItemByEventType(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String eventTypeId = getRequest().getParameter("eventtype");
		String serviceName = getRequest().getParameter("name");
		String official = getRequest().getParameter("official");
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 1);
		int pageNo = start / pageSize + 1;
		Page page=eventService.findServiceItemByEventType(eventTypeId,serviceName,official,pageNo,pageSize);
		Long total=page.getTotalCount();
		List siList=page.list();
		StringBuilder json = new StringBuilder("{success: true,"+"rowCount:"+total+",data:[");
		if(siList!=null&&siList.size()!=0){
			for(int i=0;i<siList.size();i++){
				ServiceItem si=(ServiceItem) siList.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + si.getId()+ "',");
				json.append("name:'" + si.getName()+ "'");
				json.append("}");	
			}
		}
		json.append("]");
		json.append("}");
		response.setContentType("text/plain;charset=gbk");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �¼�ȷ��ҳ�汣��������
	 * @Methods Name saveEventSolutionForEngineer
	 * @Create In Jun 10, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	@SuppressWarnings("unchecked")
	public String saveEventSolutionForEngineer() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelparam = request.getParameter("panelparam");
		String resolvent=request.getParameter("resolvent");
		String eventId = request.getParameter("eventId");
		String serviceItemId = request.getParameter("serviceItemId");
		String problemSortId = request.getParameter("problemSortId");
		String frequencyId = request.getParameter("frequency");
		String ponderanceId = request.getParameter("ponderance");
		String desc = request.getParameter("desc");
		HashMap infoMap = new HashMap();
		String knowSubmitFlag="";//add by huzh for �Ƿ��ύ֪ʶ����
		String result = "";
		String dealTypeId="";//add by huzh for �¼�����ʽ
		JSONObject jo = JSONObject.fromObject(panelparam);
		Iterator itInfo = jo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			// 2010-08-05 modified by huzh for ���ύ֪ʶ��yes����֪ʶ״̬Ϊ���ݸ塱������Ϊ�������䡱״̬ begin
			if("enabled".equals(key)){//�Ƿ��ύ֪ʶ
				knowSubmitFlag=jo.getString(key);
				continue;
			}
			String[] keyString = key.split("\\$");
			String value = jo.getString(key);
			if("eventDealType".equals(keyString[1])){//add by huzh for �¼�����ʽ
				dealTypeId=value;
				continue;
			}
			if(keyString[1].equals("resolvent")
						||keyString[1].equals("SubmitFlag")){
				continue;
			}
			// 2010-08-05 modified by huzh for ���ύ֪ʶ��yes����֪ʶ״̬Ϊ���ݸ塱������Ϊ�������䡱״̬ end
			infoMap.put(keyString[1], value);
		}
		infoMap.put("createUser", UserContext.getUserInfo());
		infoMap.put("createDate", new Date());
		infoMap.put("resolvent", resolvent);
		infoMap.put("useTime", 0);
		
		// 2010-08-05 modified by huzh for ���ύ֪ʶ��yes����֪ʶ״̬Ϊ���ݸ塱������Ϊ�������䡱״̬ begin
		if("no".equals(knowSubmitFlag)){
			infoMap.put("status", Knowledge.STATUS_RENEW);	
		}else{
			infoMap.put("status", Knowledge.STATUS_DRAFT);
		}
		// 2010-08-05 modified by huzh for ���ύ֪ʶ��yes����֪ʶ״̬Ϊ���ݸ塱������Ϊ�������䡱״̬ end
		
		Event newevent=(Event)service.findUnique(Event.class, "id", Long.parseLong(eventId));
		if(serviceItemId!=null&&"".equals(serviceItemId)==false){
			ServiceItem sitem = (ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(serviceItemId));
			infoMap.put("serviceItem", sitem);
			newevent.setScidData(sitem);
			newevent.setScidType(sitem.getServiceItemType());
		}
		if(problemSortId!=null&&"".equals(problemSortId)==false){
			ProblemSort problemSort=new ProblemSort();
			problemSort.setId(Long.valueOf(problemSortId));
			newevent.setProblemSort(problemSort);
		}
		if(frequencyId!=null&&"".equals(frequencyId)==false){
			EventFrequency fre=new EventFrequency();
			fre.setId(Long.parseLong(frequencyId));
			newevent.setFrequency(fre);
		}
		if(ponderanceId!=null&&"".equals(ponderanceId)==false){
			EventPonderance pon=new EventPonderance();
			pon.setId(Long.parseLong(ponderanceId));
			newevent.setPonderance(pon);
		}
		if("".equals(dealTypeId)==false){//add by huzh for �¼�����ʽ
			EventDealType dealType=new EventDealType();
			dealType.setId(Long.parseLong(dealTypeId));
			newevent.setEventDealType(dealType);
		}
		newevent.setSelfResolveFlag(Event.EVENT_SELFRESOLVE_TRUE);
		newevent.setDescription(desc);
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� begin
		if("no".equals(knowSubmitFlag)){
			newevent.setKnowSubmitFlag(Event.SUBMIT_NO);
		}else{
			newevent.setKnowSubmitFlag(Event.SUBMIT_YES);
		}
		newevent.setKnowSendFlag(Event.SEND_YES);
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� end
		service.save(newevent);
		
		EventSulotion eventSulotion = (EventSulotion) service.findUnique(EventSulotion.class, "event", newevent);
		if (eventSulotion != null) {
			service.remove(eventSulotion);
			if (Knowledge.STATUS_DRAFT.equals(eventSulotion.getKnowledge().getStatus()))//2010-05-18 modified by huzh
				service.remove(eventSulotion.getKnowledge());
		}
		Knowledge knowledge = (Knowledge) metaDataManager.saveEntityData(Knowledge.class, infoMap);
		eventSulotion = new EventSulotion();
		eventSulotion.setEvent(newevent);
		eventSulotion.setKnowledge(knowledge);
		eventSulotion.setCreateDate(new Date());
		eventSulotion.setCreatName(UserContext.getUserInfo());
		eventService.save(eventSulotion);
		result = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}
	/**
	 * �����¼��¼�������������¼�����Ƶ���Լ�������,�������
	 * @Methods Name saveEventDescInDealing
	 * @Create In Jul 7, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	public String saveEventDescInDealing() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String eventId = request.getParameter("eventId");
		String serviceItemId = request.getParameter("serviceItemId");
		String frequencyId = request.getParameter("frequency");
		String ponderanceId = request.getParameter("ponderance");
		String desc = request.getParameter("desc");
		String problemSortId = request.getParameter("problemSortId");
		String result = "";
		Event newevent=(Event)service.findUnique(Event.class, "id", Long.parseLong(eventId));
		if(serviceItemId!=null&&"".equals(serviceItemId.trim())==false){
			ServiceItem sitem = (ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(serviceItemId));
			newevent.setScidData(sitem);
			newevent.setScidType(sitem.getServiceItemType());
		}
		if(problemSortId!=null&&"".equals(problemSortId.trim())==false){
			ProblemSort problemSort = new ProblemSort();
			problemSort.setId(Long.valueOf(problemSortId));
			newevent.setProblemSort(problemSort);
		}
		if(frequencyId!=null&&"".equals(frequencyId.trim())==false){
			EventFrequency fre=new EventFrequency();
			fre.setId(Long.parseLong(frequencyId));
			newevent.setFrequency(fre);
		}
		if(ponderanceId!=null&&"".equals(ponderanceId.trim())==false){
			EventPonderance pon=new EventPonderance();
			pon.setId(Long.parseLong(ponderanceId));
			newevent.setPonderance(pon);
		}
		newevent.setDescription(desc);
		Event ev=(Event) getService().save(newevent);
		result = "{success:true,event:'"+ev.getDescription()+"'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}
	/**
	 * ͨ����Ӧ������ѯ��������
	 * @Methods Name findproblemTypeTypeBySI
	 * @Create In Jul 1, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	public String findproblemTypeTypeBySI() throws IOException{
		HttpServletResponse response = super.getResponse();
		int start = HttpUtil.getInt(getRequest(), "start",0);
		int pageSize = HttpUtil.getInt(getRequest(), "pageSize",10);
		int pageNo = start/pageSize+1;
		String siTypeName = getRequest().getParameter("name");
		String typeName = (siTypeName==null?"":siTypeName);
		String siId = getRequest().getParameter("serviceItemId");
		Long serviceItemId=null;
		if(siId!=null&&"".equals(siId.trim())){
			serviceItemId=Long.valueOf(siId);
		}
		Long total=1L;
		Page page=eventService.findproblemTypeByServiceItem(typeName,serviceItemId, pageNo, pageSize);
		List<KnowProblemType> list = page.list();		
		 total = page.getTotalCount();
		 StringBuilder json = new StringBuilder("{success: true,"+"rowCount:"+total+",data:[");
			if(list!=null&&list.size()!=0){
				for(int i=0;i<list.size();i++){
					KnowProblemType type=(KnowProblemType) list.get(i);
					if (i != 0)
						json.append(",");
					json.append("{");
					json.append("id:'" + type.getId()+ "',");
					json.append("name:'" + type.getName()+ "'");
					json.append("}");	
				}
			}
			json.append("]");
			json.append("}");
			response.setContentType("text/plain;charset=gbk");
			try {
				PrintWriter out = response.getWriter();
				out.print(json.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	}
	/**
	 * ����֧�����鳤��ѯ����������ķ�����
	 * @Methods Name findServiceItemByGroupLeader
	 * @Create In Jul 5, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findServiceItemByGroupLeader() {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		try {
			int pageSize = HttpUtil.getInt(request, "pageSize", 10);
			int start = HttpUtil.getInt(request, "start", 0);
			int pageNo = start / pageSize + 1;
			String name=HttpUtil.getString(request, "name","");
			String official=HttpUtil.getString(request, "official","1");
			
			String adminFlag= this.isSystemAdmin(UserContext.getAuthorities()) ? "yes" : "no";
			
			UserInfo userInfo=UserContext.getUserInfo();
			Long total=1L;
			Long userId=(userInfo==null)?Long.valueOf("0"):userInfo.getId();
			Page page=eventService.findServiceItemByGroupLeader(name,Integer.valueOf(official),userId, adminFlag,pageNo, pageSize);
			List<ServiceItem> list = page.list();
			if(list!=null&&list.size()!=0){
				total = page.getTotalCount();
			}
			 StringBuilder json = new StringBuilder("{success: true,"+"rowCount:"+total+",data:[");
				if(list!=null&&list.size()!=0){
					for(int i=0;i<list.size();i++){
						ServiceItem si=(ServiceItem) list.get(i);
						if (i != 0)
							json.append(",");
						json.append("{");
						json.append("id:'" + si.getId()+ "',");
						json.append("name:'" + si.getName()+ "'");
						json.append("}");	
					}
				}
				json.append("]");
				json.append("}");
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		return null;
	}
	/**
	 * ����ʦ�����¼�ҳ���¼��Լ������������
	 * @Methods Name saveEventSulotionForEngineer
	 * @Create In Jul 14, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	@SuppressWarnings("unchecked")
	public String saveEventSulotionForEngineer() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String knowInfo = request.getParameter("knowparam");
		String resolvent = request.getParameter("resolvent");
		String eventInfo = request.getParameter("eventparam");
		String serviceItemId = request.getParameter("serviceItem");
		String customerItcode = request.getParameter("customerItcode");
		UserInfo customer=(UserInfo) getService().findUnique(UserInfo.class, "itcode", customerItcode);
		String knowSubmitFlag="";//�Ƿ��ύ֪ʶ�������
		String eventDealTypeId="";//�¼�����ʽID
		HashMap infoMap = new HashMap();
		JSONObject knowjo = JSONObject.fromObject(knowInfo);
		Iterator itInfo = knowjo.keys();
		while (itInfo.hasNext()) {
			String key = (String) itInfo.next();
			if("enabled".equals(key)){//�Ƿ��ύ֪ʶ
				knowSubmitFlag=knowjo.getString(key);
				continue;
			}
			String[] keyString = key.split("\\$");
			if(keyString[1].equals("engineer")
					||keyString[1].equals("scidData")
						||keyString[1].equals("SubmitFlag")
							||keyString[1].equals("resolvent_other")){
				continue;
			}
			if(keyString[1].equals("eventDealType")){//�¼�����ʽ
				eventDealTypeId=knowjo.getString(key);
				continue;
			}
			String	value = knowjo.getString(key);
			infoMap.put(keyString[1], value);
		}
		infoMap.put("resolvent",resolvent);
		infoMap.put("createUser",UserContext.getUserInfo());
		infoMap.put("createDate", new Date());
		infoMap.put("useTime", 1);
		if("no".equals(knowSubmitFlag)){//�Ƿ��ύ֪ʶ��֪ʶ״̬��ͬ
			infoMap.put("status", Knowledge.STATUS_RENEW);	
		}else{
			infoMap.put("status", Knowledge.STATUS_APPROVING);
		}
		ServiceItem si=(ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(serviceItemId));
		infoMap.put("serviceItem", si);
		Knowledge knowledge = (Knowledge) metaDataManager.saveEntityData(Knowledge.class, infoMap);
		HashMap eventMap = new HashMap();
		JSONObject eventjo = JSONObject.fromObject(eventInfo);
		Iterator evIterator = eventjo.keys();
		while (evIterator.hasNext()) {
			String key = (String) evIterator.next();
			String[] keyString = key.split("\\$");
			String value = eventjo.getString(key);
			eventMap.put(keyString[1], value);
		}
		if("".equals(eventDealTypeId)==false){
			EventDealType dealtype= new EventDealType();
			dealtype.setId(Long.parseLong(eventDealTypeId));
			eventMap.put("eventDealType", dealtype);
		}
		eventMap.put("scidData", si);
		eventMap.put("scidType", si.getServiceItemType());
		eventMap.put("submitUser",customer);
		eventMap.put("createUser", UserContext.getUserInfo());
		eventMap.put("eventStatus", getService().findUnique(EventStatus.class, "keyword",EventStatus.FINISH));
		eventMap.put("submitDate", new Date());
		eventMap.put("closedDate", new Date());
		eventMap.put("selfResolveFlag", Event.EVENT_SELFRESOLVE_TRUE);
		eventMap.put("dealuser", UserContext.getUserInfo());
		
//		//2010-07-21 add by huzh for ���ݴ�����ʦ���¼����֧���飨����һ����һ��û�еķ��ض�����begin
//		List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(UserContext.getUserInfo());
//		if(sgList!=null&&sgList.size()!=0){
//			eventMap.put("supportGroup", sgList.get(0));
//		}
//		//2010-07-21 add by huzh for ���ݴ�����ʦ���¼����֧���飨����һ����һ��û�еķ��ض�����end
		//2010-08-09 add by huzh for ���ݴ�����ʦ�Լ�������ȥ��֧���� begin
		List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(UserContext.getUserInfo(),si);
		if(sgList!=null&&sgList.size()!=0){
			eventMap.put("supportGroup", sgList.get(0));
		}
		//2010-08-09 add by huzh for ���ݴ�����ʦ�Լ�������ȥ��֧���� end
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� begin
		if("no".equals(knowSubmitFlag)){//�Ƿ��ύ֪ʶ
			eventMap.put("knowSubmitFlag", Event.SUBMIT_NO);
		}else{
			eventMap.put("knowSubmitFlag", Event.SUBMIT_YES);
		}
		eventMap.put("knowSendFlag", Event.SEND_YES);
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� end
		Event event = (Event) metaDataManager.saveEntityData(Event.class, eventMap);
		EventSulotion eventsulotion = new EventSulotion();
		eventsulotion.setCreateDate(new Date());
		eventsulotion.setCreatName(UserContext.getUserInfo());
		eventsulotion.setEvent(event);
		eventsulotion.setKnowledge(knowledge);
		super.getService().save(eventsulotion);
		KnowledgeType knowledgeType = (KnowledgeType) getService().findUnique(KnowledgeType.class, "className",
										"com.zsgj.itil.knowledge.entity.Knowledge");
		String json = "{success:true,createUser:'" + UserContext.getUserInfo().getId() + "',knowledgeId:'"
		+ knowledge.getId() + "',knowledgeTypeId:'" + knowledgeType.getId() + "'}";
		//��������ȵ����ʼ�
		String subject="IT��ܰ��ʾ:"+event.getSubmitUser().getRealName()+"/"+event.getSubmitUser().getUserName()+"������ġ�"+event.getSummary()+"���Ľ����������д����ȵ��顣";
		String url = PropertiesUtil.getProperties("system.web.url","localhost:8080") + "/user/event/eventNormal/userconfirmForSatisfy.jsp?dataId="+event.getId()+"&knowledgeId="+knowledge.getId();
		SendMailThred satisfyThread =new SendMailThred(ms,event.getSubmitUser().getEmail(), "", null, subject, this.eventHtmlSatisfyContent(event.getSubmitUser(), url, event), null);
		Thread th = new Thread(satisfyThread);
	    th.start();
	    
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(json);
		writer.flush();
		writer.close();
		return null;

	}
	/**
	 * ����ʦ�����¼�ҳ��ʹ��֪ʶ������������¼�
	 * @Methods Name endEventWithValidKnowledgeForEngineer
	 * @Create In Jul 14, 2010 By huzh
	 * @return
	 * @throws IOException 
	 * @Return String
	 */
	@SuppressWarnings("unchecked")
	public String endEventWithValidKnowledgeForEngineer() throws IOException {
		HttpServletRequest request = super.getRequest();
		String knowledgeId = request.getParameter("knowledgeId");
		String customerItcode = request.getParameter("customerItcode");
		UserInfo customer=(UserInfo) getService().findUnique(UserInfo.class, "itcode", customerItcode.toUpperCase());
		Knowledge knowledge= (Knowledge) getService().find(Knowledge.class,knowledgeId);
		knowledge.setUseTime(knowledge.getUseTime()+1);
		
		getService().save(knowledge);
		
		Map eventMap = new HashMap();
		UserInfo engineer =UserContext.getUserInfo();
		eventMap.put("summary", knowledge.getSummary());
		eventMap.put("scidData", knowledge.getServiceItem());
		eventMap.put("scidType", knowledge.getServiceItem().getServiceItemType());
		eventMap.put("submitUser",customer);
		eventMap.put("createUser", engineer);
		eventMap.put("dealuser", engineer);
		eventMap.put("eventStatus", getService().findUnique(EventStatus.class, "keyword",EventStatus.FINISH));
		eventMap.put("submitDate", new Date());
		eventMap.put("closedDate", new Date());
		
//		//2010-07-21 add by huzh for ���ݴ�����ʦ���¼����֧���飨����һ����һ��û�еķ��ض�����begin
//		List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer);
//		if(sgList!=null&&sgList.size()!=0){
//			eventMap.put("supportGroup", sgList.get(0));
//		}
//		//2010-07-21 add by huzh for ���ݴ�����ʦ���¼����֧���飨����һ����һ��û�еķ��ض�����end
		//2010-08-09 add by huzh for ���ݴ�����ʦ��������ȥ��֧���� begin
		List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer,knowledge.getServiceItem());
		if(sgList!=null&&sgList.size()!=0){
			eventMap.put("supportGroup", sgList.get(0));
		}
		//2010-08-09 add by huzh for ���ݴ�����ʦ��������ȥ��֧���� end
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� begin
		eventMap.put("knowSubmitFlag", Event.SUBMIT_NO);
		eventMap.put("knowSendFlag", Event.SEND_YES);
		//2010-09-08 add by huzh for ��ӡ��Ƿ��ύ֪ʶ�������Ƿ��������ͽ����������� end
		Event event = (Event) metaDataManager.saveEntityData(Event.class, eventMap);
		EventSulotion eventsulotion = new EventSulotion();
		eventsulotion.setCreateDate(new Date());
		eventsulotion.setCreatName(engineer);
		eventsulotion.setEvent(event);
		eventsulotion.setKnowledge(knowledge);
		super.getService().save(eventsulotion);
		
		//2010-07-20 add by huzh for �������û����ͽ������ begin
		String subject="IT��ܰ��ʾ:"+event.getSubmitUser().getRealName()+"/"+event.getSubmitUser().getUserName()+"������ġ�"+event.getSummary()+"���Ľ����������д����ȵ��顣";
		String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") + "/user/event/eventNormal/userconfirmForSatisfy.jsp?dataId="+event.getId()+"&knowledgeId="+knowledge.getId();
		SendMailThred satisfyThread =new SendMailThred(ms,event.getSubmitUser().getEmail(), "", null, subject, this.eventHtmlSatisfyContent(event.getSubmitUser(), url, event), null);
		Thread th = new Thread(satisfyThread);
	    th.start();
	    //2010-07-20 add by huzh for �������û����ͽ������ end
		return null;
	}
	/**
	 * ����ʦ�����¼�ҳ���ύ��������ʦ����
	 * @Methods Name submitEventForEngineer
	 * @Create In Jul 14, 2010 By huzh
	 * @return
	 * @throws Exception 
	 * @Return String
	 */
	@SuppressWarnings("unchecked")
	public String submitEventForEngineer() throws Exception {
		String info = getRequest().getParameter("panelparam");
		String customerItcode = getRequest().getParameter("customerItcode");
		String scidData = getRequest().getParameter("serviceItem");
		String engineerId = getRequest().getParameter("engineer");
		String result = null;
		try {
			UserInfo user=(UserInfo) getService().findUnique(UserInfo.class, "itcode", customerItcode.toUpperCase());
			HashMap infoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String[] keyString = key.split("\\$");
				String value = jo.getString(key);
				if("resolvent_other".equals(keyString[1])){
					infoMap.put("resolvent", value);
					continue;
				}
				infoMap.put(keyString[1], value);
			}
			ServiceItem sitem = (ServiceItem) service.findUnique(ServiceItem.class, "id", Long.parseLong(scidData));
			infoMap.put("scidData", sitem);
			infoMap.put("scidType", sitem.getServiceItemType());
			infoMap.put("createUser", UserContext.getUserInfo());
			infoMap.put("submitUser", user);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			infoMap.put("submitDate", new Date());
			
			//2010-07-21 add by huzh for ���¼����֧���飺��ָ���˹���ʦ�����ָ������ʦ��֧���飬�������ѡ���ķ������ң�����һ����һ��û�еķ��ض�����begin
			if(engineerId!=null&&"".equals(engineerId.trim())==false){
				UserInfo engineer = new UserInfo();
				engineer.setId(Long.valueOf(engineerId));
//				List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer);
				List<SupportGroup> sgList=supportGroupService.findSupportGroupByEngineer(engineer,sitem);//2010-08-09 modified by huzh for ���ݷ��������ʦȥ��֧����
				if(sgList!=null&&sgList.size()!=0){
					infoMap.put("supportGroup", sgList.get(0));
				}
			}else{
				List<SupportGroup> sgList=supportGroupService.findSupportGroupByServiceItem(sitem);
				if(sgList!=null&&sgList.size()!=0){
					infoMap.put("supportGroup", sgList.get(0));
				}
			}
			//2010-07-21 add by huzh for ���¼����֧���飺��ָ���˹���ʦ�����ָ������ʦ��֧���飬�������ѡ���ķ������ң�����һ����һ��û�еķ��ض�����end
			
			Event event = (Event) metaDataManager.saveEntityData(Event.class, infoMap);
			String userId="";
			if(engineerId==null||"".equals(engineerId)==true){
				List<SupportGroupEngineer> engineers = supportGroupService.findSupportGroupEngineer(sitem);
				if (engineers.size() == 0) {
					engineers = supportGroupService.findAllEngineerIn();
				} 
				for (SupportGroupEngineer supportEngineer : engineers) {
					userId += supportEngineer.getUserInfo().getId() + ",";
				}
				userId = userId.substring(0, userId.length() - 1);
			}else{
				userId=engineerId;
			}
			result = "{success:true,eventId:'" + event.getId() + "',userID:'" + userId + "',eventName:'"
					+ event.getSummary() + "',eventCisn:'" + event.getEventCisn() + "" + "',eventSubmitUser:'"
					+ event.getSubmitUser().getUserName() + "',eventSubmitDate:'"
					+ dateFormat.format(event.getSubmitDate()) + "'}";
		} catch (Exception e) {
			result = "{success:flase}";
			throw new ApplicationException();
		}
		super.getResponse().setContentType("text/plain");
		getResponse().setCharacterEncoding("gbk");
		PrintWriter writer = getResponse().getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}
	/**
	 * ���ݲ�����ѯ�����¼�
	 * @Methods Name findAllEventByParams
	 * @Create In Jul 21, 2010 By huzh
	 * @return 
	 * @Return String
	 */
	public String findAllEventByParams(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String summary = HttpUtil.getString(getRequest(), "summary", "");
		String eventCisn = HttpUtil.getString(getRequest(), "eventCisn", "");
		String createUser = HttpUtil.getString(getRequest(), "createUser", "");
		String dealUser = HttpUtil.getString(getRequest(), "dealUser", "");
		String submitUser = HttpUtil.getString(getRequest(), "submitUser", "");
		String eventStatusId =request.getParameter("eventStatus");
		String supportGroupId =request.getParameter("supportGroup");
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		Page page=eventService.findAllEventByParams(summary,eventCisn,createUser,dealUser,submitUser,eventStatusId,pageNo,supportGroupId,pageSize);
		Long total=page.getTotalCount();
		List eventList=page.list();
		StringBuilder json = new StringBuilder("{success: true,"+"rowCount:"+total+",data:[");
		if(eventList!=null&&eventList.size()!=0){
			for(int i=0;i<eventList.size();i++){
				if(i != 0){
					json.append(",");
				}	
					Event event = (Event) eventList.get(i);
					String dUser = "";
					String sUser = "";
					String cUser = "";
					String ponderance = "";
					String frequency = "";
					String cDate = "";
					String sDate = "";
					String status = "";
					String groupName="";
					if(event.getDealuser() !=null){
						dUser = event.getDealuser().getRealName()+"/"+event.getDealuser().getUserName();
					}
					if(event.getCreateUser() !=null){
						cUser = event.getCreateUser().getRealName()+"/"+event.getCreateUser().getUserName();
					}
					if(event.getSubmitUser()!=null){
						sUser = event.getSubmitUser().getRealName()+"/"+event.getSubmitUser().getUserName();
					}
					if(event.getPonderance() !=null){
						ponderance = event.getPonderance().getName();
					}
					if(event.getFrequency() !=null){
						frequency = event.getFrequency().getName();
					}
					if(event.getClosedDate()!=null){
						cDate=event.getClosedDate().toString().substring(0, 16);
					}
					if(event.getSubmitDate()!=null){
						sDate=event.getSubmitDate().toString().substring(0, 16);
					}
					if(event.getEventStatus()!=null){
						status=event.getEventStatus().getName();
					}
					if(event.getSupportGroup()!=null){
						groupName=event.getSupportGroup().getGroupName();
					}
					json.append("{");
					json.append("'id':'"+event.getId()+"','eventCisn':'"+event.getEventCisn()+"',");
					json.append("'summary':'"+event.getSummary()+"','createUser':'"+cUser+"',");
					json.append("'submitDate':'"+sDate+"','dealuser':'"+dUser+"',"+"'submitUser':'"+sUser+"',");
					json.append("'eventStatus':'"+status+"','ponderance':'"+ponderance+"',");
					json.append("'closedDate':'"+cDate+"','frequency':'"+frequency+"','supportGroup':'"+groupName+"'");
					json.append("}");
			}
		}
		json.append("]");
		json.append("}");
		response.setContentType("text/plain;charset=gbk");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String findAllProblemSort(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String typeName = HttpUtil.getString(request, "name", "");
		String id=request.getParameter("id");
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		Page page=eventService.findAllProblemSort(id,typeName,pageNo,pageSize);
		Long total=page.getTotalCount();
		List siList=page.list();
		StringBuilder json = new StringBuilder("{success: true,"+"rowCount:"+total+",data:[");
		if(siList!=null&&siList.size()!=0){
			for(int i=0;i<siList.size();i++){
				ProblemSort si=(ProblemSort) siList.get(i);
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + si.getId()+ "',");
				json.append("name:'" + si.getName()+ "'");
				json.append("}");	
			}
			json.append(",");
			json.append("{");
			json.append("id:'',");
			json.append("name:''");
			json.append("}");
		}
		json.append("]");
		json.append("}");
		response.setContentType("text/plain;charset=gbk");
		try {
			PrintWriter out = response.getWriter();
			out.print(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String findTaskIdByDataIdandProcessId() throws IOException {
		HttpServletResponse response = super.getResponse();
		HttpServletRequest request = super.getRequest();
		String dataId=request.getParameter("dataId");
		String processId=request.getParameter("processId");
		Long taskId = eventService.findTaskIdByDataId(dataId,processId);
		String json = "";
		json = "{success : true , taskId : '" + taskId + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = super.getResponse().getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	//2010-06-29 add by huzh for ���ͽ�������ʼ��߳���
	class SendMailThred implements Runnable{
		private MailSenderService mss;
		private String to;
		private String cc;
		private String bcc;
		private String subject;
		private String context;
		private String filePath;
		public SendMailThred() {}
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
	
	//2010-06-29 add by huzh for ���ͽ�������ʼ��ĸ�ʽ
	private String eventHtmlContent(UserInfo creator, String url,Event event) {
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
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">��������ύ�¼����¼���ţ�<font style='font-weight: bold'>"+event.getEventCisn()+"</font>���¼����ƣ�<font style='font-weight: bold'>"+event.getSummary()+"</font>���Ľ��������<a href=" + url + ">" + "���������</a>"+"���ʡ������԰������ݽ�����Ӧ������ϣ���ܰ�����������������⡣лл��</div></td>");
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
	
	private String eventHtmlSatisfyContent(UserInfo creator, String url,Event event) {

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
		sb.append("<td class=\"STYLE1\" style=\"text-indent:2em\">");
		sb.append("���ύ���¼���"+"�¼����ƣ�<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"���¼���ţ�<font style='font-weight: bold'>"+event.getEventCisn()+"</font>�������Ѿ�������ϡ�ͬʱ����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ���������������������" + "<a href=" + url + ">"
				+ "�������</a>"+"���Ĳ��������ȵ��飬лл��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ�ü���IT������������������κ�����ͽ���,���Է����ʼ���it-manage@zsgj.com,���߲���IT�����鼰Ͷ������7888-0��"); 
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
	private String KnowledgeHtmlContent(UserInfo submitUser, String url,
			Event event,Knowledge knowledge) {
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
				+ submitUser.getRealName() + "/" + submitUser.getUserName()
				+ "�����ã�</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">IT����ʦ"+event.getDealuser().getRealName()+"/"+event.getDealuser().getUserName()+"Ϊ�����͡�<font style='font-weight: bold'>"+knowledge.getSummary()+"</font>������������������޸ĺ����ã�����<a href=" + url + ">" + "���������</a>���ʡ�</div></td>");
		sb.append("</tr>");
		sb.append("				 <br>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em,font-family:����\">");
		sb.append("���������ܴ�ҳ�棬�뽫�±ߵ����ӵ�ַճ����IE��ַ���д򿪡�<br>"+url+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<br>�������⣬��ӭ���ٴ��벦��IT��������7888��</td>");
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

}

