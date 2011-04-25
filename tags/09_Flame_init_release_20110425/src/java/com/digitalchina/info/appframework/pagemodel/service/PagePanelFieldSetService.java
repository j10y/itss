package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelFieldSet;

/**
 * ����������
 * @Class Name PagePanelFieldSetService
 * @Author sa
 * @Create In May 8, 2009
 */
public interface PagePanelFieldSetService {
	
	/**
	 * ͨ������ȡ������е����з����
	 * @Methods Name findFieldSetByPanel
	 * @Create In May 8, 2009 By sa
	 * @param panel
	 * @return List<PagePanelFieldSet>
	 */
	List<PagePanelFieldSet> findFieldSetByPanel(PagePanel panel);
	
	/**
	 * ��ȡPagePanelColumn���õ�fieldSet
	 * @Methods Name findPagePanelFieldSet
	 * @Create In May 13, 2009 By sa
	 * @param ppc
	 * @return PagePanelFieldSet
	 */
	PagePanelFieldSet findPagePanelFieldSet(PagePanelColumn ppc);
	
	/**
	 * ��ȡ������е������ֶ�
	 * @Methods Name findColumnByFieldSet
	 * @Create In May 8, 2009 By sa
	 * @param fieldSet
	 * @return List<PagePanelColumn>
	 */
	List<PagePanelColumn> findColumnByFieldSet(PagePanelFieldSet fieldSet);
	
	/**
	 * ��ȡfieldset������ֶ�
	 * @Methods Name findFieldSetColumn
	 * @Create In May 13, 2009 By sa
	 * @param pagePanelColumn
	 * @return List<PagePanelColumn>
	 */
	List<PagePanelColumn> findFieldSetColumn(PagePanelColumn pagePanelColumn);
}
