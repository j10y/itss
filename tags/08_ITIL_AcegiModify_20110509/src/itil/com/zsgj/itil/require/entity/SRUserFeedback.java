package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 用户反馈
 * @Class Name SRUserFeedback
 * @Author lee
 * @Create In Aug 6, 2009
 */
public class SRUserFeedback extends BaseObject {
	private Long id;	//自动编号
	private SpecialRequirement specialRequire;	//需求
	private Integer complete;	//是否满足需求
	private Integer content;	//是否满意
	
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
