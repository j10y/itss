package com.zsgj.info.appframework.pagemodel.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;

/**
 * PagePanelColumns����
 * @Class Name PagePanelColumnsService
 * @Author lee
 * @Create In 2008-11-26
 */
public interface PagePanelColumnService {
	/**
	 * ��ȡPagePanelColumn
	 * @Methods Name findPanelColumn
	 * @Create In 2008-11-26 By lee
	 * @param pagePanel
	 * @param column
	 * @return PagePanelColumn
	 */
	PagePanelColumn findPanelColumn(PagePanel pagePanel,Column column);
	/**
	 * ��ȡPagePanel�Ŀɼ��ֶ�
	 * @Methods Name findColumnByPanel
	 * @Create In 2008-11-26 By lee
	 * @param panel
	 * @return List<PagePanelColumn>
	 */
	List<PagePanelColumn> findColumnByPanel(PagePanel panel);
	/**
	 * ɾ��PagePanel�Ŀɼ��ֶ�
	 * @Methods Name removePanelColumn
	 * @Create In 2008-11-26 By lee
	 * @param ppcId void
	 */
	void removePanelColumn(String ppcId);
	/**
	 * ɾ��ѡ����PagePanel�Ŀɼ��ֶ�
	 * @Methods Name removePanelColumn
	 * @Create In 2008-11-26 By lee
	 * @param ppcIds void
	 */
	void removePanelColumn(String[] ppcIds);
	/**
	 * ͨ��ID��ȡPagePanelColumn
	 * @Methods Name findPagePaneColumnlById
	 * @Create In 2008-11-27 By lee
	 * @param pagePanelColumnId
	 * @return PagePanelColumn
	 */
	PagePanelColumn findPagePaneColumnlById(String pagePanelColumnId);
	/**����PagePanelColumn
	 * @Methods Name savePagePanelColumn
	 * @Create In 2008-11-27 By lee
	 * @param pagePanelColumn
	 * @return PagePanelColumn
	 */
	PagePanelColumn savePagePanelColumn(PagePanelColumn pagePanelColumn);
	/**����PagePanelColumn�Ƿ���ʾ
	 * @Methods Name saveColumnIsDisplay
	 * @Create In 2008-11-27 By lee
	 * @param pagePanelColumnId
	 * @param isDisplay void
	 */
	void saveColumnIsDisplay(String ppcId, String isDisplay);
	/**
	 * ����PagePanelColumn���ƶ�
	 * @Methods Name savePagePanelColumnMove
	 * @Create In 2008-12-2 By lee
	 * @param panelId ��ǰ�ƶ���column���
	 * @param oldParentId ��ǰ�ƶ���column�ĸ��ڵ�
	 * @param newParentId �ƶ�����Ŀ�길�ڵ�
	 * @param nodeIndex ��ǰ�ƶ���column�ڵ�������
	 * void
	 */
	void savePagePanelColumnMove(String columnId, String oldParentId, String newParentId, String nodeIndex);
	/**
	 * ɾ����ָ��ϵͳ���������PagePanelColumn
	 * @Methods Name removePanelColumn
	 * @Create In 2008-11-26 By lee
	 * @param pagePanelId
	 * @param systemMainTableId
	 * @return void
	 */
	void removePanelColumn(String ppId,String smtId);
}
