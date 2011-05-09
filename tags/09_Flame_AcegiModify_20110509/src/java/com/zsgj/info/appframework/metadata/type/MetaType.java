package com.zsgj.info.appframework.metadata.type;

import java.util.List;
import java.util.Map;

import org.hibernate.type.Type;

/**
 * ����Ԫ��������
 * @Class Name AbstractMetaType
 * @Author peixf
 * @Create In 2008-6-30
 */
public interface MetaType {
	
	/**
	 * �Ƿ��ǹ������ͣ��������������Դ������һ�ű�
	 * @Methods Name isAssociationType
	 * @return boolean
	 */
	public boolean isAssociationType();
	
	/**
	 * �Ƿ���Map���ͣ��������������ȡ���Ͷ���ļ�������
	 * @Methods Name isMapType
	 * @Create In 2008-7-1 By peixf
	 * @return boolean
	 */
	public boolean isMapType();
	
	/**
	 * �Ƿ�����޸�,��������޸ģ��ֶζ�Ӧ��ҳ��ؼ����ɱ༭��
	 * @Methods Name isCollectionType
	 * @return boolean
	 */
	public boolean isMutable();
	
	/**
	 * �����͵������Ƿ��ṩ�û����ҹ��ܣ�������򵯳��´������������ѯ��
	 * @Methods Name isSearchable
	 * @return boolean
	 */
	public boolean isSearchable();
		
	/**
	 * ��Ӧ��SQL���ͣ�����java.sql.Types�ĳ���ֵ����Types.INTEGER
	 * @Methods Name sqlType
	 * @return int
	 */
	public int sqlType();
	
	/**
	 * Ĭ�����ǰ׺�ַ�
	 * @Methods Name getFrontChar
	 * @return String
	 */
	public String getFrontChar();

	
	/**
	 * ��Ӧ��hibernate���ͣ�����ֵ����org.hibernate.type.Type
	 * @Methods Name hibernateType
	 * @return Type 
	 */
	public Type hibernateType();

	public String getName();
	
	public String getCnName();
	
	public Long getKey();

	public Object getValue();

	public String getText();

	public List getList(Class clazz);

	public List getList(Class clazz, String orderProp, boolean isAsc);

	public List getList(Class clazz, String propName, Object defaultValue);

	public List getList(Class clazz, String propName, Object defaultValue, String orderProp, boolean isAsc);

	public List getParentList(Class clazz, String parentPropName);

	public List getChildList(Class clazz, String parentPropName);
	
	public List getParentList(Class clazz, String parentPropName, String orderProp, boolean isAsc);

	public List getChildList(Class clazz, String parentPropName, String orderProp, boolean isAsc);

	public Map getDefaultSimpleKeyValue();


	
}



///**
// * �����͵������Ƿ��ṩ�û�����GOOGLE��AJAX��ѯ���ܡ�
// * @Methods Name isSearchable
// * @return boolean
// */
//public boolean isGoogleable();	
//
///**
// * �Ƿ��Ǽ������ͣ��������������Դ�������Ļ��߻��档
// * @Methods Name isCollectionType
// * @return boolean
// */
//public boolean isCollectionType();