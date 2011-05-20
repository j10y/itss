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
 * Action���࣬�̳���Struts��MappingDispatchAction��
 * ʹ��BaseMappingDispatchAction���Դ��������ļ��ĳ��ȡ�
 * ͨ��struts�����ļ���action��ǩ������parameter����������action�� �������õķ�����<br>
 * �磺parameter="findUsers"
 * <p>
 * ���⣬��action�����࣬�����ж�����action form�������BaseDispatchAction��ͬ�� <br>
 * �磺
 * 
 * <pre>
 *      action��ǩ���ã�
 *      &lt;action path=&quot;/addItemToCart&quot; 
 *             form=&quot;cartItemForm&quot; 
 *             type=&quot;com.fxfeiyi.ebuy.web.action.CartAction&quot; 
 *             parameter=&quot;addCartItem&quot; 
 *             scope=&quot;request&quot;/&gt; 
 *             
 *     action��   
 *      public class CardAction extends BaseMappingDispatchAction{
 *        public ActionForward addCartItem(ActionMapping actionMapping,
 *            ActionForm actionForm, HttpServletRequest request,
 *            HttpServletResponse httpServletResponse) throws BaseException {
 *            //.............
 *        }
 *     }
 * </pre>
 * 
 * ��ʹ�� /addItemToCart.do����CartActionʱ��������CartAction��addCartItem������������execute������
 * <p>
 * �ڲ�����:<br>
 * �ȵ���MappingDispatchAction��execute������MappingDispatchAction�����Լ���execute���������
 * �����ļ��и�action��ǩ��parameter���Ե�ֵ������ʵ�ʵķ�������˿�����BaseMappingDispatchAction�п���
 * ͨ����MappingDispatchAction��execute�������쳣��catch�����ﵽcatch��������action���쳣��Ŀ�ġ�������ʵ����
 * action�Է��񷽷����쳣����ͬʱ�����쳣������룬���������е�action�������쳣����
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
	 * ����spring����ķ���service
	 * 
	 * @Methods Name getBean
	 * @Create In 2008-3-3 By peixf
	 * @param name
	 * @return Object
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @Methods Name getBaseService
	 * @Create In 2008-4-11 By peixf
	 * @return Service
	 */
	protected Service getService() {
		return (Service) getBean("baseService");
	}

	/**
	 * ����DispatchAction��execute�������ڸ÷�������û���DispatchAction��execute
	 * ����������DispatchAction��execute�����ڲ������mapping�����෽���������super.execute
	 * ǰ����쳣���񣬼����Զ�����������ҵ�������ص���Action���׳����쳣��
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
			// ������ҳ�洫���½�û���Ϣ����
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
			logger.error("����������쳣: " + e.getMessageAndErrorCode());
			request.setAttribute("errorMessage", e.getMessageAndErrorCode());
			return mapping.findForward("error");
		} catch (ApplicationException e) {
			logger.error("����Ӧ�ü��쳣: " + e.getMessageAndErrorCode());
			request.setAttribute("errorMessage", e.getMessage());
			return mapping.findForward("error");
		} catch (org.springframework.security.access.AccessDeniedException e) {
			logger.error("û�з��񷽷�����Ȩ��: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
			return mapping.findForward("accessDenied");
		} catch (Exception e) {
			// Ϊ��ֹ������쳣����action���ֶ��׳�Ӧ�ü��쳣֮����쳣���ؼ�Exception�����쳣catch
			logger.error("���������쳣: " + e.getMessage());
			e.printStackTrace();
			request.setAttribute("errorMessage", e.getMessage());
			return mapping.findForward("error");
		}
		return forward;
	}

	/**
	 * ��ȡ��Դ�ļ���Ϣ
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            ��Դ�ļ�Key
	 * @param defaultValue
	 *            Ĭ����Ϣ
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
	 * ��֤�Ƿ���ϵͳ����Ա
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
	 * ��֤�Ƿ����û�����Ա
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
