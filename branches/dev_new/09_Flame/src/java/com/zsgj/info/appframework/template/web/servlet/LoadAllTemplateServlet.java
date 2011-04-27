package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.entity.Template;
import com.zsgj.info.appframework.template.service.TemplateService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

/**
 * 加载所有的模板信息
 * @Class Name LoadAllTemplateServlet
 * @author hp
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class LoadAllTemplateServlet extends HttpServlet {
	
	private TemplateService templateService = (TemplateService) getBean("templateService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	    List<Template> itemList = templateService.findAllTemplates();
	    String result = "";
		for(int i = 0; i< itemList.size(); i++){
			Template item = (Template)itemList.get(i);
			Long id = item.getId();
			String templateName = item.getTemplateName();
			String descn = item.getDescn();
			String department = item.getDepartment().getDepartName();
			String createUser = item.getCreateUser().getRealName();
			String createDate = item.getCreateDate().toString();
			String ruleFile = item.getRuleFile();
			
			result +="{\"id\":"+id+",\"templateName\":\""+templateName+"\",\"descn\":\""+
			descn+"\",\"department\":\""+department+"\",\"createUser\":\""+createUser+
			"\",\"createDate\":\""+createDate+"\",\"ruleFile\":\""+ruleFile+"\"},";
//			result += "[\"+id+\",\""+name+"\"],";
		}
		result = "{data:["+ result.substring(0, result.length()-1) + "]}";
		System.out.println("LoadAllTemplateServlet 模板信息："+result);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
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
