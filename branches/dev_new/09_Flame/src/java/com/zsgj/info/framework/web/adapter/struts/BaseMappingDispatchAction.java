package com.zsgj.info.framework.web.adapter.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.springframework.context.ApplicationContext;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;

/**
 * Action基类，继承于Struts的MappingDispatchAction。
 * 使用BaseMappingDispatchAction可以大大简化配置文件的长度。
 * 通过struts配置文件的action标签中增加parameter属性来配置action中 将被调用的方法。<br>
 * 如：parameter="findUsers"
 * <p>
 * 另外，该action的子类，可以有独立的action form，这点与BaseDispatchAction不同。 <br>
 * 如：
 * 
 * <pre>
 *      action标签配置：
 *      &lt;action path=&quot;/addItemToCart&quot; 
 *             form=&quot;cartItemForm&quot; 
 *             type=&quot;com.fxfeiyi.ebuy.web.action.CartAction&quot; 
 *             parameter=&quot;addCartItem&quot; 
 *             scope=&quot;request&quot;/&gt; 
 *             
 *     action：   
 *      public class CardAction extends BaseMappingDispatchAction{
 *        public ActionForward addCartItem(ActionMapping actionMapping,
 *            ActionForm actionForm, HttpServletRequest request,
 *            HttpServletResponse httpServletResponse) throws BaseException {
 *            //.............
 *        }
 *     }
 * </pre>
 * 
 * 当使用 /addItemToCart.do调用CartAction时，将调用CartAction的addCartItem方法，而不是execute方法。
 * <p>
 * 内部机制:<br>
 * 先调用MappingDispatchAction的execute方法，MappingDispatchAction将在自己的execute方法里根据
 * 配置文件中该action标签的parameter属性的值来调用实际的方法，因此可以在BaseMappingDispatchAction中可以
 * 通过对MappingDispatchAction的execute方法做异常的catch，而达到catch到具体子action中异常的目的。这样即实现了
 * action对服务方法的异常捕获，同时简化了异常处理代码，避免在所有的action都进行异常捕获。
 * 
 * @Class Name BaseMappingDispatchAction
 * @Author xiaofeng
 * @Create In 2007-11-14
 * 
 */
public abstract class BaseMappingDispatchAction extends MappingDispatchAction {

	protected final Log logger = LogFactory.getLog("actionlog");
	protected int pageSize = 10;

	/**
	 * 返回spring管理的服务service
	 * 
	 * @Methods Name getBean
	 * @Create In 2008-3-3 By peixf
	 * @param name
	 * @return Object
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}

	/**
	 * 获取基础服务
	 * 
	 * @Methods Name getBaseService
	 * @Create In 2008-4-11 By peixf
	 * @return Service
	 */
	protected Service getService() {
		return (Service) getBean("baseService");
	}

	/**
	 * 覆盖DispatchAction的execute方法。在该方法里调用基类DispatchAction的execute
	 * 方法，由于DispatchAction的execute方法内部会调用mapping的子类方法，因此在super.execute
	 * 前后加异常捕获，即可自动捕获从与具体业务操作相关的子Action中抛出的异常。
	 */
	public final ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		try {
			this.pageSize = Integer.valueOf(
					this.getProperties("system.grid.lines", "10")).intValue();
			request.setAttribute("pageSize", pageSize);
			UserInfo user = UserContext.getUserInfo();
			// 增加向页面传输登陆用户信息功能
			if (user != null) {
				request.setAttribute("userInfo", user);
				request.getSession().setAttribute("userInfo", user);
				request.setAttribute("systemAdmin", isSystemAdmin(UserContext
						.getAuthorities()));
				request.setAttribute("userAdmin", isUserAdmin(UserContext
						.getAuthorities()));
				forward = super.execute(mapping, form, request, response);
			} else {
				if (request.getRequestURI().indexOf(
						this.getProperties("system.security.loginpath",
								"/login.do")) != -1) {
					String loginFail = this.getProperties(
							"system.security.loginpath", "/login.do")
							+ "?"
							+ this.getProperties("system.security.loginmethod",
									"methodCall")
							+ "="
							+ request.getParameter(this
									.getProperties(
											"system.security.loginmethod",
											"methodCall"));
					if (loginFail.equals(this.getProperties(
							"system.security.authenticationFailureUrl",
							"/login.do?methodCall=toLoginFailed"))) {
						forward = super.execute(mapping, form, request,
								response);
					} else {
						forward = mapping.findForward("login");
					}
				} else {
					forward = mapping.findForward("login");
				}

				return forward;
			}
		} catch (ServiceException e) {
			logger.error("发生服务层异常: " + e.getMessageAndErrorCode());
			request.setAttribute("errorMessage", e.getMessageAndErrorCode());
			return mapping.findForward("error");
		} catch (ApplicationException e) {
			logger.error("发生应用级异常: " + e.getMessageAndErrorCode());
			request.setAttribute("errorMessage", e.getMessage());
			return mapping.findForward("error");
		} catch (org.springframework.security.access.AccessDeniedException e) {
			logger.error("没有服务方法服务权限: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
			return mapping.findForward("accessDenied");
		} catch (Exception e) {
			// 为防止服务层异常和在action中手动抛出应用级异常之外的异常，特加Exception类型异常catch
			logger.error("发生其他异常: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
			return mapping.findForward("error");
		}
		return forward;
	}

	/**
	 * 获取资源文件信息
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            资源文件Key
	 * @param defaultValue
	 *            默认信息
	 * @return String
	 */
	protected String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, new Object[0], ContextHolder
					.getInstance().getLocal());

			return (message != null && !message.equals("") ? message
					: defaultValue);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return defaultValue;
		}
	}

	/**
	 * 验证是否是系统管理员
	 * 
	 * @Methods Name isSystemAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isSystemAdmin(GrantedAuthority[] userDetails) {
		for (int i = 0; i < userDetails.length; i++) {
			if (userDetails[i].equals(this.getProperties(
					"system.adminkey.systemadmin", "AUTH_SYS_ADMIN"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证是否是用户管理员
	 * 
	 * @Methods Name isUserAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isUserAdmin(GrantedAuthority[] userDetails) {
		for (int i = 0; i < userDetails.length; i++) {
			if (userDetails[i].equals(this.getProperties(
					"system.adminkey.useradmin", "ROLE_USER_ADMIN"))) {
				return true;
			}
		}
		return false;
	}
}
