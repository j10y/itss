package com.xpsoft.oa.model.personal;

import com.xpsoft.core.model.BaseModel;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;

import org.apache.commons.lang.builder.HashCodeBuilder;

import org.apache.commons.lang.builder.ToStringBuilder;

public class LeaveLeaderRead extends BaseModel {
	/* 19 */public static Short NOT_CHECK = Short.valueOf((short) 0);
	/* 20 */public static Short IS_PASS = Short.valueOf((short) 1);
	/* 21 */public static Short NOT_PASS = Short.valueOf((short) 2);
	protected Long readId;
	protected String leaderName;
	protected Long userId;
	protected String leaderOpinion;
	protected Date createtime;
	protected Integer depLevel;
	protected String depName;
	protected Short isPass;
	protected String checkName;
	protected ErrandsRegister  errandsRegister;

	public LeaveLeaderRead() {
	}

	public LeaveLeaderRead(Long in_readId) {
		/* 50 */setReadId(in_readId);
	}

	
	
	public ErrandsRegister getErrandsRegister() {
		return errandsRegister;
	}

	public void setErrandsRegister(ErrandsRegister errandsRegister) {
		this.errandsRegister = errandsRegister;
	}


	public String getCheckName() {
		/* 72 */return this.checkName;
	}

	public void setCheckName(String checkName) {
		/* 76 */this.checkName = checkName;
	}

	public Long getReadId() {
		/* 84 */return this.readId;
	}

	public void setReadId(Long aValue) {
		/* 91 */this.readId = aValue;
	}

	public String getLeaderName() {
		/* 99 */return this.leaderName;
	}

	public void setLeaderName(String aValue) {
		/* 107 */this.leaderName = aValue;
	}

	public Long getUserId() {
		/* 115 */return this.userId;
	}

	public void setUserId(Long aValue) {
		/* 123 */this.userId = aValue;
	}

	public String getLeaderOpinion() {
		/* 131 */return this.leaderOpinion;
	}

	public void setLeaderOpinion(String aValue) {
		/* 138 */this.leaderOpinion = aValue;
	}

	public Date getCreatetime() {
		/* 146 */return this.createtime;
	}

	public void setCreatetime(Date aValue) {
		/* 154 */this.createtime = aValue;
	}

	public Long getDateId() {
		/* 161 */return getErrandsRegister() == null ? null : getErrandsRegister()
				.getDateId();
	}

	public void setDateId(Long aValue) {
		/* 168 */if (aValue == null) {
			/* 169 */this.errandsRegister = null;
			/* 170 */} else if (this.errandsRegister == null) {
			/* 171 */this.errandsRegister = new ErrandsRegister(aValue);
			/* 172 */this.errandsRegister.setVersion(new Integer(0));
		} else {
			/* 174 */this.errandsRegister.setDateId(aValue);
		}
	}

	public Integer getDepLevel() {
		/* 204 */return this.depLevel;
	}

	public void setDepLevel(Integer aValue) {
		/* 211 */this.depLevel = aValue;
	}

	public String getDepName() {
		/* 219 */return this.depName;
	}

	public void setDepName(String aValue) {
		/* 226 */this.depName = aValue;
	}

	public Short getIsPass() {
		/* 236 */return this.isPass;
	}

	public void setIsPass(Short aValue) {
		/* 244 */this.isPass = aValue;
	}

	public boolean equals(Object object) {
		/* 251 */if (!(object instanceof LeaveLeaderRead)) {
			/* 252 */return false;
		}
		/* 254 */LeaveLeaderRead rhs = (LeaveLeaderRead) object;
		/* 255 */return new EqualsBuilder()
		/* 256 */.append(this.readId, rhs.readId)
		/* 257 */.append(this.leaderName, rhs.leaderName)
		/* 258 */.append(this.userId, rhs.userId)
		/* 259 */.append(this.leaderOpinion, rhs.leaderOpinion)
		/* 260 */.append(this.createtime, rhs.createtime)
		/* 261 */.append(this.depLevel, rhs.depLevel)
		/* 262 */.append(this.depName, rhs.depName)
		/* 263 */.append(this.isPass, rhs.isPass)
		/* 264 */.append(this.checkName, rhs.checkName)
		/* 265 */.isEquals();
	}

	public int hashCode() {
		/* 272 */return new HashCodeBuilder(-82280557, -700257973)
		/* 273 */.append(this.readId)
		/* 274 */.append(this.leaderName)
		/* 275 */.append(this.userId)
		/* 276 */.append(this.leaderOpinion)
		/* 277 */.append(this.createtime)
		/* 278 */.append(this.depLevel)
		/* 279 */.append(this.depName)
		/* 280 */.append(this.isPass)
		/* 281 */.append(this.checkName)
		/* 282 */.toHashCode();
	}

	public String toString() {
		/* 289 */return new ToStringBuilder(this)
		/* 290 */.append("readId", this.readId)
		/* 291 */.append("leaderName", this.leaderName)
		/* 292 */.append("userId", this.userId)
		/* 293 */.append("leaderOpinion", this.leaderOpinion)
		/* 294 */.append("createtime", this.createtime)
		/* 295 */.append("depLevel", this.depLevel)
		/* 296 */.append("depName", this.depName)
		/* 297 */.append("isPass", this.isPass)
		/* 298 */.append("checkName", this.checkName)
		/* 299 */.toString();
	}

}
