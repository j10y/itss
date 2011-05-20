package com.zsgj.info.appframework.pagemodel.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTable;
import com.zsgj.info.appframework.pagemodel.service.PagePanelTableService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.exception.ServiceException;

public class PagePanelTableServiceImpl extends BaseDao implements PagePanelTableService{

	public boolean isExist(String ppId, String smtId) {
		if(ppId==null||ppId.equals("")||smtId==null||smtId.equals(""))
			return false;
		String hql="select ppt from PagePanelTable ppt where ppt.pagePanel.id=?and ppt.SystemMainTable.id=?";
		List list=super.find(hql, new Object[]{Long.valueOf(ppId), Long.valueOf(smtId)});
		return !list.isEmpty();
	}

	public PagePanelTable save(String ppId, String smtId) {
		PagePanelTable ppt=new PagePanelTable();
		ppt.setPagePanel(get(PagePanel.class,Long.valueOf(ppId)));
		ppt.setSystemMainTable(get(SystemMainTable.class,Long.valueOf(smtId)));
		super.save(ppt);
		return ppt;
	}

	public List<SystemMainTable> findSystemMainTableByPanel(PagePanel pp) {
		String hql="select ppt.systemMainTable from PagePanelTable ppt where ppt.pagePanel=?";
		List<SystemMainTable> list=super.find(hql,pp);
		return list;
	}

	public void remove(String ppId, String smtId) {
		String hql="delete from PagePanelTable ppt where ppt.pagePanel.id=? and ppt.systemMainTable.id=?";
		super.executeUpdate(hql, new Object[]{Long.valueOf(ppId),Long.valueOf(smtId)});
	}

	public void removePagePanelTable(String pagePanelTableId) {
		PagePanelTable ppt = super.get(PagePanelTable.class, Long.valueOf(pagePanelTableId));
		PagePanel panel = ppt.getPagePanel();
		SystemMainTable smt = ppt.getSystemMainTable();
		
		super.executeUpdate("delete from PagePanelColumn ppc " +
				"where ppc.pagePanel.id=? and ppc.systemMainTable.id=?", 
				panel.getId(), smt.getId());
		
		super.remove(ppt);
		
		
	}

	public void removePagePanelTable(String[] pagePanelTables) {
		if(pagePanelTables==null|| pagePanelTables.length==0){
			throw new ServiceException("请选择要删除的panelTable");
		}
		for(String pagePanelTableId: pagePanelTables){
			this.removePagePanelTable(pagePanelTableId);
		}
		
	}

	public PagePanelTable save(PagePanel pagePanel, SystemMainTable systemMainTable) {
		PagePanelTable ppt=new PagePanelTable();
		ppt.setPagePanel(pagePanel);
		ppt.setSystemMainTable(systemMainTable);
		PagePanelTable result=null;
		try{
			result=(PagePanelTable) super.save(ppt);
		}catch(DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("保存pagePanelTable发生异常");
		}
		return result;
	}

	public void removeAll(PagePanel pagePanel) {
		String hql="select ppt from PagePanelTable ppt where ppt.pagePanel=?";
		List<PagePanelTable> list=super.find(hql,pagePanel);
		for(PagePanelTable ppt:list){
			removePagePanelTable(ppt.getId().toString());
		}
	}

	public PagePanelTable findPagePanelTableById(String pagePanelTableId) {
		PagePanelTable result = null;
		try {
			result = super.get(PagePanelTable.class, Long.valueOf(pagePanelTableId));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("获取PagePanelTable发生异常");
		}		
		return result;
	}

}
