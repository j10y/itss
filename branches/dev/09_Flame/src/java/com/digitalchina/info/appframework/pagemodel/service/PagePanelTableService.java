package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;

/**
 * PagePanelTable����
 * @Class Name PagePanelTableService
 * @Author lee
 * @Create In 2008-12-1
 */
public interface PagePanelTableService {
	/**
	 * ����
	 * @Methods Name save
	 * @Create In 2008-12-1 By lee
	 * @param pagePanelId
	 * @param smtId
	 * @return PagePanelTable
	 */
	PagePanelTable save(String ppId,String smtId);
	/**
	 * ����
	 * @Methods Name save
	 * @Create In 2008-12-1 By lee
	 * @param pagePanel
	 * @param smt
	 * @return PagePanelTable
	 */
	PagePanelTable save(PagePanel pagePanel,SystemMainTable smt);
	/**
	 * ɾ��������pagePanel�йص�PagePanelTable������ɾ��panel�µ������ֶ�
	 * @Methods Name removeAll
	 * @Create In 2008-12-5 By lee
	 * @param pagePanel void
	 */
	void removeAll(PagePanel pagePanel);
	/**
	 * ɾ��PagePanelTable������ɾ��panel�µ������ֶ�
	 * @Methods Name remove
	 * @Create In 2008-12-5 By sa
	 * @param PagePanelTable void
	 */
	void removePagePanelTable(String[] pagePanelTableIds);
	
	
	/**
	 * ɾ��PagePanelTable������ɾ��panel�µ������ֶ�
	 * @Methods Name remove
	 * @Create In 2008-12-5 By sa
	 * @param PagePanelTable void
	 */
	void removePagePanelTable(String pagePanelTableId);
	
	/**
	 * ɾ��
	 * @Methods Name remove
	 * @Create In 2008-12-1 By lee
	 * @param pagePanelId
	 * @param smtId
	 * @return void
	 */
	void remove(String ppId,String smtId);
	/**
	 * ����ϵͳ�����Ƿ��Ѿ���pagePanel�д���
	 * @Methods Name isExist
	 * @Create In 2008-12-1 By lee
	 * @param pagePanelId
	 * @param smtId
	 * @return boolean
	 */
	boolean isExist(String ppId,String smtId);
	/**
	 * ��ȡ�Ѽ���pagePanel��ϵͳ����
	 * @Methods Name findSystemMainTableByPanel
	 * @Create In 2008-12-1 By lee
	 * @param pagePanel
	 * @return List
	 */
	List<SystemMainTable> findSystemMainTableByPanel(PagePanel pp);
	/**
	 * ��ȡPagePanelTable
	 * @Methods Name findPagePanelTableById
	 * @Create In 2008-12-1 By lee
	 * @param pagePanelTableId
	 * @return PagePanelTable
	 */
	PagePanelTable findPagePanelTableById(String pagePanelTableId);

	
}
