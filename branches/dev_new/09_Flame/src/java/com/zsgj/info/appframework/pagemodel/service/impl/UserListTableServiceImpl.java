package com.zsgj.info.appframework.pagemodel.service.impl;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
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
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SimpleValue;

//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
//import com.digitalchina.info.appframework.metadata.service.SystemExtColumnServcie;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelType;
import com.zsgj.info.appframework.pagemodel.service.UserListTableService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.util.PathUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.util.asm.render.FreemarkerRender;
import com.zsgj.info.framework.util.asm.render.RenderClass;
import com.zsgj.info.framework.util.asm.render.RenderProperty;
import com.zsgj.info.framework.util.asm.render.Templates;
import com.zsgj.info.framework.util.code.RuntimeCode;

public class UserListTableServiceImpl extends BaseDao implements UserListTableService{
	private SystemColumnService systemColumnService;
	String FSP = System.getProperty("file.separator");
	String LSP = "\n";

	public SystemMainTable findCustomerTableById(String tableId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page findSystemMainTableByModule(Module module, String tableName,
			int pageNo, int pageSize) {
		
		Page page = null;		
		Criteria critera = super.createCriteria(SystemMainTable.class, "tableName", true);
		if(module!=null){
			critera.add(Restrictions.eq("module", module));
		}
		if(StringUtils.isNotBlank(tableName)){
			critera.add(Restrictions.disjunction()
					.add(Restrictions.ilike("tableName", tableName, MatchMode.ANYWHERE)) //忽略大小写
					.add(Restrictions.ilike("tableCnName", tableName, MatchMode.ANYWHERE)));
		}
		critera.add(Restrictions.eq("userListFlag", Integer.valueOf(1)));
		page = super.pagedQuery(critera, pageNo, pageSize);
		return page;		
	}

	public void removeSystemMainTable(String[] smtIds) {
		if(smtIds==null|| smtIds.length==0){
			throw new ServiceException("请选择要删除的系统主表");
		}
		for(int i=0; i<smtIds.length; i++){
			String smtId = smtIds[i];
			this.removeSystemMainTable(smtId);
		}
	}
	
	public void removeSystemMainTable(String smtId) {
		SystemMainTable smt = super.get(SystemMainTable.class, Long.valueOf(smtId));
		if(smt.getDeployFlag()!=null&& smt.getDeployFlag()==1){
			//throw new ServiceException("当前配置项类型处于发布状态不可以删除");
		}
		String hql="select cit from ConfigItemType cit where cit.systemMainTable=?";
//		List<ConfigItemType> citypes = super.find(hql, smt);
//		for(ConfigItemType cit : citypes){
//			super.executeUpdate("delete from ConfigItem ci where ci.configItemType=?", cit);
//		}
		super.executeUpdate("delete from ConfigItemType smtc where smtc.systemMainTable=?", smt);
		
		super.executeUpdate("delete from SystemMainTableColumn smtc where smtc.systemMainTable=?", smt);
		super.executeUpdate("delete from SystemTableSetting smtc where smtc.systemMainTable=?", smt);
		super.executeUpdate("delete from PagePanel smtc where smtc.systemMainTable=?", smt);
		super.executeUpdate("delete from PagePanel smtc where smtc.systemMainTable=?", smt);
		super.executeUpdate("delete from PagePanelColumn smtc where smtc.systemMainTable=?", smt);
		super.executeUpdate("delete from PagePanelTable smtc where smtc.systemMainTable=?", smt);
		super.executeUpdate("delete from PagePanelTableRelation smtc where smtc.systemMainTable=?", smt);
		super.remove(smt);

	}

	public void removeSystemMainTableColumn(String[] smtcIds) {
		if(smtcIds==null|| smtcIds.length==0){
			throw new ServiceException("请选择要删除的字段");
		}
		for(int i=0; i<smtcIds.length; i++){
			String smtcId = smtcIds[i];
			try {
				//删除主字段
				this.removeSystemMainTableColumn(smtcId);
			} catch (ServiceException e) {
				e.printStackTrace();
			}			
		}
		
	}

	/**
	 * 用户主表功能里删除字段，级联清空所有引用的字段，以及关联的可见字段
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2009-1-12 By sa
	 * @param smtcId void
	 */
	private void removeSystemMainTableColumn(String smtcId) {
		SystemMainTableColumn smtc = (SystemMainTableColumn) super.get(SystemMainTableColumn.class, Long.valueOf(smtcId));
		if(smtc!=null){
				SystemMainTable sysMainTable = smtc.getSystemMainTable();
				SystemMainTableColumn pkColumn = sysMainTable.getPrimaryKeyColumn();
				if(smtc== pkColumn){
					sysMainTable.setPrimaryKeyColumn(null);
					super.save(sysMainTable);
				}
				super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableKeyColumn=null" +
						" where foreignTableKeyColumn=?", smtc);
				super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableValueColumn=null" +
						" where foreignTableValueColumn=?", smtc);
				super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableParentColumn=null" +
						" where foreignTableParentColumn=?", smtc);
				
