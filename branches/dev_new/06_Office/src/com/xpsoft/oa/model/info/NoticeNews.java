package com.xpsoft.oa.model.info;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;

public class NoticeNews extends BaseModel {
	/* 21 */public static Short ISDESKNEWS = Short.valueOf((short) 1);
	/* 22 */public static Short NOTDESKNEWS = Short.valueOf((short) 0);

	@Expose
	protected Long newsId;

	@Expose
	protected String subjectIcon;

	@Expose
	protected String subject;

	@Expose
	protected String author;

	@Expose
	protected Date createtime;

	@Expose
	protected Integer replyCounts;

	@Expose
	protected Integer viewCounts;

	@Expose
	protected String content;

	@Expose
	protected Date updateTime;

	@Expose
	protected Short status;

	@Expose
	protected NoticeNewsType newsType;

	@Expose
	protected String issuer;

	@Expose
	protected Short isDeskImage;
	
	@Expose
	protected Short isAll;//是否全部可见 0否 1是
	
	
	/**
	 *评论or阅读
	 */
	protected Set<NoticeNewsComment> newsComments = new HashSet();
	
	/**
	 * 附件
	 */
	@Expose
	protected Set<NoticeNewsDoc> noticeNewsDocs = new HashSet();

	public NoticeNews() {
	}

	public NoticeNews(Long in_newsId) {
		/* 63 */setNewsId(in_newsId);
	}

	public Short getIsDeskImage() {
		/* 69 */return this.isDeskImage;
	}

	public void setIsDeskImage(Short isDeskImage) {
		/* 73 */this.isDeskImage = isDeskImage;
	}

	public Set<NoticeNewsComment> getNewsComments() {
		/* 77 */return this.newsComments;
	}

	public void setNewsComments(Set<NoticeNewsComment> newsComments) {
		/* 81 */this.newsComments = newsComments;
	}

	public NoticeNewsType getNewsType() {
		/* 85 */return this.newsType;
	}

	public void setNewsType(NoticeNewsType in_newsType) {
		/* 89 */this.newsType = in_newsType;
	}

	public Long getNewsId() {
		/* 98 */return this.newsId;
	}

	public void setNewsId(Long aValue) {
		/* 105 */this.newsId = aValue;
	}

	public Long getTypeId() {
		/* 112 */return getNewsType() == null ? null : getNewsType()
				.getTypeId();
	}

	public void setTypeId(Long aValue) {
		/* 119 */if (aValue == null) {
			/* 120 */this.newsType = null;
			/* 121 */} else if (this.newsType == null) {
			/* 122 */this.newsType = new NoticeNewsType();
			/* 123 */this.newsType.setTypeId(aValue);
		} else {
			/* 125 */this.newsType.setTypeId(aValue);
		}
	}

	public String getSubjectIcon() {
		/* 134 */return this.subjectIcon;
	}

	public void setSubjectIcon(String aValue) {
		/* 141 */this.subjectIcon = aValue;
	}

	public String getSubject() {
		/* 149 */return this.subject;
	}

	public void setSubject(String aValue) {
		/* 157 */this.subject = aValue;
	}

	public String getAuthor() {
		/* 165 */return this.author;
	}

	public void setAuthor(String aValue) {
		/* 173 */this.author = aValue;
	}

	public Date getCreatetime() {
		/* 181 */return this.createtime;
	}

	public void setCreatetime(Date aValue) {
		/* 189 */this.createtime = aValue;
	}

	public Integer getReplyCounts() {
		/* 197 */return this.replyCounts;
	}

	public void setReplyCounts(Integer aValue) {
		/* 204 */this.replyCounts = aValue;
	}

	public Integer getViewCounts() {
		/* 212 */return this.viewCounts;
	}

	public void setViewCounts(Integer aValue) {
		/* 219 */this.viewCounts = aValue;
	}

	public String getContent() {
		/* 227 */return this.content;
	}

