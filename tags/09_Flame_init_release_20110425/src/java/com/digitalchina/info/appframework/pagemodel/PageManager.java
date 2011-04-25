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
	 * 通过pageModel关键字获取pageModel
	 * @Methods Name findPageModel
	 * @Create In 2008-11-24 By sa
	 * @param pageKeyName
	 * @return PageModel
	 */
	PageModel findPageModel(String pageKeyName);
//	
//	/**
//	 * 保存pageModel中所有panel表单中的数据
//	 * @Methods Name savePageModelData
//	 * @Create In 2008-12-1 By sa
//	 * @param model
//	 * @param panel
//	 * @param columnDataMap void
//	 */
//	Object savePageModelData(String model, String panel, Map<String,Object> columnDataMap);
	
	/**
	 * 将model中的数据按照规定的格式同意发到后台保存
	 * @Methods Name savePageModelData
	 * @Create In 2008-12-14 By sa
	 * @param model
	 * @param modelDataMap
	 * @return Object
	 */
	Object savePageModelData(String model, Map<String,List<Map<String,Object>>> modelDataMap);
//	
//	/**
//	 * 删除pageModel中的所有关联数据
//	 * @Methods Name removePageModelData
//	 * @Create In 2008-12-10 By sa
//	 * @param model
//	 * @param mainObjectId void
//	 */
//	void removePageModelData(String model, String mainObjectId);
//	
//	/**
//	 * 通过model关键字，panel关键字和panel主对象id获取panel中的所有数据，返回结果可能包括多个主表的数据
//	 * @param model
//	 * @param panel
//	 * @param mainPanelDataId
//	 * @deprecated
//	 * @return
//	 */
//	//Map<String,Object> getPageModelDataForEdit(String model, String panel, String mainPanelDataId);	
	
		/**
	 * 获取修改时页面的所有数据。提供model关键字，model主对象id获取model中的所有数据，返回结果包括model的所有panel中所需数据
	 * @Methods Name getPageModelDataForEdit
	 * @Create In 2008-12-3 By sa
	 * @param model
	 * @param mainPanelDataId
	 * @return 	Map<String,List<Map<String,Object>>>
	 */
	Map<String,List<Map<String,Object>>> getPageModelDataForEdit(String model, String mainPanelDataId);
//	
//	/**
//	 * 留待扩展使用
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
//	 * 提供panel关键字，panel主对象id获取panel中的所有数据，返回结果包括panel的所有column中所需数据
//	 * @Methods Name getPagePanelDataForEdit
//	 * @Create In 2008-12-5 By lee
//	 * @param panelName
//	 * @param panelObjectId
//	 * @return Map<String,Object>
//	 */
//	Map<String,Object> getPagePanelDataForEdit(String panelName, String panelObjectId);
	
	/**
	 * 为二期框架字段展开的表单获取表单数据
	 * @Methods Name getFormPanelDataForEdit
	 * @Create In May 14, 2009 By sa
	 * @param panelName
	 * @param panelObjectId
	 * @return Map<String,Object>
	 */
	Map<String,Object> getFormPanelDataForEdit(String panelName, String panelObjectId);
//	
//	/**
//	 * 提供panel关键字，panel主对象id获取panel中的所有预览界面的数据，返回结果包括panel的所有column中所需数据
//	 * @Methods Name getPagePanelDataForLook
//	 * @Create In Apr 20, 2009 By sa
//	 * @param panelName
//	 * @param panelObjectId
//	 * @return Map<String,Object>
//	 */
//	Map<String,Object> getPagePanelDataForLook(String panelName, String panelObjectId);
	
	/**
	 * 获取指定panel中的数据，但是因为panel的数据可能依赖于其他panel，故需要传递model的名称
	 * @Methods Name getPagePanelDataForEdit
	 * @Create In 2008-12-9 By sa
	 * @param modelName
	 * @param panelName
	 * @param panelObjectId 此方法的问题是时刻需要传递主对象的id
	 * @return Map<String,Object>//Map<String,Object>
	 */
	List<Map<String,Object>> getPagePanelDataForEdit(String modelName, String panelName, String mainObjectId);
	
	/**
	 * 为面板预览功能获取数据
	 * @Methods Name getPagePanelDataForLook
	 * @Create In 2009-3-13 By sa
	 * @param modelName
	 * @param panelName
	 * @param mainObjectId
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> getPagePanelDataForLook(String modelName, String panelName, String mainObjectId);
	
	/**
	 * 获取增加页面的面板数据
	 * @Methods Name getPagePanelDataForAdd
	 * @Create In 2008-12-9 By sa
	 * @param panelName
	 * @return Map<String,Object>
	 */
	Map<String,Object> getPagePanelDataForAdd(String panelName);
//	
//	/**
//	 * 获取列表页面的数据，2期新增，暂时这样，列表页面需要修改
//	 * @Methods Name getPagePanelDataForList
//	 * @Create In 2009-1-1 By sa
//	 * @param panelName
//	 * @return Map<String,Object>
//	 */
//	Map<String,Object> getPagePanelDataForList(String panelName);
	
	/**
	 * 获取面板的查询字段
	 * @Methods Name getPagePanelDataForQuery
	 * @Create In 2009-1-1 By sa
	 * @param panelName
	 * @return Map<String,Object>
	 */
	Map<String,Object> getPagePanelDataForQuery(String panelName);
//	
//	
//	/**
//	 * 获取pageModel列表页面的所有数据
//	 * @Methods Name getPageModelDataForList
//	 * @Create In 2008-12-22 By sa
//	 * @param clazz
//	 * @param mainList
//	 * @return List<Map<String,Object>>
//	 */
//	List<Map<String, Object>> getPageModelDataForList(String pagePanel, List mainList);
//	/**
//	 * 使用面板名称和查询条件进行查询，与1期查询方法区别是此查询应该更加强大，支持多级跨表查询
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
//	 * 使用面板名称和查询条件进行查询，与1期查询方法区别是此查询应该更加强大，支持多级跨表查询
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
//	 * 将基于panel中跨表查询返回的实体数据转成Map形式返回
//	 * @Methods Name getEntityMapDataForList
//	 * @Create In 2008-12-10 By sa
//	 * @param panelName
//	 * @param mainList
//	 * @return List<Map<String,Object>>
//	 */
	List<Map<String, Object>> getEntityMapDataForList(String panelName, List mainList);
	
	/**
	 * 通过panel关键字获取panel中的所有column数据
	 * @Methods Name getUserPagePanelColumnForEdit
	 * @Create In 2008-12-5 By lee
	 * @param panelName
	 * @return
	 */
	List<PagePanelColumn> getUserPagePanelColumn(String panelName);
//	
//	/**
//	 * 通过panel关键字获取panel中的所有column数据
//	 * @Methods Name getUserPagePanelColumnForEdit
//	 * @Create In 2008-12-5 By lee
//	 * @param panel
//	 * @return
//	 */
//	List<PagePanelColumn> getUserPagePanelColumn(PagePanel panel);
	
	/**
	 * 保存模板中单面板数据，当无必须关联时将保存失败
	 * @Methods Name savePagePanelData
	 * @Create In 2008-1-5 By lee
	 * @param modelName
	 * @param panelName
	 * @param panelDataMap
	 * @return String
	 */
	String saveSinglePanelData(String model, String panel,Map<String, List<Map<String, Object>>> panelDataMap);
}
