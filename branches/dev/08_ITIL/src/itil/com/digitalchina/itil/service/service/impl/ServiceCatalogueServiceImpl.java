package com.digitalchina.itil.service.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.DateUtil;
import com.digitalchina.itil.service.entity.SCIRelationShip;
import com.digitalchina.itil.service.entity.ServiceCatalogue;
import com.digitalchina.itil.service.service.ServiceCatalogueService;

public class ServiceCatalogueServiceImpl extends BaseDao implements ServiceCatalogueService {

	public Page findServiceCatalogue(Map params, int pageNo, int pageSize, String orderProp, boolean isAsc) {
		String name = (String) params.get("name");
		String rootFlag = (String) params.get("rootFlag");
		
		Criteria c = super.createCriteria(ServiceCatalogue.class, orderProp, isAsc);
		if(StringUtils.isNotBlank(name)){
			c.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(rootFlag)){
			c.add(Restrictions.eq("rootFlag", Integer.valueOf(rootFlag)));
		}
		
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public ServiceCatalogue save(ServiceCatalogue serviceCatalogue) {
		UserInfo currentUser = UserContext.getUserInfo();
		if(serviceCatalogue.getId()==null){
			serviceCatalogue.setCreateUser(currentUser);
			Date currentDate = DateUtil.getCurrentDate();
			serviceCatalogue.setCreateDate(currentDate);
		}else{
			serviceCatalogue.setModifyUser(currentUser);
			Date currentDate = DateUtil.getCurrentDate();
			serviceCatalogue.setModifyDate(currentDate);
		}
		ServiceCatalogue result = (ServiceCatalogue) super.save(serviceCatalogue);
		return result;
	}

	public ServiceCatalogue findServiceCatalogueById(String id) {
		if(id==null||id.equals("")){
			return null;
		}
		ServiceCatalogue result = null;
		Criteria c = super.getCriteria(ServiceCatalogue.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		result = (ServiceCatalogue) c.uniqueResult();
		return result;
	}
	
	public void removeServiceCatalogue(String scIds) {
		ServiceCatalogue sc = super.get(ServiceCatalogue.class, Long.valueOf(scIds));
//		sc.setDeleteFlag(Integer.valueOf(1));
//		super.save(sc);
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("rootServiceCatalogue", sc));
		List<SCIRelationShip> list = c.list();
		for(SCIRelationShip item : list){
			super.remove(item); //底层做了级联删除操作
		}
		super.remove(sc);
		
	}

	private void removeChildSc(SCIRelationShip parent){
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("rootServiceCatalogue", parent.getRootServiceCatalogue()));
		c.add(Restrictions.eq("parentRelationShip", parent));
		List<SCIRelationShip> childShip = c.list();
		if(!childShip.isEmpty()){
			for(SCIRelationShip item : childShip){
				
			}
		}
		super.remove(parent);
		
	}

	public SCIRelationShip saveSCIRelationShip(SCIRelationShip sciRelationShip) {
		// TODO Auto-generated method stub
		return null;
	}

	public SCIRelationShip findSCIRelationShip(String id) {
		if(id==null||id.equals("")){
			return null;
		}
		SCIRelationShip result = null;
		Criteria c = super.getCriteria(SCIRelationShip.class);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		result = (SCIRelationShip) c.uniqueResult();
		return result;
	}
}
