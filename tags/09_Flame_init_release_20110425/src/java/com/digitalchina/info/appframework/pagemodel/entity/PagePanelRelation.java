package com.digitalchina.info.appframework.pagemodel.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * pagePanel之间关系实体
 * 记录分组面板包含哪些子面板
 * @Class Name PagePanelRelation
 * @Author sa
 * @Create In 2008-12-18
 */
@SuppressWarnings("serial")
public class PagePanelRelation extends BaseObject {
	private Long id;
	//当前的分组模板
	private PagePanel parentPagePanel;
	//一个具体的子模板
	private PagePanel pagePanel;
	//排序
	private Integer order;
	//子模板是否显示标题
	private Integer titleDisplayFlag;
	//子模板是否显示
	private Integer isDisplay;
	//是否必须显示
	private Integer isMustInput;
	//只读属性，这个子模板中字段数据只用来读
	private Integer readonly;

	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PagePanel getParentPagePanel() {
		return parentPagePanel;
	}
	public void setParentPagePanel(PagePanel parentPagePanel) {
		this.parentPagePanel = parentPagePanel;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public Integer getIsMustInput() {
		return isMustInput;
	}
	public void setIsMustInput(Integer isMustInput) {
		this.isMustInput = isMustInput;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getReadonly() {
		return readonly;
	}
	public void setReadonly(Integer readonly) {
		this.readonly = readonly;
	}
	public Integer getTitleDisplayFlag() {
		return titleDisplayFlag;
	}
	public void setTitleDisplayFlag(Integer titleDisplayFlag) {
		this.titleDisplayFlag = titleDisplayFlag;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((order == null) ? 0 : order.hashCode());
		result = PRIME * result + ((pagePanel == null) ? 0 : pagePanel.hashCode());
		result = PRIME * result + ((parentPagePanel == null) ? 0 : parentPagePanel.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PagePanelRelation other = (PagePanelRelation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (pagePanel == null) {
			if (other.pagePanel != null)
				return false;
		} else if (!pagePanel.equals(other.pagePanel))
			return false;
		if (parentPagePanel == null) {
			if (other.parentPagePanel != null)
				return false;
		} else if (!parentPagePanel.equals(other.parentPagePanel))
			return false;
		return true;
	}
}
