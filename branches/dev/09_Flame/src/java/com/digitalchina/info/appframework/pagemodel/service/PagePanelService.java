package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelFieldSet;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Module;

/**
 * PagePanel����
 * @Class Name PagePanelService
 * @Author sa
 * @Create In 2008-11-21
 */
public interface PagePanelService {
	
	/**
	 * ͨ��panel��title��ȷ��������Ӧ��panel
	 * @Methods Name findPagePanelByPanelName
	 * @Create In Dec 23, 2008 By Administrator
	 * @param panelName
	 * @return PagePanel
	 */
	public PagePanel findPagePanelByPanelName(String panelName);
	
	/**
	 * ͨ����ҳ��������Ӧҳ���panel
	 * @Methods Name findPagePanelByPageNoAndAmount
	 * @Create In Dec 23, 2008 By Administrator
	 * @param pageNo
	 * @param amount
	 * @return List
	 */
	public Page findPagePanelByPage(String factor ,String box,int pageNo, int pageSize);
	
	/**
	 * �����Ƿ�����廹����ͨ�����������Ӧ���������
	 * @Methods Name searchPagePanelByPanelName
	 * @Create In Dec 24, 2008 By Administrator
	 * @param panelName
	 * @return List
	 */
	public List searchPagePanelByPanelName(String panelName);
	
	/**
	 * 
	 * @Methods Name findPagePanelByTable
	 * @Create In 2008-12-12 By sa
	 * @param smt
	 * @return PagePanel
	 */
	PagePanel findPagePanelByTable(SystemMainTable smt, Integer settingType);
	/**
	 * ��ȡpanel�����е�ϵͳ����
	 * @Methods Name findTableByPanel
	 * @Create In 2008-12-1 By sa
	 * @param panel
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable> findTableByPanel(PagePanel panel);
	
	/**
	 * ͨ���ؼ��ֻ�ȡpagePanel
	 * @Methods Name findPagePanel
	 * @Create In 2008-12-1 By sa
	 * @param keyName
	 * @return PagePanel
	 */
	PagePanel findPagePanel(String keyName);
	/**
	 * ��ʼ��PagePanel�Ŀɼ��ֶ�
	 * @Methods Name saveColumnToPanelColumn
	 * @Create In 2008-11-24 By sa
	 * @param module
	 * @return List<PagePanelColumn>
	 */
	List<PagePanelColumn> saveColumnToPanelColumn(PagePanel panel, List<Column> columns);
	/**
	 * �ṩָ��ģ���µ�����ϵͳ����
	 * @Methods Name findTableByModule
	 * @Create In 2008-11-24 By sa
	 * @param module
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable> findTableByModule(Module module);
	/**
	 * ��ȡϵͳ����������ֶ�
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	List<Column> findColumns(SystemMainTable smt);
	
	/**
	 * ��ȡ����ָ���������͵Ŀɼ��ֶ�
	 * @Methods Name findColumns
	 * @Create In 2008-11-21 By sa
	 * @param smt
	 * @param settingType 1�б�
	 * @return List<Column>
	 */
	List<Column> findColumns(SystemMainTable smt, int settingType);
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
	Page findPagePanel(Module module, String pageName, int pageNo, int pageSize);
	
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
	 * ɾ��PagePanel
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
	void removePagePanel(String[] pagePanelId);
	
	/**
	 * ͨ��ID��ȡPagePanel
	 * @Methods Name findPagePanelById
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId
	 * @return PagePanel
	 */
	PagePanel findPagePanelById(String pagePanelId);
		
	/**
	 * ͨ��PagePanel��ȡ���µ�����pagePanel
	 * @Methods Name findPagePanelByPageModel
	 * @Create In 2008-11-21 By sa
	 * @return List ����ֵ�е�����������PagePanel
	 */
	List<PagePanel> findPagePanelByPageModel(PageModel pageModel);

	/**
	 * ��ȡָ��PagePanel��������pagePanel���ֶ�
	 * @Methods Name findPagePaneAndColumnByPagePanel
	 * @Create In 2008-11-21 By sa
	 * @param parentPagePanel
	 * @return List
	 */
	List findPagePanelAndColumnByPagePanel(PagePanel parentPagePanel);
	
	/**
	 * ����pagePanel
	 * @Methods Name savePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param pagePanel
	 * @param childPanelIds
	 * @param smtId
	 * @return PagePanel
	 */
	PagePanel savePagePanel(PagePanel pagePanel, List childPanelIds, String smtId);
	
	/**
	 * ��ȡ����PagePanelTable
	 * @Methods Name findPagePanelTable
	 * @Create In 2008-12-12 By sa
	 * @param panel
	 * @return List<PagePanelTable>
	 */
	List<PagePanelTable> findPagePanelTable(PagePanel panel);
	
	
	/**
	 * ��ȡ��pagePanel������ϵͳ����
	 * @Methods Name findMainTableByPanel
	 * @Create In 2008-11-27 By lee
	 * @param pagePanel
	 * @return List
	 */
	List<SystemMainTable> findMainTableByPanel(PagePanel panel);
	
	/**
	 * ��ȡpanel֮���Ĺ�ϵ
	 * @Methods Name findMainTableRelationByPanel
	 * @Create In 2008-12-7 By sa
	 * @param panel
	 * @return List
	 */
	List findMainTableRelationByPanel(PagePanel panel);
	
