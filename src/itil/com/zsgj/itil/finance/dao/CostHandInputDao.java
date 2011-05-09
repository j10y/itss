package com.zsgj.itil.finance.dao;

import java.util.Map;

import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;

public interface CostHandInputDao extends Dao{
	/**
	 * ��ҳ��ѯ����ϵͳ����ʽ��������
	 * @Methods Name selectConfigItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectConfigItem(int pageNo, int pageSize,String value);
	/**
	 * ��ҳ��ѯ����ϵͳ����ʽ��������
	 * @Methods Name selectServiceItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectServiceItem(int pageNo, int pageSize,String value);
	/**
	 * ��ҳ��ѯ���з�������
	 * @Methods Name selectServiceItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectFinanceCostType(int pageNo, int pageSize,String value);
	/**
	 * ��ҳ��ѯ�����û�
	 * @Methods Name selectServiceItem
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectReimbursement(int pageNo, int pageSize,String value);
	
	/**
	 * ��ҳ��ѯ���гɱ���������
	 * @Methods Name selectFinanceCostCenter
	 * @Create In Oct 12, 2010 By liaogs1
	 * @Return Page
	 */
	public Page selectFinanceCostCenter(int pageNo, int pageSize,String value);
	
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
	
	/**
	 * ��ѯ�б�
	 * @Methods Name selectList
	 * @Create In Oct 14, 2010 By liaogs1
	 * @param clazz : �����ȫ·�������ڹ���Class
	 * @param map �� ����
	 * @param pageNo �� ҳ��
	 * @param pageSize ��ҳ��С
	 * @return Page
	 */
	public Page selectList(Map<String,String> map,int pageNo, int pageSize);
}
