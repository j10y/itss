package com.digitalchina.itil.account.service;

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

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.message.mail.service.MailSenderService;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.DateUtil;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.itil.account.entity.PersonFormalAccount;
import com.digitalchina.itil.account.entity.SpecialAccount;
import com.digitalchina.itil.event.entity.Event;

public class DeptAccountJob extends QuartzJobBean{
	
	protected static final Log log=LogFactory.getLog(DeptAccountJob.class);
	private AccountService accountService;
	private MailSenderService mailSenderSerivce = (MailSenderService)ContextHolder.getBean("mailSenderService");
    private GregorianCalendar calendar=new GregorianCalendar();
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		String accountType="DeptMail";
		Date curDate=DateUtil.getCurrentDate();
		calendar.setTime(curDate);
		int nowDate=calendar.get(Calendar.DATE);
		System.out.print("部门特殊邮件确认通知");
		List<SpecialAccount> specialAccount=accountService.findSpecailAccountByType(accountType);
		for(SpecialAccount account:specialAccount){
			UserInfo user=account.getAccountNowUser();
			String email=user.getEmail();
			String rootPath = PropertiesUtil.getProperties("system.web.url");// 获取项目根路径
			Long id=account.getId();
			String url=rootPath+"/accountJobAction_mailAccountConfirm.action?id="+id;
			mailSenderSerivce.sendMimeMail(email, null, null, "部门特殊邮件帐号确认通知", this.accountHtmlContent(account,user,url), null)	;
			
		}
	}
	
	//邮件格式  accountHtmlContent
	private String accountHtmlContent(SpecialAccount account,UserInfo creator, String url) {
        StringBuilder sb = new StringBuilder();
		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
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
		sb.append("<td  style=\"font-size:20px\" align=\"center\">部门特殊邮件帐号确认通知</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\">尊敬的"+creator.getRealName()+"/"+creator.getItcode()+"您好:</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<br>请确认您帐号名为"+account.getAccountName()+"的部门特殊邮件帐号确认通知！您可以点击" + "<a href=" + url + ">"
				+ "继续使用</a>"+"来继续使用.如果您不想再继续使用,可以不用做任何操作!");
		sb.append("<br>为了帮助我们提高IT服务质量，请您在确认后抽出20-30秒的时间对我们的IT服务进行满意度评价，谢谢！");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE2\">");
		sb.append("<br>感谢您使用集团IT服务，如果您对我们有任何意见和建议,可以发送邮件到it-manage@digitalchina.com,或者拨打IT服务建议及投诉热线7888-0。"); 
		sb.append("</td>");	
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" align=\"right\">");
		sb.append("<br>集团信息化管理部(神州数码IT)");
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
