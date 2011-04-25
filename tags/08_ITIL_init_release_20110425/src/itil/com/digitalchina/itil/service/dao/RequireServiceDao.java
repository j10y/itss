package com.digitalchina.itil.service.dao;

import java.util.List;

import com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis;

public interface RequireServiceDao {

	/**
	 * ��ȡ��ʷ���е���Ϣ
	 * @Methods Name findServiceItemApplyAuditHis
	 * @Create In 28 01, 2009 By zhangzy
	 * @param dataId
	 * @param processId
	 * @return List<ServiceItemApplyAuditHis>
	 */
	public List<ServiceItemApplyAuditHis> findServiceItemApplyAuditHis(String dataId,
			String processId);
	/**
	 * ����RequireApplyDefaultAuditʵ���е�deleteFlag�ֶΣ��û��߼�ɾ��
	 * @Methods Name updateDeleteFlag
	 * @Create In 02 02, 2010 By zhangzy
	 * @param id
	 * @return boolean
	 */
	public boolean updateDeleteFlag(String id);
}
