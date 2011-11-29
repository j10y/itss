package com.xpsoft.oa.model.document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.FileAttach;

public class Document extends BaseModel {
	/* 26 */public static final Short SHARED = Short.valueOf((short) 1);
	/* 27 */public static final Short NOT_SHARED = Short.valueOf((short) 0);

	/* 29 */public static final Short HAVE_ATTACH = Short.valueOf((short) 1);
	/* 30 */public static final Short NOT_HAVE_ATTACH = Short
			.valueOf((short) 0);

	@Expose
	protected Long docId;

	@Expose
	protected String docName;

	@Expose
	protected String content;

	@Expose
	protected Date createtime;

	@Expose
	protected Date updatetime;

	@Expose
	protected Short haveAttach;

	@Expose
	protected String sharedUserIds;

	@Expose
	protected String sharedUserNames;

	@Expose
	protected String sharedDepIds;

	@Expose
	protected String sharedDepNames;

	@Expose
	protected String sharedRoleIds;

	@Expose
	protected String sharedRoleNames;

	@Expose
	protected Short isShared;

	@Expose
	protected String fullname;

	@Expose
	protected DocFolder docFolder;
	protected AppUser appUser;

	@Expose
	/* 63 */protected Set<FileAttach> attachFiles = new HashSet();

	public Document() {
	}

	public Document(Long in_docId) {
		/* 78 */setDocId(in_docId);
	}

	public String getFullname() {
		/* 82 */return this.fullname;
	}

	public void setFullname(String fullname) {
		/* 86 */this.fullname = fullname;
	}

	public DocFolder getDocFolder() {
		/* 90 */return this.docFolder;
	}

	public void setDocFolder(DocFolder in_docFolder) {
		/* 94 */this.docFolder = in_docFolder;
	}

	public AppUser getAppUser() {
		/* 98 */return this.appUser;
	}

	public void setAppUser(AppUser in_appUser) {
		/* 102 */this.appUser = in_appUser;
	}

	public Long getDocId() {
		/* 111 */return this.docId;
	}

	public void setDocId(Long aValue) {
		/* 118 */this.docId = aValue;
	}

	public String getDocName() {
		/* 126 */return this.docName;
	}

	public void setDocName(String aValue) {
		/* 134 */this.docName = aValue;
	}

	public String getContent() {
		/* 142 */return this.content;
	}

	public void setContent(String aValue) {
		/* 149 */this.content = aValue;
	}

	public Date getCreatetime() {
		/* 157 */return this.createtime;
	}

	public void setCreatetime(Date aValue) {
		/* 165 */this.createtime = aValue;
	}

	public Date getUpdatetime() {
		/* 173 */return this.updatetime;
	}

	public void setUpdatetime(Date aValue) {
		/* 180 */this.updatetime = aValue;
	}

	public Long getFolderId() {
		/* 187 */return getDocFolder() == null ? null : getDocFolder()
				.getFolderId();
	}

	public void setFolderId(Long aValue) {
		/* 194 */if (aValue == null) {
			/* 195 */this.docFolder = null;
			/* 196 */} else if (this.docFolder == null) {
			/* 197 */this.docFolder = new DocFolder(aValue);
			/* 198 */this.docFolder.setVersion(new Integer(0));
		} else {
			/* 200 */this.docFolder.setFolderId(aValue);
		}
	}

	public Long getUserId() {
		/* 208 */return getAppUser() == null ? null : getAppUser().getUserId();
	}

	public void setUserId(Long aValue) {
		/* 215 */if (aValue == null) {
			/* 216 */this.appUser = null;
			/* 217 */} else if (this.appUser == null) {
			/* 218 */this.appUser = new AppUser(aValue);
			/* 219 */this.appUser.setVersion(new Integer(0));
		} else {
			/* 221 */this.appUser.setUserId(aValue);
		}
	}

	public Short getHaveAttach() {
		/* 230 */return this.haveAttach;
	}

	public void setHaveAttach(Short aValue) {
		/* 237 */this.haveAttach = aValue;
	}

	public String getSharedUserIds() {
		/* 245 */return this.sharedUserIds;
	}

