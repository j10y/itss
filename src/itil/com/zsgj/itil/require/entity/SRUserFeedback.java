package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * �û�����
 * @Class Name SRUserFeedback
 * @Author lee
 * @Create In Aug 6, 2009
 */
public class SRUserFeedback extends BaseObject {
	private Long id;	//�Զ����
	private SpecialRequirement specialRequire;	//����
	private Integer complete;	//�Ƿ���������
	private Integer content;	//�Ƿ�����
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SpecialRequirement getSpecialRequire() {
		return specialRequire;
	}
	public void setSpecialRequire(SpecialRequirement specialRequire) {
		this.specialRequire = specialRequire;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getContent() {
		return content;
	}
	public void setContent(Integer content) {
		this.content = content;
	}
}
