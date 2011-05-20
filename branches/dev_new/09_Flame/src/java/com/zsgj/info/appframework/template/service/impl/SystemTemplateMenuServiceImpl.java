package com.zsgj.info.appframework.template.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.template.entity.DeptMenuTemplate;
import com.zsgj.info.appframework.template.entity.DeptMenuTemplateItem;
import com.zsgj.info.appframework.template.entity.SystemMenuTemplate;
import com.zsgj.info.appframework.template.entity.SystemMenuTemplateItem;
import com.zsgj.info.appframework.template.entity.TemplateItem;
import com.zsgj.info.appframework.template.entity.UserMenu;
import com.zsgj.info.appframework.template.entity.UserMenuItem;
import com.zsgj.info.appframework.template.service.DeptTemplateMenuService;
import com.zsgj.info.appframework.template.service.SystemTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.exception.ServiceException;

public class SystemTemplateMenuServiceImpl extends BaseDao implements SystemTemplateMenuService {
	private DeptTemplateMenuService deptTemplateMenuService;
	public void setDeptTemplateMenuService(
			DeptTemplateMenuService deptTemplateMenuService) {
		this.deptTemplateMenuService = deptTemplateMenuService;
	}

	public SystemMenuTemplate saveSystemTemplate4Copy(SystemMenuTemplate targetSmt, String sourceTmpId) {
		super.save(targetSmt);
		if(targetSmt.getId()!=null){
			super.executeUpdate("delete from SystemMenuTemplateItem source where source.systemMenuTemplate=?", targetSmt);
		}
		//������ģ����Դ
		SystemMenuTemplate sourceSmt = super.get(SystemMenuTemplate.class, Long.valueOf(sourceTmpId));
		
		//��Դģ�忽����Ŀ��ϵͳģ�壬ͬʱ�����������Ų˵�ģ��
		this.saveDeptMenuItemFromSystem(targetSmt, sourceSmt);
		
		DeptTemplateMenuService dtms = (DeptTemplateMenuService) ContextHolder.getBean("deptTemplateMenuService");
		Criteria c = super.getCriteria(DeptMenuTemplate.class);
		//Ŀ��ϵͳģ�壬Ҳ���ǵ�ǰ�����ϵͳģ�壬��ȡ��ϵͳ����Ӧ�����в���ģ��
		c.add(Restrictions.eq("systemMenuTemplate", targetSmt));
		List<DeptMenuTemplate> dmts = c.list();
		for(DeptMenuTemplate dmt : dmts){
			dtms.saveDeptSystemTemplateChange(dmt, targetSmt);
		}
		
		
		
		return targetSmt;
		
	}
	
	private void saveDeptMenuItemFromSystem(SystemMenuTemplate targetSmt, SystemMenuTemplate sourceSmt){
		Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
		c.add(Restrictions.isNull("parentMenu"));
		c.setFetchMode("childMenus", FetchMode.JOIN);
		c.add(Restrictions.eq("systemMenuTemplate", sourceSmt));
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List list = c.list();
		Iterator rootIterator = list.iterator();
		while(rootIterator.hasNext()){
			SystemMenuTemplateItem sourceItem = (SystemMenuTemplateItem) rootIterator.next();
			
			SystemMenuTemplateItem targetNewItem = new SystemMenuTemplateItem();
			
			targetNewItem.setSystemMenuTemplate(targetSmt);
			targetNewItem.setLeafFlag(sourceItem.getLeafFlag());
			targetNewItem.setMenuLevel(sourceItem.getMenuLevel());
			targetNewItem.setMenuName(sourceItem.getMenuName());
			targetNewItem.setMenuOrder(sourceItem.getMenuOrder());
			targetNewItem.setMenuUrl(sourceItem.getMenuUrl());

			super.save(targetNewItem);
			super.evict(targetNewItem);
			//targetSmt���Ѿ��¿������ɺ��ϵͳ���
			this.initDeptItemFromSysItem(targetSmt, targetNewItem, sourceItem);
		}
	}
	
	private void initDeptItemFromSysItem(SystemMenuTemplate targetSmt, 
					SystemMenuTemplateItem targetItemParent, SystemMenuTemplateItem sourceItemParent){
		Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
		c.add(Restrictions.eq("id", sourceItemParent.getId()));
		c.setFetchMode("childMenus", FetchMode.JOIN);
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		SystemMenuTemplateItem result = (SystemMenuTemplateItem) c.uniqueResult();
		Set childMenus = result.getChildMenus();
		Iterator iterC = childMenus.iterator();
		while(iterC.hasNext()){
			SystemMenuTemplateItem sourceItem = (SystemMenuTemplateItem) iterC.next();
			
			
			SystemMenuTemplateItem targetNewItem = new SystemMenuTemplateItem();
			
			targetNewItem.setSystemMenuTemplate(targetSmt);
			targetNewItem.setLeafFlag(sourceItem.getLeafFlag());
			targetNewItem.setMenuLevel(sourceItem.getMenuLevel());
			targetNewItem.setMenuName(sourceItem.getMenuName());
			targetNewItem.setMenuOrder(sourceItem.getMenuOrder());
			targetNewItem.setMenuUrl(sourceItem.getMenuUrl());
			targetNewItem.setParentMenu(targetItemParent);
			super.save(targetNewItem);
			super.evict(targetNewItem);
			
			this.initDeptItemFromSysItem(targetSmt, targetNewItem, sourceItem);
		}
	}
	
