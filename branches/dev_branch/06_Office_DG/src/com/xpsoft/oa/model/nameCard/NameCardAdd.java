package com.xpsoft.oa.model.nameCard;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

/**
 * 办理胸卡
 * @author wchao
 *
 */
public class NameCardAdd extends BaseModel{

	private Long nameCardAddId; //自动编号
	private String cardNum; //胸卡编号
	//private Long shopId; //门店ID
	private Department shop;//门店
	//private Long shoppingGuidId; //导购ID
	private AppUser shoppingGuid;
	private Integer type; //办卡类型 1新办卡2补卡3退卡
	private String billNum; //票据流水号
	private BigDecimal deposit; //办卡押金
	private BigDecimal cost; //成本费/补办
	private AppUser handler; //经手人
	private Date handleTime; //办理日期
	private Integer status;//是否生效
	
	public Long getNameCardAddId() {
		return nameCardAddId;
	}
	public void setNameCardAddId(Long nameCardAddId) {
		this.nameCardAddId = nameCardAddId;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public Department getShop() {
		return shop;
	}
	public void setShop(Department shop) {
		this.shop = shop;
	}
	public AppUser getShoppingGuid() {
		return shoppingGuid;
	}
	public void setShoppingGuid(AppUser shoppingGuid) {
		this.shoppingGuid = shoppingGuid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public BigDecimal getDeposit() {
		return deposit;
	}
	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public AppUser getHandler() {
		return handler;
	}
	public void setHandler(AppUser handler) {
		this.handler = handler;
	}
	public Date getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	
	
}
