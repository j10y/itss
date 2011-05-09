package com.zsgj.itil.finance.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

public class FinanceCostCenter extends BaseObject{

	private Long id;
	private String CBZXDM;//成本中心代码
	private Date ZZRQ;//中止有效日期
	private String YWFWDM;//业务范围代码
	private String GSDM;//公司代码
	private String DJBZ;//冻结标志，X已被冻结
	private String CBZXLX;//成本中心类型
	private String LRZXDM;//利润中心代码
	private String GCDM;//工厂代码
	private Date CJRQ;//创建日期
	private String CBZXMC;//成本中心名称
	private String CBZXLXMC;//成本中心类型名称
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCBZXDM() {
		return CBZXDM;
	}
	public void setCBZXDM(String cbzxdm) {
		CBZXDM = cbzxdm;
	}
	public Date getZZRQ() {
		return ZZRQ;
	}
	public void setZZRQ(Date zzrq) {
		ZZRQ = zzrq;
	}
	public String getYWFWDM() {
		return YWFWDM;
	}
	public void setYWFWDM(String ywfwdm) {
		YWFWDM = ywfwdm;
	}
	public String getGSDM() {
		return GSDM;
	}
	public void setGSDM(String gsdm) {
		GSDM = gsdm;
	}
	public String getDJBZ() {
		return DJBZ;
	}
	public void setDJBZ(String djbz) {
		DJBZ = djbz;
	}
	public String getCBZXLX() {
		return CBZXLX;
	}
	public void setCBZXLX(String cbzxlx) {
		CBZXLX = cbzxlx;
	}
	public String getLRZXDM() {
		return LRZXDM;
	}
	public void setLRZXDM(String lrzxdm) {
		LRZXDM = lrzxdm;
	}
	public String getGCDM() {
		return GCDM;
	}
	public void setGCDM(String gcdm) {
		GCDM = gcdm;
	}
	public Date getCJRQ() {
		return CJRQ;
	}
	public void setCJRQ(Date cjrq) {
		CJRQ = cjrq;
	}
	public String getCBZXMC() {
		return CBZXMC;
	}
	public void setCBZXMC(String cbzxmc) {
		CBZXMC = cbzxmc;
	}
	public String getCBZXLXMC() {
		return CBZXLXMC;
	}
	public void setCBZXLXMC(String cbzxlxmc) {
		CBZXLXMC = cbzxlxmc;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((CBZXDM == null) ? 0 : CBZXDM.hashCode());
		result = prime * result + ((CBZXLX == null) ? 0 : CBZXLX.hashCode());
		result = prime * result
				+ ((CBZXLXMC == null) ? 0 : CBZXLXMC.hashCode());
		result = prime * result + ((CBZXMC == null) ? 0 : CBZXMC.hashCode());
		result = prime * result + ((CJRQ == null) ? 0 : CJRQ.hashCode());
		result = prime * result + ((DJBZ == null) ? 0 : DJBZ.hashCode());
		result = prime * result + ((GCDM == null) ? 0 : GCDM.hashCode());
		result = prime * result + ((GSDM == null) ? 0 : GSDM.hashCode());
		result = prime * result + ((LRZXDM == null) ? 0 : LRZXDM.hashCode());
		result = prime * result + ((YWFWDM == null) ? 0 : YWFWDM.hashCode());
		result = prime * result + ((ZZRQ == null) ? 0 : ZZRQ.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FinanceCostCenter other = (FinanceCostCenter) obj;
		if (CBZXDM == null) {
			if (other.CBZXDM != null)
				return false;
		} else if (!CBZXDM.equals(other.CBZXDM))
			return false;
		if (CBZXLX == null) {
			if (other.CBZXLX != null)
				return false;
		} else if (!CBZXLX.equals(other.CBZXLX))
			return false;
		if (CBZXLXMC == null) {
			if (other.CBZXLXMC != null)
				return false;
		} else if (!CBZXLXMC.equals(other.CBZXLXMC))
			return false;
		if (CBZXMC == null) {
			if (other.CBZXMC != null)
				return false;
		} else if (!CBZXMC.equals(other.CBZXMC))
			return false;
		if (CJRQ == null) {
			if (other.CJRQ != null)
				return false;
		} else if (!CJRQ.equals(other.CJRQ))
			return false;
		if (DJBZ == null) {
			if (other.DJBZ != null)
				return false;
		} else if (!DJBZ.equals(other.DJBZ))
			return false;
		if (GCDM == null) {
			if (other.GCDM != null)
				return false;
		} else if (!GCDM.equals(other.GCDM))
			return false;
		if (GSDM == null) {
			if (other.GSDM != null)
				return false;
		} else if (!GSDM.equals(other.GSDM))
			return false;
		if (LRZXDM == null) {
			if (other.LRZXDM != null)
				return false;
		} else if (!LRZXDM.equals(other.LRZXDM))
			return false;
		if (YWFWDM == null) {
			if (other.YWFWDM != null)
				return false;
		} else if (!YWFWDM.equals(other.YWFWDM))
			return false;
		if (ZZRQ == null) {
			if (other.ZZRQ != null)
				return false;
		} else if (!ZZRQ.equals(other.ZZRQ))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
