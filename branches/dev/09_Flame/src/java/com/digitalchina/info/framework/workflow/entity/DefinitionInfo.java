package com.digitalchina.info.framework.workflow.entity;

import org.jbpm.graph.def.ProcessDefinition;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.workflow.info.BaseInfo;
/**
 * ��װ���̶���
 * @Class Name DefinitionInfo
 * @Author yang
 * @Create In 2008-6-10
 */
public class DefinitionInfo extends BaseObject {
	Long id;
	Long processDefinitionId;
	String name;
	int version;//���̶���汾
	String startNodeName;//���̶��忪ʼ�ڵ�����
	String description;//���̶�����������
	DefinitionType type;//���̶�����������
	Department dept;//���̶�����������   
	String ruleName;
	
	  
	public DefinitionInfo() {
	}
	
	public DefinitionInfo(ProcessDefinition pd) {
		version = pd.getVersion();		
		startNodeName = pd.getStartState().getName();
		description = pd.getDescription();
		name=pd.getName();
		//id=pd.getId();
	}
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
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

	public DefinitionType getType() {
		return type;
	}

	public void setType(DefinitionType type) {
		this.type = type;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(Long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
}
