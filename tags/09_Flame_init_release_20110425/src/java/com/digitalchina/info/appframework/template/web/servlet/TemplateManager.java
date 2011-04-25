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
 * dwr��ʽ����ģ��
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
	 * �첽���²˵�����
	 * 
	 * @param id
	 * @param title
	 * @return true-�޸ĳɹ� false-�޸�ʧ��
	 */
	public Boolean ajaxUpdateTitle(String id, String menuName) {
		templateService.modifyTemplateItemName(id, menuName);
		return true;
	}

	/**
	 * �첽ɾ�����ݣ�����������ڵ�
	 * 
	 * @param id
	 * @param title
	 */
	public void ajaxRemoveNode(String id) {
		templateService.removeNode(id);
	}

	/**
	 * �첽�ƶ�ָ���ڵ�
	 * 
	 * @param id   ָ���Ľڵ��id         
	 * @param oldParentId  �ڵ��ƶ�ǰ���ڵĸ��ڵ�          
	 * @param newParentId  �ڵ��ƶ����Ŀ�길�ڵ�          
	 * @param nodeIndex  �ڵ��ƶ����Ŀ��λ��
	 *            
	 */
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex) {
		templateService.saveNodeMove(id, oldParentId, newParentId, nodeIndex);
	}

	/**
	 * ���ý���Ƿ�ɼ� TODO Sep 4, 2008 By hp
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
		System.out.println("FindAllDeptInfoServlet ������Ϣ��"+result);
		return result;
	}
	
	
	/**
	 * 
	 * TODO
	 * Sep 11, 2008 By hp
	 * @param id  ���Id
	 * @param parentId  ���ڵ�ID
	 * @param smtId    ϵͳ����ID
	 * @param smtItemId  ϵͳ������һ�����ݵ�ID
	 * @param templateId  ģ��ID
	 * @param flag  ��־����������ϵͳ��������չ��mainTableColumn,extendColumn��
	 * @param order  �����ֵ��Ⱥ����
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
//			if("mainColumn".equals(flag)){ //ϵͳ�����ֶ�
				SystemMainTableColumn mainTableColumn = smtService.findSystemMainTableColumnByColumnId(itemId);
				obj.setMainTableColumn(mainTableColumn);
//			}else if("extendColumn".equals(flag)){  //��չ�ֶ�
//				SystemMainTableExtColumn extendTableColumn = smtService.findSystemMainTableExtColumnByColumnId(itemId);
//				obj.setExtendTableColumn(extendTableColumn);
//			}
		}
		obj.setName(name);
		obj.setOrderFlag(new Integer(order));
		templateService.saveTemplateItem(obj);
		
	}
	/**
	 * ����spring����ķ���service
	 * 
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
