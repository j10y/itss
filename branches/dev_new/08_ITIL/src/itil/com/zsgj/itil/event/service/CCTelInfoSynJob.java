package com.zsgj.itil.event.service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.itil.event.entity.CCCallInfo;
import com.zsgj.itil.event.entity.Event;

public class CCTelInfoSynJob extends QuartzJobBean {
	
	private String synchrDate;
	private CCTelSynService ccTelSynService;
	private ICCTelInfoService ccTelInfoService ;
	private MailSenderService mailSenderSerivce = (MailSenderService)ContextHolder.getBean("mailSenderService");
	@Override
    protected void executeInternal(JobExecutionContext context) 
				throws JobExecutionException {
		
		String dateString = null;
        					
        try { 
        	
        	System.out.println("synchronize cc 话务和满意度数据 at "+this.synchrDate + "of time " + DateUtil.getCurrentDate());
        	
        	if(synchrDate.equalsIgnoreCase("current")){//默认是current代表每天同步当天的，如果是测试需要修改成null表示cc表任意时间数据都同步
        		
//        		dateString = DateUtil.convertDateToString(DateUtil.getCurrentDate());
        		dateString = getDateTime("yyyy-MM-dd HH",new Date());
        		
        	}else{
        		dateString = synchrDate;
        	}
        	ccTelSynService.saveCCTel2Native(dateString);
        	
        	//2010-09-08 modified by huzh for 返回既没进行话务反馈又没进行邮件反馈的CCCallInfo begin
//        	List<CCCallInfo> list = ccTelInfoService.getUnEmailCCCallInfo();
        	List<CCCallInfo> list = ccTelInfoService.getNoFeedBackofCCCall();
        	//2010-09-08 modified by huzh for 返回既没进行话务反馈又没进行邮件反馈的CCCallInfo begin
        	
        	this.sendEmailAndChangeFlag(list);
        	//ccTelInfoService.userSendEmail(mailSenderSerivce);
        }
        
        catch(Exception e) {
            e.printStackTrace();
        }
    }
	/**
	 * 
	 * @Methods Name sendEmailAndChangeFlag
	 * @Create In Aug 14, 2009 By lee
	 * @param list void
	 */
	private void sendEmailAndChangeFlag(List<CCCallInfo> list){
		String url = PropertiesUtil.getProperties("cc.web.endUrl");//获取邮件页面
		String rootPath =PropertiesUtil.getProperties("system.web.url");//获取项目根路径

		for (int i = 0; i < list.size(); i++) {
			CCCallInfo ccc = (CCCallInfo) list.get(i);
			String customerItcode = ccc.getCustomerItcode();
			String callId = ccc.getCallId();
            //在url中传参数：dataId,isExist
			String dataId = ccc.getEvent().getId().toString();
			String isExist = ccTelInfoService.isFeedback(callId)?"s":"f";
			UserInfo userInfo = ccTelInfoService.getUserInfoByItCode(customerItcode);
			String realUrl = rootPath+url+"?dataId="+dataId+"&isExist="+isExist;
			if(userInfo!=null){
				if("s".equals(isExist)){//存在满意度
					mailSenderSerivce.sendMimeMail(userInfo.getEmail(), null, null,
							"IT温馨提示:请"+userInfo.getRealName()+"/"+userInfo.getUserName()+"及时查看您所提交事件的处理情况。", this.eventHtmlSatContent(userInfo,realUrl,ccc.getEvent()), null);
				}else{
					mailSenderSerivce.sendMimeMail(userInfo.getEmail(), null, null,
							"IT温馨提示:请"+userInfo.getRealName()+"/"+userInfo.getUserName()+"及时查看您所提交事件的处理情况。", this.eventNoSatHtmlContent(userInfo,realUrl,ccc.getEvent()), null);
				}
			}
			
//			mailSenderSerivce.sendSimplyMail("guoxiaoliang0805@163.com", null, null, "事件处理情况", "事件处理情况");
			ccc.setMailFlag(1);
			ccTelInfoService.save(ccc);
		}
	}
	public String getSynchrDate() {
		return synchrDate;
	}

	public void setSynchrDate(String synchrDate) {
		this.synchrDate = synchrDate;
	}

	public CCTelSynService getCcTelSynService() {
		return ccTelSynService;
	}

	public void setCcTelSynService(CCTelSynService ccTelSynService) {
		this.ccTelSynService = ccTelSynService;
	}

	public ICCTelInfoService getCcTelInfoService() {
		return ccTelInfoService;
	}

	public void setCcTelInfoService(ICCTelInfoService ccTelInfoService) {
		this.ccTelInfoService = ccTelInfoService;
	}
	public  String getDateTime(String aMask, Date aDate) {
	        SimpleDateFormat df = null;
	        String returnValue = "";

//	        if (aDate == null) {
//	            log.error("aDate is null!");
//	        } else {
	            df = new SimpleDateFormat(aMask);
	            returnValue = df.format(aDate);
//	        }

	        return (returnValue);
	    }
	
