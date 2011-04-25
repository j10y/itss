package com.digitalchina.info.appframework.template.web.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.template.entity.UserMenuItem;
import com.digitalchina.info.appframework.template.service.UserTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

/**
 * DWR方式管理导航菜单
 * @Class Name NavigateMenuManager
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
public class NavigateMenuManager {
	
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
	 * @param id
	 * @param enabled TODO
	 */
	public void ajaxEnableNode(String nodeId, String enabled){
		userMenuService.saveNodeEnabled(nodeId, enabled);
		
	}
	
	/**
	 * 查找指定用户的导航栏的标题
	 * TODO
	 * Sep 8, 2008 By hp
	 * @param userId
	 * @return JSON 字符串，格式:{["id":1,"name":"name1"],["id":2,"name":"name2"]}
	 */
	public String ajaxFindNavigateTitles(String userId){
		
		List<UserMenuItem> itemList = userMenuService.findAllMenuTitleByUserId(userId);
		String result = "";
		for(int i = 0; i< itemList.size(); i++){
			UserMenuItem item = (UserMenuItem)itemList.get(i);
			Long id = item.getId();
			String text = item.getMenuName();
			result += "[id :"+id+",text:\""+text+"\"],";
		}
		result = "["+result.substring(0, result.length()-1)+"]";
		
		return  result;
	}
	
	public List ajaxFindMenuNodes(String nodeId,String userId){
		List returnList = new ArrayList();
			List<UserMenuItem> itemList = userMenuService.findChildenByParentAndUserId(nodeId, userId);
			if (itemList != null && itemList.size() > 0) {
				for (int i = 0, len = itemList.size(); i < len; i++) {
					UserMenuItem item = (UserMenuItem) itemList.get(i);
					Map map = new HashMap();
					map.put("id", item.getId());
					map.put("text", item.getMenuName());
//					map.put("parentId", item.getParentId());
					map.put("leaf", item.getLeafFlag());
					map.put("expanded", true);
					returnList.add(map);
				}
			}	
			return  returnList;
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