	/**
	 * �����ƶ���Module��������Ӧ��pagePanel
	 */
	List findPagePanelByModule(Module module);
	/**
	 * ��ȡֱ����pagePanel������PagePanelColumn����
	 * @Methods Name findPagePanelColumnNoParent
	 * @Create In 2008-11-28 By lee
	 * @param pagePanel
	 * @return List
	 */
	List findPagePanelColumnNoParent(PagePanel pp);
	/**
	 * ��ȡֱ����pagePanel������PagePanelColumn����
	 * @Methods Name findPagePanelColumnByPagePanelIdAndParentId
	 * @Create In 2008-11-28 By lee
	 * @param pagePanelId
	 * @param parentId
	 * @return List
	 */
	List findChildenColumnByParentId(String parentId);
	/**
	 * ֱ�ӽ�ϵͳ�����е��ֶ�ת����pagePanelColumn����ʽ����pagePanel��
	 * @Methods Name savePanelColumnsFormSysMainTable
	 * @Create In 2008-11-28 By lee
	 * @param pagePanelId
	 * @param smtId
	 * @return void
	 */
	void savePanelColumnsFormSysMainTable(String ppId,String smtId);
	
	/**
	 * ��ȡpanel��ĳ������ӱ���Ϣ�����ȡͬһ��panel�е������������Ϣ�Ĳ�����Ϣ�ӱ�
	 * @Methods Name findPanelTableRelByParent
	 * @Create In 2008-12-8 By sa
	 * @param panel
	 * @param parentSmt
	 * @return List<PagePanelTableRelation>
	 */
	List<PagePanelTableRelation> findPanelTableRelByParent(PagePanel panel, SystemMainTable parentSmt);
	
	/**
	 * ��ȡpanel��ĳ��������и�����������Ϣ����ʵ�壬���������ڷ����̣�Ҫ��ʾ�����̵Ķ���ֶ�
	 * @Methods Name findPanelTableRelBySub
	 * @Create In 2008-12-8 By sa
	 * @param panel
	 * @param subSmt
	 * @return List<PagePanelTableRelation>
	 */
	List<PagePanelTableRelation> findPanelTableRelBySub(PagePanel panel, SystemMainTable subSmt);
	/**
	 * ͨ��cnName����ֶ����õ�ϵͳ����Ķ���
	 * @Methods Name findSystemMainTable
	 * @Create In Dec 17, 2008 By Administrator
	 * @param tableCnName
	 * @return SystemMainTable
	 */
	public SystemMainTable findSystemMainTable(String tableId);
	public SystemMainTable findSystemMainTableByName(String tableName);
	/**
	 * ͨ��xtype����ֶ����õ�panel�Ķ���
	 * @Methods Name findPagePanelByXtype
	 * @Create In Dec 18, 2008 By Administrator
	 * @param xtype
	 * @return PagePanel
	 */
	public PagePanelType findPagePanelTypeByXtype(String xtype);
	
	/**
	 * ��ȡ���еķ��������������
	 * @Methods Name findAllGroupPanelTypes
	 * @Create In 2008-12-28 By sa
	 * @return List<PagePanelType>
	 */
	public List<PagePanelType> findAllGroupPanelTypes();
	
	/**
	 * ��ȡ���еĻ��������������
	 * @Methods Name findAllBasePanelTypes
	 * @Create In 2008-12-28 By sa
	 * @return List<PagePanelType>
	 */
	public List<PagePanelType> findAllBasePanelTypes();
	
	public List findAllTable();
	public SystemMainTable findSystemMainTableByCnName(String cnName);
	public List findAllSystemMainTableColumnByName(String tableName);
	public SystemMainTableColumn findSystemMainTableColumn(String CID);
	PagePanelTableRelation savePagePanelTableRelation(PagePanelTableRelation pptr);
	void removePagePanelTableRelation(String id);
	
	/**
	 * ϵͳ����combobox�ķ�ҳ
	 * @Methods Name findSystemMainTable
	 * @Create In Dec 29, 2008 By Administrator
	 * @param pageNo
	 * @param pageSize
	 * @return List
	 */
	public Page findSystemMainTable(int pageNo,int pageSize);
	/**
	 * ����ϵͳ�����id���������combobox�ķ�ҳ
	 * @Methods Name findForeignKey
	 * @Create In Dec 29, 2008 By Administrator
	 * @param sysTabelId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findForeignKey(Long sysTabelId , int pageNo,int pageSize);

    /**
	 * ����ϵͳmodule������pagePanel�ķ�ҳ
	 * @Methods Name findForeignKey
	 * @Create In Dec 29, 2008 By Administrator
	 * @param sysTabelId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findPanelByPageModule(Module module, int pageNo,int pageSize);
	
	public List<PageGroupPanelTable> findPageGroupPanelTableByPanel(PagePanel pagePanel);
	
	void removePageGroupPanelTable(String id);
	public List findAllPagePanel();
	public PageGroupPanelTable savePageGroupPanelTable(PageGroupPanelTable pageGroupPanelTable);
	public PageGroupPanelTable findPageGroupPanelTable(String id);
	/**
	 * ����PagePanelColumn
	 * @Methods Name savePagePanelColumn
	 * @Create In May 13, 2009 By Administrator
	 * @param pagePanel
	 * @param index
	 * @return PagePanelColumn
	 */
	public PagePanelColumn savePagePanelColumn(PagePanel pagePanel,String index);
	public PagePanelFieldSet savePagePanelFieldSet(PagePanelColumn pagePanelColumn,PagePanel pagePanel ,String title);
	
}
