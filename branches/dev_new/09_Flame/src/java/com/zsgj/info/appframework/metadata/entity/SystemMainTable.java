package com.zsgj.info.appframework.metadata.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.appframework.template.entity.Template;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Module;

/**
 * 系统主表实体
 * @Class Name SystemMainTable
 * @Author peixf
 * @Create In 2008-3-17
 */
public class SystemMainTable extends BaseObject {
	
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 2228351374653277036L;
	private Long id;
	private String tableName;
	private String tableCnName;
	private String className;
	private Integer columnSum;
	
	private SystemMainTableColumn primaryKeyColumn;
	private SystemMainTableColumn defaultNameColumn; //默认的显示字段
	private SystemMainTableColumn parentColumn; //父字段
	
	private Integer level; //列表层次
	private Integer showLeafOnly; //是否只显示最底层的类型
	private Integer showCascade; //是否在一个下拉列表显示,如为true,显示多个列表
	private Integer userExtFlag; //配置项类型标志
	private Integer userScidFlag; //是否是服务项发布创建的主表
	private Integer templateFlag;  //是否是模板标志，比如合同分析报告对应的表
	private Integer userListFlag; //用户扩展的列表标志
	private Integer deployFlag; //此主表已经被hibernate管理了
	//private Integer realOnly
	private Template template;  //
	
	private Set columns = new HashSet();
	private Set extendColumns = new HashSet();
	
	private Module module;
	
	private String descn;
	
	private Set userTableSettings = new HashSet();

	public String getShortClassMName(){
//		String result = "";
		String systemShortName = "knowledge";
		int dotIndex = this.className.indexOf(systemShortName);
		if(dotIndex!=-1){
			return ".."+ this.className.substring(dotIndex);
		}
		if(this.className.startsWith("com.zsgj.info.framework")){
			int dotIndex2 = this.className.indexOf("framework");
			return ".."+ this.className.substring(dotIndex2);
		}
		return this.className;
	}
	
	public Set getUserTableSettings() {
		return userTableSettings;
	}

	public void setUserTableSettings(Set userTableSettings) {
		this.userTableSettings = userTableSettings;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
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

	public String getTableCnName() {
		return tableCnName;
	}

	public void setTableCnName(String tableCnName) {
		this.tableCnName = tableCnName;
	}

	public String getTableName() {
		return tableName;
	}
	
	public String getTableNameLowerCase() {
		return tableName.substring(0,1).toLowerCase()+ tableName.substring(1);
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((columnSum == null) ? 0 : columnSum.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SystemMainTable other = (SystemMainTable) obj;
		if (columnSum == null) {
			if (other.columnSum != null)
				return false;
		} else if (!columnSum.equals(other.columnSum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

	public Integer getColumnSum() {
		return columnSum;
	}

	public void setColumnSum(Integer columnSum) {
		this.columnSum = columnSum;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public SystemMainTableColumn getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public void setPrimaryKeyColumn(SystemMainTableColumn primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}

	public Set getColumns() {
		return columns;
	}

	public void setColumns(Set columns) {
		this.columns = columns;
	}

	public SystemMainTable(Long id) {
		super();
		this.id = id;
	}
	
	public SystemMainTable() {
		super();
	}

	public Set getExtendColumns() {
		return extendColumns;
	}

	public void setExtendColumns(Set extendColumns) {
		this.extendColumns = extendColumns;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public SystemMainTableColumn getParentColumn() {
		return parentColumn;
	}

	public void setParentColumn(SystemMainTableColumn parentColumn) {
		this.parentColumn = parentColumn;
	}

	public Integer getShowCascade() {
		return showCascade;
	}

	public void setShowCascade(Integer showCascade) {
		this.showCascade = showCascade;
	}

	public Integer getShowLeafOnly() {
		return showLeafOnly;
	}

	public void setShowLeafOnly(Integer showLeafOnly) {
		this.showLeafOnly = showLeafOnly;
	}

	public SystemMainTableColumn getDefaultNameColumn() {
		return defaultNameColumn;
	}

	public void setDefaultNameColumn(SystemMainTableColumn defaultNameColumn) {
		this.defaultNameColumn = defaultNameColumn;
	}

	public Integer getUserExtFlag() {
		return userExtFlag;
	}

	public void setUserExtFlag(Integer userExtFlag) {
		this.userExtFlag = userExtFlag;
	}

	public Integer getDeployFlag() {
		return deployFlag;
	}

	public void setDeployFlag(Integer deployFlag) {
		this.deployFlag = deployFlag;
	}

	public Integer getTemplateFlag() {
		return templateFlag;
	}

	public void setTemplateFlag(Integer templateFlag) {
		this.templateFlag = templateFlag;
	}
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Integer getUserListFlag() {
		return userListFlag;
	}

	public void setUserListFlag(Integer userListFlag) {
		this.userListFlag = userListFlag;
	}

	public Integer getUserScidFlag() {
		return userScidFlag;
	}

	public void setUserScidFlag(Integer userScidFlag) {
		this.userScidFlag = userScidFlag;
	}
	
}
