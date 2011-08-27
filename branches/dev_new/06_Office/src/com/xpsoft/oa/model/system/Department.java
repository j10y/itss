package com.xpsoft.oa.model.system;

import com.google.gson.annotations.Expose;
import com.xpsoft.core.model.BaseModel;

public class Department extends BaseModel {

	@Expose
	private Long depId;

	@Expose
	private String depName;

	@Expose
	private String depDesc;

	@Expose
	private Integer depLevel;

	@Expose
	private Long parentId;

	@Expose
	private String path;

	@Expose
	private Integer isDist;

	public Department() {
	}

	public Department(Long depId) {
		/* 29 */setDepId(depId);
	}

	public Long getDepId() {
		/* 33 */return this.depId;
	}

	public void setDepId(Long depId) {
		/* 37 */this.depId = depId;
	}

	public String getDepName() {
		/* 41 */return this.depName;
	}

	public void setDepName(String depName) {
		/* 45 */this.depName = depName;
	}

	public String getDepDesc() {
		/* 49 */return this.depDesc;
	}

	public void setDepDesc(String depDesc) {
		/* 53 */this.depDesc = depDesc;
	}

	public Integer getDepLevel() {
		/* 57 */return this.depLevel;
	}

	public void setDepLevel(Integer depLevel) {
		/* 61 */this.depLevel = depLevel;
	}

	public Long getParentId() {
		/* 65 */return this.parentId;
	}

	public void setParentId(Long parentId) {
		/* 69 */this.parentId = parentId;
	}

	public String getPath() {
		/* 73 */return this.path;
	}

	public void setPath(String path) {
		/* 77 */this.path = path;
	}

	/**
	 * @return the isDist
	 */
	public Integer getIsDist() {
		return isDist;
	}

	/**
	 * @param isDist the isDist to set
	 */
	public void setIsDist(Integer isDist) {
		this.isDist = isDist;
	}
}
