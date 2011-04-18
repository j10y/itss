package com.digitalchina.info.framework.workflow.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ����������Ľ�ɫ������������Աָ�ɵļ���
 * @Class Name WorkflowRole
 * @Author yang
 * @Create In Dec 8, 2008
 */
public class WorkflowRole extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;
	Long id;
	String code;
	String name;
	int deleteFlag;//ɾ����ǣ�1��Ϊ��ɾ��,0��Ϊδɾ��
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + deleteFlag;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final WorkflowRole other = (WorkflowRole) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (deleteFlag != other.deleteFlag)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
