package com.zsgj.info.appframework.metadata.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统表格定义实体
 * @Class Name TableDefinition
 * @Author peixf
 * @Create In 2008-3-19
 */
public class SystemMainTableColumn extends Column {
	private static final long serialVersionUID = -1812818610346384846L;
	
	public static int isMain = 0; //主字段
	
	public static int isExt = 1; //扩展字段
	
	private Long id;
	private SystemMainTable systemMainTable;
	@SuppressWarnings("unused")
	private SystemMainTable table; //冗余属性，只读，待框架优化彻底后去除
	private String tableName;

	private String propertyName; //注意文本域的高宽问题，文本域底部放置一个加号增加高度
	private PropertyType propertyType;
	
	private String columnName;
	private String columnCnName;
	
	private String lengthForPage;  //字段页面长度，因可能为数字或百分比故直接赋予字符串类型
	private String heightForPage; //字段页面高度
	private String columnAlign;  //字段对齐方式
	private String htmlEncodeFlag; //对于大文本类型数据页面的回车符处理（可能再显示时因回车符而影响显示）
	private String columnLink;
	
	private SystemMainTableColumnType systemMainTableColumnType; //字段类型，对应页面表单空间类型
	private ValidateType validateType; //字段数据（验证）类型，即表单数据输入应该符合何种格式
	
	private SystemMainTable foreignTable;  //关联字段类型，外键对应主表 role
	private SystemMainTableColumn foreignTableKeyColumn; //role.id 或者sUserRole.id
	private SystemMainTableColumn foreignTableValueColumn; //role.name 或者sUserRole.role.name
	private SystemMainTableColumn foreignTableParentColumn;
	private Integer foreignTableValueColumnOrder; //关联字段排序方式
	private Integer isNullForeignColumn;  //是否是虚拟字段，及从关联实体带出的字段内容
	
//	<set name="userRoles" table="sUserRole" inverse="true" cascade="all">
//	  <key column="UserID"/>
//	  <one-to-many class="com.digitalchina.info.framework.security.entity.UserRole" />
//	</set>	
	
//	<set name="roles" table="sUserRole" cascade="none">
//	  <key column="USER_ID"/>
//	  <many-to-many class="com.digitalchina.info.framework.security.entity.Role"  
//	              column="ROLE_ID" />
//	</set>
	
	
	//区分列表标识
	private Integer discFlag; 
	
	//当前是否是抽象字段
	private Integer abstractFlag;
	//当前抽象字段所使用的区分标记字段
	private SystemMainTableColumn discColumn;
	private SystemMainTable foreignDiscTable;
	
	//对于附件类型的字段，增加以下3个属性
	private String uploadUrl; //上传附件存放的路径
	private String fileNamePrefix; //上传文件名称的前缀
	private SystemMainTableColumn fileNameColumn;
	private SystemMainTableColumn systemFileNameColumn;
	//end
	
	private SystemMainTable referencedTable; //userRole
	private SystemMainTableColumn referencedTableKeyColumn; //id
	private SystemMainTableColumn referencedTableValueColumn; //roleid
	private SystemMainTableColumn referencedTableParentColumn; //userid
	private Integer referencedTableValueColumnOrder; //roleid order
	private Integer columnSum; //字段布局列个数
	private Integer bigFlag; //是否长字段标记，也就是单行标志
	
	private SqlColumnType sqlColumnType; 
	private String columnType; //sql column type
	private Integer length; //sql column length
	private String descn; //描述信息，可显示在页面字段旁边
	
	private Integer order;
	
	private Integer isMustInput; //按道理必输字段不可以隐藏，或考虑增加不可隐藏字段标记
	
	private Integer isImeItem;
	private Integer isSearchItem;
	private Integer isOutputItem;
	private Integer isHiddenItem;
	private Integer isExtItem;
	private Integer isUpdateItem;
	
	private Integer uniqueFlag;
	
	private Integer identityFlag; //自增标识
	private String seed; //种子
	//private Integer seedLength; //
	private Integer increment; //递增量
	private String identityMode; //win$identity,也就是前缀，如C14-
		