	//邮件格式5 eventHtmlcontent3
//	private String eventHtmlSatContent(UserInfo creator, String url,Event event) {
//
//		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date(); 
//		String dateString  = dateFormat.format(date);
//
//		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		sb.append("<html>");
//		sb.append("<head>");
//		sb.append("<title> 您已经成功提交一个问题，编号为("+event.getEventCisn()+"）”我们会尽快解决并向您反馈。谢谢</title>");
//
//		sb.append("<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
//		sb.append("<meta http-equiv=\"description\" content=\"this is my page\">");
//		sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
//		// sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\"
//		// href=\"./styles.css\">-->");
//		sb.append("<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
//		
//		sb.append("< style type=\"text/css\">");
//		sb.append("<!--");
//		sb.append(".STYLE1 {");
//		sb.append("font-size: 14px;");
//		sb.append("line-height:20px;");
//		sb.append("}");
//		sb.append(".STYLE2 {");
//		sb.append("font-family:'楷体_GB2312';");
//		sb.append("font-size: 14px;");
//		sb.append("}");
//		sb.append("-->");
//		sb.append("	</style>");
//		sb.append("</head>");
//		sb.append("<body>");
//		
//		sb.append("<div align=\"center\">");
//		sb.append("<table width=\"1000px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
//		sb.append("<tr>");
//		sb.append("<td >");
//		sb.append("<div align=\"center\" >");
//		sb.append("<div align=\"center\">邮件通知");
//		sb.append("</div>");
//		sb.append("</div>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\">尊敬的"+creator.getRealName()+"/"+creator.getItcode()+"您好:");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" style=\"text-indent:2em\">");
//		sb.append("您提出的"+"<font style='font-weight: bold'>"+event.getSummary()+"</font>（事件编号为"+event.getEventCisn()+"），现在已经处理完毕。同时我们为您编写了该问题的处理文档，以期对您日后的工作中有所帮助！您可以点击" + "<a href=" + url + ">"
//				+ "链接</a>"+"访问,谢谢！");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE2\">");
//		sb.append("<br>感谢您使用公司IT服务，如果您对我们有任何意见和建议,可以发送邮件到it-manage@zsgj.com,或者拨打IT服务建议及投诉热线7888-0。"); 
//		sb.append("</td>");	
//		sb.append("</tr>");
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
//		sb.append("</table>");
//		sb.append("</div>");
//		sb.append("</body>");
//		sb.append("</html>");
//		return sb.toString();
//	}
	private String eventHtmlSatContent(UserInfo creator, String url,Event event) {

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
		sb.append("<td class=\"STYLE1\" style=\"text-indent:2em\">");
		sb.append("您提交的事件（"+"事件名称：<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"，事件编号：<font style='font-weight: bold'>"+event.getEventCisn()+"</font>），现在已经处理完毕。同时我们为您编写了该问题的处理文档，以期对您日后的工作中有所帮助！您可以" + "<a href=" + url + ">"
				+ "点击链接</a>"+"访问，谢谢！");
		sb.append("</td>");
		sb.append("</tr>");
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
	
	//邮件格式6 eventHtmlcontent6
//	private String eventNoSatHtmlContent(UserInfo creator, String url,Event event) {
//
//
//		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date(); 
//		String dateString  = dateFormat.format(date);
//
//		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		sb.append("<html>");
//		sb.append("<head>");
//		sb.append("<title> 您已经成功提交一个问题，编号为("+event.getEventCisn()+"）”我们会尽快解决并向您反馈。谢谢</title>");
//
//		sb.append("<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
//		sb.append("<meta http-equiv=\"description\" content=\"this is my page\">");
//		sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
//		// sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\"
//		// href=\"./styles.css\">-->");
//		sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
//		
//		sb.append("<style type=\"text/css\">");
//
//		sb.append("<!--");
//		sb.append(".STYLE1 {");
//		sb.append("font-size: 14px;");
//		sb.append("line-height:20px;");
//		sb.append("}");
//		sb.append(".STYLE2 {");
//		sb.append("font-family:'楷体_GB2312';");
//		sb.append("font-size: 14px;");
//		sb.append("}");
//		
//		
//		sb.append("-->");
//		sb.append("</style>");
//		sb.append("</head>");
//		sb.append("<body>");
//		sb.append("<div align=\"center\">");
//		sb.append("<table width=\"1000px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
//		sb.append("<tr>");
//		sb.append("<td  style=\"font-size:20px\" align=\"center\">事件进展通知</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\">尊敬的"+creator.getRealName()+"/"+creator.getItcode()+"您好:</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
//		sb.append("您提出的"+"<font style='font-weight: bold'>"+event.getSummary()+"</font>（事件编号为"+event.getEventCisn()+"），现在已经处理完毕。");
//		sb.append("<br>同时我们为您编写了该问题的处理文档，以期对您日后的工作中有所帮助！您可以点击" + "<a href=" + url + ">"
//				+ "链接</a>" + "访问。");
//		sb.append("<br>为了帮助我们提高IT服务质量，请您在确认后抽出20-30秒的时间对我们的IT服务进行满意度评价，谢谢！");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE2\">");
//		sb.append("<br>感谢您使用公司IT服务，如果您对我们有任何意见和建议,可以发送邮件到it-manage@zsgj.com,或者拨打IT服务建议及投诉热线7888-0。"); 
//		sb.append("</td>");	
//		sb.append("</tr>");
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
//		sb.append("</table>");
//		sb.append("</div>");
//		sb.append("</body>");
//		sb.append("</html>");
//		return sb.toString();
//	}
	private String eventNoSatHtmlContent(UserInfo creator, String url,Event event) {

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
		sb.append("您提交的事件（"+"事件名称：<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"，事件编号：<font style='font-weight: bold'>"+event.getEventCisn()+"</font>），现在已经处理完毕。");
		sb.append("<br>同时我们为您编写了该问题的处理文档，以期对您日后的工作中有所帮助！您可以" + "<a href=" + url + ">"
				+ "点击此链接</a>" + "访问。");
		sb.append("<br>为了帮助我们提高IT服务质量，请您在确认后抽出20-30秒的时间对我们的IT服务进行满意度评价，谢谢！");
		sb.append("</td>");
		sb.append("</tr>");
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
