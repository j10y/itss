package com.digitalchina.itil.require.service.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.mapping.PersistentClass;

import com.digitalchina.info.appframework.metadata.entity.PropertyType;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.itil.config.service.CustomerTableService;
import com.digitalchina.itil.require.service.ReqTableService;

public class ReqTableServiceImpl extends BaseDao implements ReqTableService{
	private CustomerTableService customerTableService;
	private SystemColumnService systemColumnService;
	private SystemMainColumnService systemMainColumnService;
	private SystemMainTableService systemMainTableService;
	String FSP = System.getProperty("file.separator");
	String LSP = "\n";
	
	public SystemMainTable saveSystemMainTable(SystemMainTable smt) {

//		*****************************************************************************************************	
		smt.setUserScidFlag(1);    //smt.setUserExtFlag(1);smt.setUserExtFlag(1);
		String tableName = smt.getTableName();
		tableName = StringUtils.capitalize(smt.getTableName());
		if(tableName.length()>21){
			throw new ServiceException("表名称不可以超过21个字符");
		}
		smt.setTableName(tableName);
		
		String configDefaultPkg = PropertiesUtil.getProperties("system.service.defaultpkg", "com.digitalchina.itil.require.entity");
		if(smt.getClassName()==null){
			String className = configDefaultPkg+"."+tableName;
			smt.setClassName(className);
		}else{
			//如果已经发布了，不能修改表名
			if(smt.getDeployFlag()!=null&& smt.getDeployFlag().intValue()==1){
				SystemMainTable smtOld = super.get(SystemMainTable.class, smt.getId());
				smt.setTableName(smtOld.getTableName());
				String tableNameOld = smtOld.getTableName();
				if(!tableNameOld.equalsIgnoreCase(tableName)){
					String className = configDefaultPkg+"."+tableName;
					smt.setClassName(className);
				}
			}else { //发布之前还可以根据表名称自动修改类名称
				String className = configDefaultPkg+"."+tableName;
				smt.setClassName(className);
			}
		}
		
		boolean isInsert = smt.getId()==null;
		if(isInsert){ //判断表名是否存在
			Criteria cSameName = super.getCriteria(SystemMainTable.class);
			cSameName.add(Restrictions.ilike("tableName", smt.getTableName(), MatchMode.EXACT));
			cSameName.setProjection(Projections.rowCount());
			Integer sameCount = (Integer) cSameName.uniqueResult();
			if(sameCount!=null && sameCount.intValue()>0){
				throw new ServiceException("您创建的用户表已经存在, 请进入数据模型维护<br>页面查看已有主表信息");
			}
		}
		
		//begin save smt event
		SystemMainTable smtEvent = new SystemMainTable();
		try {
			//新增和修改都需要时刻同步主表的属性
			BeanUtils.copyProperties(smtEvent, smt);
			smtEvent.setTableName(smt.getTableName()+"Event");
			smtEvent.setTableCnName(smt.getTableCnName()+"历史");
			smtEvent.setClassName(smt.getClassName()+"Event");
			smtEvent.setUserExtFlag(null);
			if(isInsert){ //对于新增
				smtEvent.setId(null);
				super.save(smtEvent);
			}else{ //如果不是新增服务项需求表，也可能还没有历史表此时需要创建
				SystemMainTable smtEventTemp = findUserTableEvent(smt);
				if(smtEventTemp==null){
					smtEvent.setId(null);
					super.save(smtEvent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//end savme smt event
		
		//save smt
		super.save(smt);
		//end save smt

		//save column
		SystemMainTableColumn stmcPk = null;
		SystemMainTableColumn smtcName = null;
		SystemMainTableColumn smtcOldApply = null;
		SystemMainTableColumn smtcStatus = null;
		SystemMainTableColumn smtcProcess = null;
		SystemMainTableColumn smtcDelete = null;
		SystemMainTableColumn smtcServiceItem = null;
		if(isInsert){
			PropertyType pt = new PropertyType();
			pt.setId(2L);
			stmcPk = new SystemMainTableColumn();
			SystemMainTableColumnType smtct = new SystemMainTableColumnType();
			smtct.setId(8L);
			stmcPk.setPropertyType(pt);
			stmcPk.setSystemMainTableColumnType(smtct);
			stmcPk.setColumnCnName("自动编号");
			stmcPk.setColumnName("id");
			stmcPk.setPropertyName("id");
			stmcPk.setDescn("主键，不可修改和删除");
			stmcPk.setOrder(1);
			stmcPk.setSystemMainTable(smt);
			stmcPk.setIsHiddenItem(1);
			stmcPk.setIsSearchItem(0);
			stmcPk.setIsUpdateItem(0);
			stmcPk.setIsMustInput(1);
			super.save(stmcPk);
			smt.setPrimaryKeyColumn(stmcPk);
			//smt.setTableName("ci_ext"+tableName);
			super.save(smt);
			//增加名称字段, 强制必须有个字段叫name
			smtcName = addTextColumn(smt, "名称，请自行修改", "name", 2);
			
			smtcOldApply = addObjectColumn(smt, "变更前申请", "oldApply", 3);
			
			//增加流程类型字段
			smtcProcess = addHiddenColumn(smt, "流程类型", "processType", 4);
			//增加状态字段
			smtcStatus = addHiddenColumn(smt, "状态", "status", 5);
			
			smtcDelete = addHiddenColumn(smt, "删除状态", "deleteFlag", 6);
			
			smtcServiceItem = addHiddenColumn(smt, "所属服务", "serviceItem", 2L, 7);
			//end
//			begin_为历史表的主键字段初始化主键字段
			SystemMainTableColumn smtcEventPk = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventName = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventOldApply = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventProcess = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventStatus = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventDelete = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventServiceItem = new SystemMainTableColumn();
			try {
				//保存主键字段到历史表
				BeanUtils.copyProperties(smtcEventPk, stmcPk);
				smtcEventPk.setSystemMainTable(smtEvent);
				smtcEventPk.setId(null);
				super.save(smtcEventPk);
				smtEvent.setPrimaryKeyColumn(smtcEventPk);
				super.save(smtEvent);
				//end
				
				//begin_拷贝主键到历史表后，自动给历史表增加rootId
				SystemMainTableColumn smtcRoot = new SystemMainTableColumn();
				smtcRoot.setPropertyName("rootId");
				
				PropertyType ptRoot = new PropertyType();
				ptRoot.setId(Long.valueOf(2));	//long
				
				SystemMainTableColumnType typeRoot = new SystemMainTableColumnType();
				typeRoot.setId(Long.valueOf(8));   //隐藏域
				
				smtcRoot.setPropertyType(ptRoot);
				smtcRoot.setSystemMainTableColumnType(typeRoot);
				
				smtcRoot.setColumnCnName("主对象ID");
				smtcRoot.setColumnName("rootId");
				smtcRoot.setSystemMainTable(smtEvent);
				smtcRoot.setDescn("记录历史记录对于的主对象ID");
				smtcRoot.setOrder(2);
				smtcRoot.setIsHiddenItem(1);
				smtcRoot.setIsSearchItem(0);
				smtcRoot.setIsUpdateItem(0);
				smtcRoot.setIsMustInput(1);
				super.save(smtcRoot);
				//end
				
//				保存历史表的名称字段
				BeanUtils.copyProperties(smtcEventName, smtcName);
				smtcEventName.setSystemMainTable(smtEvent);
				smtcEventName.setId(null);
				smtcEventName.setOrder(3);
				super.save(smtcEventName);
				//end
				
//				保存历史表的历史申请
				BeanUtils.copyProperties(smtcEventOldApply, smtcOldApply);
				smtcEventOldApply.setSystemMainTable(smtEvent);
				smtcEventOldApply.setId(null);
				smtcEventOldApply.setOrder(4);
				super.save(smtcEventOldApply);
				//end
				
//				保存历史表的流程类型
				BeanUtils.copyProperties(smtcEventProcess, smtcProcess);
				smtcEventProcess.setSystemMainTable(smtEvent);
				smtcEventProcess.setId(null);
				smtcEventProcess.setOrder(5);
				super.save(smtcEventProcess);
				//end
				
				//保存历史表的状态位
				BeanUtils.copyProperties(smtcEventStatus, smtcStatus);
				smtcEventStatus.setSystemMainTable(smtEvent);
				smtcEventStatus.setId(null);
				smtcEventStatus.setOrder(6);
				super.save(smtcEventStatus);
				//end
				
				//保存历史表的删除状态
				BeanUtils.copyProperties(smtcEventDelete, smtcDelete);
				smtcEventDelete.setSystemMainTable(smtEvent);
				smtcEventDelete.setId(null);
				smtcEventDelete.setOrder(7);
				super.save(smtcEventDelete);
				//end
				
				//保存历史表的所属服务项
				BeanUtils.copyProperties(smtcEventServiceItem, smtcServiceItem);
				smtcEventServiceItem.setSystemMainTable(smtEvent);
				smtcEventServiceItem.setId(null);
				smtcEventServiceItem.setOrder(8);
				super.save(smtcEventServiceItem);
				//end
				
				//增加rootId属性和字段
				//addObjectColumn(smtEvent, "根对象ID", "rootId", 9);
			} catch (Exception e) {
				e.printStackTrace();
			}		
//			end
			
			//保存配置项类型草稿
//			ConfigItemType cit = new ConfigItemType();
//			cit.setName(smt.getTableCnName());
//			cit.setClassName(smt.getClassName());
//			cit.setTableName(smt.getTableName());
//			cit.setSystemMainTable(smt);
//			cit.setPagePanel(null);
//			cit.setDeployFlag(0);
//			super.save(cit);
			
			
		}/*else{
			//拷贝主表的字段到历史表
		}*/
		
		return smt;
	}
	public SystemMainTable findUserTableEvent(SystemMainTable smt) {
		//begin
		String eventClassName = smt.getClassName()+"Event";
		//Class clazz = this.getClass(eventClassName);
		Criteria cSmtEvent = super.getCriteria(SystemMainTable.class);
		cSmtEvent.add(Restrictions.eq("className", eventClassName));
		SystemMainTable smtEvent = null;
		List<SystemMainTable> smtEvents = cSmtEvent.list();
		if(!smtEvents.isEmpty()){
			smtEvent = smtEvents.iterator().next();
		}
//		if(smtEvent==null){
//			smtEvent = this.saveEventTableByMainTable(smt);
//		}
		return smtEvent;
	}
	private SystemMainTableColumn addHiddenColumn(SystemMainTable smt, String cnName, String columnName, Integer order) {
		SystemMainTableColumn smtcStatus;
		PropertyType ptStatus = new PropertyType();
		ptStatus.setId(4L);
		smtcStatus = new SystemMainTableColumn();
		SystemMainTableColumnType smtctStatus = new SystemMainTableColumnType();
		smtctStatus.setId(8L);
		smtcStatus.setPropertyType(ptStatus);
		smtcStatus.setSystemMainTableColumnType(smtctStatus);
		smtcStatus.setColumnCnName(cnName);
		smtcStatus.setColumnName(columnName);
		smtcStatus.setPropertyName(columnName);
		smtcStatus.setDescn(cnName+"，流程审批使用，不可以删除");
		smtcStatus.setOrder(order);
		smtcStatus.setSystemMainTable(smt);
		smtcStatus.setIsHiddenItem(1);
		smtcStatus.setIsSearchItem(0);
		smtcStatus.setIsUpdateItem(0);
		smtcStatus.setIsMustInput(1);
		super.save(smtcStatus);
		return smtcStatus;
	}
	
	private SystemMainTableColumn addHiddenLongColumn(SystemMainTable smt, String cnName, String columnName, Integer order) {
		SystemMainTableColumn smtcStatus;
		PropertyType ptStatus = new PropertyType();
		ptStatus.setId(2L);
		smtcStatus = new SystemMainTableColumn();
		SystemMainTableColumnType smtctStatus = new SystemMainTableColumnType();
		smtctStatus.setId(8L);
		smtcStatus.setPropertyType(ptStatus);
		smtcStatus.setSystemMainTableColumnType(smtctStatus);
		smtcStatus.setColumnCnName(cnName);
		smtcStatus.setColumnName(columnName);
		smtcStatus.setPropertyName(columnName);
		smtcStatus.setDescn(cnName+"，流程审批使用，不可以删除");
		smtcStatus.setOrder(order);
		smtcStatus.setSystemMainTable(smt);
		smtcStatus.setIsHiddenItem(1);
		smtcStatus.setIsSearchItem(0);
		smtcStatus.setIsUpdateItem(0);
		smtcStatus.setIsMustInput(1);
		super.save(smtcStatus);
		return smtcStatus;
	}
	
	private SystemMainTableColumn addHiddenColumn(SystemMainTable smt, String cnName, String columnName, Long ptype, Integer order) {
		SystemMainTableColumn smtcStatus;
		PropertyType ptStatus = new PropertyType();
		ptStatus.setId(ptype);
		smtcStatus = new SystemMainTableColumn();
		SystemMainTableColumnType smtctStatus = new SystemMainTableColumnType();
		smtctStatus.setId(8L);
		smtcStatus.setPropertyType(ptStatus);
		smtcStatus.setSystemMainTableColumnType(smtctStatus);
		smtcStatus.setColumnCnName(cnName);
		smtcStatus.setColumnName(columnName);
		smtcStatus.setPropertyName(columnName);
		smtcStatus.setDescn(cnName+"，流程审批使用，不可以删除");
		smtcStatus.setOrder(order);
		smtcStatus.setSystemMainTable(smt);
		smtcStatus.setIsHiddenItem(1);
		smtcStatus.setIsSearchItem(0);
		smtcStatus.setIsUpdateItem(0);
		smtcStatus.setIsMustInput(1);
		super.save(smtcStatus);
		return smtcStatus;
	}
	
	private SystemMainTableColumn addTextColumn(SystemMainTable smt, String cnName, String columnName, Integer order) {
		SystemMainTableColumn smtcText;
		PropertyType ptText = new PropertyType();
		ptText.setId(3L);//字符串类型属性
		smtcText = new SystemMainTableColumn();
		SystemMainTableColumnType smtcType = new SystemMainTableColumnType();
		smtcType.setId(1L); //下拉列表
		smtcText.setPropertyType(ptText);
		smtcText.setSystemMainTableColumnType(smtcType);
		smtcText.setColumnCnName(cnName);
		smtcText.setColumnName(columnName);
		smtcText.setPropertyName(columnName);
		smtcText.setDescn(cnName+"，名称字段，字段中文名称请根据情况修改，如ERP账号名称");
		smtcText.setOrder(order);
		smtcText.setSystemMainTable(smt);
		smtcText.setIsHiddenItem(0);
		smtcText.setIsSearchItem(0);
		smtcText.setIsUpdateItem(0);
		smtcText.setIsMustInput(1);
		super.save(smtcText);
		return smtcText;
	}
	
	
	private SystemMainTableColumn addObjectColumn(SystemMainTable smt, String cnName, String columnName, Integer order) {
		SystemMainTableColumn smtcObject;
		PropertyType ptStatus = new PropertyType();
		ptStatus.setId(1L);
		smtcObject = new SystemMainTableColumn();
		SystemMainTableColumnType smtctStatus = new SystemMainTableColumnType();
		smtctStatus.setId(3L);
		smtcObject.setPropertyType(ptStatus);
		smtcObject.setSystemMainTableColumnType(smtctStatus);
		smtcObject.setColumnCnName(cnName);
		smtcObject.setColumnName(columnName);
		smtcObject.setPropertyName(columnName);
		smtcObject.setDescn(cnName+"，变更前服务申请");
		smtcObject.setOrder(order);
		smtcObject.setSystemMainTable(smt);
		smtcObject.setForeignTable(smt); //设置外键表
		smtcObject.setForeignTableKeyColumn(smt.getPrimaryKeyColumn());
		
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("propertyName", "name"));
		SystemMainTableColumn smtcName = (SystemMainTableColumn) c.uniqueResult();
		if(smtcName!=null){
			smtcObject.setForeignTableValueColumn(smtcName);
		}
		
		smtcObject.setIsHiddenItem(0);
		smtcObject.setIsSearchItem(0);
		smtcObject.setIsUpdateItem(0);
		smtcObject.setIsMustInput(1);
		super.save(smtcObject);
		return smtcObject;
	}

	public SystemMainTableColumn saveSystemMainTableColumn(
			SystemMainTableColumn smtc) {
		
		//SessionFactory sf = super.getHibernateSessionFactory();
		//SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sf;
		//Dialect dialect = sessionFactoryImpl.getDialect();
		
		SystemMainTable smt = smtc.getSystemMainTable();
		smt = super.get(SystemMainTable.class, Long.valueOf(smt.getId()));
		
		boolean isInsert = false;
		if(smtc.getId()!=null){
			SystemMainTableColumn old = (SystemMainTableColumn) super.getObject(SystemMainTableColumn.class, smtc.getId(), true);
			boolean columnNameEqu = old.getColumnName().equals(smtc.getColumnName());
			boolean propertyTypeEqu = old.getPropertyType().getId().equals(smtc.getPropertyType().getId());
			SystemMainTableColumnType oldColumnType = old.getSystemMainTableColumnType();
			if(oldColumnType!=null){
				boolean columnTypeEqu = old.getSystemMainTableColumnType().getId().equals(smtc.getSystemMainTableColumnType().getId());
				boolean result = columnNameEqu&& propertyTypeEqu&& columnTypeEqu; //
				if(!result){
//					if(smt.getDeployFlag()!=null&& smt.getDeployFlag().intValue()==1){
//						throw new ServiceException("当前用户表已经发布，不可以修改字段名称或类型");
//					}
				}
			}
		}else{
			isInsert = true;
		}
		
		
		SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
		smtcType = super.get(SystemMainTableColumnType.class, Long.valueOf(smtcType.getId()));
		if(smtcType.getColumnTypeName().equalsIgnoreCase("multiFile")){
			smtc.setUploadUrl("/upload/serviceItem");
			smtc.setFileNamePrefix("scid_");
			
			Criteria c = super.getCriteria(SystemMainTable.class);
			c.add(Restrictions.eq("className", "com.digitalchina.info.appframework.metadata.entity.SystemFile"));
			SystemMainTable smtFile = (SystemMainTable) c.uniqueResult();
			
			smtc.setForeignTable(smtFile);
			smtc.setForeignTableKeyColumn(smtFile.getPrimaryKeyColumn());
			
			Criteria cName = super.getCriteria(SystemMainTableColumn.class);
			cName.add(Restrictions.eq("systemMainTable", smtFile));
			cName.add(Restrictions.eq("propertyName", "fileName"));
			SystemMainTableColumn smtcName = (SystemMainTableColumn) cName.uniqueResult();
			
			smtc.setForeignTableValueColumn(smtcName);
			
			cName = super.getCriteria(SystemMainTableColumn.class);
			cName.add(Restrictions.eq("systemMainTable", smtFile));
			cName.add(Restrictions.eq("propertyName", "systemFileName"));
			SystemMainTableColumn smtcSysFile = (SystemMainTableColumn) cName.uniqueResult();
			
			smtc.setFileNameColumn(smtcName);
			smtc.setSystemFileNameColumn(smtcSysFile);
			
		}
		PropertyType propType = smtc.getPropertyType();
		propType = super.get(PropertyType.class, Long.valueOf(propType.getId()));
		
		
		String tableName = smt.getTableName();
		String columnName = smtc.getColumnName();
		columnName=Character.toLowerCase(columnName.charAt(0))+columnName.substring(1);
		smtc.setColumnName(columnName);
		smtc.setPropertyName(columnName);
		if(this.existKeyword(columnName)){
			throw new ServiceException(columnName+"为系统预留关键字，请更换其他字段名");
		}
		
		if(smtc.getId()==null){
			
			if(smtc.getIsHiddenItem()==null){
				smtc.setIsHiddenItem(0);
			}
			if(smtc.getIsSearchItem()==null){
				smtc.setIsSearchItem(0);
			}
			if(smtc.getIsUpdateItem()==null){
				smtc.setIsUpdateItem(1);
			}
			if(smtc.getIsExtItem()==null){
				smtc.setIsExtItem(0);
			}
			if(smtc.getIsMustInput()==null){
				smtc.setIsMustInput(0);
			}
			
			//判断是否已经存在同名的字段
			Criteria c = super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.ilike("columnName", columnName));
			c.setProjection(Projections.rowCount());
			Integer cncount = (Integer) c.uniqueResult();
			if(cncount>0){
				throw new ServiceException("字段名称"+columnName+"已经存在，请更换其他名称");
			}
			
			c = super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.ilike("columnCnName", columnName));
			c.setProjection(Projections.rowCount());
			cncount = (Integer) c.uniqueResult();
			if(cncount>0){
				throw new ServiceException("字段中文名称"+columnName+"已经存在，请更换其他名称");
			}
			

		}
		
		super.save(smtc);
		
		//为当前字段生成相应的历史表字段
		//begin 获取主表
		String eventClassName = smt.getClassName()+"Event";
		//Class clazz = this.getClass(eventClassName);
		Criteria cSmtEvent = super.getCriteria(SystemMainTable.class);
		cSmtEvent.add(Restrictions.eq("className", eventClassName));
		SystemMainTable smtEvent = null;
		List<SystemMainTable> smtEvents = cSmtEvent.list();
		if(!smtEvents.isEmpty()){
			smtEvent = smtEvents.iterator().next();
		}
		
		if(isInsert){ //新增历史表字段
			SystemMainTableColumn smtcEvent = new SystemMainTableColumn();
			try {
				BeanUtils.copyProperties(smtcEvent, smtc); //拷贝历史表字段
				smtcEvent.setSystemMainTable(smtEvent);
				smtcEvent.setId(null);
				super.save(smtcEvent);
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}else{ //否则修改主表字段时，同样将主表字段的最新修改COPY给主表字段
			Criteria c= super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smtEvent));
			//这里使用columnName可能出现null指针
			c.add(Restrictions.eq("propertyName", smtc.getPropertyName()));//columnName
			SystemMainTableColumn smtcEvent = (SystemMainTableColumn) c.uniqueResult();
			if(smtcEvent!=null){
				Long oldSmtcEventId = smtcEvent.getId();
				try {
					BeanUtils.copyProperties(smtcEvent, smtc);
					smtcEvent.setSystemMainTable(smtEvent);
					smtcEvent.setId(oldSmtcEventId);
					super.save(smtcEvent);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			
		}
		//end
		return smtc;
	}

	private boolean existKeyword(String keyword){
		List<String> list = new ArrayList<String>();
		list.add("private");
		list.add("protected");
		list.add("public");
		list.add("abstract");
		list.add("class");
		list.add("extends");
		list.add("final");
		list.add("implements");
		list.add("interface");
		list.add("native");
		list.add("new");
		list.add("static");
		list.add("strictfp");
		list.add("synchronized");
		list.add("transient");//end
		list.add("import");
		list.add("package");
		if(list.contains(keyword)){
			return true;
		}
		return false;
		
	}
	public void saveSystemMainTableDeploy(SystemMainTable smt) {
		SessionFactory sf = super.getHibernateSessionFactory();
		SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sf;
		Dialect dialect = sessionFactoryImpl.getDialect();
		StringBuffer buff = new StringBuffer();
		String dialectName = PropertiesUtil.getProperties("hibernate.dialect");
		
		//发布主表时自动增加表名前缀
		String tablePrefix = PropertiesUtil.getProperties("system.config.scitable.prefix", "itil_sci_");
		String tableName = smt.getTableName();
		if(smt.getTableName().indexOf(tablePrefix)==-1){
			tableName = tablePrefix+ smt.getTableName();
		}
		
		if(dialectName.equalsIgnoreCase("org.hibernate.dialect.SQLServerDialect")){
			buff.append("CREATE TABLE ").append( tableName ).append("(");
			buff.append(" ID ").append(dialect.getTypeName(Types.BIGINT)).append(" identity primary key");
			buff.append(");");
		}else if(dialectName.equalsIgnoreCase("org.hibernate.dialect.OracleDialect")){
			buff.append("CREATE TABLE ").append( tableName ).append("(");
			buff.append(" ID ").append(dialect.getTypeName(Types.BIGINT)).append(" primary key");
			buff.append(");");
		}
		
		try {
			Connection conn = super.getSession().connection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(buff.toString());
			conn.commit();
		} catch (Exception e) {
			System.out.println("创建的表已经存在，直接进入字段增加");
		}	
		
		SystemMainTableColumn pkc = smt.getPrimaryKeyColumn();
		String pkcname = pkc.getColumnName();
		
		//初始化创建人创建日期修改人和修改日期
		addCreateUserDate(smt);
		
		//end
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.addOrder(Order.asc("id"));
		List<SystemMainTableColumn> list = c.list();
		for(SystemMainTableColumn smtc : list){

			String columnName = smtc.getColumnName();
			if(!pkcname.equalsIgnoreCase(columnName)){
				SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
				PropertyType pt = smtc.getPropertyType();
				if(pt==null){
					throw new ServiceException(smt.getTableCnName()+"的"+smtc.getPropertyName()+"字段的属性类型不可以为null");
				}
				String ptName = pt.getPropertyTypeName();
			
				String columnTypeName = smtcType.getColumnTypeName();
				Integer length = smtc.getLength();
				if(length==null|| length.intValue()==0){
					length = 200;
				}
				
				String sqlColumnType = dialect.getTypeName(Types.VARCHAR);
				if(ptName.equalsIgnoreCase("BaseObject")){
					sqlColumnType = dialect.getTypeName(Types.BIGINT);
				}else if(ptName.equalsIgnoreCase("Long")){
					sqlColumnType = dialect.getTypeName(Types.BIGINT);
				}else if(ptName.equalsIgnoreCase("String")){
					sqlColumnType = dialect.getTypeName(Types.VARCHAR, length, 10, 0);
				}else if(ptName.equalsIgnoreCase("Integer")){
					sqlColumnType = dialect.getTypeName(Types.BIGINT);
				}else if(ptName.equalsIgnoreCase("Double")){
					sqlColumnType = dialect.getTypeName(Types.DOUBLE);
				}else if(ptName.equalsIgnoreCase("Date")){
					sqlColumnType = dialect.getTypeName(Types.DATE);
				}
		
				String hql="ALTER TABLE "+ tableName + " ADD " + columnName + " " +  sqlColumnType;
				try {
					Connection conn = super.getSession().connection();
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(hql);
					conn.commit();
				} catch (Exception e) {
					System.out.println("增加的字段已经存在, 继续增加新的字段");
				}	
			}
			
		}//新增字段增加完毕
		
		String className = smt.getClassName();
		int lastDot = className.lastIndexOf(".");
		String sourcePkg = className.substring(0, lastDot);
		String sourceClass = className.substring(lastDot+1);
		
		String targetClassName = null;
		if(sourcePkg.equalsIgnoreCase("com.digitalchina.itil.require.entity")){
			targetClassName = "com.digitalchina.itil.require.entity.RequireTableTemplate";
		}else{
			targetClassName = className;
		}
		
	}
	public void saveTableEntity(String sourcePkg, String sourceClassName, String targetClass){
		SessionFactory ssf = super.getHibernateSessionFactory();
		Configuration config = super.getConfiguration();
		String newClassName = sourcePkg+"."+ sourceClassName;
		//通过类全路径获取持久化类
		PersistentClass model = config.getClassMapping(newClassName);   
		if(model==null){ 
			//第一次发布主表，持久化类不存在，故新建持久化类
			String tablePrefix = PropertiesUtil.getProperties("system.config.scitable.prefix", "itil_sci_");
			SystemMainTable smt = customerTableService.genPersistentClass(model, sourcePkg, sourceClassName, targetClass, tablePrefix);
		
		}else{
			//将发布后新增的字段保存到扩展字段里，并更新相关系统主表和面板
			saveExtendProps(sourceClassName, model);
		}
	}
	private void saveExtendProps(String sourceClassName, PersistentClass model) {
		this.customerTableService.saveExtendProps(sourceClassName, model);
	}
	private void addCreateUserDate(SystemMainTable smt) {
		String userUniqueColumn = PropertiesUtil.getProperties("system.user.uniquecolumn", "userName");
		
		//增加创建日期等字段
		String hqlMaxOrder = "select max(smtc.order) from SystemMainTableColumn smtc where smtc.systemMainTable=?";
		Query query = super.createQuery(hqlMaxOrder, smt);
		Integer count = (Integer) query.uniqueResult();
		if(count==null) count = 0;
		
		Criteria cUsr = super.getCriteria(SystemMainTableColumn.class);
		cUsr.add(Restrictions.eq("systemMainTable", smt));
		cUsr.add(Restrictions.eq("propertyName", "createUser"));
		cUsr.setProjection(Projections.rowCount());
		Integer countUser = (Integer) cUsr.uniqueResult();
		if(countUser==null) countUser = 0;
		if(countUser.intValue()==0){
			PropertyType pt = super.get(PropertyType.class, 1L); //baseObject属性类型
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); //隐藏域
			
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("创建人");
			smtc.setColumnName("createUser");
			smtc.setPropertyName("createUser");
			smtc.setDescn("发布主表时系统自动创建");
			smtc.setOrder(count+1);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			
			SystemMainTable ftble = systemMainTableService.findSystemMainTableByClazz(UserInfo.class);
			smtc.setForeignTable(ftble);
			
			SystemMainTableColumn ftbcKey = systemMainColumnService.findSystemMainTableColumnByTableAndName(ftble, "id");
			smtc.setForeignTableKeyColumn(ftbcKey);
			
			
			SystemMainTableColumn ftbcValue = systemMainColumnService.findSystemMainTableColumnByTableAndName(ftble, userUniqueColumn);
			smtc.setForeignTableValueColumn(ftbcValue);
			
			
			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}
	
		Criteria ccreated = super.getCriteria(SystemMainTableColumn.class);
		ccreated.add(Restrictions.eq("systemMainTable", smt));
		ccreated.add(Restrictions.eq("propertyName", "createDate"));
		ccreated.setProjection(Projections.rowCount());
		Integer countcreated = (Integer) ccreated.uniqueResult();
		if(countcreated==null) countcreated = 0;
		if(countcreated.intValue()==0){
			PropertyType pt = super.get(PropertyType.class, 6L); //日期属性类型
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); //隐藏域
			
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("创建日期");
			smtc.setColumnName("createDate");
			smtc.setPropertyName("createDate");
			smtc.setDescn("发布主表时系统自动创建");
			smtc.setOrder(count+2);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}
		
		Criteria cmdUsr = super.getCriteria(SystemMainTableColumn.class);
		cmdUsr.add(Restrictions.eq("systemMainTable", smt));
		cmdUsr.add(Restrictions.eq("propertyName", "modifyUser"));
		cmdUsr.setProjection(Projections.rowCount());
		Integer countmdUsr = (Integer) cmdUsr.uniqueResult();
		if(countmdUsr==null) countmdUsr = 0;
		if(countmdUsr.intValue()==0){
			PropertyType pt = super.get(PropertyType.class, 1L); //baseObject属性类型
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); //隐藏域
			
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtct.setId(8L); //隐藏域
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("最后修改人");
			smtc.setColumnName("modifyUser");
			smtc.setPropertyName("modifyUser");
			smtc.setDescn("发布主表时系统自动创建");
			smtc.setOrder(count+3);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);

