package com.zsgj.itil.service.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.SCIRelationShipType;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceType;
import com.zsgj.itil.service.service.SCIRelationShipTypeService;

public class SCIRelationShipTypeServiceImpl extends BaseDao implements SCIRelationShipTypeService{

	public List<SCIRelationShipType> findTypesByRelationShip(
			SCIRelationShip sciRelationShip) {
		List<SCIRelationShipType> list = null;
		Criteria criteria = super.createCriteria(SCIRelationShipType.class);
		criteria.setFetchMode("sciRelationShip", FetchMode.JOIN);
		criteria.setFetchMode("serviceType", FetchMode.JOIN);
		criteria.add(Restrictions.eq("sciRelationShip", sciRelationShip));
		list = criteria.list();
		return list;
	}

	private List<SCIRelationShipType> findTypes(SCIRelationShip sciRelationShip, ServiceType serviceType){
		Criteria criteria = super.createCriteria(SCIRelationShipType.class);
		criteria.add(Restrictions.eq("sciRelationShip", sciRelationShip));
		criteria.add(Restrictions.eq("serviceType", serviceType));
		return criteria.list();
	}
	public void removeJoin(SCIRelationShipType sciRelationShipType) {
		SCIRelationShip sciRelationShip = sciRelationShipType.getSciRelationShip();
		ServiceType serviceType = sciRelationShipType.getServiceType();
		SCIRelationShip parentRelationShip = sciRelationShip.getParentRelationShip();
		super.remove(sciRelationShipType);
		if(parentRelationShip!=null){
		//modify by lee for 级联删除服务类关系类型 in 20090604 begin
			if(this.checkType(parentRelationShip, serviceType)&&!this.isKernelType(sciRelationShip, serviceType)){
				//SCIRelationShipType ShipType = this.findTypeByRelationShipTypeAndServiceType(parentRelationShip, serviceType);
				//this.removeJoin(ShipType);
				List<SCIRelationShipType> sts = this.findTypes(parentRelationShip, serviceType);//发现以前逻辑不完整造成多条冗余数据重复，暂处理
				for(SCIRelationShipType st : sts){
					this.removeJoin(st);
				}
				
			}
//			if(this.isGeneral(sciRelationShip)==this.isGeneralParent(parentRelationShip)&&
//					this.isSpecial(sciRelationShip)==this.isSpecialParent(parentRelationShip)){
//				if(!this.isGeneral(sciRelationShip)==this.isGeneral(parentRelationShip)||
//						!this.isSpecial(sciRelationShip)==this.isSpecial(parentRelationShip)){
//				SCIRelationShipType ShipType = this.findTypeByRelationShipTypeAndServiceType(parentRelationShip, serviceType);
//				this.removeJoin(ShipType);
//				}
//			}
			//modify by lee for 级联删除服务类关系类型 in 20090604 end
		}
	}
	
	public void saveJoin(SCIRelationShipType sciRelationShipType) {
		super.save(sciRelationShipType);
		SCIRelationShip sciRelationShip = sciRelationShipType.getSciRelationShip();
		Criteria criteria = super.getCriteria(SCIRelationShip.class);
		criteria.setFetchMode("parentRelationShip", FetchMode.JOIN);
		criteria.add(Restrictions.eq("id", sciRelationShip.getId()));
		sciRelationShip = (SCIRelationShip) criteria.uniqueResult();
		SCIRelationShip parentRelationShip = sciRelationShip.getParentRelationShip();
//modify by lee for 级联保存服务类关系类型 in 20090604 begin		
		ServiceType serviceType = sciRelationShipType.getServiceType();
		if(parentRelationShip!=null&&!this.checkTypeWithOutChild(parentRelationShip, sciRelationShip, serviceType)){
			SCIRelationShipType newShipType = new SCIRelationShipType();
			newShipType.setSciRelationShip(parentRelationShip);
			newShipType.setServiceType(serviceType);
			this.saveJoin(newShipType);

//		if(parentRelationShip!=null){
//			if(!this.isGeneral(sciRelationShip)==this.isGeneral(parentRelationShip)||
//					!this.isSpecial(sciRelationShip)==this.isSpecial(parentRelationShip)){
//				ServiceType serviceType = sciRelationShipType.getServiceType();
//			SCIRelationShipType newShipType = new SCIRelationShipType();
//			newShipType.setSciRelationShip(parentRelationShip);
//			newShipType.setServiceType(serviceType);
//			this.saveJoin(newShipType);
//			}
//modify by lee for 级联保存服务类关系类型 in 20090604 end	
		}
		
	}
	
	public SCIRelationShipType findTypeByRelationShipTypeAndServiceType(
			SCIRelationShip sciRelationShip, ServiceType serviceType) {
		Criteria criteria = super.createCriteria(SCIRelationShipType.class);
		criteria.add(Restrictions.eq("sciRelationShip", sciRelationShip));
		criteria.add(Restrictions.eq("serviceType", serviceType));
		SCIRelationShipType result= (SCIRelationShipType) criteria.uniqueResult();
		return result;
	}
	
	public SCIRelationShipType createRelationShipType(
			SCIRelationShip sciRelationShip, String keyWord) {
		SCIRelationShipType shipType = this.findTypeByRelationShipTypeAndKeyWord(sciRelationShip,keyWord);
		if(shipType==null){
			shipType = new SCIRelationShipType();
			shipType.setSciRelationShip(sciRelationShip);
			ServiceType serviceType = super.findUniqueBy(ServiceType.class, "keyWord", keyWord);
			shipType.setServiceType(serviceType);
		}
		return shipType;
	}

