package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 *  实体对象的状态
 *  
 *  草稿，提交，审批中，正式版，删除。
 *  
 * @Class Name EntityObjectStatus
 * @Author sa
 * @Create In 2009-2-23
 */
public class EntityObjectStatus extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -198354101147239117L;
	private Long id;
	private String name;
//	是否记历史标志，如提交，删除
	private Integer eventFlag; 
	
	public Integer getEventFlag() {
		return eventFlag;
	}
	public void setEventFlag(Integer eventFlag) {
		this.eventFlag = eventFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
