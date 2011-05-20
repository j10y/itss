package com.zsgj.itil.actor.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 工程师状态实体
 * @Class Name SupportGroupEngineerStatus
 * @Author sa
 * @Create In 2009-3-19
 */
public class SupportGroupEngineerStatus extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -7271031178946106702L;
	public static String STATUS_IDLE = "idle"; //空闲
	public static String STATUS_BUSY = "busy"; //忙碌
	
	private Long id;	
	private String name; //状态
	private String keyword; //关键字，程序判断使用
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
