/**
 * @Probject Name: 10_InfoFramework_B2
 * @Path: com.digitalchina.info.framework.security.service.implLdapDaoAuthenticationProvider.java
 * @Create By zhangpeng
 * @Create In 2008-5-9 ����04:18:21
 * TODO
 */
package com.digitalchina.info.framework.security.service.impl;

import org.acegisecurity.AccountExpiredException;
import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationServiceException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.CredentialsExpiredException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.LockedException;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.acegisecurity.providers.dao.SaltSource;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.acegisecurity.providers.encoding.PlaintextPasswordEncoder;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.security.entity.SecurityMessageInfo;
import com.digitalchina.info.framework.security.entity.UserDetails;
import com.digitalchina.info.framework.security.service.AuthenticationCust;
import com.digitalchina.info.framework.security.service.LDAPAuthenticationService;
import com.digitalchina.info.framework.security.service.SecurityMessageService;
import com.digitalchina.info.framework.security.service.UserDetailsService;
import com.digitalchina.info.framework.security.service.UsernamePasswordAuthenticationTokenCust;

/**
 * @Class Name LdapDaoAuthenticationProvider
 * @Author zhangpeng
 * @Create In 2008-5-9
 */
public class LdapDaoAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider {

	// add by zhangpeng for acegisecurity modify in 2008-05-09
	// �޸��û���½��֤��ϵͳȨ����֤��ʽ,����DC��Ldap��֤��ʽ
	private String isSystemAdmin;
	private String isUserAdmin;
	private String isLdap = "false";
	private boolean isVailedSystemAdmin = false;

	// �õ��ķ���,�������ļ�ע��
	private LDAPAuthenticationService ldap;
	private SecurityMessageService messages;

	// DaoAuthenticationProvider��Ĭ�ϵ�
	private UserDetailsService userDetailsService;
	private PasswordEncoder passwordEncoder = new PlaintextPasswordEncoder();
	private SaltSource saltSource;

	/**
	 * @Return the String isSystemAdmin
	 */
	public String getIsSystemAdmin() {
		return isSystemAdmin;
	}

	/**
	 * @Param String isSystemAdmin to set
	 */
	public void setIsSystemAdmin(String isSystemAdmin) {
		this.isSystemAdmin = isSystemAdmin;
	}

	/**
	 * @Return the String isUserAdmin
	 */
	public String getIsUserAdmin() {
		return isUserAdmin;
	}

	/**
	 * @Param String isUserAdmin to set
	 */
	public void setIsUserAdmin(String isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class,
				authentication, messages.getMessage(
						"system.security.onlySupports", "ֻ���û���������֤���Ʊ�֧��"));
		SecurityMessageInfo smi = new SecurityMessageInfo();
		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();

		boolean cacheWasUsed = true;
		UserDetails user = (UserDetails) super.getUserCache().getUserFromCache(
				username);
		System.out.println("The UserCache was get UserInf :" + user);

		if (user == null) {
			cacheWasUsed = false;

			try {
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);

			} catch (UsernameNotFoundException notFound) {
				if (hideUserNotFoundExceptions) {

					String msg = messages.getMessage(
							"system.security.badcredentials",
							"�û���֤�����û������ڻ��������");
					smi.setMessage(msg);
					UserContext.setLoginMessage("loginerror", smi);
					throw new BadCredentialsException(msg);

				} else {
					String msg = messages
							.getMessage("system.security.badUserName",
									"�û������ڣ�����ϵ����Ա�������û���");
					System.out.println(msg);
					UserContext.setLoginMessage("loginerror", smi);

					smi.setMessage(msg);
					throw notFound;
				}
			}

