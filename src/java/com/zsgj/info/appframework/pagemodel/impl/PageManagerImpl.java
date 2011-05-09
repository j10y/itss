package com.zsgj.info.appframework.pagemodel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.ColumnDataWrapper;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableQueryColumn;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.appframework.pagemodel.PageQueryService;
import com.zsgj.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelType;
import com.zsgj.info.appframework.pagemodel.service.PageGroupPanelService;
import com.zsgj.info.appframework.pagemodel.service.PageModelPanelService;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelColumnService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelTableService;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.BeanUtil;

public class PageManagerImpl implements PageManager {
	private Service service;
	private MetaDataManager metaDataManager;
	private PageModelService pageModelService;
	private PagePanelService pagePanelService;
	private PageModelPanelService pageModelPanelService;
	private PagePanelColumnService pagePanelColumnService;
	private SystemColumnService systemColumnService;
	private PagePanelTableService pagePanelTableService;
	private PageQueryService pageQueryService;
	private PageGroupPanelService pageGroupPanelService;

	public PageModel findPageModel(String pageKeyName) {
		return pageModelService.findPageModel(pageKeyName);
	}

	public void setPageGroupPanelService(PageGroupPanelService pageGroupPanelService) {
		this.pageGroupPanelService = pageGroupPanelService;
	}

