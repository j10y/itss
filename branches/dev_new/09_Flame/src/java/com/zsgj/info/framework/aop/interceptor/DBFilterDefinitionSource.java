package com.zsgj.info.framework.aop.interceptor;



import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.ConfigAttributeEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.zsgj.info.framework.security.cache.AcegiCacheService;
import com.zsgj.info.framework.security.entity.ResourceDetail;

/**
 * DB URL过滤器InvocationDefinitionSource
 * @Class Name DBFilterInvocationDefinitionSource
 * @Author xiaofeng
 * @Create In 2008-3-12
 */
@SuppressWarnings("deprecation")
public class DBFilterDefinitionSource implements FilterInvocationSecurityMetadataSource/*extends AbstractFilterInvocationDefinitionSource*/ {

	private boolean convertUrlToLowercaseBeforeComparison = false;
	private boolean useAntPath = false;
	private final PathMatcher pathMatcher = new AntPathMatcher();
	private final PatternMatcher matcher = new Perl5Matcher();
	
	private AcegiCacheService acegiCacheService;
	
	static {
		System.out.println("URL过滤器调用源加载完毕...");
	}

	/** 返回当前URL对应的ROLE
	 * @see org.acegisecurity.intercept.web.AbstractFilterInvocationDefinitionSource#lookupAttributes(java.lang.String)
	 */

	@SuppressWarnings({ "unchecked" })
	public Collection<ConfigAttribute> lookupAttributes(String url) {
		
		if(!this.acegiCacheService.isInitializedResourceCache()){
			this.acegiCacheService.initResourceCache();
			this.acegiCacheService.setInitializedResourceCache(true);
		}		
		
		if (isUseAntPath()) {
			// Strip anything after a question mark symbol, as per SEC-161.
			int firstQuestionMarkIndex = url.lastIndexOf("?");

			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}

		//List urls = acegiRoleDao.selectUrls();/*acegiCacheManager.getUrlResStrings();*/
		List urls = this.acegiCacheService.getUrlResStrings();

//		倒叙排序
		Collections.sort(urls);
		Collections.reverse(urls);

		if (convertUrlToLowercaseBeforeComparison) {
			url = url.toLowerCase();
		}

		GrantedAuthority[] authorities = new GrantedAuthority[0];
		for (Iterator iterator = urls.iterator(); iterator.hasNext();) {
			String resString = (String) iterator.next();
			boolean matched = false;
			if (isUseAntPath()) {
				matched = pathMatcher.match(resString, url);
			} else {
				Pattern compiledPattern;
				Perl5Compiler compiler = new Perl5Compiler();
				try {
					compiledPattern = compiler.compile(resString, Perl5Compiler.READ_ONLY_MASK);
				} catch (MalformedPatternException mpe) {
					throw new IllegalArgumentException("Malformed regular expression: " );
				}

				matched = matcher.matches(url, compiledPattern);
			}
			if (matched) {
				//ResourceDetail rd = acegiRoleDao.selectResourceDetailByUrl(resString);
				ResourceDetail rd = this.acegiCacheService.getAuthorityFromCache(resString);
				authorities = rd.getAuthorities();
				break;
			}
		}

		if (authorities.length > 0) {
			String authoritiesStr = " ";
			for (int i = 0; i < authorities.length; i++) {
				authoritiesStr += authorities[i].getAuthority() + ",";
			}
			String authStr = authoritiesStr.substring(0, authoritiesStr
					.length() - 1);
			ConfigAttributeEditor configAttrEditor = new ConfigAttributeEditor();
			configAttrEditor.setAsText(authStr.trim());
			//return (ConfigAttributeDefinition) configAttrEditor.getValue();
			return (Collection<ConfigAttribute>)configAttrEditor.getValue();
		}


		return null;
	}

	public Iterator getConfigAttributeDefinitions() {
		return null;
	}

	public void setConvertUrlToLowercaseBeforeComparison(
			boolean convertUrlToLowercaseBeforeComparison) {
		this.convertUrlToLowercaseBeforeComparison = convertUrlToLowercaseBeforeComparison;
	}

	public boolean isConvertUrlToLowercaseBeforeComparison() {
		return convertUrlToLowercaseBeforeComparison;
	}

	public boolean isUseAntPath() {
		return useAntPath;
	}

	public void setUseAntPath(boolean useAntPath) {
		this.useAntPath = useAntPath;
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
	
	public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		if ((object == null) || !this.supports(object.getClass())) {
            throw new IllegalArgumentException("Object must be a FilterInvocation");
        }

        String url = ((FilterInvocation) object).getRequestUrl();

        return lookupAttributes(url);
	}

}
