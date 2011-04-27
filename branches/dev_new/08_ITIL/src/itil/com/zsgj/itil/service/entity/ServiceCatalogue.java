package com.zsgj.itil.service.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.CustomerType;

/**
 * 服务目录
 * 
 * 由客户服务经理和IT服务管理部门的人员共同参与制定，由使用此服务目录的客户确定
 * 一个服务目录，一般是服务于一个客户，如果对一个集团客户都适用，则适用于这个集团下的所有客户
 * @Class Name ServiceCatalogue
 * 
 * 可能需要2个服务目录与服务组合的关系表
 * SI ID 
 * belongOutCustomer
 * 
 * SI ID
 * belongDept
 * @Author sa
 * @Create In 2008-11-9
 */
public class ServiceCatalogue extends BaseObject{
	public static int STATUS_DRAFT = 0;// 草稿
	public static int STATUS_FINISHED = 1;// 通过
	public static int STATUS_APPROVING = 2;// 提交审批中
	public static int STATUS_DELETE = -1;// 已删除
	
	public static int STATUS_ALTER_DRAFT = 4;// 草稿
	
	public static int ROOT_FALSE = 0;	// 非根目录
	public static int ROOT_TRUE = 1;	// 根目录
	
	//服务目录ID
	private Long id;
	//服务组合ID
//	private ServicePortfolio sp;//remove by lee for 废弃属性 in 20091121
	//所服务的客户
	private Customer customer;
	//客户类型
	private CustomerType customerType;
	
	//private Integer typeFlag; //外部和内部的2种或者后面扩展
	//外部来源与外部客户，否则来源与部门，
	//typeFlag将来可能对应一张类型表，每个类型对应一张表
	
	//服务目录名称
	private String name;
	//服务目录说明
	private String descn;
	//隶属公司，内部就属于部门
		
	private Date beginDate;
	
	private Date endDate;
	
	private Integer rootFlag;
	
	private Integer status;
	
	//删除则显示标记-1
	private Integer deleteFlag;
	
	private UserInfo createUser;
	
	private Date createDate;
	
	private UserInfo modifyUser;
	
	private Date modifyDate;
	//有效期
	private Date validDate;
	
	//变更标志
	private Integer alterFlag;
	
	private Long oldCatalogueId;
	
	//服务客户列表

	
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
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
//	public ServicePortfolio getSp() {
//		return sp;
//	}
//	public void setSp(ServicePortfolio sp) {
//		this.sp = sp;
//	}
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
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

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public UserInfo getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public UserInfo getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(UserInfo modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Integer getRootFlag() {
		return rootFlag;
	}
	public void setRootFlag(Integer rootFlag) {
		this.rootFlag = rootFlag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ServiceCatalogue other = (ServiceCatalogue)obj;
		if(id==null){
			if(other.id!=null)
				return false;
		}else if(!id.equals(other.id)){
			return false;
		}
		if(name==null){
			if(other.name!=null)
				return false;
		}else if(!name.equals(other.name)){
			return false;
		}
		return true;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public Integer getAlterFlag() {
		return alterFlag;
	}
	public void setAlterFlag(Integer alterFlag) {
		this.alterFlag = alterFlag;
	}
	public Long getOldCatalogueId() {
		return oldCatalogueId;
	}
	public void setOldCatalogueId(Long oldCatalogueId) {
		this.oldCatalogueId = oldCatalogueId;
	}
}