	public List findSystemMenuTemplates() {
		List list = null;
		list = super.getObjects(SystemMenuTemplate.class);
		return list;
	}
	
	public SystemMenuTemplate findSystemMenuTemplateById(String smtId){
		SystemMenuTemplate smt = super.get(SystemMenuTemplate.class, Long.valueOf(smtId));
		return smt;
	}
	
	public List<SystemMenuTemplate> findSystemMenuTemplateByName(String smtName){
		List<SystemMenuTemplate> list = super.findBy(SystemMenuTemplate.class, "templateName", smtName);
		return list;
	}
	
	public List<SystemMenuTemplateItem> findSystemMenuTemplateItemNoParent(String smtId){
		
		SystemMenuTemplate smt = super.get(SystemMenuTemplate.class, Long.valueOf(smtId));
		String hql="select smti from SystemMenuTemplateItem smti where smti.parentMenu is null and smti.systemMenuTemplate=?  order by smti.menuOrder";
		List<SystemMenuTemplateItem> itemList = super.find(hql, smt);
		List<SystemMenuTemplateItem> returnList = new ArrayList<SystemMenuTemplateItem>();
		
		for(SystemMenuTemplateItem item:itemList){
			SystemMenuTemplateItem parentMenuItem = new SystemMenuTemplateItem();
			parentMenuItem.setId(new Long(0));
			item.setParentMenu(parentMenuItem);
			returnList.add(item);
		}
		
		return returnList;
	}
	
	public List<SystemMenuTemplateItem> findChildenByParentAndSystemMenuTemplate(String parentMenuId, String smtId){		
		SystemMenuTemplateItem smti = super.get(SystemMenuTemplateItem.class, Long.valueOf(parentMenuId));
		SystemMenuTemplate smt = super.get(SystemMenuTemplate.class, Long.valueOf(smtId));
		String hql="select smti from SystemMenuTemplateItem smti where smti.parentMenu =? and smti.systemMenuTemplate=?  order by smti.menuOrder";
		List<SystemMenuTemplateItem> itemList = super.find(hql, new Object[]{smti,smt});
		return itemList;
	}
	
	public void removeSystemMenuTemplate(String smsId) {
		SystemMenuTemplate smt = super.get(SystemMenuTemplate.class, Long.valueOf(smsId));
		super.executeUpdate("delete SystemMenuTemplateItem smti where smti.systemMenuTemplate=?", new Object[]{smt});
		super.remove(smt); 
	}

	public void removeSystemMenuTemplate(String[] dmtIds) {
		if(dmtIds==null|| dmtIds.length==0){
			throw new ServiceException("��ѡ��Ҫɾ����ϵͳ�˵�ģ��");
		}
		for(int i=0; i<dmtIds.length; i++){
			SystemMenuTemplate current = (SystemMenuTemplate) super.get(SystemMenuTemplate.class, Long.valueOf(dmtIds[i]));
			this.removeDeptTemplateAndItem(current);
			
			super.executeUpdate("delete SystemMenuTemplateItem smti where smti.systemMenuTemplate=?", new Object[]{current});
			super.remove(current);
		}
		
	}
	
	private void removeDeptTemplateAndItem(SystemMenuTemplate smt){
		String hql="from DeptMenuTemplate dmti where dmti.systemMenuTemplate=?";
		List<DeptMenuTemplate> deptTemplates = super.find(hql, smt);
		for(DeptMenuTemplate dmt: deptTemplates){
			//��ɾ���û��˵�ģ�弰�˵���
			super.executeUpdate("update Role role set role.deptMenuTemplate=null where role.deptMenuTemplate=?", new Object[]{dmt});
			this.removeUserTemplateAndItem(dmt);
			super.executeUpdate("delete DeptMenuTemplateItem smti where smti.deptMenuTemplate=?", new Object[]{dmt});
			super.remove(dmt);
		}
		
	}
	
	private void removeUserTemplateAndItem(DeptMenuTemplate smt){
		String hql="from UserMenu dmti where dmti.deptMenuTemplate=?";
		List<UserMenu> userMenus = super.find(hql, smt);
		for(UserMenu dmt: userMenus){
			super.executeUpdate("delete UserMenuItem smti where smti.userMenu=?", new Object[]{dmt});
			super.remove(dmt);
		}
	}

