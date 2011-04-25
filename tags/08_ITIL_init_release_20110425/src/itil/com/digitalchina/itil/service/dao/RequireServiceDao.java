package com.digitalchina.itil.service.dao;

import java.util.List;

import com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis;

public interface RequireServiceDao {

	/**
	 * 获取历史表中的信息
	 * @Methods Name findServiceItemApplyAuditHis
	 * @Create In 28 01, 2009 By zhangzy
	 * @param dataId
	 * @param processId
	 * @return List<ServiceItemApplyAuditHis>
	 */
	public List<ServiceItemApplyAuditHis> findServiceItemApplyAuditHis(String dataId,
			String processId);
	/**
	 * 更新RequireApplyDefaultAudit实体中的deleteFlag字段，用户逻辑删除
	 * @Methods Name updateDeleteFlag
	 * @Create In 02 02, 2010 By zhangzy
	 * @param id
	 * @return boolean
	 */
	public boolean updateDeleteFlag(String id);
}
