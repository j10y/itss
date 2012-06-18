package com.xpsoft.padoa.test.action;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.xpsoft.padoa.test.entity.Children;
import com.xpsoft.padoa.test.entity.LoginSendLog;
import com.xpsoft.padoa.test.entity.User;
import com.xpsoft.padoa.test.service.UserService;
import com.xpsoft.framework.context.ContextHolder;
import com.xpsoft.framework.web.adapter.struts2.BaseAction;

/**
 * 测试
 * @Class Name TestAction
 * @Author likang
 * @Create In Jul 18, 2011
 */
public class TestAction extends BaseAction{
	
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(TestAction.class);
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String add() throws Exception {
		 UserService userService = (UserService) ContextHolder.getBean("userService");
		if(name != null) {
			User user = new User();
			Children children = new Children();
			children.setPassword("123");
			children.setUsername(name);
			user.setChildren(children);
			userService.addUser(user);
//			ActionContext.getContext().getSession().put("success", "aa");
			
		} else {
//			ActionContext.getContext().getSession().put("failure", "");
			return this.ERROR;
		}
		return this.SUCCESS;
	}
	
	public String listQuery() throws Exception  {
		List<LoginSendLog> list = super.getBaseService().getObjects(LoginSendLog.class);
		return null;
	}
	
	
	public String list() throws Exception  {
//		List<LoginSendLog> list = super.getBaseService().getObjects(LoginSendLog.class);
		System.out.print("hi");
		return null;
	}
}
