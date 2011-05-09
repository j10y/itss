package com.zsgj.info.appframework.menu.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * ģ��˵���
 * @Class Name TemplateMenuItem
 * @Author lee
 * @Create In Aug 12, 2010
 */
public class TemplateMenuItem extends BaseObject{
	private Long id;
	private String menuName;	//�˵�����
	private String menuUrl;		//�˵�����
	private TemplateMenuItem parentItem;	//���˵���
	private Set childItems = new HashSet();	//�Ӳ˵���
	private Integer leafFlag; 	//�Ƿ���Ҷ�ӽڵ�
	private Integer menuOrder;	//����
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public TemplateMenuItem getParentItem() {
		return parentItem;
	}
	public void setParentItem(TemplateMenuItem parentItem) {
		this.parentItem = parentItem;
	}
	public Integer getLeafFlag() {
		return leafFlag;
	}
	public void setLeafFlag(Integer leafFlag) {
		this.leafFlag = leafFlag;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public Set getChildItems() {
		return childItems;
	}
	public void setChildItems(Set childItems) {
		this.childItems = childItems;
	}
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TemplateMenuItem other = (TemplateMenuItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
			return false;
		if (menuUrl == null) {
			if (other.menuUrl != null)
				return false;
		} else if (!menuUrl.equals(other.menuUrl))
			return false;
		return true;
	}
}
