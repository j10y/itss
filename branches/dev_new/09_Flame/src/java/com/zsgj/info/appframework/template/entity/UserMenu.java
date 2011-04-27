package com.zsgj.info.appframework.template.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 用户部门菜单模板
 * 备注：对于类合同分析报告模板，将deptMenuTemplate替换为其模板
 * @Class Name UserDeptMenuTemplate
 * @Author sa
 * @Create In 2008-8-29
 */
public class UserMenu extends BaseObject{
	
	private Long id;
	private UserInfo userInfo;
	private DeptMenuTemplate deptMenuTemplate;
	
	private boolean removeFlag = false;
	
	public boolean isRemoveFlag() {
		return removeFlag;
	}
	public void setRemoveFlag(boolean removeFlag) {
		this.removeFlag = removeFlag;
	}
	public DeptMenuTemplate getDeptMenuTemplate() {
		return deptMenuTemplate;
	}
	public void setDeptMenuTemplate(DeptMenuTemplate deptMenuTemplate) {
		this.deptMenuTemplate = deptMenuTemplate;
	}
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
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((deptMenuTemplate == null) ? 0 : deptMenuTemplate.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
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
		final UserMenu other = (UserMenu) obj;
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
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
	}
}
