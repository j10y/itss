package com.zsgj.info.framework.util.asm.render;

import com.zsgj.info.framework.util.asm.BuildProperty;
/*���ݿ�ӳ������������*/
public class RenderProperty extends BuildProperty{
	/*�Ƿ�����*/
	private boolean primary;
	/*����*/
	private String sequence;
	/*�ֶγ���*/
	private Integer length;
	/*��Ӧ���ݿ��ֶ���*/	
	private String field;
	
	//��Ӧ������,��userRole
	private String refTable;
	//������Ӧ�ø�������ֶΣ���userId
	private String refPColumn;
	//����������ƣ�role,com.digitalchina.info.framework.security.entity.Role
	private String foreignClass; 
	//�����ñ��ֵ�ֶ�
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
