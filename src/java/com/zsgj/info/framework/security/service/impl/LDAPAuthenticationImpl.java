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
 * LDAP��֤����
 * 
 * @Class Name LDAPAuthentication
 * @Author zhangpeng
 * @Create In 2007-11-27
 */
public class LDAPAuthenticationImpl extends BaseService implements
		LDAPAuthenticationService {
	// Ĭ�ϵ�Ldap��ַ
	private static final String DEFAULT_LDAP_ADDRESS = "LDAP://10.1.120.251";

	// Ĭ�ϵ�WebService��ַ
	private static final String DEFAUL_SERVICE_ADDRESS = "http://10.1.180.131/billweb/authservice.asmx?WSDL";

	//�µ���֤��ʽ
	private static final String ALLOW_LDAP_ADDRESS = "LDAP://ldap.zsgj.com"; 
	
	private static final int ALLOW_LDAP_PORT = 389; 
	/**
	 * ���캯��
	 */
	public LDAPAuthenticationImpl() {
	}

	/**
	 * ��Ч����֤
	 * 
	 * @param username
	 *            �û���
	 * @param pwd
	 *            ����
	 * @return �ɹ�����true
	 */
	public boolean IsAuthenticated(String username, String pwd) {
		if (!Authenticate(null, username, pwd)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ��Ч����֤
	 * 
	 * @param domain
	 *            ����
	 * @param username
	 *            �û���
	 * @param pwd
	 *            ����
	 * @return �ɹ�����true
	 */
	public boolean IsAuthenticated(String domain, String username, String pwd) {
		if (!Authenticate(domain, username, pwd)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ��Ч����֤
	 * 
	 * @param username
	 *            �û���
	 * @param pwd
	 *            ����
	 * @return �ɹ�����true
	 */
	public boolean IsAuthenticatedByLdap(String username, String pwd) {
		if (!AuthenticateByLdap(null, username, pwd)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * ��Ч����֤
	 * 
	 * @param domain
	 *            ����
	 * @param username
	 *            �û���
	 * @param pwd
	 *            ����
	 * @return �ɹ�����true
	 */
	public boolean IsAuthenticatedByLdap(String domain, String username, String pwd) {
		if (!AuthenticateByLdap(domain, username, pwd)) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * ��Ч����֤
	 * 
	 * @param domain
	 *            ����
	 * @param username
	 *            �û���
	 * @param pwd
	 *            ����
	 * @return �ɹ�����true
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
	 * Web Servers��Ч����֤
	 * 
	 * @param UserName
	 *            �û���
	 * @param Pwd
	 *            ����
	 * @return �ɹ�����true
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