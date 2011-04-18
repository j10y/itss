package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;

/**
 * PagePanelTable服务
 * @Class Name PagePanelTableService
 * @Author lee
 * @Create In 2008-12-1
 */
public interface PagePanelTableService {
	/**
	 * 保存
	 * @Methods Name save
	 * @Create In 2008-12-1 By lee
	 * @param pagePanelId
	 * @param smtId
	 * @return PagePanelTable
	 */
	PagePanelTable save(String ppId,String smtId);
	/**
	 * 保存
	 * @Methods Name save
	 * @Create In 2008-12-1 By lee
	 * @param pagePanel
	 * @param smt
	 * @return PagePanelTable
	 */
	PagePanelTable save(PagePanel pagePanel,SystemMainTable smt);
	/**
	 * 删除所有与pagePanel有关的PagePanelTable，级联删除panel下的所有字段
	 * @Methods Name removeAll
	 * @Create In 2008-12-5 By lee
	 * @param pagePanel void
	 */
	void removeAll(PagePanel pagePanel);
	/**
	 * 删除PagePanelTable，级联删除panel下的所有字段
	 * @Methods Name remove
	 * @Create In 2008-12-5 By sa
	 * @param PagePanelTable void
	 */
	void removePagePanelTable(String[] pagePanelTableIds);
	
	
	/**
	 * 删除PagePanelTable，级联删除panel下的所有字段
	 * @Methods Name remove
	 * @Create In 2008-12-5 By sa
	 * @param PagePanelTable void
	 */
	void removePagePanelTable(String pagePanelTableId);
	
	/**
	 * 删除
	 * @Methods Name remove
	 * @Create In 2008-12-1 By lee
	 * @param pagePanelId
	 * @param smtId
	 * @return void
	 */
	void remove(String ppId,String smtId);
	/**
	 * 检验系统主表是否已经在pagePanel中存在
	 * @Methods Name isExist
	 * @Create In 2008-12-1 By lee
	 * @param pagePanelId
	 * @param smtId
	 * @return boolean
	 */
	boolean isExist(String ppId,String smtId);
	/**
	 * 获取已加入pagePanel的系统主表
	 * @Methods Name findSystemMainTableByPanel
	 * @Create In 2008-12-1 By lee
	 * @param pagePanel
	 * @return List
	 */
	List<SystemMainTable> findSystemMainTableByPanel(PagePanel pp);
	/**
	 * 获取PagePanelTable
	 * @Methods Name findPagePanelTableById
	 * @Create In 2008-12-1 By lee
	 * @param pagePanelTableId
	 * @return PagePanelTable
	 */
	PagePanelTable findPagePanelTableById(String pagePanelTableId);

	
}
