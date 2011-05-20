package com.zsgj.info.framework.security.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 人事子范围
 * @Class Name PersonnelScope
 * @Author lee
 * @Create In Jul 22, 2009
 */
public class PersonnelScope extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 6084840193526587369L;
	private Long id;		//自动编号
	private String personnelScopeCode;//人事子范围编号
	private String name;	//名称
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
	
	
	
	public String getPersonnelScopeCode() {
		return personnelScopeCode;
	}
	public void setPersonnelScopeCode(String personnelScopeCode) {
		this.personnelScopeCode = personnelScopeCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		final PersonnelScope other = (PersonnelScope) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (personnelScopeCode == null) {
			if (other.personnelScopeCode != null)
				return false;
		} else if (!personnelScopeCode.equals(other.personnelScopeCode))
			return false;
		return true;
	}
}
