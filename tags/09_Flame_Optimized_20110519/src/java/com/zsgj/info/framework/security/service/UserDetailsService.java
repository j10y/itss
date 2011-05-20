/**
 * @Probject Name: 10_InfoFramework_B2
 * @Path: com.zsgj.info.framework.security.serviceUserDetailsService.java
 * @Create By zhangpeng
 * @Create In 2008-5-9 ����05:15:05
 * TODO
 */
package com.zsgj.info.framework.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

/**
 * @Class Name UserDetailsService
 * @Author zhangpeng
 * @Create In 2008-5-9
 */
public interface UserDetailsService extends
	org.springframework.security.core.userdetails.UserDetailsService {
		
	  public abstract UserDetails loadUserByUsername(String userName, boolean isSystemAdmin) throws UsernameNotFoundException, DataAccessException;
}
