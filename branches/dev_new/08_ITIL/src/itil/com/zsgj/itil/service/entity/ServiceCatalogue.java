package com.zsgj.itil.service.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.CustomerType;

/**
 * ����Ŀ¼
 * 
 * �ɿͻ��������IT��������ŵ���Ա��ͬ�����ƶ�����ʹ�ô˷���Ŀ¼�Ŀͻ�ȷ��
 * һ������Ŀ¼��һ���Ƿ�����һ���ͻ��������һ�����ſͻ������ã�����������������µ����пͻ�
 * @Class Name ServiceCatalogue
 * 
 * ������Ҫ2������Ŀ¼�������ϵĹ�ϵ��
 * SI ID 
 * belongOutCustomer
 * 
 * SI ID
 * belongDept
 * @Author sa
 * @Create In 2008-11-9
 */
public class ServiceCatalogue extends BaseObject{
	public static int STATUS_DRAFT = 0;// �ݸ�
	public static int STATUS_FINISHED = 1;// ͨ��
	public static int STATUS_APPROVING = 2;// �ύ������
	public static int STATUS_DELETE = -1;// ��ɾ��
	
	public static int STATUS_ALTER_DRAFT = 4;// �ݸ�
	
	public static int ROOT_FALSE = 0;	// �Ǹ�Ŀ¼
	public static int ROOT_TRUE = 1;	// ��Ŀ¼
	
	//����Ŀ¼ID
	private Long id;
	//�������ID
//	private ServicePortfolio sp;//remove by lee for �������� in 20091121
	//������Ŀͻ�
	private Customer customer;
	//�ͻ�����
	private CustomerType customerType;
	
	//private Integer typeFlag; //�ⲿ���ڲ���2�ֻ��ߺ�����չ
	//�ⲿ��Դ���ⲿ�ͻ���������Դ�벿�ţ�
	//typeFlag�������ܶ�Ӧһ�����ͱ�ÿ�����Ͷ�Ӧһ�ű�
	
	//����Ŀ¼����
	private String name;
	//����Ŀ¼˵��
	private String descn;
	//������˾���ڲ������ڲ���
		
	private Date beginDate;
	
	private Date endDate;
	
	private Integer rootFlag;
	
	private Integer status;
	
	//ɾ������ʾ���-1
	private Integer deleteFlag;
	
	private UserInfo createUser;
	
	private Date createDate;
	
	private UserInfo modifyUser;
	
	private Date modifyDate;
	//��Ч��
	private Date validDate;
	
	//�����־
	private Integer alterFlag;
	
	private Long oldCatalogueId;
	
	//����ͻ��б�

	
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
