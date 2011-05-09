package com.zsgj.itil.finance.service;

import java.util.Map;
import net.sf.json.JSONObject;

public interface CostHandInputService {
	/**
	 * ��ҳ��ѯ����ϵͳ����ʽ��������
	 * @Methods Name findConfigItem
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param start
	 * @param pageSize
	 * @param item ����ѯ��ı�ʶ 1��ʾ������;2��ʾ������
	 * @param propertyValue : ����ҳ�����ݵ��Զ����
	 * @return String : json ��
	 */
	public String findItem(int start, int pageSize,String item,String propertyValue);
	
	/**
	 * ��ҳ��ѯ���з�������
	 * @Methods Name findCostType
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param start
	 * @param pageSize
	 * @param propertyValue
	 * @return String
	 */
	public String findCostType(int start, int pageSize,String propertyValue);
	
	/**
	 * ��ҳ��ѯ�����û�
	 * @Methods Name findReimbursement
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param start
	 * @param pageSize
	 * @param propertyValue
	 * @return String
	 */
	public String findReimbursement(int start, int pageSize,String propertyValue);

	/**
	 * ��ҳ��ѯ���гɱ���������
	 * @Methods Name findFinanceCostCenter
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param start
	 * @param pageSize
	 * @param propertyValue
	 * @return String
	 */
	public String findFinanceCostCenter(int start, int pageSize,String propertyValue);
	
	/**
	 * �������ɱ���ϸ
	 * @Methods Name saveFinanceCostSchedules
	 * @Create In Oct 14, 2010 By liaogs1
	 * @param jo
	 * @return boolean
	 */
	public boolean saveFinanceCostSchedules(JSONObject jo);
	
	/**
	 * ��ѯ�б�
	 * @Methods Name findList
	 * @Create In Oct 15, 2010 By liaogs1
	 * @param map
	 * @param start
	 * @param pageSize
	 * @return String
	 */
	public String findList(Map<String,String> map,int start, int pageSize);
	
	/**
	 * ���ݶ�������Ժ�����ֵ���Ҷ���
	 * @Methods Name findObjectByProperty
	 * @Create In Oct 14, 2010 By Liaos1
	 * @param s : �����ȫ·�������ڹ���Class
	 * @param id �� ���������id
	 * @param propertyName �����������
	 * @param propertyValue �� ���������ֵ
	 * @return Object �� ���ظö���
	 */
	public Object findObjectByProperty(String s,Long id,String propertyName,Object propertyValue);
}
