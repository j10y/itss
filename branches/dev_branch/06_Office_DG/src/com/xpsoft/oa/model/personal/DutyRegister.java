package com.xpsoft.oa.model.personal;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;

public class DutyRegister extends BaseModel {
	/* 26 */public static final Short SIGN_IN = Short.valueOf((short) 1);

	/* 30 */public static final Short SIGN_OFF = Short.valueOf((short) 2);

	/* 41 */public static final Short REG_FLAG_NORMAL = Short
			.valueOf((short) 1);

	/* 45 */public static final Short REG_FLAG_LATE = Short.valueOf((short) 2);

	/* 49 */public static final Short REG_FLAG_EARLY_OFF = Short
			.valueOf((short) 3);

	/* 53 */public static final Short REG_FLAG_RELAX = Short.valueOf((short) 4);

	/* 57 */public static final Short REG_FLAG_TRUANCY = Short
			.valueOf((short) 5);

	/* 61 */public static final Short REG_FLAG_HOLIDAY = Short
			.valueOf((short) 6);

	@Expose
	protected Long registerId;

	@Expose
	protected Date registerDate;

	@Expose
	protected Short regFlag;

	@Expose
	protected Integer regMins;

	@Expose
	protected String reason;

	@Expose
	protected Integer dayOfWeek;

	@Expose
	protected Short inOffFlag;

	@Expose
	protected String fullname;
	protected AppUser appUser;
	protected DutySection dutySection;

	public DutyRegister() {
	}

	/* 97 */public DutyRegister(Long in_registerId) {
		setRegisterId(in_registerId);
	}

	public AppUser getAppUser() {
		/* 102 */return this.appUser;
	}

	public void setAppUser(AppUser in_appUser) {
		/* 106 */this.appUser = in_appUser;
	}

	public Long getRegisterId() {
		/* 115 */return this.registerId;
	}

	public void setRegisterId(Long aValue) {
		/* 122 */this.registerId = aValue;
	}

	public Date getRegisterDate() {
		/* 130 */return this.registerDate;
	}

	public void setRegisterDate(Date aValue) {
		/* 138 */this.registerDate = aValue;
	}

	public Long getUserId() {
		/* 145 */return getAppUser() == null ? null : getAppUser().getUserId();
	}

	public void setUserId(Long aValue) {
		/* 152 */if (aValue == null) {
			/* 153 */this.appUser = null;
			/* 154 */} else if (this.appUser == null) {
			/* 155 */this.appUser = new AppUser(aValue);
			/* 156 */this.appUser.setVersion(new Integer(0));
		} else {
			/* 158 */this.appUser.setUserId(aValue);
		}
	}

	public Short getRegFlag() {
		/* 167 */return this.regFlag;
	}

	public void setRegFlag(Short aValue) {
		/* 175 */this.regFlag = aValue;
	}

	public Integer getRegMins() {
		/* 184 */return this.regMins;
	}

	public void setRegMins(Integer aValue) {
		/* 192 */this.regMins = aValue;
	}

	public String getReason() {
		/* 200 */return this.reason;
	}

	public void setReason(String aValue) {
		/* 207 */this.reason = aValue;
	}

	public Integer getDayOfWeek() {
		/* 215 */return this.dayOfWeek;
	}

	public void setDayOfWeek(Integer aValue) {
		/* 223 */this.dayOfWeek = aValue;
	}

	public Short getInOffFlag() {
		/* 233 */return this.inOffFlag;
	}

	public void setInOffFlag(Short aValue) {
		/* 241 */this.inOffFlag = aValue;
	}

	public boolean equals(Object object) {
		/* 248 */if (!(object instanceof DutyRegister)) {
			/* 249 */return false;
		}
		/* 251 */DutyRegister rhs = (DutyRegister) object;
		/* 252 */return new EqualsBuilder()
		/* 253 */.append(this.registerId, rhs.registerId)
		/* 254 */.append(this.registerDate, rhs.registerDate)
		/* 255 */.append(this.regFlag, rhs.regFlag)
		/* 256 */.append(this.regMins, rhs.regMins)
		/* 257 */.append(this.reason, rhs.reason)
		/* 258 */.append(this.dayOfWeek, rhs.dayOfWeek)
		/* 259 */.append(this.inOffFlag, rhs.inOffFlag)
		/* 260 */.isEquals();
	}

	public int hashCode() {
		/* 267 */return new HashCodeBuilder(-82280557, -700257973)
		/* 268 */.append(this.registerId)
		/* 269 */.append(this.registerDate)
		/* 270 */.append(this.regFlag)
		/* 271 */.append(this.regMins)
		/* 272 */.append(this.reason)
		/* 273 */.append(this.dayOfWeek)
		/* 274 */.append(this.inOffFlag)
		/* 275 */.toHashCode();
	}

	public String toString() {
		/* 282 */return new ToStringBuilder(this)
		/* 283 */.append("registerId", this.registerId)
		/* 284 */.append("registerDate", this.registerDate)
		/* 285 */.append("regFlag", this.regFlag)
		/* 286 */.append("regMins", this.regMins)
		/* 287 */.append("reason", this.reason)
		/* 288 */.append("dayOfWeek", this.dayOfWeek)
		/* 289 */.append("inOffFlag", this.inOffFlag)
		/* 290 */.toString();
	}

	public DutySection getDutySection() {
		/* 294 */return this.dutySection;
	}

	public void setDutySection(DutySection dutySection) {
		/* 298 */this.dutySection = dutySection;
	}

	public String getRegisterTime() {
		/* 302 */SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		/* 303 */return sdf.format(this.registerDate);
	}

	public String getFullname() {
		/* 307 */return this.fullname;
	}

	public void setFullname(String fullname) {
		/* 311 */this.fullname = fullname;
	}
}
