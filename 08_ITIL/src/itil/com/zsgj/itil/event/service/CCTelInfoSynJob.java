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
        	
        	System.out.println("synchronize cc �������������� at "+this.synchrDate + "of time " + DateUtil.getCurrentDate());
        	
        	if(synchrDate.equalsIgnoreCase("current")){//Ĭ����current����ÿ��ͬ������ģ�����ǲ�����Ҫ�޸ĳ�null��ʾcc������ʱ�����ݶ�ͬ��
        		
//        		dateString = DateUtil.convertDateToString(DateUtil.getCurrentDate());
        		dateString = getDateTime("yyyy-MM-dd HH",new Date());
        		
        	}else{
        		dateString = synchrDate;
        	}
        	ccTelSynService.saveCCTel2Native(dateString);
        	
        	//2010-09-08 modified by huzh for ���ؼ�û���л�������û�����ʼ�������CCCallInfo begin
//        	List<CCCallInfo> list = ccTelInfoService.getUnEmailCCCallInfo();
        	List<CCCallInfo> list = ccTelInfoService.getNoFeedBackofCCCall();
        	//2010-09-08 modified by huzh for ���ؼ�û���л�������û�����ʼ�������CCCallInfo begin
        	
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
		String url = PropertiesUtil.getProperties("cc.web.endUrl");//��ȡ�ʼ�ҳ��
		String rootPath =PropertiesUtil.getProperties("system.web.url");//��ȡ��Ŀ��·��

		for (int i = 0; i < list.size(); i++) {
			CCCallInfo ccc = (CCCallInfo) list.get(i);
			String customerItcode = ccc.getCustomerItcode();
			String callId = ccc.getCallId();
            //��url�д�������dataId,isExist
			String dataId = ccc.getEvent().getId().toString();
			String isExist = ccTelInfoService.isFeedback(callId)?"s":"f";
			UserInfo userInfo = ccTelInfoService.getUserInfoByItCode(customerItcode);
			String realUrl = rootPath+url+"?dataId="+dataId+"&isExist="+isExist;
			if(userInfo!=null){
				if("s".equals(isExist)){//���������
					mailSenderSerivce.sendMimeMail(userInfo.getEmail(), null, null,
							"IT��ܰ��ʾ:��"+userInfo.getRealName()+"/"+userInfo.getUserName()+"��ʱ�鿴�����ύ�¼��Ĵ��������", this.eventHtmlSatContent(userInfo,realUrl,ccc.getEvent()), null);
				}else{
					mailSenderSerivce.sendMimeMail(userInfo.getEmail(), null, null,
							"IT��ܰ��ʾ:��"+userInfo.getRealName()+"/"+userInfo.getUserName()+"��ʱ�鿴�����ύ�¼��Ĵ��������", this.eventNoSatHtmlContent(userInfo,realUrl,ccc.getEvent()), null);
				}
			}
			
//			mailSenderSerivce.sendSimplyMail("guoxiaoliang0805@163.com", null, null, "�¼��������", "�¼��������");
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
	
	//�ʼ���ʽ5 eventHtmlcontent3
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
//		sb.append("<title> ���Ѿ��ɹ��ύһ�����⣬���Ϊ("+event.getEventCisn()+"�������ǻᾡ����������������лл</title>");
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
//		sb.append("font-family:'����_GB2312';");
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
//		sb.append("<div align=\"center\">�ʼ�֪ͨ");
//		sb.append("</div>");
//		sb.append("</div>");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\">�𾴵�"+creator.getRealName()+"/"+creator.getItcode()+"����:");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" style=\"text-indent:2em\">");
//		sb.append("�������"+"<font style='font-weight: bold'>"+event.getSummary()+"</font>���¼����Ϊ"+event.getEventCisn()+"���������Ѿ�������ϡ�ͬʱ����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ��������������������Ե��" + "<a href=" + url + ">"
//				+ "����</a>"+"����,лл��");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE2\">");
//		sb.append("<br>��л��ʹ�ù�˾IT������������������κ�����ͽ���,���Է����ʼ���it-manage@zsgj.com,���߲���IT�����鼰Ͷ������7888-0��"); 
//		sb.append("</td>");	
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" align=\"right\">");
//		sb.append("<br>��Ϣϵͳ��");
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
		sb.append("���ύ���¼���"+"�¼����ƣ�<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"���¼���ţ�<font style='font-weight: bold'>"+event.getEventCisn()+"</font>���������Ѿ�������ϡ�ͬʱ����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ���������������������" + "<a href=" + url + ">"
				+ "�������</a>"+"���ʣ�лл��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
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
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɹ�˾IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}
	
	//�ʼ���ʽ6 eventHtmlcontent6
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
//		sb.append("<title> ���Ѿ��ɹ��ύһ�����⣬���Ϊ("+event.getEventCisn()+"�������ǻᾡ����������������лл</title>");
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
//		sb.append("font-family:'����_GB2312';");
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
//		sb.append("<td  style=\"font-size:20px\" align=\"center\">�¼���չ֪ͨ</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\">�𾴵�"+creator.getRealName()+"/"+creator.getItcode()+"����:</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
//		sb.append("�������"+"<font style='font-weight: bold'>"+event.getSummary()+"</font>���¼����Ϊ"+event.getEventCisn()+"���������Ѿ�������ϡ�");
//		sb.append("<br>ͬʱ����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ��������������������Ե��" + "<a href=" + url + ">"
//				+ "����</a>" + "���ʡ�");
//		sb.append("<br>Ϊ�˰����������IT����������������ȷ�Ϻ���20-30���ʱ������ǵ�IT���������������ۣ�лл��");
//		sb.append("</td>");
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE2\">");
//		sb.append("<br>��л��ʹ�ù�˾IT������������������κ�����ͽ���,���Է����ʼ���it-manage@zsgj.com,���߲���IT�����鼰Ͷ������7888-0��"); 
//		sb.append("</td>");	
//		sb.append("</tr>");
//		sb.append("<tr>");
//		sb.append("<td class=\"STYLE1\" align=\"right\">");
//		sb.append("<br>��Ϣϵͳ��");
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
		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("���ύ���¼���"+"�¼����ƣ�<font style='font-weight: bold'>"+event.getSummary()+"</font>"+"���¼���ţ�<font style='font-weight: bold'>"+event.getEventCisn()+"</font>���������Ѿ�������ϡ�");
		sb.append("<br>ͬʱ����Ϊ����д�˸�����Ĵ����ĵ������ڶ����պ�Ĺ���������������������" + "<a href=" + url + ">"
				+ "���������</a>" + "���ʡ�");
		sb.append("<br>Ϊ�˰����������IT����������������ȷ�Ϻ���20-30���ʱ������ǵ�IT���������������ۣ�лл��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td  style=\"font-family:����\">");
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
		sb.append("<tr>");
		sb.append("<td  style=\"FILTER:alpha(opacity=30);font-size:10px\" align=\"left\">");
		sb.append("<br>���ʼ��ɹ�˾IT����ϵͳ��ITSS���Զ����ͣ�����ֱ�ӻظ���");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");
		return sb.toString();
	}
	
}
