package com.zsgj.info.appframework.pagemodel.service.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.service.PagePanelColumnService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.exception.ServiceException;

public class PagePanelColumnServiceImpl extends BaseDao implements PagePanelColumnService{

	public PagePanelColumn findPanelColumn(PagePanel pagePanel,Column column) {
		PagePanelColumn result = null;
		String hql="";
//		if(column instanceof SystemMainTableColumn){
			hql = "select ppc from PagePanelColumn ppc where ppc.pagePanel=? and ppc.mainTableColumn=?";
			column=(SystemMainTableColumn)column;
//		}
//		if(column instanceof SystemMainTableExtColumn){
//			hql = "select ppc from PagePanelColumn ppc where ppc.pagePanel=? and ppc.extendTableColumn=?";
//			column=(SystemMainTableColumn)column;
//		}
		Query query = super.createQuery(hql, new Object[]{pagePanel, column});
		Object object = query.uniqueResult();
		if(object==null) return null;
		result = (PagePanelColumn) object;
		return result;
	}
	public List<PagePanelColumn> findColumnByPanel(PagePanel panel) {
		Criteria c = super.getCriteria(PagePanelColumn.class);
		c.add(Restrictions.eq("pagePanel", panel));
		c.add(Restrictions.eq("isDisplay", Integer.valueOf(1)));
		c.setFetchMode("mainTableColumn", FetchMode.JOIN);
		c.addOrder(Order.asc("order"));
		List list = c.list();
		return list;
	}
	public void removePanelColumn(String ppcId) {
		PagePanelColumn ppc=findPagePaneColumnlById(ppcId);
		Integer order=ppc.getOrder();
		PagePanel pp=ppc.getPagePanel();
		PagePanelColumn parentColumn=ppc.getParentPagePanelColumn();
		super.executeUpdate("update PagePanelColumn ppc set ppc.order=ppc.order-1 " +
				"where ppc.parentPagePanelColumn=? and ppc.pagePanel=? and ppc.order>?",
				new Object[]{parentColumn,pp,order});
		super.executeUpdate("delete from PagePanelColumn ppc where ppc.id=?", 
				Long.valueOf(ppcId));
	}
	public void removePanelColumn(String[] ppcIds) {
		for(String ppcId: ppcIds){
			this.removePanelColumn(ppcId);
		}
	}
	public PagePanelColumn findPagePaneColumnlById(String pagePanelColumnId) {
		PagePanelColumn result = null;
		try {
			result = super.get(PagePanelColumn.class, Long.valueOf(pagePanelColumnId));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("获取PagePanelColumn发生异常");
		}		
		return result;
	}
	public PagePanelColumn savePagePanelColumn(PagePanelColumn pagePanelColumn) {
		PagePanelColumn result = null;
		result = (PagePanelColumn) super.save(pagePanelColumn);
		return result;
	}
	public void saveColumnIsDisplay(String ppcId, String isDisplay) {
		//Integer isDisplayValue=new Integer(isDisplay);
		//System.out.println("update PagePanelColumn ppc set ppc.isDisplay = "+isDisplay+" where ppc.id ="+ppcId);
		String hql="update PagePanelColumn ppc set ppc.isDisplay = ? where ppc.id =?";
		super.executeUpdate(hql, Integer.valueOf(isDisplay), Long.valueOf(ppcId));
	}
	public void removePanelColumn(String ppId, String smtId) {
		super.executeUpdate("delete from PagePanelColumn ppc where ppc.pagePanel.id=? and ppc.systemMainTable.id=?", 
				Long.valueOf(ppId),Long.valueOf(smtId));
		
	}
	public void savePagePanelColumnMove(String mId, String oldPid, String newPid, String nodeIndex) {
		int index = Integer.parseInt(nodeIndex);
		PagePanelColumn curColumn = findPagePaneColumnlById(mId);
		PagePanelColumn oldParentColumn = (oldPid.equals("0")? null:findPagePaneColumnlById(oldPid));
		PagePanelColumn newParentColumn = (newPid.equals("0")? null:findPagePaneColumnlById(newPid));
		PagePanel pp=curColumn.getPagePanel();
//		String hql="update PagePanelColumn ppc set ppc.order=ppc.order-1 " +
//				"where ppc.parentPagePanelColumn=?" +
//				"and ppc.pagePanel=?" +
//				"and ppc.order>?";
//		super.executeUpdate(hql,new Object[]{oldParentColumn,pp,Integer.valueOf(curColumn.getOrder())});
//			hql="update PagePanelColumn ppc set ppc.order=ppc.order+1 " +
//			"where ppc.parentPagePanelColumn=?" +
//			"and ppc.pagePanel=?" +
//			"and ppc.order>=?";
//		super.executeUpdate(hql,new Object[]{newParentColumn,pp,Integer.valueOf(index+1)});
		int curOrder=curColumn.getOrder();
		List<PagePanelColumn> list=findColumnByPanel(pp);
		for(PagePanelColumn column:list){
			if(column.getParentPagePanelColumn()==oldParentColumn&&column.getOrder()>curOrder){
				column.setOrder(column.getOrder()-1);
				super.save(column);
			}
			if(column.getParentPagePanelColumn()==newParentColumn&&column.getOrder()>=index+1){
				column.setOrder(column.getOrder()+1);
				super.save(column);
			}
		}
		if(!oldPid.equals(newPid))
			curColumn.setParentPagePanelColumn(newParentColumn);
		curColumn.setOrder(index+1);
		super.save(curColumn);
	}
}
