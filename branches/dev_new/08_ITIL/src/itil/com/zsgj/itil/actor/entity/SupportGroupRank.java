package com.zsgj.itil.actor.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * ֧���鼶��
 * 
 * @Class Name SupportGroupRank
 * @Author daijf
 * @Create In 2009-4-10
 */
public class SupportGroupRank extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 4650333100018696685L;
	public static final String FIRST_RANK="first";//һ��֧����
	public static final String SECOND_RANK="second";//����֧����
	private java.lang.Long id;
	private java.lang.String rank;
	private String keyString;//��Ϊ��ʶ

	public String getKeyString() {
		return keyString;
	}
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getRank() {
		return rank;
	}

	public void setRank(java.lang.String rank) {
		this.rank = rank;
	}

}
