package com.zsgj.itil.require.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.workflow.entity.PageModelConfigUnit;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.CustomerOutUserInfo;
import com.zsgj.itil.require.service.RequireSIService;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.SCIRelationShipType;
import com.zsgj.itil.service.entity.ServiceCatalogue;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.service.SCIRelationShipTypeService;

public class RequireSIServiceImpl extends BaseDao implements RequireSIService{
	private SCIRelationShipTypeService sciRelationShipTypeService;
	private DepartmentService deptService;
	private void getParentDept(List<Department> list, Department dept){
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("parentDepartment", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		Department parent = result.getParentDepartment();
		
		if(parent!=null){
			//System.out.println("parent name : "+ parent.getDepartName());
			list.add(parent);
			//�����ݹ�
			this.getParentDept(list, parent);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findCustIdByUser(UserInfo userInfo) {
		List<Long> ids = new ArrayList<Long>();
		userInfo = super.get(UserInfo.class, userInfo.getId());
		
		Integer externalFlag = userInfo.getExternalFlag();
		if(externalFlag==null||externalFlag.intValue()==0){
			//��ȡ��ǰ�˵���������
			Department dept = userInfo.getDepartment();
			//�洢��С����Ĳ����б�
			List<Department> deptList = new ArrayList<Department>();
			//���뵱ǰ����
			deptList.add(dept);
			//�ݹ��ȡ�ϼ����ţ���˳����뼯��
			this.getParentDept(deptList, dept);
			for(Department deptItem : deptList){
				//�ò���ȥ��ȡ�ڲ��ͻ�
				Criteria c = super.getCriteria(Customer.class);
				c.add(Restrictions.eq("department", deptItem));
				//System.out.println("deptname:"+ deptItem.getDepartName());
				List<Customer> list2 = c.list();
				if(!list2.isEmpty()){
					Customer customerIn = list2.iterator().next();
					//System.out.print("custIn id:"+ customerIn.getId());
					//System.out.println(", custIn name:"+ customerIn.getCustomerName());
				}
				
				c.setProjection(Projections.property("this.id"));
				List<Long> list = c.list();
				if(!list.isEmpty()){
					Long custIn = list.iterator().next();
					ids.add(custIn);
				}
			}
			
		}else{//�ⲿ�û����
			Long customerOut = null;
			Criteria c = super.getCriteria(CustomerOutUserInfo.class);
			c.add(Restrictions.eq("userInfo", userInfo));
			c.setProjection(Projections.property("customerOut.id"));
			List<Long> list = c.list();
			if(list.size()==1){
				customerOut = list.iterator().next();
			}
			ids.add(customerOut);
		}
		return ids;
	}
	@SuppressWarnings("unchecked")
	public List<UserInfo> findDataScopeByUser(UserInfo userInfo) {
		List<UserInfo> list = new ArrayList();
		list.add(userInfo);
		Set<Role> curRole = userInfo.getRoles();
		for(Role role : curRole){
			if(role.getDataViewFlag().equals(Role.VIEW_FLAG_ALL)){//�Ƿ�ɲ鿴��������Ȩ��
				Department roleDept = role.getDepartment();
				if(roleDept.getParentDepartment()==null){
					return null;	//�����ɫ����Ϊ����ţ��򷵻�null
				}else{
					Department dept = userInfo.getDepartment();
					List depts = deptService.findDeptByParentCode(dept.getDepartCode().toString());//��ȡ���ŵ������Ӳ���
					depts.add(dept);
					Criteria criteria = super.getCriteria(UserInfo.class);
					criteria.add(Restrictions.in("department", depts));//���˲���
					List deptUsers = criteria.list();
					list.addAll(deptUsers);//�������������˼���鿴�û���Χ
				}
				
			}
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<ServiceCatalogue> findServiceCatalogueByCust(List<Long> custIds) {
		List<ServiceCatalogue> scList = new ArrayList<ServiceCatalogue>();
		//ͨ����Щ�ڲ��ͻ�id��ȡ����Ŀ¼��Ҫ�󷵻�rootFlag=1��
		//CustomerType custTypeIn = super.get(CustomerType.class, c));
		for(Long custId : custIds){
			Criteria c = super.getCriteria(ServiceCatalogue.class);
			c.add(Restrictions.eq("customer", custId));
			//c.add(Restrictions.eq("customerType.id", Long.valueOf(1)));
			c.add(Restrictions.eq("status", Integer.valueOf(1))); //������-1
			
			/*
			 ��ֹ�����ڲ�����ʼ����ʼ��Ч�����ڲ������֮ǰ��Ҳ�������������ڲ��귶Χ֮�ڡ�
			 */
//			int year = TimeTool.getYearOfDate(DateUtil.getCurrentDateTime());
//			String yearBegin = year+"-04-01";
//			String yearEnd = year+1+"-03-31";
//			
//			Date dateBegin = DateUtil.convertStringToDate(yearBegin);
//			Date dateEnd = DateUtil.convertStringToDate(yearEnd);
//			
//			
//			Disjunction disjunction = Restrictions.disjunction();
//			disjunction.add(Restrictions.gt("endDate", dateBegin));
//			disjunction.add(Restrictions.lt("beginDate", dateEnd));
//			
//			c.add(disjunction);

			Date currentDate = DateUtil.getCurrentDate();
			c.add(Restrictions.le("beginDate", currentDate));
			c.add(Restrictions.ge("endDate", currentDate));
			
			List<ServiceCatalogue> list = c.list();
			if(!list.isEmpty()){
				scList.addAll(list);
			}
		}
//		Criteria c = super.getCriteria(ServiceCatalogue.class);
//		c.add(Restrictions.in("customer", custIds));
//		c.add(Restrictions.eq("customerType.id", Long.valueOf(1)));
//		List list = c.list();
		return scList;
	}
/**
 * ��ȡ����Ŀ¼��ϵ����
 * by sujs
 */
	@SuppressWarnings("unchecked")
	public List<SCIRelationShip> findSCIRelationShipByService(
			List<ServiceCatalogue> ServiceCatalogue){
		
		List<SCIRelationShip> sCIRelationShip=new ArrayList<SCIRelationShip>();
		
		for(ServiceCatalogue serviceCatalogue : ServiceCatalogue){
			Criteria c= super.getCriteria(SCIRelationShip.class);
			c.add(Restrictions.eq("serviceCatalogue.id",serviceCatalogue.getId()));
			sCIRelationShip.add((SCIRelationShip)c.uniqueResult());
		}
		return sCIRelationShip;
	}

/**
 * ���ݷ����ϵid��ȡ�ӷ���Ŀ¼��ϵ����
 * By sujs
 * */
@SuppressWarnings({ "unchecked", "unchecked" })
public List<SCIRelationShip> findSCIRelationShipById(String relationShipId,Map storeData,String serviceTypeKeyWord) {
	
	Long compareID=Long.parseLong(relationShipId);
	List<SCIRelationShip> childSCIRelationShip=new ArrayList<SCIRelationShip>();
	if(storeData!=null&& !storeData.isEmpty()&&storeData.containsKey(compareID)){
		Object key=null;
		Object value=null;
		Iterator it=storeData.keySet().iterator();
		while(it.hasNext()){
			key=it.next();
			value=storeData.get(key);
			if(storeData.get(compareID).equals(value)){
				Criteria c= super.getCriteria(SCIRelationShip.class);
				c.add(Restrictions.eq("this.id",Long.valueOf(key.toString())));
				c.setFetchMode("childRelationShips", FetchMode.JOIN);
				SCIRelationShip sCIRelationShip=(SCIRelationShip)c.uniqueResult();
				Set tempsci=sCIRelationShip.getChildRelationShips();
				Iterator itt=tempsci.iterator();
				while(itt.hasNext()){
					SCIRelationShip slationShip=(SCIRelationShip)itt.next();
					//����һ����ϵ���ǳ�������Ǹ��Եġ�����list
				  List<SCIRelationShipType> sCIRelationShipType=this.findNodeKeyWord(slationShip);
				  for (SCIRelationShipType scitype : sCIRelationShipType) {
						if (scitype.getServiceType().getKeyWord().equals(serviceTypeKeyWord)) {
							 childSCIRelationShip.add(slationShip);
							 break;
						   }
				  } 
				}
				
			}	
		}
	}else{
		Criteria c = super.getCriteria(SCIRelationShip.class);
			c.add(Restrictions.eq("id", Long.parseLong(relationShipId)));
			SCIRelationShip sCIRelationShip = (SCIRelationShip)c.uniqueResult();
			Set tempsci = sCIRelationShip.getChildRelationShips();
			Iterator itt = tempsci.iterator();
			while (itt.hasNext()) {
				SCIRelationShip slationShip=(SCIRelationShip)itt.next();
				  List<SCIRelationShipType> sCIRelationShipType=this.findNodeKeyWord(slationShip);
				  for (SCIRelationShipType scitype : sCIRelationShipType) {
					  ServiceItem scid = slationShip.getServiceItem();
						if(scid!=null){
							System.out.println("scidname :"+ scid.getName());
							System.out.println();
						}
						if (scitype.getServiceType().getKeyWord().equals(serviceTypeKeyWord)) {
							 childSCIRelationShip.add(slationShip);
							 break;
						   }
				  } 
			}
	}
	return childSCIRelationShip;
}

	/*
	 * ͨ�������ϵid��÷���Ŀ¼����
	 * */
	@SuppressWarnings("unchecked")
	public SCIRelationShip findServiceCatalogueByRelationId(
			String relationShipId) {
		Criteria c= super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("id",Long.parseLong(relationShipId)));
		SCIRelationShip sCIRelationShip=(SCIRelationShip)c.uniqueResult();
		return sCIRelationShip;
	}
	
	public List<PageModelPanel> getPanelsByServiceItem(ServiceItem serviceItem) {
		ServiceItemUserTable siut= (ServiceItemUserTable) super.findUniqueBy(ServiceItemUserTable.class, "serviceItem", serviceItem);
		SystemMainTable smt = siut.getSystemMainTable();
		Criteria criteria = super.getCriteria(PageModelPanel.class);
		criteria.setFetchMode("this.pagePanel", FetchMode.JOIN);
		criteria.add(Restrictions.eq("pagePanel.systemMainTable", smt));
		return criteria.list();
	}
	

	/**
		 * ͨ����̬��clazz����ȡ���е�����
		 * 
		 * @Methods Name findAutoClazz
		 * @Create In Mar 4, 2009 By sujs
		 * @param clazz
		 * @return List
		 */
		@SuppressWarnings("unchecked")
		public List findAutoClazz(String clazz) {
			List list=null;
			try {
				Class className = Class.forName(clazz);
				Criteria c = super.getCriteria(className);
				 list = c.list();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return list;
		}
		
		//remove by lee for scrap function in 20090625 begin
//	public List<SCIRelationShip> findGeneralSCIRelationShipByCata(List<ServiceCatalogue> serviceCatalogues) {
//		List<SCIRelationShip> sCIRelationShip=new ArrayList<SCIRelationShip>();
//		
//		for(ServiceCatalogue serviceCatalogue : serviceCatalogues){
//			Criteria c= super.getCriteria(SCIRelationShip.class);
//			c.add(Restrictions.eq("serviceCatalogue.id",serviceCatalogue.getId()));
//			SCIRelationShip relationShip = (SCIRelationShip)c.uniqueResult();
//			if(sciRelationShipTypeService.isGeneral(relationShip))
//			sCIRelationShip.add((SCIRelationShip)c.uniqueResult());
//		}
//		return sCIRelationShip;
//	}
//		
//	public List<SCIRelationShip> findSpecialSCIRelationShipByCata(
//			List<ServiceCatalogue> serviceCatalogues) {
//		List<SCIRelationShip> sCIRelationShip=new ArrayList<SCIRelationShip>();
//		
//		for(ServiceCatalogue serviceCatalogue : serviceCatalogues){
//			Criteria c= super.getCriteria(SCIRelationShip.class);
//			c.add(Restrictions.eq("serviceCatalogue.id",serviceCatalogue.getId()));
//			SCIRelationShip relationShip = (SCIRelationShip)c.uniqueResult();
//			if(sciRelationShipTypeService.isSpecial(relationShip))
//			sCIRelationShip.add((SCIRelationShip)c.uniqueResult());
//		}
//		return sCIRelationShip;
//	}
//
//	
//	public List<SCIRelationShip> findGeneralSCIRelationShipById(
//			String relationShipId, Map storeData) {
//
//		Long compareID=Long.parseLong(relationShipId);
//		List<SCIRelationShip> childSCIRelationShip=new ArrayList<SCIRelationShip>();
//		if(storeData!=null&& !storeData.isEmpty()&&storeData.containsKey(compareID)){
//			Object key=null;
//			Object value=null;
//			Iterator it=storeData.keySet().iterator();
//			while(it.hasNext()){
//				key=it.next();
//				value=storeData.get(key);
//				if(storeData.get(compareID).equals(value)){
//					Criteria c= super.getCriteria(SCIRelationShip.class);
//					c.add(Restrictions.eq("this.id",Long.valueOf(key.toString())));
//					c.setFetchMode("childRelationShips", FetchMode.JOIN);
//					SCIRelationShip sCIRelationShip=(SCIRelationShip)c.uniqueResult();
//					Set tempsci=sCIRelationShip.getChildRelationShips();
//					Iterator itt=tempsci.iterator();
//					while(itt.hasNext()){
//						SCIRelationShip ship = (SCIRelationShip) itt.next();
//						if(sciRelationShipTypeService.isGeneral(ship))
//						childSCIRelationShip.add(ship);
//					}
//					
//				}	
//			}
//		}else{
//			Criteria c= super.getCriteria(SCIRelationShip.class);
//			c.add(Restrictions.eq("id",Long.parseLong(relationShipId)));
//			SCIRelationShip sCIRelationShip=(SCIRelationShip)c.uniqueResult();
//			Set tempsci=sCIRelationShip.getChildRelationShips();
//			Iterator itt=tempsci.iterator();
//			while(itt.hasNext()){
//				SCIRelationShip ship = (SCIRelationShip) itt.next();
//				if(sciRelationShipTypeService.isGeneral(ship))
//				childSCIRelationShip.add(ship);
//			}
//		}
//		return childSCIRelationShip;
//	}
//
//	public List<SCIRelationShip> findSpecialSCIRelationShipById(
//			String relationShipId, Map storeData) {
//
//		Long compareID=Long.parseLong(relationShipId);
//		List<SCIRelationShip> childSCIRelationShip=new ArrayList<SCIRelationShip>();
//		if(storeData!=null&& !storeData.isEmpty()&&storeData.containsKey(compareID)){
//			Object key=null;
//			Object value=null;
//			Iterator it=storeData.keySet().iterator();
//			while(it.hasNext()){
//				key=it.next();
//				value=storeData.get(key);
//				if(storeData.get(compareID).equals(value)){
//					Criteria c= super.getCriteria(SCIRelationShip.class);
//					c.add(Restrictions.eq("this.id",Long.valueOf(key.toString())));
//					c.setFetchMode("childRelationShips", FetchMode.JOIN);
//					SCIRelationShip sCIRelationShip=(SCIRelationShip)c.uniqueResult();
//					Set tempsci=sCIRelationShip.getChildRelationShips();
//					Iterator itt=tempsci.iterator();
//					while(itt.hasNext()){
//						SCIRelationShip ship = (SCIRelationShip) itt.next();
//						if(sciRelationShipTypeService.isSpecial(ship))
//						childSCIRelationShip.add(ship);
//					}
//					
//				}	
//			}
//		}else{
//			Criteria c= super.getCriteria(SCIRelationShip.class);
//			c.add(Restrictions.eq("id",Long.parseLong(relationShipId)));
//			SCIRelationShip sCIRelationShip=(SCIRelationShip)c.uniqueResult();
//			Set tempsci=sCIRelationShip.getChildRelationShips();
//			Iterator itt=tempsci.iterator();
//			while(itt.hasNext()){
//				SCIRelationShip ship = (SCIRelationShip) itt.next();
//				if(sciRelationShipTypeService.isSpecial(ship))
//				childSCIRelationShip.add(ship);
//			}
//		}
//		return childSCIRelationShip;
//	}
		//remove by lee for scrap function in 20090625 end
		
	public SCIRelationShipTypeService getSciRelationShipTypeService() {
		return sciRelationShipTypeService;
	}

	public void setSciRelationShipTypeService(
			SCIRelationShipTypeService sciRelationShipTypeService) {
		this.sciRelationShipTypeService = sciRelationShipTypeService;
	}
	
	public List findEntities(String className, String serviceItemId) {
		Criteria criteria = super.getCriteria(this.getClass(className));
		criteria.add(Restrictions.eq("serviceItem", Long.valueOf(serviceItemId)));
		List list = criteria.list();
		return list;
	}
	private Class getClass(String className){
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clazz;
	}

	@SuppressWarnings("unchecked")
	public List<SCIRelationShipType> findNodeKeyWord(SCIRelationShip relationShip) {
		Criteria c= super.getCriteria(SCIRelationShipType.class);
		c.add(Restrictions.eq("sciRelationShip",relationShip));
		List<SCIRelationShipType> list =c.list();
		return list;
	}

	public List findEntities(String className, String id, List<UserInfo> users) {
		Criteria criteria = super.getCriteria(this.getClass(className));
		criteria.add(Restrictions.eq("serviceItem", Long.valueOf(id)));
		if(users!=null){//����鿴�û�Ϊ�գ��򲻿��Ǵ�����
			criteria.add(Restrictions.in("createUser", users));
		}
		List list = criteria.list();
		return list;
	}
	public List findAuditHisEntities(String className, String siId,UserInfo user) {
		Criteria creteria = super.createCriteria(ServiceItemApplyAuditHis.class); 
		creteria.add(Restrictions.eq("requirementClass", className));//������
		creteria.add(Restrictions.eq("serviceItem.id", Long.valueOf(siId)));//���˷�����
		creteria.add(Restrictions.eq("approver", user));
		List<ServiceItemApplyAuditHis> hises = creteria.list();
		List list = new ArrayList();
		Set set = new HashSet();
		for(ServiceItemApplyAuditHis his : hises){
			Long reqId = his.getRequirementId();
			if(!set.contains(reqId)){//ȥ�ش���
				set.add(reqId);
				BaseObject obj = (BaseObject) this.get(his.getRequirementClass(), reqId);//��ȡ��Ӧ��¼
				list.add(obj);
			}
		}
		return list;
	}

	public ServiceItemApplyAuditHis findLastHis(String id, String siId, UserInfo user) {
		
		Criteria creteria = super.createCriteria(ServiceItemApplyAuditHis.class); 
		creteria.add(Restrictions.eq("requirementId", Long.valueOf(id)));//����ID
		creteria.add(Restrictions.eq("serviceItem.id", Long.valueOf(siId)));//���˷�����
		creteria.add(Restrictions.eq("approver", user));
		creteria.addOrder(Order.desc("id"));
		List<ServiceItemApplyAuditHis> hises = creteria.list();
		if(hises.size()==0){
			return null;
		}else{
			ServiceItemApplyAuditHis lastHis = hises.get(0);
			return lastHis;
		}
	}
	
	public String getPageModelNameByNode(String processId, String nodeId) {
		Criteria creteria = super.createCriteria(PageModelConfigUnit.class); 
		creteria.add(Restrictions.eq("processId", Long.valueOf(processId)));//����ID
		creteria.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));//���˷�����
		List<PageModelConfigUnit> list = creteria.list();
		if(!list.isEmpty()){
			return list.get(0).getPageModelName();
		}else{
			return null;
		}
	}

	public DepartmentService getDeptService() {
		return deptService;
	}

	public void setDeptService(DepartmentService deptService) {
		this.deptService = deptService;
	}

	public void removeHisByApply(String className, String dataId) {
		Criteria creteria = super.createCriteria(ServiceItemApplyAuditHis.class); 
		creteria.add(Restrictions.eq("requirementClass", className));//��������
		creteria.add(Restrictions.eq("requirementId", Long.valueOf(dataId)));//����ID
		List<ServiceItemApplyAuditHis> list = creteria.list();
		for(ServiceItemApplyAuditHis his: list){
			this.remove(his);
		}
		
	}

}
