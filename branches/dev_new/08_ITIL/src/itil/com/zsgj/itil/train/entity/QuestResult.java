package com.zsgj.itil.train.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;


public class QuestResult  extends BaseObject{

	private Long id;
	
	private UserInfo userId;
	
	private QuestOption questOption;
	
	private Quest quest;
	
	private TrainCourse trainCourse;
	
	private Survey survey;
	
	//与具体实体类相关
	private Long objId;
	
	private String answer;//add by lee for 保存非选择类答案 in 20090922

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((quest == null) ? 0 : quest.hashCode());
		result = PRIME * result + ((questOption == null) ? 0 : questOption.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final QuestResult other = (QuestResult) obj;
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
		if (questOption == null) {
			if (other.questOption != null)
				return false;
		} else if (!questOption.equals(other.questOption))
			return false;
		return true;
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

	public QuestOption getQuestOption() {
		return questOption;
	}

	public void setQuestOption(QuestOption questOption) {
		this.questOption = questOption;
	}

	public UserInfo getUserId() {
		return userId;
	}

	public void setUserId(UserInfo userId) {
		this.userId = userId;
	}

	public TrainCourse getTrainCourse() {
		return trainCourse;
	}

	public void setTrainCourse(TrainCourse trainCourse) {
		this.trainCourse = trainCourse;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Long getObjId() {
		return objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

//	public String getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String userId) {
//		this.userId = userId;
//	}
	
}
