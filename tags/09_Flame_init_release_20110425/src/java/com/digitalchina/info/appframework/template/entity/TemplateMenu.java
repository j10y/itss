package com.digitalchina.info.appframework.template.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 模板菜单实体，存储菜单模板和菜单的映射关系
 * @Class Name TemplateMenu
 * @Author peixf
 * @Create In 2008-7-16
 */
public class TemplateMenu extends BaseObject {
	private static final long serialVersionUID = -8468625598009193073L;
	
	private Long id;
	private MenuTemplate template;
	private TemplateMenu parentTemplateMenu;
	private Menu menu;
	private Integer menuOrder;
	
	public Long getId() {
		return id;
	} 
	public void setId(Long id) {
		this.id = id;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public MenuTemplate getTemplate() {
		return template;
	}
	public void setTemplate(MenuTemplate template) {
		this.template = template;
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((menu == null) ? 0 : menu.hashCode());
		result = PRIME * result + ((template == null) ? 0 : template.hashCode());
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
		final TemplateMenu other = (TemplateMenu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		return true;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public TemplateMenu getParentTemplateMenu() {
		return parentTemplateMenu;
	}
	public void setParentTemplateMenu(TemplateMenu parentTemplateMenu) {
		this.parentTemplateMenu = parentTemplateMenu;
	}
}
