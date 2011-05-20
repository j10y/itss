package com.zsgj.info.framework.security.service.impl;

import java.util.Collection;

import javax.naming.directory.Attributes;
import javax.naming.ldap.Control;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

public class UserDetailsLdapImpl implements LdapUserDetails {


	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -470187330939532374L;

	public Attributes getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Control[] getControls() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDn() {
		// TODO Auto-generated method stub
		return null;
	}

	//modify by awen for change returnType from GrantedAuthority[] to Collection<GrantedAuthority> because acegi to security3
	public Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	//modify by awen for change acegi to security3
	
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
