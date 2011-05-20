package com.zsgj.info.appframework.template.service;

import java.util.List;

import com.zsgj.info.appframework.template.entity.UserMenuSetting;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 *  @deprecated
 * @Class Name UserMenuService
 * @Author sa
 * @Create In 2008-8-29
 */
public interface UserMenuService {


	/**
	 * �����û��˵��趨�����޸�UserMenuSetting�б�������ɼ��Ժ��ύ����
	 * action��actionͨ��List selectUserMenuSettingsByUser(UserInfo userInfo)������ȡ
	 * ������UserMenuSetting�б��Ա�request�в�����һ�޸�
	 * @Methods Name insertOrUpdateUserMenuSetting
	 * @Create In 2008-4-3 By peixf
	 * @param ums
	 * @return UserMenuSetting
	 */
	UserMenuSetting saveUserMenuSetting(UserMenuSetting ums);
	
	/**
	 * ��ȡ�û����еĲ˵��趨
	 * @Methods Name findUserMenuSettingsByUser
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo
	 * @return List
	 */
	List findUserMenuSettingsByUser(UserInfo userInfo);
	
	
	/**
	 * ���޸��û��˵��趨��order������Ժ󣬽�����UserMenuSetting�������õ�userinfo��
	 * ����userInfo����������UserMenuSetting���ϣ��˷�����Ʋ���
	 * @Methods Name updateUserMenuSettings
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo
	 * @return List
	 */
	List saveUserMenuSettings(UserInfo userInfo);
	
	/**
	 * Ϊ���в˵���ʼ���û��˵��趨�������û�������
	 * @Methods Name insertAllModulesToUserMenuSetting
	 * @Create In 2008-4-3 By peixf void
	 */
	void saveAllModulesToUserMenuSetting();
	
	/**
	 * Ϊ���в˵���ʼ���û��˵��趨��ָ���û�
	 * @Methods Name insertAllModulesToUserMenuSettingByUser
	 * @Create In 2008-4-3 By peixf
	 * @param userInfo void
	 */
	void saveAllModulesToUserMenuSettingByUser(UserInfo userInfo);
	
	/**
	 * ������ģ��ʱ��Ϊ�����û���ʼ���û��˵��趨����
	 * @Methods Name insertNewModulesToUserMenuSetting
	 * @Create In 2008-4-3 By peixf void
	 */
	void saveNewModulesToUserMenuSetting(Module module);
	
	/**
	 * ɾ��ָ��ģ�������UserMenuSetting������ʱ����ģ��ɾ��ʱ
	 * @Methods Name deleteUserMenuSettingByModule
	 * @Create In 2008-4-3 By peixf
	 * @param module void
	 */
	void removeUserMenuSettingByModule(Module module);
	
}
