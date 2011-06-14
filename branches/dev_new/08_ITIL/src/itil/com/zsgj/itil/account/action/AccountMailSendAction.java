package com.zsgj.itil.account.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.info.TaskInfo;
import com.zsgj.itil.account.service.AccountService;
import com.zsgj.itil.require.entity.AccountApplyMainTable;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;
import com.zsgj.itil.service.entity.ServiceItemProcess;

@SuppressWarnings("serial")
public class AccountMailSendAction extends BaseAction {
	private MailSenderService ms = (MailSenderService) ContextHolder.getBean("mailSenderService");
	private AccountService accountService = (AccountService) getBean("accountService");
	//private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	private ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	
	/**
	 * 列出所有帐号申请
	 * @Methods Name listAllAccountApply
	 * @Create In Sep 9, 2010 By liuying
	 * @return String
	 */
	public String listAllAccountApply() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String applyUser=request.getParameter("applyUser");
		String delegateApplyUser=request.getParameter("delegateApplyUser");
		String serviceItemProcess=request.getParameter("serviceItemProcess");
		String applyname=request.getParameter("applyName");
		int start = HttpUtil.getInt(request, "start", 0);
		int pageSize = HttpUtil.getInt(request, "pageSize", 10);
		int pageNo = start/pageSize +1;

		Page page=accountService.findAccountApply(applyUser,delegateApplyUser,serviceItemProcess,applyname,pageNo,pageSize);
		String jsons="";
		StringBuilder json=new StringBuilder();
		Long total = page.getTotalCount();
		List<AccountApplyMainTable> sas = page.list();
		for(AccountApplyMainTable aamt:sas){
			UserInfo appUser=aamt.getApplyUser();
			UserInfo deleUser=aamt.getDelegateApplyUser();
			ServiceItemProcess sip=aamt.getServiceItemProcess();
			Date applyDate=aamt.getApplyDate();
			int st=aamt.getStatus();
			
			String status="";
			String appUserName="";
			String deleUserName="";
			String definame="";
			String endpage="";
			
			if(appUser!=null){
				appUserName=appUser.getUserName()+"/"+appUser.getRealName();
			}
			if(deleUser!=null){
				deleUserName=deleUser.getUserName()+"/"+deleUser.getRealName();
			}
			if(sip!=null){
				definame=sip.getDefinitionName();
				endpage=sip.getEndPageModel().getPagePath();
			}
			if(st==1){
				status="审批中";
			}
			if(st==2){
				status="审批结束";
			}
			
			json.append("{\"id\":\"" + aamt.getId() + "\",\"name\":\"" + aamt.getName()
			 + "\",\"applyUser\":\"" + appUserName + "\",\"deleUser\":\"" + deleUserName
			+ "\",\"applyDate\":\"" + applyDate+ "\",\"status\":\"" + status
			+ "\",\"definame\":\"" + definame+ "\",\"endpage\":\"" + endpage
			+ "\"},");
		}
		
