package com.zsgj.itil.account.dao;

import com.digitalchina.info.framework.exception.DaoException;

public interface PersonnelScopeSynDao {
	/**
	 * 同步码表。
	 * @Methods Name saveOrUpdatePersonnelScope
	 * @Create In Nov 10, 2009 By duxh
	 * @throws DaoException
	 */
	public void saveOrUpdatePersonnelScope() throws DaoException;
	/**
	 * 同步sUserInfos的personnelScope字段
	 * @Methods Name updateUserInfo
	 * @Create In Nov 10, 2009 By duxh
	 * @throws DaoException
	 */
	public void updateUserInfo() throws DaoException;
	
}
