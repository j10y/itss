package com.xpsoft.oa.model.bandpoor;

import java.util.Date;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.FileAttach;

public class PictureOrdoc extends BaseModel{
	private Long id;
	
	private FileAttach fileAttach;
	
	private InfoPoor infoPoorId;
	
	private String docName;
	
	private String docPath;
	
	private Date createtime;
	
	private Integer status;//0删除 1 新建

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FileAttach getFileAttach() {
		return fileAttach;
	}

	public void setFileAttach(FileAttach fileAttach) {
		this.fileAttach = fileAttach;
	}

	public InfoPoor getInfoPoorId() {
		return infoPoorId;
	}

	public void setInfoPoorId(InfoPoor infoPoorId) {
		this.infoPoorId = infoPoorId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((docName == null) ? 0 : docName.hashCode());
		result = prime * result + ((docPath == null) ? 0 : docPath.hashCode());
		result = prime * result
				+ ((fileAttach == null) ? 0 : fileAttach.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((infoPoorId == null) ? 0 : infoPoorId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PictureOrdoc other = (PictureOrdoc) obj;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (docName == null) {
			if (other.docName != null)
				return false;
		} else if (!docName.equals(other.docName))
			return false;
		if (docPath == null) {
			if (other.docPath != null)
				return false;
		} else if (!docPath.equals(other.docPath))
			return false;
		if (fileAttach == null) {
			if (other.fileAttach != null)
				return false;
		} else if (!fileAttach.equals(other.fileAttach))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (infoPoorId == null) {
			if (other.infoPoorId != null)
				return false;
		} else if (!infoPoorId.equals(other.infoPoorId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
}
