package com.digitalchina.itil.train.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ´ð°¸ÀàÐÍA,B,C,D
 * @Class Name AnswerNo
 * @Author Administrator
 * @Create In Feb 23, 2009
 */
public class AnswerNo extends BaseObject{
	private Long id;
	private String answerNoName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((answerNoName == null) ? 0 : answerNoName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AnswerNo other = (AnswerNo) obj;
		if (answerNoName == null) {
			if (other.answerNoName != null)
				return false;
		} else if (!answerNoName.equals(other.answerNoName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getAnswerNoName() {
		return answerNoName;
	}
	public void setAnswerNoName(String answerNoName) {
		this.answerNoName = answerNoName;
	}
}
