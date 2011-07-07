package com.htsoft.oa.model.document;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.htsoft.core.model.BaseModel;
import com.htsoft.oa.model.system.AppUser;

public class DocFolder extends BaseModel {
	/* 22 */public static final Short IS_SHARED = Short.valueOf((short) 1);
	/* 23 */public static final Short IS_NOT_SHARED = Short.valueOf((short) 0);

	@Expose
	protected Long folderId;

	@Expose
	protected String folderName;

	@Expose
	protected Long parentId;

	@Expose
	protected String path;

	@Expose
	protected Short isShared;

	@Expose
	protected AppUser appUser;

	public DocFolder() {
	}

	/* 50 */public DocFolder(Long in_folderId) {
		setFolderId(in_folderId);
	}

	public AppUser getAppUser() {
		/* 55 */return this.appUser;
	}

	public void setAppUser(AppUser in_appUser) {
		/* 59 */this.appUser = in_appUser;
	}

	public Long getFolderId() {
		/* 68 */return this.folderId;
	}

	public void setFolderId(Long aValue) {
		/* 75 */this.folderId = aValue;
	}

	public Long getUserId() {
		/* 82 */return getAppUser() == null ? null : getAppUser().getUserId();
	}

	public void setUserId(Long aValue) {
		/* 89 */if (aValue == null) {
			/* 90 */this.appUser = null;
			/* 91 */} else if (this.appUser == null) {
			/* 92 */this.appUser = new AppUser(aValue);
			/* 93 */this.appUser.setVersion(new Integer(0));
		} else {
			/* 95 */this.appUser.setUserId(aValue);
		}
	}

	public String getFolderName() {
		/* 115 */return this.folderName;
	}

	public void setFolderName(String aValue) {
		/* 123 */this.folderName = aValue;
	}

	public Long getParentId() {
		/* 131 */return this.parentId;
	}

	public void setParentId(Long aValue) {
		/* 138 */this.parentId = aValue;
	}

	public String getPath() {
		/* 146 */return this.path;
	}

	public void setPath(String aValue) {
		/* 153 */this.path = aValue;
	}

	public Short getIsShared() {
		/* 161 */return this.isShared;
	}

	public void setIsShared(Short aValue) {
		/* 169 */this.isShared = aValue;
	}

	public boolean equals(Object object) {
		/* 176 */if (!(object instanceof DocFolder)) {
			/* 177 */return false;
		}
		/* 179 */DocFolder rhs = (DocFolder) object;
		/* 180 */return new EqualsBuilder()
		/* 181 */.append(this.folderId, rhs.folderId)
		/* 182 */.append(this.folderName, rhs.folderName)
		/* 183 */.append(this.parentId, rhs.parentId)
		/* 184 */.append(this.path, rhs.path)
		/* 185 */.append(this.isShared, rhs.isShared)
		/* 186 */.isEquals();
	}

	public int hashCode() {
		/* 193 */return new HashCodeBuilder(-82280557, -700257973)
		/* 194 */.append(this.folderId)
		/* 195 */.append(this.folderName)
		/* 196 */.append(this.parentId)
		/* 197 */.append(this.path)
		/* 198 */.append(this.isShared)
		/* 199 */.toHashCode();
	}

	public String toString() {
		/* 206 */return new ToStringBuilder(this)
		/* 207 */.append("folderId", this.folderId)
		/* 208 */.append("folderName", this.folderName)
		/* 209 */.append("parentId", this.parentId)
		/* 210 */.append("path", this.path)
		/* 211 */.append("isShared", this.isShared)
		/* 212 */.toString();
	}

	public String getFirstKeyColumnName() {
		/* 219 */return "folderId";
	}

	public Long getId() {
		/* 226 */return this.folderId;
	}
}
