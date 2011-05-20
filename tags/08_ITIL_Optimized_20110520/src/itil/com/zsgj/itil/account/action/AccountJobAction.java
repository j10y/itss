package com.zsgj.itil.account.action;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.account.entity.SpecialAccount;

public class AccountJobAction extends BaseAction{
	private BaseService baseSerivce = (BaseService)ContextHolder.getBean("baseSerivce");
	/**
	 * ����/���� ���ʼ��ʺ�ȷ��֪ͨ
	 * 
	 * @Methods Name mailAccountConfirm
	 * @Create In OCT 19, 2009 By gaowen
	 * @return String
	 */
	public String mailAccountConfirm() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String objectId=request.getParameter("id");
        UserInfo curUser=UserContext.getUserInfo();
        SpecialAccount sa=(SpecialAccount) getService().find(SpecialAccount.class, objectId);
        if(sa.getAccountState().equals("1")){
          if(sa.getIfLocked().equals(1)){
        	sa.setIfLocked(null);
        }
        sa.setIsConfrim(1);
        baseSerivce.save(sa);
	    return "success";
        }else{
        	 return "false";//����
        }
	}
	//�ʼ���ʽ  accountHtmlContent
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
		sb.append("<td  style=\"font-size:20px\" align=\"center\">���������ʼ��ʺ�ȷ��֪ͨ</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\">�𾴵�"+creator.getRealName()+"/"+creator.getItcode()+"����:</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE1\" style=\"padding-left:2em\">");
		sb.append("<br>��ȷ�����ʺ���Ϊ"+account.getAccountName()+"�Ĳ��������ʼ��ʺ�ȷ��֪ͨ�������Ե��" + "<a href=" + url + ">"
				+ "����ʹ��</a>"+"������ʹ��.����������ټ���ʹ��,���Բ������κβ���!");
		sb.append("<br>Ϊ�˰����������IT����������������ȷ�Ϻ���20-30���ʱ������ǵ�IT���������������ۣ�лл��");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td class=\"STYLE2\">");
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
		sb.append("</table>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
}
