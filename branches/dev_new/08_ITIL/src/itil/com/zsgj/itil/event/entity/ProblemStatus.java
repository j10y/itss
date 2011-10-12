package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * �����״̬
 * @Class Name ProblemStatus
 * @Author sa
 * @Create In 2008-11-10
 */
@SuppressWarnings("serial")
public class ProblemStatus extends BaseObject {
	public static final String KEYWORD_DEALING="dealing";
	public static final String KEYWORD_FINISH="finish";
	public static final String KEYWORD_DELETE="delete";
	private Long id;
	//״̬����
	private String name;
	//״̬�ؼ��֣�����ʹ��
	private String keyword;
	//��κ�
	private Integer rank;
	//��ɫ 
	private String color;
	//�Ƿ��Ѿ��ر�
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
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
}