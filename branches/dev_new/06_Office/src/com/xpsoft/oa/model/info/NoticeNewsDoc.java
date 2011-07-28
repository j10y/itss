package com.xpsoft.oa.model.info;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.FileAttach;

public class NoticeNewsDoc extends BaseModel {

	@Expose
	protected Long docId;

	@Expose
	protected String creator;

	@Expose
	protected Long creatorId;

	@Expose
	protected String docName;

	@Expose
	protected String docPath;

	@Expose
	protected Date updatetime;

	@Expose
	protected Date createtime;

	@Expose
	protected FileAttach fileAttach;
	protected NoticeNews noticeNews;

	public NoticeNewsDoc() {
	}

	public void initUsers(AppUser curUser) {
		/* 64 */setCreator(curUser.getFullname());
		/* 65 */setCreatorId(curUser.getUserId());

	}

	public NoticeNewsDoc(Long in_docId) {
		/* 76 */setDocId(in_docId);
	}

	public NoticeNews getNoticeNews() {
		return noticeNews;
	}

	public void setNoticeNews(NoticeNews noticeNews) {
		this.noticeNews = noticeNews;
	}

	public FileAttach getFileAttach() {
		/* 89 */return this.fileAttach;
	}

	public void setFileAttach(FileAttach in_fileAttach) {
		/* 93 */this.fileAttach = in_fileAttach;
	}

	public Long getDocId() {
		/* 110 */return this.docId;
	}

	public void setDocId(Long aValue) {
		/* 117 */this.docId = aValue;
	}

	public Long getFileId() {
		/* 124 */return getFileAttach() == null ? null : getFileAttach()
				.getFileId();
	}

	public void setFileId(Long aValue) {
		/* 131 */if (aValue == null) {
			/* 132 */this.fileAttach = null;
			/* 133 */} else if (this.fileAttach == null) {
			/* 134 */this.fileAttach = new FileAttach(aValue);
			/* 135 */this.fileAttach.setVersion(new Integer(0));
		} else {
			/* 137 */this.fileAttach.setFileId(aValue);
		}
	}

	public Long getNewsId() {
		/* 145 */return getNoticeNews() == null ? null : getNoticeNews()
				.getNewsId();
	}

	public void setArchivesId(Long aValue) {
		if (aValue == null) {
			this.noticeNews = null;
		} else if (this.noticeNews == null) {
			this.noticeNews = new NoticeNews(aValue);
		} else {
			this.noticeNews.setNewsId(aValue);
		}
	}

	public String getCreator() {
		/* 167 */return this.creator;
	}

	public void setCreator(String aValue) {
		/* 174 */this.creator = aValue;
	}

	public Long getCreatorId() {
		/* 182 */return this.creatorId;
	}

	public void setCreatorId(Long aValue) {
		/* 189 */this.creatorId = aValue;
	}



	public String getDocName() {
		/* 227 */return this.docName;
	}

	public void setDocName(String aValue) {
		/* 235 */this.docName = aValue;
	}


	public String getDocPath() {
		/* 278 */return this.docPath;
	}

	public void setDocPath(String aValue) {
		/* 286 */this.docPath = aValue;
	}

	public Date getUpdatetime() {
		/* 294 */return this.updatetime;
	}

	public void setUpdatetime(Date aValue) {
		/* 302 */this.updatetime = aValue;
	}

	public Date getCreatetime() {
		/* 310 */return this.createtime;
	}

	public void setCreatetime(Date aValue) {
		/* 318 */this.createtime = aValue;
	}

	public boolean equals(Object object) {
		/* 325 */if (!(object instanceof NoticeNewsDoc)) {
			/* 326 */return false;
		}
		/* 328 */NoticeNewsDoc rhs = (NoticeNewsDoc) object;
		/* 329 */return new EqualsBuilder()
		/* 330 */.append(this.docId, rhs.docId)
		/* 331 */.append(this.creator, rhs.creator)
		/* 332 */.append(this.creatorId, rhs.creatorId)
		/* 335 */.append(this.docName, rhs.docName)
		/* 338 */.append(this.docPath, rhs.docPath)
		/* 339 */.append(this.updatetime, rhs.updatetime)
		/* 340 */.append(this.createtime, rhs.createtime)
		/* 341 */.isEquals();
	}

	public int hashCode() {
		/* 348 */return new HashCodeBuilder(-82280557, -700257973)
		/* 349 */.append(this.docId)
		/* 350 */.append(this.creator)
		/* 351 */.append(this.creatorId)
		/* 354 */.append(this.docName)
		/* 357 */.append(this.docPath)
		/* 358 */.append(this.updatetime)
		/* 359 */.append(this.createtime)
		/* 360 */.toHashCode();
	}

	public String toString() {
		/* 367 */return new ToStringBuilder(this)
		/* 368 */.append("docId", this.docId)
		/* 369 */.append("creator", this.creator)
		/* 370 */.append("creatorId", this.creatorId)
		/* 373 */.append("docName", this.docName)
		/* 376 */.append("docPath", this.docPath)
		/* 377 */.append("updatetime", this.updatetime)
		/* 378 */.append("createtime", this.createtime)
		/* 379 */.toString();
	}
}
