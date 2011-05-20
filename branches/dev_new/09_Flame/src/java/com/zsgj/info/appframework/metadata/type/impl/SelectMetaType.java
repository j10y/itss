package com.zsgj.info.appframework.metadata.type.impl;

import java.util.List;

import org.hibernate.type.Type;

import com.zsgj.info.appframework.metadata.type.AbstractMetaType;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;

public class SelectMetaType extends AbstractMetaType {

	
	@Override
	public String getCnName() {
		return "下拉列表";
	}

	@Override
	public List getList(Class clazz, String orderProp, boolean isAsc) {
	    Service bs = (Service)ContextHolder.getBean("baseService");
		List list = bs.findAllBy(clazz, orderProp, isAsc);
		return list;
	}

	@Override
	public List getList(Class clazz, String propName, Object defaultValue, String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return super.getList(clazz, propName, defaultValue, orderProp, isAsc);
	}

	@Override
	public List getList(Class clazz, String propName, Object defaultValue) {
		// TODO Auto-generated method stub
		return super.getList(clazz, propName, defaultValue);
	}

	@Override
	public String getName() {
		return "select";
	}

	@Override
	public Type hibernateType() {
		// TODO Auto-generated method stub
		return super.hibernateType();
	}

	@Override
	public int sqlType() {
		// TODO Auto-generated method stub
		return super.sqlType();
	}

}
