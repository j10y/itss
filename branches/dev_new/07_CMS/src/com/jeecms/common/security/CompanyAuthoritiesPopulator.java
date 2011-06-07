package com.jeecms.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import com.jeecms.cms.entity.main.CmsRole;
import com.jeecms.cms.entity.main.CmsUser;
import com.jeecms.cms.manager.main.CmsUserMng;

public class CompanyAuthoritiesPopulator implements LdapAuthoritiesPopulator {
	@Autowired
	private CmsUserMng cmsUserMng;
	
	/**
	 * @Return the CmsUserMng cmsUserMng
	 */
	public CmsUserMng getCmsUserMng() {
		return cmsUserMng;
	}

	/**
	 * @Param CmsUserMng cmsUserMng to set
	 */
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	public Collection<GrantedAuthority> getGrantedAuthorities(
			DirContextOperations userData, String username) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		CmsUser user = cmsUserMng.findByUsername(username);
		Iterator<CmsRole> roles = user.getRoles().iterator();
		
		while(roles.hasNext()){
			CmsRole item = (CmsRole)roles.next();
			authorities.add(new GrantedAuthorityImpl(item.getId().toString()));
		}
		return authorities;
	}

}
