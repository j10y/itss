package com.zsgj.info.framework.util.asm.render;

import java.util.List;
/*���ݿ�ӳ��������*/
public class RenderClass {
	
	/*����*/
	private String className;
	/*��Ӧ����*/
	private String tableName;
	/*����*/
	private List properties;
	
	public RenderClass()
	{
		properties = null;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List getProperties() {
		return properties;
	}

	public void setProperties(List properties) {
		this.properties = properties;
	}
	

	
}
