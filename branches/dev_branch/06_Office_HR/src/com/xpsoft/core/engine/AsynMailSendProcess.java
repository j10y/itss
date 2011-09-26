/**
 * @Probject Name: 06_Office_HR
 * @Path: com.xpsoft.core.engineAsynMailSendProcess.java
 * @Create By Jack
 * @Create In 2011-9-23 上午10:57:27
 * TODO
 */
package com.xpsoft.core.engine;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.model.system.AppUser;

/**
 * @Class Name AsynMailSendProcess
 * @Author Jack
 * @Create In 2011-9-23
 */
public class AsynMailSendProcess implements Runnable {
	
	
	private List<AppUser> tos;
	
	private String cc;
	
	private String assginIds;
	
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
	private String htmlMsgContentVMPath;
	
	
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
	
	public AsynMailSendProcess(List<AppUser> tos, String cc,String htmlMsgContentVMPath, String assginIds){
		this.tos = tos;
		this.cc = cc;
		this.htmlMsgContentVMPath = htmlMsgContentVMPath;
		this.assginIds = assginIds;
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		MailEngine me = (MailEngine)AppUtil.getBean("mailEngine");
		VelocityEngine velocityEngine = (VelocityEngine) AppUtil.getBean("velocityEngine");
		String templateLocation = this.htmlMsgContentVMPath;
		Map model = new HashMap();
		
		if(tos.size() > 0){
			String[] tosl = new String[tos.size()];
			String userNames = "";
			int i = 0;
			for(AppUser item : tos){
				tosl[i++] = item.getEmail();
				userNames += item.getFullname() + ",";
			}
			userNames = userNames.substring(0, userNames.length() -1);
			model.put("toUserName", userNames);
			model.put("approveUrl", (String)AppUtil.getSysConfig().get("process.sendmail.apporvUrl"));
			model.put("approveUrlName", (String)AppUtil.getSysConfig().get("process.sendmail.apporvUrlName"));
			model.put("signature", (String)AppUtil.getSysConfig().get("displayName"));
			model.put("replyTo", (String)AppUtil.getSysConfig().get("replyTo"));
			
			String pageHtml = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation,model);
			
			me.sendMimeMessage(null, tosl, cc, null, (String)AppUtil.getSysConfig().get("process.sendmail.subject"), pageHtml, null, null, true);

		}
	}
}
