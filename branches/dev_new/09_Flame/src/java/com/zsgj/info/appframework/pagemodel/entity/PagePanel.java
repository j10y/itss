package com.zsgj.info.appframework.pagemodel.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Module;

/**
 * ҳ����ʵ��, PagePanel�Ƕ�����.
 * 
 * ҳ������2�����ͣ����������ֶ����
 * 
 * ����������������壬�ֶ����ֻ����ϵͳ�ɼ��ֶΡ�
 * 
 * @Class Name PagePanel
 * @Author sa
 * @Create In 2008-11-12
 */
public class PagePanel extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -2678797149402048924L;
	private Long id;
	//panel�ؼ���
	private String name;
	/**
	 * @Return the Set<PagePanelBtn> btns
	 */
	public Set<PagePanelBtn> getBtns() {
		return btns;
	}
	/**
	 * @Param Set<PagePanelBtn> btns to set
	 */
	public void setBtns(Set<PagePanelBtn> btns) {
		this.btns = btns;
	}
	/**
	 * @Return the Set<PagePanelColumn> ppcs
	 */
	public Set<PagePanelColumn> getPpcs() {
		return ppcs;
	}
	/**
	 * @Param Set<PagePanelColumn> ppcs to set
	 */
	public void setPpcs(Set<PagePanelColumn> ppcs) {
		this.ppcs = ppcs;
	}
	/**
	 * @Return the Set<PagePanelRelation> pprs
	 */
	public Set<PagePanelRelation> getPprs() {
		return pprs;
	}
	/**
	 * @Param Set<PagePanelRelation> pprs to set
	 */
	public void setPprs(Set<PagePanelRelation> pprs) {
		this.pprs = pprs;
	}
	/**
	 * @Return the Set<PagePanelTable> ppts
	 */
	public Set<PagePanelTable> getPpts() {
		return ppts;
	}
	/**
	 * @Param Set<PagePanelTable> ppts to set
	 */
	public void setPpts(Set<PagePanelTable> ppts) {
		this.ppts = ppts;
	}
	/**
	 * @Return the Set<PagePanelTableRelation> pptrs
	 */
	public Set<PagePanelTableRelation> getPptrs() {
		return pptrs;
	}
	/**
	 * @Param Set<PagePanelTableRelation> pptrs to set
	 */
	public void setPptrs(Set<PagePanelTableRelation> pptrs) {
		this.pptrs = pptrs;
	}
	//panel�ı���
	private String title;
	//������module
	private Module module;
	
	//����壬δʹ��PagePanel��������Ϊ����ģ��Ķ���ģ����������Ե����ã�������ȵ�
	private Set<PagePanelRelation> childPagePanels=new HashSet<PagePanelRelation>();
	
	//�����ǰpanel����û����panel������PagePanelColumn����
	private Set<PagePanelColumn> pagePanelColumns = new HashSet<PagePanelColumn>();
	//��ǰpanel��������ʵ����
	private SystemMainTable systemMainTable;
	//ҳ���ֶ����ͣ������б��ǵ���
	private Integer settingType;
	//��ӦExtJS���������
	private PagePanelType xtype;
	//�Ƿ�Ϊ�������͵�panel
	private Integer groupFlag;
	//�������������
	private PagePanel mainPagePanel;
	//�Ƿ��ǲ�ѯ���
	private Integer queryFlag;
	//��ѯ�Ľ������ȡȡ��Щ�����ο�PagePanelTable�������Ͳ���Ҫ��ѯ��������
	private Integer userFlag; //�û��Լ�������ģ��
	//������Ϣ
	private String descn;
	//��尴ť
	private Set<PagePanelBtn> btns = new HashSet<PagePanelBtn>();
	private Set<PagePanelColumn> ppcs = new HashSet<PagePanelColumn>();
	private Set<PagePanelRelation> pprs = new HashSet<PagePanelRelation>();
	private Set<PagePanelTable> ppts = new  HashSet<PagePanelTable>();
	private Set<PagePanelTableRelation> pptrs = new  HashSet<PagePanelTableRelation>();
	
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public Integer getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Set<PagePanelColumn> getPagePanelColumns() {
		return pagePanelColumns;
	}
	public void setPagePanelColumns(Set<PagePanelColumn> pagePanelColumns) {
		this.pagePanelColumns = pagePanelColumns;
	}
	public Integer getSettingType() {
		return settingType;
	}
	public void setSettingType(Integer settingType) {
		this.settingType = settingType;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public PagePanelType getXtype() {
		return xtype;
	}
	public void setXtype(PagePanelType xtype) {
		this.xtype = xtype;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Set<PagePanelRelation> getChildPagePanels() {
		return childPagePanels;
	}
	public void setChildPagePanels(Set<PagePanelRelation> childPagePanels) {
		this.childPagePanels = childPagePanels;
	}
	public Integer getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(Integer queryFlag) {
		this.queryFlag = queryFlag;
	}
	public Integer getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(Integer userFlag) {
		this.userFlag = userFlag;
	}
	public PagePanel getMainPagePanel() {
		return mainPagePanel;
	}
	public void setMainPagePanel(PagePanel mainPagePanel) {
		this.mainPagePanel = mainPagePanel;
	}

	
}
