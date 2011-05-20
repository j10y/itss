package com.zsgj.info.framework.aop.interceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.ConfigAttributeEditor;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.core.GrantedAuthority;

import com.zsgj.info.framework.security.cache.AcegiCacheService;
import com.zsgj.info.framework.security.entity.ResourceDetail;

/**
 * DB 业务方法过滤器InvocationDefinitionSource 在resourceCache中保存每个方法对应的ResourceDetails
 * 
 * @author xiaofeng
 */
@SuppressWarnings("deprecation")
public class DBMethodDefinitionSource implements MethodSecurityMetadataSource/*MethodDefinitionSource*/ {
	private static final Log logger = LogFactory
			.getLog(DBMethodDefinitionSource.class);

	static {
		System.out.println("Method过滤器调用源加载完毕...");
	}

	private AcegiCacheService acegiCacheService;

	// ~ Methods
	// ========================================================================================================

//	public ConfigAttributeDefinition getAttributes(Object object) {
//		// Assert.notNull(object, "Object cannot be null");
//
//		if (object instanceof MethodInvocation) {
//			MethodInvocation miv = (MethodInvocation) object;
//			return this.lookupAttributes(miv.getThis().getClass(), miv
//					.getMethod());
//		}
//
//		if (object instanceof JoinPoint) {
//			JoinPoint jp = (JoinPoint) object;
//			Class targetClazz = jp.getTarget().getClass();
//			String targetMethodName = jp.getStaticPart().getSignature()
//					.getName();
//			Class[] types = ((CodeSignature) jp.getStaticPart().getSignature())
//					.getParameterTypes();
//
//			if (logger.isDebugEnabled()) {
//				logger.debug("Target Class: " + targetClazz);
//				logger.debug("Target Method Name: " + targetMethodName);
//
//				for (int i = 0; i < types.length; i++) {
//					logger.debug("Target Method Arg #" + i + ": " + types[i]);
//				}
//			}
//
//			try {
//				return this.lookupAttributes(targetClazz, targetClazz
//						.getMethod(targetMethodName, types));
//			} catch (NoSuchMethodException nsme) {
//				throw new IllegalArgumentException(
//						"Could not obtain target method from JoinPoint: " + jp/*
//																			 * ,
//																			 * nsme
//																			 */);
//			}
//		}
//
//		throw new IllegalArgumentException(
//				"Object must be a MethodInvocation or JoinPoint");
//	}

//	public boolean supports(Class clazz) {
//		return (MethodInvocation.class.isAssignableFrom(clazz) || JoinPoint.class
//				.isAssignableFrom(clazz));
//	}

	/**
	 * 从resourceCache中获取当前方法对应的ResourceDetails{@link ResourceDetails} 最后返回由Role
	 * Name 组装成的ConfigAttributeDefinition(@link ConfigAttributeDefinition)
	 * 
	 * @see org.acegisecurity.intercept.method.AbstractMethodDefinitionSource#lookupAttributes(java.lang.reflect.Method)
	 */
	//protected ConfigAttributeDefinition lookupAttributes(Class clszz, Method mi) {
	@SuppressWarnings({ "unchecked" })
	protected Collection<ConfigAttribute> lookupAttributes(Class clszz, Method mi) {

		if (!this.acegiCacheService.isInitializedResourceCache()) {
			this.acegiCacheService.initResourceCache();
			this.acegiCacheService.setInitializedResourceCache(true);
		}

		// 获取所有的function
		List methodStrings = this.acegiCacheService.getFunctions();

		Set auths = new HashSet();// 存权限的合集

		for (Iterator iter = methodStrings.iterator(); iter.hasNext();) {// 遍历系统的所有函数
			String methodString = (String) iter.next();
			if (isMatch(clszz, mi, methodString)) {
				ResourceDetail resourceDetails = this.acegiCacheService
						.getAuthorityFromCache(methodString);
				if (resourceDetails == null) {
					break;
				}
				GrantedAuthority[] authorities = resourceDetails
						.getAuthorities();
				if (authorities == null || authorities.length == 0) {
					break;
				}
				auths.addAll(Arrays.asList(authorities));
			}
		}

		if (auths.size() == 0)
			return null;

		ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
		String authoritiesStr = " ";

		for (Iterator iter = auths.iterator(); iter.hasNext();) {
			GrantedAuthority authority = (GrantedAuthority) iter.next();
			authoritiesStr += authority.getAuthority() + ",";
		}

		String authStr = authoritiesStr.substring(0,
				authoritiesStr.length() - 1);

		configAttrEditor.setAsText(authStr.trim());
		/*ConfigAttributeDefinition cad = (ConfigAttributeDefinition)configAttrEditor
				.getValue(); 
		return cad;*/
		return (Collection<ConfigAttribute>)configAttrEditor.getValue();

	}

