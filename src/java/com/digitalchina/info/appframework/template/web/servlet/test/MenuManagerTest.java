package com.digitalchina.info.appframework.template.web.servlet.test;

import com.digitalchina.info.appframework.template.service.MenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;


public class MenuManagerTest {
	
	private MenuService menuService = (MenuService)getBean("menuService");
	
	/**
	 * �첽���²˵�����
	 * @param id
	 * @param title
	 * @return true-�޸ĳɹ� false-�޸�ʧ��
	 */
	public Boolean ajaxUpdateTitle(String id,String menuName){
		menuService.modifyMenuName(id, menuName);
		return true;
	}
	/**
	 * �첽ɾ�����ݣ�����������ڵ�
	 * @param id
	 * @param title
	 */
	public void ajaxRemoveNode(String id){
		menuService.removeNode(id);
	}
	/**
	 * �첽�ƶ�ָ���ڵ�
	 * @param id	ָ���Ľڵ��id
	 * @param oldParentId	�ڵ��ƶ�ǰ���ڵĸ��ڵ�
	 * @param newParentId	�ڵ��ƶ����Ŀ�길�ڵ�
	 * @param nodeIndex		�ڵ��ƶ����Ŀ��λ��
	 */
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex){
		menuService.saveNodeMove(id, oldParentId, newParentId, nodeIndex);
	}
	
	/**
	 * ����spring����ķ���service
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
