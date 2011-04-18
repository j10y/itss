//package com.digitalchina.info.appframework.pagemodel.impl;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.digitalchina.info.appframework.metadata.MetaDataManager;
//import com.digitalchina.info.appframework.metadata.entity.Column;
//import com.digitalchina.info.appframework.metadata.entity.PropertyType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
//import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
//import com.digitalchina.info.appframework.pagemodel.PageManager;
//import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
//import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanel;
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
//import com.digitalchina.info.appframework.pagemodel.service.PageModelPanelService;
//import com.digitalchina.info.appframework.pagemodel.service.PageModelService;
//import com.digitalchina.info.appframework.pagemodel.service.PagePanelService;
//import com.digitalchina.info.framework.exception.ServiceException;
//import com.digitalchina.info.framework.service.Service;
//import com.digitalchina.info.framework.util.BeanUtil;
//
//
///*String panelTableClass = panelSmt.getClassName();
////不是主panel，因为数据是使用表前缀加属性名称，故不需要panel名称，否则都需要在遍历panel的循环中进行
//if(!panelTableClass.equalsIgnoreCase(mainTableClass)){ //非configItem，可能是financePanel, customerPanel
//	//panel中的主表，financePanel
//	String panelTable = panelSmt.getTableName(); //financePanel表
//	Class panelClass = null; //
//	Object panelObject = null;
//	try {
//		panelClass = Class.forName(panelTableClass);
//		//panelObject = panelClass.newInstance();
//		//临时存放非主表的对象，如financePanel	
//		//
//	} catch (Exception e) {
//		e.printStackTrace();
//	}		
//	
//	//取当前遍历的panel的主表的所有字段,注意是主表，如去配置项的基本信息表的字段，不考虑扩展表的字段
//	List<Column> panelMainTableColumns = systemColumnService.findSystemTableColumns(panelSmt);
//	for(Column column : panelMainTableColumns){
//		PropertyType pt = column.getPropertyType();
//		String ptname = pt.getName();
//		if(ptname.equalsIgnoreCase("BaseObject")){
//			SystemMainTable ftable = column.getForeignTable();
//			String fclass = ftable.getClassName();
//			if(fclass.equalsIgnoreCase(panelTableClass)){ //panel中外键引用
//				
//				String propertyName = column.getPropertyName();
//				String ftableName = ftable.getTableName();
//				
////				List list = service.find(panelClass, propertyName, mainObject);
////				if(!list.isEmpty()){
////					panelObject = list.iterator().next();
////					Map map = BeanUtil.object2Map(panelObject, ftableName);
////					result.putAll(map);
////				}
//				
//			}else{
//				
//			}
//		}
//		
//	}
//}*/
//public class PageManagerImpl_copy implements PageManager {
//	private Service service;
//	private MetaDataManager metaDataManager;
//	private PageModelService pageModelService;
//	private PagePanelService pagePanelService;
//	private PageModelPanelService pageModelPanelService;
//	private SystemColumnService systemColumnService;
//	
//	public PageModel findPageModel(String pageKeyName) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public Map<String, Object> getPageModelDataForEdit(String model, String[] panelObjectIds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	private Map<SystemMainTable,Integer> getMainTableSorted(PagePanel pagePanel){
//		Map<SystemMainTable,Integer> map = new HashMap<SystemMainTable,Integer>();
//		SystemMainTable panelSmt = pagePanel.getSystemMainTable();
//		if(panelSmt!=null){
//			map.put(panelSmt, 1);
//		}
//		List<SystemMainTable> tables = pagePanelService.findMainTableByPanel(pagePanel);
//		for(SystemMainTable smt : tables){
//			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
//			
//		}
//		return map;
//	}
//	
//	/**
//	 * 暂时不考虑复杂情况，只考虑多对一和一对一情况
//	 */
//	public Map<String, Object> getPageModelDataForEdit(String model, String mainPanelDataId) {
//		if(StringUtils.isBlank(mainPanelDataId)){
//			throw new ServiceException("model main object id is needed");
//		}
//		Map<String,List<Map<String,Object>>> allResult = new HashMap<String,List<Map<String,Object>>>();
//		
//		Map<String,Object> result = new HashMap<String,Object>();
//		
//		//存储主表和遍历过的panel中的对象
//		Map<SystemMainTable,Object> loopedTableObject = new HashMap<SystemMainTable,Object>();
//		
//		PageModel pageModel = pageModelService.findPageModel(model);
//		if(pageModel==null) {
//			throw new ServiceException("model name not exist");
//		}
//		//取model的主表，如配置项表configItem
//		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
//		String tableName = modelMainTable.getTableName();
//		String mainTableClass = modelMainTable.getClassName();
//		Class mainClass = null;
//		Object mainObject = null;
//		try {
//			mainClass = Class.forName(mainTableClass);
//			mainObject = service.find(mainClass, mainPanelDataId, true);
//			//临时存放主表的对象
//			loopedTableObject.put(modelMainTable, mainObject);
//			
//			Map map = BeanUtil.object2Map(mainObject, tableName);
//			result.putAll(map);//将主实体的键值对信息放入结果键值对
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
//		//取这个model下的所有panel, 如configItemPanel,financePanel,customerPanel
//		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
//		for(PageModelPanel pmp : pmps){ 
//			//遍历一个panel, 如 configItemPanel
//			PagePanel pagePanel = pmp.getPagePanel(); //configItemPanel
//			//不是分组类型的panel
//			 if(pagePanel.getGroupFlag()!=null&& pagePanel.getGroupFlag().intValue()!=1|| pagePanel==null){//用户不选择默认不分组
//				SystemMainTable panelSmt = pagePanel.getSystemMainTable();
//				String panelTableClass = panelSmt.getClassName();
//				//不是主panel，因为数据是使用表前缀加属性名称，故不需要panel名称，否则都需要在遍历panel的循环中进行
//				if(!panelTableClass.equalsIgnoreCase(mainTableClass)){ //非configItem，可能是financePanel, customerPanel
//					//panel中的主表，financePanel
//					String panelTable = panelSmt.getTableName(); //financePanel表
//					Class panelClass = null; //
//					Object panelObject = null;
//					try {
//						panelClass = Class.forName(panelTableClass);
//						//panelObject = panelClass.newInstance();
//						//临时存放非主表的对象，如financePanel
//						//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}		
//					
//					//取当前遍历的panel的主表的所有字段,注意是主表，如去配置项的基本信息表的字段，不考虑扩展表的字段
//					List<Column> panelMainTableColumns = systemColumnService.findSystemTableColumns(panelSmt);
//					for(Column column : panelMainTableColumns){
//						PropertyType pt = column.getPropertyType();
//						String ptname = pt.getName();
//						if(ptname.equalsIgnoreCase("BaseObject")){
//							SystemMainTable ftable = column.getForeignTable();
//							String fclass = ftable.getClassName();
//							if(fclass.equalsIgnoreCase(panelTableClass)){ //panel中外键引用
//								
//								String propertyName = column.getPropertyName();
//								String ftableName = ftable.getTableName();
//								
//								List list = service.find(panelClass, propertyName, mainObject);
//								if(!list.isEmpty()){
//									panelObject = list.iterator().next();
//									Map map = BeanUtil.object2Map(panelObject, ftableName);
//									result.putAll(map);
//								}
//								
//							}else{
//								
//							}
//						}
//						
//					}
//				}
//				
//				
//				
//			 }
//			
//		}
//		
//			
//		
//		return null;
//	}
//	
//	/**
//	 * 获取当前面板中的所有数据
//	 */
//	public Map<String, Object> getPageModelDataForEdit(String model, String panel, String mainPanelDataId) {
//		if(StringUtils.isBlank(mainPanelDataId)){
//			throw new ServiceException("panel main object id is needed!");
//		}
//		Map<String,Object> result = new HashMap<String,Object>();
//		//当前面板, 如configItemPanel
//		PagePanel pagePanel = pagePanelService.findPagePanel(panel);
//		if(pagePanel==null) {
//			throw new ServiceException("panel name not exist");
//		}
//		//当前面板操作的主表, configItem
//		SystemMainTable panelSmt = pagePanel.getSystemMainTable();
//		String tableName = panelSmt.getTableName();
//		String mainTableClass = panelSmt.getClassName();
//		Class mainClass = null;
//		Object mainObject = null;
//		try {
//			mainClass = Class.forName(mainTableClass);
////			mainObject = mainClass.newInstance();
////			BeanWrapper bw = new BeanWrapperImpl(mainObject);
////			bw.setPropertyValue("id", Long.valueOf(mainPanelDataId));
//			mainObject = service.find(mainClass, mainPanelDataId, true); //configItem Object
//			Map map = BeanUtil.object2Map(mainObject, tableName); //configItem map
//			result.putAll(map);//将主实体的键值对信息放入结果键值对
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		/*Set<SystemMainTable> smtSet = new HashSet<SystemMainTable>();
//		smtSet.add(panelSmt);*/
//		
//		//获取当前面板中涉及的所有主表
//		List<SystemMainTable> tables = pagePanelService.findMainTableByPanel(pagePanel);
//		for(SystemMainTable smt : tables){
//			if(smt!=panelSmt){ //这里注意是否可以判断相等
//				String otherTableClass = panelSmt.getClassName();
//				Class otherClass = null;
//				Object otherObject = null;
//				try {
//					otherClass = Class.forName(otherTableClass);
//					otherObject = otherClass.newInstance();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}		
//				//获取这个其他表的所有字段
//				List<Column> columns = systemColumnService.findSystemTableColumns(smt);
//				for(Column column : columns){
//					PropertyType pt = column.getPropertyType();
//					String ptname = pt.getName();
//					if(ptname.equalsIgnoreCase("BaseObject")){
//						String propertyName = column.getPropertyName();
//						SystemMainTable ftable = column.getForeignTable();
//						String ftableName = ftable.getTableName();
//						String fclass = ftable.getClassName();
//						//panel的某个字段关联到panel主表，则通过主表的对象id获取panel其他表的数据
//						if(fclass.equalsIgnoreCase(mainTableClass)){ 
//							List list = service.find(otherClass, propertyName, mainObject);
//							if(!list.isEmpty()){
//								otherObject = list.iterator().next();
//								Map map = BeanUtil.object2Map(otherObject, ftableName);
//								result.putAll(map);
//							}
//							
//						}
//					}
//					
//				}
//			}
//			
//			
//		}
//		
//		return result;
//	}
//
//	public void savePageModelData(String model, String panel,
//			Map<String, Object> columnDataMap) {
//		PageModel pageModel = pageModelService.findPageModel(model);
//		PagePanel pagePanel = pagePanelService.findPagePanel(panel);
//		SystemMainTable panelSmt = pagePanel.getSystemMainTable();
//		Set<SystemMainTable> smtSet = new HashSet<SystemMainTable>();
//		smtSet.add(panelSmt);
//		
//		//取panel中的所有主表，多个主表直接关联
//		List<SystemMainTable> list = pagePanelService.findMainTableByPanel(pagePanel);
//		smtSet.addAll(list);
//		for(SystemMainTable smt : smtSet){
//			Map objectMap = new HashMap(); //保存一个表的所有属性值
//			Class clazz = null;
//			String className = smt.getClassName();
//			//BeanWrapper bw = new BeanWrapperImpl();
//			//Object object = null;
//			try {
//				clazz = Class.forName(className);
//				//object = clazz.newInstance();
//				//bw.setWrappedInstance(object);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}	
//			//SystemMainTableColumn smtc = smt.getPrimaryKeyColumn();
//			//String pkColumnName = smtc.getPropertyName();
//			String tableName = smt.getTableName();
//			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
//			for(Column column : columns){
//				String propertyName = column.getPropertyName();
//				String tableColumnName = tableName+"$"+propertyName;
//				if(columnDataMap.containsKey(tableColumnName)){
//					Object columnValue = columnDataMap.get(tableColumnName);
//					//bw.setPropertyValue(propertyName, columnValue);
//					objectMap.put(propertyName, columnValue);
//				}
//			}
//			metaDataManager.saveEntityData(clazz, objectMap);
//			
//		}
//	}
//
//	public void setMetaDataManager(MetaDataManager metaDataManager) {
//		this.metaDataManager = metaDataManager;
//	}
//
//	public void setPageModelPanelService(PageModelPanelService pageModelPanelService) {
//		this.pageModelPanelService = pageModelPanelService;
//	}
//
//	public void setPageModelService(PageModelService pageModelService) {
//		this.pageModelService = pageModelService;
//	}
//
//	public void setPagePanelService(PagePanelService pagePanelService) {
//		this.pagePanelService = pagePanelService;
//	}
//
//	public void setService(Service service) {
//		this.service = service;
//	}
//
//	public void setSystemColumnService(SystemColumnService systemColumnService) {
//		this.systemColumnService = systemColumnService;
//	}
//
//}
