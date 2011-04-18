package com.digitalchina.info.appframework.template.web.servlet;

import com.digitalchina.info.appframework.template.service.UserTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

/**
 * dwr方式管理用户菜单模板
 * @Class Name UserMenuTemplateManager
 * @author hp
 * @Create In Oct 24, 2008
 * TODO
 */
public class UserMenuTemplateManager {
	
	private UserTemplateMenuService userMenuService = (UserTemplateMenuService) getBean("userTemplateMenuService");
	
	/**
	 * 异步更新菜单标题
	 * @param id
	 * @param title
	 * @return true-修改成功 false-修改失败
	 */
	public Boolean ajaxUpdateTitle(String id,String menuName){
		userMenuService.modifyMenuName(id, menuName);
		return true;
	}
	/**
	 * 异步删除数据，包括其子孙节点
	 * @param id
	 * @param title
	 */
	public void ajaxRemoveNode(String id){
		userMenuService.removeNode(id);
	}
	/**
	 * 异步移动指定节点
	 * @param id	指定的节点的id
	 * @param oldParentId	节点移动前所在的父节点
	 * @param newParentId	节点移动后的目标父节点
	 * @param nodeIndex		节点移动后的目标位置
	 */
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex){
		userMenuService.saveNodeMove(id, oldParentId, newParentId, nodeIndex);
	}
	
	/**
	 * 设置结点是否可见
	 * TODO
	 * Sep 4, 2008 By hp
	 * @param nodeId 指定结点ID
	 * @param enabled 	是否可见：1表示可见，0表示隐藏
	 */
	public void ajaxEnableNode(String nodeId, String enabled){
		userMenuService.saveNodeEnabled(nodeId, enabled);
		
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
