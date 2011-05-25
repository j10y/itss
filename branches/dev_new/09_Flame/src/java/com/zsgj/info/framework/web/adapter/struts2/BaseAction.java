package com.zsgj.info.framework.web.adapter.struts2;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.QueryService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;

/**
 * 实现<strong>ActionSupport</strong> 基类，以获取父类方便的方法
 * 例如获取当前用户，action日期记录器，Map request, Map session。
 * 甚至可以直接获取HttpServletRequest，但强烈建议优先使用本Action积累的Map request
 * 来保存数据到Request作用域。<br>
 * 本类需要所有的Action去继承。
 *
 * @author 
 */
public class BaseAction extends ActionSupport implements RequestAware, SessionAware{
    private static final long serialVersionUID = 3525445612504421307L;
    protected final Log logger = LogFactory.getLog("actionlog");
    protected int pageSize = 10;
    
    protected Map request; 
    protected Map session;

    /**
     * 获取默认分页数的大小
     * @Methods Name getPageSize
     * @Create In 2008-10-16 By sa
     * @return int
     */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获取基础服务接口，通过Service接口可以进行常用的增删改查操作
	 * @Methods Name getService
	 * @Create In 2008-10-16 By sa
	 * @return Service
	 */
	public Service getService(){
		return (Service) getBean("baseService");
	}
    
	/**
	 * 获取元数据服务管理器。因与框架交互经常要使用该服务，故增加此获取方法。
	 * @Methods Name getMetaDataManager
	 * @Create In 2008-10-20 By sa
	 * @return MetaDataManager
	 */
	public MetaDataManager getMetaDataManager(){
		return (MetaDataManager) getBean("metaDataManager");
	}
	
	/**
	 * 获取查询服务。因QueryService为MetaDataManager之外的框架API接口，故增加此获取方法。
	 * 注意如果查询服务无法满足您的业务场景，请重新实现QueryService接口。具体办法：
	 * <pre>
	 *   仿照ColumnQueryServiceImpl类，写一个类继承ColumnQueryService抽象类，覆盖其
	 *   middle方法，如只需直接修改查询提交，覆盖middle(Criteria criteria)。如果要根据特殊的
	 *   参数组合来决定，请覆盖middle(Criteria criteria, Map extParams)方法
	 * </pre>
	 * @Methods Name getQueryService
	 * @Create In 2008-10-20 By sa
	 * @return QueryService
	 */
	public QueryService getQueryService(){
		return (QueryService) getBean("columnQueryServiceDefaultImpl");
	}
	
	/**
	 * 获取Spring管理的服务bean
	 * @Methods Name getBean
	 * @Create In 2008-10-16 By sa
	 * @param name
	 * @return Object
	 */
	public Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
    
    /**
     * 用request参数中的值转成实体对象返回
     * @Methods Name params2Object
     * @Create In 2008-10-16 By sa
     * @param clazz
     * @return Object
     */
	public Object getObjectByParams(Class clazz) {
		return BeanUtil.getObject(this.getRequest(), clazz);		
	}
    
    /**
     * 获取request中的参数params
     * @Methods Name getRequestParams
     * @Create In 2008-10-16 By sa
     * @return Map
     */
	public Map getRequestParams() {
		Map params = HttpUtil.requestParam2Map(getRequest());
		return params;
	}

    
    /**
     * 提供方便的方法来获取配置信息
     * @return the user's populated form from the session
     */
    /*protected Map getConfiguration() {
        Map config = (HashMap) getSession().getServletContext().getAttribute(Constants.CONFIG);
        // so unit tests don't puke when nothing's been set
        if (config == null) {
            return new HashMap();
        }
        return config;
    }*/
    
    /**
     * 提供方便的方法来获取HttpServletRequest
     */
	public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();  
    }
    
    /**
     * 提供方便的方法来获取HttpServletResponse
     */
	public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    /**
     * 提供方便的方法来获取HttpSession
     */
    public HttpSession getSession() {
    	return getRequest().getSession();
    }
   
    
    /**
	 * 获取资源文件信息
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key 资源文件Key
	 * @param defaultValue  默认信息      
	 * @return String
	 */
    @SuppressWarnings("static-access")
	public String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try{
			message = appContext.getMessage(Key, new Object[0],
				ContextHolder.getInstance().getLocal());
			
			return (message != null && !message.equals("") ? message : defaultValue);
		}catch(Exception e){
			logger.error(e.getMessage());
			return defaultValue;
		}
	}
	
	/**
	 * 验证是否是系统管理员
	 * @Methods Name isSystemAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isSystemAdmin(GrantedAuthority[] userDetails) {
		for (int i = 0; i < userDetails.length; i++) {
			if (userDetails[i].equals(this.getProperties("system.adminkey.systemadmin", "AUTH_SYS_ADMIN"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证是否是用户管理员
	 * @Methods Name isUserAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isUserAdmin(GrantedAuthority[] userDetails) {
		for (int i = 0; i < userDetails.length; i++) {
			if (userDetails[i].equals(this.getProperties("system.adminkey.useradmin", "ROLE_USER_ADMIN"))) {
				return true;
			}
		}
		return false;
	}

	public Log getLogger() {
		return logger;
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
