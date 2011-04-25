package com.digitalchina.info.appframework.template.web.servlet;

import com.digitalchina.info.appframework.template.service.SystemTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;


public class SystemMenuTemplateManager {
	
	private SystemTemplateMenuService systemMenuService = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	
	/**
	 * 异步更新菜单标题
	 * @param id
	 * @param title
	 * @return true-修改成功 false-修改失败
	 */
	public Boolean ajaxUpdateTitle(String id,String menuName){
		systemMenuService.modifyMenuName(id, menuName);
		return true;
	}
	/**
	 * 异步删除数据，包括其子孙节点
	 * @param id
	 * @param title
	 */
	public void ajaxRemoveNode(String id){
		systemMenuService.removeNode(id);
	}
	/**
	 * 异步移动指定节点
	 * @param id	指定的节点的id
	 * @param oldParentId	节点移动前所在的父节点
	 * @param newParentId	节点移动后的目标父节点
	 * @param nodeIndex		节点移动后的目标位置
	 */
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex){
		systemMenuService.saveNodeMove(id, oldParentId, newParentId, nodeIndex);
	}
	
	/**
	 * 返回spring管理的服务service
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
}
