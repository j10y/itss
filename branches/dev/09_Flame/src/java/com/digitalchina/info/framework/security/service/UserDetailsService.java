/**
 * @Probject Name: 10_InfoFramework_B2
 * @Path: com.digitalchina.info.framework.security.serviceUserDetailsService.java
 * @Create By zhangpeng
 * @Create In 2008-5-9 ÏÂÎç05:15:05
 * TODO
 */
package com.digitalchina.info.framework.security.service;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

/**
 * @Class Name UserDetailsService
 * @Author zhangpeng
 * @Create In 2008-5-9
 */
public interface UserDetailsService extends
		org.acegisecurity.userdetails.UserDetailsService {
		
	  public abstract UserDetails loadUserByUsername(String userName, boolean isSystemAdmin) throws UsernameNotFoundException, DataAccessException;
}
