package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Module;

/**
 * �������͵����������
 * @Class Name PageGroupPanelService
 * @Author sa
 * @Create In 2008-12-18
 */
public interface PageGroupPanelService {
	/**
	 * ��ѯPagePanel����ֹpagePanel���࣬�ʿ��԰���������ģ���panel�����ƽ��в�ѯ
	 * @Methods Name findPagePanel
	 * @Create In 2008-11-21 By sa
	 * @param module
	 * @param pageName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findPagePanel(Module module, String nameOrTitle, int pageNo, int pageSize);
	
	/**
	 * ��ѯPagePanel����ֹpagePanel���࣬�ʿ��԰���������ģ���panel�����ƽ��в�ѯ
	 * @Methods Name findPagePanel
	 * @Create In 2008-11-23 By sa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findPagePanel(Map params, int pageNo, int pageSize);
	
	/**
	 * ����pagePanel
	 * @Methods Name savePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param panel
	 * @return PagePanel
	 */
	PagePanel savePagePanel(PagePanel panel);
	
	/**
	 * ����PagePanel���ƶ�
	 * @Methods Name savePagePanelMove
	 * @Create In 2008-11-23 By sa
	 * @param panelId ��ǰ�ƶ���panel���
	 * @param oldParentId ��ǰ�ƶ���panel�ĸ��ڵ�
	 * @param newParentId �ƶ�����Ŀ�길�ڵ�
	 * @param nodeIndex ��ǰ�ƶ���panel�ڵ�������
	 * void
	 */
	void savePagePanelMove(String panelId, String oldParentId, String newParentId, String nodeIndex);
	
	/**
	 * ɾ��PagePanel, ɾ���ϼ�panelʱҪ�����panel��
	 * @Methods Name removePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId void
	 */
	void removePagePanel(String pagePanelId);
	
	/**
	 * ɾ��PagePanel
	 * @Methods Name removePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId void
	 */
	void removePagePanel(String[] pagePanelIds);
	
	/**
	 * ͨ��ID��ȡPagePanel
	 * @Methods Name findPagePanelById
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId
	 * @return PagePanel
	 */
	PagePanel findPagePanelById(String pagePanelId);
	
	/**
	 * ��ȡ����PagePanelTable
	 * @Methods Name findPagePanelTable
	 * @Create In 2008-12-12 By sa
	 * @param panel
	 * @return List<PagePanelTable>
	 */
	List<PagePanelTable> findPagePanelTable(PagePanel panel);

	/**
	 * ��ȡĳ�������е���������
	 * @Methods Name findMainTableByPanel
	 * @Create In 2008-12-30 By peixf
	 * @param pagePanel
	 * @return List
	 */
	List findMainTableByPanel(PagePanel pagePanel);

	/**
	 * ��ȡĳ������������ֶ�
	 * @Methods Name findColumns
	 * @Create In 2008-12-30 By peixf
	 * @param smt
	 * @param settingType �б�ҳ�滹������ҳ��
	 * @return List<Column>
	 */
	List<Column> findColumns(SystemMainTable smt, Integer settingType);
	
	/**
	 * ��ȡһ����������У����������и����������Ϣ��
	 * ǰ�˵�grid����ȡ��ѯ����ʱʹ�ô˷�����ȡ�����������ֶ�����
	 * @Methods Name findGroupPanelTable
	 * @Create In 2008-12-29 By sa
	 * @param groupPanel ������壬��ͻ���Ϣ�������
	 * @param subPanel ����壬���ڲ��ͻ���ϵ�����
	 * @return List<PageGroupPanelTable>
	 */
	List<PageGroupPanelTable> findGroupPanelTableBySub(PagePanel groupPanel, PagePanel subPanel);
	
	/**
	 * ��ȡһ����������У���������������壬
	 * �����������е����ݱ���ʱ���ȱ������������ݣ�Ȼ��
	 * ȡ���е��������Ϣ���ֱ𱣴������������
	 * @Methods Name findGroupPanelTableByParent
	 * @Create In 2009-1-1 By sa
	 * @param groupPanel
	 * @param subPanel
	 * @return List<PageGroupPanelTable>
	 */
	List<PageGroupPanelTable> findGroupPanelTableByParent(PagePanel groupPanel, PagePanel subPanel);
	
	/**
	 * ͨ����������ȡ�����������Ĺ�ϵPageGroupPanelTable
	 * @Methods Name findAllPageGroupPanelTableByGroupPanel
	 * @Create In 2008-12-30 By peixf
	 * @param groupPanel
	 * @return List<PageGroupPanelTable>
	 */
	List<PageGroupPanelTable> findAllPageGroupPanelTableByGroupPanel(PagePanel groupPanel);
	
	void removePageGroupPanelTable(String id);
	public List findAllPagePanel();
	public SystemMainTable findSystemMainTable(String tableId);
	public SystemMainTable findSystemMainTableByName(String tableName);
	public List findAllSystemMainTableColumnByName(String tableName);
	public PageGroupPanelTable savePageGroupPanelTable(PageGroupPanelTable pageGroupPanelTable);
	public PageGroupPanelTable findPageGroupPanelTable(String id);
}
