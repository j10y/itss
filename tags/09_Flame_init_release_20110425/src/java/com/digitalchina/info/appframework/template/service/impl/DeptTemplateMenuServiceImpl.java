package com.digitalchina.info.appframework.template.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.appframework.template.entity.DeptMenuTemplate;
import com.digitalchina.info.appframework.template.entity.DeptMenuTemplateItem;
import com.digitalchina.info.appframework.template.entity.SystemMenuTemplate;
import com.digitalchina.info.appframework.template.entity.SystemMenuTemplateItem;
import com.digitalchina.info.appframework.template.entity.UserMenu;
import com.digitalchina.info.appframework.template.entity.UserMenuItem;
import com.digitalchina.info.appframework.template.service.DeptTemplateMenuService;
import com.digitalchina.info.appframework.template.service.UserTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.UserInfo;

public class DeptTemplateMenuServiceImpl extends BaseDao implements DeptTemplateMenuService {

	@SuppressWarnings("unchecked")
	public void saveDeptSystemTemplateChange(DeptMenuTemplate dmt, SystemMenuTemplate smtNew) {
		Criteria c = super.getCriteria(DeptMenuTemplate.class);
		c.add(Restrictions.eq("id", dmt.getId()));
		c.setFetchMode("systemMenuTemplate", FetchMode.JOIN);
		DeptMenuTemplate currentModifyDmt = (DeptMenuTemplate) c.uniqueResult();
		@SuppressWarnings("unused")
		SystemMenuTemplate smtOld = currentModifyDmt.getSystemMenuTemplate();
		//先改用户的，先找使用了当前部门模板的用户模板
		String hql="select um from UserMenu um where um.deptMenuTemplate=?";
		List<UserMenu> list = super.find(hql, currentModifyDmt);
		
		Set userNeedMerge = new HashSet();
		for(UserMenu um: list){
			userNeedMerge.add(um.getUserInfo());
			//删除用户使用的部门模板项目的用户菜单项目，保留原来的用户模板
			super.executeUpdate("delete from UserMenuItem umi where umi.userMenu=?" , um);
		}
		//删除当前部门菜单模板下面所有的菜单项目
		super.executeUpdate("delete from DeptMenuTemplateItem umi where umi.deptMenuTemplate=?" , currentModifyDmt);
		
		//使用新的系统菜单模板更新部门菜单模板项目，将新的部门菜单项放到当前部门菜单模板上
		//*****************************************************************************************************
		this.saveDeptMenuItemFromSystem(currentModifyDmt, smtNew);
		//使用新的部门菜单模板项目更新用户菜单模板项目, 同时考虑merge问题
		//********更新用户菜单项***********************************************************************************
		for(UserMenu userMenu: list){
			
			Criteria cd = super.getCriteria(DeptMenuTemplateItem.class);
			cd.add(Restrictions.isNull("parentMenu"));
			cd.setFetchMode("childMenus", FetchMode.JOIN);
			cd.add(Restrictions.eq("deptMenuTemplate", currentModifyDmt));
			cd.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			List listcd = cd.list();
			Iterator rootIterator = listcd.iterator();
			while(rootIterator.hasNext()){
				DeptMenuTemplateItem currUserMenu = (DeptMenuTemplateItem) rootIterator.next();
				
				UserMenuItem userMenuItem = new UserMenuItem();
				
				userMenuItem.setDeptMenuTemplateItem(currUserMenu);
				userMenuItem.setUserMenu(userMenu);
				userMenuItem.setEnabled(currUserMenu.getEnabled());
				userMenuItem.setLeafFlag(currUserMenu.getLeafFlag());
				userMenuItem.setMenuLevel(currUserMenu.getMenuLevel());
				userMenuItem.setMenuName(currUserMenu.getMenuName());
				userMenuItem.setMenuOrder(currUserMenu.getMenuOrder());
				userMenuItem.setMenuUrl(currUserMenu.getMenuUrl());
				super.save(userMenuItem);
				super.evict(userMenuItem);
				//更新用户菜单项，子菜单项
				this.initDeptItemFromSysItem(userMenu, userMenuItem, currUserMenu);
			}//end_rootIterator
		}//end_for(UserMenu userMenu: list)
		//merge 多套使用用户菜单模板的currentModifyDmt
		
		Iterator iterUserNeedMerge = userNeedMerge.iterator();
		while(iterUserNeedMerge.hasNext()){
			UserInfo userInfo = (UserInfo) iterUserNeedMerge.next();
			String hql2 = "select count(*) from UserMenu um where um.userInfo=? ";
			Query query = super.createQuery(hql2, new Object[]{userInfo});
			Long count = (Long) query.uniqueResult();
			if(count.intValue()>1){
				
				Set userDeptTemplates = new HashSet();
				//获取所有的部门模板
				
				Set userDeptTemplateItems = new HashSet();
				
//				Criteria cud = super.getCriteria(UserMenuItem.class);
//				cud.add(Restrictions.eq("userInfo", userInfo));//***********************************************
//				cud.setFetchMode("deptMenuTemplateItem", FetchMode.JOIN);
//				List listCud = cud.list();
//				Iterator iterCud = listCud.iterator();
//				while(iterCud.hasNext()){
//					UserMenuItem item = (UserMenuItem) iterCud.next();
//					DeptMenuTemplateItem deptMenuTemplateItem = item.getDeptMenuTemplateItem();
//					boolean exits = this.exitsDeptMenuItem(userDeptTemplateItems, deptMenuTemplateItem);
//					if(!exits){
//						System.out.println("current item:"+ item.getMenuName()+" not exits, add in");
//						userDeptTemplateItems.add(item);
//					}else{
//						System.out.println("current item:"+ item.getMenuName()+" exits, push out");
//					}
//				}
				
			}
		}
		//删除用户菜单项目
		
		//开始merge
		
	}

