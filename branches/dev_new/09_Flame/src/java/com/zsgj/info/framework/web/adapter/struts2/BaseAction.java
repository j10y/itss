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
 * ʵ��<strong>ActionSupport</strong> ���࣬�Ի�ȡ���෽��ķ���
 * �����ȡ��ǰ�û���action���ڼ�¼����Map request, Map session��
 * ��������ֱ�ӻ�ȡHttpServletRequest����ǿ�ҽ�������ʹ�ñ�Action���۵�Map request
 * ���������ݵ�Request������<br>
 * ������Ҫ���е�Actionȥ�̳С�
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
     * ��ȡĬ�Ϸ�ҳ���Ĵ�С
     * @Methods Name getPageSize
     * @Create In 2008-10-16 By sa
     * @return int
     */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ��ȡ��������ӿڣ�ͨ��Service�ӿڿ��Խ��г��õ���ɾ�Ĳ����
	 * @Methods Name getService
	 * @Create In 2008-10-16 By sa
	 * @return Service
	 */
	public Service getService(){
		return (Service) getBean("baseService");
	}
    
	/**
	 * ��ȡԪ���ݷ���������������ܽ�������Ҫʹ�ø÷��񣬹����Ӵ˻�ȡ������
	 * @Methods Name getMetaDataManager
	 * @Create In 2008-10-20 By sa
	 * @return MetaDataManager
	 */
	public MetaDataManager getMetaDataManager(){
		return (MetaDataManager) getBean("metaDataManager");
	}
	
	/**
	 * ��ȡ��ѯ������QueryServiceΪMetaDataManager֮��Ŀ��API�ӿڣ������Ӵ˻�ȡ������
	 * ע�������ѯ�����޷���������ҵ�񳡾���������ʵ��QueryService�ӿڡ�����취��
	 * <pre>
	 *   ����ColumnQueryServiceImpl�࣬дһ����̳�ColumnQueryService�����࣬������
	 *   middle��������ֻ��ֱ���޸Ĳ�ѯ�ύ������middle(Criteria criteria)�����Ҫ���������
	 *   ����������������븲��middle(Criteria criteria, Map extParams)����
	 * </pre>
	 * @Methods Name getQueryService
	 * @Create In 2008-10-20 By sa
	 * @return QueryService
	 */
	public QueryService getQueryService(){
		return (QueryService) getBean("columnQueryServiceDefaultImpl");
	}
	
	/**
	 * ��ȡSpring����ķ���bean
	 * @Methods Name getBean
	 * @Create In 2008-10-16 By sa
	 * @param name
	 * @return Object
	 */
	public Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
    
    /**
     * ��request�����е�ֵת��ʵ����󷵻�
     * @Methods Name params2Object
     * @Create In 2008-10-16 By sa
     * @param clazz
     * @return Object
     */
	public Object getObjectByParams(Class clazz) {
		return BeanUtil.getObject(this.getRequest(), clazz);		
	}
    
    /**
     * ��ȡrequest�еĲ���params
     * @Methods Name getRequestParams
     * @Create In 2008-10-16 By sa
     * @return Map
     */
	public Map getRequestParams() {
		Map params = HttpUtil.requestParam2Map(getRequest());
		return params;
	}

    
    /**
     * �ṩ����ķ�������ȡ������Ϣ
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
     * �ṩ����ķ�������ȡHttpServletRequest
     */
	public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();  
    }
    
    /**
     * �ṩ����ķ�������ȡHttpServletResponse
     */
	public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }
    
    /**
     * �ṩ����ķ�������ȡHttpSession
     */
    public HttpSession getSession() {
    	return getRequest().getSession();
    }
   
    
    /**
	 * ��ȡ��Դ�ļ���Ϣ
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key ��Դ�ļ�Key
	 * @param defaultValue  Ĭ����Ϣ      
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
	 * ��֤�Ƿ���ϵͳ����Ա
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
	 * ��֤�Ƿ����û�����Ա
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
