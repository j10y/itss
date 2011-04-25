//package com.digitalchina.info.appframework.pagemodel.impl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//
//import com.digitalchina.info.appframework.metadata.MetaDataManager;
//import com.digitalchina.info.appframework.metadata.entity.Column;
//import com.digitalchina.info.appframework.metadata.entity.PropertyType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
//import com.digitalchina.info.appframework.pagemodel.PageManager;
//import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
//import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanel;
//import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanelTable;
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTableRelation;
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
//import com.digitalchina.info.appframework.pagemodel.service.PageModelPanelService;
//import com.digitalchina.info.appframework.pagemodel.service.PageModelService;
//import com.digitalchina.info.appframework.pagemodel.service.PagePanelService;
//import com.digitalchina.info.framework.exception.ServiceException;
//import com.digitalchina.info.framework.service.Service;
//import com.digitalchina.info.framework.util.BeanUtil;
//
//public class PageManagerImpl_last_copy implements PageManager {
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
//	
////	递归调用获取panel中数据的方法，主要如果是否需要入口参数
//	private void findPagePanelParentData(
//									PagePanel pagePanel, 
//									SystemMainTable subSmt, 
//									List<Map<String,Object>> list,
//									Object currentObject){
//		
//		if(list.size()<=1){//说明panel不是grid panel
//			List<PagePanelTableRelation> ptrs = pagePanelService.findPanelTableRelBySub(pagePanel, subSmt);
//			for(PagePanelTableRelation ptr : ptrs){
//				SystemMainTable parentTable = ptr.getSystemMainTable();
//				String parentTableTableName = parentTable.getTableName();
//				String parentTableClassname = parentTable.getClassName();
//				Class parentTableClass = null;
//				SystemMainTableColumn parentTableColumn = ptr.getForeignTableColumn();
//				String stcPropName = parentTableColumn.getPropertyName();
//				try {
//					parentTableClass = Class.forName(parentTableClassname);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}	
//				//取出list中的map
//				Map<String,Object> listMap = list.iterator().next();
//				
//				BeanWrapper bw = new BeanWrapperImpl(currentObject);
//				Object parentObject = bw.getPropertyValue(stcPropName);
//				//这里注意父对象可能类型不确定，如配置项的客户分内外客户,功能留扩展
//				if(parentObject!=null){
//					bw.setWrappedInstance(parentObject);
//					Long parentObjectId = (Long) bw.getPropertyValue("id");
//					Object parentObjectFull = service.findUnique(parentTableClass, stcPropName, String.valueOf(parentObjectId));
//					Map subObjectMap = BeanUtil.object2Map(parentObjectFull, parentTableTableName);
//					listMap.putAll(subObjectMap);
//					//继续找父类
//					findPagePanelParentData(pagePanel, parentTable, list, parentObjectFull);
//				}
//				
//			}
//		}
//		
//	}
//	
//	//递归调用获取panel中数据的方法，主要如果是否需要入口参数, 复选的情况先不考虑
//	private void findPagePanelSubData(
//									PagePanel pagePanel, 
//									SystemMainTable parentSmt, 
//									List<Map<String,Object>> list,
//									Object currentObject){
//		
//		if(list.size()==1){//说明panel不是grid panel
//			List<PagePanelTableRelation> ptrs = pagePanelService.findPanelTableRelByParent(pagePanel, parentSmt);
//			for(PagePanelTableRelation ptr : ptrs){
//				SystemMainTable subTable = ptr.getSystemMainTable();
//				String subTableName = subTable.getTableName();
//				String subTableClassname = subTable.getClassName();
//				Class subTableClass = null;
//				SystemMainTableColumn subTableColumn = ptr.getForeignTableColumn();
//				String stcPropName = subTableColumn.getPropertyName();
//				try {
//					subTableClass = Class.forName(subTableClassname);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}	
//				//取出list中的map
//				Map<String,Object> listMap = list.iterator().next();
//				
//				List<Object> subObjects = service.find(subTableClass, stcPropName, currentObject);
//				if(!subObjects.isEmpty()){
//					if(subObjects.size()==1){
//						Object subObject = subObjects.iterator().next();
//						Map subObjectMap = BeanUtil.object2Map(subObject, subTableName);
//						listMap.putAll(subObjectMap);
//						//继续找子数据
//						findPagePanelSubData(pagePanel, subTable, list, subObject);
//					}else{ 
//						if(list.isEmpty()){ //肯定是通过外键返回列表
//							List<Map<String,Object>> tmp = BeanUtil.listObject2Map(subObjects, subTableName);
//							list.addAll(tmp);
//						}
//						
//					}
//				}
//			}
//		}
//		
//	}
//	
//	/**
//	 * 暂时不考虑复杂情况，只考虑多对一和一对一情况
//	 */
//	public Map<String,List<Map<String,Object>>> getPageModelDataForEdit(String model, String mainPanelDataId) {
//		if(StringUtils.isBlank(mainPanelDataId)){
//			throw new ServiceException("model main object id is needed");
//		}
//		Map<String,List<Map<String,Object>>> allResult = new HashMap<String,List<Map<String,Object>>>();
//		
//		Map<String,Object> result = new HashMap<String,Object>();
//		
//		//存储主表和遍历过的panel中的对象
//		Map<SystemMainTable,Object> loopedTableObject = new HashMap<SystemMainTable,Object>();
//		Map<SystemMainTable,Object> notLoopedTableObject = new HashMap<SystemMainTable,Object>();
//		Map<PagePanel,Object> loopedPanels = new HashMap<PagePanel,Object>();
//		
//		PageModel pageModel = pageModelService.findPageModel(model);
//		if(pageModel==null) {
//			throw new ServiceException("page model '"+model+"' not exist");
//		}
//		//取model的主表，如配置项表configItem
//		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
//		String tableName = modelMainTable.getTableName();
//		String mainTableClass = modelMainTable.getClassName();
//		Class mainClass = null;
//		try {
//			mainClass = Class.forName(mainTableClass);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
//		//取这个model下的所有panel, 如configItemPanel,financePanel,customerPanel
//		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
//		for(PageModelPanel pmp : pmps){ 
//			//遍历一个panel, 如 configItemPanel
//			PagePanel pagePanel = pmp.getPagePanel(); //configItemPanel
//			
//			PagePanelType pagePanelType = pagePanel.getXtype();
//			if(pagePanelType==null){
//				throw new ServiceException(pagePanel.getName()+" must select panel type");
//			}
//			String xtypeName = pagePanelType.getName();
//			//存放panel中的所有主表
//			Set<SystemMainTable> panelTables = new HashSet<SystemMainTable>();
//			
//			//一个panel中的多个记录存放在List中
//			List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
//			
//			//不是分组类型的panel
//			 if(pagePanel.getGroupFlag()!=null&& pagePanel.getGroupFlag().intValue()!=1|| pagePanel==null){//用户不选择默认不分组
//				
//				SystemMainTable panelMainTable = pagePanel.getSystemMainTable();
//				panelTables.add(panelMainTable);
//				
//				List<SystemMainTable> tables = pagePanelService.findMainTableByPanel(pagePanel);
//				panelTables.addAll(tables);
//				
//				for(SystemMainTable panelTable : panelTables){
//					if(panelTable==panelMainTable&& panelTable==modelMainTable){ //当前遍历的表就是panel的主表,也是model的主表
//						//通过主对象id获取主表的对象
//						String panelTableName = panelTable.getTableName();
//						String currentPanelTableClassName = panelTable.getClassName();
//						Class panelClass = null;
//						Object parentMainObject = null;
//						try {
//							panelClass = Class.forName(currentPanelTableClassName);
//							parentMainObject = service.find(panelClass, mainPanelDataId);
//							if(parentMainObject==null){
//								throw new ServiceException("not object fount");
//							}
//							Map panelMainObjectMap = BeanUtil.object2Map(parentMainObject, panelTableName);
//							panelList.add(panelMainObjectMap);
//							//获取其父的数据
//							this.findPagePanelParentData(pagePanel, panelTable, panelList, parentMainObject);
//							//获取panel本身的子数据
//							this.findPagePanelSubData(pagePanel, panelTable, panelList, parentMainObject);
//							//向返回结果中增加panel数据
//							allResult.put(pagePanel.getName(), panelList);
//							
//							//暂存此遍历过的对象
//							loopedTableObject.put(panelTable, parentMainObject);
//							loopedPanels.put(pagePanel, parentMainObject);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}		
//						
//						//获取引用panelMainTable的表的数据，如财务信息引用基础信息，此处获取财务数据
//						List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel, 
//								pagePanel, panelTable);
//						for(PageModelPanelTable item: pmptSubs){
//							//loop to financePanel.configItem
//							PagePanel subPagePanel = item.getParentPagePanel();
//							SystemMainTable subPanelTable = item.getSubPanelTable();
//							String subPanelTableName = subPanelTable.getTableName();
//							SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
//							String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
//							String subPanelClassName = subPanelTable.getClassName();
//							Class subPanelClass = null;
//							Object subPanelObject = null;
//							panelList = null;
//							try {
//								subPanelClass = Class.forName(subPanelClassName);
//								Object parentTableObject = loopedTableObject.get(panelMainTable);
//								List<Object> subObjects = service.find(subPanelClass, subPanelTableFPropName, parentTableObject);
//								if(!subObjects.isEmpty()){
//									if(subObjects.size()==1){
//										Map<String,Object> listMap = panelList.iterator().next();
//										Object subObject = subObjects.iterator().next();
//										Map subObjectMap = BeanUtil.object2Map(subObject, subPanelTableName);
//										listMap.putAll(subObjectMap);
//									}else{
//										
//									}
//								}
//								
//								if(subPagePanel.getXtype()!=null&& subPagePanel.getXtype().getName().equalsIgnoreCase("form")){
//									panelList = service.find(subPanelClass, subPanelTableFPropName, parentTableObject);
//								}else if(subPagePanel.getXtype()!=null&& subPagePanel.getXtype().getName().equalsIgnoreCase("grid")){
//									panelList = service.find(subPanelClass, subPanelTableFPropName, parentTableObject);
//								}
//							
//							} catch (Exception e) {
//								e.printStackTrace();
//							}		
//							
//						}
//						
//					}else if(panelTable!=panelMainTable){ //这里注意是否可以判断相等
//						String otherTableClass = panelTable.getClassName();
//						Class otherClass = null;
//						Object otherObject = null;
//						try {
//							otherClass = Class.forName(otherTableClass);
//							otherObject = otherClass.newInstance();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						//获取引用panelMainTable的表的数据，如财务信息引用基础信息，此处获取财务数据
//						List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel, 
//								pagePanel, panelTable);
//						for(PageModelPanelTable item: pmptSubs){
//							//loop to financePanel.configItem
//							PagePanel subPagePanel = item.getParentPagePanel();
//							SystemMainTable subPanelTable = item.getSubPanelTable();
//							SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
//							String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
//							String subPanelClassName = subPanelTable.getClassName();
//							Class subPanelClass = null;
//							Object subPanelObject = null;
//							try {
//								subPanelClass = Class.forName(subPanelClassName);
//								Object parentTableObject = loopedTableObject.get(panelMainTable);
//								if(subPagePanel.getXtype()!=null&& subPagePanel.getXtype().getName().equalsIgnoreCase("form")){
//									subPanelObject = service.findUnique(subPanelClass, subPanelTableFPropName, parentTableObject);
//								}else if(subPagePanel.getXtype()!=null&& subPagePanel.getXtype().getName().equalsIgnoreCase("grid")){
//									subPanelObject = service.find(subPanelClass, subPanelTableFPropName, parentTableObject);
//								}
//							
//							} catch (Exception e) {
//								e.printStackTrace();
//							}							
//							
//						}
//						//获取panelMainTable引用的表的数据，如基础信息引用财务信息，此处获取关联的财务信息
//						List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, 
//								pagePanel, panelTable); //此句将发现财务信息
//						/*//获取这个其他表的所有字段
//						List<Column> columns = systemColumnService.findSystemTableColumns(panelTable);
//						for(Column column : columns){
//							PropertyType pt = column.getPropertyType();
//							String ptname = pt.getName();
//							if(ptname.equalsIgnoreCase("BaseObject")){
//								String propertyName = column.getPropertyName();
//								SystemMainTable ftable = column.getForeignTable();
//								String ftableName = ftable.getTableName();
//								String fclass = ftable.getClassName();
//								//panel的某个字段关联到panel主表，则通过主表的对象id获取panel其他表的数据
//								//if(fclass.equalsIgnoreCase(mainTableClass)){ 
//								//取出前面遍历过已存储的主对象
//								Object mainObject = loopedTableObject.get(ftable);
//								if(mainObject!=null){
//									if(xtypeName.equalsIgnoreCase("editorgrid")){
//										List list = service.find(otherClass, propertyName, mainObject);
//										
//									}else if(xtypeName.equalsIgnoreCase("form")){
//										List list = service.find(otherClass, propertyName, mainObject);
//										if(!list.isEmpty()){
//											otherObject = list.iterator().next();
//											Map map = BeanUtil.object2Map(otherObject, ftableName);
//											result.putAll(map);
//										}
//									}
//									
//								}
//								
//									
//								//}
//							}
//							
//						}*/
//					}
//				}
//				
//				
//				
//				
//			 }
//			
//		}
//		
//			
//		
//		return allResult;
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