				super.executeUpdate("delete SystemTableSetting stqc where stqc.mainTableColumn=?", new Object[]{smtc});
				super.executeUpdate("delete UserTableSetting uts where uts.mainTableColumn=?", new Object[]{smtc});
				super.executeUpdate("delete SystemTableQueryColumn stqc where stqc.mainTableColumn=?", new Object[]{smtc});
				try {
					super.executeUpdate("delete PagePanelColumn ppc where ppc.mainTableColumn=?",new Object[] { smtc });
				} catch (Exception e) {
					e.printStackTrace();
				}
			super.remove(smtc);
		}else{
			throw new ServiceException("删除的主字段不存在");
		}

	}
	public SystemMainTable saveSystemMainTable(SystemMainTable smt) {
		
//		*****************************************************************************************************	
		smt.setUserListFlag(1);
		String tableName = smt.getTableName();
		tableName = StringUtils.capitalize(smt.getTableName());
		if(tableName.length()>21){
			throw new ServiceException("表名称不可以超过21个字符");
		}
		smt.setTableName(tableName);
		
		//新增的判断是不是第二次保存表名**********************************************************
		Criteria criteria = super.getCriteria(SystemMainTable.class);
		criteria.add(Restrictions.eq("tableName", tableName));
		SystemMainTable mainTable = (SystemMainTable)criteria.setMaxResults(1).uniqueResult();
		String className = null;
		if(mainTable==null){
			className = smt.getClassName()+"."+tableName;
		}else{
			className = smt.getClassName(); 
		}
		//**********************************************************
		
		smt.setClassName(className);		
		boolean isInsert = smt.getId()==null;			
		super.save(smt);
		
		if(isInsert){
			PropertyType pt = new PropertyType();
			pt.setId(2L);
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			SystemMainTableColumnType smtct = new SystemMainTableColumnType();
			smtct.setId(8L);
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("自动编号");
			smtc.setColumnName("id");
			smtc.setPropertyName("id");
			smtc.setDescn("主键，不可修改和删除");
			smtc.setOrder(1);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(1);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			smtc.setIsExtColumn(SystemMainTableColumn.isMain);
			super.save(smtc);
			smt.setPrimaryKeyColumn(smtc);
			
			super.save(smt);	
//**************************************************************************************************************
		}
		return smt;
	}

	public SystemMainTableColumn saveSystemMainTableColumn(
			SystemMainTableColumn smtc) {
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
					if(smt.getDeployFlag()!=null&& smt.getDeployFlag().intValue()==1){
						throw new ServiceException("当前用户表已经发布，不可以修改字段名称或类型");
					}
				}
			}
			
		}else{
			isInsert = true;
		}
		
		
		SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
		smtcType = super.get(SystemMainTableColumnType.class, Long.valueOf(smtcType.getId()));
		
		//begin_初始化附件字段
		if(smtcType.getColumnTypeName().equalsIgnoreCase("multiFile")){
			smtc.setUploadUrl("/infoAdmin/upload");
			smtc.setFileNamePrefix("file_");
			
			PropertyType pt = new PropertyType();
			pt.setId(3L); //购置必是字符串
			smtc.setPropertyType(pt);
			
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
		//end
		
		
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
		
		if(isInsert){
			SystemMainTableColumn smtcEvent = new SystemMainTableColumn();
			try {
				BeanUtils.copyProperties(smtcEvent, smtc);
				smtcEvent.setSystemMainTable(smtEvent);
				smtcEvent.setId(null);
				super.save(smtcEvent);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}else{
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
		String tableName =smt.getTableName();
		
		String tablePrefix = PropertiesUtil.getProperties("system.userlist.table.prefix", "itil_lst_");
		
		if(dialectName.equalsIgnoreCase("org.hibernate.dialect.SQLServerDialect")){
			buff.append("CREATE TABLE ").append(tablePrefix+ smt.getTableName()).append("(");
			buff.append(" ID ").append(dialect.getTypeName(Types.BIGINT)).append(" identity primary key");
			buff.append(");");
		}else if(dialectName.equalsIgnoreCase("org.hibernate.dialect.OracleDialect")){
			buff.append("CREATE TABLE ").append(smt.getTableName()).append("(");
			buff.append(" ID ").append(dialect.getTypeName(Types.BIGINT)).append(" primary key");
			buff.append(");");
		}
		
		try {
			Connection conn = super.getSession().connection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(buff.toString());
			conn.commit();
		} catch (Exception e) {
			//e.printStackTrace();
		}	
		
		SystemMainTableColumn pkc = smt.getPrimaryKeyColumn();
		String pkcname = pkc.getColumnName();
		
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.addOrder(Order.asc("id"));
		List<SystemMainTableColumn> list = c.list();
		for(SystemMainTableColumn smtc : list){

			String columnName = smtc.getColumnName();
			if(!pkcname.equalsIgnoreCase(columnName)){
				SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
				//smtcType = super.get(SystemMainTableColumnType.class, Long.valueOf(smtcType.getId()));
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
				
//				String sqlColumnType = dialect.getTypeName(Types.VARCHAR);
//				
//				if(columnTypeName.equalsIgnoreCase("text")){
//					
//					sqlColumnType = dialect.getTypeName(Types.VARCHAR, length, 10, 0);
//				}else if(columnTypeName.equalsIgnoreCase("textArea")){
//					sqlColumnType = dialect.getTypeName(Types.VARCHAR);
//				}else if(columnTypeName.equalsIgnoreCase("select")){
//					sqlColumnType = dialect.getTypeName(Types.BIGINT);
//				}else if(columnTypeName.equalsIgnoreCase("yesNoSelect")){
//					sqlColumnType = dialect.getTypeName(Types.INTEGER);
//				}else if(columnTypeName.equalsIgnoreCase("hidden")){
//					sqlColumnType = dialect.getTypeName(Types.BIGINT);
//				}else if(columnTypeName.equalsIgnoreCase("multiFile")){
//					sqlColumnType = dialect.getTypeName(Types.VARCHAR, length, 18, 0);
//				}
				String hql="ALTER TABLE "+ tablePrefix+ smt.getTableName() + " ADD " + columnName + " " +  sqlColumnType;
				try {
					Connection conn = super.getSession().connection();
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(hql);
					conn.commit();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("增加的字段已经存在");
				}	
			}
			
		}
		
		String className = smt.getClassName();
		int lastDot = className.lastIndexOf(".");
		String sourcePkg = className.substring(0, lastDot);
		String sourceClass = className.substring(lastDot+1);
		
	
		
		
		this.saveTableEntity(sourcePkg, sourceClass, "com.digitalchina.itil.config.extlist.entity.ExtList");
	}

	
	@SuppressWarnings("unchecked")
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}
	
	
	private void saveExtendProps(String sourceClassName, PersistentClass model) {
		//获取主表
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.ilike("tableName", sourceClassName, MatchMode.EXACT));
		List list = c.list();
		SystemMainTable smt = null;
		Class clazz = null;
		if(!list.isEmpty()){
			smt = (SystemMainTable) list.iterator().next();
			String className = smt.getClassName();
			clazz = this.getClass(className);
		}
		
		RenderClass rc = new RenderClass();
		ArrayList rcolumns = new ArrayList();
		
		
		//获取主表的所有字段
		Criteria cc = super.getCriteria(SystemMainTableColumn.class);
		cc.add(Restrictions.eq("systemMainTable", smt));
		cc.setFetchMode("propertyType", FetchMode.JOIN);
		cc.addOrder(Order.asc("id"));
		List<SystemMainTableColumn> columns = cc.list();
//		
//		//中间注释了将新增的字段加载到hibernate持久化类中的代码，因为没有成功
//		Criteria ccc = super.getCriteria(SystemMainTableColumn.class);
//		ccc.add(Restrictions.eq("systemMainTable", smt));
//		ccc.setFetchMode("propertyType", FetchMode.JOIN);
//		ccc.addOrder(Order.asc("id"));
//		List<SystemMainTableColumn> columnCcs = ccc.list();
//		
//		for(SystemMainTableColumn smtc : columnCcs){
//			String propertyName = smtc.getPropertyName();
//			
//			RenderProperty property = new RenderProperty();
//			property.setName(propertyName);
//			property.setField(propertyName);
//			//property.setLength(smtc.getLength());
//			if(propertyName.equalsIgnoreCase("id")){
//				property.setPrimary(true);
//			}
//			PropertyType pt = smtc.getPropertyType();
//			String ptClassName = pt.getPropertyTypeClass();
//			property.setType(ptClassName);
//			rcolumns.add(property);
//		}
//		
//		try {
//			this.genPersistentClass(model, "", sourceClassName, ConfigItem.class);
//		} catch (Exception e) {
//			System.out.println("发生重复添加mapping异常");
//		}			
		//end
		for(SystemMainTableColumn smtc : columns){
			//System.out.println("smtc name: "+ smtc.getPropertyName());
			
			//判断当前遍历的字段是否需要增加到hibernate持久化上下文里面
			boolean needAddFlag = this.needAddProperty2PersistClass(model, smtc);
			if(needAddFlag){
				
				//定义新的字段
				org.hibernate.mapping.Column column = new org.hibernate.mapping.Column();
				column.setName(smtc.getPropertyName());
				if(smtc.getIsMustInput()!=null && smtc.getIsMustInput().intValue()==1){
					column.setNullable(true);
				}
				if(smtc.getUniqueFlag()!=null && smtc.getUniqueFlag().intValue()==1){
					column.setUnique(true);
				}
				model.getTable().addColumn(column);
				//包装字段给一个VALUE
				SimpleValue value = new SimpleValue();
				value.setTable(model.getTable());
				PropertyType ptt = smtc.getPropertyType();
				String propertyTypeName = ptt.getPropertyTypeName();
				if(!propertyTypeName.equalsIgnoreCase("BaseObject")){
					value.setTypeName(propertyTypeName.toLowerCase());
				}else{
					SystemMainTable fsmt = smtc.getForeignTable();
					String fclassName = fsmt.getClassName();
					value.setTypeName(fclassName);
				}
				value.addColumn(column);
				//定义一个新的属性
				Property prop = new Property();
				prop.setValue(value);
				prop.setName(smtc.getPropertyName());
				prop.setNodeName(prop.getName());
				model.addProperty(prop);
				
			}//end need add
			
			//更新sessionFactory
			System.out.println("smtc name: "+ smtc.getPropertyName());
			//super.getSessionFactory().refreshPersistentClass(model, super.getConfiguration().getMapping());
		//	super.getConfiguration().buildSessionFactory();
			//end
			
//				给系统可见字段增加字段
			Criteria cSysInput = super.getCriteria(SystemTableSetting.class);
			cSysInput.add(Restrictions.eq("systemMainTable", smt));
			cSysInput.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
			cSysInput.add(Restrictions.eq("mainTableColumn", smtc));
			
			cSysInput.setProjection(Projections.rowCount());
			Integer sSysInputCount = (Integer) cSysInput.uniqueResult();
			if(sSysInputCount.intValue()==0){
				cSysInput.setProjection(Projections.max("order"));
				Integer sysInputMaxOrder = (Integer) cSysInput.uniqueResult();
				if(sysInputMaxOrder==null) sysInputMaxOrder = 0;
//					初始化输入系统可见字段
				SystemTableSetting stsInput = new SystemTableSetting();
				stsInput.setSettingType(SystemTableSetting.INPUT);
				stsInput.setSystemMainTable(smt);
				if(smtc instanceof SystemMainTableColumn){
					stsInput.setMainTableColumn((SystemMainTableColumn) smtc);
				}
				stsInput.setIsDisplay(new Integer(1));
				stsInput.setOrder(sysInputMaxOrder+1);
				this.save(stsInput);
				this.evict(stsInput);
				
			}
			
			Criteria cSysList = super.getCriteria(SystemTableSetting.class);
			cSysList.add(Restrictions.eq("systemMainTable", smt));
			cSysList.add(Restrictions.eq("settingType", SystemTableSetting.LIST));
			cSysList.add(Restrictions.eq("mainTableColumn", smtc));
			cSysList.setProjection(Projections.rowCount());
			
			Integer cSysListCount = (Integer) cSysList.uniqueResult();
			if(cSysListCount.intValue()==0){
				cSysList.setProjection(Projections.max("order"));
				Integer sysListMaxOrder = (Integer) cSysList.uniqueResult();
				if(sysListMaxOrder==null) sysListMaxOrder = 0;
//					初始化输入系统可见字段
				SystemTableSetting stsList = new SystemTableSetting();
				stsList.setSettingType(SystemTableSetting.LIST);
				stsList.setSystemMainTable(smt);
				if(smtc instanceof SystemMainTableColumn){
					stsList.setMainTableColumn((SystemMainTableColumn) smtc);
				}
				stsList.setIsDisplay(new Integer(1));
				stsList.setOrder(sysListMaxOrder+1);
				this.save(stsList);
				this.evict(stsList);
				
			}
								
			//给当前表的面板增加字段，默认可见
			Criteria cPanel = super.getCriteria(PagePanel.class);
			cPanel.add(Restrictions.eq("systemMainTable", smt));
			List<PagePanel> pagePanels = cPanel.list();
			if(pagePanels.isEmpty()){
				//如果没有面板，说明面板已经被删除，初始化
				//genTablePanels(smt);
				
			}else{
				for(PagePanel pItem : pagePanels){
					Criteria cpc = super.getCriteria(PagePanelColumn.class);
					cpc.add(Restrictions.eq("pagePanel", pItem));
					cpc.add(Restrictions.eq("mainTableColumn", smtc));
					cpc.setProjection(Projections.rowCount());
					Integer cpcCount = (Integer) cpc.uniqueResult();
					if(cpcCount.intValue()==0){
						cpc.setProjection(Projections.max("order"));
						Integer maxPanelOrder = (Integer) cpc.uniqueResult();
						if(maxPanelOrder==null) maxPanelOrder = 0;
						PagePanelColumn ppc = new PagePanelColumn();
						ppc.setPagePanel(pItem);
						ppc.setSystemMainTable(smt);
						ppc.setMainTableColumn(smtc);
						ppc.setOrder(maxPanelOrder+1);
						ppc.setIsDisplay(1);
						ppc.setIsMustInput(1);
						ppc.setMatchMode(null);
						super.save(ppc);
						
					}
				}
			}
			
		}//end
	}
	
	public void saveTableEntity(String sourcePkg, String sourceClassName,
			Class targetClass) {
		SessionFactory ssf = super.getHibernateSessionFactory();
		Configuration config = super.getConfiguration();
		String newClassName = sourcePkg+"."+ sourceClassName;
		//通过类全路径获取持久化类
		PersistentClass model = config.getClassMapping(newClassName);   
		if(model==null){
			//genEntityAndMapping(model, sourcePkg, sourceClassName, targetClass);
		
		}else{
			//将发布后新增的字段保存到扩展字段里，并更新相关系统主表和面板
			saveExtendProps(sourceClassName, model);
		}
		
		
	}
	
	private void saveTableEntity(String sourcePkg, String sourceClassName,
			String targetClass) {
		SessionFactory ssf = super.getHibernateSessionFactory();
		Configuration config = super.getConfiguration();
		String newClassName = sourcePkg+"."+ sourceClassName;
		//通过类全路径获取持久化类
		PersistentClass model = config.getClassMapping(newClassName);   
		if(model==null){
			String tablePrefix = PropertiesUtil.getProperties("system.userlist.table.prefix");
			
			genEntityAndMapping(model, sourcePkg, sourceClassName, targetClass, tablePrefix);
		
		}else{
			//将发布后新增的字段保存到扩展字段里，并更新相关系统主表和面板
			saveExtendProps(sourceClassName, model);
		}
	}
	

	//保存一个用户主表后自动生成对应的面板
	private void saveTablePanel(SystemMainTable smt){
		String className = smt.getClassName();
		String tableName = smt.getTableName();
		String tableCnName = smt.getTableCnName();
		String ppcount = "select count(*) from PagePanel ppc where ppc.systemMainTable=?";
		Query ppQuery = super.createQuery(ppcount, smt);
		Long ppcRows = (Long) ppQuery.uniqueResult();
		
		PagePanel panel = null;
		
		if(ppcRows.intValue()==0){
			panel = new PagePanel();
			panel.setName("panel_"+tableName);
			panel.setTitle(tableCnName);
			panel.setDescn(tableCnName+"面板，系统自动创建");
			panel.setGroupFlag(0);
			PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
			panel.setXtype(ppt);
			panel.setSettingType(SystemTableSetting.INPUT); //form
			panel.setSystemMainTable(smt);
			Module module = super.findUniqueBy(Module.class, "name", "配置项管理");
			panel.setModule(module);
			super.save(panel);
			
			//生成面板主表
			PagePanelTable pptble = new PagePanelTable();
			pptble.setPagePanel(panel);
			pptble.setSystemMainTable(smt);
			super.save(pptble);
			
//			取系统主表的所有字段，初始化系统可见字段
			int order = 1;
			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
			for(Column column : columns){
				//初始化输入系统可见字段
				SystemTableSetting stsInput = new SystemTableSetting();
				stsInput.setSettingType(SystemTableSetting.INPUT);
				stsInput.setSystemMainTable(smt);
				//if(column instanceof SystemMainTableColumn){
				stsInput.setMainTableColumn((SystemMainTableColumn) column);
//				}else if(column instanceof SystemMainTableExtColumn) {
//					stsInput.setExtendTableColumn((SystemMainTableExtColumn) column);
//				}
				stsInput.setIsDisplay(new Integer(1));
				stsInput.setOrder(order++);
				this.save(stsInput);
				this.evict(stsInput);
				//初始化列表页面的系统可见字段
				SystemTableSetting stsList = new SystemTableSetting();
				stsList.setSettingType(SystemTableSetting.LIST);
				stsList.setSystemMainTable(smt);
//				if(column instanceof SystemMainTableColumn){
					stsList.setMainTableColumn((SystemMainTableColumn) column);
//				}else if(column instanceof SystemMainTableExtColumn) {
//					stsList.setExtendTableColumn((SystemMainTableExtColumn) column);
//				}
				stsList.setIsDisplay(new Integer(1));
				stsList.setOrder(order++);
				this.save(stsList);
				this.evict(stsList);
			}
			
			String ppccount = "select count(*) from PagePanelColumn ppc where ppc.pagePanel=?";
			Query query = super.createQuery(ppccount, panel);
			Long cunt = (Long) query.uniqueResult();
			if(cunt.intValue()==0){
//				取所有的系统可见字段
				Criteria c = super.getCriteria(SystemTableSetting.class);
				c.add(Restrictions.eq("systemMainTable", smt));
				c.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
				c.addOrder(Order.asc("order"));
				List list = c.list();
				Iterator iter = list.iterator();
				while(iter.hasNext()){
					SystemTableSetting uts = (SystemTableSetting) iter.next();
					Column column = uts.getColumn();
					//遍历系统可见字段，生成面板字段
					PagePanelColumn ppc = new PagePanelColumn();
					ppc.setPagePanel(panel);
					ppc.setSystemMainTable(smt);
//					if(column instanceof SystemMainTableColumn){
						ppc.setMainTableColumn((SystemMainTableColumn) column);
//					}else if(column instanceof SystemMainTableExtColumn) {
//						ppc.setExtendTableColumn((SystemMainTableExtColumn) column);
//					}
					ppc.setIsDisplay(1);
					ppc.setOrder(uts.getOrder());
					ppc.setIsMustInput(column.getIsMustInput());
					super.save(ppc);
				}
			}
			
		}
		
	}
	
	//在配置项类型的面板没有的情况下，直接保存会默认生成
	private void genTablePanels(SystemMainTable smt) {
		PagePanel panel = new PagePanel();
		panel.setName("panel_"+smt.getTableCnName()+"Panel");
		panel.setTitle(smt.getTableCnName());
		panel.setDescn(smt.getTableCnName()+"面板，用户增加此类配置项时系统自动创建此面板");
		panel.setGroupFlag(0);
		PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
		panel.setXtype(ppt);
		panel.setSettingType(SystemTableSetting.INPUT); //form
		panel.setSystemMainTable(smt);
		Module module = super.findUniqueBy(Module.class, "name", "配置项管理");
		panel.setModule(module);
		super.save(panel);
		
		//生成面板主表
		PagePanelTable pptble = new PagePanelTable();
		pptble.setPagePanel(panel);
		pptble.setSystemMainTable(smt);
		super.save(pptble);
	}

	/**
	 * 将元数据配置实例化
	 * @Methods Name genEntityAndMapping
	 * @Create In Apr 13, 2010 By lee
	 * @param model
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass
	 * @param prefix void
	 */
	private void genEntityAndMapping(PersistentClass model, String sourcePkg, String sourceClassName, 
			String targetClass, String prefix) {
		SessionFactory ssf = super.getHibernateSessionFactory();
		Configuration config = super.getConfiguration();
		StringBuffer code = new StringBuffer();
		code.append("package ").append(sourcePkg).append(";").append(LSP);
		code.append(LSP);
		
		StringBuffer importer = new StringBuffer();
		importer.append("import java.util.Date;").append(LSP);
		importer.append("import com.digitalchina.info.framework.dao.BaseObject;").append(LSP);
		importer.append("import com.digitalchina.info.framework.security.entity.UserInfo;").append(LSP);
		
		code.append(importer);

		StringBuffer field = new StringBuffer();
		StringBuffer setter = new StringBuffer();
		StringBuffer getter = new StringBuffer();
		StringBuffer equals = new StringBuffer();
		StringBuffer hashCode = new StringBuffer();
		
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.ilike("tableName", sourceClassName, MatchMode.EXACT));
		List list = c.list();
		SystemMainTable smt = null;
		String className = null;
