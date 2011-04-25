package com.digitalchina.itil.service.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 服务分类
 * @Class Name ServiceType
 * @Author sa
 * @Create In 2008-10-20
 */
public class ServiceType extends BaseObject{
// remove by lee for 废弃属性 in 20091121 begin
//	public static String TYPE_GENERAL="general";//常规
//	public static String TYPE_SPECIAL="special";//个性化
//	public static String TYPE_FIXED="fixed";	//固定
//	public static String Type_ACCOUNT="account";//账号类
// remove by lee for 废弃属性 in 20091121 end
	private Long id;
	private String name;//显示字段
	private String keyWord;//后台使用字段
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
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ServiceType other = (ServiceType) obj;
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
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
}