	private Integer enabled;
	
	//二期框架新增
	private Integer userLogFlag;
	private Integer dateLogFlag;
	private Integer deployFlag;

	
	private Set<UserTableSetting> userTableSettings = new HashSet<UserTableSetting>(0);
	private Set<SystemTableQueryColumn> systemTableQueryColumns = new HashSet<SystemTableQueryColumn>(0);

	
	//主表扩展表合并字段
	private Integer isExtColumn;//是否是扩展字段0为主表1为扩展
	
	private Integer extSelectType;//0为源与主表2为源于自定义列表
	
	
	
	public Integer getIsExtColumn() {
		return isExtColumn;
	}

	public void setIsExtColumn(Integer isExtColumn) {
		this.isExtColumn = isExtColumn;
	}

	public Integer getExtSelectType() {
		return extSelectType;
	}

	public void setExtSelectType(Integer extSelectType) {
		this.extSelectType = extSelectType;
	}

	public boolean isTextColumnType(){
		if(systemMainTableColumnType==null) return false;
		return systemMainTableColumnType.isTextColumnType();
	}
	
	public boolean isNumberColumnType(){
		if(systemMainTableColumnType==null) return false;
		return systemMainTableColumnType.isNumberColumnType();
	}
	
	public boolean isCurrencyColumn(){
		if(validateType==null) return false;
		return validateType.getValidateTypeName().equalsIgnoreCase("Currency");
	}
	
	public boolean isDateColumn(){
		if(validateType==null) return false;
		return validateType.getValidateTypeName().equalsIgnoreCase("Date");
	}
	
	public boolean isParentProperty() {
		if(this.getPropertyName().startsWith("parent")) return true;
		return false;
	}

