package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelFieldSet;

/**
 * 面板分组框服务
 * @Class Name PagePanelFieldSetService
 * @Author sa
 * @Create In May 8, 2009
 */
public interface PagePanelFieldSetService {
	
	/**
	 * 通过面板获取该面板中的所有分组框
	 * @Methods Name findFieldSetByPanel
	 * @Create In May 8, 2009 By sa
	 * @param panel
	 * @return List<PagePanelFieldSet>
	 */
	List<PagePanelFieldSet> findFieldSetByPanel(PagePanel panel);
	
	/**
	 * 获取PagePanelColumn对用的fieldSet
	 * @Methods Name findPagePanelFieldSet
	 * @Create In May 13, 2009 By sa
	 * @param ppc
	 * @return PagePanelFieldSet
	 */
	PagePanelFieldSet findPagePanelFieldSet(PagePanelColumn ppc);
	
	/**
	 * 获取分组框中的所有字段
	 * @Methods Name findColumnByFieldSet
	 * @Create In May 8, 2009 By sa
	 * @param fieldSet
	 * @return List<PagePanelColumn>
	 */
	List<PagePanelColumn> findColumnByFieldSet(PagePanelFieldSet fieldSet);
	
	/**
	 * 获取fieldset里面的字段
	 * @Methods Name findFieldSetColumn
	 * @Create In May 13, 2009 By sa
	 * @param pagePanelColumn
	 * @return List<PagePanelColumn>
	 */
	List<PagePanelColumn> findFieldSetColumn(PagePanelColumn pagePanelColumn);
}
