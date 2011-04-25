package com.digitalchina.info.framework.message.mail.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;

import com.digitalchina.info.framework.context.ContextHolder;

/**
 * �ʼ����������
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
	 * ��������ID�����ʼ�
	 * @Methods Name getMail
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param processID ���̶���ID
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
	 * ͨ���������������ʼ�
	 * @Methods Name getMail
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param processName ��������
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
	 * ��ȡĬ�϶���
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
	 * ��¼�ʼ�������־
	 * 
	 * @Methods Name writeLog
	 * @Create In Dec 13, 2007 By ����
	 * @param message
	 *            SimpleMailMessageʵ�����
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
		logger.info("�����ʼ���:" + msgTo + "]\n");
		logger.info("����:" + msgCC + "]\n");
		logger.info("����:" + msgBCC + "]\n");
		logger.info("�ʼ�����:" + message.getSubject() + "\n");
		logger.info("�ʼ�����:" + message.getText() + "\n");
	}
	
}
