package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * �¼���״̬
 * @Class Name EventStatus
 * @Author sa
 * @Create In 2008-11-9
 */
public class EventStatus extends BaseObject {
	public static String DEALING = "dealing";//������״̬
	public static String FINISH = "finish";	//���״̬
	public static String CONFIRM = "confirm";	//����ָ��
	public static String USERCONFIRM = "userconfirm";//�û�ȷ��
	private Long id;
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
