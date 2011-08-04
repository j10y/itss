/*     */package com.xpsoft.oa.model.archive;

/*     */
/*     */import com.google.gson.annotations.Expose; /*     */
import com.xpsoft.core.model.BaseModel; /*     */
import java.util.Date; /*     */
import org.apache.commons.lang.builder.EqualsBuilder; /*     */
import org.apache.commons.lang.builder.HashCodeBuilder; /*     */
import org.apache.commons.lang.builder.ToStringBuilder;

/*     */
/*     */public class ArchivesHandle extends BaseModel
/*     */{

	@Expose
	protected Long handleId;

	@Expose
	protected String handleOpinion;

	@Expose
	protected Long userId;

	@Expose
	protected String userFullname;

	@Expose
	protected Date createtime;

	@Expose
	protected Date fillTime;

	@Expose
	protected Short isPass;
	protected Archives archives;
	
	//归档部门
	@Expose
	protected Long filedDeptId;

	@Expose
	protected String filedDeptName;
	
	//归档类型
	@Expose
	protected Long recFiledTypeId;

	@Expose
	protected String recFiledTypeName;

	public ArchivesHandle() {
	}

	public ArchivesHandle(Long in_handleId) {
		/*  51 */setHandleId(in_handleId);
	}

	public Archives getArchives() {
		/*  56 */return this.archives;
	}

	public void setArchives(Archives in_archives) {
		/*  60 */this.archives = in_archives;
	}

	public Long getHandleId() {
		/*  69 */return this.handleId;
	}

	public void setHandleId(Long aValue) {
		/*  76 */this.handleId = aValue;
	}

	public Long getArchivesId() {
		/*  83 */return getArchives() == null ? null : getArchives()
				.getArchivesId();
	}

	public void setArchivesId(Long aValue) {
		/*  90 */if (aValue == null) {
			/*  91 */this.archives = null;
			/*  92 */} else if (this.archives == null) {
			/*  93 */this.archives = new Archives(aValue);
			/*  94 */this.archives.setVersion(new Integer(0));
		} else {
			/*  96 */this.archives.setArchivesId(aValue);
		}
	}

	public String getHandleOpinion() {
		/* 105 */return this.handleOpinion;
	}

	public void setHandleOpinion(String aValue) {
		/* 112 */this.handleOpinion = aValue;
	}

	public Long getUserId() {
		/* 120 */return this.userId;
	}

	public void setUserId(Long aValue) {
		/* 128 */this.userId = aValue;
	}

	public String getUserFullname() {
		/* 136 */return this.userFullname;
	}

	public void setUserFullname(String aValue) {
		/* 144 */this.userFullname = aValue;
	}

	public Date getCreatetime() {
		/* 152 */return this.createtime;
	}

	public void setCreatetime(Date aValue) {
		/* 160 */this.createtime = aValue;
	}

	public Date getFillTime() {
		/* 168 */return this.fillTime;
	}

	public void setFillTime(Date aValue) {
		/* 175 */this.fillTime = aValue;
	}

	public Short getIsPass() {
		/* 185 */return this.isPass;
	}

	public void setIsPass(Short aValue) {
		/* 193 */this.isPass = aValue;
	}

	public boolean equals(Object object) {
		/* 200 */if (!(object instanceof ArchivesHandle)) {
			/* 201 */return false;
		}
		/* 203 */ArchivesHandle rhs = (ArchivesHandle) object;
		/* 204 */return new EqualsBuilder()
		/* 205 */.append(this.handleId, rhs.handleId)
		/* 206 */.append(this.handleOpinion, rhs.handleOpinion)
		/* 207 */.append(this.userId, rhs.userId)
		/* 208 */.append(this.userFullname, rhs.userFullname)
		/* 209 */.append(this.createtime, rhs.createtime)
		/* 210 */.append(this.fillTime, rhs.fillTime)
		/* 211 */.append(this.isPass, rhs.isPass)
		/* 212 */.isEquals();
	}

	public int hashCode() {
		/* 219 */return new HashCodeBuilder(-82280557, -700257973)
		/* 220 */.append(this.handleId)
		/* 221 */.append(this.handleOpinion)
		/* 222 */.append(this.userId)
		/* 223 */.append(this.userFullname)
		/* 224 */.append(this.createtime)
		/* 225 */.append(this.fillTime)
		/* 226 */.append(this.isPass)
		/* 227 */.toHashCode();
	}

	public String toString() {
		/* 234 */return new ToStringBuilder(this)
		/* 235 */.append("handleId", this.handleId)
		/* 236 */.append("handleOpinion", this.handleOpinion)
		/* 237 */.append("userId", this.userId)
		/* 238 */.append("userFullname", this.userFullname)
		/* 239 */.append("createtime", this.createtime)
		/* 240 */.append("fillTime", this.fillTime)
		/* 241 */.append("isPass", this.isPass)
		/* 242 */.toString();
	}

	/**
	 * @return the filedDeptId
	 */
	public Long getFiledDeptId() {
		return filedDeptId;
	}

	/**
	 * @param filedDeptId the filedDeptId to set
	 */
	public void setFiledDeptId(Long filedDeptId) {
		this.filedDeptId = filedDeptId;
	}

	/**
	 * @return the filedDeptName
	 */
	public String getFiledDeptName() {
		return filedDeptName;
	}

	/**
	 * @param filedDeptName the filedDeptName to set
	 */
	public void setFiledDeptName(String filedDeptName) {
		this.filedDeptName = filedDeptName;
	}

	/**
	 * @return the recFiledTypeId
	 */
	public Long getRecFiledTypeId() {
		return recFiledTypeId;
	}

	/**
	 * @param recFiledTypeId the recFiledTypeId to set
	 */
	public void setRecFiledTypeId(Long recFiledTypeId) {
		this.recFiledTypeId = recFiledTypeId;
	}

	/**
	 * @return the recFiledTypeName
	 */
	public String getRecFiledTypeName() {
		return recFiledTypeName;
	}

	/**
	 * @param recFiledTypeName the recFiledTypeName to set
	 */
	public void setRecFiledTypeName(String recFiledTypeName) {
		this.recFiledTypeName = recFiledTypeName;
	}

}
