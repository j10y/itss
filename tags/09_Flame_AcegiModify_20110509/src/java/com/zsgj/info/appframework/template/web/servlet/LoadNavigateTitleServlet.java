package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.entity.UserMenuItem;
import com.zsgj.info.appframework.template.service.UserTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.exception.ServiceException;
/**
 * 加载前台主页面导航菜单
 * @Class Name LoadNavigateTitleServlet
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class LoadNavigateTitleServlet extends HttpServlet {
	
	private UserTemplateMenuService userMenuService = (UserTemplateMenuService)getBean("userTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
	
	    List<UserMenuItem> itemList = userMenuService.findAllMenuTitleByUserId(userId);
	    /* start modify by tongjp 20091202 merge menu with the same menuname*/
	    Map ummap=new HashMap();
	    for(UserMenuItem um:itemList){
	    	String text = um.getMenuName();
	    	Long id = um.getId();
	    	if(ummap.get(text)==null){
	    		ummap.put(text, id);
	    	}
	    }
	    /* end modify by tongjp 20091202 merge menu with the same menuname*/
	    if(itemList.isEmpty()){
	    	try {
				response.setCharacterEncoding("utf-8");
				response.getWriter().write("{success:false}");
			} catch (IOException e) {
				e.printStackTrace();
				throw new ApplicationException("你没有可显示的菜单，请联系部门管理员");
			}
	    }else{
		    String result = "";
//			for(int i = 0; i< itemList.size(); i++){
//				UserMenuItem item = (UserMenuItem)itemList.get(i);
//				Long id = item.getId();
//				String text = item.getMenuName();
//				result += "{\"id\":"+id+",\"text\":\""+text+"\"},";
//			}
		    /* start modify by tongjp 20091202 merge menu with the same menuname*/
			Set menuNames=ummap.keySet();
			Iterator it=menuNames.iterator();
			while(it.hasNext()){
				String menuName=it.next().toString();
				result += "{\"id\":"+ummap.get(menuName).toString()+",\"text\":\""+menuName+"\"},";
			}
			 /* end modify by tongjp 20091202 merge menu with the same menuname*/
			result = "[" + result.substring(0, result.length()-1) + "]";
			System.out.println("**********"+result);
			try {
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }		
	}
	
	/**
	 * 返回spring管理的服务service
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
}
