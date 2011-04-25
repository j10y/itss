package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTableRelation;

public interface PagePanelTableRelationService {
	/**
	 * ����
	 * @Methods Name save
	 * @Create In 2008-12-22 By lee
	 * @param pagePanelTableRelation
	 * @return PagePanelTableRelation
	 */
	PagePanelTableRelation save(PagePanelTableRelation pagePanelTableRelation);
	/**
	 * ɾ������Ϊָ������Ĺ�����ϵ������ɾ�����������ϵ
	 * @Methods Name remove
	 * @Create In 2008-12-22 By lee
	 * @param pagePanel
	 * @param SystemMainTable
	 * @return PagePanelTableRelation
	 */
	void remove(PagePanel pagePanel,SystemMainTable smt);
	/**
	 * ��ȡpanel�����������ڱ��ϵ
	 * @Methods Name findRelationsByPanel
	 * @Create In May 27, 2009 By lee
	 * @param pagePanel
	 * @return List<PagePanelTableRelation>
	 */
	List<PagePanelTableRelation> findRelationsByPanel(PagePanel pagePanel);
	
}
