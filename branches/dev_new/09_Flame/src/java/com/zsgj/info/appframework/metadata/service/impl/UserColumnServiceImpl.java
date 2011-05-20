package com.zsgj.info.appframework.metadata.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.tuple.StandardProperty;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.Type;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.ExtData;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableIdBuilder;
import com.zsgj.info.appframework.metadata.entity.SystemTableQuery;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableRole;
import com.zsgj.info.appframework.metadata.entity.SystemTableRoleColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.metadata.entity.SystemTableSettingVersion;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.metadata.entity.UserTableSettingVersion;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainAndExtColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.metadata.service.UserColumnService;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.idgen.IdGenRuleMethodHelper;

public class UserColumnServiceImpl extends BaseDao implements UserColumnService {

	private SystemMainTableService systemMainTableService;
	private SystemMainColumnService systemMainColumnService;
	private SystemColumnService systemColumnService;
	private SystemMainAndExtColumnService systemMainAndExtColumnService;

	public SystemTableQuery saveSystemTableQuery(SystemTableQuery stq) {
		String hql = "select count(*) from SystemTableQuery stq where stq.systemMainTable=? and stq.queryType=? ";
		Query query = super.createQuery(hql, new Object[] {
				stq.getSystemMainTable(), SystemTableQuery.QUERY_TYPE_SINGLE });
		Long count = (Long) query.uniqueResult();
		if (count.intValue() == 1) {
			throw new ServiceException("只能有一个单表查询");
		}
		this.save(stq);
		return stq;
	}

	// 查最大的值
	@SuppressWarnings("unused")
	private Long findMaxNumberByOrder(Class clazz, String propertyName) {
		Criteria c = this.createCriteria(clazz);
		c.setProjection(Projections.projectionList().add(
				Projections.max(propertyName).as("maxOrder")));
		Object maxOrder = c.uniqueResult();
		Long orderMax = null;
		if (maxOrder != null) { // 如果为null，以为着没有记录
			orderMax = Long.parseLong(maxOrder.toString());
		}
		return orderMax;
	}

	// 查最大的配置项编号，注意是字符串类型的
	@SuppressWarnings("unused")
	private String findMaxCINumberByOrder(Class clazz, String propertyName) {
		Criteria c = this.createCriteria(clazz);
		c.setProjection(Projections.projectionList().add(
				Projections.max("id").as("maxConfigItemId")));
		Object maxId = c.uniqueResult();
		Long idMax = null;
		if (maxId != null) { // 如果为null，以为着没有记录
			idMax = Long.parseLong(maxId.toString());
		}

		Criteria cc = this.createCriteria(clazz);
		cc.add(Restrictions.eq("id", idMax));
		cc.setProjection(Projections.property(propertyName));
		String maxCINumber = (String) cc.uniqueResult();

		return maxCINumber;

	}

