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
		
		SystemTableQuery stq = userColumnService.findSystemTableQuery(smt); //ͨ��ϵͳ�����ȡϵͳ���ѯ
		//List list = ms.findUserQueryColumn(stq, true);
		//ͨ��ϵͳ��ѯ��ȡ��ǰ�û������в�ѯ�ֶ�
		List list = userColumnService.findUserQueryColumn(stq);
		for(int i=0; i<list.size(); i++){
			UserTableQueryColumn column = (UserTableQueryColumn) list.get(i);
			SystemTableQueryColumn sysTableQueryColumn = column.getSystemTableQueryColumn();
			SystemMainTableColumn smtc = sysTableQueryColumn.getMainTableColumn();
			if(sysTableQueryColumn.isSystemColumn()){
				SystemMainTableColumnType ct = smtc.getSystemMainTableColumnType();//���ֶ�����
				String columnTypeName = ct.getColumnTypeName();
				String propertyName = smtc.getPropertyName(); //������������
				String propertyValue = null; //����������ֵ
				//System.out.println(propertyName);
				//ȡ����������ֵ
//				if(queryParams.get(propertyName)!=null){
//					propertyValue = queryParams.get(propertyName);
//				}else{
//					continue;
//				}
				//�����������ֵ���գ���queryParamValue�м���˲�ѯ����ֵ
				if(StringUtils.isNotBlank(requestParams.get(propertyName))){
					propertyValue = requestParams.get(propertyName);
					
					queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
				}else{
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//�������򣬵�ǰ���Ա�null
					if(matchMode!=null&& !matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						continue;
					}
					
				}
				if(columnTypeName.equalsIgnoreCase("text")){
					
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//������������
					if(matchMode!=null&& matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						queryParamValue.put(sysTableQueryColumn, null);
						queryParamValue.put(propertyNameBegin, requestParams.get(propertyNameBegin));
						queryParamValue.put(propertyNameEnd, requestParams.get(propertyNameEnd));
					}else{ //�˷�֧����ʡ��
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
					
					//ȡ������ԣ���tradeWay
					//propertyValue = (String) queryParams.get(propertyName);
					//queryParamValue.put(sysTableQueryColumn, propertyValue);//�������ֵ�����б�ֵ
					if(fParentColumn!=null){
						String parentPropertyName = "parent"+propertyName; //���ֶ���������
						String parentPropertyValue = null; //��Ÿ��ֶ����Ե�ֵ
						if(requestParams.get(parentPropertyName)!=null){ //���ֶ��Ƿ��в�ѯ����ֵ
							parentPropertyValue = requestParams.get(parentPropertyName);
						}
						//������������յ����������գ���queryParamValue�м���˲�ѯ����ֵ
						if(StringUtils.isNotBlank(parentPropertyValue)&& StringUtils.isBlank(propertyValue)){
							Long parentPropertyLongValue = Long.valueOf(parentPropertyValue);
							
							BaseObject pObject = (BaseObject) ms.findForeignTableEntity(fClassName, parentPropertyLongValue);
							queryParamValue.put(sysTableQueryColumn, pObject);
							//queryParamValue.put(sysTableQueryColumn, parentPropertyValue.trim());
						}
						//queryParamValue.put(parentPropertyName, parentPropertyValue);
					}

				}//end select
				else if(ct.getColumnTypeName().equalsIgnoreCase("dateText")){ //���Ϊ���������ֶΣ�����������Ҫ��ʾ��������
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//������������
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
						//��������
						String queryColumnDateValue = requestParams.get(propertyName);
						queryParamValue.put(propertyName, queryColumnDateValue);
						
					}
					
				}
			}else if(sysTableQueryColumn.isExtendColumn()){
				//SystemMainTableExtColumn smtec=sysTableQueryColumn.getExtendTableColumn();
				SystemMainTableColumnType ct = smtc.getSystemMainTableColumnType();//��չ�ֶ�����
				String columnTypeName = ct.getColumnTypeName();
				String propertyName = smtc.getPropertyName(); //������������
				String propertyValue = null; //����������ֵ
				if(StringUtils.isNotBlank(requestParams.get(propertyName))){
					propertyValue = requestParams.get(propertyName);
					
					queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
				}else{
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//�������򣬵�ǰ���Ա�null
					if(matchMode!=null&& !matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						continue;
					}
					
				}	
				if(columnTypeName.equalsIgnoreCase("text")){
					
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//������������
					if(matchMode!=null&& matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						queryParamValue.put(sysTableQueryColumn, null);
						queryParamValue.put(propertyNameBegin, requestParams.get(propertyNameBegin));
						queryParamValue.put(propertyNameEnd, requestParams.get(propertyNameEnd));
					}else{ //�˷�֧����ʡ��
						if(propertyValue!=null){
						queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
						}
					}

				}else if(columnTypeName.equalsIgnoreCase("textArea")){
					
					String matchMode = sysTableQueryColumn.getMatchModeStr();
					//������������
					if(matchMode!=null&& matchMode.equalsIgnoreCase("MATCH_MODE_BETWEEN")){
						String propertyNameBegin = propertyName+"Begin";
						String propertyNameEnd = propertyName+"End";
						queryParamValue.put(sysTableQueryColumn, null);
						queryParamValue.put(propertyNameBegin, requestParams.get(propertyNameBegin));
						queryParamValue.put(propertyNameEnd, requestParams.get(propertyNameEnd));
					}else{ //�˷�֧����ʡ��
						if(propertyValue!=null){
							queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
						}
					}

				}else if(columnTypeName.equalsIgnoreCase("select")||columnTypeName.equalsIgnoreCase("radio")){//if select
					if(propertyValue!=null){
						queryParamValue.put(sysTableQueryColumn, propertyValue.trim());
					}

				}//end select
				else if(ct.getColumnTypeName().equalsIgnoreCase("dateText")){ //���Ϊ���������ֶΣ�����������Ҫ��ʾ��������
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
	 * ���ȷ�����ۺ�������ѯ����
	 */
	public Page query(Class clazz, Map<String, Object> requestParams, 
						int pageNo, int pageSize, String orderProp, boolean isAsc) {
		//��ת��������ʽ��ʵ�����Ը�ʽ
		Map<String, Object> queryParams = this.genPropParams(clazz, requestParams);
		//���ò�ѯ������ṩparams���Բ�ѯ�ķ���
		Page page = queryService.queryByParams(clazz, queryParams, null, pageNo, pageSize, orderProp, isAsc);
		return page;
	}
	
	public List query(Class clazz, Map<String, Object> requestParams, String orderProp, boolean isAsc) {
		//��ת��������ʽ��ʵ�����Ը�ʽ
		Map<String, Object> queryParams = this.genPropParams(clazz, requestParams);
		//���ò�ѯ������ṩparams���Բ�ѯ�ķ���
		List list = queryService.queryByParams(clazz, queryParams, null, orderProp, isAsc);
		return list;
	}


	@SuppressWarnings("unchecked")
	public Map<String, Object> genPropParams(Class clazz, Map<String, Object> requestParams) {
		MetaDataService ms = (MetaDataService)ContextHolder.getBean("metaDataService");
		Map<String,Object> queryParamValue = new HashMap<String,Object>();
		
		SystemMainTable smt = systemMainTableService.findSystemMainTableByClazz(clazz);
		
		SystemTableQuery stq = userColumnService.findSystemTableQuery(smt); //ͨ��ϵͳ�����ȡϵͳ���ѯ
		//List list = ms.findUserQueryColumn(stq, true);
		//ͨ��ϵͳ��ѯ��ȡ��ǰ�û������в�ѯ�ֶ�
		List<Column> list = systemColumnService.findSystemTableColumns(smt);
		for(int i=0; i<list.size(); i++){
			Column column = (Column) list.get(i);
			SystemMainTableColumn columnMainOrExt=(SystemMainTableColumn)column;
			if(columnMainOrExt.getIsExtColumn()==SystemMainTableColumn.isMain){
				boolean isMatchModeBetween = false;
				SystemMainTableColumnType ct = column.getSystemMainTableColumnType();//���ֶ�����
				PropertyType pt = column.getPropertyType();
				String columnTypeName = ct.getColumnTypeName();
				String propertyTypeName = pt.getPropertyTypeName();
				String propertyName = column.getPropertyName(); //������������
				//System.out.println("propertyName: "+ propertyName);
				//System.out.println("propertyTypeName: "+ propertyName);
				
				Object columnQueryValue = requestParams.get(propertyName);
				//System.out.println("columnQueryValue: "+ columnQueryValue);
				if(columnQueryValue!=null&& !columnQueryValue.toString().equals("")){
					//System.out.println("dd");
					if(propertyTypeName.equalsIgnoreCase("Long")){//����������Long
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
					else if(propertyTypeName.equalsIgnoreCase("Integer")){//����������Integer
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
					else if(propertyTypeName.equalsIgnoreCase("Double")){//����������Double
						Double queryParamValueDouble = Double.valueOf(columnQueryValue.toString());
						queryParamValue.put(propertyName, queryParamValueDouble);
					}
					else if(propertyTypeName.equalsIgnoreCase("Date")){//����������Date
						if(columnQueryValue instanceof java.lang.String){
							String columnQueryValueString = (String) columnQueryValue;
							Date queryParamValueDate = DateUtil.convertStringToDate(columnQueryValueString);
							queryParamValue.put(propertyName, queryParamValueDate);
						}else{
							queryParamValue.put(propertyName, columnQueryValue);
						}
					}
					else if(propertyTypeName.equalsIgnoreCase("String")){//����������String
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
					else if(propertyTypeName.equalsIgnoreCase("BaseObject")){//����������String
						if(columnQueryValue instanceof BaseObject){
							queryParamValue.put(propertyName, columnQueryValue);
							
						}else if(columnQueryValue instanceof java.lang.String){
							
							if(columnQueryValue.toString().indexOf("_")==-1){ //�������idû���»���
								Long queryParamValueLong = Long.valueOf(columnQueryValue.toString());
								SystemMainTable foreignTable = column.getForeignTable();
								if(foreignTable==null){
									throw new ServiceException("�����ǰ����Ĺ���ʵ��ѡ�����������");
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
							}else{	//����id����_��Ҳ���Ƕ���»��߼������ʾ��
								Set<Long> paramValues = new HashSet<Long>();
								String propertyValues = columnQueryValue.toString();
								String[] propValues = propertyValues.split("_");
								for(String propValue : propValues){
									//begin
									Long queryParamValueLong = Long.valueOf(propValue.toString());
//									SystemMainTable foreignTable = column.getForeignTable();
//									if(foreignTable==null){
//										throw new ServiceException("�����ǰ����Ĺ���ʵ��ѡ�����������");
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
								throw new ServiceException("�����ǰ����Ĺ���ʵ��ѡ�����������");
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
								throw new ServiceException("�����ǰ����Ĺ���ʵ��ѡ�����������");
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
								throw new ServiceException("��ʼ����(������)�����ֶθ�ʽ����");
							}
						}else if(propertyTypeName.equalsIgnoreCase("Double")){//����
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
								throw new ServiceException("���������ֶθ�ʽ����");
							}
						}else{
							throw new ServiceException("�����ڻ�����ֶβ�����ʹ�������ѯ");
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
								throw new ServiceException("��ֹ����(������)�����ֶθ�ʽ����");
							}
						}else if(propertyTypeName.equalsIgnoreCase("Double")){//����
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
								throw new ServiceException("���������ֶθ�ʽ����");
							}
						}else{
							throw new ServiceException("�����ڻ�����ֶβ�����ʹ�������ѯ");
						}
					
					}
				}
			}
			else if(columnMainOrExt.getIsExtColumn()==SystemMainTableColumn.isExt){
				SystemMainTableColumnType ct = column.getSystemMainTableColumnType();//���ֶ�����
				PropertyType pt = column.getPropertyType();
				String columnTypeName = ct.getColumnTypeName();
				//String propertyTypeName = pt.getPropertyTypeName();
				String propertyName = column.getPropertyName(); //������������
				//String propertyValue = null; //����������ֵ
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
								throw new ServiceException("��ʼ����(������)�����ֶθ�ʽ����");
							}
						}else if(columnTypeName.equalsIgnoreCase("text")){//����
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
								throw new ServiceException("���������ֶθ�ʽ����");
							}
						}else{
							throw new ServiceException("�����ڻ�����ֶβ�����ʹ�������ѯ");
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
								throw new ServiceException("��ֹ����(������)�����ֶθ�ʽ����");
							}
						}else if(columnTypeName.equalsIgnoreCase("text")){//����
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
								throw new ServiceException("���������ֶθ�ʽ����");
							}
						}else{
							throw new ServiceException("�����ڻ�����ֶβ�����ʹ�������ѯ");
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
      	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//���ñ������¾���
      	     	
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
	     	
//			���������У�0��
	     	HSSFRow row=sheet.createRow(0);
	      	row.setHeight((short)400);			
	      	//���������е�ÿһ���ֶ�  	
	      	List allcolumns=userColumnService.findUserColumns(smt, UserTableSetting.EXPORT);
	      	for(int iii=0; iii<allcolumns.size(); iii++){
	      		UserTableSetting uts=(UserTableSetting)allcolumns.get(iii);
				SystemMainTableColumn mc = uts.getMainTableColumn();
				//SystemMainTableExtColumn mec=uts.getExtendTableColumn();
				if(mc.getIsExtColumn()==SystemMainTableColumn.isMain){
	                String columnCnName = mc.getColumnCnName();
					HSSFCell cell=row.createCell((short)iii); //���������¼�У��ӵ�1�п�ʼ *******
		     		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
		     		cell.setCellStyle(cellStyle);
		         	cell.setCellValue(columnCnName);
				}else if(mc.getIsExtColumn()==SystemMainTableColumn.isExt){
	                String columnCnName = mc.getColumnCnName();
					HSSFCell cell=row.createCell((short)iii); //���������¼�У��ӵ�1�п�ʼ *******
		     		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
		     		cell.setCellStyle(cellStyle);
		         	cell.setCellValue(columnCnName);
				}
				
			}
			for(int i=ii* 32767, rows=0; i<ii* 32767+32767&& i<totalCount && rows<32767; i++, rows++){
				
				BaseObject object = (BaseObject) mainList.get(i); //ȡ��list�е�ʵ��BaseObject
								
				HSSFRow rowj = null; //������ǰ��¼���� *******
				try {
					rowj = sheet.createRow((short) (rows + 1));
				} catch (Exception e) {
					e.printStackTrace();
					//System.out.println("aaa");
				}				
				
				Long mainTableRowId = object.getId(); //����ǰ��¼��id���кţ�
				
				BeanWrapperImpl bwMain = new BeanWrapperImpl(object); //ʹ��BeanWrapper��֤��ʵ��
				for(int j=0;j<allcolumns.size();j++){
					UserTableSetting uts=(UserTableSetting)allcolumns.get(j);
					SystemMainTableColumn mc = uts.getMainTableColumn();
					//SystemMainTableExtColumn mec=uts.getExtendTableColumn();
					Object mainPropValue = null;
					if(mc.getIsExtColumn()==SystemMainTableColumn.isMain){
						SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
						String typeName = mcType.getColumnTypeName();
						String pmainPropName = mc.getPropertyName(); //��ǰ�����Ե�����

						if(typeName.equalsIgnoreCase("radio")){
							SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
							String foreignValueColumnName = foreignValueColumn.getPropertyName();
							
							//��ʱmainPropValueΪ��������
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
							if(mainPropValue!=null){
//								ʹ��BeanWrapper��װ����ʵ��
								BeanWrapper bwForei = new BeanWrapperImpl(mainPropValue); 
								//��ȡ����ʵ����������Ե�ֵ
								String foreiTableNameValue = (String) bwForei.getPropertyValue(foreignValueColumnName);
//								����ֵ�滻mainPropValue
								mainPropValue = foreiTableNameValue;
							}

						}
						else if(typeName.equalsIgnoreCase("select")){
							SystemMainTable foreiTable = mc.getForeignTable();
							SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
							String foreignValueColumnName = foreignValueColumn.getPropertyName();
							//��ʱmainPropValueΪ��������
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
							if(mainPropValue!=null&& mainPropValue instanceof BaseObject){//Ϊ�ͻ�����Ӳ�Լ�����ж�
//								ʹ��BeanWrapper��װ����ʵ��
								BeanWrapper bwForei = new BeanWrapperImpl(mainPropValue); 
								//��ȡ����ʵ����������Ե�ֵ
								String foreiTableNameValue = (String) bwForei.getPropertyValue(foreignValueColumnName);
								Integer level = foreiTable.getLevel();
								if(level!=null&& level.intValue()==2){
									SystemMainTableColumn parentColumn = foreiTable.getParentColumn();
									String parentColumnName = parentColumn.getPropertyName();
									String parentColumnValue = (String) bwForei.getPropertyValue(parentColumnName+"."+foreignValueColumnName);
									foreiTableNameValue = parentColumnValue+"��"+foreiTableNameValue;//��
								}
								//����ֵ�滻mainPropValue
								mainPropValue = foreiTableNameValue;
							}

						}else if(typeName.equalsIgnoreCase("extSelect")){
							SystemMainTable foreiTable = mc.getForeignTable();
							SystemMainTableColumn foreignValueColumn = mc.getForeignTableValueColumn();
							String foreignValueColumnName = foreignValueColumn.getPropertyName();
							//��ʱmainPropValueΪ��������
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
							if(mainPropValue!=null&& mainPropValue instanceof BaseObject){//Ϊ�ͻ�����Ӳ�Լ�����ж�
//								ʹ��BeanWrapper��װ����ʵ��
								BeanWrapper bwForei = new BeanWrapperImpl(mainPropValue); 
								//��ȡ����ʵ����������Ե�ֵ
								String foreiTableNameValue = (String) bwForei.getPropertyValue(foreignValueColumnName);
								Integer level = foreiTable.getLevel();
								if(level!=null&& level.intValue()==2){
									SystemMainTableColumn parentColumn = foreiTable.getParentColumn();
									String parentColumnName = parentColumn.getPropertyName();
									String parentColumnValue = (String) bwForei.getPropertyValue(parentColumnName+"."+foreignValueColumnName);
									foreiTableNameValue = parentColumnValue+"��"+foreiTableNameValue;//��
								}
								//����ֵ�滻mainPropValue
								mainPropValue = foreiTableNameValue;
							}

						}else if(typeName.equalsIgnoreCase("multiSelect")){
							SystemMainTableColumn fValueColumn = mc.getForeignTableValueColumn();
							SystemMainTableColumn fKeyColumn = mc.getForeignTableKeyColumn();
							Object rObject = bwMain.getPropertyValue(pmainPropName); //��ȡ�����ö���ļ���
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
							//��ȡֱ�ӵ�����ֵ
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
							if(mainPropValue!=null){
								Integer intValue = Integer.valueOf(mainPropValue.toString());
								mainPropValue = intValue.intValue()==1?"��":"��";
							}else{ //����Ƿ��б������ֵΪnull����Ϊ��ʾ��
								mainPropValue = "";
							}
						}else if(typeName.equalsIgnoreCase("foreiText")){
							
						}else{
							mainPropValue = bwMain.getPropertyValue(pmainPropName);
						} 				
						
//						д�뵥Ԫ�� ************************************
						HSSFCell cellk=rowj.createCell((short)j);
		      			cellk.setEncoding(HSSFCell.ENCODING_UTF_16); 
		      			
		      			//�����������ݽ��и�ʽ��
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
						String pextPropName = mc.getPropertyName(); //��ǰ��չ���Ե�����
						String columnCnName = mc.getColumnCnName();
					    Integer selectTypeName=mc.getExtSelectType();//��ǰ��չ�ֶ�Դ���������չ��
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
							//��ȡֱ�ӵ�����ֵ
							Integer key=(Integer) map.get(pextPropName);
							if(key!=null){
								mainPropValue = key==1?"��":"��";
							}else{ //����Ƿ��б������ֵΪnull����Ϊ��ʾ��
								mainPropValue = "";
							}
						}else if(typeName.equalsIgnoreCase("yesNoRadio")){
							//��ȡֱ�ӵ�����ֵ
							Integer key=(Integer) map.get(pextPropName);
							if(key!=null){
								mainPropValue = key==1?"��":"��";
							}else{ //����Ƿ��б������ֵΪnull����Ϊ��ʾ��
								mainPropValue = "";
							}
						}else{
							String value=(String) map.get(pextPropName);
							mainPropValue=value;
						}
						
//						д�뵥Ԫ�� ************************************
						HSSFCell cellk=rowj.createCell((short)(j));
		      			cellk.setEncoding(HSSFCell.ENCODING_UTF_16); 
		      			
		      			//�����������ݽ��и�ʽ��
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
//			���Excel�ļ�����ʵ·��
      		excelFileName = filePrefix+"_"+System.currentTimeMillis() + ".xls";
      		String excelFullFileName = fileRootPath + FSP + excelFileName;
      		//deleteFile(excelFullFileName);//��ɾ��ԭ�ļ�
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
				requestParams.put(propertyName, columnWapper.getList()); //ѡ�е����userRole���еļ�¼
				requestParams.put(propertyName+"s", columnWapper.getSourceList()); //��ѡ������Դ
				
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
				//begin modify by peixf2�ڿ�ܷ����Ż�������������͵����ԣ�Ӧ��ʹ��id�����Ƕ����tostring��ʽ
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
				requestParams.put(propertyName, columnWapper.getList()); //ѡ�е����userRole���еļ�¼
				
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
				//key�Ǹ�����id��value�Ǹ�������
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
				//����������������ҳ��ֱ�Ӵ�������ȡֵ
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
				//begin modify by peixf2�ڿ�ܷ����Ż�������������͵����ԣ�Ӧ��ʹ��id�����Ƕ����tostring��ʽ
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
				requestParams.put(propertyName, columnWapper.getList()); //ѡ�е����userRole���еļ�¼
				requestParams.put(propertyName+"s", columnWapper.getSourceList()); //��ѡ������Դ
				
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
				//key�Ǹ�����id��value�Ǹ�������
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
				//����������������ҳ��ֱ�Ӵ�������ȡֵ
			}
		}
		return requestParams;
	}


	public Map<String, Object> getEntityDataForEdit(Class clazz, String objectId) {
		
		BaseObject objectEdit = (BaseObject) baseService.find(clazz, objectId, true);
		return this.getEntityDataForEdit(objectEdit);
		
	}

	//������
	public List<Object> getEntityDataForList(Class clazz, List mainList) {

		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		List sysMainColumns =this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isMain, smt);
		List sysExtColumns=this.systemMainAndExtColumnService.findColumnByIsExtAndSysMainTable(SystemMainTableColumn.isExt, smt);

		List<Object> mapList = new ArrayList<Object>();
		Iterator iter = mainList.iterator();
		while(iter.hasNext()){//����ÿ�м�¼��ȡ��list�е�ʵ��BaseObject item

			BaseObject object = (BaseObject) iter.next();
			Long mainTableRowId = object.getId(); //����ǰ��¼��id���кţ�

			//ȡ��ʵ������ֶ�
		
			for(int i=0; i<sysMainColumns.size(); i++){
				Object propertyValue = null;
				SystemMainTableColumn mc = (SystemMainTableColumn) sysMainColumns.get(i);
				SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = mc.getPropertyName(); //��ǰ�����Ե�����
				
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(mc);
				columnDataWrapper.initData(object);
				propertyValue = columnDataWrapper.getText();
				//multiSelect����ʾ
			
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
		while(iter.hasNext()){//����ÿ�м�¼��ȡ��list�е�ʵ��BaseObject item
			Map<String, Object> item = new HashMap<String, Object>(); //�洢ÿ����¼�����һ��ʵ�����
			BaseObject object = (BaseObject) iter.next();
			Long mainTableRowId = object.getId(); //����ǰ��¼��id���кţ�

			//ȡ��ʵ������ֶ�
		
			for(int i=0; i<sysMainColumns.size(); i++){
				Object propertyValue = null;
				SystemMainTableColumn mc = (SystemMainTableColumn) sysMainColumns.get(i);
				SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = mc.getPropertyName(); //��ǰ�����Ե�����				
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(mc);
				columnDataWrapper.initData(object);
				propertyValue = columnDataWrapper.getText();
				if(columnDataWrapper.getKey()!=null){
					Long key = columnDataWrapper.getKey();
					item.put(propertyName+"Id", key);
				}
				item.put(propertyName, propertyValue);
				
				//�����û����ֶε��û�����ʾ���⣬��������Ա/admin/�������롱
//				if(mc.getForeignTable()!=null){
//					String fclassName = mc.getForeignTable().getClassName();
//					if(fclassName.equalsIgnoreCase("com.zsgj.info.framework.security.entity.UserInfo")){
//						Object fObject = columnDataWrapper.getValue();
//						item.put("realNameAndDept", fObject.toString());
//					}
//				}
				//2�ڿ������δ��ֲ���
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
				String propertyName = mtec.getPropertyName(); //��ǰ�����Ե�����
				
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
		while(iter.hasNext()){//����ÿ�м�¼��ȡ��list�е�ʵ��BaseObject item
			Map<String, Object> item = new HashMap<String, Object>(); //�洢ÿ����¼�����һ��ʵ�����
			BaseObject object = (BaseObject) iter.next();
			Long mainTableRowId = object.getId(); //����ǰ��¼��id���кţ�

			//ȡ��ʵ������ֶ�
		
			for(int i=0; i<sysMainColumns.size(); i++){
				Object propertyValue = null;
				SystemMainTableColumn mc = (SystemMainTableColumn) sysMainColumns.get(i);
				SystemMainTableColumnType mcType = mc.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = mc.getPropertyName(); //��ǰ�����Ե�����				
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
				String propertyName = mtec.getPropertyName(); //��ǰ�����Ե�����
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
		Map<String,Object> map = new HashMap<String,Object>(); //������в�ѯ�ֶεĹ�������
		SystemMainTable smt = this.systemMainTableService.findSystemMainTableByClazz(clazz);
		SystemTableQuery stq = this.userColumnService.findSystemTableQuery(smt);//ע���̨�������ж�������ѯ
		//List list = ms.findUserQueryColumn(stq, true); //��ȡ�û��ɼ��Ĳ�ѯ�ֶ�
		//findUserQueryColumn(SystemTableQuery stq, boolean onlyShowVisible)
		List list = this.userColumnService.findUserQueryColumn(stq); //��ȡ�û��ɼ��Ĳ�ѯ�ֶ�
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
//					String fTableName = fTable.getTableName(); //��������ƣ���д��ͷ
//					SystemMainTableColumn fValueColumn = column.getForeignTableValueColumn();
//					SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();
//					String fClassName = column.getForeignTable().getClassName();
//					Class clazzRel = null;
//					try {
//						clazzRel = Class.forName(fClassName);
//					} catch (ClassNotFoundException e1) {
//						e1.printStackTrace();
//					}
//					//��ȡ�����������͵��������ݣ�����ҳ����ʾ�����������Ե������б����ڸ����б�����ʾ2���б�
//					if(fParentColumn==null) { //��ͨ�б�ֻ��ʾ�������һ���ֶ�
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
//					}else { //�����б���ʾ������������ֶκ͸��ֶ�
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
		List<UserTableQueryColumn> list = this.userColumnService.findUserQueryColumn(stq); //��ȡ�û��ɼ��Ĳ�ѯ�ֶ�
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
