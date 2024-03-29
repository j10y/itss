package com.zsgj.info.framework.web.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 对不通过struts控制器转发的请求数据进行中文编码过滤。
 * 由于BoundUserInfoToThreadLocalFilter的使用，直接将中文编码过滤做入
 * BoundUserInfoToThreadLocalFilter。此过滤器暂未使用。
 * @Class Name SetCharacterEncodingFilter
 * @author xiaofeng
 * @Create In Oct 24, 2007
 * @deprecated
 */

public class SetCharacterEncodingFilter implements Filter {
    protected String encoding = null;
    protected FilterConfig filterConfig = null;
    protected boolean ignore = true;
    public void destroy() {

        this.encoding = null;
        this.filterConfig = null;

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
	throws IOException, ServletException {

        // Conditionally select and set the character encoding to be used
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encoding = selectEncoding(request);
            if (encoding != null)
                request.setCharacterEncoding(encoding);
        }

	// Pass control on to the next filter
        chain.doFilter(request, response);

    }

    public void init(FilterConfig filterConfig) throws ServletException {

	this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        if (value == null)
            this.ignore = true;
        else if (value.equalsIgnoreCase("true"))
            this.ignore = true;
        else if (value.equalsIgnoreCase("yes"))
            this.ignore = true;
        else
            this.ignore = false;

    }

    protected String selectEncoding(ServletRequest request) {

        return (this.encoding);

    }


}
