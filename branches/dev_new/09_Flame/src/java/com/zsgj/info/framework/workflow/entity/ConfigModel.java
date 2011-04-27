package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * ConfigModelʵ�壬��һ��NodeType��ConfigUnit���м��������ʾNodeType���ڵ����ͣ�
 * ��ConfigUnit�����õ�Ԫ���Ĺ�ϵ��
 * @Class Name ConfigModel
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class ConfigModel extends BaseObject{
	private Long id ;
	private String nodeType;
	private ConfigUnit configUnit;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public ConfigUnit getConfigUnit() {
		return configUnit;
	}
	public void setConfigUnit(ConfigUnit configUnit) {
		this.configUnit = configUnit;
	}

}
