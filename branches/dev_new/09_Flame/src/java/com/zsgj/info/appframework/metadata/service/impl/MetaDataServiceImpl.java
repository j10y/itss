package com.zsgj.info.appframework.metadata.service.impl;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.metadata.dao.MetaDataDao;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableQuery;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.metadata.service.MetaDataService;
import com.zsgj.info.appframework.metadata.type.MetaType;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.UserInfo;

public class MetaDataServiceImpl extends BaseDao implements MetaDataService {
	private MetaDataDao metaDataDao = null;

	public MetaType findMetaTypeByName(String metaTypeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public SystemMainTable findSystemMainTableByClazz(Class clazz) {
		String className = clazz.getClass().getName();
		List list = this.getObjects(clazz, "className", className);
		return (SystemMainTable) list.iterator().next();
	}
	
	public Page findDataListByEntityAndParam(Class entityClazz, int pageNo, int pageSize, String orderProp, boolean isAsc, Criterion... criterions) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page findDataListByEntityAndParam(Class entityClazz, Map paramValues, int pageNo, int pageSize, String orderProp, boolean isAsc, Criterion... criterions) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findDataListByEntityAndParam(Class entityClazz, String orderProp, boolean isAsc, Criterion... criterions) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SystemMainTable> findSystemMainTableByModule(Module module) {
		Criteria c = createCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("module", module));
		c.addOrder(Order.asc("tableName"));
		List<SystemMainTable> list = c.list();
		return list;
	}

	public List findUserColumns(SystemMainTable sysmt, Integer userColumnType) {
		Criteria c = createCriteria(UserTableSetting.class);
		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("systemMainTable", sysmt));
		c.add(Restrictions.eq("settingType", userColumnType));
		c.addOrder(Order.asc("order"));
		if(userColumnType==UserTableSetting.LIST){
			c.add(Restrictions.eq("isDisplay", new Integer(1)));
		}
		List list = c.list();
		return list;
	}

	public void saveMainColumnToUserSettingByMainTable(String tableId) {
		metaDataDao.initSysMainTableAllColumnsToUser(tableId);
		
	}

	public SystemMainTable findSystemMainTable(String smtId) {
		SystemMainTable smt = null;
		smt = metaDataDao.selectMainTableById(Long.valueOf(smtId));
		return smt;
	}

	public List findSystemMainTablesAll() {
		List list = null;
		list = metaDataDao.selectMainTablesAll();
		return list;
	}

	public List findSystemMainTableHasCnId() {
		Criteria c = createCriteria(SystemMainTable.class);
		c.add(Restrictions.isNotNull("primaryKeyColumn"));
		List list = c.list();
		return list;
	}

	public void removeSystemMainTable(String smtId) {
		metaDataDao.deleteMainTableById(Long.valueOf(smtId));
	}

	public SystemMainTable saveSystemMainTable(SystemMainTable smt) {
		SystemMainTable result = null;
		result = metaDataDao.insertOrUpdateMainTable(smt);
		return result;
	}

	public SystemMainTable findSystemMainTableByName(String tableName) {
		SystemMainTable result = null;
		Criteria c = createCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("tableName", tableName));
		result = (SystemMainTable) c.uniqueResult();
		return result;
	}

	public void setMetaDataDao(MetaDataDao metaDataDao) {
		this.metaDataDao = metaDataDao;
	}

//	public SystemExtTable findSystemExtTable(String smtId) {
//		SystemExtTable smt = null;
//		smt = metaDataDao.selectSysExtTableById(Long.valueOf(smtId));
//		return smt;
//	}

	public List findSystemExtTablesAll() {
		List list = null;
		list = metaDataDao.selectSysExtTablesAll();
		return list;
	}

	public void removeSystemExtTable(String smtId) {
		metaDataDao.deleteSysExtTableById(Long.valueOf(smtId));
	}

//	public SystemExtTable saveSystemExtTable(SystemExtTable smt) {
//		SystemExtTable result = null;
//		result = metaDataDao.insertOrUpdateExtendColumnType(smt);
//		return result;
//	}

	public SystemMainTableColumn findSystemMainTableColumnByTableId(String tableDefId) {
		SystemMainTableColumn tf = null;
		tf = metaDataDao.selectMainColumnById(Long.valueOf(tableDefId));
		return tf;
	}


	public List findSystemMainTableColumns(SystemMainTable smt) {
		Criteria c = createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		
		List list = c.list();
		return list;
	}

	public List findSystemMainTableColumnsByTableName(String tableName) {
		Criteria c = createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("tableName", tableName));
		List list = c.list();
		return list;
	}

	public List findSystemMainTableOutPutColumnsByTableName(String tableName) {
		List list = null;
		list = metaDataDao.selectMainOutputColumnsByTableName(tableName);
		return list;
	}

	public List findSystemMainTableColumnsAll() {
		List list = null;
		list = metaDataDao.selectColumnsAll();
		return list;
	}

	public void saveSystemMainTableColumnsAfterReLoadAllColumn() {
		metaDataDao.initSysMainTableAllColumnsToUser();
	}

	public void saveSystemMainTableColumnsAfterReLoadNewColumn() {
		metaDataDao.reLoadSysMainTableNewColumns();
	}

	public void saveSystemMainTableColumnsAfterReLoadNewColumn(String tableName) {
		metaDataDao.reLoadSysMainTableNewColumnsByTableName(tableName);
	}

	public void saveSystemMainTableColumnsAfterReLoadMultiNewColumn() {
		metaDataDao.reLoadSysMainTableMultiNewColumnsToUsers();
		
	}

	public void removeMainColumn(String tableDefId) {
		metaDataDao.deleteMainColumnById(Long.valueOf(tableDefId));
	}

	public SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn tf) {
		SystemMainTableColumn result = null;
		result = metaDataDao.insertOrUpdateMainColumn(tf);
		return result;
	}

	public List findForeignTableEntitysAll(String tableName) {
		List list = null;
		list = metaDataDao.selectEntitysAllByClassName(tableName);
		return list;
	}
	
	public Object findForeignTableEntity(String className, Long id) {
		Object object = null;
		object = metaDataDao.selectEntityByClassNameAndId(className, id);
		return object;
	}

	public List findExtendColumnsByMainTableName(String mainTableName) {
		List list = null;
		list = metaDataDao.selectExtendTableColumnsByMainTableName(mainTableName);
		return list;
	}

	public List findExtendColumnsByMainTableId(String mainTableId) {
		List list = null;
		list = metaDataDao.selectExtendTableColumnsByMainTableId(Long.valueOf(mainTableId));
		return list;
	}

