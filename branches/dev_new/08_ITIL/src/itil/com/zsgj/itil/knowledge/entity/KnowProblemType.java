package com.zsgj.itil.knowledge.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.service.entity.ServiceItem;

/**
 * 解决方案中的问题类型
 * @Class Name KnowProblemType
 * @Author sa
 * @Create In Mar 31, 2009
 */
public class KnowProblemType extends BaseObject {
	// 2010-04-15 add by huzh for 增加删除标记 begin
	public static int DELETE_TRUE = 1;//删除
	public static int DELETE_FALSE = 0;//未删除
	// 2010-04-15 add by huzh for 增加删除标记 end
	private Long id;
	private String name;
	private ServiceItem serviceItem;
	// 2010-04-15 add by huzh for 作为删除标记,并默认为未删除 begin
    private Integer deleteFlag=DELETE_FALSE; 
    // 2010-04-15 add by huzh for 作为删除标记,并默认为未删除 end
	
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
