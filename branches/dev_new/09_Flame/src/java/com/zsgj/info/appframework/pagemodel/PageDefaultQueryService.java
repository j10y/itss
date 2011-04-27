package com.zsgj.info.appframework.pagemodel;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelTableService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.util.DateUtil;

public class PageDefaultQueryService extends BaseDao implements PageQueryService{

	public Criteria before(String panelName, Map params, int pageNo, int pageSize, String orderProp, boolean isAsc) {
		PagePanelService pagePanelService = (PagePanelService) ContextHolder.getBean("pagePanelService");
		PagePanelTableService pagePanelTableService = (PagePanelTableService) ContextHolder.getBean("pagePanelTableService");
		
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable smt = panel.getSystemMainTable();
		String mainTableName = smt.getTableName();
		String className = smt.getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Criteria critea = super.getCriteria(clazz);
		//获取当前主表的所依赖的表和外键字段，以便主动抓取
		List<PagePanelTableRelation> ptrs = pagePanelService.findPanelTableRelBySub(panel, smt);
		for(PagePanelTableRelation item : ptrs){
			SystemMainTable ftable = item.getForeignTable();
			String tableName = ftable.getTableName();
			SystemMainTableColumn fcolumn = item.getForeignTableColumn();
			String fpropertyName = fcolumn.getPropertyName();
			String aliasName = mainTableName+"$"+fpropertyName;
			critea.createAlias("this."+fpropertyName, aliasName);
			critea.setFetchMode(aliasName, FetchMode.JOIN);
		}
		Set<PagePanelColumn> ppcs = panel.getPagePanelColumns();
		for(PagePanelColumn ppc : ppcs){
			Column column = ppc.getColumn();
			SystemMainTable csmt = column.getSystemMainTable();
			String ctableName = csmt.getTableName();
			String propertyName = column.getPropertyName();
			String ctablePropertyName = ctableName+"$"+propertyName;
			Object propertyValue = params.get(ctablePropertyName);
			
			
			if(column instanceof SystemMainTableColumn){
				String queryTableName = ctableName;
				if(csmt==smt){
					queryTableName = "this";
				}
				//查询字段的属性名称
				String queryPropName = queryTableName+"."+ propertyValue;
				
				PropertyType propType = column.getPropertyType();
				if(propType==null){
					throw new ServiceException("请选择字段类型");
				}
				String type = propType.getPropertyTypeName();
				if(propertyValue!=null&& !propertyValue.toString().equals("")){
					
					if(type.equals("Integer")){
						if(propertyValue instanceof java.util.Set){
							Set set = (Set)propertyValue;
							critea.add(Restrictions.in(queryPropName, set));
						}else{
							critea.add(Restrictions.eq(queryPropName, Integer.valueOf(propertyValue.toString())));
						}
						
					}
				}else if(type.equals("String")){
					if(propertyValue instanceof java.util.Set){
						Set set = (Set)propertyValue;
						critea.add(Restrictions.in(queryPropName, set));
					}else{
						Criteria c = super.getCriteria(SystemTableQueryColumn.class);
						c.add(Restrictions.eq("systemMainTable", smt));
						c.add(Restrictions.eq("mainTableColumn", column));
						List list = c.list();
						if(!list.isEmpty()){
							SystemTableQueryColumn stqc = (SystemTableQueryColumn) list.iterator().next();
							Integer matchMode = stqc.getMatchModeAsInt();
							if(matchMode!=null){
								if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_ANYWHERE)){
									critea.add(Restrictions.like(queryPropName, String.valueOf(propertyValue), MatchMode.ANYWHERE));
								}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_START)){
									critea.add(Restrictions.like(queryPropName, String.valueOf(propertyValue), MatchMode.START));
								}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_EXACT)){
									critea.add(Restrictions.like(queryPropName, String.valueOf(propertyValue), MatchMode.EXACT));
								}else if(matchMode.equals(SystemTableQueryColumn.MATCH_MODE_END)){
									critea.add(Restrictions.like(queryPropName, String.valueOf(propertyValue), MatchMode.END));
								}else{
									throw new ServiceException("string类型字段，单个查询参数值不可以使用区域查询");
								}
							}
							super.evict(stqc);
						}
						//critea.add(Restrictions.like(queryPropName, String.valueOf(propertyValue)));
					}
				}else if(type.equals("Long")){
					critea.add(Restrictions.eq(queryPropName, Long.valueOf(propertyValue.toString())));
				}else if(type.equals("Double")){
					if(propertyValue!=null){
						critea.add(Restrictions.eq(queryPropName, Double.valueOf(propertyValue.toString())));
					}
				}else if(type.equals("Date")){
					if(propertyValue!=null){
						critea.add(Restrictions.eq(queryPropName, DateUtil.convertStringToDate(propertyValue.toString())));
					}
				}else if(type.equals("BaseObject")){
					if(propertyValue!=null){
						critea.add(Restrictions.eq(queryPropName, propertyValue));
					}
				}
			}
			
			
		}//loop columns
		orderProp = orderProp.replace('$', '.');
		if(orderProp!=null&& !orderProp.equals("")){
			if(isAsc==true){
				critea.addOrder(Order.asc(orderProp));
			}else {
				critea.addOrder(Order.desc(orderProp));
			}
		}
		return critea;
	}

	public Page end(Criteria criteria, int pageNo, int pageSize) {
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		
		Page page = this.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}

	public Page end(Criteria criteria, Map params, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public void middle(Criteria criteria, Map extParams) {
		// TODO Auto-generated method stub
		
	}

	public void middle(Criteria criteria) {
		// TODO Auto-generated method stub
		
	}

	public Page query(String panelName, Map<String, Object> params, Map<String, Object> extParams, 
							int pageNo, int pageSize, String orderProp, boolean isAsc) {
		Criteria  criteria = this.before(panelName, params, pageNo, pageSize, orderProp, isAsc);
		this.middle(criteria, extParams);
		return this.end(criteria, pageNo, pageSize);
	}

	public List query(String panelName, Map<String, Object> params, Map<String, Object> extParams,
						String orderProp, boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

}