	public void setSharedUserIds(String aValue) {
		/* 252 */this.sharedUserIds = aValue;
	}

	public String getSharedDepIds() {
		/* 260 */return this.sharedDepIds;
	}

	public void setSharedDepIds(String aValue) {
		/* 267 */this.sharedDepIds = aValue;
	}

	public String getSharedRoleIds() {
		/* 275 */return this.sharedRoleIds;
	}

	public void setSharedRoleIds(String aValue) {
		/* 282 */this.sharedRoleIds = aValue;
	}

	public Short getIsShared() {
		/* 290 */return this.isShared;
	}

	public void setIsShared(Short aValue) {
		/* 298 */this.isShared = aValue;
	}

	public boolean equals(Object object) {
		/* 305 */if (!(object instanceof Document)) {
			/* 306 */return false;
		}
		/* 308 */Document rhs = (Document) object;
		/* 309 */return new EqualsBuilder()
		/* 310 */.append(this.docId, rhs.docId)
		/* 311 */.append(this.docName, rhs.docName)
		/* 312 */.append(this.fullname, rhs.fullname)
		/* 313 */.append(this.content, rhs.content)
		/* 314 */.append(this.createtime, rhs.createtime)
		/* 315 */.append(this.updatetime, rhs.updatetime)
		/* 316 */.append(this.haveAttach, rhs.haveAttach)
		/* 317 */.append(this.sharedUserIds, rhs.sharedUserIds)
		/* 318 */.append(this.sharedDepIds, rhs.sharedDepIds)
		/* 319 */.append(this.sharedRoleIds, rhs.sharedRoleIds)
		/* 320 */.append(this.isShared, rhs.isShared)
		/* 321 */.isEquals();
	}

	public int hashCode() {
		/* 328 */return new HashCodeBuilder(-82280557, -700257973)
		/* 329 */.append(this.docId)
		/* 330 */.append(this.docName)
		/* 331 */.append(this.content)
		/* 332 */.append(this.createtime)
		/* 333 */.append(this.updatetime)
		/* 334 */.append(this.fullname)
		/* 335 */.append(this.haveAttach)
		/* 336 */.append(this.sharedUserIds)
		/* 337 */.append(this.sharedDepIds)
		/* 338 */.append(this.sharedRoleIds)
		/* 339 */.append(this.isShared)
		/* 340 */.toHashCode();
	}

	public String toString() {
		/* 347 */return new ToStringBuilder(this)
		/* 348 */.append("docId", this.docId)
		/* 349 */.append("docName", this.docName)
		/* 350 */.append("content", this.content)
		/* 351 */.append("fullname", this.fullname)
		/* 352 */.append("createtime", this.createtime)
		/* 353 */.append("updatetime", this.updatetime)
		/* 354 */.append("haveAttach", this.haveAttach)
		/* 355 */.append("sharedUserIds", this.sharedUserIds)
		/* 356 */.append("sharedDepIds", this.sharedDepIds)
		/* 357 */.append("sharedRoleIds", this.sharedRoleIds)
		/* 358 */.append("isShared", this.isShared)
		/* 359 */.toString();
	}

	public String getFirstKeyColumnName() {
		/* 366 */return "docId";
	}

	public Long getId() {
		/* 373 */return this.docId;
	}

	public Set<FileAttach> getAttachFiles() {
		/* 377 */return this.attachFiles;
	}

	public void setAttachFiles(Set<FileAttach> attachFiles) {
		/* 381 */this.attachFiles = attachFiles;
	}

	public String getSharedUserNames() {
		/* 385 */return this.sharedUserNames;
	}

	public void setSharedUserNames(String sharedUserNames) {
		/* 389 */this.sharedUserNames = sharedUserNames;
	}

	public String getSharedDepNames() {
		/* 393 */return this.sharedDepNames;
	}

	public void setSharedDepNames(String sharedDepNames) {
		/* 397 */this.sharedDepNames = sharedDepNames;
	}

	public String getSharedRoleNames() {
		/* 401 */return this.sharedRoleNames;
	}

	public void setSharedRoleNames(String sharedRoleNames) {
		/* 405 */this.sharedRoleNames = sharedRoleNames;
	}

}