	public Iterator getConfigAttributeDefinitions() {
		List list = new ArrayList();
		ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
		//String authoritiesStr = " ";

		configAttrEditor.setAsText("");
		configAttrEditor.setValue(null);

		return list.iterator();
	}

	/**
	 * Return if the given method name matches the mapped name. The default
	 * implementation checks for "xxx" and "xxx" matches.
	 */
	public static boolean isMatch(Class clszz, Method mi, String methodString) {
		//modify by zhangpengf for mail auth in 20090629 begin
		boolean isMatchClass = false, isMatchMethod = false;
		try {
			int lastDotIndex = methodString.lastIndexOf('.');
			String className = methodString.substring(0, lastDotIndex);
			String methodName = methodString.substring(lastDotIndex + 1);

			String trigetClassName = clszz.getName().substring(
					clszz.getName().lastIndexOf('.') + 1);
			// 判断类是否相等
			if (trigetClassName.equals(className)) {
				isMatchClass = true;
			}

			// 判断接口是否相等
			Class[] interfaces = clszz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				Class inf = interfaces[i];
				String trigetInterfaceName = inf.getName().substring(
						inf.getName().lastIndexOf('.') + 1);
				if (trigetInterfaceName.equals(className)) {
					isMatchClass = true;
					continue;
				}
			}

			// 判断方法是否相等
			String trigetMethodName = mi.getName();
			// if (isMatchClass
			// && !(mi.getName().equals(methodName)
			// || (methodName.endsWith("*") && mi.getName().startsWith(
			// methodName.substring(0, methodName.length() - 1))) ||
			// (methodName.startsWith("*") && mi
			// .getName().endsWith(methodName.substring(1,
			// methodName.length())))))
			// isMatchMethod = false;

			if (isMatchClass) {
				if (trigetMethodName.equals(methodName)) {
					isMatchMethod = true;
				} else if (methodName.endsWith("*")) {
					String methodStart = methodName.substring(0, methodName
							.indexOf('*'));
					if (trigetMethodName.startsWith(methodStart)) {
						isMatchMethod = true;
					}
				} else if (methodName.startsWith("*")) {
					String methodStart = methodName.substring(methodName
							.lastIndexOf('*'));
					if (trigetMethodName.endsWith(methodStart)) {
						isMatchMethod = true;
					}
				}
			}

		} catch (Exception e) {
			isMatchClass = false;
			isMatchMethod = false;
		}
		return (isMatchClass && isMatchMethod ? true : false);
		//modify by zhangpengf for mail auth in 20090629 end
	}

	/**
	 * @Return the AcegiCacheService acegiCacheService
	 */
	public AcegiCacheService getAcegiCacheService() {
		return acegiCacheService;
	}

	/**
	 * @Param AcegiCacheService acegiCacheService to set
	 */
	public void setAcegiCacheService(AcegiCacheService acegiCacheService) {
		this.acegiCacheService = acegiCacheService;
	}

	public java.util.Collection<org.springframework.security.access.ConfigAttribute> getAttributes(
			Method method, Class<?> targetClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public java.util.Collection<org.springframework.security.access.ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	//add by awen for change acegi to spring security 
	
	public java.util.Collection<org.springframework.security.access.ConfigAttribute> getAttributes(
			Object object) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		// Assert.notNull(object, "Object cannot be null");

		if (object instanceof MethodInvocation) {
			MethodInvocation miv = (MethodInvocation) object;
			return this.lookupAttributes(miv.getThis().getClass(), miv
					.getMethod());
		}

		if (object instanceof JoinPoint) {
			JoinPoint jp = (JoinPoint) object;
			Class targetClazz = jp.getTarget().getClass();
			String targetMethodName = jp.getStaticPart().getSignature()
					.getName();
			Class[] types = ((CodeSignature) jp.getStaticPart().getSignature())
					.getParameterTypes();

			if (logger.isDebugEnabled()) {
				logger.debug("Target Class: " + targetClazz);
				logger.debug("Target Method Name: " + targetMethodName);

				for (int i = 0; i < types.length; i++) {
					logger.debug("Target Method Arg #" + i + ": " + types[i]);
				}
			}

			try {
				return this.lookupAttributes(targetClazz, targetClazz
						.getMethod(targetMethodName, types));
			} catch (NoSuchMethodException nsme) {
				throw new IllegalArgumentException(
						"Could not obtain target method from JoinPoint: " + jp/*
																			 * ,
																			 * nsme
																			 */);
			}
		}

		throw new IllegalArgumentException(
				"Object must be a MethodInvocation or JoinPoint");
	}

	public boolean supports(Class<?> clazz) {
		return (MethodInvocation.class.isAssignableFrom(clazz) || JoinPoint.class
				.isAssignableFrom(clazz));
	}

}
