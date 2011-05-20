package com.zsgj.info.appframework.metadata.impl;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.ColumnDataWrapper;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.QueryService;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.ExtOptionData;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.SystemTableQuery;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.metadata.service.MetaDataService;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainAndExtColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.metadata.service.UserColumnService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.DateUtil;

public class MetaDataManagerImpl implements MetaDataManager {

	private SystemMainTableService systemMainTableService;
	//private SystemMainColumnService systemMainColumnService;
	private UserColumnService userColumnService;
	private SystemColumnService systemColumnService;
	private QueryService queryService;
	private Service baseService;
	//private SystemExtColumnServcie systemExtColumnService;
	private SystemMainAndExtColumnService systemMainAndExtColumnService;

	public Map<Object, Object> genQueryParams(Class clazz, Map<String, String> requestParams) {
		
		MetaDataService ms = (MetaDataService)ContextHolder.getBean("metaDataService");
		Map<Object,Object> queryParamValue = new HashMap<Object,Object>();
		
		SystemMainTable smt = systemMainTableService.findSystemMainTableByClazz(clazz);
		
		SystemTableQuery stq = userColumnService.findSystemTableQuery(smt); //通过系统主表获取系统表查询
		//List list = ms.findUserQueryColumn(stq, true);
		//通过系统查询获取当前用户的所有查询字段
		List list = userColumnService.findUserQueryColumn(stq);
		for(int i=0; i<list.size(); i++){
			UserTableQueryColumn column = (UserTableQueryColumn) list.get(i);
			SystemTableQueryColumn sysTableQueryColumn = column.getSystemTableQueryColumn();
			SystemMainTableColumn smtc = sysTableQueryColumn.getMainTableColumn();
			if(sysTableQueryColumn.isSystemColumn()){
				SystemMainTableColumnType ct = smtc.getSystemMainTableColumnType();//主字段类型
				String columnTypeName = ct.getColumnTypeName();
				String propertyName = smtc.getPropertyName(); //搜索参数名称
				String propertyValue = null; //搜索参数的值
				//System.out.println(propertyName);
				//取搜索参数的值
//				if(queryParams.get(propertyName)!=null){
//					propertyValue = queryParams.get(propertyName);
//				}else{
//					continue;
//				}
				//如果搜索参数值不空，向queryParamValue中加入此查询参数值
				if(StringUtils.isNotBlank(requestParams.get(propertyName))){
					propertyValue = requestParams.get(propertyName);
					
					queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
				}else{
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//对于区域，当前属性必null
					if(matchMode!=null&& !matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						continue;
					}
					
				}
				if(columnTypeName.equalsIgnoreCase("text")){
					
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//日期区间搜索
					if(matchMode!=null&& matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						queryParamValue.put(sysTableQueryColumn, null);
						queryParamValue.put(propertyNameBegin, requestParams.get(propertyNameBegin));
						queryParamValue.put(propertyNameEnd, requestParams.get(propertyNameEnd));
					}else{ //此分支可以省略
						if(propertyValue!=null){
							queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
						}
					}

				}
				else if(ct.getColumnTypeName().equalsIgnoreCase("select")){//if select

					SystemMainTable fTable = column.getForeignTable();
					String fClassName = fTable.getClassName();
					SystemMainTableColumn fValueColumn = smtc.getForeignTableValueColumn();
					SystemMainTableColumn fParentColumn = smtc.getForeignTableParentColumn();
					
					//取类别属性（如tradeWay
					//propertyValue = (String) queryParams.get(propertyName);
					//queryParamValue.put(sysTableQueryColumn, propertyValue);//加入类别值或子列表值
					if(fParentColumn!=null){
						String parentPropertyName = "parent"+propertyName; //父字段属性名称
						String parentPropertyValue = null; //存放父字段属性的值
						if(requestParams.get(parentPropertyName)!=null){ //父字段是否有查询参数值
							parentPropertyValue = requestParams.get(parentPropertyName);
						}
						//如果子类别参数空但父参数不空，向queryParamValue中加入此查询参数值
						if(StringUtils.isNotBlank(parentPropertyValue)&& StringUtils.isBlank(propertyValue)){
							Long parentPropertyLongValue = Long.valueOf(parentPropertyValue);
							
							BaseObject pObject = (BaseObject) ms.findForeignTableEntity(fClassName, parentPropertyLongValue);
							queryParamValue.put(sysTableQueryColumn, pObject);
							//queryParamValue.put(sysTableQueryColumn, parentPropertyValue.trim());
						}
						//queryParamValue.put(parentPropertyName, parentPropertyValue);
					}

				}//end select
				else if(ct.getColumnTypeName().equalsIgnoreCase("dateText")){ //如果为日期类型字段，搜索条件需要显示搜索区域
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//日期区间搜索
					if(matchMode!=null&& matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						if(requestParams.get(propertyNameBegin)!=null&&requestParams.get(propertyNameBegin).length()>0){
							queryParamValue.put(sysTableQueryColumn, requestParams.get(propertyName));
							queryParamValue.put(propertyNameBegin, requestParams.get(propertyNameBegin));
						}
						if(requestParams.get(propertyNameEnd)!=null&&requestParams.get(propertyNameEnd).length()>0){
							queryParamValue.put(sysTableQueryColumn, requestParams.get(propertyName));
							queryParamValue.put(propertyNameEnd, requestParams.get(propertyNameEnd));
						}
					}else{
						//后续增加
						String queryColumnDateValue = requestParams.get(propertyName);
						queryParamValue.put(propertyName, queryColumnDateValue);
						
					}
					
				}
			}else if(sysTableQueryColumn.isExtendColumn()){
				//SystemMainTableExtColumn smtec=sysTableQueryColumn.getExtendTableColumn();
				SystemMainTableColumnType ct = smtc.getSystemMainTableColumnType();//扩展字段类型
				String columnTypeName = ct.getColumnTypeName();
				String propertyName = smtc.getPropertyName(); //搜索参数名称
				String propertyValue = null; //搜索参数的值
				if(StringUtils.isNotBlank(requestParams.get(propertyName))){
					propertyValue = requestParams.get(propertyName);
					
					queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
				}else{
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//对于区域，当前属性必null
					if(matchMode!=null&& !matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						continue;
					}
					
				}	
				if(columnTypeName.equalsIgnoreCase("text")){
					
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//日期区间搜索
					if(matchMode!=null&& matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						queryParamValue.put(sysTableQueryColumn, null);
						queryParamValue.put(propertyNameBegin, requestParams.get(propertyNameBegin));
						queryParamValue.put(propertyNameEnd, requestParams.get(propertyNameEnd));
					}else{ //此分支可以省略
						if(propertyValue!=null){
						queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
						}
					}

				}else if(columnTypeName.equalsIgnoreCase("textArea")){
					
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//日期区间搜索
					if(matchMode!=null&& matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						queryParamValue.put(sysTableQueryColumn, null);
						queryParamValue.put(propertyNameBegin, requestParams.get(propertyNameBegin));
						queryParamValue.put(propertyNameEnd, requestParams.get(propertyNameEnd));
					}else{ //此分支可以省略
						if(propertyValue!=null){
							queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
						}
					}

				}else if(columnTypeName.equalsIgnoreCase("select")||columnTypeName.equalsIgnoreCase("radio")){//if select
					if(propertyValue!=null){
						queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
					}

				}//end select
				else if(ct.getColumnTypeName().equalsIgnoreCase("dateText")){ //如果为日期类型字段，搜索条件需要显示搜索区域
					String propertyNameBegin = propertyName+"Begin";
					String propertyNameEnd = propertyName+"End";
					if(requestParams.get(propertyNameBegin)!=null&&requestParams.get(propertyNameBegin).length()>0){
						queryParamValue.put(sysTableQueryColumn,  requestParams.get(propertyName));
						queryParamValue.put(propertyNameBegin, requestParams.get(propertyNameBegin));
					}
					if(requestParams.get(propertyNameEnd)!=null&&requestParams.get(propertyNameEnd).length()>0){
						queryParamValue.put(sysTableQueryColumn,  requestParams.get(propertyName));
						queryParamValue.put(propertyNameEnd, requestParams.get(propertyNameEnd));
					}
				}
			}
			
		}
		return queryParamValue;
		
	}

	/**
	 * 最后确定的综合条件查询方法
	 */
	public Page query(Class clazz, Map<String, Object> requestParams, 
						int pageNo, int pageSize, String orderProp, boolean isAsc) {
		//先转换参数格式到实体属性格式
		Map<String, Object> queryParams = this.genPropParams(clazz, requestParams);
		//调用查询服务的提供params属性查询的方法
		Page page = queryService.queryByParams(clazz, queryParams, null, pageNo, pageSize, orderProp, isAsc);
		return page;
	}
	
	public List query(Class clazz, Map<String, Object> requestParams, String orderProp, boolean isAsc) {
		//先转换参数格式到实体属性格式
		Map<String, Object> queryParams = this.genPropParams(clazz, requestParams);
		//调用查询服务的提供params属性查询的方法
		List list = queryService.queryByParams(clazz, queryParams, null, orderProp, isAsc);
		return list;
	}


