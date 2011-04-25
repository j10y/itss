package com.digitalchina.itil.knowledge.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.itil.event.entity.SCIDData;
import com.digitalchina.itil.event.entity.SCIDType;

/**
 * �¼������������������ϲ���֪ʶ��������Ǵ�����������
 * �������ù���
 * @Class Name KnowledgeConfigItem
 * @Author sa
 * @Create In 2009-3-5
 */
public class KnowledgeConfigItem extends BaseObject {
	private Long id;
	private Knowledge knowledge;//֪ʶ
	private Integer type;//������������
	private Long scitype;//����������
	private Long sciddata;//����������
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Knowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getScitype() {
		return scitype;
	}
	public void setScitype(Long scitype) {
		this.scitype = scitype;
	}
	public Long getSciddata() {
		return sciddata;
	}
	public void setSciddata(Long sciddata) {
		this.sciddata = sciddata;
	}

	
	
}
