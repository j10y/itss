package com.digitalchina.info.appframework.template.dao;

import java.util.List;

import com.digitalchina.info.appframework.template.entity.UserMenuSetting;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * �û��˵��趨Dao
 * @Class Name UserMenuDao
 * @Author peixf
 * @Create In 2008-4-3
 */
public interface UserMenuDao {
	
	/**
	 * �����û��˵��趨�����޸�UserMenuSetting�б�������ɼ��Ժ��ύ����
	 * action��actionͨ��List selectUserMenuSettingsByUser(UserInfo userInfo)������ȡ
	 * ������UserMenuSetting�б��Ա�request�в�����һ�޸�
	 * @Methods Name insertOrUpdateUserMenuSetting
	 * @Create In 2008-4-3 By peixf
	 * @param ums
	 * @return UserMenuSetting
	 */
	UserMenuSetting insertOrUpdateUserMenuSetting(UserMenuSetting ums);
	
	/**
	 * ��ȡ�û����еĲ˵��趨
	 * @Methods Name selectUserMenuSettingsByUser
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo
	 * @return List
	 */
	List selectUserMenuSettingsByUser(UserInfo userInfo);
	
	/**
	 * ���޸��û��˵��趨��order������Ժ󣬽�����UserMenuSetting�������õ�userinfo��
	 * ����userInfo����������UserMenuSetting����
	 * @Methods Name updateUserMenuSettings
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo
	 * @return List
	 */
	List updateUserMenuSettings(UserInfo userInfo);
	
	/**
	 * Ϊ���в˵���ʼ���û��˵��趨�������û�
	 * @Methods Name insertAllModulesToUserMenuSetting
	 * @Create In 2008-4-3 By peixf void
	 */
	void insertAllModulesToUserMenuSetting();
	
	/**
	 * Ϊ���в˵���ʼ���û��˵��趨��ָ���û�
	 * @Methods Name insertAllModulesToUserMenuSettingByUser
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo void
	 */
	void insertAllModulesToUserMenuSettingByUser(UserInfo userInfo);
	
	/**
	 * ������ģ��ʱ��Ϊ�����û���ʼ���û��˵��趨����
	 * @Methods Name insertNewModulesToUserMenuSetting
	 * @Create In 2008-4-3 By peixf void
	 */
	void insertNewModulesToUserMenuSetting(Module module);
	
	/**
	 * ɾ��ָ��ģ�������UserMenuSetting������ʱ����ģ��ɾ��ʱ
	 * @Methods Name deleteUserMenuSettingByModule
	 * @Create In 2008-4-3 By peixf
	 * @param module void
	 */
	void deleteUserMenuSettingByModule(Module module);
	
	
}