	@SuppressWarnings("unchecked")
	public Map<String, Object> genPropParams(Class clazz, Map<String, Object> requestParams) {
		MetaDataService ms = (MetaDataService)ContextHolder.getBean("metaDataService");
		Map<String,Object> queryParamValue = new HashMap<String,Object>();
		
		SystemMainTable smt = systemMainTableService.findSystemMainTableByClazz(clazz);
		
		SystemTableQuery stq = userColumnService.findSystemTableQuery(smt); //通过系统主表获取系统表查询
		//List list = ms.findUserQueryColumn(stq, true);
		//通过系统查询获取当前用户的所有查询字段
		List<Column> list = systemColumnService.findSystemTableColumns(smt);
		for(int i=0; i<list.size(); i++){
			Column column = (Column) list.get(i);
			SystemMainTableColumn columnMainOrExt=(SystemMainTableColumn)column;
			if(columnMainOrExt.getIsExtColumn()==SystemMainTableColumn.isMain){
				boolean isMatchModeBetween = false;
				SystemMainTableColumnType ct = column.getSystemMainTableColumnType();//主字段类型
				PropertyType pt = column.getPropertyType();
				String columnTypeName = ct.getColumnTypeName();
				String propertyTypeName = pt.getPropertyTypeName();
				String propertyName = column.getPropertyName(); //搜索参数名称
				//System.out.println("propertyName: "+ propertyName);
				//System.out.println("propertyTypeName: "+ propertyName);
				
				Object columnQueryValue = requestParams.get(propertyName);
				//System.out.println("columnQueryValue: "+ columnQueryValue);
				if(columnQueryValue!=null&& !columnQueryValue.toString().equals("")){
					//System.out.println("dd");
					if(propertyTypeName.equalsIgnoreCase("Long")){//属性类型是Long
						if(columnQueryValue instanceof java.lang.String){
							Long queryParamValueLong = Long.valueOf(columnQueryValue.toString());
							queryParamValue.put(propertyName, queryParamValueLong);
						}else if(columnQueryValue instanceof java.lang.Integer){
							Long queryParamValueLong = Long.valueOf(columnQueryValue.toString());
							queryParamValue.put(propertyName, queryParamValueLong);
						}else if(columnQueryValue instanceof java.lang.Long){
							Long queryParamValueLong = Long.valueOf(columnQueryValue.toString());
							queryParamValue.put(propertyName, queryParamValueLong);
						}
					}
					else if(propertyTypeName.equalsIgnoreCase("Integer")){//属性类型是Integer
						if(columnQueryValue instanceof java.lang.String[]){
							Set<Integer> paramValues = new HashSet<Integer>();
							String[] valueStrings = (String[]) columnQueryValue;
							for(String item: valueStrings){
								if(StringUtils.isNotBlank(item)){
									paramValues.add(Integer.valueOf(item));
								}	
							}
							queryParamValue.put(propertyName, paramValues);
						}else if(columnQueryValue instanceof java.lang.Integer[]){
							Set<Integer> paramValues = new HashSet<Integer>();
							Integer[] valueInts = (Integer[]) columnQueryValue;
							for(Integer item: valueInts){
								if(item!=null){
									paramValues.add(Integer.valueOf(item));
								}	
							}
							queryParamValue.put(propertyName, paramValues);
						}else{
							if(columnQueryValue.toString().indexOf("_")!=-1){
								Set<Integer> paramValues = new HashSet<Integer>();
								String propertyValues = columnQueryValue.toString();
								String[] propValues = propertyValues.split("_");
								for(String propValue : propValues){
									paramValues.add(Integer.valueOf(propValue));
								}
								queryParamValue.put(propertyName, paramValues);
							
							}else{
								Integer queryParamValueInteger = Integer.valueOf(columnQueryValue.toString());
								queryParamValue.put(propertyName, queryParamValueInteger);
							}
							
						}
						
					}
					else if(propertyTypeName.equalsIgnoreCase("Double")){//属性类型是Double
						Double queryParamValueDouble = Double.valueOf(columnQueryValue.toString());
						queryParamValue.put(propertyName, queryParamValueDouble);
					}
					else if(propertyTypeName.equalsIgnoreCase("Date")){//属性类型是Date
						if(columnQueryValue instanceof java.lang.String){
							String columnQueryValueString = (String) columnQueryValue;
							Date queryParamValueDate = DateUtil.convertStringToDate(columnQueryValueString);
							queryParamValue.put(propertyName, queryParamValueDate);
						}else{
							queryParamValue.put(propertyName, columnQueryValue);
						}
					}
					else if(propertyTypeName.equalsIgnoreCase("String")){//属性类型是String
						if(columnQueryValue instanceof java.lang.String[]){
							Set<String> paramValues = new HashSet<String>();
							String[] valueStrings = (String[]) columnQueryValue;
							for(String item: valueStrings){
								if(StringUtils.isNotBlank(item)){
									paramValues.add(item);
								}	
							}
						}else if(columnQueryValue instanceof java.lang.Integer[]){
							Set<String> paramValues = new HashSet<String>();
							Integer[] valueInts = (Integer[]) columnQueryValue;
							for(Integer item: valueInts){
								if(item!=null){
									paramValues.add(String.valueOf(item));
								}	
							}
						}else{
							String columnQueryValueString = String.valueOf(columnQueryValue);
							queryParamValue.put(propertyName, columnQueryValueString);
						}
						
					}
					else if(propertyTypeName.equalsIgnoreCase("BaseObject")){//属性类型是String
						if(columnQueryValue instanceof BaseObject){
							queryParamValue.put(propertyName, columnQueryValue);
							
						}else if(columnQueryValue instanceof java.lang.String){
							
							if(columnQueryValue.toString().indexOf("_")==-1){ //如果对象id没有下划线
								Long queryParamValueLong = Long.valueOf(columnQueryValue.toString());
								SystemMainTable foreignTable = column.getForeignTable();
								if(foreignTable==null){
									throw new ServiceException("请给当前对象的关联实体选择外键关联表");
								}
								String fClassName = foreignTable.getClassName();
								Class clazzObject = null;
								try {
									clazzObject = Class.forName(fClassName);
								} catch (ClassNotFoundException e1) {
									e1.printStackTrace();
								}
								Object fObject = baseService.find(clazzObject,queryParamValueLong.toString());
								queryParamValue.put(propertyName, fObject);
							}else{	//对象id中有_，也就是多个下划线间隔，表示或
								Set<Long> paramValues = new HashSet<Long>();
								String propertyValues = columnQueryValue.toString();
								String[] propValues = propertyValues.split("_");
								for(String propValue : propValues){
									//begin
									Long queryParamValueLong = Long.valueOf(propValue.toString());
//									SystemMainTable foreignTable = column.getForeignTable();
//									if(foreignTable==null){
//										throw new ServiceException("请给当前对象的关联实体选择外键关联表");
//									}
//									String fClassName = foreignTable.getClassName();
//									Class clazzObject = null;
//									try {
//										clazzObject = Class.forName(fClassName);
//									} catch (ClassNotFoundException e1) {
//										e1.printStackTrace();
//									}
//									Object fObject = baseService.find(clazzObject,queryParamValueLong.toString());
									//end
									paramValues.add(queryParamValueLong);
								}
								queryParamValue.put(propertyName, paramValues);
							}
							
							
						}else if(columnQueryValue instanceof java.lang.Integer){
							Long queryParamValueLong = Long.valueOf(columnQueryValue.toString());
							SystemMainTable foreignTable = column.getForeignTable();
							if(foreignTable==null){
								throw new ServiceException("请给当前对象的关联实体选择外键关联表");
							}
							String fClassName = foreignTable.getClassName();
							Class clazzObject = null;
							try {
								clazzObject = Class.forName(fClassName);
							} catch (ClassNotFoundException e1) {
								e1.printStackTrace();
							}
							Object fObject = baseService.find(clazzObject,queryParamValueLong.toString());
							queryParamValue.put(propertyName, fObject);
						}else if(columnQueryValue instanceof java.lang.Long){
							Long queryParamValueLong = Long.valueOf(columnQueryValue.toString());
							SystemMainTable foreignTable = column.getForeignTable();
							if(foreignTable==null){
								throw new ServiceException("请给当前对象的关联实体选择外键关联表");
							}
							String fClassName = foreignTable.getClassName();
							Class clazzObject = null;
							try {
								clazzObject = Class.forName(fClassName);
							} catch (ClassNotFoundException e1) {
								e1.printStackTrace();
							}
							Object fObject = baseService.find(clazzObject,queryParamValueLong.toString());
							queryParamValue.put(propertyName, fObject);
						}
						
					}//baseObject
					
				}else{
					String propertyNameBegin = propertyName+"Begin";
					String propertyNameEnd = propertyName+"End";
					Object queryParamValueBegin = requestParams.get(propertyNameBegin);
					Object queryParamValueEnd = requestParams.get(propertyNameEnd);
					
					if(queryParamValueBegin!=null&& !queryParamValueBegin.toString().equals("")){
						isMatchModeBetween = true;
						
						if(propertyTypeName.equalsIgnoreCase("Date")){
							if(queryParamValueBegin instanceof java.util.Date){
								queryParamValue.put(propertyNameBegin, queryParamValueBegin);
							}else if(queryParamValueBegin instanceof java.lang.String){
								Date queryParamValueBeginDate = DateUtil.convertStringToDate(queryParamValueBegin.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueBeginDate);
							}else{
								throw new ServiceException("起始日期(年月日)类型字段格式有误");
							}
						}else if(propertyTypeName.equalsIgnoreCase("Double")){//货币
							if(queryParamValueBegin instanceof java.lang.String){
								Double queryParamValueBeginDouble = Double.valueOf(queryParamValueBegin.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueBeginDouble);
							}else if(queryParamValueBegin instanceof java.lang.Integer){
								Double queryParamValueBeginDouble = Double.valueOf(queryParamValueBegin.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueBeginDouble);
							}else if(queryParamValueBegin instanceof java.lang.Long){
								Double queryParamValueBeginDouble = Double.valueOf(queryParamValueBegin.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueBeginDouble);
							}else{
								throw new ServiceException("货币类型字段格式有误");
							}
						}else{
							throw new ServiceException("非日期或货币字段不允许使用区域查询");
						}
					
					}
					if(queryParamValueEnd!=null&& !queryParamValueEnd.toString().equals("")){
						isMatchModeBetween = true;
						
						if(propertyTypeName.equalsIgnoreCase("Date")){
							if(queryParamValueEnd instanceof java.util.Date){
								queryParamValue.put(propertyNameEnd, queryParamValueEnd);
							}else if(queryParamValueEnd instanceof java.lang.String){
								Date queryParamValueEndDate = DateUtil.convertStringToDate(queryParamValueEnd.toString());
								queryParamValue.put(propertyNameEnd, queryParamValueEndDate);
							}else{
								throw new ServiceException("终止日期(年月日)类型字段格式有误");
							}
						}else if(propertyTypeName.equalsIgnoreCase("Double")){//货币
							if(queryParamValueEnd instanceof java.lang.String){
								Double queryParamValueEndDouble = Double.valueOf(queryParamValueEnd.toString());
								queryParamValue.put(propertyNameEnd, queryParamValueEndDouble);
							}else if(queryParamValueEnd instanceof java.lang.Integer){
								Double queryParamValueEndDouble = Double.valueOf(queryParamValueEnd.toString());
								queryParamValue.put(propertyNameEnd, queryParamValueEndDouble);
							}else if(queryParamValueEnd instanceof java.lang.Long){
								Double queryParamValueEndDouble = Double.valueOf(queryParamValueEnd.toString());
								queryParamValue.put(propertyNameEnd, queryParamValueEndDouble);
							}else{
								throw new ServiceException("货币类型字段格式有误");
							}
						}else{
							throw new ServiceException("非日期或货币字段不允许使用区域查询");
						}
					
					}
				}
			}
			else if(columnMainOrExt.getIsExtColumn()==SystemMainTableColumn.isExt){
				SystemMainTableColumnType ct = column.getSystemMainTableColumnType();//主字段类型
				PropertyType pt = column.getPropertyType();
				String columnTypeName = ct.getColumnTypeName();
				//String propertyTypeName = pt.getPropertyTypeName();
				String propertyName = column.getPropertyName(); //搜索参数名称
				//String propertyValue = null; //搜索参数的值
				boolean isMatchModeBetween = false;
				
				Object columnQueryValue = requestParams.get(propertyName);
				if(columnQueryValue!=null&& !columnQueryValue.toString().equals("")){
					String columnQueryValueString = String.valueOf(columnQueryValue.toString());
					queryParamValue.put(propertyName, columnQueryValueString);					
				}else{
					String propertyNameBegin = propertyName+"Begin";
					String propertyNameEnd = propertyName+"End";
					Object queryParamValueBegin = requestParams.get(propertyNameBegin);
					Object queryParamValueEnd = requestParams.get(propertyNameEnd);
					
					if(queryParamValueBegin!=null&& !queryParamValueBegin.toString().equals("")){
						isMatchModeBetween = true;
						
						if(columnTypeName.equalsIgnoreCase("dateText")){
							if(queryParamValueBegin instanceof java.util.Date){
								queryParamValue.put(propertyNameBegin, queryParamValueBegin);
							}else if(queryParamValueBegin instanceof java.lang.String){
								Date queryParamValueBeginDate = DateUtil.convertStringToDate(queryParamValueBegin.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueBeginDate);
							}else{
								throw new ServiceException("起始日期(年月日)类型字段格式有误");
							}
						}else if(columnTypeName.equalsIgnoreCase("text")){//货币
							if(queryParamValueBegin instanceof java.lang.String){
								Double queryParamValueBeginDouble = Double.valueOf(queryParamValueBegin.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueBeginDouble);
							}else if(queryParamValueBegin instanceof java.lang.Integer){
								Double queryParamValueBeginDouble = Double.valueOf(queryParamValueBegin.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueBeginDouble);
							}else if(queryParamValueBegin instanceof java.lang.Long){
								Double queryParamValueBeginDouble = Double.valueOf(queryParamValueBegin.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueBeginDouble);
							}else{
								throw new ServiceException("货币类型字段格式有误");
							}
						}else{
							throw new ServiceException("非日期或货币字段不允许使用区域查询");
						}
					
					}
					if(queryParamValueEnd!=null&& !queryParamValueEnd.toString().equals("")){
						isMatchModeBetween = true;
						
						if(columnTypeName.equalsIgnoreCase("dateText")){
							if(queryParamValueEnd instanceof java.util.Date){
								queryParamValue.put(propertyNameBegin, queryParamValueEnd);
							}else if(queryParamValueEnd instanceof java.lang.String){
								Date queryParamValueEndDate = DateUtil.convertStringToDate(queryParamValueEnd.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueEndDate);
							}else{
								throw new ServiceException("终止日期(年月日)类型字段格式有误");
							}
						}else if(columnTypeName.equalsIgnoreCase("text")){//货币
							if(queryParamValueEnd instanceof java.lang.String){
								Double queryParamValueEndDouble = Double.valueOf(queryParamValueEnd.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueEndDouble);
							}else if(queryParamValueEnd instanceof java.lang.Integer){
								Double queryParamValueEndDouble = Double.valueOf(queryParamValueEnd.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueEndDouble);
							}else if(queryParamValueEnd instanceof java.lang.Long){
								Double queryParamValueEndDouble = Double.valueOf(queryParamValueEnd.toString());
								queryParamValue.put(propertyNameBegin, queryParamValueEndDouble);
							}else{
								throw new ServiceException("货币类型字段格式有误");
							}
						}else{
							throw new ServiceException("非日期或货币字段不允许使用区域查询");
						}
					
					}
				}
				
				
			}
		}
		return queryParamValue;
	}


