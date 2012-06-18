//
//package com.xpsoft.framework.web.adapter.struts1;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.struts.action.ActionServlet;
//
//import com.xpsoft.framework.util.PropertiesUtil;
///**
// * 核心控制器，覆盖struts1的ActionServlet。所有的struts的action均要先通过此类进行处理。
// * 处理编码,今后加入用户访问日志等
// * @Class Name BaseController
// * @Author likang 
// * @Create In Jul 30, 2010
// */
//
//public class BaseActionServletController extends ActionServlet {
//	/**
//	 * 服务层日志记录器
//	 */
//	protected final Log logger = LogFactory.getLog(BaseActionServletController.class);
//	
//	protected void process(HttpServletRequest request,
//			HttpServletResponse response) throws IOException, ServletException {
//		
//		String encoding = getServletContext().getInitParameter("characterEncoding"); 
//		request.setCharacterEncoding((encoding == null || encoding.equals("") ? PropertiesUtil.getProperties("system.characterEncoding","UTF-8") : encoding));
////		logger.info(user.getRealName() + "(" + user.getUserName()+ ")访问了:" + request.getRequestURI());
//		response.setHeader("Pragma","No-cache"); 
//		response.setHeader("Cache-Control","no-cache"); 
//		response.setDateHeader("Expires", 0); 
//		super.process(request, response);
//
//	}
//
//}
