package com.digitalchina.itil.train.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;


/**
 * 调查问题。单选，复选，填空题，问答题
 * 允许一个调查项目中有多道题目Quest
 * @Class Name Quest
 * @Author Administrator
 * @Create In 2008-7-12
 */
public class Quest extends BaseObject {
	private Long id;
	private String questName;
	private String content; //问题内容
	private QuestType questType; //问题类型
	private Survey survey; //问题所属的调查
	
	private Set questOptions = new HashSet();


	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((content == null) ? 0 : content.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((questType == null) ? 0 : questType.hashCode());
		result = PRIME * result + ((survey == null) ? 0 : survey.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Quest other = (Quest) obj;
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
		if (questType == null) {
			if (other.questType != null)
				return false;
		} else if (!questType.equals(other.questType))
			return false;
		if (survey == null) {
			if (other.survey != null)
				return false;
		} else if (!survey.equals(other.survey))
			return false;
		return true;
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

	public Set getQuestOptions() {
		return questOptions;
	}

	public void setQuestOptions(Set questOptions) {
		this.questOptions = questOptions;
	}

	public QuestType getQuestType() {
		return questType;
	}

	public void setQuestType(QuestType questType) {
		this.questType = questType;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public String getQuestName() {
		return questName;
	}

	public void setQuestName(String questName) {
		this.questName = questName;
	}

}
