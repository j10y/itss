package com.zsgj.itil.event.service.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.itil.event.dao.CCTelInfoDao;
import com.zsgj.itil.event.entity.CCCallInfo;
import com.zsgj.itil.event.entity.CCTblIVRSatisfy;
import com.zsgj.itil.event.service.ICCTelInfoService;

@SuppressWarnings("unchecked")
public class CCTelInfoServicImpl extends BaseService implements ICCTelInfoService {
	private CCTelInfoDao ccTelInfoDao;
	
	public List<CCCallInfo> getUnEmailCCCallInfo(){
		return ccTelInfoDao.selectUnEmailCCCallInfo();
	}

	public String htmlContent(String url) {

		StringBuilder sb = new StringBuilder();
//		NumberFormat currencyFormat = NumberFormat.getNumberInstance();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		sb.append("<html>");
		sb.append("	<head>");
		sb.append("		<title>事件处理情况</title>");

		sb.append("		<meta http-equiv=\"keywords\" content=\"keyword1,keyword2,keyword3\">");
		sb.append("		<meta http-equiv=\"description\" content=\"this is my page\">");
		sb.append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\">");

		sb.append("		<!--<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\">-->");
		sb.append("		<style type=\"text/css\">");
		sb.append("		<!--");
		sb.append("		.STYLE1 {");
		sb.append("			font-size: 24px;");
		sb.append("			font-weight: bold;");
		sb.append("		}");
		sb.append("		-->");
		sb.append("		</style>");
		sb.append("	</head>");

		sb.append("	<body>");
		sb.append("		<div align=\"center\">");
		sb.append("			<table width=\"1000\" height=\"200\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("				<tr>");
		sb.append("					<td height=\"29\" colspan=\"3\" nowrap><div align=\"center\" class=\"STYLE1\">邮件通知</div></td>");
		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("					<td ><div align=\"left\"><strong>提交人：</strong></div></td>");

		sb.append("				</tr>");
		sb.append("				<tr>");
		sb.append("					<td align=\"left\">" + "点击此链接，查看处理结果！链接：" + "</td>");
		sb.append("					<td >" + "<a href=" + url + ">"
				+ "http://10.1.180.167/itil</a>" + "</td>");
		sb.append("				</tr>");
		sb.append("			</table>");
		sb.append("		</div>");
		sb.append("	</body>");
		sb.append("</html>");

		return sb.toString();
	}
	public boolean isFeedback(String callId) {
		List list=find(CCTblIVRSatisfy.class, "handle",callId);
		if(!list.isEmpty()){
			return true;
		}
		return false;
	}
	public UserInfo getUserInfoByItCode(String itCode) {
		return (UserInfo) findUnique(UserInfo.class, "itcode",itCode);
	}
	public void setCcTelInfoDao(CCTelInfoDao ccTelInfoDao) {
		this.ccTelInfoDao = ccTelInfoDao;
	}
	public List<CCCallInfo> getNoFeedBackofCCCall() {
		return ccTelInfoDao.selectNoFeedBackofCCCall();
	}
}
