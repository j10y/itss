package com.zsgj.itil.event.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import net.sf.json.JSONObject;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.ContextService;
import com.zsgj.info.framework.workflow.ParameterService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.info.HistoryInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.event.entity.Event;

public class EventManagerAction extends BaseAction {
	private TaskService ts = (TaskService) ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService) ContextHolder.getBean("parameterService");
	private Service service = (Service) ContextHolder.getBean("baseService");
//	private ContextService vm = (ContextService) ContextHolder.getBean("contextService");
	private ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	//2010-06-04 add by huzh begin
	private MailSenderService ms = (MailSenderService) ContextHolder.getBean("mailSenderService");
	//2010-06-04 add by huzh end
	/**
	 * ������루������������,��Ҫ���ǵ�һ���ڵ��п���ָ�ɸ����˵���� ֧�ֵ��ڵ㵥������ָ�ɣ���ʽΪa|b|c,
	 * 
	 * @Methods Name apply
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String apply() throws Exception {
		String json = "";
		// ��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");// ��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");// ������id
		String creator = super.getRequest().getParameter("creator");// ������id
		//modify by lee for remove unnecessary code in 20100408 begin
		// add by awen for add two param in bizParam on 2009-8-23 begin
		//Event event = (Event) service.find(Event.class, dataId, true);
		// add by awen for add two param in bizParam on 2009-8-23 end
		//String departmentCode = super.getRequest().getParameter("deptcode");
		//String userAssign = super.getRequest().getParameter("userAssign");
		//2010-04-29 delete by huzh for û�� begin
		/*String applyNum = super.getRequest().getParameter("eventCisn");
		String applyName = super.getRequest().getParameter("eventName");*/
		//2010-04-29 delete by huzh for û�� end
		//modify by lee for remove unnecessary code in 20100408 end
		// ��Ҫ���������ĵ�ҵ�����
		//2010-05-12 add by huzh for ��ô��ύ�¼����ύ�� begin
		Event event=(Event) this.getService().find(Event.class, dataId);
		Long submitUserId=event.getSubmitUser().getId();
		//2010-05-12 add by huzh for ��ô��ύ�¼����ύ�� end
		Map mapBizz = new HashMap();
		if (buzzParameters != null && !buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value="";
				//2010-05-12 add by huzh for ָ���û�ȷ�ϻ��ڵ��ʼ�������Ϊ�¼��ύ�ˣ���Ҫ����������ύ�¼��� begin
				if(key.trim().equals("users")){
					value = (String) jo.get(key)+"$userConfirm:"+submitUserId;
				}else{
					value = (String) jo.get(key);
				}
				//2010-05-12 add by huzh for ָ���û�ȷ�ϻ��ڵ��ʼ�������Ϊ�¼��ύ�ˣ���Ҫ����������ύ�¼��� begin
				mapBizz.put(key, value);
			}
		//2010-04-29 modified by huzh for �޸� begin	
			// add by awen for add two param in bizParam on 2009-8-23 begin
			if (StringUtils.isNotBlank(dataId)) {
				mapBizz.put("applyNum", mapBizz.get("eventCisn"));
				mapBizz.put("applyName", mapBizz.get("eventName"));
			}
			// add by awen for add two param in bizParam on 2009-8-23 end
		//2010-04-29 modified by huzh for �޸� end	
		}
		if(creator==null)
			creator = UserContext.getUserInfo().getUserName();
