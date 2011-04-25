package com.digitalchina.itil.config.extlist.entity;

import java.util.Date;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;

public class NotesIDFile extends BaseObject {
	private java.lang.Long id;
	private java.lang.String fileName;
	private java.lang.String webMail;
	private java.lang.String dcMail;
	private java.lang.String attachment;
	private java.util.Date createDate;
	private AccountApplyMainTable applyId;
	private String  noPassword;
	private String  password;

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}

	public void setWebMail(java.lang.String webMail) {
		this.webMail = webMail;
	}

	public void setAttachment(java.lang.String attachment) {
		this.attachment = attachment;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public java.lang.String getFileName() {
		return this.fileName;
	}

	public java.lang.String getWebMail() {
		return this.webMail;
	}

	public java.lang.String getDcMail() {
		return dcMail;
	}

	public void setDcMail(java.lang.String dcMail) {
		this.dcMail = dcMail;
	}

	public java.lang.String getAttachment() {
		return this.attachment;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	

	public AccountApplyMainTable getApplyId() {
		return applyId;
	}

	public void setApplyId(AccountApplyMainTable applyId) {
		this.applyId = applyId;
	}
	

	public String getNoPassword() {
		return noPassword;
	}

	public void setNoPassword(String noPassword) {
		this.noPassword = noPassword;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final com.digitalchina.itil.config.extlist.entity.NotesIDFile other = (com.digitalchina.itil.config.extlist.entity.NotesIDFile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
