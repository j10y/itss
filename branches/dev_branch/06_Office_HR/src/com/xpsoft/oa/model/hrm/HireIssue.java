package com.xpsoft.oa.model.hrm;

import com.xpsoft.core.model.BaseModel;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class HireIssue extends BaseModel {
	/* 19 */public static Short PASS_CHECK = Short.valueOf((short) 1);
	/* 20 */public static Short NOTPASS_CHECK = Short.valueOf((short) 2);
	/* 21 */public static Short NOTYETPASS_CHECK = Short.valueOf((short) 0);
	protected Long hireId;
	protected String title;
	protected Date startDate;
	protected Date endDate;
	protected Integer hireCount;
	protected String jobName;
	protected String jobCondition;
	protected String regFullname;
	protected Date regDate;
	protected String modifyFullname;
	protected Date modifyDate;
	protected String checkFullname;
	protected String checkOpinion;
	protected Date checkDate;
	protected Short status;
	protected String memo;

	public HireIssue() {
	}

	public HireIssue(Long in_hireId) {
		/* 53 */setHireId(in_hireId);
	}

	public Long getHireId() {
		/* 63 */return this.hireId;
	}

	public void setHireId(Long aValue) {
		/* 70 */this.hireId = aValue;
	}

	public String getTitle() {
		/* 78 */return this.title;
	}

	public void setTitle(String aValue) {
		/* 86 */this.title = aValue;
	}

	public Date getStartDate() {
		/* 94 */return this.startDate;
	}

	public void setStartDate(Date aValue) {
		/* 102 */this.startDate = aValue;
	}

	public Date getEndDate() {
		/* 110 */return this.endDate;
	}

	public void setEndDate(Date aValue) {
		/* 118 */this.endDate = aValue;
	}

	public Integer getHireCount() {
		/* 126 */return this.hireCount;
	}

	public void setHireCount(Integer aValue) {
		/* 134 */this.hireCount = aValue;
	}

	public String getJobName() {
		/* 142 */return this.jobName;
	}

	public void setJobName(String aValue) {
		/* 150 */this.jobName = aValue;
	}

	public String getJobCondition() {
		/* 158 */return this.jobCondition;
	}

	public void setJobCondition(String aValue) {
		/* 165 */this.jobCondition = aValue;
	}

	public String getRegFullname() {
		/* 173 */return this.regFullname;
	}

	public void setRegFullname(String aValue) {
		/* 181 */this.regFullname = aValue;
	}

	public Date getRegDate() {
		/* 189 */return this.regDate;
	}

	public void setRegDate(Date aValue) {
		/* 197 */this.regDate = aValue;
	}

	public String getModifyFullname() {
		/* 205 */return this.modifyFullname;
	}

	public void setModifyFullname(String aValue) {
		/* 212 */this.modifyFullname = aValue;
	}

	public Date getModifyDate() {
		/* 220 */return this.modifyDate;
	}

	public void setModifyDate(Date aValue) {
		/* 227 */this.modifyDate = aValue;
	}

	public String getCheckFullname() {
		/* 235 */return this.checkFullname;
	}

	public void setCheckFullname(String aValue) {
		/* 242 */this.checkFullname = aValue;
	}

	public String getCheckOpinion() {
		/* 250 */return this.checkOpinion;
	}

	public void setCheckOpinion(String aValue) {
		/* 257 */this.checkOpinion = aValue;
	}

	public Date getCheckDate() {
		/* 265 */return this.checkDate;
	}

	public void setCheckDate(Date aValue) {
		/* 272 */this.checkDate = aValue;
	}

	public Short getStatus() {
		/* 282 */return this.status;
	}

	public void setStatus(Short aValue) {
		/* 290 */this.status = aValue;
	}

	public String getMemo() {
		/* 298 */return this.memo;
	}

	public void setMemo(String aValue) {
		/* 305 */this.memo = aValue;
	}

	public boolean equals(Object object) {
		/* 312 */if (!(object instanceof HireIssue)) {
			/* 313 */return false;
		}
		/* 315 */HireIssue rhs = (HireIssue) object;
		/* 316 */return new EqualsBuilder()
		/* 317 */.append(this.hireId, rhs.hireId)
		/* 318 */.append(this.title, rhs.title)
		/* 319 */.append(this.startDate, rhs.startDate)
		/* 320 */.append(this.endDate, rhs.endDate)
		/* 321 */.append(this.hireCount, rhs.hireCount)
		/* 322 */.append(this.jobName, rhs.jobName)
		/* 323 */.append(this.jobCondition, rhs.jobCondition)
		/* 324 */.append(this.regFullname, rhs.regFullname)
		/* 325 */.append(this.regDate, rhs.regDate)
		/* 326 */.append(this.modifyFullname, rhs.modifyFullname)
		/* 327 */.append(this.modifyDate, rhs.modifyDate)
		/* 328 */.append(this.checkFullname, rhs.checkFullname)
		/* 329 */.append(this.checkOpinion, rhs.checkOpinion)
		/* 330 */.append(this.checkDate, rhs.checkDate)
		/* 331 */.append(this.status, rhs.status)
		/* 332 */.append(this.memo, rhs.memo)
		/* 333 */.isEquals();
	}

	public int hashCode() {
		/* 340 */return new HashCodeBuilder(-82280557, -700257973)
		/* 341 */.append(this.hireId)
		/* 342 */.append(this.title)
		/* 343 */.append(this.startDate)
		/* 344 */.append(this.endDate)
		/* 345 */.append(this.hireCount)
		/* 346 */.append(this.jobName)
		/* 347 */.append(this.jobCondition)
		/* 348 */.append(this.regFullname)
		/* 349 */.append(this.regDate)
		/* 350 */.append(this.modifyFullname)
		/* 351 */.append(this.modifyDate)
		/* 352 */.append(this.checkFullname)
		/* 353 */.append(this.checkOpinion)
		/* 354 */.append(this.checkDate)
		/* 355 */.append(this.status)
		/* 356 */.append(this.memo)
		/* 357 */.toHashCode();
	}

	public String toString() {
		/* 364 */return new ToStringBuilder(this)
		/* 365 */.append("hireId", this.hireId)
		/* 366 */.append("title", this.title)
		/* 367 */.append("startDate", this.startDate)
		/* 368 */.append("endDate", this.endDate)
		/* 369 */.append("hireCount", this.hireCount)
		/* 370 */.append("jobName", this.jobName)
		/* 371 */.append("jobCondition", this.jobCondition)
		/* 372 */.append("regFullname", this.regFullname)
		/* 373 */.append("regDate", this.regDate)
		/* 374 */.append("modifyFullname", this.modifyFullname)
		/* 375 */.append("modifyDate", this.modifyDate)
		/* 376 */.append("checkFullname", this.checkFullname)
		/* 377 */.append("checkOpinion", this.checkOpinion)
		/* 378 */.append("checkDate", this.checkDate)
		/* 379 */.append("status", this.status)
		/* 380 */.append("memo", this.memo)
		/* 381 */.toString();
	}
}
