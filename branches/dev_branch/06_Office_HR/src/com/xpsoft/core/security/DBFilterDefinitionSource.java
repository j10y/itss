/**
 * @Probject Name: 06_Office_HR
 * @Path: com.xpsoft.core.securityDBFilterDefinitionSource.java
 * @Create By Jack
 * @Create In 2011-9-29 下午08:13:02
 * TODO
 */
package com.xpsoft.core.security;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.ConfigAttributeEditor;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.RegexUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;

/**
 * @Class Name DBFilterDefinitionSource
 * @Author Jack
 * @Create In 2011-9-29
 */
public class DBFilterDefinitionSource implements
		FilterInvocationDefinitionSource {

	private Map<String, Set<String>> roleUrlsMap = null;
	

	/**
	 * @Return the UrlMatcher urlMatcher
	 */
	public UrlMatcher getUrlMatcher() {
		return urlMatcher;
	}

	/**
	 * @Param UrlMatcher urlMatcher to set
	 */
	public void setUrlMatcher(UrlMatcher urlMatcher) {
		this.urlMatcher = urlMatcher;
	}

	/**
	 * @Return the boolean useAntPath
	 */
	public boolean isUseAntPath() {
		return useAntPath;
	}

	/**
	 * @Return the boolean lowercaseComparisons
	 */
	public boolean isLowercaseComparisons() {
		return lowercaseComparisons;
	}

	private UrlMatcher urlMatcher;

	private boolean useAntPath = true;

	private boolean lowercaseComparisons = true;

	/**
	 * 　
	 * 
	 * @param useAntPath
	 *            the useAntPath to set　
	 */
	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
	}

	/**
	 * 　 　　　　 * @param lowercaseComparisons　
	 */
	public void setLowercaseComparisons(boolean lowercaseComparisons) {
		this.lowercaseComparisons = lowercaseComparisons;
	}

	/*
	 * (non-Javadoc)　
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()　
	 */
	public void afterPropertiesSet() throws Exception {
		 // default url matcher will be RegexUrlPathMatcher　　   
		this.urlMatcher = new RegexUrlPathMatcher();
		if (useAntPath) { // change the implementation if required　　   
			this.urlMatcher = new AntUrlPathMatcher();  
		}
		// Only change from the defaults if the attribute has been set　　   
		if ("true".equals(lowercaseComparisons)) {
			if (!this.useAntPath) {
				((RegexUrlPathMatcher) this.urlMatcher).setRequiresLowerCaseUrl(true);
			}
		}else if("false".equals(lowercaseComparisons)){
			if (this.useAntPath)
				((AntUrlPathMatcher) this.urlMatcher).setRequiresLowerCaseUrl(false);
		}
	}

	@Override
	public ConfigAttributeDefinition getAttributes(Object filter)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		 FilterInvocation filterInvocation = (FilterInvocation) filter;
		 String requestURI = filterInvocation.getRequestUrl();
		 Map<String, Set<String>> urlAuthorities = this.getUrlAuthorities(filterInvocation);
		 
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 boolean isSuperUser = false;
			if (auth != null) {
				for (int i = 0; i < auth.getAuthorities().length; i++) {
					if ("超级管理员".equals(auth.getAuthorities()[i].getAuthority())) {
						isSuperUser = true;
						break;
					}
				}
				if ((!isSuperUser) && (!isUrlGrantedRight(requestURI, auth))) {
					return null;
				}else{
					String grantedAuthorities = null;
					grantedAuthorities = getAuth(requestURI, auth);
					 
					 if(grantedAuthorities != null) {
						 ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
						 configAttrEditor.setAsText(grantedAuthorities);
						 return (ConfigAttributeDefinition) configAttrEditor.getValue();
					 }
				}
			}
		 
		 
		 return null;
	}

	@Override
	public Collection getConfigAttributeDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	private String getAuth(String url, Authentication auth) {
		for (GrantedAuthority ga : auth.getAuthorities()) {
			Set urlSet = (Set) this.roleUrlsMap.get(ga.getAuthority());

			if ((urlSet != null) && (urlSet.contains(url))) {
				return ga.getAuthority();
			}
		}
		return null;
	}
	
	private boolean isUrlGrantedRight(String url, Authentication auth) {
		for (GrantedAuthority ga : auth.getAuthorities()) {
			Set urlSet = (Set) this.roleUrlsMap.get(ga.getAuthority());

			if ((urlSet != null) && (urlSet.contains(url))) {
				return true;
			}
		}
		return false;
	}
	
	/**　  
	 *　　  
	 * @param filterInvocation　  
	 * @return　  
	*/
	@SuppressWarnings("unchecked")
	private Map<String, Set<String>> getUrlAuthorities(FilterInvocation filterInvocation) {
		ServletContext servletContext = filterInvocation.getHttpRequest().getSession().getServletContext();
		this.roleUrlsMap = (Map<String, Set<String>>)servletContext.getAttribute("urlAuthorities");
		return (Map<String, Set<String>>)servletContext.getAttribute("urlAuthorities");
	}

	
}
