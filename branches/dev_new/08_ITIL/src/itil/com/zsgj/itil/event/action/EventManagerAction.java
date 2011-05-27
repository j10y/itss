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
	 * 提出申请（启动工作流）,需要考虑到一个节点有可能指派给多人的情况 支持单节点单行数据指派，形式为a|b|c,
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
		// 需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");// 在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");// 主数据id
		String creator = super.getRequest().getParameter("creator");// 主数据id
		//modify by lee for remove unnecessary code in 20100408 begin
		// add by awen for add two param in bizParam on 2009-8-23 begin
		//Event event = (Event) service.find(Event.class, dataId, true);
		// add by awen for add two param in bizParam on 2009-8-23 end
		//String departmentCode = super.getRequest().getParameter("deptcode");
		//String userAssign = super.getRequest().getParameter("userAssign");
		//2010-04-29 delete by huzh for 没用 begin
		/*String applyNum = super.getRequest().getParameter("eventCisn");
		String applyName = super.getRequest().getParameter("eventName");*/
		//2010-04-29 delete by huzh for 没用 end
		//modify by lee for remove unnecessary code in 20100408 end
		// 需要进入上下文的业务参数
		//2010-05-12 add by huzh for 获得代提交事件的提交人 begin
		Event event=(Event) this.getService().find(Event.class, dataId);
		Long submitUserId=event.getSubmitUser().getId();
		//2010-05-12 add by huzh for 获得代提交事件的提交人 end
		Map mapBizz = new HashMap();
		if (buzzParameters != null && !buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value="";
				//2010-05-12 add by huzh for 指定用户确认环节的邮件接收人为事件提交人（主要用来处理代提交事件） begin
				if(key.trim().equals("users")){
					value = (String) jo.get(key)+"$userConfirm:"+submitUserId;
				}else{
					value = (String) jo.get(key);
				}
				//2010-05-12 add by huzh for 指定用户确认环节的邮件接收人为事件提交人（主要用来处理代提交事件） begin
				mapBizz.put(key, value);
			}
		//2010-04-29 modified by huzh for 修改 begin	
			// add by awen for add two param in bizParam on 2009-8-23 begin
			if (StringUtils.isNotBlank(dataId)) {
				mapBizz.put("applyNum", mapBizz.get("eventCisn"));
				mapBizz.put("applyName", mapBizz.get("eventName"));
			}
			// add by awen for add two param in bizParam on 2009-8-23 end
		//2010-04-29 modified by huzh for 修改 end	
		}
		if(creator==null)
			creator = UserContext.getUserInfo().getUserName();
//		Long instanceId = null;
//		String meg = "";
		//2010-06-04 modified by huzh for 启动流程改为异步，若出现异常给创建人和管理员发邮件 begin
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
//			String subject="IT温馨提示:"+event.getCreateUser().getRealName()+"/"+event.getCreateUser().getUserName()+"创建的编号为"+event.getEventCisn()+",名称为："+event.getSummary()+"的事件在提交时发生了异常，请查看并重新提交。";//"异常通知";
			String subject="IT温馨提示:"+event.getCreateUser().getRealName()+"/"+event.getCreateUser().getUserName()+"创建的事件在提交时发生了异常，请及时查看并重新提交。";//"异常通知";
			String url = PropertiesUtil.getProperties("system.web.url","itss.zsgj.com") + "/user/event/eventResubmit/eventResubmitForException.jsp?dataId="+dataId;
			SendExceptionMailThred smthread =new SendExceptionMailThred(ms,event.getCreateUser().getEmail()+";"+"liming1@zsgj.com", "", null, subject, this.eventHtmlContent(event.getCreateUser(), url, event), null);
			Thread std = new Thread(smthread);
		    std.start();
		}
		//2010-06-04 modified by huzh for 启动流程改为异步，若出现异常给创建人和管理员发邮件 begin
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
	 * 根据不同流程名称显示不同的任务列表
	 * 
	 * @Methods Name tasks
	 * @Create In Mar 6, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String tasks() throws Exception {
		HttpServletRequest request = super.getRequest();
		// 需要的参数
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
			// 用实际名称代替用户系统名
			// String realName = getUserRealNameByName(taskInfo.getActorId());
			// str += "actorId:'"+realName+"',";
			str += "startDate:'" + toBlank(taskInfo.getStart()) + "',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			String eventName = (String) bizParams.get("eventName");
//			String eventCisn = (String) bizParams.get("eventCisn");
			//2010-05-04 delete by huzh for 没用 begin
//			String eventSubmitUser = (String) bizParams.get("eventSubmitUser");
//			String eventSubmitDate = (String) bizParams.get("eventSubmitDate");
			//2010-05-04 delete by huzh for 没用 end
			String dataId = (String) bizParams.get("dataId");
			if (eventName == null || "null".equalsIgnoreCase(eventName)) {
				bizParams.put("eventName", "未命名");
			}
			String applyTypeString = (String) bizParams.get("applyType");
			if ("eproject".equals(applyTypeString)) {
				Event event = (Event) service.find(Event.class, dataId, true);
				//2010-05-04 modified by huzh for 提交人加上itcode,提交时间变为年/月/日 begin
				bizParams.put("eventSubmitUser",event.getSubmitUser().getRealName()+"/"+event.getSubmitUser().getUserName());
				bizParams.put("eventSubmitDate",DateUtil.convertDateTimeToString(event.getSubmitDate()).substring(0, 16));
				//2010-05-04 modified by huzh for 提交人加上itcode,提交时间变为年/月/日 begin
				if (event.getDealuser() != null) {
					//2010-05-04 modified by huzh for 处理人加上itcode begin
					bizParams.put("currEngineer", event.getDealuser().getRealName()+"/"+event.getDealuser().getUserName());
					//2010-05-04 modified by huzh for 处理人加上itcode begin
				//2010-04-22 add by huzh for 将当前处理工程师的userName传到后台 begin
					bizParams.put("currEngineerItcode", event.getDealuser().getUserName());
				 //2010-04-22 add by huzh for 将当前处理工程师的userName传到后台 begin
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
				// dealUser的组长也应该能看到，要不到了返回本组组长后在protel中就看不到了，通过当前登录人得到其登录人的组长
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
					// dealUser的组长也应该能看到，要不到了返回本组组长后在protel中就看不到了
					// 其他不显示

					UserInfo dealUser = null;
					if (event != null) {
						// 如果事件不为空,如果是处理人为空,则显示
						dealUser = event.getDealuser();
						if (dealUser == null) {
							json += str;
							rowCount++;
							// 如果事件不为空,如果处理人不为空,且处理人为当前登录人或者提交人也显示
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
	 * 获得某流程的审批历史信息
	 * 
	 * @Methods Name history
	 * @Create In Sep 10, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unused")
	private String next() throws Exception {
		// 需要的参数
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
			System.out.println("ListHistoryAction参数不对");
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
			// 用实际名称代替用户系统名
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
	//2010-06-04 add by huzh for 效率优化 begin
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
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<div align=\"left\">您创建的事件（事件编号：<font style='font-weight: bold'>"+event.getEventCisn()+"</font>，事件名称：<font style='font-weight: bold'>"+event.getSummary()+"</font>）在提交时发生了异常，<a href=" + url + ">" + "请点击此链接</a>"+"查看详细信息，并重新提交该事件。谢谢！</div></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:楷体\">");
		sb.append("<br>感谢您使用IT服务"); 
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
		sb.append("<br>本邮件由IT服务系统（ITSS）自动发送，请勿直接回复。");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}
}
