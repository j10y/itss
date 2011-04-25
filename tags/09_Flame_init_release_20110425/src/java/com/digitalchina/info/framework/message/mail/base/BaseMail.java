package com.digitalchina.info.framework.message.mail.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;

import com.digitalchina.info.framework.context.ContextHolder;

/**
 * 邮件处理基础类
 * @Class Name BaseMail
 * @Author zhangpeng
 * @Create In Mar 6, 2008
 */
public class BaseMail {
	
	protected static ContextHolder cx = ContextHolder.getInstance();

	protected final Log logger = LogFactory.getLog("servicelog");
	
	private String defaultSender;
	
	private String from;
	
	private String subject;
	
	private String message;
	
	/**
	 * 根据流程ID生成邮件
	 * @Methods Name getMail
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param processID 流程定义ID
	 * @return SimpleMailMessage
	 */
	protected SimpleMailMessage getMail(long processID){
		getDefined();
		SimpleMailMessage mail = new SimpleMailMessage();
		
		if(this.from != null && !this.from.equals("")){
			mail.setFrom(from);
		}else{
			mail.setFrom(this.defaultSender);
		}
		
		mail.setSubject(subject);
		mail.setText(message);
		
		return mail;
	}
	
	/**
	 * 通过流程名称生成邮件
	 * @Methods Name getMail
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param processName 流程名称
	 * @return SimpleMailMessage
	 */
	protected SimpleMailMessage getMail(String processName){
		getDefined();
		SimpleMailMessage mail = new SimpleMailMessage();
		
		if(this.from != null && !this.from.equals("")){
			mail.setFrom(from);
		}else{
			mail.setFrom(this.defaultSender);
		}
		
		mail.setSubject(subject);
		mail.setText(message);
		
		return mail;
	}
	
	/**
	 * 获取默认定义
	 * @Methods Name getDefined
	 * @Create In Mar 6, 2008 By zhangpeng 
	 */
	private void getDefined(){
		this.defaultSender = cx.getApplicationContext().getMessage("system.mail.defaultSender", new Object[0], cx.getLocal());
		this.from = cx.getApplicationContext().getMessage("system.mail.from", new Object[0], cx.getLocal());
		this.subject = cx.getApplicationContext().getMessage("system.mail.subject", new Object[0], cx.getLocal());
		this.message = cx.getApplicationContext().getMessage("system.mail.message", new Object[0], cx.getLocal());
	}
	

	/**
	 * 记录邮件发送日志
	 * 
	 * @Methods Name writeLog
	 * @Create In Dec 13, 2007 By 张鹏
	 * @param message
	 *            SimpleMailMessage实体对象
	 */
	protected void writeLog(SimpleMailMessage message) {
		// TODO Auto-generated method stub
		String msgTo = "";
		String msgCC = "";
		String msgBCC = "";
		for (int i = 0; i < message.getTo().length; i++) {
			msgTo = (i == 0 ? "[" + message.getTo()[i] + ";" : (i == message
					.getTo().length ? msgTo + message.getTo()[i] + "]" : msgTo
					+ message.getTo()[i] + ";"));
		}
		for (int i = 0; i < message.getCc().length; i++) {
			msgCC = (i == 0 ? "[" + message.getCc()[i] + ";" : (i == message
					.getCc().length ? msgCC + message.getCc()[i] + "]" : msgCC
					+ message.getCc()[i] + ";"));
		}
		for (int i = 0; i < message.getBcc().length; i++) {
			msgBCC = (i == 0 ? "[" + message.getBcc()[i] + ";" : (i == message
					.getBcc().length ? msgBCC + message.getBcc()[i] + "]"
					: msgBCC + message.getBcc()[i] + ";"));
		}
		logger.info("发送邮件到:" + msgTo + "]\n");
		logger.info("抄送:" + msgCC + "]\n");
		logger.info("密送:" + msgBCC + "]\n");
		logger.info("邮件主题:" + message.getSubject() + "\n");
		logger.info("邮件内容:" + message.getText() + "\n");
	}
	
}
