package com.digitalchina.info.framework.message.sametime;

import javax.servlet.http.HttpSession;

public interface SameTimeService {
	
	/**
	 * ��½SameTime
	 * @Methods Name login
	 * @Create In Feb 1, 2008 By Iceman
	 * @param loginUser �û���
	 * @param passWord ����
	 * @param jsMethod ҳ��JS������
	 * @param reqSession void
	 */
	public void login(String loginUser,String passWord,HttpSession reqSession,String jsMethod);
	
	/**
	 * �ǳ�SameTime
	 * @Methods Name logout
	 * @Create In Feb 1, 2008 By Iceman void
	 */
	public void logout();
	
	/**
	 * �Ƿ��Ѿ���½�ɹ�
	 * @Methods Name isLoginScuess
	 * @Create In Mar 5, 2008 By zhangpeng
	 * @return boolean
	 */
	public boolean isLoginScuess();
	
	/**
	 * �Ƿ�������
	 * @Methods Name isHasError
	 * @Create In Mar 5, 2008 By zhangpeng
	 * @return boolean
	 */
	public boolean isHasError();
}