	public void setContent(String aValue) {
		/* 235 */this.content = aValue;
	}

	public Date getUpdateTime() {
		/* 243 */return this.updateTime;
	}

	public void setUpdateTime(Date aValue) {
		/* 251 */this.updateTime = aValue;
	}

	public Short getStatus() {
		/* 259 */return this.status;
	}

	public void setStatus(Short aValue) {
		/* 267 */this.status = aValue;
	}

	public String getIssuer() {
		/* 271 */return this.issuer;
	}

	public void setIssuer(String issuer) {
		/* 275 */this.issuer = issuer;
	}

	public boolean equals(Object object) {
		/* 282 */if (!(object instanceof NoticeNews)) {
			/* 283 */return false;
		}
		/* 285 */NoticeNews rhs = (NoticeNews) object;
		/* 286 */return new EqualsBuilder()
		/* 287 */.append(this.newsId, rhs.newsId)
		/* 288 */.append(this.subjectIcon, rhs.subjectIcon)
		/* 289 */.append(this.subject, rhs.subject)
		/* 290 */.append(this.author, rhs.author)
		/* 291 */.append(this.createtime, rhs.createtime)
		/* 292 */.append(this.replyCounts, rhs.replyCounts)
		/* 293 */.append(this.viewCounts, rhs.viewCounts)
		/* 294 */.append(this.content, rhs.content)
		/* 295 */.append(this.updateTime, rhs.updateTime)
		/* 296 */.append(this.status, rhs.status)
		/* 297 */.append(this.issuer, rhs.issuer)
		/* 298 */.append(this.isDeskImage, rhs.isDeskImage)
		/* 298 */.append(this.isAll, rhs.isAll)
		/* 299 */.isEquals();
	}

	public int hashCode() {
		/* 306 */return new HashCodeBuilder(-82280557, -700257973)
		/* 307 */.append(this.newsId)
		/* 308 */.append(this.subjectIcon)
		/* 309 */.append(this.subject)
		/* 310 */.append(this.author)
		/* 311 */.append(this.createtime)
		/* 312 */.append(this.replyCounts)
		/* 313 */.append(this.viewCounts)
		/* 314 */.append(this.content)
		/* 315 */.append(this.updateTime)
		/* 316 */.append(this.status)
		/* 317 */.append(this.issuer)
		/* 318 */.append(this.isDeskImage)
		/* 318 */.append(this.isAll)
		/* 319 */.toHashCode();
	}

	public String toString() {
		/* 326 */return new ToStringBuilder(this)
		/* 327 */.append("newsId", this.newsId)
		/* 328 */.append("subjectIcon", this.subjectIcon)
		/* 329 */.append("subject", this.subject)
		/* 330 */.append("author", this.author)
		/* 331 */.append("createtime", this.createtime)
		/* 332 */.append("replyCounts", this.replyCounts)
		/* 333 */.append("viewCounts", this.viewCounts)
		/* 334 */.append("content", this.content)
		/* 335 */.append("updateTime", this.updateTime)
		/* 336 */.append("status", this.status)
		/* 337 */.append("issuer", this.issuer)
		/* 338 */.append("isDeskImage", this.isDeskImage)
		/* 338 */.append("isAll", this.isAll)
		/* 339 */.toString();
	}
	
	

	public Short getIsAll() {
		return isAll;
	}

	public void setIsAll(Short isAll) {
		this.isAll = isAll;
	}

	public void setNoticeNewsDocs(Set<NoticeNewsDoc> noticeNewsDocs) {
		this.noticeNewsDocs = noticeNewsDocs;
	}

	public String getFirstKeyColumnName() {
		/* 346 */return "newsId";
	}

	public Long getId() {
		/* 353 */return this.newsId;
	}

	public Set<NoticeNewsDoc> getNoticeNewsDocs() {
		return noticeNewsDocs;
	}
	
}
