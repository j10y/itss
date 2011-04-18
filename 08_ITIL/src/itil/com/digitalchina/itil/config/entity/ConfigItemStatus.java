package com.digitalchina.itil.config.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ������״̬��Ϣ
 * �ɹ����ڿ⡢���ա���,��̭�����С����ۡ�����
 * @Class Name ConfigItemStatus
 * @Author sa
 * @Create In 2008-10-20
 */
public class ConfigItemStatus extends BaseObject{
	public static final String CONFIGITEMSTATUS_STANDBY = "standby";//����
	public static final String CONFIGITEMSTATUS_DISABLED = "Disabled";//����
	public static final String CONFIGITEMSTATUS_ARCHIVED = "Archived";//�ѹ鵵
	public static final String CONFIGITEMSTATUS_LOAN = "loan";//���
	private Long id;
	private String name;
	private String enname;
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
		final ConfigItemStatus other = (ConfigItemStatus) obj;
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
	public String getEnname() {
		return enname;
	}
	public void setEnname(String enname) {
		this.enname = enname;
	}
	
}