	private boolean exitsDeptMenuItem(Set userDeptTemplateItems, DeptMenuTemplateItem dmti){
		boolean result = false;
		String menuNameCurrent = dmti.getMenuName();
		String menuUrlCurrent = dmti.getMenuUrl();
		/*System.out.println("current loop:"+ menuNameCurrent+"/ "+ menuUrlCurrent);*/
		
		Iterator iter2 = userDeptTemplateItems.iterator();
		while(iter2.hasNext()){
			DeptMenuTemplateItem deptMenuTemplateItem = (DeptMenuTemplateItem) iter2.next();
			DeptMenuTemplate deptMenuTemplate = deptMenuTemplateItem.getDeptMenuTemplate();
			String menuName = deptMenuTemplateItem.getMenuName();
			String menuUrl = deptMenuTemplateItem.getMenuUrl();
			System.out.println("current loop:"+ menuName+"/ "+ menuUrl);
			DeptMenuTemplateItem parentMenu = deptMenuTemplateItem.getParentMenu();
			if(StringUtils.isNotBlank(menuUrl)){ //如果菜单有连接，用菜单连接判断是否等同
				result = menuUrl.equalsIgnoreCase(menuUrlCurrent);
				if(result == true) break; //只有是存在一样的就返回，说明已经存在
			}else{
				result = menuName.equalsIgnoreCase(menuNameCurrent);
				if(result == true) break;
			}
		}
		return result;
	}
	
	//begin from userTmpMS
	/**
	 * 用部门菜单模板初始化用户菜单模板
	 * @Methods Name initDeptItemFromSysItem
	 * @Create In 2008-9-3 By sa
	 * @param userMenu 用户菜单模板
	 * @param cUserMenu 用户菜单模板项目
	 * @param currParenDeptItem 当前的部门菜单模板
	 * void 
	 */
	private void initDeptItemFromSysItem(UserMenu userMenu, UserMenuItem cUserMenu, DeptMenuTemplateItem currParenDeptItem){
		Criteria c = super.getCriteria(DeptMenuTemplateItem.class);
		c.add(Restrictions.eq("id", currParenDeptItem.getId()));
		c.setFetchMode("childMenus", FetchMode.JOIN);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		DeptMenuTemplateItem result = (DeptMenuTemplateItem) c.uniqueResult();
		Set childMenus = result.getChildMenus();
		Iterator iterC = childMenus.iterator();
		while(iterC.hasNext()){
			DeptMenuTemplateItem dmtItem = (DeptMenuTemplateItem) iterC.next();
			
			UserMenuItem dItem = new UserMenuItem();
			
			dItem.setDeptMenuTemplateItem(dmtItem);
			dItem.setUserMenu(userMenu);
			dItem.setEnabled(dmtItem.getEnabled());
			dItem.setLeafFlag(dmtItem.getLeafFlag());
			dItem.setMenuLevel(dmtItem.getMenuLevel());
			dItem.setMenuName(dmtItem.getMenuName());
			dItem.setMenuOrder(dmtItem.getMenuOrder());
			dItem.setMenuUrl(dmtItem.getMenuUrl());
			
			dItem.setParentMenu(cUserMenu);
			
			super.save(dItem);
			super.evict(dItem);
			
			initDeptItemFromSysItem(userMenu, dItem, dmtItem);
		}
	}
	//未使用
	private UserMenu saveUserMenu(UserMenu um) {
		UserMenu userMenu = null;
		if(um.getId()==null){
			String hql="select count(*) from UserMenu um where um.userInfo=?";
			Query query = super.createQuery(hql, new Object[]{um.getUserInfo()});
			Long count = (Long) query.uniqueResult();
			if(count.intValue()>0){
				throw new ServiceException("此用户已经具有一套模板，不能再增加新模板");
			}
			userMenu = (UserMenu) super.save(um);
			 
			if(userMenu.getId()==null){
				DeptMenuTemplate deptMenuTemplate = userMenu.getDeptMenuTemplate();
				
				Criteria c = super.getCriteria(DeptMenuTemplateItem.class);
				c.add(Restrictions.isNull("parentMenu"));
				c.setFetchMode("childMenus", FetchMode.JOIN);
				c.add(Restrictions.eq("deptMenuTemplate", deptMenuTemplate));
				c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				
				List list = c.list();
				Iterator rootIterator = list.iterator();
				while(rootIterator.hasNext()){
					DeptMenuTemplateItem currUserMenu = (DeptMenuTemplateItem) rootIterator.next();
					
					UserMenuItem userMenuItem = new UserMenuItem();
					
					userMenuItem.setDeptMenuTemplateItem(currUserMenu);
					userMenuItem.setUserMenu(userMenu);
					userMenuItem.setEnabled(currUserMenu.getEnabled());
					userMenuItem.setLeafFlag(currUserMenu.getLeafFlag());
					userMenuItem.setMenuLevel(currUserMenu.getMenuLevel());
					userMenuItem.setMenuName(currUserMenu.getMenuName());
					userMenuItem.setMenuOrder(currUserMenu.getMenuOrder());
					userMenuItem.setMenuUrl(currUserMenu.getMenuUrl());
					super.save(userMenuItem);
					super.evict(userMenuItem);
					
					this.initDeptItemFromSysItem(userMenu, userMenuItem, currUserMenu);
				}
			}
			
		}
		
		return userMenu;
	}
	
	//end userTmpMS
	public List findDeptMenuTemplate(Department dept) {
		Criteria c = super.getCriteria(DeptMenuTemplate.class);
		c.add(Restrictions.eq("dept", dept));
		c.setFetchMode("systemMenuTemplate", FetchMode.JOIN);
		List list = c.list();
		return list;
	}

	public List findDeptMenuTemplates() {
		List list = null;
		list = super.getObjects(DeptMenuTemplate.class);
		return list;
	}
	
	public DeptMenuTemplate findDeptMenuTemplateById(String dmtId) {
		DeptMenuTemplate dmt = super.get(DeptMenuTemplate.class, Long.valueOf(dmtId));
		return dmt;
	}
	
