package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.pagemodel.entity.PageBtnType;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelBtn;

public interface PagePanelBtnService {
	/**
	 * ��ȡָ��id�İ�ť
	 * @Methods Name findPanelBtnById
	 * @Create In 2008-12-17 By lee
	 * @param id
	 * @return PagePanelBtn
	 */
	PagePanelBtn findPanelBtnById(String id);
	/**
	 * ��ȡpanel�а�ť
	 * @Methods Name findPanelBtnByPanel
	 * @Create In 2008-12-15 By lee
	 * @param panel
	 * @return List<PagePanelBtn>
	 */
	List<PagePanelBtn> findPanelBtnByPanel(PagePanel panel);
	/**
	 * ��ʼ��panel�а�ť
	 * @Methods Name initPagePanelBtn
	 * @Create In 2008-12-15 By lee
	 * @param panel
	 */
	void initPagePanelBtn(PagePanel pp);
	/**
	 * ͨ��PageBtnTypeName��ȡPageBtnType
	 * @Methods Name findPageBtnTypeByName
	 * @Create In 2008-12-15 By lee
	 * @param PageBtnType
	 */
	PageBtnType findPageBtnTypeByName(String keyName);
	/**
	 * ���水ť
	 * @Methods Name savePanelBtn
	 * @Create In 2008-12-15 By lee
	 * @param Map
	 * @return PagePanelBtn
	 */
	PagePanelBtn savePanelBtn(Map buttonMap);
}
