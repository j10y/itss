/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.info.framework.security.service.implMenuServiceImpl.java
 * @Create By zhangpeng
 * @Create In 2008-7-17 下午03:12:39
 * TODO
 */
package com.zsgj.info.appframework.template.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zsgj.info.appframework.template.entity.Menu;
import com.zsgj.info.appframework.template.service.MenuService;
import com.zsgj.info.framework.dao.BaseDao;

/**
 * @Class Name MenuServiceImpl
 * @Author zhangpeng
 * @Create In 2008-7-17
 */
public class MenuServiceImpl extends BaseDao implements MenuService {

	
	public List<Menu> findChildenByParent(String parentMenuId){
		Menu parent = this.get(Menu.class, Long.valueOf(parentMenuId));
		String hql = "select m from Menu m where m.parentMenu=?";
		List list = super.find(hql, parent);
		return list;
	}

	public Menu modifyMenuName(String menuId, String menuName) {
		Menu menu = this.get(Menu.class, Long.valueOf(menuId));
		menu.setMenuName(menuName);
		this.saveMenu(menu);
		return menu;
	}

	/**
	 * 删除菜单节点：先下移，再删除（级联删除子节点）
	 */
	public void removeNode(String menuId) {
		Menu menu = this.get(Menu.class, Long.valueOf(menuId));
		this.downNode(menu.getParentMenu().getId(), menu.getMenuOrder(), -1);
		this.removeMenu(menuId);
	}

	/**
	 * 指定的节点下移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点移动发生时所在的位置
	 * @param maxIndex	指定节点要移动到的目标位置
	 */
	public void downNode(Long parentId, Integer minIndex, Integer maxIndex){
		Menu parent = this.get(Menu.class, parentId);
		// 指定的节点下移，意味着其范围内的节点各自减1
		StringBuffer hql = new StringBuffer("update Menu m set m.menuOrder=m.menuOrder-1 where m.parentMenu = ?");
		List paramsList = new ArrayList();
		paramsList.add(parent);
		
		if(maxIndex != -1){
			hql.append(" and m.menuOrder <= ? ");
			paramsList.add(maxIndex);
		}
		if(minIndex != -1){
			hql.append(" and m.menuOrder > ? ");
			paramsList.add(minIndex);
		}		
		Object[] params = paramsList.toArray();
		super.executeUpdate(hql.toString(), params/*new Object[]{parent, maxIndex, minIndex}*/);
	}
	
	/**
	 * 指定的节点上移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点要移动到的目标位置
	 * @param maxIndex	指定节点移动发生时所在的位置
	 */
	@SuppressWarnings("unchecked")
	public void upNode(Long parentId, Integer minIndex, Integer maxIndex){
		Menu parent = this.get(Menu.class, parentId);
		// 指定的节点下移，意味着其范围内的节点各自减1
		StringBuffer hql = new StringBuffer("update Menu m set m.menuOrder=m.menuOrder+1 where m.parentMenu = ?");
		List paramsList = new ArrayList();
		paramsList.add(parent);
		
		if(maxIndex != -1){
			hql.append(" and m.menuOrder < ? ");
			paramsList.add(maxIndex);
		}
		if(minIndex != -1){
			hql.append(" and m.menuOrder >= ? ");
			paramsList.add(minIndex);
		}		
		Object[] params = paramsList.toArray();
		super.executeUpdate(hql.toString(), params);
	}
	
	public void saveNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {
		
		Long menuId = Long.valueOf(mId);
		Long oldParentId = Long.valueOf(oldPid);
		Long newParentId = Long.valueOf(newPid);
		int nodeIndex = Integer.parseInt(nodeIndx);
		Menu obj = this.get(Menu.class, menuId);
		Menu newParent = super.get(Menu.class, newParentId);
		int minIndex = obj.getMenuOrder().intValue();
		int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
		if(oldParentId == newParentId && minIndex != maxIndex){
			// 在同一个父节点下发生移动
			if(minIndex < maxIndex){
				// 当要移动的节点的序号小于要移动到的目标序号，则下移
				this.downNode(oldParentId, minIndex, maxIndex);
			}else if(minIndex > maxIndex){
				// 当要移动的节点的序号大于要移动到的目标序号，则上移
				maxIndex = minIndex;
				minIndex = nodeIndex;
				this.upNode(oldParentId, minIndex, maxIndex);
			}
			// 节点本身的序号设置成要移动到的目标序号
			obj.setMenuOrder(nodeIndex);
			this.saveMenu(obj);
		}
		if(oldParentId != newParentId){
			// 在不同父节点下发生移动
			//1、相当于要移动的节点在原父节点下下移到最后再删除掉，因此要指定移动发生时节点所在的位置
			this.downNode(oldParentId, minIndex, -1);
			//2、相当于要移动的节点在新父节点下上移到指定的位置，因此需要指定要移动到的位置
			this.upNode(newParentId, maxIndex, -1);
			// 节点本身的序号设置成要移动到的目标序号
			obj.setMenuOrder(nodeIndex);
			obj.setParentMenu(newParent);
			this.saveMenu(obj);
		}
		
	}

	public List<Menu> findAllMenu() {
		return this.getObjects(Menu.class);
	}


	public Menu findMenuById(String id) {
		Menu menu = null;
		menu = this.get(Menu.class, Long.valueOf(id));
		return menu;
	}


	public Menu saveMenu(Menu menu) {
		Menu result = null;
		result = (Menu) super.save(menu);
		/*String hql="select mt.id from MenuTemplate mt";
		List menuTemplates = super.find(hql, null);
		Iterator iter = menuTemplates.iterator();
		while(iter.hasNext()){
			Long mtId = (Long) iter.next();
			MenuTemplate mt = new MenuTemplate(mtId);
			String hql2 = "select tm from TemplateMenu tm where tm.template=?";
			List templateMenus = super.find(hql2, new Object[]{mt});
			Iterator iterTm = templateMenus.iterator();
			while(iterTm.hasNext()){
				TemplateMenu tm = (TemplateMenu) iterTm.next();
			}
		}*/
		return result;
	}
	

	public List<Menu> findMenusByName(String name) {
		List list = null;
		list = super.findBy(Menu.class, "menuName", name);
		return list;
	}

	public void removeMenu(String menuId) {
		Menu menu = this.get(Menu.class, Long.valueOf(menuId));
		this.executeUpdate("delete from Menu m where m.parentMenu=?", menu);
		super.removeById(Menu.class, Long.valueOf(menuId));
	}

}
