package com.zsgj.itil.knowledge.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.service.entity.ServiceItem;

/**
 * ��������е���������
 * @Class Name KnowProblemType
 * @Author sa
 * @Create In Mar 31, 2009
 */
public class KnowProblemType extends BaseObject {
	// 2010-04-15 add by huzh for ����ɾ����� begin
	public static int DELETE_TRUE = 1;//ɾ��
	public static int DELETE_FALSE = 0;//δɾ��
	// 2010-04-15 add by huzh for ����ɾ����� end
	private Long id;
	private String name;
	private ServiceItem serviceItem;
	// 2010-04-15 add by huzh for ��Ϊɾ�����,��Ĭ��Ϊδɾ�� begin
    private Integer deleteFlag=DELETE_FALSE; 
    // 2010-04-15 add by huzh for ��Ϊɾ�����,��Ĭ��Ϊδɾ�� end
	
	public ServiceItem getServiceItem() {
		return serviceItem;
	}
	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
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
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
