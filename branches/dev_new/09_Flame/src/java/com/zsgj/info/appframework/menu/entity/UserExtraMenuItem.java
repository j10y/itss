package com.zsgj.info.appframework.menu.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 用户菜单项
 * @Class Name UserMenuItem
 * @Author lee
 * @Create In Aug 12, 2010
 */
public class UserExtraMenuItem extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -191810083107104266L;
	private Long id;	//自动编号
	private UserInfo userInfo;	//对应部门菜单
	private TemplateMenuItem templateMenuItem;	//模板菜单项
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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
		final UserExtraMenuItem other = (UserExtraMenuItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