//	public ExtText findExtTextByMainTableRowIdAndColumnNum(Long mainId, Integer idx) {
//		ExtText result = null;
//		result = metaDataDao.selectExtTextByMainTableRowIdAndColumnNum(mainId, idx);
//		return result;
//	}
//
//	public ExtSelect findExtSelectByMainTableRowIdAndColumnNum(Long mainId, Integer idx) {
//		ExtSelect result = null;
//		result = metaDataDao.selectExtSelectByMainTableRowIdAndColumnNum(mainId, idx);
//		return result;
//	}
//
//	public ExtMetaData saveExtMetaData(ExtMetaData extMetaData) {
//		ExtMetaData result = null;
//		if(extMetaData instanceof ExtText){
//			result = metaDataDao.insertOrUpdateExtText((ExtText) extMetaData);
//		}
//		else if(extMetaData instanceof ExtSelect){
//			result = metaDataDao.insertOrUpdateExtSelect((ExtSelect) extMetaData);
//		}
//		else if(extMetaData instanceof ExtTextArea){
//			result = metaDataDao.insertOrUpdateExtTextArea((ExtTextArea) extMetaData);
//		}
//		return result;
//	}
//
//	public ExtTextArea findExtTextAreaByMainTableRowIdAndColumnNum(Long mainId, Integer columnNum) {
//		ExtTextArea result = null;
//		result = metaDataDao.selectExtTextAreaByMainTableRowIdAndColumnNum(mainId, columnNum);
//		return result;
//	}

	public List findColumnsBySystemTableId(String tableId) {
		List list = null;
		list = metaDataDao.selectMainColumnsByTableId(Long.valueOf(tableId));
		return list;
	}

	public List findColumnsHaveCnNameBySystemTableId(String tableId) {
		SystemMainTable smt = (SystemMainTable) get(SystemMainTable.class, tableId);
		Criteria dc = createCriteria(SystemMainTableColumn.class);
		dc.add(Restrictions.eq("systemMainTable", smt));
		dc.add(Restrictions.isNotNull("columnCnName"));
		//dc.add(Restrictions.isNotEmpty("columnCnName"));
		List list = dc.list();
		return list;
	}

//	public SystemMainTableExtColumn saveExtendColumns(SystemMainTableExtColumn extendTable) {
//		SystemMainTableExtColumn result = null;
//		result = metaDataDao.insertOrUpdateExtendColumn(extendTable);
//		return result;
//	}

//	public void removeExtendColumn(String extendColumnId, Integer extMetaDataColumnNum, SystemExtTable sysExtTable){
//		metaDataDao.deleteExtendTableById(Long.valueOf(extendColumnId));
//		metaDataDao.deleteExtMetaDataByColumnNumberAndType(extMetaDataColumnNum, sysExtTable);
//	}
//
//	public void removeExtendData(Integer columnNum, SystemExtTable sysExtTable) {
//		metaDataDao.deleteExtMetaDataByColumnNumberAndType(columnNum, sysExtTable);
//		
//	}

//	public SystemMainTableExtColumn findExtendColumn(String extendTableId) {
//		SystemMainTableExtColumn result = null;
//		result = metaDataDao.selectExtendTableById(Long.valueOf(extendTableId));
//		return result;
//	}

	public void removeUserExtendTableSettingById(Long uetsId) {
		// TODO Auto-generated method stub
		
	}

	public UserTableSetting findUserExtendTableSettingById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findUserExtendTableSettingsAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public UserTableSetting saveUserExtendTableSetting(UserTableSetting uets) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveNewColumnToUserTableSetting(UserInfo userInfo, SystemMainTable systemMainTable) {
		metaDataDao.insertNewColumnToUserColumnSet(userInfo, systemMainTable);
	}

	public List findUserTableSetting(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType) {
		List list = null;
		list = metaDataDao.selectUserColumnSet(userInfo, systemMainTable, settingType);
		return list;
	}

	public List findTableAllColumns(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType, boolean showVisibleOnly) {
		List list = null;
		if(showVisibleOnly==true){//只显示用户可见的字段，用于列表页面
			list = metaDataDao.selectUserColumnSetsVisible(userInfo, systemMainTable, settingType);
		}
		else{ //显示所有的字段给主实体的编辑界面
			list = metaDataDao.selectUserColumnSet(userInfo, systemMainTable, settingType);
		}
		return list;
	}

	public List findUserTableExtendColumnsVisible(UserInfo userInfo, SystemMainTable systemMainTable, Integer settingType) {
		List list = null;
		list = metaDataDao.selectUserExtendColumnSetVisible(userInfo, systemMainTable, settingType);
		return list;
	}

	public UserTableSetting saveUserTableSetting(UserTableSetting uts) {
		UserTableSetting result = null;
		result = metaDataDao.updateUserColumnSet(uts);
		return result;
	}

//	public ExtOption findExtOptionById(String id) {
//		ExtOption extOpt = null;
//		extOpt = metaDataDao.selectExtOptionById(Long.valueOf(id));
//		return extOpt;
//	}

	public List findExtOptionsByExtSelectColumnNum(Integer ExtSelectColumnNum) {
		List list = null;
		list = metaDataDao.selectExtOptionsByExtSelectColumnNum(ExtSelectColumnNum);
		return list;
	}

	public void removeExtOptionByExtSelectColumnNum(Integer ExtSelectColumnNum) {
		metaDataDao.deleteExtOptionByExtSelectColumnNum(ExtSelectColumnNum);
		
	}

	public void removeExtOptionById(String id) {
		metaDataDao.deleteExtOptionById(Long.valueOf(id));
		
	}

//	public ExtOption saveExtOption(ExtOption smt) {
//		ExtOption extopt = null;
//		extopt = metaDataDao.insertOrUpdateExtOption(smt);
//		return extopt;
//	}

//	public void removeExtendData(String mainTableId, SystemExtTable sysExtTable) {
//		metaDataDao.deleteExtendData(Long.valueOf(mainTableId), sysExtTable);
//		
//	}

	/**
	 * 保存系统主表查询，包括单表查询和复合查询。保存系统表查询后，保存系统字段查询。
	 */
	public SystemTableQuery saveUserTableQuery(SystemTableQuery utq) {
		boolean isInsert = utq.getId()==null;
		SystemTableQuery result = null;
		result = (SystemTableQuery) save(utq); 
		//插入主表查询后初始化所有的默认查询字段到主表查询字段表中，包括扩展字段和主字段
		int order = 0;
		if(isInsert && utq.getQueryType().intValue()==1){ //如果是单表查询
			SystemMainTable smt = utq.getSystemMainTable();
			metaDataDao.insertRelateTableColumnToQuery(utq, smt);
			
		}else if(isInsert && utq.getQueryType().intValue()==2){ //如果是复合查询
			SystemMainTable smt = utq.getSystemMainTable();
			Set loopedSet = new HashSet();
			metaDataDao.insertRelateTableColumnToQuery(utq, smt, loopedSet);

		}
		//metaDataDao.insertUserTableQueryColumnToUsers(utq);
		
		return result;
	}


	public List<SystemTableQuery> findSystemTableQuery(SystemMainTable smt) {
		List<SystemTableQuery> list = null;
		list = findBy(SystemTableQuery.class, "systemMainTable", smt);
		return list;
	}

	public List findSystemTableQueryColumn(SystemTableQuery utq) {
		List list = null; 
		list = getCriteria(SystemTableQueryColumn.class)
				.add(Restrictions.eq("systemTableQuery", utq))
				.addOrder(Order.asc("systemMainTable"))
				.list();
		return list;
	}

	public List findUserQueryColumn(SystemTableQuery stq, boolean onlyShowVisible) {
		List list = null;
		Criteria c = getCriteria(UserTableQueryColumn.class);
		c.addOrder(Order.asc("order"));
		if(onlyShowVisible){
			c.add(Restrictions.eq("isDisplay", Integer.valueOf(1)));
		}
 		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
 		c.add(Restrictions.eq("systemTableQuery", stq));
 		c.setFetchMode("systemTableQueryColumn", FetchMode.JOIN);
		list = c.list(); 		
		return list;
	}

	public SystemTableQuery findSystemTableSingleTableQuery(SystemMainTable smt) {
		SystemTableQuery result = null;
		List list = null;
		Criteria c = createCriteria(SystemTableQuery.class);
		c.add(Restrictions.eq("systemMainTable",smt));
		c.add(Restrictions.eq("queryType", SystemTableQuery.QUERY_TYPE_SINGLE));
		c.setCacheable(true);
		list = c.list();
		if(!list.isEmpty()){
			result = (SystemTableQuery) list.iterator().next();
		}
		return result;
	}

	
	public List findAllWithNullParentProp(String className, String parentPropName) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Criteria critea = getCriteria(clazz);
		critea.add(Restrictions.isNull(parentPropName));
		return critea.list();
	}

	public List findAllWithNotNullParentProp(String className, String parentPropName) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Criteria critea = getCriteria(clazz);
		critea.add(Restrictions.isNotNull(parentPropName));
		return critea.list();
	}

	public List findDataListByEntityAndParam(String className, Map params) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Criteria critea = getCriteria(clazz);
		Set keySet = params.keySet();
		Iterator keyItera = keySet.iterator();
		while(keyItera.hasNext()){
			String propName = (String) keyItera.next();
			Object propValue = params.get(propName);
			critea.add(Restrictions.eq(propName, propValue));
		}
		return critea.list();
	}

