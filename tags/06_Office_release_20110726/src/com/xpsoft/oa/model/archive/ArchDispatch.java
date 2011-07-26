package com.xpsoft.oa.model.archive;

import com.xpsoft.core.model.BaseModel;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;

import org.apache.commons.lang.builder.HashCodeBuilder;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchDispatch extends BaseModel {
	/* 19 */public static Short HAVE_READ = Short.valueOf((short) 1);
	/* 20 */public static Short NOT_READ = Short.valueOf((short) 0);
	/* 21 */public static Short IS_UNDERTAKE = Short.valueOf((short) 1);
	/* 22 */public static Short IS_READER = Short.valueOf((short) 0);
	/* 23 */public static Short IS_DISPATCH = Short.valueOf((short) 2);
	protected Long dispatchId;
	protected Date dispatchTime;
	protected Long userId;
	protected String fullname;
	protected Short isRead;
	protected String subject;
	protected String readFeedback;
	protected Short archUserType;
	protected Long disRoleId;
	protected String disRoleName;
	protected Archives archives;

	public ArchDispatch() {
	}

	public Long getDisRoleId() {
		/* 48 */return this.disRoleId;
	}

	public void setDisRoleId(Long disRoleId) {
		/* 55 */this.disRoleId = disRoleId;
	}

	public String getDisRoleName() {
		/* 62 */return this.disRoleName;
	}

	public void setDisRoleName(String disRoleName) {
		/* 69 */this.disRoleName = disRoleName;
	}

	public ArchDispatch(Long in_dispatchId) {
		/* 81 */setDispatchId(in_dispatchId);
	}

	public Archives getArchives() {
		/* 86 */return this.archives;
	}

	public void setArchives(Archives in_archives) {
		/* 90 */this.archives = in_archives;
	}

	public Long getDispatchId() {
		/* 99 */return this.dispatchId;
	}

	public void setDispatchId(Long aValue) {
		/* 106 */this.dispatchId = aValue;
	}

	public Long getArchivesId() {
		/* 113 */return getArchives() == null ? null : getArchives()
				.getArchivesId();
	}

	public void setArchivesId(Long aValue) {
		/* 120 */if (aValue == null) {
			/* 121 */this.archives = null;
			/* 122 */} else if (this.archives == null) {
			/* 123 */this.archives = new Archives(aValue);
			/* 124 */this.archives.setVersion(new Integer(0));
		} else {
			/* 126 */this.archives.setArchivesId(aValue);
		}
	}

	public Date getDispatchTime() {
		/* 135 */return this.dispatchTime;
	}

	public void setDispatchTime(Date aValue) {
		/* 143 */this.dispatchTime = aValue;
	}

	public Long getUserId() {
		/* 151 */return this.userId;
	}

	public void setUserId(Long aValue) {
		/* 159 */this.userId = aValue;
	}

	public String getFullname() {
		/* 167 */return this.fullname;
	}

	public void setFullname(String aValue) {
		/* 174 */this.fullname = aValue;
	}

	public Short getIsRead() {
		/* 182 */return this.isRead;
	}

	public void setIsRead(Short aValue) {
		/* 189 */this.isRead = aValue;
	}

	public String getSubject() {
		/* 197 */return this.subject;
	}

	public void setSubject(String aValue) {
		/* 204 */this.subject = aValue;
	}

	public String getReadFeedback() {
		/* 212 */return this.readFeedback;
	}

	public void setReadFeedback(String aValue) {
		/* 219 */this.readFeedback = aValue;
	}

	public Short getArchUserType() {
		/* 227 */return this.archUserType;
	}

	public void setArchUserType(Short aValue) {
		/* 234 */this.archUserType = aValue;
	}

	public boolean equals(Object object) {
		/* 241 */if (!(object instanceof ArchDispatch)) {
			/* 242 */return false;
		}
		/* 244 */ArchDispatch rhs = (ArchDispatch) object;
		/* 245 */return new EqualsBuilder()
		/* 246 */.append(this.dispatchId, rhs.dispatchId)
		/* 247 */.append(this.dispatchTime, rhs.dispatchTime)
		/* 248 */.append(this.userId, rhs.userId)
		/* 249 */.append(this.fullname, rhs.fullname)
		/* 250 */.append(this.isRead, rhs.isRead)
		/* 251 */.append(this.subject, rhs.subject)
		/* 252 */.append(this.readFeedback, rhs.readFeedback)
		/* 253 */.append(this.archUserType, rhs.archUserType)
		/* 254 */.append(this.disRoleId, rhs.disRoleId)
		/* 255 */.append(this.disRoleName, rhs.disRoleName)
		/* 256 */.isEquals();
	}

	public int hashCode() {
		/* 263 */return new HashCodeBuilder(-82280557, -700257973)
		/* 264 */.append(this.dispatchId)
		/* 265 */.append(this.dispatchTime)
		/* 266 */.append(this.userId)
		/* 267 */.append(this.fullname)
		/* 268 */.append(this.isRead)
		/* 269 */.append(this.subject)
		/* 270 */.append(this.readFeedback)
		/* 271 */.append(this.archUserType)
		/* 272 */.append(this.disRoleId)
		/* 273 */.append(this.disRoleName)
		/* 274 */.toHashCode();
	}

	public String toString() {
		/* 281 */return new ToStringBuilder(this)
		/* 282 */.append("dispatchId", this.dispatchId)
		/* 283 */.append("dispatchTime", this.dispatchTime)
		/* 284 */.append("userId", this.userId)
		/* 285 */.append("fullname", this.fullname)
		/* 286 */.append("isRead", this.isRead)
		/* 287 */.append("subject", this.subject)
		/* 288 */.append("readFeedback", this.readFeedback)
		/* 289 */.append("isUndertake", this.archUserType)
		/* 290 */.append("disRoleId", this.disRoleId)
		/* 291 */.append("disRoleName", this.disRoleName)
		/* 292 */.toString();
	}

}