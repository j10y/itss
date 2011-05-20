package com.zsgj.itil.service.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * SCID需求表的状态位，保证所有需求表都有状态位字段
 * @Class Name ServiceItemUserTableStatus
 * @Author sa
 * @Create In 2009-2-25
 */
public class ServiceItemUserTableStatus extends BaseObject{
	private Long id;

	private ServiceItemUserTable scidTable;
	
	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Return the ServiceItemUserTable scidTable
	 */
	public ServiceItemUserTable getScidTable() {
		return scidTable;
	}
	/**
	 * @Param ServiceItemUserTable scidTable to set
	 */
	public void setScidTable(ServiceItemUserTable scidTable) {
		this.scidTable = scidTable;
	}
	/**
	 * @Return the SystemMainTable userTable
	 */
	public SystemMainTable getUserTable() {
		return userTable;
	}
	/**
	 * @Param SystemMainTable userTable to set
	 */
	public void setUserTable(SystemMainTable userTable) {
		this.userTable = userTable;
	}
	/**
	 * @Return the Long userTableId
	 */
	public Long getUserTableId() {
		return userTableId;
	}
	/**
	 * @Param Long userTableId to set
	 */
	public void setUserTableId(Long userTableId) {
		this.userTableId = userTableId;
	}
	/**
	 * @Return the Integer sidProcessType
	 */
	public Integer getSidProcessType() {
		return sidProcessType;
	}
	/**
	 * @Param Integer sidProcessType to set
	 */
	public void setSidProcessType(Integer sidProcessType) {
		this.sidProcessType = sidProcessType;
	}
	/**
	 * @Return the Integer status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @Param Integer status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	private SystemMainTable userTable;
	
	private Long userTableId;
	private Integer sidProcessType;
	private Integer status;
	
	
}
