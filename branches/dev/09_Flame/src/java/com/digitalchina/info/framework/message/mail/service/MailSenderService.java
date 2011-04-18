package com.digitalchina.info.framework.message.mail.service;

import java.util.Map;

import com.digitalchina.info.framework.message.mail.entity.MailMessage;


public interface MailSenderService {
	
	/**
	 * �����Զ����ʼ�
	 * @Methods Name sendSimplyMail
	 * @Create In 2009-3-26 By ����
	 * @param to ����ʹһ���ʼ���ַ,Ҳ��������";" �ָ�Ķ���ʼ���ַ
	 * @param cc ����ʹһ���ʼ���ַ,Ҳ��������";" �ָ�Ķ���ʼ���ַ
	 * @param bcc ����ʹһ���ʼ���ַ,Ҳ��������";" �ָ�Ķ���ʼ���ַ
	 * @param Subject
	 * @param Context
	 * @return boolean
	 */
	boolean sendSimplyMail(String to, String cc, String bcc, String Subject, String Context);
	
	/**
	 * �����Զ����ʼ�
	 * @Methods Name sendSimplyMail
	 * @Create In 2009-3-26 By ����
	 * @param to ��ַ����String[]
	 * @param cc ��ַ����String[]
	 * @param bcc ��ַ����String[]
	 * @param Subject
	 * @param Context
	 * @return boolean
	 */
	boolean sendSimplyMail(String[] to, String[] cc, String[] bcc, String Subject, String Context);
	
	/**
	 * ���͸��ı��ʼ�
	 * @Methods Name sendMimeMail
	 * @Create In 2009-3-26 By ����
	 * @param to ����ʹһ���ʼ���ַ,Ҳ��������";" �ָ�Ķ���ʼ���ַ
	 * @param cc ����ʹһ���ʼ���ַ,Ҳ��������";" �ָ�Ķ���ʼ���ַ
	 * @param bcc ����ʹһ���ʼ���ַ,Ҳ��������";" �ָ�Ķ���ʼ���ַ
	 * @param Subject
	 * @param context
	 * @param filePath ����ʹһ������λ��,Ҳ��������";" �ָ�Ķ������λ��
	 * @return boolean
	 */
	boolean sendMimeMail(String to, String cc, String bcc, String subject, String context, String filePath);
	
	/**
	 * ���͸��ı��ʼ�
	 * @Methods Name sendMimeMail
	 * @Create In 2009-3-26 By ����
	 * @param to �ʼ���ַ����String[]
	 * @param cc �ʼ���ַ����String[]
	 * @param bcc �ʼ���ַ����String[]
	 * @param Subject 
	 * @param context
	 * @param filePath ����λ��String[]
	 * @return boolean
	 */
	boolean sendMimeMail(String[] to, String[] cc, String[] bcc, String subject, String context, String[] filePath);
	
	
	/**
	 * ���������������������ʼ�
	 * @Methods Name getMapFromInform
	 * @Create In 2009-3-26 By ����
	 * @param map ����Ϊ����
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
	 * ������ʹ�õ�Ĭ�Ϸ�ʽ�ʼ�����
	 * @Methods Name sendWorkFlowSimplyMail
	 * @Create In 2009-3-26 By ����
	 * @param map
	 * @return boolean
	 */
	boolean sendWorkFlowSimplyMail(Map smap);
}
