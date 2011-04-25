package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTableRelation;

public interface PagePanelTableRelationService {
	/**
	 * 保存
	 * @Methods Name save
	 * @Create In 2008-12-22 By lee
	 * @param pagePanelTableRelation
	 * @return PagePanelTableRelation
	 */
	PagePanelTableRelation save(PagePanelTableRelation pagePanelTableRelation);
	/**
	 * 删除主表为指定主表的关联关系，级联删除外键关联关系
	 * @Methods Name remove
	 * @Create In 2008-12-22 By lee
	 * @param pagePanel
	 * @param SystemMainTable
	 * @return PagePanelTableRelation
	 */
	void remove(PagePanel pagePanel,SystemMainTable smt);
	/**
	 * 获取panel相关联的面板内表关系
	 * @Methods Name findRelationsByPanel
	 * @Create In May 27, 2009 By lee
	 * @param pagePanel
	 * @return List<PagePanelTableRelation>
	 */
	List<PagePanelTableRelation> findRelationsByPanel(PagePanel pagePanel);
	
}