	@SuppressWarnings("deprecation")
	public String exportData(Class clazz, List mainList, String fileRootPath, String sheetName, String filePrefix) {
		
		SystemMainTable smt = systemMainTableService.findSystemMainTableByClazz(clazz);
		
		HSSFWorkbook wb=new HSSFWorkbook();
      	HSSFCellStyle cellStyle=wb.createCellStyle();
      	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置标题上下居中
      	     	
     	int totalCount = mainList.size();
     	int pageSize = 32767;
     	int sheetSum = 0;
     	if (totalCount % pageSize == 0){
     		sheetSum = totalCount / pageSize;
     	}else{
     		sheetSum = totalCount / pageSize + 1;
     	}
     		
		for(int ii=0; ii<sheetSum; ii++){
			
			HSSFSheet sheet = wb.createSheet();
	     	wb.setSheetName(ii,sheetName+(ii+1),HSSFWorkbook.ENCODING_UTF_16);
	     	
//			建立标题行，0行
	     	HSSFRow row=sheet.createRow(0);
	      	row.setHeight((short)400);			
	      	//建立标题行的每一个字段  	
	      	List allcolumns=userColumnService.findUserColumns(smt, UserTableSetting.EXPORT);
	      	for(int iii=0; iii<allcolumns.size(); iii++){
	      		UserTableSetting uts=(UserTableSetting)allcolumns.get(iii);
				SystemMainTableColumn mc = uts.getMainTableColumn();
				//SystemMainTableExtColumn mec=uts.getExtendTableColumn();
				if(mc.getIsExtColumn()==SystemMainTableColumn.isMain){
	                String columnCnName = mc.getColumnCnName();
					HSSFCell cell=row.createCell((short)iii); //建立标题记录行，从第1行开始 *******
		     		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
		     		cell.setCellStyle(cellStyle);
		         	cell.setCellValue(columnCnName);
				}else if(mc.getIsExtColumn()==SystemMainTableColumn.isExt){
	                String columnCnName = mc.getColumnCnName();
					HSSFCell cell=row.createCell((short)iii); //建立标题记录行，从第1行开始 *******
		     		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
		     		cell.setCellStyle(cellStyle);
		         	cell.setCellValue(columnCnName);
				}
				
			}
			for(int i=ii* 32767, rows=0; i<ii* 32767+32767&& i<totalCount && rows<32767; i++, rows++){
				
				BaseObject object = (BaseObject) mainList.get(i); //取出list中的实体BaseObject
								
				HSSFRow rowj = null; //建立当前记录的行 *******
				try {
					rowj = sheet.createRow((short) (rows + 1));
				} catch (Exception e) {
					e.printStackTrace();
					//System.out.println("aaa");
				}				
				
				Long mainTableRowId = object.getId(); //主表当前记录的id（行号）
				
				BeanWrapperImpl bwMain = new BeanWrapperImpl(object); //使用BeanWrapper保证主实体
				for(int j=0;j<allcolumns.size();j++){
					UserTableSetting uts=(UserTableSetting)allcolumns.get(j);
					SystemMainTableColumn mc = uts.getMainTableColumn();
					//SystemMainTableExtColumn mec=uts.getExtendTableColumn();
					Object mainPropValue = null;
					if(mc.getIsExtColumn()==SystemMainTableColumn.isMain){
						SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
						String typeName = mcType.getColumnTypeName();
						String pmainPropName = mc.getPropertyName(); //当前主属性的名称

						if(typeName.equalsIgnoreCase("radio")){
							SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
							String foreignValueColumnName = foreignValueColumn.getPropertyName();
							
							//此时mainPropValue为关联对象
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
							if(mainPropValue!=null){
//								使用BeanWrapper包装关联实体
								BeanWrapper bwForei = new BeanWrapperImpl(mainPropValue); 
								//获取关联实体的名称属性的值
								String foreiTableNameValue = (String) bwForei.getPropertyValue(foreignValueColumnName);
//								将此值替换mainPropValue
								mainPropValue = foreiTableNameValue;
							}

						}
						else if(typeName.equalsIgnoreCase("select")){
							SystemMainTable foreiTable = mc.getForeignTable();
							SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
							String foreignValueColumnName = foreignValueColumn.getPropertyName();
							//此时mainPropValue为关联对象
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
							if(mainPropValue!=null&& mainPropValue instanceof BaseObject){//为客户案例硬性加入的判断
//								使用BeanWrapper包装关联实体
								BeanWrapper bwForei = new BeanWrapperImpl(mainPropValue); 
								//获取关联实体的名称属性的值
								String foreiTableNameValue = (String) bwForei.getPropertyValue(foreignValueColumnName);
								Integer level = foreiTable.getLevel();
								if(level!=null&& level.intValue()==2){
									SystemMainTableColumn parentColumn = foreiTable.getParentColumn();
									String parentColumnName = parentColumn.getPropertyName();
									String parentColumnValue = (String) bwForei.getPropertyValue(parentColumnName+"."+foreignValueColumnName);
									foreiTableNameValue = parentColumnValue+"→"+foreiTableNameValue;//→
								}
								//将此值替换mainPropValue
								mainPropValue = foreiTableNameValue;
							}

						}else if(typeName.equalsIgnoreCase("extSelect")){
							SystemMainTable foreiTable = mc.getForeignTable();
							SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
							String foreignValueColumnName = foreignValueColumn.getPropertyName();
							//此时mainPropValue为关联对象
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
							if(mainPropValue!=null&& mainPropValue instanceof BaseObject){//为客户案例硬性加入的判断
//								使用BeanWrapper包装关联实体
								BeanWrapper bwForei = new BeanWrapperImpl(mainPropValue); 
								//获取关联实体的名称属性的值
								String foreiTableNameValue = (String) bwForei.getPropertyValue(foreignValueColumnName);
								Integer level = foreiTable.getLevel();
								if(level!=null&& level.intValue()==2){
									SystemMainTableColumn parentColumn = foreiTable.getParentColumn();
									String parentColumnName = parentColumn.getPropertyName();
									String parentColumnValue = (String) bwForei.getPropertyValue(parentColumnName+"."+foreignValueColumnName);
									foreiTableNameValue = parentColumnValue+"→"+foreiTableNameValue;//→
								}
								//将此值替换mainPropValue
								mainPropValue = foreiTableNameValue;
							}

						}else if(typeName.equalsIgnoreCase("multiSelect")){
							SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
							SystemMainTableColumn fKeyColumn = mc.getForeignTableKeyColumn();
							Object rObject = bwMain.getPropertyValue(pmainPropName); //获取被引用对象的集合
							if(rObject instanceof java.util.Collection){
								Set sets=(Set) rObject;
								for(Object ob:sets){
									bwMain.setWrappedInstance(ob);
									String te= (String) bwMain.getPropertyValue(fValueColumn.getPropertyName());
									if(mainPropValue==null){
										mainPropValue=te;
									}else{
										mainPropValue=mainPropValue+","+te;
									}
								}
							}
					  }else if(typeName.equalsIgnoreCase("yesNoSelect")){
							//获取直接的属性值
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
							if(mainPropValue!=null){
								Integer intValue = Integer.valueOf(mainPropValue.toString());
								mainPropValue = intValue.intValue()==1?"是":"否";
							}else{ //如果是否列表的属性值为null，则为显示否
								mainPropValue = "";
							}
						}else if(typeName.equalsIgnoreCase("foreiText")){
							
						}else{
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
						} 				
						
//						写入单元格 ************************************
						HSSFCell cellk=rowj.createCell((short)j);
		      			cellk.setEncoding(HSSFCell.ENCODING_UTF_16); 
		      			
		      			//货币类型数据进行格式化
		      			if(mc.getValidateType()!=null
		      					&& mc.getValidateType().getValidateTypeName().equalsIgnoreCase("Currency")
		      					&& mainPropValue!=null){
		      				java.text.DecimalFormat nf = new java.text.DecimalFormat("###,###.##");
		      				String moneyValue = nf.format(mainPropValue);
		      				mainPropValue = moneyValue;
		      			}
		      			cellk.setCellValue(mainPropValue==null?"":mainPropValue.toString()); 

					}else if(mc.getIsExtColumn()==SystemMainTableColumn.isExt){
						SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
						String typeName = mcType.getColumnTypeName();
						String pextPropName = mc.getPropertyName(); //当前扩展属性的名称
						String columnCnName = mc.getColumnCnName();
					    Integer selectTypeName=mc.getExtSelectType();//当前扩展字段源自主表或扩展表
					    Map map=this.getEntityDataForEdit(clazz, mainTableRowId.toString());
					    
						if(typeName.equalsIgnoreCase("radio")){
							if(selectTypeName==0){
								String value=(String) map.get(pextPropName).toString();
								SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
								String foreignValueColumnName = foreignValueColumn.getPropertyName();
								SystemMainTableColumn foreignKeyColumn = mc.getForeignTableKeyColumn();
								String foreignKeyColumnName = foreignKeyColumn.getPropertyName();
								List<Object> list=(List) map.get(pextPropName+"s");
								SystemMainTable foreiTable = mc.getForeignTable();
								for(Object extObject:list ){
									BeanWrapper bwExt = new BeanWrapperImpl(extObject);
									String foreignKey=bwExt.getPropertyValue(foreignKeyColumnName).toString();
									if(foreignKey.equals(value)){
										mainPropValue=(String) bwExt.getPropertyValue(foreignValueColumnName);
									}
								}
								
							}else if(selectTypeName==2){
								String value=(String) map.get(pextPropName).toString();
								List<ExtOptionData> list=(List) map.get(pextPropName+"s");
								for(ExtOptionData extRadioOption:list){
									String extid=extRadioOption.getId().toString();
									if(extid.equals(value)){
										mainPropValue=extRadioOption.getExtOptionValue();
									}
								}
							}
						}else if(typeName.equalsIgnoreCase("select")){
							if(selectTypeName==0){
								String value=(String) map.get(pextPropName).toString();
								SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
								String foreignValueColumnName = foreignValueColumn.getPropertyName();
								SystemMainTableColumn foreignKeyColumn = mc.getForeignTableKeyColumn();
								String foreignKeyColumnName = foreignKeyColumn.getPropertyName();
								List<Object> list=(List) map.get(pextPropName+"s");
								SystemMainTable foreiTable = mc.getForeignTable();
								for(Object extObject:list ){
									BeanWrapper bwExt = new BeanWrapperImpl(extObject);
									String foreignKey=bwExt.getPropertyValue(foreignKeyColumnName).toString();
									if(foreignKey.equals(value)){
										mainPropValue=(String) bwExt.getPropertyValue(foreignValueColumnName);
									}
								}
								
							}else if(selectTypeName==2){
								String value=(String) map.get(pextPropName).toString();
								List<ExtOptionData> list=(List) map.get(pextPropName+"s");
								for(ExtOptionData extOption:list){
									String extid=extOption.getId().toString();
									if(extid.equals(value)){
										mainPropValue=extOption.getExtOptionValue();
									}
								}
							}
						}else if(typeName.equalsIgnoreCase("extSelect")){
							if(selectTypeName==0){
								String value=(String) map.get(pextPropName).toString();
								SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
								String foreignValueColumnName = foreignValueColumn.getPropertyName();
								SystemMainTableColumn foreignKeyColumn = mc.getForeignTableKeyColumn();
								String foreignKeyColumnName = foreignKeyColumn.getPropertyName();
								List<Object> list=(List) map.get(pextPropName+"s");
								SystemMainTable foreiTable = mc.getForeignTable();
								for(Object extObject:list ){
									BeanWrapper bwExt = new BeanWrapperImpl(extObject);
									String foreignKey=bwExt.getPropertyValue(foreignKeyColumnName).toString();
									if(foreignKey.equals(value)){
										mainPropValue=(String) bwExt.getPropertyValue(foreignValueColumnName);
									}
								}
								
							}else if(selectTypeName==2){
								String value=(String) map.get(pextPropName).toString();
								List<ExtOptionData> list=(List) map.get(pextPropName+"s");
								for(ExtOptionData extOption:list){
									String extid=extOption.getId().toString();
									if(extid.equals(value)){
										mainPropValue=extOption.getExtOptionValue();
									}
								}
							}
						}else if(typeName.equalsIgnoreCase("checkbox")){
							if(selectTypeName==0){
								List<Long> value=(List) map.get(pextPropName);
								SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
								String foreignValueColumnName = foreignValueColumn.getPropertyName();
								SystemMainTableColumn foreignKeyColumn = mc.getForeignTableKeyColumn();
								String foreignKeyColumnName = foreignKeyColumn.getPropertyName();
								List<Object> list=(List) map.get(pextPropName+"s");
								SystemMainTable foreiTable = mc.getForeignTable();
								for(Object extObject:list ){
									BeanWrapper bwExt = new BeanWrapperImpl(extObject);
									String foreignKey=bwExt.getPropertyValue(foreignKeyColumnName).toString();
									for(Long va:value){
										if(foreignKey.equals(va.toString())){
											if(mainPropValue==null){
												mainPropValue=(String) bwExt.getPropertyValue(foreignValueColumnName);
											}else{
												mainPropValue=mainPropValue+","+(String) bwExt.getPropertyValue(foreignValueColumnName);
											}
										}
									}
								}
								
							}else if(selectTypeName==2){
								List<Long> value=(List) map.get(pextPropName);
								List<ExtOptionData> list=(List) map.get(pextPropName+"s");
								for(ExtOptionData extCheckBoxOption:list){
									String extid=extCheckBoxOption.getId().toString();
									for(Long va:value){
										if(extid.equals(va.toString())){
											if(mainPropValue==null){
												mainPropValue=extCheckBoxOption.getExtOptionValue();
											}else{
												mainPropValue=mainPropValue+","+extCheckBoxOption.getExtOptionValue();
											}
										}
									}
								}
							}
						}else if(typeName.equalsIgnoreCase("yesNoSelect")){
							//获取直接的属性值
							Integer key=(Integer) map.get(pextPropName);
							if(key!=null){
								mainPropValue = key==1?"是":"否";
							}else{ //如果是否列表的属性值为null，则为显示否
								mainPropValue = "";
							}
						}else if(typeName.equalsIgnoreCase("yesNoRadio")){
							//获取直接的属性值
							Integer key=(Integer) map.get(pextPropName);
							if(key!=null){
								mainPropValue = key==1?"是":"否";
							}else{ //如果是否列表的属性值为null，则为显示否
								mainPropValue = "";
							}
						}else{
							String value=(String) map.get(pextPropName);
							mainPropValue=value;
						}
						
//						写入单元格 ************************************
						HSSFCell cellk=rowj.createCell((short)(j));
		      			cellk.setEncoding(HSSFCell.ENCODING_UTF_16); 
		      			
		      			//货币类型数据进行格式化
//		      			if(mc.getValidateType()!=null
//		      					&& mc.getValidateType().getValidateTypeName().equalsIgnoreCase("Currency")
//		      					&& mainPropValue!=null){
//		      				java.text.DecimalFormat nf = new java.text.DecimalFormat("###,###.##");
//		      				String moneyValue = nf.format(mainPropValue);
//		      				mainPropValue = moneyValue;
//		      			}
		      			cellk.setCellValue(mainPropValue==null?"":mainPropValue.toString()); 
					}
				}
			}
			
			
		}
		
		
		String excelFileName = null;
		final String FSP = System.getProperty("file.separator");
		try{
//			获得Excel文件的真实路径
      		excelFileName = filePrefix+"_"+System.currentTimeMillis() + ".xls";
      		String excelFullFileName = fileRootPath + FSP + excelFileName;
      		//deleteFile(excelFullFileName);//先删除原文件
      	 	FileOutputStream fileout=new FileOutputStream(excelFullFileName);
          	wb.write(fileout);
          	fileout.close();
       	}
      	catch(Exception e){
      		e.printStackTrace();
      	}
      	return excelFileName;
	}


