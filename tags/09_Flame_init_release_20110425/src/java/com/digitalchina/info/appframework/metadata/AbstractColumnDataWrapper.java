package com.digitalchina.info.appframework.metadata;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.type.MetaType;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 抽象字段数据包装器，框架version3新增
 * @Class Name AbstractColumnDataWrapper
 * @Author peixf
 * @Create In 2008-7-1
 */ 
public abstract class AbstractColumnDataWrapper {
	protected BaseObject object;

	protected MetaType metaType;

	protected Column column;
	
	public AbstractColumnDataWrapper(){}
	
	public AbstractColumnDataWrapper(Column column){
		this.column = column;
	}

	public abstract List getList(Column column);

	public abstract List getParentList(Column column);

	public abstract List getChildList(Column column);

	public abstract Map getSimpleKeyValues(Column column);

	public abstract Long getKey();

	public abstract String getText();

	public abstract Object getObject();
	
	

}


//private Long key; 	//属性值的Long表现形式
//private String text; //属性值的文本表现形式
//private Object value; //属性值

//private List list = new ArrayList(0);
//private List parentList = new ArrayList(0);
//private List allList = new ArrayList(0);
//private Map map = new HashMap();
