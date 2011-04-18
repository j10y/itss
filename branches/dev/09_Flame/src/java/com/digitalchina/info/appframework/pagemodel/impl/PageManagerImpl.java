package com.digitalchina.info.appframework.pagemodel.impl;

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

import com.digitalchina.info.appframework.metadata.ColumnDataWrapper;
import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.PropertyType;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.digitalchina.info.appframework.metadata.entity.UserTableQueryColumn;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.appframework.pagemodel.PageManager;
import com.digitalchina.info.appframework.pagemodel.PageQueryService;
import com.digitalchina.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
import com.digitalchina.info.appframework.pagemodel.service.PageGroupPanelService;
import com.digitalchina.info.appframework.pagemodel.service.PageModelPanelService;
import com.digitalchina.info.appframework.pagemodel.service.PageModelService;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelColumnService;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelService;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelTableService;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.BeanUtil;

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
	
	
//	�ݹ���û�ȡpanel�����ݵķ�������Ҫ����Ƿ���Ҫ��ڲ���
	private void findPagePanelParentData(
									PagePanel pagePanel, 
									SystemMainTable subSmt, 
									List<Map<String,Object>> list,
									Object currentObject){
		
		if(list.size()<=1){//˵��panel����grid panel
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
				//ȡ��list�е�map
				Map<String,Object> listMap = list.iterator().next();
				
				BeanWrapper bw = new BeanWrapperImpl(currentObject);
				Object parentObject = bw.getPropertyValue(stcPropName);
				//����ע�⸸����������Ͳ�ȷ������������Ŀͻ�������ͻ�,��������չ
				if(parentObject!=null){
					bw.setWrappedInstance(parentObject);
					Long parentObjectId = (Long) bw.getPropertyValue("id");
					Object parentObjectFull = service.findUnique(parentTableClass, "id", parentObjectId);
					Map subObjectMap = metaDataManager.getEntityDataForEdit(parentObjectFull, parentTableTableName);
						//BeanUtil.object2Map(parentObjectFull, parentTableTableName);
					listMap.putAll(subObjectMap);
					//�����Ҹ���
					findPagePanelParentData(pagePanel, parentTable, list, parentObjectFull);
				}
				
			}
		}
		
	}
	
	//�ݹ���û�ȡpanel�����ݵķ�������Ҫ����Ƿ���Ҫ��ڲ���, ��ѡ������Ȳ�����
	private void findPagePanelSubData(
									PagePanel pagePanel, 
									SystemMainTable parentSmt, 
									List<Map<String,Object>> list,
									Object currentObject){
		
		if(list.size()==1){//˵��panel����grid panel
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
				//ȡ��list�е�map
				Map<String,Object> listMap = list.iterator().next();
				
				List<Object> subObjects = service.find(subTableClass, stcPropName, currentObject);
				if(!subObjects.isEmpty()){
					if(subObjects.size()==1){
						Object subObject = subObjects.iterator().next();
						Map subObjectMap = metaDataManager.getEntityDataForEdit(subObject, subTableName);
							//BeanUtil.object2Map(subObject, subTableName);
						listMap.putAll(subObjectMap);
						//������������
						findPagePanelSubData(pagePanel, subTable, list, subObject);
					}else{ 
						if(list.isEmpty()){ //�϶���ͨ����������б�
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
	 * ͨ��model��������idץȡ����������ݡ�
	 * Ŀǰֻʵ�ֶ��һ��һ��һ����
	 */
	public Map<String,List<Map<String,Object>>> getPageModelDataForEdit(String model, String mainPanelDataId) {
		if(StringUtils.isBlank(mainPanelDataId)){
			throw new ServiceException("model main object id is needed");
		}
		Map<String,List<Map<String,Object>>> allResult = new HashMap<String,List<Map<String,Object>>>();
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		//�洢����ͱ�������panel�еĶ���
		Map<SystemMainTable,Object> loopedTableObject = new HashMap<SystemMainTable,Object>();
		Map<SystemMainTable,Object> notLoopedTableObject = new HashMap<SystemMainTable,Object>();
		Map<PagePanel,Object> loopedPanels = new HashMap<PagePanel,Object>();
		
		PageModel pageModel = pageModelService.findPageModel(model);
		if(pageModel==null) {
			throw new ServiceException("page model '"+model+"' not exist");
		}
		//ȡmodel���������������configItem
		PagePanel mainPanel = null;
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		if(modelMainTable==null){
			throw new ServiceException("page model '"+model+"' need systemMainTable");
		}
	
		mainPanel = pageModel.getMainPagePanel();
		//ȡ���model�µ�����panel, ��configItemPanel,financePanel,customerPanel
//		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
//		for(PageModelPanel pmp : pmps){ 
//			//����һ��panel, �� configItemPanel
//			PagePanel pagePanel = pmp.getPagePanel(); //configItemPanel
//			SystemMainTable panelMainTable = pagePanel.getSystemMainTable();
//			if(panelMainTable==modelMainTable){
//				mainPanel = pagePanel;
//				break;
//			}
//		}
		//ȡpanel������
		PagePanelType pagePanelType = mainPanel.getXtype();
		if(pagePanelType==null){
			throw new ServiceException(mainPanel.getName()+" must select panel type");
		}
		String xtypeName = pagePanelType.getName();
		
		//�����panel�е�����
		List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
		
		//ͨ��������id��ȡ����Ķ���
		String panelTableName = modelMainTable.getTableName();
		String panelTableClsName = modelMainTable.getClassName();
		Class panelClass = null;
		Object parentMainObject = null;
		try {
			panelClass = Class.forName(panelTableClsName);
			//������
			parentMainObject = service.find(panelClass, mainPanelDataId, true); //��ʵ������չ�ֶ��޷���ȡ
			if(parentMainObject==null){
				throw new ServiceException("not object fount");
			}
			//��������ת��Map��ʽ���˷������Խ���չ����Ҳ��������ͬʱ���ص�key׷���˱�ǰ׺
			Map panelMainObjectMap = metaDataManager.getEntityDataForEdit(parentMainObject, panelTableName);
			//Map panelMainObjectMap = BeanUtil.object2Map(parentMainObject, panelTableName);
			//����panel�е������м��������������
			panelList.add(panelMainObjectMap);
			//���¶��ǻ�ȡ��ǰ����еĸ�����壬���漰model�¶�����ϵ
			//��ȡ�丸�����ݣ����������ŵĺܶ��ֶ�
			this.findPagePanelParentData(mainPanel, modelMainTable, panelList, parentMainObject);
			//��ȡpanel����������ݣ���������Ĳ���������
			this.findPagePanelSubData(mainPanel, modelMainTable, panelList, parentMainObject);
			//���ݶ�׼�����ˣ�����panelList���ˣ�����򷵻ؽ��������panel����
			allResult.put(mainPanel.getName(), panelList);
			
			//�ݴ�˱������Ķ�����������panel����Ҫʱ��ȡ
			loopedTableObject.put(modelMainTable, parentMainObject);
			//loopedPanels.put(mainPanel, parentMainObject);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//��ȡ������panel���������panel�ͱ���financePanel��configItem
		List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel, mainPanel, modelMainTable);
		for(PageModelPanelTable item: pmptSubs){
			//loop to financePanel.configItem
			PagePanel subPagePanel = item.getSubPagePanel();
			//�洢��panel������
			List<Map<String,Object>> subPanelList = new ArrayList<Map<String,Object>>();
			
			PagePanelType ppt = subPagePanel.getXtype();
			String xtype = ppt.getName();
			if(ppt==null){
				throw new ServiceException("pagePanelType cannot null");
			}
			SystemMainTable subPanelTable = item.getSubPanelTable();
			String subPanelTableName = subPanelTable.getTableName();
			//�ӱ�������������panel����
			SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
			String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
			String subPanelClassName = subPanelTable.getClassName();
			Class subPanelClass = null;
			panelList = null;
			try {
				subPanelClass = Class.forName(subPanelClassName);
				//��ͨ�������ȡ��panel������
				Object parentTableObject = loopedTableObject.get(modelMainTable);
				List<Object> subObjects = service.find(subPanelClass, subPanelTableFPropName, parentTableObject);
				if(!subObjects.isEmpty()){//�����panel�����ݴ���
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
					//�򷵻ؽ��������panel����
					allResult.put(subPagePanel.getName(), subPanelList);
				}else{ //���������panel�е����ݲ����ڣ�ԭ������Ǵ�ҳ���е�panel�Ǻ����ӽ�����
					if(xtype.equalsIgnoreCase("form")){
						Object subObject = subPanelClass.newInstance();//new��һ���յĶ��󣬱�֤ҳ���panel����ʾ
						//modify by lee for �����ն����һ�Զ�������δ�����BUG in 20091208 begin
						//Map subObjectMap = BeanUtil.object2Map(subObject, subPanelTableName);
						Map subObjectMap = metaDataManager.getEntityDataForEdit(subObject, subPanelTableName);
						//modify by lee for �����ն����һ�Զ�������δ�����BUG in 20091208 begin
						subPanelList.add(subObjectMap);
					}
					allResult.put(subPagePanel.getName(), subPanelList);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}//end ��ȡ������panel���������panel�ͱ���financePanel��configItem
		//*****************
		
		//��ȡ������panel�ĸ����
		List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, mainPanel, modelMainTable);
		for(PageModelPanelTable item: pmptParents){
			//loop to financePanel.configItem
			PagePanel parentPagePanel = item.getParentPagePanel();
			//�洢��panel������
			List<Map<String,Object>> parentPanelList = new ArrayList<Map<String,Object>>();
			
			PagePanelType ppt = parentPagePanel.getXtype();
			String xtype = ppt.getName();
			if(ppt==null){
				throw new ServiceException("pagePanelType cannot null");
			}
			SystemMainTable parentPanelTable = item.getParentPanelTable();
			String parentPanelTableName = parentPanelTable.getTableName();
			//�ӱ�������������panel����
			SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
			String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
			
			SystemMainTableColumn parentPk = item.getParentPanelTablePColumn();
			String parentPkName = parentPk.getPropertyName();
			
			String parentPanelClassName = parentPanelTable.getClassName();
			Class parentPanelClass = null;
			panelList = null;
			try {
				parentPanelClass = Class.forName(parentPanelClassName);
				//��ͨ�������ȡ��panel������
				Object subTableObject = loopedTableObject.get(modelMainTable);
				BeanWrapper subObjWrapper = new BeanWrapperImpl(subTableObject);
				//��ȡ��������������������������϶���,subPanelTableFPropName=sp
				Object parentObject = subObjWrapper.getPropertyValue(subPanelTableFPropName);
				if(parentObject==null){
					throw new ServiceException(subTableObject.getClass().getName()+"���õĸ����󲻴���");
				}
				
				Map subObjectMap = metaDataManager.getEntityDataForEdit(parentObject, parentPanelTableName);
				parentPanelList.add(subObjectMap);
				allResult.put(parentPagePanel.getName(), parentPanelList);
				
				
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}//end ��ȡ������panel���������panel�ͱ���financePanel��configItem
	
		return allResult;
	}
	
	
	public Map<String,List<Map<String,Object>>> getPageModelDataForLook(String model, String mainPanelDataId) {
		if(StringUtils.isBlank(mainPanelDataId)){
			throw new ServiceException("model main object id is needed");
		}
		Map<String,List<Map<String,Object>>> allResult = new HashMap<String,List<Map<String,Object>>>();
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		//�洢����ͱ�������panel�еĶ���
		Map<SystemMainTable,Object> loopedTableObject = new HashMap<SystemMainTable,Object>();
		Map<SystemMainTable,Object> notLoopedTableObject = new HashMap<SystemMainTable,Object>();
		Map<PagePanel,Object> loopedPanels = new HashMap<PagePanel,Object>();
		
		PageModel pageModel = pageModelService.findPageModel(model);
		if(pageModel==null) {
			throw new ServiceException("page model '"+model+"' not exist");
		}
		//ȡmodel���������������configItem
		PagePanel mainPanel = null;
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		if(modelMainTable==null){
			throw new ServiceException("page model '"+model+"' need systemMainTable");
		}
	
		mainPanel = pageModel.getMainPagePanel();
		//ȡ���model�µ�����panel, ��configItemPanel,financePanel,customerPanel
//		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
//		for(PageModelPanel pmp : pmps){ 
//			//����һ��panel, �� configItemPanel
//			PagePanel pagePanel = pmp.getPagePanel(); //configItemPanel
//			SystemMainTable panelMainTable = pagePanel.getSystemMainTable();
//			if(panelMainTable==modelMainTable){
//				mainPanel = pagePanel;
//				break;
//			}
//		}
		//ȡpanel������
		PagePanelType pagePanelType = mainPanel.getXtype();
		if(pagePanelType==null){
			throw new ServiceException(mainPanel.getName()+" must select panel type");
		}
		String xtypeName = pagePanelType.getName();
		
		//�����panel�е�����
		List<Map<String,Object>> panelList = new ArrayList<Map<String,Object>>();
		
		//ͨ��������id��ȡ����Ķ���
		String panelTableName = modelMainTable.getTableName();
		String panelTableClsName = modelMainTable.getClassName();
		Class panelClass = null;
		Object parentMainObject = null;
		try {
			panelClass = Class.forName(panelTableClsName);
			//������
			parentMainObject = service.find(panelClass, mainPanelDataId, true); //��ʵ������չ�ֶ��޷���ȡ
			if(parentMainObject==null){
				throw new ServiceException("not object fount");
			}
			//��������ת��Map��ʽ���˷������Խ���չ����Ҳ��������ͬʱ���ص�key׷���˱�ǰ׺
			Map panelMainObjectMap = metaDataManager.getEntityDataForLook(parentMainObject, panelTableName);
			//Map panelMainObjectMap = BeanUtil.object2Map(parentMainObject, panelTableName);
			//����panel�е������м��������������
			panelList.add(panelMainObjectMap);
			//���¶��ǻ�ȡ��ǰ����еĸ�����壬���漰model�¶�����ϵ
			//��ȡ�丸�����ݣ����������ŵĺܶ��ֶ�
			this.findPagePanelParentData(mainPanel, modelMainTable, panelList, parentMainObject);
			//��ȡpanel����������ݣ���������Ĳ���������
			this.findPagePanelSubData(mainPanel, modelMainTable, panelList, parentMainObject);
			//���ݶ�׼�����ˣ�����panelList���ˣ�����򷵻ؽ��������panel����
			allResult.put(mainPanel.getName(), panelList);
			
			//�ݴ�˱������Ķ�����������panel����Ҫʱ��ȡ
			loopedTableObject.put(modelMainTable, parentMainObject);
			//loopedPanels.put(mainPanel, parentMainObject);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//��ȡ������panel���������panel�ͱ���financePanel��configItem
		List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel, mainPanel, modelMainTable);
		for(PageModelPanelTable item: pmptSubs){
			//loop to financePanel.configItem
			PagePanel subPagePanel = item.getSubPagePanel();
			//�洢��panel������
			List<Map<String,Object>> subPanelList = new ArrayList<Map<String,Object>>();
			
			PagePanelType ppt = subPagePanel.getXtype();
			String xtype = ppt.getName();
			if(ppt==null){
				throw new ServiceException("pagePanelType cannot null");
			}
			SystemMainTable subPanelTable = item.getSubPanelTable();
			String subPanelTableName = subPanelTable.getTableName();
			//�ӱ�������������panel����
			SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
			String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
			String subPanelClassName = subPanelTable.getClassName();
			Class subPanelClass = null;
			panelList = null;
			try {
				subPanelClass = Class.forName(subPanelClassName);
				//��ͨ�������ȡ��panel������
				Object parentTableObject = loopedTableObject.get(modelMainTable);
				List<Object> subObjects = service.find(subPanelClass, subPanelTableFPropName, parentTableObject);
				if(!subObjects.isEmpty()){//�����panel�����ݴ���
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
					//�򷵻ؽ��������panel����
					allResult.put(subPagePanel.getName(), subPanelList);
				}else{ //���������panel�е����ݲ����ڣ�ԭ������Ǵ�ҳ���е�panel�Ǻ����ӽ�����
					if(xtype.equalsIgnoreCase("form")){
						Object subObject = subPanelClass.newInstance();//new��һ���յĶ��󣬱�֤ҳ���panel����ʾ
						Map subObjectMap = BeanUtil.object2Map(subObject, subPanelTableName);
						subPanelList.add(subObjectMap);
					}
					allResult.put(subPagePanel.getName(), subPanelList);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}//end ��ȡ������panel���������panel�ͱ���financePanel��configItem
		//*****************
		
		//��ȡ������panel�ĸ����
		List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, mainPanel, modelMainTable);
		for(PageModelPanelTable item: pmptParents){
			//loop to financePanel.configItem
			PagePanel parentPagePanel = item.getParentPagePanel();
			//�洢��panel������
			List<Map<String,Object>> parentPanelList = new ArrayList<Map<String,Object>>();
			
			PagePanelType ppt = parentPagePanel.getXtype();
			String xtype = ppt.getName();
			if(ppt==null){
				throw new ServiceException("pagePanelType cannot null");
			}
			SystemMainTable parentPanelTable = item.getParentPanelTable();
			String parentPanelTableName = parentPanelTable.getTableName();
			//�ӱ�������������panel����
			SystemMainTableColumn subPanelTableFColumn = item.getSubPanelTableFColumn();
			String subPanelTableFPropName = subPanelTableFColumn.getPropertyName();
			
			SystemMainTableColumn parentPk = item.getParentPanelTablePColumn();
			String parentPkName = parentPk.getPropertyName();
			
			String parentPanelClassName = parentPanelTable.getClassName();
			Class parentPanelClass = null;
			panelList = null;
			try {
				parentPanelClass = Class.forName(parentPanelClassName);
				//��ͨ�������ȡ��panel������
				Object subTableObject = loopedTableObject.get(modelMainTable);
				BeanWrapper subObjWrapper = new BeanWrapperImpl(subTableObject);
				//��ȡ��������������������������϶���,subPanelTableFPropName=sp
				Object parentObject = subObjWrapper.getPropertyValue(subPanelTableFPropName);
				if(parentObject==null){
					throw new ServiceException(subTableObject.getClass().getName()+"���õĸ����󲻴���");
				}
				
				Map subObjectMap = metaDataManager.getEntityDataForLook(parentObject, parentPanelTableName);
				parentPanelList.add(subObjectMap);
				allResult.put(parentPagePanel.getName(), parentPanelList);
				
				
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}//end ��ȡ������panel���������panel�ͱ���financePanel��configItem
	
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
	 * ��ȡ��ǰ����е���������
	 * @deprecated
	 */
	public Map<String, Object> getPageModelDataForEdit(String model, String panel, String mainPanelDataId) {
		if(StringUtils.isBlank(mainPanelDataId)){
			throw new ServiceException("panel main object id is needed!");
		}
		Map<String,Object> result = new HashMap<String,Object>();
		//��ǰ���, ��configItemPanel
		PagePanel pagePanel = pagePanelService.findPagePanel(panel);
		if(pagePanel==null) {
			throw new ServiceException("panel name not exist");
		}
		//��ǰ������������, configItem
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
			result.putAll(map);//����ʵ��ļ�ֵ����Ϣ��������ֵ��
		} catch (Exception e) {
			e.printStackTrace();
		}		
		/*Set<SystemMainTable> smtSet = new HashSet<SystemMainTable>();
		smtSet.add(panelSmt);*/
		
		//��ȡ��ǰ������漰����������
		List<SystemMainTable> tables = pagePanelService.findMainTableByPanel(pagePanel);
		for(SystemMainTable smt : tables){
			if(smt!=panelSmt){ //����ע���Ƿ�����ж����
				String otherTableClass = panelSmt.getClassName();
				Class otherClass = null;
				Object otherObject = null;
				try {
					otherClass = Class.forName(otherTableClass);
					otherObject = otherClass.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}		
				//��ȡ���������������ֶ�
				List<Column> columns = systemColumnService.findSystemTableColumns(smt);
				for(Column column : columns){
					PropertyType pt = column.getPropertyType();
					String ptname = pt.getName();
					if(ptname.equalsIgnoreCase("BaseObject")){
						String propertyName = column.getPropertyName();
						SystemMainTable ftable = column.getForeignTable();
						String ftableName = ftable.getTableName();
						String fclass = ftable.getClassName();
						//panel��ĳ���ֶι�����panel������ͨ������Ķ���id��ȡpanel�����������
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
		
		//��ͨ��������Ϣ��ȡ�����̵Ķ���ֶΣ��ȱ����´����ķ�������Ϣ���ڰѷ����̶���id���ø�������Ϣ�ķ���������ֶ�
		List<PagePanelTableRelation> pptrParent = pagePanelService.findPanelTableRelBySub(mainPanel, subTable);
		
		for(PagePanelTableRelation pptr : pptrParent){
			//�������������ݹ������ӱ�
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
			//����������������ֵ����ȥ�˱�ǰ׺��map��
			List<Column> columns = systemColumnService.findSystemTableColumns(parentTable);//root apply
			for(Column column : columns){
				String propertyName = column.getPropertyName();
				String tableColumnName = parentTableName+"$"+propertyName;
				if(objectMap.containsKey(tableColumnName)){
					Object columnValue = objectMap.get(tableColumnName);
					tempMap.put(propertyName, columnValue);
				}
			}
			//���游����������̣�Ϊ�˻�ȡ���󸸶���id
			Object parentObject = metaDataManager.saveEntityData(parentTableClass, tempMap);
			if(parentObject!=null){
				//�����������ֶΣ���������Ϣ�ķ���������ֶ�
				SystemMainTableColumn parentFKColumn = pptr.getForeignTableColumn();
				String parentFKPropertyName = parentFKColumn.getPropertyName();
				//apply$support
				parentFKPropertyName = parentTableName +"$"+ parentFKPropertyName; 
				BeanWrapper wb = new BeanWrapperImpl(parentObject);
				Long parentObjectId = (Long) wb.getPropertyValue("id");
				//���Ӷ��������ֶ��ø������id��ʼ��
				objectMap.put(parentFKPropertyName, parentObjectId);
			}
			
			//List<PagePanelTableRelation> pptrSub = pagePanelService.findPanelTableRelByParent(mainPanel, subTable);
			
		}
	}
	
	
	private void saveSubTableData(PagePanel mainPanel, 
			SystemMainTable parentRootTable, Map<String, Object> objectMap){
	
		//��ͨ��������Ϣ��ȡ�����̵Ķ���ֶΣ��ȱ����´����ķ�������Ϣ���ڰѷ����̶���id���ø�������Ϣ�ķ���������ֶ�
		List<PagePanelTableRelation> pptrSubs = pagePanelService.findPanelTableRelByParent(mainPanel, parentRootTable);
		
		for(PagePanelTableRelation pptr : pptrSubs){
			//�������������ݹ������ӱ�
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
			//��ȡ�Ӷ��������ֶ�
			SystemMainTableColumn parentFKColumn = pptr.getForeignTableColumn();
			String parentFKPropertyName = parentFKColumn.getPropertyName();
			//�Ӷ�������ֶ���������
			String tableParentFKPropertyName = parentTableName +"$"+ parentFKPropertyName;
			//������id�ֶ�
			String parentObjectId = parentTableName +"$id";
			//������id�ֶε�ֵ
			Long parentObjectIdValue = (Long) objectMap.get(parentObjectId);
			//���Ӷ��������ֶ����ø�����id��ֵ
			objectMap.put(tableParentFKPropertyName, parentObjectIdValue);
			tempMap.put(parentFKPropertyName, parentObjectIdValue);
			
			Object subObject = metaDataManager.saveEntityData(subTableClass, tempMap);
			
			
		}
	}
	
	/**
	 * ����������ݹ��÷���
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
			throw new ServiceException(subPanel.getName()+" ����ѡ���������");
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
		// ��ȡmodel�������
		PagePanel mainPanel = pageModel.getMainPagePanel();
		if (mainPanel == null) {
			throw new ServiceException(pageModel.getName() + " ����ѡ�������");
		}
		PagePanelType pagePanelType = mainPanel.getXtype();
		if (pagePanelType == null) {
			throw new ServiceException(mainPanel.getName() + " ����ѡ���������");
		}
		String xtype = pagePanelType.getName();
		// ͨ��������id��ȡ����Ķ���
		String panelTableName = modelMainTable.getTableName();
		String panelTableClsName = modelMainTable.getClassName();
		Class panelClass = null;
		Object rootObject = null;
		try {
			panelClass = Class.forName(panelTableClsName);
			// begin���������****************************************
			List<Map<String, Object>> list = modelDataMap.get(mainPanel
					.getName());
			if (xtype.equalsIgnoreCase("form")) {// �����panel��������formpanel
				if (!list.isEmpty()) {
					Map<String, Object> objectMap = list.iterator().next();
					// begin���浱ǰ���******************************************
					// �ȱ��浱ǰ������ĸ�����
					if (pagePanel.getName() == mainPanel.getName()) {// ����������Ϊ������򱣴�
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
					// ���浱ǰ��������Ӷ�������
					this.saveSubTableData(mainPanel, modelMainTable, objectMap);
					// end���浱ǰ���******************************************
					}
					List<PageModelPanel> modelPanels = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
					for (PageModelPanel pmp : modelPanels) {
						PagePanel pp = pmp.getPagePanel();
						if (pp.getGroupFlag() != null
								&& pp.getGroupFlag().intValue() == 1) {
							// ͨ��������壬��ǰ����壬��ȡ��������µ�������ϵ��Ϣ
							List<PageGroupPanelTable> pgpts = pageGroupPanelService.findGroupPanelTableByParent(pp, mainPanel);
							for (PageGroupPanelTable pgpt : pgpts) {
								// �����ڲ��ͻ�������壬�����и��ڲ��ͻ���ϵ�������
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
					// ���浱ǰҳ��pageModel��������panel�Ķ���ֵ
					List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel,mainPanel, modelMainTable);
					for (PageModelPanelTable item : pmptSubs) {
						if (item.getSubPagePanel().getName() == pagePanel.getName()) {
							// loop to financePanel.configItem
							PagePanel subPagePanel = item.getSubPagePanel();
							// �洢��panel������
							PagePanelType ppt = subPagePanel.getXtype();
							if (ppt == null) {
								throw new ServiceException(
										"pagePanelType cannot null");
							}
							String subXtype = ppt.getName();
							if (subXtype.equalsIgnoreCase("form")) {// �������form
								SystemMainTable subPanelTable = item.getSubPanelTable();
								String subPanelTableName = subPanelTable.getTableName();
								// ��ȡ��panel������list
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
									// �����Ӷ���
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

							} else if (subXtype.equalsIgnoreCase("editorgrid")) {// �������grid

								SystemMainTable subPanelTable = item
										.getSubPanelTable();
								String subPanelTableName = subPanelTable
										.getTableName();
								// ��ȡ��panel������list
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
									// �����Ӷ���

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

						}// end���������
						// ******************************************
						List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, null,null);
					}
				}

			} else if (xtype.equalsIgnoreCase("editorgrid")) {// ��panel��������editorgrid����ֻ�����Լ�����
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
			}// end���������****************************************

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataId;
	}
	public Object savePageModelData(String model, Map<String, List<Map<String, Object>>> modelDataMap) {
		PageModel pageModel = pageModelService.findPageModel(model);
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		
		//��ȡmodel�������
		PagePanel mainPanel = pageModel.getMainPagePanel();
		if(mainPanel==null){
			throw new ServiceException(pageModel.getName()+" ����ѡ�������");
		}
		PagePanelType pagePanelType = mainPanel.getXtype();
		if(pagePanelType==null){
			throw new ServiceException(mainPanel.getName()+" ����ѡ���������");
		}
		String xtype = pagePanelType.getName();
//		ͨ��������id��ȡ����Ķ���	
		String panelTableName = modelMainTable.getTableName();
		String panelTableClsName = modelMainTable.getClassName();
		Class panelClass = null;
		Object rootObject = null;
		try {
			panelClass = Class.forName(panelTableClsName);
			//begin���������****************************************
			List<Map<String, Object>> list = modelDataMap.get(mainPanel.getName());
			if(xtype.equalsIgnoreCase("form")){//�����panel��������formpanel
				if(!list.isEmpty()){
					Map<String, Object> objectMap = list.iterator().next();
					//begin���浱ǰ���******************************************
					//�ȱ��浱ǰ������ĸ�����
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
					
					//���浱ǰ��������Ӷ�������
					this.saveSubTableData(mainPanel, modelMainTable, objectMap);
					//end���浱ǰ���******************************************
					
					List<PageModelPanel> modelPanels = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
					for(PageModelPanel pmp : modelPanels){
						PagePanel pp = pmp.getPagePanel();
						if(pp.getGroupFlag()!=null&& pp.getGroupFlag().intValue()==1){
							//ͨ��������壬��ǰ����壬��ȡ��������µ�������ϵ��Ϣ
							List<PageGroupPanelTable> pgpts = pageGroupPanelService.findGroupPanelTableByParent(pp, mainPanel);
							for(PageGroupPanelTable pgpt : pgpts){
								//�����ڲ��ͻ�������壬�����и��ڲ��ͻ���ϵ�������
								PagePanel subPanel = pgpt.getSubPagePanel();
								SystemMainTableColumn fc = pgpt.getSubPanelTableFColumn();
								String fcName = fc.getPropertyName();
								this.savePanelData(subPanel, modelDataMap, fcName, (BaseObject)rootObject);
								
							}
//							
						}
					}
						
					//******************************************
					//���浱ǰҳ��pageModel��������panel�Ķ���ֵ
					List<PageModelPanelTable> pmptSubs = pageModelService.findPageModelPanelTableByParent(pageModel, mainPanel, modelMainTable);
					for(PageModelPanelTable item: pmptSubs){
						//loop to financePanel.configItem
						PagePanel subPagePanel = item.getSubPagePanel();
						//�洢��panel������
						
						PagePanelType ppt = subPagePanel.getXtype();
						if(ppt==null){
							throw new ServiceException("pagePanelType cannot null");
						}
						String subXtype = ppt.getName();
						if(subXtype.equalsIgnoreCase("form")){//�������form
							SystemMainTable subPanelTable = item.getSubPanelTable();
							String subPanelTableName = subPanelTable.getTableName();
							//��ȡ��panel������list
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
								//�����Ӷ���
								
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
							
							
						}else if(subXtype.equalsIgnoreCase("editorgrid")){//�������grid
							
							SystemMainTable subPanelTable = item.getSubPanelTable();
							String subPanelTableName = subPanelTable.getTableName();
							//��ȡ��panel������list
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
								//�����Ӷ���
								
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
						
					}//end���������
					//******************************************
					List<PageModelPanelTable> pmptParents = pageModelService.findPageModelPanelTableBySub(pageModel, null,null);
				}
				
			}else if(xtype.equalsIgnoreCase("editorgrid")){//��panel��������editorgrid����ֻ�����Լ�����
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
			}//end���������****************************************
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return rootObject;
		
	}

	/**
	 * ����model��ָ��panel�е�����
	 * �޸�ʱ�����ֱ�ӵĹ��������������ֶα��棬������ʱ�����ֶ�ֵ��������Ҫ��̨�ֶδ���
	 */
	public Object savePageModelData(String model, String panel,
			Map<String, Object> columnDataMap) {
		Object mainObject = null;
		
		PageModel pageModel = pageModelService.findPageModel(model);
		SystemMainTable modelMainTable = pageModel.getSystemMainTable();
		
		//��ȡmodel�������
		PagePanel mainPanel = null;
		List<PageModelPanel> pmps = pageModelPanelService.findPageModelPanelByPageModel(pageModel);
		for(PageModelPanel pmp : pmps){ 
			//����һ��panel, �� configItemPanel
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
		
		
		//ȡpanel�е����������������ֱ�ӹ���
		List<SystemMainTable> list = pagePanelService.findMainTableByPanel(pagePanel);
		smtSet.addAll(list);
		for(SystemMainTable smt : smtSet){
			Map objectMap = new HashMap(); //����һ�������������ֵ
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
					//����������id
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
		SystemMainTable smt = pp.getSystemMainTable(); //*************��ʱȡ1�ű�
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
		while(iter.hasNext()){//����ÿ�м�¼��ȡ��list�е�ʵ��BaseObject item
			Map<String, Object> item = new HashMap<String, Object>(); //�洢ÿ����¼�����һ��ʵ�����
			BaseObject object = (BaseObject) iter.next();
			Long mainTableRowId = object.getId(); //����ǰ��¼��id���кţ�

			for(PagePanelColumn ppc : ppcs){
				Column column = ppc.getColumn();
				SystemMainTable smt = column.getSystemMainTable();
				String tableName = smt.getTableName();
				Object propertyValue = null;
				SystemMainTableColumnType mcType = column.getSystemMainTableColumnType();
				String typeName = mcType.getColumnTypeName();
				String propertyName = column.getPropertyName(); //��ǰ�����Ե�����				
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
	 * ���2���ۺϲ�ѯ����
	 */
	public Page query(String panelName, Map<String, Object> queryParams, 
					int pageNo, int pageSize, String orderProp, boolean isAsc) {
		//ת��ǰ�˵Ĳ�ѯ�����������ǵ�map
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
	 *�ṩpanel�ؼ��֣�panel������id��ȡpanel�е��������ݣ����ؽ������panel������column����������
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
		//��ȡ�丸������
		this.findPagePanelParentData(pagePanel, panelTable, panelList, panelObject);
		//��ȡpanel�����������
		this.findPagePanelSubData(pagePanel, panelTable, panelList, panelObject);
		//�����ĺ��ӵ����ݺϲ�������map��
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
		//��ȡ�丸������
		//this.findPagePanelParentData(pagePanel, panelTable, panelList, panelObject); //ע��
		//��ȡpanel�����������
		//this.findPagePanelSubData(pagePanel, panelTable, panelList, panelObject); //ע��
		//�����ĺ��ӵ����ݺϲ�������map��
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
		//��ȡ�丸������
		this.findPagePanelParentData(pagePanel, panelTable, panelList, panelObject);
		//��ȡpanel�����������
		this.findPagePanelSubData(pagePanel, panelTable, panelList, panelObject);
		//�����ĺ��ӵ����ݺϲ�������map��
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
//				if(fParentColumn!=null){//***********************************������*******88888
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
				if(fParentColumn!=null){//***********************************������*******88888
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
			System.out.print("������" + className + "����ȷ��");
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