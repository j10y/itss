package com.digitalchina.info.appframework.pagemodel;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.dao.support.Page;

public interface PageManager {
	/**
	 * ͨ��pageModel�ؼ��ֻ�ȡpageModel
	 * @Methods Name findPageModel
	 * @Create In 2008-11-24 By sa
	 * @param pageKeyName
	 * @return PageModel
	 */
	PageModel findPageModel(String pageKeyName);
//	
//	/**
//	 * ����pageModel������panel���е�����
//	 * @Methods Name savePageModelData
//	 * @Create In 2008-12-1 By sa
//	 * @param model
//	 * @param panel
//	 * @param columnDataMap void
//	 */
//	Object savePageModelData(String model, String panel, Map<String,Object> columnDataMap);
	
	/**
	 * ��model�е����ݰ��չ涨�ĸ�ʽͬ�ⷢ����̨����
	 * @Methods Name savePageModelData
	 * @Create In 2008-12-14 By sa
	 * @param model
	 * @param modelDataMap
	 * @return Object
	 */
	Object savePageModelData(String model, Map<String,List<Map<String,Object>>> modelDataMap);
//	
//	/**
//	 * ɾ��pageModel�е����й�������
//	 * @Methods Name removePageModelData
//	 * @Create In 2008-12-10 By sa
//	 * @param model
//	 * @param mainObjectId void
//	 */
//	void removePageModelData(String model, String mainObjectId);
//	
//	/**
//	 * ͨ��model�ؼ��֣�panel�ؼ��ֺ�panel������id��ȡpanel�е��������ݣ����ؽ�����ܰ���������������
//	 * @param model
//	 * @param panel
//	 * @param mainPanelDataId
//	 * @deprecated
//	 * @return
//	 */
//	//Map<String,Object> getPageModelDataForEdit(String model, String panel, String mainPanelDataId);	
	
		/**
	 * ��ȡ�޸�ʱҳ����������ݡ��ṩmodel�ؼ��֣�model������id��ȡmodel�е��������ݣ����ؽ������model������panel����������
	 * @Methods Name getPageModelDataForEdit
	 * @Create In 2008-12-3 By sa
	 * @param model
	 * @param mainPanelDataId
	 * @return 	Map<String,List<Map<String,Object>>>
	 */
	Map<String,List<Map<String,Object>>> getPageModelDataForEdit(String model, String mainPanelDataId);
//	
//	/**
//	 * ������չʹ��
//	 * @Methods Name getPageModelDataForEdit
//	 * @Create In 2008-12-9 By sa
//	 * @param model
//	 * @param panelObjectIds
//	 * @return Map<String,Object> * 
//	 * @deprecated
//	 */
//	Map<String,Object> getPageModelDataForEdit(String model, String[] panelObjectIds);
//	
//	/**
//	 * �ṩpanel�ؼ��֣�panel������id��ȡpanel�е��������ݣ����ؽ������panel������column����������
//	 * @Methods Name getPagePanelDataForEdit
//	 * @Create In 2008-12-5 By lee
//	 * @param panelName
//	 * @param panelObjectId
//	 * @return Map<String,Object>
//	 */
//	Map<String,Object> getPagePanelDataForEdit(String panelName, String panelObjectId);
	
	/**
	 * Ϊ���ڿ���ֶ�չ���ı���ȡ������
	 * @Methods Name getFormPanelDataForEdit
	 * @Create In May 14, 2009 By sa
	 * @param panelName
	 * @param panelObjectId
	 * @return Map<String,Object>
	 */
	Map<String,Object> getFormPanelDataForEdit(String panelName, String panelObjectId);
//	
//	/**
//	 * �ṩpanel�ؼ��֣�panel������id��ȡpanel�е�����Ԥ����������ݣ����ؽ������panel������column����������
//	 * @Methods Name getPagePanelDataForLook
//	 * @Create In Apr 20, 2009 By sa
//	 * @param panelName
//	 * @param panelObjectId
//	 * @return Map<String,Object>
//	 */
//	Map<String,Object> getPagePanelDataForLook(String panelName, String panelObjectId);
	
	/**
	 * ��ȡָ��panel�е����ݣ�������Ϊpanel�����ݿ�������������panel������Ҫ����model������
	 * @Methods Name getPagePanelDataForEdit
	 * @Create In 2008-12-9 By sa
	 * @param modelName
	 * @param panelName
	 * @param panelObjectId �˷�����������ʱ����Ҫ�����������id
	 * @return Map<String,Object>//Map<String,Object>
	 */
	List<Map<String,Object>> getPagePanelDataForEdit(String modelName, String panelName, String mainObjectId);
	
