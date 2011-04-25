package com.digitalchina.info.appframework.metadata.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.tuple.StandardProperty;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.Type;

import com.digitalchina.info.appframework.metadata.entity.PropertyType;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemTableQuery;
import com.digitalchina.info.appframework.metadata.entity.SystemTableQueryColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.PropertiesUtil;

public class SystemMainColumnServiceImpl extends BaseDao implements SystemMainColumnService {

	public SystemMainTableColumn findSystemMainTableColumnById(String smtcId) {
		String hql = "from SystemMainTableColumn smtc where smtc.id = ?";
		Query query = super.createQuery(hql, new Object[]{Long.valueOf(smtcId)});
		SystemMainTableColumn smtc = (SystemMainTableColumn) query.uniqueResult();
		return smtc;
	}
	
	/**
	 * 二期框架新增方法
	 */
	public SystemMainTableColumn findSystemMainTableColumnByTableAndName(SystemMainTable smt, String propName) {
		SystemMainTableColumn smtc = null;
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.ilike("propertyName", propName));
		List list = c.list();
		if(!list.isEmpty()){
			smtc = (SystemMainTableColumn) list.iterator().next();
		}
		return smtc;
	}

	public List findSystemMainTableColumns(SystemMainTable smt) {//2期框架修改
		String hql = "from SystemMainTableColumn smtc where smtc.systemMainTable = ? order by smtc.id";
		Query query = super.createQuery(hql, new Object[]{smt});
		List list = query.list();
		return list;
	}

	public ResultSetMetaData getResultSetMetaData(Connection connect, String tableName) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		try {
			stmt=connect.createStatement();
	   		rs=stmt.executeQuery("select * from "+tableName);
	   		meta = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
		return meta;
	}
	
	private StandardProperty[] getEntityProperties(String entityClass){
		StandardProperty[] sps = null;
		Map map = this.getHibernateTemplate().getSessionFactory().getAllClassMetadata();
		Set keySet = map.keySet();
		Iterator iter = keySet.iterator();
		while(iter.hasNext()){  
			String classname = (String) iter.next();
			if(entityClass.equalsIgnoreCase(classname)){
				ClassMetadata classMetadata = (ClassMetadata) map.get(classname);
				SingleTableEntityPersister stepersister = (SingleTableEntityPersister) classMetadata;
				stepersister.getIdentifierPropertyName();
				EntityMetamodel entityMetamode = stepersister.getEntityMetamodel();
				sps = entityMetamode.getProperties();
				for(int i=0; i<sps.length; i++){
					StandardProperty sp = sps[i];
					String propertyName = sp.getName();
					Type propertyType = sp.getType();
					String typeName = propertyType.getName();
				}
				break;
			}
			
			
		}
		return sps;
	}
	
	private StandardProperty getEntityProperty(String entityClass, String columnName){
		StandardProperty sp = null;
		Map map = this.getHibernateTemplate().getSessionFactory().getAllClassMetadata();
		Set keySet = map.keySet();
		Iterator iter = keySet.iterator();
		while(iter.hasNext()){  
			String classname = (String) iter.next();
			if(entityClass.equalsIgnoreCase(classname)){
				ClassMetadata classMetadata = (ClassMetadata) map.get(classname);
				SingleTableEntityPersister stepersister = (SingleTableEntityPersister) classMetadata;
				stepersister.getIdentifierPropertyName();
				EntityMetamodel entityMetamode = stepersister.getEntityMetamodel();
				StandardProperty[] sps = entityMetamode.getProperties();
				for(int i=0; i<sps.length; i++){
					StandardProperty item = sps[i];
					String propertyName = item.getName();
					String nodeName = item.getNode();
					Type propertyType = item.getType();
					String typeName = propertyType.getName();
					if(propertyName.equalsIgnoreCase(columnName)){
						sp = item;
						break;
					}
				}
			}
		}
		return sp;
	}
	
	private PropertyType getPropertyTypeByHbnType(String ptypeclass){
		Criteria c = this.getCriteria(PropertyType.class);
		c.add(Restrictions.like("hibernateTypeClass", ptypeclass, MatchMode.EXACT).ignoreCase());
		PropertyType ptobj = (PropertyType) c.uniqueResult();
		return ptobj;
	}
	
	private PropertyType getPropertyTypeByName(String propertyTypeName){
		Criteria c = this.getCriteria(PropertyType.class);
		c.add(Restrictions.like("propertyTypeName", propertyTypeName, MatchMode.EXACT).ignoreCase());
		PropertyType ptobj = (PropertyType) c.uniqueResult();
		return ptobj;
	}
	
	public void saveSystemMainTableColumnsLoadFromDb(SystemMainTable smt) {
		Session session = this.getSession();
		Connection connect = session.connection();
	
		ResultSetMetaData rsmd = this.getResultSetMetaData(connect, smt.getTableName());
		if(rsmd==null) {
			throw new ServiceException("系统主表中存在无效表名");
		}
		try {
			long rsmdCount = rsmd.getColumnCount();
			for(int i=1; i<=rsmdCount; i++){
				String columnName = rsmd.getColumnName(i);
				//if(columnName.equalsIgnoreCase("id"))
				String typeName = rsmd.getColumnTypeName(i);
				/*String xname = rsmd.getColumnClassName(i);
				String bigints="java.math.BigDecimal";
				String strings="java.lang.String";
				String times = "java.sql.Timestamp";*/
				
				rsmd.getCatalogName(i);
				rsmd.getColumnClassName(i);
				rsmd.getColumnLabel(i);
				
				int length = rsmd.getPrecision(i);
				//判断此字段是否已在表结构定义表中存在
				String hql="select count(mc.id) from SystemMainTableColumn mc where mc.systemMainTable=? and mc.columnName=? ";
				Query query = this.createQuery(hql, new Object[]{smt, columnName});
				Long count = (Long) query.uniqueResult();
				if(count.intValue()==0){
					
					SystemMainTableColumn mc = new SystemMainTableColumn();
					//mc.setPropertyName(columnName.substring(0,1).toLowerCase()+ columnName.substring(1));
					if(PropertiesUtil.getProperties("jdbc.driverClassName").equals("oracle.jdbc.driver.OracleDriver")){
						mc.setPropertyName(columnName);
					}else{
						mc.setPropertyName(columnName.substring(0,1).toLowerCase()+ columnName.substring(1));
					}
					//columnName.substring(0,1).toLowerCase()+ columnName.substring(1))
					mc.setColumnCnName(columnName);
					mc.setSystemMainTable(smt);
					mc.setColumnName(columnName);
					mc.setColumnType(typeName);
					mc.setOrder(Integer.valueOf(i));
					mc.setIsExtColumn(SystemMainTableColumn.isMain);
					
					StandardProperty stp = this.getEntityProperty(smt.getClassName(), columnName);
					if(stp!=null){
						String propertyName = stp.getName();
						String nodeName = stp.getNode();
						Type ptype = stp.getType();
						String ptypename = ptype.getName();
						Class ptclass = ptype.getClass();
						String ptclassname = ptclass.getName();
						//if(ptclass.isAssignableFrom(com.digitalchina.info.framework.dao.BaseObject.class)){
						if(ptclassname.equalsIgnoreCase("org.hibernate.type.ManyToOneType")){
							Criteria c = this.getCriteria(SystemMainTable.class);
							c.add(Restrictions.eq("className", ptypename));
							SystemMainTable ftable = (SystemMainTable) c.uniqueResult();
							if(ftable!=null){
								mc.setForeignTable(ftable);
								mc.setForeignTableKeyColumn(ftable.getPrimaryKeyColumn());
							}				
							Criteria ctype = this.getCriteria(SystemMainTableColumnType.class);
							ctype.add(Restrictions.eq("columnTypeName", "select"));
							SystemMainTableColumnType smtcType = (SystemMainTableColumnType) ctype.uniqueResult();
							mc.setSystemMainTableColumnType(smtcType);
							
							PropertyType propertyType = this.getPropertyTypeByHbnType("org.hibernate.type.ManyToOneType");
							mc.setPropertyType(propertyType);
						}else{//非ManyToOneType
							PropertyType propertyType = this.getPropertyTypeByHbnType(ptclassname);
							mc.setPropertyType(propertyType);
							
							if(ptclassname.equalsIgnoreCase("org.hibernate.type.StringType")){
								SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, Long.valueOf(1));
								mc.setSystemMainTableColumnType(smtct);
							}else if(ptclassname.equalsIgnoreCase("org.hibernate.type.LongType")){
								SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, Long.valueOf(8));
								mc.setSystemMainTableColumnType(smtct);
							}else if(ptclassname.equalsIgnoreCase("org.hibernate.type.IntegerType")){
								SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, Long.valueOf(8));
								mc.setSystemMainTableColumnType(smtct);
							}else if(ptclassname.equalsIgnoreCase("org.hibernate.type.DoubleType")){
								SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, Long.valueOf(1));
								mc.setSystemMainTableColumnType(smtct);
							}else if(ptclassname.equalsIgnoreCase("org.hibernate.type.DateType")){
								SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, Long.valueOf(9));
								mc.setSystemMainTableColumnType(smtct);
							}else{
								SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, Long.valueOf(1));
								mc.setSystemMainTableColumnType(smtct);
							}
						}
						
					}else{
						if(mc.getColumnName().equalsIgnoreCase("id")){
							PropertyType propertyType = this.getPropertyTypeByHbnType("org.hibernate.type.LongType");
							mc.setPropertyType(propertyType);
							
							SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, Long.valueOf(8));
							mc.setSystemMainTableColumnType(smtct);	
							mc.setColumnCnName("自动编号");
							
						}
						
					}
					
					
					mc.setLength(new Integer(length));
					mc.setTableName(smt.getTableName());
					mc.setIsSearchItem(Integer.valueOf(0));
					mc.setIsOutputItem(Integer.valueOf(0));
					mc.setIsMustInput(Integer.valueOf(0));
					this.save(mc);
					
					if(mc.getPropertyName().equalsIgnoreCase("id")){
						smt.setPrimaryKeyColumn(mc);
						super.save(smt);
					}
					
					//保存新增字段到系统可见字段和系统查询字段,
					int sysTableSettingMaxOrderList = getSystemTableSettingMaxOrder(smt, SystemTableSetting.LIST);
					SystemTableSetting stsList = new SystemTableSetting();
					stsList.setSettingType(SystemTableSetting.LIST);
					stsList.setSystemMainTable(smt);
					stsList.setMainTableColumn(mc);
					stsList.setIsDisplay(new Integer(1));
					stsList.setOrder(new Integer(sysTableSettingMaxOrderList+1));
					this.save(stsList);
					this.evict(stsList);
					
					int sysTableSettingMaxOrderInput = getSystemTableSettingMaxOrder(smt, SystemTableSetting.INPUT);
					SystemTableSetting stsInput = new SystemTableSetting();
					stsInput.setSettingType(SystemTableSetting.INPUT);
					stsInput.setSystemMainTable(smt);
					stsInput.setMainTableColumn(mc);
					stsInput.setIsDisplay(new Integer(1));
					stsInput.setOrder(new Integer(sysTableSettingMaxOrderInput+1));
					this.save(stsInput);
					this.evict(stsInput);
					
					//保存新增字段到用户可见字段和用户查询字段2张表，这段代码需要吗&&&&&&&&&&&&&&&&
