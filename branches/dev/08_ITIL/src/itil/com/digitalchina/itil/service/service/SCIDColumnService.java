package com.digitalchina.itil.service.service;

import java.util.List;

import com.digitalchina.itil.service.entity.SCIColumn;
import com.digitalchina.itil.service.entity.SCIDColumn;
import com.digitalchina.itil.service.entity.ServiceItem;

/**
 * �����������ֶη���
 * @Class Name SCIDColumnService
 * @Author lee
 * @Create In 2009-2-12
 */
public interface SCIDColumnService {
	/**
	 * ��ȡ������������ص��ֶ�
	 * @Methods Name findSCIDColumnByServiceItem
	 * @Create In 2009-2-12 By lee
	 * @param serviceItem
	 * @return List
	 */
	List<SCIDColumn> findSCIDColumnByServiceItem(ServiceItem serviceItem);
	/**
	 * ��ȡ������������ص��ֶ�����ַ���
	 * @Methods Name encode
	 * @Create In 2009-2-12 By lee
	 * @param columns
	 * @return String
	 */
	String encode(List<SCIDColumn> columns);
	/**
	 * ��������������ֶ�
	 * @Methods Name save
	 * @Create In 2009-2-12 By lee
	 * @param scidColumn
	 * @param SCIDColumn
	 */
	SCIDColumn save(SCIDColumn scidColumn);
	/**
	 * ��������������ֶε�ֵ
	 * @Methods Name save
	 * @Create In 2009-2-12 By lee
	 * @param serviceItem
	 * @param columnName
	 * @param value
	 * @return SCIDColumn
	 */
	SCIDColumn saveColumnValue(ServiceItem serviceItem,String columnName,String value);
	/**
	 * ��������������ֶΣ��ӷ��������������ֶε��룩
	 * @Methods Name save
	 * @Create In 2009-2-12 By lee
	 * @param sciColumns
	 * @param serviceItem
	 */
	void save(List<SCIColumn> sciColumns,ServiceItem serviceItem);
	/**
	 * ͨ������ʵ��SCIDColumn ɾ��SCIDColumn
	 * @author tongjp
	 * @param sCIDColumn
	 */
	void removeSCIDColumn(SCIDColumn sCIDColumn);
	/**
	 * ͨ��id����SCIDColumn
	 * @author tongjp
	 * @param id
	 * @return
	 */
	SCIDColumn findSCIDColumnById(String id);
	
	/**
	 * ͨ�����������idsɾ������
	 * @author tongjp
	 * @param ids
	 */
	void removeSCIDColumnByIds(String[] ids);
	/**
	 * ����������ֶ�
	 * @author tongjp
	 * @param scidColumn
	 * @return
	 */
	SCIDColumn saveSCIDColumn(SCIDColumn scidColumn);
	
	/**
	 * ����SCIDColumn���ڱ��������жϣ��������������ͬ�ֶ����ľͲ����棬���û�оͱ��档
	 * @Methods Name saveSCIDColumn
	 * @Create In Feb 26, 2009 By tongjp
	 * @param sciColumns
	 * @param serviceItem void
	 */
	void saveSCIDColumn(List<SCIColumn> sciColumns, ServiceItem serviceItem);
}
