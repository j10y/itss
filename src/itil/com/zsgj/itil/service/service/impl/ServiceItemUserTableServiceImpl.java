package com.zsgj.itil.service.service.impl;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.SystemTableSetting;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelType;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.config.service.CustomerTableService;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.service.ServiceItemUserTableService;

public class ServiceItemUserTableServiceImpl extends BaseDao implements ServiceItemUserTableService{
	private CustomerTableService customerTableService;
	private SystemColumnService systemColumnService;
	private SystemMainColumnService systemMainColumnService;
	private SystemMainTableService systemMainTableService;
	String FSP = System.getProperty("file.separator");
	String LSP = "\n";
	
	public List<PagePanel> findAllBasePanels() {
		return customerTableService.findAllBasePanels();
	}

	public List<PagePanel> findAllGroupPanels() {
		return customerTableService.findAllGroupPanels();
	}

	public List<PagePanel> findAllPagePanels() {
		return customerTableService.findAllPagePanels();
	}

	public List findRequireTableColumns(SystemMainTable smt) {
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.add(Restrictions.ne("propertyName", "processType"));
		c.add(Restrictions.ne("propertyName", "status"));
		c.add(Restrictions.ne("propertyName", "deleteFlag"));
		List<SystemMainTableColumn> list = c.list();
		return list;
		
	}

	//%%%%%%%%%%%%%%%%%%%
	public ConfigItemType findConfigItemTypeByTable(SystemMainTable smt) {
		ConfigItemType result = null;
		Criteria c =super.getCriteria(ConfigItemType.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		List list = c.list();
		if(!list.isEmpty()){
			result = (ConfigItemType) list.iterator().next();
		}
		return result;
	}
	
	public ServiceItemUserTable findServiceItemUserTableByTable(SystemMainTable smt) {
		ServiceItemUserTable result = null;
		Criteria c =super.getCriteria(ServiceItemUserTable.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		List list = c.list();
		if(!list.isEmpty()){
			result = (ServiceItemUserTable) list.iterator().next();
		}
		return result;
	}

	public SystemMainTable findCustomerTableById(String tableId) {
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("id", Long.valueOf(tableId)));
		SystemMainTable smt = (SystemMainTable) c.uniqueResult();
		return smt;
	}

//	%%%%%%%%%%%%%%%%%%%
	public List<ConfigItemType> findAllTopConfigItems() {
		Criteria c = super.getCriteria(ConfigItemType.class);
		c.add(Restrictions.isNull("parentConfigItemType"));
		c.add(Restrictions.ne("deployFlag", Integer.valueOf(0)));
		c.addOrder(Order.asc("name"));
		List<ConfigItemType> list = c.list();
		return list;
	}

	/**
	 * ��ȡ�־û������������
	 * @Methods Name getPropertyByPersistentClass
	 * @Create In 2009-2-24 By sa
	 * @param model
	 * @return List<Property>
	 */
	private List<Property> getPropertyByPersistentClass(PersistentClass model){
		List<Property> list = new ArrayList<Property>();
		if(model!=null){
			Iterator<Property> iter = model.getPropertyIterator();
			while(iter.hasNext()){
				Property item =  iter.next();
				list.add(item);
			}
		}
		return list;
	}
	
	/**
	 * ʹ�����������͵���������excel�ļ�
	 * @Methods Name saveUserMainTableExcel
	 * @Create In 2009-1-19 By sa
	 * @param citypeTable
	 * @param fileRootPath
	 * @return String
	 */
	public String saveUserMainTableExcel(SystemMainTable citypeTable, String fileRootPath) {
//		begin
		HSSFWorkbook wb=new HSSFWorkbook();
      	HSSFCellStyle cellStyle=wb.createCellStyle();
      	cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//���ñ������¾���
      	     	
      	HSSFSheet sheet = wb.createSheet();
     	wb.setSheetName(0, citypeTable.getTableCnName(), HSSFWorkbook.ENCODING_UTF_16);
     	
     	//���������У�0��
     	HSSFRow row=sheet.createRow(0);
      	row.setHeight((short)400);	
      	//end
      	//�����������Ϣ
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.createAlias("this.systemMainTable", "systemMainTable");
		c.setFetchMode("systemMainTable", FetchMode.JOIN);
		c.add(Restrictions.ne("systemMainTableColumnType.id", Long.valueOf(8)));
		c.add(Restrictions.ilike("systemMainTable.className", "com.zsgj.itil.config.entity.ConfigItem"));
		List<SystemMainTableColumn> smtcs = c.list();
		//int cicsize = 0;
		for(int i=0; i<smtcs.size(); i++){
			SystemMainTableColumn smtc = smtcs.get(i);
			Long smtcId = smtc.getId();
			String columnCnName = smtc.getColumnCnName();
			SystemMainTableColumnType smtctype = smtc.getSystemMainTableColumnType();
			
			//�������������ŵ����ֶ�
			//if(smtctype!=null&& !smtctype.getColumnTypeName().equalsIgnoreCase("hidden")){
				//if(!smtc.getColumnName().equalsIgnoreCase("id")){
			HSSFCell cell=row.createCell((short)i); //���������¼�У��ӵ�1�п�ʼ *******
     		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
     		cell.setCellStyle(cellStyle);
         	cell.setCellValue("������."+columnCnName+"("+smtcId+")");
         	//cicsize ++ ;
			//	}
			//}
		}
		int cicsize = smtcs.size();
		System.out.println("cicsize:"+ cicsize);
		//����
		c = super.getCriteria(SystemMainTableColumn.class);
		c.createAlias("this.systemMainTable", "systemMainTable");
		c.setFetchMode("systemMainTable", FetchMode.JOIN);
		c.add(Restrictions.ne("systemMainTableColumnType.id", Long.valueOf(8)));
		c.add(Restrictions.ilike("systemMainTable.className", "com.zsgj.itil.config.entity.ConfigItemFinanceInfo"));
		smtcs = c.list();
		int cifcsize = 0;
		for(int i=0; i<smtcs.size(); i++){
			SystemMainTableColumn smtc = smtcs.get(i);
			Long smtcId = smtc.getId();
			String columnCnName = smtc.getColumnCnName();
			SystemMainTableColumnType smtctype = smtc.getSystemMainTableColumnType();
			
//			�������������ŵ����ֶ�
		//	if(smtctype!=null&& !smtctype.getColumnTypeName().equalsIgnoreCase("hidden")){
		//		if(!smtc.getColumnName().equalsIgnoreCase("id")){
			HSSFCell cell=row.createCell((short)(i + cicsize)); //���������¼�У�����������������ſ�ʼ
     		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
     		cell.setCellStyle(cellStyle);
         	cell.setCellValue("����."+columnCnName+"("+smtcId+")");
         	cifcsize++;
		//		}
		//	}
			
		}
		
		//�����������������е��ֶ�
		c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", citypeTable));
		c.add(Restrictions.ne("systemMainTableColumnType.id", Long.valueOf(8)));
		smtcs = c.list();
		for(int i=0; i<smtcs.size(); i++){
			SystemMainTableColumn smtc = smtcs.get(i);
			Long smtcId = smtc.getId();
			String columnCnName = smtc.getColumnCnName();
			
			HSSFCell cell=row.createCell((short)(i+ cicsize + cifcsize)); //���������¼�У�����������������ſ�ʼ
     		cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
     		cell.setCellStyle(cellStyle);
         	cell.setCellValue(citypeTable.getTableCnName()+"."+columnCnName+ "("+ smtcId+")");
			
		}
		
		String excelFileName = null;
		String excelFullFileName = null;
		final String FSP = System.getProperty("file.separator");
		try{
//			���Excel�ļ�����ʵ·��
      		excelFileName = "citype_"+citypeTable.getTableName()+"_"+System.currentTimeMillis() + ".xls";
      		excelFullFileName = fileRootPath + FSP + excelFileName;
      	 	FileOutputStream fileout=new FileOutputStream(excelFullFileName);
          	wb.write(fileout);
          	fileout.close();
       	}
      	catch(Exception e){
      		e.printStackTrace();
      	}
		return excelFileName;
	}
	
	public String saveConfigItemTypeExcel(ConfigItemType citype, String fileRootPath) {
		SystemMainTable citypeTable = citype.getSystemMainTable();
		return this.saveUserMainTableExcel(citypeTable, fileRootPath);
	}

	//�Ƿ���Ҫ�����µ����Խ���־û���
	private boolean needAddProperty2PersistClass(PersistentClass model, Column column){
		boolean result = true; //Ĭ����Ҫ����
		Iterator<Property> iter = model.getPropertyIterator();
		while(iter.hasNext()){
			Property item =  iter.next();
			String propertyName = item.getName();
			Object value = item.getValue();
			if(!column.getPropertyName().equalsIgnoreCase("id")){
				if(column.getPropertyName().equalsIgnoreCase(propertyName)){ //ֻ�г־û��������ˣ��Ͳ��ټ���
					result = false;
					return result;
				}
			}
			
		}
		return result;
	}
	/**
	 * ���ú�̨��������������ʵ���mapping����sessionFactory
	 * ��Ҫ��Ҫ��һ������
	 */
	public void saveTableEntity(ServiceItem scid, String sourcePkg, String sourceClassName, String targetClass) {
		SessionFactory ssf = super.getHibernateSessionFactory();
		Configuration config = super.getConfiguration();
		String newClassName = sourcePkg+"."+ sourceClassName;
		//ͨ����ȫ·����ȡ�־û���
		PersistentClass model = config.getClassMapping(newClassName);   
		if(model==null){ 
			//��һ�η��������־û��಻���ڣ����½��־û���
			String tablePrefix = PropertiesUtil.getProperties("system.config.scitable.prefix", "itil_sci_");
			SystemMainTable smt = 
				customerTableService.genPersistentClass(model, sourcePkg, sourceClassName, targetClass, tablePrefix);
					
			this.saveServiceItemUserTablePanel(scid, smt, 1);
		
		}else{
			//���������������ֶα��浽��չ�ֶ�����������ϵͳ��������
			saveExtendProps(sourceClassName, model);
		}
		
	}

	@SuppressWarnings("unchecked")
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("������" + className + "����ȷ��");
			e.printStackTrace();
		}
		return clazz;
	}
	
	private void saveExtendProps(String sourceClassName, PersistentClass model) {
		this.customerTableService.saveExtendProps(sourceClassName, model);
	}

	//�����������͵����û�е�����£�ֱ�ӱ����Ĭ������
	private void genTablePanels(SystemMainTable smt) {
		PagePanel panel = new PagePanel();
		panel.setName("panel_"+smt.getTableCnName()+"Panel");
		panel.setTitle(smt.getTableCnName());
		panel.setDescn(smt.getTableCnName()+"��壬�û����Ӵ���������ʱϵͳ�Զ����������");
		panel.setGroupFlag(0);
		PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
		panel.setXtype(ppt);
		panel.setSettingType(SystemTableSetting.INPUT); //form
		panel.setSystemMainTable(smt);
		Module module = super.findUniqueBy(Module.class, "serviceKeyName", "service");
		panel.setModule(module);
		super.save(panel);
		
		//�����������
		PagePanelTable pptble = new PagePanelTable();
		pptble.setPagePanel(panel);
		pptble.setSystemMainTable(smt);
		super.save(pptble);
	}