//			持久类对象描述
		RenderClass rc = new RenderClass();
		ArrayList rcolumns = new ArrayList();
		if(!list.isEmpty()){
			smt = (SystemMainTable) list.iterator().next();
			className = smt.getClassName();
			code.append(LSP);
			code.append("public class ").append(sourceClassName).append(" extends BaseObject {").append(LSP);		
		
			Criteria cc = super.getCriteria(SystemMainTableColumn.class);
			cc.add(Restrictions.eq("systemMainTable", smt));
			cc.addOrder(Order.asc("id"));
			List<SystemMainTableColumn> listcc = cc.list();
			
			for(SystemMainTableColumn smtc : listcc){
				//String columnName = smtc.getColumnName();
				String propertyName = smtc.getPropertyName();
				if(propertyName==null){
					propertyName = smtc.getColumnName2();
				}
				String bPropertyName = propertyName.substring(0,1).toUpperCase()+ propertyName.substring(1);
				
				PropertyType pt = smtc.getPropertyType();
				//String ptName = pt.getName();
				String ptClassName = pt.getPropertyTypeClass();
				//增加字段
				if(!ptClassName.equals("com.digitalchina.info.framework.dao.BaseObject")){
					RenderProperty property = new RenderProperty();
					property.setName(propertyName);
					property.setField(propertyName);
					//property.setLength(smtc.getLength());
					if(propertyName.equalsIgnoreCase("id")){
						property.setPrimary(true);
					}
					property.setType(ptClassName);
					
					//begin
					if(ptClassName.equals("java.util.Set")){
						SystemMainTable ftable = smtc.getForeignTable();
						SystemMainTableColumn fkey = smtc.getForeignTableKeyColumn();
						SystemMainTableColumn fvalue = smtc.getForeignTableValueColumn();
						String fclassName = ftable.getClassName();
						
						SystemMainTable refTable = smtc.getReferencedTable(); //关联表userRole
						String refTableName = refTable.getTableName();
						Integer userListFlag = refTable.getUserListFlag();
						Integer userExtFlag = refTable.getUserExtFlag();
						Integer userScidFlag = refTable.getUserScidFlag();
						
						//获取关联表名称并自动给表名称加前缀
						if(userListFlag!=null&& userListFlag.intValue()==1){
							String lstPrefix = PropertiesUtil.getProperties("system.userlist.table.prefix","itil_lst_");
							refTableName = lstPrefix + refTableName;
						}else if(userExtFlag!=null&& userExtFlag.intValue()==1){
							String lstPrefix = PropertiesUtil.getProperties("system.config.citable.prefix","itil_ci_");
							refTableName = lstPrefix + refTableName;
						}else if(userScidFlag!=null&& userScidFlag.intValue()==1){
							String lstPrefix = PropertiesUtil.getProperties("system.config.scitable.prefix","itil_sci_");
							refTableName = lstPrefix + refTableName;
						}
						
						SystemMainTableColumn refTableVColumn = smtc.getReferencedTableValueColumn();//user_id
						SystemMainTableColumn refTablePColumn = smtc.getReferencedTableParentColumn(); //role_id
						
						property.setForeignClass(fclassName);//role class
						property.setRefTable(refTableName); //userRole
						property.setRefPColumn(refTablePColumn.getPropertyName()); //user_id
						property.setRefVColunn(refTableVColumn.getPropertyName()); //role_id
					}
					//end
					
					rcolumns.add(property);
					
					if(ptClassName.equals("java.util.Set")){
						field.append("   private ").append(ptClassName).append(" ").append(propertyName).append(" = new java.util.HashSet();").append(LSP);
					}else{
						field.append("   private ").append(ptClassName).append(" ").append(propertyName).append(";").append(LSP);
					}
					
					
					setter.append("   public void set").append(bPropertyName).append("(").append(ptClassName).append(" ").append(propertyName).append("){").append(LSP);
					setter.append("	     this.").append(propertyName).append("=").append(propertyName).append(";").append(LSP);
					setter.append("   }").append(LSP);
					
					getter.append("   public ").append(ptClassName).append(" get").append(bPropertyName).append("(){").append(LSP);
					getter.append("	     return this.").append(propertyName).append(";").append(LSP);
					getter.append("   }").append(LSP);
					
					
				}else{
					SystemMainTable ftable = smtc.getForeignTable();
					SystemMainTableColumn fkey = smtc.getForeignTableKeyColumn();
					SystemMainTableColumn fvalue = smtc.getForeignTableValueColumn();
					String fclassName = ftable.getClassName();
					ptClassName = fclassName;
					
					
					RenderProperty property = new RenderProperty();
					property.setName(propertyName);
					property.setField(propertyName);
					//property.setLength(smtc.getLength());
					if(propertyName.equalsIgnoreCase("id")){
						property.setPrimary(true);
					}
					property.setType(ptClassName);
					
					rcolumns.add(property);
					
					//对象属性的setter和getter
					field.append("   private ").append(ptClassName).append(" ").append(propertyName).append(";").append(LSP);
					
					setter.append("   public void set").append(bPropertyName).append("(").append(ptClassName).append(" ").append(propertyName).append("){").append(LSP);
					setter.append("	     this.").append(propertyName).append("=").append(propertyName).append(";").append(LSP);
					setter.append("   }").append(LSP);
					
					getter.append("   public ").append(ptClassName).append(" get").append(bPropertyName).append("(){").append(LSP);
					getter.append("	     return this.").append(propertyName).append(";").append(LSP);
					getter.append("   }").append(LSP);
				}
		
				
			}
			rc.setProperties(rcolumns);
			
			//equals方法
			equals.append("   public boolean equals(Object obj) {").append(LSP);
			equals.append("		if (this == obj)").append(LSP);
			equals.append("   		return true;").append(LSP);
			equals.append("   	if (!super.equals(obj))").append(LSP);
			equals.append("   		return false;").append(LSP);
			equals.append("   	if (getClass() != obj.getClass())").append(LSP);
			equals.append("   		return false;").append(LSP);
			equals.append("   	final ").append(smt.getClassName()).append(" other = (").append(smt.getClassName()).append(") obj;").append(LSP);
			
			SystemMainTableColumn pkc = smt.getPrimaryKeyColumn();
			String pkcPropertyName = pkc.getPropertyName();
			equals.append("   	if ("+ pkcPropertyName +" == null) {").append(LSP);;
			equals.append("   		if (other."+ pkcPropertyName +" != null)").append(LSP);
			equals.append("   			return false;").append(LSP);
			equals.append("   	} else if (!"+ pkcPropertyName +".equals(other."+ pkcPropertyName +"))").append(LSP);
			equals.append("   		return false;").append(LSP);
			equals.append("   	return true; ").append(LSP);
			equals.append("   }").append(LSP).append(LSP);
			
			//hashCode方法
			hashCode.append("   public int hashCode() {").append(LSP);
			hashCode.append("		final int prime = 31;").append(LSP);
			hashCode.append("   	int result = super.hashCode();").append(LSP);
			hashCode.append("   	result = prime * result + (("+ pkcPropertyName +" == null) ? 0 : "+ pkcPropertyName +".hashCode());").append(LSP);
			hashCode.append("   	return result;").append(LSP);
			hashCode.append("  	}").append(LSP);
			
			code.append(field).append(LSP).append(setter).append(LSP).append(getter).append(LSP).append(equals).append(LSP).append(hashCode);
			
			code.append("} ");
			
			if(!listcc.isEmpty()){
				String targetDIR = null;
				try {
					//String targetClassName = targetClass.getName();
					targetDIR = PathUtil.getPkgPathFromClass(targetClass);//这个是得到放到jboss中的位置
				} catch (Exception e) {
					e.printStackTrace();
				}		
				
				rc.setClassName(sourcePkg+"."+ sourceClassName);
				String tablePrefix = prefix;
				rc.setTableName(tablePrefix + sourceClassName);
				//rc.setTableName(sourceClassName);
				String tmp = System.getProperty("java.class.path");
				String WEB_INF_CLASSES = this.getClass().getResource("/").toString().replace("/", "\\");
				int fileLength = "file:\\".length();
				WEB_INF_CLASSES = WEB_INF_CLASSES.substring(fileLength);
				
				
				//RuntimeCode.genEntityAndClass(WEB_INF_CLASSES, sourcePkg, sourceClassName, code.toString(), targetClassName);
				
				RuntimeCode.genEntityAndClass(WEB_INF_CLASSES, sourcePkg, sourceClassName, code.toString(), targetClass);
				//开始生成hbm.xml
				String hbmDIR = targetDIR +"map" + FSP + sourceClassName + ".hbm.xml";
				
				FreemarkerRender render = new FreemarkerRender();
				render.render(rc, Templates.TEMPLATE_HIBERNATE3, hbmDIR);

				
				int classesIndex = hbmDIR.indexOf("classes");
				hbmDIR = hbmDIR.substring(classesIndex+7).replace("\\", "/");
				URL  url = this.getClass().getResource(hbmDIR);   
		        config.addURL(url);  
		        model = config.getClassMapping(className);
				try {
					ssf.addPersistentClass(model, config.getMapping());
				} catch (Exception e) {
					System.out.println("重复增加持久化类发生异常");
				}		        
				if(smt!=null){
		        	smt.setDeployFlag(1);
		        	super.save(smt);	
		        	//自动生成面板
		        	this.saveTablePanel(smt);		
		        }
			}
			
			
		}
	}
	
	//是否需要增加新的属性进入持久化类
	private boolean needAddProperty2PersistClass(PersistentClass model, Column column){
		boolean result = true; //默认需要增加
		Iterator<Property> iter = model.getPropertyIterator();
		while(iter.hasNext()){
			Property item =  iter.next();
			String propertyName = item.getName();
			Object value = item.getValue();
			if(column.getPropertyName().equalsIgnoreCase("id")){
				if(column.getPropertyName().equalsIgnoreCase(propertyName)){ //只有持久化类里有了，就不再加入
					result = false;
					return result;
				}
			}
			
		}
		return result;
	}

	public void setSystemColumnService(SystemColumnService systemColumnService) {
		this.systemColumnService = systemColumnService;
	}

}