	public boolean isMultiColumn() {
		if(systemMainTableColumnType==null) return false;
		return systemMainTableColumnType.isMultiValueColumnType();
	}

	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public Integer getIsImeItem() {
		return isImeItem;
	}
	public void setIsImeItem(Integer isImeItem) {
		this.isImeItem = isImeItem;
	}
	public String getColumnCnName() {
		return columnCnName;
	}
	public void setColumnCnName(String columnCnName) {
		this.columnCnName = columnCnName;
	}
	public String getColumnName() {
		return columnName;
	}
	public String getColumnName2() {
		if(columnName==null) return null;
		return columnName.substring(0,1).toLowerCase()+ columnName.substring(1);
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
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
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((columnName == null) ? 0 : columnName.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((systemMainTable == null) ? 0 : systemMainTable.hashCode());
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
		final SystemMainTableColumn other = (SystemMainTableColumn) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (systemMainTable == null) {
			if (other.systemMainTable != null)
				return false;
		} else if (!systemMainTable.equals(other.systemMainTable))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}
	public Integer getIsMustInput() {
		return isMustInput;
	}
	public void setIsMustInput(Integer isMustInput) {
		this.isMustInput = isMustInput;
	}
	public Integer getIsOutputItem() {
		return isOutputItem;
	}
	public void setIsOutputItem(Integer isOutputItem) {
		this.isOutputItem = isOutputItem;
	}
	public Integer getIsSearchItem() {
		return isSearchItem;
	}
	public void setIsSearchItem(Integer isSearchItem) {
		this.isSearchItem = isSearchItem;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public Set<SystemTableQueryColumn> getSystemTableQueryColumns() {
		return systemTableQueryColumns;
	}

	public void setSystemTableQueryColumns(
			Set<SystemTableQueryColumn> systemTableQueryColumns) {
		this.systemTableQueryColumns = systemTableQueryColumns;
	}

	public Set<UserTableSetting> getUserTableSettings() {
		return userTableSettings;
	}

	public void setUserTableSettings(Set<UserTableSetting> userTableSettings) {
		this.userTableSettings = userTableSettings;
	}

	public SystemMainTableColumnType getSystemMainTableColumnType() {
		return systemMainTableColumnType;
	}
	public Long getTypeId() {
		if(this.getSystemMainTableColumnType()==null) return null;
		return this.getSystemMainTableColumnType().getId();
	}
	public void setSystemMainTableColumnType(
			SystemMainTableColumnType systemMainTableColumnType) {
		this.systemMainTableColumnType = systemMainTableColumnType;
	}
	public SystemMainTable getForeignTable() {
		return foreignTable;
	}
	public void setForeignTable(SystemMainTable foreignTable) {
		this.foreignTable = foreignTable;
	}
	public SystemMainTableColumn getForeignTableKeyColumn() {
		return foreignTableKeyColumn;
	}
	public void setForeignTableKeyColumn(SystemMainTableColumn foreignTableKeyColumn) {
		this.foreignTableKeyColumn = foreignTableKeyColumn;
	}
	public SystemMainTableColumn getForeignTableValueColumn() {
		return foreignTableValueColumn;
	}
	public void setForeignTableValueColumn(
			SystemMainTableColumn foreignTableValueColumn) {
		this.foreignTableValueColumn = foreignTableValueColumn;
	}
	public SystemMainTableColumn(Long id) {
		super();
		this.id = id;
	}
	public SystemMainTableColumn() {
		super();
	}

	public ValidateType getValidateType() {
		return validateType;
	}

	public void setValidateType(ValidateType validateType) {
		this.validateType = validateType;
	}

	public SystemMainTable getReferencedTable() {
		return referencedTable;
	}

	public void setReferencedTable(SystemMainTable referencedTable) {
		this.referencedTable = referencedTable;
	}

	public SystemMainTableColumn getReferencedTableKeyColumn() {
		return referencedTableKeyColumn;
	}

	public void setReferencedTableKeyColumn(
			SystemMainTableColumn referencedTableKeyColumn) {
		this.referencedTableKeyColumn = referencedTableKeyColumn;
	}

	public SystemMainTableColumn getReferencedTableValueColumn() {
		return referencedTableValueColumn;
	}

	public void setReferencedTableValueColumn(
			SystemMainTableColumn referencedTableValueColumn) {
		this.referencedTableValueColumn = referencedTableValueColumn;
	}

	public Integer getIsNullForeignColumn() {
		return isNullForeignColumn;
	}

	public void setIsNullForeignColumn(Integer isNullForeignColumn) {
		this.isNullForeignColumn = isNullForeignColumn;
	}

	public Integer getIsExtItem() {
		return isExtItem;
	}

	public void setIsExtItem(Integer isExtItem) {
		this.isExtItem = isExtItem;
	}

	public Integer getIsHiddenItem() {
		return isHiddenItem;
	}

	public void setIsHiddenItem(Integer isHiddenItem) {
		this.isHiddenItem = isHiddenItem;
	}

	public Integer getIsUpdateItem() {
		return isUpdateItem;
	}

	public void setIsUpdateItem(Integer isUpdateItem) {
		this.isUpdateItem = isUpdateItem;
	}

	public String getLengthForPage() {
		return lengthForPage;
	}

	public void setLengthForPage(String lengthForPage) {
		this.lengthForPage = lengthForPage;
	}

	public SqlColumnType getSqlColumnType() {
		return sqlColumnType;
	}

	public void setSqlColumnType(SqlColumnType sqlColumnType) {
		this.sqlColumnType = sqlColumnType;
	}

	public Integer getForeignTableValueColumnOrder() {
		return foreignTableValueColumnOrder;
	}

	public void setForeignTableValueColumnOrder(Integer foreignTableValueColumnOrder) {
		this.foreignTableValueColumnOrder = foreignTableValueColumnOrder;
	}

	public SystemMainTableColumn getForeignTableParentColumn() {
		return foreignTableParentColumn;
	}

	public void setForeignTableParentColumn(
			SystemMainTableColumn foreignTableParentColumn) {
		this.foreignTableParentColumn = foreignTableParentColumn;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public String getColumnAlign() {
		return columnAlign;
	}

	public void setColumnAlign(String columnAlign) {
		this.columnAlign = columnAlign;
	}

	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}

	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}

	//保证之前框架界面部分代码在本实体属性修改后不受影响，故留此2方法
	public SystemMainTable getTable() {
		return this.systemMainTable;
	}

	public void setTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}