//		Long instanceId = null;
//		String meg = "";
		//2010-06-04 modified by huzh for �������̸�Ϊ�첽���������쳣�������˺͹���Ա���ʼ� begin
		try {
//			 throw new Exception();  
			StartEventProcess startThread =new StartEventProcess(definitionName, creator, mapBizz);
			Thread st = new Thread(startThread);
		    st.start();
		    json = "{success:true,id:" + st.getId() + "}";
//			instanceId = ps.createProcess(definitionName, creator, mapBizz);
//			json = "{success:true,id:'" + instanceId + "'}";
		} catch (Exception e) {
//			meg = e.getMessage();
//			json = "{success:true,Exception:'" + meg + "'}";
//			String subject="IT��ܰ��ʾ:"+event.getCreateUser().getRealName()+"/"+event.getCreateUser().getUserName()+"�����ı��Ϊ"+event.getEventCisn()+",����Ϊ��"+event.getSummary()+"���¼����ύʱ�������쳣����鿴�������ύ��";//"�쳣֪ͨ";
			String subject="IT��ܰ��ʾ:"+event.getCreateUser().getRealName()+"/"+event.getCreateUser().getUserName()+"�������¼����ύʱ�������쳣���뼰ʱ�鿴�������ύ��";//"�쳣֪ͨ";
			String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") + "/user/event/eventResubmit/eventResubmitForException.jsp?dataId="+dataId;
			SendExceptionMailThred smthread =new SendExceptionMailThred(ms,event.getCreateUser().getEmail()+";"+"liming1@zsgj.com", "", null, subject, this.eventHtmlContent(event.getCreateUser(), url, event), null);
			Thread std = new Thread(smthread);
		    std.start();
		}
		//2010-06-04 modified by huzh for �������̸�Ϊ�첽���������쳣�������˺͹���Ա���ʼ� begin
		try {
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ݲ�ͬ����������ʾ��ͬ�������б�
	 * 
	 * @Methods Name tasks
	 * @Create In Mar 6, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String tasks() throws Exception {
		HttpServletRequest request = super.getRequest();
		// ��Ҫ�Ĳ���
		String actor = request.getParameter("actorId");
		// String actor = UserContext.getUserInfo().getUserName();
		String json = "";

		int rowCount = 0;
		List<TaskInfo> list = ts.listTasks(actor);

		for (TaskInfo taskInfo : list) {
			String str = "";
			str += "defname:'" + taskInfo.getDefinitionName() + "',";
			str += "defdesc:'" + taskInfo.getDefinitionDesc() + "',";
			str += "nodeName:'" + taskInfo.getNodeName() + "',";
			str += "taskId:'" + taskInfo.getId() + "',";
			str += "taskName:'" + taskInfo.getName() + "',";
			// ��ʵ�����ƴ����û�ϵͳ��
			// String realName = getUserRealNameByName(taskInfo.getActorId());
			// str += "actorId:'"+realName+"',";
			str += "startDate:'" + toBlank(taskInfo.getStart()) + "',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			String eventName = (String) bizParams.get("eventName");
//			String eventCisn = (String) bizParams.get("eventCisn");
			//2010-05-04 delete by huzh for û�� begin
//			String eventSubmitUser = (String) bizParams.get("eventSubmitUser");
//			String eventSubmitDate = (String) bizParams.get("eventSubmitDate");
			//2010-05-04 delete by huzh for û�� end
			String dataId = (String) bizParams.get("dataId");
			if (eventName == null || "null".equalsIgnoreCase(eventName)) {
				bizParams.put("eventName", "δ����");
			}
			String applyTypeString = (String) bizParams.get("applyType");
			if ("eproject".equals(applyTypeString)) {
				Event event = (Event) service.find(Event.class, dataId, true);
				//2010-05-04 modified by huzh for �ύ�˼���itcode,�ύʱ���Ϊ��/��/�� begin
				bizParams.put("eventSubmitUser",event.getSubmitUser().getRealName()+"/"+event.getSubmitUser().getUserName());
				bizParams.put("eventSubmitDate",DateUtil.convertDateTimeToString(event.getSubmitDate()).substring(0, 16));
				//2010-05-04 modified by huzh for �ύ�˼���itcode,�ύʱ���Ϊ��/��/�� begin
				if (event.getDealuser() != null) {
					//2010-05-04 modified by huzh for �����˼���itcode begin
					bizParams.put("currEngineer", event.getDealuser().getRealName()+"/"+event.getDealuser().getUserName());
					//2010-05-04 modified by huzh for �����˼���itcode begin
				//2010-04-22 add by huzh for ����ǰ������ʦ��userName������̨ begin
					bizParams.put("currEngineerItcode", event.getDealuser().getUserName());
				 //2010-04-22 add by huzh for ����ǰ������ʦ��userName������̨ begin
				}
				//add by lee for show the ponderance to user in 20100416 begin
				if(event.getPonderance()==null){
					bizParams.put("ponderance", "");
				}else{
					bizParams.put("ponderance", event.getPonderance().getName());
				}
				//add by lee for show the ponderance to user in 20100416 end
			}
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams == null || strBizParams.equals("null") ? "''" : strBizParams;
			if (strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if (strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0, strBizParams.length() - 1);
			}

			str += strBizParams + ",";
			str += "comments:'" + toBlank(taskInfo.getComments().getValue("comment")) + "'";
			str = "{" + str + "},";
			if ("eproject".equals(applyTypeString)) {
				// add by guoxl in 2009/08/04 begin
				UserInfo userInfo = UserContext.getUserInfo();
				// dealUser���鳤ҲӦ���ܿ�����Ҫ�����˷��ر����鳤����protel�оͿ������ˣ�ͨ����ǰ��¼�˵õ����¼�˵��鳤
				if (userInfo != null) {

					Event event = (Event) service.find(Event.class, dataId, true);
					UserInfo leader = null;
					List<SupportGroupEngineer> engineerList = super.getService().find(SupportGroupEngineer.class,
							"userInfo", event.getDealuser());
					if (engineerList.size() > 0) {
						SupportGroupEngineer engineer = engineerList.get(0);
						if (engineer != null) {
							SupportGroup supportGroup = engineer.getSupportGroup();
							if (supportGroup != null) {
								leader = supportGroup.getGroupLeader();
							}
						}
					}
					// dealUser���鳤ҲӦ���ܿ�����Ҫ�����˷��ر����鳤����protel�оͿ�������
					// ��������ʾ

					UserInfo dealUser = null;
					if (event != null) {
						// ����¼���Ϊ��,����Ǵ�����Ϊ��,����ʾ
						dealUser = event.getDealuser();
						if (dealUser == null) {
							json += str;
							rowCount++;
							// ����¼���Ϊ��,��������˲�Ϊ��,�Ҵ�����Ϊ��ǰ��¼�˻����ύ��Ҳ��ʾ
						} else if (dealUser != null && dealUser.getId().equals(userInfo.getId())) {
							json += str;
							rowCount++;
						} else if (dealUser != null && event.getSubmitUser().getId().equals(userInfo.getId())) {
							json += str;
							rowCount++;
						} else if (dealUser != null && leader.getId().equals(userInfo.getId())) {
							json += str;
							rowCount++;

						}
					}
				}
			}
		}
		json = deleteComma(json);
		json = "{success: true, rowCount:'" + rowCount + "',data:[" + json + "]}";

		HttpServletResponse res = super.getResponse();
		res.setContentType("text/plain");
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		return null;
	}

	/**
	 * ���ĳ���̵�������ʷ��Ϣ
	 * 
	 * @Methods Name history
	 * @Create In Sep 10, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unused")
	private String next() throws Exception {
		// ��Ҫ�Ĳ���
		String taskId = super.getRequest().getParameter("taskid");
		String procId = super.getRequest().getParameter("procid");
		long instId = 0;
		if (procId != null && procId.trim().length() != 0) {
			instId = Long.parseLong(procId);
			List tasks = ps.getActiveTasks(instId);
			if (!tasks.isEmpty()) {
				taskId = ((TaskInfo) tasks.get(0)).getId() + "";
			}
		} else if (taskId != null && taskId.trim().length() != 0) {
			instId = ts.getProcessInfo(Long.parseLong(taskId)).getId();
		} else {
			System.out.println("ListHistoryAction��������");
		}

		// List<HistoryInfo> list = ps.getHistory(instId);
		List<HistoryInfo> list = new ArrayList();
		List tasks = ps.getActiveTasks(instId);
		for (int i = 0; i < tasks.size(); i++) {
			TaskInfo ti = (TaskInfo) tasks.get(i);
			HistoryInfo hi = new HistoryInfo(ti);
			hi.setTaskName(ti.getName());
			list.add(hi);
		}
		String json = "";
		for (HistoryInfo historyInfo : list) {
			String str = "";
			// historyInfo.getComments()
			// ��ʵ�����ƴ����û�ϵͳ��
			// String realName =
			// getUserRealNameByName(historyInfo.getActorId());
			// str += "actorId:'"+realName+"',";
			str += "date:'" + historyInfo.getDate() + "',";
			str += "definitionName:'" + historyInfo.getDefinitionName() + "',";
			str += "processId:'" + historyInfo.getProcessId() + "',";
			str += "nodeName:'" + historyInfo.getNodeName() + "',";
			str += "taskName:'" + historyInfo.getTaskName() + "',";
			str += "name:'" + historyInfo.getName() + "',";
			str += "taskId:'" + historyInfo.getTaskId() + "'";
			str = "{" + str + "},";
			json += str;
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "[" + json + "]";
		// json = "{data:["+json+"]}";
		// json = "{success:true,data:"+json+"}";
		return json;
	}

	private String toBlank(Object o) {
		return o == null ? "" : (String) o;
	}

	private String deleteComma(String json) {
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		return json;
	}
	//2010-06-04 add by huzh for Ч���Ż� begin
	class StartEventProcess implements Runnable{
		private String definitionName;
		private String creator;
		private Map mapBizz;
		public StartEventProcess() {
		}
		
		public StartEventProcess(String definitionName, String creator, Map mapBizz) {
			this.definitionName = definitionName;
			this.creator = creator;
			this.mapBizz = mapBizz;
		}

		public void run(){
			ps.createProcess(definitionName, creator, mapBizz);
		}
	} 
	
	class SendExceptionMailThred implements Runnable{
		private MailSenderService mss;
		private String to;
		private String cc;
		private String bcc;
		private String subject;
		private String context;
		private String filePath;
		public SendExceptionMailThred() {}
		public SendExceptionMailThred(MailSenderService mss, String to, String cc,
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
		sb.append("<div align=\"left\">���������¼����¼���ţ�<font style='font-weight: bold'>"+event.getEventCisn()+"</font>���¼����ƣ�<font style='font-weight: bold'>"+event.getSummary()+"</font>�����ύʱ�������쳣��<a href=" + url + ">" + "����������</a>"+"�鿴��ϸ��Ϣ���������ύ���¼���лл��</div></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
		sb.append("<br>��л��ʹ��IT����"); 
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
		sb.append("<br>���ʼ���IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}
}
