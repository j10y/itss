package com.zsgj.itil.config.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.actor.entity.CustomerType;

/**
 * �������ϵͼ
 * �����Ƶ��������ϵ��Ϊ��ϵͼ�����ƣ������ͼ�ĸ��ڵ�.
 * 
 * �������ϵ�������̣� 
 * 
 * �½��������ϵ��������ƣ�������ֹ���ڣ����棬
 * Ĭ���Դ�������Ϊ���ڵ���ʾ��Ȼ����Ҳ�ͨ�����������ͺ�����������������������ĸ�����ק
 * ��һ����ק�����Ľڵ㣬��parentConfigItem��Ȼ�ǵ�ǰ�ձ�����������ϵ��
 * 
 * ������Ҫ������ڵ��ͼƬ���ܸ���״̬�Ĳ�ͬ����ͬ
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
	public static final String NAME_zsgj="���̹���";
	private Long id;
	//�������ϵ����
	private String name;//ɾ��������ʷ��
	//��ϵ��ʼ����
	private Date beginDate;
	//��ϵ��������
	private Date endDate;
	
	//�ͻ�����
	private CustomerType customerType;
	
	//�����ͻ�
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