			Assert
					.notNull(user,
							"retrieveUser returned null - a violation of the interface contract");
		}

		if (!user.isAccountNonLocked()) {
			String msg = messages.getMessage("system.security.locked",
					"��¼�û��Ѿ���������");
			smi.setMessage(msg);
			UserContext.setLoginMessage("loginerror", smi);
			throw new LockedException(msg);
		}

		if (!user.isEnabled()) {
			String msg = messages.getMessage("system.security.disabled",
					"��¼�û��Ѿ�����ֹʹ�ã�");
			smi.setMessage(msg);
			UserContext.setLoginMessage("loginerror", smi);
			throw new DisabledException(msg);
		}

		if (!user.isAccountNonExpired()) {
			String msg = messages.getMessage("system.security.expired",
					"��½�û��ʻ��Ѿ����ڣ�");
			smi.setMessage(msg);
			UserContext.setLoginMessage("loginerror", smi);
			throw new AccountExpiredException(msg);
		}

		if (!user.isCredentialsNonExpired()) {
			String msg = messages.getMessage(
					"system.security.credentialsExpired", "��½�û��������Ѿ����ڣ�");
			smi.setMessage(msg);
			UserContext.setLoginMessage("loginerror", smi);
			throw new CredentialsExpiredException(msg);
		}

		// ��֤�û��Ƿ����Ȩ��!!!!
		// modify by zhangpeng for acegi used cache in 20081010 begin
		additionalAuthenticationChecks(user,
				(UsernamePasswordAuthenticationToken) authentication);
		// try {
		// additionalAuthenticationChecks(user,
		// (UsernamePasswordAuthenticationToken) authentication);
		// if (this.isVailedSystemAdmin) {
		// user = retrieveUser(username,
		// (UsernamePasswordAuthenticationToken) authentication);
		// }
		// modify by zhangpeng for acegi used cache in 20081010 end
		// } catch (AuthenticationException exception) {
		// // There was a problem, so try again after checking we're using
		// // latest data
		// System.out.println("authentication exception!");
		// cacheWasUsed = false;
		// user = retrieveUser(username,
		// (UsernamePasswordAuthenticationToken) authentication);
		// additionalAuthenticationChecks(user,
		// (UsernamePasswordAuthenticationToken) authentication);
		// }

		if (!cacheWasUsed) {
			getUserCache().putUserInCache(user);
		}

		Object principalToReturn = user;

		if (super.isForcePrincipalAsString()) {
			principalToReturn = user.getUsername();
		}

		this.isVailedSystemAdmin = false;
		return createSuccessAuthentication(principalToReturn, authentication,
				user);
	}

	/**
	 * ������֤�û���½�ķ���
	 */
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		this.isVailedSystemAdmin = this.isSystemAdmin(userDetails);
		SecurityMessageInfo smi = new SecurityMessageInfo();
		
		//add by zhangpengf for sso in 2009-12-15 begin
		if ("SP_SSO".equals(authentication.getCredentials().toString().trim())) {
			String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
					: authentication.getName();
			if("NONE_PROVIDED".equals(username)){
				String msg = messages.getMessage(
						"system.security.ssoExpired",
						"δ�ҵ����û��������Ƿ�������ȷ��");
				smi.setMessage(msg);
				UserContext.setLoginMessage("loginerror", smi);
				throw new BadCredentialsException(msg, userDetails);
			}else if (!username.trim().toLowerCase().equals(userDetails.getUsername().toLowerCase())){
				String msg = messages.getMessage(
						"system.security.ssoExpired",
						"δ�ҵ����û��������Ƿ�������ȷ��");
				smi.setMessage(msg);
				UserContext.setLoginMessage("loginerror", smi);
				throw new BadCredentialsException(msg, userDetails);
			}
		} else {
			if (userDetails.isSpecialUser()
					|| !Boolean.valueOf(this.isLdap).booleanValue()) {
				Object salt = null;
				if (this.saltSource != null) {
					salt = this.saltSource.getSalt(userDetails);
				}

				if (!passwordEncoder.isPasswordValid(userDetails.getPassword(),
						authentication.getCredentials().toString(), salt)) {
					String msg = messages.getMessage(
							"system.security.dbExpired",
							"��֤����������������û������������ڱ�ϵͳ���趨���û����Լ����룡");
					smi.setMessage(msg);
					UserContext.setLoginMessage("loginerror", smi);
					throw new BadCredentialsException(msg, userDetails);
				}
			} else if (Boolean.valueOf(this.isLdap).booleanValue()) {
				try {
					boolean isAuth = false;
					System.out.println("The User Check Type Is : "
							+ this.messages.getMessage(
									"system.security.authtype", "ldap"));

					if ("ldap".equalsIgnoreCase(this.messages.getMessage(
							"system.security.authtype", "ldap"))) {

						isAuth = ldap.IsAuthenticatedByLdap(authentication
								.getName().toLowerCase(), authentication
								.getCredentials().toString());
					} else if ("notes".equalsIgnoreCase(this.messages
							.getMessage("system.security.authtype", "ldap"))) {
						isAuth = ldap.IsAuthenticated(authentication.getName()
								.toLowerCase(), authentication.getCredentials()
								.toString());
					}
					if (!isAuth) {
						if (isVailedSystemAdmin) {
							Object salt = null;
							if (this.saltSource != null) {
								salt = this.saltSource.getSalt(userDetails);
							}

							if (!passwordEncoder.isPasswordValid(userDetails
									.getPassword(), authentication
									.getCredentials().toString(), salt)) {
								String msg = messages.getMessage(
										"system.security.dbExpired",
										"��֤����������������û������������ڱ�ϵͳ���趨���û����Լ����룡");
								smi.setMessage(msg);
								UserContext.setLoginMessage("loginerror", smi);
								throw new BadCredentialsException(msg,
										userDetails);
							}
						} else {
							String msg = messages.getMessage(
									"system.security.ldapExpired",
									"Ldap��֤����������������û�������������Notes�û����Լ����룡");
							smi.setMessage(msg);
							UserContext.setLoginMessage("loginerror", smi);
							throw new BadCredentialsException(msg, userDetails);
						}
					}
				} catch (Exception e) {
					String msg = messages
							.getMessage("system.security.unknowExpired",
									"��½��֤������������ϵ����Ա��");
					smi.setMessage(msg);
					UserContext.setLoginMessage("loginerror", smi);
					throw new BadCredentialsException(msg, userDetails);
				}
			}
		}
		//add by zhangpengf for sso in 2009-12-15 end
	}

	/**
	 * ��֤�Ƿ���ϵͳ����Ա
	 * 
	 * @Methods Name isSystemAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isSystemAdmin(UserDetails userDetails) {
		for (int i = 0; i < userDetails.getAuthorities().length; i++) {
			if (userDetails.getAuthorities()[i].equals(this.isSystemAdmin)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��֤�Ƿ����û�����Ա
	 * 
	 * @Methods Name isUserAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isUserAdmin(UserDetails userDetails) {
		for (int i = 0; i < userDetails.getAuthorities().length; i++) {
			if (userDetails.getAuthorities()[i].equals(this.isUserAdmin)) {
				return true;
			}
		}
		return false;
	}

	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userDetailsService,
				"An Authentication DAO must be set");
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public SaltSource getSaltSource() {
		return saltSource;
	}

	/*
	 * ��ȡ��½��Ա��Ϣ
	 * 
	 * @see
	 * org.acegisecurity.providers.dao.AbstractUserDetailsAuthenticationProvider
	 * #retrieveUser(java.lang.String,
	 * org.acegisecurity.providers.UsernamePasswordAuthenticationToken)
	 */
	protected final UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser;

		try {
			if (!isVailedSystemAdmin) {
				loadedUser = (UserDetails) this.userDetailsService
						.loadUserByUsername(username);
			} else {
				loadedUser = (UserDetails) this.userDetailsService
						.loadUserByUsername(username, isVailedSystemAdmin);
			}
		} catch (DataAccessException repositoryProblem) {
			throw new AuthenticationServiceException(repositoryProblem
					.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new AuthenticationServiceException(messages.getMessage(
					"system.security.badUserName", "�û������ڣ�����ϵ����Ա�������û���"));
		}

		return loadedUser;
	}

	public void setUserDetailsService(UserDetailsService authenticationDao) {
		this.userDetailsService = authenticationDao;
	}

	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate
	 * passwords. If not set, {@link PlaintextPasswordEncoder} will be used by
	 * default.
	 * 
	 * @param passwordEncoder
	 *            The passwordEncoder to use
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * The source of salts to use when decoding passwords. <code>null</code> is
	 * a valid value, meaning the <code>DaoAuthenticationProvider</code> will
	 * present <code>null</code> to the relevant <code>PasswordEncoder</code>.
	 * 
	 * @param saltSource
	 *            to use when attempting to decode passwords via the
	 *            <code>PasswordEncoder</code>
	 */
	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	/**
	 * @Return the LDAPAuthenticationService ldap
	 */
	public LDAPAuthenticationService getLdap() {
		return ldap;
	}

	/**
	 * @Param LDAPAuthenticationService ldap to set
	 */
	public void setLdap(LDAPAuthenticationService ldap) {
		this.ldap = ldap;
	}

	/**
	 * @Return the String isLdap
	 */
	public String getIsLdap() {
		return isLdap;
	}

	/**
	 * @Param String isLdap to set
	 */
	public void setIsLdap(String isLdap) {
		this.isLdap = isLdap;
	}

	/**
	 * @Return the SecurityMessageService messages
	 */
	public SecurityMessageService getMessages() {
		return messages;
	}

	/**
	 * @Param SecurityMessageService messages to set
	 */
	public void setMessages(SecurityMessageService messages) {
		this.messages = messages;
	}

	@Override
	protected void additionalAuthenticationChecks(
			org.acegisecurity.userdetails.UserDetails arg0,
			UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		additionalAuthenticationChecks((UserDetails) arg0, arg1);

	}

	protected AuthenticationCust createSuccessAuthentication(
			Object principalToReturn, Authentication authentication,
			UserDetails user) {
		UsernamePasswordAuthenticationTokenCust result = new UsernamePasswordAuthenticationTokenCust(
				principalToReturn, authentication.getCredentials(), user
						.getAuthorities());
		result.setDetails(authentication.getDetails());
		result.setCurrentUserInfo(user.getCurrentUserInfo());
		return result;
	}
}
