package com.zsgj.info.appframework.metadata.type;

import java.util.List;
import java.util.Map;

import org.hibernate.type.Type;

/**
 * 抽象元数据类型
 * @Class Name AbstractMetaType
 * @Author peixf
 * @Create In 2008-6-30
 */
public interface MetaType {
	
	/**
	 * 是否是关联类型，如果是则其数据源于另外一张表。
	 * @Methods Name isAssociationType
	 * @return boolean
	 */
	public boolean isAssociationType();
	
	/**
	 * 是否是Map类型，如果是则其数据取类型定义的集合数据
	 * @Methods Name isMapType
	 * @Create In 2008-7-1 By peixf
	 * @return boolean
	 */
	public boolean isMapType();
	
	/**
	 * 是否可以修改,如果不可修改，字段对应的页面控件不可编辑。
	 * @Methods Name isCollectionType
	 * @return boolean
	 */
	public boolean isMutable();
	
	/**
	 * 此类型的数据是否提供用户查找功能，如果是则弹出新窗口完成搜索查询。
	 * @Methods Name isSearchable
	 * @return boolean
	 */
	public boolean isSearchable();
		
	/**
	 * 对应的SQL类型，返回java.sql.Types的常量值，如Types.INTEGER
	 * @Methods Name sqlType
	 * @return int
	 */
	public int sqlType();
	
	/**
	 * 默认填充前缀字符
	 * @Methods Name getFrontChar
	 * @return String
	 */
	public String getFrontChar();

	
	/**
	 * 对应的hibernate类型，返回值类型org.hibernate.type.Type
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
// * 此类型的数据是否提供用户类似GOOGLE的AJAX查询功能。
// * @Methods Name isSearchable
// * @return boolean
// */
//public boolean isGoogleable();	
//
///**
// * 是否是集合类型，如果是则其数据源于上下文或者缓存。
// * @Methods Name isCollectionType
// * @return boolean
// */
//public boolean isCollectionType();