	public SystemMenuTemplate saveSystemMenuTemplate(SystemMenuTemplate smt) {
		SystemMenuTemplate result = null;
		result = (SystemMenuTemplate) super.save(smt);
		String hql="from SystemMenuTemplateItem dmti where dmti.systemMenuTemplate=?";
		List<SystemMenuTemplateItem> list = super.find(hql, smt);
		for(SystemMenuTemplateItem smti: list){
			Criteria c = super.getCriteria(DeptMenuTemplateItem.class);
			c.add(Restrictions.eq("systemMenuTemplateItem", smti));
			List<DeptMenuTemplateItem> deptItems = c.list();
			for(DeptMenuTemplateItem dmti: deptItems){
				dmti.setMenuName(smti.getMenuName());
				dmti.setMenuUrl(smti.getMenuUrl());
				super.save(dmti);
			}
			
		}
		return result;
	}

	public List<SystemMenuTemplateItem> findChildenByParent(String parentMenuId){
			SystemMenuTemplateItem parent = this.get(SystemMenuTemplateItem.class, Long.valueOf(parentMenuId));
			String hql = "select m from SystemMenuTemplateItem m where m.parentMenu=? order by smti.menuOrder" ;
			List list = super.find(hql, parent);
			return list;		
	}

	public SystemMenuTemplateItem modifyMenuName(String menuId, String menuName) {
		SystemMenuTemplateItem menu = this.get(SystemMenuTemplateItem.class, Long.valueOf(menuId));
		menu.setMenuName(menuName);
		this.saveMenu(menu);
		String hql = "select dmti from DeptMenuTemplateItem dmti where dmti.systemMenuTemplateItem=?";
		List<DeptMenuTemplateItem> list = super.find(hql, menu);
		for(DeptMenuTemplateItem item : list){
			item.setMenuName(menu.getMenuName());
			item.setMenuUrl(menu.getMenuUrl());
			super.save(item);
			this.modifyUserMenuItem(item);
		}
		return menu;
	}

	private void modifyUserMenuItem(DeptMenuTemplateItem dmti){
		String hql = "select dmti from UserMenuItem dmti where dmti.deptMenuTemplateItem=?";
		List<UserMenuItem> list = super.find(hql, dmti);
		for(UserMenuItem item : list){
			item.setMenuName(dmti.getMenuName());
			item.setMenuUrl(dmti.getMenuUrl());
			super.save(item);
			super.evict(item);
			super.evict(dmti);
		}
	}
	/**
	 * ɾ���˵��ڵ㣺�����ƣ���ɾ��������ɾ���ӽڵ㣩
	 */
	public void removeNode(String menuId) {
		SystemMenuTemplateItem menu = this.get(SystemMenuTemplateItem.class, Long.valueOf(menuId));
		if(menu.getParentMenu() == null){
//			ɾ�����ڵ��ϵĽڵ㣬Ҳ���Ǹ�Ϊnull��
			Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
			c.add(Restrictions.eq("id", Long.valueOf(menuId)));
			c.setFetchMode("systemMenuTemplate", FetchMode.JOIN);
			//��ȡ��ǰ�ڵ�������ϵͳģ��
			SystemMenuTemplateItem res = (SystemMenuTemplateItem) c.uniqueResult();
			//���Ƹ��ڵ��µ����е��ӽڵ�
			this.downNode(res.getSystemMenuTemplate(), menu.getMenuOrder(), -1);
		}else{
			this.downNode(menu.getParentMenu().getId(), menu.getMenuOrder(), -1);
		}
		this.removeMenu(menuId);
	}

