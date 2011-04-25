package com.digitalchina.info.appframework.metadata.type;

import java.util.List;
import java.util.Map;

import org.hibernate.type.Type;

/**
 * 抽象元数据类型的抽象实现类，起到Adapter的作用。
 * @Class Name AbstractMetaType
 * @Author peixf
 * @Create In 2008-6-30
 */
public abstract class AbstractMetaType implements MetaType {

	public List getChildList(Class clazz, String parentPropName, String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getParentList(Class clazz, String parentPropName, String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getChildList(Class clazz, String parentPropName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCnName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getDefaultSimpleKeyValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFrontChar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getList(Class clazz, String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getList(Class clazz, String propName, Object defaultValue, String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getList(Class clazz, String propName, Object defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getList(Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getParentList(Class clazz, String parentPropName) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public Type hibernateType() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAssociationType() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isMapType() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isGoogleable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isMutable() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isSearchable() {
		// TODO Auto-generated method stub
		return false;
	}

	public int sqlType() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
