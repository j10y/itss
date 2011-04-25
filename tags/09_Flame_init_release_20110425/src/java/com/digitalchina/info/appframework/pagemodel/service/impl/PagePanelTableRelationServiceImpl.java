package com.digitalchina.info.appframework.pagemodel.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelTableRelationService;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.exception.ServiceException;

public class PagePanelTableRelationServiceImpl extends BaseDao implements PagePanelTableRelationService{

	public PagePanelTableRelation save(PagePanelTableRelation pagePanelTableRelation) {
		PagePanelTableRelation result=null;
		try{
			result=(PagePanelTableRelation) super.save(pagePanelTableRelation);
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("保存pagePanelTableRelation发生异常");
		}
		return result;
	}

	public void remove(PagePanel pagePanel, SystemMainTable smt) {
		super.executeUpdate("delete from PagePanelTableRelation pptr where pptr.pagePanel=? and pptr.systemMainTable=?", 
				new Object[]{pagePanel,smt});
		super.executeUpdate("delete from PagePanelTableRelation pptr where pptr.pagePanel=? and pptr.foreignTable=?", 
				new Object[]{pagePanel,smt});
	}

	public List<PagePanelTableRelation> findRelationsByPanel(PagePanel pagePanel) {
		return super.findBy(PagePanelTableRelation.class, "pagePanel", pagePanel);
	}
}
