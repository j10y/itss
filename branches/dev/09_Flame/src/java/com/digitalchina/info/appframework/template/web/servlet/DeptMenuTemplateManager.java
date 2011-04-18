package com.digitalchina.info.appframework.template.web.servlet;

import com.digitalchina.info.appframework.template.service.DeptTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

/**
 * �첽�������Ų˵�Servlet
 * @Class Name DeptMenuTemplateManager
 * @author hp
 * @Create In Oct 24, 2008
 * TODO
 */
public class DeptMenuTemplateManager {
	
	private DeptTemplateMenuService deptTemplateMenuService = (DeptTemplateMenuService) getBean("deptTemplateMenuService");
	
	/**
	 * �첽���²˵�����
	 * @param id
	 * @param title
	 * @return true-�޸ĳɹ� false-�޸�ʧ��
	 */
	public Boolean ajaxUpdateTitle(String id,String menuName){
		deptTemplateMenuService.modifyMenuName(id, menuName);
		return true;
	}
	/**
	 * �첽ɾ�����ݣ�����������ڵ�
	 * @param id
	 * @param title
	 */
	public void ajaxRemoveNode(String id){
		deptTemplateMenuService.removeNode(id);
	}
	/**
	 * �첽�ƶ�ָ���ڵ�
	 * @param id	ָ���Ľڵ��id
	 * @param oldParentId	�ڵ��ƶ�ǰ���ڵĸ��ڵ�
	 * @param newParentId	�ڵ��ƶ����Ŀ�길�ڵ�
	 * @param nodeIndex		�ڵ��ƶ����Ŀ��λ��
	 */
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex){
		deptTemplateMenuService.saveNodeMove(id, oldParentId, newParentId, nodeIndex);
	}
	
	/**
	 * ���ý���Ƿ�ɼ�
	 * TODO
	 * Sep 4, 2008 By hp
	 * @param id
	 * @param enabled TODO
	 */
	public void ajaxEnableNode(String nodeId, String enabled){
		deptTemplateMenuService.saveNodeEnabled(nodeId, enabled);
		
	}
	
	/**
	 * ����spring����ķ���service
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
