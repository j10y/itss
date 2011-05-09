package com.zsgj.itil.service.entity;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.ValidateType;

/**
 * ϵͳ�����ʵ��
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
	
	private SystemMainTableColumnType systemMainTableColumnType; //�ֶ����ͣ���Ӧҳ����ռ�����
	private ValidateType validateType; //�ֶ����ݣ���֤�����ͣ�������������Ӧ�÷��Ϻ��ָ�ʽ
	
	private SystemMainTable foreignTable;  //�����ֶ����ͣ������Ӧ����
	private SystemMainTableColumn foreignTableKeyColumn;
	private SystemMainTableColumn foreignTableValueColumn;
	private SystemMainTableColumn foreignTableParentColumn;
	private Integer foreignTableValueColumnOrder; //�����ֶ�����ʽ
	private Integer isNullForeignColumn;  //�Ƿ��������ֶΣ����ӹ���ʵ��������ֶ�����
	
	
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
	

	private Integer length; //sql column length
	private String descn; //������Ϣ������ʾ��ҳ���ֶ��Ա�
	
	private Integer order;
	
	private Integer isMustInput; //����������ֶβ��������أ��������Ӳ��������ֶα��
	
	private Integer isImeItem;
	private Integer isHiddenItem;
	private Integer isExtItem;
	private Integer isUpdateItem;
	
	private Integer uniqueFlag;
		
	private Integer enabled;
	
	//���ڿ������
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
