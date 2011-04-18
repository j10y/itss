package com.digitalchina.info.appframework.menu.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 部门菜单项
 * @Class Name DeptMenuItem
 * @Author lee
 * @Create In Aug 12, 2010
 */
public class DeptMenuItem extends BaseObject{
	private Long id;	//自动编号
	private DeptMenu deptMenu;	//对应部门菜单
	private TemplateMenuItem templateMenuItem;	//模板菜单项
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DeptMenu getDeptMenu() {
		return deptMenu;
	}
	public void setDeptMenu(DeptMenu deptMenu) {
		this.deptMenu = deptMenu;
	}
	public TemplateMenuItem getTemplateMenuItem() {
		return templateMenuItem;
	}
	public void setTemplateMenuItem(TemplateMenuItem templateMenuItem) {
		this.templateMenuItem = templateMenuItem;
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
		final DeptMenuItem other = (DeptMenuItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
