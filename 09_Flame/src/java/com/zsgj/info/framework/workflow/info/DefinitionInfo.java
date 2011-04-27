package com.zsgj.info.framework.workflow.info;

import org.jbpm.graph.def.ProcessDefinition;
/**
 * ��װ���̶���
 * @Class Name DefinitionInfo
 * @Author yang
 * @Create In 2008-6-10
 */
public class DefinitionInfo extends BaseInfo {
	int version;//�汾
	String startNodeName;//��ʼ�ڵ�����
	String description;//��������
	
	public DefinitionInfo() {
	}
	
	public DefinitionInfo(ProcessDefinition pd) {
		version = pd.getVersion();		
		startNodeName = pd.getStartState().getName();
		description = pd.getDescription();
		setName(pd.getName());
		setId(pd.getId());
	}
	public static DefinitionInfo copy(ProcessDefinition pd) {
		return new DefinitionInfo(pd);
	}
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getStartNodeName() {
		return startNodeName;
	}

	public void setStartNodeName(String startNodeName) {
		this.startNodeName = startNodeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