	public String getColumnLink() {
		return columnLink;
	}

	public void setColumnLink(String columnLink) {
		this.columnLink = columnLink;
	}

	public SystemMainTableColumn getReferencedTableParentColumn() {
		return referencedTableParentColumn;
	}

	public void setReferencedTableParentColumn(
			SystemMainTableColumn referencedTableParentColumn) {
		this.referencedTableParentColumn = referencedTableParentColumn;
	}

	public Integer getReferencedTableValueColumnOrder() {
		return referencedTableValueColumnOrder;
	}

	public void setReferencedTableValueColumnOrder(
			Integer referencedTableValueColumnOrder) {
		this.referencedTableValueColumnOrder = referencedTableValueColumnOrder;
	}

	public SystemMainTableColumn getFileNameColumn() {
		return fileNameColumn;
	}

	public void setFileNameColumn(SystemMainTableColumn fileNameColumn) {
		this.fileNameColumn = fileNameColumn;
	}

	public String getFileNamePrefix() {
		return fileNamePrefix;
	}

	public void setFileNamePrefix(String fileNamePrefix) {
		this.fileNamePrefix = fileNamePrefix;
	}

	public SystemMainTableColumn getSystemFileNameColumn() {
		return systemFileNameColumn;
	}

	public void setSystemFileNameColumn(SystemMainTableColumn systemFileNameColumn) {
		this.systemFileNameColumn = systemFileNameColumn;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getHtmlEncodeFlag() {
		return htmlEncodeFlag;
	}

	public void setHtmlEncodeFlag(String htmlEncodeFlag) {
		this.htmlEncodeFlag = htmlEncodeFlag;
	}

	public Integer getAbstractFlag() {
		return abstractFlag;
	}

	public void setAbstractFlag(Integer abstractFlag) {
		this.abstractFlag = abstractFlag;
	}

	public Integer getUniqueFlag() {
		return uniqueFlag;
	}

	public void setUniqueFlag(Integer uniqueFlag) {
		this.uniqueFlag = uniqueFlag;
	}


	public Integer getDeployFlag() {
		return deployFlag;
	}

	public void setDeployFlag(Integer deployFlag) {
		this.deployFlag = deployFlag;
	}

	public Integer getDateLogFlag() {
		return dateLogFlag;
	}

	public void setDateLogFlag(Integer dateLogFlag) {
		this.dateLogFlag = dateLogFlag;
	}

	public Integer getUserLogFlag() {
		return userLogFlag;
	}

	public void setUserLogFlag(Integer userLogFlag) {
		this.userLogFlag = userLogFlag;
	}

	public Integer getDiscFlag() {
		return discFlag;
	}

	public void setDiscFlag(Integer discFlag) {
		this.discFlag = discFlag;
	}

	public SystemMainTableColumn getDiscColumn() {
		return discColumn;
	}

	public void setDiscColumn(SystemMainTableColumn discColumn) {
		this.discColumn = discColumn;
	}

	public SystemMainTable getForeignDiscTable() {
		return foreignDiscTable;
	}

	public void setForeignDiscTable(SystemMainTable foreignDiscTable) {
		this.foreignDiscTable = foreignDiscTable;
	}

	public Integer getIdentityFlag() {
		return identityFlag;
	}

	public void setIdentityFlag(Integer identityFlag) {
		this.identityFlag = identityFlag;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}


	public String getHeightForPage() {
		return heightForPage;
	}

	public void setHeightForPage(String heightForPage) {
		this.heightForPage = heightForPage;
	}

	public String getIdentityMode() {
		return identityMode;
	}

	public void setIdentityMode(String identityMode) {
		this.identityMode = identityMode;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public Integer getColumnSum() {
		return columnSum;
	}

	public void setColumnSum(Integer columnSum) {
		this.columnSum = columnSum;
	}

	public Integer getBigFlag() {
		return bigFlag;
	}

	public void setBigFlag(Integer bigFlag) {
		this.bigFlag = bigFlag;
	}


}
