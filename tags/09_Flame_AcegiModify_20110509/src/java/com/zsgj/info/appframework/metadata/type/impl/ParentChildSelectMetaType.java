package com.zsgj.info.appframework.metadata.type.impl;

import java.util.List;

import com.zsgj.info.appframework.metadata.service.MetaDataService;
import com.zsgj.info.appframework.metadata.type.AbstractMetaType;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;

public class ParentChildSelectMetaType  extends AbstractMetaType {

	@Override
	public List getChildList(Class clazz, String parentPropName) {
		Service bs = (Service)ContextHolder.getBean("baseService");
		List list = bs.findAllChildBy(clazz, parentPropName, null, false);
		return list;
	}
	@Override
	public String getCnName() {
		return "¸¸×ÓÁÐ±í";
	}
	@Override
	public List getList(Class clazz, String orderProp, boolean isAsc) {
		Service bs = (Service)ContextHolder.getBean("baseService");
		return bs.findAllBy(clazz, orderProp, isAsc);
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
	public List getList(Class clazz) {
		Service bs = (Service)ContextHolder.getBean("baseService");
		return bs.findAll(clazz);
	}
	@Override
	public List getParentList(Class clazz, String parentPropName) {
		Service bs = (Service)ContextHolder.getBean("baseService");
		return bs.findAllTopBy(clazz, parentPropName, null, false);
	}
}
