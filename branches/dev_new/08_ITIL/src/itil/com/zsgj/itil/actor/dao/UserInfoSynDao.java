package com.zsgj.itil.actor.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 同步人员信息DAO
 * @Class Name UserInfoSynDao
 * @Author lee
 * @Create In Jun 23, 2010
 */
public interface UserInfoSynDao {
	/**
	 * 更新平台信息
	 * @Methods Name updatePlatform
	 * @Create In Jun 22, 2010 By lee
	 * @param personnelScopeMap void
	 */
	public void updatePlatform(Map<String,String> platformMap);
	/**
	 * 更新人事子范围表
	 * @Methods Name updatePersonnelScope
	 * @Create In Jun 22, 2010 By lee
	 * @param personnelScopeMap void
	 */
	public void updatePersonnelScope(Map<String,String> personnelScopeMap);
	/**
	 * 更新人员类型码表
	 * @Methods Name updateUserType
	 * @Create In Jun 22, 2010 By lee
	 * @param userTypes void
	 */
	public void updateUserType(Set<String> userTypes);
	/**
	 * 更新部门
	 * @Methods Name updateDeptment
	 * @Create In Jun 23, 2010 By lee
	 * @param deptments void
	 */
	public void updateDeptment(List<Hashtable> deptments);
	/**
	 * 同步人员信息
	 * @Methods Name updateUserInfo
	 * @Create In Jun 23, 2010 By lee
	 * @param users void
	 */
	public void updateUserInfo(List<Hashtable> users);
}
