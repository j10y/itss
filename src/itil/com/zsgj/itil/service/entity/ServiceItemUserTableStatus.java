package com.zsgj.itil.service.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * SCID������״̬λ����֤�����������״̬λ�ֶ�
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
