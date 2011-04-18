package com.digitalchina.info.appframework.template.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 部门菜单模板的菜单项
 * @Class Name DeptMenuTemplateItem
 * @Author sa
 * @Create In 2008-8-29
 */
public class DeptMenuTemplateItem extends BaseObject {

	private Long id;
	
	private DeptMenuTemplate deptMenuTemplate;//所属部门模板
	
	private String menuName;
	
	private String menuUrl;
	
	private SystemMenuTemplateItem systemMenuTemplateItem;
	
	private DeptMenuTemplateItem parentMenu; //上级菜单
	
	private Set childMenus = new HashSet();
	
	private Integer leafFlag; //是否是叶子节点
	
	private Integer menuLevel;
	
	private Integer menuOrder;
	
	private Integer enabled; //是否可用，不可用用户则看不见

	
	public DeptMenuTemplateItem(Long id) {
		super();
		this.id = id;
	}
	
	public DeptMenuTemplateItem() {
	}

	public Set getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set childMenus) {
		this.childMenus = childMenus;
	}

	public DeptMenuTemplate getDeptMenuTemplate() {
		return deptMenuTemplate;
	}

	public void setDeptMenuTemplate(DeptMenuTemplate deptMenuTemplate) {
		this.deptMenuTemplate = deptMenuTemplate;
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

	public DeptMenuTemplateItem getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(DeptMenuTemplateItem parentMenu) {
		this.parentMenu = parentMenu;
	}

	

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((deptMenuTemplate == null) ? 0 : deptMenuTemplate.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((menuName == null) ? 0 : menuName.hashCode());
		result = PRIME * result + ((menuUrl == null) ? 0 : menuUrl.hashCode());
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
		final DeptMenuTemplateItem other = (DeptMenuTemplateItem) obj;
		if (deptMenuTemplate == null) {
			if (other.deptMenuTemplate != null)
				return false;
		} else if (!deptMenuTemplate.equals(other.deptMenuTemplate))
			return false;
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

	public SystemMenuTemplateItem getSystemMenuTemplateItem() {
		return systemMenuTemplateItem;
	}

	public void setSystemMenuTemplateItem(
			SystemMenuTemplateItem systemMenuTemplateItem) {
		this.systemMenuTemplateItem = systemMenuTemplateItem;
	}
	
	

}
