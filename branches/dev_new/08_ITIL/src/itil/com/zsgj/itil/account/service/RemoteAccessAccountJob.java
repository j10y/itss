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
import com.zsgj.itil.account.entity.SpecialAccount;
import com.zsgj.itil.event.entity.Event;

/**
 * 远程接入账号提示任务类
 * @Class Name RemoteAccessAccountJob
 * @Author lee
 * @Create In Jan 13, 2010
 */
public class RemoteAccessAccountJob extends QuartzJobBean{
	
	protected static final Log log=LogFactory.getLog(RemoteAccessAccountJob.class);
	private AccountService accountService;
	private MailSenderService mailSenderSerivce = (MailSenderService)ContextHolder.getBean("mailSenderService");
    private GregorianCalendar calendar=new GregorianCalendar();
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		String accountType="VPNAccount";
//		String accountVPNType="TempVPNAccount";
		Date curDate=DateUtil.getCurrentDate();
		calendar.setTime(curDate);
		Long nowTime=calendar.getTimeInMillis();
		List<PersonFormalAccount> personFormalAccount=accountService.findAllPersonAccountByAccountType(accountType);
		int i1 = 1;
		for(PersonFormalAccount account:personFormalAccount){
			UserInfo user=account.getAccountowner();
			String email=user.getEmail();
			Date endDate=account.getEndDate();
			if(endDate!=null){
				calendar.setTime(endDate);
				Long endTime=calendar.getTimeInMillis();
				Long time=endTime-nowTime;
				Long dataValue=time/1000/60/60/24;
				//System.out.println("远程接入帐号到期催办任务调度开始发邮件！");
				if(dataValue<=15&&dataValue>0){
					i1++;
					System.out.println("远程接入帐号到期催办任务调度开始发邮件--"+user.getUserName()+"-->"+endDate);
					mailSenderSerivce.sendMimeMail(email, null, null, "IT温馨提示：您的令牌卡即将到期，请及时处理。", this.accountHtmlContent(user, ""), null);
				}
			}
		 }
		System.out.println("远程接入帐号到期催办任务调度开始发邮件【"+i1+"】============");
//		int i2 = 1;
		List<SpecialAccount> specailAccount=accountService.findSpecailAccountByType(accountType);
		  for(SpecialAccount account:specailAccount){
				UserInfo user=account.getAccountNowUser();
				String email=user.getEmail();
				Date endDate=account.getEndDate();
				if(endDate!=null){
					calendar.setTime(endDate);
					Long endTime=calendar.getTimeInMillis();
					Long time=endTime-nowTime;
					Long dataValue=time/1000/60/60/24;
					if(dataValue<=15&&dataValue>0){
						System.out.println("临时远程接入帐号到期催办任务调度开始发邮件--"+user.getUserName()+"--"+account.getAccountName());
						mailSenderSerivce.sendMimeMail(email, null, null, "IT温馨提示：您的令牌卡即将到期，请及时处理。", this.accountHtmlContent(user, ""), null);
					}
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
		sb.append("<td  style=\"font-size:20px\" align=\"center\">远程接入帐号到期催办通知</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\">尊敬的"+creator.getRealName()+"/"+creator.getItcode()+"您好:</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<br>您的令牌卡即将到期<u><i>(可以在您的令牌卡背面查看具体到期日期：MM/DD/YY)</i></u>，请在两周内联系平台/办公区IT现场工程师进行更换,或拨打集团IT服务热线7888-1指导您进行更换，以免影响您使用VPN服务。");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE2\">");
		sb.append("<br>感谢您使用集团IT服务"); 
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