	public Map<String, Object> getEntityDataForAdd(Class clazz) {
		
		Map<String, Object> requestParams = new HashMap<String, Object>();
		
		SystemMainTable smt = systemMainTableService.findSystemMainTableByClazz(clazz);
		//List sysMainColumns = systemMainColumnService.findSystemMainTableColumns(smt);
		List columns=systemMainAndExtColumnService.findAllColumnBySysMainTable(smt);
		for(int i=0; i<columns.size(); i++){
			SystemMainTableColumn column = (SystemMainTableColumn) columns.get(i);
			String propertyName = column.getPropertyName();
			SystemMainTableColumnType columnType = column.getSystemMainTableColumnType();
//			MetaType metaType = ms.findMetaTypeByName(columnType.getColumnTypeName());
//			if(metaType.isCollectionType()){
//				
//			}
			
			if(columnType.getColumnTypeName().equalsIgnoreCase("radio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(propertyName+"s", columnWapper.getList());
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("select")){
				
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(propertyName+"s", columnWapper.getList());
				if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", columnWapper.getParentList());
					requestParams.put("all"+propertyName+"s", columnWapper.getAllList());
				}
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("checkboxGroup")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(propertyName, columnWapper.getList()); //选中的项，如userRole里中的记录
				requestParams.put(propertyName+"s", columnWapper.getSourceList()); //待选的数据源
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("yesNoSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(propertyName+"s", columnWapper.getMap());
		    }else if(columnType.getColumnTypeName().equalsIgnoreCase("sexSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(propertyName+"s", columnWapper.getMap());
		    }else if(columnType.getColumnTypeName().equalsIgnoreCase("extSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				Map map=new HashMap();
				requestParams.put(propertyName+"s", map);
		    }
			else if(columnType.getColumnTypeName().equalsIgnoreCase("yesNoRadio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(propertyName+"s", columnWapper.getMap());
		    }else if(columnType.getColumnTypeName().equalsIgnoreCase("multiSelect")){
				/*SystemMainTable rtable = column.getReferencedTable();
				SystemMainTableColumn rtableKeyColumn = column.getReferencedTableKeyColumn();
				SystemMainTableColumn rtableValueColumn = column.getReferencedTableValueColumn();
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();*/

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(propertyName+"s", columnWapper.getSourceList());
				
				/*if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", columnWapper.getParentList());
					requestParams.put("all"+propertyName+"s", columnWapper.getAllList());
				}*/
			}
		}
//		for(int i=0;i<sysExtendColumns.size();i++){
//			//SystemMainTableExtColumn extColumn=(SystemMainTableExtColumn)sysExtendColumns.get(i);
//			
//			String extPropertyName =extColumn.getPropertyName();
//			SystemMainTableColumnType extColumnType=extColumn.getSystemMainTableColumnType();
//			if(extColumnType.getColumnTypeName().equalsIgnoreCase("radio")){
//				ColumnDataWrapper columnWapper = new ColumnDataWrapper(extColumn);
//				columnWapper.initList();
//				requestParams.put(extPropertyName+"s", columnWapper.getList());
//				
//			}else if(extColumnType.getColumnTypeName().equalsIgnoreCase("select")){
//					ColumnDataWrapper columnWapper = new ColumnDataWrapper(extColumn);
//					columnWapper.initList();
//					requestParams.put(extPropertyName+"s", columnWapper.getList());
//			}else if(extColumnType.getColumnTypeName().equalsIgnoreCase("checkbox")){
//				ColumnDataWrapper columnWapper = new ColumnDataWrapper(extColumn);
//				columnWapper.initList();
//				requestParams.put(extPropertyName+"s", columnWapper.getSourceList());
//		    }else if(extColumnType.getColumnTypeName().equalsIgnoreCase("yesNoSelect")){
//				ColumnDataWrapper columnWapper = new ColumnDataWrapper(extColumn);
//				columnWapper.initList();
//				requestParams.put(extPropertyName+"s", columnWapper.getMap());
//		    }else if(extColumnType.getColumnTypeName().equalsIgnoreCase("yesNoRadio")){
//				ColumnDataWrapper columnWapper = new ColumnDataWrapper(extColumn);
//				columnWapper.initList();
//				requestParams.put(extPropertyName+"s", columnWapper.getMap());
//		    }
//		}
		return requestParams;
	}

	public Map<String, Object> getEntityDataForEdit(Object object, String tableName) {
		Map<String, Object> columnDataNew = new HashMap<String, Object>();
		Map<String, Object> columnData = this.getEntityDataForEdit(object);
		Set set = columnData.keySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			String keyName = (String) iter.next();
			Object value = columnData.get(keyName);
			keyName = tableName + "$" + keyName;
			columnDataNew.put(keyName, value);
		}
		return columnDataNew;
	}
	
	
	public Map<String, Object> getFormDataForEdit(Object object,
			String tableName) {
		Map<String, Object> columnDataNew = new HashMap<String, Object>();
		Map<String, Object> columnData = this.getFormDataForEdit(object);
		Set set = columnData.keySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			String keyName = (String) iter.next();
			Object value = columnData.get(keyName);
			keyName = tableName + "$" + keyName;
			columnDataNew.put(keyName, value);
		}
		return columnDataNew;
	}
	
	public Map<String, Object> getEntityDataForLook(Object object, String tableName) {
		Map<String, Object> columnDataNew = new HashMap<String, Object>();
		Map<String, Object> columnData = this.getEntityDataForLook(object);
		Set set = columnData.keySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			String keyName = (String) iter.next();
			Object value = columnData.get(keyName);
			keyName = tableName + "$" + keyName;
			columnDataNew.put(keyName, value);
		}
		return columnDataNew;
	}

	public Map<String, Object> getEntityDataForLook(Class clazz, String objectId) {
		BaseObject objectEdit = (BaseObject) baseService.find(clazz, objectId, true);
		return this.getEntityDataForLook(objectEdit);
	}

	public Map<String, Object> getEntityDataForLook(Object objectEdit) {
		BaseObject object = (BaseObject) objectEdit;
		Class clazz = object.getClass();
		
		Map<String,Object> requestParams = new HashMap<String,Object>();
		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List sysMainColumns =this.systemMainAndExtColumnService.findAllColumnBySysMainTable(smt);
		//List sysMainColumns=systemColumnService.findSystemTableColumns(smt);
		//List sysExtColumns=this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isExt, smt);
		for(int i=0; i<sysMainColumns.size(); i++){
			SystemMainTableColumn column = (SystemMainTableColumn) sysMainColumns.get(i);
			SystemMainTableColumnType columnType = column.getSystemMainTableColumnType();
			String columnTypenName = columnType.getColumnTypeName();			
			String propertyName = column.getPropertyName();
			
			if(columnTypenName.equalsIgnoreCase("radio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getList());
				
			}else if(columnTypenName.equalsIgnoreCase("text")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("hidden")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				//begin modify by peixf2期框架发现优化，如果对象类型的属性，应该使用id而不是对象的tostring形式
				requestParams.put(propertyName, columnWapper.getValue());
				//end
			}else if(columnTypenName.equalsIgnoreCase("textArea")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("dateText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("checkbox")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList());
				requestParams.put(propertyName+"s", columnWapper.getSourceList());
				
			}else if(columnTypenName.equalsIgnoreCase("foreiText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("yesNoSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("sexSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("extSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				requestParams.put(propertyName+"s", columnWapper.getValue());
						
			}else if(columnTypenName.equalsIgnoreCase("select")){
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);

				Object value = columnWapper.getValue();
				Long key = columnWapper.getKey();
				List allList = columnWapper.getAllList();
				List list = columnWapper.getList();
				List parentList = columnWapper.getParentList();
				
				requestParams.put(propertyName, columnWapper.getText()); 
				requestParams.put(propertyName+"s", list);
					
				if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", parentList); //parentTradeWays
					requestParams.put("all"+propertyName+"s", allList);
				}
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiSelect")){
			
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList());
				List sl=columnWapper.getSourceList();
				sl.removeAll(columnWapper.getList());
				requestParams.put(propertyName+"s", sl);
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("file")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"Link", columnWapper.getLink());
				requestParams.put(propertyName+"Text", columnWapper.getText());
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiFile")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getValue());
				requestParams.put(propertyName+"Link", columnWapper.getLink());
				requestParams.put(propertyName+"Text", columnWapper.getText());
				
			}
		}
		return requestParams;
	}

	public Map<String, Object> getFormDataForEdit(Class clazz, String objectId) {
		BaseObject objectEdit = (BaseObject) baseService.find(clazz, objectId, true);
		return this.getFormDataForEdit(objectEdit);
		
	}

	public Map<String, Object> getFormDataForEdit(Object objectEdit) {
		BaseObject object = (BaseObject) objectEdit;
		Class clazz = object.getClass();
		
		Map<String,Object> requestParams = new HashMap<String,Object>();
		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List sysMainColumns =this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isMain, smt);
		//List sysMainColumns=systemColumnService.findSystemTableColumns(smt);
		List sysExtColumns=this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isExt, smt);
		for(int i=0; i<sysMainColumns.size(); i++){
			SystemMainTableColumn column = (SystemMainTableColumn) sysMainColumns.get(i);
			SystemMainTableColumnType columnType = column.getSystemMainTableColumnType();
			String columnTypenName = columnType.getColumnTypeName();			
			String propertyName = column.getPropertyName();
			
			if(columnTypenName.equalsIgnoreCase("radio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
				
			}else if(columnTypenName.equalsIgnoreCase("text")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("hidden")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getValue());
				//end
			}else if(columnTypenName.equalsIgnoreCase("textArea")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("htmlEditor")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				//add by lee for add fckediter in 20090928 begin
			}else if(columnTypenName.equalsIgnoreCase("fckEditor")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				//add by lee for add fckediter in 20090928 end
			}else if(columnTypenName.equalsIgnoreCase("dateText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("checkbox")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getList());
				
			}else if(columnTypenName.equalsIgnoreCase("foreiText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("yesNoSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
						
			}else if(columnTypenName.equalsIgnoreCase("sexSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
						
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
						
			}else if(columnTypenName.equalsIgnoreCase("extSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
						
			}else if(columnTypenName.equalsIgnoreCase("select")){
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey()); 
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("checkboxGroup")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getList()); //选中的项，如userRole里中的记录
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiSelect")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getList());
				//List sl=columnWapper.getSourceList();
				//sl.removeAll(columnWapper.getList());
				//requestParams.put(propertyName+"s", sl);
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("file")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"Link", columnWapper.getLink());
				requestParams.put(propertyName+"Text", columnWapper.getText());
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiFile")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				//key是附件的id，value是附件对象
				requestParams.put(propertyName, columnWapper.getText());
								
			}
		}
		for(int i=0; i<sysExtColumns.size(); i++){
			SystemMainTableColumn column = (SystemMainTableColumn)sysExtColumns.get(i);
			SystemMainTableColumnType columnType = column.getSystemMainTableColumnType();
			String columnTypenName = columnType.getColumnTypeName();			
			String propertyName = column.getPropertyName();
			
			if(columnTypenName.equalsIgnoreCase("radio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getList());
				
			}else if(columnTypenName.equalsIgnoreCase("text")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("htmlEditor")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("textArea")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("dateText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("yesNoSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("checkbox")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList());
				requestParams.put(propertyName+"s", columnWapper.getSourceList());
				
			}else if(columnTypenName.equalsIgnoreCase("foreiText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("extSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getValue());
						
			}else if(columnTypenName.equalsIgnoreCase("select")){
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);

				Object value = columnWapper.getValue();
				List allList = columnWapper.getAllList();
				List list = columnWapper.getList();
				List parentList = columnWapper.getParentList();
				
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", list);
					
				if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", parentList); //parentTradeWays
					requestParams.put("all"+propertyName+"s", allList);
				}
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiSelect")){
				/*SystemMainTable rtable = column.getReferencedTable();
				SystemMainTableColumn rtableKeyColumn = column.getReferencedTableKeyColumn();
				SystemMainTableColumn rtableValueColumn = column.getReferencedTableValueColumn();
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();*/

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList());
				requestParams.put(propertyName+"s", columnWapper.getSourceList());
				
				/*if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", columnWapper.getParentList());
					requestParams.put("all"+propertyName+"s", columnWapper.getAllList());
				}*/
			}else{
				//对于其他类型属性页面直接从属性里取值
			}
		}
		return requestParams;
	}

	public Map<String, Object> getEntityDataForEdit(Object objectEdit) {
		
		BaseObject object = (BaseObject) objectEdit;
		Class clazz = object.getClass();
		
		Map<String,Object> requestParams = new HashMap<String,Object>();
		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List sysMainColumns =this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isMain, smt);
		//List sysMainColumns=systemColumnService.findSystemTableColumns(smt);
		List sysExtColumns=this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isExt, smt);
		for(int i=0; i<sysMainColumns.size(); i++){
			SystemMainTableColumn column = (SystemMainTableColumn) sysMainColumns.get(i);
			SystemMainTableColumnType columnType = column.getSystemMainTableColumnType();
			String columnTypenName = columnType.getColumnTypeName();			
			String propertyName = column.getPropertyName();
			
			if(columnTypenName.equalsIgnoreCase("radio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getList());
				
			}else if(columnTypenName.equalsIgnoreCase("text")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("hidden")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				//begin modify by peixf2期框架发现优化，如果对象类型的属性，应该使用id而不是对象的tostring形式
				requestParams.put(propertyName, columnWapper.getValue());
				//end
			}else if(columnTypenName.equalsIgnoreCase("textArea")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("htmlEditor")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
			}
			//add by lee for add fckediter in 20090928 begin
			else if(columnTypenName.equalsIgnoreCase("fckEditor")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
			}
			//add by lee for add fckediter in 20090928 end
			else if(columnTypenName.equalsIgnoreCase("dateText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("checkbox")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList());
				requestParams.put(propertyName+"s", columnWapper.getSourceList());
				
			}else if(columnTypenName.equalsIgnoreCase("foreiText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("yesNoSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("sexSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("extSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getValue());
						
			}else if(columnTypenName.equalsIgnoreCase("select")){
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);

				Object value = columnWapper.getValue();
				Long key = columnWapper.getKey();
				List allList = columnWapper.getAllList();
				List list = columnWapper.getList();
				List parentList = columnWapper.getParentList();
				
				requestParams.put(propertyName, columnWapper.getKey()); 
				requestParams.put(propertyName+"s", list);
					
				if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", parentList); //parentTradeWays
					requestParams.put("all"+propertyName+"s", allList);
				}
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("checkboxGroup")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList()); //选中的项，如userRole里中的记录
				requestParams.put(propertyName+"s", columnWapper.getSourceList()); //待选的数据源
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiSelect")){
				/*SystemMainTable rtable = column.getReferencedTable();
				SystemMainTableColumn rtableKeyColumn = column.getReferencedTableKeyColumn();
				SystemMainTableColumn rtableValueColumn = column.getReferencedTableValueColumn();
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();*/

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList());
				List sl=columnWapper.getSourceList();
				sl.removeAll(columnWapper.getList());
				requestParams.put(propertyName+"s", sl);
				
				/*if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", columnWapper.getParentList());
					requestParams.put("all"+propertyName+"s", columnWapper.getAllList());
				}*/
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("file")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"Link", columnWapper.getLink());
				requestParams.put(propertyName+"Text", columnWapper.getText());
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiFile")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				//key是附件的id，value是附件对象
				requestParams.put(propertyName, columnWapper.getText());
								
			}
		}
		for(int i=0; i<sysExtColumns.size(); i++){
			SystemMainTableColumn column = (SystemMainTableColumn) sysExtColumns.get(i);
			SystemMainTableColumnType columnType = column.getSystemMainTableColumnType();
			String columnTypenName = columnType.getColumnTypeName();			
			String propertyName = column.getPropertyName();
			
			if(columnTypenName.equalsIgnoreCase("radio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getList());
				
			}else if(columnTypenName.equalsIgnoreCase("text")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("textArea")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("dateText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("htmlEditor")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("yesNoSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("yesNoRadio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("sexSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getMap());
						
			}else if(columnTypenName.equalsIgnoreCase("checkbox")||columnTypenName.equalsIgnoreCase("checkboxGroup")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList());
				requestParams.put(propertyName+"s", columnWapper.getSourceList());
				
			}else if(columnTypenName.equalsIgnoreCase("foreiText")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getText());
				
			}else if(columnTypenName.equalsIgnoreCase("extSelect")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initData(object);
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", columnWapper.getValue());
						
			}else if(columnTypenName.equalsIgnoreCase("select")){
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);

				Object value = columnWapper.getValue();
				List allList = columnWapper.getAllList();
				List list = columnWapper.getList();
				List parentList = columnWapper.getParentList();
				
				requestParams.put(propertyName, columnWapper.getKey());
				requestParams.put(propertyName+"s", list);
					
				if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", parentList); //parentTradeWays
					requestParams.put("all"+propertyName+"s", allList);
				}
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiSelect")){
				/*SystemMainTable rtable = column.getReferencedTable();
				SystemMainTableColumn rtableKeyColumn = column.getReferencedTableKeyColumn();
				SystemMainTableColumn rtableValueColumn = column.getReferencedTableValueColumn();
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();*/

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initDataAndList(object);
				requestParams.put(propertyName, columnWapper.getList());
				requestParams.put(propertyName+"s", columnWapper.getSourceList());
				
				/*if(fParentColumn!=null){
					requestParams.put(fParentColumn.getPropertyName()+"s", columnWapper.getParentList());
					requestParams.put("all"+propertyName+"s", columnWapper.getAllList());
				}*/
			}else{
				//对于其他类型属性页面直接从属性里取值
			}
		}
		return requestParams;
	}


	public Map<String, Object> getEntityDataForEdit(Class clazz, String objectId) {
		
		BaseObject objectEdit = (BaseObject) baseService.find(clazz, objectId, true);
		return this.getEntityDataForEdit(objectEdit);
		
	}

	//待完善
	public List<Object> getEntityDataForList(Class clazz, List mainList) {

		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List sysMainColumns =this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isMain, smt);
		List sysExtColumns=this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isExt, smt);

		List<Object> mapList = new ArrayList<Object>();
		Iterator iter = mainList.iterator();
		while(iter.hasNext()){//遍历每行记录，取出list中的实体BaseObject item

			BaseObject object = (BaseObject) iter.next();
			Long mainTableRowId = object.getId(); //主表当前记录的id（行号）

			//取出实体的主字段
		
			for(int i=0; i<sysMainColumns.size(); i++){
				Object propertyValue = null;
				SystemMainTableColumn mc = (SystemMainTableColumn) sysMainColumns.get(i);
				SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = mc.getPropertyName(); //当前主属性的名称
				
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(mc);
				columnDataWrapper.initData(object);
				propertyValue = columnDataWrapper.getText();
				//multiSelect不显示
			
			}
		
				
		}//end while loop
		return mapList;
	}

