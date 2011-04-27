package com.zsgj.info.appframework.pagemodel.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;

/**
 * PagePanelColumns服务
 * @Class Name PagePanelColumnsService
 * @Author lee
 * @Create In 2008-11-26
 */
public interface PagePanelColumnService {
	/**
	 * 获取PagePanelColumn
	 * @Methods Name findPanelColumn
	 * @Create In 2008-11-26 By lee
	 * @param pagePanel
	 * @param column
	 * @return PagePanelColumn
	 */
	PagePanelColumn findPanelColumn(PagePanel pagePanel,Column column);
	/**
	 * 获取PagePanel的可见字段
	 * @Methods Name findColumnByPanel
	 * @Create In 2008-11-26 By lee
	 * @param panel
	 * @return List<PagePanelColumn>
	 */
	List<PagePanelColumn> findColumnByPanel(PagePanel panel);
	/**
	 * 删除PagePanel的可见字段
	 * @Methods Name removePanelColumn
	 * @Create In 2008-11-26 By lee
	 * @param ppcId void
	 */
	void removePanelColumn(String ppcId);
	/**
	 * 删除选定的PagePanel的可见字段
	 * @Methods Name removePanelColumn
	 * @Create In 2008-11-26 By lee
	 * @param ppcIds void
	 */
	void removePanelColumn(String[] ppcIds);
	/**
	 * 通过ID获取PagePanelColumn
	 * @Methods Name findPagePaneColumnlById
	 * @Create In 2008-11-27 By lee
	 * @param pagePanelColumnId
	 * @return PagePanelColumn
	 */
	PagePanelColumn findPagePaneColumnlById(String pagePanelColumnId);
	/**保存PagePanelColumn
	 * @Methods Name savePagePanelColumn
	 * @Create In 2008-11-27 By lee
	 * @param pagePanelColumn
	 * @return PagePanelColumn
	 */
	PagePanelColumn savePagePanelColumn(PagePanelColumn pagePanelColumn);
	/**保存PagePanelColumn是否显示
	 * @Methods Name saveColumnIsDisplay
	 * @Create In 2008-11-27 By lee
	 * @param pagePanelColumnId
	 * @param isDisplay void
	 */
	void saveColumnIsDisplay(String ppcId, String isDisplay);
	/**
	 * 保存PagePanelColumn的移动
	 * @Methods Name savePagePanelColumnMove
	 * @Create In 2008-12-2 By lee
	 * @param panelId 当前移动的column编号
	 * @param oldParentId 当前移动的column的父节点
	 * @param newParentId 移动到的目标父节点
	 * @param nodeIndex 当前移动的column节点的排序号
	 * void
	 */
	void savePagePanelColumnMove(String columnId, String oldParentId, String newParentId, String nodeIndex);
	/**
	 * 删除与指定系统主表关联的PagePanelColumn
	 * @Methods Name removePanelColumn
	 * @Create In 2008-11-26 By lee
	 * @param pagePanelId
	 * @param systemMainTableId
	 * @return void
	 */
	void removePanelColumn(String ppId,String smtId);
}