	public void removeDeptMenuTemplate(String smsId) {
		DeptMenuTemplate smt = super.get(DeptMenuTemplate.class, Long.valueOf(smsId));
		String hql="select um from UserMenu um where um.deptMenuTemplate=? ";
		List<UserMenu> ums = super.find(hql, smt);
		for(UserMenu umItem : ums){
			super.executeUpdate("delete UserMenuItem umi where umi.userMenu=?", umItem);
			super.executeUpdate("delete from UserMenu um where um.id=? ", umItem.getId());
		}
		super.executeUpdate("update Role role set role.deptMenuTemplate=null where role.deptMenuTemplate=?", new Object[]{smt});
		super.executeUpdate("delete DeptMenuTemplateItem smti where smti.deptMenuTemplate=?", new Object[]{smt});
		super.remove(smt);
	}

	public void removeDeptMenuTemplate(String[] dmtIds) {
		if(dmtIds==null|| dmtIds.length==0){
			throw new ServiceException("请选择要删除的部门菜单模板");
		}
		for(int i=0; i<dmtIds.length; i++){
			this.removeDeptMenuTemplate(dmtIds[i]);
		}
		
	}

	private void initChilden(SystemMenuTemplateItem sItem){
		Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
		c.add(Restrictions.eq("id", sItem.getId()));
		c.setFetchMode("childMenus", FetchMode.JOIN);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		SystemMenuTemplateItem result = (SystemMenuTemplateItem) c.uniqueResult();
		Set childMenus = result.getChildMenus();
		Iterator iterC = childMenus.iterator();
		while(iterC.hasNext()){
			SystemMenuTemplateItem cItem = (SystemMenuTemplateItem) iterC.next();
			initChilden(cItem);
		}
	}
	
	/**
	 * 用系统的菜单模板初始化部门的菜单模板
	 * @Methods Name initDeptItemFromSysItem
	 * @Create In 2008-9-3 By sa
	 * @param deptMenuTemplate 部门菜单模板
	 * @param cdItem 部门菜单模板项
	 * @param sItem 系统菜单模板项
	 * void
	 */
	private void initDeptItemFromSysItem(DeptMenuTemplate deptMenuTemplate, DeptMenuTemplateItem cdItem, SystemMenuTemplateItem sItem){
		Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
		c.add(Restrictions.eq("id", sItem.getId()));
		c.setFetchMode("childMenus", FetchMode.JOIN);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		SystemMenuTemplateItem result = (SystemMenuTemplateItem) c.uniqueResult();
		Set childMenus = result.getChildMenus();
		Iterator iterC = childMenus.iterator();
		while(iterC.hasNext()){
			SystemMenuTemplateItem scItem = (SystemMenuTemplateItem) iterC.next();
			
			DeptMenuTemplateItem dItem = new DeptMenuTemplateItem();
			
			dItem.setSystemMenuTemplateItem(scItem);
			dItem.setDeptMenuTemplate(deptMenuTemplate);
			dItem.setEnabled(Integer.valueOf(1));
			dItem.setLeafFlag(scItem.getLeafFlag());
			dItem.setMenuLevel(scItem.getMenuLevel());
			dItem.setMenuName(scItem.getMenuName());
			dItem.setMenuOrder(scItem.getMenuOrder());
			dItem.setMenuUrl(scItem.getMenuUrl());
			
			dItem.setParentMenu(cdItem);
			
			super.save(dItem);
			super.evict(dItem);
			
			initDeptItemFromSysItem(deptMenuTemplate, dItem, scItem);
		}
	}
	
	private void saveDeptMenuItemFromSystem(DeptMenuTemplate deptTemplate, SystemMenuTemplate systemMenuTemplate){
		Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
		c.add(Restrictions.isNull("parentMenu"));
		c.setFetchMode("childMenus", FetchMode.JOIN);
		c.add(Restrictions.eq("systemMenuTemplate", systemMenuTemplate));
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List list = c.list();
		Iterator rootIterator = list.iterator();
		while(rootIterator.hasNext()){
			SystemMenuTemplateItem topsItem = (SystemMenuTemplateItem) rootIterator.next();
			
			DeptMenuTemplateItem drootItem = new DeptMenuTemplateItem();
			
			drootItem.setDeptMenuTemplate(deptTemplate);
			drootItem.setEnabled(Integer.valueOf(1));
			drootItem.setLeafFlag(topsItem.getLeafFlag());
			drootItem.setMenuLevel(topsItem.getMenuLevel());
			drootItem.setMenuName(topsItem.getMenuName());
			drootItem.setMenuOrder(topsItem.getMenuOrder());
			drootItem.setMenuUrl(topsItem.getMenuUrl());
			drootItem.setSystemMenuTemplateItem(topsItem);
			super.save(drootItem);
			super.evict(drootItem);
			
			this.initDeptItemFromSysItem(deptTemplate, drootItem, topsItem);
		}
	}
	
	public DeptMenuTemplate saveDeptMenuTemplate(DeptMenuTemplate smt) {
		DeptMenuTemplate deptTemplate = null;
		boolean isInsert = false;
		if(smt.getId()==null){
			isInsert = true;		
		}
		deptTemplate = (DeptMenuTemplate) super.save(smt);
		
		if(isInsert){
			SystemMenuTemplate systemMenuTemplate = deptTemplate.getSystemMenuTemplate();
			this.saveDeptMenuItemFromSystem(deptTemplate, systemMenuTemplate);
			
			/*Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
			c.add(Restrictions.isNull("parentMenu"));
			c.setFetchMode("childMenus", FetchMode.JOIN);
			c.add(Restrictions.eq("systemMenuTemplate", systemMenuTemplate));
			c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			List list = c.list();
			Iterator rootIterator = list.iterator();
			while(rootIterator.hasNext()){
				SystemMenuTemplateItem topsItem = (SystemMenuTemplateItem) rootIterator.next();
				
				DeptMenuTemplateItem drootItem = new DeptMenuTemplateItem();
				
				drootItem.setDeptMenuTemplate(deptTemplate);
				drootItem.setEnabled(Integer.valueOf(1));
				drootItem.setLeafFlag(topsItem.getLeafFlag());
				drootItem.setMenuLevel(topsItem.getMenuLevel());
				drootItem.setMenuName(topsItem.getMenuName());
				drootItem.setMenuOrder(topsItem.getMenuOrder());
				drootItem.setMenuUrl(topsItem.getMenuUrl());
				drootItem.setSystemMenuTemplateItem(topsItem);
				super.save(drootItem);
				super.evict(drootItem);
				
				this.initDeptItemFromSysItem(deptTemplate, drootItem, topsItem);
			}*/
		}

		return deptTemplate;
	}

