package com.zsgj.info.framework.util.asm.render;

import com.zsgj.info.framework.util.asm.BuildProperty;
/*数据库映射类属性描述*/
public class RenderProperty extends BuildProperty{
	/*是否主键*/
	private boolean primary;
	/*序列*/
	private String sequence;
	/*字段长度*/
	private Integer length;
	/*对应数据库字段名*/	
	private String field;
	
	//对应关联表,如userRole
	private String refTable;
	//关联表应用根对象的字段，如userId
	private String refPColumn;
	//外键表类名称，role,com.digitalchina.info.framework.security.entity.Role
	private String foreignClass; 
	//被引用表的值字段
	private String refVColunn;
	
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public boolean getPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}


	public String getRefTable() {
		return refTable;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public String getRefPColumn() {
		return refPColumn;
	}

	public void setRefPColumn(String refPColumn) {
		this.refPColumn = refPColumn;
	}

	public String getForeignClass() {
		return foreignClass;
	}

	public void setForeignClass(String foreignClass) {
		this.foreignClass = foreignClass;
	}

	public String getRefVColunn() {
		return refVColunn;
	}

	public void setRefVColunn(String refVColunn) {
		this.refVColunn = refVColunn;
	}
		
		
}