	/**
	 * ָ���Ľڵ�����
	 * @param parentId	ָ����Χ��Ҫ�ƶ��Ľڵ�ĸ��ڵ�
	 * @param minIndex	ָ���ڵ��ƶ�����ʱ���ڵ�λ��
	 * @param maxIndex	ָ���ڵ�Ҫ�ƶ�����Ŀ��λ��
	 */
	public void downNode(Long parentId, Integer minIndex, Integer maxIndex){
		SystemMenuTemplateItem parent = this.get(SystemMenuTemplateItem.class, parentId);
		if(parent!=null){
//			 ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
			StringBuffer hql = new StringBuffer("update SystemMenuTemplateItem m set m.menuOrder=m.menuOrder-1 where m.parentMenu = ?");
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
		}
		
	}
	
//	��������************************************************cmcc************************
	public void downNode(SystemMenuTemplate menu, Integer minIndex, Integer maxIndex){
		
//			 ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
			StringBuffer hql = new StringBuffer("update SystemMenuTemplateItem m " +
					"set m.menuOrder=m.menuOrder-1 where m.systemMenuTemplate = ? and  m.parentMenu is null");
			List paramsList = new ArrayList();
			paramsList.add(menu);
			
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
		SystemMenuTemplateItem parent = this.get(SystemMenuTemplateItem.class, parentId);
		// ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
		StringBuffer hql = new StringBuffer("update SystemMenuTemplateItem m set m.menuOrder=m.menuOrder+1 where m.parentMenu = ?");
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
		
		/*String hql2="select dmt DeptMenuTemplate from dmt where dmt.systemMenuTemplate=?";
		List<DeptMenuTemplate> list2 = super.find(hql2, parent.getSystemMenuTemplate());
		for(DeptMenuTemplate ditem : list2){
			
		}*/
		
	}
	
//	��������************************************************cmcc************************
	public void upNode(SystemMenuTemplate sysMenuTmp, Integer minIndex, Integer maxIndex){
		//SystemMenuTemplateItem parent = this.get(SystemMenuTemplateItem.class, parentId);
		// ָ���Ľڵ����ƣ���ζ���䷶Χ�ڵĽڵ���Լ�1
		StringBuffer hql = new StringBuffer("update SystemMenuTemplateItem m " +
				"set m.menuOrder=m.menuOrder+1 where m.parentMenu is null and m.systemMenuTemplate=?");
				
		List paramsList = new ArrayList();
		paramsList.add(sysMenuTmp);
		
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
		
		/*String hql2="select dmt DeptMenuTemplate from dmt where dmt.systemMenuTemplate=?";
		List<DeptMenuTemplate> list2 = super.find(hql2, parent.getSystemMenuTemplate());
		for(DeptMenuTemplate ditem : list2){
			
		}*/
		
	}
	
	
	/**
	 * ָ���Ĳ��Žڵ�����
	 * @param parentId	ָ����Χ��Ҫ�ƶ��Ľڵ�ĸ��ڵ�
	 * @param minIndex	ָ���ڵ��ƶ�����ʱ���ڵ�λ��
	 * @param maxIndex	ָ���ڵ�Ҫ�ƶ�����Ŀ��λ��
	 */
	public void downNodeDept(Long parentId, Integer minIndex, Integer maxIndex){
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
	//begin ���Ų˵�ģ��������ƶ�����
	@SuppressWarnings("unchecked")
	public void upNodeDept(Long parentId, Integer minIndex, Integer maxIndex){
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
		//===
	}
	
	//����ģ��˵���Ŀ˳���ƶ����˷�����δʹ��
	/**
	 * @deprecated
	 */
	public void saveDeptNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {
		
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
				this.downNodeDept(oldParentId, minIndex, maxIndex);
			}else if(minIndex > maxIndex){
				// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
				maxIndex = minIndex;
				minIndex = nodeIndex;
				this.upNodeDept(oldParentId, minIndex, maxIndex);
			}
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
			obj.setMenuOrder(nodeIndex);
			this.saveDeptMenu(obj);
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
			this.saveDeptMenu(obj);
			
//			begin�Զ��ƶ��û��˵���Ŀ
			DeptMenuTemplate smt = obj.getDeptMenuTemplate();
			String hql="select dmt from UserMenu dmt where dmt.deptMenuTemplate=?";
			List<UserMenu> dmts = super.find(hql, smt);
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
					UserMenuItem newDmtiParent = (UserMenuItem) cParent.uniqueResult();
					if(newDmtiParent!=null){
						Long newDeptParentId = newDmtiParent.getId();
						
						Criteria cOldParent = super.getCriteria(UserMenuItem.class);
						cOldParent.add(Restrictions.eq("deptMenuTemplateItem", new DeptMenuTemplateItem(oldParentId)));
						cOldParent.add(Restrictions.eq("userMenu", dmt));
						UserMenuItem dmtiOldParent = (UserMenuItem) cOldParent.uniqueResult();
						if(dmtiOldParent!=null){
							Long oldDeptParentId = dmtiOldParent.getId();
							this.saveUserNodeMove(String.valueOf(deptcmId), String.valueOf(oldDeptParentId), String.valueOf(newDeptParentId), nodeIndx);
						
						}
						
					}
						
				}
				
			}
			
			//end
		}
		
	}
	
	public DeptMenuTemplateItem saveDeptMenu(DeptMenuTemplateItem menu) {
		DeptMenuTemplateItem result = null;
		result = (DeptMenuTemplateItem) super.save(menu);
		return result;
	}
	//end
	
