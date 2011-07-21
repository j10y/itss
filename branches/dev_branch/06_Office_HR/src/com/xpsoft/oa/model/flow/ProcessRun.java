package com.xpsoft.oa.model.flow;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ProcessRun extends BaseModel {
	/* 26 */public static final Short RUN_STATUS_INIT = Short
			.valueOf((short) 0);

	/* 30 */public static final Short RUN_STATUS_RUNNING = Short
			.valueOf((short) 1);

	/* 34 */public static final Short RUN_STATUS_FINISHED = Short
			.valueOf((short) 2);
	protected Long runId;

	@Expose
	protected String subject;

	@Expose
	protected String creator;

	@Expose
	protected Date createtime;

	@Expose
	protected ProDefinition proDefinition;

	@Expose
	protected String piId;

	@Expose
	/* 48 */protected Short runStatus = RUN_STATUS_INIT;
	protected AppUser appUser;
	/* 52 */protected Set processForms = new HashSet();

	public ProcessRun() {
	}

	public ProcessRun(Long in_runId) {
		/* 67 */setRunId(in_runId);
	}

	public ProDefinition getProDefinition() {
		/* 72 */return this.proDefinition;
	}

	public void setProDefinition(ProDefinition proDefinition) {
		/* 76 */this.proDefinition = proDefinition;
	}

	public AppUser getAppUser() {
		/* 80 */return this.appUser;
	}

	public void setAppUser(AppUser in_appUser) {
		/* 84 */this.appUser = in_appUser;
	}

	public Set getProcessForms() {
		/* 88 */return this.processForms;
	}

	public void setProcessForms(Set in_processForms) {
		/* 92 */this.processForms = in_processForms;
	}

	public Long getRunId() {
		/* 101 */return this.runId;
	}

	public void setRunId(Long aValue) {
		/* 108 */this.runId = aValue;
	}

	public String getSubject() {
		/* 117 */return this.subject;
	}

	public void setSubject(String aValue) {
		/* 125 */this.subject = aValue;
	}

	public String getCreator() {
		/* 133 */return this.creator;
	}

	public void setCreator(String aValue) {
		/* 140 */this.creator = aValue;
	}

	public Long getUserId() {
		/* 147 */return getAppUser() == null ? null : getAppUser().getUserId();
	}

	public void setUserId(Long aValue) {
		/* 154 */if (aValue == null) {
			/* 155 */this.appUser = null;
			/* 156 */} else if (this.appUser == null) {
			/* 157 */this.appUser = new AppUser(aValue);
			/* 158 */this.appUser.setVersion(new Integer(0));
		} else {
			/* 160 */this.appUser.setUserId(aValue);
		}
	}

	public String getPiId() {
		/* 170 */return this.piId;
	}

	public void setPiId(String aValue) {
		/* 177 */this.piId = aValue;
	}

	public boolean equals(Object object) {
		/* 184 */if (!(object instanceof ProcessRun)) {
			/* 185 */return false;
		}
		/* 187 */ProcessRun rhs = (ProcessRun) object;
		/* 188 */return new EqualsBuilder()
		/* 189 */.append(this.runId, rhs.runId)
		/* 190 */.append(this.subject, rhs.subject)
		/* 191 */.append(this.creator, rhs.creator)
		/* 192 */.append(this.piId, rhs.piId)
		/* 193 */.isEquals();
	}

	public int hashCode() {
		/* 200 */return new HashCodeBuilder(-82280557, -700257973)
		/* 201 */.append(this.runId)
		/* 202 */.append(this.subject)
		/* 203 */.append(this.creator)
		/* 204 */.append(this.piId)
		/* 205 */.toHashCode();
	}

	public String toString() {
		/* 212 */return new ToStringBuilder(this)
		/* 213 */.append("runId", this.runId)
		/* 214 */.append("subject", this.subject)
		/* 215 */.append("creator", this.creator)
		/* 216 */.append("piId", this.piId)
		/* 217 */.toString();
	}

	public Date getCreatetime() {
		/* 221 */return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		/* 225 */this.createtime = createtime;
	}

	public Short getRunStatus() {
		/* 229 */return this.runStatus;
	}

	public void setRunStatus(Short runStatus) {
		/* 233 */this.runStatus = runStatus;
	}
}
