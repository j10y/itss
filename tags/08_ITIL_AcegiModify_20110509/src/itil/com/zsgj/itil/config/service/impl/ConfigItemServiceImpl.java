package com.zsgj.itil.config.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.itil.config.dao.ConfigItemDao;
import com.zsgj.itil.config.entity.CIBatchModify;
import com.zsgj.itil.config.entity.CIBatchModifyPlan;
import com.zsgj.itil.config.entity.CIBatchModifyShip;
import com.zsgj.itil.config.entity.CIRelationShip;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ConfigItemExtendInfo;
import com.zsgj.itil.config.entity.ConfigItemFinanceInfo;
import com.zsgj.itil.config.entity.ConfigItemNecessaryRel;
import com.zsgj.itil.config.entity.ConfigItemStatus;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.config.service.ConfigItemService;
import com.zsgj.itil.event.entity.Problem;
import com.zsgj.itil.require.entity.SpecialRequirement;
import com.zsgj.itil.service.entity.ServiceItem;


public class ConfigItemServiceImpl extends BaseService implements
		ConfigItemService {
	private ConfigItemDao configItemDao;
	private MetaDataManager metaDataManager;
	
	public ConfigItem saveOrUpdateConfigItem(Map basicMap,Map financeMap, Map extendMap) {
		try {
			ConfigItem ci=null;
			String basicId=(String) basicMap.get("id");
			String configItemTypeId=(String) basicMap.get("configItemType");
			ConfigItemType configItemType=(ConfigItemType) find(ConfigItemType.class, configItemTypeId);
			if(basicId.equals("")){//�½�
				Object obj=metaDataManager.saveEntityData(Class.forName(configItemType.getClassName()), extendMap);
				
				basicMap.put("cisn", PropertyUtils.getProperty(obj, "cisn"));
				basicMap.put("createDate", new Date());
				basicMap.put("createUser", UserContext.getUserInfo());
				ci=(ConfigItem) metaDataManager.saveEntityData(ConfigItem.class, basicMap);
				
				ConfigItemExtendInfo extend=new ConfigItemExtendInfo();
				extend.setConfigItem(ci);
				extend.setExtendDataId((Long) PropertyUtils.getProperty(obj, "id"));
				extend.setSystemMainTable(configItemType.getSystemMainTable());
				save(extend);
				
				financeMap.put("configItem", ci);
				metaDataManager.saveEntityData(ConfigItemFinanceInfo.class, financeMap);
			}else{//����
				metaDataManager.saveEntityData(Class.forName(configItemType.getClassName()), extendMap);
				
				basicMap.put("modifyDate", new Date());
				basicMap.put("modifyUser", UserContext.getUserInfo());
				ci=(ConfigItem)metaDataManager.saveEntityData(ConfigItem.class, basicMap);
				
				metaDataManager.saveEntityData(ConfigItemFinanceInfo.class, financeMap);
			}
			return ci;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	public CIBatchModifyPlan saveOrUpdateConfigItemAndPlan(Map basicMap,
			Map financeMap, Map extendMap, Map planMap,String modifyId,boolean createAllNecessaryRel) {
		ConfigItem ci=saveOrUpdateConfigItem(basicMap, financeMap, extendMap);
		if(planMap.get("id").toString().trim().equals("")){
			planMap.put("maintenanceCIRel", ci);
			planMap.put("newConfigItem",ci);
			planMap.put("batchModify", modifyId);
			planMap.put("result", CIBatchModifyPlan.MODIFY_SUCCESS);
		}
		
		CIBatchModifyPlan plan=(CIBatchModifyPlan) metaDataManager.saveEntityData(CIBatchModifyPlan.class, planMap);
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		//������Ϊ����״̬,��Ҫɾ����ȫ����ϵ
		if(ci.getConfigItemStatus()!=null&&status.contains(((ConfigItemStatus)configItemDao.getObject(ConfigItemStatus.class, ci.getConfigItemStatus().getId())).getEnname())){
			//��ǰ��Ч�Ĺ�ϵ
			List<CIRelationShip> rels=configItemDao.selectValidCIRelationShip(ci.getCisn());
			//���α����ɾ���Ĺ�ϵ
			List<CIRelationShip> deleteRels=configItemDao.selectDeleteRel(Long.valueOf(modifyId));
			//ɾ�����α����δɾ���Ĺ�ϵ
			toNext:for(CIRelationShip rel:rels){
				for(CIRelationShip deleteRel:deleteRels){
					if(rel.getId().compareTo(deleteRel.getId())==0){
						continue toNext;//���α������ɾ��
					}
				}
				CIBatchModifyPlan p=new CIBatchModifyPlan();
				p.setBatchModify(plan.getBatchModify());
				p.setMaintenanceCIRel(ci);
				p.setOldCIRelationShip(rel);
				p.setDescn(plan.getDescn());
				p.setEndDate(plan.getEndDate());
				p.setStartDate(plan.getStartDate());
				p.setOfficer(plan.getOfficer());
				p.setResult(CIBatchModifyPlan.MODIFY_SUCCESS);
				configItemDao.save(p);
			}
		}else if(ci.getConfigItemStatus()!=null&&!status.contains(((ConfigItemStatus)configItemDao.getObject(ConfigItemStatus.class, ci.getConfigItemStatus().getId())).getEnname())){
			//���α�������У����ܻ���֮ǰ������������Ϊ����״̬��ɾ���˹�ϵ��֮���ֽ����������޸�Ϊ�ǹ���״̬����ʱ��Ҫ��֮ǰ���Ϊ����״̬ʱϵͳ�Զ����ɵı���ƻ�ɾ����
			List<CIBatchModifyPlan> deletePlans=configItemDao.selectMaintenanceDeleteRelPlan(Long.valueOf(modifyId),ci);
			if(!deletePlans.isEmpty()){
				configItemDao.deleteObjects(deletePlans);
			}
		}
		saveNecessaryRel(Long.valueOf(modifyId), ci,plan,createAllNecessaryRel);
		return plan;
	}

	public List<CIRelationShip> findCIRelationShip(Long modifyId,String cisn){
		//������������ɾ���Ĺ�ϵ
		List<CIBatchModifyPlan> plans=configItemDao.selectNewAndDeleteRel(modifyId, cisn);
		//��ǰ��Ч�Ĺ�ϵ
		List<CIRelationShip> rels=configItemDao.selectValidCIRelationShip(cisn);
		for(CIBatchModifyPlan plan:plans){
			CIRelationShip newRel=plan.getNewCIRelationShip();
			CIRelationShip oldRel=plan.getOldCIRelationShip();
			if(newRel==null&&oldRel!=null){//ɾ���Ĺ�ϵ
				Iterator<CIRelationShip> ite=rels.iterator();
				while(ite.hasNext()){
					if(ite.next().getId().compareTo(oldRel.getId())==0){
						ite.remove();//����Ч��ϵ���Ƴ�������ɾ���Ĺ�ϵ
						break;
					}
				}
			}else if(newRel!=null&&oldRel==null){//�½��Ĺ�ϵ
				rels.add(newRel);
			}
		}
		return rels;
	}
	public CIBatchModifyPlan saveOrUpdateRelAndPlan(Map relMap, Map relPlanMap,
			String modifyId) {
		if(relMap.get("id").equals("")){
			relMap.put("createUser", UserContext.getUserInfo());
			relMap.put("createDate", new Date());
			relMap.put("status", CIRelationShip.DRAFT_STATUS);
		}else{
			relMap.put("modifyUser", UserContext.getUserInfo());
			relMap.put("modifyDate", new Date());
		}
		CIRelationShip newRel=(CIRelationShip) metaDataManager.saveEntityData(CIRelationShip.class, relMap);
		if(relPlanMap.get("id").equals("")){
			relPlanMap.put("newCIRelationShip",newRel);
			relPlanMap.put("batchModify", modifyId);
			relPlanMap.put("result", CIBatchModifyPlan.MODIFY_SUCCESS);
		}
		return (CIBatchModifyPlan) metaDataManager.saveEntityData(CIBatchModifyPlan.class, relPlanMap);
	}

	public void removeConfigItem(List<ConfigItem> configItems) {
		try {
			List<ConfigItemFinanceInfo> financeInfos=configItemDao.selectObjects(ConfigItemFinanceInfo.class, "configItem",configItems.toArray(),null);
			for(ConfigItem configItem:configItems){
				ConfigItemExtendInfo extendInfo=(ConfigItemExtendInfo) findUnique(ConfigItemExtendInfo.class, "configItem",configItem);
				Object extend=find(Class.forName(configItem.getConfigItemType().getClassName()), extendInfo.getExtendDataId().toString());
				configItemDao.remove(extendInfo);
				configItemDao.remove(extend);
			}
			configItemDao.deleteObjects(financeInfos);
			configItemDao.deleteObjects(configItems);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	public List<CIRelationShip> findDirectChildRel(String itemCode) {
		return configItemDao.selectDirectChildRel(itemCode);
	}

	public Page findConfigItem(String modifyId,String name, String cisn, Long configItemTypeId,
			int pageNo, int pageSize) {
		return configItemDao.selectConfigItem(modifyId,name, cisn, configItemTypeId, pageNo, pageSize);
	}

	public Page findServiceItem(String name, String code,
			Long serviceItemTypeId, int pageNo, int pageSize) {
		return configItemDao.selectServiceItem(name, code, serviceItemTypeId, pageNo, pageSize);
	}
	
	public Set<String> findLoop(String parentCode,String childCode,List<Long> relsId,List<Long> ignoreRid){
		//�ݹ��ѯ���и����
		Set<String> allParentCode=configItemDao.selectAllParentCode(parentCode, relsId,ignoreRid);
		//�ݹ��ѯ�����ӱ��
		Set<String> allChildCode=configItemDao.selectAllChildCode(childCode, relsId,ignoreRid);
		//ȡ���������Ǵ��ڻ���������
		allParentCode.retainAll(allChildCode);
		return allParentCode;
	}
	
	public Page findRelList(Map<String,String> basicMap, Map<String,String> advancedMap, int pageNo, int pageSize){
		return configItemDao.selectRelList(basicMap, advancedMap, pageNo, pageSize);
	}
	
	public Long saveCIBatchModify(Map batchModifyMap,Long typeId,String type){
		CIBatchModify batchModify=(CIBatchModify) metaDataManager.saveEntityData(CIBatchModify.class,batchModifyMap);
		CIBatchModifyShip ciBatchModifyShip=(CIBatchModifyShip) findUnique(CIBatchModifyShip.class, "ciBatchModify", batchModify);
		if(ciBatchModifyShip==null){
			ciBatchModifyShip = new CIBatchModifyShip();
			if("problem".equals(type)){
				Problem problem = new Problem();
				problem.setId(typeId);
				ciBatchModifyShip.setProblem(problem);
			}else if("specialRequirement".equals(type)){
				SpecialRequirement specialRequirement = new SpecialRequirement();
				specialRequirement.setId(typeId);
				ciBatchModifyShip.setSpecialRequirement(specialRequirement);
			}
			ciBatchModifyShip.setCiBatchModify(batchModify);
			ciBatchModifyShip.setSubmitUser(UserContext.getUserInfo());
			configItemDao.save(ciBatchModifyShip);
		}
		return batchModify.getId();
	}

	public CIBatchModifyPlan findHasCIModifyDraft(Long modifyId, String cisn,Long ignoreCid) {
		return configItemDao.selectHasCIModifyDraft(modifyId, cisn,ignoreCid);
	}
	
	public CIBatchModifyPlan findHasCIRelModifyDraft(Long modifyId, String parentCode,String childCode) {
		return configItemDao.selectHasCIRelModifyDraft(modifyId, parentCode,childCode);
	}
	
	public CIBatchModifyPlan removeCIRel(Long rid, Long modifyId) {
		CIRelationShip rel = new CIRelationShip();
		rel.setId(rid);
		CIBatchModify bm=new CIBatchModify();
		bm.setId(modifyId);
		CIBatchModifyPlan plan = new CIBatchModifyPlan();
		plan.setBatchModify(bm);
		plan.setOldCIRelationShip(rel);
		plan.setResult(CIBatchModifyPlan.MODIFY_SUCCESS);
		plan=(CIBatchModifyPlan) configItemDao.save(plan);
		return plan;
	}

	public void removeBatchModifyPlans(Long[] planId) {
		List<CIBatchModifyPlan> plans=configItemDao.selectObjects(CIBatchModifyPlan.class,"id",planId,"newConfigItem");
		//�½�����������
		List<String> newCisns=new ArrayList<String>();
		//�½��ͱ����������
		List<ConfigItem> newConfigItems=new ArrayList<ConfigItem>();
		//ά����ϵ��������
		List<ConfigItem> modifyCIAndMaintenanceCI=new ArrayList<ConfigItem>();
		//�½��ͱ���Ĺ�ϵ
		List<CIRelationShip> newCIRelationShips=new ArrayList<CIRelationShip>();
		for(CIBatchModifyPlan plan:plans){
			if(plan.getNewConfigItem()!=null){
				newConfigItems.add(plan.getNewConfigItem());
				if(plan.getOldConfigItem()==null){
					newCisns.add(plan.getNewConfigItem().getCisn());
				}
			}
			if(plan.getMaintenanceCIRel()!=null&&plan.getNewCIRelationShip()==null&&plan.getOldCIRelationShip()==null){
				modifyCIAndMaintenanceCI.add(plan.getMaintenanceCIRel());
			}
			if(plan.getNewCIRelationShip()!=null){
				newCIRelationShips.add(plan.getNewCIRelationShip());
			}
		}
		//��ά����ϵͳ�Զ��������Ĺ�ϵ�ı���ƻ�
		List<CIBatchModifyPlan> maintenanceRelPlans=new ArrayList<CIBatchModifyPlan>();
		//��ά����ϵͳ�Զ��������Ĺ�ϵ
		List<CIRelationShip> maintenanceRels=new ArrayList<CIRelationShip>();
		if(!modifyCIAndMaintenanceCI.isEmpty()){
			maintenanceRelPlans=configItemDao.selectMaintenanceRelPlan(plans.get(0).getBatchModify().getId(), modifyCIAndMaintenanceCI);
			for(CIBatchModifyPlan plan:maintenanceRelPlans){
				if(plan.getNewCIRelationShip()!=null){
					maintenanceRels.add(plan.getNewCIRelationShip());
				}
			}
		}
		if(!newCisns.isEmpty()){
			//���½�����������صĹ�ϵ
			List<CIRelationShip> relationShips= configItemDao.selectCIRelationShip(newCisns);
			if(!relationShips.isEmpty()){
				//���½�����������صĹ�ϵ�ı���ƻ�
				List<CIBatchModifyPlan> newRelPlans=configItemDao.selectObjects(CIBatchModifyPlan.class, "newCIRelationShip", relationShips.toArray(), null);
				toNextRel:for(CIRelationShip rel:relationShips){
					for(CIRelationShip maintenanceRel:maintenanceRels){
						//maintenanceRels���Ѵ���
						if(rel.getId().compareTo(maintenanceRel.getId())==0){
							continue toNextRel;
						}
					}
					//�������ڵĹ�ϵ�ӵ�maintenanceRels��
					maintenanceRels.add(rel);
				}
				toNextPlan:for(CIBatchModifyPlan newRelPlan:newRelPlans){
					for(CIBatchModifyPlan maintenanceRelPlan:maintenanceRelPlans){
						if(newRelPlan.getId().compareTo(maintenanceRelPlan.getId())==0){
							continue toNextPlan;
						}
					}
					maintenanceRelPlans.add(newRelPlan);
				}
			}
		}
		
		configItemDao.deleteObjects(plans);
		
		if(!maintenanceRelPlans.isEmpty()){
			configItemDao.deleteObjects(maintenanceRelPlans);
		}
		
		if(!maintenanceRels.isEmpty()){
			configItemDao.deleteObjects(maintenanceRels);
		}
		
		if(!newConfigItems.isEmpty()){
			removeConfigItem(newConfigItems);
		}
		if(!newCIRelationShips.isEmpty()){
			configItemDao.deleteObjects(newCIRelationShips);
		}
		
	}
	public Page findBatchModifyCIList(Long modifyId, int pageNo, int pageSize) {
		return configItemDao.selectBatchModifyCIList(modifyId, pageNo, pageSize);
	}
	public Page findBatchModifyRelList(Long modifyId, int pageNo, int pageSize) {
		return configItemDao.selectBatchModifyRelList(modifyId, pageNo, pageSize);
	}

	public Page findBatchModifyInfo(Long id, String type,int pageNo, int pageSize) {
		return configItemDao.selectBatchModifyInfo(id, type, pageNo, pageSize);
	}
	
	public void saveSuccessCIBatchModifyPlan(List<CIBatchModifyPlan> plans) {
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		for(CIBatchModifyPlan plan:plans){
			plan.setResult(CIBatchModifyPlan.MODIFY_SUCCESS);
			if(plan.getNewConfigItem()!=null&&plan.getOldConfigItem()!=null){
				ConfigItemStatus configItemStatus=plan.getNewConfigItem().getConfigItemStatus();
				if(configItemStatus!=null){
					//���������Ϊ����״̬���ұ���ɹ�ʱ������ɾ���Ĺ�ϵ�������ɹ�
					if(status.contains(configItemStatus.getEnname())){
						List<CIBatchModifyPlan> relPlans=configItemDao.selectDeleteRelPlan(plan.getBatchModify().getId(), plan.getNewConfigItem().getCisn());
						for(CIBatchModifyPlan relPlan:relPlans){
							relPlan.setResult(CIBatchModifyPlan.MODIFY_SUCCESS);
						}
						if(!relPlans.isEmpty()){
							configItemDao.saveOrUpdateObjects(relPlans);
						}
					}
				}
			}
		}
		if(!plans.isEmpty()){
			configItemDao.saveOrUpdateObjects(plans);
		}
	}
	
	public void saveUnSuccessCIBatchModifyPlan(List<CIBatchModifyPlan> plans) {
		//�½���������
		List<String> newCisns=new ArrayList<String>();
		for(CIBatchModifyPlan plan:plans){
			plan.setResult(CIBatchModifyPlan.MODIFY_UNSUCCESS);
			if(plan.getNewConfigItem()!=null&&plan.getOldConfigItem()==null){
				newCisns.add(plan.getNewConfigItem().getCisn());
			}
		}
		if(!plans.isEmpty()){
			configItemDao.saveOrUpdateObjects(plans);
		}
		
		if(!newCisns.isEmpty()){
			//�½���������δ����ɹ�ʱ����Ҫ�����ϵ���δ�ɹ���
			List<CIRelationShip> relationShips= configItemDao.selectCIRelationShip(newCisns);
			if(!relationShips.isEmpty()){
				List<CIBatchModifyPlan> relPlan=configItemDao.selectObjects(CIBatchModifyPlan.class, "newCIRelationShip", relationShips.toArray(), null);
				for(CIBatchModifyPlan plan:relPlan){
					plan.setResult(CIBatchModifyPlan.MODIFY_UNSUCCESS);
				}
				if(!relPlan.isEmpty()){
					configItemDao.saveOrUpdateObjects(relPlan);
				}
			}
		}
	}
	
	public CIBatchModify saveSuccessModify(Long modifyId) {
		try {
			CIBatchModify bm=(CIBatchModify) configItemDao.getObject(CIBatchModify.class, modifyId);
			Map<String,Object> nameAndValue=new HashMap<String,Object>();
			nameAndValue.put("batchModify", bm);
			List<String> property=new ArrayList<String>();
			property.add("newConfigItem");
			property.add("oldConfigItem");
			property.add("newCIRelationShip");
			property.add("oldCIRelationShip");
			//���α�������е����б���ƻ�
			List<CIBatchModifyPlan> plans=configItemDao.selectObjects(CIBatchModifyPlan.class, nameAndValue, property);
			for(CIBatchModifyPlan plan:plans){
				ConfigItem newConfigItem=plan.getNewConfigItem();	
				ConfigItem oldConfigItem=plan.getOldConfigItem();
				CIRelationShip newCIRelationShip=plan.getNewCIRelationShip();
				CIRelationShip oldCIRelationShip=plan.getOldCIRelationShip();
				Integer result=plan.getResult();
				UserInfo currentUser=UserContext.getUserInfo();
				Date currentDate=new Date();
				//����ɹ��ı���ƻ���ɾ��ԭ�������ϵ��ʹ���������ϵ��Ч
				if(result.compareTo(CIBatchModifyPlan.MODIFY_SUCCESS)==0){
					//�޸�ԭ�����������޸���Ϣ����״̬
					if(oldConfigItem!=null){
						oldConfigItem.setModifyUser(currentUser);
						oldConfigItem.setModifyDate(currentDate);
						oldConfigItem.setStatus(ConfigItem.DELETE_STATUS);
						configItemDao.save(oldConfigItem);
					}
					if(oldCIRelationShip!=null){
						oldCIRelationShip.setModifyUser(currentUser);
						oldCIRelationShip.setModifyDate(currentDate);
						oldCIRelationShip.setStatus(CIRelationShip.DELETE_STATUS);
						configItemDao.save(oldCIRelationShip);
					}
					if(newConfigItem!=null){
						if(oldConfigItem==null){
							newConfigItem.setCreateUser(currentUser);
							newConfigItem.setCreateDate(currentDate);
							newConfigItem.setModifyUser(null);
							newConfigItem.setModifyDate(null);
						}else{
							//���ʱ������ʱ��Ϊԭ������Ĵ���ʱ��
							newConfigItem.setCreateDate(oldConfigItem.getCreateDate());
							newConfigItem.setCreateUser(oldConfigItem.getCreateUser());
							newConfigItem.setModifyUser(currentUser);
							newConfigItem.setModifyDate(currentDate);
						}
						newConfigItem.setStatus(ConfigItem.VALID_STATUS);
						configItemDao.save(newConfigItem);
						
					}
					if(newCIRelationShip!=null){
						if(oldCIRelationShip==null){
							newCIRelationShip.setCreateUser(currentUser);
							newCIRelationShip.setCreateDate(currentDate);
							newCIRelationShip.setModifyUser(null);
							newCIRelationShip.setModifyDate(null);
						}else{
							newCIRelationShip.setCreateUser(oldCIRelationShip.getCreateUser());
							newCIRelationShip.setCreateDate(oldCIRelationShip.getCreateDate());
							newCIRelationShip.setModifyUser(currentUser);
							newCIRelationShip.setModifyDate(currentDate);
						}
						newCIRelationShip.setStatus(CIRelationShip.VALID_STATUS);
						configItemDao.save(newCIRelationShip);
					}
				}else{
					//���δ�ɹ��ı���ƻ������½��������������ϵ��Ϊ��ʷ״̬
					if(newConfigItem!=null){
						newConfigItem.setStatus(ConfigItem.HISTORY_STATUS);
						configItemDao.save(newConfigItem);
					}
					if(newCIRelationShip!=null){
						newCIRelationShip.setStatus(CIRelationShip.HISTORY_STATUS);
						configItemDao.save(newCIRelationShip);
					}
				}
			}
			bm.setStatus(CIBatchModify.STATUS_PASSED);
			return bm;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	public void saveUnSuccessModify(CIBatchModify bm){
		Map<String,Object> nameAndValue=new HashMap<String,Object>();
		nameAndValue.put("batchModify", bm);
		List<CIBatchModifyPlan> plans=configItemDao.selectObjects(CIBatchModifyPlan.class, nameAndValue, null);
		//�����б���ƻ���Ϊ���δ�ɹ��������½��������������ϵ��Ϊ��ʷ״̬
		for(CIBatchModifyPlan plan:plans){
			ConfigItem newConfigItem=plan.getNewConfigItem();	
			CIRelationShip newCIRelationShip=plan.getNewCIRelationShip();
			if(newConfigItem!=null){
				newConfigItem.setStatus(ConfigItem.HISTORY_STATUS);
				configItemDao.save(newConfigItem);
			}
			if(newCIRelationShip!=null){
				newCIRelationShip.setStatus(CIRelationShip.HISTORY_STATUS);
				configItemDao.save(newCIRelationShip);
			}
			plan.setResult(CIBatchModifyPlan.MODIFY_UNSUCCESS);
			configItemDao.save(plan);
		}
		bm.setStatus(CIBatchModify.STATUS_GIVEUP);
		configItemDao.save(bm);
	}
	
	public Page findBatchModify(String modifyNo, String name, Date applyDateStart,Date applyDateEnd, Integer status, int start, int size) {
		return configItemDao.selectBatchModify(modifyNo, name, applyDateStart, applyDateEnd,status, start, size);
	}
	public void removeBatchModifyDraft(Long[] modifyIdArray) {
		List<CIBatchModify> batchModify =configItemDao.selectObjects(CIBatchModify.class, "id", modifyIdArray, null);
		List<CIBatchModifyPlan> plans=configItemDao.selectObjects(CIBatchModifyPlan.class,"batchModify", batchModify.toArray(),null);
		List<ConfigItem> newConfigItems=new ArrayList<ConfigItem>();
		List<CIRelationShip> newCIRelationShips=new ArrayList<CIRelationShip>();
		for(CIBatchModifyPlan plan:plans){
			if(plan.getNewConfigItem()!=null){
				newConfigItems.add(plan.getNewConfigItem());
			}
			if(plan.getNewCIRelationShip()!=null){
				newCIRelationShips.add(plan.getNewCIRelationShip());
			}
		}
		List<CIBatchModifyShip> ships=configItemDao.selectObjects(CIBatchModifyShip.class,"ciBatchModify", batchModify.toArray(),null);
		if(!ships.isEmpty()){
			configItemDao.deleteObjects(ships);
		}
		if(!plans.isEmpty()){
			configItemDao.deleteObjects(plans);
		}
		if(!batchModify.isEmpty()){
			configItemDao.deleteObjects(batchModify);
		}
		if(!newCIRelationShips.isEmpty()){
			configItemDao.deleteObjects(newCIRelationShips);
		}
		if(!newConfigItems.isEmpty()){
			removeConfigItem(newConfigItems);
		}
	}
	
	public Set<String> findDeliveryTeamTechnicalLeader() {
		return configItemDao.selectDeliveryTeamTechnicalLeader();
	}

	public <T> List<T> findObjects(Class<T> entity, String propertyName,
			Object[] propertyValues, String fetchProperty) {
		return configItemDao.selectObjects(entity, propertyName, propertyValues, fetchProperty);
	}
	
	public List<String> findNewCIModifyUnsuccess(List<CIBatchModifyPlan> plans){
		//�½��Ĺ�ϵ���漰����������
		List<String> cisn=new ArrayList<String>();
		for(CIBatchModifyPlan plan:plans){
			if(plan.getNewCIRelationShip()!=null&&plan.getOldCIRelationShip()==null){//�½��Ĺ�ϵ
				CIRelationShip rel=plan.getNewCIRelationShip();
				if(rel.getParentConfigItemCode()!=null){
					cisn.add(rel.getParentConfigItemCode());
				}
				if(rel.getChildConfigItemCode()!=null){
					cisn.add(rel.getChildConfigItemCode());
				}
			}
		}
		if(!cisn.isEmpty()){
			return configItemDao.selectNewCIModifyUnsuccess(plans.get(0).getBatchModify(), cisn);
		}
		return cisn;
	}
	public List<ConfigItem> findOrphanCIModifySuccess(List<CIBatchModifyPlan> plans){
		List<String> cisn=new ArrayList<String>();
		for(CIBatchModifyPlan plan:plans){
			if(plan.getNewCIRelationShip()==null&&plan.getOldCIRelationShip()!=null){
				CIRelationShip rel=plan.getOldCIRelationShip();
				if(rel.getParentConfigItemCode()!=null){
					cisn.add(rel.getParentConfigItemCode());
				}
				if(rel.getChildConfigItemCode()!=null){
					cisn.add(rel.getChildConfigItemCode());
				}
			}
		}
		if(!cisn.isEmpty()){
			return configItemDao.selectOrphanCIModifySuccess(plans.get(0).getBatchModify(), cisn);
		}
		return new ArrayList<ConfigItem>();
	}
	
	public <T> List<T> findObjects(Class<T> entity,
			Map<String, Object> propertysNameAndValue,
			List<String> fetchPropertys) {
		return configItemDao.selectObjects(entity, propertysNameAndValue, fetchPropertys);
	}
	
	public <T> List<T> findObjects(Class<T> entity,
			Map<String, Object> propertysNameAndValue,
			List<String> fetchPropertys, String orderProperty, boolean isAsc) {
		return configItemDao.selectObjects(entity, propertysNameAndValue, fetchPropertys, orderProperty, isAsc);
	}
	
	public boolean findHasSameDraft(Long modifyId, String parentCode,
			String childCode, Long ignoreRid) {
		return configItemDao.selectHasSameDraft(modifyId, parentCode, childCode, ignoreRid);
	}

	public boolean findHasSameValidAndProcessingRel(String parentCode,
			String childCode, List<Long> rids,Long ignoreRid) {
		return configItemDao.selectHasSameValidAndProcessingRel(parentCode, childCode, rids,ignoreRid);
	}

	public void saveOrUpdateObjects(Collection entity) {
		configItemDao.saveOrUpdateObjects(entity);
	}
	
	public List<CIRelationShip> findDeleteRel(Long modifyId) {
		return configItemDao.selectDeleteRel(modifyId);
	}

	public List<CIBatchModifyPlan> findProcessingRelPlan(Long modifyId){
		return configItemDao.selectProcessingRelPlan(modifyId);
	}
	public List<String> findCIProcessing(Long modifyId,List<String> cisn){
		return configItemDao.selectCIProcessing(modifyId,cisn);
	}
	
	public void saveOrUpdateOldCIAndOldRel(List<CIBatchModifyPlan> plans) {
		for(CIBatchModifyPlan plan:plans){
			ConfigItem newCI=plan.getNewConfigItem();
			ConfigItem oldCI=plan.getOldConfigItem();
			CIRelationShip newRel=plan.getNewCIRelationShip();
			CIRelationShip oldRel=plan.getOldCIRelationShip();
			if(oldCI!=null){
				Map<String,Object> oldCIMap=new HashMap<String,Object>();
				oldCIMap.put("status", ConfigItem.VALID_STATUS);
				oldCIMap.put("cisn", oldCI.getCisn());
				List<ConfigItem> currentValid=configItemDao.selectObjects(ConfigItem.class, oldCIMap, null);
				if(!currentValid.isEmpty()){
					//�����������ɹ��˱��������б����������
					if(oldCI.getId().compareTo(currentValid.get(0).getId())!=0){
						//���ѱ������������Ļ���֮�������
						plan.setOldConfigItem(currentValid.get(0));
						configItemDao.save(plan);
					}
				}else{
					//�����ɾ��
					if(newCI!=null){
						//��Ϊ�½�
						plan.setOldConfigItem(null);
						configItemDao.save(plan);
					}
				}
			}
			if(newRel!=null&&oldRel==null){
				String parentCode="";
				String childCode="";
				if(newRel.getParentConfigItemCode()!=null){
					parentCode=newRel.getParentConfigItemCode();
				}else if(newRel.getParentServiceItemCode()!=null){
					parentCode=newRel.getParentServiceItemCode();
				}
				if(newRel.getChildConfigItemCode()!=null){
					childCode=newRel.getChildConfigItemCode();
				}
				else if(newRel.getChildServiceItemCode()!=null){
					childCode=newRel.getChildServiceItemCode();
				}
				//�Ѵ��ڴ��ֹ�ϵ
				CIRelationShip currentRel=configItemDao.selectHasSameValidRel(parentCode, childCode);
				if(currentRel!=null){
					//���Ѵ��ڹ�ϵ�Ļ���֮�������
					plan.setOldCIRelationShip(currentRel);
					configItemDao.save(plan);
				}
			}else if(oldRel!=null){
				String parentCode="";
				String childCode="";
				if(oldRel.getParentConfigItemCode()!=null){
					parentCode=oldRel.getParentConfigItemCode();
				}else if(oldRel.getParentServiceItemCode()!=null){
					parentCode=oldRel.getParentServiceItemCode();
				}
				if(oldRel.getChildConfigItemCode()!=null){
					childCode=oldRel.getChildConfigItemCode();
				}
				else if(oldRel.getChildServiceItemCode()!=null){
					childCode=oldRel.getChildServiceItemCode();
				}
				CIRelationShip currentRel=configItemDao.selectHasSameValidRel(parentCode, childCode);
				//�����������ɹ��˱��������б���Ĺ�ϵ
				if(currentRel!=null&&currentRel.getId().compareTo(oldRel.getId())!=0){
					//���ѱ������ϵ�Ļ���֮�������
					plan.setOldCIRelationShip(currentRel);
					configItemDao.save(plan);
				}else if(currentRel==null&&newRel!=null){//����������ɾ�����ұ���ɹ��˱��α���б���Ĺ�ϵ
					//��Ϊ�½�
					plan.setOldCIRelationShip(null);
					configItemDao.save(plan);
				}
			}
		}
	}
	
	public String findItemName(String item, String itemCode, Long modifyId) {
		String itemName="";
		if(item.equals("ci")){
			Map<String,Object> ciMap=new HashMap<String,Object>();
			ciMap.put("status", ConfigItem.VALID_STATUS);
			ciMap.put("cisn", itemCode);
			List<ConfigItem> cis=configItemDao.selectObjects(ConfigItem.class, ciMap, null);
			if(!cis.isEmpty()){
				itemName=cis.get(0).getName();
			}else{
				if(modifyId!=null){
					ConfigItem ci=configItemDao.selectModifyConfigItem(modifyId, itemCode);
					if(ci!=null){
						itemName=ci.getName();
					}
				}
			}
		}else if(item.equals("si")){
			Map<String,Object> siMap=new HashMap<String,Object>();
			siMap.put("deleteFlag", ServiceItem.DELETE_FALSE);
			siMap.put("serviceItemCode", itemCode);
			List<ServiceItem> sis=configItemDao.selectObjects(ServiceItem.class, siMap, null);
			if(!sis.isEmpty()){
				itemName=sis.get(0).getName();
			}
		}
		return itemName;
	}
	public Page findValidConfigItemExtendInfo(String entity,Map map,List<String> fuzzyQuery ,int pageNo, int pageSize){
		return configItemDao.selectValidConfigItemExtendInfo(entity,map,fuzzyQuery, pageNo, pageSize);
	}
	
	
	public Page findServiceEngineer(Long deliveryTeamId,Map map, List<String> fuzzyQuery,
			int pageNo, int pageSize) {
		return configItemDao.selectServiceEngineer(deliveryTeamId,map, fuzzyQuery, pageNo, pageSize);
	}

	public List<CIRelationShip> findValidCIRelationShip(String cisn){
		return configItemDao.selectValidCIRelationShip(cisn);
	}
	public List<CIRelationShip> findCIRelationShip(ConfigItem ci){
		List<CIRelationShip> rels=new ArrayList<CIRelationShip>();
		if(ci.getStatus().compareTo(ConfigItem.VALID_STATUS)==0){
			rels=findValidCIRelationShip(ci.getCisn());
		}else if(ci.getStatus().compareTo(ConfigItem.DELETE_STATUS)==0){
			rels=configItemDao.selectCIRelationShipByDate(ci.getCisn(),ci.getModifyDate());
		}
		return rels;
	}
	public List<ConfigItem> findOrphanCI(Long modifyId, List<String> cisn) {
		return configItemDao.selectOrphanCI(modifyId, cisn);
	}
	public List<ConfigItem> findProcessingOrphanCI(Long modifyId,Set<String> cisn) {
		return configItemDao.selectProcessingOrphanCI(modifyId,cisn);
	}
	
	public Page findReplaceRelList(String itemCode, Long modifyId, int pageNo,int pageSize) {
		//����������ɾ���Ĺ�ϵ
		List<CIBatchModifyPlan> plans=configItemDao.selectModifyRel(modifyId);
		//����������ɾ���ĺʹ���������صĹ�ϵ�漰���ķ������������
		List<String> codes=new ArrayList<String>();
		for(CIBatchModifyPlan plan:plans){
			CIRelationShip newRel=plan.getNewCIRelationShip();
			CIRelationShip oldRel=plan.getOldCIRelationShip();
			if(newRel!=null){//���
				if(newRel.getParentConfigItemCode()!=null&&newRel.getParentConfigItemCode().equals(itemCode)){
					if(newRel.getChildConfigItemCode()!=null){
						codes.add(newRel.getChildConfigItemCode());
					}else if(newRel.getChildServiceItemCode()!=null){
						codes.add(newRel.getChildServiceItemCode());
					}
				}
				if(newRel.getChildConfigItemCode()!=null&&newRel.getChildConfigItemCode().equals(itemCode)){
					if(newRel.getParentConfigItemCode()!=null){
						codes.add(newRel.getParentConfigItemCode());
					}else if(newRel.getParentServiceItemCode()!=null){
						codes.add(newRel.getParentServiceItemCode());
					}
				}
			}else{//ɾ��
				if(oldRel.getParentConfigItemCode()!=null&&oldRel.getParentConfigItemCode().equals(itemCode)){
					if(oldRel.getChildConfigItemCode()!=null){
						codes.add(oldRel.getChildConfigItemCode());
					}else if(oldRel.getChildServiceItemCode()!=null){
						codes.add(oldRel.getChildServiceItemCode());
					}
				}
				if(oldRel.getChildConfigItemCode()!=null&&oldRel.getChildConfigItemCode().equals(itemCode)){
					if(oldRel.getParentConfigItemCode()!=null){
						codes.add(oldRel.getParentConfigItemCode());
					}else if(oldRel.getParentServiceItemCode()!=null){
						codes.add(oldRel.getParentServiceItemCode());
					}
				}
			}
		}
		//�ų�����codes��صĹ�ϵ
		return configItemDao.selectRelList(itemCode, codes, pageNo, pageSize);
	}
	public List<CIRelationShip> findReplaceRelList(String itemCode, Long modifyId) {
		//����������ɾ���Ĺ�ϵ
		List<CIBatchModifyPlan> plans=configItemDao.selectModifyRel(modifyId);
		//����������ɾ���ĺʹ���������صĹ�ϵ�漰���ķ������������
		List<String> codes=new ArrayList<String>();
		for(CIBatchModifyPlan plan:plans){
			CIRelationShip newRel=plan.getNewCIRelationShip();
			CIRelationShip oldRel=plan.getOldCIRelationShip();
			if(newRel!=null){//���
				if(newRel.getParentConfigItemCode()!=null&&newRel.getParentConfigItemCode().equals(itemCode)){
					if(newRel.getChildConfigItemCode()!=null){
						codes.add(newRel.getChildConfigItemCode());
					}else if(newRel.getChildServiceItemCode()!=null){
						codes.add(newRel.getChildServiceItemCode());
					}
				}
				if(newRel.getChildConfigItemCode()!=null&&newRel.getChildConfigItemCode().equals(itemCode)){
					if(newRel.getParentConfigItemCode()!=null){
						codes.add(newRel.getParentConfigItemCode());
					}else if(newRel.getParentServiceItemCode()!=null){
						codes.add(newRel.getParentServiceItemCode());
					}
				}
			}else{//ɾ��
				if(oldRel.getParentConfigItemCode()!=null&&oldRel.getParentConfigItemCode().equals(itemCode)){
					if(oldRel.getChildConfigItemCode()!=null){
						codes.add(oldRel.getChildConfigItemCode());
					}else if(oldRel.getChildServiceItemCode()!=null){
						codes.add(oldRel.getChildServiceItemCode());
					}
				}
				if(oldRel.getChildConfigItemCode()!=null&&oldRel.getChildConfigItemCode().equals(itemCode)){
					if(oldRel.getParentConfigItemCode()!=null){
						codes.add(oldRel.getParentConfigItemCode());
					}else if(oldRel.getParentServiceItemCode()!=null){
						codes.add(oldRel.getParentServiceItemCode());
					}
				}
			}
		}
		//�ų�����codes��صĹ�ϵ
		return configItemDao.selectRelList(itemCode, codes);
	}
	
	public CIRelationShip findHasSameValidRel(String parentCode,
			String childCode) {
		return configItemDao.selectHasSameValidRel(parentCode, childCode);
	}

	public void removeObjects(Class entitieClass, Long[] ids) {
		try {
			List<Object> obj=configItemDao.selectObjects(entitieClass, "id", ids, null);
			configItemDao.deleteObjects(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	public void saveReplaceRel(List<CIRelationShip> source,
			List<CIRelationShip> target,Long modifyId) {
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH , cal.get(Calendar.DAY_OF_MONTH)+1);
		cal.set(Calendar.HOUR_OF_DAY ,20);
		cal.set(Calendar.MINUTE ,0);
		cal.set(Calendar.SECOND ,0);
		CIBatchModify bm=new CIBatchModify();
		bm.setId(modifyId);
		for(CIRelationShip rel:source){
			CIBatchModifyPlan plan = new CIBatchModifyPlan();
			plan.setBatchModify(bm);
			plan.setOldCIRelationShip(rel);
			plan.setOfficer(UserContext.getUserInfo());
			plan.setStartDate(cal.getTime());
			plan.setEndDate(cal.getTime());
			plan.setResult(CIBatchModifyPlan.MODIFY_SUCCESS);
			configItemDao.save(plan);
		}
		
		configItemDao.saveOrUpdateObjects(target);
		
		for(CIRelationShip rel:target){
			CIBatchModifyPlan plan = new CIBatchModifyPlan();
			plan.setBatchModify(bm);
			plan.setNewCIRelationShip(rel);
			plan.setOfficer(UserContext.getUserInfo());
			plan.setStartDate(cal.getTime());
			plan.setEndDate(cal.getTime());
			plan.setResult(CIBatchModifyPlan.MODIFY_SUCCESS);
			configItemDao.save(plan);
		}
	}
	
	public ConfigItem findModifyConfigItem(Long modifyId, String itemCode) {
		return configItemDao.selectModifyConfigItem(modifyId, itemCode);
	}
	
	public void saveNecessaryRel(Long modifyId,ConfigItem ci,CIBatchModifyPlan ciPlan,boolean createAllNecessaryRel){
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		if(ci.getConfigItemStatus()!=null){		
	 		ConfigItemStatus configItemStatus=(ConfigItemStatus) findUnique(ConfigItemStatus.class, "id", ci.getConfigItemStatus().getId());
	 		//��������������ɱ�Ҫ��ϵ
			if(status.contains(configItemStatus.getEnname())){
				return;
			}
		}
		Map map=new HashMap();
		map.put("configItemType", ci.getConfigItemType());
		if(!createAllNecessaryRel){
			map.put("isOptional", ConfigItemNecessaryRel.ISOPTIONAL_NO);
		}
		//�����Ҫ��ϵ
		List<ConfigItemNecessaryRel> necessaryRels=configItemDao.selectObjects(ConfigItemNecessaryRel.class, map, null);
		if(necessaryRels.isEmpty()){
			return;
		}
		CIBatchModify bm=new CIBatchModify();
		bm.setId(modifyId);
		//������й�ϵ
		List<CIRelationShip> rels=findCIRelationShip(modifyId,ci.getCisn());
		//��������ڵı�Ҫ��ϵ
		List<ConfigItemNecessaryRel> notExistNecessaryRel=findNotExistNecessaryRel(necessaryRels, rels);
		//������Ҫ��ϵ���ɱ���ƻ�
		for(ConfigItemNecessaryRel necessaryRel:notExistNecessaryRel){
			CIRelationShip createNew=new CIRelationShip();
			createNew.setStatus(CIRelationShip.DRAFT_STATUS);
			if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE)==0){
				createNew.setParentConfigItemType(necessaryRel.getConfigItemType());
				createNew.setParentConfigItemCode(ci.getCisn());
				createNew.setChildConfigItemType(necessaryRel.getOtherConfigItemType());
			}else if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.CHILD_PARENT_REL_TYPE)==0){
				createNew.setParentConfigItemType(necessaryRel.getOtherConfigItemType());
				createNew.setChildConfigItemType(necessaryRel.getConfigItemType());
				createNew.setChildConfigItemCode(ci.getCisn());
			}
			createNew.setAttachQuotiety(necessaryRel.getAttachQuotiety());
			createNew.setAtechnoInfo(necessaryRel.getAtechnoInfo());
			createNew.setBtechnoInfo(necessaryRel.getBtechnoInfo());
			createNew.setOtherInfo(necessaryRel.getOtherInfo());
			createNew.setRelationShipType(necessaryRel.getRelationShipType());
			createNew.setRelationShipGrade(necessaryRel.getRelationShipGrade());
			createNew.setCreateDate(new Date());
			createNew.setCreateUser(UserContext.getUserInfo());
			createNew= (CIRelationShip) save(createNew);
			CIBatchModifyPlan plan=new CIBatchModifyPlan();
			plan.setMaintenanceCIRel(ci);
			plan.setBatchModify(bm);
			plan.setNewCIRelationShip(createNew);
			plan.setResult(CIBatchModifyPlan.MODIFY_SUCCESS);
			plan.setOfficer(ciPlan.getOfficer());
			plan.setStartDate(ciPlan.getStartDate());
			plan.setEndDate(ciPlan.getEndDate());
			plan.setDescn(ciPlan.getDescn());
			save(plan);
		}
	}
	
	public List<ConfigItemNecessaryRel> findNotExistNecessaryRel(List<ConfigItemNecessaryRel> necessaryRels,List<CIRelationShip> rels){
		List<ConfigItemNecessaryRel> notExistNecessaryRels=new ArrayList<ConfigItemNecessaryRel>();
		toNext:for(ConfigItemNecessaryRel necessaryRel:necessaryRels){
					for(CIRelationShip rel:rels){
						if(necessaryRel.getOtherConfigItemType()!=null){//��Ҫ��ϵ�й��������Ϊnull
							if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE)==0){//��-->�ӵı�Ҫ��ϵ
								if(rel.getParentConfigItemType()!=null&&
								   rel.getParentConfigItemType().getId().compareTo(necessaryRel.getConfigItemType().getId())==0&&
								   rel.getChildConfigItemType()!=null&&
								   rel.getChildConfigItemType().getId().compareTo(necessaryRel.getOtherConfigItemType().getId())==0){
									continue toNext;//���ڴ��ֱ�Ҫ��ϵ�������ڲ�ѭ��ִ�����ѭ������һ��ѭ��
								}
							}else if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.CHILD_PARENT_REL_TYPE)==0){//��-->���ı�Ҫ��ϵ
								if(rel.getChildConfigItemType()!=null&&
								   rel.getChildConfigItemType().getId().compareTo(necessaryRel.getConfigItemType().getId())==0&&
								   rel.getParentConfigItemType()!=null&&
								   rel.getParentConfigItemType().getId().compareTo(necessaryRel.getOtherConfigItemType().getId())==0){
									continue toNext;
								}
							}
						}else{
							if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE)==0){
								if(rel.getParentConfigItemType()!=null&&
								   rel.getParentConfigItemType().getId().compareTo(necessaryRel.getConfigItemType().getId())==0){
									continue toNext;
								}
							}else if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.CHILD_PARENT_REL_TYPE)==0){
								if(rel.getChildConfigItemType()!=null&&
								   rel.getChildConfigItemType().getId().compareTo(necessaryRel.getConfigItemType().getId())==0){
									continue toNext;
								}
							}
						}
					}
					//������ִ�е��ˣ�˵���ڲ�ѭ����������Ҫ��ϵ������
					notExistNecessaryRels.add(necessaryRel);
		}
		return notExistNecessaryRels;
	}
	public List<ConfigItemNecessaryRel> findHasNotExistOptionalRel(Long modifyId,Long configItemTypeId,String cisn){
		List<ConfigItemNecessaryRel> notExistOptionalRel=new ArrayList<ConfigItemNecessaryRel>();
		Map map=new HashMap();
		map.put("configItemType.id",configItemTypeId);
		map.put("isOptional", ConfigItemNecessaryRel.ISOPTIONAL_YES);
		List<ConfigItemNecessaryRel> necessaryRels=configItemDao.selectObjects(ConfigItemNecessaryRel.class, map, null);
		if(necessaryRels.isEmpty()){
			return notExistOptionalRel;
		}
		if(cisn!=null&&cisn.trim().length()!=0){
			//��ѯĳ������Ĺ�ϵ
			List<CIRelationShip> rels=findCIRelationShip(modifyId,cisn);
			//������Щ��Ҫ��ϵ��������
			notExistOptionalRel=findNotExistNecessaryRel(necessaryRels, rels);
			return notExistOptionalRel;
		}else{
			return necessaryRels;
		}
	}
	
	public boolean findHasAllNecessaryRel(ConfigItem ci, Long modifyId) {
		Map map=new HashMap();
		map.put("configItemType", ci.getConfigItemType());
		map.put("isOptional", ConfigItemNecessaryRel.ISOPTIONAL_NO);
		List<ConfigItemNecessaryRel> necessaryRels=configItemDao.selectObjects(ConfigItemNecessaryRel.class, map, null);
		if(necessaryRels.isEmpty()){
			return true;
		}
		List<CIRelationShip> rels=findCIRelationShip(modifyId,ci.getCisn());
		if(!findNotExistNecessaryRel(necessaryRels, rels).isEmpty()){
			return false;
		}else{
			return true;
		}
	}

	public void savePlanAndCreateNecessaryRel(Map planMap, Long modifyId,ConfigItem ci,boolean createAllNecessaryRel) {
		planMap.put("maintenanceCIRel", ci);
		planMap.put("batchModify", modifyId);
		planMap.put("result", CIBatchModifyPlan.MODIFY_SUCCESS);
		CIBatchModifyPlan ciPlan=(CIBatchModifyPlan) metaDataManager.saveEntityData(CIBatchModifyPlan.class, planMap);
    	saveNecessaryRel(modifyId,ci, ciPlan,createAllNecessaryRel);
	}
	public List<CIBatchModifyPlan> findMaintenanceConfigItem(Long modifyId, ConfigItem ci,Integer result){
		return configItemDao.selectMaintenanceConfigItem(modifyId, ci,result);
	}
	
	public List<CIRelationShip> findMaintenanceRelPlan(Long modifyId,ConfigItem maintenanceCI){
		List<CIRelationShip> rels=new ArrayList<CIRelationShip>();
		List<ConfigItem> cis=new ArrayList<ConfigItem>();
		cis.add(maintenanceCI);
		List<CIBatchModifyPlan> plans= configItemDao.selectMaintenanceRelPlan(modifyId, cis);
		for(CIBatchModifyPlan plan:plans){
			rels.add(plan.getNewCIRelationShip());
		}
		return rels;
	}

	public List<CIRelationShip> findDeleteRel(Long modifyId,List<CIRelationShip> rels){
		return configItemDao.selectDeleteRel(modifyId, rels);
	}
	

	public Page getConfigItemNecessaryRelation(Map paramMap,int pageNo,int pageSize) {
		try {
			Page page = configItemDao.selectConfigItemNecessaryRelation( paramMap,  pageNo,  pageSize);
			return page;
		} catch (Exception e) {			
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	public List<CIRelationShip>  saveNecessaryRelation(String[] necessaryIds,String batchModifyId,String[] configItemCode,List<ConfigItem> cis){
		UserInfo cuUser = UserContext.getUserInfo();
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH , cal.get(Calendar.DAY_OF_MONTH)+1);
		cal.set(Calendar.HOUR_OF_DAY ,20);
		cal.set(Calendar.MINUTE ,0);
		cal.set(Calendar.SECOND ,0);
		Long[] longIds = new Long[necessaryIds.length];
		for(int i = 0 ; i < necessaryIds.length; i++){
			longIds[i] = Long.valueOf(necessaryIds[i]);
		}
		List<ConfigItemNecessaryRel> notExistNecessaryRel = configItemDao.selectObjects(ConfigItemNecessaryRel.class, "id", longIds, null);
		CIBatchModify bm = new CIBatchModify();
		bm.setId(Long.valueOf(batchModifyId));		
		List<CIRelationShip> returnDataList = new ArrayList(); 
		List<List> countList = new ArrayList();
			for(int i = 0 ; i < longIds.length; i++){				
				ConfigItemNecessaryRel necessaryRel= null;
				for(int t = 0 ; t < notExistNecessaryRel.size() ; t ++){
					ConfigItemNecessaryRel tempNecessaryRel= notExistNecessaryRel.get(t);
					if(longIds[i].compareTo(tempNecessaryRel.getId())==0){// ������˵��tempNecessaryRel ���� necessaryRel
						necessaryRel = tempNecessaryRel ;
					}
				}
				List<CIBatchModifyPlan> planList = new ArrayList();
				if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE)==0){
					Map paramMap = new HashMap();
					paramMap.put("batchModify", bm);
					paramMap.put("newCIRelationShip.parentConfigItemType", necessaryRel.getConfigItemType());
					paramMap.put("newCIRelationShip.childConfigItemType", necessaryRel.getOtherConfigItemType());
					paramMap.put("newCIRelationShip.parentConfigItemCode", configItemCode[i]);
					planList =  configItemDao.selectObjects(CIBatchModifyPlan.class, paramMap, null);
					if(!planList.isEmpty()){//planList��Ϊ�ղŷŵ�����list��
						countList.add(planList);
					}
				}else if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.CHILD_PARENT_REL_TYPE)==0){					
					Map paramMap = new HashMap();
					paramMap.put("batchModify", bm);
					paramMap.put("newCIRelationShip.parentConfigItemType", necessaryRel.getOtherConfigItemType());
					paramMap.put("newCIRelationShip.childConfigItemType", necessaryRel.getConfigItemType());
					paramMap.put("newCIRelationShip.childConfigItemCode", configItemCode[i]);
					planList =  configItemDao.selectObjects(CIBatchModifyPlan.class, paramMap, null);	
					if(!planList.isEmpty()){//planList��Ϊ�ղŷŵ�����list��
						countList.add(planList);
					}
				}
			}
			if(countList.isEmpty()){								//���Ϊ��˵��������Ҫ��ϵû�����
				for(int i = 0 ; i < longIds.length; i++){				
					ConfigItemNecessaryRel necessaryRel= null;
					for(int t = 0 ; t < notExistNecessaryRel.size() ; t ++){
						ConfigItemNecessaryRel tempNecessaryRel= notExistNecessaryRel.get(t);
						if(longIds[i].compareTo(tempNecessaryRel.getId())==0){// ������˵��tempNecessaryRel ���� necessaryRel
							necessaryRel = tempNecessaryRel ;
						}
					}
					CIRelationShip createNew=new CIRelationShip();
					createNew.setStatus(CIRelationShip.DRAFT_STATUS);
					if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE)==0){
						createNew.setParentConfigItemType(necessaryRel.getConfigItemType());
						createNew.setParentConfigItemCode(configItemCode[i]);
						createNew.setChildConfigItemType(necessaryRel.getOtherConfigItemType());
					}else if(necessaryRel.getParentOrChildType().compareTo(ConfigItemNecessaryRel.CHILD_PARENT_REL_TYPE)==0){
						createNew.setParentConfigItemType(necessaryRel.getOtherConfigItemType());
						createNew.setChildConfigItemType(necessaryRel.getConfigItemType());
						createNew.setChildConfigItemCode(configItemCode[i]);
					}
					createNew.setAttachQuotiety(necessaryRel.getAttachQuotiety());
					createNew.setAtechnoInfo(necessaryRel.getAtechnoInfo());
					createNew.setBtechnoInfo(necessaryRel.getBtechnoInfo());
					createNew.setOtherInfo(necessaryRel.getOtherInfo());
					createNew.setRelationShipType(necessaryRel.getRelationShipType());
					createNew.setRelationShipGrade(necessaryRel.getRelationShipGrade());
					createNew.setCreateDate(new Date());
					createNew.setCreateUser(UserContext.getUserInfo());
					createNew= (CIRelationShip) save(createNew);
					CIBatchModifyPlan plan=new CIBatchModifyPlan();
					plan.setMaintenanceCIRel(cis.get(i));
					plan.setBatchModify(bm);
					plan.setNewCIRelationShip(createNew);
					plan.setResult(CIBatchModifyPlan.MODIFY_SUCCESS);
					plan.setOfficer(cuUser);
					plan.setStartDate(cal.getTime());
					plan.setEndDate(cal.getTime());
					save(plan);
				}		
	}else{
		for(int i =0; i <countList.size();i++ ){
			List<CIBatchModifyPlan> temList = countList.get(i);
			CIBatchModifyPlan plan = temList.get(0);
			returnDataList.add(plan.getNewCIRelationShip());
		}
	}	
		return returnDataList;
	}
	
	public List<Long> findServerEngineer(String cisn) {
		return configItemDao.selectServerEngineer(cisn);
	}

	public ConfigItemDao getConfigItemDao() {
		return configItemDao;
	}
	public void setConfigItemDao(ConfigItemDao configItemDao) {
		this.configItemDao = configItemDao;
	}

	public MetaDataManager getMetaDataManager() {
		return metaDataManager;
	}

	public void setMetaDataManager(MetaDataManager metaDataManager) {
		this.metaDataManager = metaDataManager;
	}
}