	public SCIRelationShipType findTypeByRelationShipTypeAndKeyWord(
			SCIRelationShip sciRelationShip, String keyWord) {
		ServiceType serviceType = super.findUniqueBy(ServiceType.class, "keyWord", keyWord);
		if(serviceType==null){
			return null;
		}else{
			return this.findTypeByRelationShipTypeAndServiceType(sciRelationShip, serviceType);
		}
	}

	public void updateTypesBySci(ServiceItem serviceItem, ServiceType oldType,
			ServiceType newType) {
		Criteria criteria = super.createCriteria(SCIRelationShipType.class);
		criteria.createAlias("this.sciRelationShip", "scirs").setFetchMode("scirs", FetchMode.JOIN);
		criteria.add(Restrictions.eq("scirs.serviceItem", serviceItem));
		//criteria.createAlias("scirs.serviceItem", "sci").setFetchMode("sci", FetchMode.JOIN);
		//criteria.add(Restrictions.eq("sci", serviceItem));
		//criteria.add(Restrictions.eq("serviceType", oldType));
		List<SCIRelationShipType> scirsts = criteria.list();
		for(SCIRelationShipType scirst : scirsts){
			SCIRelationShipType newScirst = new SCIRelationShipType();
			newScirst.setSciRelationShip(scirst.getSciRelationShip());
			newScirst.setServiceType(newType);
			this.removeJoin(scirst);
			this.saveJoin(newScirst);
		}
	}
	public boolean isKernelType(SCIRelationShip sciRelationShip,ServiceType serviceType){
		Criteria criteria = super.createCriteria(SCIRelationShip.class);
		criteria.add(Restrictions.eq("id",sciRelationShip.getId()));
		criteria.createAlias("this.serviceItem", "serviceItem").setFetchMode("serviceItem", FetchMode.JOIN);
		criteria.add(Restrictions.isNotNull("serviceItem"));
		criteria.add(Restrictions.eq("serviceItem.serviceType", serviceType));
		return !criteria.list().isEmpty();
	}
	
	
	  
	public boolean checkType(SCIRelationShip sciRelationShip,ServiceType serviceType) {
		if(isKernelType(sciRelationShip,serviceType)){
			return true;
		}
		Criteria criteria = super.createCriteria(SCIRelationShipType.class);
		criteria.createAlias("this.sciRelationShip", "scirs").setFetchMode("scirs",FetchMode.JOIN);
		criteria.add(Restrictions.eq("scirs.parentRelationShip", sciRelationShip));
		criteria.add(Restrictions.eq("serviceType", serviceType));
		return !criteria.list().isEmpty();
	}
	/**
	 * 验证参数父关系是否是因子关系而得到的关系类型
	 * @Methods Name checkTypeWithOutChild
	 * @Create In Jun 25, 2009 By lee
	 * @param parentRelationShip
	 * @param childRelationShip
	 * @param serviceType
	 * @return boolean
	 */
	private boolean checkTypeWithOutChild(SCIRelationShip parentRelationShip,SCIRelationShip childRelationShip,ServiceType serviceType){
		if(isKernelType(parentRelationShip,serviceType)){
			return true;
		}
		Criteria criteria = super.createCriteria(SCIRelationShipType.class);
		criteria.createAlias("this.sciRelationShip", "sciRelationShip").setFetchMode("sciRelationShip",FetchMode.JOIN);
		criteria.add(Restrictions.eq("sciRelationShip.parentRelationShip", parentRelationShip));
		criteria.add(Restrictions.not(Restrictions.eq("sciRelationShip", childRelationShip)));
		criteria.add(Restrictions.eq("serviceType", serviceType));
		return !criteria.list().isEmpty();
	}
	//remove by lee for scrap function in 20090625 begin
//	public boolean isGeneral(SCIRelationShip sciRelationShip) {
//		List<SCIRelationShipType> list = null;
//		Criteria criteria = super.createCriteria(SCIRelationShipType.class);
//		criteria.createAlias("this.serviceType", "st").setFetchMode("st", FetchMode.JOIN);
//		criteria.add(Restrictions.eq("st.keyWord", ServiceType.TYPE_GENERAL));
//		criteria.add(Restrictions.eq("sciRelationShip", sciRelationShip));
//		list = criteria.list();
//		if(list.size()>0){
//			return true;
//		}else{
//			return false;
//		}
//	}
//
//	public boolean isSpecial(SCIRelationShip sciRelationShip) {
//		List<SCIRelationShipType> list = null;
//		Criteria criteria = super.createCriteria(SCIRelationShipType.class);
//		criteria.createAlias("this.serviceType", "st").setFetchMode("st", FetchMode.JOIN);
//		criteria.add(Restrictions.eq("st.keyWord", ServiceType.TYPE_SPECIAL));
//		criteria.add(Restrictions.eq("sciRelationShip", sciRelationShip));
//		list = criteria.list();
//		if(list.size()>0){
//			return true;
//		}else{
//			return false;
//		}
//	}
	
//	private boolean isGeneralParent(SCIRelationShip sciRelationShip){
//		Criteria criteria = super.createCriteria(SCIRelationShip.class);
//		criteria.add(Restrictions.eq("parentRelationShip", sciRelationShip));
//		List<SCIRelationShip> list = criteria.list();
//		for(SCIRelationShip rs : list){
//			if(isGeneral(rs)){
//				return true;
//			}
//		}
//		return false;
//	}
//	private boolean isSpecialParent(SCIRelationShip sciRelationShip){
//		Criteria criteria = super.createCriteria(SCIRelationShip.class);
//		criteria.add(Restrictions.eq("parentRelationShip", sciRelationShip));
//		List<SCIRelationShip> list = criteria.list();
//		for(SCIRelationShip rs : list){
//			if(isSpecial(rs)){
//				return true;
//			}
//		}
//		return false;
//	}
	//remove by lee for scrap function in 20090625 end
}