	/**
	 * Ϊ���Ԥ�����ܻ�ȡ����
	 * @Methods Name getPagePanelDataForLook
	 * @Create In 2009-3-13 By sa
	 * @param modelName
	 * @param panelName
	 * @param mainObjectId
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getPagePanelDataForLook(String modelName, String panelName, String mainObjectId);
	
	/**
	 * ��ȡ����ҳ����������
	 * @Methods Name getPagePanelDataForAdd
	 * @Create In 2008-12-9 By sa
	 * @param panelName
	 * @return Map<String,Object>
	 */
	Map<String,Object> getPagePanelDataForAdd(String panelName);
//	
//	/**
//	 * ��ȡ�б�ҳ������ݣ�2����������ʱ�������б�ҳ����Ҫ�޸�
//	 * @Methods Name getPagePanelDataForList
//	 * @Create In 2009-1-1 By sa
//	 * @param panelName
//	 * @return Map<String,Object>
//	 */
//	Map<String,Object> getPagePanelDataForList(String panelName);
	
	/**
	 * ��ȡ���Ĳ�ѯ�ֶ�
	 * @Methods Name getPagePanelDataForQuery
	 * @Create In 2009-1-1 By sa
	 * @param panelName
	 * @return Map<String,Object>
	 */
	Map<String,Object> getPagePanelDataForQuery(String panelName);
//	
//	
//	/**
//	 * ��ȡpageModel�б�ҳ�����������
//	 * @Methods Name getPageModelDataForList
//	 * @Create In 2008-12-22 By sa
//	 * @param clazz
//	 * @param mainList
//	 * @return List<Map<String,Object>>
//	 */
//	List<Map<String, Object>> getPageModelDataForList(String pagePanel, List mainList);
//	/**
//	 * ʹ��������ƺͲ�ѯ�������в�ѯ����1�ڲ�ѯ���������Ǵ˲�ѯӦ�ø���ǿ��֧�ֶ༶����ѯ
//	 * @Methods Name query
//	 * @Create In 2008-12-10 By sa
//	 * @param panelName
//	 * @param queryParams
//	 * @param pageNo
//	 * @param pageSize
//	 * @param orderProp
//	 * @param isAsc
//	 * @return Page
//	 */
//	Page query(String panelName, Map<String,Object> queryParams, 
//			int pageNo, int pageSize, String orderProp, boolean isAsc);
//	
//	/**
//	 * ʹ��������ƺͲ�ѯ�������в�ѯ����1�ڲ�ѯ���������Ǵ˲�ѯӦ�ø���ǿ��֧�ֶ༶����ѯ
//	 * @Methods Name query
//	 * @Create In 2008-12-10 By sa
//	 * @param panelName
//	 * @param queryParams
//	 * @param orderProp
//	 * @param isAsc
//	 * @return List
//	 */
//	List query(String panelName, Map<String,Object> queryParams, String orderProp, boolean isAsc);
//	
//	/**
//	 * ������panel�п���ѯ���ص�ʵ������ת��Map��ʽ����
//	 * @Methods Name getEntityMapDataForList
//	 * @Create In 2008-12-10 By sa
//	 * @param panelName
//	 * @param mainList
//	 * @return List<Map<String,Object>>
//	 */
	List<Map<String, Object>> getEntityMapDataForList(String panelName, List mainList);
	
	/**
	 * ͨ��panel�ؼ��ֻ�ȡpanel�е�����column����
	 * @Methods Name getUserPagePanelColumnForEdit
	 * @Create In 2008-12-5 By lee
	 * @param panelName
	 * @return
	 */
	List<PagePanelColumn> getUserPagePanelColumn(String panelName);
//	
//	/**
//	 * ͨ��panel�ؼ��ֻ�ȡpanel�е�����column����
//	 * @Methods Name getUserPagePanelColumnForEdit
//	 * @Create In 2008-12-5 By lee
//	 * @param panel
//	 * @return
//	 */
//	List<PagePanelColumn> getUserPagePanelColumn(PagePanel panel);
	
	/**
	 * ����ģ���е�������ݣ����ޱ������ʱ������ʧ��
	 * @Methods Name savePagePanelData
	 * @Create In 2008-1-5 By lee
	 * @param modelName
	 * @param panelName
	 * @param panelDataMap
	 * @return String
	 */
	String saveSinglePanelData(String model, String panel,Map<String, List<Map<String, Object>>> panelDataMap);
}
