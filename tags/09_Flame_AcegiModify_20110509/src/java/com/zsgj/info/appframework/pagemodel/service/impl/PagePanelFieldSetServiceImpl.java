package com.zsgj.info.appframework.pagemodel.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelFieldSet;
import com.zsgj.info.appframework.pagemodel.service.PagePanelFieldSetService;
import com.zsgj.info.framework.dao.BaseDao;

public class PagePanelFieldSetServiceImpl extends BaseDao implements PagePanelFieldSetService {

	public List<PagePanelColumn> findColumnByFieldSet(PagePanelFieldSet fieldSet) {
		String hql = "SELECT ppfs.pagePanelColumn FROM PagePanelFieldSetColumn ppfs " +
				"WHERE ppfs.pagePanelFieldSet=? ORDER BY ppfs.order";
		Query query = super.createQuery(hql, fieldSet);
		List<PagePanelColumn> list = query.list();
		return list;
	}

	public PagePanelFieldSet findPagePanelFieldSet(PagePanelColumn ppc) {
		PagePanelFieldSet result = null;
		Criteria c = super.getCriteria(PagePanelFieldSet.class);
		c.add(Restrictions.eq("pagePanelColumn", ppc));
		List<PagePanelFieldSet> list = c.list();
		if(!list.isEmpty()){
			result = list.iterator().next();
		}
		return result;
	}

	public List<PagePanelFieldSet> findFieldSetByPanel(PagePanel panel) {
		Criteria criteria = super.getCriteria(PagePanelFieldSet.class);
		criteria.add(Restrictions.eq("pagePanel", panel));
		criteria.addOrder(Order.asc("orderFlag"));
		List<PagePanelFieldSet> list = criteria.list();
		return list;
	}

	public List<PagePanelColumn> findFieldSetColumn(
			PagePanelColumn pagePanelColumn) {
		Criteria criteria = super.getCriteria(PagePanelColumn.class);
		criteria.add(Restrictions.eq("parentPagePanelColumn", pagePanelColumn));
		criteria.addOrder(Order.asc("order"));
		List<PagePanelColumn> list = criteria.list();
		
		return list;
	}

}
