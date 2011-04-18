package com.digitalchina.info.appframework.metadata.service;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;

/**
 * ϵͳ�������չ��ϲ�
 * @Class Name SystemMainAndExtColumnService
 * @Author tongjp
 * @Create In Jun 23, 2009
 */
public interface SystemMainAndExtColumnService {
	
	/**
	 * ͨ�������ȡ�����ֶ�
	 * @Methods Name findAllColumnBySysMainTable
	 * @Create In Jun 23, 2009 By tongjp
	 * @param smt
	 * @return List
	 */
	public List findAllColumnBySysMainTable(SystemMainTable smt);
	
	/**
	 * ͨ��������Ƿ�Ϊ��չ������ͻ�ȡ�����ֶλ���չ���ֶ�
	 * @Methods Name findColumnByIsExtAndSysMainTable
	 * @Create In Jun 23, 2009 By tongjp
	 * @param isExtend
	 * @param smt
	 * @return List
	 */
	public List findColumnByIsExtAndSysMainTable(Integer isExtend,SystemMainTable smt);
	
	/**
	 * ͨ����չ�ֶε�id��ȡ�����б�
	 * @Methods Name findExtOptionDataByExtColId
	 * @Create In Jun 26, 2009 By tongjp
	 * @param ectColId
	 * @return List
	 */
	public List findExtOptionDataByExtColId(String ectColId);
	
	/**
	 * ͨ����ʵ���ֶε�id����չ�ֶε�id�鴦��Ӧ����չ�ֶε�����
	 * @Methods Name findObjectByMainRowIdAndExtColId
	 * @Create In Jun 26, 2009 By tongjp
	 * @param mainRowId
	 * @param extid
	 * @return Object
	 */
	public Object findObjectByMainRowIdAndExtColId(Integer mainRowId,Integer extid);
	
	/**
	 * ͨ��id�鴦�Զ�������ݵ�ֵ
	 * @Methods Name findOptionById
	 * @Create In Jun 26, 2009 By tongjp
	 * @param id
	 * @return Object
	 */
	public Object findOptionById(Long id);
	
	/**
	 * ����ʵ��
	 * @Methods Name saveExtOption
	 * @Create In Jun 29, 2009 By tongjp
	 * @param object
	 * @return Object
	 */
	public Object saveExtOption(Object object);
	
	/**
	 * ͨ��idɾ���Զ��������б������
	 * @Methods Name removeOptionById
	 * @param id
	 * @author tongjp
	 */
	public void removeOptionById(Long id);

}
