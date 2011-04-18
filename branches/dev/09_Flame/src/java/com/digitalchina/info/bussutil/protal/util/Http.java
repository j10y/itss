package com.digitalchina.info.bussutil.protal.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.util.json.CollectionUtil;
import com.digitalchina.info.framework.util.json.StringPool;

public class Http {
	private static Http http = null;

	private Log log = LogFactory.getLog(this.getClass());

	private Http() {
	}

	public static synchronized Http getHttp() {
		if (http == null) {
			http = new Http();
		}
		return http;
	}

	public String getParameter(HttpServletRequest request, String paramName) {
		String result = null;
		try {
			if (StringUtils.isNotEmpty(request.getParameter(paramName))) {
				result = request.getParameter(paramName);
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("" + e.toString());
			}
		}
		return result;
	}

	public Serializable getUserId(HttpServletRequest request) {
		return UserContext.getUserInfo().getId();
	}

	public Serializable getEntityId(HttpServletRequest request) {
		try {
			String objectId = this.getParameter(request, StringPool.ENTITY_ID);
			if (StringUtils.isNotEmpty(objectId)) {
				return (Serializable) objectId;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.info("faceye error in method:getEntityid:" + e.toString());
			return null;
		}
	}

	public String getEntityClassName(HttpServletRequest request) {
		return this.getParameter(request, StringPool.ENTITY_CLASS) == null ? null
				: this.getParameter(request, StringPool.ENTITY_CLASS);
	}

	public int getCurrentIndex(HttpServletRequest request) {
		String index = this.getParameter(request, StringPool.CURRENT_INDEX) == null ? this
				.getParameter(request, StringPool.START)
				: this.getParameter(request, StringPool.CURRENT_INDEX);
		if (StringUtils.isEmpty(index)) {
			index = "0";
		}
		int currentIndex = Integer.parseInt(index);
		return currentIndex;
	}

	public int getPageSize(HttpServletRequest request) {
		int size;
		String pageSize = this.getParameter(request,
				StringPool.CURRENT_PAGE_SIZE);
		if (StringUtils.isEmpty(pageSize)) {
			size = PaginationSupport.PAGESIZE;
		} else {
			size = Integer.parseInt(pageSize);
		}
		return size;
	}

	public int getCurrentPageSize(HttpServletRequest request) {
		return null == this.getHttp().getParameter(request,
				StringPool.CURRENT_PAGE_SIZE) ? 15 : Integer.parseInt(this
				.getHttp().getParameter(request, StringPool.CURRENT_PAGE_SIZE));
	}

	public void setPaginationSupport(PaginationSupport page,
			HttpServletRequest request) {
		if (this.getRequestAttribute(request, StringPool.PAGINATION_SUPPORT) != null) {
			this.removeRequestAttribute(request, StringPool.PAGINATION_SUPPORT);
		}
		this.setRequestAttribute(request, StringPool.PAGINATION_SUPPORT, page);
	}

	public Object getRequestAttribute(HttpServletRequest request, String arg0) {
		Object o = null;
		if (request.getAttribute(arg0) != null
				&& !"".equals(request.getAttribute(arg0))) {
			o = request.getAttribute(arg0);
		}
		return o;
	}

	public void removeRequestAttribute(HttpServletRequest request, String arg0) {
		if (this.getRequestAttribute(request, arg0) != null) {
			request.removeAttribute(arg0);
		}
	}

	public void setRequestAttribute(HttpServletRequest request, String arg0,
			Object arg1) {
		if (this.getRequestAttribute(request, arg0) != null) {
			this.removeRequestAttribute(request, arg0);
		}
		request.setAttribute(arg0, arg1);
	}

	public static Map getParametersStartingWith(ServletRequest request,
			String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	public Map getParameterMap(HttpServletRequest request) {
		return CollectionUtil.getCollectionUtil().transRequestParametersMap(
				request.getParameterMap());
	}

	public String getMethod(HttpServletRequest request) {
		return this.getParameterMap(request).get(StringPool.METHOD) == null ? null
				: this.getParameterMap(request).get(StringPool.METHOD)
						.toString();

	}

	public String getEntityClass(HttpServletRequest request) {
		return this.getParameterMap(request).get(StringPool.ENTITY_CLASS) == null ? null
				: this.getParameterMap(request).get(StringPool.ENTITY_CLASS)
						.toString();
	}

	public String getQueryType(HttpServletRequest request) {
		return this.getParameterMap(request).get(StringPool.QUERY_TYPE) == null ? null
				: this.getParameterMap(request).get(StringPool.QUERY_TYPE)
						.toString();
	}

	public HttpSession getSeesion(HttpServletRequest request) {
		return request.getSession();
	}

	public Object getObjectFormSession(String key, HttpServletRequest request) {
		Object o = null;
		o = this.getSeesion(request).getAttribute(key);
		return o;
	}
}
