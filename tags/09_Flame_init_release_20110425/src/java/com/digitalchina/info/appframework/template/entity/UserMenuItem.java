package com.digitalchina.info.appframework.template.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 用户部门菜单模板项，用户菜单项
 * @Class Name UserMenuItem
 * @Author sa
 * @Create In 2008-8-29
 */
public class UserMenuItem extends BaseObject {
	
	private Long id;
	
	private UserMenu userMenu; //所属用户菜单/用户部门菜单模板

	private String menuName;
	
	private String menuUrl;
	
	private DeptMenuTemplateItem deptMenuTemplateItem;
	
	private UserMenuItem parentMenu; //上级菜单
	
	private Set childMenus = new HashSet();
	
	private Integer leafFlag; //是否是叶子节点
	
	private Integer menuLevel;
	
	private Integer menuOrder;
	
	private Integer enabled; //是否可见
	private String childmenu;//临时性的数据，用于组装数据
    
	public String getChildmenu() {
		return childmenu;
	}
	public void setChildmenu(String childmenu) {
		this.childmenu = childmenu;
	}

	public Set getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set childMenus) {
		this.childMenus = childMenus;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
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

	public UserMenuItem getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(UserMenuItem parentMenu) {
		this.parentMenu = parentMenu;
	}

	public UserMenu getUserMenu() {
		return userMenu;
	}

	public void setUserMenu(UserMenu userMenu) {
		this.userMenu = userMenu;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = PRIME * result + ((parentMenu == null) ? 0 : parentMenu.hashCode());
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
		final UserMenuItem other = (UserMenuItem) obj;
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
		if (parentMenu == null) {
			if (other.parentMenu != null)
				return false;
		} else if (!parentMenu.equals(other.parentMenu))
			return false;
		return true;
	}

	public DeptMenuTemplateItem getDeptMenuTemplateItem() {
		return deptMenuTemplateItem;
	}

	public void setDeptMenuTemplateItem(DeptMenuTemplateItem deptMenuTemplateItem) {
		this.deptMenuTemplateItem = deptMenuTemplateItem;
	}

}
