package com.zsgj.info.appframework.template.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 用户菜单实体,存储用户可见的菜单设置
 * @Class Name UserMenu
 * @Author peixf
 * @Create In 2008-8-22
 */
public class UserMenuSetting extends BaseObject {
	private Long id;
	private UserInfo userInfo;
	private Menu menu;
	
	private UserMenuSetting parent;
	
	private Integer dispFlag;
	private Integer menuOrder;
	
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((menu == null) ? 0 : menu.hashCode());
		result = PRIME * result + ((userInfo == null) ? 0 : userInfo.hashCode());
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
		final UserMenuSetting other = (UserMenuSetting) obj;
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
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
	}
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
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Integer getDispFlag() {
		return dispFlag;
	}
	public void setDispFlag(Integer dispFlag) {
		this.dispFlag = dispFlag;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public UserMenuSetting getParent() {
		return parent;
	}
	public void setParent(UserMenuSetting parent) {
		this.parent = parent;
	}
	
}
