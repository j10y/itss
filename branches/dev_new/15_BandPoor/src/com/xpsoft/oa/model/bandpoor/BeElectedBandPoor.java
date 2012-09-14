package com.xpsoft.oa.model.bandpoor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
/**
 * 待选品牌池
 * @author jptong
 *
 */
public class BeElectedBandPoor extends BaseModel{
	public static final Integer TYPE_SCORE = new Integer(1);//可评分
	public static final Integer TYPE_UNSCORE = new Integer(2);//不可评分
	public static final Integer STATUS_CREATE = new Integer(1);//新建
	public static final Integer STATUS_DELETE = new Integer(0);//删除
	private Long id;
	
	private String bandName;
	
	private Band bandId;
	
	private Double bandScore;
	
	private Integer status;
	
	private Date creatDate;
	
	private Date modifyDate;
	
	private AppUser createUser;
	
	private AppUser modifyUser;
	
	private Integer infoType;

	private ProClass proClassId;
	
	private Set<InfoPoor> infoPoors=new HashSet();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public Band getBandId() {
		return bandId;
	}

	public void setBandId(Band bandId) {
		this.bandId = bandId;
	}

	public Double getBandScore() {
		return bandScore;
	}

	public void setBandScore(Double bandScore) {
		this.bandScore = bandScore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public AppUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(AppUser createUser) {
		this.createUser = createUser;
	}

	public AppUser getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(AppUser modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}

	public Set<InfoPoor> getInfoPoors() {
		return infoPoors;
	}

	public void setInfoPoors(Set<InfoPoor> infoPoors) {
		this.infoPoors = infoPoors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bandId == null) ? 0 : bandId.hashCode());
		result = prime * result
				+ ((bandName == null) ? 0 : bandName.hashCode());
		result = prime * result
				+ ((bandScore == null) ? 0 : bandScore.hashCode());
		result = prime * result
				+ ((creatDate == null) ? 0 : creatDate.hashCode());
		result = prime * result
				+ ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((infoPoors == null) ? 0 : infoPoors.hashCode());
		result = prime * result
				+ ((infoType == null) ? 0 : infoType.hashCode());
		result = prime * result
				+ ((modifyDate == null) ? 0 : modifyDate.hashCode());
		result = prime * result
				+ ((modifyUser == null) ? 0 : modifyUser.hashCode());
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
		final BeElectedBandPoor other = (BeElectedBandPoor) obj;
		if (bandId == null) {
			if (other.bandId != null)
				return false;
		} else if (!bandId.equals(other.bandId))
			return false;
		if (bandName == null) {
			if (other.bandName != null)
				return false;
		} else if (!bandName.equals(other.bandName))
			return false;
		if (bandScore == null) {
			if (other.bandScore != null)
				return false;
		} else if (!bandScore.equals(other.bandScore))
			return false;
		if (creatDate == null) {
			if (other.creatDate != null)
				return false;
		} else if (!creatDate.equals(other.creatDate))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (infoPoors == null) {
			if (other.infoPoors != null)
				return false;
		} else if (!infoPoors.equals(other.infoPoors))
			return false;
		if (infoType == null) {
			if (other.infoType != null)
				return false;
		} else if (!infoType.equals(other.infoType))
			return false;
		if (modifyDate == null) {
			if (other.modifyDate != null)
				return false;
		} else if (!modifyDate.equals(other.modifyDate))
			return false;
		if (modifyUser == null) {
			if (other.modifyUser != null)
				return false;
		} else if (!modifyUser.equals(other.modifyUser))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	public ProClass getProClassId() {
		return proClassId;
	}

	public void setProClassId(ProClass proClassId) {
		this.proClassId = proClassId;
	}
	
	
}
