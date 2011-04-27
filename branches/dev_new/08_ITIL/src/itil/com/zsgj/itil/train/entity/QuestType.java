package com.zsgj.itil.train.entity;

import com.zsgj.info.framework.dao.BaseObject;



/**
 * 调查试题类型<br>
 * 单选，复选，填空题，问答题
 * @Class Name QuestType
 * @Author Administrator
 * @Create In 2008-7-12
 */
public class QuestType extends BaseObject {
	private Long id;
	private String questTypeName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestTypeName() {
		return questTypeName;
	}
	public void setQuestTypeName(String questTypeName) {
		this.questTypeName = questTypeName;
	}
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((questTypeName == null) ? 0 : questTypeName.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final QuestType other = (QuestType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (questTypeName == null) {
			if (other.questTypeName != null)
				return false;
		} else if (!questTypeName.equals(other.questTypeName))
			return false;
		return true;
	}
}
