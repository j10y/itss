package com.xpsoft.framework.web.adapter.struts2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;


import com.xpsoft.framework.cache.CacheManager;
import com.xpsoft.framework.context.ContextHolder;
import com.xpsoft.framework.service.Service;
import com.xpsoft.framework.util.BeanUtil;
import com.xpsoft.framework.util.HttpUtil;
import com.xpsoft.framework.util.LogUtil;
import com.xpsoft.framework.util.PropertiesUtil;
import org.apache.commons.logging.Log;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 实现<strong>ActionSupport</strong> 基类，以获取父类方便的方法
 * 本类需要所有的Struts2的Action去继承。
 * @Class Name BaseAction
 * @Author likang
 * @Create In Jul 22, 2010
 */
public class BaseAction extends ActionSupport implements RequestAware, SessionAware{
	private Log logger = LogFactory.getLog(BaseAction.class); 

    private static final long serialVersionUID = 2L;
    
    protected int pageSize = 10;
    
    protected Map request; 
    protected Map session;

    /**
     * 获得默认的分页数
     * @Methods Name getPageSize
     * @Create In Jul 22, 2010 By likang
     * @return int
     */
	public int getPageSize() {
		return pageSize;
	}
    
	
	/**
	 * 获取基础服务
	 * @Methods Name getBaseService
	 * @Create In Jul 30, 2010 By likang
	 * @return Service
	 */
	protected Service getBaseService() {
		try {
			return (Service) getBean("baseService");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取Spring管理的服务bean
	 * @Methods Name getBean
	 * @Create In Jul 22, 2010 By likang
	 * @param name
	 * @return
	 * @throws Exception Object
	 */
	public Object getBean(String name){
		Object serviceBean = ContextHolder.getBean(name);
		return serviceBean;
	}
    
    /**
     * 用request参数中的值转成实体对象返回
     * @Methods Name getObjectByParams
     * @Create In Jul 22, 2010 By likang
     * @param clazz
     * @return Object
     */
	public Object getObjectByParams(Class clazz) {
		return BeanUtil.getObject(this.getRequest(), clazz);		
	}
    
    /**
     * 获取request中的参数params
     * @Methods Name getRequestParams
     * @Create In Jul 22, 2010 By likang
     * @return Map
     */
	public Map getRequestParams() {
		Map params = HttpUtil.requestParam2Map(getRequest());
		return params;
	}

    /**
     * 提供方便的方法来获取HttpServletRequest
     * @Methods Name getRequest
     * @Create In Jul 22, 2010 By likang
     * @return HttpServletRequest
     */
	public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();  
    }
    
    /**
     * 提供方便的方法来获取HttpServletResponse
     * @Methods Name getResponse
     * @Create In Jul 22, 2010 By likang
     * @return HttpServletResponse
     */
	public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    /**
     * 提供方便的方法来获取HttpSession
     * @Methods Name getSession
     * @Create In Jul 22, 2010 By likang
     * @return HttpSession
     */
    public HttpSession getSession() {
    	return getRequest().getSession();
    }
	
    /**
     * 获取PrintWriter 用于回写json
     * @Methods Name getPrintWriter
     * @Create In Jul 26, 2010 By likang
     * @return PrintWriter
     */
    public PrintWriter getPrintWriter() {
    	try {
    		HttpServletResponse response = getResponse();
    		response.setCharacterEncoding(PropertiesUtil.getProperties("system.characterEncoding","UTF-8"));
			return response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
	public void setRequest(Map request) {
		this.request = request;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