//	private List findUserSelectCorporations(UserInfo userInfo) {
////		Criteria dc = createCriteria(UserInfoCorporation.class);
////		dc.add(Restrictions.eq("userInfo", userInfo));
////		dc.setProjection(Projections.property("corporation"));
////		dc.setFetchMode("corporation", FetchMode.JOIN);
////		dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
////		List list = dc.list();
//		return null;
//	}
//	
//	//根据搜索条件搜索实体，返回结果为分页器
//	public Page findDataListByEntityAndParam(Class entityClazz, Map map, int pageNo, int pageSize, String orderProp, boolean isAsc) {
//		List<SystemTableQueryColumn> extSystemTableQueryColumns = new ArrayList<SystemTableQueryColumn>();
//		
//		Criteria critea = getCriteria(entityClazz);
//		critea.add(Restrictions.isNotNull("id"));
//
//		Set keySet = map.keySet();
//		Iterator keyItera = keySet.iterator();
//		while(keyItera.hasNext()){
//			Object columnValue = null;
//			Object keyObj = keyItera.next();
//			if(keyObj instanceof java.lang.String){ //如果key为字符串类型，则为搜索区间的条件
//				continue;
//			}
//			SystemTableQueryColumn sysTableQueryColumn = (SystemTableQueryColumn) keyObj;
//			Integer matchMode = sysTableQueryColumn.getMatchModeAsInt();
//			
//			if(sysTableQueryColumn.isSystemColumn()){
//				SystemMainTableColumn smtc = sysTableQueryColumn.getMainTableColumn();
//				SystemMainTableColumnType colType = smtc.getSystemMainTableColumnType();
//				
//				String propertyName = smtc.getPropertyName();
//				Object propertyValue = map.get(sysTableQueryColumn);
//				
//				SystemMainTable foreiTable = smtc.getForeignTable();
//				
//				if(colType.getColumnTypeName().equalsIgnoreCase("text")
//						||colType.getColumnTypeName().equalsIgnoreCase("textArea")){
//					columnValue = propertyValue;
//					
//					if(matchMode==null){
//						critea.add(Restrictions.eq("this."+propertyName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						if(propertyValue!=null){
//							critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.ANYWHERE));
//						}
//						
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						if(propertyValue!=null){
//							critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.EXACT));
//						}
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						if(propertyValue!=null){
//							critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.START));
//						}
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						if(propertyValue!=null){
//							critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.END));
//						}
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_BETWEEN)){ 
//						Object begin = map.get(propertyName+"Begin");
//						Object end = map.get(propertyName+"End");
//						ValidateType validateType = smtc.getValidateType();
//						String validateTypeName = validateType.getValidateTypeName();
//						if(begin!=null&& !begin.equals("")){
//							if(validateTypeName.equalsIgnoreCase("Currency")
//									||validateTypeName.equalsIgnoreCase("Double")
//									||validateTypeName.equalsIgnoreCase("Number") ){
//								begin = new Double(begin.toString());
//							}else if(validateTypeName.equalsIgnoreCase("Integer")){
//								begin = new Integer(begin.toString());
//							}
//							
//							critea.add(Restrictions.ge("this."+propertyName, begin));
//						}
//						if(end!=null && !end.equals("")){
//							if(validateTypeName.equalsIgnoreCase("Currency")
//									||validateTypeName.equalsIgnoreCase("Double")
//									||validateTypeName.equalsIgnoreCase("Number") ){
//								end = new Double(end.toString());
//							}else if(validateTypeName.equalsIgnoreCase("Integer")){
//								end = new Integer(end.toString());
//							}
//							critea.add(Restrictions.le("this."+propertyName, end));
//						}
//					}
//					
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("select")){
//					String foreiClassname = foreiTable.getClassName();
//					String foreiTableName = foreiTable.getTableName();
//					//取关联对象
//					columnValue = get(foreiClassname, (Serializable) Long.valueOf(propertyValue.toString()));
//					//指定关联对象的查询条件
//	
//					if(sysTableQueryColumn.isParentType()==true){
//						String childSetName = "child"+foreiTableName+"s";
//										Object parentObj =  get(foreiClassname, (Serializable) Long.valueOf(propertyValue.toString()));
//						BeanWrapper bwp = new BeanWrapperImpl(parentObj);
//						Set childSet = (Set) bwp.getPropertyValue(childSetName);
//
//						if(childSet!=null&& !childSet.isEmpty()){
//							critea.add(Restrictions.in("this."+propertyName, childSet));
//						}
//					}else{
//						critea.add(Restrictions.eq("this."+propertyName, columnValue));
//					}
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("yesNoSelect")){
//					columnValue = new Integer(propertyValue.toString());
//					critea.add(Restrictions.eq("this."+propertyName, columnValue));
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("dateText")){ //日期控件
//					String matchModeStr = sysTableQueryColumn.getMatchModeStr();
//					//日期区间搜索
//					if(matchModeStr!=null&& matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
//						Object begin = map.get(propertyName+"Begin");
//						Object end = map.get(propertyName+"End");
//						if(begin!=null&& !begin.equals("")){
//							Date dateBegin = DateUtil.convertStringToDate(begin.toString());
//							critea.add(Restrictions.ge("this."+propertyName, dateBegin));
//						}
//						if(end!=null&& !end.equals("")){
//							Date dateEnd = DateUtil.convertStringToDate(end.toString());
//							critea.add(Restrictions.le("this."+propertyName, dateEnd));
//						}
//					}else{ //日期准确搜索
//						columnValue = new Integer(propertyValue.toString());
//						critea.add(Restrictions.eq("this."+propertyName, columnValue));
//					}
//					
//				}
//
//				
//			}else if(sysTableQueryColumn.isExtendColumn()){//记录有哪些扩展字段的查询
//				extSystemTableQueryColumns.add(sysTableQueryColumn);
//			}
//
//		}
//		if(orderProp!=null&& !orderProp.equals("")){
//			if(isAsc==true){
//				critea.addOrder(Order.asc(orderProp));
//			}else {
//				critea.addOrder(Order.desc(orderProp));
//			}
//		}
//		Page page = this.pagedQuery(critea, pageNo, pageSize);
//		List list = (List) page.getResult();
//		//List list = critea.list(); //查询主数据结束
//		Iterator iter = list.iterator();
//		while(iter.hasNext()){ //遍历已查询到的主数据，再进一步按照扩展数据的输入查询条件筛选，如仍复合继续，否则主数据需要移除
//			BaseObject baseObj = (BaseObject) iter.next();
//			//查询扩展数据
//			for(int i=0; i<extSystemTableQueryColumns.size(); i++){ //遍历系统扩展查询字段
//				Object columnValue = null; //最终保存扩展字段搜索数据
//				SystemTableQueryColumn extStqc = extSystemTableQueryColumns.get(i);
//				Integer matchMode = extStqc.getMatchModeAsInt();
//				SystemMainTableExtColumn etc = extStqc.getExtendTableColumn();
//				SystemExtTable colType = etc.getSystemExtTable();
//				Integer extColumnNum = etc.getExtendTableColumnNum();
//				
//				String propertyName = etc.getPropertyName(); //扩展字段的属性名
//				String propertyValue = (String) map.get(extStqc); //表单传递过来的参数值，扩展数据值都是字符串类型
//				 
//			/*	SystemMainTable foreiTable = etc.getForeignTable();
//				String foreignTableName = foreiTable.getTableName();
//				String foreignClassName = foreiTable.getClassName();*/
//				
//				Criteria criteaMetaData = null;
//				String extColumnName = null;
//				if(colType.getTableName().equalsIgnoreCase("tExt_Text")){
//					
//					criteaMetaData = createCriteria(ExtText.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					if(matchMode==null){
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.ANYWHERE));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.EXACT));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.START));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.END));
//					}			
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//				}
//				else if(colType.getTableName().equalsIgnoreCase("tExt_TextArea")){
//
//					criteaMetaData = createCriteria(ExtTextArea.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					if(matchMode==null){
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.ANYWHERE));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.EXACT));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.START));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.END));
//					}			
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//					
//				}
//				else if(colType.getTableName().equalsIgnoreCase("tExt_Select")){
//					criteaMetaData = createCriteria(ExtSelect.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					
//					Integer extSelectType = etc.getExtSelectType(); //0源于主表，1源于扩展表，2源于自定义
//					if(extSelectType!=null && extSelectType.intValue()==0){//0源于主表
//						SystemMainTable foreiTable = etc.getForeignTable();
//						String foreignTableName = foreiTable.getTableName();
//						String foreignClassName = foreiTable.getClassName();
//
//						if(extStqc.isParentType()==true){//是二级父子列表
//							Set idSet = new HashSet();
//							String childSetName = "child"+foreignTableName+"s";
//											Object parentObj =  get(foreignClassName, (Serializable) Long.valueOf(propertyValue.toString()));
//							BeanWrapper bwp = new BeanWrapperImpl(parentObj);
//							Set childSet = (Set) bwp.getPropertyValue(childSetName);
//							Iterator iterCld = childSet.iterator();
//							while(iterCld.hasNext()){
//								BaseObject item = (BaseObject) iterCld.next();
//								Long itemId = item.getId();
//								idSet.add(itemId);
//							}
//							if(childSet!=null&& !childSet.isEmpty()){
//								critea.add(Restrictions.in(extColumnName, idSet));
//							}
//						}else{ //非二级父子类表
//							criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//						}
//					}
//					else if(extSelectType!=null && extSelectType.intValue()==2){ //源于自定义表
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//						
//					}
//					else if(extSelectType!=null && extSelectType.intValue()==1){ //源于扩展表
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}
//					
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//					
//				}
//			}
//		}
//
//		return page;
//	}

	//为解决方案单独写的一个方法
