package com.zsgj.itil.event.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.knowledge.entity.Knowledge;

/**
 * 事件解决方案关系
 * @Class Name EventTelephoneInfo
 * @Author sa
 * @Create In 2009-3-11
 */
@SuppressWarnings("serial")
public class EventSulotion extends BaseObject {
	private Long id;
	private Event event;//关联事件
	private Knowledge knowledge; //解决方案
	private UserInfo creatName; //创建人姓名
	private Date createDate; //创建时间
	/**
	 * @Return the UserInfo creatName
	 */

	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Return the Event event
	 */
	public Event getEvent() {
		return event;
	}
	/**
	 * @Param Event event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	/**
	 * @Return the Knowledge knowledge
	 */
	public Knowledge getKnowledge() {
		return knowledge;
	}
	/**
	 * @Param Knowledge knowledge to set
	 */
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	/**
	 * @Return the String creatName
	 */
	
	/**
	 * @Return the Date createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @Param Date createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public UserInfo getCreatName() {
		return creatName;
	}
	public void setCreatName(UserInfo creatName) {
		this.creatName = creatName;
	}
	
	
	
	
}