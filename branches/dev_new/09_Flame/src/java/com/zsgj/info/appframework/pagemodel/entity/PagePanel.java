package com.zsgj.info.appframework.pagemodel.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Module;

/**
 * 页面版块实体, PagePanel是独立的.
 * 
 * 页面面板分2种类型：分组面板和字段面板
 * 
 * 分组面板包括其他面板，字段面板只包含系统可见字段。
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
	//panel关键字
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
	//panel的标题
	private String title;
	//所属的module
	private Module module;
	
	//子面板，未使用PagePanel类型是因为分组模板的对子模板有许多属性的设置，如排序等等
	private Set<PagePanelRelation> childPagePanels=new HashSet<PagePanelRelation>();
	
	//如果当前panel下面没有子panel，则有PagePanelColumn集合
	private Set<PagePanelColumn> pagePanelColumns = new HashSet<PagePanelColumn>();
	//当前panel操作的主实体类
	private SystemMainTable systemMainTable;
	//页面字段类型，输入列表还是导出
	private Integer settingType;
	//对应ExtJS的组件类型
	private PagePanelType xtype;
	//是否为分组类型的panel
	private Integer groupFlag;
	//分组面板的主面板
	private PagePanel mainPagePanel;
	//是否是查询面板
	private Integer queryFlag;
	//查询的结果具体取取哪些主表，参考PagePanelTable表，这样就不需要查询结果面板了
	private Integer userFlag; //用户自己创建的模板
	//描述信息
	private String descn;
	//面板按钮
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
