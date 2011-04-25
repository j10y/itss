package com.digitalchina.info.appframework.template.web.servlet;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.appframework.template.entity.Template;
import com.digitalchina.info.appframework.template.entity.TemplateItem;
import com.digitalchina.info.appframework.template.service.TemplateService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.service.DepartmentService;

/**
 * dwr方式管理模板
 * @Class Name TemplateManager
 * @author hp
 * @Create In Oct 24, 2008
 * TODO
 */
public class TemplateManager {

	private TemplateService templateService = (TemplateService) getBean("templateService");
	private DepartmentService deptService = (DepartmentService)getBean("deptService");
	private SystemMainTableService smtService = (SystemMainTableService) getBean("systemMainTableService");
	
	/**
	 * 异步更新菜单标题
	 * 
	 * @param id
	 * @param title
	 * @return true-修改成功 false-修改失败
	 */
	public Boolean ajaxUpdateTitle(String id, String menuName) {
		templateService.modifyTemplateItemName(id, menuName);
		return true;
	}

	/**
	 * 异步删除数据，包括其子孙节点
	 * 
	 * @param id
	 * @param title
	 */
	public void ajaxRemoveNode(String id) {
		templateService.removeNode(id);
	}

	/**
	 * 异步移动指定节点
	 * 
	 * @param id   指定的节点的id         
	 * @param oldParentId  节点移动前所在的父节点          
	 * @param newParentId  节点移动后的目标父节点          
	 * @param nodeIndex  节点移动后的目标位置
	 *            
	 */
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex) {
		templateService.saveNodeMove(id, oldParentId, newParentId, nodeIndex);
	}

	/**
	 * 设置结点是否可见 TODO Sep 4, 2008 By hp
	 * 
	 * @param id
	 * @param enabled
	 *  TODO
	 */
	
	
	public String ajaxFindDeptData(){
		List<Department> list = deptService.findDeptAll();
		String result = "";
		for(int i =0; i< list.size(); i++){
			Department item = (Department)list.get(i);
			Long id = item.getId();
			String name = item.getDepartName();
			result += "[\""+id+"\",\""+name+"\"],";
		}
		result = "["+ result.substring(0, result.length()-1) + "]";
		System.out.println("FindAllDeptInfoServlet 部门信息："+result);
		return result;
	}
	
	
	/**
	 * 
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param id  结点Id
	 * @param parentId  父节点ID
	 * @param smtId    系统主表ID
	 * @param smtItemId  系统主表中一条数据的ID
	 * @param templateId  模板ID
	 * @param flag  标志数据是来自系统主表还是扩展表（mainTableColumn,extendColumn）
	 * @param order  结点出现的先后次序
	 */
	public void ajaxSaveTemplateItem(String id,String parentId, String name,String smtId,String itemId,String templateId,String flag, String order){
		
		TemplateItem obj = null;
		if(null != id && !"".equals(id)){
			obj = templateService.findTemplateItemById(id);
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
//			if("mainColumn".equals(flag)){ //系统主表字段
				SystemMainTableColumn mainTableColumn = smtService.findSystemMainTableColumnByColumnId(itemId);
				obj.setMainTableColumn(mainTableColumn);
//			}else if("extendColumn".equals(flag)){  //扩展字段
//				SystemMainTableExtColumn extendTableColumn = smtService.findSystemMainTableExtColumnByColumnId(itemId);
//				obj.setExtendTableColumn(extendTableColumn);
//			}
		}
		obj.setName(name);
		obj.setOrderFlag(new Integer(order));
		templateService.saveTemplateItem(obj);
		
	}
	/**
	 * 返回spring管理的服务service
	 * 
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
}