//	����һ���û�������Զ����ɶ�Ӧ�����
	public void saveServiceItemUserTablePanel_backup(ServiceItem scid, SystemMainTable smt, int deployFlag){
		String className = smt.getClassName();
		String tableName = smt.getTableName();
		String tableCnName = smt.getTableCnName();
		String ppcount = "select count(*) from PagePanel ppc where ppc.systemMainTable=?";
		Query ppQuery = super.createQuery(ppcount, smt);
		Long ppcRows = (Long) ppQuery.uniqueResult();
		
		PagePanel panel = null;
		
		if(ppcRows.intValue()==0){
			panel = new PagePanel();
			panel.setName("panel_"+tableName+"Panel");
			panel.setTitle(tableCnName);
			panel.setDescn(tableCnName+"��壬�û����ӷ���������ʱϵͳ�Զ����������");
			panel.setGroupFlag(0);
			PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
			panel.setXtype(ppt);
			panel.setSettingType(SystemTableSetting.INPUT); //form
			panel.setSystemMainTable(smt);
			Module module = super.findUniqueBy(Module.class, "name", "���������");
			panel.setModule(module);
			super.save(panel);
			
			//�����������
			PagePanelTable pptble = new PagePanelTable();
			pptble.setPagePanel(panel);
			pptble.setSystemMainTable(smt);
			super.save(pptble);
			
//			ȡϵͳ����������ֶΣ���ʼ��ϵͳ�ɼ��ֶ�
			int order = 1;
			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
			for(Column column : columns){
				//��ʼ������ϵͳ�ɼ��ֶ�
				//�жϵ�ǰ�ֶ���ϵͳ�ɼ��ֶ����Ƿ��Ѿ�����
				Criteria cSysInput = super.getCriteria(SystemTableSetting.class);
				cSysInput.add(Restrictions.eq("systemMainTable", smt));
				cSysInput.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
				//if(column instanceof SystemMainTableColumn){
					SystemMainTableColumn smtc = ((SystemMainTableColumn) column);
					cSysInput.add(Restrictions.eq("mainTableColumn", smtc));
//				}else if(column instanceof SystemMainTableExtColumn) {
//					SystemMainTableExtColumn smtec = ((SystemMainTableExtColumn) column);
//					cSysInput.add(Restrictions.eq("extendTableColumn", smtec));
//				}
				cSysInput.setProjection(Projections.rowCount());
				
				Integer cSysInputCount = (Integer) cSysInput.uniqueResult();
				if(cSysInputCount.intValue()==0){
					cSysInput.setProjection(Projections.max("order"));
					Integer sysInputMaxOrder = (Integer) cSysInput.uniqueResult();
					if(sysInputMaxOrder==null) sysInputMaxOrder = 0;
					//begin
					SystemTableSetting stsInput = new SystemTableSetting();
					stsInput.setSettingType(SystemTableSetting.INPUT);
					stsInput.setSystemMainTable(smt);
					//if(column instanceof SystemMainTableColumn){
						stsInput.setMainTableColumn((SystemMainTableColumn) column);
//					}else if(column instanceof SystemMainTableExtColumn) {
//						stsInput.setExtendTableColumn((SystemMainTableExtColumn) column);
//					}
					stsInput.setIsDisplay(new Integer(1));
					stsInput.setOrder(sysInputMaxOrder+1);
					this.save(stsInput);
					this.evict(stsInput);
					//end
				}	
					
				
				//��ʼ���б�ҳ���ϵͳ�ɼ��ֶ�
				Criteria cSysList = super.getCriteria(SystemTableSetting.class);
				cSysList.add(Restrictions.eq("systemMainTable", smt));
				cSysList.add(Restrictions.eq("settingType", SystemTableSetting.LIST));
//				if(column instanceof SystemMainTableColumn){
//					SystemMainTableColumn smtc = ((SystemMainTableColumn) column);
					cSysList.add(Restrictions.eq("mainTableColumn", smtc));
//				}else if(column instanceof SystemMainTableExtColumn) {
//					SystemMainTableExtColumn smtec = ((SystemMainTableExtColumn) column);
//					cSysList.add(Restrictions.eq("extendTableColumn", smtec));
//				}
				cSysList.setProjection(Projections.rowCount());
				
				Integer cSysListCount = (Integer) cSysList.uniqueResult();
				if(cSysListCount.intValue()==0){
					cSysList.setProjection(Projections.max("order"));
					Integer sysListMaxOrder = (Integer) cSysList.uniqueResult();
					if(sysListMaxOrder==null) sysListMaxOrder = 0;
					
					SystemTableSetting stsList = new SystemTableSetting();
					stsList.setSettingType(SystemTableSetting.LIST);
					stsList.setSystemMainTable(smt);
					//if(column instanceof SystemMainTableColumn){
						stsList.setMainTableColumn((SystemMainTableColumn) column);
//					}else if(column instanceof SystemMainTableExtColumn) {
//						stsList.setExtendTableColumn((SystemMainTableExtColumn) column);
//					}
					stsList.setIsDisplay(new Integer(1));
					stsList.setOrder(sysListMaxOrder+1);
					this.save(stsList);
					this.evict(stsList);
					
					
				}
				
			}
			
			String ppccount = "select count(*) from PagePanelColumn ppc where ppc.pagePanel=?";
			Query query = super.createQuery(ppccount, panel);
			Long cunt = (Long) query.uniqueResult();
			if(cunt.intValue()==0){
//				ȡ���е�ϵͳ�ɼ��ֶ�
				Criteria c = super.getCriteria(SystemTableSetting.class);
				c.add(Restrictions.eq("systemMainTable", smt));
				c.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
				c.addOrder(Order.asc("order"));
				List list = c.list();
				Iterator iter = list.iterator();
				while(iter.hasNext()){
					SystemTableSetting uts = (SystemTableSetting) iter.next();
					Column column = uts.getColumn();
					//����ϵͳ�ɼ��ֶΣ���������ֶ�
					
					//begin
					Criteria cpc = super.getCriteria(PagePanelColumn.class);
					cpc.add(Restrictions.eq("pagePanel", panel));
					//if(column instanceof SystemMainTableColumn){
						cpc.add(Restrictions.eq("mainTableColumn", (SystemMainTableColumn) column));
//					}else{
//						cpc.add(Restrictions.eq("extendTableColumn", (SystemMainTableExtColumn) column));
//					}
					cpc.setProjection(Projections.rowCount());
					Integer cpcCount = (Integer) cpc.uniqueResult();
					if(cpcCount==null||cpcCount.intValue()==0){
						cpc.setProjection(Projections.max("order"));
						Integer maxPanelOrder = (Integer) cpc.uniqueResult();
						if(maxPanelOrder==null) maxPanelOrder = 0;
						//begin
						PagePanelColumn ppc = new PagePanelColumn();
						ppc.setPagePanel(panel);
						ppc.setSystemMainTable(smt);
						//if(column instanceof SystemMainTableColumn){
							ppc.setMainTableColumn((SystemMainTableColumn) column);
//						}else if(column instanceof SystemMainTableExtColumn) {
//							ppc.setExtendTableColumn((SystemMainTableExtColumn) column);
//						}
						ppc.setIsDisplay(uts.getIsDisplay());
						ppc.setOrder(maxPanelOrder+1);
						ppc.setIsMustInput(column.getIsMustInput());
						super.save(ppc);
						super.evict(ppc);
						
					}//end
					//end
					
				}
			}
			
			
			//���������ʷ������SCID������ϵ��Ϣ
			if(!smt.getClassName().endsWith("Event")){
				ServiceItemUserTable cit = this.findServiceItemUserTableByTable(smt);
				if(cit==null){
					cit = new ServiceItemUserTable();
				}
				cit.setServiceItemType(scid.getServiceItemType());
				cit.setServiceItem(scid);
				
				cit.setClassName(smt.getClassName());
				cit.setTableName(smt.getTableName());
				cit.setSystemMainTable(smt);
				cit.setPagePanel(panel);
				cit.setDeployFlag(deployFlag);
				super.save(cit);
			}
			
		}
		
	}
	
	//����һ���û�������Զ����ɶ�Ӧ�����
	public void saveServiceItemUserTablePanel(ServiceItem scid, SystemMainTable smt, int deployFlag){
		
			PagePanel pageInputPanel = this.saveSCIDPanel(scid, smt, UserTableSetting.INPUT, deployFlag);
			
			PagePanel pageListPanel = this.saveSCIDPanel(scid, smt, UserTableSetting.LIST, deployFlag);
			
			//���������ʷ������SCID������ϵ��Ϣ
			if(!smt.getClassName().endsWith("Event")){
				ServiceItemUserTable cit = this.findServiceItemUserTableByTable(smt);
				if(cit==null){
					cit = new ServiceItemUserTable();
				}
				cit.setServiceItemType(scid.getServiceItemType());
				cit.setServiceItem(scid);
				
				cit.setClassName(smt.getClassName());
				cit.setTableName(smt.getTableName());
				cit.setSystemMainTable(smt);
				cit.setPagePanel(pageInputPanel);
				cit.setPageListPanel(pageListPanel);
				cit.setDeployFlag(deployFlag);
				super.save(cit);
			}
			
	}

	
