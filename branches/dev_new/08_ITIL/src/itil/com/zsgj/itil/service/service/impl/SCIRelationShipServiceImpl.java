package com.zsgj.itil.service.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.metadata.service.SystemTableIdGenService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.dao.CustomerDao;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.service.dao.SCIRelationShipDao;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.SCIRelationShipEvent;
import com.zsgj.itil.service.entity.SCIRelationShipType;
import com.zsgj.itil.service.entity.ServiceCatalogue;
import com.zsgj.itil.service.entity.ServiceCatalogueContract;
import com.zsgj.itil.service.entity.ServiceCatalogueContractEvent;
import com.zsgj.itil.service.entity.ServiceCatalogueEvent;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemSLA;
import com.zsgj.itil.service.entity.ServiceItemSLAEvent;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceType;
import com.zsgj.itil.service.service.SCIRelationShipService;

public class SCIRelationShipServiceImpl extends BaseDao implements
		SCIRelationShipService {
	private SCIRelationShipDao sciRelationShipDao;
	private CustomerDao customerDao;
	public SCIRelationShip findRootRelationShip(String serviceCatalogueId) {
		SCIRelationShip result = null;
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.createAlias("this.rootServiceCatalogue", "rootServiceCatalogue")
				.setFetchMode("rootServiceCatalogue", FetchMode.JOIN);
		c.add(Restrictions.eq("rootServiceCatalogue.id", Long
				.valueOf(serviceCatalogueId)));
		c.add(Restrictions.isNull("parentRelationShip"));
		c.addOrder(Order.asc("order"));
		List list = c.list();
		if (!list.isEmpty()) {
			result = (SCIRelationShip) list.iterator().next();
			// System.out.println("result: "+ result.getName());
			this.initSCIRelationShipChild(result);
		}
		return result;
	}

	private void initSCIRelationShipChild(SCIRelationShip parent) {
		Criteria c = super.getCriteria(SCIRelationShip.class);

		// c.add(Restrictions.eq("this.id", parent.getId()));
		// c.setFetchMode("childRelationShips", FetchMode.JOIN);
		// parent = (SCIRelationShip) c.uniqueResult();
		//		
		// Set<SCIRelationShip> childs = parent.getChildRelationShips();
		// if(!childs.isEmpty()){
		// for(SCIRelationShip child : childs){
		// if(child.getTypeFlag()!=null&&
		// child.getTypeFlag().equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
		// //String name = child.getName();
		// //System.out.println("name:"+name);
		// this.initSCIRelationShipChild(child);
		// }
		//				
		// }
		// }
		c.add(Restrictions.eq("rootServiceCatalogue", parent
				.getRootServiceCatalogue()));
		c.add(Restrictions.eq("parentRelationShip", parent));
		// c.add(Restrictions.eq("typeFlag",
		// SCIRelationShip.SCI_TYPE_CATALOGUE));
		c.addOrder(Order.asc("order"));
		List<SCIRelationShip> list = null;
		try {
			list = c.list();
		} catch (org.hibernate.ObjectNotFoundException e) {
			e.printStackTrace();
			System.out.println("服务目录关系引用的子目录或服务项已经被删除，数据不一致");

		} catch (Exception e) {
			e.printStackTrace();

		}
		if (!list.isEmpty()) {
			parent.getChildRelationShips().addAll(list);
			for (SCIRelationShip child : list) {
				// System.out.println("child: "+ child.getName());
				this.initSCIRelationShipChild(child);
			}
		}

	}

	public Page findSCIRelationShip(Map params, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public SCIRelationShip findSCIRelationShipById(String sciRelationShipId) {
		if (sciRelationShipId == null) {
			return null;
		}
		SCIRelationShip result = null;
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("id", Long.valueOf(sciRelationShipId)));
		result = (SCIRelationShip) c.uniqueResult();
		return result;
	}

	public SCIRelationShip findSCIRelationShipWithParentById(
			String sciRelationShipId) {
		if (sciRelationShipId == null) {
			return null;
		}
		SCIRelationShip result = null;
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("id", Long.valueOf(sciRelationShipId)));
		c.setFetchMode("parentRelationShip", FetchMode.JOIN);
		result = (SCIRelationShip) c.uniqueResult();
		return result;
	}

	public SCIRelationShip save(SCIRelationShip sciRelationShip) {
		SCIRelationShip result = (SCIRelationShip) super.save(sciRelationShip);
		return result;
	}

	// 删除的是服务目录,要把此服务目录保存下来，再删除其所有的子，再删除其关系，最后是删除服务目录，要有顺序
	public void remove(SCIRelationShip scirs) {
		super.remove(scirs);
		List<SCIRelationShip> childs = findChildRelationShipByParent(scirs);
		for (SCIRelationShip child : childs) {
			remove(child);
		}
	}

	public List<SCIRelationShip> findChildRelationShipByParent(
			SCIRelationShip parentRelationShip) {
		List<SCIRelationShip> result = null;
		Criteria c = super.getCriteria(SCIRelationShip.class);
		if (parentRelationShip != null) {
			c.add(Restrictions.eq("parentRelationShip", parentRelationShip));
		} else {
			c.add(Restrictions.isNull("parentRelationShip"));
		}
		c.addOrder(Order.asc("order"));
		result = c.list();
		return result;
	}

	public List<SCIRelationShip> findAllChildRelationShipByParent(
			SCIRelationShip parentRelationShip) {
		List<SCIRelationShip> childs = new ArrayList();
		List<SCIRelationShip> list = findChildRelationShipByParent(parentRelationShip);
		for (SCIRelationShip scirs : list) {
			childs.add(scirs);
			childs.addAll(findAllChildRelationShipByParent(scirs));
		}
		return childs;
	}

	public Page findServiceItemByPage(String serviceItemName, int pageNo,
			int pageSize) {
		Criteria c = super.getCriteria(ServiceItem.class);
		if (StringUtils.isNotBlank(serviceItemName)) {
			c.add(Restrictions.ilike("name", serviceItemName,
					MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public ServiceCatalogue findServiceItemById(String id) {
		if (id == "") {
			return null;
		}
		ServiceCatalogue r = null;
		Criteria c = super.getCriteria(ServiceCatalogue.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		r = (ServiceCatalogue) c.uniqueResult();
		return r;
	}

	public SCIRelationShip findRootRelationShipByRootCata(
			ServiceCatalogue serviceCatalogue) {
		if (serviceCatalogue == null) {
			return null;
		}
		SCIRelationShip result = null;
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("serviceCatalogue", serviceCatalogue));
		// c.add(Restrictions.eq("parentRelationShip", null));
		result = (SCIRelationShip) c.uniqueResult();
		return result;
	}

	public List<SCIRelationShip> findRelationShipsInLineToRoot(
			SCIRelationShip sciRelationShip) {
		List<SCIRelationShip> list = new ArrayList();
		list.add(sciRelationShip);
		SCIRelationShip relationShip = sciRelationShip;

		for (;;) {
			SCIRelationShip parent = relationShip.getParentRelationShip();

			if (parent != null) {
				Long parentId = parent.getId();
				parent = this.findSCIRelationShipWithParentById(parentId
						.toString());
				list.add(parent);
				relationShip = parent;
			} else {
				break;
			}
		}
		return list;
	}

	public boolean isRingRelation(SCIRelationShip parentRelationShip,
			SCIRelationShip childRelationShip) {
		List<SCIRelationShip> parents = findRelationShipsInLineToRoot(parentRelationShip);
		List<SCIRelationShip> childs = findAllChildRelationShipByParent(childRelationShip);
		childs.add(childRelationShip);
		for (SCIRelationShip child : childs) {
			ServiceItem childServiceItem = child.getServiceItem();
			ServiceCatalogue childServiceCatalogue = child
					.getServiceCatalogue();
			for (SCIRelationShip parent : parents) {
				ServiceItem parentServiceItem = parent.getServiceItem();
				ServiceCatalogue parentServiceCatalogue = parent
						.getServiceCatalogue();
				if ((childServiceItem != null && parentServiceItem != null && parentServiceItem
						.equals(childServiceItem))
						|| (childServiceCatalogue != null
								&& parentServiceCatalogue != null && parentServiceCatalogue
								.equals(childServiceCatalogue))) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isDoubleSameChilds(SCIRelationShip parentRelationShip,
			SCIRelationShip childRelationShip) {
		List<SCIRelationShip> childs = findChildRelationShipByParent(parentRelationShip);
		for (SCIRelationShip child : childs) {
			ServiceItem curChildServiceItem = childRelationShip
					.getServiceItem();
			ServiceItem otherChildServiceItem = child.getServiceItem();
			ServiceCatalogue curChildServiceCatalogue = childRelationShip
					.getServiceCatalogue();
			ServiceCatalogue otherChildCatalogue = child.getServiceCatalogue();
			if ((curChildServiceItem != null)
					&& (otherChildServiceItem != null)
					&& (otherChildServiceItem.equals(curChildServiceItem))) {
				return true;
			} else if ((curChildServiceCatalogue != null)
					&& (otherChildCatalogue != null)
					&& (otherChildCatalogue.equals(curChildServiceCatalogue))) {
				return true;
			}
			// if ((curChildServiceItem != null && otherChildServiceItem != null
			// && otherChildServiceItem.equals(curChildServiceItem))
			// || (curChildServiceCatalogue != null
			// && otherChildCatalogue != null &&
			// otherChildCatalogue.equals(curChildServiceCatalogue))) {
			// return true;
			// }
		}
		return false;
	}

	/**
	 * 用于将服务目录Id保存在服务目录合同表中
	 * 
	 * @Methods Name isDoubleSameChilds
	 * @Create in sujs
	 */
	public void saveServiceCatalogueIdToContract(String ServiceCatalogueId) {
		// MetaDataManager metaDataManager = (MetaDataManager)
		// ContextHolder.getBean("metaDataManager");
		SystemTableIdGenService stigs = (SystemTableIdGenService) ContextHolder
				.getBean("systemTableIdGenService");
		SystemMainTableService smts = (SystemMainTableService) ContextHolder
				.getBean("systemMainTableService");
		ServiceCatalogueContract serviceContract = new ServiceCatalogueContract();

		Criteria cc = super.getCriteria(ServiceCatalogue.class);
		cc.add(Restrictions.eq("id", Long.parseLong(ServiceCatalogueId)));
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue) cc
				.uniqueResult();

		Criteria c = super.getCriteria(ServiceCatalogueContract.class);
		c.add(Restrictions.eq("serviceCatalogue", serviceCatalogue));

		ServiceCatalogueContract scc = (ServiceCatalogueContract) c
				.uniqueResult();
		if (scc == null) {
			serviceContract.setServiceCatalogue(serviceCatalogue);
			SystemMainTable smt = smts
					.findSystemMainTableByClazz(ServiceCatalogueContract.class);
			String code = stigs.findCurrentIdByRule(smt);
			serviceContract.setContractCode(code);
			super.save(serviceContract);
		}
	}

	/**
	 * 通过服务目录Id获得服务合同Id
	 * 
	 * @Methods Name getServiceCatalogueContractId
	 * @Create In Mar 1, 2009 By sujs
	 * @param ServiceCatalogueId
	 * @return String
	 */
	public String getServiceCatalogueContractId(String ServiceCatalogueId) {
		Criteria cc = super.getCriteria(ServiceCatalogue.class);
		cc.add(Restrictions.eq("id", Long.parseLong(ServiceCatalogueId)));
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue) cc
				.uniqueResult();
		Criteria c = super.getCriteria(ServiceCatalogueContract.class);
		c.add(Restrictions.eq("serviceCatalogue", serviceCatalogue));
		ServiceCatalogueContract serviceCatalogueContract = (ServiceCatalogueContract) c
				.uniqueResult();
		String contractId = String.valueOf(serviceCatalogueContract.getId());
		return contractId;

	}

	/**
	 * 通过服务目录ID取得所有服务项信息并保存到ServiceItemSLA表中
	 * 
	 * @Methods Name saveServiceItemSLAfromservicelogueId
	 * @Create In Mar 1, 2009 sujs
	 * @param ServiceCatalogueId
	 *            void
	 */
	@SuppressWarnings("unchecked")
	public void saveServiceItemSLAfromservicelogueId(String ServiceCatalogueId) {
		// 获取服务目录
		Criteria se = super.getCriteria(ServiceCatalogue.class);
		se.add(Restrictions.eq("id", Long.parseLong(ServiceCatalogueId)));
		ServiceCatalogue sec = (ServiceCatalogue) se.uniqueResult();
		// 获得所有的id为ServiceCatalogueId的服务关系数据
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("rootServiceCatalogue", sec));
		c.add(Restrictions.eq("typeFlag", SCIRelationShip.SCI_TYPE_ITEM));
		List<SCIRelationShip> sCIRelationShip = c.list();
		// 获得服务项
		Criteria cc = super.getCriteria(ServiceItemSLA.class);
		cc.add(Restrictions.eq("serviceCatalogue", sec));
		List<ServiceItemSLA> serviceItemSLA = cc.list();
		// Set<SCIRelationShip> sciShipSet = new HashSet<SCIRelationShip>();
		List<SCIRelationShip> shipList = new ArrayList<SCIRelationShip>();
		if (serviceItemSLA.size() == 0) {
			for (SCIRelationShip sCIR : sCIRelationShip) {
				if (sCIR != null) {
					int flag = 0;
					for (SCIRelationShip pList : shipList) {
						if (pList != null) {
							if (sCIR.getServiceItem().equals(
									pList.getServiceItem())) {
								// shipList.add(sCIR);
								flag = 1;
							}
						}
					}
					if (flag == 0) {
						shipList.add(sCIR);
					}
					// sciShipSet.add(sCIR);
				}
			}
			for (SCIRelationShip ship : shipList) {
				ServiceItemSLA ServiceSLA = new ServiceItemSLA();
				ServiceSLA.setServiceItemName(ship.getServiceItem().getName());
				ServiceSLA.setServiceCatalogue(ship.getRootServiceCatalogue());
				ServiceSLA.setServiceItem(ship.getServiceItem());
				super.save(ServiceSLA);
			}
		}// 否则为修改
		else {
			for (SCIRelationShip scirelation : sCIRelationShip) {// sCIRelationShip
				int i = 0;
				for (ServiceItemSLA sersla : serviceItemSLA) {
					if (scirelation.getServiceItem().equals(
							sersla.getServiceItem())) {
						i = 1;
						break;
					}
				}
				if (i == 0) {
					ServiceItemSLA ServiceSLA = new ServiceItemSLA();
					ServiceSLA.setServiceItemName(scirelation.getServiceItem()
							.getName());
					ServiceSLA.setServiceCatalogue(scirelation
							.getRootServiceCatalogue());
					ServiceSLA.setServiceItem(scirelation.getServiceItem());
					super.save(ServiceSLA);
				}
			}
			// 当merge完之后第二次查询数据
			Criteria ccc = super.getCriteria(ServiceItemSLA.class);
			ccc.add(Restrictions.eq("serviceCatalogue", Long
					.parseLong(ServiceCatalogueId)));
			List<ServiceItemSLA> serviceItemSLAsecond = cc.list();
			for (ServiceItemSLA serslasecond : serviceItemSLAsecond) {
				int j = 0;
				for (SCIRelationShip servicereSecond : sCIRelationShip) {// sCIRelationShip
					if (serslasecond.getServiceItem().equals(
							servicereSecond.getServiceItem())) {
						j = 1;
						break;
					}
				}
				if (j == 0) {
					super.remove(serslasecond);
				}
			}
		}
	}

	public Page findServiceItemByPage(String searchName,
			ServiceItemType serviceItemType, int pageNo, int pageSize) {
		Criteria c = super.getCriteria(ServiceItem.class);
		if (StringUtils.isNotBlank(searchName)) {
			c.add(Restrictions.ilike("name", searchName, MatchMode.ANYWHERE));
		}
		if (serviceItemType != null) {
			c.add(Restrictions.eq("serviceItemType", serviceItemType));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public List<SCIRelationShip> getChildSCIRelationShipsByServiceCata(
			ServiceCatalogue serviceCatalogue) {
		// Criteria c = super.getCriteria(SCIRelationShip.class);
		// c.add(Restrictions.isNull("parentRelationShip"));//父为空
		// c.add(Restrictions.eq("rootServiceCatalogue", serviceCatalogue));
		// SCIRelationShip sRelationShip = (SCIRelationShip) c.uniqueResult();
		Criteria criteria = super.getCriteria(SCIRelationShip.class);
		criteria.add(Restrictions.eq("rootServiceCatalogue", serviceCatalogue));
		criteria.add(Restrictions.isNotNull("parentRelationShip"));
		return criteria.list();
	}

	public void removeServiceCataByRootId(String rootServiceCataId) {
		Criteria c = super.getCriteria(ServiceCatalogue.class);
		c.add(Restrictions.eq("id", Long.valueOf(rootServiceCataId)));
		ServiceCatalogue serviceCatalogue = (ServiceCatalogue) c.uniqueResult();
		Criteria criteria = super.getCriteria(ServiceCatalogueContract.class);
		criteria.add(Restrictions.eq("serviceCatalogue", serviceCatalogue));
		ServiceCatalogueContract serviceCatalogueContract = (ServiceCatalogueContract) criteria
				.uniqueResult();
		Criteria criteria2 = super.getCriteria(ServiceItemSLA.class);
		criteria2.add(Restrictions.eq("serviceCatalogue", serviceCatalogue));
		List<ServiceItemSLA> serSLAList = criteria2.list();
		SCIRelationShip root = this
				.findRootRelationShipByRootCata(serviceCatalogue);
		List<SCIRelationShip> childList = this
				.getChildSCIRelationShipsByServiceCata(serviceCatalogue);
		try {
			if (childList.size() > 0) {
				for (SCIRelationShip ship : childList) {
					if (ship != null) {
						super.remove(ship);
					}
				}
			}
			super.remove(root);
			if (serSLAList.size() > 0) {
				for (ServiceItemSLA sla : serSLAList) {
					if (sla != null) {
						super.remove(sla);
					}
				}
			}
			super.remove(serviceCatalogueContract);
			super.remove(serviceCatalogue);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<SCIRelationShip> findServiceCataShipByRoot(
			ServiceCatalogue serviceCatalogue) {
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("rootServiceCatalogue", serviceCatalogue));
		c.add(Restrictions.isNotNull("parentRelationShip"));
		c.add(Restrictions.isNotNull("serviceCatalogue"));
		return c.list();
	}

	public void removeAllChildServiceCataShip(SCIRelationShip parentRelationShip) {
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("parentRelationShip", parentRelationShip));
		c.add(Restrictions.isNotNull("serviceCatalogue"));
		List<SCIRelationShip> sciReList = c.list();
		if (sciReList.size() > 0) {
			for (SCIRelationShip sciRelationShip : sciReList) {
				removeAllChildServiceCataShip(sciRelationShip);
			}
		} else {
			// ServiceCatalogue serviceCatalogue =
			// rootRelationShip.getServiceCatalogue();
			super.remove(parentRelationShip);
			// super.remove(serviceCatalogue);

		}
	}

	public void saveServiceCataEvent(ServiceCatalogue serviceCatalogue) {
		serviceCatalogue.setStatus(Integer.valueOf(ServiceCatalogue.STATUS_FINISHED));

		ServiceCatalogueContract serviceCatalogueContract = (ServiceCatalogueContract) super.findUniqueBy(ServiceCatalogueContract.class,
						"serviceCatalogue", serviceCatalogue);

		List<ServiceItemSLA> serSLAsList = super.findBy(ServiceItemSLA.class,
				"serviceCatalogue", serviceCatalogue);

		SCIRelationShip rootSCIRelationShip = this.findRootRelationShipByRootCata(serviceCatalogue);
		List<SCIRelationShip> ChildRelationShips = this.getChildSCIRelationShipsByServiceCata(serviceCatalogue);
		try {
			// 保存服务目录历史
			ServiceCatalogueEvent serviceCatalogueEvent = new ServiceCatalogueEvent();
			BeanUtils.copyProperties(serviceCatalogue, serviceCatalogueEvent);
			serviceCatalogueEvent.setServiceCatalogue(serviceCatalogue.getId());
			serviceCatalogueEvent.setId(null);
			super.save(serviceCatalogueEvent);
			// 保存合同历史
			ServiceCatalogueContractEvent serviceCatalogueContractEvent = new ServiceCatalogueContractEvent();
			BeanUtils.copyProperties(serviceCatalogueContract,
					serviceCatalogueContractEvent);
			serviceCatalogueContractEvent
					.setServiceCatalogueEvent(serviceCatalogueEvent);
			serviceCatalogueContractEvent.setId(null);
			super.save(serviceCatalogueContractEvent);

			// 保存服务SLA
			if (serSLAsList.size() > 0) {
				for (ServiceItemSLA serviceItemSLA : serSLAsList) {
					ServiceItemSLAEvent serviceItemSLAEvent = new ServiceItemSLAEvent();
					BeanUtils.copyProperties(serviceItemSLA,
							serviceItemSLAEvent);
					serviceItemSLAEvent
							.setServiceCatalogueEvent(serviceCatalogueEvent);
					serviceItemSLAEvent.setId(null);
					super.save(serviceItemSLAEvent);

				}
			}
			SCIRelationShipEvent shipEvent = new SCIRelationShipEvent();
			shipEvent.setRootServiceCatalogueEvent(serviceCatalogueEvent);
			shipEvent.setParentRelationShip(null);
			ServiceCatalogue serCatalogue = (ServiceCatalogue) super.get(
					ServiceCatalogue.class, serviceCatalogueEvent
							.getServiceCatalogue());
			shipEvent.setServiceCatalogueEvent(serCatalogue);
			shipEvent
					.setServiceItemFee(rootSCIRelationShip.getServiceItemFee());
			shipEvent.setServiceItem(rootSCIRelationShip.getServiceItem());
			shipEvent.setTypeFlag(rootSCIRelationShip.getTypeFlag());
			shipEvent.setOrder(rootSCIRelationShip.getOrder());
			//原因：新加了字段
			//modify By guoxl in 2009/5/11 begin
			shipEvent.setDispFlag(rootSCIRelationShip.getDispFlag());
			//modify By guoxl in 2009/5/11 end
			shipEvent.setId(null);
			super.save(shipEvent);

			for (SCIRelationShip sRelationShip : ChildRelationShips) {
				if (sRelationShip != null) {
					SCIRelationShipEvent sRelationShipEvent = new SCIRelationShipEvent();
					sRelationShipEvent
							.setRootServiceCatalogueEvent(serviceCatalogueEvent);
					sRelationShipEvent.setParentRelationShip(shipEvent);
					sRelationShipEvent.setServiceCatalogueEvent(sRelationShip
							.getServiceCatalogue());
					sRelationShipEvent.setServiceItem(sRelationShip
							.getServiceItem());
					sRelationShipEvent.setServiceItemFee(sRelationShip
							.getServiceItemFee());
					sRelationShipEvent.setTypeFlag(sRelationShip.getTypeFlag());
					sRelationShipEvent.setOrder(sRelationShip.getOrder());
					//原因：新加了字段
					//modify By guoxl in 2009/5/11 begin
					sRelationShipEvent.setDispFlag(sRelationShip.getDispFlag());
					//modify By guoxl in 2009/5/11 end
					sRelationShipEvent.setId(null);
					super.save(sRelationShipEvent);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void saveServiceCataAlterEvent(ServiceCatalogue newServiceCatalogue,
			ServiceCatalogue oldServiceCatalogue) {
		Long oldServiceCatalogueId = oldServiceCatalogue.getId();
		ServiceCatalogueContract oldCatalogueContract = (ServiceCatalogueContract) super
				.findUniqueBy(ServiceCatalogueContract.class,
						"serviceCatalogue", oldServiceCatalogue);
		Long oldCatalogueContractId = oldCatalogueContract.getId();
		ServiceCatalogueContract newCatalogueContract = (ServiceCatalogueContract) super
				.findUniqueBy(ServiceCatalogueContract.class,
						"serviceCatalogue", newServiceCatalogue);
		List<ServiceItemSLA> oldSLAList = super.findBy(ServiceItemSLA.class,
				"serviceCatalogue", oldServiceCatalogue);
		List<ServiceItemSLA> newSLAList = super.findBy(ServiceItemSLA.class,
				"serviceCatalogue", newServiceCatalogue);
		// List<SCIRelationShip> newRelationShips =
		// service.find(SCIRelationShip.class, "rootServiceCatalogue",
		// newServiceCatalogue);
		// List<SCIRelationShip> oldRelationShips =
		// service.find(SCIRelationShip.class, "rootServiceCatalogue",
		// oldServiceCatalogue);
		// --
		SCIRelationShip rootSCIRelationShip = this
				.findRootRelationShipByRootCata(oldServiceCatalogue);
		List<SCIRelationShip> oldChildRelationShips = this
				.getChildSCIRelationShipsByServiceCata(oldServiceCatalogue);// 得到跟下面的所有的子（包括子服务目录和服务项）
		// --
		SCIRelationShip newRootSCIRelationShip = this
				.findRootRelationShipByRootCata(newServiceCatalogue);
		List<SCIRelationShip> newChildRelationShips = this
				.getChildSCIRelationShipsByServiceCata(newServiceCatalogue);// 得到跟下面的所有的子（包括子服务目录和服务项）
		// --
		try {
			// copy根服务目录
			BeanUtils.copyProperties(newServiceCatalogue, oldServiceCatalogue);
			oldServiceCatalogue.setId(oldServiceCatalogueId);
			oldServiceCatalogue.setStatus(ServiceCatalogue.STATUS_FINISHED);
			oldServiceCatalogue.setOldCatalogueId(null);
			super.save(oldServiceCatalogue);

			// 保存服务目录历史
			ServiceCatalogueEvent serviceCatalogueEvent = new ServiceCatalogueEvent();
			BeanUtils
					.copyProperties(oldServiceCatalogue, serviceCatalogueEvent);
			serviceCatalogueEvent.setId(null);
			serviceCatalogueEvent.setServiceCatalogue(oldServiceCatalogue
					.getId());
			super.save(serviceCatalogueEvent);
			// newServiceCatalogue.setStatus(Constants.STATUS_DELETE);
			// service.save(newServiceCatalogue);//************逻辑删除

			List<SCIRelationShip> newReList = this
					.findServiceCataShipByRoot(newServiceCatalogue);// 得到新根下的所有子服务目录
			for (SCIRelationShip ship : newReList) {
				ServiceCatalogue serviceCatalogue = ship.getServiceCatalogue();
				serviceCatalogue.setOldCatalogueId(null);
				super.save(serviceCatalogue);

			}

			// copy服务合同
			BeanUtils
					.copyProperties(newCatalogueContract, oldCatalogueContract);
			oldCatalogueContract.setId(oldCatalogueContractId);
			oldCatalogueContract.setServiceCatalogue(oldServiceCatalogue);
			super.save(oldCatalogueContract);

			// copy服务合同历史
			ServiceCatalogueContractEvent serviceCatalogueContractEvent = new ServiceCatalogueContractEvent();
			BeanUtils.copyProperties(oldCatalogueContract,
					serviceCatalogueContractEvent);
			serviceCatalogueContractEvent.setId(null);
			serviceCatalogueContractEvent
					.setServiceCatalogueEvent(serviceCatalogueEvent);
			super.save(serviceCatalogueContractEvent);
			super.remove(newCatalogueContract);

			// copy 服务SLA
			if (newSLAList.size() > 0) {
				for (ServiceItemSLA serviceItemSLA : newSLAList) {
					serviceItemSLA.setServiceCatalogue(oldServiceCatalogue);
					super.save(serviceItemSLA);

				}
			}
			if (oldSLAList.size() > 0) {
				// 就将其删除标记置为-1，记得保存
				for (ServiceItemSLA serviceItemSLA : oldSLAList) {
					ServiceItemSLAEvent serviceItemSLAEvent = new ServiceItemSLAEvent();// SLA保存历史
					BeanUtils.copyProperties(serviceItemSLA,
							serviceItemSLAEvent);
					serviceItemSLAEvent
							.setServiceCatalogueEvent(serviceCatalogueEvent);
					serviceItemSLAEvent.setId(null);
					super.save(serviceItemSLAEvent);
					super.remove(serviceItemSLA);// *******************应该逻辑删除

				}
			}
			List<SCIRelationShip> oldReList = this
					.findServiceCataShipByRoot(oldServiceCatalogue);// 得到老根下的所有子服务目录

			List<SCIRelationShip> oldChilReList = this
					.getChildSCIRelationShipsByServiceCata(oldServiceCatalogue);// 得到老根下的所有子关系
			oldChilReList.add(rootSCIRelationShip);
			for (SCIRelationShip sciShip : oldChilReList) {
				List<SCIRelationShipType> sciTypeList = super.findBy(
						SCIRelationShipType.class, "sciRelationShip", sciShip);
				if (sciTypeList.size() > 0) {
					for (SCIRelationShipType type : sciTypeList) {
						super.remove(type);
					}
				}
			}
			// copy 服务目录关系表
			newRootSCIRelationShip.setRootServiceCatalogue(oldServiceCatalogue);
			newRootSCIRelationShip.setServiceCatalogue(oldServiceCatalogue);
			super.save(newRootSCIRelationShip);

			if (newChildRelationShips.size() > 0) {// 将所有的子关系的rootServiceCatalogue再换成老的
				for (SCIRelationShip sRelationShip : newChildRelationShips) {
					sRelationShip.setRootServiceCatalogue(oldServiceCatalogue);
					super.save(sRelationShip);
				}

			}
			// 保存根服务目录关系历史
			SCIRelationShipEvent sRelationShipEvent = new SCIRelationShipEvent();
			sRelationShipEvent
					.setRootServiceCatalogueEvent(serviceCatalogueEvent);
			sRelationShipEvent.setParentRelationShip(null);// serviceCatalogueEvent.getServiceCatalogue()
			ServiceCatalogue sCatalogue = (ServiceCatalogue) super.get(
					ServiceCatalogue.class, serviceCatalogueEvent
							.getServiceCatalogue());
			sRelationShipEvent.setServiceCatalogueEvent(sCatalogue);
			sRelationShipEvent.setServiceItem(rootSCIRelationShip
					.getServiceItem());
			sRelationShipEvent.setServiceItemFee(rootSCIRelationShip
					.getServiceItemFee());
			sRelationShipEvent.setTypeFlag(rootSCIRelationShip.getTypeFlag());
			sRelationShipEvent.setOrder(rootSCIRelationShip.getOrder());
			//原因：新加了字段
			//modify By guoxl in 2009/5/11 begin
			sRelationShipEvent.setDispFlag(rootSCIRelationShip.getDispFlag());
			//modify By guoxl in 2009/5/11 end
			// sRelationShipEvent.setId(null);
			super.save(sRelationShipEvent);

			// 删除SCIRelationShipType的老记录
			// 删除根
//			List<SCIRelationShip> childsList = this
//					.getChildSCIRelationShipsByServiceCata(oldServiceCatalogue);// 得到子关系

			if (oldChildRelationShips.size() > 0) {
				// 就将其删除标记置为-1，记得保存
				for (SCIRelationShip sRelationShip : oldChildRelationShips) {
					SCIRelationShipEvent sRelShipEvent = new SCIRelationShipEvent();
					// BeanUtils.copyProperties(sRelationShip,sRelationShipEvent);
					sRelShipEvent
							.setRootServiceCatalogueEvent(serviceCatalogueEvent);
					sRelShipEvent.setParentRelationShip(sRelationShipEvent);
					sRelShipEvent.setServiceCatalogueEvent(sRelationShip
							.getServiceCatalogue());
					sRelShipEvent
							.setServiceItem(sRelationShip.getServiceItem());
					sRelShipEvent.setServiceItemFee(sRelationShip
							.getServiceItemFee());
					sRelShipEvent.setTypeFlag(sRelationShip.getTypeFlag());
					sRelShipEvent.setOrder(sRelationShip.getOrder());
					//modify By guoxl in 2009/5/11 begin
					sRelShipEvent.setDispFlag(sRelationShip.getDispFlag());
					//modify By guoxl in 2009/5/11 end
					// sRelShipEvent.setId(null);
					super.save(sRelShipEvent);
					// service.remove(sRelationShip);//******************草稿可以物理删除
				}
				for (SCIRelationShip sRelationShip : oldChildRelationShips) {// 先删除子服务项再删除子服务目录
					if (sRelationShip.getServiceCatalogue() == null) {
						super.remove(sRelationShip);
					}
				}
				// for(SCIRelationShip
				// sRelationShip:oldChildRelationShips){//要递归先删子再删父
				// if(sRelationShip.getServiceCatalogue()!=null){
				// sciRelationShipService.removeAllChildServiceCata(sRelationShip);
				// service.remove(sRelationShip);
				// }
				// }
				this.removeAllChildServiceCataShip(rootSCIRelationShip);
			}
			for (SCIRelationShip ship : oldReList) {
				ServiceCatalogue oldChildServiceCatalogue = ship
						.getServiceCatalogue();
				super.remove(oldChildServiceCatalogue);

			}

			super.remove(rootSCIRelationShip);// *************草稿可以物理删除，删关系应该先删应用他的，再删自己(删除老的更服务目录关系)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getUserServiceCataJson(UserInfo userInfo) {
		List custIds = customerDao.findCustIdsByUser(userInfo);
		List<ServiceCatalogue> servicecataLogues = sciRelationShipDao.findRootServiceCatalogueByCust(custIds,userInfo);
		List<SCIRelationShip> sciRelationShips = new ArrayList();
		for(ServiceCatalogue servicecataLogue : servicecataLogues){
			sciRelationShips.add(sciRelationShipDao.findRootRelationShip(servicecataLogue));
		}
		Map<Object,List<SCIRelationShip>> rootChildsMap = this.getINCChildMap(sciRelationShips);
		int parent=0;
		int level=1;
		int lft=1;
		int rgt=1;
		String json = "";
		List subList=new ArrayList();
		List paramete=new ArrayList();
		if(sciRelationShips!=null&&sciRelationShips.size()>0){
			paramete.add(sciRelationShips.get(0).getId());
			paramete.add(level);
			paramete.add(lft);
			subList=this.initSCIRelationShipChild(rootChildsMap,rootChildsMap.size(),paramete);
			json+="{id:'"+sciRelationShips.get(0).getId()+"',name:'我的服务目录',";
			String typeName = "目录";
			json+="typeFlag:'"+typeName+"',";
			json+="rootName:'我的服务目录',";
			json += "_parent:"+parent+",";
			json += "_level:"+level+",";
			json += "_lft:"+lft+",";
			int subNum=0;
			if(!subList.get(1).equals("")){
			   subNum=((Integer) subList.get(1)).intValue();//得到值得个数，第二个参数是存的子的总个数
			}
			rgt=lft+subNum*2+1;
			json += "_rgt:"+rgt+",";
			json += "_is_leaf:"+false+"},"; 
			if(!subList.get(0).equals("")){
				json +=subList.get(0);
			 }
			  lft=rgt+1;
		}
		if(json.equals("")){
			json="[]";
		}else{
			json = json.substring(0, json.length()-1);
			json="["+json+"]";
		}
		return json;
	}
	/**
	 * 合并子节点
	 * @Methods Name getINCChildMap
	 * @Create In Nov 18, 2009 By lee
	 * @param list
	 * @return Map<Object,List<SCIRelationShip>>
	 */
	private Map<Object,List<SCIRelationShip>> getINCChildMap(List<SCIRelationShip> list){
		Map<Object,List<SCIRelationShip>> map = new HashMap();
		for(SCIRelationShip ship : list){
			List<SCIRelationShip> childs = sciRelationShipDao.getChildShips(ship);
			for(SCIRelationShip curShip : childs){
				ServiceItem curSi = curShip.getServiceItem();
				ServiceCatalogue curSc = curShip.getServiceCatalogue();
				curShip.getTypeFlag().equals(SCIRelationShip.SCI_TYPE_CATALOGUE);
				if(curShip.getTypeFlag().equals(SCIRelationShip.SCI_TYPE_CATALOGUE)){
					if(!map.containsKey(curSc)){
						List templist = new ArrayList();
						templist.add(curShip);
						map.put(curSc, templist);
					}else{
						List templist = map.get(curSc);
						templist.add(curShip);
						map.put(curSc, templist);
					}
				}
				if(curShip.getTypeFlag().equals(SCIRelationShip.SCI_TYPE_ITEM)){
					if(!map.containsKey(curSi)){
						List templist = new ArrayList();
						templist.add(curShip);
						map.put(curSi, templist);
					}else{
						List templist = map.get(curSi);
						templist.add(curSi);
						map.put(curSc, templist);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 树的子递归
	 * @Methods Name initSCIRelationShipChild
	 * @Create In Nov 18, 2009 By lee
	 * @param dataMap
	 * @param childNum
	 * @param para
	 * @return List
	 */
	private List initSCIRelationShipChild(Map<Object,List<SCIRelationShip>> dataMap,int childNum ,List para){
		List list=new ArrayList();
		List subList=new ArrayList();
		List paramete=new ArrayList();
		int parent1=((Long)para.get(0)).intValue();
		int level=((Integer)para.get(1)).intValue()+1;
		int lft=((Integer)para.get(2)).intValue()+1;
		int rgt=0;
		String temp = "";
		Set set = dataMap.keySet();
		for(Object obj : set){
			List<SCIRelationShip> incList = dataMap.get(obj);
			Map<Object,List<SCIRelationShip>> childMap = this.getINCChildMap(incList);
			if(!childMap.isEmpty()){
				paramete.clear();
				paramete.add(incList.get(0).getId());
				paramete.add(level);
				paramete.add(lft);
				subList=this.initSCIRelationShipChild(childMap,childMap.size(),paramete);
				temp+="{id:'"+incList.get(0).getId()+"',";
				if(Integer.valueOf(0).equals(incList.get(0).getDispFlag())){
					temp+="name:'<font color=red>"+incList.get(0).getName()+"</font>',";
				}else{
					temp+="name:'"+incList.get(0).getName()+"',";
				}
				String typeName = null;
				String typeFlag = incList.get(0).getTypeFlag();
				if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
					typeName = "目录";
				}else{
					typeName = "服务项";
				}
				temp+="typeFlag:'"+typeName+"',";
				//temp+="typeFlag:'"+item.getTypeFlag()+"',";
				temp+="rootName:'"+incList.get(0).getRootServiceCatalogue().getName()+"',";
				temp += "_parent:"+parent1+",";
				temp += "_level:"+level+",";
				temp += "_lft:"+lft+",";
				int subNum=0;
				if(!subList.get(1).equals("")){
				   subNum=((Integer) subList.get(1)).intValue();
				   childNum+=((Integer) subList.get(1)).intValue();
				}
				rgt=lft+subNum*2+1;
				temp += "_rgt:"+rgt+",";
				temp += "_is_leaf:"+false+"},"; 
				if(!subList.get(0).equals("")){
					temp +=subList.get(0);
				 }
				}
				else{
					temp+="{id:'"+incList.get(0).getId()+"',";
					if(Integer.valueOf(0).equals(incList.get(0).getDispFlag())){
						temp+="name:'<font color=red>"+incList.get(0).getName()+"</font>',";
					}else{
						temp+="name:'"+incList.get(0).getName()+"',";
					}
					String typeName = null;
					String typeFlag = incList.get(0).getTypeFlag();
					if(typeFlag.equalsIgnoreCase(SCIRelationShip.SCI_TYPE_CATALOGUE)){
						typeName = "目录";
					}else{
						typeName = "服务项";
					}
					temp+="typeFlag:'"+typeName+"',";
					temp+="rootName:'"+incList.get(0).getRootServiceCatalogue().getName()+"',";
					temp += "_parent:"+parent1+",";
					temp += "_level:"+level+",";
					temp += "_lft:"+lft+",";
					rgt=lft+1;
					temp += "_rgt:"+rgt+",";
					temp += "_is_leaf:"+true+"},"; 
					 }
				
				  lft=rgt+1;
				
		}
		list.add(temp);
		list.add(childNum);
		return list;
	}

	public SCIRelationShipDao getSciRelationShipDao() {
		return sciRelationShipDao;
	}

	public void setSciRelationShipDao(SCIRelationShipDao sciRelationShipDao) {
		this.sciRelationShipDao = sciRelationShipDao;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public List<SCIRelationShip> findServiceItemsByServiceType(
			ServiceType serviceType, UserInfo userInfo) {
		List<SCIRelationShip> myShips = new ArrayList<SCIRelationShip>();
		List<Long> custIds = customerDao.findCustIdsByUser(userInfo);
		List<ServiceCatalogue> rootServiceCatalogues = sciRelationShipDao.findRootServiceCatalogueByCust(custIds,userInfo);
		//modify by awen for conditon judgement on2011-05-18 begin
		//List<SCIRelationShip> allShip = sciRelationShipDao.getShipsByServiceType(serviceType, rootServiceCatalogues);
		List<SCIRelationShip> allShip = (rootServiceCatalogues==null || rootServiceCatalogues.size()==0) ? Collections.<SCIRelationShip>emptyList() : sciRelationShipDao.getShipsByServiceType(serviceType, rootServiceCatalogues);
		//modify by awen for conditon judgement on2011-05-18 enf
		Map<ServiceItem,Map<Long,SCIRelationShip>> map = new HashMap<ServiceItem,Map<Long,SCIRelationShip>>();
		for(SCIRelationShip ship : allShip){
			ServiceItem curSi = ship.getServiceItem();
			if(map.containsKey(curSi)){
				map.get(curSi).put(ship.getRootServiceCatalogue().getCustomer().getId(), ship);
			}else{
				Map<Long,SCIRelationShip> shipMap = new HashMap<Long,SCIRelationShip>();
				shipMap.put(ship.getRootServiceCatalogue().getCustomer().getId(), ship);
				map.put(curSi, shipMap);
			}
		}
		Set<ServiceItem> keySet = map.keySet();
		for(ServiceItem si : keySet){
			myShips.add(this.getMySciRelationShip(map.get(si),custIds));
		}
		return myShips;
	}
	/**
	 * 得到跟用户关系最直接的服务目录关系
	 * @Methods Name getMySciRelationShip
	 * @Create In Nov 20, 2009 By lee
	 * @param shipMap
	 * @param custIds
	 * @return SCIRelationShip
	 */
	private SCIRelationShip getMySciRelationShip(Map<Long,SCIRelationShip> shipMap,List<Long> custIds){
		for(Long custId : custIds){
			if(shipMap.containsKey(custId)){
				return shipMap.get(custId);
			}
		}
		return null;
	}

	public List<SCIRelationShip>  listServiceItemByUserService(String serviceItemName,
			ServiceItemType serviceItemType, ServiceType serviceType, UserInfo userInfo ) {
		try {
			List<SCIRelationShip> myShips = new ArrayList<SCIRelationShip>();
			List<Long> custIds = customerDao.findCustIdsByUser(userInfo);
			
			//modify by awen for add conditon judgement on 2011-05-18 begin
			
			//List<ServiceCatalogue> rootServiceCatalogues = sciRelationShipDao.findRootServiceCatalogueByCust(custIds,userInfo);
			List<ServiceCatalogue> rootServiceCatalogues = custIds!=null && custIds.size() > 0 ? sciRelationShipDao.findRootServiceCatalogueByCust(custIds,userInfo) : Collections.<ServiceCatalogue>emptyList();			
			
			//List<SCIRelationShip> allShip = sciRelationShipDao.getShipsByServiceType(serviceItemName , serviceItemType , serviceType, rootServiceCatalogues );
			List<SCIRelationShip> allShip = rootServiceCatalogues!=null && rootServiceCatalogues.size()>0 ? sciRelationShipDao.getShipsByServiceType(serviceItemName , serviceItemType , serviceType, rootServiceCatalogues ) : Collections.<SCIRelationShip>emptyList();
			
			//modify by awen for add conditon judgement on 2011-05-18 end

			Map<ServiceItem,Map<Long,SCIRelationShip>> map = new HashMap<ServiceItem,Map<Long,SCIRelationShip>>();
			for(SCIRelationShip ship : allShip){
				ServiceItem curSi = ship.getServiceItem();
				if(map.containsKey(curSi)){
					map.get(curSi).put(ship.getRootServiceCatalogue().getCustomer().getId(), ship);
				}else{
					Map<Long,SCIRelationShip> shipMap = new HashMap<Long,SCIRelationShip>();
					shipMap.put(ship.getRootServiceCatalogue().getCustomer().getId(), ship);
					map.put(curSi, shipMap);
				}
			}
			Set<ServiceItem> keySet = map.keySet();
			for(ServiceItem si : keySet){
				myShips.add(this.getMySciRelationShip(map.get(si),custIds));
			}
			return myShips;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
