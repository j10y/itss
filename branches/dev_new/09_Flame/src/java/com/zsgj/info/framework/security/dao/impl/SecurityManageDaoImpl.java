package com.zsgj.info.framework.security.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.zsgj.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableRole;
import com.zsgj.info.appframework.metadata.entity.SystemTableRoleColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.info.framework.orm.BaseDao;
import com.zsgj.info.framework.security.Constants;
import com.zsgj.info.framework.security.dao.SecurityManageDao;
import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Resource;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserActionLog;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.PropertiesUtil;


public class SecurityManageDaoImpl extends BaseDao implements SecurityManageDao {

	public List selectModulesHasNoChild() {
		DetachedCriteria dc = DetachedCriteria.forClass(Module.class);
		dc.add(Restrictions.isNull("parentModule"));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}

	public void deleteModuleById(Long id) {
		getDaoSupport().delete(Module.class, id);
	}

	public Module insertOrUpdateModule(Module module) {
		if(module.getId()!=null&& module.getParentModule()==null){
			//加载其所有子模块
			DetachedCriteria dc = DetachedCriteria.forClass(Module.class);
			dc.add(Restrictions.eq("id", module.getId()));
			dc.setFetchMode("childModules", FetchMode.JOIN);
			Module obj = (Module) getDaoSupport().uniqueResult(dc);
			module.setChildModules(obj.getChildModules());
			
		}
		return (Module) getDaoSupport().insertOrUpdate(module);
	}

	public List selectResourcesByType(String type) {
		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		if(type.equalsIgnoreCase(Constants.RESOURCE_FUNCTION)){
			
		}else if(type.equalsIgnoreCase(Constants.RESOURCE_URL)){
			
		}
		return null;
	}

	public void deleteResourceById(Long id) {
		getDaoSupport().delete(Resource.class, id);
	}

	public Resource insertOrUpdateResource(Resource res) {
		return (Resource) getDaoSupport().insertOrUpdate(res);
	}

