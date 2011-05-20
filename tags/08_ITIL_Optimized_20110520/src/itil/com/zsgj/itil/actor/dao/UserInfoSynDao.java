package com.zsgj.itil.actor.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ͬ����Ա��ϢDAO
 * @Class Name UserInfoSynDao
 * @Author lee
 * @Create In Jun 23, 2010
 */
public interface UserInfoSynDao {
	/**
	 * ����ƽ̨��Ϣ
	 * @Methods Name updatePlatform
	 * @Create In Jun 22, 2010 By lee
	 * @param personnelScopeMap void
	 */
	public void updatePlatform(Map<String,String> platformMap);
	/**
	 * ���������ӷ�Χ��
	 * @Methods Name updatePersonnelScope
	 * @Create In Jun 22, 2010 By lee
	 * @param personnelScopeMap void
	 */
	public void updatePersonnelScope(Map<String,String> personnelScopeMap);
	/**
	 * ������Ա�������
	 * @Methods Name updateUserType
	 * @Create In Jun 22, 2010 By lee
	 * @param userTypes void
	 */
	public void updateUserType(Set<String> userTypes);
	/**
	 * ���²���
	 * @Methods Name updateDeptment
	 * @Create In Jun 23, 2010 By lee
	 * @param deptments void
	 */
	public void updateDeptment(List<Hashtable> deptments);
	/**
	 * ͬ����Ա��Ϣ
	 * @Methods Name updateUserInfo
	 * @Create In Jun 23, 2010 By lee
	 * @param users void
	 */
	public void updateUserInfo(List<Hashtable> users);
}
