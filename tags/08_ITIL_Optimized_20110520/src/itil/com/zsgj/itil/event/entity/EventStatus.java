package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 事件的状态
 * @Class Name EventStatus
 * @Author sa
 * @Create In 2008-11-9
 */
public class EventStatus extends BaseObject {
	public static String DEALING = "dealing";//处理中状态
	public static String FINISH = "finish";	//完成状态
	public static String CONFIRM = "confirm";	//重新指派
	public static String USERCONFIRM = "userconfirm";//用户确认
	private Long id;
	private String name;
	
	//状态关键字，程序使用
	private String keyword;
	//层次号
	private Integer rank;
	//颜色
	private String color;
	//是否已经关闭
	private Integer isclosed;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIsclosed() {
		return isclosed;
	}
	public void setIsclosed(Integer isclosed) {
		this.isclosed = isclosed;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
