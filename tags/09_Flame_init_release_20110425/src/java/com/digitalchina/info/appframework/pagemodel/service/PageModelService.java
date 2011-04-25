package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanelMiddleTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Module;

public interface PageModelService{
	/**
	 * ͨ��id��ȡҳ��ģ��
	 * @Methods Name findPageModelById
	 * @Create In Jul 1, 2009 By lee
	 * @param id
	 * @return PageModel
	 */
	PageModel findPageModelById(String id);
	/**
	 * ��ȡҳ��·��by�������ڵ�
	 * @Methods Name findPageModelByNode
	 * @Create In 2009-1-5 By sa
	 * @param node
	 * @return PageModel
	 */
	PageModel findPageModelByNode(String node);
	/**
	 * ��ȡָ��ҳ��·����ҳ��ģ�͸���
	 * @Methods Name findPageModelCountByPagePath
	 * @Create In 2008-12-30 By sa
	 * @param pagePath
	 * @return Long
	 */
	boolean existPageModelCountByPagePath(String pagePath);
	
	/**
	 * ��ȡPageModel������ץȡ������panel������ʼ����pagePanels����
	 * @Methods Name findPageModelWithPanels
	 * @Create In 2008-11-16 By sa
	 * @param pageModelId
	 * @return PageModel
	 */
	PageModel findPageModelWithPanels(String pageModelId);
	
	/**
	 * ͨ��pageModel�Ĺؼ��ֻ�ȡpageModel
	 * @Methods Name findPageModel
	 * @Create In 2008-12-5 By sa
	 * @param keyName
	 * @return PageModel
	 */
	PageModel findPageModel(String keyName);
	
	/**
	 * ͨ��pageModel�Ĺؼ��ֻ�ȡpageModel
	 * @Methods Name findPageModel
	 * @Create In 2008-12-19 By sa
	 * @param keyName
	 * @return PageModel
	 */
	 PageModel findPageModel$$$$$(String keyName);
	/**
	 * ����PageModel
	 * @Methods Name savePageModel
	 * @Create In 2008-11-16 By sa
	 * @param pageModel
	 * @return PageModel
	 */
	PageModel savePageModel(PageModel pageModel);
	
	/**
	 * ɾ��PagePanel
	 * @Methods Name removePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId void
	 */
	void removePageModel(String[] pagePanelId);
	
	/**
	 * ��ʾPageModel�б���������module��pageModel���ƻ�ؼ���
	 * @Methods Name findPageModel
	 * @Create In 2008-11-16 By sa
	 * @param module
	 * @param pageName
	 * @param pageNo
	 * @param pageSize
	 * @return Page ����page��ļ���������PageModel����
	 */
	Page findPageModel(Module module, String pageName, int pageNo, int pageSize);
	
	/**
	 * ��ʾPageModel�б���������module��pageModel���ƻ�ؼ���
	 * @Methods Name findPageModel
	 * @Create In 2008-11-23 By sa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findPageModel(Map params, int pageNo, int pageSize);

	/**����pageModelName��������Ӧ��pageModel
	 * 
	 */
	PageModel findPageModelByPageModelName(String pageModelName);
	
	/**
	 * �����޸ĵİ�ťid��������Ӧ�İ�ť��¼
	 */
	PageModelBtn findPageModelBtnByModifyId(String id);
	/**
	 * �ڱ���pageModel��ͬʱҲҪ��Ӧ����pagemModelBtn,����settingType���ж�ʲô���İ�ťӦ�ñ���Ӧ
	 * �ı��档
	 */
	void savePageModelBtn(PageModel pageModel);
	
	/**
	 * ��ȡpageModel������PageModelPanelTable��Ϣ
	 * @Methods Name findPageModelPanelTableByModel
	 * @Create In 2008-12-5 By sa
	 * @param pageModel
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelTable> findPageModelPanelTableByModel(PageModel pageModel);
	
	/**
	 * ͨ����ȡPageModelPanelTable
	 * @Methods Name findPageModelPanelTableByModel
	 * @Create In 2008-12-7 By sa
	 * @param pageModel
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelTable> findPageModelPanelTable(PageModel pageModel, PagePanel pagePanel);
	
	/**
	 * ͨ����panel����panel������ȡPageModelPanelTable
	 * @Methods Name findPageModelPanelTableByModel
	 * @Create In 2008-12-7 By sa
	 * @param pageModel
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelTable> findPageModelPanelTableBySub(PageModel pageModel, PagePanel subPagePanel, SystemMainTable subTable);
	
	/**
	 * ͨ��ҳ��ģ�ͣ�����������������ȡ
	 * @Methods Name findPageModelPanelMiddleTableBySub
	 * @Create In 2009-3-11 By sa
	 * @param pageModel
	 * @param subPagePanel
	 * @param subTable
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelMiddleTable> findPageModelPanelMiddleTableBySub(PageModel pageModel, 
				PagePanel subPagePanel, SystemMainTable subTable);
	
	
	/**
	 * ͨ����panel�͸�panel������ȡ�������ô�panel����panel������
	 * @Methods Name findPageModelPanelTableByParent
	 * @Create In 2008-12-8 By sa
	 * @param pageModel
	 * @param parentPagePanel
	 * @param parentTable
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelTable> findPageModelPanelTableByParent(PageModel pageModel, PagePanel parentPagePanel, SystemMainTable parentTable);
	
	/**
	 * ͨ��pageModel��ȡmodel�еİ�ť
	 * @Methods Name findPageModelBtnByModel
	 * @Create In 2008-12-16 By lee
	 * @param pageModel
	 * @return List<PageModelBtn>
	 */
	List<PageModelBtn> findPageModelBtnByModel(PageModel pageModel);
	
	/**
	 * ͨ��ѡ���ģ�����õ���Ӧ��ϵͳ����
	 * @Methods Name findSystemMainTable
	 * @Create In Dec 17, 2008 By Administrator
	 * @param module
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable> findSystemMainTable(Module module);
	void removePageModelPanelTable(String id);
	List findAllPagePanel();
	PagePanel findPagePanelById(String id);
	List findAllMainTableByPanel(PagePanel pagePanel);
	SystemMainTable findSystemMainTable(String tableId);
	List findAllSystemMainTableColumnByName(String tableName);
	//SystemMainTableColumn findSystemMainTablePrimaryKeyColumn(SystemMainTableColumn systemMainTableColumn);
	PageModelPanelTable savePageModelPanelTable(PageModelPanelTable pageModelPanelTable);
	PageModelPanelTable findPageModelPanelTable(String id);
}
