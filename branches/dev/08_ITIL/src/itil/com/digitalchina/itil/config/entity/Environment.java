package com.digitalchina.itil.config.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 配置单所属的环境
 *  开发环境：正在进行开发或者搭建的资产/配置项
	测试环境：正在进行测试的资产/配置项
	正式环境：测试完成，正式使用的环境。
	其他：没有在以上三个环境中
 * @Class Name Environment
 * @Author sa
 * @Create In 2008-10-27
 */
public class Environment extends BaseObject {

	private Long id;
	private String name;
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
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
		final Environment other = (Environment) obj;
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
