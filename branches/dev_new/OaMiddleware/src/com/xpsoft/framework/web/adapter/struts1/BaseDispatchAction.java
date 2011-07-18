//struts1包移除 如果需要后续再加
//package com.xpsoft.framework.web.adapter.struts1;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.apache.struts.actions.DispatchAction;
//import org.apache.struts2.ServletActionContext;
//import org.springframework.context.ApplicationContext;
//import org.springframework.dao.DataAccessException;
//
//import com.xpsoft.framework.context.ContextHolder;
//import com.xpsoft.framework.exception.BaseException;
//import com.xpsoft.framework.exception.NoThisServiceException;
//import com.xpsoft.framework.service.Service;
//import com.xpsoft.framework.util.PropertiesUtil;
//import com.opensymphony.xwork2.ActionContext;
//import com.opensymphony.xwork2.ActionInvocation;
//import com.opensymphony.xwork2.ActionSupport;
//
///**
// * Action基类，继承于Struts的DispatchAction。建议优先使用此action来扩展Action。
// * 因为此DispatchAction通过URI中参数来决定调用与具体业务相关的action方法。
// * <p>
// * 内部机制:<br>
// * 先调用MappingDispatchAction的execute方法，MappingDispatchAction将在自己的execute方法里根据
// * 配置文件中该action标签的parameter属性的值来调用实际的方法，因此可以在BaseMappingDispatchAction中可以
// * 通过对MappingDispatchAction的execute方法做异常的catch，而达到catch到具体子action中异常的目的。这样即实现了
// * action对服务方法的异常捕获，同时简化了异常处理代码，避免在所有的action都进行异常捕获。
// * @Class Name BaseDispatchAction
// * @Author likang
// * @Create In Jul 30, 2010
// */
//public abstract class BaseDispatchAction extends DispatchAction {
//
//	protected final Log logger = LogFactory.getLog(BaseDispatchAction.class);
//	
//	protected final Log errorLogger = LogFactory.getLog("ERROR");
//
//	public static final String FSP = System.getProperty("file.separator");
//
//	protected int pageSize = 10;
//	
//	/**
//	 * 覆盖DispatchAction的execute方法。在该方法里调用基类DispatchAction的execute
//	 * 方法，由于DispatchAction的execute方法内部会调用mapping的子类方法，因此在super.execute
//	 * 前后加异常捕获，即可自动捕获从与具体业务操作相关的子Action中抛出的异常。
//	 * 并自动写入Log文件
//	 * @Methods Name execute
//	 * @Create In Apr , 2010 By likang
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception ActionForward
//	 */
//	public final ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		// TODO Auto-generated method stub
//		String result = "";
//		try {
//			//可能需要增加向页面传输登陆用户信息功能预留
//			
//			//正常执行
//			return super.execute(mapping, form, request, response);
//		} catch (Exception e) {
//			logger.error(e);
//			exception(e, request);
//			//在服务器端判断request来自异步还是同步请求
//			boolean isAjaxRequest = (request.getHeader("x-requested-with") != null)? true:false; 
//			//异步请求则将异常回写到浏览器
//			if (isAjaxRequest) {
//				response.setCharacterEncoding(PropertiesUtil.getProperties("system.characterEncoding","utf-8"));
//				PrintWriter pWriter = response.getWriter();
//				pWriter.append(request.getAttribute("exception").toString());
//				pWriter.flush();
//				return null;
//			//同步请求则跳转到错误页面显示错误信息
//			} else {
//				return mapping.findForward("error");
//			}
//			
//		} 
//		
//	}
//
//	/**
//	 * 将日志信息加入日志文件，并放入exception对象中用于前台提示
//	 * @Methods Name exception
//	 * @Create In Jul 22, 2010 By likang
//	 * @param e
//	 * @param request
//	 */
//	private void exception(Exception e, HttpServletRequest request)  {
//		String msg = "";
//		String msgDev = "";
//		if (e instanceof BaseException) {
//			msg = ((BaseException)e).getMessage();
//			msgDev = ((BaseException)e).getMessage() + "\r\nErrorCode: " + ((BaseException)e).getErrorCode() + " , ExceptionName: " + ((BaseException)e).getClass().getName();
//		} else if (e instanceof DataAccessException) {
//			msg = PropertiesUtil.getProperties("00000001");
//		} else if (e instanceof NullPointerException) {
//			msg = PropertiesUtil.getProperties("00000002");
//		} else if (e instanceof IOException) {
//			msg = PropertiesUtil.getProperties("00000003");
//		} else if (e instanceof ClassNotFoundException) {
//			msg = PropertiesUtil.getProperties("00000004");
//		} else if (e instanceof ArithmeticException) {
//			msg = PropertiesUtil.getProperties("00000005");
//		} else if (e instanceof ArrayIndexOutOfBoundsException) {
//			msg = PropertiesUtil.getProperties("00000006");
//		} else if (e instanceof IllegalArgumentException) {
//			msg = PropertiesUtil.getProperties("00000007");
//		} else if (e instanceof ClassCastException) {
//			msg = PropertiesUtil.getProperties("00000008");
//		} else if (e instanceof SQLException) {
//			msg = PropertiesUtil.getProperties("00000009");
//		} else if (e instanceof Exception) {
//			msg = PropertiesUtil.getProperties("00000010");
//		} else if (e instanceof Throwable) {
//			msg = PropertiesUtil.getProperties("00000011");
//		} else {
//			msg = PropertiesUtil.getProperties("00000000");
//		}
//		msg += "ExceptionName: " + e.getClass().getName();
//		String beginString = "\r\n*****************************************Begin*********************************************************\r\n";
//		String endString = "*****************************************End*********************************************************\r\n";
//		StringBuffer sBuffer = new StringBuffer();
//		for (int i = 0; i < e.getStackTrace().length; i++) {
//			sBuffer.append("      " + e.getStackTrace()[i] + "\r\n");
//		}
//		if ((e instanceof BaseException)) {
//			errorLogger.error(beginString + msgDev + "\r\n" + sBuffer + endString);
//		} else {
//			errorLogger.error(beginString + msg + "\r\n" + sBuffer + endString);
//		}
//		//e.printStackTrace();
//		//exception对象用于同步请求发生异常后的前台显示异常信息
//		request.setAttribute("exception", msg + PropertiesUtil.getProperties("00000012"));
//		
//	} 
//
//	/**
//	 * 返回spring管理的服务service
//	 * @Methods Name getBean
//	 * @Create In Jul 30, 2010 By likang
//	 * @param name
//	 * @return Object
//	 */
//	protected Object getBean(String name) {
//		Object serviceBean = ContextHolder.getBean(name);
//		if (serviceBean == null) {
//			throw new NoThisServiceException();
//		}
//		return serviceBean;
//	}
//
//	/**
//	 * 获取基础服务
//	 * @Methods Name getBaseService
//	 * @Create In Jul 30, 2010 By likang
//	 * @return Service
//	 */
//	protected Service getBaseService() {
//		return (Service) getBean("baseService");
//	}
//	
//	/**
//	 * 获取资源文件信息
//	 * @Methods Name getProperties
//	 * @Create In Jul 30, 2010 By likang
//	 * @param Key
//	 * @param defaultValue
//	 * @return String
//	 */
//	protected String getProperties(String Key, String defaultValue) {
//		ApplicationContext appContext = ContextHolder.getApplicationContext();
//		String message = "";
//		try {
//			message = appContext.getMessage(Key, new Object[0], ContextHolder
//					.getInstance().getLocal());
//			return (message != null && !message.equals("") ? message
//					: defaultValue);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return defaultValue;
//		}
//	}
//	
//	/**
//     * 提供方便的方法来获取HttpSession
//     * @Methods Name getSession
//     * @Create In Jul 22, 2010 By likang
//     * @return HttpSession
//     */
//    protected HttpSession getSession(HttpServletRequest request) {
//    	return request.getSession();
//    }
//    
//    /**
//     * 获取PrintWriter 用于回写json
//     * @Methods Name getPrintWriter
//     * @Create In Jul 26, 2010 By likang
//     * @return PrintWriter
//     */
//    protected PrintWriter getPrintWriter(HttpServletResponse response) {
//    	try {
//    		response.setCharacterEncoding(PropertiesUtil.getProperties("system.characterEncoding","UTF-8"));
//			return response.getWriter();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//    }
//    
//    /**
//     * 获得默认分页大小
//     * @Methods Name getPageSize
//     * @Create In Jul 30, 2010 By likang
//     * @return int
//     */
//	public int getPageSize() {
//		// TODO Auto-generated method stub
//		return this.pageSize;
//	}
//}
