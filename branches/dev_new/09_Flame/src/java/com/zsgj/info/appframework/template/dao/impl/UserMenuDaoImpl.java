package com.zsgj.info.appframework.template.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.template.dao.UserMenuDao;
import com.zsgj.info.appframework.template.entity.UserMenuSetting;
import com.zsgj.info.framework.orm.BaseDao;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.UserInfo;


public class UserMenuDaoImpl extends BaseDao implements UserMenuDao {

	public void insertAllModulesToUserMenuSetting() {
		DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
		dc.add(Restrictions.eq("enabled", new Integer(1)));
		List usersAll = getDaoSupport().selectByCriteria(dc);
		
		List modulesAll = getDaoSupport().selectAll(Module.class);
		for(int i=0; i<modulesAll.size(); i++){
//			Module module = (Module) modulesAll.get(i);
			for(int j=0; j<usersAll.size(); j++){
				UserInfo userInfo = (UserInfo) usersAll.get(j);
				UserMenuSetting ums = new UserMenuSetting();
				//ums.setEnabled(new Integer(1));
				ums.setMenuOrder(new Integer(i+1));
				ums.setUserInfo(userInfo);
				getDaoSupport().insertOrUpdate(ums);
				getDaoSupport().evict(ums);
			}
		}
		
		
	}

	public void insertAllModulesToUserMenuSettingByUser(UserInfo userInfo) {	
		List modulesAll = getDaoSupport().selectAll(Module.class);
		for(int i=0; i<modulesAll.size(); i++){
//			Module module = (Module) modulesAll.get(i);
			UserMenuSetting ums = new UserMenuSetting();
			///ums.setEnabled(new Integer(1));
			ums.setMenuOrder(new Integer(i+1));
			ums.setUserInfo(userInfo);
			getDaoSupport().insertOrUpdate(ums);
			getDaoSupport().evict(ums);
		}
	}

	public UserMenuSetting insertOrUpdateUserMenuSetting(UserMenuSetting ums) {
		UserMenuSetting result = null;
		result = (UserMenuSetting) getDaoSupport().insertOrUpdate(ums);
		return result;
	}

	public List selectUserMenuSettingsByUser(UserInfo userInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(UserMenuSetting.class);
		dc.add(Restrictions.eq("userInfo", userInfo));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}

	public List updateUserMenuSettings(UserInfo userInfo) {
		/*List list = new ArrayList();
		getDaoSupport().update(userInfo);
		getDaoSupport().flush();
		Set umss = userInfo.getUserMenuSettings();
		list.addAll(umss);
		return list;*/
		return null;
	}

	public void deleteUserMenuSettingByModule(Module module) {
		getDaoSupport().executeUpdate("delete from UserMenuSetting ums where ums.module=?", new Object[]{module});
		
	}

	public void insertNewModulesToUserMenuSetting(Module module){
		DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
		dc.add(Restrictions.eq("enabled", new Integer(1)));
		List usersAll = getDaoSupport().selectByCriteria(dc);
		for(int j=0; j<usersAll.size(); j++){
			UserInfo userInfo = (UserInfo) usersAll.get(j);
//			--获取当前用户模块的最大order
			DetachedCriteria dcMaxOrder = DetachedCriteria.forClass(UserMenuSetting.class);
			dcMaxOrder.add(Restrictions.eq("userInfo", userInfo));
			dcMaxOrder.setProjection(Projections.projectionList()
					.add(Projections.max("order").as("maxOrder"))
			);
			Object result = getDaoSupport().uniqueResult(dcMaxOrder);
			int orderMax = 0; 
			if(result!=null){
				orderMax = ((Integer)result).intValue();
			}
//			--保存新模块设定
			UserMenuSetting ums = new UserMenuSetting();
			///ums.setEnabled(new Integer(1));
			ums.setMenuOrder(new Integer(orderMax+1));
			ums.setUserInfo(userInfo);
			getDaoSupport().insertOrUpdate(ums);
			getDaoSupport().evict(ums);
		}
		
	}

}
