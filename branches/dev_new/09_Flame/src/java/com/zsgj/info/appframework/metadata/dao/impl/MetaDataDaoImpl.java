package com.zsgj.info.appframework.metadata.dao.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.metadata.dao.MetaDataDao;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.SystemTableQuery;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.exception.DaoException;
import com.zsgj.info.framework.orm.BaseDao;
import com.zsgj.info.framework.orm.JdbcMetaDataAware;
import com.zsgj.info.framework.security.entity.UserInfo;

public class MetaDataDaoImpl extends BaseDao implements MetaDataDao {
	
	private JdbcMetaDataAware jdbcMetaData  = null;

	public void setJdbcMetaData(JdbcMetaDataAware jdbcMetaData) {
		this.jdbcMetaData = jdbcMetaData;
	}

	public List selectEntitysAllByClassName(String className) {
		/*Map mapClass = getDaoSupport().getAllClassMetadata();
		Set keySet = mapClass.keySet();
		Iterator iter = keySet.iterator();
		while(iter.hasNext()){
			String clazz = (String) iter.next();
			ClassMetadata classMetadata = getDaoSupport().getClassMetadata(clazz);
			
			String[] properties = classMetadata.getPropertyNames();
			for(int i=0; i<properties.length; i++){
				Type type = classMetadata.getPropertyType(properties[i]);
				Class typeClazz = type.getClass();
				String typeName = type.getName();
				System.out.println();
			}
		}
		Map mapCollections = getDaoSupport().getAllCollectionMetadata();*/
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		DetachedCriteria dc =DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.isNotNull("id"));
		if(className.equalsIgnoreCase("com.zsgj.knowledge.common.entity.InternalRelationPerson")){
			dc.addOrder(Order.asc("itcode"));
		}
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}

	public Object selectEntityByClassNameAndId(String className, Long id) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		DetachedCriteria dc =DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.eq("id", id));
		Object object = getDaoSupport().uniqueResult(dc);
		return object;
	}

//	public List selectExtendTableColumnsByMainTableName(String tableName) {
//		SystemMainTable mt = (SystemMainTable) getDaoSupport().selectByKey(SystemMainTable.class, "tableName", tableName);
//		StringBuffer bff = new StringBuffer();
//		bff.append("from SystemMainTableExtColumn etc ");
//		bff.append("where etc.systemMainTable=? ");
//		bff.append("order by etc.order ");
//		List txtbs = getDaoSupport().selectForList(bff.toString(), mt);
//		/*DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableExtColumn.class);
//		dc.add(Restrictions.eq("systemMainTable", mt));
//		dc.setFetchMode("systemMainTable", FetchMode.JOIN);
//		dc.setFetchMode("systemExtTable", FetchMode.JOIN);
//		dc.setFetchMode("foreignTable", FetchMode.JOIN);
//		dc.addOrder(Order.asc("order"));
//		List txtbs = getDaoSupport().selectByCriteria(dc);*/
//		return txtbs;
//	}

//	public List selectExtendTableColumnsByMainTableId(Long mainTableId) {
//		SystemMainTable mt = (SystemMainTable) getDaoSupport().select(SystemMainTable.class, mainTableId);
//		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableExtColumn.class);
//		dc.add(Restrictions.eq("systemMainTable", mt));
//		dc.setFetchMode("systemMainTable", FetchMode.JOIN);
//		dc.setFetchMode("systemExtTable", FetchMode.JOIN);
//		dc.setFetchMode("foreignTable", FetchMode.JOIN);
//		dc.setFetchMode("foreignTableKeyColumn", FetchMode.JOIN);
//		dc.setFetchMode("foreignTableValueColumn", FetchMode.JOIN);
//		dc.addOrder(Order.asc("order"));
//		List txtbs = getDaoSupport().selectByCriteria(dc);
//		return txtbs;
//	}

	public void deleteMainTableById(Long id) {
		SystemMainTable smt = (SystemMainTable) getDaoSupport().select(SystemMainTable.class, id);
		if(smt!=null){
			getDaoSupport().delete(smt);
//			Set<SystemMainTableColumn> columns = smt.getColumns();
//			for(SystemMainTableColumn c : columns){
//				getDaoSupport().delete(c);
//				getDaoSupport().flush();
//			}
			getDaoSupport().executeUpdate(
					"delete from SystemMainTableColumn smtc where smtc.systemMainTable=?", 
					new Object[]{smt});
			getDaoSupport().executeUpdate(
					"delete from SystemMainTableExtColumn etc where etc.systemMainTable=?", 
					new Object[]{smt});
			
			getDaoSupport().executeUpdate(
					"delete from SystemTableQuery stq where stq.systemMainTable=?", 
					new Object[]{smt});
			getDaoSupport().executeUpdate(
					"delete from SystemTableQueryColumn stqc where stqc.systemMainTable=?", 
					new Object[]{smt});
			getDaoSupport().executeUpdate(
					"delete from SystemTableSetting sts where sts.systemMainTable=?", 
					new Object[]{smt});
			getDaoSupport().executeUpdate(
					"delete from UserTableSetting uts where uts.systemMainTable=?", 
					new Object[]{smt});
			getDaoSupport().executeUpdate(
					"update SystemMainTableColumn smt set smt.foreignTable=null where smt.foreignTable=?", 
					new Object[]{smt});
			getDaoSupport().executeUpdate(
					"update SystemMainTableColumn smt2 set smt2.referencedTable=null where smt2.referencedTable=?", 
					new Object[]{smt});
			getDaoSupport().executeUpdate(
					"update SystemMainTableExtColumn etc2 set etc2.foreignTable=null where etc2.foreignTable=?", 
					new Object[]{smt});
			
			
		}
	}

	public SystemMainTable selectMainTableByTableName(String tableName) {
		return (SystemMainTable) getDaoSupport().selectByKey(SystemMainTable.class, "tableName", tableName);
	}

	public SystemMainTable insertOrUpdateMainTable(SystemMainTable smt) {
		return (SystemMainTable) getDaoSupport().insertOrUpdate(smt);
	}

	public SystemMainTable selectMainTableById(Long id) {
		SystemMainTable result = null;
		result = (SystemMainTable) getDaoSupport().select(SystemMainTable.class, id);
		return result;
	}

	public List selectMainTablesAll() {
		List list = null;
		list = getDaoSupport().selectAll(SystemMainTable.class);
		return list;
	}

	
//	public void deleteSysExtTableById(Long id) {
//		getDaoSupport().delete(SystemExtTable.class, id);
//	}
//
//	public SystemExtTable insertOrUpdateExtendColumnType(SystemExtTable smt) {
//		return (SystemExtTable) getDaoSupport().insertOrUpdate(smt);
//	}
//
//	public SystemExtTable selectSysExtTableById(Long id) {
//		SystemExtTable result = null;
//		result = (SystemExtTable) getDaoSupport().select(SystemExtTable.class, id);
//		return result;
//	}