	private SystemMainTableIdBuilder getIdBuilder(SystemMainTable smt) {
		SystemMainTableIdBuilder result = null;
		Criteria c = super.getCriteria(SystemMainTableIdBuilder.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("deployFlag", Integer.valueOf(1)));
		List<SystemMainTableIdBuilder> list = c.list();
		if (!list.isEmpty()) {
			result = list.get(0);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public synchronized Object saveMainAndExtData(Class clazz, Map requestParams) {
		// start add by tongjp 2009-09-15 test why event have saved twice,to
		// make sure whether this method's bug
//		Set sets=requestParams.keySet();
//		Iterator it=sets.iterator();
//		String testst="";
//		while(it.hasNext()){
//			Object ob=it.next();
//			Object st=requestParams.get(ob);
			//remove by zhangzy for st 空指针异常 in 2009 11 24
			//testst=testst+ob.toString()+":"+st.toString()+"==";
//		}
		//System.out.println(testst+"----------------------------------");
		// end
		Object newObject  = BeanUtil.getObject(requestParams, clazz);
		Long mainRowId = null;
		BeanWrapper bwNew = new BeanWrapperImpl(newObject);
		SystemMainTable smt =  systemMainTableService.findSystemMainTableByClazz(clazz);
		
		Criteria c = createCriteria(SystemTableSetting.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.isNotNull("mainTableColumn"));
		c.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
		c.addOrder(Order.asc("order"));
		List list = c.list(); 
		
//		Criteria c = createCriteria(UserTableSetting.class);
//		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
//		c.add(Restrictions.eq("systemMainTable", smt));
//		c.add(Restrictions.isNotNull("mainTableColumn"));
//		c.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
//		c.addOrder(Order.asc("order"));
//		// c.add(Restrictions.eq("isDisplay", new Integer(1)));
//		List list = c.list(); 
//		// ===========================================
//		if(UserContext.getUserInfo()!=null&& list.isEmpty()){// 如因问题未初始化用户字段，这里进行初始化
//			Criteria cSys = super.getCriteria(SystemTableSetting.class);
//			cSys.add(Restrictions.eq("systemMainTable", smt));
//			cSys.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
//			cSys.add(Restrictions.isNotNull("mainTableColumn"));
//			cSys.setFetchMode("mainTableColumn", FetchMode.JOIN);
//			// cSys.add(Restrictions.eq("isDisplay", new Integer(1)));
//			cSys.addOrder(Order.asc("order"));
//			List<SystemTableSetting> listSys = cSys.list();
//			for(SystemTableSetting sts : listSys){
//				UserTableSetting ax = new UserTableSetting();
//				ax.setSystemMainTable(smt);
//				ax.setSettingType(sts.getSettingType());
//				ax.setDescn(sts.getDescn());
//				Column column = sts.getColumn();
//				// if(column instanceof SystemMainTableColumn){
//				ax.setMainTableColumn((SystemMainTableColumn)column);
//				// }else{
//				// ax.setExtendTableColumn((SystemMainTableExtColumn) column);
//				// }
//				ax.setOrder(sts.getOrder());
//				ax.setIsDisplay(sts.getIsDisplay());
//				ax.setIsMustInput(sts.getIsMustInput());
//				ax.setUserInfo(UserContext.getUserInfo());
//				ax.setLengthForPage(sts.getLengthForPage());
//				list.add(ax);
//			}
//		}
		
		// 暂存选中的复选组框字段
		Set<Column> checkboxGroupSet = new HashSet<Column>();
		
		for(int i=0; i<list.size(); i++){
			SystemTableSetting uts = (SystemTableSetting) list.get(i);
			Column column = uts.getColumn();
			Integer isMainAndExt=uts.getMainTableColumn().getIsExtColumn();
			if(isMainAndExt==SystemMainTableColumn.isMain){
				String propertyName = column.getPropertyName();
				
				SystemMainTableColumnType smtct = column.getSystemMainTableColumnType();
				String smtctName = smtct.getColumnTypeName();
				boolean isHiddenType = smtctName.equals("hidden");
				
				Integer isHiddenItem = column.getIsHiddenItem(); // 字段级别的隐藏给前端手动给隐藏域赋值
				boolean isHidden = isHiddenItem!=null&& isHiddenItem.intValue()==1;
				
				// 不可见字段，但对于如订单状态等字段，需要使用最新的值覆盖原值。
				// 也就是对于非隐藏字段的不可见字段才拷贝之前的值
				if(uts.getIsDisplay().intValue()==0&& !isHidden&& !isHiddenType){// 不可见,创建日期存在问题
					Long id = ((BaseObject) newObject).getId();
					if(id!=null){// update
						Object oldObject = this.getObject(clazz, id, true);
						if(oldObject!=null){
							BeanWrapper bwOld = new BeanWrapperImpl(oldObject);
							
							Object oldPropValue = bwOld.getPropertyValue(propertyName);
							bwNew.setPropertyValue(propertyName, oldPropValue);
						}
		
					}
						
				}
//				else{// end
//					Long id = ((BaseObject) newObject).getId();
//					if(id!=null){// update
//						Object newPropertyValue = bwNew.getPropertyValue(propertyName);
//						Object oldObject = this.getObject(clazz, id, true);
//						boolean isNewNull = (newPropertyValue==null|| 
//								newPropertyValue!=null&& newPropertyValue.toString()!=null
//								&& newPropertyValue.toString().equals("null"));		
//					}
					
//				}
				if(smtctName.equalsIgnoreCase("multiFile")){
					if(requestParams.get(propertyName)!=null){
						String nowtime = (String) requestParams.get(propertyName);
									
						SystemMainTable ftable = column.getForeignTable();
						String fileClassName = ftable.getClassName();
						Class fileClazz = this.getClass(fileClassName);
						
						Criteria cf = super.getCriteria(fileClazz);
						cf.add(Restrictions.eq("nowtime", nowtime));
						cf.setProjection(Projections.rowCount());
						Integer cfRow = (Integer) cf.uniqueResult();
						if(cfRow==null|| cfRow.intValue()==0){
							bwNew.setPropertyValue(propertyName, null);
						}
						
					}
					
					
				}else if(smtctName.equalsIgnoreCase("checkboxGroup")){
					// 因需要先保持主对象，故先暂存复选框字段，待保存主对象后，在保存复选数据
					checkboxGroupSet.add(column); 
				}
				
				// 自增序列
				Integer identityFlag=uts.getMainTableColumn().getIdentityFlag();
				//System.out.println("["+uts.getColumnCnName()+"/"+uts.getColumnName()+"]:"+identityFlag);
				// 如果是标识字段，且是新建的，则查询原数据的最大值再加递增量，如果原来无数据，则给出起始值
				if(identityFlag!=null&& identityFlag.intValue()==1&&((BaseObject) newObject).getId()==null){
						// begin
						SystemMainTableIdBuilder smtIdBuilder = this.getIdBuilder(smt);
						synchronized(smtIdBuilder) {
							if(smtIdBuilder!=null){
								String prefix = smtIdBuilder.getPrefix(); // C014-
								Long length = smtIdBuilder.getLength();
								//add by duxh for 没有编号或者编号不符合规则时生成新的编号 in 20100506 begin
								Object curId = bwNew.getPropertyValue(propertyName);
								if(curId==null||curId!=null&&!curId.toString().startsWith(prefix)){
								//add by duxh for 没有编号或者编号不符合规则时生成新的编号 in 20100506 end
									// int numberLength = length.intValue()-prefix.length();
									String ruleFile = smtIdBuilder.getRuleFileName();
									String latestValue = smtIdBuilder.getLatestValue();
									Department dept = smtIdBuilder.getDepartment();
																
									Map map = new HashedMap();
									map.put("ruleName", "systemTableIdGen");
									map.put("prefix", prefix);
									map.put("length", String.valueOf(length));
									map.put("latestValue", latestValue);
									// map.put("latestNumber", latestNumber);
									map.put("dept", dept);
									
									if(ruleFile==null){
										ruleFile = "/com/zsgj/info/framework/util/idgen/systemTableIdGen.drl";
									}
									String nextValue = IdGenRuleMethodHelper.executeRule(ruleFile, map);
									smtIdBuilder.setLatestValue(nextValue);
									bwNew.setPropertyValue(propertyName, nextValue);
								}
							}
						}
						// end
				}
			}
		}
		Object result = null;
		try {
		   result = this.save(newObject);
		   //System.out.println("_______________________________保存实体"+new Date()+"——————————————————————————————————————————————————");
		   // begin_保存复选组框字段数据
		   for(Column column : checkboxGroupSet){
			   String propertyName = column.getPropertyName();
			   SystemMainTable refTable = column.getReferencedTable();
				String refClassName = refTable.getClassName();
				Class refClass = this.getClass(refClassName);// UserRole
				SystemMainTableColumn refVColumn = column.getReferencedTableValueColumn();
				SystemMainTableColumn refPColumn = column.getReferencedTableParentColumn();
				String refvcName = refVColumn.getPropertyName();// role
				String refpcName =refPColumn.getPropertyName(); // user
								
				Set<Long> currentSelects = new HashSet<Long>(); // 暂存选中的对象ID
				// String selectObjectId = "(";
				
				SystemMainTable fTable = column.getForeignTable(); // Role
				String fclassName = fTable.getClassName();
				Class fClass = this.getClass(fclassName);// all roles
				List<BaseObject> fobjects = getAll(fClass);
				Iterator<BaseObject> iter = fobjects.iterator();
				while(iter.hasNext()){ // 遍历外键对象，如Role
					BaseObject fObjectRole = iter.next(); // role Object
					Long fobjectId = fObjectRole.getId();
					String propName = propertyName+"$"+ fobjectId; // roles.11
					Object value = requestParams.get(propName);
					if(value!=null&& value.toString().equalsIgnoreCase("on")){ // 需要选中
						try {
							Object refObject = refClass.newInstance(); // UserRole
																		// Object
							
							Criteria cRef = super.getCriteria(refClass);
							cRef.add(Restrictions.eq(refpcName, newObject));// UserRole.user=userObject
							cRef.add(Restrictions.eq(refvcName, fObjectRole)); // UserRole.role=roleObject
							cRef.setProjection(Projections.rowCount());
							Integer fObjectCount = (Integer) cRef.uniqueResult();
							if(fObjectCount==null||fObjectCount.intValue()==0){
								
								BeanWrapper bWrapper = new BeanWrapperImpl(refObject);
								bWrapper.setPropertyValue(refpcName, newObject);// UserRole.user=userObject
								bWrapper.setPropertyValue(refvcName, fObjectRole);// UserRole.role=roleObject
								super.save(refObject);
								
								currentSelects.add(fobjectId);
								// selectObjectId = selectObjectId +
								// fObjectRole.getId() + ",";
							}
							
						} catch (Exception e) {
							
						}
					}
					
				}// end遍历外键对象
				
				// begin_开始删除之前选中的多余记录
				Criteria cRef = super.getCriteria(refClass);
				// UserRole.user=userObject
				cRef.add(Restrictions.eq(refpcName, newObject));
				// UserRole.role not in(1,2,3)
				//add by lee for 修正包含复选框组保存BUG in 20091209 begin
				if(!currentSelects.isEmpty()){
					cRef.add(Restrictions.not(Restrictions.in(refvcName+".id", currentSelects))); 
				}
				//add by lee for 修正包含复选框组保存BUG in 20091209 end
				List notSelects = cRef.list();
				Iterator iterNotS = notSelects.iterator();
				while(iterNotS.hasNext()){
					Object notSelectObject = iterNotS.next();
					super.save(notSelectObject);
				}
				// end
				
		   }// end_遍历_checkboxGroupSet
		   
		   
		} catch (Exception e) {
			e.printStackTrace();
			// 解决新增的实体无法更新问题
			super.getSession().update(newObject);
			//System.out.println("_______________________________更新实体"+new Date()+"——————————————————————————————————————————————————");
		}		
		mainRowId = ((BaseObject) newObject).getId();
		/*
		 * Criteria cext = createCriteria(UserTableSetting.class);
		 * cext.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		 * cext.add(Restrictions.eq("systemMainTable", smt));
		 * cext.add(Restrictions.isNotNull("extendTableColumn"));
		 * cext.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
		 * cext.addOrder(Order.asc("order")); List listExt = cext.list(); //
		 * ===========================================
		 * if(UserContext.getUserInfo()!=null&&
		 * listExt.isEmpty()){//如因问题未初始化用户字段，这里进行初始化 Criteria cSys =
		 * super.getCriteria(SystemTableSetting.class);
		 * cSys.add(Restrictions.eq("systemMainTable", smt));
		 * cSys.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
		 * cSys.add(Restrictions.isNotNull("extendTableColumn"));
		 * cSys.setFetchMode("extendTableColumn", FetchMode.JOIN);
		 * //cSys.add(Restrictions.eq("isDisplay", new Integer(1)));
		 * cSys.addOrder(Order.asc("order")); List<SystemTableSetting> listSys =
		 * cSys.list(); for(SystemTableSetting sts : listSys){ UserTableSetting
		 * ax = new UserTableSetting(); ax.setSystemMainTable(smt);
		 * ax.setSettingType(sts.getSettingType()); ax.setDescn(sts.getDescn());
		 * Column column = sts.getColumn(); //if(column instanceof
		 * SystemMainTableColumn){
		 * ax.setMainTableColumn((SystemMainTableColumn)column); // }else{ //
		 * ax.setExtendTableColumn((SystemMainTableExtColumn) column); // }
		 * ax.setOrder(sts.getOrder()); ax.setIsDisplay(sts.getIsDisplay());
		 * ax.setIsMustInput(sts.getIsMustInput());
		 * ax.setUserInfo(UserContext.getUserInfo());
		 * ax.setLengthForPage(sts.getLengthForPage()); listExt.add(ax); } }
		 */
		// ===========================================
		for(int i=0; i<list.size(); i++){
			SystemTableSetting uts = (SystemTableSetting) list.get(i);
			Column column = uts.getColumn();
			Integer isMainAndExt=uts.getMainTableColumn().getIsExtColumn();
			if(isMainAndExt==SystemMainTableColumn.isExt){
				SystemMainTableColumn smtc=uts.getMainTableColumn();
				String extColuId=smtc.getId().toString();
				// Integer extTableColumnNum=smtec.getExtendTableColumnNum();
				String propertyName=column.getPropertyName();
				String extColValue=(String) requestParams.get(propertyName);
				ExtData extData=(ExtData) systemMainAndExtColumnService.findObjectByMainRowIdAndExtColId(Integer.parseInt(mainRowId.toString()), Integer.parseInt(extColuId));
				if(extData==null){
					extData=new ExtData();
					extData.setExtendTableId(Integer.parseInt(extColuId));
					extData.setMainTableRowID(Integer.parseInt(mainRowId.toString()));
				}
				if(extColValue!=null&&extColValue.equals("")){
					extColValue=null;
				}
				extData.setExtendTableData(extColValue);
				this.save(extData);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	// 同步用户功能缺少设置orderFlag标记
	public List<SystemTableSetting> findSystemColumns(
			SystemMainTable mainTable, Integer settingType) {

		Criteria c = super.createCriteria(SystemTableSetting.class);
		c.add(Restrictions.eq("systemMainTable", mainTable));
		c.add(Restrictions.eq("settingType", settingType));
		c.addOrder(Order.asc("order"));
		List<SystemTableSetting> list = c.list();
		if (list.isEmpty()) { // 如果没有，读取主表和扩展表字段，初始化
			List<SystemMainTableColumn> mainColumns = systemMainColumnService
					.findSystemMainTableColumns(mainTable);
			int i = 1;
			for (SystemMainTableColumn smtc : mainColumns) {
				SystemTableSetting sts = new SystemTableSetting();
				sts.setSystemMainTable(mainTable);
				sts.setMainTableColumn(smtc);
				sts.setSettingType(settingType);
				// sts.setExtendTableColumn(null);
				sts.setOrder(Integer.valueOf(i++));
				if (settingType.intValue() == 5) {
					if (smtc.getIsOutputItem() != null
							&& smtc.getIsOutputItem().intValue() == 1) {
						sts.setIsDisplay(Integer.valueOf(1));
					}
				} else {
					sts.setIsDisplay(Integer.valueOf(1));
				}
				super.save(sts);
				super.evict(sts);
				list.add(sts);
			}
			// List<SystemMainTableExtColumn> extColumns =
			// systemExtColumnService.findSystemExtendColumns(mainTable);
			// for(SystemMainTableExtColumn smtc : extColumns){
			// SystemTableSetting sts = new SystemTableSetting();
			// sts.setSystemMainTable(mainTable);
			// sts.setExtendTableColumn(smtc);
			// sts.setSettingType(settingType);
			// sts.setIsDisplay(Integer.valueOf(1));
			// sts.setMainTableColumn(null);
			// sts.setOrder(Integer.valueOf(i++));
			// super.save(sts);
			// super.evict(sts);
			// list.add(sts);
			// }
			// 以后修改字段后级联修改此表

		} else {
//			List<SystemMainTableColumn> mainColumns = systemMainColumnService
//					.findSystemMainTableColumns(mainTable);
			List<Long> ids = new ArrayList();
			for (SystemTableSetting sts : list) {
				// mainColumns.remove(sts.getMainTableColumn());
				ids.add(sts.getMainTableColumn().getId());
			}
			Criteria c1 = this.createCriteria(SystemMainTableColumn.class);
			c1.add(Restrictions.eq("systemMainTable", mainTable));
			c1.add(Restrictions.not(Restrictions.in("id", ids)));
			List<SystemMainTableColumn> list1 = c1.list();
			for (SystemMainTableColumn smtc : list1) {
				SystemTableSetting sts = new SystemTableSetting();
				sts.setSystemMainTable(mainTable);
				sts.setMainTableColumn(smtc);
				sts.setSettingType(settingType);
				// sts.setExtendTableColumn(null);
				sts.setOrder(Integer.valueOf(1));
				if (settingType.intValue() == 5) {
					if (smtc.getIsOutputItem() != null
							&& smtc.getIsOutputItem().intValue() == 1) {
						sts.setIsDisplay(Integer.valueOf(1));
					}
				} else {
					sts.setIsDisplay(Integer.valueOf(1));
				}
				super.save(sts);
				super.evict(sts);
				list.add(sts);
			}
		}
		return list;
	}

	public List<SystemTableQuery> findSystemTableQueryAll(SystemMainTable smt) {
		String hql = "from SystemTableQuery stq where stq.systemMainTable=? ";
		Query query = super.createQuery(hql, new Object[] { smt });
		List list = query.list();
		return list;
	}

	public List<SystemTableRole> findSystemTableRoleAll(SystemMainTable smt) {
		String hql = "from SystemTableRole stq where stq.systemMainTable=? ";
		Query query = super.createQuery(hql, new Object[] { smt });
		List list = query.list();
		return list;
	}

	public SystemTableRole saveSystemTableRole(SystemTableRole str) {
		if (str.getId() == null) {
			Role role = str.getRole();
			Integer settingType = str.getSettingType();
			String hql = "select count(*) from SystemTableRole stq where stq.systemMainTable=? and stq.role=? and stq.settingType=?";
			Query query = super.createQuery(hql, new Object[] {
					str.getSystemMainTable(), role, settingType });
			Long count = (Long) query.uniqueResult();
			if (count.intValue() == 1) {
				throw new ServiceException("每个主表对于一个角色只能有个系统角色可见字段模板");
			}
		}
		this.save(str);
		return str;
	}

	public void saveSystemTableRoleColumnSort(SystemTableRole str,
			Integer settingType, String targetOrderFlag,
			String[] sourceOrderFlags) {
		for (int i = 0; i < sourceOrderFlags.length; i++) {
			if (StringUtils.isNotBlank(sourceOrderFlags[i])) {
				// 源排序flag
				// Integer currentSourceOrderFlag =
				// Integer.valueOf(sourceOrderFlags[i]);
				Long currentSourceId = Long.valueOf(sourceOrderFlags[i]);
				// 源查询字段
				// SystemTableQueryColumn stqc = findSystemTableQueryColumn(stq,
				// currentSourceOrderFlag);
				SystemTableRoleColumn stqc = get(SystemTableRoleColumn.class,
						currentSourceId);
				Integer currentSourceOrderFlag = stqc.getOrder();
				// 目标flag
				Integer targetOrderFlagLong = Integer.valueOf(targetOrderFlag);
				// 目标查询字段
//				SystemTableRoleColumn stqcTarget = findSystemTableRoleColumn(
//						str, settingType, targetOrderFlagLong);

				if (targetOrderFlagLong > currentSourceOrderFlag) { // 目标排序在源排序的下面
					StringBuffer bf = new StringBuffer();
					bf
							.append(" update SystemTableRoleColumn stqc set stqc.order=stqc.order-1");
					bf.append(" where stqc.systemTableRole=? ");
					bf.append(" and stqc.settingType=? ");
					bf.append(" and stqc.order> ? and stqc.order<=? ");
					this.executeUpdate(bf.toString(), new Object[] { str,
							settingType, currentSourceOrderFlag,
							targetOrderFlagLong });

					// 目标查询字段
					// SystemTableQueryColumn stqcTarget =
					// findSystemTableQueryColumn(stq, targetOrderFlagLong);
					// 目标查询字段的order
//					Integer targetOrder = stqcTarget.getOrder();
					// 源查询字段排序标记修改
					stqc.setOrder(targetOrderFlagLong);
					this.save(stqc);
					this.evict(stqc);

				} else {
					StringBuffer bf = new StringBuffer();
					bf
							.append(" update SystemTableRoleColumn stqc set stqc.order=stqc.order+1");
					bf.append(" where stqc.systemTableRole=? ");
					bf.append(" and stqc.settingType=? ");
					bf.append(" and stqc.order>? and stqc.order<? ");

					this.executeUpdate(bf.toString(), new Object[] { str,
							settingType, targetOrderFlagLong,
							currentSourceOrderFlag });

					// 目标查询字段
					// SystemTableQueryColumn stqcTarget =
					// findSystemTableQueryColumn(stq, targetOrderFlagLong);
					// 目标查询字段的order
//					Integer targetOrder = stqcTarget.getOrder();
					// 源查询字段排序标记修改
					stqc.setOrder(targetOrderFlagLong + 1);
					this.save(stqc);
					this.evict(stqc);
				}

			}

		}

	}

	public List<SystemTableRoleColumn> findSystemTableRoleColumn(
			SystemTableRole str) {
		String hql = "from SystemTableRoleColumn stqc where stqc.systemTableRole=? order by stqc.systemMainTable asc, stqc.order ";
		Query query = super.createQuery(hql, new Object[] { str });
		List list = query.list();
		if (list.isEmpty()) {
			SystemMainTable mainTable = str.getSystemMainTable();
			Integer settingType = str.getSettingType();
			List<Column> columns = systemColumnService
					.findSystemTableColumns(mainTable);
			Iterator iter = columns.iterator();
			int orderFlag = 1;
			while (iter.hasNext()) {
				Column column = (Column) iter.next();
				SystemTableRoleColumn stqc = new SystemTableRoleColumn();
				stqc.setSettingType(settingType);
				stqc.setSystemTableRole(str);
				stqc.setSystemMainTable(mainTable);
				// if(column instanceof SystemMainTableColumn){
				SystemMainTableColumn mainTableColumn = (SystemMainTableColumn) column;
				stqc.setMainTableColumn(mainTableColumn);
				// }else{
				// SystemMainTableExtColumn extendTableColumn =
				// (SystemMainTableExtColumn) column;
				// stqc.setExtendTableColumn(extendTableColumn);
				// }
				stqc.setIsDisplay(Integer.valueOf(1));
				stqc.setOrder(Integer.valueOf(orderFlag++));
				super.save(stqc);
				super.evict(stqc);
				list.add(stqc);
			}

		}
		return list;
	}

	public void removeSystemTableRole(String[] ids) {
		if (ids == null || ids.length == 0) {
			throw new ServiceException("请选择角色模板");
		}
		for (int i = 0; i < ids.length; i++) {
			SystemTableRole stq = (SystemTableRole) super.get(
					SystemTableRole.class, Long.valueOf(ids[i]));
			this
					.executeUpdate(
							"delete from SystemTableRoleColumn stqc where stqc.systemTableRole=?",
							new Object[] { stq });
			this.remove(stq);
		}

	}

	public SystemTableQuery findSystemTableQuery(SystemMainTable smt) {
		String hql = "from SystemTableQuery stq where stq.systemMainTable=? and stq.queryType=? ";
		Query query = super.createQuery(hql, new Object[] { smt,
				SystemTableQuery.QUERY_TYPE_SINGLE });
		SystemTableQuery result = (SystemTableQuery) query.uniqueResult();
		return result;
	}

	@SuppressWarnings("unused")
	private StandardProperty getEntityProperty(String entityClass,
			String columnName) {
		StandardProperty sp = null;
		Map map = this.getHibernateTemplate().getSessionFactory()
				.getAllClassMetadata();
		Set keySet = map.keySet();
		Iterator iter = keySet.iterator();
		while (iter.hasNext()) {
			String classname = (String) iter.next();
			if (entityClass.equalsIgnoreCase(classname)) {
				ClassMetadata classMetadata = (ClassMetadata) map
						.get(classname);
				SingleTableEntityPersister stepersister = (SingleTableEntityPersister) classMetadata;
				stepersister.getIdentifierPropertyName();
				EntityMetamodel entityMetamode = stepersister
						.getEntityMetamodel();
				StandardProperty[] sps = entityMetamode.getProperties();
				for (int i = 0; i < sps.length; i++) {
					StandardProperty item = sps[i];
					String propertyName = item.getName();
//					String nodeName = item.getNode();
//					Type propertyType = item.getType();
//					String typeName = propertyType.getName();
					if (propertyName.equalsIgnoreCase(columnName)) {
						sp = item;
						break;
					}
				}
			}
		}
		return sp;
	}

	/**
	 * 通过系统表查询获取其所有查询字段，此处只是单表查询
	 */
	@SuppressWarnings("unchecked")
	public List<SystemTableQueryColumn> findSystemTableQueryColumn(
			SystemTableQuery utq) {
		String hql = "from SystemTableQueryColumn stqc where stqc.systemTableQuery=? order by stqc.systemMainTable asc, stqc.order ";
		Query query = super.createQuery(hql, new Object[] { utq });
		List list = query.list();
		if (list.isEmpty()) {
			SystemMainTable mainTable = utq.getSystemMainTable();
			List<SystemMainTableColumn> columns = systemMainAndExtColumnService
					.findAllColumnBySysMainTable(mainTable);
			// List<SystemMainTableExtColumn>
			// extColumns=systemExtColumnService.findSystemExtendColumns(mainTable);
			Iterator iter = columns.iterator();
			int orderFlag = 1;
			while (iter.hasNext()) {
				SystemMainTableColumn smtc = (SystemMainTableColumn) iter
						.next();// 系统主表
				if (smtc.getIsSearchItem().intValue() == 1) {
//					String properyName = smtc.getPropertyName();
					SystemTableQueryColumn stqc = new SystemTableQueryColumn();
					stqc.setSystemTableQuery(utq);
					stqc.setSystemMainTable(mainTable);
					stqc.setMainTableColumn(smtc);
					// stqc.setExtendTableColumn(null);
					stqc.setIsDisplay(Integer.valueOf(1));
					/*
					 * if(smtc.getIsOutputItem().intValue()==1){暂且注释
					 * stqc.setIsDisplay(Integer.valueOf(1)); }else{
					 * stqc.setIsDisplay(Integer.valueOf(0)); }
					 */
					stqc.setOrder(Integer.valueOf(orderFlag++));
					// =====
					// 页面传递过来的extColumn的SystemExtTable为null，手动设置一下
					SystemMainTableColumnType type = smtc
							.getSystemMainTableColumnType();
					if (type != null) {
						if (smtc.isTextColumnType()) { // 如果字段类型是文本类型，模糊搜索模式
							stqc
									.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
						} else if (smtc.isNumberColumnType()) { // 如果字段类型是下拉列表之类的存数字的类型
							stqc
									.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
						} else if (smtc.isCurrencyColumn()) { // 如果字段的验证类型是货币，搜索时应指定区间
							stqc
									.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
						} else if (smtc.isDateColumn()) { // 如果字段的验证类型是时间，搜索时应指定区间
							stqc
									.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
						} else { // 在if中后加入else，并新增else分支，未测试
							stqc
									.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
						}
					}
					// =====

					super.save(stqc);
					super.evict(stqc);
					list.add(stqc);
				}// end is output item
			}
			/*
			 * Iterator extiter = extColumns.iterator();
			 * while(extiter.hasNext()){ SystemMainTableExtColumn smtec =
			 * (SystemMainTableExtColumn) extiter.next();//系统主表
			 * if(smtec.getIsSearchItem().intValue()==1){ String properyName =
			 * smtec.getPropertyName(); SystemTableQueryColumn stqc = new
			 * SystemTableQueryColumn(); stqc.setSystemTableQuery(utq);
			 * stqc.setSystemMainTable(mainTable);
			 * stqc.setMainTableColumn(null); stqc.setExtendTableColumn(smtec);
			 * stqc.setIsDisplay(Integer.valueOf(1));
			 * stqc.setOrder(Integer.valueOf(orderFlag++)); //===== //
			 * 页面传递过来的extColumn的SystemExtTable为null，手动设置一下
			 * SystemMainTableColumnType type =
			 * smtec.getSystemMainTableColumnType(); if(type!=null){ //
			 * if(smtec.isTextColumnType()){ //如果字段类型是文本类型，模糊搜索模式 //
			 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE); //
			 * }else if(smtec.isNumberColumnType()){ //如果字段类型是下拉列表之类的存数字的类型 //
			 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT); //
			 * }else if(smtec.isCurrencyColumn()){ //如果字段的验证类型是货币，搜索时应指定区间
			 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
			 * }else if(smtec.isDateColumn()){ //如果字段的验证类型是时间，搜索时应指定区间
			 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
			 * }else{ //在if中后加入else，并新增else分支，未测试
			 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT); } }
			 * super.save(stqc); super.evict(stqc); list.add(stqc); }//end is
			 * output item }
			 */
		}
		return list;
	}

	public void removeSystemTableQuery(String[] ids) {
		if (ids == null || ids.length == 0) {
			throw new ServiceException("删除系统查询");
		}
		for (int i = 0; i < ids.length; i++) {
			SystemTableQuery stq = (SystemTableQuery) super.get(
					SystemTableQuery.class, Long.valueOf(ids[i]));
			this
					.executeUpdate(
							"delete from SystemTableQueryColumn stqc where stqc.systemTableQuery=?",
							new Object[] { stq });
			this.remove(stq);
		}

	}

	@SuppressWarnings("unused")
	private SystemTableQueryColumn findSystemTableQueryColumn(
			SystemTableQuery stq, Integer orderFlag) {
		Criteria c = this.getCriteria(SystemTableQueryColumn.class);
		c.add(Restrictions.eq("systemTableQuery", stq));
		c.add(Restrictions.eq("order", orderFlag));
		SystemTableQueryColumn stqc = (SystemTableQueryColumn) c.uniqueResult();
		return stqc;

	}

	public void saveSystemTableQueryColumnSort(SystemTableQuery stq,
			String targetOrderFlag, String[] sourceOrderFlags) {
		for (int i = 0; i < sourceOrderFlags.length; i++) {
			if (StringUtils.isNotBlank(sourceOrderFlags[i])) {
				// 源排序flag
				// Integer currentSourceOrderFlag =
				// Integer.valueOf(sourceOrderFlags[i]);
				Long currentSourceId = Long.valueOf(sourceOrderFlags[i]);
				// 源查询字段
				// SystemTableQueryColumn stqc = findSystemTableQueryColumn(stq,
				// currentSourceOrderFlag);
				SystemTableQueryColumn stqc = get(SystemTableQueryColumn.class,
						currentSourceId);
				Integer currentSourceOrderFlag = stqc.getOrder();
				// 目标flag
				Integer targetOrderFlagLong = Integer.valueOf(targetOrderFlag);
				// 目标查询字段
//				SystemTableQueryColumn stqcTarget = findSystemTableQueryColumn(
//						stq, targetOrderFlagLong);

				if (targetOrderFlagLong > currentSourceOrderFlag) { // 目标排序在源排序的下面
					StringBuffer bf = new StringBuffer();
					bf
							.append(" update SystemTableQueryColumn stqc set stqc.order=stqc.order-1");
					bf.append(" where stqc.systemTableQuery=? ");
					bf.append(" and stqc.order> ? and stqc.order<=? ");
					this.executeUpdate(bf.toString(), new Object[] { stq,
							currentSourceOrderFlag, targetOrderFlagLong });

					// 目标查询字段
					// SystemTableQueryColumn stqcTarget =
					// findSystemTableQueryColumn(stq, targetOrderFlagLong);
					// 目标查询字段的order
//					Integer targetOrder = stqcTarget.getOrder();
					// 源查询字段排序标记修改
					stqc.setOrder(targetOrderFlagLong);
					this.save(stqc);
					this.evict(stqc);

				} else {
					StringBuffer bf = new StringBuffer();
					bf
							.append(" update SystemTableQueryColumn stqc set stqc.order=stqc.order+1");
					bf.append(" where stqc.systemTableQuery=? ");
					bf.append(" and stqc.order>? and stqc.order<? ");

					this.executeUpdate(bf.toString(), new Object[] { stq,
							targetOrderFlagLong, currentSourceOrderFlag });

					// 目标查询字段
					// SystemTableQueryColumn stqcTarget =
					// findSystemTableQueryColumn(stq, targetOrderFlagLong);
					// 目标查询字段的order
//					Integer targetOrder = stqcTarget.getOrder();
					// 源查询字段排序标记修改
					stqc.setOrder(targetOrderFlagLong + 1);
					this.save(stqc);
					this.evict(stqc);
				}

			}

		}

		// for(long j=stqcId+1; j<=targetId; j++ ){
		//		
		// }
		// String hql = "from SystemTableQueryColumn stqc where
		// stqc.systemTableQuery=? order by stqc.systemMainTable asc, stqc.order
		// ";
		// Query query = super.createQuery(hql, new Object[]{stq});
		// List list = query.list();
		// Iterator iter = list.iterator();
		// while(iter.hasNext()){
		// SystemTableQueryColumn stqc = (SystemTableQueryColumn) iter.next();
		//			
		// }

	}

	@SuppressWarnings("unused")
	private SystemTableSetting findSystemTableSettingColumn(
			SystemMainTable smt, Integer settingType, Integer orderFlag) {
		Criteria c = this.getCriteria(SystemTableSetting.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("settingType", settingType));
		c.add(Restrictions.eq("order", orderFlag));
		SystemTableSetting stqc = (SystemTableSetting) c.uniqueResult();
		return stqc;

	}

	// 此出有疑问
	@SuppressWarnings("unused")
	private UserTableSetting findUserTableSettingColumn(SystemMainTable smt,
			Integer settingType, Integer orderFlag) {
		Criteria c = this.getCriteria(UserTableSetting.class);
		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("settingType", settingType));
		c.add(Restrictions.eq("order", orderFlag));
		UserTableSetting stqc = (UserTableSetting) c.uniqueResult();
		return stqc;

	}

	@SuppressWarnings({ "unused" })
	private SystemTableRoleColumn findSystemTableRoleColumn(
			SystemTableRole smt, Integer settingType, Integer orderFlag) {
		Criteria c = this.getCriteria(SystemTableRoleColumn.class);
		c.add(Restrictions.eq("systemTableRole", smt));
		c.add(Restrictions.eq("settingType", settingType));
		c.add(Restrictions.eq("order", orderFlag));
		SystemTableRoleColumn stqc = (SystemTableRoleColumn) c.uniqueResult();
		return stqc;

	}

	public void saveAllSystemTableSettingColumn(SystemMainTable smt,
			Integer settingType, Map<String, String> params) {
		try {
			// begin
			SystemTableSettingVersion stsv = null;
			Criteria c = super.getCriteria(SystemTableSettingVersion.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.eq("settingType", settingType));
			c.setProjection(Projections.rowCount());
			Integer stsvCount = (Integer) c.uniqueResult();
			if (stsvCount == null || stsvCount.intValue() == 0) { // 如果stsv没有记录要新建系统可见字段版本
				stsv = new SystemTableSettingVersion();
				stsv.setVersion(Integer.valueOf(1));
			} else {
				c.setProjection(Projections.max("version"));
				Integer maxVersion = (Integer) c.uniqueResult();
				c.setProjection(null);
				c.add(Restrictions.eq("version", maxVersion));
				stsv = (SystemTableSettingVersion) c.uniqueResult();
				stsv.setVersion(maxVersion + 1);
			}
			stsv.setSettingType(settingType);
			stsv.setSystemMainTable(smt);
			super.save(stsv);
			// end
		} catch (Exception e) {
			// TODO: handle exception
		}
		List userTableColumnSettings = this.findSystemColumns(smt, settingType);
		for (int i = 0; i < userTableColumnSettings.size(); i++) {
			SystemTableSetting sts = (SystemTableSetting) userTableColumnSettings
					.get(i);
			Long utsId = sts.getId();
			String isDisplayPara = params.get("isDisplay" + utsId);
			String lengthForPage = params.get("lengthForPage" + utsId);
			String isMustInput = params.get("isMustInput" + utsId);
			String hiddenValue = params.get("hiddenValue" + utsId);
			StringTokenizer token = new StringTokenizer(isDisplayPara, "|");
//			String itemId = token.nextToken();
			String trueOrFalse = token.nextToken();
			sts.setIsDisplay(Integer.valueOf(trueOrFalse));
			sts.setLengthForPage(lengthForPage);
			if (StringUtils.isNotBlank(isMustInput)) {
				sts.setIsMustInput(Integer.valueOf(isMustInput));
			}
			if (StringUtils.isNotBlank(hiddenValue)) {
				sts.setHiddenValue(hiddenValue);
			}
			// --------------------------
			String orderPara = params.get("order" + utsId);
			sts.setOrder(Integer.valueOf(orderPara));
			super.save(sts);

		}

	}

	public void saveSystemTableSettingColumnSort(SystemMainTable smt,
			Integer settingType, String targetOrderFlag,
			String[] sourceOrderFlags) {
		for (int i = 0; i < sourceOrderFlags.length; i++) {
			if (StringUtils.isNotBlank(sourceOrderFlags[i])) {
				// 源排序flag
				// Integer currentSourceOrderFlag =
				// Integer.valueOf(sourceOrderFlags[i]);
				Long currentSourceId = Long.valueOf(sourceOrderFlags[i]);
				// 源查询字段
				// SystemTableQueryColumn stqc = findSystemTableQueryColumn(stq,
				// currentSourceOrderFlag);
				SystemTableSetting stsSource = get(SystemTableSetting.class,
						currentSourceId);
				Integer currentSourceOrderFlag = stsSource.getOrder();
				// 目标flag
				Integer targetOrderFlagLong = Integer.valueOf(targetOrderFlag);
				// 目标查询字段
//				SystemTableSetting stqcTarget = findSystemTableSettingColumn(
//						smt, settingType, targetOrderFlagLong);

				if (targetOrderFlagLong > currentSourceOrderFlag) { // 目标排序在源排序的下面
					StringBuffer bf = new StringBuffer();
					bf
							.append(" update SystemTableSetting stqc set stqc.order=stqc.order-1");
					bf.append(" where stqc.systemMainTable=? ");
					bf.append(" and stqc.settingType=? ");
					bf.append(" and stqc.order> ? and stqc.order<=? ");
					this.executeUpdate(bf.toString(), new Object[] { smt,
							settingType, currentSourceOrderFlag,
							targetOrderFlagLong });

					// 目标查询字段
					// SystemTableQueryColumn stqcTarget =
					// findSystemTableQueryColumn(stq, targetOrderFlagLong);
					// 目标查询字段的order
//					Integer targetOrder = stqcTarget.getOrder();
					// 源查询字段排序标记修改
					stsSource.setOrder(targetOrderFlagLong);
					this.save(stsSource);
					this.evict(stsSource);

				} else {
					StringBuffer bf = new StringBuffer();
					bf
							.append(" update SystemTableSetting stqc set stqc.order=stqc.order+1");
					bf.append(" where stqc.systemMainTable=? ");
					bf.append(" and stqc.settingType=? ");
					bf.append(" and stqc.order>? and stqc.order<? ");

					this.executeUpdate(bf.toString(), new Object[] { smt,
							settingType, targetOrderFlagLong,
							currentSourceOrderFlag });

					// 目标查询字段
					// SystemTableQueryColumn stqcTarget =
					// findSystemTableQueryColumn(stq, targetOrderFlagLong);
					// 目标查询字段的order
//					Integer targetOrder = stqcTarget.getOrder();
					// 源查询字段排序标记修改
					stsSource.setOrder(targetOrderFlagLong + 1);
					this.save(stsSource);
					this.evict(stsSource);
				}

			}

		}

	}

	public void saveUserTableSettingColumnSort(SystemMainTable smt,
			Integer settingType, String targetOrderFlag,
			String[] sourceOrderFlags) {
		UserInfo userInfo = UserContext.getUserInfo();
		if (userInfo == null) {
			throw new ServiceException("请先登录系统才可以查看您的可见字段");
		}
		for (int i = 0; i < sourceOrderFlags.length; i++) {
			if (StringUtils.isNotBlank(sourceOrderFlags[i])) {
				// 源排序flag
				// Integer currentSourceOrderFlag =
				// Integer.valueOf(sourceOrderFlags[i]);
				Long currentSourceId = Long.valueOf(sourceOrderFlags[i]);
				// 源查询字段
				// SystemTableQueryColumn stqc = findSystemTableQueryColumn(stq,
				// currentSourceOrderFlag);
				UserTableSetting stsSource = get(UserTableSetting.class,
						currentSourceId);
				Integer currentSourceOrderFlag = stsSource.getOrder();
				// 目标flag
				Integer targetOrderFlagLong = Integer.valueOf(targetOrderFlag);
				// 目标查询字段
//				UserTableSetting stqcTarget = findUserTableSettingColumn(smt,
//						settingType, targetOrderFlagLong);

				if (targetOrderFlagLong > currentSourceOrderFlag) { // 目标排序在源排序的下面
					StringBuffer bf = new StringBuffer();
					bf
							.append(" update UserTableSetting stqc set stqc.order=stqc.order-1");
					bf.append(" where stqc.systemMainTable=? ");
					bf.append(" and stqc.settingType=? ");
					bf.append(" and stqc.userInfo=? ");
					bf.append(" and stqc.order> ? and stqc.order<=? ");
					this.executeUpdate(bf.toString(), new Object[] { smt,
							settingType, userInfo, currentSourceOrderFlag,
							targetOrderFlagLong });

					// 目标查询字段
					// SystemTableQueryColumn stqcTarget =
					// findSystemTableQueryColumn(stq, targetOrderFlagLong);
					// 目标查询字段的order
//					Integer targetOrder = stqcTarget.getOrder();
					// 源查询字段排序标记修改
					stsSource.setOrder(targetOrderFlagLong);
					this.save(stsSource);
					this.evict(stsSource);

				} else {
					StringBuffer bf = new StringBuffer();
					bf
							.append(" update UserTableSetting stqc set stqc.order=stqc.order+1");
					bf.append(" where stqc.systemMainTable=? ");
					bf.append(" and stqc.settingType=? ");
					bf.append(" and stqc.userInfo=? ");
					bf.append(" and stqc.order>? and stqc.order<? ");

					this.executeUpdate(bf.toString(), new Object[] { smt,
							settingType, userInfo, targetOrderFlagLong,
							currentSourceOrderFlag });

					// 目标查询字段
					// SystemTableQueryColumn stqcTarget =
					// findSystemTableQueryColumn(stq, targetOrderFlagLong);
					// 目标查询字段的order
//					Integer targetOrder = stqcTarget.getOrder();
					// 源查询字段排序标记修改
					stsSource.setOrder(targetOrderFlagLong + 1);
					this.save(stsSource);
					this.evict(stsSource);
				}

			}

		}

	}

	public void saveSystemTableSettingColumnToUser(SystemMainTable smt,
			Role role, Integer settingType) {
		String hsql = "select ur.userInfo from UserRole ur where ur.role=?";
		List userInfos = super.find(hsql, new Object[] { role });
		for (int i = 0; i < userInfos.size(); i++) {
			UserInfo user = (UserInfo) userInfos.get(i);
			this.saveSystemTableSettingColumnToUser(smt, user, settingType);
		}
	}

	public void saveSystemTableSettingColumnToUser(SystemMainTable smt,
			UserInfo user, Integer settingType) {
		Criteria csts = createCriteria(SystemTableSetting.class);
		csts.add(Restrictions.eq("systemMainTable", smt));
		csts.add(Restrictions.eq("settingType", settingType));
		List list = csts.list();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			SystemTableSetting sts = (SystemTableSetting) iter.next();
			Column column = sts.getColumn();

			Criteria c = createCriteria(UserTableSetting.class);
			c.add(Restrictions.eq("userInfo", user));
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.eq("settingType", settingType));
			c.addOrder(Order.asc("order"));
			// if(column instanceof SystemMainTableColumn){
			c.add(Restrictions.eq("mainTableColumn",
					(SystemMainTableColumn) column));
			// }else if(column instanceof SystemMainTableExtColumn){
			// c.add(Restrictions.eq("extendTableColumn",
			// (SystemMainTableExtColumn)column));
			// }
			Object object = c.uniqueResult();
			if (object != null) {
				UserTableSetting uts = (UserTableSetting) object;
				uts.setOrder(sts.getOrder());
				uts.setIsDisplay(sts.getIsDisplay());
				uts.setLengthForPage(sts.getLengthForPage());
				uts.setHiddenValue(sts.getHiddenValue());
				uts.setIsMustInput(sts.getIsMustInput());
				this.save(uts);// update
			} else {
				UserTableSetting uts = new UserTableSetting();
				uts.setUserInfo(user);
				uts.setSystemMainTable(smt);
				// if(column instanceof SystemMainTableColumn){
				uts.setMainTableColumn((SystemMainTableColumn) column);
				// }else if(column instanceof SystemMainTableExtColumn){
				// uts.setExtendTableColumn((SystemMainTableExtColumn)column);
				// }
				uts.setSettingType(settingType);
				uts.setIsDisplay(sts.getIsDisplay());
				uts.setOrder(sts.getOrder());
				uts.setLengthForPage(sts.getLengthForPage());
				uts.setHiddenValue(sts.getHiddenValue());
				uts.setIsMustInput(sts.getIsMustInput());
				this.save(uts); // insert

			}

		}

	}

	public void saveSystemTableSettingColumnToUser(SystemMainTable smt,
			Integer settingType) {

		String uhsql = "select usr.id from UserInfo usr where enabled=?";
		List users = this.find(uhsql, Integer.valueOf(1));

		Criteria csts = createCriteria(SystemTableSetting.class);
		csts.add(Restrictions.eq("systemMainTable", smt));
		csts.add(Restrictions.eq("settingType", settingType));
		List list = csts.list();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			SystemTableSetting sts = (SystemTableSetting) iter.next();
			Column column = sts.getColumn();

			for (int i = 0; i < users.size(); i++) {
				Long id = (Long) users.get(i);
				UserInfo user = new UserInfo(id);
				Criteria c = createCriteria(UserTableSetting.class);
				c.add(Restrictions.eq("userInfo", user));
				c.add(Restrictions.eq("systemMainTable", smt));
				c.add(Restrictions.eq("settingType", settingType));
				c.addOrder(Order.asc("order"));
				// if(column instanceof SystemMainTableColumn){
				c.add(Restrictions.eq("mainTableColumn",
						(SystemMainTableColumn) column));
				// }else if(column instanceof SystemMainTableExtColumn){
				// c.add(Restrictions.eq("extendTableColumn",
				// (SystemMainTableExtColumn)column));
				// }
				Object object = c.uniqueResult();
				if (object != null) {
					UserTableSetting uts = (UserTableSetting) object;
					uts.setOrder(sts.getOrder());
					uts.setIsDisplay(sts.getIsDisplay());
					uts.setLengthForPage(sts.getLengthForPage());
					uts.setHiddenValue(sts.getHiddenValue());
					uts.setIsMustInput(sts.getIsMustInput());
					this.save(uts);// update
				} else {
					UserTableSetting uts = new UserTableSetting();
					uts.setUserInfo(user);
					uts.setSystemMainTable(smt);
					// if(column instanceof SystemMainTableColumn){
					uts.setMainTableColumn((SystemMainTableColumn) column);
					// }else if(column instanceof SystemMainTableExtColumn){
					// uts.setExtendTableColumn((SystemMainTableExtColumn)column);
					// }
					uts.setSettingType(settingType);
					uts.setIsDisplay(sts.getIsDisplay());
					uts.setOrder(sts.getOrder());
					uts.setLengthForPage(sts.getLengthForPage());
					uts.setHiddenValue(sts.getHiddenValue());
					uts.setIsMustInput(sts.getIsMustInput());
					this.save(uts); // insert

				}
			}
		}

	}

	public void saveSystemTableQueryColumnToUser(SystemTableQuery stq) {
		Criteria csts = createCriteria(SystemTableQueryColumn.class);
		csts.add(Restrictions.eq("systemTableQuery", stq));
		List list = csts.list();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			SystemTableQueryColumn stqc = (SystemTableQueryColumn) iter.next();
			// 判断是否是所有用户
			SystemMainTable systemMainTable = stqc.getSystemMainTable();
			String uhsql = "select usr.id from UserInfo usr where enabled=?";
			List users = this.find(uhsql, Integer.valueOf(1));
			for (int i = 0; i < users.size(); i++) {
				Long userId = (Long) users.get(i);
				UserInfo user = new UserInfo(userId);
				Criteria c = createCriteria(UserTableQueryColumn.class);
				c.add(Restrictions.eq("userInfo", user));
				c.add(Restrictions.eq("systemTableQuery", stq));
				c.add(Restrictions.eq("systemTableQueryColumn", stqc));
				c.uniqueResult();
				// c.addOrder(Order.asc("order"));
				c.setProjection(Projections.rowCount());
				Integer rowCount = (Integer) c.uniqueResult();
				if (rowCount.intValue() == 0) {
					Column stqCc = stqc.getColumn();
					UserTableQueryColumn newObj = new UserTableQueryColumn();
					// if(stqCc instanceof SystemMainTableColumn){
					newObj.setMainTableColumn((SystemMainTableColumn) stqCc);
					// }else if(stqCc instanceof SystemMainTableExtColumn ){
					// newObj.setExtendTableColumn((SystemMainTableExtColumn)stqCc);
					// }
					newObj.setSystemMainTable(systemMainTable);
					newObj.setSystemTableQuery(stq);
					newObj.setSystemTableQueryColumn(stqc);
					newObj.setUserInfo(user);
					newObj.setIsDisplay(stqc.getIsDisplay());
					newObj.setOrder(stqc.getOrder());
					newObj.setMatchMode(stqc.getMatchMode());
					newObj.setIsMustInput(stqc.getIsMustInput());
					newObj.setLengthForPage(stqc.getLengthForPage());
					newObj.setHiddenValue(stqc.getHiddenValue());
					newObj.setIsHiddenItem(stqc.getIsHiddenItem());
					save(newObj);
				} else {// 已经有记录
					Criteria c2 = createCriteria(UserTableQueryColumn.class);
					c2.add(Restrictions.eq("userInfo", user));
					c2.add(Restrictions.eq("systemTableQuery", stq));
					c2.add(Restrictions.eq("systemTableQueryColumn", stqc));
					// c2.addOrder(Order.asc("order"));
					// c.setProjection(null);
					Column stqCc = stqc.getColumn();
					UserTableQueryColumn oldUtqc = (UserTableQueryColumn) c2
							.uniqueResult();
					// if(stqCc instanceof SystemMainTableColumn){
					oldUtqc.setMainTableColumn((SystemMainTableColumn) stqCc);
					// }else if(stqCc instanceof SystemMainTableExtColumn ){
					// oldUtqc.setExtendTableColumn((SystemMainTableExtColumn)stqCc);
					// }
					oldUtqc.setIsDisplay(stqc.getIsDisplay());
					oldUtqc.setSystemMainTable(systemMainTable);
					oldUtqc.setOrder(stqc.getOrder());
					oldUtqc.setMatchMode(stqc.getMatchMode());
					oldUtqc.setIsMustInput(stqc.getIsMustInput());
					oldUtqc.setLengthForPage(stqc.getLengthForPage());
					oldUtqc.setHiddenValue(stqc.getHiddenValue());
					oldUtqc.setIsHiddenItem(stqc.getIsHiddenItem());
					this.save(oldUtqc);
				}
			}

		}

	}

