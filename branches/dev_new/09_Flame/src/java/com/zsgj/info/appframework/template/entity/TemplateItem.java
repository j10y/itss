package com.zsgj.info.appframework.template.entity;

import java.util.HashSet;
import java.util.Set;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * 模板条目实体
 * @Class Name TemplateItem
 * @Author peixf
 * @Create In 2008-8-22
 */
public class TemplateItem extends BaseObject {
	private static final long serialVersionUID = 2772193021641320781L;
	
	private Long id;
	private String name;
	private Template template;
	private SystemMainTable systemMainTable;
	private SystemMainTableColumn mainTableColumn;
//	private SystemMainTableExtColumn extendTableColumn;
	private TemplateItem parentTemplateItem;
	private Set childTemplateItems = new HashSet();
	private Integer orderFlag;
	
	public String getName(){
		Column column = this.getColumn();
		return column.getColumnCnName();
	}
	
	public String getPropertyName(){
		Column column = this.getColumn();
		return column.getPropertyName();
	}
	
	public Column getColumn(){
		if(this.getMainTableColumn()!=null){
			return this.getMainTableColumn();
		}
//		else if(this.getExtendTableColumn()!=null){
//			return this.getExtendTableColumn();
//		}
		else {
			return null;
		}
	}
	
//	public SystemMainTableExtColumn getExtendTableColumn() {
//		return extendTableColumn;
//	}
//	public void setExtendTableColumn(SystemMainTableExtColumn extendTableColumn) {
//		this.extendTableColumn = extendTableColumn;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemMainTableColumn getMainTableColumn() {
		return mainTableColumn;
	}
	public void setMainTableColumn(SystemMainTableColumn mainTableColumn) {
		this.mainTableColumn = mainTableColumn;
	}
	public Integer getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}
	public TemplateItem getParentTemplateItem() {
		return parentTemplateItem;
	}
	public void setParentTemplateItem(TemplateItem parentTemplateItem) {
		this.parentTemplateItem = parentTemplateItem;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((mainTableColumn == null) ? 0 : mainTableColumn.hashCode());
		result = PRIME * result + ((systemMainTable == null) ? 0 : systemMainTable.hashCode());
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
		final TemplateItem other = (TemplateItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mainTableColumn == null) {
			if (other.mainTableColumn != null)
				return false;
		} else if (!mainTableColumn.equals(other.mainTableColumn))
			return false;
		if (systemMainTable == null) {
			if (other.systemMainTable != null)
				return false;
		} else if (!systemMainTable.equals(other.systemMainTable))
			return false;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		return true;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getChildTemplateItems() {
		return childTemplateItems;
	}

	public void setChildTemplateItems(Set childTemplateItems) {
		this.childTemplateItems = childTemplateItems;
	}
	
}
