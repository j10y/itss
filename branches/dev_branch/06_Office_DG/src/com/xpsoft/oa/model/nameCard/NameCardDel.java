package com.xpsoft.oa.model.nameCard;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.xpsoft.core.model.BaseModel;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;

/**
 * 退胸卡
 * @author wchao
 *
 */
public class NameCardDel extends BaseModel{

	private Long nameCardDelId; //自动编号
	private String cardNum; //胸卡编号
	private Department shop;//门店
	private AppUser shoppingGuid; //导购
	private Integer type; //办卡类型 1新办卡2补卡3退卡
	private BigDecimal refundAmount; //退卡金额
	private AppUser handler; //经手人
	private Date handleTime; //办卡日期
	private Date quitTime; //退卡日期，系统时间
	private Integer status;//是否生效
	
	public Long getNameCardDelId() {
		return nameCardDelId;
	}
	public void setNameCardDelId(Long nameCardDelId) {
		this.nameCardDelId = nameCardDelId;
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
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
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
	public Date getQuitTime() {
		return quitTime;
	}
	public void setQuitTime(Date quitTime) {
		this.quitTime = quitTime;
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
