package com.zsgj.itil.config.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.actor.entity.CustomerType;

/**
 * 配置项关系图
 * 有名称的配置项关系即为关系图的名称，即真个图的根节点.
 * 
 * 配置项关系建立过程： 
 * 
 * 新建配置项关系，起个名称，输入起止日期，保存，
 * 默认以此名称作为根节点显示，然后从右侧通过配置项类型和名称搜索配置项，依次向左侧的根上拖拽
 * 第一次拖拽过来的节点，其parentConfigItem自然是当前刚保存的配置项关系。
 * 
 * 另外主要配置项节点的图片可能根据状态的不同而不同
 * 
 * @Class Name ConfigItemRelation
 * @Author sa
 * @Create In 2008-11-9
 * @deprecated deprecated by duxh in 09-11-19
 */
public class CIRelationShipPic extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -8868287903445103029L;
	public static final String NAME_zsgj="中商国际";
	private Long id;
	//配置项关系名称
	private String name;//删除还有历史表
	//关系起始日期
	private Date beginDate;
	//关系结束日期
	private Date endDate;
	
	//客户类型
	private CustomerType customerType;
	
	//隶属客户
	private Long customer;

	private Integer deleteFlag;
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getOtherInfo() {
//		return otherInfo;
//	}
//	public void setOtherInfo(String otherInfo) {
//		this.otherInfo = otherInfo;
//	}
//	public String getAtechnoInfo() {
//		return atechnoInfo;
//	}
//	public void setAtechnoInfo(String atechnoInfo) {
//		this.atechnoInfo = atechnoInfo;
//	}
//	public String getBtechnoInfo() {
//		return btechnoInfo;
//	}
//	public void setBtechnoInfo(String btechnoInfo) {
//		this.btechnoInfo = btechnoInfo;
//	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public Long getCustomer() {
		return customer;
	}
	public void setCustomer(Long customer) {
		this.customer = customer;
	}
	

}
