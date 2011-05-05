package com.zsgj.info.appframework.metadata.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.tuple.StandardProperty;
import org.hibernate.type.Type;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.util.PropertiesUtil;

public class SystemMainTableServiceImpl extends BaseDao implements SystemMainTableService {

	public SystemMainTable findSystemMainTable(String smtId) {
		String hql = "from SystemMainTable smtc where smtc.id = ?";
		Query query = super.createQuery(hql, new Object[]{Long.valueOf(smtId)});
		SystemMainTable smtc = (SystemMainTable) query.uniqueResult();
		return smtc;
		

	}

	public SystemMainTable findSystemMainTableWithColumn(String smtId) {
		Criteria c = getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("id", Long.valueOf(smtId)));
		c.setFetchMode("columns", FetchMode.JOIN);
		c.setFetchMode("extendColumns", FetchMode.JOIN);
		c.setFetchMode("userTableSettings", FetchMode.JOIN);
		SystemMainTable smt = (SystemMainTable) c.uniqueResult();
		return smt;
	}

	public SystemMainTable findSystemMainTableByClazz(Class clazz) {
		String className = clazz.getName();
		SystemMainTable smtc = null;
		try {
			String hql = "from SystemMainTable smtc where smtc.className = ?";
			Query query = super.createQuery(hql, new Object[] { className });
			smtc = (SystemMainTable) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smtc;
		
	}

	@SuppressWarnings("unchecked")
	public List<SystemMainTable> findSystemMainTableByModule(Module module) {
		String hql = "from SystemMainTable smtc where smtc.module = ?";
		Query query = super.createQuery(hql, new Object[]{module});
		List list = query.list();
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public Page findSystemMainTableByModule(Module module, String tableName, int pageNo, int pageSize) {
		Page page = null;
		//String hql ="";
		Criteria critera = super.createCriteria(SystemMainTable.class, "tableName", true);
		if(module!=null){
			critera.add(Restrictions.eq("module", module));
			//hql = "select smtc from SystemMainTable smtc where smtc.module = ? order by smtc.id desc ";
			//page = super.pagedQuery(hql, pageNo, pageSize, new Object[]{module});
		}else if(StringUtils.isNotBlank(tableName)){
			critera.add(Restrictions.disjunction()
					.add(Restrictions.ilike("tableName", tableName, MatchMode.ANYWHERE)) //忽略大小写
					.add(Restrictions.ilike("tableCnName", tableName, MatchMode.ANYWHERE)));
			//hql = "select smtc from SystemMainTable smtc order by smtc.id desc ";
			//page = super.pagedQuery(hql, pageNo, pageSize);
		}
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


	public void removeSystemTableSetting(String stsId) {
		SystemTableSetting sts = super.get(SystemTableSetting.class, Long.valueOf(stsId));
		Column column = sts.getColumn();
//		if(column instanceof SystemMainTableColumn){
			SystemMainTableColumn mc = (SystemMainTableColumn) column;
			super.executeUpdate("delete UserTableSetting uts where uts.mainTableColumn=?", mc);
			try {
				super.executeUpdate("delete PagePanelColumn ppc where ppc.mainTableColumn=?",new Object[] { mc });
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}else{
//			SystemMainTableExtColumn ec = (SystemMainTableExtColumn) column;
//			super.executeUpdate("delete UserTableSetting uts where uts.extendTableColumn=?", ec);
//			try {
//				super.executeUpdate("delete PagePanelColumn ppc where ppc.extendTableColumn=?",new Object[] { ec });
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		
		super.removeById(SystemTableSetting.class, Long.valueOf(stsId));
		
	}

	@SuppressWarnings("unchecked")
	public void removeSystemMainTable(String smtId) {
		SystemMainTable smt = super.get(SystemMainTable.class, Long.valueOf(smtId));
		if(smt!=null){
			//先删除所有关联
			super.executeUpdate(
					"delete from UserTableSetting uts where uts.systemMainTable=?", 
					new Object[]{smt});
			
			super.executeUpdate(
					"delete from SystemTableSetting sts where sts.systemMainTable=?", 
					new Object[]{smt});
			
			super.executeUpdate(
					"delete from UserTableQueryColumn uts where uts.systemMainTable=?", 
					new Object[]{smt});
			
			super.executeUpdate(
					"delete from SystemTableQuery stq where stq.systemMainTable=?", 
					new Object[]{smt});
			super.executeUpdate(
					"delete from SystemTableQueryColumn stqc where stqc.systemMainTable=?", 
					new Object[]{smt});
			
			super.executeUpdate(
					"delete from SystemMainTableColumn smtc where smtc.systemMainTable=?", 
					new Object[]{smt});
//			super.executeUpdate(
//					"delete from SystemMainTableExtColumn etc where etc.systemMainTable=?", 
//					new Object[]{smt});
			
			try {
				super.executeUpdate(
						"update SystemMainTableColumn smt set smt.foreignTableKeyColumn=null where smt.foreignTable=?",
						new Object[] { smt });
				super.executeUpdate(
						"update SystemMainTableColumn smt set smt.foreignTableValueColumn=null where smt.foreignTable=?",
						new Object[] { smt });
				//需先删除外键关联字段和显示字段才能删除外键关联表
				super.executeUpdate(
						"update SystemMainTableColumn smt set smt.foreignTable=null where smt.foreignTable=?", 
						new Object[]{smt});
				super.executeUpdate(
						"update SystemMainTableColumn smt2 set smt2.referencedTable=null where smt2.referencedTable=?", 
						new Object[]{smt});
//				super.executeUpdate(
//						"update SystemMainTableExtColumn etc2 set etc2.foreignTable=null where etc2.foreignTable=?", 
//						new Object[]{smt});
				
				//二期框架级联删除页面模型引用
				super.executeUpdate(
						"update PageModel smt set smt.systemMainTable=null where smt.systemMainTable=?",
						new Object[] { smt });
				//二期框架级联删除面板引用
				super.executeUpdate(
						"update PagePanel smt set smt.systemMainTable=null where smt.systemMainTable=?",
						new Object[] { smt });
				//二期框架级联删除PageGroupPanelTable
				super.executeUpdate(
						"delete from PageGroupPanelTable etc where etc.subPanelTable=? or etc.parentPanelTable=?", 
						new Object[]{smt, smt});
				
				//二期框架级联删除PageModelPanelTable
				super.executeUpdate(
						"delete from PageModelPanelTable etc where etc.subPanelTable=? or etc.parentPanelTable=?", 
						new Object[]{smt, smt});
				
				//二期框架级联删除PagePanelTableRelation
				super.executeUpdate(
						"delete from PagePanelTableRelation etc where etc.systemMainTable=? or etc.foreignTable=?", 
						new Object[]{smt, smt});
				
				//二期框架级联删除PagePanelTable数据
				List<Long> panels = super.find("select ppt.pagePanel.id from PagePanelTable ppt where ppt.systemMainTable=?", 
						new Object[]{smt});
				//级联删除面板字段
				for(Long panelId : panels){
					super.executeUpdate("delete from PagePanelColumn etc where etc.pagePanel.id=?", new Object[]{panelId});
				}
				//级联删除面板主表
				super.executeUpdate("delete from PagePanelTable ppt where ppt.systemMainTable=?", new Object[]{smt});
				
			} catch (Exception e) { 
				System.out.println("删除主表，级联删除字段或面板时发生异常, from SystemMainTableServiceImpl");
				e.printStackTrace();
			}		
			
			//最后删除主表
			super.executeUpdate(
					"delete from SystemMainTable sts where sts.id=?", 
					new Object[]{Long.valueOf(smtId)});
			//super.remove(smt);

			
		}

	}

	//有待做级联测试
	public SystemMainTable saveSystemMainTable(SystemMainTable smt) {
		//先初始化所有关联属性，防止级联保存出问题
		/*Criteria c = getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("id", smt.getId()));
		c.setFetchMode("columns", FetchMode.JOIN);
		c.setFetchMode("extendColumns", FetchMode.JOIN);
		c.setFetchMode("userTableSettings", FetchMode.JOIN);
		SystemMainTable result =  (SystemMainTable) c.uniqueResult();
		smt.setColumns(result.getColumns());
		smt.setExtendColumns(result.getExtendColumns());
		smt.setUserTableSettings(result.getUserTableSettings());*/
		SystemMainTable saveResult = (SystemMainTable) super.save(smt);
		return saveResult;
	}

	private StandardProperty[] getEntityProperties(){
		StandardProperty[] sps = null;
		
		return sps;
	}
	
	private SystemMainTableColumn findColumn(SystemMainTable smt, String columnName){
		Criteria c = this.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.eq("columnName", columnName));
		SystemMainTableColumn smtc = (SystemMainTableColumn) c.uniqueResult();
		return smtc;
	}
	
	private String getPackagename(String classname){
		String pkname = "";
		int lastIndex = classname.lastIndexOf(".");
		if(lastIndex!=-1){
			pkname = classname.substring(0, lastIndex);
		}
		return pkname;
	}
	
	private String getClassname(String classname){
		String pkname = "";
		int lastIndex = classname.lastIndexOf(".");
		if(lastIndex!=-1){
			pkname = classname.substring(lastIndex+1);
		}
		return pkname;
	}
	
	public void saveSystemMainTableFromMapping() {
		Map map = this.getHibernateTemplate().getSessionFactory().getAllClassMetadata();
		Set keySet = map.keySet();
		Iterator iter = keySet.iterator();
		while(iter.hasNext()){  
			String classname = (String) iter.next();
			String packageName = this.getPackagename(classname);
			String projectName = PropertiesUtil.getProperties("webAppPkgName");
			if(packageName.startsWith("com.zsgj."+projectName)){
				String clazzName = this.getClassname(classname);
//				System.out.println("classname: "+classname);
//				System.out.println("packageName: "+packageName);
//				System.out.println("clazzName: "+clazzName);
				ClassMetadata classMetadata = (ClassMetadata) map.get(classname);
				Class entityClass = classMetadata.getClass();
				org.hibernate.persister.entity.SingleTableEntityPersister step=null;
				step = (SingleTableEntityPersister) classMetadata;
				String tableName = step.getTableName();
				//Table table = cfg.getClassMapping(entityClass).getTable();
				
				
				String entityName = classMetadata.getEntityName();
				String idname = classMetadata.getIdentifierPropertyName();
				
				
				Type idType = classMetadata.getIdentifierType();
				Criteria c = this.getCriteria(SystemMainTable.class);
				c.add(Restrictions.eq("className", classname));
				SystemMainTable smt = (SystemMainTable) c.uniqueResult();
				//SystemMainTableColumn idcolumn = this.findColumn(smt, idname);
				if(smt==null){
					smt = new SystemMainTable();
					smt.setClassName(classname);
					//smtn.setPrimaryKeyColumn(idcolumn);
					smt.setTableName(tableName);
					smt.setTableCnName(clazzName);
					this.save(smt);
					
					SystemMainTableColumn smtc = new SystemMainTableColumn();
					
					PropertyType pt = this.getPropertyTypeByHbnType("org.hibernate.type.LongType");
					smtc.setPropertyType(pt);
					
//					SystemMainTableColumnType smtct = new SystemMainTableColumnType();
//					smtct.setId(8L);
//					smtc.setSystemMainTableColumnType(smtct);
//					
//					smtc.setColumnCnName("自动编号");
//					smtc.setColumnName("id");
//					smtc.setPropertyName("id");
//					smtc.setDescn("主键，不可修改和删除");
//					smtc.setOrder(1);
//					smtc.setSystemMainTable(smt);
//					smtc.setIsHiddenItem(1);
//					smtc.setIsSearchItem(0);
//					smtc.setIsUpdateItem(0);
//					smtc.setIsMustInput(1);
//					super.save(smtc);
//					smt.setPrimaryKeyColumn(smtc);
//					this.save(smt);
					
				}
			}
			
			
		}


	}
	
	private PropertyType getPropertyTypeByHbnType(String ptypeclass){
		Criteria c = this.getCriteria(PropertyType.class);
		c.add(Restrictions.like("hibernateTypeClass", ptypeclass, MatchMode.EXACT).ignoreCase());
		PropertyType ptobj = (PropertyType) c.uniqueResult();
		return ptobj;
	}

	public SystemMainTableColumn findSystemMainTableColumnByColumnId(String mainTableColumnId) {
		SystemMainTableColumn smtc = super.get(SystemMainTableColumn.class, Long.valueOf(mainTableColumnId));
		return smtc;
	}

//	public SystemMainTableExtColumn findSystemMainTableExtColumnByColumnId(String extColumnId) {
//		SystemMainTableExtColumn smtec = super.get(SystemMainTableExtColumn.class, Long.valueOf(extColumnId));
//		return smtec;
//	}

}
