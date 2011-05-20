package com.zsgj.info.appframework.template.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Department;

/**
 * 部门菜单模板,部门和模板名称唯一标识一套部门模板，用于set存储时
 * @Class Name DeptMenuTemplate
 * @Author sa
 * @Create In 2008-8-29
 */
public class DeptMenuTemplate extends BaseObject {

	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -3084472795183890833L;
	private Long id;
	private String templateName;
	private SystemMenuTemplate systemMenuTemplate;
	private Department dept;
	private Integer adminFlag;
	
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemMenuTemplate getSystemMenuTemplate() {
		return systemMenuTemplate;
	}
	public void setSystemMenuTemplate(SystemMenuTemplate systemMenuTemplate) {
		this.systemMenuTemplate = systemMenuTemplate;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Integer getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(Integer adminFlag) {
		this.adminFlag = adminFlag;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((dept == null) ? 0 : dept.hashCode());
		result = PRIME * result + ((templateName == null) ? 0 : templateName.hashCode());
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
		final DeptMenuTemplate other = (DeptMenuTemplate) obj;
		if (dept == null) {
			if (other.dept != null)
				return false;
		} else if (!dept.equals(other.dept))
			return false;
		if (templateName == null) {
			if (other.templateName != null)
				return false;
		} else if (!templateName.equals(other.templateName))
			return false;
		return true;
	}
	
	

}
