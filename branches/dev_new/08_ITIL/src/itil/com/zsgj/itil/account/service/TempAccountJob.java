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
				mailSenderSerivce.sendMimeMail(email, null, null, "Զ�̽����ʺŵ��ڴ߰�֪ͨ", this.accountHtmlContent(user, ""), null)	;
			}
			
		}
	}
	
	//�ʼ���ʽ  accountHtmlContent
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
		sb.append("font-family:'����_GB2312';");
		sb.append("font-size: 14px;");
		sb.append("}");
		
		
		sb.append("-->");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div align=\"center\">");
		sb.append("<table width=\"1000px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("<tr>");
		sb.append("<td  style=\"font-size:20px\" align=\"center\">�¼���չ֪ͨ</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\">�𾴵�"+creator.getRealName()+"/"+creator.getItcode()+"����:</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
		
		sb.append("<br>ͬʱ����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ��������������������Ե��" + "<a href=" + url + ">"
				+ "����</a>" + "���ʡ�");
		sb.append("<br>Ϊ�˰����������IT����������������ȷ�Ϻ���20-30���ʱ������ǵ�IT���������������ۣ�лл��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE2\">");
		sb.append("<br>��л��ʹ�ù�˾IT����"); 
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