		if (json.length() == 0) {
			jsons = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			jsons = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jsons);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 给申请人补发结束邮件
	 * @Methods Name sendEndMailToUser
	 * @Create In Sep 10, 2010 By liuying
	 * @return String
	 */
	public String sendEndMailToUser(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		String userMail=request.getParameter("user");
		String dataId=request.getParameter("dataId");
		String json="";
		String combinEmail ="";
		
		AccountApplyMainTable aamt=(AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId);
		ServiceItemProcess sip=aamt.getServiceItemProcess();
		UserInfo applyUser=null;
		if(aamt.getApplyDate()!=null){
			applyUser=aamt.getDelegateApplyUser();
		}else{
			applyUser=aamt.getApplyUser();
		}
		if(userMail!=null&&!userMail.trim().equals("")){
			List<UserInfo> users=getService().find(UserInfo.class, "userName", userMail.trim().toLowerCase());
			if(users!=null&&users.size()!=0){
				combinEmail=users.get(0).getEmail();
			}else{
				json="{success:true,mes:'系统中没有这个用户！请核实接受此邮件的用户的itcode是否填写正确'}";
				PrintWriter out;
				try {
					out = response.getWriter();
					out.println(json);
					out.flush();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		}else{
			combinEmail = applyUser.getEmail();
		}
		PageModel pageModel = sip.getEndPageModel();
		String url = pageModel.getPagePath(); // 获取页面路径
		String rootPath = PropertiesUtil.getProperties("system.web.url");// 获取项目根路径
		String realUrl = rootPath + url + "?dataId=" + dataId; // 得到真实连接（全路径）
		
		String definitionName=sip.getDefinitionName();
		String subject = "IT温馨提示：您提交的" +definitionName+ "处理完成";
		List auditHis = accountService.findServiceItemApplyAuditHis(dataId, aamt.getServiceItem().toString());//查找出来的是所有的按流程顺序排列的节点信息
		String context = this.htmlContent(applyUser, realUrl,auditHis,definitionName);
		try {
			boolean success=ms.sendMimeMail(combinEmail, null, null, subject, context,null);
			if(success){
				json="{success:true,mes:'发送邮件成功！'}";
			}else{
				json="{success:true,mes:'发送邮件失败！'}";
			}
		}catch(Exception e){
			json="{success:true,mes:'发送邮件失败！'}";
			e.printStackTrace();
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 给审批人补发邮件
	 * @Methods Name sendAuditMailToUser
	 * @Create In Sep 10, 2010 By liuying
	 * @return String
	 */
	public String sendAuditMailToUser(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String dataId=request.getParameter("dataId");
		String json="";
		Long processId=null;
		
		AccountApplyMainTable aamt=(AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId);
		ServiceItemProcess sip=aamt.getServiceItemProcess();
		
		UserInfo applyUser=null;
		if(aamt.getApplyDate()!=null){
			applyUser=aamt.getApplyUser();
		}else{
			applyUser=aamt.getDelegateApplyUser();
		}
		String definitionName=sip.getDefinitionName();
		String subject = "IT温馨提示："+applyUser.getRealName()+"/"+applyUser.getUserName()+"提交了" +definitionName+ "，请您及时审批。";
		
		try {
			List auditHis = accountService.findServiceItemApplyAuditHis(dataId, aamt.getServiceItem().toString());
			if(auditHis!=null){
				ServiceItemApplyAuditHis siaah=(ServiceItemApplyAuditHis) auditHis.get(auditHis.size()-1);
				processId=siaah.getProcessId();
			}
			List<TaskInfo> tasks = ps.getActiveTasks(processId);
			int num=1;
			if(tasks!=null){
				for(TaskInfo ti:tasks){
					String actor=ti.getActorId();
					if(actor!=null&&!"".equals(actor)){
						if(actor.contains("|")){
							String[] tempAudit=actor.split("\\|");
							for(int i=0;i<tempAudit.length;i++){
								List<UserInfo> tempAuditUsers=getService().find(UserInfo.class, "userName",tempAudit[i]);
								if(tempAuditUsers!=null&&tempAuditUsers.size()!=0){
									String context=AuditHtmlContent("acproject", dataId, null, ti.getId(), applyUser, definitionName, auditHis,tempAuditUsers.get(0));
									boolean success= ms.sendMimeMail(tempAuditUsers.get(0).getEmail(), null, null, subject, context,null);
									if(success){
										num=num+1;
									}
								}
							}
						}else{
							List<UserInfo> tempAuditUsers=getService().find(UserInfo.class, "userName",actor);
							if(tempAuditUsers!=null&&tempAuditUsers.size()!=0){
								String context=AuditHtmlContent("acproject", dataId, null, ti.getId(), applyUser, definitionName, auditHis,tempAuditUsers.get(0));
								boolean success=ms.sendMimeMail(tempAuditUsers.get(0).getEmail(), null, null, subject, context,null);
								if(success){
									num=num+1;
								}
							}
						}
					}
				}
			}
			if(num>1){
				json="{success:true,mes:'发送邮件成功！'}";
			}else{
				json="{success:true,mes:'发送邮件失败！'}";
			}
		} catch (Exception e) {
			json="{success:true,mes:'发送邮件失败！'}";
			e.printStackTrace();
		}
	
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
		
	/**
	 * 组装结束邮件
	 * @Methods Name htmlContent
	 * @Create In Sep 10, 2010 By liuying
	 * @param creator
	 * @param url
	 * @param auditHis
	 * @param definitionName
	 * @return String
	 */
	private String htmlContent(UserInfo creator, String url,List auditHis,String definitionName) {

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
		sb.append("<div align=\"left\">您提交的"+definitionName+"已处理完成,<a href=" + url + ">" + "请点击链接查看详细信息。" + "</a>如有问题，请拨打IT服务热线7888。</div></td>");
		sb.append("</tr>");
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>过程：</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
				//String resultFlag=(String)baseObjectWrapper.getPropertyValue("resultFlag");
				//String comment=(String)baseObjectWrapper.getPropertyValue("comment");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("提交")){
					auditMeg= nodeMeg+"环节"+" "+userName+" "+timeString+" "+"提交；";
				}else {
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
		sb.append("<br>感谢您使用公司IT服务"); 
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
		sb.append("<br>本邮件由公司IT服务系统（ITSS）自动发送，请勿直接回复。");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}
	
	
	/**
	 * 组装审批邮件
	 * @Methods Name AuditHtmlContent
	 * @Create In Sep 10, 2010 By liuying
	 * @param applyType
	 * @param dataId
	 * @param goStartState
	 * @param taskId
	 * @param creatorMeg
	 * @param vDesc
	 * @param auditHis
	 * @param userInfo
	 * @return String
	 */
	public String AuditHtmlContent(String applyType,String dataId,
			String goStartState, Long taskId, UserInfo creatorMeg, String vDesc,
			List auditHis,UserInfo userInfo) {
		String browseFlag = "";
		
		
		StringBuilder sb = new StringBuilder();
		//NumberFormat currencyFormat = NumberFormat.getNumberInstance();
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
		sb.append("                <td class=\"STYLE1\">尊敬的"+userInfo.getRealName()+"/"+userInfo.getUserName()+"，您好:</td>");
		sb.append("				</tr>");
		sb.append("				 <br>");
		sb.append("				<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("				"+creatorMeg.getRealName()+"/"+creatorMeg.getUserName()+"提交了"+vDesc +"，<a href=" + PropertiesUtil.getProperties("system.web.url","localhost:8080") + "/infoAdmin/workflow/configPage/auditFromMail.jsp?"+"taskId="+taskId+"&dataId="+dataId+"&goStartState="+goStartState+"&taskName="+"&applyType="+applyType+"&browseFlag="+browseFlag+">"+"请点击链接审批。</a>"+"谢谢!</td>");
		sb.append("				</tr>");
		sb.append("<tr>");
		sb.append("             <td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("如果需要同时审批多个IT服务类申请，您可以直接访问"+"<a href=" + PropertiesUtil.getProperties("system.web.url","http://10.1.120.53/itil") +">"+"IT服务系统（ITSS）</a>"+"，减少您多次输入ITcode和ITpassword进行验证的过程。"); 
		sb.append("</td>");	
		sb.append("</tr>");
	
		sb.append("				 <tr>");
    	sb.append("					<td><div class=\"STYLE1\" align=\"left\"><br>过程：</div></td>");
    	sb.append("				 </tr>");
		if(auditHis!=null&&!"".equals(auditHis)){
			for (int i=0;i<auditHis.size();i++) {
				BeanWrapper baseObjectWrapper = new BeanWrapperImpl(auditHis.get(i));
				String nodeMeg = (String)baseObjectWrapper.getPropertyValue("nodeName");
				UserInfo user = (UserInfo)baseObjectWrapper.getPropertyValue("approver");
				Date approverDate = (Date)baseObjectWrapper.getPropertyValue("approverDate");
				SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String timeString  = dateFormats.format(approverDate);
				String auditMeg=null;
				if(user!=null){
				String userName = user.getRealName();
				if(nodeMeg.contains("提交")){
					auditMeg= nodeMeg+"环节"+" "+userName+" "+timeString+" "+"提交；";
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
		sb.append("<br>感谢您使用公司IT服务"); 
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
		sb.append("<br>本邮件由公司IT服务系统（ITSS）自动发送，请勿直接回复。");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}
}
