//package com.digitalchina.info.appframework.pagemodel.service.impl;
//
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.sql.Types;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.commons.lang.StringUtils;
//import org.hibernate.Criteria;
//import org.hibernate.FetchMode;
//import org.hibernate.Query;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.criterion.MatchMode;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Projections;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.dialect.Dialect;
//import org.hibernate.impl.SessionFactoryImpl;
//import org.hibernate.mapping.PersistentClass;
//import org.hibernate.mapping.Property;
//import org.hibernate.mapping.SimpleValue;
//
//import com.digitalchina.info.appframework.metadata.entity.Column;
//import com.digitalchina.info.appframework.metadata.entity.PropertyType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
//import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;
//import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
//import com.digitalchina.info.appframework.pagemodel.service.CustomerTableService;
//import com.digitalchina.info.framework.dao.BaseDao;
//import com.digitalchina.info.framework.dao.support.Page;
//import com.digitalchina.info.framework.exception.ServiceException;
//import com.digitalchina.info.framework.security.entity.Module;
//import com.digitalchina.info.framework.util.PathUtil;
//import com.digitalchina.info.framework.util.PropertiesUtil;
//import com.digitalchina.info.framework.util.asm.render.FreemarkerRender;
//import com.digitalchina.info.framework.util.asm.render.RenderClass;
//import com.digitalchina.info.framework.util.asm.render.RenderProperty;
//import com.digitalchina.info.framework.util.asm.render.Templates;
//import com.digitalchina.info.framework.util.code.RuntimeCode;
//import com.digitalchina.itil.config.entity.ConfigItem;
//import com.digitalchina.itil.config.entity.ConfigItemType;
//
//public class CustomerTableServiceImpl_copy extends BaseDao implements CustomerTableService {
//	private SystemColumnService systemColumnService;
//	
//	String FSP = System.getProperty("file.separator");
//	String LSP = "\n";
//	
//
//
//	public List<PagePanel> findAllBasePanels() {
//		Criteria c = super.getCriteria(PagePanel.class);
//		c.add(Restrictions.eq("groupFlag", Integer.valueOf(0)));
//		List list = c.list();
//		return list;
//	}
//
//	public List<PagePanel> findAllGroupPanels() {
//		Criteria c = super.getCriteria(PagePanel.class);
//		c.add(Restrictions.eq("groupFlag", Integer.valueOf(1)));
//		List list = c.list();
//		return list;
//	}
//
//	public List<PagePanel> findAllPagePanels() {
//		Criteria c = super.getCriteria(PagePanel.class);
//		
//		List list = c.list();
//		return list;
//	}
//
//	public ConfigItemType findConfigItemTypeByTable(SystemMainTable smt) {
//		ConfigItemType result = null;
//		Criteria c =super.getCriteria(ConfigItemType.class);
//		c.add(Restrictions.eq("systemMainTable", smt));
//		List list = c.list();
//		if(!list.isEmpty()){
//			result = (ConfigItemType) list.iterator().next();
//		}
//		return result;
//	}
//
//	public SystemMainTable findCustomerTableById(String tableId) {
//		Criteria c = super.getCriteria(SystemMainTable.class);
//		c.add(Restrictions.eq("id", Long.valueOf(tableId)));
//		SystemMainTable smt = (SystemMainTable) c.uniqueResult();
//		return smt;
//	}
//
//	public List<ConfigItemType> findAllTopConfigItems() {
//		Criteria c = super.getCriteria(ConfigItemType.class);
//		c.add(Restrictions.isNull("parentConfigItemType"));
//		c.add(Restrictions.ne("deployFlag", Integer.valueOf(0)));
//		c.addOrder(Order.asc("name"));
//		List<ConfigItemType> list = c.list();
//		return list;
//	}
//
//	private List<Property> getPropertyByPersistentClass(PersistentClass model){
//		List<Property> list = new ArrayList<Property>();
//		if(model!=null){
//			Iterator<Property> iter = model.getPropertyIterator();
//			while(iter.hasNext()){
//				Property item =  iter.next();
//				list.add(item);
//			}
//		}
//		return list;
//	}
//	
//	//是否需要增加新的属性进入持久化类
//	private boolean needAddProperty2PersistClass(PersistentClass model, Column column){
//		boolean result = true; //默认需要增加
//		Iterator<Property> iter = model.getPropertyIterator();
//		while(iter.hasNext()){
//			Property item =  iter.next();
//			String propertyName = item.getName();
//			Object value = item.getValue();
//			if(column.getPropertyName().equalsIgnoreCase("id")){
//				if(column.getPropertyName().equalsIgnoreCase(propertyName)){ //只有持久化类里有了，就不再加入
//					result = false;
//					return result;
//				}
//			}
//			
//		}
//		return result;
//	}
//	/**
//	 * 利用后台新增的主表生成实体和mapping更新sessionFactory
//	 * 主要需要进一步完事
//	 */
//	public void saveTableEntity(String sourcePkg, String sourceClassName, Class targetClass) {
//		SessionFactory ssf = super.getHibernateSessionFactory();
//		Configuration config = super.getConfiguration();
//		String newClassName = sourcePkg+"."+ sourceClassName;
//		//通过类全路径获取持久化类
//		PersistentClass model = config.getClassMapping(newClassName); 
//		if(model==null){
//			genEntityAndMapping(model, sourcePkg, sourceClassName, targetClass);
//		
//		}else{
//			saveExtProps(sourcePkg, sourceClassName, targetClass, model);
//		}
//		
//	}
//
//	private void saveExtProps(String sourcePkg, String sourceClassName, Class targetClass, PersistentClass model) {
//		//获取主表
//		Criteria c = super.getCriteria(SystemMainTable.class);
//		c.add(Restrictions.ilike("tableName", sourceClassName, MatchMode.EXACT));
//		List list = c.list();
//		SystemMainTable smt = null;
//		if(!list.isEmpty()){
//			smt = (SystemMainTable) list.iterator().next();
//		}
//		
//		RenderClass rc = new RenderClass();
//		ArrayList rcolumns = new ArrayList();
//		
//		
//		//获取主表的所有字段
//		Criteria cc = super.getCriteria(SystemMainTableColumn.class);
//		cc.add(Restrictions.eq("systemMainTable", smt));
//		cc.setFetchMode("propertyType", FetchMode.JOIN);
//		cc.addOrder(Order.asc("id"));
//		List<SystemMainTableColumn> columns = cc.list();
//		
//		for(SystemMainTableColumn smtc : columns){
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
//			this.genEntityAndMapping(model, sourcePkg, sourceClassName,targetClass);
//		} catch (Exception e) {
//			//e.printStackTrace();
//			System.out.println("发生重复添加mapping异常");
//			//throw new ServiceException("当前用户表出于发布状态，不可以新增字段");
//		}			
//		for(SystemMainTableColumn smtc : columns){
//			System.out.println("smtc name: "+ smtc.getPropertyName());
//			
//			boolean needAddFlag = this.needAddProperty2PersistClass(model, smtc);
//			if(needAddFlag){
//				//定义新的字段
//				org.hibernate.mapping.Column column = new org.hibernate.mapping.Column();
//				column.setName(smtc.getPropertyName());
//				if(smtc.getIsMustInput()!=null && smtc.getIsMustInput().intValue()==1){
//					column.setNullable(true);
//				}
//				if(smtc.getUniqueFlag()!=null && smtc.getUniqueFlag().intValue()==1){
//					column.setUnique(true);
//				}
//				model.getTable().addColumn(column);
//				//包装字段给一个VALUE
//				SimpleValue value = new SimpleValue();
//				value.setTable(model.getTable());
//				PropertyType ptt = smtc.getPropertyType();
//				String propertyTypeName = ptt.getPropertyTypeName();
//				if(!propertyTypeName.equalsIgnoreCase("BaseObject")){
//					value.setTypeName(propertyTypeName.toLowerCase());
//				}else{
//					SystemMainTable fsmt = smtc.getForeignTable();
//					String fclassName = fsmt.getClassName();
//					value.setTypeName(fclassName);
//				}
//				value.addColumn(column);
//				//定义一个新的属性
//				Property prop = new Property();
//				prop.setValue(value);
//				prop.setName(smtc.getPropertyName());
//				prop.setNodeName(prop.getName());
//				model.addProperty(prop);
//				
//			}//end need add
//			
//			//更新sessionFactory
//			System.out.println("smtc name: "+ smtc.getPropertyName());
//			
//			//super.getSessionFactory().refreshPersistentClass(model, super.getConfiguration().getMapping());
//		//	super.getConfiguration().buildSessionFactory();
//			//end
//			
////				给系统可见字段增加字段
//			Criteria cSysInput = super.getCriteria(SystemTableSetting.class);
//			cSysInput.add(Restrictions.eq("systemMainTable", smt));
//			cSysInput.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
//			cSysInput.add(Restrictions.eq("mainTableColumn", smtc));
//			
//			cSysInput.setProjection(Projections.rowCount());
//			Integer sSysInputCount = (Integer) cSysInput.uniqueResult();
//			if(sSysInputCount.intValue()==0){
//				cSysInput.setProjection(Projections.max("order"));
//				Integer sysInputMaxOrder = (Integer) cSysInput.uniqueResult();
//				if(sysInputMaxOrder==null) sysInputMaxOrder = 0;
////					初始化输入系统可见字段
//				SystemTableSetting stsInput = new SystemTableSetting();
//				stsInput.setSettingType(SystemTableSetting.INPUT);
//				stsInput.setSystemMainTable(smt);
//				if(smtc instanceof SystemMainTableColumn){
//					stsInput.setMainTableColumn((SystemMainTableColumn) smtc);
//				}
//				stsInput.setIsDisplay(new Integer(1));
//				stsInput.setOrder(sysInputMaxOrder+1);
//				this.save(stsInput);
//				this.evict(stsInput);
//				
//			}
//			
//			Criteria cSysList = super.getCriteria(SystemTableSetting.class);
//			cSysList.add(Restrictions.eq("systemMainTable", smt));
//			cSysList.add(Restrictions.eq("settingType", SystemTableSetting.LIST));
//			cSysList.add(Restrictions.eq("mainTableColumn", smtc));
//			cSysList.setProjection(Projections.rowCount());
//			
//			Integer cSysListCount = (Integer) cSysList.uniqueResult();
//			if(cSysListCount.intValue()==0){
//				cSysList.setProjection(Projections.max("order"));
//				Integer sysListMaxOrder = (Integer) cSysList.uniqueResult();
//				if(sysListMaxOrder==null) sysListMaxOrder = 0;
////					初始化输入系统可见字段
//				SystemTableSetting stsList = new SystemTableSetting();
//				stsList.setSettingType(SystemTableSetting.LIST);
//				stsList.setSystemMainTable(smt);
//				if(smtc instanceof SystemMainTableColumn){
//					stsList.setMainTableColumn((SystemMainTableColumn) smtc);
//				}
//				stsList.setIsDisplay(new Integer(1));
//				stsList.setOrder(sysListMaxOrder+1);
//				this.save(stsList);
//				this.evict(stsList);
//				
//			}
//								
//			//给当前表的面板增加字段，默认可见
//			Criteria cPanel = super.getCriteria(PagePanel.class);
//			cPanel.add(Restrictions.eq("systemMainTable", smt));
//			List<PagePanel> pagePanels = cPanel.list();
//			if(pagePanels.isEmpty()){
//				//如果没有面板，说明面板已经被删除，初始化
//				//genTablePanels(smt);
//				
//			}else{
//				for(PagePanel pItem : pagePanels){
//					Criteria cpc = super.getCriteria(PagePanelColumn.class);
//					cpc.add(Restrictions.eq("pagePanel", pItem));
//					cpc.add(Restrictions.eq("mainTableColumn", smtc));
//					cpc.setProjection(Projections.rowCount());
//					Integer cpcCount = (Integer) cpc.uniqueResult();
//					if(cpcCount.intValue()==0){
//						cpc.setProjection(Projections.max("order"));
//						Integer maxPanelOrder = (Integer) cpc.uniqueResult();
//						if(maxPanelOrder==null) maxPanelOrder = 0;
//						PagePanelColumn ppc = new PagePanelColumn();
//						ppc.setPagePanel(pItem);
//						ppc.setSystemMainTable(smt);
//						ppc.setMainTableColumn(smtc);
//						ppc.setOrder(maxPanelOrder+1);
//						ppc.setIsDisplay(1);
//						ppc.setIsMustInput(1);
//						ppc.setMatchMode(null);
//						super.save(ppc);
//						
//					}
//				}
//			}
//			super.reset();
//			
//		}//end
//	}
//
//	//在配置项类型的面板没有的情况下，直接保存会默认生成
//	private void genTablePanels(SystemMainTable smt) {
//		PagePanel panel = new PagePanel();
//		panel.setName("panel_"+smt.getTableCnName()+"Panel");
//		panel.setTitle(smt.getTableCnName());
//		panel.setDescn(smt.getTableCnName()+"面板，用户增加此类配置项时系统自动创建此面板");
//		panel.setGroupFlag(0);
//		PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
//		panel.setXtype(ppt);
//		panel.setSettingType(SystemTableSetting.INPUT); //form
//		panel.setSystemMainTable(smt);
//		Module module = super.findUniqueBy(Module.class, "name", "配置项管理");
//		panel.setModule(module);
//		super.save(panel);
//		
//		//生成面板主表
//		PagePanelTable pptble = new PagePanelTable();
//		pptble.setPagePanel(panel);
//		pptble.setSystemMainTable(smt);
//		super.save(pptble);
//	}
//
//	private void genEntityAndMapping(PersistentClass model, String sourcePkg, String sourceClassName, Class targetClass) {
//		SessionFactory ssf = super.getHibernateSessionFactory();
//		Configuration config = super.getConfiguration();
//		//PersistentClass model;
//		StringBuffer code = new StringBuffer();
//		code.append("package ").append(sourcePkg).append(";").append(LSP);
//		code.append(LSP);
//		
//		StringBuffer importer = new StringBuffer();
//		importer.append("import java.util.Date;").append(LSP);
//		importer.append("import com.digitalchina.info.framework.dao.BaseObject;").append(LSP);
//		importer.append("import com.digitalchina.info.framework.security.entity.UserInfo;").append(LSP);
//		
//		code.append(importer);
//
//		StringBuffer field = new StringBuffer();
//		StringBuffer setter = new StringBuffer();
//		StringBuffer getter = new StringBuffer();
//		
//		Criteria c = super.getCriteria(SystemMainTable.class);
//		c.add(Restrictions.ilike("tableName", sourceClassName, MatchMode.EXACT));
//		List list = c.list();
//		SystemMainTable smt = null;
//		String className = null;
////			持久类对象描述
//		RenderClass rc = new RenderClass();
//		ArrayList rcolumns = new ArrayList();
//		if(!list.isEmpty()){
//			smt = (SystemMainTable) list.iterator().next();
//			className = smt.getClassName();
//			code.append(LSP);
//			code.append("public class ").append(sourceClassName).append(" extends BaseObject {").append(LSP);
//			//code.append(LSP);			
//		
//			Criteria cc = super.getCriteria(SystemMainTableColumn.class);
//			cc.add(Restrictions.eq("systemMainTable", smt));
//			cc.addOrder(Order.asc("id"));
//			List<SystemMainTableColumn> listcc = cc.list();
//			
//			for(SystemMainTableColumn smtc : listcc){
//				//String columnName = smtc.getColumnName();
//				String propertyName = smtc.getPropertyName();
//				if(propertyName==null){
//					propertyName = smtc.getColumnName2();
//				}
//				String bPropertyName = propertyName.substring(0,1).toUpperCase()+ propertyName.substring(1);
//				
//				PropertyType pt = smtc.getPropertyType();
//				//String ptName = pt.getName();
//				String ptClassName = pt.getPropertyTypeClass();
//				//增加字段
//				if(!ptClassName.equals("com.digitalchina.info.framework.dao.BaseObject")){
//					RenderProperty property = new RenderProperty();
//					property.setName(propertyName);
//					property.setField(propertyName);
//					//property.setLength(smtc.getLength());
//					if(propertyName.equalsIgnoreCase("id")){
//						property.setPrimary(true);
//					}
//					property.setType(ptClassName);
//					rcolumns.add(property);
//					
//					field.append("   private ").append(ptClassName).append(" ").append(propertyName).append(";").append(LSP);
//					
//					setter.append("   public void set").append(bPropertyName).append("(").append(ptClassName).append(" ").append(propertyName).append("){").append(LSP);
//					setter.append("	     this.").append(propertyName).append("=").append(propertyName).append(";").append(LSP);
//					setter.append("   }").append(LSP);
//					
//					getter.append("   public ").append(ptClassName).append(" get").append(bPropertyName).append("(){").append(LSP);
//					getter.append("	     return this.").append(propertyName).append(";").append(LSP);
//					getter.append("   }").append(LSP);
//					
//					
//				}else{
//					SystemMainTable ftable = smtc.getForeignTable();
//					SystemMainTableColumn fkey = smtc.getForeignTableKeyColumn();
//					SystemMainTableColumn fvalue = smtc.getForeignTableValueColumn();
//					String fclassName = ftable.getClassName();
//					ptClassName = fclassName;
//					RenderProperty property = new RenderProperty();
//					property.setName(propertyName);
//					property.setField(propertyName);
//					//property.setLength(smtc.getLength());
//					if(propertyName.equalsIgnoreCase("id")){
//						property.setPrimary(true);
//					}
//					property.setType(ptClassName);
//					rcolumns.add(property);
//					
//					//对象属性的setter和getter
//					field.append("   private ").append(ptClassName).append(" ").append(propertyName).append(";").append(LSP);
//					
//					setter.append("   public void set").append(bPropertyName).append("(").append(ptClassName).append(" ").append(propertyName).append("){").append(LSP);
//					setter.append("	     this.").append(propertyName).append("=").append(propertyName).append(";").append(LSP);
//					setter.append("   }").append(LSP);
//					
//					getter.append("   public ").append(ptClassName).append(" get").append(bPropertyName).append("(){").append(LSP);
//					getter.append("	     return this.").append(propertyName).append(";").append(LSP);
//					getter.append("   }").append(LSP);
//				}
//		
//				
//			}
//			rc.setProperties(rcolumns);
//			
//			code.append(field).append(LSP).append(setter).append(LSP).append(getter);
//			code.append("} ");
//			
//			if(!listcc.isEmpty()){
//				String targetDIR = null;
//				try {
//					targetDIR = PathUtil.getPkgPathFromClass(targetClass);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}		
//				
//				rc.setClassName(sourcePkg+"."+ sourceClassName);
//				rc.setTableName(sourceClassName);
//				String tmp = System.getProperty("java.class.path");
//				String WEB_INF_CLASSES = this.getClass().getResource("/").toString().replace("/", "\\");
//				int fileLength = "file:\\".length();
//				WEB_INF_CLASSES = WEB_INF_CLASSES.substring(fileLength);
//				
//				RuntimeCode.genEntityAndClass(WEB_INF_CLASSES, sourcePkg, sourceClassName, code.toString(), ConfigItem.class);
//				//开始生成hbm.xml
//				String hbmDIR = targetDIR +"map" + FSP + sourceClassName + ".hbm.xml";
//				
//				FreemarkerRender render = new FreemarkerRender();
//				render.render(rc, Templates.TEMPLATE_HIBERNATE3, hbmDIR);
//				//URL url = this.getClass().getResource(hbmdir);
//				
//				int classesIndex = hbmDIR.indexOf("classes");
//				hbmDIR = hbmDIR.substring(classesIndex+7).replace("\\", "/");
//				URL  url = this.getClass().getResource(hbmDIR);   
//		        config.addURL(url);  
//		        model = config.getClassMapping(className);
//				try {
//					ssf.addPersistentClass(model, config.getMapping());
//				} catch (Exception e) {
//					System.out.println("重复增加持久化类发生异常");
//				}		        
//				if(smt!=null){
//		        	smt.setDeployFlag(1);
//		        	super.save(smt);
//		        	
//		        	this.saveConfigItemTablePanel(smt);
//		        	
//		        	//FreemarkerRender render22 = new FreemarkerRender();
//		        	//render22.render(rc, Templates.TEMPLATE_HIBERNATE3, hbmDIR);
//					//URL url = this.getClass().getResource(hbmdir);
//					
//		        }
//			}
//			
//			
//		}
//	}
//	
//	//保存一个用户主表后自动生成对应的面板
//	private void saveConfigItemTablePanel(SystemMainTable smt){
//		String className = smt.getClassName();
//		String tableName = smt.getTableName();
//		String tableCnName = smt.getTableCnName();
//		String ppcount = "select count(*) from PagePanel ppc where ppc.systemMainTable=?";
//		Query ppQuery = super.createQuery(ppcount, smt);
//		Long ppcRows = (Long) ppQuery.uniqueResult();
//		
//		PagePanel panel = null;
//		
//		if(ppcRows.intValue()==0){
//			panel = new PagePanel();
//			panel.setName("panel_"+tableName+"Panel");
//			panel.setTitle(tableCnName);
//			panel.setDescn(tableCnName+"面板，用户增加此类配置项时系统自动创建此面板");
//			panel.setGroupFlag(0);
//			PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
//			panel.setXtype(ppt);
//			panel.setSettingType(SystemTableSetting.INPUT); //form
//			panel.setSystemMainTable(smt);
//			Module module = super.findUniqueBy(Module.class, "name", "配置项管理");
//			panel.setModule(module);
//			super.save(panel);
//			
//			//生成面板主表
//			PagePanelTable pptble = new PagePanelTable();
//			pptble.setPagePanel(panel);
//			pptble.setSystemMainTable(smt);
//			super.save(pptble);
//			
////			取系统主表的所有字段，初始化系统可见字段
//			int order = 1;
//			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
//			for(Column column : columns){
//				//初始化输入系统可见字段
//				SystemTableSetting stsInput = new SystemTableSetting();
//				stsInput.setSettingType(SystemTableSetting.INPUT);
//				stsInput.setSystemMainTable(smt);
//				if(column instanceof SystemMainTableColumn){
//					stsInput.setMainTableColumn((SystemMainTableColumn) column);
//				}else if(column instanceof SystemMainTableExtColumn) {
//					stsInput.setExtendTableColumn((SystemMainTableExtColumn) column);
//				}
//				stsInput.setIsDisplay(new Integer(1));
//				stsInput.setOrder(order++);
//				this.save(stsInput);
//				this.evict(stsInput);
//				//初始化列表页面的系统可见字段
//				SystemTableSetting stsList = new SystemTableSetting();
//				stsList.setSettingType(SystemTableSetting.LIST);
//				stsList.setSystemMainTable(smt);
//				if(column instanceof SystemMainTableColumn){
//					stsList.setMainTableColumn((SystemMainTableColumn) column);
//				}else if(column instanceof SystemMainTableExtColumn) {
//					stsList.setExtendTableColumn((SystemMainTableExtColumn) column);
//				}
//				stsList.setIsDisplay(new Integer(1));
//				stsList.setOrder(order++);
//				this.save(stsList);
//				this.evict(stsList);
//			}
//			
//			String ppccount = "select count(*) from PagePanelColumn ppc where ppc.pagePanel=?";
//			Query query = super.createQuery(ppccount, panel);
//			Long cunt = (Long) query.uniqueResult();
//			if(cunt.intValue()==0){
////				取所有的系统可见字段
//				Criteria c = super.getCriteria(SystemTableSetting.class);
//				c.add(Restrictions.eq("systemMainTable", smt));
//				c.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
//				c.addOrder(Order.asc("order"));
//				List list = c.list();
//				Iterator iter = list.iterator();
//				while(iter.hasNext()){
//					SystemTableSetting uts = (SystemTableSetting) iter.next();
//					Column column = uts.getColumn();
//					//遍历系统可见字段，生成面板字段
//					PagePanelColumn ppc = new PagePanelColumn();
//					ppc.setPagePanel(panel);
//					ppc.setSystemMainTable(smt);
//					if(column instanceof SystemMainTableColumn){
//						ppc.setMainTableColumn((SystemMainTableColumn) column);
//					}else if(column instanceof SystemMainTableExtColumn) {
//						ppc.setExtendTableColumn((SystemMainTableExtColumn) column);
//					}
//					ppc.setIsDisplay(1);
//					ppc.setOrder(uts.getOrder());
//					ppc.setIsMustInput(column.getIsMustInput());
//					super.save(ppc);
//				}
//			}
//			
//			
//			//增加配置项类型
//			ConfigItemType cit = this.findConfigItemTypeByTable(smt);
//			cit.setName(smt.getTableCnName());
//			cit.setClassName(smt.getClassName());
//			cit.setTableName(smt.getTableName());
//			cit.setSystemMainTable(smt);
//			cit.setPagePanel(panel);
//			cit.setDeployFlag(1);
//			super.save(cit);
//		}
//		
//	}
//
//	
//	public Page findSystemMainTableByModule(Module module, String tableName, int pageNo, int pageSize) {
//		Page page = null;
//		//String hql ="";
//		Criteria critera = super.createCriteria(SystemMainTable.class, "tableName", true);
//		if(module!=null){
//			critera.add(Restrictions.eq("module", module));
//		}
//		if(StringUtils.isNotBlank(tableName)){
//			critera.add(Restrictions.disjunction()
//					.add(Restrictions.ilike("tableName", tableName, MatchMode.ANYWHERE)) //忽略大小写
//					.add(Restrictions.ilike("tableCnName", tableName, MatchMode.ANYWHERE)));
//		}
//		critera.add(Restrictions.eq("userExtFlag", Integer.valueOf(1)));
//		page = super.pagedQuery(critera, pageNo, pageSize);
//		return page;
//	}
//
//	public void removeSystemMainTable(String[] smtIds) {
//		if(smtIds==null|| smtIds.length==0){
//			throw new ServiceException("请选择要删除的系统主表");
//		}
//		for(int i=0; i<smtIds.length; i++){
//			String smtId = smtIds[i];
//			this.removeSystemMainTable(smtId);
//		}
//
//	}
//	
//	public void removeSystemMainTable(String smtId) {
//		SystemMainTable smt = super.get(SystemMainTable.class, Long.valueOf(smtId));
//		if(smt.getDeployFlag()!=null&& smt.getDeployFlag()==1){
//			//throw new ServiceException("当前配置项类型处于发布状态不可以删除");
//		}
//		String hql="select cit from ConfigItemType cit where cit.systemMainTable=?";
//		List<ConfigItemType> citypes = super.find(hql, smt);
//		for(ConfigItemType cit : citypes){
//			super.executeUpdate("delete from ConfigItem ci where ci.configItemType=?", cit);
//		}
//		super.executeUpdate("delete from ConfigItemType smtc where smtc.systemMainTable=?", smt);
//		
//		super.executeUpdate("delete from SystemMainTableColumn smtc where smtc.systemMainTable=?", smt);
//		super.executeUpdate("delete from SystemTableSetting smtc where smtc.systemMainTable=?", smt);
//		super.executeUpdate("delete from PagePanel smtc where smtc.systemMainTable=?", smt);
//		super.executeUpdate("delete from PagePanel smtc where smtc.systemMainTable=?", smt);
//		super.executeUpdate("delete from PagePanelColumn smtc where smtc.systemMainTable=?", smt);
//		super.executeUpdate("delete from PagePanelTable smtc where smtc.systemMainTable=?", smt);
//		super.executeUpdate("delete from PagePanelTableRelation smtc where smtc.systemMainTable=?", smt);
//		super.remove(smt);
//
//	}
//	
//	public void removeSystemMainTableColumn(String[] smtcIds) {
//		if(smtcIds==null|| smtcIds.length==0){
//			throw new ServiceException("请选择要删除的字段");
//		}
//		for(int i=0; i<smtcIds.length; i++){
//			String smtcId = smtcIds[i];
//			this.removeSystemMainTableColumn(smtcId);
//		}
//		
//	}
//
//	private void removeSystemMainTableColumn(String smtcId) {
//		SystemMainTableColumn smtc = (SystemMainTableColumn) super.get(SystemMainTableColumn.class, Long.valueOf(smtcId));
//		if(smtc!=null){
//			SystemMainTable sysMainTable = smtc.getSystemMainTable();
//			SystemMainTableColumn pkColumn = sysMainTable.getPrimaryKeyColumn();
//			if(smtc== pkColumn){
//				sysMainTable.setPrimaryKeyColumn(null);
//				super.save(sysMainTable);
//			}
//			super.executeUpdate("delete SystemTableSetting stqc where stqc.mainTableColumn=?", new Object[]{smtc});
//			super.executeUpdate("delete UserTableSetting uts where uts.mainTableColumn=?", new Object[]{smtc});
//			super.executeUpdate("delete SystemTableQueryColumn stqc where stqc.mainTableColumn=?", new Object[]{smtc});
//
//		}else{ 
//			SystemMainTableExtColumn smtec = (SystemMainTableExtColumn) super.get(SystemMainTableExtColumn.class, Long.valueOf(smtcId));
//			super.executeUpdate("delete SystemTableSetting stqc where stqc.extendTableColumn=?", new Object[]{smtec});
//			super.executeUpdate("delete UserTableSetting uts where uts.extendTableColumn=?", new Object[]{smtec});
//			super.executeUpdate("delete SystemTableQueryColumn stqc where stqc.extendTableColumn=?", new Object[]{smtec});
//		}
//
//		super.remove(smtc);
//		
//
//	}
//	
//	
//	public void saveSystemMainTableDeploy(SystemMainTable smt) {
//		SessionFactory sf = super.getHibernateSessionFactory();
//		SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sf;
//		Dialect dialect = sessionFactoryImpl.getDialect();
//		StringBuffer buff = new StringBuffer();
//		String dialectName = PropertiesUtil.getProperties("hibernate.dialect");
//		String tableName =smt.getTableName();
//		
//		if(dialectName.equalsIgnoreCase("org.hibernate.dialect.SQLServerDialect")){
//			buff.append("CREATE TABLE ").append(smt.getTableName()).append("(");
//			buff.append(" ID ").append(dialect.getTypeName(Types.BIGINT)).append(" identity primary key");
//			buff.append(");");
//		}else if(dialectName.equalsIgnoreCase("org.hibernate.dialect.OracleDialect")){
//			buff.append("CREATE TABLE ").append(smt.getTableName()).append("(");
//			buff.append(" ID ").append(dialect.getTypeName(Types.BIGINT)).append(" primary key");
//			buff.append(");");
//		}
//		
//		try {
//			Connection conn = super.getSession().connection();
//			Statement stmt = conn.createStatement();
//			stmt.executeUpdate(buff.toString());
//			conn.commit();
//		} catch (Exception e) {
//			System.out.println("创建的表已经存在，直接进入字段增加");
//		}	
//		
//		SystemMainTableColumn pkc = smt.getPrimaryKeyColumn();
//		String pkcname = pkc.getColumnName();
//		
//		Criteria c = super.getCriteria(SystemMainTableColumn.class);
//		c.add(Restrictions.eq("systemMainTable", smt));
//		c.addOrder(Order.asc("id"));
//		List<SystemMainTableColumn> list = c.list();
//		for(SystemMainTableColumn smtc : list){
//
//			String columnName = smtc.getColumnName();
//			if(!pkcname.equalsIgnoreCase(columnName)){
//				SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
//				//smtcType = super.get(SystemMainTableColumnType.class, Long.valueOf(smtcType.getId()));
//			
//				String columnTypeName = smtcType.getColumnTypeName();
//				Integer length = smtc.getLength();
//				if(length==null|| length.intValue()==0){
//					length = 200;
//				}
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
//				}else if(columnTypeName.equalsIgnoreCase("dateText")){
//					sqlColumnType = dialect.getTypeName(Types.DATE);
//				}
//				String hql="ALTER TABLE "+ tableName + " ADD " + columnName + " " +  sqlColumnType;
//				try {
//					Connection conn = super.getSession().connection();
//					Statement stmt = conn.createStatement();
//					stmt.executeUpdate(hql);
//					conn.commit();
//				} catch (Exception e) {
//					System.out.println("增加的字段已经存在, 继续增加新的字段");
//				}	
//			}
//			
//		}
//		
//		String className = smt.getClassName();
//		int lastDot = className.lastIndexOf(".");
//		String sourcePkg = className.substring(0, lastDot);
//		String sourceClass = className.substring(lastDot+1);
//		
//		this.saveTableEntity(sourcePkg, sourceClass, ConfigItem.class);
//		
//	}
//	
//	
//	public SystemMainTable saveSystemMainTable(SystemMainTable smt) {
//
////		*****************************************************************************************************	
//		smt.setUserExtFlag(1);
//		String tableName = smt.getTableName();
//		tableName = StringUtils.capitalize(smt.getTableName());
//		if(tableName.length()>21){
//			throw new ServiceException("表名称不可以超过21个字符");
//		}
//		smt.setTableName(tableName);
//		
//		if(smt.getClassName()==null){
//			String className = "com.digitalchina.itil.config.entity."+tableName;
//			smt.setClassName(className);
//		}else{
//			if(smt.getDeployFlag()!=null&& smt.getDeployFlag().intValue()==1){
//				SystemMainTable smtOld = super.get(SystemMainTable.class, smt.getId());
//				smt.setTableName(smtOld.getTableName());
//			}
//		}
//		
//		boolean isInsert = smt.getId()==null;
//		
//		
//		super.save(smt);
//		//save column
//		if(isInsert){
//			PropertyType pt = new PropertyType();
//			pt.setId(2L);
//			SystemMainTableColumn smtc = new SystemMainTableColumn();
//			SystemMainTableColumnType smtct = new SystemMainTableColumnType();
//			smtct.setId(8L);
//			smtc.setPropertyType(pt);
//			smtc.setSystemMainTableColumnType(smtct);
//			smtc.setColumnCnName("自动编号");
//			smtc.setColumnName("id");
//			smtc.setPropertyName("id");
//			smtc.setDescn("主键，不可修改和删除");
//			smtc.setOrder(1);
//			smtc.setSystemMainTable(smt);
//			smtc.setIsHiddenItem(1);
//			smtc.setIsSearchItem(0);
//			smtc.setIsUpdateItem(0);
//			smtc.setIsMustInput(1);
//			super.save(smtc);
//			smt.setPrimaryKeyColumn(smtc);
//			
//			//smt.setTableName("ci_ext"+tableName);
//			
//			super.save(smt);
//			
//			//保存配置项类型草稿
//			ConfigItemType cit = new ConfigItemType();
//			cit.setName(smt.getTableCnName());
//			cit.setClassName(smt.getClassName());
//			cit.setTableName(smt.getTableName());
//			cit.setSystemMainTable(smt);
//			cit.setPagePanel(null);
//			cit.setDeployFlag(0);
//			super.save(cit);
//			
//			
//		}
//		
//		
//		
//		return smt;
//	}
//
//	public SystemMainTableColumn saveSystemMainTableColumn(
//			SystemMainTableColumn smtc) {
//		
//		SessionFactory sf = super.getHibernateSessionFactory();
//		SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sf;
//		Dialect dialect = sessionFactoryImpl.getDialect();
//		
//		SystemMainTable smt = smtc.getSystemMainTable();
//		smt = super.get(SystemMainTable.class, Long.valueOf(smt.getId()));
//		
//		if(smtc.getId()!=null){
//			SystemMainTableColumn old = (SystemMainTableColumn) super.getObject(SystemMainTableColumn.class, smtc.getId(), true);
//			boolean columnNameEqu = old.getColumnName().equals(smtc.getColumnName());
//			boolean propertyTypeEqu = old.getPropertyType().getId().equals(smtc.getPropertyType().getId());
//			boolean columnTypeEqu = old.getSystemMainTableColumnType().getId().equals(smtc.getSystemMainTableColumnType().getId());
//			boolean result = columnNameEqu&& propertyTypeEqu&& columnTypeEqu;
//			if(!result){
//				if(smt.getDeployFlag()!=null&& smt.getDeployFlag().intValue()==1){
//					throw new ServiceException("当前用户表已经发布，不可以修改字段名称或类型");
//				}
//			}
//			
//		}
//		
//		
//		SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
//		smtcType = super.get(SystemMainTableColumnType.class, Long.valueOf(smtcType.getId()));
//		
//		PropertyType propType = smtc.getPropertyType();
//		propType = super.get(PropertyType.class, Long.valueOf(propType.getId()));
//		
//
//		String tableName = smt.getTableName();
//		String columnName = smtc.getColumnName();
//		columnName=Character.toLowerCase(columnName.charAt(0))+columnName.substring(1);
//		smtc.setColumnName(columnName);
//		smtc.setPropertyName(columnName);
//		
//		if(smtc.getId()==null){
//			
//			if(smtc.getIsHiddenItem()==null){
//				smtc.setIsHiddenItem(0);
//			}
//			if(smtc.getIsSearchItem()==null){
//				smtc.setIsSearchItem(0);
//			}
//			if(smtc.getIsUpdateItem()==null){
//				smtc.setIsUpdateItem(1);
//			}
//			if(smtc.getIsExtItem()==null){
//				smtc.setIsExtItem(0);
//			}
//			if(smtc.getIsMustInput()==null){
//				smtc.setIsMustInput(0);
//			}
//			
//			//判断是否已经存在同名的字段
//			Criteria c = super.getCriteria(SystemMainTableColumn.class);
//			c.add(Restrictions.eq("systemMainTable", smt));
//			c.add(Restrictions.ilike("columnName", columnName));
//			c.setProjection(Projections.rowCount());
//			Integer cncount = (Integer) c.uniqueResult();
//			if(cncount>0){
//				throw new ServiceException("字段名称"+columnName+"已经存在，请更换其他名称");
//			}
//			
//			c = super.getCriteria(SystemMainTableColumn.class);
//			c.add(Restrictions.eq("systemMainTable", smt));
//			c.add(Restrictions.ilike("columnCnName", columnName));
//			c.setProjection(Projections.rowCount());
//			cncount = (Integer) c.uniqueResult();
//			if(cncount>0){
//				throw new ServiceException("字段中文名称"+columnName+"已经存在，请更换其他名称");
//			}
////			*********************************************************************************			
////			String columnTypeName = smtcType.getColumnTypeName();
////			Integer length = smtc.getLength();
////			if(length==null|| length.intValue()==0){
////				length = 200;
////			}
////			
////			String sqlColumnType = dialect.getTypeName(Types.VARCHAR);
////			
////			if(columnTypeName.equalsIgnoreCase("text")){
////				
////				sqlColumnType = dialect.getTypeName(Types.VARCHAR, length, 10, 0);
////			}else if(columnTypeName.equalsIgnoreCase("textArea")){
////				sqlColumnType = dialect.getTypeName(Types.VARCHAR);
////			}else if(columnTypeName.equalsIgnoreCase("select")){
////				sqlColumnType = dialect.getTypeName(Types.BIGINT);
////			}else if(columnTypeName.equalsIgnoreCase("yesNoSelect")){
////				sqlColumnType = dialect.getTypeName(Types.INTEGER);
////			}else if(columnTypeName.equalsIgnoreCase("hidden")){
////				sqlColumnType = dialect.getTypeName(Types.BIGINT);
////			}else if(columnTypeName.equalsIgnoreCase("dateText")){
////				sqlColumnType = dialect.getTypeName(Types.DATE);
////			}
//
////			String hql="ALTER TABLE "+ tableName + " ADD " + columnName + " " +  sqlColumnType;
////			try {
////				Connection conn = super.getSession().connection();
////				Statement stmt = conn.createStatement();
////				stmt.executeUpdate(hql);
////				conn.commit();
////			} catch (Exception e) {
////				e.printStackTrace();
////			}	
////			*********************************************************************************
//		}
//		
//		super.save(smtc);
//		
//		
//		return smtc;
//	}
//
//
//	public void setSystemColumnService(SystemColumnService systemColumnService) {
//		this.systemColumnService = systemColumnService;
//	}
//
//}
