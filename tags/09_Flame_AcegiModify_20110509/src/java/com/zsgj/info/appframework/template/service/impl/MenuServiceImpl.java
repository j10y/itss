/**
 * @Probject Name: 10_InfoFramework_B2B
 * @Path: com.digitalchina.info.framework.security.service.implMenuServiceImpl.java
 * @Create By zhangpeng
 * @Create In 2008-7-17 ����03:12:39
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
	 * ɾ���˵��ڵ㣺�����ƣ���ɾ��������ɾ���ӽڵ㣩
	 */
	public void removeNode(String menuId) {
		Menu menu = this.get(Menu.class, Long.valueOf(menuId));
		this.downNode(menu.getParentMenu().getId(), menu.getMenuOrder(), -1);
		this.removeMenu(menuId);
	}

	/**
	 * ָ���Ľڵ�����
	 * @param parentId	ָ����Χ��Ҫ�ƶ��Ľڵ�ĸ��ڵ�
	 * @param minIndex	ָ���ڵ��ƶ�����ʱ���ڵ�λ��
	 * @param maxIndex	ָ���ڵ�Ҫ�ƶ�����Ŀ��λ��
	 */
	public void downNode(Long parentId, Integer minIndex, Integer maxIndex){
		Menu parent = this.get(Menu.class, parentId);
		// ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
	 * ָ���Ľڵ�����
	 * @param parentId	ָ����Χ��Ҫ�ƶ��Ľڵ�ĸ��ڵ�
	 * @param minIndex	ָ���ڵ�Ҫ�ƶ�����Ŀ��λ��
	 * @param maxIndex	ָ���ڵ��ƶ�����ʱ���ڵ�λ��
	 */
	@SuppressWarnings("unchecked")
	public void upNode(Long parentId, Integer minIndex, Integer maxIndex){
		Menu parent = this.get(Menu.class, parentId);
		// ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
			// ��ͬһ�����ڵ��·����ƶ�
			if(minIndex < maxIndex){
				// ��Ҫ�ƶ��Ľڵ�����С��Ҫ�ƶ�����Ŀ����ţ�������
				this.downNode(oldParentId, minIndex, maxIndex);
			}else if(minIndex > maxIndex){
				// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
				maxIndex = minIndex;
				minIndex = nodeIndex;
				this.upNode(oldParentId, minIndex, maxIndex);
			}
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
			obj.setMenuOrder(nodeIndex);
			this.saveMenu(obj);
		}
		if(oldParentId != newParentId){
			// �ڲ�ͬ���ڵ��·����ƶ�
			//1���൱��Ҫ�ƶ��Ľڵ���ԭ���ڵ������Ƶ������ɾ���������Ҫָ���ƶ�����ʱ�ڵ����ڵ�λ��
			this.downNode(oldParentId, minIndex, -1);
			//2���൱��Ҫ�ƶ��Ľڵ����¸��ڵ������Ƶ�ָ����λ�ã������Ҫָ��Ҫ�ƶ�����λ��
			this.upNode(newParentId, maxIndex, -1);
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
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
