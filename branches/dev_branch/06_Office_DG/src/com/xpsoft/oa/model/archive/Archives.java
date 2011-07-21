package com.xpsoft.oa.model.archive;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.Department;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Archives extends BaseModel {
	/* 25 */public static final Short STATUS_DRAFT = Short.valueOf((short) 0);

	/* 29 */public static final Short STATUS_ISSUE = Short.valueOf((short) 1);

	/* 33 */public static final Short STATUS_ARCHIVE = Short.valueOf((short) 2);

	/* 37 */public static final Short STATUS_HANDLE = Short.valueOf((short) 1);

	/* 41 */public static final Short STATUS_HANDLEING = Short.valueOf((short) 2);

	/* 45 */public static final Short STATUS_LEADERREAD = Short.valueOf((short) 3);

	/* 49 */public static final Short STATUS_DISPATCH = Short.valueOf((short) 4);

	/* 53 */public static final Short STATUS_READ = Short.valueOf((short) 5);

	/* 57 */public static final Short STATUS_READING = Short.valueOf((short) 6);

	/* 61 */public static final Short STATUS_END = Short.valueOf((short) 7);

	/* 65 */public static final Short ARCHIVE_TYPE_DISPATCH = Short.valueOf((short) 0);

	/* 70 */public static final Short ARCHIVE_TYPE_RECEIVE = Short.valueOf((short) 1);

	@Expose
	protected Long archivesId;

	@Expose
	protected String typeName;

	@Expose
	protected String archivesNo;

	@Expose
	protected String issueDep;

	@Expose
	protected String subject;

	@Expose
	protected Date issueDate;

	@Expose
	protected Date createtime;

	@Expose
	protected Short status;

	@Expose
	protected String shortContent;

	@Expose
	protected Integer fileCounts;

	@Expose
	protected String privacyLevel;

	@Expose
	protected String urgentLevel;

	@Expose
	protected String issuer;

	@Expose
	protected Long issuerId;

	@Expose
	protected String keywords;

	@Expose
	protected String sources;

	@Expose
	protected Short archType;

	@Expose
	protected String recDepIds;

	@Expose
	protected String recDepNames;

	@Expose
	protected String handlerUids;

	@Expose
	protected String handlerUnames;

	@Expose
	protected Long orgArchivesId;

	@Expose
	protected String depSignNo;

	@Expose
	protected ArchivesType archivesType;

	@Expose
	protected Department department;

	@Expose
	protected ArchRecType archRecType;

	@Expose
	/* 125 */protected Set archivesHandles = new HashSet();

	@Expose
	/* 127 */protected Set archivesDeps = new HashSet();

	@Expose
	/* 129 */protected Set archivesDocs = new HashSet();
	/* 130 */protected Set leaders = new HashSet();
	/* 131 */protected Set archivesDispatch = new HashSet();
	/* 132 */protected Set archivesAttends = new HashSet();

	public Set getArchivesAttends() {
		/* 136 */return this.archivesAttends;
	}

	public void setArchivesAttends(Set archivesAttends) {
		/* 140 */this.archivesAttends = archivesAttends;
	}

	public Set getArchivesDispatch() {
		/* 144 */return this.archivesDispatch;
	}

	public void setArchivesDispatch(Set archivesDispatch) {
		/* 148 */this.archivesDispatch = archivesDispatch;
	}

	public Set getLeaders() {
		/* 152 */return this.leaders;
	}

	public void setLeaders(Set leaders) {
		/* 156 */this.leaders = leaders;
	}

	public String getHandlerUids() {
		/* 162 */return this.handlerUids;
	}

	public void setHandlerUids(String handlerUids) {
		/* 166 */this.handlerUids = handlerUids;
	}

	public String getHandlerUnames() {
		/* 170 */return this.handlerUnames;
	}

	public void setHandlerUnames(String handlerUnames) {
		/* 174 */this.handlerUnames = handlerUnames;
	}

	public Archives() {
	}

	public Archives(Long in_archivesId) {
		/* 190 */setArchivesId(in_archivesId);
	}

	public Set getArchivesHandles() {
		/* 194 */return this.archivesHandles;
	}

	public void setArchivesHandles(Set archivesHandles) {
		/* 198 */this.archivesHandles = archivesHandles;
	}

	public ArchRecType getArchRecType() {
		/* 202 */return this.archRecType;
	}

	public void setArchRecType(ArchRecType archRecType) {
		/* 206 */this.archRecType = archRecType;
	}

	public ArchivesType getArchivesType() {
		/* 210 */return this.archivesType;
	}

	public void setArchivesType(ArchivesType in_archivesType) {
		/* 214 */this.archivesType = in_archivesType;
	}

	public Department getDepartment() {
		/* 218 */return this.department;
	}

	public void setDepartment(Department in_department) {
		/* 222 */this.department = in_department;
	}

	public Set getArchivesDeps() {
		/* 226 */return this.archivesDeps;
	}

	public void setArchivesDeps(Set in_archivesDeps) {
		/* 230 */this.archivesDeps = in_archivesDeps;
	}

	public Long getArchivesId() {
		/* 238 */return this.archivesId;
	}

	public void setArchivesId(Long aValue) {
		/* 245 */this.archivesId = aValue;
	}

	public Long getTypeId() {
		/* 252 */return getArchivesType() == null ? null : getArchivesType()
				.getTypeId();
	}

	public void setTypeId(Long aValue) {
		/* 259 */if (aValue == null) {
			/* 260 */this.archivesType = null;
			/* 261 */} else if (this.archivesType == null) {
			/* 262 */this.archivesType = new ArchivesType(aValue);
			/* 263 */this.archivesType.setVersion(new Integer(0));
		} else {
			/* 265 */this.archivesType.setTypeId(aValue);
		}
	}

	public Long getRecTypeId() {
		/* 273 */return getArchRecType() == null ? null : getArchRecType()
				.getRecTypeId();
	}

	public void setRecTypeId(Long aValue) {
		/* 280 */if (aValue == null) {
			/* 281 */this.archRecType = null;
			/* 282 */} else if (this.archRecType == null) {
			/* 283 */this.archRecType = new ArchRecType(aValue);
			/* 284 */this.archRecType.setVersion(new Integer(0));
		} else {
			/* 286 */this.archRecType.setRecTypeId(aValue);
		}
	}

	public String getTypeName() {
		/* 295 */return this.typeName;
	}

	public void setTypeName(String aValue) {
		/* 303 */this.typeName = aValue;
	}

	public String getArchivesNo() {
		/* 311 */return this.archivesNo;
	}

	public void setArchivesNo(String aValue) {
		/* 319 */this.archivesNo = aValue;
	}

	public String getIssueDep() {
		/* 327 */return this.issueDep;
	}

	public void setIssueDep(String aValue) {
		/* 335 */this.issueDep = aValue;
	}

	public Long getDepId() {
		/* 343 */return getDepartment() == null ? null : getDepartment()
				.getDepId();
	}

	public void setDepId(Long aValue) {
		/* 350 */if (aValue == null) {
			/* 351 */this.department = null;
			/* 352 */} else if (this.department == null) {
			/* 353 */this.department = new Department(aValue);
			/* 354 */this.department.setVersion(new Integer(0));
		} else {
			/* 356 */this.department.setDepId(aValue);
		}
	}

	public String getSubject() {
		/* 365 */return this.subject;
	}

	public void setSubject(String aValue) {
		/* 373 */this.subject = aValue;
	}

	public Date getIssueDate() {
		/* 381 */return this.issueDate;
	}

	public void setIssueDate(Date aValue) {
		/* 389 */this.issueDate = aValue;
	}

	public Short getStatus() {
		/* 400 */return this.status;
	}

	public void setStatus(Short aValue) {
		/* 408 */this.status = aValue;
	}

	public String getShortContent() {
		/* 416 */return this.shortContent;
	}

	public void setShortContent(String aValue) {
		/* 423 */this.shortContent = aValue;
	}

	public Integer getFileCounts() {
		/* 431 */return this.fileCounts;
	}

	public void setFileCounts(Integer aValue) {
		/* 439 */this.fileCounts = aValue;
	}

	public String getPrivacyLevel() {
		/* 451 */return this.privacyLevel;
	}

	public void setPrivacyLevel(String aValue) {
		/* 458 */this.privacyLevel = aValue;
	}

	public String getUrgentLevel() {
		/* 470 */return this.urgentLevel;
	}

	public void setUrgentLevel(String aValue) {
		/* 477 */this.urgentLevel = aValue;
	}

	public String getIssuer() {
		/* 485 */return this.issuer;
	}

	public void setIssuer(String aValue) {
		/* 492 */this.issuer = aValue;
	}

	public Long getIssuerId() {
		/* 500 */return this.issuerId;
	}

	public void setIssuerId(Long aValue) {
		/* 507 */this.issuerId = aValue;
	}

	public String getKeywords() {
		/* 515 */return this.keywords;
	}

	public void setKeywords(String aValue) {
		/* 522 */this.keywords = aValue;
	}

	public String getSources() {
		/* 533 */return this.sources;
	}

	public void setSources(String aValue) {
		/* 540 */this.sources = aValue;
	}

	public Short getArchType() {
		/* 548 */return this.archType;
	}

	public void setArchType(Short aValue) {
		/* 556 */this.archType = aValue;
	}

	public String getRecDepIds() {
		/* 560 */return this.recDepIds;
	}

	public void setRecDepIds(String recDepIds) {
		/* 564 */this.recDepIds = recDepIds;
	}

	public String getRecDepNames() {
		/* 568 */return this.recDepNames;
	}

	public void setRecDepNames(String recDepNames) {
		/* 572 */this.recDepNames = recDepNames;
	}

	public Long getOrgArchivesId() {
		/* 577 */return this.orgArchivesId;
	}

	public void setOrgArchivesId(Long orgArchivesId) {
		/* 581 */this.orgArchivesId = orgArchivesId;
	}

	public String getDepSignNo() {
		/* 585 */return this.depSignNo;
	}

	public void setDepSignNo(String depSignNo) {
		/* 589 */this.depSignNo = depSignNo;
	}

	public Date getCreatetime() {
		/* 593 */return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		/* 597 */this.createtime = createtime;
	}

	public boolean equals(Object object) {
		/* 604 */if (!(object instanceof Archives)) {
			/* 605 */return false;
		}
		/* 607 */Archives rhs = (Archives) object;
		/* 608 */return new EqualsBuilder()
		/* 609 */.append(this.archivesId, rhs.archivesId)
		/* 610 */.append(this.typeName, rhs.typeName)
		/* 611 */.append(this.archivesNo, rhs.archivesNo)
		/* 612 */.append(this.issueDep, rhs.issueDep)
		/* 613 */.append(this.subject, rhs.subject)
		/* 614 */.append(this.issueDate, rhs.issueDate)
		/* 615 */.append(this.status, rhs.status)
		/* 616 */.append(this.shortContent, rhs.shortContent)
		/* 617 */.append(this.fileCounts, rhs.fileCounts)
		/* 618 */.append(this.privacyLevel, rhs.privacyLevel)
		/* 619 */.append(this.urgentLevel, rhs.urgentLevel)
		/* 620 */.append(this.issuer, rhs.issuer)
		/* 621 */.append(this.issuerId, rhs.issuerId)
		/* 622 */.append(this.keywords, rhs.keywords)
		/* 623 */.append(this.sources, rhs.sources)
		/* 624 */.append(this.archType, rhs.archType)
		/* 625 */.append(this.recDepIds, rhs.recDepIds)
		/* 626 */.append(this.recDepNames, rhs.recDepNames)
		/* 627 */.append(this.orgArchivesId, rhs.orgArchivesId)
		/* 628 */.append(this.depSignNo, rhs.depSignNo)
		/* 629 */.isEquals();
	}

	public int hashCode() {
		/* 636 */return new HashCodeBuilder(-82280557, -700257973)
		/* 637 */.append(this.archivesId)
		/* 638 */.append(this.typeName)
		/* 639 */.append(this.archivesNo)
		/* 640 */.append(this.issueDep)
		/* 641 */.append(this.subject)
		/* 642 */.append(this.issueDate)
		/* 643 */.append(this.status)
		/* 644 */.append(this.shortContent)
		/* 645 */.append(this.fileCounts)
		/* 646 */.append(this.privacyLevel)
		/* 647 */.append(this.urgentLevel)
		/* 648 */.append(this.issuer)
		/* 649 */.append(this.issuerId)
		/* 650 */.append(this.keywords)
		/* 651 */.append(this.sources)
		/* 652 */.append(this.archType)
		/* 653 */.append(this.recDepIds)
		/* 654 */.append(this.recDepNames)
		/* 655 */.append(this.orgArchivesId)
		/* 656 */.append(this.depSignNo)
		/* 657 */.toHashCode();
	}

	public String toString() {
		/* 664 */return new ToStringBuilder(this)
		/* 665 */.append("archivesId", this.archivesId)
		/* 666 */.append("typeName", this.typeName)
		/* 667 */.append("archivesNo", this.archivesNo)
		/* 668 */.append("issueDep", this.issueDep)
		/* 669 */.append("subject", this.subject)
		/* 670 */.append("issueDate", this.issueDate)
		/* 671 */.append("status", this.status)
		/* 672 */.append("shortContent", this.shortContent)
		/* 673 */.append("fileCounts", this.fileCounts)
		/* 674 */.append("privacyLevel", this.privacyLevel)
		/* 675 */.append("urgentLevel", this.urgentLevel)
		/* 676 */.append("issuer", this.issuer)
		/* 677 */.append("issuerId", this.issuerId)
		/* 678 */.append("keywords", this.keywords)
		/* 679 */.append("sources", this.sources)
		/* 680 */.append("archType", this.archType)
		/* 681 */.append("recDepIds", this.recDepIds)
		/* 682 */.append("recDepNames", this.recDepNames)
		/* 683 */.append("orgArchivesId", this.orgArchivesId)
		/* 684 */.append("depSignNo", this.depSignNo)
		/* 685 */.toString();
	}

	public Set getArchivesDocs() {
		/* 689 */return this.archivesDocs;
	}

	public void setArchivesDocs(Set archivesDocs) {
		/* 693 */this.archivesDocs = archivesDocs;
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.oa.model.archive.Archives JD-Core Version: 0.6.0
 */