//	public Page findDataListByEntityAndParam(Class entityClazz, Map map, Map initParam, int pageNo, int pageSize, String orderProp, boolean isAsc) {
//		List<SystemTableQueryColumn> extSystemTableQueryColumns = new ArrayList<SystemTableQueryColumn>();
//		
//		Criteria critea = getCriteria(entityClazz);
//		critea.add(Restrictions.isNotNull("id"));
//		
//		if(initParam!=null){
//			Set keySetInitParam = initParam.keySet();//加初始条件
//			Iterator initParamIter = keySetInitParam.iterator();
//			while(initParamIter.hasNext()){
//				String paramName = (String) initParamIter.next();
//				Object paramValue = initParam.get(paramName);
//				critea.add(Restrictions.eq(paramName, paramValue));
//			}
//		}
//
//		Set keySet = map.keySet();
//		Iterator keyItera = keySet.iterator();
//		while(keyItera.hasNext()){
//			Object columnValue = null;
//			Object keyObj = keyItera.next();
//			if(keyObj instanceof java.lang.String){ //如果key为字符串类型，则为搜索区间的条件
//				continue;
//			}
//			SystemTableQueryColumn sysTableQueryColumn = (SystemTableQueryColumn) keyObj;
//			Integer matchMode = sysTableQueryColumn.getMatchModeAsInt();
//			
//			if(sysTableQueryColumn.isSystemColumn()){
//				SystemMainTableColumn smtc = sysTableQueryColumn.getMainTableColumn();
//				SystemMainTableColumnType colType = smtc.getSystemMainTableColumnType();
//				
//				String propertyName = smtc.getPropertyName();
//				Object propertyValue = map.get(sysTableQueryColumn);
//				
//				SystemMainTable foreiTable = smtc.getForeignTable();
//				
//				if(colType.getColumnTypeName().equalsIgnoreCase("text")
//						||colType.getColumnTypeName().equalsIgnoreCase("textArea")){
//					columnValue = propertyValue;
//					
//					if(matchMode==null){
//						critea.add(Restrictions.eq("this."+propertyName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.ANYWHERE));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.EXACT));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.START));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.END));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_BETWEEN)){ 
//						Object begin = map.get(propertyName+"Begin");
//						Object end = map.get(propertyName+"End");
//						ValidateType validateType = smtc.getValidateType();
//						String validateTypeName = validateType.getValidateTypeName();
//						if(begin!=null&& !begin.equals("")){
//							if(validateTypeName.equalsIgnoreCase("Currency")
//									||validateTypeName.equalsIgnoreCase("Double")
//									||validateTypeName.equalsIgnoreCase("Number") ){
//								begin = new Double(begin.toString());
//							}else if(validateTypeName.equalsIgnoreCase("Integer")){
//								begin = new Integer(begin.toString());
//							}
//							
//							critea.add(Restrictions.ge("this."+propertyName, begin));
//						}
//						if(end!=null && !end.equals("")){
//							if(validateTypeName.equalsIgnoreCase("Currency")
//									||validateTypeName.equalsIgnoreCase("Double")
//									||validateTypeName.equalsIgnoreCase("Number") ){
//								end = new Double(end.toString());
//							}else if(validateTypeName.equalsIgnoreCase("Integer")){
//								end = new Integer(end.toString());
//							}
//							critea.add(Restrictions.le("this."+propertyName, end));
//						}
//					}
//					
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("select")){
//					String foreiClassname = foreiTable.getClassName();
//					String foreiTableName = foreiTable.getTableName();
//					//取关联对象
//					columnValue = get(foreiClassname, (Serializable) Long.valueOf(propertyValue.toString()));
//					//指定关联对象的查询条件
//	
//					if(sysTableQueryColumn.isParentType()==true){
//						String childSetName = "child"+foreiTableName+"s";
//										Object parentObj =  get(foreiClassname, (Serializable) Long.valueOf(propertyValue.toString()));
//						BeanWrapper bwp = new BeanWrapperImpl(parentObj);
//						Set childSet = (Set) bwp.getPropertyValue(childSetName);
//
//						if(childSet!=null&& !childSet.isEmpty()){
//							critea.add(Restrictions.in("this."+propertyName, childSet));
//						}
//					}else{
//						critea.add(Restrictions.eq("this."+propertyName, columnValue));
//					}
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("yesNoSelect")){
//					columnValue = new Integer(propertyValue.toString());
//					critea.add(Restrictions.eq("this."+propertyName, columnValue));
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("dateText")){ //日期控件
//					String matchModeStr = sysTableQueryColumn.getMatchModeStr();
//					//日期区间搜索
//					if(matchModeStr!=null&& matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
//						Object begin = map.get(propertyName+"Begin");
//						Object end = map.get(propertyName+"End");
//						if(begin!=null&& !begin.equals("")){
//							Date dateBegin = DateUtil.convertStringToDate(begin.toString());
//							critea.add(Restrictions.ge("this."+propertyName, dateBegin));
//						}
//						if(end!=null&& !end.equals("")){
//							Date dateEnd = DateUtil.convertStringToDate(end.toString());
//							critea.add(Restrictions.le("this."+propertyName, dateEnd));
//						}
//					}else{ //日期准确搜索
//						columnValue = new Integer(propertyValue.toString());
//						critea.add(Restrictions.eq("this."+propertyName, columnValue));
//					}
//					
//				}
//
//				
//			}else if(sysTableQueryColumn.isExtendColumn()){//记录有哪些扩展字段的查询
//				extSystemTableQueryColumns.add(sysTableQueryColumn);
//			}
//
//		}
//		if(orderProp!=null&& !orderProp.equals("")){
//			if(isAsc==true){
//				critea.addOrder(Order.asc(orderProp));
//			}else {
//				critea.addOrder(Order.desc(orderProp));
//			}
//		}
//		Page page = null;
//		List list = null;
//		if(pageNo<=0 || pageSize<=0){//此时不分页，导出功能需要使用所有记录
//			list = critea.list();
//			page = new Page();
//			page.setResult(list);
//		}else{
//			page = this.pagedQuery(critea, pageNo, pageSize);
//			list = (List) page.getResult();
//		}
//		
//		//List list = critea.list(); //查询主数据结束
//		Iterator iter = list.iterator();
//		while(iter.hasNext()){ //遍历已查询到的主数据，再进一步按照扩展数据的输入查询条件筛选，如仍复合继续，否则主数据需要移除
//			BaseObject baseObj = (BaseObject) iter.next();
//			//查询扩展数据
//			for(int i=0; i<extSystemTableQueryColumns.size(); i++){ //遍历系统扩展查询字段
//				Object columnValue = null; //最终保存扩展字段搜索数据
//				SystemTableQueryColumn extStqc = extSystemTableQueryColumns.get(i);
//				Integer matchMode = extStqc.getMatchModeAsInt();
//				SystemMainTableExtColumn etc = extStqc.getExtendTableColumn();
//				SystemExtTable colType = etc.getSystemExtTable();
//				Integer extColumnNum = etc.getExtendTableColumnNum();
//				
//				String propertyName = etc.getPropertyName(); //扩展字段的属性名
//				String propertyValue = (String) map.get(extStqc); //表单传递过来的参数值，扩展数据值都是字符串类型
//				 
//			/*	SystemMainTable foreiTable = etc.getForeignTable();
//				String foreignTableName = foreiTable.getTableName();
//				String foreignClassName = foreiTable.getClassName();*/
//				
//				Criteria criteaMetaData = null;
//				String extColumnName = null;
//				if(colType.getTableName().equalsIgnoreCase("tExt_Text")){
//					
//					criteaMetaData = createCriteria(ExtText.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					if(matchMode==null){
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.ANYWHERE));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.EXACT));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.START));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.END));
//					}			
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//				}
//				else if(colType.getTableName().equalsIgnoreCase("tExt_TextArea")){
//
//					criteaMetaData = createCriteria(ExtTextArea.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					if(matchMode==null){
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.ANYWHERE));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.EXACT));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.START));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.END));
//					}			
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//					
//				}
//				else if(colType.getTableName().equalsIgnoreCase("tExt_Select")){
//					criteaMetaData = createCriteria(ExtSelect.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					
//					Integer extSelectType = etc.getExtSelectType(); //0源于主表，1源于扩展表，2源于自定义
//					if(extSelectType!=null && extSelectType.intValue()==0){//0源于主表
//						SystemMainTable foreiTable = etc.getForeignTable();
//						String foreignTableName = foreiTable.getTableName();
//						String foreignClassName = foreiTable.getClassName();
//
//						if(extStqc.isParentType()==true){//是二级父子列表
//							Set idSet = new HashSet();
//							String childSetName = "child"+foreignTableName+"s";
//											Object parentObj =  get(foreignClassName, (Serializable) Long.valueOf(propertyValue.toString()));
//							BeanWrapper bwp = new BeanWrapperImpl(parentObj);
//							Set childSet = (Set) bwp.getPropertyValue(childSetName);
//							Iterator iterCld = childSet.iterator();
//							while(iterCld.hasNext()){
//								BaseObject item = (BaseObject) iterCld.next();
//								Long itemId = item.getId();
//								idSet.add(itemId);
//							}
//							if(childSet!=null&& !childSet.isEmpty()){
//								critea.add(Restrictions.in(extColumnName, idSet));
//							}
//						}else{ //非二级父子类表
//							criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//						}
//					}
//					else if(extSelectType!=null && extSelectType.intValue()==2){ //源于自定义表
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//						
//					}
//					else if(extSelectType!=null && extSelectType.intValue()==1){ //源于扩展表
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}
//					
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//					
//				}
//			}
//		}
//
//		return page;
//	}

