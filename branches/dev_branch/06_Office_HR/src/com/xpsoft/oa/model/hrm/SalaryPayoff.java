package com.xpsoft.oa.model.hrm;

import com.xpsoft.core.model.BaseModel;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SalaryPayoff extends BaseModel {
	/* 19 */public static short CHECK_FLAG_NONE = 0;
	/* 20 */public static short CHECK_FLAG_PASS = 1;
	/* 21 */public static short CHECK_FLAG_NOT_PASS = 2;
	protected Long recordId;
	protected String fullname;
	protected Long userId;
	protected String profileNo;
	protected String idNo;
	protected BigDecimal standAmount;
	protected BigDecimal encourageAmount;
	protected BigDecimal deductAmount;
	protected BigDecimal achieveAmount;
	protected String encourageDesc;
	protected String deductDesc;
	protected String memo;
	protected BigDecimal acutalAmount;
	protected Date regTime;
	protected String register;
	protected String checkName;
	protected Date checkTime;
	protected Short checkStatus;
	protected Date startTime;
	protected Date endTime;
	protected String checkOpinion;
	protected Long standardId;

	protected BigDecimal provident;//公积金
	
	protected BigDecimal insurance;//保险
	
	protected BigDecimal selftax;//个人所得税
	
	
	protected BigDecimal perCoefficient;// 绩效系数
	
	protected BigDecimal issuedAmount;// 应发金额
	
	protected BigDecimal taxableAmount;// 应税金额
	
	
	
	public BigDecimal getPerCoefficient() {
		return perCoefficient;
	}


	public void setPerCoefficient(BigDecimal perCoefficient) {
		this.perCoefficient = perCoefficient;
	}


	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}


	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}


	public BigDecimal getTaxableAmount() {
		return taxableAmount;
	}


	public void setTaxableAmount(BigDecimal taxableAmount) {
		this.taxableAmount = taxableAmount;
	}


	public SalaryPayoff() {
	}

	
	public BigDecimal getProvident() {
		return provident;
	}


	public void setProvident(BigDecimal provident) {
		this.provident = provident;
	}


	public BigDecimal getInsurance() {
		return insurance;
	}


	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}


	public BigDecimal getSelftax() {
		return selftax;
	}


	public void setSelftax(BigDecimal selftax) {
		this.selftax = selftax;
	}


	public SalaryPayoff(Long in_recordId) {
		/* 60 */setRecordId(in_recordId);
	}

	public Long getRecordId() {
		/* 70 */return this.recordId;
	}

	public void setRecordId(Long aValue) {
		/* 77 */this.recordId = aValue;
	}

	public String getFullname() {
		/* 85 */return this.fullname;
	}

	public void setFullname(String aValue) {
		/* 93 */this.fullname = aValue;
	}

	public Long getUserId() {
		/* 101 */return this.userId;
	}

	public void setUserId(Long aValue) {
		/* 109 */this.userId = aValue;
	}

	public String getProfileNo() {
		/* 117 */return this.profileNo;
	}

	public void setProfileNo(String aValue) {
		/* 124 */this.profileNo = aValue;
	}

	public String getIdNo() {
		/* 132 */return this.idNo;
	}

	public void setIdNo(String aValue) {
		/* 139 */this.idNo = aValue;
	}

	public BigDecimal getStandAmount() {
		/* 147 */return this.standAmount;
	}

	public void setStandAmount(BigDecimal aValue) {
		/* 155 */this.standAmount = aValue;
	}

	public BigDecimal getEncourageAmount() {
		/* 163 */return this.encourageAmount;
	}

	public void setEncourageAmount(BigDecimal aValue) {
		/* 171 */this.encourageAmount = aValue;
	}

	public BigDecimal getDeductAmount() {
		/* 179 */return this.deductAmount;
	}

	public void setDeductAmount(BigDecimal aValue) {
		/* 187 */this.deductAmount = aValue;
	}

	public BigDecimal getAchieveAmount() {
		/* 195 */return this.achieveAmount;
	}

	public void setAchieveAmount(BigDecimal aValue) {
		/* 202 */this.achieveAmount = aValue;
	}

	public String getEncourageDesc() {
		/* 210 */return this.encourageDesc;
	}

	public void setEncourageDesc(String aValue) {
		/* 217 */this.encourageDesc = aValue;
	}

	public String getDeductDesc() {
		/* 225 */return this.deductDesc;
	}

	public void setDeductDesc(String aValue) {
		/* 232 */this.deductDesc = aValue;
	}

	public String getMemo() {
		/* 240 */return this.memo;
	}

	public void setMemo(String aValue) {
		/* 247 */this.memo = aValue;
	}

	public BigDecimal getAcutalAmount() {
		/* 255 */return this.acutalAmount;
	}

	public void setAcutalAmount(BigDecimal aValue) {
		/* 262 */this.acutalAmount = aValue;
	}

	public Date getRegTime() {
		/* 270 */return this.regTime;
	}

	public void setRegTime(Date aValue) {
		/* 278 */this.regTime = aValue;
	}

	public String getRegister() {
		/* 286 */return this.register;
	}

	public void setRegister(String aValue) {
		/* 293 */this.register = aValue;
	}

	public String getCheckName() {
		/* 301 */return this.checkName;
	}

	public void setCheckName(String aValue) {
		/* 308 */this.checkName = aValue;
	}

	public Date getCheckTime() {
		/* 316 */return this.checkTime;
	}

	public void setCheckTime(Date aValue) {
		/* 323 */this.checkTime = aValue;
	}

	public Short getCheckStatus() {
		/* 335 */return this.checkStatus;
	}

	public void setCheckStatus(Short aValue) {
		/* 342 */this.checkStatus = aValue;
	}

	public Date getStartTime() {
		/* 350 */return this.startTime;
	}

	public void setStartTime(Date aValue) {
		/* 358 */this.startTime = aValue;
	}

	public Date getEndTime() {
		/* 366 */return this.endTime;
	}

	public void setEndTime(Date aValue) {
		/* 374 */this.endTime = aValue;
	}

	public String getCheckOpinion() {
		/* 378 */return this.checkOpinion;
	}

	public void setCheckOpinion(String checkOpinion) {
		/* 382 */this.checkOpinion = checkOpinion;
	}

	public Long getStandardId() {
		/* 386 */return this.standardId;
	}

	public void setStandardId(Long standardId) {
		/* 390 */this.standardId = standardId;
	}

	public boolean equals(Object object) {
		/* 397 */if (!(object instanceof SalaryPayoff)) {
			/* 398 */return false;
		}
		/* 400 */SalaryPayoff rhs = (SalaryPayoff) object;
		/* 401 */return new EqualsBuilder()
		/* 402 */.append(this.recordId, rhs.recordId)
		/* 403 */.append(this.fullname, rhs.fullname)
		/* 404 */.append(this.userId, rhs.userId)
		/* 405 */.append(this.profileNo, rhs.profileNo)
		/* 406 */.append(this.idNo, rhs.idNo)
		/* 407 */.append(this.standAmount, rhs.standAmount)
		/* 408 */.append(this.encourageAmount, rhs.encourageAmount)
		/* 409 */.append(this.deductAmount, rhs.deductAmount)
		/* 410 */.append(this.achieveAmount, rhs.achieveAmount)
		/* 411 */.append(this.encourageDesc, rhs.encourageDesc)
		/* 412 */.append(this.deductDesc, rhs.deductDesc)
		/* 413 */.append(this.memo, rhs.memo)
		/* 414 */.append(this.acutalAmount, rhs.acutalAmount)
		/* 415 */.append(this.regTime, rhs.regTime)
		/* 416 */.append(this.register, rhs.register)
		/* 417 */.append(this.checkName, rhs.checkName)
		/* 418 */.append(this.checkTime, rhs.checkTime)
		/* 419 */.append(this.checkStatus, rhs.checkStatus)
		/* 420 */.append(this.startTime, rhs.startTime)
		/* 421 */.append(this.endTime, rhs.endTime)
		/* 422 */.append(this.checkOpinion, rhs.checkOpinion)
		/* 423 */.append(this.standardId, rhs.standardId)
		/* 424 */.isEquals();
	}

	public int hashCode() {
		/* 431 */return new HashCodeBuilder(-82280557, -700257973)
		/* 432 */.append(this.recordId)
		/* 433 */.append(this.fullname)
		/* 434 */.append(this.userId)
		/* 435 */.append(this.profileNo)
		/* 436 */.append(this.idNo)
		/* 437 */.append(this.standAmount)
		/* 438 */.append(this.encourageAmount)
		/* 439 */.append(this.deductAmount)
		/* 440 */.append(this.achieveAmount)
		/* 441 */.append(this.encourageDesc)
		/* 442 */.append(this.deductDesc)
		/* 443 */.append(this.memo)
		/* 444 */.append(this.acutalAmount)
		/* 445 */.append(this.regTime)
		/* 446 */.append(this.register)
		/* 447 */.append(this.checkName)
		/* 448 */.append(this.checkTime)
		/* 449 */.append(this.checkStatus)
		/* 450 */.append(this.startTime)
		/* 451 */.append(this.endTime)
		/* 452 */.append(this.checkOpinion)
		/* 453 */.append(this.standardId)
		/* 454 */.toHashCode();
	}

	public String toString() {
		/* 461 */return new ToStringBuilder(this)
		/* 462 */.append("recordId", this.recordId)
		/* 463 */.append("fullname", this.fullname)
		/* 464 */.append("userId", this.userId)
		/* 465 */.append("profileNo", this.profileNo)
		/* 466 */.append("idNo", this.idNo)
		/* 467 */.append("standAmount", this.standAmount)
		/* 468 */.append("encourageAmount", this.encourageAmount)
		/* 469 */.append("deductAmount", this.deductAmount)
		/* 470 */.append("achieveAmount", this.achieveAmount)
		/* 471 */.append("encourageDesc", this.encourageDesc)
		/* 472 */.append("deductDesc", this.deductDesc)
		/* 473 */.append("memo", this.memo)
		/* 474 */.append("acutalAmount", this.acutalAmount)
		/* 475 */.append("regTime", this.regTime)
		/* 476 */.append("register", this.register)
		/* 477 */.append("checkName", this.checkName)
		/* 478 */.append("checkTime", this.checkTime)
		/* 479 */.append("checkStatus", this.checkStatus)
		/* 480 */.append("startTime", this.startTime)
		/* 481 */.append("endTime", this.endTime)
		/* 482 */.append("checkOpinion", this.checkOpinion)
		/* 483 */.append("standardId", this.standardId)
		/* 484 */.toString();
	}
}