//	public abstract void addMainColumnData(Class claszz, List mainList){}
//	public abstract void addExtendColumnData(Class claszz, List mainList){}
//	
	public List<Map<String, Object>> getEntityMapDataForList(Class clazz, List mainList) {
	
		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List sysMainColumns =this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isMain, smt);
		List sysExtColumns=this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isExt, smt);

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Iterator iter = mainList.iterator();
		while(iter.hasNext()){//遍历每行记录，取出list中的实体BaseObject item
			Map<String, Object> item = new HashMap<String, Object>(); //存储每个记录，相对一个实体对象
			BaseObject object = (BaseObject) iter.next();
			Long mainTableRowId = object.getId(); //主表当前记录的id（行号）

			//取出实体的主字段
		
			for(int i=0; i<sysMainColumns.size(); i++){
				Object propertyValue = null;
				SystemMainTableColumn mc = (SystemMainTableColumn) sysMainColumns.get(i);
				SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = mc.getPropertyName(); //当前主属性的名称				
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(mc);
				columnDataWrapper.initData(object);
				propertyValue = columnDataWrapper.getText();
				if(columnDataWrapper.getKey()!=null){
					Long key = columnDataWrapper.getKey();
					item.put(propertyName+"Id", key);
				}
				item.put(propertyName, propertyValue);
				
				//处理用户类字段的用户名显示问题，即“管理员/admin/神州数码”
//				if(mc.getForeignTable()!=null){
//					String fclassName = mc.getForeignTable().getClassName();
//					if(fclassName.equalsIgnoreCase("com.zsgj.info.framework.security.entity.UserInfo")){
//						Object fObject = columnDataWrapper.getValue();
//						item.put("realNameAndDept", fObject.toString());
//					}
//				}
				//2期框架新增未充分测试
				if(typeName.equalsIgnoreCase("hidden")&& mc.getForeignTable()!=null){
					item.put(propertyName, columnDataWrapper.getKey());
				}
				
				if(columnDataWrapper.getLink()!=null&& !columnDataWrapper.getLink().equals("")){
					item.put(propertyName+"Link", columnDataWrapper.getLink());
				}

			}
			for(int i=0; i<sysExtColumns.size(); i++){
				Object propertyValue = null;
				SystemMainTableColumn mtec = (SystemMainTableColumn) sysExtColumns.get(i);
				SystemMainTableColumnType mcType = mtec.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = mtec.getPropertyName(); //当前主属性的名称
				
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(mtec);
				columnDataWrapper.initData(object);
				propertyValue = columnDataWrapper.getText();
				item.put(propertyName, propertyValue);

			}
			mapList.add(item); 
				
		}//end while loop
		return mapList;
	}

	public List<Map<String, Object>> getEntityMapDataForList(Class clazz, List mainList, String tableName) {
		
		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List sysMainColumns =this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isMain, smt);
		List sysExtColumns=this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isExt, smt);

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Iterator iter = mainList.iterator();
		while(iter.hasNext()){//遍历每行记录，取出list中的实体BaseObject item
			Map<String, Object> item = new HashMap<String, Object>(); //存储每个记录，相对一个实体对象
			BaseObject object = (BaseObject) iter.next();
			Long mainTableRowId = object.getId(); //主表当前记录的id（行号）

			//取出实体的主字段
		
			for(int i=0; i<sysMainColumns.size(); i++){
				Object propertyValue = null;
				SystemMainTableColumn mc = (SystemMainTableColumn) sysMainColumns.get(i);
				SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = mc.getPropertyName(); //当前主属性的名称				
				String tableNameProperyName = tableName+"$"+ propertyName; //*****add***
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(mc);
				columnDataWrapper.initData(object);
				propertyValue = columnDataWrapper.getText();

				item.put(tableNameProperyName, propertyValue);
				
				if(columnDataWrapper.getLink()!=null&& !columnDataWrapper.getLink().equals("")){
					item.put(tableNameProperyName+"Link", columnDataWrapper.getLink());
				}

			}
			for(int i=0; i<sysExtColumns.size(); i++){
				Object propertyValue = null;
				SystemMainTableColumn mtec = (SystemMainTableColumn) sysExtColumns.get(i);
				SystemMainTableColumnType mcType = mtec.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = mtec.getPropertyName(); //当前主属性的名称
				String tableNameProperyName = tableName+"$"+ propertyName;//*****add***
				
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(mtec);
				columnDataWrapper.initData(object);
				propertyValue = columnDataWrapper.getText();
				item.put(tableNameProperyName, propertyValue);

			}
			mapList.add(item); 
				
		}//end while loop
		return mapList;
	}

	public void removeEntityData(Class clazz, Class eventClazz, String objectId) {
		
		Object source = baseService.find(clazz, objectId);
		Object event = null;
		try {
			 event = eventClazz.newInstance();
			PropertyUtils.copyProperties(event, source);
			((BaseObject)event).setId(null);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		baseService.save(event);
		baseService.remove(clazz, objectId);
		
		
	}


	public void removeEntityData(Class clazz, String objectId) {
		this.systemColumnService.removeMainAndExtData(clazz, objectId);	
	}


	public Object saveEntityData(Class clazz, Map requestParams) {
		return this.userColumnService.saveMainAndExtData(clazz, requestParams);
	}


	/*public Page query(Class clazz, Map params, Map extParams, int pageNo,
			int pageSize, String orderProp, boolean isAsc) {
		
		Page page = queryService.query(clazz, params, extParams, pageNo, pageSize, orderProp, isAsc);
		return page;
		
	}*/

	public Page query(Class clazz, Map<Object, Object> queryParams, Map extParams, 
						int pageNo, int pageSize, String orderProp, boolean isAsc) {
		
		Page page = queryService.query(clazz, queryParams, null, pageNo, pageSize, orderProp, isAsc);
		return page;
	}


	public Map<String, Object> getUserColumnDataForQuery(Class clazz) {
		Map<String,Object> map = new HashMap<String,Object>(); //存放所有查询字段的关联数据
		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		SystemTableQuery stq = this.userColumnService.findSystemTableQuery(smt);//注意后台不可以有多个单表查询
		//List list = ms.findUserQueryColumn(stq, true); //获取用户可见的查询字段
		//findUserQueryColumn(SystemTableQuery stq, boolean onlyShowVisible)
		List list = this.userColumnService.findUserQueryColumn(stq); //获取用户可见的查询字段
		/*List list = ms.findUserQueryColumn(stq, true)*/; 
		for(int i=0; i<list.size(); i++){
			UserTableQueryColumn userQueryColumn = (UserTableQueryColumn) list.get(i);
			SystemTableQueryColumn stqc = userQueryColumn.getSystemTableQueryColumn();
			if(stqc.getMainTableColumn()!=null){
				SystemMainTableColumn column = stqc.getMainTableColumn();
				
				String propertyName = column.getPropertyName();
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				map.put(propertyName+"s", columnWapper.getList());
//				if(map!=null){
//					map.put(fParentColumn.getPropertyName()+"s", columnWapper.getParentList());
//					map.put("all"+propertyName+"s", columnWapper.getAllList());
//				}
				
				
//				SystemMainTable systemMainTable = column.getSystemMainTable();
//				String mainTableClassName = systemMainTable.getClassName();
//				String propertyName = column.getPropertyName();
//				SystemMainTableColumnType smtct = column.getSystemMainTableColumnType();
//				String typeName = smtct.getColumnTypeName();
//				if(typeName.equalsIgnoreCase("select")){
//					
//					SystemMainTable fTable = column.getForeignTable();
//					String fTableName = fTable.getTableName(); //外键表名称，大写开头
//					SystemMainTableColumn fValueColumn = column.getForeignTableValueColumn();
//					SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();
//					String fClassName = column.getForeignTable().getClassName();
//					Class clazzRel = null;
//					try {
//						clazzRel = Class.forName(fClassName);
//					} catch (ClassNotFoundException e1) {
//						e1.printStackTrace();
//					}
//					//获取关联对象类型的所有数据，用于页面显示对象类型属性的下拉列表，对于父子列表需显示2个列表
//					if(fParentColumn==null) { //普通列表，只显示关联表的一个字段
//						Integer fColumnOrder = column.getForeignTableValueColumnOrder();
//						
//						List fObjects = null;
//						if(fColumnOrder==null){
//							fObjects = baseService.findAll(clazzRel);
//						}else{
//							boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
//							fObjects = baseService.findAllBy(clazzRel, fValueColumn.getPropertyName(), isAsc);
//						}
//						map.put(propertyName+"s", fObjects);
//						
//					}else { //父子列表，显示关联表的名字字段和父字段
//						Integer fColumnOrder = column.getForeignTableValueColumnOrder();
//						
//						List fChildObjects = null;
//						if(fColumnOrder==null){
//							fChildObjects = baseService.findAll(clazzRel);
//						}else{
//							boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
//							fChildObjects = baseService.findAllChildBy(clazzRel, fParentColumn.getPropertyName(), fValueColumn.getPropertyName(), isAsc);
//						}
//						map.put(propertyName+"s", fChildObjects); //tradeWay
//						
//						Integer fPVColumnOrder = column.getForeignTableValueColumnOrder();
//						
//						List fParentObjects = null;
//						if(fColumnOrder==null){
//							fParentObjects = baseService.findAll(clazzRel);
//						}else{
//							boolean isAsc = fColumnOrder.intValue()==1 ? true : false; //parentTradeWay
//							fParentObjects = baseService.findAllTopBy(clazzRel, fParentColumn.getPropertyName(), fValueColumn.getPropertyName(), isAsc);
//						}
//						map.put(fParentColumn.getPropertyName()+"s", fParentObjects); //parentTradeWays
//						
//						List fObjects = null;
//						if(fColumnOrder==null){
//							fObjects = baseService.findAll(clazzRel);
//						}else{
//							boolean isAsc = fColumnOrder.intValue()==1 ? true : false;
//							fObjects = baseService.findAllBy(clazzRel, fValueColumn.getPropertyName(), isAsc);
//						}
//						String tableName = smt.getTableName();
//						map.put("all"+propertyName+"s", fObjects);//alltradeWays 
//					}
//				}
			}
		}
		return map;
	}

	public List<UserTableQueryColumn> getUserColumnForQuery(Class clazz) {
		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		SystemTableQuery stq = this.userColumnService.findSystemTableQuery(smt);
		List<UserTableQueryColumn> list = this.userColumnService.findUserQueryColumn(stq); //获取用户可见的查询字段
		return list;
	}
	
	public List<UserTableSetting> getUserColumnForEdit(Class clazz) {
		SystemMainTable sysmt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List<UserTableSetting> userColumns = userColumnService.findUserColumns(sysmt, UserTableSetting.INPUT);
		return userColumns;
	}


	public List<UserTableSetting> getUserColumnForList(Class clazz) {
		SystemMainTable sysmt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List<UserTableSetting> userColumns = userColumnService.findUserColumns(sysmt, UserTableSetting.LIST);
		return userColumns;
	}

	public void setSystemMainTableService(
			SystemMainTableService systemMainTableService) {
		this.systemMainTableService = systemMainTableService;
	}

//	public void setSystemMainColumnService(
//			SystemMainColumnService systemMainColumnService) {
//		this.systemMainColumnService = systemMainColumnService;
//	}


	public void setUserColumnService(UserColumnService userColumnService) {
		this.userColumnService = userColumnService;
	}
	
	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}


	public void setBaseService(Service baseService) {
		this.baseService = baseService;
	}


	/**
	 * @Param SystemColumnService systemColumnService to set
	 */
	public void setSystemColumnService(SystemColumnService systemColumnService) {
		this.systemColumnService = systemColumnService;
	}


	/**
	 * @Param SystemExtColumnServcie systemExtColumnService to set
	 */
//	public void setSystemExtColumnService(
//			SystemExtColumnServcie systemExtColumnService) {
//		this.systemExtColumnService = systemExtColumnService;
//	}

	public void setSystemMainAndExtColumnService(
			SystemMainAndExtColumnService systemMainAndExtColumnService) {
		this.systemMainAndExtColumnService = systemMainAndExtColumnService;
	}

	public Page queryForUser(Class clazz, Map<String, Object> requestParams,
			int pageNo, int pageSize, String orderProp, boolean isAsc,
			String propertyName) {
		Map<String, Object> queryParams = this.genPropParams(clazz, requestParams);
		Page page = queryService.queryByParamsForUser(clazz, queryParams, null, pageNo, pageSize, orderProp, isAsc, propertyName);
		return page;
	}

	public Object saveEntityDataForUser(Class clazz, Map requestParams,UserInfo user){
		return this.userColumnService.saveMainAndExtDataForUser(clazz, requestParams,user);
	}
}
