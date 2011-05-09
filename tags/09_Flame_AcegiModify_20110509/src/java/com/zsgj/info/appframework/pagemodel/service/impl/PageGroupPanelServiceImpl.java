package com.zsgj.info.appframework.pagemodel.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTable;
import com.zsgj.info.appframework.pagemodel.service.PageGroupPanelService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Module;

public class PageGroupPanelServiceImpl extends BaseDao implements PageGroupPanelService {

	public Page findPagePanel(Map params, int pageNo, int pageSize) {
		Page page = null;
		Module module = (Module) params.get("module");
		String pageName = (String) params.get("pageName");
		String settingType = (String) params.get("settingType");
		
		Criteria critera = super.createCriteria(PagePanel.class);
		critera.add(Restrictions.isNotNull("id"));
		if(module!=null){
			critera.add(Restrictions.eq("module", module));
		}
		if(StringUtils.isNotBlank(pageName)){
			critera.add(Restrictions.ilike("titile", pageName));
		}
		
		/**Restrictions.disjunction()
		.add(Restrictions.ilike("name", pageName, MatchMode.ANYWHERE)) 
		.add(Restrictions.ilike("title", pageName, MatchMode.ANYWHERE))*/
		
		if(StringUtils.isNotBlank(settingType)){
			critera.add(Restrictions.eq("settingType", Integer.valueOf(settingType)));
		}
		/*critera.addOrder(Order.asc("module"));
		critera.addOrder(Order.asc("name"));*/
		critera.add(Restrictions.eq("groupFlag", Integer.valueOf(1)));
		page = super.pagedQuery(critera, pageNo, pageSize);
		return page;
	}

