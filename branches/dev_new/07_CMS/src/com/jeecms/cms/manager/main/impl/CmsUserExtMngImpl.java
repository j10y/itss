package com.jeecms.cms.manager.main.impl;

import net.shopin.ldap.ws.client.SystemWSImpl;
import net.shopin.ldap.ws.client.SystemWSImplService;
import net.shopin.ldap.ws.client.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.dao.main.CmsUserExtDao;
import com.jeecms.cms.entity.main.CmsUser;
import com.jeecms.cms.entity.main.CmsUserExt;
import com.jeecms.cms.manager.main.CmsUserExtMng;
import com.jeecms.common.hibernate3.Updater;

@Service
@Transactional
public class CmsUserExtMngImpl implements CmsUserExtMng {
	public CmsUserExt save(CmsUserExt ext, CmsUser user) {
		ext.blankToNull();
		ext.setUser(user);
		dao.save(ext);
		return ext;
	}

	public CmsUserExt update(CmsUserExt ext, CmsUser user) {
		CmsUserExt entity = dao.findById(user.getId());
		if (entity == null) {
			entity = save(ext, user);
			user.getUserExtSet().add(entity);
			updateLdap(user);
			return entity;
		} else {
			Updater<CmsUserExt> updater = new Updater<CmsUserExt>(ext);
			updater.include("gender");
			updater.include("birthday");
			ext = dao.updateByUpdater(updater);
			ext.blankToNull();
			updateLdap(user);
			return ext;
		}
	}
	
	private void updateLdap(CmsUser user){
		try{
			SystemWSImpl port = SystemWSImplService.getPort("http://172.16.100.173:8081/uac/services/sysService?wsdl");
			User ldapUser = port.getUserDetailByUid(user.getUsername());
			ldapUser.setDisplayName(user.getRealname());
			ldapUser.setTelephoneNumber(user.getPhone());
			ldapUser.setMobile(user.getMoble());
			ldapUser.setDescription(user.getIntro());
			
			port.updateUser(ldapUser);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private CmsUserExtDao dao;

	@Autowired
	public void setDao(CmsUserExtDao dao) {
		this.dao = dao;
	}
}