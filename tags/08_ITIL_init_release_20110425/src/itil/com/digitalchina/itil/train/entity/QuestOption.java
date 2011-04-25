package com.digitalchina.itil.train.entity;

import com.digitalchina.info.framework.dao.BaseObject;



public class QuestOption extends BaseObject {

	private Long id;
	
	private String answerNo;//问题答案编号 如A,B,C,D

	private String content; //问题选项内容

	private Quest quest; //选项所属问题
	
	private Long answerUsers; //暂存该选项的票数

	public Long getAnswerUsers() {
		return answerUsers;
	}

	public void setAnswerUsers(Long answerUsers) {
		this.answerUsers = answerUsers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Quest getQuest() {
		return quest;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((content == null) ? 0 : content.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((quest == null) ? 0 : quest.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final QuestOption other = (QuestOption) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (quest == null) {
			if (other.quest != null)
				return false;
		} else if (!quest.equals(other.quest))
			return false;
		return true;
	}

	public String getAnswerNo() {
		return answerNo;
	}

	public void setAnswerNo(String answerNo) {
		this.answerNo = answerNo;
	}





}
