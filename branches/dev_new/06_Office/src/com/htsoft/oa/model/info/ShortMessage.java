package com.htsoft.oa.model.info;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.htsoft.core.model.BaseModel;

public class ShortMessage extends BaseModel {
	private Long messageId;
	private String content;
	private Long senderId;
	private String sender;
	private Short msgType;
	private Date sendTime;
	/* 28 */public static final Short MSG_TYPE_PERSONAL = Short
			.valueOf((short) 1);
	/* 29 */public static final Short MSG_TYPE_CALENDAR = Short
			.valueOf((short) 2);
	/* 30 */public static final Short MSG_TYPE_PLAN = Short.valueOf((short) 3);
	/* 31 */public static final Short MSG_TYPE_TASK = Short.valueOf((short) 4);
	/* 32 */public static final Short MSG_TYPE_SYS = Short.valueOf((short) 5);

	/* 35 */private Set<InMessage> messages = new HashSet();

	public Set<InMessage> getMessages() {
		/* 43 */return this.messages;
	}

	public void setMessages(Set<InMessage> messages) {
		/* 48 */this.messages = messages;
	}

	public Long getMessageId() {
		/* 52 */return this.messageId;
	}

	public void setMessageId(Long messageId) {
		/* 56 */this.messageId = messageId;
	}

	public String getContent() {
		/* 60 */return this.content;
	}

	public void setContent(String content) {
		/* 64 */this.content = content;
	}

	public Long getSenderId() {
		/* 68 */return this.senderId;
	}

	public void setSenderId(Long senderId) {
		/* 72 */this.senderId = senderId;
	}

	public String getSender() {
		/* 76 */return this.sender;
	}

	public void setSender(String sender) {
		/* 80 */this.sender = sender;
	}

	public Short getMsgType() {
		/* 84 */return this.msgType;
	}

	public void setMsgType(Short msgType) {
		/* 88 */this.msgType = msgType;
	}

	public Date getSendTime() {
		/* 92 */return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		/* 96 */this.sendTime = sendTime;
	}
}
