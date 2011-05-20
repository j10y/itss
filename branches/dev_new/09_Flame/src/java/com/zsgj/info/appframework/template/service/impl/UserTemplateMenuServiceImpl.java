package com.zsgj.info.appframework.template.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.template.entity.DeptMenuTemplate;
import com.zsgj.info.appframework.template.entity.DeptMenuTemplateItem;
import com.zsgj.info.appframework.template.entity.UserMenu;
import com.zsgj.info.appframework.template.entity.UserMenuItem;
import com.zsgj.info.appframework.template.service.UserTemplateMenuService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.PropertiesUtil;

public class UserTemplateMenuServiceImpl extends BaseDao implements UserTemplateMenuService {
 
	public void saveUserDeptTemplateChange(UserMenu umPara, DeptMenuTemplate dmtNew) {
		Criteria c = super.getCriteria(UserMenu.class);
		c.add(Restrictions.eq("id", umPara.getId()));
		c.setFetchMode("deptMenuTemplate", FetchMode.JOIN);
		UserMenu umOld = (UserMenu) c.uniqueResult(); //当前用户菜单模板
		
		DeptMenuTemplate dmtOld = umOld.getDeptMenuTemplate(); //老的部门菜单模板
		//先改用户的，先找使用了老部门模板的用户模板
		String hql="select um from UserMenu um where um.deptMenuTemplate=?";
		List<UserMenu> list = super.find(hql, dmtOld);
		for(UserMenu um: list){
			//删除用户使用的部门模板项目的用户菜单项目，保留原来的用户模板
			super.executeUpdate("delete from UserMenuItem umi where umi.userMenu=?" , um);
		}
		
		//使用新的部门菜单模板项目更新用户菜单模板项目, 同时考虑merge问题
		for(UserMenu userMenu: list){
			//获取新的部门菜单模板下的所有菜单项目
			Criteria cd = super.getCriteria(DeptMenuTemplateItem.class);
			cd.add(Restrictions.isNull("parentMenu"));
			cd.setFetchMode("childMenus", FetchMode.JOIN);
			cd.add(Restrictions.eq("deptMenuTemplate", dmtNew)); //使用新的部门菜单模板
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
				
				this.initDeptItemFromSysItem(userMenu, userMenuItem, currUserMenu);
			}
		}
		
		
	}

	public UserInfo findUserByItcode(String itcode) {
		Criteria c = super.getCriteria(UserInfo.class);
		c.add(Restrictions.eq("itcode", itcode));
		UserInfo userInfo = (UserInfo) c.uniqueResult();
		return userInfo;
	}

	public GrantedAuthority[] selectAcegiRoleNamesByUser(UserInfo user) {//from acegiRoleDaoImpl
		Set authories = new HashSet();
		Set roles = user.getRoles();
		Iterator iter = roles.iterator();
		while(iter.hasNext()){
			Role role = (Role) iter.next();
	
			Set tempSet = role.getAuthorizations();
			authories.addAll(tempSet);
		}
		//----------------
		List roleNames = new ArrayList();
		Iterator itera = authories.iterator();
		while(itera.hasNext()){
			Authorization auth = (Authorization)itera.next();
			Right right = auth.getRight();
			if(right!=null) {
				roleNames.add(right.getKeyName());
			}
		}
		return role2authorities(roleNames);
	}

	public void removeUserMenu(String[] umsId) {
		if(umsId==null|| umsId.length==0){
			throw new ServiceException("请选择要删除的用户菜单模板");
		}
		for(int i=0; i<umsId.length; i++){
			UserMenu um = (UserMenu) super.get(UserMenu.class, Long.valueOf(umsId[i]));
			super.executeUpdate("delete UserMenuItem umi where umi.userMenu=?", new Object[]{um});
			super.remove(um);
		}
		
	}

	
	private static GrantedAuthority[] role2authorities(Collection roleNames) {
		List authorities = new ArrayList();
		for (Iterator iter = roleNames.iterator(); iter.hasNext();) {
			String roleName = (String) iter.next();
			GrantedAuthority g = new GrantedAuthorityImpl(roleName);//ROLE_开始的角色名称
			authorities.add(g);
		}
		return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[0]);
	}
	
	protected boolean isUserAdmin(GrantedAuthority[] userDetails) {
		for (int i = 0; i < userDetails.length; i++) {
			if (userDetails[i].equals(PropertiesUtil.getProperties("system.adminkey.useradmin", "ROLE_USER_ADMIN"))) {
				return true;
			}
		}
		return false;
	}
	
	public List findUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public List findUserAsUserAdminRight() {
		List result = new ArrayList();
		String hql="select usr from UserInfo usr order by usr.itcode asc";
		List list = super.find(hql, null);
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			//Long userId = (Long) iter.next();
			//UserInfo userInfo = new UserInfo(userId);
			UserInfo userInfo = (UserInfo) iter.next();
			GrantedAuthority[] userDetails = this.selectAcegiRoleNamesByUser(userInfo);
			boolean isUserAdmin	= this.isUserAdmin(userDetails);
			if(isUserAdmin){
				result.add(userInfo);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Page findUserMenus(Map params, int pageNo, int pageSize){
		Page page = null;
		String userInfo = (String) params.get("userInfo");
		
		List<Long> userInfos = null;
		Criteria cUser = super.getCriteria(UserInfo.class);
		if(StringUtils.isNotBlank(userInfo)){
			
			Disjunction disjunction = Restrictions.disjunction();
			disjunction.add(Restrictions.like("userName", userInfo,MatchMode.START));
			disjunction.add(Restrictions.like("realName", userInfo,MatchMode.START));
			cUser.add(disjunction);
		
			cUser.setProjection(Projections.property("id"));
			userInfos = cUser.list();
		}
		Criteria c = super.getCriteria(UserMenu.class);
		if(userInfos!=null&&!userInfos.isEmpty()){
			c.add(Restrictions.in("userInfo.id", userInfos));
		}
		page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public List findUserMenus() {
		List list = null;
		list = super.getObjects(UserMenu.class);
		return list;
	}

	public void removeUserMenu(String smsId) {
		UserMenu smt = super.get(UserMenu.class, Long.valueOf(smsId));
		super.executeUpdate("delete UserMenuItem smti where smti.userMenu=?", new Object[]{smt});
		super.remove(smt);
	}

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
	
	public UserMenu saveUserMenu(UserMenu um) {
		UserMenu userMenu = null;
		//if(um.getId()==null){
		/*String hql="select count(*) from UserMenu um where um.userInfo=?";
		Query query = super.createQuery(hql, new Object[]{um.getUserInfo()});
		Long count = (Long) query.uniqueResult();
		if(count.intValue()>0){
			throw new ServiceException("此用户已经具有一套模板，不能再增加新模板");
		}*/
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
			
		//}
		
		return userMenu;
	}

	public List<UserMenuItem> findChildenByParent(String parentMenuId){
		UserMenuItem parent = this.get(UserMenuItem.class, Long.valueOf(parentMenuId));
		String hql = "select m from UserMenuItem m where m.parentMenu=?  order by m.menuOrder";
		List list = super.find(hql, parent);
		return list;
	}

	public UserMenuItem modifyMenuName(String menuId, String menuName) {
		UserMenuItem menu = this.get(UserMenuItem.class, Long.valueOf(menuId));
		menu.setMenuName(menuName);
		this.saveMenu(menu);
		return menu;
	}

	/**
	 * 删除菜单节点：先下移，再删除（级联删除子节点）
	 */
	public void removeNode(String menuId) {
		UserMenuItem menu = this.get(UserMenuItem.class, Long.valueOf(menuId));
		this.downNode(menu.getParentMenu().getId(), menu.getMenuOrder(), -1);
		super.remove(menu); //级联删除用户菜单项目及子菜单
	}

	/**
	 * 指定的节点下移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点移动发生时所在的位置
	 * @param maxIndex	指定节点要移动到的目标位置
	 */
	public void downNode(Long parentId, Integer minIndex, Integer maxIndex){
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
	}
	
	/**
	 * 指定的节点上移
	 * @param parentId	指定范围内要移动的节点的父节点
	 * @param minIndex	指定节点要移动到的目标位置
	 * @param maxIndex	指定节点移动发生时所在的位置
	 */
	@SuppressWarnings("unchecked")
	public void upNode(Long parentId, Integer minIndex, Integer maxIndex){
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
	}
	
	public void saveNodeMove(String mId, String oldPid, String newPid, String nodeIndx) {
		
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

	public List<UserMenuItem> findAllMenu() {
		return this.getObjects(UserMenuItem.class);
	}


	public UserMenuItem findMenuById(String id) {
		UserMenuItem menu = null;
		menu = this.get(UserMenuItem.class, Long.valueOf(id));
		return menu;
	}


	public UserMenuItem saveMenu(UserMenuItem menu) {
		UserMenuItem result = null;
		result = (UserMenuItem) super.save(menu);
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
	

	public List<UserMenuItem> findMenusByName(String name) {
		List list = null;
		list = super.findBy(UserMenuItem.class, "menuName", name);
		return list;
	}

	public void removeMenu(String menuId) {
		UserMenuItem menu = this.get(UserMenuItem.class, Long.valueOf(menuId));
		this.executeUpdate("delete from UserMenuItem m where m.parentMenu=?", menu);
		super.removeById(UserMenuItem.class, Long.valueOf(menuId));
	}

	public UserMenu findUserMenuById(String userMenuId) {
		UserMenu um = super.get(UserMenu.class, Long.valueOf(userMenuId));
		return um;
	}

	public List<UserMenuItem> findUserMenuItemNoParent(String userMenuId) {
		UserMenu umt = super.get(UserMenu.class, Long.valueOf(userMenuId));
		String hql="select umi from UserMenuItem umi where umi.parentMenu is null and umi.userMenu=?  order by umi.menuOrder";
		List<UserMenuItem> itemList = super.find(hql, umt);
		List<UserMenuItem> returnList = new ArrayList<UserMenuItem>();
		
		for(UserMenuItem item:itemList){
			UserMenuItem parentMenuItem = new UserMenuItem();
			parentMenuItem.setId(new Long(0));
			item.setParentMenu(parentMenuItem);
			returnList.add(item);
		}
		
		return returnList;
	}

	public List<UserMenuItem> findChildenByParentAndUserMenu(String parentId, String userMenuId) {
		UserMenuItem umi = super.get(UserMenuItem.class, Long.valueOf(parentId));
		UserMenu um = super.get(UserMenu.class, Long.valueOf(userMenuId));		
		String hql="select umi from UserMenuItem umi where umi.parentMenu =? and umi.userMenu=?  order by umi.menuOrder";
		List<UserMenuItem> itemList = super.find(hql, new Object[]{umi,um});
		return itemList;
	}

	public UserMenuItem saveNodeEnabled(String nodeId, String enabled) {
		
		Integer enabledFlag = new Integer(enabled);
		String hql="update UserMenuItem umi set umi.enabled = ? where umi.id = ?";
		super.executeUpdate(hql, Integer.valueOf(enabledFlag), Long.valueOf(nodeId));
		UserMenuItem umi = super.get(UserMenuItem.class, Long.valueOf(nodeId));
		
		return umi;
	}

	public List<UserMenuItem> findChildenByParentAndUserId(String nodeId, String userId) {
//		UserInfo user = super.get(UserInfo.class, Long.valueOf(userId));
//		String hql="select um.id from UserMenu um where um.userInfo = ? ";
//		List list = super.find(hql, user);
		List itemList = null;
		if(nodeId!=null){
			UserMenuItem parentMenu = super.get(UserMenuItem.class, Long.valueOf(nodeId));
			UserInfo user = super.get(UserInfo.class, Long.valueOf(userId));	
			String hql="select um.id from UserMenu um where um.userInfo = ? ";
			List list = super.find(hql, user);
			
			Criteria c = super.getCriteria(UserMenuItem.class);
			c.add(Restrictions.eq("enabled", Integer.valueOf(1)));
			c.add(Restrictions.eq("parentMenu", parentMenu));
			c.addOrder(Order.asc("menuOrder"));
			c.add(Restrictions.in("userMenu.id", list));
			itemList = c.list();
		}
		return itemList;
	}

	public List<UserMenuItem> findUserMenuItemNoParentByUserId(String userId) {
		List<UserMenuItem> returnList = new ArrayList<UserMenuItem>();
		
		UserInfo user = super.get(UserInfo.class, Long.valueOf(userId));
		String hql="select um.id from UserMenu um where um.userInfo = ? ";
		List list = super.find(hql, user);
		if(!list.isEmpty()){
			Criteria c = super.getCriteria(UserMenuItem.class);
			c.add(Restrictions.eq("enabled", Integer.valueOf(1)));
			c.add(Restrictions.isNull("parentMenu"));
			c.addOrder(Order.asc("menuOrder"));
			c.add(Restrictions.in("userMenu.id", list));
			List<UserMenuItem> itemList = c.list();
			
			
			
			for(UserMenuItem item:itemList){
				UserMenuItem parentMenuItem = new UserMenuItem();
				parentMenuItem.setId(new Long(0));
				item.setParentMenu(parentMenuItem);
				returnList.add(item);
			}
		}
		/*UserInfo user = super.get(UserInfo.class, Long.valueOf(userId));
		UserMenu userMenu = super.findUniqueBy(UserMenu.class, "userInfo", user);		
		String hql="select umi from UserMenuItem umi where umi.parentMenu is null and umi.userMenu= ?  order by umi.menuOrder";
		List<UserMenuItem> itemList = super.find(hql, userMenu);
		List<UserMenuItem> returnList = new ArrayList<UserMenuItem>();
		
		for(UserMenuItem item:itemList){
			UserMenuItem parentMenuItem = new UserMenuItem();
			parentMenuItem.setId(new Long(0));
			item.setParentMenu(parentMenuItem);
			returnList.add(item);
		}*/
		
		return returnList;
	}

	
	
	public List<UserMenuItem> findUserMenuItemAllNoParentByUserId(String userId) {
		List<UserMenuItem> returnList = new ArrayList<UserMenuItem>();
		
		UserInfo user = super.get(UserInfo.class, Long.valueOf(userId));
		String hql="select um.id from UserMenu um where um.userInfo = ? ";
		List list = super.find(hql, user);
		if(!list.isEmpty()){
			Criteria c = super.getCriteria(UserMenuItem.class);
			//c.add(Restrictions.eq("enabled", Integer.valueOf(1)));
			c.add(Restrictions.isNull("parentMenu"));
			c.addOrder(Order.asc("menuOrder"));
			c.add(Restrictions.in("userMenu.id", list));
			List<UserMenuItem> itemList = c.list();
			
			for(UserMenuItem item:itemList){
				UserMenuItem parentMenuItem = new UserMenuItem();
				parentMenuItem.setId(new Long(0));
				item.setParentMenu(parentMenuItem);
				returnList.add(item);
			}
		}
		return returnList;
	}

	public List<UserMenuItem> findAllMenuTitleByUserId(String userId) {	
		List itemList = new ArrayList();
		UserInfo user = super.get(UserInfo.class, Long.valueOf(userId));
		String hql="select um.id from UserMenu um where um.userInfo = ? ";
		List list = super.find(hql, user);
		if(!list.isEmpty()){
			Criteria c = super.getCriteria(UserMenuItem.class);
			c.add(Restrictions.eq("enabled", Integer.valueOf(1)));
			c.add(Restrictions.isNull("parentMenu"));
			c.addOrder(Order.asc("menuOrder"));
			c.add(Restrictions.in("userMenu.id", list));
			itemList = c.list();
		}
		
		return itemList;
	}
	
	public List<UserMenuItem> findUserSettingMenuChildenByParentAndUserId(String nodeId, String userId) {
		
		List itemList = null;
		if(nodeId!=null){
			UserMenuItem parentMenu = super.get(UserMenuItem.class, Long.valueOf(nodeId));
			UserInfo user = super.get(UserInfo.class, Long.valueOf(userId));	
			String hql="select um.id from UserMenu um where um.userInfo = ? ";
			List list = super.find(hql, user);
			
			Criteria c = super.getCriteria(UserMenuItem.class);
			//c.add(Restrictions.eq("enabled", Integer.valueOf(1)));
			c.add(Restrictions.eq("parentMenu", parentMenu));
			c.addOrder(Order.asc("menuOrder"));
			c.add(Restrictions.in("userMenu.id", list));
			itemList = c.list();
		}
		return itemList;
		
		/*UserMenuItem umi = super.get(UserMenuItem.class, Long.valueOf(nodeId));
		UserInfo user = super.get(UserInfo.class, Long.valueOf(userId));		
		UserMenu userMenu = super.findUniqueBy(UserMenu.class, "userInfo", user);
		String hql="select umi from UserMenuItem umi where umi.parentMenu =? and umi.userMenu=?  order by umi.menuOrder";
		List<UserMenuItem> itemList = super.find(hql, new Object[]{umi,userMenu});
		
		return itemList;*/
	}
}
