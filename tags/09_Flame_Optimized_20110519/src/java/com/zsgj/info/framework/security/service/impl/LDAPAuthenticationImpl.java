package com.zsgj.info.framework.security.service.impl;

import java.net.URL;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.service.LDAPAuthenticationService;
import com.zsgj.info.framework.service.BaseService;

/**
 * LDAP验证服务
 * 
 * @Class Name LDAPAuthentication
 * @Author zhangpeng
 * @Create In 2007-11-27
 */
public class LDAPAuthenticationImpl extends BaseService implements
		LDAPAuthenticationService {
	// 默认的Ldap地址
	private static final String DEFAULT_LDAP_ADDRESS = "LDAP://10.1.120.251";

	// 默认的WebService地址
	private static final String DEFAUL_SERVICE_ADDRESS = "http://10.1.180.131/billweb/authservice.asmx?WSDL";

	//新的验证方式
	private static final String ALLOW_LDAP_ADDRESS = "LDAP://ldap.zsgj.com"; 
	
	private static final int ALLOW_LDAP_PORT = 389; 
	/**
	 * 构造函数
	 */
	public LDAPAuthenticationImpl() {
	}

	/**
	 * 有效性验证
	 * 
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticated(String username, String pwd) {
		if (!Authenticate(null, username, pwd)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 有效性验证
	 * 
	 * @param domain
	 *            域名
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticated(String domain, String username, String pwd) {
		if (!Authenticate(domain, username, pwd)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 有效性验证
	 * 
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticatedByLdap(String username, String pwd) {
		if (!AuthenticateByLdap(null, username, pwd)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 有效性验证
	 * 
	 * @param domain
	 *            域名
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticatedByLdap(String domain, String username, String pwd) {
		if (!AuthenticateByLdap(domain, username, pwd)) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 有效性验证
	 * 
	 * @param domain
	 *            域名
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 成功返回true
	 */
	private boolean Authenticate(String domain, String username, String pwd) {

		DirContext oDirContext = null;
		Properties oProperties = new Properties();
		boolean bResult = false;
		String sFullUserName = "";

		if (domain != null && !domain.equals("")) {
			sFullUserName = domain + "\\" + username;
		} else {
			sFullUserName = username;
		}

		oProperties.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		oProperties.put(Context.PROVIDER_URL, this.getProperties(
				"system.security.Ldap.Path", DEFAULT_LDAP_ADDRESS));
		oProperties.put(Context.SECURITY_AUTHENTICATION, "simple");
		oProperties.put(Context.SECURITY_PRINCIPAL, "cn=" + sFullUserName);
		oProperties.put(Context.SECURITY_CREDENTIALS, pwd);

		try {
			oDirContext = new InitialDirContext(oProperties);
			bResult = true;
		} catch (Exception ex) {
			System.err.println(ex);
			logger.error(ex.getMessage());
		} finally {
			try {
				if(oDirContext != null){
					oDirContext.close();
				}
			} catch (Exception ex) {
				System.err.println(ex);
				logger.error(ex.getMessage());
			}
		}
		return bResult;
	}
	
	private boolean AuthenticateByLdap(String domain, String username, String pwd) {

		DirContext oDirContext = null;
		Properties oProperties = new Properties();
		boolean bResult = false;
		String sFullUserName = "";
		String DN = "cn=employees,o=dc";
		
		if (domain != null && !domain.equals("")) {
			sFullUserName = domain + "\\" + username.trim();
		} else {
			sFullUserName = username.trim();
		}
		DN = "uid=" + sFullUserName + "," + DN;

		oProperties.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		oProperties.put(Context.PROVIDER_URL, this.getProperties(
				"system.security.Ldap.Path.notNotes", ALLOW_LDAP_ADDRESS) + ":" + Integer.valueOf(this.getProperties(
						"system.security.Ldap.Path.notNotesPort", String.valueOf(ALLOW_LDAP_PORT))).intValue());
		oProperties.put(Context.SECURITY_AUTHENTICATION, "simple");
		oProperties.put(Context.SECURITY_PRINCIPAL, DN);
		oProperties.put(Context.SECURITY_CREDENTIALS, pwd.trim());

		try {
			oDirContext = new InitialDirContext(oProperties);
			bResult = true;
		} catch (Exception ex) {
			System.err.println(ex);
			logger.error(ex.getMessage());
		} finally {
			try {
				if(oDirContext != null){
					oDirContext.close();
				}
			} catch (Exception ex) {
				System.err.println(ex);
				logger.error(ex.getMessage());
			}
		}
		return bResult;
	}
	
	/**
	 * Web Servers有效性验证
	 * 
	 * @param UserName
	 *            用户名
	 * @param Pwd
	 *            密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticatedByWebServer(String UserName, String Pwd) {
		boolean bIsLogin = false;
		String endpoint = getProperties("system.Ldap.ServicePath", this
				.getProperties("system.security.Ldap.ServicePath",
						DEFAUL_SERVICE_ADDRESS));

		Service service = new Service();
		Call call;

		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(new URL(endpoint));
			// call.setOperationName(new
			// QName("http://www.zjh.com/SU","IsAuthenticated"));
			call.setOperationName(new QName("AuthUser"));

			call.addParameter("username", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("pwd", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_BOOLEAN);

			call.setUseSOAPAction(true);
			// call.setSOAPActionURI("http://www.zjh.com/Rpc");

			bIsLogin = Boolean.valueOf(
					(String) call.invoke(new Object[] { UserName, Pwd }))
					.booleanValue();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ServiceException(Long.valueOf("10000110106").longValue());
		}
		return bIsLogin;
	}

}