	public List<DeptMenuTemplateItem> findChildenByParent(String parentMenuId){
		DeptMenuTemplateItem parent = this.get(DeptMenuTemplateItem.class, Long.valueOf(parentMenuId));
		String hql = "select m from DeptMenuTemplateItem m where m.parentMenu=? order by m.menuOrder";
		List list = super.find(hql, parent);
		return list;
	}

	public DeptMenuTemplateItem modifyMenuName(String menuId, String menuName) {
		DeptMenuTemplateItem menu = this.get(DeptMenuTemplateItem.class, Long.valueOf(menuId));
		menu.setMenuName(menuName);
		this.saveMenu(menu);
		return menu;
	}

	/**
	 * 删除菜单节点：先下移，再删除（级联删除子节点）
	 */
	public void removeNode(String menuId) {
		DeptMenuTemplateItem menu = this.get(DeptMenuTemplateItem.class, Long.valueOf(menuId));
		this.downNode(menu.getParentMenu().getId(), menu.getMenuOrder(), -1);
		this.removeMenu(menuId);
	}

	//begin 加入移动用户菜单代码
	/**
	 * 指定的节点下移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点移动发生时所在的位置
	 * @param maxIndex	指定节点要移动到的目标位置
	 */
	public void downUserNode(Long parentId, Integer minIndex, Integer maxIndex){
		if(parentId!=null&& parentId.intValue()!=0){
			UserMenuItem parent = this.get(UserMenuItem.class, parentId);
			// 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update UserMenuItem m set m.menuOrder=m.menuOrder-1 where m.parentMenu = ?");
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
		}else{
			 //指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update UserMenuItem m set m.menuOrder=m.menuOrder-1 where m.parentMenu is null");
			List paramsList = new ArrayList();
			
			if(maxIndex != -1){
				hql.append(" and m.menuOrder <= ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.menuOrder > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		}
		
	}
	
	/**
	 * 指定的节点上移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点要移动到的目标位置
	 * @param maxIndex	指定节点移动发生时所在的位置
	 */
	@SuppressWarnings("unchecked")
	public void upUserNode(Long parentId, Integer minIndex, Integer maxIndex){
		if(parentId!=null&& parentId.intValue()!=0){
			UserMenuItem parent = this.get(UserMenuItem.class, parentId);
			// 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update UserMenuItem m set m.menuOrder=m.menuOrder+1 where m.parentMenu = ?");
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
		}else{
			StringBuffer hql = new StringBuffer("update UserMenuItem m set m.menuOrder=m.menuOrder+1 where m.parentMenu is null ");
			List paramsList = new ArrayList();
			//paramsList.add(parent);
			
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
	}
	//end 结束
	/**
	 * 指定的节点下移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点移动发生时所在的位置
	 * @param maxIndex	指定节点要移动到的目标位置
	 */
	public void downNode(Long parentId, Integer minIndex, Integer maxIndex){
		
		if(parentId!=null&& parentId.intValue()!=0){
			DeptMenuTemplateItem parent = null;
			Criteria c = super.getCriteria(DeptMenuTemplateItem.class);
			c.add(Restrictions.eq("id", parentId));
			parent = (DeptMenuTemplateItem) c.uniqueResult();
//			 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update DeptMenuTemplateItem m set m.menuOrder=m.menuOrder-1 where m.parentMenu = ?");
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
			super.executeUpdate(hql.toString(), params);
		}else{ //顶级菜单栏目移动顺序
			
//			 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update DeptMenuTemplateItem m set m.menuOrder=m.menuOrder-1 where m.parentMenu is null");
			List paramsList = new ArrayList();
			
			if(maxIndex != -1){
				hql.append(" and m.menuOrder <= ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.menuOrder > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		}
		
		
	}
	
	public void downDeptRootNode(DeptMenuTemplate dmt, Integer minIndex, Integer maxIndex){
			//指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer();
			hql.append("update DeptMenuTemplateItem m set m.menuOrder=m.menuOrder-1 ");
			hql.append("where m.deptMenuTemplate=? and m.parentMenu is null");
			List paramsList = new ArrayList();
			paramsList.add(dmt);
			
			if(maxIndex != -1){
				hql.append(" and m.menuOrder <= ? ");
				paramsList.add(maxIndex);
			}
			if(minIndex != -1){
				hql.append(" and m.menuOrder > ? ");
				paramsList.add(minIndex);
			}		
			Object[] params = paramsList.toArray();
			super.executeUpdate(hql.toString(), params);
		
	}
	
	public void upDeptRootNode(DeptMenuTemplate dmt, Integer minIndex, Integer maxIndex){
		//指定的节点下移，意味着其范围内的节点各自减1
		StringBuffer hql = new StringBuffer();
		hql.append("update DeptMenuTemplateItem m set m.menuOrder=m.menuOrder+1 ");
		hql.append("where m.deptMenuTemplate=? and m.parentMenu is null");
		List paramsList = new ArrayList();
		paramsList.add(dmt);
		
		if(maxIndex != -1){
			hql.append(" and m.menuOrder <= ? ");
			paramsList.add(maxIndex);
		}
		if(minIndex != -1){
			hql.append(" and m.menuOrder > ? ");
			paramsList.add(minIndex);
		}		
		Object[] params = paramsList.toArray();
		super.executeUpdate(hql.toString(), params);
	
	}
	
	//将部门菜单模板项目的子目录移动到根上时，级联移动用户的，在下移用户菜单项到旧父栏目最底下再删除疑惑，upUserNode改调这个方法；
	public void upUserRootNode(UserMenu userMenu, Integer minIndex, Integer maxIndex){
		//指定的节点下移，意味着其范围内的节点各自减1
		StringBuffer hql = new StringBuffer();
		hql.append("update UserMenuItem m set m.menuOrder=m.menuOrder+1 ");
		hql.append("where m.userMenu=? and m.parentMenu is null");
		List paramsList = new ArrayList();
		paramsList.add(userMenu);
		
		if(maxIndex != -1){
			hql.append(" and m.menuOrder <= ? ");
			paramsList.add(maxIndex);
		}
		if(minIndex != -1){
			hql.append(" and m.menuOrder > ? ");
			paramsList.add(minIndex);
		}		
		Object[] params = paramsList.toArray();
		super.executeUpdate(hql.toString(), params);
	
	}
	
	/**
	 * 指定的节点上移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点要移动到的目标位置
	 * @param maxIndex	指定节点移动发生时所在的位置
	 */
	@SuppressWarnings("unchecked")
	public void upNode(Long parentId, Integer minIndex, Integer maxIndex){

		if(parentId!=null&& parentId.intValue()!=0){
			DeptMenuTemplateItem parent = this.get(DeptMenuTemplateItem.class, parentId);
//			 指定的节点下移，意味着其范围内的节点各自减1
			StringBuffer hql = new StringBuffer("update DeptMenuTemplateItem m set m.menuOrder=m.menuOrder+1 where m.parentMenu = ?");
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
		}else{
			StringBuffer hql = new StringBuffer("update DeptMenuTemplateItem m set m.menuOrder=m.menuOrder+1 where m.parentMenu is null ");
			List paramsList = new ArrayList();
			//paramsList.add(parent);
			
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
		
	}
	
	public void saveNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {
		
		if(oldPid!=null&& !oldPid.equalsIgnoreCase("0")){//原始父菜单不是0，即不是根
			if(newPid!=null&& !newPid.equalsIgnoreCase("0")){ //从子目录移动到另一个子目录##############
				Long menuId = Long.valueOf(mId);
				Long oldParentId = Long.valueOf(oldPid);
				Long newParentId = Long.valueOf(newPid);
				int nodeIndex = Integer.parseInt(nodeIndx);
				DeptMenuTemplateItem obj = this.get(DeptMenuTemplateItem.class, menuId);
				DeptMenuTemplateItem newParent = super.get(DeptMenuTemplateItem.class, newParentId);
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				if(oldParentId.intValue() == newParentId.intValue() && minIndex != maxIndex){
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
				if(oldParentId.intValue() != newParentId.intValue()){
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
				//begin自动移动用户菜单项目
				DeptMenuTemplate smt = obj.getDeptMenuTemplate();
				String hql="select dmt from UserMenu dmt where dmt.deptMenuTemplate=?";
				List<UserMenu> dmts = super.find(hql, smt);
				for(UserMenu dmt: dmts){
					//当前移动的用户菜单
					Criteria c = super.getCriteria(UserMenuItem.class);
					c.add(Restrictions.eq("deptMenuTemplateItem", obj));
					c.add(Restrictions.eq("userMenu", dmt));
					UserMenuItem dmti = (UserMenuItem) c.uniqueResult();
					if(dmti!=null){
						Long deptcmId = dmti.getId();
						//新的父节点菜单
						Criteria cParent = super.getCriteria(UserMenuItem.class);
						cParent.add(Restrictions.eq("deptMenuTemplateItem", newParent));
						cParent.add(Restrictions.eq("userMenu", dmt));
						UserMenuItem newDmtiParent = (UserMenuItem) cParent.uniqueResult();
						
						if(newDmtiParent!=null){
							//旧的父节点菜单
							Long newUserParentId = newDmtiParent.getId();
							Criteria cOldParent = super.getCriteria(UserMenuItem.class);
							cOldParent.add(Restrictions.eq("deptMenuTemplateItem", new DeptMenuTemplateItem(oldParentId)));
							cOldParent.add(Restrictions.eq("userMenu", dmt));
							UserMenuItem dmtiOldParent = (UserMenuItem) cOldParent.uniqueResult();
							if(dmtiOldParent!=null){
								Long oldUserParentId = dmtiOldParent.getId();
//								UserTemplateMenuService utms = (UserTemplateMenuService) ContextHolder.getBean("userTemplateMenuService");
//								utms.saveNodeMove(String.valueOf(deptcmId), String.valueOf(oldUserParentId), 	String.valueOf(newUserParentId), nodeIndx);
									
//***************************************************************************
								this.saveUserNodeMove(String.valueOf(deptcmId), String.valueOf(oldUserParentId), String.valueOf(newUserParentId), nodeIndx);
//										
							}
							
						}else{
							this.saveUserNodeMove(String.valueOf(deptcmId), "x", null, nodeIndx);
						}
					}
					
					
				}
				//end自动移动用户菜单项
			}else{ //从子目录移动到根：目标菜单是根,源菜单不是根############## 从子目录移动到根，目前问题移动后用户的都显示在最底下 ##############
				
				Long menuId = Long.valueOf(mId);
				Long oldParentId = Long.valueOf(oldPid);
				//Long newParentId = Long.valueOf(newPid);
				int nodeIndex = Integer.parseInt(nodeIndx);
				
				DeptMenuTemplateItem obj = this.get(DeptMenuTemplateItem.class, menuId);
				DeptMenuTemplateItem oldParent = this.get(DeptMenuTemplateItem.class, oldParentId); //obj.getParentMenu();
				if(obj.getEnabled()!=null&& obj.getEnabled().intValue()==0){
					throw new ServiceException("当前部门菜单项被设置为不可见，不可以移动位置");
				}
				DeptMenuTemplate dmt = obj.getDeptMenuTemplate();
				//DeptMenuTemplateItem newParent = super.get(DeptMenuTemplateItem.class, newParentId);
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				
//				 在不同父节点下发生移动
				//1、相当于要移动的节点在原父节点下下移到最后再删除掉，因此要指定移动发生时节点所在的位置
				this.downNode(oldParentId, minIndex, -1);
				//2、相当于要移动的节点在新父节点下上移到指定的位置，因此需要指定要移动到的位置
				this.upDeptRootNode(dmt, maxIndex, -1);
				// 节点本身的序号设置成要移动到的目标序号
				obj.setMenuOrder(nodeIndex);
				obj.setParentMenu(null);
				this.saveMenu(obj);
				
				//移动用户的
				String hql="select dmt from UserMenu dmt where dmt.deptMenuTemplate=?";
				List<UserMenu> dmts = super.find(hql, dmt);
				for(UserMenu userMenu: dmts){
					Criteria c = super.getCriteria(UserMenuItem.class);
					c.add(Restrictions.eq("deptMenuTemplateItem", obj));
					c.add(Restrictions.eq("userMenu", userMenu));
					UserMenuItem userMenuItem = (UserMenuItem) c.uniqueResult();
					UserInfo userInfo = userMenu.getUserInfo();
					
					if(userMenuItem!=null){ //对用的用户菜单有才移动，没有移动是部门菜单项目隐藏了，用户在初始化时就得不到这个菜单项
						Long userMenuId = userMenuItem.getId(); //对应的当前用户菜单项
						
						Criteria cOldParent = super.getCriteria(UserMenuItem.class);
						cOldParent.add(Restrictions.eq("deptMenuTemplateItem", oldParent));
						cOldParent.add(Restrictions.eq("userMenu", userMenu));
						UserMenuItem oldParentUserMenuItem = (UserMenuItem) cOldParent.uniqueResult();
						if(oldParentUserMenuItem!=null){
							Long oldUserParentId = oldParentUserMenuItem.getId();
							
							this.saveUserNodeMove(String.valueOf(userMenuId), String.valueOf(oldUserParentId), null, nodeIndx);
						}
						
					}
					
				}
				
			}
			
		}else if(oldPid!=null&& oldPid.equalsIgnoreCase("0")){ //源菜单是根
			if(newPid!=null&& !newPid.equalsIgnoreCase("0")){ //目标父菜单不是根############## 从根移动到子目录 ##############
				DeptMenuTemplateItem newParent= super.get(DeptMenuTemplateItem.class, Long.valueOf(newPid));
//				将当前的用户菜单目录的parent置null
				executeUpdate("update DeptMenuTemplateItem set parentMenu=? where id=?", newParent, Long.valueOf(mId));
				//获取当前顶级菜单的最大order
				String hql="select max(umi.menuOrder) from DeptMenuTemplateItem umi where umi.parentMenu=?";
				Query query = super.createQuery(hql, newParent);
				Integer maxOrder = (Integer) query.uniqueResult();			
				//设置当前的order为最大的加1
				executeUpdate("update DeptMenuTemplateItem set menuOrder=? where id=?", new Object[]{maxOrder+1, Long.valueOf(mId)} );
				
				//移动用户菜单项目
				Long menuId = Long.valueOf(mId);
				DeptMenuTemplateItem obj = this.get(DeptMenuTemplateItem.class, menuId);
				
				Long newParentId = Long.valueOf(newPid);
				
				DeptMenuTemplate smt = obj.getDeptMenuTemplate();
				String hql2="select dmt from UserMenu dmt where dmt.deptMenuTemplate=?";
				List<UserMenu> dmts = super.find(hql2, smt);
				for(UserMenu dmt: dmts){
					Criteria c = super.getCriteria(UserMenuItem.class);
					c.add(Restrictions.eq("deptMenuTemplateItem", obj));
					c.add(Restrictions.eq("userMenu", dmt));
					UserMenuItem dmti = (UserMenuItem) c.uniqueResult();
					if(dmti!=null){
						Long deptcmId = dmti.getId();
						Criteria cParent = super.getCriteria(UserMenuItem.class);
						cParent.add(Restrictions.eq("deptMenuTemplateItem", newParent));
						cParent.add(Restrictions.eq("userMenu", dmt));
						UserMenuItem newUserParent = (UserMenuItem) cParent.uniqueResult();
						Long newUserParentId = newUserParent.getId();
						
						this.saveUserNodeMove(String.valueOf(deptcmId), null, 
									String.valueOf(newUserParentId), nodeIndx);
					}
				}
			}else{ //部门菜单项目，源菜单是根.目标菜单也是根############## 从根移动到根 ##############
				Long menuId = Long.valueOf(mId);
				int nodeIndex = Integer.parseInt(nodeIndx);
				//根节点下的一个子节点
				DeptMenuTemplateItem obj = this.get(DeptMenuTemplateItem.class, menuId);
				//根节点下节点所属的部门菜单模板
				DeptMenuTemplate dmt = obj.getDeptMenuTemplate();
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndx); //目标位置，即顶级菜单的最大排序号
//				 在同一个父节点下发生移动
				if(minIndex < maxIndex){
					// 当要移动的节点的序号小于要移动到的目标序号，则下移
					this.downDeptRootNode(dmt, minIndex, maxIndex);
				}else if(minIndex > maxIndex){
					// 当要移动的节点的序号大于要移动到的目标序号，则上移
					maxIndex = minIndex;
					minIndex = nodeIndex;
					this.upDeptRootNode(dmt, minIndex, maxIndex);
				}
				// 节点本身的序号设置成要移动到的目标序号
				obj.setMenuOrder(nodeIndex);
				this.saveMenu(obj);
				
				//移动用户菜单项目
				DeptMenuTemplate smt = obj.getDeptMenuTemplate();
				String hql2="select dmt from UserMenu dmt where dmt.deptMenuTemplate=?";
				List<UserMenu> dmts = super.find(hql2, smt);
				for(UserMenu um: dmts){
					Criteria c = super.getCriteria(UserMenuItem.class);
					c.add(Restrictions.eq("deptMenuTemplateItem", obj));
					c.add(Restrictions.eq("userMenu", um));
					UserMenuItem dmti = (UserMenuItem) c.uniqueResult();
					if(dmti!=null){
						Long userItemId = dmti.getId();
						this.saveRootUserNodeMove(String.valueOf(userItemId), um , nodeIndx);
					}
					
				}
				
			}
			
			
		}
		
		//end
		
	}

	//begin user menu move
	
	public void saveRootUserNodeMove(String mId, UserMenu userMenu, String nodeIndx) {
		Long menuId = Long.valueOf(mId);
		int nodeIndex = Integer.parseInt(nodeIndx);
		UserMenuItem obj = this.get(UserMenuItem.class, menuId);

		int minIndex = obj.getMenuOrder().intValue();
		int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
		if(minIndex != maxIndex){
			// 在同一个父节点下发生移动
			if(minIndex < maxIndex){
				// 当要移动的节点的序号小于要移动到的目标序号，则下移
				 //指定的节点下移，意味着其范围内的节点各自减1
				StringBuffer hql = new StringBuffer();
				hql.append("update UserMenuItem m set m.menuOrder=m.menuOrder-1 ");
				hql.append("where m.userMenu=? and m.parentMenu is null");
				List paramsList = new ArrayList();
				paramsList.add(userMenu);
				if(maxIndex != -1){
					hql.append(" and m.menuOrder <= ? ");
					paramsList.add(maxIndex);
				}
				if(minIndex != -1){
					hql.append(" and m.menuOrder > ? ");
					paramsList.add(minIndex);
				}		
				Object[] params = paramsList.toArray();
				super.executeUpdate(hql.toString(), params);
				//this.downUserNode(null, minIndex, maxIndex);
			}else if(minIndex > maxIndex){
				// 当要移动的节点的序号大于要移动到的目标序号，则上移
				maxIndex = minIndex;
				minIndex = nodeIndex;
				StringBuffer hql = new StringBuffer();
				hql.append("update UserMenuItem m set m.menuOrder=m.menuOrder+1 ");
				hql.append("where m.userMenu=? and m.parentMenu is null");
				
				List paramsList = new ArrayList();
				paramsList.add(userMenu);
				
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
				//this.upUserNode(null, minIndex, maxIndex);
			}
			// 节点本身的序号设置成要移动到的目标序号
			obj.setMenuOrder(nodeIndex);
			this.saveUserMenu(obj);
		}
		
	}
	
	public void saveUserNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {
		if(oldPid!=null){ 
			if(newPid!=null){ //子目录移动到子目录
				Long menuId = Long.valueOf(mId);
				Long oldParentId = Long.valueOf(oldPid);
				Long newParentId = Long.valueOf(newPid);
				int nodeIndex = Integer.parseInt(nodeIndx);
				UserMenuItem obj = this.get(UserMenuItem.class, menuId);
				UserMenuItem newParent = super.get(UserMenuItem.class, newParentId);
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				if(oldParentId == newParentId && minIndex != maxIndex){
					// 在同一个父节点下发生移动
					if(minIndex < maxIndex){
						// 当要移动的节点的序号小于要移动到的目标序号，则下移
						this.downUserNode(oldParentId, minIndex, maxIndex);
					}else if(minIndex > maxIndex){
						// 当要移动的节点的序号大于要移动到的目标序号，则上移
						maxIndex = minIndex;
						minIndex = nodeIndex;
						this.upUserNode(oldParentId, minIndex, maxIndex);
					}
					// 节点本身的序号设置成要移动到的目标序号
					obj.setMenuOrder(nodeIndex);
					this.saveUserMenu(obj);
				}
				if(oldParentId != newParentId){
					// 在不同父节点下发生移动
					//1、相当于要移动的节点在原父节点下下移到最后再删除掉，因此要指定移动发生时节点所在的位置
					this.downUserNode(oldParentId, minIndex, -1);
					//2、相当于要移动的节点在新父节点下上移到指定的位置，因此需要指定要移动到的位置
					this.upUserNode(newParentId, maxIndex, -1);
					// 节点本身的序号设置成要移动到的目标序号
					obj.setMenuOrder(nodeIndex);
					obj.setParentMenu(newParent);
					this.saveUserMenu(obj);
				}
			}else{ //目标null,######### 子目录移动到根，此处都移动到底部了，因此存在bug
				//begin resolve bug
				
				Long menuId = Long.valueOf(mId);
				Long oldParentId = Long.valueOf(oldPid);
				//Long newParentId = Long.valueOf(newPid);
				int nodeIndex = Integer.parseInt(nodeIndx);
				
				UserMenuItem obj = this.get(UserMenuItem.class, menuId);
				UserMenu userMenu = obj.getUserMenu();
				//UserMenuItem newParent = super.get(UserMenuItem.class, newParentId);
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				
				// 在不同父节点下发生移动
				//1、相当于要移动的节点在原父节点下下移到最后再删除掉，因此要指定移动发生时节点所在的位置
				this.downUserNode(oldParentId, minIndex, -1);
				//2、相当于要移动的节点在新父节点下上移到指定的位置，因此需要指定要移动到的位置
				this.upUserRootNode(userMenu, maxIndex, -1);
				// 节点本身的序号设置成要移动到的目标序号
				obj.setMenuOrder(nodeIndex);
				obj.setParentMenu(null);
				this.saveUserMenu(obj);
				//end
				
				//将当前的用户菜单目录的parent置null
				//executeUpdate("update UserMenuItem set parentMenu=null where id=?", Long.valueOf(mId));
				/*//获取当前顶级菜单的最大order
				String hql="select max(umi.menuOrder) from UserMenuItem umi where umi.parentMenu is null ";
				Query query = super.createQuery(hql, null);
				Integer maxOrder = (Integer) query.uniqueResult();			
				//设置当前的order为最大的加1
				executeUpdate("update UserMenuItem set menuOrder=? where id=?", new Object[]{maxOrder+1, Long.valueOf(mId)} );*/
			}
		}else{ //从根移动到下级目录项目
			if(newPid!=null){
				UserMenuItem newParent = super.get(UserMenuItem.class, Long.valueOf(newPid));
//				将当前的用户菜单目录的parent置null
				executeUpdate("update UserMenuItem set parentMenu=? where id=?", newParent, Long.valueOf(mId));
				//获取当前顶级菜单的最大order
				String hql="select max(umi.menuOrder) from UserMenuItem umi where umi.parentMenu=? ";
				Query query = super.createQuery(hql, newParent);
				Integer maxOrder = (Integer) query.uniqueResult();			
				//设置当前的order为最大的加1
				executeUpdate("update UserMenuItem set menuOrder=? where id=?", new Object[]{maxOrder+1, Long.valueOf(mId)} );
			}else{//是根上的菜单移动
				/*Long menuId = Long.valueOf(mId);
				int nodeIndex = Integer.parseInt(nodeIndx);
				UserMenuItem obj = this.get(UserMenuItem.class, menuId);
	
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				if(minIndex != maxIndex){
					// 在同一个父节点下发生移动
					if(minIndex < maxIndex){
						// 当要移动的节点的序号小于要移动到的目标序号，则下移
						this.downUserNode(null, minIndex, maxIndex);
					}else if(minIndex > maxIndex){
						// 当要移动的节点的序号大于要移动到的目标序号，则上移
						maxIndex = minIndex;
						minIndex = nodeIndex;
						this.upUserNode(null, minIndex, maxIndex);
					}
					// 节点本身的序号设置成要移动到的目标序号
					obj.setMenuOrder(nodeIndex);
					this.saveUserMenu(obj);
				}*/
				
			}
			
		}
		
		
	}
	
	public UserMenuItem saveUserMenu(UserMenuItem menu) {
		UserMenuItem result = null;
		result = (UserMenuItem) super.save(menu);
		return result;
	}
	//end
	public List<DeptMenuTemplateItem> findAllMenu() {
		return this.getObjects(DeptMenuTemplateItem.class);
	}


	public DeptMenuTemplateItem findMenuById(String id) {
		DeptMenuTemplateItem menu = null;
		Criteria c = super.getCriteria(DeptMenuTemplateItem.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		c.setFetchMode("systemMenuTemplateItem", FetchMode.JOIN);
		c.setFetchMode("parentMenu", FetchMode.JOIN);
		menu = (DeptMenuTemplateItem) c.uniqueResult();
		return menu;
	}


	public DeptMenuTemplateItem saveMenu(DeptMenuTemplateItem menu) {
		DeptMenuTemplateItem result = null;
		result = (DeptMenuTemplateItem) super.save(menu);
		
		return result;
	}
	

	public List<DeptMenuTemplateItem> findMenusByName(String name) {
		List list = null;
		list = super.findBy(DeptMenuTemplateItem.class, "menuName", name);
		return list;
	}

	public void removeMenu(String menuId) {
		DeptMenuTemplateItem menu = this.get(DeptMenuTemplateItem.class, Long.valueOf(menuId));
		this.executeUpdate("delete from DeptMenuTemplateItem m where m.parentMenu=?", menu);
		super.removeById(DeptMenuTemplateItem.class, Long.valueOf(menuId));
	}

	public List<DeptMenuTemplateItem> findChildenByParentAndDeptMenuTemplate(String parentMenuId, String dmtId) {
		
		DeptMenuTemplateItem dmti = super.get(DeptMenuTemplateItem.class, Long.valueOf(parentMenuId));
		DeptMenuTemplate dmt = super.get(DeptMenuTemplate.class, Long.valueOf(dmtId));
		String hql="select dmti from DeptMenuTemplateItem dmti where dmti.parentMenu =? and dmti.deptMenuTemplate=? order by dmti.menuOrder";
		List<DeptMenuTemplateItem> itemList = super.find(hql, new Object[]{dmti,dmt});
		return itemList;
	}

	public List<DeptMenuTemplateItem> findDeptMenuTemplateItemNoParent(String dmtId) {
		DeptMenuTemplate dmt = super.get(DeptMenuTemplate.class, Long.valueOf(dmtId));
		String hql="select dmti from DeptMenuTemplateItem dmti where dmti.parentMenu is null and dmti.deptMenuTemplate=? order by dmti.menuOrder";
		List<DeptMenuTemplateItem> itemList = super.find(hql, dmt);
		List<DeptMenuTemplateItem> returnList = new ArrayList<DeptMenuTemplateItem>();
		
		for(DeptMenuTemplateItem item:itemList){
			DeptMenuTemplateItem parentMenuItem = new DeptMenuTemplateItem();
			parentMenuItem.setId(new Long(0));
			item.setParentMenu(parentMenuItem);
			returnList.add(item);
		}
		
		return returnList;
	}

	public DeptMenuTemplateItem saveNodeEnabled(String nodeId, String  enabled) {
		Integer enabledFlag = new Integer(enabled);
		String hql="update DeptMenuTemplateItem dmti set dmti.enabled = ? where dmti.id =?";
		super.executeUpdate(hql, Integer.valueOf(enabledFlag), Long.valueOf(nodeId));
		DeptMenuTemplateItem dmti = super.get(DeptMenuTemplateItem.class, Long.valueOf(nodeId));
		
		String hql2="update UserMenuItem dmti set dmti.enabled = ? where deptMenuTemplateItem =?";
		super.executeUpdate(hql2, Integer.valueOf(enabledFlag), dmti);
		//this.saveUserNodeEnableWithDept(dmti);
		return dmti;
		
	}

	private void saveUserNodeEnableWithDept(DeptMenuTemplateItem dmti){
		String hql = "select dmti from UserMenuItem dmti where dmti.deptMenuTemplateItem=?";
		List<UserMenuItem> list = super.find(hql, dmti);
		for(UserMenuItem item : list){
			item.setEnabled(dmti.getEnabled());
			super.save(item);
			super.evict(item);
			super.evict(dmti);
		}
	}
	
	public List<DeptMenuTemplate> findDeptMenuTemplateByDeptCode(String deptCode) {
		Department dept = super.get(Department.class, Long.valueOf(deptCode));
		String hql = "select dmt from DeptMenuTemplate dmt where dmt.dept = ?";
		List<DeptMenuTemplate> dmtList = super.find(hql, dept);
		return dmtList;
	}

}
