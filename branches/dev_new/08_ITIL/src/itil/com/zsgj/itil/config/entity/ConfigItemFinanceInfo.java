package com.zsgj.itil.config.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.finance.entity.ApportionMode;

/**
 * 配置项财务信息
 * @Class Name ConfigItemFinanceInfo
 * @Author sa
 * @Create In 2008-11-9
 */
public class ConfigItemFinanceInfo extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 4386282195031372L;
	public static final Integer ASSETFLAG_YES=1;
	public static final Integer ASSETFLAG_NO=0;
	public static final Integer LEVELFLAG_YES=1;
	public static final Integer LEVELFLAG_NO=0;
	public static final Integer MAFLAG_YES=1;
	public static final Integer MAFLAG_NO=0;
	private Long id;
	private ConfigItem configItem;
	//主资产号
	private String mainAssetCode;
	//次资产号
	private String attachAssetCode;
	//公司代码
	private String companyCode;
	//资产标识
	private Integer assetFlag;	
	//层级标识, 标识进行成本归集（ABC成本动因法）的归集路径
	//比例归集系数??分摊系数??
	private Integer levelFlag;
	//是否MA
	private Integer maFlag;
	//MA费用
	private Double maFee;
	//MA有效起始日期
	private Date maTimeBegin;
	//MA有效终止日期
	private Date maTimeEnd;
	//购置值
	private Double buyFee;
	//销售值
	private Double saleFee;
	//折旧方式
	private DepressMode depressMode;
	//折旧周期
	private Integer depressPeriod;	
	//已折旧日期
	private Integer depressedPeriod;
	//月折旧金额
	private Double monthDepressFee;
	//月需费用
	private Double fee;
	//当前残值
	private Double feeRemain;
	//费用分摊，平均法还是比例法
	private ApportionMode apportionMode;
	
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((configItem == null) ? 0 : configItem.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((maFee == null) ? 0 : maFee.hashCode());
		result = PRIME * result + ((maFlag == null) ? 0 : maFlag.hashCode());
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
		final ConfigItemFinanceInfo other = (ConfigItemFinanceInfo) obj;
		if (configItem == null) {
			if (other.configItem != null)
				return false;
		} else if (!configItem.equals(other.configItem))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maFee == null) {
			if (other.maFee != null)
				return false;
		} else if (!maFee.equals(other.maFee))
			return false;
		if (maFlag == null) {
			if (other.maFlag != null)
				return false;
		} else if (!maFlag.equals(other.maFlag))
			return false;
		return true;
	}
	public ApportionMode getApportionMode() {
		return apportionMode;
	}
	public void setApportionMode(ApportionMode apportionMode) {
		this.apportionMode = apportionMode;
	}
	public Integer getAssetFlag() {
		return assetFlag;
	}
	public void setAssetFlag(Integer assetFlag) {
		this.assetFlag = assetFlag;
	}
	public Double getBuyFee() {
		return buyFee;
	}
	public void setBuyFee(Double buyFee) {
		this.buyFee = buyFee;
	}
	public ConfigItem getConfigItem() {
		return configItem;
	}
	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}
	public Integer getDepressedPeriod() {
		return depressedPeriod;
	}
	public void setDepressedPeriod(Integer depressedPeriod) {
		this.depressedPeriod = depressedPeriod;
	}
	public DepressMode getDepressMode() {
		return depressMode;
	}
	public void setDepressMode(DepressMode depressMode) {
		this.depressMode = depressMode;
	}
	public Integer getDepressPeriod() {
		return depressPeriod;
	}
	public void setDepressPeriod(Integer depressPeriod) {
		this.depressPeriod = depressPeriod;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Double getFeeRemain() {
		return feeRemain;
	}
	public void setFeeRemain(Double feeRemain) {
		this.feeRemain = feeRemain;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLevelFlag() {
		return levelFlag;
	}
	public void setLevelFlag(Integer levelFlag) {
		this.levelFlag = levelFlag;
	}
	public Double getMaFee() {
		return maFee;
	}
	public void setMaFee(Double maFee) {
		this.maFee = maFee;
	}
	public Integer getMaFlag() {
		return maFlag;
	}
	public void setMaFlag(Integer maFlag) {
		this.maFlag = maFlag;
	}
	public Date getMaTimeBegin() {
		return maTimeBegin;
	}
	public void setMaTimeBegin(Date maTimeBegin) {
		this.maTimeBegin = maTimeBegin;
	}
	public Date getMaTimeEnd() {
		return maTimeEnd;
	}
	public void setMaTimeEnd(Date maTimeEnd) {
		this.maTimeEnd = maTimeEnd;
	}
	public Double getMonthDepressFee() {
		return monthDepressFee;
	}
	public void setMonthDepressFee(Double monthDepressFee) {
		this.monthDepressFee = monthDepressFee;
	}
	public Double getSaleFee() {
		return saleFee;
	}
	public void setSaleFee(Double saleFee) {
		this.saleFee = saleFee;
	}
	public String getAttachAssetCode() {
		return attachAssetCode;
	}
	public void setAttachAssetCode(String attachAssetCode) {
		this.attachAssetCode = attachAssetCode;
	}
	public String getMainAssetCode() {
		return mainAssetCode;
	}
	public void setMainAssetCode(String mainAssetCode) {
		this.mainAssetCode = mainAssetCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
}
