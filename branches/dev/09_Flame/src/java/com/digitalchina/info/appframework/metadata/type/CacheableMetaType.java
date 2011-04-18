package com.digitalchina.info.appframework.metadata.type;

import java.util.List;
import java.util.Map;

public interface CacheableMetaType extends MetaType{

	public List getContextList(String name);
		
	public List getContextParentList(String name, String parentPropName);

	public List getContextChildList(String name, String parentPropName);

	public Map getContextSimpleKeyValue(String name);
	
	public List getCacheList(String regionName);

	public List getCacheParentList(String regionName);

	public List getCacheChildList(String regionName);

	public Map getCacheSimpleKeyValue(String regionName);
}



//
//public List getContextList(String name, Class clazz);
//
//public List getContextList(String name, Class clazz, String orderProp, boolean isAsc);
//
//public List getContextParentList(String name, Class clazz, String parentPropName);
//
//public List getContextChildList(String name, Class clazz, String parentPropName);