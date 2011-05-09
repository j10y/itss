package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.template.entity.Template;
import com.zsgj.info.appframework.template.entity.TemplateItem;
import com.zsgj.info.appframework.template.service.TemplateService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

@SuppressWarnings("serial")
public class TemplateItemSaveServlet extends HttpServlet {
	
	private TemplateService templateService = (TemplateService) getBean("templateService");
	private SystemMainTableService smtService = (SystemMainTableService) getBean("systemMainTableService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		TemplateItem obj = null;
		request.setCharacterEncoding("gbk");
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		//系统主表ID
		String smtId = request.getParameter("smtId");
		//数据项ID
		String itemId = request.getParameter("itemId");
		//模板ID
		String templateId = request.getParameter("templateId");
		//是否是系统主表字段标志. mainColumn: 系统主表字段， extendColumn:扩展表字段
		String flag = request.getParameter("flag");
		//结点的显示顺序
		String order = request.getParameter("order");
			
		if(null != id && !"".equals(id)){
			obj = templateService.findTemplateItemById(id);
			if(obj == null){
				RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/sysTemplate/error.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}else{
			obj = new TemplateItem();
			TemplateItem parentMenu = null;
			if("".equals(parentId) || "0".equals(parentId)){
				obj.setParentTemplateItem(null);
			}else{
				parentMenu = templateService.findTemplateItemById(parentId);
				obj.setParentTemplateItem(parentMenu);
			}
			
			if(!"".equals(templateId) && templateId != null){
				Template template = templateService.findTemplateById(templateId);
				obj.setTemplate(template);
			}else{
				obj.setTemplate(null);
			}
		}
		
		SystemMainTable smt = smtService.findSystemMainTable(smtId);
		obj.setSystemMainTable(smt);
		if(!"".equals(flag) && flag != null){
			//if("mainColumn".equals(flag)){ //系统主表字段
				SystemMainTableColumn mainTableColumn = smtService.findSystemMainTableColumnByColumnId(itemId);
				obj.setMainTableColumn(mainTableColumn);
//			}else if("extendColumn".equals(flag)){
//				SystemMainTableExtColumn extendTableColumn = smtService.findSystemMainTableExtColumnByColumnId(itemId);
//				obj.setExtendTableColumn(extendTableColumn);
//			}
		}
		
		
		obj.setOrderFlag(new Integer(order));
		
		templateService.saveTemplateItem(obj);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/sysTemplate/success.jsp");
		dispatcher.forward(request, response);		
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
