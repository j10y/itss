package com.digitalchina.info.appframework.template.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 系统菜单模板条目
 * @Class Name SystemMenuTemplateItem
 * @Author sa
 * @Create In 2008-8-29
 */
public class SystemMenuTemplateItem extends BaseObject {

	private Long id;
	private SystemMenuTemplate systemMenuTemplate;

	private String menuName;
	
	private String menuUrl;
	
	private SystemMenuTemplateItem parentMenu;
	
	private Set childMenus = new HashSet();
	
	private Integer leafFlag; //是否是叶子节点
	
	private Integer menuLevel;
	
	private Integer menuOrder;

	public SystemMenuTemplateItem(Long id) {
		super();
		this.id = id;
	}

	public SystemMenuTemplateItem() {
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = PRIME * result + ((parentMenu == null) ? 0 : parentMenu.hashCode());
		result = PRIME * result + ((systemMenuTemplate == null) ? 0 : systemMenuTemplate.hashCode());
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
		final SystemMenuTemplateItem other = (SystemMenuTemplateItem) obj;
		if (menuName == null) {
			if (other.menuName != null)
				return false;
		} else if (!menuName.equals(other.menuName))
			return false;
		if (parentMenu == null) {
			if (other.parentMenu != null)
				return false;
		} else if (!parentMenu.equals(other.parentMenu))
			return false;
		if (systemMenuTemplate == null) {
			if (other.systemMenuTemplate != null)
				return false;
		} else if (!systemMenuTemplate.equals(other.systemMenuTemplate))
			return false;
		return true;
	}

	public Set getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set childMenus) {
		this.childMenus = childMenus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLeafFlag() {
		return leafFlag;
	}

	public void setLeafFlag(Integer leafFlag) {
		this.leafFlag = leafFlag;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public SystemMenuTemplateItem getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(SystemMenuTemplateItem parentMenu) {
		this.parentMenu = parentMenu;
	}

	public SystemMenuTemplate getSystemMenuTemplate() {
		return systemMenuTemplate;
	}

	public void setSystemMenuTemplate(SystemMenuTemplate systemMenuTemplate) {
		this.systemMenuTemplate = systemMenuTemplate;
	}

	

}
