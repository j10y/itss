package com.zsgj.itil.account.service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.itil.account.entity.PersonFormalAccount;
import com.zsgj.itil.event.entity.Event;

public class TempAccountJob extends QuartzJobBean{
	
	protected static final Log log=LogFactory.getLog(TempAccountJob.class);
	private AccountService accountService;
	private MailSenderService mailSenderSerivce = (MailSenderService)ContextHolder.getBean("mailSenderService");
    private GregorianCalendar calendar=new GregorianCalendar();
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		String accountType="VPNAccount";
		Date curDate=DateUtil.getCurrentDate();
		calendar.setTime(curDate);
		int nowDate=calendar.get(Calendar.DATE);
		List<PersonFormalAccount> personFormalAccount=accountService.findAllPersonAccountByAccountType(accountType);
		for(PersonFormalAccount account:personFormalAccount){
			UserInfo user=account.getAccountowner();
			String email=user.getEmail();
//			Date endDate=account.getEndDate();
			calendar.setTime(curDate);
			int date=calendar.get(Calendar.DATE);
			int dataValue=date-nowDate;
			if(dataValue<15){
				mailSenderSerivce.sendMimeMail(email, null, null, "远程接入帐号到期催办通知", this.accountHtmlContent(user, ""), null)	;
			}
			
		}
	}
	
	//邮件格式  accountHtmlContent
	private String accountHtmlContent(UserInfo creator, String url) {
        StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); 
		String dateString  = dateFormat.format(date);

		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("<head>");
		

		sb.append("<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");
		// sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\"
		// href=\"./styles.css\">-->");
		sb.append(" <!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		
		sb.append("<style type=\"text/css\">");

		sb.append("<!--");
		sb.append(".STYLE1 {");
		sb.append("font-size: 14px;");
		sb.append("line-height:20px;");
		sb.append("}");
		sb.append(".STYLE2 {");
		sb.append("font-family:'楷体_GB2312';");
		sb.append("font-size: 14px;");
		sb.append("}");
		
		
		sb.append("-->");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div align=\"center\">");
		sb.append("<table width=\"1000px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("<tr>");
		sb.append("<td  style=\"font-size:20px\" align=\"center\">事件进展通知</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\">尊敬的"+creator.getRealName()+"/"+creator.getItcode()+"您好:</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
		
		sb.append("<br>同时我们为您编写了该问题的处理文档，以期对您日后的工作中有所帮助！您可以点击" + "<a href=" + url + ">"
				+ "链接</a>" + "访问。");
		sb.append("<br>为了帮助我们提高IT服务质量，请您在确认后抽出20-30秒的时间对我们的IT服务进行满意度评价，谢谢！");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE2\">");
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
		sb.append("</table>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
	



	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	

}
