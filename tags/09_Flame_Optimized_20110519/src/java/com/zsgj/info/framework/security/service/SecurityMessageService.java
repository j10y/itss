package com.zsgj.info.framework.security.service;

public interface SecurityMessageService {
	/**
	 * ��ȫģ����Ϣ�����ȡ��ϸ��Ϣ
	 * @Methods Name getMessage
	 * @Create In 2008-5-9 By zhangpeng
	 * @param key ��Դ�ļ�KEy
	 * @param defaults Ĭ����Ϣ
	 * @return String
	 */
	public String getMessage(String key,String defaults);
	
	/**
	 * ��ȫģ����Ϣ�����ȡ��ϸ��Ϣ
	 * @Methods Name getMessage
	 * @Create In 2008-5-9 By zhangpeng
	 * @param key ��Դ�ļ�KEy
	 * @return String
	 */
	public String getMessage(String key);
}
