package com.xpsoft.oa.model.archive;

import com.xpsoft.core.model.BaseModel;

public class ArchUnderTakes extends BaseModel{
	private Long id;
	private Long archivesId;
	private String userIds;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArchivesId() {
		return archivesId;
	}
	public void setArchivesId(Long archivesId) {
		this.archivesId = archivesId;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	
}