	// 保存系统角色可见字段到指定角色的所有用户
	public void saveSystemTableRoleColumnToUser(SystemTableRole str) {
		SystemMainTable smt = str.getSystemMainTable();
		Role role = str.getRole();
		Integer settingType = str.getSettingType();

		String uhsql = "select distinct ur.userInfo from UserRole ur where ur.role=?";
		List users = this.find(uhsql, role);

		Criteria csts = createCriteria(SystemTableRoleColumn.class);
		csts.add(Restrictions.eq("systemTableRole", str));
		List list = csts.list();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			SystemTableRoleColumn stqc = (SystemTableRoleColumn) iter.next();
			Column column = stqc.getColumn();
			// 判断是否是所有用户

			for (int i = 0; i < users.size(); i++) {
				// Long id = (Long) users.get(i);
				UserInfo user = (UserInfo) users.get(i);
				Criteria c = createCriteria(UserTableSetting.class);
				c.add(Restrictions.eq("userInfo", user));
				c.add(Restrictions.eq("systemMainTable", smt));
				c.add(Restrictions.eq("settingType", settingType));
				c.addOrder(Order.asc("order"));
				// if(column instanceof SystemMainTableColumn){
				c.add(Restrictions.eq("mainTableColumn",
						(SystemMainTableColumn) column));
				// }else if(column instanceof SystemMainTableExtColumn){
				// c.add(Restrictions.eq("extendTableColumn",
				// (SystemMainTableExtColumn)column));
				// }
				Object object = c.uniqueResult();
				if (object != null) {
					UserTableSetting uts = (UserTableSetting) object;
					uts.setOrder(stqc.getOrder());
					uts.setIsDisplay(stqc.getIsDisplay());
					uts.setLengthForPage(stqc.getLengthForPage());
					uts.setHiddenValue(stqc.getHiddenValue());
					uts.setIsMustInput(stqc.getIsMustInput());
					this.save(uts);// update
					this.evict(uts);
				} else {
					UserTableSetting uts = new UserTableSetting();
					uts.setUserInfo(user);
					uts.setSystemMainTable(smt);
					// if(column instanceof SystemMainTableColumn){
					uts.setMainTableColumn((SystemMainTableColumn) column);
					// }else if(column instanceof SystemMainTableExtColumn){
					// uts.setExtendTableColumn((SystemMainTableExtColumn)column);
					// }
					uts.setSettingType(settingType);
					uts.setIsDisplay(stqc.getIsDisplay());
					uts.setOrder(stqc.getOrder());
					uts.setLengthForPage(stqc.getLengthForPage());
					uts.setHiddenValue(stqc.getHiddenValue());
					uts.setIsMustInput(stqc.getIsMustInput());
					this.save(uts); // insert
					this.evict(uts);

				}
				super.evict(user);
			}

		}

	}

	public void saveSystemTableQueryColumnToUser(SystemTableQuery stq,
			UserInfo user) {
		Criteria csts = createCriteria(SystemTableQueryColumn.class);
		csts.add(Restrictions.eq("systemTableQuery", stq));
		List list = csts.list();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			SystemTableQueryColumn stqc = (SystemTableQueryColumn) iter.next();
			SystemMainTable systemMainTable = stqc.getSystemMainTable();
			Criteria c = createCriteria(UserTableQueryColumn.class);
			c.add(Restrictions.eq("userInfo", user));
			c.add(Restrictions.eq("systemTableQuery", stq));
			c.add(Restrictions.eq("systemTableQueryColumn", stqc));
			// c.addOrder(Order.asc("order"));
			c.setProjection(Projections.rowCount());
			Integer rowCount = (Integer) c.uniqueResult();
			if (rowCount.intValue() == 0) {
				Column stqCc = stqc.getColumn();
				UserTableQueryColumn newObj = new UserTableQueryColumn();
				// if(stqCc instanceof SystemMainTableColumn){
				newObj.setMainTableColumn((SystemMainTableColumn) stqCc);
				// }else if(stqCc instanceof SystemMainTableExtColumn ){
				// newObj.setExtendTableColumn((SystemMainTableExtColumn)stqCc);
				// }
				newObj.setSystemMainTable(systemMainTable);
				newObj.setSystemTableQuery(stq);
				newObj.setSystemTableQueryColumn(stqc);
				newObj.setUserInfo(user);
				newObj.setIsDisplay(stqc.getIsDisplay());
				newObj.setOrder(stqc.getOrder());
				newObj.setIsHiddenItem(stqc.getIsHiddenItem());
				save(newObj);
			} else {// 已经有记录
				Criteria c2 = createCriteria(UserTableQueryColumn.class);
				c2.add(Restrictions.eq("userInfo", user));
				c2.add(Restrictions.eq("systemTableQuery", stq));
				c2.add(Restrictions.eq("systemTableQueryColumn", stqc));
				// c.setProjection(null);
				Column stqCc = stqc.getColumn();
				UserTableQueryColumn oldUtqc = (UserTableQueryColumn) c2
						.uniqueResult();
				// if(stqCc instanceof SystemMainTableColumn){
				oldUtqc.setMainTableColumn((SystemMainTableColumn) stqCc);
				// }else if(stqCc instanceof SystemMainTableExtColumn ){
				// oldUtqc.setExtendTableColumn((SystemMainTableExtColumn)stqCc);
				// }
				oldUtqc.setSystemMainTable(systemMainTable);
				oldUtqc.setIsDisplay(stqc.getIsDisplay());
				oldUtqc.setOrder(stqc.getOrder());
				oldUtqc.setIsHiddenItem(stqc.getIsHiddenItem());
				this.save(oldUtqc);
			}

		}

	}

	public List<SystemTableQueryColumn> findSystemTableQueryColumn(
			SystemMainTable smt) {
		// 删除此主表的所有系统查询字段？？？？？？？？？？？
		// super.executeUpdate("delete from SystemTableQueryColumn stqc where
		// stqc.systemMainTable=?", smt);
//		List<SystemMainTableColumn> mainColumns = systemMainColumnService
//				.findSystemMainTableColumns(smt);
		/*
		 * int i=1; for(SystemMainTableColumn fmtc: mainColumns){
		 * if(fmtc.getIsSearchItem().intValue()==1){ //字段是查询项才初始化到系统查询字段
		 * SystemTableQueryColumn stqc = new SystemTableQueryColumn();
		 * stqc.setSystemMainTable(fmtc.getSystemMainTable());
		 * stqc.setIsDisplay(1); stqc.setOrder(Integer.valueOf(i++));
		 * stqc.setMainTableColumn(fmtc); stqc.setExtendTableColumn(null);
		 * //考虑将代码移到一处 if(fmtc.getPropertyType()==null){
		 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE); }else
		 * if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("String")){
		 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE); }else
		 * if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("Integer")){
		 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT); }else
		 * if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("Long")){
		 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT); }else
		 * if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("BaseObject")){
		 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT); }else
		 * if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("Double")){
		 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT); }else
		 * if(fmtc.getPropertyType().getPropertyTypeName().equalsIgnoreCase("Date")){
		 * stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT); }
		 * super.save(stqc); } }
		 */
		return null;
	}

	/**
	 * 获取用户可见字段
	 */
	@SuppressWarnings("unchecked")
	public List<UserTableSetting> findUserColumns(SystemMainTable smt,
			Integer settingType) {

		Criteria cUts = createCriteria(UserTableSetting.class);
		cUts.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		cUts.add(Restrictions.eq("systemMainTable", smt));
		cUts.add(Restrictions.eq("settingType", settingType));
		cUts.setFetchMode("mainTableColumn", FetchMode.JOIN);
		cUts.setFetchMode("systemMainTable", FetchMode.JOIN);
		cUts.addOrder(Order.asc("order"));
		// if(settingType==UserTableSetting.LIST){
		cUts.add(Restrictions.eq("isDisplay", new Integer(1)));
		// }
		List listUts = cUts.list();

		if (UserContext.getUserInfo() != null && listUts.isEmpty()) {// 如因问题未初始化用户字段，这里进行初始化
			Criteria cSys = super.getCriteria(SystemTableSetting.class);
			cSys.add(Restrictions.eq("systemMainTable", smt));
			cSys.add(Restrictions.eq("settingType", settingType));
			cSys.setFetchMode("mainTableColumn", FetchMode.JOIN);
			cSys.add(Restrictions.eq("isDisplay", new Integer(1)));// 此处好像有问题，那不显示的字段怎么办呢
			cSys.addOrder(Order.asc("order"));
			List<SystemTableSetting> listSys = cSys.list();
			for (SystemTableSetting sts : listSys) {
				UserTableSetting ax = new UserTableSetting();
				ax.setSystemMainTable(smt);
				ax.setSettingType(sts.getSettingType());
				ax.setDescn(sts.getDescn());
				Column column = sts.getColumn();
				// if(column instanceof SystemMainTableColumn){
				ax.setMainTableColumn((SystemMainTableColumn) column);
				// }else{
				// ax.setExtendTableColumn((SystemMainTableExtColumn) column);
				// }
				ax.setOrder(sts.getOrder());
				ax.setIsDisplay(sts.getIsDisplay());
				ax.setIsMustInput(sts.getIsMustInput());
				ax.setUserInfo(UserContext.getUserInfo());
				ax.setLengthForPage(sts.getLengthForPage());
				super.save(ax);
				listUts.add(ax);
			}
		} else {

			// begin
			// 获取系统可见字段最大版本
			Criteria c = super.getCriteria(SystemTableSettingVersion.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.eq("settingType", settingType));
			c.setProjection(Projections.max("version"));
			Integer maxStsVersion = (Integer) c.uniqueResult();

			Criteria cUtsv = super.getCriteria(UserTableSettingVersion.class);
			cUtsv.add(Restrictions.eq("systemMainTable", smt));
			cUtsv.add(Restrictions.eq("settingType", settingType));
			cUtsv.setProjection(Projections.max("version"));
			Integer maxUtsVersion = (Integer) cUtsv.uniqueResult();

			if (maxStsVersion != null && maxUtsVersion != null) {
				if (maxUtsVersion.intValue() < maxStsVersion.intValue()) {// 用户可见字段最大版本小于系统的

					List<Column> columns = systemColumnService
							.findSystemTableColumns(smt);
					for (Column column : columns) {
						// 判断当前字段在系统可见字段里是否已经存在
						Criteria cSysc = super
								.getCriteria(SystemTableSetting.class);
						cSysc.add(Restrictions.eq("systemMainTable", smt));
						cSysc.add(Restrictions.eq("settingType", settingType));
						// if(column instanceof SystemMainTableColumn){
						SystemMainTableColumn smtc = ((SystemMainTableColumn) column);
						cSysc.add(Restrictions.eq("mainTableColumn", smtc));
						// }
						// else if(column instanceof SystemMainTableExtColumn) {
						// SystemMainTableExtColumn smtec =
						// ((SystemMainTableExtColumn) column);
						// cSysc.add(Restrictions.eq("extendTableColumn",
						// smtec));
						// }
						cSysc.setProjection(null);
						cSysc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						List<SystemTableSetting> stsList = cSysc.list();
						SystemTableSetting stsResult = null;
						if (!stsList.isEmpty()) {
							stsResult = stsList.iterator().next();

							Criteria cUtsMaxOrder = super
									.getCriteria(UserTableSetting.class);
							cUtsMaxOrder.add(Restrictions.eq("systemMainTable",
									smt));
							cUtsMaxOrder.add(Restrictions.eq("settingType",
									settingType));
							// if(column instanceof SystemMainTableColumn){
							// SystemMainTableColumn smtc1 =
							// ((SystemMainTableColumn) column);
							cUtsMaxOrder.add(Restrictions.eq("mainTableColumn",
									smtc));
							// }
							// else if(column instanceof
							// SystemMainTableExtColumn) {
							// SystemMainTableExtColumn smtec =
							// ((SystemMainTableExtColumn) column);
							// cUtsMaxOrder.add(Restrictions.eq("extendTableColumn",
							// smtec));
							// }
							UserTableSetting utsResult = null;
							List<UserTableSetting> utsList = cUtsMaxOrder
									.list();
							if (!utsList.isEmpty()) {
								utsResult = utsList.iterator().next();
							}
							utsResult.setOrder(stsResult.getOrder());

						} else {
							// begin
							Criteria cUtsMaxOrder = super
									.getCriteria(UserTableSetting.class);
							cUtsMaxOrder.add(Restrictions.eq("systemMainTable",
									smt));
							cUtsMaxOrder.add(Restrictions.eq("settingType",
									settingType));
							// if(column instanceof SystemMainTableColumn){
							// SystemMainTableColumn smtc =
							// ((SystemMainTableColumn) column);
							cUtsMaxOrder.add(Restrictions.eq("mainTableColumn",
									smtc));
							// }else if(column instanceof
							// SystemMainTableExtColumn) {
							// SystemMainTableExtColumn smtec =
							// ((SystemMainTableExtColumn) column);
							// cUtsMaxOrder.add(Restrictions.eq("extendTableColumn",
							// smtec));
							// }
							cUtsMaxOrder
									.setProjection(Projections.max("order"));
							Integer maxUtsOrder = (Integer) cUtsMaxOrder
									.uniqueResult();

							UserTableSetting stsInput = new UserTableSetting();
							stsInput.setSettingType(settingType);
							stsInput.setSystemMainTable(smt);
							// if(column instanceof SystemMainTableColumn){
							stsInput
									.setMainTableColumn((SystemMainTableColumn) column);
							// }else if(column instanceof
							// SystemMainTableExtColumn) {
							// stsInput.setExtendTableColumn((SystemMainTableExtColumn)
							// column);
							// }
							stsInput.setIsDisplay(new Integer(1));
							stsInput.setOrder(maxUtsOrder);
							this.save(stsInput);
							this.evict(stsInput);
							// end
						}

						// }
					}
					// ***************
				}
			}

			// end
		}
		return listUts;
	}

	/**
	 * 获取用户可见字段，显示所有的
	 */
	@SuppressWarnings("unchecked")
	public List<UserTableSetting> findUserColumnsAll(SystemMainTable sysmt,
			Integer settingType) {

		Criteria c = createCriteria(UserTableSetting.class);
		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("systemMainTable", sysmt));
		c.add(Restrictions.eq("settingType", settingType));
		c.addOrder(Order.asc("order"));
		c.createAlias("this.mainTableColumn", "mc").setFetchMode("mc",
				FetchMode.JOIN);
		c.add(Restrictions.ne("mc.systemMainTableColumnType",
				new SystemMainTableColumnType(8L)));
		// c.add(Restrictions.ne("mc.propertyName", "id"));
		// if(settingType==UserTableSetting.LIST){
		// c.add(Restrictions.eq("isDisplay", new Integer(1)));
		// }
		List list = c.list();
		if (list.isEmpty() && UserContext.getUserInfo() != null) {
			Criteria c2 = super.getCriteria(SystemTableSetting.class);
			c2.add(Restrictions.eq("systemMainTable", sysmt));
			c2.add(Restrictions.eq("settingType", settingType));
			c2.addOrder(Order.asc("order"));
			c2.setFetchMode("mainTableColumn", FetchMode.JOIN);

			c2.createAlias("this.mainTableColumn", "mc").setFetchMode("mc",
					FetchMode.JOIN);
			c2.add(Restrictions.ne("mc.systemMainTableColumnType",
					new SystemMainTableColumnType(8L)));
			// c2.add(Restrictions.ne("mc.propertyName", "id"));

			List<SystemTableSetting> list2 = c2.list();
			for (SystemTableSetting sts : list2) {
				UserTableSetting ax = new UserTableSetting();
				ax.setSystemMainTable(sysmt);
				ax.setSettingType(sts.getSettingType());
				ax.setDescn(sts.getDescn());
				Column column = sts.getColumn();
				// if(column instanceof SystemMainTableColumn){
				ax.setMainTableColumn((SystemMainTableColumn) column);
				// }else{
				// ax.setExtendTableColumn((SystemMainTableExtColumn) column);
				// }
				ax.setOrder(sts.getOrder());
				ax.setIsDisplay(sts.getIsDisplay());
				ax.setIsMustInput(sts.getIsMustInput());
				ax.setUserInfo(UserContext.getUserInfo());
				ax.setLengthForPage(sts.getLengthForPage());
				super.save(ax);
				list.add(ax);

			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<UserTableQueryColumn> findUserQueryColumn(SystemTableQuery stq) {
		Criteria c = createCriteria(UserTableQueryColumn.class);
		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("systemTableQuery", stq));
		c.setFetchMode("mainTableColumn", FetchMode.JOIN);
		c.setFetchMode("systemTableQuery", FetchMode.JOIN);
		Disjunction disj = Restrictions.disjunction();
		disj.add(Restrictions.eq("isDisplay", Integer.valueOf(1)));
		disj.add(Restrictions.eq("isHiddenItem", Integer.valueOf(1)));
		c.add(disj);
		c.addOrder(Order.asc("order"));
		List list = c.list();
		if (list.isEmpty() && UserContext.getUserInfo() != null) {// 延迟初始化用户查询字段
			Criteria critSys = createCriteria(SystemTableQueryColumn.class);
			critSys.add(Restrictions.eq("systemTableQuery", stq));
			critSys.setFetchMode("systemTableQuery", FetchMode.JOIN);
			critSys.setFetchMode("mainTableColumn", FetchMode.JOIN);
			Disjunction disjSys = Restrictions.disjunction();
			disjSys.add(Restrictions.eq("isDisplay", Integer.valueOf(1)));
			disjSys.add(Restrictions.eq("isHiddenItem", Integer.valueOf(1)));
			critSys.add(disjSys);
			critSys.addOrder(Order.asc("order"));
			List<SystemTableQueryColumn> listSys = critSys.list();
			for (SystemTableQueryColumn item : listSys) {
				UserTableQueryColumn utqc = new UserTableQueryColumn();
				// utqc.setExtendTableColumn(null);
				utqc.setHiddenValue(item.getHiddenValue());
				utqc.setIsDisplay(item.getIsDisplay());
				utqc.setIsHiddenItem(item.getIsHiddenItem());
				utqc.setIsMustInput(item.getIsMustInput());
				utqc.setLengthForPage(item.getLengthForPage());
				utqc.setMainTableColumn(item.getMainTableColumn());
				utqc.setMatchMode(item.getMatchMode());
				utqc.setOrder(item.getOrder());
				utqc.setSystemMainTable(item.getSystemMainTable());
				utqc.setSystemTableQuery(item.getSystemTableQuery());
				utqc.setSystemTableQueryColumn(item);
				utqc.setUserInfo(null);
				list.add(utqc);
			}
		}
		return list;
	}

	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}

	public void setSystemMainColumnService(
			SystemMainColumnService systemMainColumnService) {
		this.systemMainColumnService = systemMainColumnService;
	}

	public void setSystemMainTableService(
			SystemMainTableService systemMainTableService) {
		this.systemMainTableService = systemMainTableService;
	}

	public void setSystemColumnService(SystemColumnService systemColumnService) {
		this.systemColumnService = systemColumnService;
	}

	public void setSystemMainAndExtColumnService(
			SystemMainAndExtColumnService systemMainAndExtColumnService) {
		this.systemMainAndExtColumnService = systemMainAndExtColumnService;
	}

	public Object saveMainAndExtDataForUser(Class clazz, Map requestParams,
			UserInfo user) {

		// start add by tongjp 2009-09-15 test why event have saved twice,to
		// make sure whether this method's bug
		Set sets = requestParams.keySet();
		Iterator it = sets.iterator();
		String testst = "";
		while (it.hasNext()) {
			Object ob = it.next();
			Object st = requestParams.get(ob);
			testst = testst + ob.toString() + ":" + st.toString() + "==";
		}
		//System.out.println(testst + "----------------------------------");
		// end
		Object newObject = BeanUtil.getObject(requestParams, clazz);
		Long mainRowId = null;
		BeanWrapper bwNew = new BeanWrapperImpl(newObject);
		SystemMainTable smt = systemMainTableService
				.findSystemMainTableByClazz(clazz);
		Criteria c = createCriteria(UserTableSetting.class);
		c.add(Restrictions.eq("userInfo", user));
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.isNotNull("mainTableColumn"));
		c.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
		c.addOrder(Order.asc("order"));
		// c.add(Restrictions.eq("isDisplay", new Integer(1)));
		List list = c.list();
		// ===========================================
		if (user != null && list.isEmpty()) {// 如因问题未初始化用户字段，这里进行初始化
			Criteria cSys = super.getCriteria(SystemTableSetting.class);
			cSys.add(Restrictions.eq("systemMainTable", smt));
			cSys.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
			cSys.add(Restrictions.isNotNull("mainTableColumn"));
			cSys.setFetchMode("mainTableColumn", FetchMode.JOIN);
			// cSys.add(Restrictions.eq("isDisplay", new Integer(1)));
			cSys.addOrder(Order.asc("order"));
			List<SystemTableSetting> listSys = cSys.list();
			for (SystemTableSetting sts : listSys) {
				UserTableSetting ax = new UserTableSetting();
				ax.setSystemMainTable(smt);
				ax.setSettingType(sts.getSettingType());
				ax.setDescn(sts.getDescn());
				Column column = sts.getColumn();
				// if(column instanceof SystemMainTableColumn){
				ax.setMainTableColumn((SystemMainTableColumn) column);
				// }else{
				// ax.setExtendTableColumn((SystemMainTableExtColumn) column);
				// }
				ax.setOrder(sts.getOrder());
				ax.setIsDisplay(sts.getIsDisplay());
				ax.setIsMustInput(sts.getIsMustInput());
				ax.setUserInfo(UserContext.getUserInfo());
				ax.setLengthForPage(sts.getLengthForPage());
				list.add(ax);
			}
		}

		// 暂存选中的复选组框字段
		Set<Column> checkboxGroupSet = new HashSet<Column>();

		for (int i = 0; i < list.size(); i++) {
			UserTableSetting uts = (UserTableSetting) list.get(i);
			Column column = uts.getColumn();
			Integer isMainAndExt = uts.getMainTableColumn().getIsExtColumn();
			if (isMainAndExt == SystemMainTableColumn.isMain) {
				String propertyName = column.getPropertyName();

				SystemMainTableColumnType smtct = column
						.getSystemMainTableColumnType();
				String smtctName = smtct.getColumnTypeName();
				boolean isHiddenType = smtctName.equals("hidden");

				Integer isHiddenItem = column.getIsHiddenItem(); // 字段级别的隐藏给前端手动给隐藏域赋值
				boolean isHidden = isHiddenItem != null
						&& isHiddenItem.intValue() == 1;

				// 不可见字段，但对于如订单状态等字段，需要使用最新的值覆盖原值。
				// 也就是对于非隐藏字段的不可见字段才拷贝之前的值
				if (uts.getIsDisplay().intValue() == 0 && !isHidden
						&& !isHiddenType) {// 不可见,创建日期存在问题
					Long id = ((BaseObject) newObject).getId();
					if (id != null) {// update
						Object oldObject = this.getObject(clazz, id, true);
						if (oldObject != null) {
							BeanWrapper bwOld = new BeanWrapperImpl(oldObject);

							Object oldPropValue = bwOld
									.getPropertyValue(propertyName);
							bwNew.setPropertyValue(propertyName, oldPropValue);
						}

					}

				} 
//				else {// end
//					Long id = ((BaseObject) newObject).getId();
//					if (id != null) {// update
//						Object newPropertyValue = bwNew
//								.getPropertyValue(propertyName);
//						Object oldObject = this.getObject(clazz, id, true);
//						boolean isNewNull = (newPropertyValue == null || newPropertyValue != null
//								&& newPropertyValue.toString() != null
//								&& newPropertyValue.toString().equals("null"));
//					}
//
//				}
				if (smtctName.equalsIgnoreCase("multiFile")) {
					if (requestParams.get(propertyName) != null) {
						String nowtime = (String) requestParams
								.get(propertyName);

						SystemMainTable ftable = column.getForeignTable();
						String fileClassName = ftable.getClassName();
						Class fileClazz = this.getClass(fileClassName);

						Criteria cf = super.getCriteria(fileClazz);
						cf.add(Restrictions.eq("nowtime", nowtime));
						cf.setProjection(Projections.rowCount());
						Integer cfRow = (Integer) cf.uniqueResult();
						if (cfRow == null || cfRow.intValue() == 0) {
							bwNew.setPropertyValue(propertyName, null);
						}

					}

				} else if (smtctName.equalsIgnoreCase("checkboxGroup")) {
					// 因需要先保持主对象，故先暂存复选框字段，待保存主对象后，在保存复选数据
					checkboxGroupSet.add(column);
				}

				// 自增序列
				Integer identityFlag = uts.getMainTableColumn()
						.getIdentityFlag();
				// 如果是标识字段，且是新建的，则查询原数据的最大值再加递增量，如果原来无数据，则给出起始值
				if (identityFlag != null && identityFlag.intValue() == 1
						&& ((BaseObject) newObject).getId() == null) {
					// begin
					SystemMainTableIdBuilder smtIdBuilder = this
							.getIdBuilder(smt);
					if (smtIdBuilder != null) {
						String prefix = smtIdBuilder.getPrefix(); // C014-
						Long length = smtIdBuilder.getLength();
						//add by duxh for 没有编号或者编号不符合规则时生成新的编号 in 20100506 begin
						Object curId = bwNew.getPropertyValue(propertyName);
						if(curId==null||curId!=null&&!curId.toString().startsWith(prefix)){
						//add by duxh for 没有编号或者编号不符合规则时生成新的编号 in 20100506 end
							// int numberLength = length.intValue()-prefix.length();
							String ruleFile = smtIdBuilder.getRuleFileName();
							String latestValue = smtIdBuilder.getLatestValue();
							Department dept = smtIdBuilder.getDepartment();
	
							Map map = new HashedMap();
							map.put("ruleName", "systemTableIdGen");
							map.put("prefix", prefix);
							map.put("length", String.valueOf(length));
							map.put("latestValue", latestValue);
							// map.put("latestNumber", latestNumber);
							map.put("dept", dept);
	
							if (ruleFile == null) {
								ruleFile = "systemTableIdGen.drl";
							}
							String result2 = "/com/zsgj/info/framework/util/idgen/"
									+ ruleFile;
							String nextValue = IdGenRuleMethodHelper.executeRule(
									result2, map);
							smtIdBuilder.setLatestValue(nextValue);
							bwNew.setPropertyValue(propertyName, nextValue);
						}
					// end
					}
				}
			}
		}
		Object result = null;
		try {
			result = this.save(newObject);
//			System.out.println("_______________________________保存实体"
//					+ new Date()
//					+ "——————————————————————————————————————————————————");
			// begin_保存复选组框字段数据
			for (Column column : checkboxGroupSet) {
				String propertyName = column.getPropertyName();
				SystemMainTable refTable = column.getReferencedTable();
				String refClassName = refTable.getClassName();
				Class refClass = this.getClass(refClassName);// UserRole
				SystemMainTableColumn refVColumn = column
						.getReferencedTableValueColumn();
				SystemMainTableColumn refPColumn = column
						.getReferencedTableParentColumn();
				String refvcName = refVColumn.getPropertyName();// role
				String refpcName = refPColumn.getPropertyName(); // user

				Set<Long> currentSelects = new HashSet<Long>(); // 暂存选中的对象ID
				// String selectObjectId = "(";

				SystemMainTable fTable = column.getForeignTable(); // Role
				String fclassName = fTable.getClassName();
				Class fClass = this.getClass(fclassName);// all roles
				List<BaseObject> fobjects = getAll(fClass);
				Iterator<BaseObject> iter = fobjects.iterator();
				while (iter.hasNext()) { // 遍历外键对象，如Role
					BaseObject fObjectRole = iter.next(); // role Object
					Long fobjectId = fObjectRole.getId();
					String propName = propertyName + "$" + fobjectId; // roles.11
					Object value = requestParams.get(propName);
					if (value != null
							&& value.toString().equalsIgnoreCase("on")) { // 需要选中
						try {
							Object refObject = refClass.newInstance(); // UserRole
							// Object

							Criteria cRef = super.getCriteria(refClass);
							cRef.add(Restrictions.eq(refpcName, newObject));// UserRole.user=userObject
							cRef.add(Restrictions.eq(refvcName, fObjectRole)); // UserRole.role=roleObject
							cRef.setProjection(Projections.rowCount());
							Integer fObjectCount = (Integer) cRef
									.uniqueResult();
							if (fObjectCount == null
									|| fObjectCount.intValue() == 0) {

								BeanWrapper bWrapper = new BeanWrapperImpl(
										refObject);
								bWrapper.setPropertyValue(refpcName, newObject);// UserRole.user=userObject
								bWrapper.setPropertyValue(refvcName,
										fObjectRole);// UserRole.role=roleObject
								super.save(refObject);

								currentSelects.add(fobjectId);
								// selectObjectId = selectObjectId +
								// fObjectRole.getId() + ",";
							}

						} catch (Exception e) {

						}
					}

				}// end遍历外键对象

				// begin_开始删除之前选中的多余记录
				Criteria cRef = super.getCriteria(refClass);
				// UserRole.user=userObject
				cRef.add(Restrictions.eq(refpcName, newObject));
				// UserRole.role not in(1,2,3)
				cRef.add(Restrictions.not(Restrictions.in(refvcName + ".id",
						currentSelects)));
				List notSelects = cRef.list();
				Iterator iterNotS = notSelects.iterator();
				while (iterNotS.hasNext()) {
					Object notSelectObject = iterNotS.next();
					super.save(notSelectObject);
				}
				// end

			}// end_遍历_checkboxGroupSet

		} catch (Exception e) {
			e.printStackTrace();
			// 解决新增的实体无法更新问题
			super.getSession().update(newObject);
//			System.out.println("_______________________________更新实体"
//					+ new Date()
//					+ "——————————————————————————————————————————————————");
		}
		mainRowId = ((BaseObject) newObject).getId();
		// ===========================================
		for (int i = 0; i < list.size(); i++) {
			UserTableSetting uts = (UserTableSetting) list.get(i);
			Column column = uts.getColumn();
			Integer isMainAndExt = uts.getMainTableColumn().getIsExtColumn();
			if (isMainAndExt == SystemMainTableColumn.isExt) {
				SystemMainTableColumn smtc = uts.getMainTableColumn();
				String extColuId = smtc.getId().toString();
				// Integer extTableColumnNum=smtec.getExtendTableColumnNum();
				String propertyName = column.getPropertyName();
				String extColValue = (String) requestParams.get(propertyName);
				ExtData extData = (ExtData) systemMainAndExtColumnService
						.findObjectByMainRowIdAndExtColId(Integer
								.parseInt(mainRowId.toString()), Integer
								.parseInt(extColuId));
				if (extData == null) {
					extData = new ExtData();
					extData.setExtendTableId(Integer.parseInt(extColuId));
					extData.setMainTableRowID(Integer.parseInt(mainRowId
							.toString()));
				}
				if (extColValue != null && extColValue.equals("")) {
					extColValue = null;
				}
				extData.setExtendTableData(extColValue);
				this.save(extData);
			}
		}
		return result;
	}
}
