package com.zsgj.info.appframework.pagemodel.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Module;

/**
 * ҳ��ģ��PageModel
 * @Class Name PageModel
 * @Author sa
 * @Create In 2008-11-16
 */
public class PageModel extends BaseObject {
	private Long id;
	//ҳ�����֣�����ʹ�ã�������Ӣ��
	private String name;
	//ҳ����⣬��������������
	private String title; 
	//ҳ����ʵ·��
	private String pagePath;
	//��panel
	private PagePanel mainPagePanel;
	//Ĭ���������
	private PagePanelType pagePanelType;
	//��ǰpanel��������ʵ����
	private SystemMainTable systemMainTable;
	//ҳ���������
	private List<PageModelParamIn> paramIns = new ArrayList<PageModelParamIn>();
	//ҳ���������
	private List<PageModelParamOut> paramOuts = new ArrayList<PageModelParamOut>();
	//ҳ�水ť���б�ҳ�涥������ҳ�������panel�ĵײ�
	private List<PageModelBtn> pageModelBtns = new ArrayList<PageModelBtn>();
	
	//��ǰ��pageModel����ЩpagePanels
	private List<PageModelPanel> pagePanels = new ArrayList<PageModelPanel>();	
	//ҳ���ֶ����ͣ������б��ǵ���
	private Integer settingType; 
	//����ģ��
	private Module module;
	//������Ϣ
	private String descn;
	
	private Set<PageModelBtn> btns = new HashSet<PageModelBtn>();
	private Set<PageModelNode> nodes = new HashSet<PageModelNode>();
	private Set<PageModelPanel> pmpanels = new HashSet<PageModelPanel>();
	//private Set<PageModelPanelRelation> pmprs = new HashSet<PageModelPanelRelation>();
	private Set<PageModelPanelTable> pmpts = new HashSet<PageModelPanelTable>();
	private Set<PageGroupPanelTable> pgpts = new HashSet<PageGroupPanelTable>();
	
	public String getUniquePropName(){
		return "name";
	}
	
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public Integer getSettingType() {
		return settingType;
	}
	public void setSettingType(Integer settingType) {
		this.settingType = settingType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public PageModel(Long id) {
		super();
		this.id = id;
	}
	public PageModel() {
	}
	public List<PageModelPanel> getPagePanels() {
		return pagePanels;
	}
	public void setPagePanels(List<PageModelPanel> pagePanels) {
		this.pagePanels = pagePanels;
	}
	public List<PageModelBtn> getPageModelBtns() {
		return pageModelBtns;
	}
	public void setPageModelBtns(List<PageModelBtn> pageModelBtns) {
		this.pageModelBtns = pageModelBtns;
	}
	public List<PageModelParamIn> getParamIns() {
		return paramIns;
	}
	public void setParamIns(List<PageModelParamIn> paramIns) {
		this.paramIns = paramIns;
	}
	public List<PageModelParamOut> getParamOuts() {
		return paramOuts;
	}
	public void setParamOuts(List<PageModelParamOut> paramOuts) {
		this.paramOuts = paramOuts;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	public PagePanel getMainPagePanel() {
		return mainPagePanel;
	}
	public void setMainPagePanel(PagePanel mainPagePanel) {
		this.mainPagePanel = mainPagePanel;
	}

	public PagePanelType getPagePanelType() {
		return pagePanelType;
	}

	public void setPagePanelType(PagePanelType pagePanelType) {
		this.pagePanelType = pagePanelType;
	}
}
