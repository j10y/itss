package com.xpsoft.oa.model.archive;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.Department;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ArchivesDep extends BaseModel {
	/* 21 */public static final Short RECEIVE_MAIN = Short.valueOf((short) 1);

	/* 25 */public static final Short RECEIVE_COPY = Short.valueOf((short) 0);

	/* 30 */public static final Short STATUS_SIGNED = Short.valueOf((short) 1);

	/* 34 */public static final Short STATUS_UNSIGNED = Short
			.valueOf((short) 0);
	protected Long archDepId;
	protected String signNo;
	protected String subject;
	protected Short status;
	protected Date signTime;
	protected String signFullname;
	protected Long signUserID;
	protected String handleFeedback;
	protected Short isMain;
	protected Archives archives;
	protected Department department;

	public ArchivesDep() {
	}

	public ArchivesDep(Long in_archDepId) {
		/* 63 */setArchDepId(in_archDepId);
	}

	public Archives getArchives() {
		/* 76 */return this.archives;
	}

	public void setArchives(Archives in_archives) {
		/* 80 */this.archives = in_archives;
	}

	public Department getDepartment() {
		/* 84 */return this.department;
	}

	public void setDepartment(Department in_department) {
		/* 88 */this.department = in_department;
	}

	public Long getArchDepId() {
		/* 97 */return this.archDepId;
	}

	public void setArchDepId(Long aValue) {
		/* 104 */this.archDepId = aValue;
	}

	public String getSignNo() {
		/* 112 */return this.signNo;
	}

	public void setSignNo(String aValue) {
		/* 119 */this.signNo = aValue;
	}

	public Long getDepId() {
		/* 126 */return getDepartment() == null ? null : getDepartment()
				.getDepId();
	}

	public void setDepId(Long aValue) {
		/* 133 */if (aValue == null) {
			/* 134 */this.department = null;
			/* 135 */} else if (this.department == null) {
			/* 136 */this.department = new Department(aValue);
			/* 137 */this.department.setVersion(new Integer(0));
		} else {
			/* 139 */this.department.setDepId(aValue);
		}
	}

	public Long getArchivesId() {
		/* 147 */return getArchives() == null ? null : getArchives()
				.getArchivesId();
	}

	public void setArchivesId(Long aValue) {
		/* 154 */if (aValue == null) {
			/* 155 */this.archives = null;
			/* 156 */} else if (this.archives == null) {
			/* 157 */this.archives = new Archives(aValue);
			/* 158 */this.archives.setVersion(new Integer(0));
		} else {
			/* 160 */this.archives.setArchivesId(aValue);
		}
	}

	public String getSubject() {
		/* 169 */return this.subject;
	}

	public void setSubject(String aValue) {
		/* 177 */this.subject = aValue;
	}

	public Short getStatus() {
		/* 208 */return this.status;
	}

	public void setStatus(Short aValue) {
		/* 216 */this.status = aValue;
	}

	public Date getSignTime() {
		/* 224 */return this.signTime;
	}

	public void setSignTime(Date aValue) {
		/* 231 */this.signTime = aValue;
	}

	public String getSignFullname() {
		/* 239 */return this.signFullname;
	}

	public void setSignFullname(String aValue) {
		/* 246 */this.signFullname = aValue;
	}

	public Long getSignUserID() {
		/* 250 */return this.signUserID;
	}

	public void setSignUserID(Long signUserID) {
		/* 254 */this.signUserID = signUserID;
	}

	public String getHandleFeedback() {
		/* 262 */return this.handleFeedback;
	}

	public void setHandleFeedback(String aValue) {
		/* 269 */this.handleFeedback = aValue;
	}

	public Short getIsMain() {
		/* 279 */return this.isMain;
	}

	public void setIsMain(Short aValue) {
		/* 287 */this.isMain = aValue;
	}

	public boolean equals(Object object) {
		/* 294 */if (!(object instanceof ArchivesDep)) {
			/* 295 */return false;
		}
		/* 297 */ArchivesDep rhs = (ArchivesDep) object;
		/* 298 */return new EqualsBuilder()
		/* 299 */.append(this.archDepId, rhs.archDepId)
		/* 300 */.append(this.signNo, rhs.signNo)
		/* 301 */.append(this.subject, rhs.subject)
		/* 302 */.append(this.status, rhs.status)
		/* 303 */.append(this.signTime, rhs.signTime)
		/* 304 */.append(this.signFullname, rhs.signFullname)
		/* 305 */.append(this.handleFeedback, rhs.handleFeedback)
		/* 306 */.append(this.isMain, rhs.isMain)
		/* 307 */.isEquals();
	}

	public int hashCode() {
		/* 314 */return new HashCodeBuilder(-82280557, -700257973)
		/* 315 */.append(this.archDepId)
		/* 316 */.append(this.signNo)
		/* 317 */.append(this.subject)
		/* 318 */.append(this.status)
		/* 319 */.append(this.signTime)
		/* 320 */.append(this.signFullname)
		/* 321 */.append(this.handleFeedback)
		/* 322 */.append(this.isMain)
		/* 323 */.toHashCode();
	}

	public String toString() {
		/* 330 */return new ToStringBuilder(this)
		/* 331 */.append("archDepId", this.archDepId)
		/* 332 */.append("signNo", this.signNo)
		/* 333 */.append("subject", this.subject)
		/* 334 */.append("status", this.status)
		/* 335 */.append("signTime", this.signTime)
		/* 336 */.append("signFullname", this.signFullname)
		/* 337 */.append("handleFeedback", this.handleFeedback)
		/* 338 */.append("isMain", this.isMain)
		/* 339 */.toString();
	}
}
