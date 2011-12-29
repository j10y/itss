package com.xpsoft.core.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.model.system.AppUser;

public class AsynMeetingMailSendProcess implements Runnable {
	
	private VelocityEngine velocityEngine = (VelocityEngine) AppUtil.getBean("velocityEngine");
	
	private List<AppUser> tos;
	
	private String cc;
	
	private String assginIds;
	
	private Map model;
	
	public Map getModel() {
		return model;
	}
	public void setModel(Map model) {
		this.model = model;
	}
	private String emailContent;
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	/**
	 * @Return the String assginIds
	 */
	public String getAssginIds() {
		return assginIds;
	}
	/**
	 * @Param String assginIds to set
	 */
	public void setAssginIds(String assginIds) {
		this.assginIds = assginIds;
	}
	/**
	 * @Param String htmlMsgContentVMPath to set
	 */
	public void setHtmlMsgContentVMPath(String htmlMsgContentVMPath) {
		this.htmlMsgContentVMPath = htmlMsgContentVMPath;
	}
	private String htmlMsgContentVMPath = "mail\\sendMeetingNotice.vm";
	
	
	/**
	 * @Return the String[] tos
	 */
	public List<AppUser> getTos() {
		return tos;
	}
	/**
	 * @Param String[] tos to set
	 */
	public void setTos(List<AppUser> tos) {
		this.tos = tos;
	}
	/**
	 * @Return the String cc
	 */
	public String getCc() {
		return cc;
	}
	/**
	 * @Param String cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}
	
	/**
	 * @Return the String content
	 */
	public String getHtmlMsgContentVMPath() {
		return htmlMsgContentVMPath;
	}
	/**
	 * @Param String content to set
	 */
	public void setContent(String htmlMsgContentVMPath) {
		this.htmlMsgContentVMPath = htmlMsgContentVMPath;
	}
	
	public AsynMeetingMailSendProcess(List<AppUser> tos, String cc, String assginIds,Map model){
		this.tos = tos;
		this.cc = cc;
		this.assginIds = assginIds;
		this.model = model;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		MailEngine me = (MailEngine)AppUtil.getBean("mailEngine");
		me.setFrom("kanglei@shopin.cn");
		//String templateLocation = this.htmlMsgContentVMPath;
		//Map model = new HashMap();
		
		if(this.tos.size() > 0){
			String[] tosl = new String[tos.size()];
			String userNames = "";
			int i = 0;
			for(AppUser item : tos){
				tosl[i++] = item.getEmail();
				userNames += item.getFullname() + ",";
			}
//			userNames = userNames.substring(0, userNames.length() -1);
			//model.put("toUserName", userNames);
//			model.put("approveUrl", (String)AppUtil.getSysConfig().get("process.sendmail.apporvUrl"));
//			model.put("approveUrlName", (String)AppUtil.getSysConfig().get("process.sendmail.apporvUrlName"));
//			model.put("signature", (String)AppUtil.getSysConfig().get("displayName"));
//			model.put("replyTo", (String)AppUtil.getSysConfig().get("replyTo"));
			
			String pageHtml = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, this.htmlMsgContentVMPath,model);
			me.sendMimeMessage((String)this.model.get("presideEmail"), tosl, cc, null, "会议通知", pageHtml, null, null, true);
			//System.out.println("会议通知："+tosl+ "content:"+pageHtml);
		}
	}

	/**
	 * 
	 * @param model, 模板中需要的参数以Map 形式传过去
	 * @return
	 */
	
	public void generateEmailContent(Map model){
		this.emailContent = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, this.htmlMsgContentVMPath,model);
	}

}