	public Page findPagePanel(Module module, String pageName, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public PagePanel findPagePanelById(String pagePanelId) {
		PagePanel result = null;
		try {
			result = super.get(PagePanel.class, Long.valueOf(pagePanelId));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("获取PagePanel发生异常");
		}		
		return result;
	}

	public List<PagePanelTable> findPagePanelTable(PagePanel panel) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removePagePanel(String pagePanelId) {
		PagePanel groupPanel = super.get(PagePanel.class, Long.valueOf(pagePanelId));
		if(groupPanel.getGroupFlag()!=null&& groupPanel.getGroupFlag().intValue()==1){
			super.executeUpdate("delete from PagePanelRelation ppr where ppr.parentPagePanel=?", groupPanel);
		}
		super.executeUpdate("delete from PagePanelColumn ppc where ppc.pagePanel.id=?", 
				Long.valueOf(pagePanelId));
		super.removeObject(PagePanel.class, Long.valueOf(pagePanelId));
		
	}

	public void removePagePanel(String[] pagePanelIds) {
		if(pagePanelIds==null||pagePanelIds.length==0){
			throw new ServiceException("删除PagePanel发生异常");
		}
		for(String pagePanelId: pagePanelIds){
			this.removePagePanel(pagePanelId);
		}
	}

	public PagePanel savePagePanel(PagePanel panel) {
		PagePanel result = null;
		try {
			
			Criteria c = super.getCriteria(PagePanel.class);
			c.add(Restrictions.eq("id", panel.getId()));
			c.setFetchMode("childPagePanels", FetchMode.JOIN);
			PagePanel old = (PagePanel) c.uniqueResult();
			if(old!=null){
				Set childpps = old.getChildPagePanels();
				panel.setChildPagePanels(childpps);
			}
			
			result = (PagePanel) super.save(panel);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("保存pagePanel发生异常");
		}		
		return result;
	}

	public void savePagePanelMove(String panelId, String oldParentId, String newParentId, String nodeIndex) {
		// TODO Auto-generated method stub
		
	}

	public List findMainTableByPanel(PagePanel panel) {
		String hql = "select ppt.systemMainTable from PagePanelTable ppt where ppt.pagePanel=?";
		List list = super.find(hql, panel);
		return list;
	}

	public List<Column> findColumns(SystemMainTable smt, Integer settingType) {
		List<Column> columns = new ArrayList<Column>();
		Criteria c = super.getCriteria(SystemTableSetting.class);
		//c.add(Restrictions.eq("userInfo", UserContext.getUserInfo()));
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("settingType", new Integer(settingType)));
		c.add(Restrictions.eq("isDisplay", 1));
		c.setFetchMode("mainTableColumn", FetchMode.JOIN);
		List list = c.list();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			SystemTableSetting uts = (SystemTableSetting) iter.next();
			Column column = uts.getColumn();
			columns.add(column);
		}
		return columns;
	}

	public List<PageGroupPanelTable> findGroupPanelTableBySub(PagePanel groupPanel, PagePanel subPanel) {
		Criteria c = super.getCriteria(PageGroupPanelTable.class);
		c.add(Restrictions.eq("pagePanel", groupPanel));
		c.add(Restrictions.eq("subPagePanel", subPanel));
		return c.list();
	}

	public List<PageGroupPanelTable> findGroupPanelTableByParent(PagePanel groupPanel, PagePanel parentPanel) {
		Criteria c = super.getCriteria(PageGroupPanelTable.class);
		c.add(Restrictions.eq("pagePanel", groupPanel));
		c.add(Restrictions.eq("parentPagePanel", parentPanel));
		return c.list();
	}

	public List<PageGroupPanelTable> findAllPageGroupPanelTableByGroupPanel(PagePanel groupPanel) {
		String hql="select pgpt from PageGroupPanelTable pgpt where pgpt.pagePanel=?";
		List list = super.find(hql, groupPanel);
		return list;
//		Criteria c = super.getCriteria(PageGroupPanelTable.class);
//		c.add(Restrictions.eq("pagePanel", groupPanel));
//		List<PageGroupPanelTable> list = c.list();
//		return list;
	}

	public void removePageGroupPanelTable(String id) {
		// TODO Auto-generated method stub
		super.executeUpdate("delete from PageGroupPanelTable pgpt where pgpt.id=?",
				Long.valueOf(id));
	}

	public List findAllPagePanel() {
		// TODO Auto-generated method stub
		List list = super.getAll(PagePanel.class);
		return   list;
	}
	public SystemMainTable findSystemMainTable(String tableId){
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("id",Long.valueOf(tableId)));
		SystemMainTable systemMainTable = (SystemMainTable)c.setMaxResults(1).uniqueResult();
		return systemMainTable;
	}
	public List findAllSystemMainTableColumnByName(String tableName) {
		String hql = "from SystemMainTable smt where smt.tableName=?";
		List<SystemMainTable> list = super.find(hql, tableName);
		SystemMainTable smt = null;
		if(!list.isEmpty()){
			smt = list.iterator().next();
		}
		Criteria c =createCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		return c.list();
	}

	public SystemMainTable findSystemMainTableByName(String tableName) {
		// TODO Auto-generated method stub
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("tableName",tableName));
		SystemMainTable systemMainTable = (SystemMainTable)c.setMaxResults(1).uniqueResult();
		return systemMainTable;
	}

	public PageGroupPanelTable savePageGroupPanelTable(
			PageGroupPanelTable pageGroupPanelTable) {
		PageGroupPanelTable pgpt = null;
		try {
			pgpt = (PageGroupPanelTable) super.save(pageGroupPanelTable);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("保存pagePanel发生异常");
		}		
		return pgpt;
	}

	public PageGroupPanelTable findPageGroupPanelTable(String id) {
		Criteria c = super.getCriteria(PageGroupPanelTable.class);
		c.add(Restrictions.eq("id",Long.valueOf(id)));
		PageGroupPanelTable pageGroupPanelTable = (PageGroupPanelTable) c.setMaxResults(1).uniqueResult();
		return pageGroupPanelTable;
	}
}
