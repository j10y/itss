package com.digitalchina.itil.account.webservice;

import java.util.List;
import java.util.Map;

/**
 * ����Ƹ���Խ��ṩ�ӿ�
 * @Class Name ITILToHRJobData
 * @Author lee
 * @Create In Jan 19, 2010
 */
public interface ITILToHRJobData {
	
	/**
	 * ��ȡ�����ص�
	 * @Methods Name getWorkSpace
	 * @Create In Jan 19, 2010 By lee
	 * @return String
	 */
	public String getWorkSpace();
	/**
	 * ��ȡ�ʼ��ȼ�������
	 * @Methods Name getSameMailDept
	 * @Create In Jan 19, 2010 By lee
	 * @param sameMailDeptName
	 * @param pageNo
	 * @return String
	 */
	public String getSameMailDept(String sameMailDeptName,int pageNo);
	/**
	 * ��Ƹ�����ýӿ��ύ��Ա��IT�ʺ�����
	 * @Methods Name hRJobStartItilAccount
	 * @Create In Jan 19, 2010 By lee
	 * @param josnData
	 * @return String
	 */
	public String hRJobStartItilAccount(String josnData);
}