//	public List<BaseObject> findDataListByEntityAndParam(Class entityClazz, Map map, String orderProp, boolean isAsc) {
//		//临时存放系统表查询字段
//		List<SystemTableQueryColumn> extSystemTableQueryColumns = new ArrayList<SystemTableQueryColumn>();
//		
//		Criteria critea = getCriteria(entityClazz);
//		critea.add(Restrictions.isNotNull("id"));
//		Set keySet = map.keySet();
//		Iterator keyItera = keySet.iterator();
//		while(keyItera.hasNext()){
//			Object columnValue = null;
//			Object keyObj = keyItera.next();
//			if(keyObj instanceof java.lang.String){ //如果key为字符串类型，则为搜索区间的条件
//				continue;
//			}
//			SystemTableQueryColumn sysTableQueryColumn = (SystemTableQueryColumn) keyObj;
//			Integer matchMode = sysTableQueryColumn.getMatchModeAsInt();
//			
//			if(sysTableQueryColumn.isSystemColumn()){
//				SystemMainTableColumn smtc = sysTableQueryColumn.getMainTableColumn();
//				SystemMainTableColumnType colType = smtc.getSystemMainTableColumnType();
//				
//				String propertyName = smtc.getPropertyName();
//				Object propertyValue = map.get(sysTableQueryColumn);
//				
//				SystemMainTable foreiTable = smtc.getForeignTable();
//				
//				if(colType.getColumnTypeName().equalsIgnoreCase("text")
//						||colType.getColumnTypeName().equalsIgnoreCase("textArea")){
//					columnValue = propertyValue;
//					
//					if(matchMode==null){
//						critea.add(Restrictions.eq("this."+propertyName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.ANYWHERE));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.EXACT));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.START));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						critea.add(Restrictions.like("this."+propertyName, propertyValue.toString(), MatchMode.END));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_BETWEEN)){ 
//						Object begin = map.get(propertyName+"Begin");
//						Object end = map.get(propertyName+"End");
//						ValidateType validateType = smtc.getValidateType();
//						String validateTypeName = validateType.getValidateTypeName();
//						if(begin!=null&& !begin.equals("")){
//							if(validateTypeName.equalsIgnoreCase("Currency")
//									||validateTypeName.equalsIgnoreCase("Double")
//									||validateTypeName.equalsIgnoreCase("Number") ){
//								begin = new Double(begin.toString());
//							}else if(validateTypeName.equalsIgnoreCase("Integer")){
//								begin = new Integer(begin.toString());
//							}
//							
//							critea.add(Restrictions.ge("this."+propertyName, begin));
//						}
//						if(end!=null && !end.equals("")){
//							if(validateTypeName.equalsIgnoreCase("Currency")
//									||validateTypeName.equalsIgnoreCase("Double")
//									||validateTypeName.equalsIgnoreCase("Number") ){
//								end = new Double(end.toString());
//							}else if(validateTypeName.equalsIgnoreCase("Integer")){
//								end = new Integer(end.toString());
//							}
//							critea.add(Restrictions.le("this."+propertyName, end));
//						}
//					}
//					
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("select")){
//					String foreiClassname = foreiTable.getClassName();
//					String foreiTableName = foreiTable.getTableName();
//					//取关联对象
//					columnValue = get(foreiClassname, (Serializable) Long.valueOf(propertyValue.toString()));
//					//指定关联对象的查询条件
//	
//					if(sysTableQueryColumn.isParentType()==true){
//						String childSetName = "child"+foreiTableName+"s";
//										Object parentObj =  get(foreiClassname, (Serializable) Long.valueOf(propertyValue.toString()));
//						BeanWrapper bwp = new BeanWrapperImpl(parentObj);
//						Set childSet = (Set) bwp.getPropertyValue(childSetName);
//
//						if(childSet!=null&& !childSet.isEmpty()){
//							critea.add(Restrictions.in("this."+propertyName, childSet));
//						}
//					}else{
//						critea.add(Restrictions.eq("this."+propertyName, columnValue));
//					}
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("yesNoSelect")){
//					columnValue = new Integer(propertyValue.toString());
//					critea.add(Restrictions.eq("this."+propertyName, columnValue));
//				}
//				else if(colType.getColumnTypeName().equalsIgnoreCase("dateText")){ //日期控件
//					String matchModeStr = sysTableQueryColumn.getMatchModeStr();
//					//日期区间搜索
//					if(matchModeStr!=null&& matchModeStr.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
//						Object begin = map.get(propertyName+"Begin");
//						Object end = map.get(propertyName+"End");
//						if(begin!=null&& !begin.equals("")){
//							Date dateBegin = DateUtil.convertStringToDate(begin.toString());
//							critea.add(Restrictions.ge("this."+propertyName, dateBegin));
//						}
//						if(end!=null&& !end.equals("")){
//							Date dateEnd = DateUtil.convertStringToDate(end.toString());
//							critea.add(Restrictions.le("this."+propertyName, dateEnd));
//						}
//					}else{ //日期准确搜索
//						columnValue = new Integer(propertyValue.toString());
//						critea.add(Restrictions.eq("this."+propertyName, columnValue));
//					}
//					
//				}
//
//				
//			}else if(sysTableQueryColumn.isExtendColumn()){//记录有哪些扩展字段的查询
//				extSystemTableQueryColumns.add(sysTableQueryColumn);
//			}
//
//		}
//		if(orderProp!=null&& !orderProp.equals("")){
//			if(isAsc==true){
//				critea.addOrder(Order.asc(orderProp));
//			}else {
//				critea.addOrder(Order.desc(orderProp));
//			}
//		}
//		//Page page = this.pagedQuery(critea, 1, 2);
//		List list = critea.list(); //查询主数据结束
//		Iterator iter = list.iterator();
//		while(iter.hasNext()){ //遍历已查询到的主数据，再进一步按照扩展数据的输入查询条件筛选，如仍复合继续，否则主数据需要移除
//			BaseObject baseObj = (BaseObject) iter.next();
//			//查询扩展数据
//			for(int i=0; i<extSystemTableQueryColumns.size(); i++){ //遍历系统扩展查询字段
//				Object columnValue = null; //最终保存扩展字段搜索数据
//				SystemTableQueryColumn extStqc = extSystemTableQueryColumns.get(i);
//				Integer matchMode = extStqc.getMatchModeAsInt();
//				SystemMainTableExtColumn etc = extStqc.getExtendTableColumn();
//				SystemExtTable colType = etc.getSystemExtTable();
//				Integer extColumnNum = etc.getExtendTableColumnNum();
//				
//				String propertyName = etc.getPropertyName(); //扩展字段的属性名
//				String propertyValue = (String) map.get(extStqc); //表单传递过来的参数值，扩展数据值都是字符串类型
//				 
//			/*	SystemMainTable foreiTable = etc.getForeignTable();
//				String foreignTableName = foreiTable.getTableName();
//				String foreignClassName = foreiTable.getClassName();*/
//				
//				Criteria criteaMetaData = null;
//				String extColumnName = null;
//				if(colType.getTableName().equalsIgnoreCase("tExt_Text")){
//					
//					criteaMetaData = createCriteria(ExtText.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					if(matchMode==null){
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.ANYWHERE));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.EXACT));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.START));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.END));
//					}			
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//				}
//				else if(colType.getTableName().equalsIgnoreCase("tExt_TextArea")){
//
//					criteaMetaData = createCriteria(ExtTextArea.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					if(matchMode==null){
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.ANYWHERE));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.EXACT));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.START));
//					}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
//						criteaMetaData.add(Restrictions.like(extColumnName, propertyValue, MatchMode.END));
//					}			
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//					
//				}
//				else if(colType.getTableName().equalsIgnoreCase("tExt_Select")){
//					criteaMetaData = createCriteria(ExtSelect.class);
//					criteaMetaData.add(Restrictions.eq("mainTableRowID", baseObj.getId()));
//					criteaMetaData.add(Restrictions.eq("useColumnNum", extColumnNum));
//					extColumnName = "extColumn" + extColumnNum; //扩展字段名称
//					
//					Integer extSelectType = etc.getExtSelectType(); //0源于主表，1源于扩展表，2源于自定义
//					if(extSelectType!=null && extSelectType.intValue()==0){//0源于主表
//						SystemMainTable foreiTable = etc.getForeignTable();
//						String foreignTableName = foreiTable.getTableName();
//						String foreignClassName = foreiTable.getClassName();
//
//						if(extStqc.isParentType()==true){//是二级父子列表
//							Set idSet = new HashSet();
//							String childSetName = "child"+foreignTableName+"s";
//											Object parentObj =  get(foreignClassName, (Serializable) Long.valueOf(propertyValue.toString()));
//							BeanWrapper bwp = new BeanWrapperImpl(parentObj);
//							Set childSet = (Set) bwp.getPropertyValue(childSetName);
//							Iterator iterCld = childSet.iterator();
//							while(iterCld.hasNext()){
//								BaseObject item = (BaseObject) iterCld.next();
//								Long itemId = item.getId();
//								idSet.add(itemId);
//							}
//							if(childSet!=null&& !childSet.isEmpty()){
//								critea.add(Restrictions.in(extColumnName, idSet));
//							}
//						}else{ //非二级父子类表
//							criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//						}
//					}
//					else if(extSelectType!=null && extSelectType.intValue()==2){ //源于自定义表
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//						
//					}
//					else if(extSelectType!=null && extSelectType.intValue()==1){ //源于扩展表
//						criteaMetaData.add(Restrictions.eq(extColumnName, propertyValue));
//					}
//					
//					Object extMetaData = criteaMetaData.uniqueResult();
//					if(extMetaData==null){
//						iter.remove();
//					}
//					
//				}
//			}
//		}
//
//		return list;
//
//	}

	public UserTableQueryColumn saveUserTableQueryColumn(UserTableQueryColumn utqc) {
		UserTableQueryColumn result = null;
		result = (UserTableQueryColumn) save(utqc);
		return result;
	}

	public void removeSystemTableQuery(String sysTableQueryId) {
		SystemTableQuery stq = get(SystemTableQuery.class, Long.valueOf(sysTableQueryId));
		remove(stq);
		executeUpdate("delete from UserTableSetting uts where uts.systemTableQuery=?", new Object[]{stq});
	}

	public List findMultiQueryColumn(SystemMainTable smt, String sysTableQueryName) {
		
		Criteria cm = this.createCriteria(SystemTableQuery.class);
		cm.add(Restrictions.eq("systemMainTable", smt));
		cm.add(Restrictions.eq("queryName", sysTableQueryName));
		cm.add(Restrictions.eq("queryType", SystemTableQuery.QUERY_TYPE_MUTL));
		List querys = cm.list();
		
		SystemTableQuery stq = (SystemTableQuery) querys.iterator().next();
		
		Criteria c = this.createCriteria(UserTableQueryColumn.class);
		c.add(Restrictions.eq("systemTableQuery", stq));
		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("isDisplay", new Integer(1)));
		c.addOrder(Order.asc("order"));
		List queryColumns = c.list();
		return queryColumns;
	}

	public List findMultiQueryVisibleColumn(SystemMainTable smt, String sysTableQueryName) {
		Criteria cm = this.createCriteria(SystemTableQuery.class);
		cm.add(Restrictions.eq("systemMainTable", smt));
		cm.add(Restrictions.eq("queryName", sysTableQueryName));
		cm.add(Restrictions.eq("queryType", SystemTableQuery.QUERY_TYPE_MUTL));
		List querys = cm.list();
		
		SystemTableQuery stq = (SystemTableQuery) querys.iterator().next();
		
		Criteria c = this.createCriteria(UserTableSetting.class);
		c.add(Restrictions.eq("systemTableQuery", stq));
		c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("isDisplay", new Integer(1)));
		c.addOrder(Order.asc("order"));
		List queryColumns = c.list();
		return queryColumns;
	}

	public List findSystemMainTableColumnsHaveCnNameAll() {
		Criteria dc = createCriteria(SystemMainTableColumn.class);
		dc.add(Restrictions.isNotNull("columnCnName"));
		//dc.add(Restrictions.isNotEmpty("columnCnName"));
		List list = dc.list();
		return list;
	}

	public void initMainColumnOrderToUsers(String mainTableId) {
		SystemMainTable smt = findSystemMainTable(mainTableId);
		metaDataDao.initMainColumnOrderToUsers(smt);
		
	}

	public List findSysTableSettingColumns(SystemMainTable mainTable, Integer settingType) {
		Criteria c = super.createCriteria(SystemTableSetting.class);
		c.add(Restrictions.eq("systemMainTable", mainTable));
		c.add(Restrictions.eq("settingType", settingType));
		c.addOrder(Order.asc("order"));
		List list = c.list();
		return list;
	}

	public void initColumnsToSysTableSetting(SystemMainTable mainTable) {
		Set mColumns = mainTable.getColumns();
		Iterator iter = mColumns.iterator();
		int order = 0;
		while(iter.hasNext()){
			SystemMainTableColumn item = (SystemMainTableColumn) iter.next();
//			插入LIST类型的系统字段设置
			boolean exitsList = exitsSysTableSetBySettingType(mainTable, item, SystemTableSetting.LIST);
			if(!exitsList){
				SystemTableSetting stsList = new SystemTableSetting();
				stsList.setSystemMainTable(mainTable);
				stsList.setMainTableColumn(item);
				stsList.setSettingType(SystemTableSetting.LIST);
				stsList.setIsDisplay(Integer.valueOf(1));
				stsList.setOrder(new Integer(++order));
				this.save(stsList);
			}
//			插入INPUT类型的系统字段设置
			boolean exitsInput = exitsSysTableSetBySettingType(mainTable, item, SystemTableSetting.INPUT);
			if(!exitsInput){
				SystemTableSetting stsLInput = new SystemTableSetting();
				stsLInput.setSystemMainTable(mainTable);
				stsLInput.setMainTableColumn(item);
				stsLInput.setSettingType(SystemTableSetting.INPUT);
				stsLInput.setIsDisplay(Integer.valueOf(1));
				stsLInput.setOrder(new Integer(++order));
				this.save(stsLInput);
			}
		}
/*		
		//扩展字段
		Set extColumns = mainTable.getColumns();
		Iterator extIter = extColumns.iterator();
		while(iter.hasNext()){
			SystemMainTableExtColumn item = (SystemMainTableExtColumn) extIter.next();
//			插入LIST类型的系统字段设置
			boolean exitsList = exitsSysTableSetExtBySettingType(mainTable, item, SystemTableSetting.LIST);
			if(!exitsList){
				SystemTableSetting stsList = new SystemTableSetting();
				stsList.setSystemMainTable(mainTable);
				stsList.setExtendTableColumn(item);
				stsList.setSettingType(SystemTableSetting.LIST);
				stsList.setIsDisplay(Integer.valueOf(1));
				this.save(stsList);
			}
//			插入INPUT类型的系统字段设置
			boolean exitsInput = exitsSysTableSetExtBySettingType(mainTable, item, SystemTableSetting.INPUT);
			if(!exitsInput){
				SystemTableSetting stsLInput = new SystemTableSetting();
				stsLInput.setSystemMainTable(mainTable);
				stsLInput.setExtendTableColumn(item);
				stsLInput.setSettingType(SystemTableSetting.INPUT);
				stsLInput.setIsDisplay(Integer.valueOf(1));
				this.save(stsLInput);
			}
		}
		*/
		
	}

	private boolean exitsSysTableSetBySettingType(
												SystemMainTable mainTable, 
												SystemMainTableColumn item, 
												Integer settingType) {
		Criteria csts = createCriteria(SystemTableSetting.class);
		csts.add(Restrictions.eq("systemMainTable", mainTable));
		csts.add(Restrictions.eq("mainTableColumn", item));
		csts.add(Restrictions.eq("settingType", settingType));
		csts.setProjection(Projections.rowCount());
		Integer countList = (Integer) csts.uniqueResult();
		int countIntList = countList.intValue();
		if(countIntList>0) return true;
		return false;
	}
	
