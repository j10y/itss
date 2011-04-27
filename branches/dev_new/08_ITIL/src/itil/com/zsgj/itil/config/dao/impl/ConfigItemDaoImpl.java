package com.zsgj.itil.config.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.config.dao.ConfigItemDao;
import com.zsgj.itil.config.entity.CIBatchModify;
import com.zsgj.itil.config.entity.CIBatchModifyPlan;
import com.zsgj.itil.config.entity.CIBatchModifyShip;
import com.zsgj.itil.config.entity.CIRelationShip;
import com.zsgj.itil.config.entity.CIRelationShipDisplay;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ConfigItemNecessaryRel;
import com.zsgj.itil.config.entity.ConfigItemStatus;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.config.extci.entity.DeliveryTeam;
import com.zsgj.itil.config.extci.entity.ServiceEngineer;
import com.zsgj.itil.service.entity.ServiceItem;

public class ConfigItemDaoImpl extends BaseDao implements ConfigItemDao {

	@SuppressWarnings("unchecked")
	public Page selectConfigItem(String modifyId,String name, String cisn,
			Long configItemTypeId, int pageNo, int pageSize) {
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		//需要包含的
		List<Long> idIn=new ArrayList<Long>();
		//不能包含
		List<Long> idNotIn=new ArrayList<Long>();
		Criteria c = super.getCriteria(ConfigItem.class);
		if(modifyId!=null){
		    c.createAlias("configItemStatus", "configItemStatus");
			c.add(Restrictions.not(Restrictions.in("configItemStatus.enname", status)));
			//本次变更申请中新建的非孤立状态的
			idIn.addAll(createCriteria(CIBatchModifyPlan.class)
					    .createAlias("newConfigItem", "newCI")
					    .createAlias("newCI.configItemStatus", "newCIStatus")
						.add(Restrictions.eq("batchModify.id", Long.valueOf(modifyId)))
						.add(Restrictions.and(Restrictions.isNotNull("newConfigItem"),Restrictions.isNull("oldConfigItem")))
						.add(Restrictions.not(Restrictions.in("newCIStatus.enname", status)))
						.setProjection(Projections.property("newConfigItem.id"))
						.list());
			//本次申请中将孤立的配置项变为非孤立的
			idIn.addAll(createCriteria(CIBatchModifyPlan.class)
					   .createAlias("newConfigItem", "newCI")
					   .createAlias("newCI.configItemStatus", "newCIStatus")
					   .createAlias("oldConfigItem", "oldCI")
					   .createAlias("oldCI.configItemStatus", "oldCIStatus")
					   .add(Restrictions.eq("batchModify.id", Long.valueOf(modifyId)))
					   .add(Restrictions.and(Restrictions.isNotNull("newConfigItem"),Restrictions.isNotNull("oldConfigItem")))
					   .add(Restrictions.not(Restrictions.in("newCIStatus.enname", status)))
					   .add(Restrictions.in("oldCIStatus.enname", status))
					   .setProjection(Projections.property("newConfigItem.id"))
					   .list());
			 //本次申请中将非孤立变为孤立的
			idNotIn.addAll(createCriteria(CIBatchModifyPlan.class)
					   .createAlias("newConfigItem", "newCI")
					   .createAlias("newCI.configItemStatus", "newCIStatus")
					   .createAlias("oldConfigItem", "oldCI")
					   .createAlias("oldCI.configItemStatus", "oldCIStatus")
					   .add(Restrictions.eq("batchModify.id", Long.valueOf(modifyId)))
					   .add(Restrictions.and(Restrictions.isNotNull("newConfigItem"),Restrictions.isNotNull("oldConfigItem")))
					   .add(Restrictions.in("newCIStatus.enname", status))
					   .add(Restrictions.not(Restrictions.in("oldCIStatus.enname", status)))
					   .setProjection(Projections.property("oldConfigItem.id"))
					.list());
			
		}
		if(!idNotIn.isEmpty()){
			c.add(Restrictions.not(Restrictions.in("id", idNotIn)));
		}
		if(!idIn.isEmpty()){
			c.add(Restrictions.or(Restrictions.eq("status", ConfigItem.VALID_STATUS),Restrictions.in("id", idIn)));
		}else{
			c.add(Restrictions.eq("status", ConfigItem.VALID_STATUS));
		}
		if (name!=null&&name.trim().length()!=0) {
			c.add(Restrictions.like("name", name.trim(), MatchMode.ANYWHERE));
		}
		if (cisn!=null&&cisn.trim().length()!=0) {
			c.add(Restrictions.like("cisn", cisn.trim(), MatchMode.ANYWHERE));
		}
		if (configItemTypeId!=null) {
			c.add(Restrictions.eq("configItemType.id", configItemTypeId));
		}
		c.addOrder(Order.asc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public Page selectServiceItem(String name, String code,
			Long serviceItemTypeId, int pageNo, int pageSize) {
		Criteria c = super.getCriteria(ServiceItem.class);
		c.add(Restrictions.eq("deleteFlag", ServiceItem.DELETE_FALSE));
		if (name!=null&&name.trim().length()!=0) {
			c.add(Restrictions.like("name", name.trim(), MatchMode.ANYWHERE));
		}
		if (code!=null&&code.trim().length()!=0) {
			c.add(Restrictions.like("serviceItemCode", code.trim(), MatchMode.ANYWHERE));
		}
		if (serviceItemTypeId!=null) {
			c.add(Restrictions.eq("serviceItemType.id", serviceItemTypeId));
		}
		c.addOrder(Order.asc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public List<CIRelationShip> selectDirectChildRel(String itemCode) {
		Criteria rels=createCriteria(CIRelationShip.class)
				.add(Restrictions.eq("status",CIRelationShip.VALID_STATUS))
				.add(Restrictions.or(Restrictions.isNotNull("childConfigItemCode"),Restrictions.isNotNull("childServiceItemCode")));
			rels.add(Restrictions.or(
										Restrictions.eq("parentConfigItemCode", itemCode), 
										Restrictions.eq("parentServiceItemCode", itemCode)));
		return rels.list();
	}

	public Set<String> selectAllChildCode(String itemCode,List<Long> relId,List<Long> ignoreRid) {
		Set<String> codes=new HashSet<String>();
		codes.add(itemCode);
		Criteria c=createCriteria(CIRelationShip.class);
		if(ignoreRid!=null&&!ignoreRid.isEmpty()){
			c.add(Restrictions.not(Restrictions.in("id", ignoreRid)));
		}
		if(relId!=null&&!relId.isEmpty()){
			c.add(Restrictions.or(Restrictions.eq("status",CIRelationShip.VALID_STATUS), Restrictions.in("id",relId)));
		}else{
			c.add(Restrictions.eq("status",CIRelationShip.VALID_STATUS));
		}
		c.add(Restrictions.or(Restrictions.eq("parentConfigItemCode",itemCode), Restrictions.eq("parentServiceItemCode",itemCode)));
		List<CIRelationShip> rels=c.list();
		for(CIRelationShip rel:rels){
			ignoreRid.add(rel.getId());
			if(rel.getChildConfigItemCode()!=null)
				codes.addAll(selectAllChildCode(rel.getChildConfigItemCode(),relId,ignoreRid));
			else if(rel.getChildServiceItemCode()!=null)
				codes.addAll(selectAllChildCode(rel.getChildServiceItemCode(),relId,ignoreRid)); 
		}
		return codes;
	}

	public Set<String> selectAllParentCode(String itemCode,List<Long> relsId,List<Long> ignoreRid) {
		Set<String> codes=new HashSet<String>();
		codes.add(itemCode);
		Criteria c=createCriteria(CIRelationShip.class);
		if(ignoreRid!=null&&!ignoreRid.isEmpty()){
			c.add(Restrictions.not(Restrictions.in("id", ignoreRid)));
		}
		if(relsId!=null&&!relsId.isEmpty()){
			c.add(Restrictions.or(Restrictions.eq("status",CIRelationShip.VALID_STATUS), Restrictions.in("id",relsId)));
		}else{
			c.add(Restrictions.eq("status",CIRelationShip.VALID_STATUS));
		}
		c.add(Restrictions.or(Restrictions.eq("childConfigItemCode",itemCode), Restrictions.eq("childServiceItemCode",itemCode)));
		List<CIRelationShip> rels=c.list();
		for(CIRelationShip rel:rels){
			ignoreRid.add(rel.getId());
			if(rel.getParentConfigItemCode()!=null)
				codes.addAll(selectAllParentCode(rel.getParentConfigItemCode(),relsId,ignoreRid));
			else if(rel.getParentServiceItemCode()!=null)
				codes.addAll(selectAllParentCode(rel.getParentServiceItemCode(),relsId,ignoreRid)); 
		}
		return codes;
	}
	public Page selectRelList(Map<String,String> basicMap, Map<String,String> advancedMap, int start, int pageSize) {
		StringBuilder sb=new StringBuilder();
		sb.append("select rel.id as id , ");
		sb.append("      case ");
		sb.append("      when rel.parentConfigItemCode is not null ");
		sb.append("		 then 'ci' ");
		sb.append("		 when rel.parentServiceItemCode is not null ");
		sb.append("		 then 'si' ");
		sb.append("		 end ");
		sb.append("		 as parentItem , ");
		sb.append("		 case ");
		sb.append("		 when rel.parentConfigItemCode is not null ");
		sb.append("		 then pciType.name ");
		sb.append("		 when rel.parentServiceItemCode is not null ");
		sb.append("		 then psiType.name ");
		sb.append("		 end ");
		sb.append("		 as parentItemType , ");
		sb.append("		 case ");
		sb.append("		 when rel.parentConfigItemCode is not null ");
		sb.append("		 then pci.name ");
		sb.append("		 when rel.parentServiceItemCode is not null ");
		sb.append("		 then psi.name ");
		sb.append("		 end ");
		sb.append("		 as parentItemName , ");
		sb.append("		 case ");
		sb.append("		 when rel.parentConfigItemCode is not null ");
		sb.append("		 then rel.parentConfigItemCode ");
		sb.append("		 when rel.parentServiceItemCode is not null ");
		sb.append("		 then rel.parentServiceItemCode ");
		sb.append("		 end ");
		sb.append("		 as parentItemCode , ");
		sb.append("		 case ");
		sb.append("		 when rel.childConfigItemCode is not null ");
		sb.append("		 then 'ci' ");
		sb.append("		 when rel.childServiceItemCode is not null ");
		sb.append("		 then 'si' ");
		sb.append("		 end "); 
		sb.append("		 as childItem , ");
		sb.append("		 case ");
		sb.append("		 when rel.childConfigItemCode is not null ");
		sb.append("		 then cciType.name ");
		sb.append("		 when rel.childServiceItemCode is not null ");
		sb.append("		 then csiType.name ");
		sb.append("		 end ");
		sb.append("		 as childItemType , ");
		sb.append("		 case ");
		sb.append("		 when rel.childConfigItemCode is not null ");
		sb.append("		 then cci.name ");
		sb.append("		 when rel.childServiceItemCode is not null ");
		sb.append("		 then csi.name ");
		sb.append("		 end ");
		sb.append("		 as childItemName , ");
		sb.append("		 case ");
		sb.append("		 when rel.childConfigItemCode is not null ");
		sb.append("		 then rel.childConfigItemCode ");
		sb.append("		 when rel.childServiceItemCode is not null ");
		sb.append("		 then rel.childServiceItemCode ");
		sb.append("		 end ");
		sb.append("		 as childItemCode , ");
		sb.append("      relType.name as relationShipType , ");
		sb.append("      relGrade.name as relationShipGrade , ");
		sb.append("		 rel.attachQuotiety as attachQuotiety , ");
		sb.append("      rel.atechnoInfo as atechnoInfo , ");
		sb.append("      rel.btechnoInfo as btechnoInfo , ");
		sb.append("      rel.otherInfo as otherInfo ");
		
		StringBuilder from=new StringBuilder();
		
		from.append("from  CIRelationShip rel ");
		from.append("	   left outer join ");
		from.append("      ConfigItem pci  ");
		from.append("      on ");
		from.append("      rel.parentConfigItemCode=pci.cisn ");
		from.append("      and ");
		from.append("      pci.status = ? ");
		from.append("      left outer join ");
		from.append("      ConfigItemType pciType ");
		from.append("      on ");
		from.append("      rel.parentConfigItemType=pciType.id ");
		from.append("      left outer join ");
		from.append("      ServiceItem psi ");
		from.append("      on ");
		from.append("      rel.parentServiceItemCode=psi.serviceItemCode ");
		from.append("      and ");
		from.append("      psi.deleteFlag = ? ");
		from.append("      left outer join ");
		from.append("      ServiceItemType psiType ");
		from.append("      on ");
		from.append("      rel.parentServiceItemType=psiType.id ");
		from.append("      left outer join ");
		from.append("      ConfigItem cci ");
		from.append("      on ");
		from.append("      rel.childConfigItemCode=cci.cisn ");
		from.append("      and ");
		from.append("      cci.status = ? ");
		from.append("      left outer join ");
		from.append("      ConfigItemType cciType ");
		from.append("      on ");
		from.append("      rel.childConfigItemType=cciType.id ");
		from.append("      left outer join ");
		from.append("      ServiceItem csi ");
		from.append("      on ");
		from.append("      rel.childServiceItemCode=csi.serviceItemCode ");
		from.append("      and ");
		from.append("      csi.deleteFlag = ? ");
		from.append("      left outer join ");
		from.append("      ServiceItemType csiType ");
		from.append("      on ");
		from.append("      rel.childServiceItemType=csiType.id ");
		from.append("      left outer join ");
		from.append("      CIRelationShipType relType "); 
		from.append("      on ");
		from.append("      rel.relationShipType=relType.id ");
		from.append("      left outer join ");
		from.append("      CIRelationShipGrade relGrade ");
		from.append("      on ");
		from.append("      rel.relationShipGrade=relGrade.id ");
		from.append("where rel.status = ? ");
	    List<Object> values=new ArrayList<Object>();
	    List<Type> types=new ArrayList<Type>();
	    values.add(ConfigItem.VALID_STATUS);
	    types.add(Hibernate.INTEGER);
	    values.add(ServiceItem.DELETE_FALSE);
	    types.add(Hibernate.INTEGER);
	    values.add(ConfigItem.VALID_STATUS);
	    types.add(Hibernate.INTEGER);
	    values.add(ServiceItem.DELETE_FALSE);
	    types.add(Hibernate.INTEGER);
	    values.add(CIRelationShip.VALID_STATUS);
	    types.add(Hibernate.INTEGER);
	    if(!advancedMap.isEmpty()){
	    	String parentItem=advancedMap.get("parentItem");
	    	String parentItemType=advancedMap.get("parentItemType");
	    	String parentItemName=advancedMap.get("parentItemName");
	    	String parentItemCode=advancedMap.get("parentItemCode");
	    	String childItem=advancedMap.get("childItem");
	    	String childItemType=advancedMap.get("childItemType");
	    	String childItemName=advancedMap.get("childItemName");
	    	String childItemCode=advancedMap.get("childItemCode");
	    	if(parentItem!=null&&parentItem.equals("ci")){
	    		from.append(" and rel.parentConfigItemCode is not null ");
	    		if(parentItemType!=null){
	    			from.append(" and rel.parentConfigItemType = ? ");
		    		values.add(Long.valueOf(parentItemType));
		    		types.add(Hibernate.LONG);
	    		}
	    	}else if(parentItem!=null&&parentItem.equals("si")){
	    		from.append(" and rel.parentServiceItemCode is not null ");
	    		if(parentItemType!=null){
	    			from.append(" and rel.parentServiceItemType = ? ");
		    		values.add(Long.valueOf(parentItemType));
		    		types.add(Hibernate.LONG);
	    		}
	    	}
	    	if(childItem!=null&&childItem.equals("ci")){
	    		from.append(" and rel.childConfigItemCode is not null ");
	    		if(childItemType!=null){
	    			from.append(" and rel.childConfigItemType = ? ");
	    			values.add(Long.valueOf(childItemType));
	    			types.add(Hibernate.LONG);
	    		}
	    	}else if(childItem!=null&&childItem.equals("si")){
	    		from.append(" and rel.childServiceItemCode is not null ");
	    		if(childItemType!=null){
	    			from.append(" and rel.childServiceItemType = ? ");
	    			values.add(Long.valueOf(childItemType));
	    			types.add(Hibernate.LONG);
	    		}
	    	}
	    	if(parentItemName!=null){
	    		if(parentItem!=null&&parentItem.equals("ci")){
	    			from.append(" and pci.name like ? ");
	    			values.add("%"+parentItemName+"%");
			    	types.add(Hibernate.STRING);
	    		}else if(parentItem!=null&&parentItem.equals("si")){
	    			from.append(" and psi.name like ? ");
	    			values.add("%"+parentItemName+"%");
			    	types.add(Hibernate.STRING);
	    		}else{
	    			from.append(" and ( pci.name like ? ");
	    			from.append(" or psi.name like ? ) ");
	    			values.add("%"+parentItemName+"%");
			    	types.add(Hibernate.STRING);
	    			values.add("%"+parentItemName+"%");
			    	types.add(Hibernate.STRING);
	    		}
	    	}
	    	if(childItemName!=null){
	    		if(childItem!=null&&childItem.equals("ci")){
	    			from.append(" and cci.name like ? ");
	    			values.add("%"+childItemName+"%");
	    			types.add(Hibernate.STRING);
	    		}else if(childItem!=null&&childItem.equals("si")){
	    			from.append(" and csi.name like ? ");
	    			values.add("%"+childItemName+"%");
	    			types.add(Hibernate.STRING);
	    		}else{
	    			from.append(" and ( cci.name like ? ");
	    			from.append(" or csi.name like ? ) ");
	    			values.add("%"+childItemName+"%");
	    			types.add(Hibernate.STRING);
	    			values.add("%"+childItemName+"%");
	    			types.add(Hibernate.STRING);
	    		}
	    	}
	    	if(parentItemCode!=null){
	    		if(parentItem!=null&&parentItem.equals("ci")){
	    			from.append(" and rel.parentConfigItemCode like ? ");
			    	values.add("%"+parentItemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}else if(parentItem!=null&&parentItem.equals("si")){
	    			from.append(" and rel.parentServiceItemCode like ? ");
			    	values.add("%"+parentItemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}else{
	    			from.append("  and ( rel.parentConfigItemCode like ? ");
	    			from.append("  or rel.parentServiceItemCode like ? ) ");
	    			values.add("%"+parentItemCode+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+parentItemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}
	    	}
	    	if(childItemCode!=null){
	    		if(childItem!=null&&childItem.equals("ci")){
	    			from.append(" and rel.childConfigItemCode like ? ");
			    	values.add("%"+childItemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}else if(childItem!=null&&childItem.equals("si")){
			    	from.append(" and rel.childServiceItemCode like ? ) ");
			    	values.add("%"+childItemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}else{
	    			from.append("  and ( rel.childConfigItemCode like ? ");
			    	from.append("  or rel.childServiceItemCode like ? ) ");
	    			values.add("%"+childItemCode+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+childItemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}
	    	}
	    }else if(!basicMap.isEmpty()){
	    	String item=basicMap.get("item");
	    	String itemType=basicMap.get("itemType");
	    	String itemName=basicMap.get("itemName");
	    	String itemCode=basicMap.get("itemCode");
	    	if(item!=null&&item.equals("ci")){
	    		from.append(" and ( rel.parentConfigItemCode is not null ");
	    		from.append(" or rel.childConfigItemCode is not null ) ");
	    		if(itemType!=null){
		    		from.append(" and ( rel.parentConfigItemType = ? ");
		    		from.append(" or rel.childConfigItemType = ? ) ");
		    		values.add(Long.valueOf(itemType));
		    		types.add(Hibernate.LONG);
		    		values.add(Long.valueOf(itemType));
		    		types.add(Hibernate.LONG);
	    		}
	    	}else if(item!=null&&item.equals("si")){
	    		from.append(" and ( rel.parentServiceItemCode is not null ");
	    		from.append(" or rel.childServiceItemCode is not null ) ");
	    		if(itemType!=null){
		    		from.append(" and ( rel.parentServiceItemType = ? ");
		    		from.append(" or rel.childServiceItemType = ? ) ");
		    		values.add(Long.valueOf(itemType));
		    		types.add(Hibernate.LONG);
		    		values.add(Long.valueOf(itemType));
		    		types.add(Hibernate.LONG);
	    		}
	    	}
	    	if(itemName!=null){
	    		if(item!=null&&item.equals("ci")){
	    			from.append(" and ( pci.name like ? ");
	    			from.append(" or cci.name like ? ) ");
			    	values.add("%"+itemName+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemName+"%");
			    	types.add(Hibernate.STRING);
	    		}else if(item!=null&&item.equals("si")){
	    			from.append(" and ( psi.name like ? ");
		    		from.append(" or csi.name like ? ) ");
		    		values.add("%"+itemName+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemName+"%");
			    	types.add(Hibernate.STRING);
	    		}else{
	    			from.append(" and ( pci.name like ? ");
	    			from.append(" or cci.name like ? ");
	    			from.append(" or psi.name like ? ");
		    		from.append(" or csi.name like ? ) ");
		    		values.add("%"+itemName+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemName+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemName+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemName+"%");
			    	types.add(Hibernate.STRING);
	    		}
	    	}
	    	if(itemCode!=null){
	    		if(item!=null&&item.equals("ci")){
	    			from.append("  and ( rel.parentConfigItemCode like ? ");
	    			from.append("  or rel.childConfigItemCode like ? ) ");
	    			values.add("%"+itemCode+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}else if(item!=null&&item.equals("si")){
	    			from.append("  and ( rel.parentServiceItemCode like ? ");
			    	from.append("  or rel.childServiceItemCode like ? ) ");
			    	values.add("%"+itemCode+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}else{
	    			from.append("  and ( rel.parentConfigItemCode like ? ");
	    			from.append("  or rel.childConfigItemCode like ? ");
	    			from.append("  or rel.parentServiceItemCode like ? ");
			    	from.append("  or rel.childServiceItemCode like ? ) ");
			    	values.add("%"+itemCode+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemCode+"%");
			    	types.add(Hibernate.STRING);
	    			values.add("%"+itemCode+"%");
			    	types.add(Hibernate.STRING);
			    	values.add("%"+itemCode+"%");
			    	types.add(Hibernate.STRING);
	    		}
	    	}
	    }
	    Type[] typesArray=new Type[types.size()];
	    List<CIRelationShipDisplay> rels=getSession().createSQLQuery(sb.append(from).append(" order by rel.id desc ").toString())
		.addScalar("id", Hibernate.LONG)
		.addScalar("parentItem", Hibernate.STRING)
		.addScalar("parentItemType", Hibernate.STRING)
		.addScalar("parentItemName", Hibernate.STRING)
		.addScalar("parentItemCode", Hibernate.STRING)
		.addScalar("childItem", Hibernate.STRING)
		.addScalar("childItemType", Hibernate.STRING)
		.addScalar("childItemName", Hibernate.STRING)
		.addScalar("childItemCode", Hibernate.STRING)
		.addScalar("relationShipType", Hibernate.STRING)
		.addScalar("relationShipGrade", Hibernate.STRING)
		.addScalar("attachQuotiety", Hibernate.DOUBLE)
		.addScalar("atechnoInfo", Hibernate.STRING)
		.addScalar("btechnoInfo", Hibernate.STRING)
		.addScalar("otherInfo", Hibernate.STRING)
		.setResultTransformer(Transformers.aliasToBean(CIRelationShipDisplay.class))
		.setFirstResult(start)
		.setMaxResults(pageSize)
		.setParameters(values.toArray(), types.toArray(typesArray))
		.list();
	    StringBuilder countString=new StringBuilder();
	    countString.append("select count(*) ");
	    countString.append(from);
		Integer count=(Integer) getSession().createSQLQuery(countString.toString())
		.setParameters(values.toArray(), types.toArray(typesArray))
		.uniqueResult();
		return new Page(start,count,pageSize,rels);
	}
	public Page selectRelList(String itemCode,List<String> ignoreCode, int pageNo, int pageSize) {
		Criteria c =createCriteria(CIRelationShip.class)
		.add(Restrictions.eq("status", CIRelationShip.VALID_STATUS))
		//和itemCode相关的关系
		.add(Restrictions.or(
				//父配置项编号等于itemCode
				Restrictions.and(
						Restrictions.isNotNull("parentConfigItemCode"), 
						Restrictions.eq("parentConfigItemCode", itemCode)),
				//子配置项编号等于itemCode
				Restrictions.and(
						Restrictions.isNotNull("childConfigItemCode"),
						Restrictions.eq("childConfigItemCode", itemCode))
				));
		if(ignoreCode!=null&&!ignoreCode.isEmpty()){
			c.add(Restrictions.or(
					//忽略父配置项编号在ignoreCode中存在的关系
					Restrictions.and(
							Restrictions.isNotNull("parentConfigItemCode"),
					        Restrictions.not(Restrictions.in("parentConfigItemCode", ignoreCode))),
					//忽略父服务项编号在ignoreCode中存在的关系
					Restrictions.and(
							Restrictions.isNotNull("parentServiceItemCode"),
					        Restrictions.not(Restrictions.in("parentServiceItemCode", ignoreCode)))));
			c.add(Restrictions.or(
					//忽略子配置项编号在ignoreCode中存在的关系
					Restrictions.and(
							Restrictions.isNotNull("childConfigItemCode"),
							Restrictions.not(Restrictions.in("childConfigItemCode", ignoreCode))),
					//忽略子服务项编号在ignoreCode中存在的关系
					Restrictions.and(
									Restrictions.isNotNull("childServiceItemCode"),
									Restrictions.not(Restrictions.in("childServiceItemCode", ignoreCode)))));
		}
		c.addOrder(Order.asc("id"));
		return this.pagedQuery(c, pageNo, pageSize);
	}
	public List<CIRelationShip> selectRelList(String itemCode,List<String> ignoreCode) {
		Criteria c =createCriteria(CIRelationShip.class)
		.add(Restrictions.eq("status", CIRelationShip.VALID_STATUS))
		//和itemCode相关的关系
		.add(Restrictions.or(
				Restrictions.eq("parentConfigItemCode", itemCode),
				Restrictions.eq("childConfigItemCode", itemCode)));
		if(ignoreCode!=null&&!ignoreCode.isEmpty()){
			//忽略父配置项编号在ignoreCode中存在的关系
			c.add(Restrictions.or(
					Restrictions.isNull("parentConfigItemCode"), 
					Restrictions.not(Restrictions.in("parentConfigItemCode", ignoreCode))));
			//忽略父服务项编号在ignoreCode中存在的关系
			c.add(Restrictions.or(
					Restrictions.isNull("parentServiceItemCode"),
					Restrictions.not(Restrictions.in("parentServiceItemCode", ignoreCode))));
			//忽略子配置项编号在ignoreCode中存在的关系
			c.add(Restrictions.or(
					Restrictions.isNull("childConfigItemCode"),
					Restrictions.not(Restrictions.in("childConfigItemCode", ignoreCode))));
			//忽略子服务项编号在ignoreCode中存在的关系
			c.add(Restrictions.or(
					Restrictions.isNull("childServiceItemCode"),
					Restrictions.not(Restrictions.in("childServiceItemCode", ignoreCode))));
		}
		return c.list();
	}

	public Page selectBatchModifyInfo(Long id, String type, int pageNo, int pageSize) {
			Criteria  criteria = createCriteria(CIBatchModifyShip.class);
			criteria.setProjection(Projections.property("ciBatchModify"));
			if("problem".equals(type)){//问题
				criteria.add(Restrictions.eq("problem.id", id));
			}else{//需求
				criteria.add(Restrictions.eq("specialRequirement.id", id));
			}
			criteria.addOrder(Order.desc("id"));
			Page page =pagedQuery(criteria, pageNo, pageSize);
			return page;
	}

	public Page selectBatchModifyCIList(Long modifyId, int pageNo, int pageSize) {
		Criteria c=createCriteria(CIBatchModifyPlan.class)
		.setFetchMode("officer", FetchMode.JOIN)
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.or(
				//新建或变更
				Restrictions.isNotNull("newConfigItem"), 
				//删除或维护关系
				Restrictions.or(
						Restrictions.isNotNull("oldConfigItem"),
						Restrictions.isNotNull("maintenanceCIRel"))))
		.add(Restrictions.and(
				Restrictions.isNull("newCIRelationShip"), 
				Restrictions.isNull("oldCIRelationShip")))
		.addOrder(Order.desc("id"));
		return pagedQuery(c, pageNo, pageSize);
	}
	public Page selectBatchModifyRelList(Long modifyId, int pageNo, int pageSize) {
		Criteria c=createCriteria(CIBatchModifyPlan.class)
		.setFetchMode("newCIRelationShip", FetchMode.JOIN)
		.setFetchMode("officer", FetchMode.JOIN)
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.or(
				//新建或变更
				Restrictions.isNotNull("newCIRelationShip"), 
				//删除
				Restrictions.isNotNull("oldCIRelationShip")))
		.addOrder(Order.desc("id"));
		return pagedQuery(c, pageNo, pageSize);
	}

	public CIBatchModifyPlan selectCIBatchModifyPlan(Long configItemId,
			Long modifyId) {
		return (CIBatchModifyPlan) createCriteria(CIBatchModifyPlan.class)
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.eq("newConfigItem.id", configItemId))
		.uniqueResult();
	}


	public List<CIRelationShip> selectAllRelationShip(List<String> itemCode){
		return createCriteria(CIRelationShip.class)
			.add(Restrictions.eq("status", CIRelationShip.VALID_STATUS))
			.add(Restrictions.or(Restrictions.or(
											Restrictions.in("parentConfigItemCode", itemCode), 
											Restrictions.in("parentServiceItemCode", itemCode)),
									Restrictions.or(
											Restrictions.in("childConfigItemCode", itemCode), 
											Restrictions.in("childServiceItemCode", itemCode))))
			.list();
	}

	public void saveOrUpdateObjects(Collection entity) {
		getHibernateTemplate().saveOrUpdateAll(entity);
	}
	public Page selectBatchModify(String modifyNo, String name, Date applyDateStart,Date applyDateEnd, Integer status, int start, int size) {
		Criteria count=createCriteria(CIBatchModify.class)
		.add(Restrictions.eq("applyUser", UserContext.getUserInfo()));
		if(modifyNo!=null&&modifyNo.trim().length()!=0){
			count.add(Restrictions.like("modifyNo",modifyNo,MatchMode.ANYWHERE));
		}
		if(name!=null&&name.trim().length()!=0){
			count.add(Restrictions.like("name",name,MatchMode.ANYWHERE));
		}
		if(applyDateStart!=null){
			count.add(Restrictions.ge("applyDate", applyDateStart));
		}
		if(applyDateEnd!=null){
			count.add(Restrictions.le("applyDate", applyDateEnd));
		}
		if(status!=null){
			count.add(Restrictions.eq("status", status));
		}
		Long totalSize=((Integer)count.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		Criteria c=createCriteria(CIBatchModify.class)
					.add(Restrictions.eq("applyUser", UserContext.getUserInfo()))
					.createAlias("applyUser", "au")
					.setFetchMode("applyUser", FetchMode.JOIN)
					.setFetchMode("au.department", FetchMode.JOIN)
					.setFirstResult(start)
					.setMaxResults(size);
		if(modifyNo!=null&&modifyNo.trim().length()!=0){
			c.add(Restrictions.like("modifyNo",modifyNo,MatchMode.ANYWHERE));
		}
		if(name!=null&&name.trim().length()!=0){
			c.add(Restrictions.like("name",name,MatchMode.ANYWHERE));
		}
		if(applyDateStart!=null){
			c.add(Restrictions.ge("applyDate", applyDateStart));
		}
		if(applyDateEnd!=null){
			c.add(Restrictions.le("applyDate", applyDateEnd));
		}
		if(status!=null){
			c.add(Restrictions.eq("status", status));
		}
		c.addOrder(Order.desc("id"));
		List<CIBatchModify> batchModifys=c.list();
		return new Page(start,totalSize,size,batchModifys);
	}
	
	public <T> List<T> selectObjects(Class<T> entity,String propertyName,Object[] propertyValues,String fetchProperty){
		Criteria c=createCriteria(entity);
		if(propertyName!=null&&propertyName.trim().length()!=0&&propertyValues!=null&&propertyValues.length!=0){
			String[] names=propertyName.split("\\.");
			String previousAlias="";//上一个别名
			for(int i=0;i<names.length;i++){
				if(i!=names.length-1){//属性名不是最后一级
					c.createAlias((previousAlias.equals("")?names[i]:previousAlias+"."+names[i]), previousAlias+"_"+names[i]);
					previousAlias=previousAlias+"_"+names[i];//当前别名作为上一个别名
				}else{
					String name=(previousAlias.equals("")?names[i]:previousAlias+"."+names[i]);
					if(propertyValues.length>1){
						c.add(Restrictions.in(name, propertyValues));
					}
					if(propertyValues.length==1){
						c.add(Restrictions.eq(name, propertyValues[0]));
					}
				}
			}
		}
		if(fetchProperty!=null){
			c.setFetchMode(fetchProperty, FetchMode.JOIN);
		}
		return c.list();
	}
	
	public <T> List<T> selectObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys,String orderProperty,boolean isAsc){
		Criteria c=createCriteria(entity);
		List<String> allAlias=new ArrayList<String>();//所有别名
		if(propertysNameAndValue!=null){
			for(String key:propertysNameAndValue.keySet()){
				String[] names=key.split("\\.");
				String previousAlias="";//上一个别名
				for(int i=0;i<names.length;i++){
					if(i!=names.length-1){//属性名不是最后一级
						if(!allAlias.contains(previousAlias+"_"+names[i])){//当前别名之前未使用
							c.createAlias((previousAlias.equals("")?names[i]:previousAlias+"."+names[i]), previousAlias+"_"+names[i]);
							allAlias.add(previousAlias+"_"+names[i]);
						}
						previousAlias=previousAlias+"_"+names[i];//当前别名作为上一个别名
					}else{
						String name=(previousAlias.equals("")?names[i]:previousAlias+"."+names[i]);
						Object value=propertysNameAndValue.get(key);
						if(value instanceof Collection){
							c.add(Restrictions.in(name, (Collection)value));
						}else if(value.getClass().isArray()){
							c.add(Restrictions.in(name, (Object[])value));
						}else{
							c.add(Restrictions.eq(name, value));
						}
					}
				}
				
			}
		}
		if(fetchPropertys!=null){
			for(String property:fetchPropertys){
				c.setFetchMode(property,FetchMode.JOIN);
			}
		}
		if(orderProperty!=null&&orderProperty.trim().length()!=0){
			if(isAsc){
				c.addOrder(Order.asc(orderProperty));
			}else{
				c.addOrder(Order.desc(orderProperty));
			}
		}
		return c.list();
	}
	public <T> List<T> selectObjects(Class<T> entity,Map<String,Object> propertysNameAndValue,List<String> fetchPropertys){
		Criteria c=createCriteria(entity);
		List<String> allAlias=new ArrayList<String>();//所有别名
		if(propertysNameAndValue!=null){
			for(String key:propertysNameAndValue.keySet()){
				String[] names=key.split("\\.");
				String previousAlias="";//上一个别名
				for(int i=0;i<names.length;i++){
					if(i!=names.length-1){//属性名不是最后一级
						if(!allAlias.contains(previousAlias+"_"+names[i])){//当前别名之前未使用
							c.createAlias((previousAlias.equals("")?names[i]:previousAlias+"."+names[i]), previousAlias+"_"+names[i]);
							allAlias.add(previousAlias+"_"+names[i]);
						}
						previousAlias=previousAlias+"_"+names[i];//当前别名作为上一个别名
					}else{
						String name=(previousAlias.equals("")?names[i]:previousAlias+"."+names[i]);
						Object value=propertysNameAndValue.get(key);
						if(value instanceof Collection){
							c.add(Restrictions.in(name, (Collection)value));
						}else if(value.getClass().isArray()){
							c.add(Restrictions.in(name, (Object[])value));
						}else{
							c.add(Restrictions.eq(name, value));
						}
					}
				}
				
			}
		}
		if(fetchPropertys!=null){
			for(String property:fetchPropertys){
				c.setFetchMode(property,FetchMode.JOIN);
			}
		}
		return c.list();
	}
	
	public void deleteObjects(Collection entities){
		getHibernateTemplate().deleteAll(entities);
	}
	
	public Set<String> selectDeliveryTeamTechnicalLeader(){
		Set<String> user=new HashSet<String>();
		if(UserContext.getUserInfo()!=null){
			List<Object> values=new ArrayList<Object>();
			StringBuilder engineerHql=new StringBuilder();
			engineerHql.append(" select deliveryTeam.technicalLeader.userName from ConfigItemExtendInfo engineerExtendInfo ");
			engineerHql.append(" join engineerExtendInfo.configItem engineerCI with engineerCI.status = ? ");
			engineerHql.append(" join engineerCI.configItemStatus engineerStatus with engineerStatus.enname not in (?,?,?,?) ");
			engineerHql.append(" join engineerCI.configItemType engineerType with engineerType.className = ? , ");
			engineerHql.append(" ServiceEngineer engineer , ");
			engineerHql.append(" CIRelationShip rel , ");
			engineerHql.append(" ConfigItemExtendInfo deliveryTeamExtendInfo ");
			engineerHql.append(" join deliveryTeamExtendInfo.configItem deliveryTeamCI with deliveryTeamCI.status = ? ");
			engineerHql.append(" join deliveryTeamCI.configItemStatus deliveryTeamStatus with deliveryTeamStatus.enname not in (?,?,?,?) ");
			engineerHql.append(" join deliveryTeamCI.configItemType deliveryTeamType with deliveryTeamType.className = ? , ");
			engineerHql.append(" DeliveryTeam deliveryTeam ");
			engineerHql.append(" where engineer.id=engineerExtendInfo.extendDataId ");
			engineerHql.append(" and deliveryTeam.id=deliveryTeamExtendInfo.extendDataId ");
			engineerHql.append(" and engineer.userInfo.id = ? ");
			engineerHql.append(" and rel.status = ? ");
			engineerHql.append(" and rel.childConfigItemCode=engineer.cisn ");
			engineerHql.append(" and rel.parentConfigItemCode=deliveryTeam.cisn ");
			values.add(ConfigItem.VALID_STATUS);
			values.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
			values.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
			values.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
			values.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
			values.add(ServiceEngineer.class.getName());
			values.add(ConfigItem.VALID_STATUS);
			values.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
			values.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
			values.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
			values.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
			values.add(DeliveryTeam.class.getName());
			values.add(UserContext.getUserInfo().getId());
			values.add(CIRelationShip.VALID_STATUS);
			user.addAll(getHibernateTemplate().find(engineerHql.toString(),values.toArray()));
		}
		return user;
	}

	public Page selectValidConfigItemExtendInfo(String entity,Map map,List<String> fuzzyQuery, int pageNo, int pageSize){
		List propertys=new ArrayList();
		Object extendId=map.get("id");
		StringBuilder hql=new StringBuilder();
		hql.append(" select extend from  "+entity+" extend ");
		if(extendId==null){
			hql.append(" , ConfigItemExtendInfo extendInfo ");
			hql.append(" join extendInfo.configItem ci with ci.status = ? ");
			hql.append(" join ci.configItemStatus status with status.enname not in (?,?,?,?) ");
			hql.append(" join ci.configItemType type with type.className = ? ");
			hql.append(" where extend.id=extendInfo.extendDataId ");
			propertys.add(ConfigItem.VALID_STATUS);
			propertys.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
			propertys.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
			propertys.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
			propertys.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
			propertys.add(entity);
			Set<String> keys=map.keySet();
			for(String key:keys){
				if(map.get(key)!=null&&map.get(key).toString().length()!=0){
					if(fuzzyQuery.contains(key)){
						hql.append(" and extend."+key.trim()+" like ? ");
						propertys.add("%"+map.get(key).toString()+"%");
					}else{
						hql.append(" and extend."+key.trim()+" = ? ");
						propertys.add(map.get(key));
					}
				}
			}
		}else{
			hql.append(" where extend.id = ? ");
			propertys.add(extendId);
		}
		hql.append(" order by extend.id asc ");
		return  pagedQuery(hql.toString(), pageNo, pageSize, propertys.toArray());
	}
	
	public Page selectServiceEngineer(Long deliveryTeamId,Map map, List<String> fuzzyQuery,
			int pageNo, int pageSize) {
		List propertys=new ArrayList();
		Object engineerId=map.get("id");
		StringBuilder hql=new StringBuilder();
		hql.append(" select engineer from ServiceEngineer engineer ");
		if(engineerId==null){
			hql.append(" , ConfigItemExtendInfo engineerExtendInfo ");
			hql.append(" join engineerExtendInfo.configItem engineerCI with engineerCI.status = ? ");
			hql.append(" join engineerCI.configItemStatus engineerStatus with engineerStatus.enname not in (?,?,?,?) ");
			hql.append(" join engineerCI.configItemType engineerType with engineerType.className = ? ");
			propertys.add(ConfigItem.VALID_STATUS);
			propertys.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
			propertys.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
			propertys.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
			propertys.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
			propertys.add(ServiceEngineer.class.getName());
			if(deliveryTeamId!=null){
				hql.append(" , CIRelationShip rel , ");
				hql.append(" ConfigItemExtendInfo deliveryTeamExtendInfo ");
				//此处之所以不需指定交付团队的状态为有效、非孤立，是因为交付团队有可能会被变更，id会变化，如果用户选择的交付团队是变更之前的，当变更结束时，通过该交付团队的id将找不到工程师
				hql.append(" join deliveryTeamExtendInfo.configItem deliveryTeamCI ");
				hql.append(" join deliveryTeamCI.configItemType deliveryTeamType with deliveryTeamType.className = ? , ");
				hql.append(" DeliveryTeam deliveryTeam ");
				propertys.add(DeliveryTeam.class.getName());
			}
			hql.append(" where engineer.id=engineerExtendInfo.extendDataId ");
			if(deliveryTeamId!=null){
				hql.append(" and deliveryTeam.id=deliveryTeamExtendInfo.extendDataId ");
				hql.append(" and rel.status = ? ");
				hql.append(" and rel.childConfigItemCode=engineer.cisn ");
				hql.append(" and rel.parentConfigItemCode=deliveryTeam.cisn ");
				hql.append(" and deliveryTeam.id = ? ");
				propertys.add(CIRelationShip.VALID_STATUS);
				propertys.add(deliveryTeamId);
			}
			Set<String> keys=map.keySet();
			for(String key:keys){
				if(map.get(key)!=null&&map.get(key).toString().length()!=0){
					if(fuzzyQuery.contains(key)){
						hql.append(" and engineer."+key.trim()+" like ? ");
						propertys.add("%"+map.get(key).toString()+"%");
					}else{
						hql.append(" and engineer."+key.trim()+" = ? ");
						propertys.add(map.get(key));
					}
				}
			}
		}else{
			hql.append(" where engineer.id = ? ");
			propertys.add(engineerId);
		}
		hql.append(" order by engineer.id asc ");
		return  pagedQuery(hql.toString(), pageNo, pageSize, propertys.toArray());
	}

	public List<String> selectNewCIModifyUnsuccess(CIBatchModify bm,List<String> cisn){
		return createCriteria(CIBatchModifyPlan.class)
		.createAlias("newConfigItem", "newCI")
		.setProjection(Projections.property("newCI.cisn"))
		.add(Restrictions.eq("batchModify", bm))
		.add(Restrictions.isNull("oldConfigItem"))
		.add(Restrictions.isNotNull("newConfigItem"))
		.add(Restrictions.in("newCI.cisn",cisn))
		.add(Restrictions.eq("result", CIBatchModifyPlan.MODIFY_UNSUCCESS))
		.list();
		
	}
	public List<ConfigItem> selectOrphanCIModifySuccess(CIBatchModify bm,List<String> cisn){
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		
		return createCriteria(CIBatchModifyPlan.class)
		.createAlias("newConfigItem", "newCI")
		.createAlias("newCI.configItemStatus", "newCIStatus")
		.setProjection(Projections.property("newConfigItem"))
		.add(Restrictions.in("newCIStatus.enname", status))
		.add(Restrictions.eq("batchModify", bm))
		.add(Restrictions.isNotNull("oldConfigItem"))
		.add(Restrictions.isNotNull("newConfigItem"))
		.add(Restrictions.in("newCI.cisn",cisn))
		.add(Restrictions.eq("result", CIBatchModifyPlan.MODIFY_SUCCESS))
		.list();
		
	}
	public CIBatchModifyPlan selectHasCIModifyDraft(Long modifyId, String cisn,Long ignoreCid) {
		Criteria c=createCriteria(CIBatchModifyPlan.class)
		.createAlias("newConfigItem", "newCI")
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.eq("newCI.cisn", cisn));
		if(ignoreCid!=null){
			c.add(Restrictions.ne("newCI.id", ignoreCid));
		}
		return (CIBatchModifyPlan) c.uniqueResult();
	}
	
	public CIBatchModifyPlan selectHasCIRelModifyDraft(Long modifyId, String parentCode,String childCode) {
		Criteria c=createCriteria(CIBatchModifyPlan.class)
		.createAlias("oldCIRelationShip", "oldRel")
		.add(Restrictions.eq("batchModify.id", modifyId));
		if(parentCode.trim().length()!=0){
			c.add(Restrictions.or(Restrictions.eq("oldRel.parentConfigItemCode", parentCode),
								  Restrictions.eq("oldRel.parentServiceItemCode", parentCode)));
		}else{
			c.add(Restrictions.and(Restrictions.isNull("oldRel.parentConfigItemCode"),
					               Restrictions.isNull("oldRel.parentServiceItemCode")));
		}
		if(childCode.trim().length()!=0){
			c.add(Restrictions.or(Restrictions.eq("oldRel.childConfigItemCode", childCode),
								  Restrictions.eq("oldRel.childServiceItemCode", childCode)));
		}
		return (CIBatchModifyPlan) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<CIRelationShip> selectCIRelationShip(List<String> cisn) {
		return createCriteria(CIRelationShip.class)
		.add(Restrictions.or(Restrictions.in("parentConfigItemCode", cisn),
				             Restrictions.in("childConfigItemCode", cisn)))
		.list();
	}
	@SuppressWarnings("unchecked")
	public List<CIRelationShip> selectValidCIRelationShip(String cisn) {
		return createCriteria(CIRelationShip.class)
		.add(Restrictions.eq("status", CIRelationShip.VALID_STATUS))
		.add(Restrictions.or(Restrictions.eq("parentConfigItemCode", cisn),
				Restrictions.eq("childConfigItemCode", cisn)))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<CIRelationShip> selectCIRelationShipByDate(String cisn,Date date) {
		return createCriteria(CIRelationShip.class)
		.add(Restrictions.or(Restrictions.eq("parentConfigItemCode", cisn),
			 Restrictions.eq("childConfigItemCode", cisn)))
		.add(Restrictions.or(
				//当前有效的关系
				Restrictions.and(
					Restrictions.eq("status", CIRelationShip.VALID_STATUS),
					Restrictions.or(
							//关系修改过且关系的修改时间小于这个时间点
							Restrictions.and(
									Restrictions.le("modifyDate", date),
							        Restrictions.isNotNull("modifyDate")
							                 ), 
							//关系未修改过且关系的创建时间小于这个时间点                 
						    Restrictions.and(
							        		Restrictions.isNull("modifyDate"),
							        		Restrictions.le("createDate", date)
							                )
							        )
						      ),
				//当前已删除的关系
				Restrictions.and(
						Restrictions.eq("status", CIRelationShip.DELETE_STATUS),
						Restrictions.and(
								//创建时间小于这个时间点
								Restrictions.le("createDate", date),
								//修改时间大于这个时间点
								Restrictions.ge("modifyDate", date)
									     )
						        )
						    )
			)
		.list();
	}

	public boolean selectHasSameDraft(Long modifyId, String parentCode,
			String childCode,Long ignoreRid) {
		Criteria c=createCriteria(CIBatchModifyPlan.class)
		.createAlias("newCIRelationShip", "newRel")
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.isNotNull("newCIRelationShip"))
		.add(Restrictions.or(Restrictions.eq("newRel.childConfigItemCode", childCode), Restrictions.eq("newRel.childServiceItemCode", childCode)))
		.add(Restrictions.or(Restrictions.eq("newRel.parentConfigItemCode", parentCode), Restrictions.eq("newRel.parentServiceItemCode", parentCode)));
		if(ignoreRid!=null){
			c.add(Restrictions.ne("newCIRelationShip.id", ignoreRid));
		}
		List<CIBatchModifyPlan> plans=c.list();
		if(!plans.isEmpty()){
			return true;
		}
		return false;
	}

	public boolean selectHasSameValidAndProcessingRel(String parentCode, String childCode,List<Long> rids,Long ignoreRid) {
		Criteria c=createCriteria(CIRelationShip.class)
		.add(Restrictions.or(Restrictions.eq("childConfigItemCode", childCode), Restrictions.eq("childServiceItemCode", childCode)))
		.add(Restrictions.or(Restrictions.eq("parentConfigItemCode", parentCode), Restrictions.eq("parentServiceItemCode", parentCode)));
		if(ignoreRid!=null){
			c.add(Restrictions.ne("id", ignoreRid));
		}
		if(rids!=null&&!rids.isEmpty()){
			c.add(Restrictions.or(Restrictions.eq("status", CIRelationShip.VALID_STATUS), Restrictions.in("id", rids)));
		}else{
			c.add(Restrictions.eq("status", CIRelationShip.VALID_STATUS));
		}
		List<CIBatchModifyPlan> plans=c.list();
		if(!plans.isEmpty()){
			return true;
		}
		return false;
	}
	
	public CIRelationShip selectHasSameValidRel(String parentCode,
			String childCode) {
		Criteria c=createCriteria(CIRelationShip.class)
		.add(Restrictions.or(Restrictions.eq("childConfigItemCode", childCode), Restrictions.eq("childServiceItemCode", childCode)))
		.add(Restrictions.eq("status", CIRelationShip.VALID_STATUS))
		.add(Restrictions.or(Restrictions.eq("parentConfigItemCode", parentCode), Restrictions.eq("parentServiceItemCode", parentCode)));
		return (CIRelationShip) c.uniqueResult();
	}

	public List<CIBatchModifyPlan> selectProcessingRelPlan(Long modifyId) {
		return createCriteria(CIBatchModifyPlan.class)
			.setFetchMode("newCIRelationShip", FetchMode.JOIN)
			.setFetchMode("oldCIRelationShip", FetchMode.JOIN)
			.createAlias("batchModify", "bm")
			.add(Restrictions.ne("bm.id", modifyId))
			.add(Restrictions.or(Restrictions.isNotNull("newCIRelationShip"), Restrictions.isNotNull("oldCIRelationShip")))
			.add(Restrictions.eq("bm.status", CIBatchModify.STATUS_PROCESSING))
			.list();
	}
	public List<String> selectCIProcessing(Long modifyId,List<String> cisn) {
		return createCriteria(CIBatchModifyPlan.class)
			.createAlias("batchModify", "bm")
			.createAlias("oldConfigItem", "oldCI")
			.add(Restrictions.ne("bm.id", modifyId))
			.add(Restrictions.eq("bm.status", CIBatchModify.STATUS_PROCESSING))
			.add(Restrictions.isNotNull("oldConfigItem"))
			.add(Restrictions.in("oldCI.cisn", cisn))
			.setProjection(Projections.property("oldCI.cisn"))
			.list();
	}

	public List<ConfigItem> selectOrphanCI(Long modifyId, List<String> cisn) {
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		Criteria c =createCriteria(CIBatchModifyPlan.class)
		.createAlias("newConfigItem", "newCI")
		.createAlias("newCI.configItemStatus", "CIStatus")
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.isNotNull("newConfigItem"))
		.add(Restrictions.in("newCI.cisn", cisn))
		.add(Restrictions.in("CIStatus.enname", status))
		.setFetchMode("newCI.configItemStatus", FetchMode.JOIN)
		.setProjection(Projections.property("newConfigItem"));
		return c.list();
	}
	public List<ConfigItem> selectProcessingOrphanCI(Long modifyId,Set<String> cisn) {
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		Criteria c =createCriteria(CIBatchModifyPlan.class)
		.createAlias("newConfigItem", "newCI")
		.createAlias("newCI.configItemStatus", "CIStatus")
		.createAlias("batchModify", "bm")
		.add(Restrictions.ne("bm.id", modifyId))
		.add(Restrictions.eq("bm.status", CIBatchModify.STATUS_PROCESSING))
		.add(Restrictions.isNotNull("newConfigItem"))
		.add(Restrictions.isNotNull("oldConfigItem"))
		.add(Restrictions.in("newCI.cisn", cisn))
		.add(Restrictions.in("CIStatus.enname", status))
		.setFetchMode("newCI.configItemStatus", FetchMode.JOIN)
		.setProjection(Projections.property("newConfigItem"));
		return c.list();
	}

	public List<CIBatchModifyPlan> selectDeleteRelPlan(Long modifyId, String cisn) {
		return createCriteria(CIBatchModifyPlan.class)
		.createAlias("oldCIRelationShip", "oldRel")
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.isNotNull("oldCIRelationShip"))
		.add(Restrictions.isNull("newCIRelationShip"))
		.add(Restrictions.or(Restrictions.eq("oldRel.parentConfigItemCode", cisn),
					         Restrictions.eq("oldRel.childConfigItemCode", cisn)))
		.list();
	}
	public List<CIBatchModifyPlan> selectMaintenanceDeleteRelPlan(Long modifyId,ConfigItem maintenance){
		return createCriteria(CIBatchModifyPlan.class)
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.isNotNull("oldCIRelationShip"))
		.add(Restrictions.isNull("newCIRelationShip"))
		.add(Restrictions.eq("maintenanceCIRel",maintenance))
		.list();
	}
	
	public List<CIRelationShip> selectDeleteRel(Long modifyId) {
		return createCriteria(CIBatchModifyPlan.class)
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.isNotNull("oldCIRelationShip"))
		.add(Restrictions.isNull("newCIRelationShip"))
		.setProjection(Projections.property("oldCIRelationShip"))
		.list();
	}
	public List<CIRelationShip> selectDeleteRel(Long modifyId,List<CIRelationShip> rels) {
		return createCriteria(CIBatchModifyPlan.class)
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.isNotNull("oldCIRelationShip"))
		.add(Restrictions.in("oldCIRelationShip", rels))
		.add(Restrictions.isNull("newCIRelationShip"))
		.setProjection(Projections.property("oldCIRelationShip"))
		.list();
	}
	public List<CIBatchModifyPlan> selectModifyRel(Long modifyId) {
		return createCriteria(CIBatchModifyPlan.class)
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.or(
				Restrictions.isNotNull("oldCIRelationShip"),
				Restrictions.isNotNull("newCIRelationShip")))
		.list();
	}
	public List<CIBatchModifyPlan> selectNewAndDeleteRel(Long modifyId,String cisn) {
		List<CIBatchModifyPlan> plans=new ArrayList<CIBatchModifyPlan>();
		//新建的关系
		plans.addAll(createCriteria(CIBatchModifyPlan.class)
				.createAlias("newCIRelationShip", "newRel")
				.add(Restrictions.eq("batchModify.id", modifyId))
				.add(Restrictions.isNotNull("newCIRelationShip"))
				.add(Restrictions.isNull("oldCIRelationShip"))
				.add(Restrictions.or(
						Restrictions.eq("newRel.parentConfigItemCode", cisn),
		        		Restrictions.eq("newRel.childConfigItemCode", cisn)))
		        .list());
		//删除的关系
		plans.addAll(createCriteria(CIBatchModifyPlan.class)
				.createAlias("oldCIRelationShip", "oldRel")
				.add(Restrictions.eq("batchModify.id", modifyId))
				.add(Restrictions.isNull("newCIRelationShip"))
				.add(Restrictions.isNotNull("oldCIRelationShip"))
				.add(Restrictions.or(
						Restrictions.eq("oldRel.parentConfigItemCode", cisn),
		         		Restrictions.eq("oldRel.childConfigItemCode", cisn)))
		        .list());
		return plans;
	}

	public ConfigItem selectModifyConfigItem(Long modifyId, String itemCode) {
		return (ConfigItem) createCriteria(CIBatchModifyPlan.class)
		.createAlias("newConfigItem", "newCI")
		.add(Restrictions.eq("newCI.cisn", itemCode))
		.add(Restrictions.isNotNull("newConfigItem"))
		.add(Restrictions.eq("batchModify.id",modifyId))
		.setProjection(Projections.property("newConfigItem"))
		.uniqueResult();
	}
	public List<CIBatchModifyPlan> selectMaintenanceConfigItem(Long modifyId, ConfigItem ci,Integer result) {
		return createCriteria(CIBatchModifyPlan.class)
		.add(Restrictions.eq("maintenanceCIRel", ci))
		.add(Restrictions.isNull("newCIRelationShip"))
		.add(Restrictions.isNull("oldCIRelationShip"))
		.add(Restrictions.eq("batchModify.id",modifyId))
		.add(Restrictions.eq("result",result))
		.list();
	}

	public List<CIBatchModifyPlan> selectMaintenanceRelPlan(Long modifyId,
			List<ConfigItem> maintenanceCIs) {
		return createCriteria(CIBatchModifyPlan.class)
		.add(Restrictions.eq("batchModify.id", modifyId))
		.add(Restrictions.in("maintenanceCIRel", maintenanceCIs))
		.add(Restrictions.or(
				Restrictions.isNotNull("newCIRelationShip"), 
				Restrictions.isNotNull("oldCIRelationShip")))
		.list();
	}
	public Page selectConfigItemNecessaryRelation(Map paramMap, int start, int pageSize){
		
		Map propertys=new HashMap();
		StringBuilder hqlSelect  = new  StringBuilder();
		StringBuilder hqlFrom  = new  StringBuilder();
		 
		hqlSelect.append("  SELECT data1.configItemType AS configItemType,   ");
		hqlSelect.append("  data1.otherConfigItemType AS otherConfigItemType,   ");
		hqlSelect.append("  data1.parentOrChildType AS parentOrChildType,   ");
		hqlSelect.append("  data1.configItemId AS configItemId,   ");
		hqlSelect.append("  data1.configItemName AS configItemName, data1.configItemNum AS configItemNum,   ");
		hqlSelect.append("   data1.configItemNecessaryId,   ");
		hqlSelect.append(" data1.isOptional AS isOptional,   ");
		hqlSelect.append("  configItem1.name AS engineer, configItemType1.name AS configItemTypeName,   ");
		hqlSelect.append("   configItemType2.name AS otherConfigItemTypeName,   ");
		hqlSelect.append("   CASE WHEN configItemType2.name IS NOT NULL   ");
		hqlSelect.append("   THEN configItemType1.name + '->' + configItemType2.name ELSE configItemType1.name  ");
		hqlSelect.append("    + '->' END AS necessaryRelation,   ");
		hqlSelect.append("   parentOrChildType  AS necessaryRelationType  ");
		
		hqlFrom.append(" FROM (SELECT dbo.ConfigItemNecessaryRel.configItemType AS configItemType,   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.otherConfigItemType AS otherConfigItemType,   ");
		hqlFrom.append("           dbo.ConfigItemNecessaryRel.parentOrChildType AS parentOrChildType,   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.isOptional AS isOptional,   ");
		hqlFrom.append("           dbo.ConfigItem.id AS configItemId,   ");
		hqlFrom.append("           dbo.ConfigItem.name AS configItemName,   ");
		hqlFrom.append("           dbo.ConfigItem.cisn AS configItemNum,   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.id AS configItemNecessaryId  ,dbo.ConfigItem.configItemStatus as configItemStatus");
		hqlFrom.append("     FROM dbo.ConfigItemNecessaryRel INNER JOIN  ");
		hqlFrom.append("           dbo.ConfigItem ON   ");
		hqlFrom.append("           dbo.ConfigItemNecessaryRel.configItemType = dbo.ConfigItem.configItemType AND  ");
		hqlFrom.append("          ConfigItem.status = :configItemStatus1  LEFT OUTER JOIN  ");
		hqlFrom.append("          dbo.CIRelationShip ON   ");
		hqlFrom.append("          dbo.ConfigItem.cisn = dbo.CIRelationShip.parentConfigItemCode AND   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.otherConfigItemType = dbo.CIRelationShip.childConfigItemType  ");
		hqlFrom.append("            AND CIRelationShip.status = :relationStatus1  ");
		hqlFrom.append("     WHERE (dbo.CIRelationShip.id IS NULL) AND parentOrChildType = :parentOrChildType1 AND  "); 
		hqlFrom.append("            otherConfigItemType IS NOT NULL  ");
		hqlFrom.append("   UNION  ");
		hqlFrom.append("     SELECT dbo.ConfigItemNecessaryRel.configItemType AS configItemType,   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.otherConfigItemType AS otherConfigItemType,   ");
		hqlFrom.append("           dbo.ConfigItemNecessaryRel.parentOrChildType,   ");
		hqlFrom.append("           dbo.ConfigItemNecessaryRel.isOptional AS isOptional,   ");
		hqlFrom.append("           dbo.ConfigItem.id AS configItemId,   ");
		hqlFrom.append("           dbo.ConfigItem.name AS configItemName,   ");
		hqlFrom.append("          dbo.ConfigItem.cisn AS configItemNum,   ");
		hqlFrom.append("        dbo.ConfigItemNecessaryRel.id AS configItemNecessaryId  ,dbo.ConfigItem.configItemStatus");
		hqlFrom.append("    FROM dbo.ConfigItemNecessaryRel INNER JOIN  ");
		hqlFrom.append("          dbo.ConfigItem ON   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.configItemType = dbo.ConfigItem.configItemType AND  ");
		hqlFrom.append("           ConfigItem.status = :configItemStatus2  LEFT OUTER JOIN  ");
		hqlFrom.append("          dbo.CIRelationShip ON   ");
		hqlFrom.append("           dbo.ConfigItemNecessaryRel.otherConfigItemType = dbo.CIRelationShip.parentConfigItemType  ");
		hqlFrom.append("          AND dbo.ConfigItem.cisn = dbo.CIRelationShip.childConfigItemCode AND   ");
		hqlFrom.append("         CIRelationShip.status = :relationStatus2  ");
		hqlFrom.append("    WHERE (dbo.CIRelationShip.id IS NULL) AND parentOrChildType = :parentOrChildType2 AND   ");
		hqlFrom.append("           otherConfigItemType IS NOT NULL  ");
		hqlFrom.append("   UNION  ");
		hqlFrom.append("    SELECT dbo.ConfigItemNecessaryRel.configItemType AS configItemType,   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.otherConfigItemType AS otherConfigItemType,   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.parentOrChildType AS parentOrChildType,   ");
		hqlFrom.append("           dbo.ConfigItemNecessaryRel.isOptional AS isOptional,   ");
		hqlFrom.append("           dbo.ConfigItem.id AS configItemId,   ");
		hqlFrom.append("        dbo.ConfigItem.name AS configItemName,   ");
		hqlFrom.append("         dbo.ConfigItem.cisn AS configItemNum,   ");
		hqlFrom.append("         dbo.ConfigItemNecessaryRel.id AS configItemNecessaryId  ,dbo.ConfigItem.configItemStatus");
		hqlFrom.append("     FROM dbo.ConfigItemNecessaryRel INNER JOIN  ");
		hqlFrom.append("           dbo.ConfigItem ON   ");
		hqlFrom.append("           dbo.ConfigItemNecessaryRel.configItemType = dbo.ConfigItem.configItemType AND  ");
		hqlFrom.append("            dbo.ConfigItem.status = :configItemStatus3  LEFT OUTER JOIN  ");
		hqlFrom.append("           dbo.CIRelationShip ON   ");
		hqlFrom.append("           dbo.ConfigItem.cisn = dbo.CIRelationShip.parentConfigItemCode AND   ");
		hqlFrom.append("          dbo.CIRelationShip.status = :relationStatus3  ");
		hqlFrom.append("      WHERE (dbo.CIRelationShip.id IS NULL) AND   ");
		hqlFrom.append("          (dbo.ConfigItemNecessaryRel.parentOrChildType = :parentOrChildType3) AND   ");
		hqlFrom.append("          (dbo.ConfigItemNecessaryRel.otherConfigItemType IS NULL)  ");
		hqlFrom.append("    UNION  ");
		hqlFrom.append("      SELECT dbo.ConfigItemNecessaryRel.configItemType AS configItemType,   ");
		hqlFrom.append("          dbo.ConfigItemNecessaryRel.otherConfigItemType AS otherConfigItemType,   ");
		hqlFrom.append("        dbo.ConfigItemNecessaryRel.parentOrChildType AS parentOrChildType,   ");
		hqlFrom.append("           dbo.ConfigItemNecessaryRel.isOptional AS isOptional,   ");
		hqlFrom.append("           dbo.ConfigItem.id AS configItemId,   ");
		hqlFrom.append("          dbo.ConfigItem.name AS configItemName,   ");
		hqlFrom.append("          dbo.ConfigItem.cisn AS configItemNum,   ");
		hqlFrom.append("         dbo.ConfigItemNecessaryRel.id AS configItemNecessaryId  ,dbo.ConfigItem.configItemStatus");
		hqlFrom.append("    FROM dbo.ConfigItemNecessaryRel INNER JOIN  ");
		hqlFrom.append("          dbo.ConfigItem ON   ");
		hqlFrom.append("         dbo.ConfigItemNecessaryRel.configItemType = dbo.ConfigItem.configItemType AND  ");
		hqlFrom.append("          dbo.ConfigItem.status = :configItemStatus4  LEFT OUTER JOIN  ");
		hqlFrom.append("            dbo.CIRelationShip ON   ");
		hqlFrom.append("         dbo.ConfigItem.cisn = dbo.CIRelationShip.childConfigItemCode AND   ");
		hqlFrom.append("           dbo.CIRelationShip.status = :relationStatus4  ");
		hqlFrom.append("    WHERE (dbo.CIRelationShip.id IS NULL) AND   ");
		hqlFrom.append("         (dbo.ConfigItemNecessaryRel.parentOrChildType = :parentOrChildType4) AND   ");
		hqlFrom.append("         (dbo.ConfigItemNecessaryRel.otherConfigItemType IS NULL))   ");
		hqlFrom.append("  data1 LEFT OUTER JOIN  ");
		hqlFrom.append("  CIRelationShip CIR1 ON data1.configItemType <> :configItemType1 AND data1.configItemType <> :configItemType2 AND data1.configItemNum = CIR1.parentConfigItemCode AND   ");
		hqlFrom.append("   CIR1.childConfigItemType = :configItemType3 AND CIR1.parentConfigItemCode IS NOT NULL   ");
		hqlFrom.append("   AND CIR1.status = :relationStatus5 LEFT OUTER JOIN  ");
		hqlFrom.append("  ConfigItem configItem1 ON CIR1.childConfigItemCode = configItem1.cisn AND   ");
		hqlFrom.append("   configItem1.status = :configItemStatus5  LEFT OUTER JOIN  ");
		hqlFrom.append("   ConfigItemType configItemType1 ON   ");
		hqlFrom.append("  data1.configItemType = configItemType1.id LEFT OUTER JOIN  ");
		hqlFrom.append("  ConfigItemType configItemType2 ON data1.otherConfigItemType = configItemType2.id   inner JOIN ");
		hqlFrom.append("  dbo.ConfigItemStatus ON ");
		hqlFrom.append("  data1.configItemStatus = dbo.ConfigItemStatus.id AND dbo.ConfigItemStatus.enname not in ( :standby, :disabled , :archived , :loan) ");
		hqlFrom.append("  where 1=1 ");
		propertys.put("relationStatus1", CIRelationShip.VALID_STATUS);
		propertys.put("relationStatus2", CIRelationShip.VALID_STATUS);
		propertys.put("relationStatus3", CIRelationShip.VALID_STATUS);
		propertys.put("relationStatus4", CIRelationShip.VALID_STATUS);
		propertys.put("relationStatus5", CIRelationShip.VALID_STATUS);
		propertys.put("configItemStatus1", ConfigItem.VALID_STATUS);
		propertys.put("configItemStatus2", ConfigItem.VALID_STATUS);
		propertys.put("configItemStatus3", ConfigItem.VALID_STATUS);
		propertys.put("configItemStatus4", ConfigItem.VALID_STATUS);
		propertys.put("configItemStatus5", ConfigItem.VALID_STATUS);
		propertys.put("configItemType1", ConfigItemType.DELIVERYTEAM_ID);
		propertys.put("configItemType2", ConfigItemType.SERVICEPROVIDER_ID);
		propertys.put("configItemType3", ConfigItemType.SERVICEENGINEER_ID);
		propertys.put("parentOrChildType1", ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE);
		propertys.put("parentOrChildType2", ConfigItemNecessaryRel.CHILD_PARENT_REL_TYPE);
		propertys.put("parentOrChildType3", ConfigItemNecessaryRel.PARENT_CHILD_REL_TYPE);
		propertys.put("parentOrChildType4", ConfigItemNecessaryRel.CHILD_PARENT_REL_TYPE);
		propertys.put("standby", ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		propertys.put("disabled", ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		propertys.put("archived", ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		propertys.put("loan", ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
			Set<String> keys=paramMap.keySet();
			for(String key:keys){				
				if(paramMap.get(key)!=null && paramMap.get(key).toString().length()!=0){
					if("configItemNum".contains(key)){
						String value = paramMap.get(key).toString();
						if (StringUtils.isNotBlank(value)){
							hqlFrom.append(" and data1.configItemNum  like :configItemNum ");
							propertys.put("configItemNum","%"+value+"%");
						}
					}else if("configItemName".contains(key) ){
						String value = paramMap.get(key).toString();
						if (StringUtils.isNotBlank(value)){
							hqlFrom.append(" and configItemName like :configItemName ");
							propertys.put("configItemName","%"+value+"%");
						}
					}else if("configItemType".contains(key) ){
						String value = paramMap.get(key).toString();
						if (StringUtils.isNotBlank(value)){
							hqlFrom.append(" and data1.configItemType = :configItemType ");
							propertys.put("configItemType", value);
						}
					}else if("engineer".contains(key) ){
						String value = paramMap.get(key).toString();
						if (StringUtils.isNotBlank(value)){
							hqlFrom.append(" and configItem1.name like :engineer ");
							propertys.put("engineer","%"+value+"%");
						}
					}else if("isOptional".contains(key) ){
						String value = paramMap.get(key).toString();
						if (StringUtils.isNotBlank(value)){
							hqlFrom.append(" and isOptional =  :isOptional ");
							propertys.put("isOptional",value);
						}
					}
				}
			}	
			List dataList = getSession().createSQLQuery(hqlSelect.append(hqlFrom).toString())
										.setFirstResult(start)
										.setMaxResults(pageSize)
										.setProperties(propertys)
										.list();
			StringBuilder hqlCount = new StringBuilder("select count(*) ");
			Integer count = (Integer)getSession().createSQLQuery(hqlCount.append(hqlFrom).toString()).setProperties(propertys).uniqueResult();			
			return new Page(start,count.longValue(),pageSize,dataList);
		
	}
	public List<Long> selectServerEngineer(String cisn){
		List<Object> values=new ArrayList<Object>();
		StringBuilder engineerHql=new StringBuilder();
		engineerHql.append(" select engineer.userInfo.id from ConfigItemExtendInfo engineerExtendInfo ");
		engineerHql.append(" join engineerExtendInfo.configItem engineerCI with engineerCI.status = ? ");
		engineerHql.append(" join engineerCI.configItemStatus engineerStatus with engineerStatus.enname not in (?,?,?,?) ");
		engineerHql.append(" join engineerCI.configItemType engineerType with engineerType.className = ? , ");
		engineerHql.append(" ServiceEngineer engineer , ");
		engineerHql.append(" CIRelationShip rel ");
		engineerHql.append(" where engineer.id=engineerExtendInfo.extendDataId ");
		engineerHql.append(" and rel.status = ? ");
		engineerHql.append(" and rel.childConfigItemCode = engineer.cisn ");
		engineerHql.append(" and rel.parentConfigItemCode = ? ");
		values.add(ConfigItem.VALID_STATUS);
		values.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		values.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		values.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		values.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		values.add(ServiceEngineer.class.getName());
		values.add(CIRelationShip.VALID_STATUS);
		values.add(cisn);
		return getHibernateTemplate().find(engineerHql.toString(),values.toArray());
	}
}
