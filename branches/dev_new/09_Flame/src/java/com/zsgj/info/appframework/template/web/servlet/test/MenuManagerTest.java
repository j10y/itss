package com.zsgj.info.appframework.template.web.servlet.test;

import com.zsgj.info.appframework.template.entity.Menu;
import com.zsgj.info.appframework.template.service.MenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;


@SuppressWarnings("deprecation")
public class MenuManagerTest {
	
	private MenuService menuService = (MenuService)getBean("menuService");
	
	/**
	 * 异步更新菜单标题
	 * @param id
	 * @param title
	 * @return true-修改成功 false-修改失败
	 */
	public Boolean ajaxUpdateTitle(String id,String menuName){
		Menu mu = menuService.modifyMenuName(id, menuName);
		return mu != null ? true : false;
	}
	/**
	 * 异步删除数据，包括其子孙节点
	 * @param id
	 * @param title
	 */
	public void ajaxRemoveNode(String id){
		menuService.removeNode(id);
	}
	/**
	 * 异步移动指定节点
	 * @param id	指定的节点的id
	 * @param oldParentId	节点移动前所在的父节点
	 * @param newParentId	节点移动后的目标父节点
	 * @param nodeIndex		节点移动后的目标位置
	 */
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex){
		menuService.saveNodeMove(id, oldParentId, newParentId, nodeIndex);
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