//	private boolean exitsSysTableSetExtBySettingType(
//			SystemMainTable mainTable, 
//			SystemMainTableExtColumn item, 
//			Integer settingType) {
//		Criteria csts = createCriteria(SystemTableSetting.class);
//		csts.add(Restrictions.eq("systemMainTable", mainTable));
//		csts.add(Restrictions.eq("extendTableColumn", item));
//		csts.add(Restrictions.eq("settingType", settingType));
//		csts.setProjection(Projections.rowCount());
//		Integer countList = (Integer) csts.uniqueResult();
//		int countIntList = countList.intValue();
//		if(countIntList>0) return true;
//		return false;
//	}

	public SystemTableSetting saveSystemTableSetting(SystemTableSetting sts) {
		this.save(sts);
		this.flush();
		return sts;
	}

	public List findUserInfosOrderByName() {
		Criteria c = createCriteria(UserInfo.class);
		c.addOrder(Order.asc("realName"));
		List list = c.list();
		return list;
	}

	public void saveForSynchrSysColumnToUserTableSetting(SystemMainTable smt, Integer settingType, boolean isAllUser, UserInfo assignedUser) {
		Criteria csts = createCriteria(SystemTableSetting.class);
		csts.add(Restrictions.eq("systemMainTable", smt));
		csts.add(Restrictions.eq("settingType", settingType));
		List list = csts.list();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			SystemTableSetting sts = (SystemTableSetting) iter.next();
			Column column = sts.getColumn();
			if(isAllUser){ // all user to set
				List users = this.findUserInfosOrderByName();
				for(int i=0; i<users.size(); i++){
					UserInfo user = (UserInfo) users.get(i);
					Criteria c = createCriteria(UserTableSetting.class);
					c.add(Restrictions.eq("userInfo", user));
					c.add(Restrictions.eq("systemMainTable", smt));
					c.add(Restrictions.eq("settingType", settingType));
//					if(column instanceof SystemMainTableColumn){
					c.add(Restrictions.eq("mainTableColumn", (SystemMainTableColumn)column));
//					}else if(column instanceof SystemMainTableExtColumn){
//						c.add(Restrictions.eq("extendTableColumn", (SystemMainTableExtColumn)column));
//					}
					Object object = c.uniqueResult();
					if(object!=null){
						UserTableSetting uts = (UserTableSetting) object;
						uts.setOrder(sts.getOrder());
						uts.setIsDisplay(sts.getIsDisplay());
						this.save(uts);// update
					}else{
						UserTableSetting uts = new UserTableSetting();
						uts.setUserInfo(user);
						uts.setSystemMainTable(smt);
						//if(column instanceof SystemMainTableColumn){
							uts.setMainTableColumn((SystemMainTableColumn)column);
//						}else if(column instanceof SystemMainTableExtColumn){
//							uts.setExtendTableColumn((SystemMainTableExtColumn)column);
//						}
						uts.setSettingType(settingType);
						uts.setIsDisplay(sts.getIsDisplay());
						uts.setOrder(uts.getOrder());
						this.save(uts); //insert 
						
					}
					
				}
			}//end all user
			else{ //synchroni to assigned user by parameter 'assignedUser'
				Criteria c = createCriteria(UserTableSetting.class);
				c.add(Restrictions.eq("userInfo", assignedUser));
				c.add(Restrictions.eq("systemMainTable", smt));
				c.add(Restrictions.eq("settingType", settingType));
				//if(column instanceof SystemMainTableColumn){
				c.add(Restrictions.eq("mainTableColumn", (SystemMainTableColumn)column));
//				}else if(column instanceof SystemMainTableExtColumn){
//					c.add(Restrictions.eq("extendTableColumn", (SystemMainTableExtColumn)column));
//				}
				Object object = c.uniqueResult();
				if(object!=null){
					UserTableSetting uts = (UserTableSetting) object;
					uts.setOrder(sts.getOrder());
					uts.setIsDisplay(sts.getIsDisplay());
					this.save(uts);// update
				}else{
					UserTableSetting uts = new UserTableSetting();
					uts.setUserInfo(assignedUser);
					uts.setSystemMainTable(smt);
//					if(column instanceof SystemMainTableColumn){
					uts.setMainTableColumn((SystemMainTableColumn)column);
//					}else if(column instanceof SystemMainTableExtColumn){
//						uts.setExtendTableColumn((SystemMainTableExtColumn)column);
//					}
					uts.setSettingType(settingType);
					uts.setIsDisplay(sts.getIsDisplay());
					uts.setOrder(uts.getOrder());
					this.save(uts); //insert 
					
				}
			}
		}
		
	}

	public void saveForSynchrSysQueryColumnToUser(SystemTableQuery stq, boolean isAllUser, UserInfo assignedUser) {
		Criteria csts = createCriteria(SystemTableQueryColumn.class);
		csts.add(Restrictions.eq("systemTableQuery", stq));
		List list = csts.list();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			SystemTableQueryColumn stqc = (SystemTableQueryColumn) iter.next();
			//判断是否是所有用户
			if(isAllUser){ // all user to set
				List users = this.findUserInfosOrderByName();
				for(int i=0; i<users.size(); i++){
					UserInfo user = (UserInfo) users.get(i);
					Criteria c = createCriteria(UserTableQueryColumn.class);
					c.add(Restrictions.eq("userInfo", user));
					c.add(Restrictions.eq("systemTableQuery", stq));
					c.add(Restrictions.eq("systemTableQueryColumn", stqc));
					c.setProjection(Projections.rowCount());
					Integer rowCount = (Integer) c.uniqueResult();
					if(rowCount.intValue()==0){
						UserTableQueryColumn newObj = new UserTableQueryColumn();
						newObj.setSystemTableQuery(stq);
						newObj.setSystemTableQueryColumn(stqc);
						newObj.setUserInfo(user);
						newObj.setIsDisplay(stqc.getIsDisplay());
						newObj.setOrder(stqc.getOrder());
						save(newObj);
					}else{//已经有记录
						c.setProjection(null);
						UserTableQueryColumn oldUtqc = (UserTableQueryColumn) c.uniqueResult();
						oldUtqc.setIsDisplay(stqc.getIsDisplay());
						oldUtqc.setOrder(stqc.getOrder());
						this.save(oldUtqc);
					}
				}
			}else{
				Criteria c = createCriteria(UserTableQueryColumn.class);
				c.add(Restrictions.eq("userInfo", assignedUser));
				c.add(Restrictions.eq("systemTableQuery", stq));
				c.add(Restrictions.eq("systemTableQueryColumn", stqc));
				c.setProjection(Projections.rowCount());
				Integer rowCount = (Integer) c.uniqueResult();
				if(rowCount.intValue()==0){
					UserTableQueryColumn newObj = new UserTableQueryColumn();
					newObj.setSystemTableQuery(stq);
					newObj.setSystemTableQueryColumn(stqc);
					newObj.setUserInfo(assignedUser);
					newObj.setIsDisplay(stqc.getIsDisplay());
					newObj.setOrder(stqc.getOrder());
					save(newObj);
				}else{//已经有记录
					c.setProjection(null);
					UserTableQueryColumn oldUtqc = (UserTableQueryColumn) c.uniqueResult();
					oldUtqc.setIsDisplay(stqc.getIsDisplay());
					oldUtqc.setOrder(stqc.getOrder());
					this.save(oldUtqc);
				}
			}
		}
	}

	public List findQueryColumnsByQuery(SystemTableQuery stq) {
		Criteria csts = createCriteria(SystemTableQueryColumn.class);
		csts.add(Restrictions.eq("systemTableQuery", stq));
		List list = csts.list();
		return list;
	}

	public SystemMainTableColumn findMainForeiColumn(SystemMainTable mainTable, SystemMainTable foreiTable) {
		Criteria c = createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", mainTable));
		c.add(Restrictions.eq("foreignTable", foreiTable));
		//isNullForeignColumn为null默认是主关联字段，为0为null关联字段即主关联字段
		c.add(Restrictions.or(
				Restrictions.isNull("isNullForeignColumn"), Restrictions.eq("isNullForeignColumn", new Integer(0))
		));
		Object o = c.uniqueResult();
		return (SystemMainTableColumn)o;

	}

	public List<BaseObject> findDataListByEntityAndParam(Class entityClazz,
			Map paramValues, String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page findDataListByEntityAndParam(Class entityClazz,
			Map paramValues, Map initParam, int pageNo, int pageSize,
			String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page findDataListByEntityAndParam(Class entityClazz,
			Map paramValues, int pageNo, int pageSize, String orderProp,
			boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
