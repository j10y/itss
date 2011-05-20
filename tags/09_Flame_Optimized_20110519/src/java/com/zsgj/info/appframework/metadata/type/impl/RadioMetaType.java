package com.zsgj.info.appframework.metadata.type.impl;

import java.util.List;

import com.zsgj.info.appframework.metadata.service.MetaDataService;
import com.zsgj.info.appframework.metadata.type.AbstractMetaType;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;

public class RadioMetaType extends AbstractMetaType{

	@SuppressWarnings("unused")
	private MetaDataService ms; /* = (MetaDataService)ContextHolder.getBean("metaDataService");*/
	private Service bs;
	
	@Override
	public String getCnName() {
		return "µ¥Ñ¡¿ò";
	}

	@Override
	public List getList(Class clazz, String orderProp, boolean isAsc) {
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
		return bs.findAll(clazz);
	}

	@Override
	public String getName() {
		return "radio";
	}

	
}
