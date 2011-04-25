package com.digitalchina.info.framework.message.mail.service;

import java.util.Map;

import com.digitalchina.info.framework.message.mail.entity.MailMessage;


public interface MailSenderService {
	
	/**
	 * 发送自定义邮件
	 * @Methods Name sendSimplyMail
	 * @Create In 2009-3-26 By 张鹏
	 * @param to 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param cc 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param bcc 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param Subject
	 * @param Context
	 * @return boolean
	 */
	boolean sendSimplyMail(String to, String cc, String bcc, String Subject, String Context);
	
	/**
	 * 发送自定义邮件
	 * @Methods Name sendSimplyMail
	 * @Create In 2009-3-26 By 张鹏
	 * @param to 地址数组String[]
	 * @param cc 地址数组String[]
	 * @param bcc 地址数组String[]
	 * @param Subject
	 * @param Context
	 * @return boolean
	 */
	boolean sendSimplyMail(String[] to, String[] cc, String[] bcc, String Subject, String Context);
	
	/**
	 * 发送富文本邮件
	 * @Methods Name sendMimeMail
	 * @Create In 2009-3-26 By 张鹏
	 * @param to 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param cc 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param bcc 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param Subject
	 * @param context
	 * @param filePath 可以使一个附件位置,也可以是由";" 分割开的多个附件位置
	 * @return boolean
	 */
	boolean sendMimeMail(String to, String cc, String bcc, String subject, String context, String filePath);
	
	/**
	 * 发送富文本邮件
	 * @Methods Name sendMimeMail
	 * @Create In 2009-3-26 By 张鹏
	 * @param to 邮件地址数组String[]
	 * @param cc 邮件地址数组String[]
	 * @param bcc 邮件地址数组String[]
	 * @param Subject 
	 * @param context
	 * @param filePath 附件位置String[]
	 * @return boolean
	 */
	boolean sendMimeMail(String[] to, String[] cc, String[] bcc, String subject, String context, String[] filePath);
	
	
	/**
	 * 工作流调用拦截器发送邮件
	 * @Methods Name getMapFromInform
	 * @Create In 2009-3-26 By 张鹏
	 * @param map 内容为以下
	 * map.put("creator", creator);
	 * map.put("thisActorId", thisActorId)
	 * map.put("toActorId", toActorId);
	 * map.put("thisNodeName", thisNode.getName());
	 * map.put("toNodeName", toNode.getName());
	 * map.put("definationName", ec.getProcessDefinition().getName());
	 * @return Map
	 */
	Map getMapFromInform(Map map);
	
	/**
	 * 工作流使用的默认方式邮件发送
	 * @Methods Name sendWorkFlowSimplyMail
	 * @Create In 2009-3-26 By 张鹏
	 * @param map
	 * @return boolean
	 */
	boolean sendWorkFlowSimplyMail(Map smap);
}
