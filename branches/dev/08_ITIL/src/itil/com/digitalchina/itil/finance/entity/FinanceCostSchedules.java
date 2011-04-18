package com.digitalchina.itil.finance.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
/**
 * 财务成本明细表
 * 数据来源：手工录入；ERP导入；系统计算
 * 日期格式：YYYY-MM-DD hh:mm:ss
 * @author guangsa
 *
 */
public class FinanceCostSchedules extends BaseObject{
	
	
	
	private Long id;
	private int costReduceType;//成本归结类型,是配置项还是服务项;暂定配置项为1,服务项为2
	private String costItem;//具体到某个配置项或是服务项，此处记录的是各自编号
	private FinanceCostType financeCostType;//费用类型，根据成本不同来源得到不同成本类型
	private BigDecimal amount;//成本发生金额
	private UserInfo reimbursement;//财务报销人员
	private String serviceProvider;//服务提供商
	private FinanceCostCenter costCenter;//成本中心
	private Date costDate;//当前费用发生日期
	private Date borrowDate;//借款日期
	private int borrowType;//借款类型；暂定1为直接报销；2为借款；3为借款后清帐
	private String costDetailExplanation;//费用明细说明
	private int costDataSource;//费用数据来源；暂定1为手工录入；2为ERP导入；3为系统计算
	private UserInfo costAuditUser;//费用审批人
	private UserInfo costApplyUser;//费用提交人
	private Date costApplyDate;//费用提交日期
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getCostReduceType() {
		return costReduceType;
	}
	public void setCostReduceType(int costReduceType) {
		this.costReduceType = costReduceType;
	}

	public String getCostItem() {
		return costItem;
	}
	public void setCostItem(String costItem) {
		this.costItem = costItem;
	}
	public FinanceCostType getFinanceCostType() {
		return financeCostType;
	}
	public void setFinanceCostType(FinanceCostType financeCostType) {
		this.financeCostType = financeCostType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public UserInfo getReimbursement() {
		return reimbursement;
	}
	public void setReimbursement(UserInfo reimbursement) {
		this.reimbursement = reimbursement;
	}
	public String getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	
	public FinanceCostCenter getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(FinanceCostCenter costCenter) {
		this.costCenter = costCenter;
	}
	public Date getCostDate() {
		return costDate;
	}
	public void setCostDate(Date costDate) {
		this.costDate = costDate;
	}
	public Date getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	public int getBorrowType() {
		return borrowType;
	}
	public void setBorrowType(int borrowType) {
		this.borrowType = borrowType;
	}
	public String getCostDetailExplanation() {
		return costDetailExplanation;
	}
	public void setCostDetailExplanation(String costDetailExplanation) {
		this.costDetailExplanation = costDetailExplanation;
	}
	public int getCostDataSource() {
		return costDataSource;
	}
	public void setCostDataSource(int costDataSource) {
		this.costDataSource = costDataSource;
	}
	public UserInfo getCostAuditUser() {
		return costAuditUser;
	}
	public void setCostAuditUser(UserInfo costAuditUser) {
		this.costAuditUser = costAuditUser;
	}
	public UserInfo getCostApplyUser() {
		return costApplyUser;
	}
	public void setCostApplyUser(UserInfo costApplyUser) {
		this.costApplyUser = costApplyUser;
	}
	public Date getCostApplyDate() {
		return costApplyDate;
	}
	public void setCostApplyDate(Date costApplyDate) {
		this.costApplyDate = costApplyDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((borrowDate == null) ? 0 : borrowDate.hashCode());
		result = prime * result + borrowType;
		result = prime * result
				+ ((costApplyDate == null) ? 0 : costApplyDate.hashCode());
		result = prime * result
				+ ((costApplyUser == null) ? 0 : costApplyUser.hashCode());
		result = prime * result
				+ ((costAuditUser == null) ? 0 : costAuditUser.hashCode());
		result = prime * result
				+ ((costCenter == null) ? 0 : costCenter.hashCode());
		result = prime * result + costDataSource;
		result = prime * result
				+ ((costDate == null) ? 0 : costDate.hashCode());
		result = prime
				* result
				+ ((costDetailExplanation == null) ? 0 : costDetailExplanation
						.hashCode());
		result = prime * result
				+ ((costItem == null) ? 0 : costItem.hashCode());
		result = prime * result + costReduceType;
		result = prime * result
				+ ((financeCostType == null) ? 0 : financeCostType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((reimbursement == null) ? 0 : reimbursement.hashCode());
		result = prime * result
				+ ((serviceProvider == null) ? 0 : serviceProvider.hashCode());
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
		final FinanceCostSchedules other = (FinanceCostSchedules) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (borrowDate == null) {
			if (other.borrowDate != null)
				return false;
		} else if (!borrowDate.equals(other.borrowDate))
			return false;
		if (borrowType != other.borrowType)
			return false;
		if (costApplyDate == null) {
			if (other.costApplyDate != null)
				return false;
		} else if (!costApplyDate.equals(other.costApplyDate))
			return false;
		if (costApplyUser == null) {
			if (other.costApplyUser != null)
				return false;
		} else if (!costApplyUser.equals(other.costApplyUser))
			return false;
		if (costAuditUser == null) {
			if (other.costAuditUser != null)
				return false;
		} else if (!costAuditUser.equals(other.costAuditUser))
			return false;
		if (costCenter == null) {
			if (other.costCenter != null)
				return false;
		} else if (!costCenter.equals(other.costCenter))
			return false;
		if (costDataSource != other.costDataSource)
			return false;
		if (costDate == null) {
			if (other.costDate != null)
				return false;
		} else if (!costDate.equals(other.costDate))
			return false;
		if (costDetailExplanation == null) {
			if (other.costDetailExplanation != null)
				return false;
		} else if (!costDetailExplanation.equals(other.costDetailExplanation))
			return false;
		if (costItem == null) {
			if (other.costItem != null)
				return false;
		} else if (!costItem.equals(other.costItem))
			return false;
		if (costReduceType != other.costReduceType)
			return false;
		if (financeCostType == null) {
			if (other.financeCostType != null)
				return false;
		} else if (!financeCostType.equals(other.financeCostType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (reimbursement == null) {
			if (other.reimbursement != null)
				return false;
		} else if (!reimbursement.equals(other.reimbursement))
			return false;
		if (serviceProvider == null) {
			if (other.serviceProvider != null)
				return false;
		} else if (!serviceProvider.equals(other.serviceProvider))
			return false;
		return true;
	}
}
