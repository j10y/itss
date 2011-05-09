package com.zsgj.info.framework.security.service;

public interface LDAPAuthenticationService {
	
	/**
	 * ��Ч����֤
	 * @param username �û���  
	 * @param pwd ����
	 * @return �ɹ�����true
	 */
	public boolean IsAuthenticated(String username,String pwd);
	
	/**
	 * ��Ч����֤
	 * @param domain Ldap������·��,����ʹ���ƻ���IP��ַ
	 * @param username �û���
	 * @param pwd ����
	 * @return �ɹ�����true
	 */
	public boolean IsAuthenticated(String domain ,String username,String pwd);
	
	
	/**
	 * Web Servers��Ч����֤
	 * @param UserName �û���
	 * @param Pwd ����
	 * @return �ɹ�����true
	 */
	public boolean IsAuthenticatedByWebServer(String UserName,String Pwd);
	
	
	/**
	 * ��Ч����֤
	 * @param domain Ldap������·��,����ʹ���ƻ���IP��ַ
	 * @param username �û���
	 * @param pwd ����
	 * @return �ɹ�����true
	 */
	boolean IsAuthenticatedByLdap(String domain, String username, String pwd);
	
	/**
	 * ��Ч����֤
	 * 
	 * @param username
	 *            �û���
	 * @param pwd
	 *            ����
	 * @return �ɹ�����true
	 */
	public boolean IsAuthenticatedByLdap(String username, String pwd);
}