//	public List selectSysExtTablesAll() {
//		List list = null;
//		list = getDaoSupport().selectAll(SystemExtTable.class);
//		return list;
//	}

	public void deleteMainColumnById(Long id) {
		SystemMainTableColumn smtc = (SystemMainTableColumn) getDaoSupport().select(SystemMainTableColumn.class, id);
		SystemMainTable sysMainTable = smtc.getSystemMainTable();
		SystemMainTableColumn pkColumn = sysMainTable.getPrimaryKeyColumn();
		if(smtc== pkColumn){
			sysMainTable.setPrimaryKeyColumn(null);
			getDaoSupport().update(sysMainTable);
		}
		getDaoSupport().executeUpdate("delete UserTableSetting uts where uts.mainTableColumn=?", new Object[]{smtc});
		getDaoSupport().delete(smtc);//删除主表字段前先删除用户字段设置表的对应记录
	}

	public SystemMainTableColumn insertOrUpdateMainColumn(SystemMainTableColumn mainColumn) {
		SystemMainTableColumn result = (SystemMainTableColumn) getDaoSupport().insertOrUpdate(mainColumn);
//		boolean isAdd = result.getId()==null;

		//新增或修改的字段被新设置为查询项
		if(mainColumn.getIsSearchItem()!=null&& mainColumn.getIsSearchItem().intValue()==1){
			SystemMainTable sysMainTable = mainColumn.getSystemMainTable();
			DetachedCriteria dc = DetachedCriteria.forClass(SystemTableQuery.class);
			dc.add(Restrictions.eq("systemMainTable", sysMainTable));
			List list = getDaoSupport().selectByCriteria(dc);
			
			for(int i=0; i<list.size(); i++){
				SystemTableQuery stq = (SystemTableQuery) list.get(i);
				//看之前该字段是否已经被设置为查询项
				DetachedCriteria dce = DetachedCriteria.forClass(SystemTableQueryColumn.class);
				dce.add(Restrictions.eq("systemTableQuery", stq));
				dce.add(Restrictions.eq("systemMainTable", sysMainTable));
				dce.add(Restrictions.eq("mainTableColumn", mainColumn));
				SystemTableQueryColumn res = (SystemTableQueryColumn) getDaoSupport().uniqueResult(dce);
				if(res==null){ //是否已经存在记录
					SystemTableQueryColumn stqc = new SystemTableQueryColumn();
					stqc.setSystemTableQuery(stq);
					stqc.setSystemMainTable(sysMainTable);
					//stqc.setExtendTableColumn(null);
					stqc.setMainTableColumn(mainColumn);
					//页面传递过来的extColumn的SystemExtTable为null，手动设置一下
					SystemMainTableColumnType type = (SystemMainTableColumnType)getDaoSupport().select(SystemMainTableColumnType.class, mainColumn.getTypeId()); 
					mainColumn.setSystemMainTableColumnType(type);
					if(mainColumn.isTextColumnType()){ //如果字段类型是文本类型，模糊搜索模式
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
					}if(mainColumn.isNumberColumnType()){ //如果字段类型是下拉列表之类的存数字的类型
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
					}if(mainColumn.isCurrencyColumn()){ //如果字段的验证类型是货币，搜索时应指定区间
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
					}if(mainColumn.isDateColumn()){ //如果字段的验证类型是时间，搜索时应指定区间
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
					}
					//初始SystemTableQueryColumn数据
					getDaoSupport().insertOrUpdate(stqc);
					//初始UserTableQueryColumn数据，需要使用上面的SystemTableQuery和SystemTableQueryColumn
					//遍历系统的所有人员
					List users = getDaoSupport().selectAll(UserInfo.class);
					Iterator iterUsr = users.iterator();
					while(iterUsr.hasNext()){
						UserInfo userInfo = (UserInfo) iterUsr.next();
						DetachedCriteria dcUtqc = DetachedCriteria.forClass(UserTableQueryColumn.class);
						dcUtqc.add(Restrictions.eq("systemTableQuery", stq));
						dcUtqc.add(Restrictions.eq("systemTableQueryColumn", stqc)); //刚保存的SystemTableQueryColumn
						dcUtqc.add(Restrictions.eq("userInfo", userInfo));
						Object userTableQueryColumn = getDaoSupport().uniqueResult(dcUtqc);
						if(userTableQueryColumn==null){
							UserTableQueryColumn utqc = new UserTableQueryColumn();
							utqc.setUserInfo(userInfo);
							utqc.setSystemTableQuery(stq);
							utqc.setSystemTableQueryColumn(stqc);
							utqc.setIsDisplay(Integer.valueOf(1));
							utqc.setOrder(null);
							getDaoSupport().insertOrUpdate(utqc); //插入UserTableQueryColumn
						}
					}
				}
				
				
			}
		}
		//否则如果是修改时，设置查询项为非查询项，则应该去除系统查询和用户查询字段表中的记录
		else if(mainColumn.getId()!=null
				&&mainColumn.getIsSearchItem()!=null&& mainColumn.getIsSearchItem().intValue()==0 ){
			//使用批量删除功能提高效率
			//getDaoSupport().executeUpdate("delete from UserTableSetting uts where uts.mainTableColumn=?", new Object[]{mainColumn});
			DetachedCriteria dc = DetachedCriteria.forClass(SystemTableQueryColumn.class);
			dc.add(Restrictions.eq("mainTableColumn", mainColumn));
			List<SystemTableQueryColumn> stqcList = getDaoSupport().selectByCriteria(dc);
			for(int i=0; i<stqcList.size(); i++){
				SystemTableQueryColumn stqc = stqcList.get(i);
				getDaoSupport().executeUpdate("delete from UserTableQueryColumn utqc " +
											  "where utqc.systemTableQueryColumn=?", new Object[]{stqc});
				getDaoSupport().delete(stqc);
				getDaoSupport().flush();
			}
			//
			
		}
		return result;
	}

	public SystemMainTableColumn selectMainColumnById(Long id) {
		return (SystemMainTableColumn) getDaoSupport().select(SystemMainTableColumn.class, id);
	}
	
	private SystemMainTableColumn getTableDefByTableName(String tableName, String columnName){
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableColumn.class);
		dc.add(Restrictions.eq("tableName", tableName));
		dc.add(Restrictions.eq("columnName", columnName));
		SystemMainTableColumn tf = (SystemMainTableColumn) getDaoSupport().uniqueResult(dc);
		return tf;
	}
	
	public List selectColumnsAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableColumn.class);
		dc.add(Restrictions.isNotNull("id"));
		dc.addOrder(Order.asc("table"));
		return getDaoSupport().selectByCriteria(dc);
	}

	public void initSysMainTableAllColumnsToUser(String mainTableId) {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTable.class);
		dc.add(Restrictions.eq("id", Long.valueOf(mainTableId)));
		dc.setFetchMode("columns", FetchMode.JOIN);
		SystemMainTable mt = (SystemMainTable) getDaoSupport().uniqueResult(dc);
		Set mainColumns = mt.getColumns();
		Iterator iter = mainColumns.iterator();
		while(iter.hasNext()){
			SystemMainTableColumn smtc = (SystemMainTableColumn) iter.next();
			
			List userInfos = getDaoSupport().selectAll(UserInfo.class);
			Iterator iterUser = userInfos.iterator();
			while(iterUser.hasNext()){ //遍历所有用户
				UserInfo userInfo = (UserInfo) iterUser.next();
				//--------------
				DetachedCriteria dcUts = DetachedCriteria.forClass(UserTableSetting.class);
				dcUts.add(Restrictions.eq("userInfo", userInfo));
				dcUts.add(Restrictions.eq("systemMainTable", smtc.getSystemMainTable()));
				dcUts.add(Restrictions.eq("mainTableColumn", smtc));
				dcUts.add(Restrictions.eq("settingType", new Integer(UserTableSetting.LIST)));//列表定制
				List object = getDaoSupport().selectByCriteria(dcUts);
				if(object==null||object.isEmpty()){ //说明此字段没有给该用户设置 列表可见性， 故需要补充设置
					UserTableSetting utsList = new UserTableSetting();
					utsList.setSettingType(UserTableSetting.LIST);
					utsList.setUserInfo(userInfo);
					utsList.setSystemMainTable(smtc.getSystemMainTable());
					utsList.setMainTableColumn(smtc);
					utsList.setIsDisplay(new Integer(1));
					getDaoSupport().insertOrUpdate(utsList);
					getDaoSupport().evict(utsList);
				}
				
//				--------------
				DetachedCriteria dcUtsI = DetachedCriteria.forClass(UserTableSetting.class);
				dcUtsI.add(Restrictions.eq("userInfo", userInfo));
				dcUtsI.add(Restrictions.eq("systemMainTable", smtc.getSystemMainTable()));
				dcUtsI.add(Restrictions.eq("mainTableColumn", smtc));
				dcUtsI.add(Restrictions.eq("settingType", new Integer(UserTableSetting.INPUT)));//列表定制
				List objectI = getDaoSupport().selectByCriteria(dcUtsI);
				if(objectI==null||objectI.isEmpty()){ //说明此字段没有给该用户设置 列表可见性， 故需要补充设置
					UserTableSetting utsList = new UserTableSetting();
					utsList.setSettingType(UserTableSetting.INPUT);
					utsList.setUserInfo(userInfo);
					utsList.setSystemMainTable(smtc.getSystemMainTable());
					utsList.setMainTableColumn(smtc);
					utsList.setIsDisplay(new Integer(1));
					getDaoSupport().insertOrUpdate(utsList);
					getDaoSupport().evict(utsList);
				}
			}
		}
	}

	public void initSysMainTableAllColumnsToUser() { //reLoadSysMainTableAllColumns2
		List sysMainTables = getDaoSupport().selectAll(SystemMainTable.class);
		Iterator iter = sysMainTables.iterator();
		while(iter.hasNext()){
			SystemMainTable item = (SystemMainTable) iter.next();
			String tableName = item.getTableName();

			ResultSetMetaData rsmd = jdbcMetaData.getResultSetMetaData(tableName);
			if(rsmd==null) {
				throw new DaoException("系统主表中存在无效表名");
			}
			try {
				for(int i=1; i<=rsmd.getColumnCount(); i++){
					String columnName = rsmd.getColumnName(i);
//					String typeName = rsmd.getColumnTypeName(i);
					rsmd.getCatalogName(i);
					rsmd.getColumnClassName(i);
					rsmd.getColumnLabel(i);
//					int length = rsmd.getPrecision(i);
					//判断此字段是否已在表结构定义表中存在
					SystemMainTableColumn tdfod = this.getTableDefByTableName(tableName, columnName);
					if(tdfod!=null){
						/*String columnCnName =tdfod.getColumnCnName();
						SystemMainTableColumn tdf = new SystemMainTableColumn();
						tdf.setId(tdfod.getId());
						tdf.setTable(item);
						tdf.setPropertyName(columnName.substring(0,1).toLowerCase()+ columnName.substring(1));
						tdf.setColumnName(columnName);
						tdf.setColumnCnName(columnCnName);
						tdf.setColumnType(typeName);
						tdf.setLength(new Integer(length));
						tdf.setTableName(tableName);
						getDaoSupport().insertOrUpdate(tdf);*/
						//-------------------
						
						List userInfos = getDaoSupport().selectAll(UserInfo.class);
						Iterator iterUser = userInfos.iterator();
						while(iterUser.hasNext()){ //遍历所有用户
							UserInfo userInfo = (UserInfo) iterUser.next();
							//--------------
							DetachedCriteria dcUts = DetachedCriteria.forClass(UserTableSetting.class);
							dcUts.add(Restrictions.eq("userInfo", userInfo));
							dcUts.add(Restrictions.eq("systemMainTable", tdfod.getSystemMainTable()));
							dcUts.add(Restrictions.eq("mainTableColumn", tdfod));
							dcUts.add(Restrictions.eq("settingType", new Integer(1)));//列表定制
							//dcUts.setProjection(Projections.rowCount());
							List listUts = getDaoSupport().selectByCriteria(dcUts);
							
//							--------当前主表所有字段的最大排序号-------------------
							int orderMax = getMaxOrderByUserMainTable(item, userInfo);
							//判断是否已经存在
							if(listUts==null|| listUts.isEmpty()){
								UserTableSetting utsList = new UserTableSetting();
								utsList.setSettingType(UserTableSetting.LIST);
								utsList.setUserInfo(userInfo);
								utsList.setSystemMainTable(item);
								utsList.setMainTableColumn(tdfod);
								utsList.setIsDisplay(new Integer(1));
								utsList.setOrder(new Integer(orderMax));
								getDaoSupport().insertOrUpdate(utsList);
								getDaoSupport().evict(utsList);
							}
							
//							--------------
							DetachedCriteria dcUtsI = DetachedCriteria.forClass(UserTableSetting.class);
							dcUtsI.add(Restrictions.eq("userInfo", userInfo));
							dcUtsI.add(Restrictions.eq("systemMainTable", tdfod.getSystemMainTable()));
							dcUtsI.add(Restrictions.eq("mainTableColumn", tdfod));
							dcUtsI.add(Restrictions.eq("settingType", new Integer(2)));//输入定制
							List listUtsi = getDaoSupport().selectByCriteria(dcUtsI);
							if(listUtsi==null|| listUtsi.isEmpty()){
								UserTableSetting utsInput = new UserTableSetting();
								utsInput.setSettingType(UserTableSetting.INPUT);
								utsInput.setUserInfo(userInfo);
								utsInput.setSystemMainTable(item);
								utsInput.setMainTableColumn(tdfod);
								utsInput.setIsDisplay(new Integer(1));
								utsInput.setOrder(new Integer(orderMax));
								getDaoSupport().insertOrUpdate(utsInput);
								getDaoSupport().evict(utsInput);
							}
							//if(tdf.getIsSearchItem().intValue()==1){
								
							//}
							
						}////遍历所有用户
						
					}		
					
					
					
				}//for
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public void initMainColumnOrderToUsers(SystemMainTable smt) {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableColumn.class);
		dc.add(Restrictions.eq("systemMainTable", smt));
		dc.addOrder(Order.asc("order"));
		List columns = getDaoSupport().selectByCriteria(dc);
		Iterator iter = columns.iterator();
		while(iter.hasNext()){
			SystemMainTableColumn column = (SystemMainTableColumn) iter.next();
			//获取有效用户
			List users = this.findUsersHaveColumnSetting();
			for(int i=0; i<users.size(); i++){
				UserInfo userInfo = (UserInfo) users.get(i);
				//获取用户该字段的可见性设定
				DetachedCriteria dcs = DetachedCriteria.forClass(UserTableSetting.class);
				dcs.add(Restrictions.eq("userInfo", userInfo));
				dcs.add(Restrictions.eq("systemMainTable", column.getSystemMainTable()));
				dcs.add(Restrictions.eq("mainTableColumn", column));
				dcs.add(Restrictions.eq("settingType", new Integer(1)));
				dcs.setProjection(Projections.count("id"));
				Object rowCountObject = getDaoSupport().uniqueResult(dcs);
				int rowCount = ((Integer)rowCountObject).intValue();
				if(rowCount>0){
					dcs.setProjection(null);
					Object object = getDaoSupport().uniqueResult(dcs);
					UserTableSetting uts = (UserTableSetting) object;
					uts.setOrder(column.getOrder());
					getDaoSupport().update(uts);
				}
				//--------------------------------
				DetachedCriteria dcsInput = DetachedCriteria.forClass(UserTableSetting.class);
				dcsInput.add(Restrictions.eq("userInfo", userInfo));
				dcsInput.add(Restrictions.eq("systemMainTable", column.getSystemMainTable()));
				dcsInput.add(Restrictions.eq("mainTableColumn", column));
				dcsInput.add(Restrictions.eq("settingType", new Integer(2)));
				dcsInput.setProjection(Projections.count("id"));
				Object rowCountObjectInput = getDaoSupport().uniqueResult(dcsInput);
				int rowCountInput = ((Integer)rowCountObjectInput).intValue();
				if(rowCountInput>0){
					dcsInput.setProjection(null);
					Object objectInput = getDaoSupport().uniqueResult(dcsInput);
					UserTableSetting utsInput = (UserTableSetting) objectInput;
					utsInput.setOrder(column.getOrder());
					getDaoSupport().update(utsInput);
				}
			}
			
		}
		
	}

	List findUsersHaveColumnSetting(){
		DetachedCriteria dc = DetachedCriteria.forClass(UserTableSetting.class);
		dc.setProjection(
			Projections.projectionList().add(Projections.property("this.userInfo").as("userInfo")));
		dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		//dc.setResultTransformer(Transformers.aliasToBean(UserInfo.class));		
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}
	
	//增加新字段后，刷新数据
	public void reLoadSysMainTableNewColumnsByTableName(String tableName) {
		SystemMainTable mainTb = (SystemMainTable)getDaoSupport()
								.selectByKey(SystemMainTable.class, "tableName", tableName);
		ResultSetMetaData rsmd = jdbcMetaData.getResultSetMetaData(tableName);
		if(rsmd==null) {
			throw new DaoException("系统主表中存在无效表名");
		}
		try {
			for(int i=1; i<=rsmd.getColumnCount(); i++){
				String columnName = rsmd.getColumnName(i);
				String typeName = rsmd.getColumnTypeName(i);
				rsmd.getCatalogName(i);
				rsmd.getColumnClassName(i);
				rsmd.getColumnLabel(i);
				int length = rsmd.getPrecision(i);
				//判断此字段是否已在表结构定义表中存在
				SystemMainTableColumn tdfod = this.getTableDefByTableName(tableName, columnName);
				if(tdfod==null){
					SystemMainTableColumn tdf = new SystemMainTableColumn();
					tdf.setPropertyName(columnName.substring(0,1).toLowerCase()+ columnName.substring(1));
					tdf.setColumnName(columnName);
					tdf.setColumnType(typeName);
					tdf.setLength(new Integer(length));
					tdf.setSystemMainTable(mainTb);
					tdf.setTableName(tableName);
					tdf.setIsSearchItem(Integer.valueOf(0));
					tdf.setIsOutputItem(Integer.valueOf(0));
					tdf.setIsMustInput(Integer.valueOf(0));
					getDaoSupport().insertOrUpdate(tdf);//insert
					/*
					//List userInfos = getDaoSupport().selectAll(UserInfo.class);
					List userInfos = findUsersHaveColumnSetting();
					Iterator iterUser = userInfos.iterator();
					while(iterUser.hasNext()){ //遍历所有用户
						UserInfo userInfo = (UserInfo) iterUser.next();
//							--------当前主表所有字段的最大排序号-------------------
						int orderMax = getMaxOrderByUserMainTable(mainTb, userInfo);
						
						UserTableSetting utsList = new UserTableSetting();
						utsList.setSettingType(UserTableSetting.LIST);
						utsList.setUserInfo(userInfo);
						utsList.setSystemMainTable(mainTb);
						utsList.setMainTableColumn(tdf);
						utsList.setIsDisplay(new Integer(1));
						utsList.setOrder(new Integer(orderMax));
						getDaoSupport().insertOrUpdate(utsList);
						getDaoSupport().evict(utsList);
						
						//if(tdf.getIsSearchItem().intValue()==1){
							UserTableSetting utsInput = new UserTableSetting();
							utsInput.setSettingType(UserTableSetting.INPUT);
							utsInput.setUserInfo(userInfo);
							utsInput.setSystemMainTable(mainTb);
							utsInput.setMainTableColumn(tdf);
							utsInput.setIsDisplay(new Integer(1));
							utsInput.setOrder(new Integer(orderMax));
							getDaoSupport().insertOrUpdate(utsInput);
							getDaoSupport().evict(utsList);
						//}
						
					}////遍历所有用户
*/					
				}		
			}//for
		} catch (SQLException e) {
			e.printStackTrace();
	  }
	
	}

	public void reLoadSysMainTableMultiNewColumnsToUsers() {
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableColumn.class);
		dc.add(Restrictions.isNotNull("columnCnName"));
		List list = getDaoSupport().selectByCriteria(dc);
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			SystemMainTableColumn colum = (SystemMainTableColumn) iter.next();
			
			if(colum.isMultiColumn()){
				List userInfos = getDaoSupport().selectAll(UserInfo.class);
				Iterator iterUser = userInfos.iterator();
				while(iterUser.hasNext()){ //遍历所有用户
					UserInfo userInfo = (UserInfo) iterUser.next();
					
					DetachedCriteria dcU= DetachedCriteria.forClass(UserTableSetting.class);
					dcU.add(Restrictions.eq("userInfo", userInfo));
					dcU.add(Restrictions.eq("mainTableColumn", colum));
					//dcU.add(Restrictions.eq("settingType", colum));
					List utsUs = getDaoSupport().selectByCriteria(dcU);
					if(utsUs!=null&&utsUs.size()==0){

//						--------当前主表所有字段的最大排序号-------------------
						int orderMax = getMaxOrderByUserMainTable(colum.getSystemMainTable(), userInfo);
						
						UserTableSetting utsList = new UserTableSetting();
						utsList.setSettingType(UserTableSetting.LIST);
						utsList.setUserInfo(userInfo);
						utsList.setSystemMainTable(colum.getSystemMainTable());
						utsList.setMainTableColumn(colum);
						utsList.setIsDisplay(new Integer(1));
						utsList.setOrder(new Integer(orderMax));
						getDaoSupport().insertOrUpdate(utsList);
						getDaoSupport().evict(utsList);
						
						//if(tdf.getIsSearchItem().intValue()==1){
						UserTableSetting utsInput = new UserTableSetting();
						utsInput.setSettingType(UserTableSetting.INPUT);
						utsInput.setUserInfo(userInfo);
						utsInput.setSystemMainTable(colum.getSystemMainTable());
						utsInput.setMainTableColumn(colum);
						utsInput.setIsDisplay(new Integer(orderMax));
						utsInput.setOrder(new Integer(orderMax));
						getDaoSupport().insertOrUpdate(utsInput);
						getDaoSupport().evict(utsList);
					}
					
					//}
					
				}
			}
		}
	}

	public void reLoadSysMainTableNewColumns() {
		List sysMainTables = getDaoSupport().selectAll(SystemMainTable.class);
		Iterator iter = sysMainTables.iterator();
		while(iter.hasNext()){
			SystemMainTable item = (SystemMainTable) iter.next();
			String tableName = item.getTableName();

			ResultSetMetaData rsmd = jdbcMetaData.getResultSetMetaData(tableName);
			if(rsmd==null) {
				throw new DaoException("系统主表中存在无效表名");
			}
			try {
				for(int i=1; i<=rsmd.getColumnCount(); i++){
					String columnName = rsmd.getColumnName(i);
					String typeName = rsmd.getColumnTypeName(i);
					rsmd.getCatalogName(i);
					rsmd.getColumnClassName(i);
					rsmd.getColumnLabel(i);
					int length = rsmd.getPrecision(i);
					//判断此字段是否已在表结构定义表中存在
					SystemMainTableColumn tdfod = this.getTableDefByTableName(tableName, columnName);
					if(tdfod==null){
						SystemMainTableColumn tdf = new SystemMainTableColumn();
						tdf.setPropertyName(columnName.substring(0,1).toLowerCase()+ columnName.substring(1));
						tdf.setColumnName(columnName);
						tdf.setColumnType(typeName);
						tdf.setLength(new Integer(length));
						tdf.setSystemMainTable(item);
						tdf.setTableName(tableName);
						tdf.setIsSearchItem(Integer.valueOf(0));
						tdf.setIsOutputItem(Integer.valueOf(0));
						tdf.setIsMustInput(Integer.valueOf(0));
						getDaoSupport().insertOrUpdate(tdf);//insert
						
						List userInfos = getDaoSupport().selectAll(UserInfo.class);
						Iterator iterUser = userInfos.iterator();
						while(iterUser.hasNext()){ //遍历所有用户
							UserInfo userInfo = (UserInfo) iterUser.next();
//							--------当前主表所有字段的最大排序号-------------------
							int orderMax = getMaxOrderByUserMainTable(item, userInfo);
							
							UserTableSetting utsList = new UserTableSetting();
							utsList.setSettingType(UserTableSetting.LIST);
							utsList.setUserInfo(userInfo);
							utsList.setSystemMainTable(item);
							utsList.setMainTableColumn(tdf);
							utsList.setIsDisplay(new Integer(1));
							utsList.setOrder(new Integer(orderMax));
							getDaoSupport().insertOrUpdate(utsList);
							getDaoSupport().evict(utsList);
							
							//if(tdf.getIsSearchItem().intValue()==1){
								UserTableSetting utsInput = new UserTableSetting();
								utsInput.setSettingType(UserTableSetting.INPUT);
								utsInput.setUserInfo(userInfo);
								utsInput.setSystemMainTable(item);
								utsInput.setMainTableColumn(tdf);
								utsInput.setIsDisplay(new Integer(1));
								utsInput.setOrder(new Integer(orderMax));
								getDaoSupport().insertOrUpdate(utsInput);
								getDaoSupport().evict(utsList);
							//}
							
						}
						
					}		
				}//for
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	public ExtText insertOrUpdateExtText(ExtText extText) {
//		return (ExtText) getDaoSupport().insertOrUpdate(extText);
//	}
//
//	public ExtText selectExtTextByMainTableRowIdAndColumnNum(Long mainId, Integer idx) {
//		DetachedCriteria dc = DetachedCriteria.forClass(ExtText.class);
//		dc.add(Restrictions.eq("mainTableRowID", mainId));
//		dc.add(Restrictions.eq("useColumnNum", idx));
//		List list = getDaoSupport().selectByCriteria(dc);
//		if(list==null|| list.isEmpty()) return null;
//		if(list!=null&& list.size()>2) throw new DaoException("文本字段扩展表数据有问题");
//		return (ExtText) list.iterator().next();
//	}
//
//	public ExtSelect insertOrUpdateExtSelect(ExtSelect extText) {
//		return (ExtSelect) getDaoSupport().insertOrUpdate(extText);
//	}
//
//	public ExtSelect selectExtSelectByMainTableRowIdAndColumnNum(Long mainId, Integer idx) {
//		DetachedCriteria dc = DetachedCriteria.forClass(ExtSelect.class);
//		dc.add(Restrictions.eq("mainTableRowID", mainId));
//		dc.add(Restrictions.eq("useColumnNum", idx));
//		List list = getDaoSupport().selectByCriteria(dc);
//		if(list==null|| list.isEmpty()) return null;
//		if(list!=null&& list.size()>2) throw new DaoException("下拉列表字段扩展表数据有问题");
//		return (ExtSelect) list.iterator().next();
//	}
//
//	public ExtTextArea insertOrUpdateExtTextArea(ExtTextArea extTextArea) {
//		return (ExtTextArea) getDaoSupport().insertOrUpdate(extTextArea);
//	}
//
//	public ExtTextArea selectExtTextAreaByMainTableRowIdAndColumnNum(Long mainId, Integer idx) {
//		DetachedCriteria dc = DetachedCriteria.forClass(ExtTextArea.class);
//		dc.add(Restrictions.eq("mainTableRowID", mainId));
//		dc.add(Restrictions.eq("useColumnNum", idx));
//		List list = getDaoSupport().selectByCriteria(dc);
//		if(list==null|| list.isEmpty()) return null;
//		if(list!=null&& list.size()>2) throw new DaoException("文本域字段扩展表数据有问题");
//		return (ExtTextArea) list.iterator().next();
//	}

	public List selectMainColumnsByTableId(Long tableId) {
		SystemMainTable smt = (SystemMainTable) getDaoSupport().select(SystemMainTable.class, tableId);
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableColumn.class);
		dc.add(Restrictions.eq("systemMainTable", smt));
		dc.addOrder(Order.asc("order"));
		//dc.add(Restrictions.isNotNull("columnCnName"));
		//dc.add(Restrictions.isNotEmpty("columnCnName"));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}

	public List selectMainColumnsByTableName(String tableName) {
		SystemMainTable smt = (SystemMainTable) getDaoSupport().selectByKey(SystemMainTable.class, "tableName", tableName);
							
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableColumn.class);
		dc.add(Restrictions.eq("systemMainTable", smt));
		dc.addOrder(Order.asc("order"));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}
	

	public List selectMainOutputColumnsByTableName(String tableName) {
		SystemMainTable smt = (SystemMainTable) getDaoSupport().selectByKey(SystemMainTable.class, "tableName", tableName);
		
		DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableColumn.class);
		dc.add(Restrictions.eq("systemMainTable", smt));
		dc.add(Restrictions.eq("isOutputItem", new Integer(1)));
		dc.addOrder(Order.asc("order"));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}

//	public void deleteExtendTableById(Long id) {
//		SystemMainTableExtColumn extendTableColumn = (SystemMainTableExtColumn) getDaoSupport().select(SystemMainTableExtColumn.class, id);
//		SystemMainTable systemMainTable = extendTableColumn.getSystemMainTable();
//		Integer orderExt = extendTableColumn.getOrder(); //扩展字段的order
////		----------------------------------------------------------------------
//		DetachedCriteria dc = DetachedCriteria.forClass(UserTableSetting.class);
//		dc.add(Restrictions.eq("systemMainTable", systemMainTable));
//		dc.add(Restrictions.gt("order", orderExt));
//		List list = getDaoSupport().selectByCriteria(dc);
//		Iterator iter = list.iterator();
//		while(iter.hasNext()){ //删除某扩展字段后，遍历UserTableSetting表，将所有排序order比删除order大的记录的order都依次减1，无论字段类型是什么
//			UserTableSetting uts = (UserTableSetting)iter.next();
//			Integer order = uts.getOrder();
//			uts.setOrder(new Integer(order.intValue()-1));
//			getDaoSupport().update(uts);
//			getDaoSupport().evict(uts);//由于是批量修改，情况缓存以提高效率
////			uts.setExtendTableColumn(extendTableColumn);
//		}
////		---------------删除UserTableSetting中的扩展字段------------------------------------------------------ 
///*		DetachedCriteria dc2 = DetachedCriteria.forClass(UserTableSetting.class);
//		dc2.add(Restrictions.eq("systemMainTable", systemMainTable));
//		dc2.add(Restrictions.eq("extendTableColumn", extendTableColumn));
//		List utsList = getDaoSupport().selectByCriteria(dc2);
//		if(utsList!=null&& !utsList.isEmpty()){
//			getDaoSupport().delete(utsList);
//		}	*/
////		---------------最终删除扩展字段--------------------------------------------------------
//		if(extendTableColumn.isSelfDefSelect()){
//			Integer extSelectNumber = extendTableColumn.getExtendTableColumnNum();
//				getDaoSupport().executeUpdate("delete ExtOption et where et.extSelectColumnNum=?", new Integer[]{extSelectNumber});
//		}
//		getDaoSupport().delete(extendTableColumn);
//		getDaoSupport().flush();
//	}
	
	//保存扩展字段
//	public SystemMainTableExtColumn insertOrUpdateExtendColumn(SystemMainTableExtColumn extColumn) {
//		Object result = null;
//		int extMetaDataSum = Constants.extMetaDataSum;
//		//SystemExtTable sysExtTable = extColumn.getSystemExtTable();
//		//------------------获取ExtendTableColumn中extTableColumnNum的最大值----------------
//		if(extColumn.getId()==null){ //新增扩展字段
//			Integer maxColumnNum = getMaxColumnNumByExtendColumn(sysExtTable);
//			Integer emptyNumber = null;
//			for(int i=1; i<maxColumnNum.intValue(); i++){ //从1到maxColumnNum遍历,找一个最小的空闲数字
//				DetachedCriteria dc = DetachedCriteria.forClass(SystemMainTableExtColumn.class);
//				dc.add(Restrictions.eq("systemExtTable", sysExtTable));
//				dc.add(Restrictions.eq("extendTableColumnNum", new Integer(i)));
//				Object column = getDaoSupport().uniqueResult(dc);
//				if(column==null){
//					emptyNumber = new Integer(i);
//					break;
//				}else{
//					getDaoSupport().evict((BaseObject) column);
//				}
//			}
//			if(emptyNumber == null){
//				emptyNumber = new Integer(maxColumnNum.intValue()+1);
//			}
//			extColumn.setExtendTableColumnNum(emptyNumber);
//			//save SystemExtTable, 界面传递过来的SystemExtTable可能只有一个id属性有值，故重新加载
//			SystemExtTable sysExtTable2 = (SystemExtTable) getDaoSupport().select(SystemExtTable.class, sysExtTable.getId());
//			
//				int countleft = sysExtTable2.getCountLeft().intValue();
//				sysExtTable2.setCountLeft(new Integer(countleft-1));
//				getDaoSupport().update(sysExtTable2);
//		}
//		//before save
//		//save extendTable
//		boolean isAdd = false;
//		if(extColumn.getId()==null){
//			isAdd = true;
//		}
//		result = getDaoSupport().insertOrUpdate(extColumn);
//		getDaoSupport().flush();
//		
////		将新增扩展字段设置给所有的用户
//		if(isAdd){
//			List userInfos = getDaoSupport().selectAll(UserInfo.class);
//			Iterator iter = userInfos.iterator();
//			while(iter.hasNext()){ //遍历所有用户
//				UserInfo userInfo = (UserInfo) iter.next();
////				--------当前主表所有字段的最大排序号-------------------
//				int orderMax = getMaxOrderByUserMainTable(extColumn, userInfo);
//				
//				UserTableSetting utsList = new UserTableSetting(); //用户列表字段设置
//				utsList.setUserInfo(userInfo);
//				utsList.setSystemMainTable(extColumn.getSystemMainTable());
//				utsList.setExtendTableColumn(extColumn);
//				utsList.setIsDisplay(new Integer(1));
//				utsList.setOrder(new Integer(orderMax));
//				this.saveUserTableSettingByType(utsList, UserTableSetting.LIST);
//				
//				UserTableSetting utsInput = new UserTableSetting(); //用户输入字段设置
//				utsInput.setUserInfo(userInfo);
//				utsInput.setSystemMainTable(extColumn.getSystemMainTable());
//				utsInput.setExtendTableColumn(extColumn);
//				utsInput.setIsDisplay(new Integer(1));
//				utsInput.setOrder(new Integer(orderMax));
//				this.saveUserTableSettingByType(utsInput, UserTableSetting.INPUT);
//				
//				if(extColumn.getIsSearchItem().intValue()==1){ //如果默认是搜索字段，该字段给所有用户的搜索字段设置
//					UserTableSetting utsQuery = new UserTableSetting();
//					utsQuery.setUserInfo(userInfo);
//					utsQuery.setSystemMainTable(extColumn.getSystemMainTable());
//					utsQuery.setExtendTableColumn(extColumn);
//					utsQuery.setIsDisplay(new Integer(1));
//					utsQuery.setOrder(new Integer(orderMax));
//					this.saveUserTableSettingByType(utsQuery, UserTableSetting.QUERY);
//				}
//				
//				/*getDaoSupport().insertOrUpdate(uts);
//				getDaoSupport().evict(uts);*/
//			}
//		}
//		//新增或修改的字段被新设置为查询项
//		if(extColumn.getIsSearchItem()!=null&& extColumn.getIsSearchItem().intValue()==1){
//			SystemMainTable sysMainTable = extColumn.getSystemMainTable();
//			DetachedCriteria dc = DetachedCriteria.forClass(SystemTableQuery.class);
//			dc.add(Restrictions.eq("systemMainTable", sysMainTable));
//			List list = getDaoSupport().selectByCriteria(dc);
//			
//			for(int i=0; i<list.size(); i++){
//				SystemTableQuery stq = (SystemTableQuery) list.get(i);
//				//看之前该字段是否已经被设置为查询项
//				DetachedCriteria dce = DetachedCriteria.forClass(SystemTableQueryColumn.class);
//				dce.add(Restrictions.eq("systemTableQuery", stq));
//				dce.add(Restrictions.eq("systemMainTable", sysMainTable));
//				dce.add(Restrictions.eq("extendTableColumn", extColumn));
//				SystemTableQueryColumn res = (SystemTableQueryColumn) getDaoSupport().uniqueResult(dce);
//				if(res==null){ //是否已经存在记录
//					SystemTableQueryColumn stqc = new SystemTableQueryColumn();
//					stqc.setSystemTableQuery(stq);
//					stqc.setSystemMainTable(sysMainTable);
//					stqc.setMainTableColumn(null);
//					stqc.setExtendTableColumn(extColumn);
//					//页面传递过来的extColumn的SystemExtTable为null，手动设置一下
//					SystemExtTable sysExtTb = (SystemExtTable) getDaoSupport().select(SystemExtTable.class, extColumn.getSystemExtTable().getId());
//					extColumn.setSystemExtTable(sysExtTb);
//					if(extColumn.isTextColumnType()){ //如果字段类型是文本类型，模糊搜索模式
//						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
//					}if(extColumn.isNumberColumnType()){ //如果字段类型是下拉列表之类的存数字的类型
//						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//					}if(extColumn.isCurrencyColumn()){ //如果字段的验证类型是货币，搜索时应指定区间
//						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//					}if(extColumn.isDateColumn()){ //如果字段的验证类型是时间，搜索时应指定区间
//						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//					}
//					//初始SystemTableQueryColumn数据
//					getDaoSupport().insertOrUpdate(stqc);
//					//初始UserTableQueryColumn数据，需要使用上面的SystemTableQuery和SystemTableQueryColumn
//					//遍历系统的所有人员
//					List users = getDaoSupport().selectAll(UserInfo.class);
//					Iterator iterUsr = users.iterator();
//					while(iterUsr.hasNext()){
//						UserInfo userInfo = (UserInfo) iterUsr.next();
//						DetachedCriteria dcUtqc = DetachedCriteria.forClass(UserTableQueryColumn.class);
//						dcUtqc.add(Restrictions.eq("systemTableQuery", stq));
//						dcUtqc.add(Restrictions.eq("systemTableQueryColumn", stqc)); //刚保存的SystemTableQueryColumn
//						dcUtqc.add(Restrictions.eq("userInfo", userInfo));
//						Object userTableQueryColumn = getDaoSupport().uniqueResult(dcUtqc);
//						if(userTableQueryColumn==null){
//							UserTableQueryColumn utqc = new UserTableQueryColumn();
//							utqc.setUserInfo(userInfo);
//							utqc.setSystemTableQuery(stq);
//							utqc.setSystemTableQueryColumn(stqc);
//							utqc.setIsDisplay(Integer.valueOf(1));
//							utqc.setOrder(null);
//							getDaoSupport().insertOrUpdate(utqc); //插入UserTableQueryColumn
//						}
//					}
//				}
//				
//				
//			}
//		}
//		//否则如果是修改时，设置查询项为非查询项，则应该去除系统查询和用户查询字段表中的记录
//		else if(extColumn.getId()!=null
//				&&extColumn.getIsSearchItem()!=null&& extColumn.getIsSearchItem().intValue()==0 ){
//			//使用批量删除功能提高效率
//			//getDaoSupport().executeUpdate("delete from UserTableSetting uts where uts.extendTableColumn=?", new Object[]{extColumn});
//			DetachedCriteria dc = DetachedCriteria.forClass(SystemTableQueryColumn.class);
//			dc.add(Restrictions.eq("extendTableColumn", extColumn));
//			List<SystemTableQueryColumn> stqcList = getDaoSupport().selectByCriteria(dc);
//			for(int i=0; i<stqcList.size(); i++){
//				SystemTableQueryColumn stqc = stqcList.get(i);
//				getDaoSupport().executeUpdate("delete from UserTableQueryColumn utqc " +
//											  "where utqc.systemTableQueryColumn=?", new Object[]{stqc});
//				getDaoSupport().delete(stqc);
//				getDaoSupport().flush();
//			}
//			//
//			
//		}
//		return (SystemMainTableExtColumn) result;
//	}
	
	//保存用户字段显示设置数据
	@SuppressWarnings("unused")
	private void saveUserTableSettingByType(UserTableSetting uts, Integer settingType){
		uts.setSettingType(settingType);
		getDaoSupport().insertOrUpdate(uts);
		getDaoSupport().evict(uts);
	}

	//通过扩展字段类型获取该类型所有已使用的字段的最大order
//	private Integer getMaxColumnNumByExtendColumn(SystemExtTable sysExtTable) {
//		DetachedCriteria dcm = DetachedCriteria.forClass(SystemMainTableExtColumn.class);
//		dcm.add(Restrictions.eq("systemExtTable", sysExtTable));
//		dcm.setProjection(Projections.projectionList()
//				.add(Projections.max("extendTableColumnNum"))
//		);
//		Object maxNumber = getDaoSupport().uniqueResult(dcm);
//		Integer maxColumnNum = 0; 
//		if(maxNumber!=null){
//			maxColumnNum = (Integer) maxNumber;
//		}
//		return maxColumnNum;
//	}

	//通过扩展字段和当前用户获取最大的字段order
//	private int getMaxOrderByUserMainTable(SystemMainTableExtColumn extendTable, UserInfo userInfo) {
//		DetachedCriteria dcMaxOrder = DetachedCriteria.forClass(UserTableSetting.class);
//		dcMaxOrder.add(Restrictions.eq("settingType", UserTableSetting.LIST));
//		dcMaxOrder.add(Restrictions.eq("userInfo", userInfo));
//		dcMaxOrder.add(Restrictions.eq("systemMainTable", extendTable.getSystemMainTable()));
//		dcMaxOrder.setProjection(Projections.projectionList()
//				.add(Projections.max("order").as("maxOrder"))
//		);
//		Object maxOrder = getDaoSupport().uniqueResult(dcMaxOrder);
//		int orderMax = 0; 
//		if(maxOrder!=null){
//			orderMax = ((Integer)maxOrder).intValue();
//		}
//		return orderMax;
//	}
	
//	通过主字段和当前用户获取最大的字段order
	private int getMaxOrderByUserMainTable(SystemMainTable sysMainTable, UserInfo userInfo) {
		DetachedCriteria dcMaxOrder = DetachedCriteria.forClass(UserTableSetting.class);
		dcMaxOrder.add(Restrictions.eq("settingType", UserTableSetting.LIST));
		dcMaxOrder.add(Restrictions.eq("userInfo", userInfo));
		dcMaxOrder.add(Restrictions.eq("systemMainTable", sysMainTable));
		dcMaxOrder.setProjection(Projections.projectionList()
				.add(Projections.max("order").as("maxOrder"))
		);
		Object maxOrder = getDaoSupport().uniqueResult(dcMaxOrder);
		int orderMax = 0; 
		if(maxOrder!=null){
			orderMax = ((Integer)maxOrder).intValue();
		}
		return orderMax;
	}

//	public SystemMainTableExtColumn selectExtendTableById(Long id) {
//		return (SystemMainTableExtColumn) getDaoSupport().select(SystemMainTableExtColumn.class, id);
//	}

//	public List selectExtendTablesAll() {
//		return getDaoSupport().selectAll(SystemMainTableExtColumn.class);
//	}

//	public void deleteExtMetaDataByColumnNumberAndType(Integer columnNum, SystemExtTable sysExtTable){
//		String extTableName = sysExtTable.getTableName();
//		String extClassName = sysExtTable.getClassName();
//		Class extClazz = null;
//		try {
//			extClazz = Class.forName(extClassName);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		String columnName = "extColumn"+columnNum;
//		/*String extendTable = null;
//		if(extTableName.equalsIgnoreCase("tExt_Text")){
//			extendTable = "ExtText";
//		}else if(extTableName.equalsIgnoreCase("tExt_Select")){
//			extendTable = "ExtSelect";
//		}else if(extTableName.equalsIgnoreCase("tExt_TextArea")){
//			extendTable = "ExtTextArea";
//		}else if(extTableName.equalsIgnoreCase("tExt_Radio")){
//			extendTable = "ExtText";
//		}*/
//		
//		//if(!extTableName.equalsIgnoreCase("tExt_Option")){
//		getDaoSupport().executeUpdate("delete "+extClassName+" et where et."+columnName+" is not null", null);
//		//}else{
//		//getDaoSupport().executeUpdate("delete "+extClassName+" et where et.extSelectColumnNum=?", new Integer[]{columnNum});
//		//}
//		getDaoSupport().executeUpdate("update SystemExtTable s set s.countLeft = s.countLeft + 1 where s.tableName=?", new Object[]{extTableName});
//
//	}

	public void deleteUserExtendTableSettingById(Long id) {
		getDaoSupport().delete(UserTableSetting.class, id);
	}

	public UserTableSetting insertOrUpdateUserColumnSetting(UserTableSetting uets) {
		return (UserTableSetting) getDaoSupport().insertOrUpdate(uets);
	}

	public UserTableSetting selectUserExtendTableSettingById(Long id) {
		return (UserTableSetting) getDaoSupport().select(UserTableSetting.class, id);
	}

	public List selectUserExtendTableSettingsAll() {
		return getDaoSupport().selectAll(UserTableSetting.class);
	}
	
	public void insertNewColumnToUserColumnSet(UserInfo userInfo, SystemMainTable systemMainTable) {
//		--------当前主表所有字段的最大排序号-------------------
		DetachedCriteria dcMaxOrder = DetachedCriteria.forClass(UserTableSetting.class);
		dcMaxOrder.add(Restrictions.eq("systemMainTable", systemMainTable));
		dcMaxOrder.setProjection(Projections.projectionList()
				.add(Projections.max("order").as("maxOrder"))
		);
		Object result = getDaoSupport().uniqueResult(dcMaxOrder);
		int orderMax = 0; 
		if(result!=null){
			orderMax = ((Integer)result).intValue();
		}
//		--------遍历当前主表的所有字段------------------	
		DetachedCriteria dcMainTable = DetachedCriteria.forClass(SystemMainTableColumn.class);
		dcMainTable.add(Restrictions.eq("systemMainTable", systemMainTable));
		List mainColumns = getDaoSupport().selectByCriteria(dcMainTable);
		Iterator iterm = mainColumns.iterator();
		while(iterm.hasNext()){
			SystemMainTableColumn smtc = (SystemMainTableColumn)iterm.next();
//			--------看此主表字段是否已设置给当前用户-------------------
			DetachedCriteria dcCurrentColumn = DetachedCriteria.forClass(UserTableSetting.class);
			//dcCurrentColumn.add(Restrictions.eq("systemMainTable", systemMainTable));
			dcCurrentColumn.add(Restrictions.eq("userInfo", userInfo));
			dcCurrentColumn.add(Restrictions.eq("mainTableColumn", smtc));
			UserTableSetting utsExits = (UserTableSetting) getDaoSupport().uniqueResult(dcCurrentColumn);
			if(utsExits==null){ //未设置，说明是新增的字段，默认把最大的排序号给它
				UserTableSetting uts = new UserTableSetting(); //新插入此字段的设置数据
				uts.setSystemMainTable(systemMainTable);
				uts.setUserInfo(userInfo);
				uts.setMainTableColumn(smtc);
				//uts.setExtendTableColumn(null);
				uts.setIsDisplay(new Integer(1));
				uts.setOrder(++orderMax);
				getDaoSupport().insertOrUpdate(uts);
				getDaoSupport().evict(uts); //批量更新需立即清空缓存，以通过性能
			}
			
		}
		
//		DetachedCriteria dcExtendTable = DetachedCriteria.forClass(SystemMainTableExtColumn.class);
//		dcExtendTable.add(Restrictions.eq("systemMainTable", systemMainTable));
//		
//		List extendColumns = getDaoSupport().selectByCriteria(dcExtendTable);
//		Iterator itere = extendColumns.iterator();
//		while(itere.hasNext()){
//			//SystemMainTableExtColumn etc = (SystemMainTableExtColumn)itere.next();
//			
////			--------看此扩展字段是否已设置给当前用户-------------------
//			DetachedCriteria dcCurrentColumn = DetachedCriteria.forClass(UserTableSetting.class);
//			//dcCurrentColumn.add(Restrictions.eq("systemMainTable", systemMainTable));
//			dcCurrentColumn.add(Restrictions.eq("userInfo", userInfo));
//			dcCurrentColumn.add(Restrictions.eq("extendTableColumn", etc));
//			UserTableSetting utsExits = (UserTableSetting) getDaoSupport().uniqueResult(dcCurrentColumn);
//			if(utsExits==null){ //未设置，说明是新增的字段，默认把最大的排序号给它
//				UserTableSetting uts = new UserTableSetting();
//				uts.setSystemMainTable(systemMainTable);
//				uts.setUserInfo(userInfo);
//				uts.setMainTableColumn(null);
//				//uts.setExtendTableColumn(etc);
//				uts.setIsDisplay(new Integer(1));
//				uts.setOrder(++orderMax);
//				getDaoSupport().insertOrUpdate(uts);
//				getDaoSupport().evict(uts); //批量更新需立即清空缓存，以通过性能
//			}
//		}
	}

	public List selectUserColumnSet(UserInfo userInfo, SystemMainTable systemMainTable) {
		DetachedCriteria dc = DetachedCriteria.forClass(UserTableSetting.class);
		dc.add(Restrictions.eq("userInfo", userInfo));		
		dc.add(Restrictions.eq("systemMainTable", systemMainTable));	
		dc.setFetchMode("extendTableColumn", FetchMode.JOIN);
		dc.addOrder(Order.asc("order"));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}

	public List selectUserColumnSetsVisible(UserInfo userInfo, 
			SystemMainTable systemMainTable, Integer settingType) {
		/*DetachedCriteria dc = DetachedCriteria.forClass(UserTableSetting.class);
		dc.add(Restrictions.eq("settingType", settingType));
		dc.add(Restrictions.eq("userInfo", userInfo));		
		dc.add(Restrictions.eq("systemMainTable", systemMainTable));	
		dc.add(Restrictions.eq("isDisplay", new Integer(1)));
		dc.addOrder(Order.asc("order"));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;*/
		
		StringBuffer sf = new StringBuffer();
		sf.append("select uts from UserTableSetting uts ");
		sf.append("where uts.settingType=? ");
		sf.append("and uts.systemMainTable=? ");
		sf.append("and uts.isDisplay=1 ");
		sf.append("and uts.userInfo=? ");
		sf.append("order by uts.order ");
		List list = super.getDaoSupport().selectForList(sf.toString(), 
				new Object[]{settingType, systemMainTable, userInfo});
		return list;
		
	}

	public List selectUserExtendColumnSetVisible(UserInfo userInfo, 
			SystemMainTable systemMainTable, Integer settingType) {
		DetachedCriteria dc = DetachedCriteria.forClass(UserTableSetting.class);
		dc.add(Restrictions.eq("settingType", settingType));
		dc.add(Restrictions.eq("userInfo", userInfo));		
		dc.add(Restrictions.eq("systemMainTable", systemMainTable));	
		dc.add(Restrictions.eq("isDisplay", new Integer(1)));
		dc.add(Restrictions.isNotNull("extendTableColumn"));	
		dc.addOrder(Order.asc("order"));
		List list = getDaoSupport().selectByCriteria(dc);
		return list;
	}
	public UserTableSetting updateUserColumnSet(UserTableSetting uts) {
		getDaoSupport().update(uts);
		getDaoSupport().evict(uts);
		return uts;
	}

	public void deleteExtOptionByExtSelectColumnNum(Integer ExtSelectColumnNum) {
		getDaoSupport().executeUpdate("delete from ExtOption eo where eo.extSelectColumnNum=?", 
				new Integer[]{ExtSelectColumnNum});
		
	}

//	public void deleteExtOptionById(Long id) {
//		getDaoSupport().delete(ExtOption.class, id);
//		
//	}
//
//	public ExtOption insertOrUpdateExtOption(ExtOption smt) {
//		return (ExtOption) getDaoSupport().insertOrUpdate(smt);
//	}
//
//	public ExtOption selectExtOptionById(Long id) {
//		return (ExtOption) getDaoSupport().select(ExtOption.class, id);
//	}
//
//	public List selectExtOptionsByExtSelectColumnNum(Integer extSelectColumnNum) {
//		DetachedCriteria dc = DetachedCriteria.forClass(ExtOption.class);
//		dc.add(Restrictions.eq("extSelectColumnNum", extSelectColumnNum));
//		List list = getDaoSupport().selectByCriteria(dc);
//		return list;
//	}

//	public void deleteExtendData(Long mainTableId, SystemExtTable sysExtTable) {
//		String tableName = sysExtTable.getClassName();
//		getDaoSupport().executeUpdate("delete "+tableName+" emd where emd.mainTableRowID=?", new Object[]{mainTableId});
//		
//	}

	public List selectUserColumnSet(UserInfo userInfo, 
			SystemMainTable systemMainTable, Integer settingType) {
		/*DetachedCriteria dc = DetachedCriteria.forClass(UserTableSetting.class);
		dc.add(Restrictions.eq("userInfo", userInfo));
		dc.add(Restrictions.eq("systemMainTable", systemMainTable));
		dc.add(Restrictions.eq("settingType", settingType));
		dc.addOrder(Order.asc("order"));
		return getDaoSupport().selectByCriteria(dc);*/
		
		StringBuffer sf = new StringBuffer();
		sf.append("select uts from UserTableSetting uts ");
		sf.append("where uts.settingType=? ");
		sf.append("and uts.systemMainTable=? ");
		sf.append("and uts.userInfo=? ");
		sf.append("order by uts.order ");
		List list = super.getDaoSupport().selectForList(sf.toString(), 
				new Object[]{settingType, systemMainTable, userInfo});
		return list;
		
	}

	//添加复合查询时
//	public void insertRelateTableColumnToQuery(SystemTableQuery utq, SystemMainTable smt, Set loopedTable) {
//		loopedTable.add(smt);
//		Set smtColumns = smt.getColumns();
//		Iterator iter = smtColumns.iterator();
//		while(iter.hasNext()){ //主字段
//			SystemMainTableColumn column = (SystemMainTableColumn) iter.next();
//			if(column.getIsSearchItem()!=null&& column.getIsSearchItem().intValue()==1){
//				SystemTableQueryColumn utqc = new SystemTableQueryColumn();
//				utqc.setSystemTableQuery(utq);
//				utqc.setSystemMainTable(smt);
//				utqc.setMainTableColumn(column);
//				utqc.setExtendTableColumn(null);
//				if(column.isTextColumnType()){ //如果字段类型是文本类型，模糊搜索模式
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
//				}if(column.isNumberColumnType()){ //如果字段类型是下拉列表之类的存数字的类型
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}if(column.isCurrencyColumn()){ //如果字段的验证类型是货币，搜索时应指定区间
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//				}if(column.isDateColumn()){ //如果字段的验证类型是时间，搜索时应指定区间
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//				}
//				getDaoSupport().insertOrUpdate(utqc);
//				getDaoSupport().flush();
//				insertUserTableQueryColumnToUsers(utqc); //初始化系统表查询字段给所有用户
//				insertVisibleColumnToUsers(utqc); //初始化可见字段给所有用户
//				//对于复合查询的列表页面同样需要定制，因此需要给所有用户初始化
//				getDaoSupport().evict(utqc);
//			}
//			//获取关联实体
//			SystemMainTable foreignTable = column.getForeignTable();
//			if(foreignTable!=null){
//				if(!loopedTable.contains(foreignTable)){
//					insertRelateTableColumnToQuery(utq, foreignTable, loopedTable);
//				}
//				
//			}
//			
//		}
//		Set smtExtColumns = smt.getExtendColumns();
//		Iterator iterExt = smtExtColumns.iterator();
//		while(iterExt.hasNext()){ //扩展字段
//			SystemMainTableExtColumn column = (SystemMainTableExtColumn) iterExt.next();
//			if(column.getIsSearchItem()!=null&& column.getIsSearchItem().intValue()==1){
//				SystemTableQueryColumn utqc = new SystemTableQueryColumn();
//				utqc.setSystemTableQuery(utq);
//				utqc.setSystemMainTable(smt);
//				utqc.setMainTableColumn(null);
//				utqc.setExtendTableColumn(column);
//				if(column.isTextColumnType()){ //如果字段类型是文本类型，模糊搜索模式
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
//				}if(column.isNumberColumnType()){ //如果字段类型是下拉列表之类的存数字的类型
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}if(column.isCurrencyColumn()){ //如果字段的验证类型是货币，搜索时应指定区间
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//				}if(column.isDateColumn()){ //如果字段的验证类型是时间，搜索时应指定区间
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//				}
//				getDaoSupport().insertOrUpdate(utqc);
//				getDaoSupport().flush();
//				insertUserTableQueryColumnToUsers(utqc); //初始化系统表查询给所有用户
//				insertVisibleColumnToUsers(utqc); //初始化可见字段给所有用户
//				getDaoSupport().evict(utqc);
//			}
////			获取关联实体
//			SystemMainTable foreignTable = column.getForeignTable();
//			if(foreignTable!=null){
//				if(!loopedTable.contains(foreignTable)){
//					insertRelateTableColumnToQuery(utq, foreignTable, loopedTable);
//				}
//			}
//		}
//		
//	}
	//添加单表查询时
//	public void insertRelateTableColumnToQuery(SystemTableQuery utq, SystemMainTable smt) {
//		Set smtColumns = smt.getColumns();
//		Iterator iter = smtColumns.iterator();
//		while(iter.hasNext()){ //主字段
//			SystemMainTableColumn column = (SystemMainTableColumn) iter.next();
//			if(column.getIsSearchItem()!=null&& column.getIsSearchItem().intValue()==1){
//				SystemTableQueryColumn utqc = new SystemTableQueryColumn();
//				utqc.setSystemTableQuery(utq);
//				utqc.setSystemMainTable(smt);
//				utqc.setMainTableColumn(column);
//				utqc.setExtendTableColumn(null);
//				if(column.isTextColumnType()){ //如果字段类型是文本类型，模糊搜索模式
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
//				}if(column.isNumberColumnType()){ //如果字段类型是下拉列表之类的存数字的类型
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}if(column.isCurrencyColumn()){ //如果字段的验证类型是货币，搜索时应指定区间
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//				}if(column.isDateColumn()){ //如果字段的验证类型是时间，搜索时应指定区间
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//				}
//				getDaoSupport().insertOrUpdate(utqc);
//				getDaoSupport().flush();
//				insertUserTableQueryColumnToUsers(utqc); //初始化给所有用户
//				getDaoSupport().evict(utqc);
//			}
//		}
//		Set smtExtColumns = smt.getExtendColumns();
//		Iterator iterExt = smtExtColumns.iterator();
//		while(iterExt.hasNext()){ //扩展字段
//			SystemMainTableExtColumn column = (SystemMainTableExtColumn) iterExt.next();
//			if(column.getIsSearchItem()!=null&& column.getIsSearchItem().intValue()==1){
//				SystemTableQueryColumn utqc = new SystemTableQueryColumn();
//				utqc.setSystemTableQuery(utq);
//				utqc.setSystemMainTable(smt);
//				utqc.setMainTableColumn(null);
//				utqc.setExtendTableColumn(column);
//				if(column.isTextColumnType()){ //如果字段类型是文本类型，模糊搜索模式
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
//				}if(column.isNumberColumnType()){ //如果字段类型是下拉列表之类的存数字的类型
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
//				}if(column.isCurrencyColumn()){ //如果字段的验证类型是货币，搜索时应指定区间
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//				}if(column.isDateColumn()){ //如果字段的验证类型是时间，搜索时应指定区间
//					utqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
//				}
//				getDaoSupport().insertOrUpdate(utqc);
//				getDaoSupport().flush();
//				insertUserTableQueryColumnToUsers(utqc); //初始化系统表查询给所有用户
//				getDaoSupport().evict(utqc);
//			}
//		}
//	}

	/*public void insertUserTableQueryColumnToUsers(SystemTableQuery stq) {
		List users = getDaoSupport().selectAll(UserInfo.class);
		for(int i=0; i<users.size(); i++){
			UserInfo userInfo = (UserInfo) users.get(i);
			Set columns = stq.getQueryColumns();
			Iterator iteraC = columns.iterator();
			int order = 0;
			while(iteraC.hasNext()){
				SystemTableQueryColumn item = (SystemTableQueryColumn) iteraC.next();
				
				UserTableQueryColumn utqc = new UserTableQueryColumn();
				utqc.setUserInfo(userInfo);
				utqc.setSystemTableQueryColumn(item);
				utqc.setIsDisplay(Integer.valueOf(1));
				utqc.setOrder(Integer.valueOf(order));
				
				getDaoSupport().insertOrUpdate(utqc);
				getDaoSupport().flush();
			}
			
		
		}
		
	}*/
	//初始化系统表查询字段给所有用户
	public void insertUserTableQueryColumnToUsers(SystemTableQueryColumn stqc) {
		List users = getDaoSupport().selectAll(UserInfo.class);
		for(int i=0; i<users.size(); i++){
			UserInfo userInfo = (UserInfo) users.get(i);
				UserTableQueryColumn utqc = new UserTableQueryColumn();
				utqc.setUserInfo(userInfo);
				utqc.setSystemTableQuery(stqc.getSystemTableQuery());
				utqc.setSystemTableQueryColumn(stqc);//具体的系统查询字段
				utqc.setIsDisplay(Integer.valueOf(1));
				
				getDaoSupport().insertOrUpdate(utqc);
				getDaoSupport().flush();
		}
	}

	//复合查询用户可见字段初始化
	public void insertVisibleColumnToUsers(SystemTableQueryColumn stqc) {
		List users = getDaoSupport().selectAll(UserInfo.class);
		for(int i=0; i<users.size(); i++){
			UserInfo userInfo = (UserInfo) users.get(i);
			
			UserTableSetting uts = new UserTableSetting(); //用户可见字段
			
			Column column = stqc.getColumn();
//			if(column instanceof SystemMainTableColumn){
				uts.setMainTableColumn((SystemMainTableColumn) column);
//			}else if(column instanceof SystemMainTableExtColumn){
//				uts.setExtendTableColumn((SystemMainTableExtColumn) column);
//			}
//			
			uts.setUserInfo(userInfo);
			uts.setSystemMainTable(stqc.getSystemMainTable());
			uts.setSystemTableQuery(stqc.getSystemTableQuery());
			uts.setSettingType(UserTableSetting.MUL_QUERY); //复合查询字段的可见字段类型
			uts.setIsDisplay(Integer.valueOf(1));
			
			getDaoSupport().insertOrUpdate(uts);
			getDaoSupport().flush();
		}
	}

	public void deleteExtOptionById(Long id) {
		// TODO Auto-generated method stub
		
	}

	public List selectExtOptionsByExtSelectColumnNum(Integer ExtSelectColumnNum) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteExtendTableById(Long id) {
		// TODO Auto-generated method stub
		
	}

	public void deleteSysExtTableById(Long id) {
		// TODO Auto-generated method stub
		
	}

//	public SystemMainTableExtColumn insertOrUpdateExtendColumn(
//			SystemMainTableExtColumn extendTable) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public void insertRelateTableColumnToQuery(SystemTableQuery utq,
			SystemMainTable smt) {
		// TODO Auto-generated method stub
		
	}

	public void insertRelateTableColumnToQuery(SystemTableQuery utq,
			SystemMainTable smt, Set loopedTable) {
		// TODO Auto-generated method stub
		
	}

	public List selectSysExtTablesAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List selectExtendTableColumnsByMainTableId(Long mainTableId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List selectExtendTableColumnsByMainTableName(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List selectExtendTablesAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
