package com.zsgj.info.appframework.metadata.type.impl;

import java.util.List;

import com.zsgj.info.appframework.metadata.service.MetaDataService;
import com.zsgj.info.appframework.metadata.type.AbstractMetaType;
import com.zsgj.info.appframework.metadata.type.MutilableType;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.service.Service;

public class MultiSelectMetaType extends AbstractMetaType implements MutilableType{

	public List getOriginalSelect(Class clazz) {
		Service bs = (Service)ContextHolder.getBean("baseService");
		return bs.findAll(clazz);
	}

	
	public List getOriginalSelect(Class clazz, String orderProp, boolean isAsc) {
		@SuppressWarnings("unused")
		MetaDataService ms = (MetaDataService)ContextHolder.getBean("metaDataService");
		Service bs = (Service)ContextHolder.getBean("baseService");
		return bs.findAllBy(clazz, orderProp, isAsc);
	}


	public List getOriginalSelect(Class clazz, String propName, Object defaultValue, String orderProp, boolean isAsc) {
		return null;
	}


	public List getOriginalSelect(Class clazz, String propName, Object defaultValue) {
		return null;
	}


	@Override
	public String getName() {
		return "multiSelect";
	}


	@Override
	public String getCnName() {
		return "¸´Ñ¡ÁÐ±í";
	}

}
