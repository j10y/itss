package com.xpsoft.framework.exception.struts2.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;

import com.xpsoft.framework.exception.BaseException;
import com.xpsoft.framework.util.PropertiesUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 统一异常处理拦截器，用于处理所有层向上抛出的所有异常
 * 常见异常和业务异常的描述内容均来自资源文件
 * @Class Name ExceptionInteceptor
 * @Author likang
 * @Create In Jul 22, 2010
 */
public class ExceptionInteceptor extends AbstractInterceptor {
	
	/** 
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 3L;
	
	protected final Log logger = LogFactory.getLog("ERROR");
	
	/**
	 * 拦截方法
	 * 处理同步异步两种请求的异常信息，同步则转到错误页面显示，异步则回写浏览器
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		String result = "";
		try {
			result = invocation.invoke();
		} catch (Exception ex) {
			ActionContext ctx = ActionContext.getContext();
			ActionSupport action = (ActionSupport) invocation.getAction();
			exception(ex, action, ctx);
			//在服务器端判断request来自异步还是同步请求
			boolean isAjaxRequest = (ServletActionContext.getRequest().getHeader("x-requested-with") != null)? true:false; 
			//异步请求则将异常回写到浏览器
			if (isAjaxRequest) {
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding(PropertiesUtil.getProperties("response.characterEncoding","utf-8"));
				PrintWriter pWriter = response.getWriter();
				pWriter.append(ServletActionContext.getRequest().getAttribute("exception").toString());
				pWriter.flush();
				return null;
			//同步请求则跳转到错误页面显示错误信息
			} else {
				return "error";
			}
			
		}
		return result;
	}
	
	/**
	 * 将日志信息加入日志文件，并放入exception对象中用于前台提示
	 * @Methods Name exception
	 * @Create In Jul 22, 2010 By likang
	 * @param e
	 * @param actionSupport
	 * @param ctx
	 * @throws Exception void
	 */
	private void exception(Exception e, ActionSupport actionSupport,
			ActionContext ctx) throws Exception {
		
		e.printStackTrace() ;
		String msg = "";
		String msgDev = "";
		String elderMessageString = e.getMessage();
		String errorNum = "";
		if (e instanceof BaseException) {
			msg = ((BaseException)e).getMessage();
			msgDev = ((BaseException)e).getMessage() + "\r\nErrorCode: " + ((BaseException)e).getErrorCode() + " , ExceptionName: " + ((BaseException)e).getClass().getName();
		} else if (e instanceof DataAccessException) {
			msg = PropertiesUtil.getProperties("00000001");
			errorNum = "00000001";
		} else if (e instanceof NullPointerException) {
			msg = PropertiesUtil.getProperties("00000002");
			errorNum = "00000002";
		} else if (e instanceof IOException) {
			msg = PropertiesUtil.getProperties("00000003");
			errorNum = "00000003";
		} else if (e instanceof ClassNotFoundException) {
			msg = PropertiesUtil.getProperties("00000004");
			errorNum = "00000004";
		} else if (e instanceof ArithmeticException) {
			msg = PropertiesUtil.getProperties("00000005");
			errorNum = "00000005";
		} else if (e instanceof ArrayIndexOutOfBoundsException) {
			msg = PropertiesUtil.getProperties("00000006");
			errorNum = "00000006";
		} else if (e instanceof IllegalArgumentException) {
			msg = PropertiesUtil.getProperties("00000007");
			errorNum = "00000007";
		} else if (e instanceof ClassCastException) {
			msg = PropertiesUtil.getProperties("00000008");
			errorNum = "00000008";
		} else if (e instanceof SQLException) {
			msg = PropertiesUtil.getProperties("00000009");
			errorNum = "00000009";
		} else if (e instanceof Exception) {
			msg = PropertiesUtil.getProperties("00000010");
			errorNum = "00000010";
		} else if (e instanceof Throwable) {
			msg = PropertiesUtil.getProperties("00000011");
			errorNum = "00000011";
		} else {
			msg = PropertiesUtil.getProperties("00000000");
			errorNum = "00000000";
		}
		String toUserString = "ErrorCode: " + errorNum + "," + msg;
		msg += "ErrorCode: "+errorNum+",ExceptionName: " + e.getClass().getName();
		//String beginString = "\r\n*****************************************Begin*********************************************************\r\n";
		//String endString = "*****************************************End*********************************************************\r\n";
		//StringBuffer sBuffer = new StringBuffer();
		//sBuffer.append("      " + elderMessageString + "\r\n");
		//for (int i = 0; i < e.getStackTrace().length; i++) {
		//	sBuffer.append("      " + e.getStackTrace()[i] + "\r\n");
		//}
		if ((e instanceof BaseException)) {
			//logger.error(beginString + msgDev + "\r\n" + sBuffer + endString);
		} else {
			//logger.error(beginString + msg + "\r\n" + sBuffer + endString);
		}
		//e.printStackTrace();
		//exception对象用于同步请求发生异常后的前台显示异常信息
		ServletActionContext.getRequest().setAttribute("exception", toUserString + PropertiesUtil.getProperties("00000012"));
		//String actionname = ctx.getName();
		String string = msg + PropertiesUtil.getProperties("00000012");
		actionSupport.addActionError(string);
		
	} 
}
