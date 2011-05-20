package com.zsgj.info.appframework.metadata.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * ϵͳ�����ʵ��
 * @Class Name TableDefinition
 * @Author peixf
 * @Create In 2008-3-19
 */
public class SystemMainTableColumn extends Column {
	private static final long serialVersionUID = -1812818610346384846L;
	
	public static int isMain = 0; //���ֶ�
	
	public static int isExt = 1; //��չ�ֶ�
	
	private Long id;
	private SystemMainTable systemMainTable;
	@SuppressWarnings("unused")
	private SystemMainTable table; //�������ԣ�ֻ����������Ż����׺�ȥ��
	private String tableName;

	private String propertyName; //ע���ı���ĸ߿����⣬�ı���ײ�����һ���Ӻ����Ӹ߶�
	private PropertyType propertyType;
	
	private String columnName;
	private String columnCnName;
	
	private String lengthForPage;  //�ֶ�ҳ�泤�ȣ������Ϊ���ֻ�ٷֱȹ�ֱ�Ӹ����ַ�������
	private String heightForPage; //�ֶ�ҳ��߶�
	private String columnAlign;  //�ֶζ��뷽ʽ
	private String htmlEncodeFlag; //���ڴ��ı���������ҳ��Ļس���������������ʾʱ��س�����Ӱ����ʾ��
	private String columnLink;
	
	private SystemMainTableColumnType systemMainTableColumnType; //�ֶ����ͣ���Ӧҳ����ռ�����
	private ValidateType validateType; //�ֶ����ݣ���֤�����ͣ�������������Ӧ�÷��Ϻ��ָ�ʽ
	
	private SystemMainTable foreignTable;  //�����ֶ����ͣ������Ӧ���� role
	private SystemMainTableColumn foreignTableKeyColumn; //role.id ����sUserRole.id
	private SystemMainTableColumn foreignTableValueColumn; //role.name ����sUserRole.role.name
	private SystemMainTableColumn foreignTableParentColumn;
	private Integer foreignTableValueColumnOrder; //�����ֶ�����ʽ
	private Integer isNullForeignColumn;  //�Ƿ��������ֶΣ����ӹ���ʵ��������ֶ�����
	
//	<set name="userRoles" table="sUserRole" inverse="true" cascade="all">
//	  <key column="UserID"/>
//	  <one-to-many class="com.digitalchina.info.framework.security.entity.UserRole" />
//	</set>	
	
//	<set name="roles" table="sUserRole" cascade="none">
//	  <key column="USER_ID"/>
//	  <many-to-many class="com.digitalchina.info.framework.security.entity.Role"  
//	              column="ROLE_ID" />
//	</set>
	
	
	//�����б��ʶ
	private Integer discFlag; 
	
	//��ǰ�Ƿ��ǳ����ֶ�
	private Integer abstractFlag;
	//��ǰ�����ֶ���ʹ�õ����ֱ���ֶ�
	private SystemMainTableColumn discColumn;
	private SystemMainTable foreignDiscTable;
	
	//���ڸ������͵��ֶΣ���������3������
	private String uploadUrl; //�ϴ�������ŵ�·��
	private String fileNamePrefix; //�ϴ��ļ����Ƶ�ǰ׺
	private SystemMainTableColumn fileNameColumn;
	private SystemMainTableColumn systemFileNameColumn;
	//end
	
	private SystemMainTable referencedTable; //userRole
	private SystemMainTableColumn referencedTableKeyColumn; //id
	private SystemMainTableColumn referencedTableValueColumn; //roleid
	private SystemMainTableColumn referencedTableParentColumn; //userid
	private Integer referencedTableValueColumnOrder; //roleid order
	private Integer columnSum; //�ֶβ����и���
	private Integer bigFlag; //�Ƿ��ֶα�ǣ�Ҳ���ǵ��б�־
	
	private SqlColumnType sqlColumnType; 
	private String columnType; //sql column type
	private Integer length; //sql column length
	private String descn; //������Ϣ������ʾ��ҳ���ֶ��Ա�
	
	private Integer order;
	
	private Integer isMustInput; //����������ֶβ��������أ��������Ӳ��������ֶα��
	
	private Integer isImeItem;
	private Integer isSearchItem;
	private Integer isOutputItem;
	private Integer isHiddenItem;
	private Integer isExtItem;
	private Integer isUpdateItem;
	
	private Integer uniqueFlag;
	
	private Integer identityFlag; //������ʶ
	private String seed; //����
	//private Integer seedLength; //
	private Integer increment; //������
	private String identityMode; //win$identity,Ҳ����ǰ׺����C14-
		
	private Integer enabled;
	
	//���ڿ������
	private Integer userLogFlag;
	private Integer dateLogFlag;
	private Integer deployFlag;

	
	private Set<UserTableSetting> userTableSettings = new HashSet<UserTableSetting>(0);
	private Set<SystemTableQueryColumn> systemTableQueryColumns = new HashSet<SystemTableQueryColumn>(0);

	
	//������չ��ϲ��ֶ�
	private Integer isExtColumn;//�Ƿ�����չ�ֶ�0Ϊ����1Ϊ��չ
	
	private Integer extSelectType;//0ΪԴ������2ΪԴ���Զ����б�
	
	
	
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

	//��֤֮ǰ��ܽ��沿�ִ����ڱ�ʵ�������޸ĺ���Ӱ�죬������2����
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
