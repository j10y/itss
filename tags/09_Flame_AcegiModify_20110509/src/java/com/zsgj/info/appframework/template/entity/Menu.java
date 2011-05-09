package com.zsgj.info.appframework.template.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 菜单实体
 * @Class Name Menu
 * @Author peixf
 * @Create In 2008-7-16
 */
public class Menu extends BaseObject {
	private static final long serialVersionUID = 4689971643247080050L;

	private Long id;
	
	private String menuName;
	
	private String menuUrl;
	
	private Menu parentMenu;
	
	private Set childMenus = new HashSet();
	
	private Integer leafFlag; //是否是叶子节点
	
	private Integer menuLevel;
	
	private Integer menuOrder;

	
	public String getMenuFullName(){
		String name = this.getMenuName();
		Menu menu = this;
		while(menu.getParentMenu()!=null){
			name = menu.getParentMenu().getMenuName()+"->"+name;
			menu = menu.getParentMenu();
		}
		return name;	
		
	}
	
	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((menuName == null) ? 0 : menuName.hashCode());
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
		final Menu other = (Menu) obj;
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
		return true;
	}

	public Set getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set childMenus) {
		this.childMenus = childMenus;
	}

	public Integer getLeafFlag() {
		return leafFlag;
	}

	public void setLeafFlag(Integer leafFlag) {
		this.leafFlag = leafFlag;
	}
	

}