			SystemMainTable ftble = systemMainTableService.findSystemMainTableByClazz(UserInfo.class);
			smtc.setForeignTable(ftble);
			
			SystemMainTableColumn ftbcKey = systemMainColumnService.findSystemMainTableColumnByTableAndName(ftble, "id");
			smtc.setForeignTableKeyColumn(ftbcKey);
			
			SystemMainTableColumn ftbcValue = systemMainColumnService.findSystemMainTableColumnByTableAndName(ftble, userUniqueColumn);
			smtc.setForeignTableValueColumn(ftbcValue);
			
			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}
		
		Criteria cmodate = super.getCriteria(SystemMainTableColumn.class);
		cmodate.add(Restrictions.eq("systemMainTable", smt));
		cmodate.add(Restrictions.eq("propertyName", "modifyDate"));
		cmodate.setProjection(Projections.rowCount());
		Integer countmdate = (Integer) cmodate.uniqueResult();
		if(countmdate==null) countmdate = 0;
		if(countmdate.intValue()==0){
			PropertyType pt = super.get(PropertyType.class, 6L); //日期属性类型
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); //隐藏域
			
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtct.setId(8L); //隐藏域
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("最后修改日期");
			smtc.setColumnName("modifyDate");
			smtc.setPropertyName("modifyDate");
			smtc.setDescn("发布主表时系统自动创建");
			smtc.setOrder(count+4);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}
	}
	public SystemMainTable saveEventTableByMainTable(SystemMainTable smt) {
		SystemMainTable smtEvent = new SystemMainTable();
		try {
			//新增和修改都需要时刻同步主表的属性
			smtEvent.setDeployFlag(smt.getDeployFlag());
			smtEvent.setDescn(smt.getDescn());
			smtEvent.setId(null);
			smtEvent.setModule(smt.getModule());
			smtEvent.setPrimaryKeyColumn(null);
			smtEvent.setTableName(smt.getTableName()+"Event");
			smtEvent.setTableCnName(smt.getTableCnName()+"历史");
			smtEvent.setClassName(smt.getClassName()+"Event");
			smtEvent.setUserExtFlag(null);

			super.save(smtEvent);//保存主表的历史表
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//end save smt event
		
		//配置项基础信息
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt)); //获取主表的所有字段
		c.addOrder(Order.asc("id"));
		List<SystemMainTableColumn> smtcs = c.list();
		//int cicsize = 0;
		for(int i=0; i<smtcs.size(); i++){//遍历主表的字段
			//新建主表历史表的字段
			SystemMainTableColumn smtcEvent = new SystemMainTableColumn();
			SystemMainTableColumn smtc = smtcs.get(i);
			try {
				BeanUtils.copyProperties(smtcEvent, smtc);
				smtcEvent.setSystemMainTable(smtEvent);
				smtcEvent.setId(null);
				smtcEvent.setOrder(null);
				super.save(smtcEvent);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
			
		}
		
		addHiddenLongColumn(smtEvent, "根对象ID", "rootId", null);
		
		//获取主键字段
		Criteria cPk = super.getCriteria(SystemMainTableColumn.class);
		cPk.add(Restrictions.eq("systemMainTable", smtEvent)); //获取主表的所有字段
		cPk.add(Restrictions.ilike("propertyName", "id"));
		SystemMainTableColumn smtcPk = (SystemMainTableColumn) cPk.uniqueResult();
		smtEvent.setPrimaryKeyColumn(smtcPk);
		super.save(smtEvent);//保存主表的历史表
		
		return smtEvent;
	}
}
