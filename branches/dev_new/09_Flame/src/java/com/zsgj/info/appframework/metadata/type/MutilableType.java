package com.zsgj.info.appframework.metadata.type;

import java.util.List;

/**
 * 多值元数据类型
 * @Class Name MutilableType
 * @Author peixf
 * @Create In 2008-7-1
 */
public interface MutilableType extends MetaType{
	
	public List getOriginalSelect(Class clazz);

	public List getOriginalSelect(Class clazz, String orderProp, boolean isAsc);

	public List getOriginalSelect(Class clazz, String propName, Object defaultValue);

	public List getOriginalSelect(Class clazz, String propName, Object defaultValue, String orderProp, boolean isAsc);
}