//					String uhsql="select usr.id from UserInfo usr where enabled=?";
//					List userIds = this.find(uhsql, Integer.valueOf(1));
//					Iterator iterUser = userIds.iterator();
//					while(iterUser.hasNext()){ //遍历所有用户
//						Long userId = (Long) iterUser.next();
//						UserInfo userInfo = new UserInfo(userId);
//						int maxOrderList = getUserTableSettingMaxOrder(smt, userInfo, UserTableSetting.LIST);
//						
//						UserTableSetting utsList = new UserTableSetting();
//						utsList.setSettingType(UserTableSetting.LIST);
//						utsList.setUserInfo(userInfo);
//						utsList.setSystemMainTable(smt);
//						utsList.setMainTableColumn(mc);
//						utsList.setIsDisplay(new Integer(1));
//						utsList.setOrder(new Integer(maxOrderList+1));
//						this.save(utsList);
//						this.evict(utsList);
//						
//						int maxOrderInput = getUserTableSettingMaxOrder(smt, userInfo, UserTableSetting.INPUT);
//						UserTableSetting utsInput = new UserTableSetting();
//						utsInput.setSettingType(UserTableSetting.INPUT);
//						utsInput.setUserInfo(userInfo);
//						utsInput.setSystemMainTable(smt);
//						utsInput.setMainTableColumn(mc);
//						utsInput.setIsDisplay(new Integer(1));
//						utsInput.setOrder(new Integer(maxOrderInput+1));
//						this.save(utsInput);
//						this.evict(utsInput);
//					}
					
					
//					增加新字段到页面面板
					Criteria c = super.getCriteria(PagePanel.class);
					c.add(Restrictions.eq("systemMainTable", smt));
					c.setProjection(Projections.rowCount());
					Integer pcc = (Integer) c.uniqueResult();
					if(pcc!=null&& pcc.intValue()>0){
						c.setProjection(null);
						c.setResultTransformer(Criteria.ROOT_ENTITY);
						List<PagePanel> panels = c.list();
						for(PagePanel pagePanel : panels){
							PagePanelColumn ppc = new PagePanelColumn();
							ppc.setPagePanel(pagePanel);
							ppc.setSystemMainTable(smt);
							ppc.setMainTableColumn((SystemMainTableColumn) mc);
							ppc.setIsDisplay(1);
							int maxOrder = this.getPanelColumnMaxOrder(pagePanel);
							ppc.setOrder(maxOrder+1);
							ppc.setIsMustInput(mc.getIsMustInput());
							super.save(ppc);
						}
					}
						
				}		
			}//for
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//JdbcUtil.close(connect);
			//session.close();
		}
	}

	private int getUserTableSettingMaxOrder(SystemMainTable sysMainTable, UserInfo userInfo, Integer settingType) {
		Criteria dcMaxOrder = this.getCriteria(UserTableSetting.class);
		dcMaxOrder.add(Restrictions.eq("settingType", settingType));
		dcMaxOrder.add(Restrictions.eq("userInfo", userInfo));
		dcMaxOrder.add(Restrictions.eq("systemMainTable", sysMainTable));
		dcMaxOrder.setProjection(Projections.projectionList()
				.add(Projections.max("order").as("maxOrder"))
		);
		Object maxOrder = dcMaxOrder.uniqueResult();
		int orderMax = 0; 
		if(maxOrder!=null){
			orderMax = ((Integer)maxOrder).intValue();
		}
		return orderMax;
	}
	
	private int getSystemTableSettingMaxOrder(SystemMainTable sysMainTable, Integer settingType) {
		Criteria dcMaxOrder = this.getCriteria(SystemTableSetting.class);
		dcMaxOrder.add(Restrictions.eq("settingType", settingType));
		dcMaxOrder.add(Restrictions.eq("systemMainTable", sysMainTable));
		dcMaxOrder.setProjection(Projections.projectionList()
				.add(Projections.max("order").as("maxOrder"))
		);
		Object maxOrder = dcMaxOrder.uniqueResult();
		int orderMax = 0; 
		if(maxOrder!=null){
			orderMax = ((Integer)maxOrder).intValue();
		}
		return orderMax;
	}
	private int getSystemTableQueryMaxOrder(SystemMainTable sysMainTable) {
		Criteria stqMaxOrder = this.getCriteria(SystemTableQueryColumn.class);
		stqMaxOrder.add(Restrictions.eq("systemMainTable", sysMainTable));
		stqMaxOrder.setProjection(Projections.projectionList()
				.add(Projections.max("order").as("maxOrder"))
		);
		Object maxOrder = stqMaxOrder.uniqueResult();
		int orderMax = 0; 
		if(maxOrder!=null){
			orderMax = ((Integer)maxOrder).intValue();
		}
		return orderMax;
	}
	
	public List findSystemMainTableColumns(SystemMainTable smt,
			boolean validteFlag) {
		String hql = "";
		if(validteFlag){
			hql = "from SystemMainTableColumn smtc where smtc.systemMainTable = ? and smtc.columnCnName is not null";
		}else{
			hql = "from SystemMainTableColumn smtc where smtc.systemMainTable = ? ";
		}
		Query query = super.createQuery(hql, new Object[]{smt});
		List list = query.list();
		return list;
	}

	public List findSystemMainTableOutputColumns(SystemMainTable smt) {
		String hql = "from SystemMainTableColumn smtc where smtc.systemMainTable = ? and smtc.isOutputItem = 1";
		Query query = super.createQuery(hql, new Object[]{smt});
		List list = query.list();
		return list;
	}

	public void removeSystemMainTableColumn(String[] smtcIds) {
		if(smtcIds==null|| smtcIds.length==0){
			throw new ServiceException("请选择要删除的字段");
		}
		for(int i=0; i<smtcIds.length; i++){
			String smtcId = smtcIds[i];
			this.removeSystemMainTableColumn(smtcId);
		}
		
	}

	public void removeSystemMainTableColumn(String smtcId) {
		SystemMainTableColumn smtc = (SystemMainTableColumn) super.get(SystemMainTableColumn.class, Long.valueOf(smtcId));
		//if(smtc!=null) return;
		if(smtc!=null&&smtc.getIsExtColumn()==SystemMainTableColumn.isMain){
			SystemMainTable sysMainTable = smtc.getSystemMainTable();
			SystemMainTableColumn pkColumn = sysMainTable.getPrimaryKeyColumn();
			if(smtc== pkColumn){
				sysMainTable.setPrimaryKeyColumn(null);
				super.save(sysMainTable);
			}
			Long foreignColumnCount = null; //关联引用字段的记录个数
			String fkcount = "select count(*) from SystemMainTableColumn smtc where smtc.foreignTableKeyColumn=?";
			Query query = super.createQuery(fkcount, smtc);
			foreignColumnCount = (Long) query.uniqueResult();
			if(foreignColumnCount!=null&& foreignColumnCount.intValue()>0){
				throw new ServiceException("存在外键（关联外键）引用当前字段，不可以删除");
			}
			
			String fvcount = "select count(*) from SystemMainTableColumn smtc where smtc.foreignTableValueColumn=?";
			query = super.createQuery(fvcount, smtc);
			foreignColumnCount = (Long) query.uniqueResult();
			if(foreignColumnCount!=null&& foreignColumnCount.intValue()>0){
				throw new ServiceException("存在外键（关联显示字段）引用当前字段，不可以删除");
			}
			
			String fpcount = "select count(*) from SystemMainTableColumn smtc where smtc.foreignTableParentColumn=?";
			query = super.createQuery(fpcount, smtc);
			foreignColumnCount = (Long) query.uniqueResult();
			if(foreignColumnCount!=null&& foreignColumnCount.intValue()>0){
				throw new ServiceException("存在外键（关联父字段）引用当前字段，不可以删除");
			}
			
			String ffcount = "select count(*) from SystemMainTableColumn smtc where smtc.fileNameColumn=?";
			query = super.createQuery(ffcount, smtc);
			foreignColumnCount = (Long) query.uniqueResult();
			if(foreignColumnCount!=null&& foreignColumnCount.intValue()>0){
				throw new ServiceException("存在外键（附件字段）引用当前字段，不可以删除");
			}
			
			String fsfcount = "select count(*) from SystemMainTableColumn smtc where smtc.systemFileNameColumn=?";
			query = super.createQuery(fsfcount, smtc);
			foreignColumnCount = (Long) query.uniqueResult();
			if(foreignColumnCount!=null&& foreignColumnCount.intValue()>0){
				throw new ServiceException("存在外键（系统附件字段）引用当前字段，不可以删除");
			}
			
			super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableKeyColumn=null" +
					" where foreignTableKeyColumn=?", smtc);
			super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableValueColumn=null" +
					" where foreignTableValueColumn=?", smtc);
			super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableParentColumn=null" +
					" where foreignTableParentColumn=?", smtc);
			super.executeUpdate("update SystemMainTableColumn smtc set smtc.fileNameColumn=null" +
					" where fileNameColumn=?", smtc);
			super.executeUpdate("update SystemMainTableColumn smtc set smtc.systemFileNameColumn=null" +
					" where systemFileNameColumn=?", smtc);
			
			super.executeUpdate("delete SystemTableSetting stqc where stqc.mainTableColumn=?", new Object[]{smtc});
			super.executeUpdate("delete UserTableSetting uts where uts.mainTableColumn=?", new Object[]{smtc});
			super.executeUpdate("delete SystemTableQueryColumn stqc where stqc.mainTableColumn=?", new Object[]{smtc});
			super.executeUpdate("delete UserTableQueryColumn stqc where stqc.mainTableColumn=?", new Object[]{smtc});
			super.executeUpdate("delete SystemTableRoleColumn strc where strc.mainTableColumn=?", new Object[]{smtc});
			
			try {
				super.executeUpdate("delete PagePanelColumn ppc where ppc.mainTableColumn=?",new Object[] { smtc });
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}else if(smtc!=null&&smtc.getIsExtColumn()==SystemMainTableColumn.isExt){
			String selData="select count(*) from ExtData extData where extData.extendTableId=?";
			Query query = super.createQuery(selData, Integer.parseInt(smtc.getId().toString()));
			Long dataCount = (Long) query.uniqueResult();
			if(dataCount!=null&& dataCount.intValue()>0){
				throw new ServiceException("该字段存在数据，不可以删除");
			}
			super.executeUpdate("delete SystemTableSetting stqc where stqc.mainTableColumn=?", new Object[]{smtc});
			super.executeUpdate("delete UserTableSetting uts where uts.mainTableColumn=?", new Object[]{smtc});
			super.executeUpdate("delete SystemTableQueryColumn stqc where stqc.mainTableColumn=?", new Object[]{smtc});
			super.executeUpdate("delete UserTableQueryColumn stqc where stqc.mainTableColumn=?", new Object[]{smtc});
			super.executeUpdate("delete SystemTableRoleColumn strc where strc.mainTableColumn=?", new Object[]{smtc});
			try {
				super.executeUpdate("delete PagePanelColumn ppc where ppc.mainTableColumn=?",new Object[] { smtc });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		else{ 
//			SystemMainTableExtColumn smtec = (SystemMainTableExtColumn) super.get(SystemMainTableExtColumn.class, Long.valueOf(smtcId));
//			try {
//				super.executeUpdate("delete SystemTableSetting stqc where stqc.extendTableColumn=?", new Object[]{smtec});
//				super.executeUpdate("delete UserTableSetting uts where uts.extendTableColumn=?", new Object[]{smtec});
//				super.executeUpdate("delete SystemTableQueryColumn stqc where stqc.extendTableColumn=?", new Object[]{smtec});
//				super.executeUpdate("delete UserTableQueryColumn stqc where stqc.extendTableColumn=?", new Object[]{smtc});
//				super.executeUpdate("delete SystemTableRoleColumn strc where strc.extendTableColumn=?", new Object[]{smtc});
//			}catch (Exception e) {
//				e.printStackTrace();
//			}	
//			
//		}
		super.remove(smtc);
	}

	private int getPanelColumnMaxOrder(PagePanel pagePanel) {
		Criteria dcMaxOrder = this.getCriteria(PagePanelColumn.class);
		//dcMaxOrder.add(Restrictions.eq("settingType", settingType));
		dcMaxOrder.add(Restrictions.eq("pagePanel", pagePanel));
		dcMaxOrder.setProjection(Projections.projectionList()
				.add(Projections.max("order").as("maxOrder"))
		);
		Object maxOrder = dcMaxOrder.uniqueResult();
		int orderMax = 0; 
		if(maxOrder!=null){
			orderMax = ((Integer)maxOrder).intValue();
		}
		return orderMax;
	}
	
	@SuppressWarnings("unchecked")
	public SystemMainTableColumn saveSystemMainTableColumn(
			SystemMainTableColumn smtc) {
		
		boolean isAdd = smtc.getId()==null;
		
		SystemMainTableColumn result = (SystemMainTableColumn) super.save(smtc);
		Criteria cmc = super.getCriteria(SystemMainTableColumn.class);
		cmc.add(Restrictions.eq("id", smtc.getId()));
		cmc.setFetchMode("propertyType", FetchMode.JOIN);
		cmc.setFetchMode("systemMainTableColumnType", FetchMode.JOIN);
		cmc.setFetchMode("validateType", FetchMode.JOIN);
		smtc = (SystemMainTableColumn) cmc.uniqueResult();
		
		//begin_初始化附件字段
		SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
		smtcType = super.get(SystemMainTableColumnType.class, Long.valueOf(smtcType.getId()));
		
		if(smtcType.getColumnTypeName().equalsIgnoreCase("multiFile")){
			smtc.setUploadUrl("/infoAdmin/upload");
			smtc.setFileNamePrefix("file_");
			
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
		//begin add by peixf
		if(isAdd){
			SystemMainTable smt = smtc.getSystemMainTable();
//			保存新增字段到系统可见字段和系统查询字段
			int sysTableSettingMaxOrderList = getSystemTableSettingMaxOrder(smt, SystemTableSetting.LIST);
			SystemTableSetting stsList = new SystemTableSetting();
			stsList.setSettingType(SystemTableSetting.LIST);
			stsList.setSystemMainTable(smt);
			stsList.setMainTableColumn(smtc);
			stsList.setIsDisplay(new Integer(1));
			stsList.setOrder(new Integer(sysTableSettingMaxOrderList+1));
			this.save(stsList);
			this.evict(stsList);
			
			int sysTableSettingMaxOrderInput = getSystemTableSettingMaxOrder(smt, SystemTableSetting.INPUT);
			SystemTableSetting stsInput = new SystemTableSetting();
			stsInput.setSettingType(SystemTableSetting.INPUT);
			stsInput.setSystemMainTable(smt);
			stsInput.setMainTableColumn(smtc);
			stsInput.setIsDisplay(new Integer(1));
			stsInput.setOrder(new Integer(sysTableSettingMaxOrderInput+1));
			this.save(stsInput);
			this.evict(stsInput);
			
			if(smtc.getIsOutputItem()!=null&& smtc.getIsOutputItem().intValue()==1){
				int sysTableSettingMaxOrderExport = getSystemTableSettingMaxOrder(smt, SystemTableSetting.EXPORT);
				SystemTableSetting stsExport = new SystemTableSetting();
				stsExport.setSettingType(SystemTableSetting.EXPORT);
				stsExport.setSystemMainTable(smt);
				stsExport.setMainTableColumn(smtc);
				stsExport.setIsDisplay(new Integer(1));
				stsExport.setOrder(new Integer(sysTableSettingMaxOrderExport+1));
				this.save(stsExport);
				this.evict(stsExport);
			}
			
			
			//增加新字段到页面面板
			Criteria c = super.getCriteria(PagePanel.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.setProjection(Projections.rowCount());
			Integer pcc = (Integer) c.uniqueResult();
			if(pcc!=null&& pcc.intValue()>0){
				c.setProjection(null);
				c.setResultTransformer(Criteria.ROOT_ENTITY);
				List<PagePanel> panels = c.list();
				for(PagePanel pagePanel : panels){
					PagePanelColumn ppc = new PagePanelColumn();
					ppc.setPagePanel(pagePanel);
					ppc.setSystemMainTable(smt);
					ppc.setMainTableColumn((SystemMainTableColumn) smtc);
					ppc.setIsDisplay(1);
					int maxOrder = this.getPanelColumnMaxOrder(pagePanel);
					ppc.setOrder(maxOrder+1);
					ppc.setIsMustInput(smtc.getIsMustInput());
					super.save(ppc);
				}
			}
		}
		
		//end 

		//新增或修改的字段被新设置为查询项
		if(smtc.getIsSearchItem()!=null&& smtc.getIsSearchItem().intValue()==1){
			SystemMainTable sysMainTable = smtc.getSystemMainTable();
			Criteria dc = super.getCriteria(SystemTableQuery.class);
			dc.add(Restrictions.eq("systemMainTable", sysMainTable));
			List list = dc.list();
			
			for(int i=0; i<list.size(); i++){
				SystemTableQuery stq = (SystemTableQuery) list.get(i);
				//看之前该字段是否已经被设置为查询项
				Criteria dce = super.getCriteria(SystemTableQueryColumn.class);
				dce.add(Restrictions.eq("systemTableQuery", stq));
				dce.add(Restrictions.eq("systemMainTable", sysMainTable));
				dce.add(Restrictions.eq("mainTableColumn", smtc));
				SystemTableQueryColumn res = (SystemTableQueryColumn) dce.uniqueResult();
				int stquMO=this.getSystemTableQueryMaxOrder(sysMainTable);
				if(res==null){ //是否已经存在记录
					SystemTableQueryColumn stqc = new SystemTableQueryColumn();
					stqc.setSystemTableQuery(stq);
					stqc.setSystemMainTable(sysMainTable);
					//stqc.setExtendTableColumn(null);
					stqc.setMainTableColumn(smtc);
					stqc.setOrder(stquMO+1);
					//页面传递过来的extColumn的SystemExtTable为null，手动设置一下
					SystemMainTableColumnType type = (SystemMainTableColumnType)
							super.get(SystemMainTableColumnType.class, smtc.getTypeId()); 
					smtc.setSystemMainTableColumnType(type);
					if(smtc.isTextColumnType()){ //如果字段类型是文本类型，模糊搜索模式
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_ANYWHERE);
					}else if(smtc.isNumberColumnType()){ //如果字段类型是下拉列表之类的存数字的类型
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
					}else if(smtc.isCurrencyColumn()){ //如果字段的验证类型是货币，搜索时应指定区间
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
					}else if(smtc.isDateColumn()){ //如果字段的验证类型是时间，搜索时应指定区间
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_BETWEEN);
					}else{ //在if中后加入else，并新增else分支，未测试
						stqc.setMatchMode(SystemTableQueryColumn.MATCH_MODE_EXACT);
					}
					//初始SystemTableQueryColumn数据
					super.save(stqc);
					//初始UserTableQueryColumn数据，需要使用上面的SystemTableQuery和SystemTableQueryColumn
					//遍历系统的所有人员
//					List users = super.getObjects(UserInfo.class);
//					Iterator iterUsr = users.iterator();
//					while(iterUsr.hasNext()){
//						UserInfo userInfo = (UserInfo) iterUsr.next();
//						Criteria dcUtqc = super.getCriteria(UserTableQueryColumn.class);
//						dcUtqc.add(Restrictions.eq("systemTableQuery", stq));
//						dcUtqc.add(Restrictions.eq("systemTableQueryColumn", stqc)); //刚保存的SystemTableQueryColumn
//						dcUtqc.add(Restrictions.eq("userInfo", userInfo));
//						Object userTableQueryColumn = dc.uniqueResult();
//						if(userTableQueryColumn==null){
//							UserTableQueryColumn utqc = new UserTableQueryColumn();
//							utqc.setUserInfo(userInfo);
//							utqc.setSystemTableQuery(stq);
//							utqc.setSystemTableQueryColumn(stqc);
//							utqc.setIsDisplay(Integer.valueOf(1));
//							utqc.setOrder(stquMO+1);
//							super.save(utqc); //插入UserTableQueryColumn
//						}
//					}
				}
				
				
			}
		}
		//否则如果是修改时，设置查询项为非查询项，则应该去除系统查询和用户查询字段表中的记录
		else if(smtc.getId()!=null
				&&smtc.getIsSearchItem()!=null&& smtc.getIsSearchItem().intValue()==0 ){
			//使用批量删除功能提高效率
			//getDaoSupport().executeUpdate("delete from UserTableSetting uts where uts.mainTableColumn=?", new Object[]{smtc});
			Criteria dc = super.getCriteria(SystemTableQueryColumn.class);
			dc.add(Restrictions.eq("mainTableColumn", smtc));
			List<SystemTableQueryColumn> stqcList = dc.list();
			for(int i=0; i<stqcList.size(); i++){
				SystemTableQueryColumn stqc = stqcList.get(i);
				super.executeUpdate("delete from UserTableQueryColumn utqc " +
											  "where utqc.systemTableQueryColumn=?", new Object[]{stqc});
				super.remove(stqc);
				super.flush();
			}
			//
			
		}
		return result;
		
	}
}
