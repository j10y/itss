/*     */package com.htsoft.oa.model.personal;

/*     */
/*     */import com.htsoft.core.model.BaseModel;
/*     */
import com.htsoft.oa.model.system.AppUser;
/*     */
import java.util.Date;
/*     */
import org.apache.commons.lang.builder.EqualsBuilder;
/*     */
import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */
import org.apache.commons.lang.builder.ToStringBuilder;

/*     */
/*     */public class ErrandsRegister extends BaseModel
/*     */{
	/*     */protected Long dateId;
	/*     */protected Long approvalId;
	/*     */protected String descp;
	/*     */protected Date startTime;
	/*     */protected Date endTime;
	/*     */protected Short status;
	/*     */protected String approvalOption;
	/*     */protected String approvalName;
	/*     */protected Short flag;
	/*     */protected AppUser appUser;
	/* 34 */public static final Short STATUS_UNCHECKED = Short
			.valueOf((short) 0);
	/*     */
	/* 38 */public static final Short STATUS_APPROVAL = Short
			.valueOf((short) 1);
	/*     */
	/* 42 */public static final Short STATUS_UNAPPROVAL = Short
			.valueOf((short) 2);
	/*     */
	/* 45 */public static final Short FLAG_OVERTIME = Short.valueOf((short) 0);
	/*     */
	/* 47 */public static final Short FLAG_LEAVE = Short.valueOf((short) 1);
	/*     */
	/* 49 */public static final Short FLAG_OUT = Short.valueOf((short) 2);

	/*     */
	/*     */public ErrandsRegister()
	/*     */{
		/*     */}

	/*     */
	/*     */public ErrandsRegister(Long in_dateId)
	/*     */{
		/* 64 */setDateId(in_dateId);
		/*     */}

	/*     */
	/*     */public AppUser getAppUser()
	/*     */{
		/* 69 */return this.appUser;
		/*     */}

	/*     */
	/*     */public void setAppUser(AppUser in_appUser) {
		/* 73 */this.appUser = in_appUser;
		/*     */}

	/*     */
	/*     */public Long getDateId()
	/*     */{
		/* 82 */return this.dateId;
		/*     */}

	/*     */
	/*     */public void setDateId(Long aValue)
	/*     */{
		/* 89 */this.dateId = aValue;
		/*     */}

	/*     */
	/*     */public Long getUserId()
	/*     */{
		/* 97 */return getAppUser() == null ? null : getAppUser().getUserId();
		/*     */}

	/*     */
	/*     */public void setUserId(Long aValue)
	/*     */{
		/* 105 */if (aValue == null) {
			/* 106 */this.appUser = null;
			/* 107 */} else if (this.appUser == null) {
			/* 108 */this.appUser = new AppUser(aValue);
			/* 109 */this.appUser.setVersion(new Integer(0));
			/*     */} else {
			/* 111 */this.appUser.setUserId(aValue);
			/*     */}
		/*     */}

	/*     */
	/*     */public String getDescp()
	/*     */{
		/* 120 */return this.descp;
		/*     */}

	/*     */
	/*     */public void setDescp(String aValue)
	/*     */{
		/* 128 */this.descp = aValue;
		/*     */}

	/*     */
	/*     */public Date getStartTime()
	/*     */{
		/* 136 */return this.startTime;
		/*     */}

	/*     */
	/*     */public void setStartTime(Date aValue)
	/*     */{
		/* 144 */this.startTime = aValue;
		/*     */}

	/*     */
	/*     */public Date getEndTime()
	/*     */{
		/* 152 */return this.endTime;
		/*     */}

	/*     */
	/*     */public void setEndTime(Date aValue)
	/*     */{
		/* 160 */this.endTime = aValue;
		/*     */}

	/*     */
	/*     */public Long getApprovalId()
	/*     */{
		/* 167 */return this.approvalId;
		/*     */}

	/*     */
	/*     */public void setApprovalId(Long aValue)
	/*     */{
		/* 174 */this.approvalId = aValue;
		/*     */}

	/*     */
	/*     */public Short getStatus()
	/*     */{
		/* 182 */return this.status;
		/*     */}

	/*     */
	/*     */public void setStatus(Short aValue)
	/*     */{
		/* 190 */this.status = aValue;
		/*     */}

	/*     */
	/*     */public String getApprovalOption()
	/*     */{
		/* 198 */return this.approvalOption;
		/*     */}

	/*     */
	/*     */public void setApprovalOption(String aValue)
	/*     */{
		/* 205 */this.approvalOption = aValue;
		/*     */}

	/*     */
	/*     */public String getApprovalName()
	/*     */{
		/* 213 */return this.approvalName;
		/*     */}

	/*     */
	/*     */public void setApprovalName(String aValue)
	/*     */{
		/* 221 */this.approvalName = aValue;
		/*     */}

	/*     */
	/*     */public Short getFlag()
	/*     */{
		/* 231 */return this.flag;
		/*     */}

	/*     */
	/*     */public void setFlag(Short aValue)
	/*     */{
		/* 238 */this.flag = aValue;
		/*     */}

	/*     */
	/*     */public boolean equals(Object object)
	/*     */{
		/* 245 */if (!(object instanceof ErrandsRegister)) {
			/* 246 */return false;
			/*     */}
		/* 248 */ErrandsRegister rhs = (ErrandsRegister) object;
		/* 249 */return new EqualsBuilder()
		/* 250 */.append(this.dateId, rhs.dateId)
		/* 251 */.append(this.approvalId, rhs.approvalId)
		/* 252 */.append(this.descp, rhs.descp)
		/* 253 */.append(this.startTime, rhs.startTime)
		/* 254 */.append(this.endTime, rhs.endTime)
		/* 255 */.append(this.status, rhs.status)
		/* 256 */.append(this.approvalOption, rhs.approvalOption)
		/* 257 */.append(this.approvalName, rhs.approvalName)
		/* 258 */.append(this.flag, rhs.flag)
		/* 259 */.isEquals();
		/*     */}

	/*     */
	/*     */public int hashCode()
	/*     */{
		/* 266 */return new HashCodeBuilder(-82280557, -700257973)
		/* 267 */.append(this.dateId)
		/* 268 */.append(this.approvalId)
		/* 269 */.append(this.descp)
		/* 270 */.append(this.startTime)
		/* 271 */.append(this.endTime)
		/* 272 */.append(this.status)
		/* 273 */.append(this.approvalOption)
		/* 274 */.append(this.approvalName)
		/* 275 */.append(this.flag)
		/* 276 */.toHashCode();
		/*     */}

	/*     */
	/*     */public String toString()
	/*     */{
		/* 283 */return new ToStringBuilder(this)
		/* 284 */.append("dateId", this.dateId)
		/* 285 */.append("userId", this.approvalId)
		/* 286 */.append("descp", this.descp)
		/* 287 */.append("startTime", this.startTime)
		/* 288 */.append("endTime", this.endTime)
		/* 289 */.append("status", this.status)
		/* 290 */.append("approvalOption", this.approvalOption)
		/* 291 */.append("approvalName", this.approvalName)
		/* 292 */.append("flag", this.flag)
		/* 293 */.toString();
		/*     */}
	/*     */
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.htsoft.oa.model.personal.ErrandsRegister JD-Core Version: 0.6.0
 */