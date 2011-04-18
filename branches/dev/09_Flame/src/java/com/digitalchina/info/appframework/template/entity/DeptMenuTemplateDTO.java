package com.digitalchina.info.appframework.template.entity;


public class DeptMenuTemplateDTO implements java.io.Serializable{
	private static final long serialVersionUID = -469641231286537081L;
	
	private Long id;
	private String templateName;
	private String systemMenuTemplate;
	private String dept;
	private Integer adminFlag;
	
	public DeptMenuTemplateDTO(){}
	
	public DeptMenuTemplateDTO(DeptMenuTemplate dmt){
		this.id = dmt.getId();
		this.templateName = dmt.getTemplateName();
		this.systemMenuTemplate = dmt.getSystemMenuTemplate().getTemplateName();
		this.dept = dmt.getDept().getDepartName();
		this.adminFlag = dmt.getAdminFlag();
	}
	
	public Integer getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(Integer adminFlag) {
		this.adminFlag = adminFlag;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSystemMenuTemplate() {
		return systemMenuTemplate;
	}
	public void setSystemMenuTemplate(String systemMenuTemplate) {
		this.systemMenuTemplate = systemMenuTemplate;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
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
		final DeptMenuTemplateDTO other = (DeptMenuTemplateDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (templateName == null) {
			if (other.templateName != null)
				return false;
		} else if (!templateName.equals(other.templateName))
			return false;
		return true;
	}
}