//	begin user menu move
	public void saveUserNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {
		
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
				this.downNode(oldParentId, minIndex, maxIndex);
			}else if(minIndex > maxIndex){
				// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
				maxIndex = minIndex;
				minIndex = nodeIndex;
				this.upNode(oldParentId, minIndex, maxIndex);
			}
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
			obj.setMenuOrder(nodeIndex);
			this.saveUserMenu(obj);
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
			this.saveUserMenu(obj);
		}
		
	}
	
	public UserMenuItem saveUserMenu(UserMenuItem menu) {
		UserMenuItem result = null;
		result = (UserMenuItem) super.save(menu);
		return result;
	}
	//end
	@SuppressWarnings("unchecked")
	public void saveNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {//nodeIndxĿ�길�ڵ������λ��0��ʼ����012
		
		Long menuId = Long.valueOf(mId);
		Long oldParentId = Long.valueOf(oldPid);
		Long newParentId = Long.valueOf(newPid);
		int nodeIndex = Integer.parseInt(nodeIndx);
		SystemMenuTemplateItem obj = this.get(SystemMenuTemplateItem.class, menuId);
		SystemMenuTemplateItem newParent = super.get(SystemMenuTemplateItem.class, newParentId);
		int minIndex = obj.getMenuOrder().intValue();
		int maxIndex = Integer.valueOf(nodeIndex); //nodeIndex;
//********************************************
		if(oldParentId.intValue() == newParentId.intValue() && minIndex != maxIndex){
			// ��ͬһ�����ڵ��·����ƶ�
			if(minIndex < maxIndex){
				// ��Ҫ�ƶ��Ľڵ�����С��Ҫ�ƶ�����Ŀ����ţ�������
				if(!oldParentId.toString().equals("0")){
					this.downNode(oldParentId, minIndex, maxIndex);
				}else{
					//���ڵ��µĽڵ�ֱ���ƶ�
					Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
					c.add(Restrictions.eq("id", menuId));
					c.setFetchMode("systemMenuTemplate", FetchMode.JOIN);
					SystemMenuTemplateItem res = (SystemMenuTemplateItem) c.uniqueResult();
					this.downNode(res.getSystemMenuTemplate(), minIndex, maxIndex);
				}
				
			}else if(minIndex > maxIndex){
				// ��Ҫ�ƶ��Ľڵ����Ŵ���Ҫ�ƶ�����Ŀ����ţ�������
				maxIndex = minIndex;
				minIndex = nodeIndex;
				if(!oldParentId.toString().equals("0")){
					this.upNode(oldParentId, minIndex, maxIndex);
				}else{
					//���ڵ��µĽڵ�ֱ���ƶ�
					Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
					c.add(Restrictions.eq("id", menuId));
					c.setFetchMode("systemMenuTemplate", FetchMode.JOIN);
					SystemMenuTemplateItem res = (SystemMenuTemplateItem) c.uniqueResult();
					this.upNode(res.getSystemMenuTemplate(), minIndex, maxIndex);
				}
				
			}
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
			obj.setMenuOrder(nodeIndex);
			this.saveMenu(obj);
		}
//		********************************************
		if(oldParentId.intValue() != newParentId.intValue()){
			// �ڲ�ͬ���ڵ��·����ƶ�
			//1���൱��Ҫ�ƶ��Ľڵ���ԭ���ڵ������Ƶ������ɾ���������Ҫָ���ƶ�����ʱ�ڵ����ڵ�λ��
			if(!oldParentId.toString().equals("0")){
				this.downNode(oldParentId, minIndex, -1);
			}else{//***********ԭ�нڵ��Ǹ��ڵ㣬Ŀ��ڵ����ӽڵ㣬���Ƹ��ڵ�Ľڵ�********************
				Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
				c.add(Restrictions.eq("id", menuId));
				c.setFetchMode("systemMenuTemplate", FetchMode.JOIN);
				SystemMenuTemplateItem res = (SystemMenuTemplateItem) c.uniqueResult();
				this.downNode(res.getSystemMenuTemplate(), minIndex, -1);
			}
			
			//2���൱��Ҫ�ƶ��Ľڵ����¸��ڵ������Ƶ�ָ����λ�ã������Ҫָ��Ҫ�ƶ�����λ��
			if(!newParentId.toString().equals("0")){
				this.upNode(newParentId, maxIndex, -1);
			}else{//***********Ŀ��ڵ��Ǹ��ڵ㣬�ڸ��ڵ����ƶ�********************************************
				Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
				c.add(Restrictions.eq("id", menuId));
				c.setFetchMode("systemMenuTemplate", FetchMode.JOIN);
				SystemMenuTemplateItem res = (SystemMenuTemplateItem) c.uniqueResult();
				this.upNode(res.getSystemMenuTemplate(), maxIndex, -1);
			}
			
			// �ڵ㱾���������ó�Ҫ�ƶ�����Ŀ�����
			obj.setMenuOrder(nodeIndex);
			obj.setParentMenu(newParent);
			this.saveMenu(obj);
		}
		//begin
		SystemMenuTemplate smt = obj.getSystemMenuTemplate();
		String hql="select dmt from DeptMenuTemplate dmt where dmt.systemMenuTemplate=?";
		List<DeptMenuTemplate> dmts = super.find(hql, smt);
		for(DeptMenuTemplate dmt: dmts){
			Criteria c = super.getCriteria(DeptMenuTemplateItem.class);
			c.add(Restrictions.eq("systemMenuTemplateItem", obj));
			c.add(Restrictions.eq("deptMenuTemplate", dmt));
			DeptMenuTemplateItem dmti = (DeptMenuTemplateItem) c.uniqueResult();
			if(dmti!=null){
				Long deptcmId = dmti.getId();
				
				Criteria cParent = super.getCriteria(DeptMenuTemplateItem.class);
				cParent.add(Restrictions.eq("systemMenuTemplateItem", newParent));
				cParent.add(Restrictions.eq("deptMenuTemplate", dmt));
				DeptMenuTemplateItem newDmtiParent = (DeptMenuTemplateItem) cParent.uniqueResult();
				if(newDmtiParent!=null){//�൱Ŀ��ڵ㲻�Ǹ�������Ŀ��ڵ����
					Long newDeptParentId = newDmtiParent.getId();
					
					Criteria cOldParent = super.getCriteria(DeptMenuTemplateItem.class);
					cOldParent.add(Restrictions.eq("systemMenuTemplateItem", new SystemMenuTemplateItem(oldParentId)));
					cOldParent.add(Restrictions.eq("deptMenuTemplate", dmt));
					DeptMenuTemplateItem dmtiOldParent = (DeptMenuTemplateItem) cOldParent.uniqueResult();
					if(dmtiOldParent!=null){ //ԭ�и��ڵ㲻�Ǹ�
						Long oldDeptParentId = dmtiOldParent.getId();
						if(oldDeptParentId!=null){
							Integer nodeIndexInteger = Integer.valueOf(nodeIndx)/*+1*/;
							deptTemplateMenuService
								.saveNodeMove(String.valueOf(deptcmId), String.valueOf(oldDeptParentId), 
									String.valueOf(newDeptParentId), String.valueOf(nodeIndexInteger));
							
							/*this.saveDeptNodeMove(String.valueOf(deptcmId), String.valueOf(oldDeptParentId), 
									String.valueOf(newDeptParentId), String.valueOf(nodeIndexInteger));*/
						}
					}else{//ԭ�и��ڵ��Ǹ���Ҳ���ǴӸ������ӽڵ��ƶ�
						Integer nodeIndexInteger = Integer.valueOf(nodeIndx)/*+1*/;
						deptTemplateMenuService.saveNodeMove(String.valueOf(deptcmId), "0", String.valueOf(newDeptParentId), String.valueOf(nodeIndexInteger));
								
					}
					
					
				}else{ //����Ŀ��ڵ����Ǹ��ڵ�
					Criteria cOldParent = super.getCriteria(DeptMenuTemplateItem.class);
					cOldParent.add(Restrictions.eq("systemMenuTemplateItem", new SystemMenuTemplateItem(oldParentId)));
					cOldParent.add(Restrictions.eq("deptMenuTemplate", dmt));
					DeptMenuTemplateItem dmtiOldParent = (DeptMenuTemplateItem) cOldParent.uniqueResult();
					if(dmtiOldParent!=null){
						Long oldDeptParentId = dmtiOldParent.getId();
						if(oldDeptParentId!=null){
							Integer nodeIndexInteger = Integer.valueOf(nodeIndx);
							
							deptTemplateMenuService.saveNodeMove(String.valueOf(deptcmId), String.valueOf(oldDeptParentId), "0", String.valueOf(nodeIndexInteger));
							
						}
					}else{//�Ͻڵ��Ǹ����½ڵ�Ҳ�Ǹ�
						Integer nodeIndexInteger = Integer.valueOf(nodeIndx);
						deptTemplateMenuService.saveNodeMove(String.valueOf(deptcmId), "0", "0", String.valueOf(nodeIndexInteger));
					}
				}//end���Žڵ��Ǹ�
				
			}
		
		}//end for
		
	}

	public List<SystemMenuTemplateItem> findAllMenu() {
		return this.getObjects(SystemMenuTemplateItem.class);
	}


	public SystemMenuTemplateItem findMenuById(String id) {
		SystemMenuTemplateItem menu = null;
		Criteria c = super.getCriteria(SystemMenuTemplateItem.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		c.setFetchMode("systemMenuTemplate", FetchMode.JOIN);
		c.setFetchMode("parentMenu", FetchMode.JOIN);
		menu = (SystemMenuTemplateItem) c.uniqueResult();
		return menu;
	}

	private Integer getMaxDeptChildOrder(DeptMenuTemplateItem dmti){
		String hql="select max(item.menuOrder) from DeptMenuTemplateItem item where item.parentMenu=?";
		Query query = super.createQuery(hql, dmti);
		Integer maxOrder = (Integer) query.uniqueResult();
		Integer result = maxOrder==null?1:maxOrder+1; //�޸�Ϊ����1����ʾǰҹ
		return result;
	}
	
	private Integer getMaxDeptChildOrder(DeptMenuTemplate dmti){
		String hql="select max(item.menuOrder) from DeptMenuTemplateItem item " +
				"where item.parentMenu is null and item.deptMenuTemplate=?";
		Query query = super.createQuery(hql, dmti);
		Integer maxOrder = (Integer) query.uniqueResult();
		Integer result = maxOrder==null?1:maxOrder+1; //�޸�Ϊ����1����ʾǰҹ
		return result;
	}
	
	private Integer getMaxUserChildOrder(UserMenuItem parent){
		String hql="select max(item.menuOrder) from UserMenuItem item where item.parentMenu=?";
		Query query = super.createQuery(hql, parent);
		Integer maxOrder = (Integer) query.uniqueResult();
		Integer result = maxOrder==null?1:maxOrder+1; 
		return result;
	}
	
	private Integer getMaxUserChildOrder(UserMenu parent){
		String hql="select max(item.menuOrder) from UserMenuItem item " +
				"where item.parentMenu is null and item.userMenu=?";
				
		Query query = super.createQuery(hql, parent);
		Integer maxOrder = (Integer) query.uniqueResult();
		Integer result = maxOrder==null?1:maxOrder+1; 
		return result;
	}
	
	public SystemMenuTemplateItem saveMenu(SystemMenuTemplateItem menu) {
		boolean isInsert = menu.getId()==null;
		SystemMenuTemplateItem result = null;
		result = (SystemMenuTemplateItem) super.save(menu);
		if(isInsert){
			//��ȡ��ǰ�������Ӳ˵��ĸ��˵�
			SystemMenuTemplateItem parentSysItem = result.getParentMenu(); //��ϵͳ�����Ӳ˵����Զ��ҵ����Ų˵���Ӧ�ĸ��˵���
			if(parentSysItem!=null){
				//��ȡ��ϵͳ���˵��ڲ��Ų˵�ģ����Ŀ��Ķ�Ӧ�˵�
				String hql = "select dmti from DeptMenuTemplateItem dmti where dmti.systemMenuTemplateItem=?";
				List<DeptMenuTemplateItem> list = super.find(hql, parentSysItem); //ϵͳ�˵��Ĺҽӵ�
				for(DeptMenuTemplateItem parentDeptItem : list){ //�ҵ�һ�����Ų˵�ģ����Ŀ�Ķ�Ӧ��Ҫ�ҽӵĸ��˵�λ��
					DeptMenuTemplateItem deptItemForAdd = new DeptMenuTemplateItem();
					deptItemForAdd.setParentMenu(parentDeptItem); //��Ҫ�ҽӵĸ��˵�
					deptItemForAdd.setDeptMenuTemplate(parentDeptItem.getDeptMenuTemplate());
					deptItemForAdd.setEnabled(Integer.valueOf(1));
					deptItemForAdd.setLeafFlag(result.getLeafFlag());//??
					deptItemForAdd.setMenuLevel(result.getMenuLevel());
					deptItemForAdd.setMenuName(result.getMenuName());
					deptItemForAdd.setMenuOrder(getMaxDeptChildOrder(parentDeptItem));//get parent max order child
					deptItemForAdd.setMenuUrl(result.getMenuUrl());
					deptItemForAdd.setSystemMenuTemplateItem(menu);
					super.save(deptItemForAdd);
					//���Ӳ��ŵļ��������û���
					this.saveUserMenuItemCascadeWithDept(parentDeptItem, deptItemForAdd); 
					
				}
			}else{
				String hql = "select dmt from DeptMenuTemplate dmt where dmt.systemMenuTemplate=?";
				List<DeptMenuTemplate> list = super.find(hql, menu.getSystemMenuTemplate()); //��ȡ���еĲ��Ų˵�ģ��
				for(DeptMenuTemplate dmtItem : list){ 
					DeptMenuTemplateItem deptItemForAdd = new DeptMenuTemplateItem();
					deptItemForAdd.setParentMenu(null); //��Ҫ�ҽӵĸ��˵�
					deptItemForAdd.setDeptMenuTemplate(dmtItem);
					deptItemForAdd.setEnabled(Integer.valueOf(1));
					deptItemForAdd.setLeafFlag(result.getLeafFlag());//??
					deptItemForAdd.setMenuLevel(result.getMenuLevel());
					deptItemForAdd.setMenuName(result.getMenuName());
					deptItemForAdd.setMenuOrder(this.getMaxDeptChildOrder(dmtItem));//get parent max order child
					deptItemForAdd.setMenuUrl(result.getMenuUrl());
					deptItemForAdd.setSystemMenuTemplateItem(menu);
					super.save(deptItemForAdd);
					this.saveUserMenuRootItemCascadeWithDept(dmtItem, deptItemForAdd); 
				}
				
			}
			
		}
		//��ȡ��ǰ�޸Ĳ˵��Ķ�Ӧ���Ų˵���Ŀ
		String hql = "select dmti from DeptMenuTemplateItem dmti where dmti.systemMenuTemplateItem=?";
		List<DeptMenuTemplateItem> list = super.find(hql, result);
		for(DeptMenuTemplateItem item : list){
			item.setMenuName(menu.getMenuName());//�����µ�ϵͳ�˵���Ŀ���Ƹ��²��ŵ�
			item.setMenuUrl(menu.getMenuUrl());
			super.save(item);
			this.modifyUserMenuItem(item); //�޸Ĳ��ŵļ����޸��û���
		}
		return result;
	}
	

	/**
	 * ���Ӳ��Ų˵���Ŀ���û��˵���ĿҲ�Զ�����
	 * @Methods Name saveUserMenuItemCascadeWithDept
	 * @Create In 2008-9-18 By sa
	 * @param dmti
	 * @param sysMenuToAdd void
	 */
	private void saveUserMenuItemCascadeWithDept(DeptMenuTemplateItem parent, DeptMenuTemplateItem deptMenuToAdd){
		//��ȡ�û��˵��Ĺҽӵ�
		String hql = "select dmti from UserMenuItem dmti where dmti.deptMenuTemplateItem=?";
		List<UserMenuItem> list = super.find(hql, parent);
		for(UserMenuItem parentUserMenu : list){ //�û��˵��Ĺҽӵ�
			
			UserMenuItem newDmti = new UserMenuItem();
			newDmti.setParentMenu(parentUserMenu); //��Ҫ�ҽӵĸ��˵�
			newDmti.setUserMenu(parentUserMenu.getUserMenu());
			newDmti.setEnabled(Integer.valueOf(1));
			newDmti.setLeafFlag(deptMenuToAdd.getLeafFlag());//??
			newDmti.setMenuLevel(deptMenuToAdd.getMenuLevel());
			newDmti.setMenuName(deptMenuToAdd.getMenuName());
			newDmti.setMenuOrder(getMaxUserChildOrder(parentUserMenu));//get parent max order child
			newDmti.setMenuUrl(deptMenuToAdd.getMenuUrl());
			newDmti.setDeptMenuTemplateItem(deptMenuToAdd);
			super.save(newDmti);
			super.evict(newDmti);
			super.evict(parentUserMenu);
		}
	}
	
	private void saveUserMenuRootItemCascadeWithDept(DeptMenuTemplate parent, DeptMenuTemplateItem deptMenuToAdd){
		//��ȡ�û��˵��Ĺҽӵ�
		String hql = "select dmti from UserMenu dmti where dmti.deptMenuTemplate=?";
		List<UserMenu> list = super.find(hql, parent);
		for(UserMenu userMenu : list){ //�û��˵��Ĺҽӵ�
			
			UserMenuItem newDmti = new UserMenuItem();
			newDmti.setParentMenu(null); //��Ҫ�ҽӵĸ��˵�
			newDmti.setUserMenu(userMenu);
			newDmti.setEnabled(Integer.valueOf(1));
			newDmti.setLeafFlag(deptMenuToAdd.getLeafFlag());//??
			newDmti.setMenuLevel(deptMenuToAdd.getMenuLevel());
			newDmti.setMenuName(deptMenuToAdd.getMenuName());
			newDmti.setMenuOrder(getMaxUserChildOrder(userMenu));//get parent max order child
			newDmti.setMenuUrl(deptMenuToAdd.getMenuUrl());
			newDmti.setDeptMenuTemplateItem(deptMenuToAdd);
			super.save(newDmti);
			super.evict(newDmti);
			super.evict(userMenu);
		}
	}
	
	public List<SystemMenuTemplateItem> findMenusByName(String name) {
		List list = null;
		list = super.findBy(SystemMenuTemplateItem.class, "menuName", name);
		return list;
	}

	public void removeMenu(String menuId) {
		SystemMenuTemplateItem menu = this.get(SystemMenuTemplateItem.class, Long.valueOf(menuId));
		String hql="from DeptMenuTemplateItem dmti where dmti.systemMenuTemplateItem=?";
		List<DeptMenuTemplateItem> deptTemplates = super.find(hql, menu);
		for(DeptMenuTemplateItem dmt: deptTemplates){
			//��ɾ���û��˵��˵���
			String hqlDept="from UserMenuItem dmti where dmti.deptMenuTemplateItem=?";
			List<UserMenuItem> userTemplates = super.find(hqlDept, dmt);
			for(UserMenuItem umt: userTemplates){
				super.remove(umt);
			}
			super.remove(dmt);
		}
		super.remove(menu);
	}

}
