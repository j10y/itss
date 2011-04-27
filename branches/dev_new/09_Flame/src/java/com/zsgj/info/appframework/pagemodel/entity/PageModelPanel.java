package com.zsgj.info.appframework.pagemodel.entity;

import java.util.ArrayList;
import java.util.List;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * Page Model��Panelֱ�ӵ�ӳ���
 * ���һ��panel���Ա����pageModelʹ��
 * @Class Name PageModelPanel
 * @Author sa
 * @Create In 2008-11-23
 */
@SuppressWarnings("serial")
public class PageModelPanel extends BaseObject {
	private Long id;
	private PageModel pageModel;
	
	//��panel��־
	private Integer mainPanelFlag;
	
	private PagePanel pagePanel;
	private PagePanel parentPagePanel;
	//no mapping
	private List<PageModelPanel> childPagePanels = new ArrayList<PageModelPanel>();
	//extjs panel����
	private PagePanelType xtype;
	//�Ƿ���ʾ����
	private Integer titleDisplayFlag;
	//�Ƿ���ʾ
	private Integer isDisplay;
	//����
	private Integer order;
	//�Ƿ������ʾ
	private Integer isMustInput;
	//���뷽ʽ
	private String divFloat;
	//���ַ�ʽ
	private String layout;
	//�߶�
	private Integer height;
	//���
	private Integer width;
	//ֻ����壬��ͻ�����ϵ����Ϣֻ�ܴ������������ڿͻ�������޸�
	private Integer readonly;

	public Integer getReadonly() {
		return readonly;
	}
	public void setReadonly(Integer readonly) {
		this.readonly = readonly;
	}
//	public List<PageModelPanel> getChildPagePanels() {
//		return childPagePanels;
//	}
//	public void setChildPagePanels(List<PageModelPanel> childPagePanels) {
//		this.childPagePanels = childPagePanels;
//	}
	public String getDivFloat() {
		return divFloat;
	}
	public void setDivFloat(String divFloat) {
		this.divFloat = divFloat;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public Integer getIsMustInput() {
		return isMustInput;
	}
	public void setIsMustInput(Integer isMustInput) {
		this.isMustInput = isMustInput;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}
//	public PagePanel getParentPagePanel() {
//		return parentPagePanel;
//	}
//	public void setParentPagePanel(PagePanel parentPagePanel) {
//		this.parentPagePanel = parentPagePanel;
//	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public PagePanelType getXtype() {
		return xtype;
	}
	public void setXtype(PagePanelType xtype) {
		this.xtype = xtype;
	}
	public Integer getTitleDisplayFlag() {
		return titleDisplayFlag;
	}
	public void setTitleDisplayFlag(Integer titleDisplayFlag) {
		this.titleDisplayFlag = titleDisplayFlag;
	}
	public Integer getMainPanelFlag() {
		return mainPanelFlag;
	}
	public void setMainPanelFlag(Integer mainPanelFlag) {
		this.mainPanelFlag = mainPanelFlag;
	}
	public List<PageModelPanel> getChildPagePanels() {
		return childPagePanels;
	}
	public void setChildPagePanels(List<PageModelPanel> childPagePanels) {
		this.childPagePanels = childPagePanels;
	}
	public PagePanel getParentPagePanel() {
		return parentPagePanel;
	}
	public void setParentPagePanel(PagePanel parentPagePanel) {
		this.parentPagePanel = parentPagePanel;
	}

	
	
}