//	����һ���û�������Զ����ɶ�Ӧ�����
	public PagePanel saveSCIDPanel(ServiceItem scid, SystemMainTable smt, int settingType, int deployFlag){
		String className = smt.getClassName();
		String tableName = smt.getTableName();
		String tableCnName = smt.getTableCnName();
		String ppcount = "select count(*) from PagePanel ppc where ppc.systemMainTable=? and ppc.settingType=?";
		Query ppQuery = super.createQuery(ppcount, smt, new Integer(settingType));
		Long ppcRows = (Long) ppQuery.uniqueResult();
		
		PagePanel panel = null;
		
		if(ppcRows.intValue()==0){
			panel = new PagePanel();
			String settingTypeStr = "Input";
			if(settingType==UserTableSetting.LIST){
				 settingTypeStr = "List";
			}
			panel.setName("panel_"+tableName+"_"+settingTypeStr);
			panel.setTitle(tableCnName);
			panel.setDescn(tableCnName+"��壬�û����ӷ���������ʱϵͳ�Զ����������");
			panel.setGroupFlag(0);
			PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
			panel.setXtype(ppt);
			panel.setSettingType(new Integer(settingType)); //form
			panel.setSystemMainTable(smt);
			Module module = super.findUniqueBy(Module.class, "name", "�������");
			panel.setModule(module);
			super.save(panel);
			
			//�����������
			PagePanelTable pptble = new PagePanelTable();
			pptble.setPagePanel(panel);
			pptble.setSystemMainTable(smt);
			super.save(pptble);
			
//			ȡϵͳ����������ֶΣ���ʼ��ϵͳ�ɼ��ֶ�
			int order = 1;
			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
			for(Column column : columns){
				//��ʼ������ϵͳ�ɼ��ֶ�
				//�жϵ�ǰ�ֶ���ϵͳ�ɼ��ֶ����Ƿ��Ѿ�����
				Criteria cSysInput = super.getCriteria(SystemTableSetting.class);
				cSysInput.add(Restrictions.eq("systemMainTable", smt));
				cSysInput.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
				//if(column instanceof SystemMainTableColumn){
					SystemMainTableColumn smtc = ((SystemMainTableColumn) column);
					cSysInput.add(Restrictions.eq("mainTableColumn", smtc));
//				}else if(column instanceof SystemMainTableExtColumn) {
//					SystemMainTableExtColumn smtec = ((SystemMainTableExtColumn) column);
//					cSysInput.add(Restrictions.eq("extendTableColumn", smtec));
//				}
				cSysInput.setProjection(Projections.rowCount());
				
				Integer cSysInputCount = (Integer) cSysInput.uniqueResult();
				if(cSysInputCount.intValue()==0){
					cSysInput.setProjection(Projections.max("order"));
					Integer sysInputMaxOrder = (Integer) cSysInput.uniqueResult();
					if(sysInputMaxOrder==null) sysInputMaxOrder = 0;
					//begin
					SystemTableSetting stsInput = new SystemTableSetting();
					stsInput.setSettingType(SystemTableSetting.INPUT);
					stsInput.setSystemMainTable(smt);
//					if(column instanceof SystemMainTableColumn){
						stsInput.setMainTableColumn((SystemMainTableColumn) column);
//					}else if(column instanceof SystemMainTableExtColumn) {
//						stsInput.setExtendTableColumn((SystemMainTableExtColumn) column);
//					}
					stsInput.setIsDisplay(new Integer(1));
					stsInput.setOrder(sysInputMaxOrder+1);
					this.save(stsInput);
					this.evict(stsInput);
					//end
				}	
					
				
				//��ʼ���б�ҳ���ϵͳ�ɼ��ֶ�
				Criteria cSysList = super.getCriteria(SystemTableSetting.class);
				cSysList.add(Restrictions.eq("systemMainTable", smt));
				cSysList.add(Restrictions.eq("settingType", SystemTableSetting.LIST));
//				if(column instanceof SystemMainTableColumn){
//					SystemMainTableColumn smtc = ((SystemMainTableColumn) column);
					cSysList.add(Restrictions.eq("mainTableColumn", smtc));
//				}else if(column instanceof SystemMainTableExtColumn) {
//					SystemMainTableExtColumn smtec = ((SystemMainTableExtColumn) column);
//					cSysList.add(Restrictions.eq("extendTableColumn", smtec));
//				}
				cSysList.setProjection(Projections.rowCount());
				
				Integer cSysListCount = (Integer) cSysList.uniqueResult();
				if(cSysListCount.intValue()==0){
					cSysList.setProjection(Projections.max("order"));
					Integer sysListMaxOrder = (Integer) cSysList.uniqueResult();
					if(sysListMaxOrder==null) sysListMaxOrder = 0;
					
					SystemTableSetting stsList = new SystemTableSetting();
					stsList.setSettingType(SystemTableSetting.LIST);
					stsList.setSystemMainTable(smt);
//					if(column instanceof SystemMainTableColumn){
						stsList.setMainTableColumn((SystemMainTableColumn) column);
//					}else if(column instanceof SystemMainTableExtColumn) {
//						stsList.setExtendTableColumn((SystemMainTableExtColumn) column);
//					}
					stsList.setIsDisplay(new Integer(1));
					stsList.setOrder(sysListMaxOrder+1);
					this.save(stsList);
					this.evict(stsList);
					
					
				}
				
			}
			
			String ppccount = "select count(*) from PagePanelColumn ppc where ppc.pagePanel=?";
			Query query = super.createQuery(ppccount, panel);
			Long cunt = (Long) query.uniqueResult();
			if(cunt.intValue()==0){
//				ȡ���е�ϵͳ�ɼ��ֶ�
				Criteria c = super.getCriteria(SystemTableSetting.class);
				c.add(Restrictions.eq("systemMainTable", smt));
				c.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
				c.addOrder(Order.asc("order"));
				List list = c.list();
				Iterator iter = list.iterator();
				while(iter.hasNext()){
					SystemTableSetting uts = (SystemTableSetting) iter.next();
					Column column = uts.getColumn();
					//����ϵͳ�ɼ��ֶΣ���������ֶ�
					
					//begin
					Criteria cpc = super.getCriteria(PagePanelColumn.class);
					cpc.add(Restrictions.eq("pagePanel", panel));
//					if(column instanceof SystemMainTableColumn){
						cpc.add(Restrictions.eq("mainTableColumn", (SystemMainTableColumn) column));
//					}else{
//						cpc.add(Restrictions.eq("extendTableColumn", (SystemMainTableExtColumn) column));
//					}
					cpc.setProjection(Projections.rowCount());
					Integer cpcCount = (Integer) cpc.uniqueResult();
					if(cpcCount==null||cpcCount.intValue()==0){
						cpc.setProjection(Projections.max("order"));
						Integer maxPanelOrder = (Integer) cpc.uniqueResult();
						if(maxPanelOrder==null) maxPanelOrder = 0;
						//begin
						PagePanelColumn ppc = new PagePanelColumn();
						ppc.setPagePanel(panel);
						ppc.setSystemMainTable(smt);
					//	if(column instanceof SystemMainTableColumn){
							ppc.setMainTableColumn((SystemMainTableColumn) column);
//						}else if(column instanceof SystemMainTableExtColumn) {
//							ppc.setExtendTableColumn((SystemMainTableExtColumn) column);
//						}
						ppc.setIsDisplay(uts.getIsDisplay());
						ppc.setOrder(maxPanelOrder+1);
						ppc.setIsMustInput(column.getIsMustInput());
						super.save(ppc);
						super.evict(ppc);
						
					}//end
					//end
					
				}
			}
			
		}
		return panel;
	}
	
	
	public Page findSystemMainTableByModule(Module module, String tableName, int pageNo, int pageSize) {
		Page page = null;
		//String hql ="";
		Criteria critera = super.createCriteria(SystemMainTable.class, "tableName", true);
		if(module!=null){
			critera.add(Restrictions.eq("module", module));
		}
		if(StringUtils.isNotBlank(tableName)){
			critera.add(Restrictions.disjunction()
					.add(Restrictions.ilike("tableName", tableName, MatchMode.ANYWHERE)) //���Դ�Сд
					.add(Restrictions.ilike("tableCnName", tableName, MatchMode.ANYWHERE)));
		}
		critera.add(Restrictions.eq("userExtFlag", Integer.valueOf(1)));
		page = super.pagedQuery(critera, pageNo, pageSize);
		return page;
	}

	public void removeSystemMainTable(String[] smtIds) {
		if(smtIds==null|| smtIds.length==0){
			throw new ServiceException("��ѡ��Ҫɾ����ϵͳ����");
		}
		for(int i=0; i<smtIds.length; i++){
			String smtId = smtIds[i];
			this.removeSystemMainTable(smtId);
		}

	}
	
	public void removeSystemMainTable(String smtId) {
		SystemMainTable smt = super.get(SystemMainTable.class, Long.valueOf(smtId));
		if(smt.getDeployFlag()!=null&& smt.getDeployFlag()==1){
			//throw new ServiceException("��ǰ���������ʹ��ڷ���״̬������ɾ��");
		}
		String hql="select cit from ConfigItemType cit where cit.systemMainTable=?";
		List<ConfigItemType> citypes = super.find(hql, smt);
		try {
			for (ConfigItemType cit : citypes) {
				//�������������Ͷ�Ӧ������������
				Criteria c = super.getCriteria(ConfigItem.class);
				c.add(Restrictions.eq("configItemType", cit));
				c.setProjection(Projections.property("id"));
				List<Long> cis = c.list();
				for(Long ciId : cis){
					super.executeUpdate("delete from CIRelationShipItem where configItem.id=?", new Object[]{ciId});
				}
				super.executeUpdate("delete from ConfigItem ci where ci.configItemType=?",cit);						
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
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
			throw new ServiceException("��ѡ��Ҫɾ�����ֶ�");
		}
		for(int i=0; i<smtcIds.length; i++){
			String smtcId = smtcIds[i];
			try {
				//ɾ�����ֶ�
				this.removeSystemMainTableColumn(smtcId);
			} catch (ServiceException e) {
				//ɾ����չ�ֶ�
//				SystemExtColumnServcie secs = (SystemExtColumnServcie) ContextHolder.getBean("systemExtColumnService");
//				secs.removeSystemExtTableColumn(new String[]{smtcId});
				
			}			
		}
		
	}

	/**
	 * �û���������ɾ���ֶΣ���������������õ��ֶΣ��Լ������Ŀɼ��ֶ�
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2009-1-12 By sa
	 * @param smtcId void
	 */
	private void removeSystemMainTableColumn(String smtcId) {
		SystemMainTableColumn smtc = (SystemMainTableColumn) super.get(SystemMainTableColumn.class, Long.valueOf(smtcId));
		if(smtc!=null){
			//if(smtc!=null){
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
//			}else{ 
//				SystemMainTableExtColumn smtec = (SystemMainTableExtColumn) super.get(SystemMainTableExtColumn.class, Long.valueOf(smtcId));
//				super.executeUpdate("delete SystemTableSetting stqc where stqc.extendTableColumn=?", new Object[]{smtec});
//				super.executeUpdate("delete UserTableSetting uts where uts.extendTableColumn=?", new Object[]{smtec});
//				super.executeUpdate("delete SystemTableQueryColumn stqc where stqc.extendTableColumn=?", new Object[]{smtec});
//			}
			super.remove(smtc);
		}else{
			throw new ServiceException("ɾ�������ֶβ�����");
		}

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
	
	public SystemMainTable saveEventTableByMainTable(SystemMainTable smt) {
		//begin save smt event
		SystemMainTable smtEvent = new SystemMainTable();
		try {
			//�������޸Ķ���Ҫʱ��ͬ�����������
			smtEvent.setDeployFlag(smt.getDeployFlag());
			smtEvent.setDescn(smt.getDescn());
			smtEvent.setId(null);
			smtEvent.setModule(smt.getModule());
			smtEvent.setPrimaryKeyColumn(null);
			smtEvent.setTableName(smt.getTableName()+"Event");
			smtEvent.setTableCnName(smt.getTableCnName()+"��ʷ");
			smtEvent.setClassName(smt.getClassName()+"Event");
			smtEvent.setUserExtFlag(null);

			super.save(smtEvent);//�����������ʷ��
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//end save smt event
		
		//�����������Ϣ
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt)); //��ȡ����������ֶ�
		c.addOrder(Order.asc("id"));
		List<SystemMainTableColumn> smtcs = c.list();
		//int cicsize = 0;
		for(int i=0; i<smtcs.size(); i++){//����������ֶ�
			//�½�������ʷ����ֶ�
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
		
		addHiddenLongColumn(smtEvent, "������ID", "rootId", null);
		
		//��ȡ�����ֶ�
		Criteria cPk = super.getCriteria(SystemMainTableColumn.class);
		cPk.add(Restrictions.eq("systemMainTable", smtEvent)); //��ȡ����������ֶ�
		cPk.add(Restrictions.ilike("propertyName", "id"));
		SystemMainTableColumn smtcPk = (SystemMainTableColumn) cPk.uniqueResult();
		smtEvent.setPrimaryKeyColumn(smtcPk);
		super.save(smtEvent);//�����������ʷ��
		
		return smtEvent;
	}

	/**
	 * �������������͵��û�����
	 */
	public void saveSystemMainTableDeploy(ServiceItem scid, SystemMainTable smt) {
		SessionFactory sf = super.getHibernateSessionFactory();
		SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sf;
		Dialect dialect = sessionFactoryImpl.getDialect();
		StringBuffer buff = new StringBuffer();
		String dialectName = PropertiesUtil.getProperties("hibernate.dialect");
		
		//��������ʱ�Զ����ӱ���ǰ׺
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
			System.out.println("�����ı��Ѿ����ڣ�ֱ�ӽ����ֶ�����");
		}	
		
		SystemMainTableColumn pkc = smt.getPrimaryKeyColumn();
		String pkcname = pkc.getColumnName();
		
		//��ʼ�������˴��������޸��˺��޸�����
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
					throw new ServiceException(smt.getTableCnName()+"��"+smtc.getPropertyName()+"�ֶε��������Ͳ�����Ϊnull");
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
					System.out.println("���ӵ��ֶ��Ѿ�����, ���������µ��ֶ�");
				}	
			}
			
		}//�����ֶ��������
		
		String className = smt.getClassName();
		int lastDot = className.lastIndexOf(".");
		String sourcePkg = className.substring(0, lastDot);
		String sourceClass = className.substring(lastDot+1);
		
		String targetClassName = null;
		if(sourcePkg.equalsIgnoreCase("com.zsgj.itil.require.entity")){
			targetClassName = "com.zsgj.itil.require.entity.RequireTableTemplate";
		}else{
			targetClassName = className;
		}
		
		this.saveTableEntity(scid, sourcePkg, sourceClass, targetClassName);
		
	}

	private void addCreateUserDate(SystemMainTable smt) {
		String userUniqueColumn = PropertiesUtil.getProperties("system.user.uniquecolumn", "userName");
		
		//���Ӵ������ڵ��ֶ�
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
			PropertyType pt = super.get(PropertyType.class, 1L); //baseObject��������
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); //������
			
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("������");
			smtc.setColumnName("createUser");
			smtc.setPropertyName("createUser");
			smtc.setDescn("��������ʱϵͳ�Զ�����");
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
			PropertyType pt = super.get(PropertyType.class, 6L); //������������
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); //������
			
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("��������");
			smtc.setColumnName("createDate");
			smtc.setPropertyName("createDate");
			smtc.setDescn("��������ʱϵͳ�Զ�����");
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
			PropertyType pt = super.get(PropertyType.class, 1L); //baseObject��������
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); //������
			
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtct.setId(8L); //������
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("����޸���");
			smtc.setColumnName("modifyUser");
			smtc.setPropertyName("modifyUser");
			smtc.setDescn("��������ʱϵͳ�Զ�����");
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
			PropertyType pt = super.get(PropertyType.class, 6L); //������������
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); //������
			
			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtct.setId(8L); //������
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("����޸�����");
			smtc.setColumnName("modifyDate");
			smtc.setPropertyName("modifyDate");
			smtc.setDescn("��������ʱϵͳ�Զ�����");
			smtc.setOrder(count+4);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}
	}
	

	public SystemMainTable saveSystemMainTable(SystemMainTable smt) {

//		*****************************************************************************************************	
		smt.setUserScidFlag(1);    //smt.setUserExtFlag(1);smt.setUserExtFlag(1);
		String tableName = smt.getTableName();
		tableName = StringUtils.capitalize(smt.getTableName());
		if(tableName.length()>21){
			throw new ServiceException("�����Ʋ����Գ���21���ַ�");
		}
		smt.setTableName(tableName);
		
		String configDefaultPkg = PropertiesUtil.getProperties("system.service.defaultpkg", "com.zsgj.itil.require.entity");
		if(smt.getClassName()==null){
			String className = configDefaultPkg+"."+tableName;
			smt.setClassName(className);
		}else{
			//����Ѿ������ˣ������޸ı���
			if(smt.getDeployFlag()!=null&& smt.getDeployFlag().intValue()==1){
				SystemMainTable smtOld = super.get(SystemMainTable.class, smt.getId());
				smt.setTableName(smtOld.getTableName());
				String tableNameOld = smtOld.getTableName();
				if(!tableNameOld.equalsIgnoreCase(tableName)){
					String className = configDefaultPkg+"."+tableName;
					smt.setClassName(className);
				}
			}else { //����֮ǰ�����Ը��ݱ������Զ��޸�������
				String className = configDefaultPkg+"."+tableName;
				smt.setClassName(className);
			}
		}
		
		boolean isInsert = smt.getId()==null;
		if(isInsert){ //�жϱ����Ƿ����
			Criteria cSameName = super.getCriteria(SystemMainTable.class);
			cSameName.add(Restrictions.ilike("tableName", smt.getTableName(), MatchMode.EXACT));
			cSameName.setProjection(Projections.rowCount());
			Integer sameCount = (Integer) cSameName.uniqueResult();
			if(sameCount!=null && sameCount.intValue()>0){
				throw new ServiceException("���������û����Ѿ�����, ���������ģ��ά��<br>ҳ��鿴����������Ϣ");
			}
		}
		
		//begin save smt event
		SystemMainTable smtEvent = new SystemMainTable();
		try {
			//�������޸Ķ���Ҫʱ��ͬ�����������
			BeanUtils.copyProperties(smtEvent, smt);
			smtEvent.setTableName(smt.getTableName()+"Event");
			smtEvent.setTableCnName(smt.getTableCnName()+"��ʷ");
			smtEvent.setClassName(smt.getClassName()+"Event");
			smtEvent.setUserExtFlag(null);
			if(isInsert){ //��������
				smtEvent.setId(null);
				super.save(smtEvent);
			}else{ //����������������������Ҳ���ܻ�û����ʷ���ʱ��Ҫ����
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
			stmcPk.setColumnCnName("�Զ����");
			stmcPk.setColumnName("id");
			stmcPk.setPropertyName("id");
			stmcPk.setDescn("�����������޸ĺ�ɾ��");
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
			//���������ֶ�, ǿ�Ʊ����и��ֶν�name
			smtcName = addTextColumn(smt, "���ƣ��������޸�", "name", 2);
			
			smtcOldApply = addObjectColumn(smt, "���ǰ����", "oldApply", 3);
			
			//�������������ֶ�
			smtcProcess = addHiddenColumn(smt, "��������", "processType", 4);
			//����״̬�ֶ�
			smtcStatus = addHiddenColumn(smt, "״̬", "status", 5);
			
			smtcDelete = addHiddenColumn(smt, "ɾ��״̬", "deleteFlag", 6);
			
			smtcServiceItem = addHiddenColumn(smt, "��������", "serviceItem", 2L, 7);
			//end
//			begin_Ϊ��ʷ��������ֶγ�ʼ�������ֶ�
			SystemMainTableColumn smtcEventPk = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventName = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventOldApply = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventProcess = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventStatus = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventDelete = new SystemMainTableColumn();
			SystemMainTableColumn smtcEventServiceItem = new SystemMainTableColumn();
			try {
				//���������ֶε���ʷ��
				BeanUtils.copyProperties(smtcEventPk, stmcPk);
				smtcEventPk.setSystemMainTable(smtEvent);
				smtcEventPk.setId(null);
				super.save(smtcEventPk);
				smtEvent.setPrimaryKeyColumn(smtcEventPk);
				super.save(smtEvent);
				//end
				
				//begin_������������ʷ����Զ�����ʷ������rootId
				SystemMainTableColumn smtcRoot = new SystemMainTableColumn();
				smtcRoot.setPropertyName("rootId");
				
				PropertyType ptRoot = new PropertyType();
				ptRoot.setId(Long.valueOf(2));	//long
				
				SystemMainTableColumnType typeRoot = new SystemMainTableColumnType();
				typeRoot.setId(Long.valueOf(8));   //������
				
				smtcRoot.setPropertyType(ptRoot);
				smtcRoot.setSystemMainTableColumnType(typeRoot);
				
				smtcRoot.setColumnCnName("������ID");
				smtcRoot.setColumnName("rootId");
				smtcRoot.setSystemMainTable(smtEvent);
				smtcRoot.setDescn("��¼��ʷ��¼���ڵ�������ID");
				smtcRoot.setOrder(2);
				smtcRoot.setIsHiddenItem(1);
				smtcRoot.setIsSearchItem(0);
				smtcRoot.setIsUpdateItem(0);
				smtcRoot.setIsMustInput(1);
				super.save(smtcRoot);
				//end
				
//				������ʷ��������ֶ�
				BeanUtils.copyProperties(smtcEventName, smtcName);
				smtcEventName.setSystemMainTable(smtEvent);
				smtcEventName.setId(null);
				smtcEventName.setOrder(3);
				super.save(smtcEventName);
				//end
				
//				������ʷ�����ʷ����
				BeanUtils.copyProperties(smtcEventOldApply, smtcOldApply);
				smtcEventOldApply.setSystemMainTable(smtEvent);
				smtcEventOldApply.setId(null);
				smtcEventOldApply.setOrder(4);
				super.save(smtcEventOldApply);
				//end
				
//				������ʷ�����������
				BeanUtils.copyProperties(smtcEventProcess, smtcProcess);
				smtcEventProcess.setSystemMainTable(smtEvent);
				smtcEventProcess.setId(null);
				smtcEventProcess.setOrder(5);
				super.save(smtcEventProcess);
				//end
				
				//������ʷ���״̬λ
				BeanUtils.copyProperties(smtcEventStatus, smtcStatus);
				smtcEventStatus.setSystemMainTable(smtEvent);
				smtcEventStatus.setId(null);
				smtcEventStatus.setOrder(6);
				super.save(smtcEventStatus);
				//end
				
				//������ʷ���ɾ��״̬
				BeanUtils.copyProperties(smtcEventDelete, smtcDelete);
				smtcEventDelete.setSystemMainTable(smtEvent);
				smtcEventDelete.setId(null);
				smtcEventDelete.setOrder(7);
				super.save(smtcEventDelete);
				//end
				
				//������ʷ�������������
				BeanUtils.copyProperties(smtcEventServiceItem, smtcServiceItem);
				smtcEventServiceItem.setSystemMainTable(smtEvent);
				smtcEventServiceItem.setId(null);
				smtcEventServiceItem.setOrder(8);
				super.save(smtcEventServiceItem);
				//end
				
				//����rootId���Ժ��ֶ�
				//addObjectColumn(smtEvent, "������ID", "rootId", 9);
			} catch (Exception e) {
				e.printStackTrace();
			}		
//			end
			
			//�������������Ͳݸ�
//			ConfigItemType cit = new ConfigItemType();
//			cit.setName(smt.getTableCnName());
//			cit.setClassName(smt.getClassName());
//			cit.setTableName(smt.getTableName());
//			cit.setSystemMainTable(smt);
//			cit.setPagePanel(null);
//			cit.setDeployFlag(0);
//			super.save(cit);
			
			
		}/*else{
			//����������ֶε���ʷ��
		}*/
		
		return smt;
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
		smtcStatus.setDescn(cnName+"����������ʹ�ã�������ɾ��");
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
		smtcStatus.setDescn(cnName+"����������ʹ�ã�������ɾ��");
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
		smtcStatus.setDescn(cnName+"����������ʹ�ã�������ɾ��");
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
		ptText.setId(3L);//�ַ�����������
		smtcText = new SystemMainTableColumn();
		SystemMainTableColumnType smtcType = new SystemMainTableColumnType();
		smtcType.setId(1L); //�����б�
		smtcText.setPropertyType(ptText);
		smtcText.setSystemMainTableColumnType(smtcType);
		smtcText.setColumnCnName(cnName);
		smtcText.setColumnName(columnName);
		smtcText.setPropertyName(columnName);
		smtcText.setDescn(cnName+"�������ֶΣ��ֶ������������������޸ģ���ERP�˺�����");
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
		smtcObject.setDescn(cnName+"�����ǰ��������");
		smtcObject.setOrder(order);
		smtcObject.setSystemMainTable(smt);
		smtcObject.setForeignTable(smt); //���������
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
//						throw new ServiceException("��ǰ�û����Ѿ��������������޸��ֶ����ƻ�����");
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
			c.add(Restrictions.eq("className", "com.zsgj.info.appframework.metadata.entity.SystemFile"));
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
			throw new ServiceException(columnName+"ΪϵͳԤ���ؼ��֣�����������ֶ���");
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
			
			//�ж��Ƿ��Ѿ�����ͬ�����ֶ�
			Criteria c = super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.ilike("columnName", columnName));
			c.setProjection(Projections.rowCount());
			Integer cncount = (Integer) c.uniqueResult();
			if(cncount>0){
				throw new ServiceException("�ֶ�����"+columnName+"�Ѿ����ڣ��������������");
			}
			
			c = super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.ilike("columnCnName", columnName));
			c.setProjection(Projections.rowCount());
			cncount = (Integer) c.uniqueResult();
			if(cncount>0){
				throw new ServiceException("�ֶ���������"+columnName+"�Ѿ����ڣ��������������");
			}
			

		}
		
		super.save(smtc);
		
		//Ϊ��ǰ�ֶ�������Ӧ����ʷ���ֶ�
		//begin ��ȡ����
		String eventClassName = smt.getClassName()+"Event";
		//Class clazz = this.getClass(eventClassName);
		Criteria cSmtEvent = super.getCriteria(SystemMainTable.class);
		cSmtEvent.add(Restrictions.eq("className", eventClassName));
		SystemMainTable smtEvent = null;
		List<SystemMainTable> smtEvents = cSmtEvent.list();
		if(!smtEvents.isEmpty()){
			smtEvent = smtEvents.iterator().next();
		}
		
		if(isInsert){ //������ʷ���ֶ�
			SystemMainTableColumn smtcEvent = new SystemMainTableColumn();
			try {
				BeanUtils.copyProperties(smtcEvent, smtc); //������ʷ���ֶ�
				smtcEvent.setSystemMainTable(smtEvent);
				smtcEvent.setId(null);
				super.save(smtcEvent);
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}else{ //�����޸������ֶ�ʱ��ͬ���������ֶε������޸�COPY�������ֶ�
			Criteria c= super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smtEvent));
			//����ʹ��columnName���ܳ���nullָ��
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
	public void setSystemColumnService(SystemColumnService systemColumnService) {
		this.systemColumnService = systemColumnService;
	}

	public void setSystemMainColumnService(
			SystemMainColumnService systemMainColumnService) {
		this.systemMainColumnService = systemMainColumnService;
	}

	public void setSystemMainTableService(
			SystemMainTableService systemMainTableService) {
		this.systemMainTableService = systemMainTableService;
	}
	
	
	public Page findServiceItemUserTables(String serviceItemName,
			String tableName, int pageNo, int pageSize) {
		
		Criteria c = super.createCriteria(ServiceItemUserTable.class);
		c.createAlias("this.serviceItem", "sid").setFetchMode("sid", FetchMode.JOIN);
		c.createAlias("this.systemMainTable", "smt").setFetchMode("smt", FetchMode.JOIN);
		
		if(StringUtils.isNotBlank(serviceItemName)){
			c.add(Restrictions.ilike("sid.name", serviceItemName));
		}
		
		if(StringUtils.isNotBlank(tableName)){
//			c.add(Restrictions.or(
//					Restrictions.ilike("smt.tableName", tableName), 
//					Restrictions.ilike("smt.tableCnName", tableName))
//			);
			
//			Criterion d1 = Restrictions.ilike("smt.tableName", tableName);
//			Criterion d2 = Restrictions.ilike("smt.tableCnName", tableName);
//			c.add(Restrictions.or(d1, d2));
			
			Disjunction disj = Restrictions.disjunction();
			disj.add(Restrictions.ilike("smt.tableName", tableName));
			disj.add(Restrictions.ilike("smt.tableCnName", tableName));
			c.add(disj);
			
			//Conjunction conj = Restrictions.conjunction();
			//conj.add(Restrictions.ilike("smt.tableName", tableName));
			//conj.add(Restrictions.ilike("smt.tableCnName", tableName));
		}
		c.addOrder(Order.desc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public ServiceItemUserTable findServiceItemUserTableById(String id) {
		ServiceItemUserTable result = null;
		if(StringUtils.isNotBlank(id)){
		Criteria c = super.createCriteria(ServiceItemUserTable.class);
		c.setFetchMode("this.serviceItem", FetchMode.JOIN);
		c.setFetchMode("this.systemMainTable", FetchMode.JOIN);
		c.add(Restrictions.eq("id", Long.valueOf(id)));
		result = (ServiceItemUserTable) c.uniqueResult();
		}
		return result;
	}

	public void setCustomerTableService(CustomerTableService customerTableService) {
		this.customerTableService = customerTableService;
	}
	
	public ServiceItemUserTable findServiceItemUserTableByServiceItem(
			ServiceItem serviceItem) {
		ServiceItemUserTable result = null;
		if(serviceItem!=null){
		Criteria c = super.createCriteria(ServiceItemUserTable.class);
		c.setFetchMode("this.serviceItem", FetchMode.JOIN);
		c.setFetchMode("this.systemMainTable", FetchMode.JOIN);
		c.add(Restrictions.eq("serviceItem", serviceItem));
		result = (ServiceItemUserTable) c.uniqueResult();
		}
		return result;
	}

	public ServiceItem findServiceItemByMainTable(SystemMainTable smt) {
		if(smt==null){
			return null;
		}
		Criteria criteria = super.getCriteria(ServiceItemUserTable.class);
		criteria.setFetchMode("serviceItem", FetchMode.JOIN);
		criteria.add(Restrictions.eq("systemMainTable", smt));
		ServiceItemUserTable reslut = (ServiceItemUserTable) criteria.uniqueResult();
		return reslut.getServiceItem();
	}
}
