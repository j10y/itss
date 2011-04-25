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
		//�ȸ��û��ģ�����ʹ���˵�ǰ����ģ����û�ģ��
		String hql="select um from UserMenu um where um.deptMenuTemplate=?";
		List<UserMenu> list = super.find(hql, currentModifyDmt);
		
		Set userNeedMerge = new HashSet();
		for(UserMenu um: list){
			userNeedMerge.add(um.getUserInfo());
			//ɾ���û�ʹ�õĲ���ģ����Ŀ���û��˵���Ŀ������ԭ�����û�ģ��
			super.executeUpdate("delete from UserMenuItem umi where umi.userMenu=?" , um);
		}
		//ɾ����ǰ���Ų˵�ģ���������еĲ˵���Ŀ
		super.executeUpdate("delete from DeptMenuTemplateItem umi where umi.deptMenuTemplate=?" , currentModifyDmt);
		
		//ʹ���µ�ϵͳ�˵�ģ����²��Ų˵�ģ����Ŀ�����µĲ��Ų˵���ŵ���ǰ���Ų˵�ģ����
		//*****************************************************************************************************
		this.saveDeptMenuItemFromSystem(currentModifyDmt, smtNew);
		//ʹ���µĲ��Ų˵�ģ����Ŀ�����û��˵�ģ����Ŀ, ͬʱ����merge����
		//********�����û��˵���***********************************************************************************
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
				//�����û��˵���Ӳ˵���
				this.initDeptItemFromSysItem(userMenu, userMenuItem, currUserMenu);
			}//end_rootIterator
		}//end_for(UserMenu userMenu: list)
		//merge ����ʹ���û��˵�ģ���currentModifyDmt
		
		Iterator iterUserNeedMerge = userNeedMerge.iterator();
		while(iterUserNeedMerge.hasNext()){
			UserInfo userInfo = (UserInfo) iterUserNeedMerge.next();
			String hql2 = "select count(*) from UserMenu um where um.userInfo=? ";
			Query query = super.createQuery(hql2, new Object[]{userInfo});
			Long count = (Long) query.uniqueResult();
			if(count.intValue()>1){
				
				Set userDeptTemplates = new HashSet();
				//��ȡ���еĲ���ģ��
				
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
		//ɾ���û��˵���Ŀ
		
		//��ʼmerge
		
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
			if(StringUtils.isNotBlank(menuUrl)){ //����˵������ӣ��ò˵������ж��Ƿ��ͬ
				result = menuUrl.equalsIgnoreCase(menuUrlCurrent);
				if(result == true) break; //ֻ���Ǵ���һ���ľͷ��أ�˵���Ѿ�����
			}else{
				result = menuName.equalsIgnoreCase(menuNameCurrent);
				if(result == true) break;
			}
		}
		return result;
	}
	
	//begin from userTmpMS
	/**
	 * �ò��Ų˵�ģ���ʼ���û��˵�ģ��
	 * @Methods Name initDeptItemFromSysItem
	 * @Create In 2008-9-3 By sa
	 * @param userMenu �û��˵�ģ��
	 * @param cUserMenu �û��˵�ģ����Ŀ
	 * @param currParenDeptItem ��ǰ�Ĳ��Ų˵�ģ��
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
	//δʹ��
	private UserMenu saveUserMenu(UserMenu um) {
		UserMenu userMenu = null;
		if(um.getId()==null){
			String hql="select count(*) from UserMenu um where um.userInfo=?";
			Query query = super.createQuery(hql, new Object[]{um.getUserInfo()});
			Long count = (Long) query.uniqueResult();
			if(count.intValue()>0){
				throw new ServiceException("���û��Ѿ�����һ��ģ�壬������������ģ��");
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
			throw new ServiceException("��ѡ��Ҫɾ���Ĳ��Ų˵�ģ��");
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
	 * ��ϵͳ�Ĳ˵�ģ���ʼ�����ŵĲ˵�ģ��
	 * @Methods Name initDeptItemFromSysItem
	 * @Create In 2008-9-3 By sa
	 * @param deptMenuTemplate ���Ų˵�ģ��
	 * @param cdItem ���Ų˵�ģ����
	 * @param sItem ϵͳ�˵�ģ����
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
	 * ɾ���˵��ڵ㣺�����ƣ���ɾ��������ɾ���ӽڵ㣩
	 */
	public void removeNode(String menuId) {
		DeptMenuTemplateItem menu = this.get(DeptMenuTemplateItem.class, Long.valueOf(menuId));
		this.downNode(menu.getParentMenu().getId(), menu.getMenuOrder(), -1);
		this.removeMenu(menuId);
	}

	//begin �����ƶ��û��˵�����
	/**
	 * ָ���Ľڵ�����
	 * @param parentId	ָ����Χ��Ҫ�ƶ��Ľڵ�ĸ��ڵ�
	 * @param minIndex	ָ���ڵ��ƶ�����ʱ���ڵ�λ��
	 * @param maxIndex	ָ���ڵ�Ҫ�ƶ�����Ŀ��λ��
	 */
	public void downUserNode(Long parentId, Integer minIndex, Integer maxIndex){
		if(parentId!=null&& parentId.intValue()!=0){
			UserMenuItem parent = this.get(UserMenuItem.class, parentId);
			// ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
			 //ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
	 * ָ���Ľڵ�����
	 * @param parentId	ָ����Χ��Ҫ�ƶ��Ľڵ�ĸ��ڵ�
	 * @param minIndex	ָ���ڵ�Ҫ�ƶ�����Ŀ��λ��
	 * @param maxIndex	ָ���ڵ��ƶ�����ʱ���ڵ�λ��
	 */
	@SuppressWarnings("unchecked")
	public void upUserNode(Long parentId, Integer minIndex, Integer maxIndex){
		if(parentId!=null&& parentId.intValue()!=0){
			UserMenuItem parent = this.get(UserMenuItem.class, parentId);
			// ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
	//end ����
	/**
	 * ָ���Ľڵ�����
	 * @param parentId	ָ����Χ��Ҫ�ƶ��Ľڵ�ĸ��ڵ�
	 * @param minIndex	ָ���ڵ��ƶ�����ʱ���ڵ�λ��
	 * @param maxIndex	ָ���ڵ�Ҫ�ƶ�����Ŀ��λ��
	 */
	public void downNode(Long parentId, Integer minIndex, Integer maxIndex){
		
		if(parentId!=null&& parentId.intValue()!=0){
			DeptMenuTemplateItem parent = null;
			Criteria c = super.getCriteria(DeptMenuTemplateItem.class);
			c.add(Restrictions.eq("id", parentId));
			parent = (DeptMenuTemplateItem) c.uniqueResult();
//			 ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
		}else{ //�����˵���Ŀ�ƶ�˳��
			
//			 ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
			//ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
		//ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
	
	//�����Ų˵�ģ����Ŀ����Ŀ¼�ƶ�������ʱ�������ƶ��û��ģ��������û��˵���ɸ���Ŀ�������ɾ���ɻ�upUserNode�ĵ����������
	public void upUserRootNode(UserMenu userMenu, Integer minIndex, Integer maxIndex){
		//ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
	 * ָ���Ľڵ�����
	 * @param parentId	ָ����Χ��Ҫ�ƶ��Ľڵ�ĸ��ڵ�
	 * @param minIndex	ָ���ڵ�Ҫ�ƶ�����Ŀ��λ��
	 * @param maxIndex	ָ���ڵ��ƶ�����ʱ���ڵ�λ��
	 */
	@SuppressWarnings("unchecked")
	public void upNode(Long parentId, Integer minIndex, Integer maxIndex){

		if(parentId!=null&& parentId.intValue()!=0){
			DeptMenuTemplateItem parent = this.get(DeptMenuTemplateItem.class, parentId);
//			 ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
		
		if(oldPid!=null&& !oldPid.equalsIgnoreCase("0")){//ԭʼ���˵�����0�������Ǹ�
			if(newPid!=null&& !newPid.equalsIgnoreCase("0")){ //����Ŀ¼�ƶ�����һ����Ŀ¼##############
				Long menuId = Long.valueOf(mId);
				Long oldParentId = Long.valueOf(oldPid);
				Long newParentId = Long.valueOf(newPid);
				int nodeIndex = Integer.parseInt(nodeIndx);
				DeptMenuTemplateItem obj = this.get(DeptMenuTemplateItem.class, menuId);
				DeptMenuTemplateItem newParent = super.get(DeptMenuTemplateItem.class, newParentId);
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				if(oldParentId.intValue() == newParentId.intValue() && minIndex != maxIndex){
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
				if(oldParentId.intValue() != newParentId.intValue()){
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
				//begin�Զ��ƶ��û��˵���Ŀ
				DeptMenuTemplate smt = obj.getDeptMenuTemplate();
				String hql="select dmt from UserMenu dmt where dmt.deptMenuTemplate=?";
				List<UserMenu> dmts = super.find(hql, smt);
				for(UserMenu dmt: dmts){
					//��ǰ�ƶ����û��˵�
					Criteria c = super.getCriteria(UserMenuItem.class);
					c.add(Restrictions.eq("deptMenuTemplateItem", obj));
					c.add(Restrictions.eq("userMenu", dmt));
					UserMenuItem dmti = (UserMenuItem) c.uniqueResult();
					if(dmti!=null){
						Long deptcmId = dmti.getId();
						//�µĸ��ڵ�˵�
						Criteria cParent = super.getCriteria(UserMenuItem.class);
						cParent.add(Restrictions.eq("deptMenuTemplateItem", newParent));
						cParent.add(Restrictions.eq("userMenu", dmt));
						UserMenuItem newDmtiParent = (UserMenuItem) cParent.uniqueResult();
						
						if(newDmtiParent!=null){
							//�ɵĸ��ڵ�˵�
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
				//end�Զ��ƶ��û��˵���
			}else{ //����Ŀ¼�ƶ�������Ŀ��˵��Ǹ�,Դ�˵����Ǹ�############## ����Ŀ¼�ƶ�������Ŀǰ�����ƶ����û��Ķ���ʾ������� ##############
				
				Long menuId = Long.valueOf(mId);
				Long oldParentId = Long.valueOf(oldPid);
				//Long newParentId = Long.valueOf(newPid);
				int nodeIndex = Integer.parseInt(nodeIndx);
				
				DeptMenuTemplateItem obj = this.get(DeptMenuTemplateItem.class, menuId);
				DeptMenuTemplateItem oldParent = this.get(DeptMenuTemplateItem.class, oldParentId); //obj.getParentMenu();
				if(obj.getEnabled()!=null&& obj.getEnabled().intValue()==0){
					throw new ServiceException("��ǰ���Ų˵������Ϊ���ɼ����������ƶ�λ��");
				}
				DeptMenuTemplate dmt = obj.getDeptMenuTemplate();
				//DeptMenuTemplateItem newParent = super.get(DeptMenuTemplateItem.class, newParentId);
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				
//				 �ڲ�ͬ���ڵ��·����ƶ�
				//1���൱��Ҫ�ƶ��Ľڵ���ԭ���ڵ������Ƶ������ɾ���������Ҫָ���ƶ�����ʱ�ڵ����ڵ�λ��
				this.downNode(oldParentId, minIndex, -1);
				//2���൱��Ҫ�ƶ��Ľڵ����¸��ڵ������Ƶ�ָ����λ�ã������Ҫָ��Ҫ�ƶ�����λ��
				this.upDeptRootNode(dmt, maxIndex, -1);
				// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
				obj.setMenuOrder(nodeIndex);
				obj.setParentMenu(null);
				this.saveMenu(obj);
				
				//�ƶ��û���
				String hql="select dmt from UserMenu dmt where dmt.deptMenuTemplate=?";
				List<UserMenu> dmts = super.find(hql, dmt);
				for(UserMenu userMenu: dmts){
					Criteria c = super.getCriteria(UserMenuItem.class);
					c.add(Restrictions.eq("deptMenuTemplateItem", obj));
					c.add(Restrictions.eq("userMenu", userMenu));
					UserMenuItem userMenuItem = (UserMenuItem) c.uniqueResult();
					UserInfo userInfo = userMenu.getUserInfo();
					
					if(userMenuItem!=null){ //���õ��û��˵��в��ƶ���û���ƶ��ǲ��Ų˵���Ŀ�����ˣ��û��ڳ�ʼ��ʱ�͵ò�������˵���
						Long userMenuId = userMenuItem.getId(); //��Ӧ�ĵ�ǰ�û��˵���
						
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
			
		}else if(oldPid!=null&& oldPid.equalsIgnoreCase("0")){ //Դ�˵��Ǹ�
			if(newPid!=null&& !newPid.equalsIgnoreCase("0")){ //Ŀ�길�˵����Ǹ�############## �Ӹ��ƶ�����Ŀ¼ ##############
				DeptMenuTemplateItem newParent= super.get(DeptMenuTemplateItem.class, Long.valueOf(newPid));
//				����ǰ���û��˵�Ŀ¼��parent��null
				executeUpdate("update DeptMenuTemplateItem set parentMenu=? where id=?", newParent, Long.valueOf(mId));
				//��ȡ��ǰ�����˵������order
				String hql="select max(umi.menuOrder) from DeptMenuTemplateItem umi where umi.parentMenu=?";
				Query query = super.createQuery(hql, newParent);
				Integer maxOrder = (Integer) query.uniqueResult();			
				//���õ�ǰ��orderΪ���ļ�1
				executeUpdate("update DeptMenuTemplateItem set menuOrder=? where id=?", new Object[]{maxOrder+1, Long.valueOf(mId)} );
				
				//�ƶ��û��˵���Ŀ
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
			}else{ //���Ų˵���Ŀ��Դ�˵��Ǹ�.Ŀ��˵�Ҳ�Ǹ�############## �Ӹ��ƶ����� ##############
				Long menuId = Long.valueOf(mId);
				int nodeIndex = Integer.parseInt(nodeIndx);
				//���ڵ��µ�һ���ӽڵ�
				DeptMenuTemplateItem obj = this.get(DeptMenuTemplateItem.class, menuId);
				//���ڵ��½ڵ������Ĳ��Ų˵�ģ��
				DeptMenuTemplate dmt = obj.getDeptMenuTemplate();
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndx); //Ŀ��λ�ã��������˵�����������
//				 ��ͬһ�����ڵ��·����ƶ�
				if(minIndex < maxIndex){
					// ��Ҫ�ƶ��Ľڵ�����С��Ҫ�ƶ�����Ŀ����ţ�������
					this.downDeptRootNode(dmt, minIndex, maxIndex);
				}else if(minIndex > maxIndex){
					// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
					maxIndex = minIndex;
					minIndex = nodeIndex;
					this.upDeptRootNode(dmt, minIndex, maxIndex);
				}
				// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
				obj.setMenuOrder(nodeIndex);
				this.saveMenu(obj);
				
				//�ƶ��û��˵���Ŀ
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
			// ��ͬһ�����ڵ��·����ƶ�
			if(minIndex < maxIndex){
				// ��Ҫ�ƶ��Ľڵ�����С��Ҫ�ƶ�����Ŀ����ţ�������
				 //ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
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
				// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
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
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
			obj.setMenuOrder(nodeIndex);
			this.saveUserMenu(obj);
		}
		
	}
	
	public void saveUserNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {
		if(oldPid!=null){ 
			if(newPid!=null){ //��Ŀ¼�ƶ�����Ŀ¼
				Long menuId = Long.valueOf(mId);
				Long oldParentId = Long.valueOf(oldPid);
				Long newParentId = Long.valueOf(newPid);
				int nodeIndex = Integer.parseInt(nodeIndx);
				UserMenuItem obj = this.get(UserMenuItem.class, menuId);
				UserMenuItem newParent = super.get(UserMenuItem.class, newParentId);
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				if(oldParentId == newParentId && minIndex != maxIndex){
					// ��ͬһ�����ڵ��·����ƶ�
					if(minIndex < maxIndex){
						// ��Ҫ�ƶ��Ľڵ�����С��Ҫ�ƶ�����Ŀ����ţ�������
						this.downUserNode(oldParentId, minIndex, maxIndex);
					}else if(minIndex > maxIndex){
						// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
						maxIndex = minIndex;
						minIndex = nodeIndex;
						this.upUserNode(oldParentId, minIndex, maxIndex);
					}
					// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
					obj.setMenuOrder(nodeIndex);
					this.saveUserMenu(obj);
				}
				if(oldParentId != newParentId){
					// �ڲ�ͬ���ڵ��·����ƶ�
					//1���൱��Ҫ�ƶ��Ľڵ���ԭ���ڵ������Ƶ������ɾ���������Ҫָ���ƶ�����ʱ�ڵ����ڵ�λ��
					this.downUserNode(oldParentId, minIndex, -1);
					//2���൱��Ҫ�ƶ��Ľڵ����¸��ڵ������Ƶ�ָ����λ�ã������Ҫָ��Ҫ�ƶ�����λ��
					this.upUserNode(newParentId, maxIndex, -1);
					// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
					obj.setMenuOrder(nodeIndex);
					obj.setParentMenu(newParent);
					this.saveUserMenu(obj);
				}
			}else{ //Ŀ��null,######### ��Ŀ¼�ƶ��������˴����ƶ����ײ��ˣ���˴���bug
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
				
				// �ڲ�ͬ���ڵ��·����ƶ�
				//1���൱��Ҫ�ƶ��Ľڵ���ԭ���ڵ������Ƶ������ɾ���������Ҫָ���ƶ�����ʱ�ڵ����ڵ�λ��
				this.downUserNode(oldParentId, minIndex, -1);
				//2���൱��Ҫ�ƶ��Ľڵ����¸��ڵ������Ƶ�ָ����λ�ã������Ҫָ��Ҫ�ƶ�����λ��
				this.upUserRootNode(userMenu, maxIndex, -1);
				// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
				obj.setMenuOrder(nodeIndex);
				obj.setParentMenu(null);
				this.saveUserMenu(obj);
				//end
				
				//����ǰ���û��˵�Ŀ¼��parent��null
				//executeUpdate("update UserMenuItem set parentMenu=null where id=?", Long.valueOf(mId));
				/*//��ȡ��ǰ�����˵������order
				String hql="select max(umi.menuOrder) from UserMenuItem umi where umi.parentMenu is null ";
				Query query = super.createQuery(hql, null);
				Integer maxOrder = (Integer) query.uniqueResult();			
				//���õ�ǰ��orderΪ���ļ�1
				executeUpdate("update UserMenuItem set menuOrder=? where id=?", new Object[]{maxOrder+1, Long.valueOf(mId)} );*/
			}
		}else{ //�Ӹ��ƶ����¼�Ŀ¼��Ŀ
			if(newPid!=null){
				UserMenuItem newParent = super.get(UserMenuItem.class, Long.valueOf(newPid));
//				����ǰ���û��˵�Ŀ¼��parent��null
				executeUpdate("update UserMenuItem set parentMenu=? where id=?", newParent, Long.valueOf(mId));
				//��ȡ��ǰ�����˵������order
				String hql="select max(umi.menuOrder) from UserMenuItem umi where umi.parentMenu=? ";
				Query query = super.createQuery(hql, newParent);
				Integer maxOrder = (Integer) query.uniqueResult();			
				//���õ�ǰ��orderΪ���ļ�1
				executeUpdate("update UserMenuItem set menuOrder=? where id=?", new Object[]{maxOrder+1, Long.valueOf(mId)} );
			}else{//�Ǹ��ϵĲ˵��ƶ�
				/*Long menuId = Long.valueOf(mId);
				int nodeIndex = Integer.parseInt(nodeIndx);
				UserMenuItem obj = this.get(UserMenuItem.class, menuId);
	
				int minIndex = obj.getMenuOrder().intValue();
				int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
				if(minIndex != maxIndex){
					// ��ͬһ�����ڵ��·����ƶ�
					if(minIndex < maxIndex){
						// ��Ҫ�ƶ��Ľڵ�����С��Ҫ�ƶ�����Ŀ����ţ�������
						this.downUserNode(null, minIndex, maxIndex);
					}else if(minIndex > maxIndex){
						// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
						maxIndex = minIndex;
						minIndex = nodeIndex;
						this.upUserNode(null, minIndex, maxIndex);
					}
					// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
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