	public Map<String, Object> getPageModelDataForEdit(String model, String[] panelObjectIds) {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<SystemMainTable,Integer> getMainTableSorted(PagePanel pagePanel){
		Map<SystemMainTable,Integer> map = new HashMap<SystemMainTable,Integer>();
		SystemMainTable panelSmt = pagePanel.getSystemMainTable();
		if(panelSmt!=null){
			map.put(panelSmt, 1);
		}
		List<SystemMainTable> tables = pagePanelService.findMainTableByPanel(pagePanel);
		for(SystemMainTable smt : tables){
			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
			
		}
		return map;
	}
	
	
//	递归调用获取panel中数据的方法，主要如果是否需要入口参数
	private void findPagePanelParentData(
									PagePanel pagePanel, 
									SystemMainTable subSmt, 
									List<Map<String,Object>> list,
									Object currentObject){
		
		if(list.size()<=1){//说明panel不是grid panel
			String tableName = subSmt.getTableName();
			List<PagePanelTableRelation> ptrs = pagePanelService.findPanelTableRelBySub(pagePanel, subSmt);
			for(PagePanelTableRelation ptr : ptrs){
				SystemMainTable parentTable = ptr.getForeignTable();
				String parentTableTableName = parentTable.getTableName();
				String parentTableClassname = parentTable.getClassName();
				Class parentTableClass = null;
				SystemMainTableColumn parentTableColumn = ptr.getForeignTableColumn();
				String stcPropName = parentTableColumn.getPropertyName();
				PropertyType stcPt = parentTableColumn.getPropertyType();
				String propTypeName = stcPt.getPropertyTypeName();
				try {
					parentTableClass = Class.forName(parentTableClassname);
				} catch (Exception e) {
					e.printStackTrace();
				}	
				//取出list中的map
				Map<String,Object> listMap = list.iterator().next();
				
				BeanWrapper bw = new BeanWrapperImpl(currentObject);
				Object parentObject = bw.getPropertyValue(stcPropName);
				//这里注意父对象可能类型不确定，如配置项的客户分内外客户,功能留扩展
				if(parentObject!=null){
					bw.setWrappedInstance(parentObject);
					Long parentObjectId = (Long) bw.getPropertyValue("id");
					Object parentObjectFull = service.findUnique(parentTableClass, "id", parentObjectId);
					Map subObjectMap = metaDataManager.getEntityDataForEdit(parentObjectFull, parentTableTableName);
						//BeanUtil.object2Map(parentObjectFull, parentTableTableName);
					listMap.putAll(subObjectMap);
					//继续找父类
					findPagePanelParentData(pagePanel, parentTable, list, parentObjectFull);
				}
				
			}
		}
		
	}
	
	//递归调用获取panel中数据的方法，主要如果是否需要入口参数, 复选的情况先不考虑
	private void findPagePanelSubData(
									PagePanel pagePanel, 
									SystemMainTable parentSmt, 
									List<Map<String,Object>> list,
									Object currentObject){
		
		if(list.size()==1){//说明panel不是grid panel
			List<PagePanelTableRelation> ptrs = pagePanelService.findPanelTableRelByParent(pagePanel, parentSmt);
			for(PagePanelTableRelation ptr : ptrs){
				SystemMainTable subTable = ptr.getSystemMainTable();
				String subTableName = subTable.getTableName();
				String subTableClassname = subTable.getClassName();
				Class subTableClass = null;
				SystemMainTableColumn subTableColumn = ptr.getForeignTableColumn();
				String stcPropName = subTableColumn.getPropertyName();
				try {
					subTableClass = Class.forName(subTableClassname);
				} catch (Exception e) {
					e.printStackTrace();
				}	
				//取出list中的map
				Map<String,Object> listMap = list.iterator().next();
				
				List<Object> subObjects = service.find(subTableClass, stcPropName, currentObject);
				if(!subObjects.isEmpty()){
					if(subObjects.size()==1){
						Object subObject = subObjects.iterator().next();
						Map subObjectMap = metaDataManager.getEntityDataForEdit(subObject, subTableName);
							//BeanUtil.object2Map(subObject, subTableName);
						listMap.putAll(subObjectMap);
						//继续找子数据
						findPagePanelSubData(pagePanel, subTable, list, subObject);
					}else{ 
						if(list.isEmpty()){ //肯定是通过外键返回列表
							for(Object subObject : subObjects){
								Map subObjectMap = metaDataManager.getEntityDataForEdit(subObject, subTableName);
								list.add(subObjectMap);
							}
							//List<Map<String,Object>> tmp = BeanUtil.listObject2Map(subObjects, subTableName);
							//list.addAll(tmp);
						}
						
					}
				}
			}
		}
		
	}
	
	/**
	 * 通过model和主对象id抓取所有面板数据。
	 * 目前只实现多对一和一对一关联
	 */
	public Map<String,List<Map<String,Object>>> getPageModelDataForEdit(String model, String mainPanelDataId) {
		if(StringUtils.isBlank(mainPanelDataId)){
			throw new ServiceException("model main object id is needed");
		}
		Map<String,List<Map<String,Object>>> allResult = new HashMap<String,List<Map<String,Object>>>();
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		//存储主表和遍历过的panel中的对象
		Map<SystemMainTable,Object> loopedTableObject = new HashMap<SystemMainTable,Object>();
		Map<SystemMainTable,Object> notLoopedTableObject = new HashMap<SystemMainTable,Object>();
		Map<PagePanel,Object> loopedPanels = new HashMap<PagePanel,Object>();
		
		PageModel pageModel = pageModelService.findPageModel(model);
		if(pageModel==null) {
			throw new ServiceException("page model '"+model+"' not exist");
		}
		//取model的主表，如配置项表configItem
		PagePanel mainPanel = null;
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		if(modelMainTable==null){
			throw new ServiceException("page model '"+model+"' need systemMainTable");
		}
	
		mainPanel = pageModel.getMainPagePanel();
		//取这个model下的所有panel, 如configItemPanel,financePanel,customerPanel
//		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
//		for(PageModelPanel pmp : pmps){ 
//			//遍历一个panel, 如 configItemPanel
//			PagePanel pagePanel = pmp.getPagePanel(); //configItemPanel
//			SystemMainTable panelMainTable = pagePanel.getSystemMainTable();
//			if(panelMainTable==modelMainTable){
//				mainPanel = pagePanel;
//				break;
//			}
//		}
		//取panel的类型
		PagePanelType pagePanelType = mainPanel.getXtype();
		if(pagePanelType==null){
			throw new ServiceException(mainPanel.getName()+" must select panel type");
		}
		String xtypeName = pagePanelType.getName();
		
		//存放主panel中的数据
		List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
		
		//通过主对象id获取主表的对象
		String panelTableName = modelMainTable.getTableName();
		String panelTableClsName = modelMainTable.getClassName();
		Class panelClass = null;
		Object parentMainObject = null;
		try {
			panelClass = Class.forName(panelTableClsName);
			//主对象
			parentMainObject = service.find(panelClass, mainPanelDataId, true); //其实这里扩展字段无法获取
			if(parentMainObject==null){
				throw new ServiceException("not object fount");
			}
			//将主对象转成Map格式，此方法可以将扩展数据也带出来，同时返回的key追加了表前缀
			Map panelMainObjectMap = metaDataManager.getEntityDataForEdit(parentMainObject, panelTableName);
			//Map panelMainObjectMap = BeanUtil.object2Map(parentMainObject, panelTableName);
			//向主panel中的数据中加入主对象的数据
			panelList.add(panelMainObjectMap);
			//以下都是获取当前面板中的父子面板，不涉及model下多面板关系
			//获取其父的数据，如所属部门的很多字段
			this.findPagePanelParentData(mainPanel, modelMainTable, panelList, parentMainObject);
			//获取panel本身的子数据，如配置项的财务子数据
			this.findPagePanelSubData(mainPanel, modelMainTable, panelList, parentMainObject);
			//数据都准备好了，放在panelList里了，最后向返回结果中增加panel数据
			allResult.put(mainPanel.getName(), panelList);
			
			//暂存此遍历过的对象，留待其他panel中需要时获取
			loopedTableObject.put(modelMainTable, parentMainObject);
			//loopedPanels.put(mainPanel, parentMainObject);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//获取依赖主panel和主表的子panel和表，如financePanel的configItem
		List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel, mainPanel, modelMainTable);
		for(PageModelPanelTable item: pmptSubs){
			//loop to financePanel.configItem
			PagePanel subPagePanel = item.getSubPagePanel();
			//存储子panel的数据
			List<Map<String,Object>> subPanelList = new ArrayList<Map<String,Object>>();
			
			PagePanelType ppt = subPagePanel.getXtype();
			String xtype = ppt.getName();
			if(ppt==null){
				throw new ServiceException("pagePanelType cannot null");
			}
			SystemMainTable subPanelTable = item.getSubPanelTable();
			String subPanelTableName = subPanelTable.getTableName();
			//子表的外键，引用主panel主表
			SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
			String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
			String subPanelClassName = subPanelTable.getClassName();
			Class subPanelClass = null;
			panelList = null;
			try {
				subPanelClass = Class.forName(subPanelClassName);
				//先通过外键获取子panel的数据
				Object parentTableObject = loopedTableObject.get(modelMainTable);
				List<Object> subObjects = service.find(subPanelClass, subPanelTableFPropName, parentTableObject);
				if(!subObjects.isEmpty()){//如果子panel中数据存在
					if(xtype.equalsIgnoreCase("form")){
						//Map<String,Object> listMap = panelList.iterator().next();
						Object subObject = subObjects.iterator().next();
						Map subObjectMap = metaDataManager.getEntityDataForEdit(subObject, subPanelTableName);
						//Map subObjectMap = BeanUtil.object2Map(subObject, subPanelTableName);
						subPanelList.add(subObjectMap);
					}else if(xtype.equalsIgnoreCase("editorgrid")||xtype.equalsIgnoreCase("grid")){
						for(Object subObject : subObjects){
							Map subObjectMap = metaDataManager.getEntityDataForEdit(subObject, subPanelTableName);
							subPanelList.add(subObjectMap);
						}
						//List<Map<String,Object>> tmp = BeanUtil.listObject2Map(subObjects, subPanelTableName);
						//subPanelList.addAll(tmp);
					}
					//向返回结果中增加panel数据
					allResult.put(subPagePanel.getName(), subPanelList);
				}else{ //否则如果子panel中的数据不存在，原因可能是此页面中的panel是后增加进来的
					if(xtype.equalsIgnoreCase("form")){
						Object subObject = subPanelClass.newInstance();//new了一个空的对象，保证页码此panel的显示
						//modify by lee for 修正空对象对一对多类属性未处理的BUG in 20091208 begin
						//Map subObjectMap = BeanUtil.object2Map(subObject, subPanelTableName);
						Map subObjectMap = metaDataManager.getEntityDataForEdit(subObject, subPanelTableName);
						//modify by lee for 修正空对象对一对多类属性未处理的BUG in 20091208 begin
						subPanelList.add(subObjectMap);
					}
					allResult.put(subPagePanel.getName(), subPanelList);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}//end 获取依赖主panel和主表的子panel和表，如financePanel的configItem
		//*****************
		
		//获取依赖主panel的父面板
		List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, mainPanel, modelMainTable);
		for(PageModelPanelTable item: pmptParents){
			//loop to financePanel.configItem
			PagePanel parentPagePanel = item.getParentPagePanel();
			//存储子panel的数据
			List<Map<String,Object>> parentPanelList = new ArrayList<Map<String,Object>>();
			
			PagePanelType ppt = parentPagePanel.getXtype();
			String xtype = ppt.getName();
			if(ppt==null){
				throw new ServiceException("pagePanelType cannot null");
			}
			SystemMainTable parentPanelTable = item.getParentPanelTable();
			String parentPanelTableName = parentPanelTable.getTableName();
			//子表的外键，引用主panel主表
			SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
			String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
			
			SystemMainTableColumn parentPk = item.getParentPanelTablePColumn();
			String parentPkName = parentPk.getPropertyName();
			
			String parentPanelClassName = parentPanelTable.getClassName();
			Class parentPanelClass = null;
			panelList = null;
			try {
				parentPanelClass = Class.forName(parentPanelClassName);
				//先通过外键获取子panel的数据
				Object subTableObject = loopedTableObject.get(modelMainTable);
				BeanWrapper subObjWrapper = new BeanWrapperImpl(subTableObject);
				//获取到父对象，如服务项的所属服务组合对象,subPanelTableFPropName=sp
				Object parentObject = subObjWrapper.getPropertyValue(subPanelTableFPropName);
				if(parentObject==null){
					throw new ServiceException(subTableObject.getClass().getName()+"引用的父对象不存在");
				}
				
				Map subObjectMap = metaDataManager.getEntityDataForEdit(parentObject, parentPanelTableName);
				parentPanelList.add(subObjectMap);
				allResult.put(parentPagePanel.getName(), parentPanelList);
				
				
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}//end 获取依赖主panel和主表的子panel和表，如financePanel的configItem
	
		return allResult;
	}
	
	
	public Map<String,List<Map<String,Object>>> getPageModelDataForLook(String model, String mainPanelDataId) {
		if(StringUtils.isBlank(mainPanelDataId)){
			throw new ServiceException("model main object id is needed");
		}
		Map<String,List<Map<String,Object>>> allResult = new HashMap<String,List<Map<String,Object>>>();
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		//存储主表和遍历过的panel中的对象
		Map<SystemMainTable,Object> loopedTableObject = new HashMap<SystemMainTable,Object>();
		Map<SystemMainTable,Object> notLoopedTableObject = new HashMap<SystemMainTable,Object>();
		Map<PagePanel,Object> loopedPanels = new HashMap<PagePanel,Object>();
		
		PageModel pageModel = pageModelService.findPageModel(model);
		if(pageModel==null) {
			throw new ServiceException("page model '"+model+"' not exist");
		}
		//取model的主表，如配置项表configItem
		PagePanel mainPanel = null;
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		if(modelMainTable==null){
			throw new ServiceException("page model '"+model+"' need systemMainTable");
		}
	
		mainPanel = pageModel.getMainPagePanel();
		//取这个model下的所有panel, 如configItemPanel,financePanel,customerPanel
//		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
//		for(PageModelPanel pmp : pmps){ 
//			//遍历一个panel, 如 configItemPanel
//			PagePanel pagePanel = pmp.getPagePanel(); //configItemPanel
//			SystemMainTable panelMainTable = pagePanel.getSystemMainTable();
//			if(panelMainTable==modelMainTable){
//				mainPanel = pagePanel;
//				break;
//			}
//		}
		//取panel的类型
		PagePanelType pagePanelType = mainPanel.getXtype();
		if(pagePanelType==null){
			throw new ServiceException(mainPanel.getName()+" must select panel type");
		}
		String xtypeName = pagePanelType.getName();
		
		//存放主panel中的数据
		List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
		
		//通过主对象id获取主表的对象
		String panelTableName = modelMainTable.getTableName();
		String panelTableClsName = modelMainTable.getClassName();
		Class panelClass = null;
		Object parentMainObject = null;
		try {
			panelClass = Class.forName(panelTableClsName);
			//主对象
			parentMainObject = service.find(panelClass, mainPanelDataId, true); //其实这里扩展字段无法获取
			if(parentMainObject==null){
				throw new ServiceException("not object fount");
			}
			//将主对象转成Map格式，此方法可以将扩展数据也带出来，同时返回的key追加了表前缀
			Map panelMainObjectMap = metaDataManager.getEntityDataForLook(parentMainObject, panelTableName);
			//Map panelMainObjectMap = BeanUtil.object2Map(parentMainObject, panelTableName);
			//向主panel中的数据中加入主对象的数据
			panelList.add(panelMainObjectMap);
			//以下都是获取当前面板中的父子面板，不涉及model下多面板关系
			//获取其父的数据，如所属部门的很多字段
			this.findPagePanelParentData(mainPanel, modelMainTable, panelList, parentMainObject);
			//获取panel本身的子数据，如配置项的财务子数据
			this.findPagePanelSubData(mainPanel, modelMainTable, panelList, parentMainObject);
			//数据都准备好了，放在panelList里了，最后向返回结果中增加panel数据
			allResult.put(mainPanel.getName(), panelList);
			
			//暂存此遍历过的对象，留待其他panel中需要时获取
			loopedTableObject.put(modelMainTable, parentMainObject);
			//loopedPanels.put(mainPanel, parentMainObject);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//获取依赖主panel和主表的子panel和表，如financePanel的configItem
		List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel, mainPanel, modelMainTable);
		for(PageModelPanelTable item: pmptSubs){
			//loop to financePanel.configItem
			PagePanel subPagePanel = item.getSubPagePanel();
			//存储子panel的数据
			List<Map<String,Object>> subPanelList = new ArrayList<Map<String,Object>>();
			
			PagePanelType ppt = subPagePanel.getXtype();
			String xtype = ppt.getName();
			if(ppt==null){
				throw new ServiceException("pagePanelType cannot null");
			}
			SystemMainTable subPanelTable = item.getSubPanelTable();
			String subPanelTableName = subPanelTable.getTableName();
			//子表的外键，引用主panel主表
			SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
			String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
			String subPanelClassName = subPanelTable.getClassName();
			Class subPanelClass = null;
			panelList = null;
			try {
				subPanelClass = Class.forName(subPanelClassName);
				//先通过外键获取子panel的数据
				Object parentTableObject = loopedTableObject.get(modelMainTable);
				List<Object> subObjects = service.find(subPanelClass, subPanelTableFPropName, parentTableObject);
				if(!subObjects.isEmpty()){//如果子panel中数据存在
					if(xtype.equalsIgnoreCase("form")){
						//Map<String,Object> listMap = panelList.iterator().next();
						Object subObject = subObjects.iterator().next();
						Map subObjectMap = metaDataManager.getEntityDataForLook(subObject, subPanelTableName);
						//Map subObjectMap = BeanUtil.object2Map(subObject, subPanelTableName);
						subPanelList.add(subObjectMap);
					}else if(xtype.equalsIgnoreCase("editorgrid")||xtype.equalsIgnoreCase("grid")){
						for(Object subObject : subObjects){
							Map subObjectMap = metaDataManager.getEntityDataForLook(subObject, subPanelTableName);
							subPanelList.add(subObjectMap);
						}
						//List<Map<String,Object>> tmp = BeanUtil.listObject2Map(subObjects, subPanelTableName);
						//subPanelList.addAll(tmp);
					}
					//向返回结果中增加panel数据
					allResult.put(subPagePanel.getName(), subPanelList);
				}else{ //否则如果子panel中的数据不存在，原因可能是此页面中的panel是后增加进来的
					if(xtype.equalsIgnoreCase("form")){
						Object subObject = subPanelClass.newInstance();//new了一个空的对象，保证页码此panel的显示
						Map subObjectMap = BeanUtil.object2Map(subObject, subPanelTableName);
						subPanelList.add(subObjectMap);
					}
					allResult.put(subPagePanel.getName(), subPanelList);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}//end 获取依赖主panel和主表的子panel和表，如financePanel的configItem
		//*****************
		
		//获取依赖主panel的父面板
		List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, mainPanel, modelMainTable);
		for(PageModelPanelTable item: pmptParents){
			//loop to financePanel.configItem
			PagePanel parentPagePanel = item.getParentPagePanel();
			//存储子panel的数据
			List<Map<String,Object>> parentPanelList = new ArrayList<Map<String,Object>>();
			
			PagePanelType ppt = parentPagePanel.getXtype();
			String xtype = ppt.getName();
			if(ppt==null){
				throw new ServiceException("pagePanelType cannot null");
			}
			SystemMainTable parentPanelTable = item.getParentPanelTable();
			String parentPanelTableName = parentPanelTable.getTableName();
			//子表的外键，引用主panel主表
			SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
			String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
			
			SystemMainTableColumn parentPk = item.getParentPanelTablePColumn();
			String parentPkName = parentPk.getPropertyName();
			
			String parentPanelClassName = parentPanelTable.getClassName();
			Class parentPanelClass = null;
			panelList = null;
			try {
				parentPanelClass = Class.forName(parentPanelClassName);
				//先通过外键获取子panel的数据
				Object subTableObject = loopedTableObject.get(modelMainTable);
				BeanWrapper subObjWrapper = new BeanWrapperImpl(subTableObject);
				//获取到父对象，如服务项的所属服务组合对象,subPanelTableFPropName=sp
				Object parentObject = subObjWrapper.getPropertyValue(subPanelTableFPropName);
				if(parentObject==null){
					throw new ServiceException(subTableObject.getClass().getName()+"引用的父对象不存在");
				}
				
				Map subObjectMap = metaDataManager.getEntityDataForLook(parentObject, parentPanelTableName);
				parentPanelList.add(subObjectMap);
				allResult.put(parentPagePanel.getName(), parentPanelList);
				
				
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}//end 获取依赖主panel和主表的子panel和表，如financePanel的configItem
	
		return allResult;
	}
	
	
	
	public List<Map<String,Object>> getPagePanelDataForEdit(String modelName, String panelName, String mainObjectId) {
		Map<String,List<Map<String,Object>>> modelMap = this.getPageModelDataForEdit(modelName, mainObjectId);
		List<Map<String,Object>> panelList = modelMap.get(panelName);
		return panelList;
	}
	
	public List<Map<String,Object>> getPagePanelDataForLook(String modelName, String panelName, String mainObjectId) {
		Map<String,List<Map<String,Object>>> modelMap = this.getPageModelDataForLook(modelName, mainObjectId);
		List<Map<String,Object>> panelList = modelMap.get(panelName);
		return panelList;
	}

	/**
	 * 获取当前面板中的所有数据
	 * @deprecated
	 */
	public Map<String, Object> getPageModelDataForEdit(String model, String panel, String mainPanelDataId) {
		if(StringUtils.isBlank(mainPanelDataId)){
			throw new ServiceException("panel main object id is needed!");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		//当前面板, 如configItemPanel
		PagePanel pagePanel = pagePanelService.findPagePanel(panel);
		if(pagePanel==null) {
			throw new ServiceException("panel name not exist");
		}
		//当前面板操作的主表, configItem
		SystemMainTable panelSmt = pagePanel.getSystemMainTable();
		String tableName = panelSmt.getTableName();
		String mainTableClass = panelSmt.getClassName();
		Class mainClass = null;
		Object mainObject = null;
		try {
			mainClass = Class.forName(mainTableClass);
//			mainObject = mainClass.newInstance();
//			BeanWrapper bw = new BeanWrapperImpl(mainObject);
//			bw.setPropertyValue("id", Long.valueOf(mainPanelDataId));
			mainObject = service.find(mainClass, mainPanelDataId, true); //configItem Object
			Map map = BeanUtil.object2Map(mainObject, tableName); //configItem map
			result.putAll(map);//将主实体的键值对信息放入结果键值对
		} catch (Exception e) {
			e.printStackTrace();
		}		
		/*Set<SystemMainTable> smtSet = new HashSet<SystemMainTable>();
		smtSet.add(panelSmt);*/
		
		//获取当前面板中涉及的所有主表
		List<SystemMainTable> tables = pagePanelService.findMainTableByPanel(pagePanel);
		for(SystemMainTable smt : tables){
			if(smt!=panelSmt){ //这里注意是否可以判断相等
				String otherTableClass = panelSmt.getClassName();
				Class otherClass = null;
				Object otherObject = null;
				try {
					otherClass = Class.forName(otherTableClass);
					otherObject = otherClass.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}		
				//获取这个其他表的所有字段
				List<Column> columns = systemColumnService.findSystemTableColumns(smt);
				for(Column column : columns){
					PropertyType pt = column.getPropertyType();
					String ptname = pt.getName();
					if(ptname.equalsIgnoreCase("BaseObject")){
						String propertyName = column.getPropertyName();
						SystemMainTable ftable = column.getForeignTable();
						String ftableName = ftable.getTableName();
						String fclass = ftable.getClassName();
						//panel的某个字段关联到panel主表，则通过主表的对象id获取panel其他表的数据
						if(fclass.equalsIgnoreCase(mainTableClass)){ 
							List list = service.find(otherClass, propertyName, mainObject);
							if(!list.isEmpty()){
								otherObject = list.iterator().next();
								Map map = BeanUtil.object2Map(otherObject, ftableName);
								result.putAll(map);
							}
							
						}
					}
					
				}
			}
			
			
		}
		
		return result;
	}

	
	private void saveParentTableData(PagePanel mainPanel, 
				SystemMainTable subTable, Map<String, Object> objectMap){
		
		//如通过申请信息获取分销商的多个字段，先保存新创建的分销商信息，在把分销商对象id设置给申请信息的分销商外键字段
		List<PagePanelTableRelation> pptrParent = pagePanelService.findPanelTableRelBySub(mainPanel, subTable);
		
		for(PagePanelTableRelation pptr : pptrParent){
			//即方法参数传递过来的子表
			//SystemMainTable subTable = pptr.getSystemMainTable();
			SystemMainTable parentTable = pptr.getForeignTable();
			String parentTableName = parentTable.getTableName();
			String parentTableClsName = parentTable.getClassName();
			Class parentTableClass = null;
			
			try {
				parentTableClass = Class.forName(parentTableClsName);
				//Map<String,Object> parentObjectData = modelDataMap.iterator().next();
			} catch (Exception e) {
				e.printStackTrace();
			}	
			Map tempMap = new HashMap();
			//将父对象所有属性值放入去了表前缀的map里
			List<Column> columns = systemColumnService.findSystemTableColumns(parentTable);//root apply
			for(Column column : columns){
				String propertyName = column.getPropertyName();
				String tableColumnName = parentTableName+"$"+propertyName;
				if(objectMap.containsKey(tableColumnName)){
					Object columnValue = objectMap.get(tableColumnName);
					tempMap.put(propertyName, columnValue);
				}
			}
			//保存父对象如分销商，为了获取对象父对象id
			Object parentObject = metaDataManager.saveEntityData(parentTableClass, tempMap);
			if(parentObject!=null){
				//根对象的外键字段，如申请信息的分销商外键字段
				SystemMainTableColumn parentFKColumn = pptr.getForeignTableColumn();
				String parentFKPropertyName = parentFKColumn.getPropertyName();
				//apply$support
				parentFKPropertyName = parentTableName +"$"+ parentFKPropertyName; 
				BeanWrapper wb = new BeanWrapperImpl(parentObject);
				Long parentObjectId = (Long) wb.getPropertyValue("id");
				//将子对象的外键字段用父对象的id初始化
				objectMap.put(parentFKPropertyName, parentObjectId);
			}
			
			//List<PagePanelTableRelation> pptrSub = pagePanelService.findPanelTableRelByParent(mainPanel, subTable);
			
		}
	}
	
	
	private void saveSubTableData(PagePanel mainPanel, 
			SystemMainTable parentRootTable, Map<String, Object> objectMap){
	
		//如通过申请信息获取分销商的多个字段，先保存新创建的分销商信息，在把分销商对象id设置给申请信息的分销商外键字段
		List<PagePanelTableRelation> pptrSubs = pagePanelService.findPanelTableRelByParent(mainPanel, parentRootTable);
		
		for(PagePanelTableRelation pptr : pptrSubs){
			//即方法参数传递过来的子表
			SystemMainTable subTable = pptr.getSystemMainTable();
			String subTableName = subTable.getTableName();
			String subTableClsName = subTable.getClassName();
			Class subTableClass = null;
			
			SystemMainTable parentTable = pptr.getForeignTable();
			String parentTableName = parentTable.getTableName();
			String parnetTableClsName = parentTable.getClassName();
			Class parentTableClass = null;
			
			try {
				subTableClass = Class.forName(subTableClsName);
				//Map<String,Object> parentObjectData = modelDataMap.iterator().next();
			} catch (Exception e) {
				e.printStackTrace();
			}	
			Map tempMap = new HashMap();
			List<Column> columns = systemColumnService.findSystemTableColumns(subTable);
			for(Column column : columns){
				String propertyName = column.getPropertyName();
				String tableColumnName = parentTableName+"$"+propertyName;
				if(objectMap.containsKey(tableColumnName)){
					Object columnValue = objectMap.get(tableColumnName);
					tempMap.put(propertyName, columnValue);
				}
			}
			//获取子对象的外键字段
			SystemMainTableColumn parentFKColumn = pptr.getForeignTableColumn();
			String parentFKPropertyName = parentFKColumn.getPropertyName();
			//子对象外键字段属性名称
			String tableParentFKPropertyName = parentTableName +"$"+ parentFKPropertyName;
			//根对象id字段
			String parentObjectId = parentTableName +"$id";
			//根对象id字段的值
			Long parentObjectIdValue = (Long) objectMap.get(parentObjectId);
			//给子对象的外键字段设置父对象id的值
			objectMap.put(tableParentFKPropertyName, parentObjectIdValue);
			tempMap.put(parentFKPropertyName, parentObjectIdValue);
			
			Object subObject = metaDataManager.saveEntityData(subTableClass, tempMap);
			
			
		}
	}
	
	/**
	 * 保存面板数据共用方法
	 * @Methods Name savePanelData
	 * @Create In 2009-1-3 By sa
	 * @param subPanel
	 * @param modelDataMap void
	 */
	private void savePanelData(PagePanel subPanel,  
								Map<String, List<Map<String, Object>>> modelDataMap, 
								String fcName,
								BaseObject pObject) {
		
		SystemMainTable subTable = subPanel.getSystemMainTable();
		String subTableName = subTable.getTableName();
		String subClassName = subTable.getClassName();
		Class subClass = this.getClass(subClassName);
		
		PagePanelType subPanelType = subPanel.getXtype();
		if(subPanelType==null){
			throw new ServiceException(subPanel.getName()+" 必须选择面板类型");
		}
		String subXtype = subPanelType.getName();
		String subPanelName = subPanel.getName();
		List<Map<String, Object>> subPanelList = modelDataMap.get(subPanelName);
		if(subXtype.equalsIgnoreCase("form")){
			if(!subPanelList.isEmpty()){
				Map<String, Object> subMap = subPanelList.iterator().next();
				Map temp = new HashMap();
				List<Column> tempColumn = systemColumnService.findSystemTableColumns(subTable);
				for(Column column : tempColumn){
					String propertyName = column.getPropertyName();
					String tableColumnName = subTableName+"$"+propertyName;
					if(subMap.containsKey(tableColumnName)){
						Object columnValue = subMap.get(tableColumnName);
						temp.put(propertyName, columnValue);
					}
				}
				if(pObject!=null){
					temp.put(fcName, pObject.getId());
				}
				Object subObject = metaDataManager.saveEntityData(subClass, temp);
			}
		}else if(subXtype.equalsIgnoreCase("editorgrid")){
			for(Map<String, Object> subMap : subPanelList){
				Map temp = new HashMap();
				List<Column> tempColumn = systemColumnService.findSystemTableColumns(subTable);
				for(Column column : tempColumn){
					String propertyName = column.getPropertyName();
					String tableColumnName = subTableName+"$"+propertyName;
					if(subMap.containsKey(tableColumnName)){
						Object columnValue = subMap.get(tableColumnName);
						temp.put(propertyName, columnValue);
					}
				}
				if(pObject!=null){
					temp.put(fcName, pObject.getId());
				}
				Object subObject = metaDataManager.saveEntityData(subClass, temp);
			}
		}
	}
	public String saveSinglePanelData(String model, String panel,Map<String, List<Map<String, Object>>> modelDataMap) {
		String dataId="";
		PageModel pageModel = pageModelService.findPageModel(model);
		PagePanel pagePanel = pagePanelService.findPagePanel(panel);
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		// 获取model的主面板
		PagePanel mainPanel = pageModel.getMainPagePanel();
		if (mainPanel == null) {
			throw new ServiceException(pageModel.getName() + " 必须选择主面板");
		}
		PagePanelType pagePanelType = mainPanel.getXtype();
		if (pagePanelType == null) {
			throw new ServiceException(mainPanel.getName() + " 必须选择面板类型");
		}
		String xtype = pagePanelType.getName();
		// 通过主对象id获取主表的对象
		String panelTableName = modelMainTable.getTableName();
		String panelTableClsName = modelMainTable.getClassName();
		Class panelClass = null;
		Object rootObject = null;
		try {
			panelClass = Class.forName(panelTableClsName);
			// begin保存根对象****************************************
			List<Map<String, Object>> list = modelDataMap.get(mainPanel
					.getName());
			if (xtype.equalsIgnoreCase("form")) {// 如果主panel的类型是formpanel
				if (!list.isEmpty()) {
					Map<String, Object> objectMap = list.iterator().next();
					// begin保存当前面板******************************************
					// 先保存当前主对象的父对象
					if (pagePanel.getName() == mainPanel.getName()) {// 如果保存面板为主面板则保存
						this.saveParentTableData(mainPanel, modelMainTable,objectMap);
						//}
						Map tempMap = new HashMap();
						List<Column> columns = systemColumnService.findSystemTableColumns(modelMainTable);
						for (Column column : columns) {
							String propertyName = column.getPropertyName();
							String tableColumnName = panelTableName + "$" + propertyName;
							if (objectMap.containsKey(tableColumnName)) {
								Object columnValue = objectMap.get(tableColumnName);
								tempMap.put(propertyName, columnValue);
							}
						}
						//if(pagePanel.getName() == mainPanel.getName()){
						rootObject = metaDataManager.saveEntityData(panelClass,tempMap);
					}else{
						rootObject=service.find(panelClass, objectMap.get(panelTableName+"$id").toString());
					}
					BeanWrapper bw = new BeanWrapperImpl(rootObject);
					Long rootObjectId = (Long) bw.getPropertyValue("id");
					dataId = rootObjectId.toString();
					objectMap.put(panelTableName + "$id", rootObjectId);
					if (pagePanel.getName() == mainPanel.getName()) {
					// 保存当前主对象的子对象数据
					this.saveSubTableData(mainPanel, modelMainTable, objectMap);
					// end保存当前面板******************************************
					}
					List<PageModelPanel> modelPanels = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
					for (PageModelPanel pmp : modelPanels) {
						PagePanel pp = pmp.getPagePanel();
						if (pp.getGroupFlag() != null
								&& pp.getGroupFlag().intValue() == 1) {
							// 通过分组面板，当前主面板，获取分组面板下的子面板关系信息
							List<PageGroupPanelTable> pgpts = pageGroupPanelService.findGroupPanelTableByParent(pp, mainPanel);
							for (PageGroupPanelTable pgpt : pgpts) {
								// 比如内部客户分组面板，下面有个内部客户联系人子面板
								PagePanel subPanel = pgpt.getSubPagePanel();
								SystemMainTableColumn fc = pgpt.getSubPanelTableFColumn();
								String fcName = fc.getPropertyName();
								if (subPanel.getName() == pagePanel.getName()) {
									this.savePanelData(subPanel, modelDataMap,fcName, (BaseObject) rootObject);
								}
							}
						}
					}

					// ******************************************
					// 保存当前页面pageModel下所有子panel的对象值
					List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel,mainPanel, modelMainTable);
					for (PageModelPanelTable item : pmptSubs) {
						if (item.getSubPagePanel().getName() == pagePanel.getName()) {
							// loop to financePanel.configItem
							PagePanel subPagePanel = item.getSubPagePanel();
							// 存储子panel的数据
							PagePanelType ppt = subPagePanel.getXtype();
							if (ppt == null) {
								throw new ServiceException(
										"pagePanelType cannot null");
							}
							String subXtype = ppt.getName();
							if (subXtype.equalsIgnoreCase("form")) {// 子面板是form
								SystemMainTable subPanelTable = item.getSubPanelTable();
								String subPanelTableName = subPanelTable.getTableName();
								// 获取子panel的数据list
								List<Map<String, Object>> subPanelList = modelDataMap.get(subPagePanel.getName());
								if (!subPanelList.isEmpty()) {
									Map<String, Object> subObjectMap = subPanelList.iterator().next();
									String subPanelClassname = subPanelTable.getClassName();
									Class subPanelMainClass = null;
									subPanelMainClass = Class.forName(subPanelClassname);
									SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
									String subTableFColumnName = subPanelTableFColumn.getPropertyName();

									SystemMainTable parentPanelTable = item.getParentPanelTable();
									String parentTableName = parentPanelTable.getTableName();
									SystemMainTableColumn parentTablePKColumn = item.getParentPanelTablePColumn();
									String parentPKColumnName = parentTablePKColumn.getPropertyName();
									Long mainId = (Long) objectMap.get(parentTableName + "$" + parentPKColumnName);

									subObjectMap.put(subPanelTableName + "$" + subTableFColumnName, mainId);
									// 保存子对象
									Map tempMapSub = new HashMap();
									List<Column> columnSubs = systemColumnService.findSystemTableColumns(subPanelTable);
									for (Column column : columnSubs) {
										String propertyName = column.getPropertyName();
										String tableColumnName = subPanelTableName + "$" + propertyName;
										if (subObjectMap.containsKey(tableColumnName)) {
											Object columnValue = subObjectMap.get(tableColumnName);
											tempMapSub.put(propertyName,columnValue);
										}
									}
									metaDataManager.saveEntityData(subPanelMainClass, tempMapSub);
								}

							} else if (subXtype.equalsIgnoreCase("editorgrid")) {// 子面板是grid

								SystemMainTable subPanelTable = item
										.getSubPanelTable();
								String subPanelTableName = subPanelTable
										.getTableName();
								// 获取子panel的数据list
								List<Map<String, Object>> subPanelList = modelDataMap
										.get(subPagePanel.getName());
								for (Map<String, Object> subObjectMap : subPanelList) {
									String subPanelClassname = subPanelTable
											.getClassName();
									Class subPanelMainClass = null;
									subPanelMainClass = Class
											.forName(subPanelClassname);
									SystemMainTableColumn subPanelTableFColumn = item
											.getSubPanelTableFColumn();
									String subTableFColumnName = subPanelTableFColumn
											.getPropertyName();

									SystemMainTable parentPanelTable = item
											.getParentPanelTable();
									String parentTableName = parentPanelTable
											.getTableName();
									SystemMainTableColumn parentTablePKColumn = item
											.getParentPanelTablePColumn();
									String parentPKColumnName = parentTablePKColumn
											.getPropertyName();
									Long mainId = (Long) objectMap
											.get(parentTableName + "$"
													+ parentPKColumnName);

									subObjectMap.put(subPanelTableName + "$"
											+ subTableFColumnName, mainId);
									// 保存子对象

									Map tempMapSub = new HashMap();
									List<Column> columnSubs = systemColumnService
											.findSystemTableColumns(subPanelTable);
									for (Column column : columnSubs) {
										String propertyName = column
												.getPropertyName();
										String tableColumnName = subPanelTableName
												+ "$" + propertyName;
										if (subObjectMap
												.containsKey(tableColumnName)) {
											Object columnValue = subObjectMap
													.get(tableColumnName);
											tempMapSub.put(propertyName,
													columnValue);
										}
									}
									metaDataManager.saveEntityData(
											subPanelMainClass, tempMapSub);
								}
							}// end editorgrid

						}// end遍历子面板
						// ******************************************
						List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, null,null);
					}
				}

			} else if (xtype.equalsIgnoreCase("editorgrid")) {// 主panel的类型是editorgrid，则只保存自己即可
				for (Map<String, Object> objectMap : list) {
					Map tempMapSub = new HashMap();
					List<Column> columnSubs = systemColumnService
							.findSystemTableColumns(modelMainTable);
					for (Column column : columnSubs) {
						String propertyName = column.getPropertyName();
						String tableColumnName = panelTableName + "$"
								+ propertyName;
						if (objectMap.containsKey(tableColumnName)) {
							Object columnValue = objectMap.get(tableColumnName);
							tempMapSub.put(propertyName, columnValue);
						}
					}
					rootObject = metaDataManager.saveEntityData(panelClass,
							objectMap);
				}
			}// end保存根对象****************************************

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataId;
	}
	public Object savePageModelData(String model, Map<String, List<Map<String, Object>>> modelDataMap) {
		PageModel pageModel = pageModelService.findPageModel(model);
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		
		//获取model的主面板
		PagePanel mainPanel = pageModel.getMainPagePanel();
		if(mainPanel==null){
			throw new ServiceException(pageModel.getName()+" 必须选择主面板");
		}
		PagePanelType pagePanelType = mainPanel.getXtype();
		if(pagePanelType==null){
			throw new ServiceException(mainPanel.getName()+" 必须选择面板类型");
		}
		String xtype = pagePanelType.getName();
//		通过主对象id获取主表的对象	
		String panelTableName = modelMainTable.getTableName();
		String panelTableClsName = modelMainTable.getClassName();
		Class panelClass = null;
		Object rootObject = null;
		try {
			panelClass = Class.forName(panelTableClsName);
			//begin保存根对象****************************************
			List<Map<String, Object>> list = modelDataMap.get(mainPanel.getName());
			if(xtype.equalsIgnoreCase("form")){//如果主panel的类型是formpanel
				if(!list.isEmpty()){
					Map<String, Object> objectMap = list.iterator().next();
					//begin保存当前面板******************************************
					//先保存当前主对象的父对象
					this.saveParentTableData(mainPanel, modelMainTable, objectMap);
					
					Map tempMap = new HashMap();
					List<Column> columns = systemColumnService.findSystemTableColumns(modelMainTable);
					for(Column column : columns){
						String propertyName = column.getPropertyName();
						String tableColumnName = panelTableName+"$"+propertyName;
						if(objectMap.containsKey(tableColumnName)){
							Object columnValue = objectMap.get(tableColumnName);
							tempMap.put(propertyName, columnValue);
						}
						SystemMainTableColumn column1=(SystemMainTableColumn)column;
						if(column1.getIsExtColumn()==SystemMainTableColumn.isExt
						&&column.getSystemMainTableColumnType().getColumnTypeName().equals("checkboxGroup")) {
							Set keySet=objectMap.keySet();
							String keys="";
							String tacn=tableColumnName+"$";
							for(Object key:keySet){
								String keytos=key.toString();
								int i=tacn.length();
								if(keytos.startsWith(tableColumnName)){
									keys+=keytos.substring(i)+",";
								}
							}
							if(!keys.equals("")){
								keys=keys.substring(0, keys.length()-1);
							}
							tempMap.put(propertyName, keys);
						}
					}
					rootObject = metaDataManager.saveEntityData(panelClass, tempMap);
					BeanWrapper bw = new BeanWrapperImpl(rootObject);
					Long rootObjectId = (Long) bw.getPropertyValue("id");
					objectMap.put(panelTableName+"$id", rootObjectId);
					
					//保存当前主对象的子对象数据
					this.saveSubTableData(mainPanel, modelMainTable, objectMap);
					//end保存当前面板******************************************
					
					List<PageModelPanel> modelPanels = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
					for(PageModelPanel pmp : modelPanels){
						PagePanel pp = pmp.getPagePanel();
						if(pp.getGroupFlag()!=null&& pp.getGroupFlag().intValue()==1){
							//通过分组面板，当前主面板，获取分组面板下的子面板关系信息
							List<PageGroupPanelTable> pgpts = pageGroupPanelService.findGroupPanelTableByParent(pp, mainPanel);
							for(PageGroupPanelTable pgpt : pgpts){
								//比如内部客户分组面板，下面有个内部客户联系人子面板
								PagePanel subPanel = pgpt.getSubPagePanel();
								SystemMainTableColumn fc = pgpt.getSubPanelTableFColumn();
								String fcName = fc.getPropertyName();
								this.savePanelData(subPanel, modelDataMap, fcName, (BaseObject)rootObject);
								
							}
//							
						}
					}
						
					//******************************************
					//保存当前页面pageModel下所有子panel的对象值
					List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel, mainPanel, modelMainTable);
					for(PageModelPanelTable item: pmptSubs){
						//loop to financePanel.configItem
						PagePanel subPagePanel = item.getSubPagePanel();
						//存储子panel的数据
						
						PagePanelType ppt = subPagePanel.getXtype();
						if(ppt==null){
							throw new ServiceException("pagePanelType cannot null");
						}
						String subXtype = ppt.getName();
						if(subXtype.equalsIgnoreCase("form")){//子面板是form
							SystemMainTable subPanelTable = item.getSubPanelTable();
							String subPanelTableName = subPanelTable.getTableName();
							//获取子panel的数据list
							List<Map<String, Object>> subPanelList = modelDataMap.get(subPagePanel.getName());
							if(!subPanelList.isEmpty()){
								Map<String, Object> subObjectMap = subPanelList.iterator().next();
								String subPanelClassname = subPanelTable.getClassName();
								Class subPanelMainClass = null;
								subPanelMainClass = Class.forName(subPanelClassname);
								SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
								String subTableFColumnName = subPanelTableFColumn.getPropertyName();
								
								SystemMainTable parentPanelTable = item.getParentPanelTable();
								String parentTableName = parentPanelTable.getTableName();
								SystemMainTableColumn parentTablePKColumn = item.getParentPanelTablePColumn();
								String parentPKColumnName = parentTablePKColumn.getPropertyName();
								Long mainId = (Long) objectMap.get(parentTableName+"$"+ parentPKColumnName);
								subObjectMap.put(subPanelTableName+"$"+subTableFColumnName, mainId);
								//保存子对象
								
								Map tempMapSub = new HashMap();
								List<Column> columnSubs = systemColumnService.findSystemTableColumns(subPanelTable);
								for(Column column : columnSubs){
									String propertyName = column.getPropertyName();
									String tableColumnName = subPanelTableName+"$"+propertyName;
									if(subObjectMap.containsKey(tableColumnName)){
										Object columnValue = subObjectMap.get(tableColumnName);
										tempMapSub.put(propertyName, columnValue);
									}
								}
								metaDataManager.saveEntityData(subPanelMainClass, tempMapSub);
							}
							
							
						}else if(subXtype.equalsIgnoreCase("editorgrid")){//子面板是grid
							
							SystemMainTable subPanelTable = item.getSubPanelTable();
							String subPanelTableName = subPanelTable.getTableName();
							//获取子panel的数据list
							List<Map<String, Object>> subPanelList = modelDataMap.get(subPagePanel.getName());
							for(Map<String, Object> subObjectMap : subPanelList){
								String subPanelClassname = subPanelTable.getClassName();
								Class subPanelMainClass = null;
								subPanelMainClass = Class.forName(subPanelClassname);
								SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
								String subTableFColumnName = subPanelTableFColumn.getPropertyName();
								
								SystemMainTable parentPanelTable = item.getParentPanelTable();
								String parentTableName = parentPanelTable.getTableName();
								SystemMainTableColumn parentTablePKColumn = item.getParentPanelTablePColumn();
								String parentPKColumnName = parentTablePKColumn.getPropertyName();
								Long mainId = (Long) objectMap.get(parentTableName+"$"+ parentPKColumnName);
								
								subObjectMap.put(subPanelTableName+"$"+subTableFColumnName, mainId);
								//保存子对象
								
								Map tempMapSub = new HashMap();
								List<Column> columnSubs = systemColumnService.findSystemTableColumns(subPanelTable);
								for(Column column : columnSubs){
									String propertyName = column.getPropertyName();
									String tableColumnName = subPanelTableName+"$"+propertyName;
									if(subObjectMap.containsKey(tableColumnName)){
										Object columnValue = subObjectMap.get(tableColumnName);
										tempMapSub.put(propertyName, columnValue);
									}
								}
								metaDataManager.saveEntityData(subPanelMainClass, tempMapSub);
							}
						}//end editorgrid
						
					}//end遍历子面板
					//******************************************
					List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, null,null);
				}
				
			}else if(xtype.equalsIgnoreCase("editorgrid")){//主panel的类型是editorgrid，则只保存自己即可
				for(Map<String, Object> objectMap : list){
					Map tempMapSub = new HashMap();
					List<Column> columnSubs = systemColumnService.findSystemTableColumns(modelMainTable);
					for(Column column : columnSubs){
						String propertyName = column.getPropertyName();
						String tableColumnName = panelTableName+"$"+propertyName;
						if(objectMap.containsKey(tableColumnName)){
							Object columnValue = objectMap.get(tableColumnName);
							tempMapSub.put(propertyName, columnValue);
						}
					}
					rootObject = metaDataManager.saveEntityData(panelClass, objectMap);
				}
			}//end保存根对象****************************************
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return rootObject;
		
	}

	/**
	 * 保存model中指定panel中的数据
	 * 修改时表与表直接的关联利用隐藏域字段保存，但新增时关联字段值的设置需要后台字段处理
	 */
	public Object savePageModelData(String model, String panel,
			Map<String, Object> columnDataMap) {
		Object mainObject = null;
		
		PageModel pageModel = pageModelService.findPageModel(model);
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		
		//获取model的主面板
		PagePanel mainPanel = null;
		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
		for(PageModelPanel pmp : pmps){ 
			//遍历一个panel, 如 configItemPanel
			PagePanel pagePanel = pmp.getPagePanel(); //configItemPanel
			SystemMainTable panelMainTable = pagePanel.getSystemMainTable();
			if(panelMainTable==modelMainTable){
				mainPanel = pagePanel;
				break;
			}
		}
		
		
		PagePanel pagePanel = pagePanelService.findPagePanel(panel);
		SystemMainTable panelSmt = pagePanel.getSystemMainTable();
		Set<SystemMainTable> smtSet = new HashSet<SystemMainTable>();
		smtSet.add(panelSmt);
		
		
		//取panel中的所有主表，多个主表直接关联
		List<SystemMainTable> list = pagePanelService.findMainTableByPanel(pagePanel);
		smtSet.addAll(list);
		for(SystemMainTable smt : smtSet){
			Map objectMap = new HashMap(); //保存一个表的所有属性值
			Class clazz = null;
			String className = smt.getClassName();
			//BeanWrapper bw = new BeanWrapperImpl();
			//Object object = null;
			try {
				clazz = Class.forName(className);
				//object = clazz.newInstance();
				//bw.setWrappedInstance(object);
			} catch (Exception e) {
				e.printStackTrace();
			}	
			//SystemMainTableColumn smtc = smt.getPrimaryKeyColumn();
			//String pkColumnName = smtc.getPropertyName();
			String tableName = smt.getTableName();
			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
			for(Column column : columns){
				String propertyName = column.getPropertyName();
				String tableColumnName = tableName+"$"+propertyName;
				if(columnDataMap.containsKey(tableColumnName)){
					Object columnValue = columnDataMap.get(tableColumnName);
					//如果是主表的id
					if(smt==panelSmt&& propertyName.equalsIgnoreCase("id")){
						String mainId = columnValue.toString();
						if(StringUtils.isNotBlank(mainId)){
							mainObject = service.find(clazz, mainId);
						}
						
					}
					//bw.setPropertyValue(propertyName, columnValue);
					objectMap.put(propertyName, columnValue);
				}
			}
			Object result = metaDataManager.saveEntityData(clazz, objectMap);
			
		}
		return mainObject;
	}

	
	public List<Map<String, Object>> getEntityMapDataForList(String panelName, List mainList) {
		PagePanel pp=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt = pp.getSystemMainTable(); //*************暂时取1张表
		String className = smt.getClassName();
		String tableName = smt.getTableName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> result = metaDataManager.getEntityMapDataForList(clazz, mainList, tableName);
		/*List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> classMap = metaDataManager.getEntityMapDataForList(clazz, mainList);
		for(Map<String, Object> item : classMap){
			Map<String,Object> newMap = new HashMap<String, Object>(); //new map
			Set entries = item.entrySet( );
			Iterator<Map.Entry> iterator = entries.iterator();
			while(iterator.hasNext( )) {
				Map.Entry entry =iterator.next();
				Object key = entry.getKey( );
				Object value = entry.getValue();
				String tableProperyName = tableName + "$" + key;
				newMap.put(tableProperyName, value);
				result.add(newMap);
			}
			
		}*/
		return result;
	}


	public List<Map<String, Object>> getPageModelDataForList(String pageName, List mainList) {
		PagePanel panel=pagePanelService.findPagePanel(pageName);
		List<PagePanelColumn> ppcs = this.pagePanelColumnService.findColumnByPanel(panel);
		
		Iterator iter = mainList.iterator();
		while(iter.hasNext()){//遍历每行记录，取出list中的实体BaseObject item
			Map<String, Object> item = new HashMap<String, Object>(); //存储每个记录，相对一个实体对象
			BaseObject object = (BaseObject) iter.next();
			Long mainTableRowId = object.getId(); //主表当前记录的id（行号）

			for(PagePanelColumn ppc : ppcs){
				Column column = ppc.getColumn();
				SystemMainTable smt = column.getSystemMainTable();
				String tableName = smt.getTableName();
				Object propertyValue = null;
				SystemMainTableColumnType mcType = column.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = column.getPropertyName(); //当前主属性的名称				
				String tableNameProperyName = tableName+"$"+ propertyName; //*****add***
				ColumnDataWrapper columnDataWrapper = new ColumnDataWrapper(column);
				columnDataWrapper.initData(object);
				propertyValue = columnDataWrapper.getText();

				item.put(tableNameProperyName, propertyValue);
				
				if(columnDataWrapper.getLink()!=null&& !columnDataWrapper.getLink().equals("")){
					item.put(tableNameProperyName+"Link", columnDataWrapper.getLink());
				}
				
			}
		
		}
		return null;
	}

	
	/**
	 * 框架2期综合查询代码
	 */
	public Page query(String panelName, Map<String, Object> queryParams, 
					int pageNo, int pageSize, String orderProp, boolean isAsc) {
		//转换前端的查询参数进入我们的map
		Map<String,Object> result = new HashMap<String,Object>();
		PagePanel panel = this.pagePanelService.findPagePanel(panelName);
		List<SystemMainTable> smts = this.pagePanelTableService.findSystemMainTableByPanel(panel);
		for(SystemMainTable smt : smts){
			String className = smt.getClassName();
			Class clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (Exception e) {
				e.printStackTrace();
			}		
			Map<String,Object> map = this.metaDataManager.genPropParams(clazz, queryParams);
			result.putAll(map);
			
		}
		Page page = pageQueryService.query(panelName, result, null, pageNo, pageSize, orderProp, isAsc);
		return page;
	}

	public List query(String panelName, Map<String, Object> queryParams, String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removePageModelData(String model, String mainObjectId) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *提供panel关键字，panel主对象id获取panel中的所有数据，返回结果包括panel的所有column中所需数据
	 */
	public Map<String, Object> getPagePanelDataForEdit(String panelName,String panelObjectId) {

		Map<String, Object> requestParams = new HashMap<String, Object>();
		
		PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
		if(pagePanel==null) {
			throw new ServiceException("panel name not exist");
		}
		PagePanelType ppt = pagePanel.getXtype();
		String xtype = ppt.getName();
		if(ppt==null){
			throw new ServiceException("pagePanelType cannot null");
		}
		SystemMainTable panelTable = pagePanel.getSystemMainTable();
		String panelTableName = panelTable.getTableName();
		String panelClassName = panelTable.getClassName();
		Class panelClass = null;
		Object panelObject = null;
		try {
			panelClass = Class.forName(panelClassName);
			panelObject = service.find(panelClass, panelObjectId,true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
		
		Map panelMainObjectMap = metaDataManager.getEntityDataForEdit(panelObject, panelTableName);
		//Map panelMainObjectMap = BeanUtil.object2Map(parentMainObject, panelTableName);
		requestParams.putAll(panelMainObjectMap);
		//获取其父的数据
		this.findPagePanelParentData(pagePanel, panelTable, panelList, panelObject);
		//获取panel本身的子数据
		this.findPagePanelSubData(pagePanel, panelTable, panelList, panelObject);
		//将父的和子的数据合并到根的map里
		for(Map<String,Object> map: panelList){
			requestParams.putAll(map);
		}
	
		return requestParams;

	}
	
	public Map<String, Object> getFormPanelDataForEdit(String panelName,
			String panelObjectId) {
		Map<String, Object> requestParams = new HashMap<String, Object>();
		
		PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
		if(pagePanel==null) {
			throw new ServiceException("panel name not exist");
		}
		PagePanelType ppt = pagePanel.getXtype();
		String xtype = ppt.getName();
		if(ppt==null){
			throw new ServiceException("pagePanelType cannot null");
		}
		SystemMainTable panelTable = pagePanel.getSystemMainTable();
		String panelTableName = panelTable.getTableName();
		String panelClassName = panelTable.getClassName();
		Class panelClass = null;
		Object panelObject = null;
		try {
			panelClass = Class.forName(panelClassName);
			panelObject = service.find(panelClass, panelObjectId,true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
		
		Map panelMainObjectMap = metaDataManager.getFormDataForEdit(panelObject, panelTableName);
		//Map panelMainObjectMap = BeanUtil.object2Map(parentMainObject, panelTableName);
		requestParams.putAll(panelMainObjectMap);
		//获取其父的数据
		//this.findPagePanelParentData(pagePanel, panelTable, panelList, panelObject); //注释
		//获取panel本身的子数据
		//this.findPagePanelSubData(pagePanel, panelTable, panelList, panelObject); //注释
		//将父的和子的数据合并到根的map里
		for(Map<String,Object> map: panelList){
			requestParams.putAll(map);
		}
	
		return requestParams;
	}

	public Map<String, Object> getPagePanelDataForLook(String panelName,
			String panelObjectId) {
		Map<String, Object> requestParams = new HashMap<String, Object>();
		
		PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
		if(pagePanel==null) {
			throw new ServiceException("panel name not exist");
		}
		PagePanelType ppt = pagePanel.getXtype();
		String xtype = ppt.getName();
		if(ppt==null){
			throw new ServiceException("pagePanelType cannot null");
		}
		SystemMainTable panelTable = pagePanel.getSystemMainTable();
		String panelTableName = panelTable.getTableName();
		String panelClassName = panelTable.getClassName();
		Class panelClass = null;
		Object panelObject = null;
		try {
			panelClass = Class.forName(panelClassName);
			panelObject = service.find(panelClass, panelObjectId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
		
		Map panelMainObjectMap = metaDataManager.getEntityDataForLook(panelObject, panelTableName);
		//Map panelMainObjectMap = BeanUtil.object2Map(parentMainObject, panelTableName);
		requestParams.putAll(panelMainObjectMap);
		//获取其父的数据
		this.findPagePanelParentData(pagePanel, panelTable, panelList, panelObject);
		//获取panel本身的子数据
		this.findPagePanelSubData(pagePanel, panelTable, panelList, panelObject);
		//将父的和子的数据合并到根的map里
		for(Map<String,Object> map: panelList){
			requestParams.putAll(map);
		}
	
		return requestParams;
	}

	public Map<String, Object> getPagePanelDataForQuery(String panelName) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		PagePanel panel=pagePanelService.findPagePanel(panelName);
		SystemMainTable smt = panel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = getClass(className);
		
		List<UserTableQueryColumn> userQueryColumns = metaDataManager.getUserColumnForQuery(clazz);
		
		for(int i=0; i<userQueryColumns.size(); i++){
			UserTableQueryColumn userQueryColumn = (UserTableQueryColumn) userQueryColumns.get(i);
			SystemTableQueryColumn stqc = userQueryColumn.getSystemTableQueryColumn();
			//if(stqc.getMainTableColumn()!=null){
				SystemMainTableColumn column = stqc.getMainTableColumn();
				
				String propertyName = column.getPropertyName();
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				map.put(propertyName+"s", columnWapper.getList());
//			}else if(stqc.getExtendTableColumn()!=null){
//				String propertyName = stqc.getExtendTableColumn().getPropertyName();
//				ColumnDataWrapper columnWapper = new ColumnDataWrapper(stqc.getExtendTableColumn());
//				columnWapper.initList();
//				map.put(propertyName+"s", columnWapper.getList());
//			}
		}
		return map;
	}

	public Map<String, Object> getPagePanelDataForList(String panelName) {
		Map<String, Object> requestParams = new HashMap<String, Object>();

		List<PagePanelColumn> pagePanelColumns=getUserPagePanelColumn(panelName);

		for(PagePanelColumn pagePanelColumn : pagePanelColumns){
			Column column = pagePanelColumn.getColumn();
			SystemMainTable smt = column.getSystemMainTable();
			String tableName = smt.getTableName();
			String propertyName = column.getPropertyName();
			String tablePropertyName = tableName+"$"+propertyName;
			SystemMainTableColumnType columnType = column.getSystemMainTableColumnType();
//			
//			ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
//			columnWapper.initList();
			
			if(columnType.getColumnTypeName().equalsIgnoreCase("radio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(tablePropertyName+"s", columnWapper.getList());
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("select")){
				
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				//columnWapper.initList();
//				requestParams.put(tablePropertyName+"s", columnWapper.getList());
//				if(fParentColumn!=null){//***********************************有问题*******88888
//					requestParams.put(fParentColumn.getSystemMainTable().getTableName()+"$"+fParentColumn.getPropertyName()+"s", columnWapper.getParentList());
//					requestParams.put("all"+tablePropertyName+"s", columnWapper.getAllList());
//				}
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("yesNoSelect")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(tablePropertyName+"s", columnWapper.getMap());
				
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiSelect")){

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(tablePropertyName+"s", columnWapper.getSourceList());
			}
		}
		//requestParams.toString();
		return requestParams;
	}

	public Map<String, Object> getPagePanelDataForAdd(String panelName) {

		Map<String, Object> requestParams = new HashMap<String, Object>();

		List<PagePanelColumn> pagePanelColumns=getUserPagePanelColumn(panelName);

		for(PagePanelColumn pagePanelColumn : pagePanelColumns){
			Column column = pagePanelColumn.getColumn();
			SystemMainTable smt = column.getSystemMainTable();
			String tableName = smt.getTableName();
			String propertyName = column.getPropertyName();
			String tablePropertyName = tableName+"$"+propertyName;
			SystemMainTableColumnType columnType = column.getSystemMainTableColumnType();
			if(columnType.getColumnTypeName().equalsIgnoreCase("radio")){
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(tablePropertyName+"s", columnWapper.getList());
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("select")){
				
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(tablePropertyName+"s", columnWapper.getList());
				if(fParentColumn!=null){//***********************************有问题*******88888
					requestParams.put(fParentColumn.getSystemMainTable().getTableName()+"$"+fParentColumn.getPropertyName()+"s", columnWapper.getParentList());
					requestParams.put("all"+tablePropertyName+"s", columnWapper.getAllList());
				}
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("extSelect")){
				
				SystemMainTableColumn fParentColumn = column.getForeignTableParentColumn();

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("yesNoSelect")
					||columnType.getColumnTypeName().equalsIgnoreCase("yesNoRadio")){
				
				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(tablePropertyName+"s", columnWapper.getMap());
				
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("multiSelect")){

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(tablePropertyName+"s", columnWapper.getSourceList());
				
			}else if(columnType.getColumnTypeName().equalsIgnoreCase("checkboxGroup")){

				ColumnDataWrapper columnWapper = new ColumnDataWrapper(column);
				columnWapper.initList();
				requestParams.put(tablePropertyName+"s", columnWapper.getSourceList());
			}
	}
		//requestParams.toString();
		return requestParams;

	}

	public List<PagePanelColumn> getUserPagePanelColumn(String panelName) {
		PagePanel panel=pagePanelService.findPagePanel(panelName);
		//System.out.println(pagePanelColumnService);
		List<PagePanelColumn> pagePanelColumns=pagePanelColumnService.findColumnByPanel(panel);
	//	System.out.println(pagePanelColumns);
		return pagePanelColumns;
	}

	public List<PagePanelColumn> getUserPagePanelColumn(PagePanel panel) {
		
		List<PagePanelColumn> pagePanelColumns=pagePanelColumnService.findColumnByPanel(panel);
	//	System.out.println(pagePanelColumns);
		return pagePanelColumns;
	}
	
	@SuppressWarnings("unchecked")
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
	
	public PagePanelColumnService getPagePanelColumnService() {
		return pagePanelColumnService;
	}

	public void setPagePanelColumnService(
			PagePanelColumnService pagePanelColumnService) {
		this.pagePanelColumnService = pagePanelColumnService;
	}
	
	public void setMetaDataManager(MetaDataManager metaDataManager) {
		this.metaDataManager = metaDataManager;
	}

	public void setPageModelPanelService(PageModelPanelService pageModelPanelService) {
		this.pageModelPanelService = pageModelPanelService;
	}

	public void setPageModelService(PageModelService pageModelService) {
		this.pageModelService = pageModelService;
	}

	public void setPagePanelService(PagePanelService pagePanelService) {
		this.pagePanelService = pagePanelService;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public void setSystemColumnService(SystemColumnService systemColumnService) {
		this.systemColumnService = systemColumnService;
	}
	
	public void setPagePanelTableService(PagePanelTableService pagePanelTableService) {
		this.pagePanelTableService = pagePanelTableService;
	}

	public void setPageQueryService(PageQueryService pageQueryService) {
		this.pageQueryService = pageQueryService;
	}



}