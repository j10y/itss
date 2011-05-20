package com.zsgj.itil.service.entity;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.ValidateType;

/**
 * 系统表格定义实体
 * @Class Name TableDefinition
 * @Author peixf
 * @Create In 2008-3-19
 */
public class SCIDColumn extends Column {
	private static final long serialVersionUID = -1812818610346384846L;
	
	private Long id;
	private ServiceItemType serviceItemType;
	private ServiceItem serviceItem;
	private String value;
	
	private String columnName;
	private String columnCnName;
	
	private SystemMainTableColumnType systemMainTableColumnType; //字段类型，对应页面表单空间类型
	private ValidateType validateType; //字段数据（验证）类型，即表单数据输入应该符合何种格式
	
	private SystemMainTable foreignTable;  //关联字段类型，外键对应主表
	private SystemMainTableColumn foreignTableKeyColumn;
	private SystemMainTableColumn foreignTableValueColumn;
	private SystemMainTableColumn foreignTableParentColumn;
	private Integer foreignTableValueColumnOrder; //关联字段排序方式
	private Integer isNullForeignColumn;  //是否是虚拟字段，及从关联实体带出的字段内容
	
	
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
	

	private Integer length; //sql column length
	private String descn; //描述信息，可显示在页面字段旁边
	
	private Integer order;
	
	private Integer isMustInput; //按道理必输字段不可以隐藏，或考虑增加不可隐藏字段标记
	
	private Integer isImeItem;
	private Integer isHiddenItem;
	private Integer isExtItem;
	private Integer isUpdateItem;
	
	private Integer uniqueFlag;
		
	private Integer enabled;
	
	//二期框架新增
	private Integer userLogFlag;
	private Integer dateLogFlag;
	
	private Integer deployFlag;

	
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

	public SCIDColumn(Long id) {
		super();
		this.id = id;
	}
	public SCIDColumn() {
		super();
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



	public Integer getForeignTableValueColumnOrder() {
		return foreignTableValueColumnOrder;
	}

	public void setForeignTableValueColumnOrder(Integer foreignTableValueColumnOrder) {
		this.foreignTableValueColumnOrder = foreignTableValueColumnOrder;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getAbstractFlag() {
		return abstractFlag;
	}

	public void setAbstractFlag(Integer abstractFlag) {
		this.abstractFlag = abstractFlag;
	}

	public Integer getDateLogFlag() {
		return dateLogFlag;
	}

	public void setDateLogFlag(Integer dateLogFlag) {
		this.dateLogFlag = dateLogFlag;
	}

	public Integer getDeployFlag() {
		return deployFlag;
	}

	public void setDeployFlag(Integer deployFlag) {
		this.deployFlag = deployFlag;
	}

	public SystemMainTableColumn getDiscColumn() {
		return discColumn;
	}

	public void setDiscColumn(SystemMainTableColumn discColumn) {
		this.discColumn = discColumn;
	}

	public Integer getDiscFlag() {
		return discFlag;
	}

	public void setDiscFlag(Integer discFlag) {
		this.discFlag = discFlag;
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

	public SystemMainTable getForeignDiscTable() {
		return foreignDiscTable;
	}

	public void setForeignDiscTable(SystemMainTable foreignDiscTable) {
		this.foreignDiscTable = foreignDiscTable;
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

	public SystemMainTableColumn getForeignTableParentColumn() {
		return foreignTableParentColumn;
	}

	public void setForeignTableParentColumn(
			SystemMainTableColumn foreignTableParentColumn) {
		this.foreignTableParentColumn = foreignTableParentColumn;
	}

	public SystemMainTableColumn getForeignTableValueColumn() {
		return foreignTableValueColumn;
	}

	public void setForeignTableValueColumn(
			SystemMainTableColumn foreignTableValueColumn) {
		this.foreignTableValueColumn = foreignTableValueColumn;
	}

	public Integer getIsMustInput() {
		return isMustInput;
	}

	public void setIsMustInput(Integer isMustInput) {
		this.isMustInput = isMustInput;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public ServiceItemType getServiceItemType() {
		return serviceItemType;
	}

	public void setServiceItemType(ServiceItemType serviceItemType) {
		this.serviceItemType = serviceItemType;
	}

	public SystemMainTableColumn getSystemFileNameColumn() {
		return systemFileNameColumn;
	}

	public void setSystemFileNameColumn(SystemMainTableColumn systemFileNameColumn) {
		this.systemFileNameColumn = systemFileNameColumn;
	}

	public SystemMainTableColumnType getSystemMainTableColumnType() {
		return systemMainTableColumnType;
	}

	public void setSystemMainTableColumnType(
			SystemMainTableColumnType systemMainTableColumnType) {
		this.systemMainTableColumnType = systemMainTableColumnType;
	}

	public Integer getUniqueFlag() {
		return uniqueFlag;
	}

	public void setUniqueFlag(Integer uniqueFlag) {
		this.uniqueFlag = uniqueFlag;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public Integer getUserLogFlag() {
		return userLogFlag;
	}

	public void setUserLogFlag(Integer userLogFlag) {
		this.userLogFlag = userLogFlag;
	}

	public ValidateType getValidateType() {
		return validateType;
	}

	public void setValidateType(ValidateType validateType) {
		this.validateType = validateType;
	}

	public ServiceItem getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
