package com.digitalchina.itil.service.entity;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * SCID需求表的状态位，保证所有需求表都有状态位字段
 * @Class Name ServiceItemUserTableStatus
 * @Author sa
 * @Create In 2009-2-25
 */
public class ServiceItemUserTableStatus extends BaseObject{
	private Long id;

	private ServiceItemUserTable scidTable;
	
	private SystemMainTable userTable;
	
	private Long userTableId;
	private Integer sidProcessType;
	private Integer status;
	
	
}
