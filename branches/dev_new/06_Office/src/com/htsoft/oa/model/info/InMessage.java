package com.htsoft.oa.model.info;

import com.htsoft.core.model.BaseModel;

public class InMessage extends BaseModel {
	/* 11 */public static final Short FLAG_READ = Short.valueOf((short) 1);
	/* 12 */public static final Short FLAG_UNREAD = Short.valueOf((short) 0);
	private Long receiveId;
	private ShortMessage shortMessage;
	private Long userId;
	private String userFullname;
	private Short readFlag;
	private Short delFlag;

	public Long getReceiveId() {
		/* 27 */return this.receiveId;
	}

	public ShortMessage getShortMessage() {
		/* 41 */return this.shortMessage;
	}

	public void setShortMessage(ShortMessage shortMessage) {
		/* 45 */this.shortMessage = shortMessage;
	}

	public void setReceiveId(Long receiveId) {
		/* 49 */this.receiveId = receiveId;
	}

	public Long getUserId() {
		/* 54 */return this.userId;
	}

	public void setUserId(Long userId) {
		/* 58 */this.userId = userId;
	}

	public String getUserFullname() {
		/* 62 */return this.userFullname;
	}

	public void setUserFullname(String userFullname) {
		/* 66 */this.userFullname = userFullname;
	}

	public Short getReadFlag() {
		/* 70 */return this.readFlag;
	}

	public void setReadFlag(Short readFlag) {
		/* 74 */this.readFlag = readFlag;
	}

	public Short getDelFlag() {
		/* 78 */return this.delFlag;
	}

	public void setDelFlag(Short delFlag) {
		/* 82 */this.delFlag = delFlag;
	}
}