	public Module selectModuleById(Long id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Module.class);
		dc.add(Restrictions.eq("id", id));
		dc.setFetchMode("childModules", FetchMode.JOIN);
		dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		Module module = (Module) getDaoSupport().uniqueResult(dc); 
		return module;
	}

	public List selectModulesAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(Module.class);
		dc.addOrder(Order.asc("parentModule"));
		dc.setFetchMode("childModules", FetchMode.JOIN);
		dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}

	public Resource selectResourceById(Long id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		dc.add(Restrictions.eq("id", id));
		Resource result = (Resource) getDaoSupport().uniqueResult(dc);
		return result;
	}

	public Resource selectResourceWithAuthorizationById(Long id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		dc.add(Restrictions.eq("id", id));
		dc.setFetchMode("authorizations", FetchMode.JOIN);
		Resource result = (Resource) getDaoSupport().uniqueResult(dc);
		return result;
	}

	public List selectResourcesAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		dc.setFetchMode("module", FetchMode.JOIN);
		return getDaoSupport().selectByCriteria(dc);
	}

	public List selectResourcesAllWithAuthorization() {
		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		dc.setFetchMode("authorizations", FetchMode.JOIN);
		return getDaoSupport().selectByCriteria(dc);
	}
	
	public void deleteAuthorizById(Long id) {
		getDaoSupport().delete(Authorization.class, id);
		
	}

	public void deleteRightById(Long id) {
		getDaoSupport().delete(Right.class, id);
		
	}

	public Authorization insertOrUpdateAuthoriz(Authorization auth) {
		return (Authorization) getDaoSupport().insertOrUpdate(auth);
	}

	public Right insertOrUpdateRight(Right right) {
		return (Right) getDaoSupport().insertOrUpdate(right);
	}

	public List selectAuthorizationsAll() {
		return getDaoSupport().selectAll(Authorization.class);
	}

	public Authorization selectAuthorizById(Long id) {
		return (Authorization) getDaoSupport().select(Authorization.class, id);
	}

	public Right selectRightById(Long id) {
		Right right = (Right) getDaoSupport().select(Right.class, id);
		getDaoSupport().flush();
		return right;
	}

	public List selectRightsAll() {
		return getDaoSupport().selectAll(Right.class);
	}

	public void deleteRoleById(Long roleId) {
		Role role = (Role) getDaoSupport().select(Role.class, roleId);
		getDaoSupport().executeUpdate("delete from RoleAuthoriz where role=?", new Object[]{role} );
		getDaoSupport().executeUpdate("delete from UserRole where role=?", new Object[]{role} );
		getDaoSupport().delete(Role.class, roleId);
	}

	public Role insertOrUpdateRole(Role role) {
		return (Role) getDaoSupport().insertOrUpdate(role);
	}

	public void saveSystemTableRoleColumnToUser(UserInfo userInfo, SystemTableRole str) {
		SystemMainTable smt = str.getSystemMainTable();
		Role role = str.getRole();
		Integer settingType = str.getSettingType();
				
		DetachedCriteria csts = DetachedCriteria.forClass(SystemTableRoleColumn.class);
		csts.add(Restrictions.eq("systemTableRole", str));
		List list = getDaoSupport().selectByCriteria(csts);
		if(!list.isEmpty()){
			Iterator iter = list.iterator();
			while(iter.hasNext()){
				SystemTableRoleColumn stqc = (SystemTableRoleColumn) iter.next();
				Column column = stqc.getColumn();
				//判断是否是所有用户

				DetachedCriteria c = DetachedCriteria.forClass(UserTableSetting.class);
				c.add(Restrictions.eq("userInfo", userInfo));
				c.add(Restrictions.eq("systemMainTable", smt));
				//Long id = smt.getId();
				c.add(Restrictions.eq("settingType", settingType));
				c.addOrder(Order.asc("order"));
				if(column instanceof SystemMainTableColumn){
					c.add(Restrictions.eq("mainTableColumn", (SystemMainTableColumn)column));
					Long cid = column.getId();
					//System.out.println("cid: "+ cid);
				}
//				else if(column instanceof SystemMainTableExtColumn){
//					c.add(Restrictions.eq("extendTableColumn", (SystemMainTableExtColumn)column));
//				}
				Object object =  getDaoSupport().uniqueResult(c); //此处tUserTableSetting出现过重复记录，如主动抓取无关系
				if(object!=null){
					UserTableSetting uts = (UserTableSetting) object;
					uts.setOrder(stqc.getOrder());
					uts.setIsDisplay(stqc.getIsDisplay());
					uts.setLengthForPage(stqc.getLengthForPage());
					uts.setHiddenValue(stqc.getHiddenValue());
					uts.setIsMustInput(stqc.getIsMustInput());
					getDaoSupport().insertOrUpdate(uts);// update
					getDaoSupport().evict(uts);
				}else{
					UserTableSetting uts = new UserTableSetting();
					uts.setUserInfo(userInfo);
					uts.setSystemMainTable(smt);
					if(column instanceof SystemMainTableColumn){
						uts.setMainTableColumn((SystemMainTableColumn)column);
					}
//					else if(column instanceof SystemMainTableExtColumn){
//						uts.setExtendTableColumn((SystemMainTableExtColumn)column);
//					}
					uts.setSettingType(settingType);
					uts.setIsDisplay(stqc.getIsDisplay());
					uts.setOrder(stqc.getOrder());
					uts.setLengthForPage(stqc.getLengthForPage());
					uts.setHiddenValue(stqc.getHiddenValue());
					uts.setIsMustInput(stqc.getIsMustInput());
					getDaoSupport().insertOrUpdate(uts); //insert 
					getDaoSupport().evict(uts);
					
				}
				//getDaoSupport().evict(userInfo);
				
			}
		}
		
		
	}
	
	public UserInfo saveUserInfoStyleWithRoles(UserInfo userInfo) {
		boolean isAdd = false;
		if(userInfo.getId()==null){
			isAdd = true;
			String hql="select count(*) from UserInfo u where u.userName=? ";
			Long ucount = (Long) getDaoSupport().selectForObject(hql, new Object[]{userInfo.getUserName()});
			if(ucount.intValue()>01){
				throw new DaoException("该用户已存在");
			}
		}
		getDaoSupport().insertOrUpdate(userInfo);
		return userInfo;
	}

	@SuppressWarnings("unchecked")
	public UserInfo saveUserInfoWithRoles(UserInfo userInfo) {
		boolean isAdd = false;
		if(userInfo.getId()==null){
			isAdd = true;
			String hql="select count(*) from UserInfo u where u.userName=? ";
			Long ucount = (Long) getDaoSupport().selectForObject(hql, new Object[]{userInfo.getUserName()});
			if(ucount.intValue()>01){
				throw new DaoException("该用户已存在");
			}
		}
		getDaoSupport().insertOrUpdate(userInfo);
		
		//设置用户的角色可见字段，注意此时同步的用户可见字段优先于后面的，而且要避免重复记录产生
		
		//begin
		Set roles = userInfo.getRoles();

		
		//初始化部门管理员类型的用户菜单模板
		Department dept = userInfo.getDepartment();
		if(dept==null){
			DetachedCriteria ddc = DetachedCriteria.forClass(Department.class);
			String rootDeptCode = PropertiesUtil.getProperties("system.dept.rootdeptcode", "50000075");
			ddc.add(Restrictions.eq("departCode", Long.valueOf(rootDeptCode)));
			Department result = (Department) getDaoSupport().uniqueResult(ddc);
			dept = result;
		}
		
		if(isAdd){
			//==============================
			/*DetachedCriteria dcMtb = DetachedCriteria.forClass(SystemMainTable.class);
			dcMtb.add(Restrictions.isNotNull("primaryKeyColumn"));
			dcMtb.add(Restrictions.isNotNull("tableName"));
			List dcMtbs = getDaoSupport().selectByCriteria(dcMtb);
			smt: for(int i=0; i<dcMtbs.size(); i++){
				SystemMainTable smt = (SystemMainTable) dcMtbs.get(i);
				
				DetachedCriteria dc = DetachedCriteria.forClass(SystemTableSetting.class);
				dc.add(Restrictions.eq("systemMainTable", smt));
				dc.add(Restrictions.isNotNull("settingType"));
				
				List<SystemTableSetting> list = getDaoSupport().selectByCriteria(dc);
				sts: for(SystemTableSetting stt : list){
					//Column column = stt.getColumn();
					//判断是否有系统角色可见字段，如果有则saveUserInfoWithRoles方法的前面部分已经给用户分配过此表对应settingtype的字段了
					DetachedCriteria dcRoleColumn = DetachedCriteria.forClass(SystemTableRole.class);
					dcRoleColumn.add(Restrictions.eq("systemMainTable", stt.getSystemMainTable()));
					dcRoleColumn.add(Restrictions.eq("settingType", stt.getSettingType()));
					dcRoleColumn.setProjection(Projections.rowCount());
					Integer roleColumnCount = (Integer) getDaoSupport().uniqueResult(dcRoleColumn);
					if(roleColumnCount.intValue()==0){ //没有系统角色可见字段,才插入否则重复记录将产生
						UserTableSetting utsList = new UserTableSetting();
						utsList.setSettingType(stt.getSettingType());
						utsList.setUserInfo(userInfo);
						utsList.setSystemMainTable(stt.getSystemMainTable());
						utsList.setMainTableColumn(stt.getMainTableColumn());
						utsList.setExtendTableColumn(stt.getExtendTableColumn());
						utsList.setIsDisplay(stt.getIsDisplay());
						utsList.setOrder(stt.getOrder());
						getDaoSupport().insertOrUpdate(utsList);
						getDaoSupport().evict(utsList);
					}else{
						break sts;
					}
				}
			}
			//初始化查询字段给当前用户
			DetachedCriteria dcStq = DetachedCriteria.forClass(SystemTableQueryColumn.class);
			dcStq.add(Restrictions.isNotNull("systemTableQuery"));
			Iterator itera = getDaoSupport().selectByCriteria(dcStq).iterator();
			while(itera.hasNext()){
				SystemTableQueryColumn stqc = (SystemTableQueryColumn) itera.next();
				SystemTableQuery stq = stqc.getSystemTableQuery();
				UserTableQueryColumn utqc = new UserTableQueryColumn();
				utqc.setSystemTableQuery(stq);
				utqc.setSystemTableQueryColumn(stqc);
				utqc.setIsDisplay(stqc.getIsDisplay());
				utqc.setOrder(stqc.getOrder());
				utqc.setUserInfo(userInfo);
				getDaoSupport().insertOrUpdate(utqc);
			}	*/
		}//end if isAdd
		/*StringBuffer buff = new StringBuffer();
		buff.append("delete from UserTableSetting "); 
		buff.append("where mainTableColumn.id not in ( ");
		buff.append(" select mc.id from SystemMainTableColumn mc");
		buff.append(") ");
		getDaoSupport().executeUpdate(buff.toString(),null);
		
		buff=new StringBuffer();
		buff.append("delete from SystemTableSetting  ");
		buff.append("where mainTableColumn.id not in ( ");
		buff.append(" select mc.id from SystemMainTableColumn mc"); 
		buff.append(") ");
		getDaoSupport().executeUpdate(buff.toString(),null);

		buff=new StringBuffer();
		buff.append("delete from SystemTableQueryColumn  ");
		buff.append("where mainTableColumn.id not in ( ");
		buff.append(" select mc.id from SystemMainTableColumn mc");
		buff.append(") ");
		getDaoSupport().executeUpdate(buff.toString(),null);
		
		buff=new StringBuffer();
		buff.append("delete from UserTableQueryColumn  ");
		buff.append("where mainTableColumn.id not in ( ");
		buff.append(" select mc.id from SystemMainTableColumn mc");
		buff.append(") ");
		getDaoSupport().executeUpdate(buff.toString(),null);
		
		buff=new StringBuffer();
		buff.append("delete from SystemTableRoleColumn  ");
		buff.append("where mainTableColumn.id not in ( ");
		buff.append(" select mc.id from SystemMainTableColumn mc");
		buff.append(") ");
		getDaoSupport().executeUpdate(buff.toString(),null);*/

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
	
	public Role selectRoleById(Long id) {
		Role role = null;
		DetachedCriteria dc = DetachedCriteria.forClass(Role.class);
		dc.setFetchMode("deptMenuTemplate", FetchMode.JOIN);
		dc.add(Restrictions.eq("id", id));
		role = (Role) getDaoSupport().uniqueResult(dc);
		return role;
	}

	public List selectRolesAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(Role.class);
		dc.setFetchMode("department", FetchMode.JOIN);
		List list = super.getDaoSupport().selectByCriteria(dc);
		return list;
	}

	public List selectAuthorizationsOrderByReourceModule() {
		DetachedCriteria dc = DetachedCriteria.forClass(Authorization.class);
		dc.setFetchMode("right", FetchMode.JOIN);
		dc.createAlias("resource", "res").setFetchMode("res", FetchMode.JOIN);
		dc.setFetchMode("res.module", FetchMode.JOIN);
		dc.addOrder(Order.asc("res.module"));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}

	public List selectModuleWithAuthorizations() {
		DetachedCriteria dc = DetachedCriteria.forClass(Module.class);
		dc.setFetchMode("authorizations", FetchMode.JOIN);
		List modules = getDaoSupport().selectByCriteria(dc);
		return modules;
	}

	public void deleteUserById(Long userId) {
		//getDaoSupport().delete(UserInfo.class, userId);
		//getDaoSupport().executeUpdate("delete UserTableSetting uts where uts.userInfo.id=?", userId);
	}

	public List selectRoleAuthorizationsAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public UserInfo selectUserById(Long userId) {
		return (UserInfo) getDaoSupport().select(UserInfo.class, userId);
	}

	public List selectUsersByUserNameOrRealName(String username, String realname) {
		DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
		if(username!=null&& !username.equals("")){
			dc.add(Restrictions.like("userName", username, MatchMode.ANYWHERE));
		}
		if(realname!=null&& !realname.equals("")){
			dc.add(Restrictions.like("realName", realname, MatchMode.ANYWHERE));
		}
		/*dc.add(Restrictions.or(
				Restrictions.like("userName", username, MatchMode.ANYWHERE), 
				Restrictions.like("realName", realname, MatchMode.ANYWHERE))
				);*/
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}
	
	

	public UserActionLog saveUserActionLog(UserActionLog userActionLog) {
		// TODO Auto-generated method stub
		
		UserActionLog ual = (UserActionLog) getDaoSupport().insertOrUpdate(userActionLog);
		
		return ual;
	}

	
}
