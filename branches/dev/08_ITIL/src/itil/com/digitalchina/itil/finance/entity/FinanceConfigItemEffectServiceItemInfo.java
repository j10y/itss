package com.digitalchina.itil.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.service.entity.ServiceItem;

public class FinanceConfigItemEffectServiceItemInfo extends BaseObject{

	private Long id;
	private ConfigItem configItem;//配置项变动
	private ServiceItem serviceItem;//影响到服务项
	private BigDecimal oldConfigItemCost;//原始配置项成本
	private BigDecimal inputCost;//录入金额
	private BigDecimal oldServiceItemCost;//原始服务项成本
	private BigDecimal changedServiceItemCost;//改变以后服务项成本
	private String apportionType;//暂定1为归结系数
	private UserInfo reimbursement;//费用发生人
	private Date costDate;//当前费用发生日期
	private int CostDataSource;//费用数据来源；暂定1为手工录入；2为ERP导入；3为系统计算
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ConfigItem getConfigItem() {
		return configItem;
	}
	public void setConfigItem(ConfigItem configItem) {
		this.configItem = configItem;
	}
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	public BigDecimal getOldConfigItemCost() {
		return oldConfigItemCost;
	}
	public void setOldConfigItemCost(BigDecimal oldConfigItemCost) {
		this.oldConfigItemCost = oldConfigItemCost;
	}
	public BigDecimal getInputCost() {
		return inputCost;
	}
	public void setInputCost(BigDecimal inputCost) {
		this.inputCost = inputCost;
	}
	public BigDecimal getOldServiceItemCost() {
		return oldServiceItemCost;
	}
	public void setOldServiceItemCost(BigDecimal oldServiceItemCost) {
		this.oldServiceItemCost = oldServiceItemCost;
	}
	public BigDecimal getChangedServiceItemCost() {
		return changedServiceItemCost;
	}
	public void setChangedServiceItemCost(BigDecimal changedServiceItemCost) {
		this.changedServiceItemCost = changedServiceItemCost;
	}
	public String getApportionType() {
		return apportionType;
	}
	public void setApportionType(String apportionType) {
		this.apportionType = apportionType;
	}
	public UserInfo getReimbursement() {
		return reimbursement;
	}
	public void setReimbursement(UserInfo reimbursement) {
		this.reimbursement = reimbursement;
	}
	public Date getCostDate() {
		return costDate;
	}
	public void setCostDate(Date costDate) {
		this.costDate = costDate;
	}
	public int getCostDataSource() {
		return CostDataSource;
	}
	public void setCostDataSource(int costDataSource) {
		CostDataSource = costDataSource;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + CostDataSource;
		result = prime * result
				+ ((apportionType == null) ? 0 : apportionType.hashCode());
		result = prime
				* result
				+ ((changedServiceItemCost == null) ? 0
						: changedServiceItemCost.hashCode());
		result = prime * result
				+ ((configItem == null) ? 0 : configItem.hashCode());
		result = prime * result
				+ ((costDate == null) ? 0 : costDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((inputCost == null) ? 0 : inputCost.hashCode());
		result = prime
				* result
				+ ((oldConfigItemCost == null) ? 0 : oldConfigItemCost
						.hashCode());
		result = prime
				* result
				+ ((oldServiceItemCost == null) ? 0 : oldServiceItemCost
						.hashCode());
		result = prime * result
				+ ((reimbursement == null) ? 0 : reimbursement.hashCode());
		result = prime * result
				+ ((serviceItem == null) ? 0 : serviceItem.hashCode());
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
		final FinanceConfigItemEffectServiceItemInfo other = (FinanceConfigItemEffectServiceItemInfo) obj;
		if (CostDataSource != other.CostDataSource)
			return false;
		if (apportionType == null) {
			if (other.apportionType != null)
				return false;
		} else if (!apportionType.equals(other.apportionType))
			return false;
		if (changedServiceItemCost == null) {
			if (other.changedServiceItemCost != null)
				return false;
		} else if (!changedServiceItemCost.equals(other.changedServiceItemCost))
			return false;
		if (configItem == null) {
			if (other.configItem != null)
				return false;
		} else if (!configItem.equals(other.configItem))
			return false;
		if (costDate == null) {
			if (other.costDate != null)
				return false;
		} else if (!costDate.equals(other.costDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inputCost == null) {
			if (other.inputCost != null)
				return false;
		} else if (!inputCost.equals(other.inputCost))
			return false;
		if (oldConfigItemCost == null) {
			if (other.oldConfigItemCost != null)
				return false;
		} else if (!oldConfigItemCost.equals(other.oldConfigItemCost))
			return false;
		if (oldServiceItemCost == null) {
			if (other.oldServiceItemCost != null)
				return false;
		} else if (!oldServiceItemCost.equals(other.oldServiceItemCost))
			return false;
		if (reimbursement == null) {
			if (other.reimbursement != null)
				return false;
		} else if (!reimbursement.equals(other.reimbursement))
			return false;
		if (serviceItem == null) {
			if (other.serviceItem != null)
				return false;
		} else if (!serviceItem.equals(other.serviceItem))
			return false;
		return true;
	}
	
}
