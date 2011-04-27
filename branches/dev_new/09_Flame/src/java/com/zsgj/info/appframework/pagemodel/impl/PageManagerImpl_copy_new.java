package com.zsgj.info.appframework.pagemodel.impl;
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
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
//import com.digitalchina.info.appframework.pagemodel.service.PageModelPanelService;
//import com.digitalchina.info.appframework.pagemodel.service.PageModelService;
//import com.digitalchina.info.appframework.pagemodel.service.PagePanelService;
//import com.digitalchina.info.framework.exception.ServiceException;
//import com.digitalchina.info.framework.service.Service;
//import com.digitalchina.info.framework.util.BeanUtil;
//
//public class PageManagerImpl_copy_new implements PageManager {
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
//	 * ��ʱ�����Ǹ��������ֻ���Ƕ��һ��һ��һ���
//	 */
//	public Map<String,List<Map<String,Object>>> getPageModelDataForEdit(String model, String mainPanelDataId) {
//		if(StringUtils.isBlank(mainPanelDataId)){
//			throw new ServiceException("model main object id is needed");
//		}
//		Map<String,List<Map<String,Object>>> allResult = new HashMap<String,List<Map<String,Object>>>();
//		
//		Map<String,Object> result = new HashMap<String,Object>();
//		
//		//�洢����ͱ�������panel�еĶ���
//		Map<SystemMainTable,Object> loopedTableObject = new HashMap<SystemMainTable,Object>();
//		
//		PageModel pageModel = pageModelService.findPageModel(model);
//		if(pageModel==null) {
//			throw new ServiceException("model name not exist");
//		}
//		//ȡmodel���������������configItem
//		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
//		String tableName = modelMainTable.getTableName();
//		String mainTableClass = modelMainTable.getClassName();
//		Class mainClass = null;
//		try {
//			mainClass = Class.forName(mainTableClass);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
//		//ȡ���model�µ�����panel, ��configItemPanel,financePanel,customerPanel
//		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
//		for(PageModelPanel pmp : pmps){ 
//			//����һ��panel, �� configItemPanel
//			PagePanel pagePanel = pmp.getPagePanel(); //configItemPanel
//			PagePanelType pagePanelType = pagePanel.getXtype();
//			if(pagePanelType==null){
//				throw new ServiceException(pagePanel.getName()+"must select panel type");
//			}
//			String xtypeName = pagePanelType.getName();
//			//���panel�е���������
//			Set<SystemMainTable> panelTables = new HashSet<SystemMainTable>();
//			
//			//һ��panel�еĶ����¼�����List��
//			List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
//			
//			//���Ƿ������͵�panel
//			 if(pagePanel.getGroupFlag()!=null&& pagePanel.getGroupFlag().intValue()!=1|| pagePanel==null){//�û���ѡ��Ĭ�ϲ�����
//				
//				SystemMainTable panelMainTable = pagePanel.getSystemMainTable();
//				panelTables.add(panelMainTable);
//				
//				List<SystemMainTable> tables = pagePanelService.findMainTableByPanel(pagePanel);
//				panelTables.addAll(tables);
//				
//				for(SystemMainTable panelTable : panelTables){
//					if(panelTable==panelMainTable&& panelTable==modelMainTable){ //��ǰ�����ı����panel������,Ҳ��model������
//						//ͨ��������id��ȡ����Ķ���
//						String currentPanelTableClassName = panelTable.getClassName();
//						Class currentPanelTableClass = null;
//						Object currentPanelTableObject = null;
//						try {
//							currentPanelTableClass = Class.forName(currentPanelTableClassName);
//							currentPanelTableObject = service.find(currentPanelTableClass, mainPanelDataId);
//							//�ݴ�˱������Ķ���
//							loopedTableObject.put(panelTable, currentPanelTableObject);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}		
//						
//					}else if(panelTable!=panelMainTable){ //����ע���Ƿ�����ж����
//						String otherTableClass = panelTable.getClassName();
//						Class otherClass = null;
//						Object otherObject = null;
//						try {
//							otherClass = Class.forName(otherTableClass);
//							otherObject = otherClass.newInstance();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}		
//						//��ȡ���������������ֶ�
//						List<Column> columns = systemColumnService.findSystemTableColumns(panelTable);
//						for(Column column : columns){
//							PropertyType pt = column.getPropertyType();
//							String ptname = pt.getName();
//							if(ptname.equalsIgnoreCase("BaseObject")){
//								String propertyName = column.getPropertyName();
//								SystemMainTable ftable = column.getForeignTable();
//								String ftableName = ftable.getTableName();
//								String fclass = ftable.getClassName();
//								//panel��ĳ���ֶι�����panel������ͨ������Ķ���id��ȡpanel�����������
//								//if(fclass.equalsIgnoreCase(mainTableClass)){ 
//								//ȡ��ǰ��������Ѵ洢��������
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
//						}
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
//	 * ��ȡ��ǰ����е���������
//	 */
//	public Map<String, Object> getPageModelDataForEdit(String model, String panel, String mainPanelDataId) {
//		if(StringUtils.isBlank(mainPanelDataId)){
//			throw new ServiceException("panel main object id is needed!");
//		}
//		Map<String,Object> result = new HashMap<String,Object>();
//		//��ǰ���, ��configItemPanel
//		PagePanel pagePanel = pagePanelService.findPagePanel(panel);
//		if(pagePanel==null) {
//			throw new ServiceException("panel name not exist");
//		}
//		//��ǰ������������, configItem
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
//			result.putAll(map);//����ʵ��ļ�ֵ����Ϣ��������ֵ��
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		/*Set<SystemMainTable> smtSet = new HashSet<SystemMainTable>();
//		smtSet.add(panelSmt);*/
//		
//		//��ȡ��ǰ������漰����������
//		List<SystemMainTable> tables = pagePanelService.findMainTableByPanel(pagePanel);
//		for(SystemMainTable smt : tables){
//			if(smt!=panelSmt){ //����ע���Ƿ�����ж����
//				String otherTableClass = panelSmt.getClassName();
//				Class otherClass = null;
//				Object otherObject = null;
//				try {
//					otherClass = Class.forName(otherTableClass);
//					otherObject = otherClass.newInstance();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}		
//				//��ȡ���������������ֶ�
//				List<Column> columns = systemColumnService.findSystemTableColumns(smt);
//				for(Column column : columns){
//					PropertyType pt = column.getPropertyType();
//					String ptname = pt.getName();
//					if(ptname.equalsIgnoreCase("BaseObject")){
//						String propertyName = column.getPropertyName();
//						SystemMainTable ftable = column.getForeignTable();
//						String ftableName = ftable.getTableName();
//						String fclass = ftable.getClassName();
//						//panel��ĳ���ֶι�����panel������ͨ������Ķ���id��ȡpanel�����������
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
//		//ȡpanel�е����������������ֱ�ӹ���
//		List<SystemMainTable> list = pagePanelService.findMainTableByPanel(pagePanel);
//		smtSet.addAll(list);
//		for(SystemMainTable smt : smtSet){
//			Map objectMap = new HashMap(); //����һ�������������ֵ
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
