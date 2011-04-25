package com.digitalchina.itil.config.service.impl;

import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.PropertyType;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableIdBuilder;
import com.digitalchina.info.appframework.metadata.entity.SystemTableSetting;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.PathUtil;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.info.framework.util.asm.render.FreemarkerRender;
import com.digitalchina.info.framework.util.asm.render.RenderClass;
import com.digitalchina.info.framework.util.asm.render.RenderProperty;
import com.digitalchina.info.framework.util.asm.render.Templates;
import com.digitalchina.info.framework.util.code.RuntimeCode;
import com.digitalchina.itil.config.entity.CIRelationShip;
import com.digitalchina.itil.config.entity.CIRelationShipGrade;
import com.digitalchina.itil.config.entity.CIRelationShipType;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.config.entity.ConfigItemExtendInfo;
import com.digitalchina.itil.config.entity.ConfigItemFinanceInfo;
import com.digitalchina.itil.config.entity.ConfigItemType;
import com.digitalchina.itil.config.service.CustomerTableService;
import com.digitalchina.itil.service.entity.ServiceItem;

public class CustomerTableServiceImpl extends BaseDao implements CustomerTableService {
	private SystemColumnService systemColumnService;
	private SystemMainColumnService systemMainColumnService;
	private SystemMainTableService systemMainTableService;
	String FSP = System.getProperty("file.separator");
	String LSP = "\n";

	public List<PagePanel> findAllBasePanels() {
		Criteria c = super.getCriteria(PagePanel.class);
		c.add(Restrictions.eq("groupFlag", Integer.valueOf(0)));
		List list = c.list();
		return list;
	}

	public List<PagePanel> findAllGroupPanels() {
		Criteria c = super.getCriteria(PagePanel.class);
		c.add(Restrictions.eq("groupFlag", Integer.valueOf(1)));
		List list = c.list();
		return list;
	}

	public List<PagePanel> findAllPagePanels() {
		Criteria c = super.getCriteria(PagePanel.class);

		List list = c.list();
		return list;
	}

	public ConfigItemType findConfigItemTypeByTable(SystemMainTable smt) {
		ConfigItemType result = null;
		Criteria c = super.getCriteria(ConfigItemType.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		List list = c.list();
		if (!list.isEmpty()) {
			result = (ConfigItemType) list.iterator().next();
		}
		return result;
	}

	public SystemMainTable findCustomerTableById(String tableId) {
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.eq("id", Long.valueOf(tableId)));
		SystemMainTable smt = (SystemMainTable) c.uniqueResult();
		return smt;
	}

	public List<ConfigItemType> findAllTopConfigItems() {
		Criteria c = super.getCriteria(ConfigItemType.class);
		c.add(Restrictions.isNull("parentConfigItemType"));
		c.add(Restrictions.ne("deployFlag", Integer.valueOf(0)));
		c.addOrder(Order.asc("name"));
		List<ConfigItemType> list = c.list();
		return list;
	}

	private List<Property> getPropertyByPersistentClass(PersistentClass model) {
		List<Property> list = new ArrayList<Property>();
		if (model != null) {
			Iterator<Property> iter = model.getPropertyIterator();
			while (iter.hasNext()) {
				Property item = iter.next();
				list.add(item);
			}
		}
		return list;
	}

	public String exportAllConfigItemForRelation(String fileRootPath) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 设置标题上下居中

		HSSFSheet sheet = wb.createSheet();
		wb.setSheetName(0, "配置项数据", HSSFWorkbook.ENCODING_UTF_16);

		// 建立标题行，0行
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 400);

		// 配置项编号
		HSSFCell cell = row.createCell((short) 0);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("配置项编号");

		// 配置项名称
		cell = row.createCell((short) 1);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("配置项名称");

		// 配置项类型
		cell = row.createCell((short) 2);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("配置项类型");

		// 配置项cisn
		cell = row.createCell((short) 3);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("配置项cisn");

		// 主资产号
		cell = row.createCell((short) 4);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("主资产号");

		// 次资产号
		cell = row.createCell((short) 5);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("次资产号");

		// 遍历所有的配置项类型
		Criteria ccitype = super.getCriteria(ConfigItemType.class);
		ccitype.add(Restrictions.eq("deployFlag", Integer.valueOf(1)));
		List<ConfigItemType> citypes = ccitype.list();

		int i = 1;

		for (ConfigItemType citype : citypes) {
			SystemMainTable smt = citype.getSystemMainTable();

			// 去当前类型的配置项数据
			Criteria c = super.getCriteria(ConfigItem.class);
			c.add(Restrictions.eq("configItemType", citype));
			c.addOrder(Order.asc("id"));
			List<ConfigItem> list = c.list();

			for (ConfigItem cItem : list) {
				Long id = cItem.getId();
				String name = cItem.getName();

				c = super.getCriteria(ConfigItemExtendInfo.class);
				c.add(Restrictions.eq("configItem.id", id));
				ConfigItemExtendInfo ciei = (ConfigItemExtendInfo) c.uniqueResult();
				SystemMainTable extTable = ciei.getSystemMainTable();
				String extClass = extTable.getClassName();
				Class extClazz = this.getClass(extClass);
				Long extId = ciei.getExtendDataId();

				Object extObject = super.getObject(extClazz, extId);
				BeanWrapper bw = new BeanWrapperImpl(extObject);
				String cisn = (String) bw.getPropertyValue("cisn");

				// 建立数据的行
				HSSFRow rowData = sheet.createRow(i++);
				rowData.setHeight((short) 400);

				// 配置项编号
				HSSFCell cellData = rowData.createCell((short) 0);
				cellData.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellData.setCellStyle(cellStyle);
				cellData.setCellValue(id);

				// 配置项名称
				cellData = rowData.createCell((short) 1);
				cellData.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellData.setCellStyle(cellStyle);
				cellData.setCellValue(name);

				// 配置项类型
				cellData = rowData.createCell((short) 2);
				cellData.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellData.setCellStyle(cellStyle);
				cellData.setCellValue(citype.getName());

				// 配置项cisn
				cellData = rowData.createCell((short) 3);
				cellData.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellData.setCellStyle(cellStyle);
				cellData.setCellValue(cisn);

				c = super.getCriteria(ConfigItemFinanceInfo.class);
				c.add(Restrictions.eq("configItem.id", id));
				ConfigItemFinanceInfo cif = (ConfigItemFinanceInfo) c.uniqueResult();
				String mainAssetCode = cif.getMainAssetCode();
				String attachAssetCode = cif.getAttachAssetCode();

				// 配置项mainAssetCode
				cellData = rowData.createCell((short) 4);
				cellData.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellData.setCellStyle(cellStyle);
				cellData.setCellValue(mainAssetCode);

				// 配置项attachAssetCode
				cellData = rowData.createCell((short) 5);
				cellData.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellData.setCellStyle(cellStyle);
				cellData.setCellValue(attachAssetCode);
			}
		}
		String excelFileName = null;
		String excelFullFileName = null;
		final String FSP = System.getProperty("file.separator");
		try {
			// 获得Excel文件的真实路径
			excelFileName = "citypeData_all_" + System.currentTimeMillis() + ".xls";
			excelFullFileName = fileRootPath + FSP + excelFileName;
			FileOutputStream fileout = new FileOutputStream(excelFullFileName);
			wb.write(fileout);
			fileout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelFullFileName;
	}
	
	public void saveConfigItemExcel(HSSFWorkbook wb, SystemMainTable smt) {
		HSSFSheet sheet = wb.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			try {
				HSSFRow row = sheet.getRow(i);
				List<SystemMainTableColumn> cismtc=getConfigItemColumns();
				ConfigItem ci=new ConfigItem();
				ci.setStatus(ConfigItem.VALID_STATUS);
				short count=0;
				for (int j = 0; j < cismtc.size(); j++) {
					HSSFCell cell = row.getCell(count);
					String cellValue=getCellStringValue(cell);
					setPropertyFromExcelValue(cismtc.get(j), ci, cellValue);
					count++;
				}
				List<SystemMainTableColumn> cifmtc=getConfigItemFinanceInfoColumns();
				ConfigItemFinanceInfo cif=new ConfigItemFinanceInfo();
				for (int j = 0; j < cifmtc.size(); j++) {
					HSSFCell cell = row.getCell((short)count);
					String cellValue=getCellStringValue(cell);
					setPropertyFromExcelValue(cifmtc.get(j), cif, cellValue);
					count++;
				}
				cif.setConfigItem(ci);
				List<SystemMainTableColumn> ciemtc=getConfigItemExtendColumns(smt);
				Object cie=Class.forName(smt.getClassName()).newInstance();
				for (int j = 0; j < ciemtc.size(); j++) {
					HSSFCell cell = row.getCell((short)count);
					String cellValue=getCellStringValue(cell);
					setPropertyFromExcelValue(ciemtc.get(j), cie, cellValue);
					count++;
				}
				PropertyUtils.setProperty(cie, "cisn", PropertyUtils.getProperty(ci, "cisn"));
				ConfigItem oldci=(ConfigItem) createCriteria(ConfigItem.class)
									.add(Restrictions.eq("cisn", ci.getCisn()))
									.add(Restrictions.eq("status", ConfigItem.VALID_STATUS))
									.uniqueResult();
				if(oldci!=null){
					ci.setId(oldci.getId());
					ci.setCreateUser(oldci.getCreateUser());
					ci.setCreateDate(oldci.getCreateDate());
					ci.setModifyUser(UserContext.getUserInfo());
					ci.setModifyDate(new Date());
					ConfigItemFinanceInfo oldcif= findUniqueBy(ConfigItemFinanceInfo.class, "configItem", oldci);
					cif.setId(oldcif.getId());
					ConfigItemExtendInfo cieinfo=findUniqueBy(ConfigItemExtendInfo.class, "configItem", oldci);
					Object oldcie=getObject(Class.forName(smt.getClassName()), cieinfo.getExtendDataId());
					PropertyUtils.setProperty(cie, "id", PropertyUtils.getProperty(oldcie, "id"));
					save(ci);
					save(cif);
					save(cie);
					System.out.println("=================第" + i + "行更新成功！=================================");
				}else{
					ci.setCreateUser(UserContext.getUserInfo());
					ci.setCreateDate(new Date());
					ci=(ConfigItem) save(ci);
					save(cif);
					cie=save(cie);
					ConfigItemExtendInfo cieinfo=new ConfigItemExtendInfo();
					cieinfo.setConfigItem(ci);
					cieinfo.setSystemMainTable(smt);
					cieinfo.setExtendDataId((Long) PropertyUtils.getProperty(cie, "id"));
					save(cieinfo);
					System.out.println("=================第" + i + "行新建成功！=================================");
				}
				
				SystemMainTableIdBuilder builder=findUniqueBy(SystemMainTableIdBuilder.class, "systemMainTable", smt);
				String cisn=(String) createCriteria(Class.forName(smt.getClassName()))
							.setProjection(Projections.max("cisn"))
							.uniqueResult();
				builder.setLatestValue(cisn);
				save(builder);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("=================第" + i + "行导入失败！=================================");
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	public void saveCIRExcel(HSSFWorkbook wb) {
		HSSFSheet sheet = wb.getSheetAt(0);
		for (int i =1; i <=sheet.getLastRowNum(); i++) {
			try {
				HSSFRow row = sheet.getRow(i);
				short num = 0;
				String parentType=getCellStringValue(row.getCell(num++));//父类型
				String parentCode=getCellStringValue(row.getCell(num++));//父编号
				String childType=getCellStringValue(row.getCell(num++));//子类型
				String childCode=getCellStringValue(row.getCell(num++));//子编号
				String relationShipType=getCellStringValue(row.getCell(num++));//关系类型
				String relationShipGrade=getCellStringValue(row.getCell(num++));//关系紧密程度
				String attachQuotiety=getCellStringValue(row.getCell(num++));//归集系数
				String atechnoInfo=getCellStringValue(row.getCell(num++));//A端技术信息
				String btechnoInfo=getCellStringValue(row.getCell(num++));//B端技术信息
				String otherInfo=getCellStringValue(row.getCell(num++));//其他信息
				if(childType.length()==0||childCode.length()==0||
				   parentType.length()==0||parentCode.length()==0){
					System.out.println("=================导入到第 " + i+ " 行=================失败================缺少必要数据");
					continue;
				}
				CIRelationShip cir=new CIRelationShip();
				if(parentType.length()!=0&&parentCode.length()!=0){
					if(parentType.equals("服务项")){
						ServiceItem si=(ServiceItem) createCriteria(ServiceItem.class)
										.add(Restrictions.eq("serviceItemCode", parentCode))
										.add(Restrictions.eq("deleteFlag", ServiceItem.DELETE_FALSE))
										.uniqueResult();
						if(si==null){
							System.out.println("=================导入到第 " + i+ " 行=================失败================父服务项不存在");
							continue;
						}
						cir.setParentServiceItemCode(si.getServiceItemCode());
						cir.setParentServiceItemType(si.getServiceItemType());
					}else{
						ConfigItem ci=(ConfigItem)createCriteria(ConfigItem.class)
						               .add(Restrictions.eq("cisn", parentCode))
						               .add(Restrictions.eq("status", ConfigItem.VALID_STATUS))
						               .uniqueResult();
						if(ci==null){
							System.out.println("=================导入到第 " + i+ " 行=================失败================父配置项不存在");
							continue;
						}
						cir.setParentConfigItemCode(ci.getCisn());
						cir.setParentConfigItemType(ci.getConfigItemType());
					}
					
				}
				if(childType.equals("服务项")){
					ServiceItem si=(ServiceItem) createCriteria(ServiceItem.class)
									.add(Restrictions.eq("serviceItemCode", childCode))
									.add(Restrictions.eq("deleteFlag", ServiceItem.DELETE_FALSE))
									.uniqueResult();
					if(si==null){
						System.out.println("=================导入到第 " + i+ " 行=================失败================子服务项不存在");
						continue;
					}
					cir.setChildServiceItemCode(si.getServiceItemCode());
					cir.setChildServiceItemType(si.getServiceItemType());
				}else{
					ConfigItem ci=(ConfigItem)createCriteria(ConfigItem.class)
					               .add(Restrictions.eq("cisn", childCode))
					               .add(Restrictions.eq("status", ConfigItem.VALID_STATUS))
					               .uniqueResult();
					if(ci==null){
						System.out.println("=================导入到第 " + i+ " 行=================失败================子配置项不存在");
						continue;
					}
					cir.setChildConfigItemCode(ci.getCisn());
					cir.setChildConfigItemType(ci.getConfigItemType());
				}
				Criteria c=createCriteria(CIRelationShip.class)
				.add(Restrictions.eq("status", CIRelationShip.VALID_STATUS));
				if(cir.getParentServiceItemCode()!=null)
					c.add(Restrictions.eq("parentServiceItemCode", cir.getParentServiceItemCode()));
				else if(cir.getParentConfigItemCode()!=null)
					c.add(Restrictions.eq("parentConfigItemCode", cir.getParentConfigItemCode()));
				else if(cir.getParentServiceItemCode()==null&&cir.getParentConfigItemCode()==null){
					c.add(Restrictions.isNull("parentServiceItemCode"));
					c.add(Restrictions.isNull("parentConfigItemCode"));
				}
				if(cir.getChildServiceItemCode()!=null)
					c.add(Restrictions.eq("childServiceItemCode", cir.getChildServiceItemCode()));
				else
					c.add(Restrictions.eq("childConfigItemCode", cir.getChildConfigItemCode()));
				
				CIRelationShip exist=(CIRelationShip) c.uniqueResult();
				
				String update="新建";
				if(exist!=null){
					cir=exist;
					cir.setModifyUser(UserContext.getUserInfo());
					cir.setModifyDate(new Date());
					update="更新";
				}else{
					cir.setStatus(CIRelationShip.VALID_STATUS);
					cir.setCreateUser(UserContext.getUserInfo());
					cir.setCreateDate(new Date());
				}
				if(relationShipType.length()!=0){
					cir.setRelationShipType(findUniqueBy(CIRelationShipType.class, "name", relationShipType));
				}
				if(relationShipGrade.length()!=0){
					cir.setRelationShipGrade(findUniqueBy(CIRelationShipGrade.class, "name", relationShipGrade));
				}
				if(attachQuotiety.length()!=0){
					cir.setAttachQuotiety(Double.valueOf(attachQuotiety));
				}
				if(atechnoInfo.length()!=0){
					cir.setAtechnoInfo(atechnoInfo);
				}
				if(btechnoInfo.length()!=0){
					cir.setBtechnoInfo(btechnoInfo);
				}
				if(otherInfo.length()!=0){
					cir.setOtherInfo(otherInfo);
				}
				save(cir);
				if(i%50==0){
					flush();
					clear();
				}
				System.out.println("=================导入到第 " + i+ " 行=================成功================"+update);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("=================导入到第 " + i+ " 行=================失败================异常"+ex.getMessage());
			}
		}
	}

	private String getCellStringValue(HSSFCell cell) {
		String cellValue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				cellValue = cell.getRichStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
					cellValue = sf.format(cell.getDateCellValue());

				} else {
					double doubleValue = cell.getNumericCellValue();
					DecimalFormat df = new DecimalFormat("#.##");
					String dfValue = df.format(Double.valueOf(doubleValue));
					cellValue = dfValue;
				}
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				try {
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							cellValue = sf.format(cell.getDateCellValue());
						} else {
							String temp = String.valueOf(cell.getNumericCellValue());
							if (temp.equals("NaN"))
								cellValue = cell.getRichStringCellValue().toString();
							else {
								DecimalFormat df = new DecimalFormat("#.##");
								cellValue = df.format(cell.getNumericCellValue());
							}
						}
					} catch (Exception e) {
					}
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				break;
			}
		}
		return cellValue.trim();
	}
	/**
	 * ConfigItem所有字段
	 * @Methods Name getConfigItemColumns
	 * @Create In Feb 2, 2010 By duxh
	 * @return 
	 * @Return List<SystemMainTableColumn>
	 */
	private List<SystemMainTableColumn> getConfigItemColumns(){
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.createAlias("systemMainTable", "smt");
		c.createAlias("systemMainTableColumnType", "smtct");
		c.add(Restrictions.ne("smtct.columnTypeName", "hidden"));
		c.add(Restrictions.eq("smt.className", "com.digitalchina.itil.config.entity.ConfigItem"));
		return c.list();
	}
	
	private List<SystemMainTableColumn> getConfigItemFinanceInfoColumns(){
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.createAlias("systemMainTable", "smt");
		c.createAlias("systemMainTableColumnType", "smtct");
		c.add(Restrictions.ne("smtct.columnTypeName", "hidden"));
		c.add(Restrictions.eq("smt.className","com.digitalchina.itil.config.entity.ConfigItemFinanceInfo"));
		return c.list();
	}
	
	private List<SystemMainTableColumn> getConfigItemExtendColumns(SystemMainTable smt){
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.createAlias("systemMainTableColumnType", "smtct");
		c.add(Restrictions.ne("smtct.columnTypeName", "hidden"));
		return c.list();
	}
	public HSSFWorkbook getConfigItemExcel(ConfigItemType citype) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
			HSSFSheet sheet = wb.createSheet();
			wb.setSheetName(0, citype.getName());
			sheet.setDefaultColumnWidth((short) 15);
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 400);
			// 配置项基础信息
			List<SystemMainTableColumn> ciColumns = getConfigItemColumns();
		
			for (int i = 0; i < ciColumns.size(); i++) {
				SystemMainTableColumn smtc = ciColumns.get(i);
				String columnCnName = smtc.getColumnCnName();
				HSSFCell cell = row.createCell((short) i);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new HSSFRichTextString("配置项." + columnCnName));
			}
			// 财务
			List<SystemMainTableColumn> cifColumns=getConfigItemFinanceInfoColumns();
			for (int i = 0; i < cifColumns.size(); i++) {
				SystemMainTableColumn smtc = cifColumns.get(i);
				String columnCnName = smtc.getColumnCnName();
				HSSFCell cell = row.createCell((short) (i + ciColumns.size()));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new HSSFRichTextString("财务." + columnCnName));
			}
		
			
			List<SystemMainTableColumn> cieColumns = getConfigItemExtendColumns(citype.getSystemMainTable());
			for (int i = 0; i < cieColumns.size(); i++) {
				SystemMainTableColumn smtc = cieColumns.get(i);
				String columnCnName = smtc.getColumnCnName();
				HSSFCell cell = row.createCell((short) (i + ciColumns.size() + cifColumns.size()));
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new HSSFRichTextString(citype.getName() + "." + columnCnName));
			}
		
			List<ConfigItem> configItems =createCriteria(ConfigItem.class)
								.add(Restrictions.eq("configItemType", citype))
								.add(Restrictions.eq("status", ConfigItem.VALID_STATUS))
								.addOrder(Order.asc("cisn"))
								.list();
			if (!configItems.isEmpty()) {
				List<ConfigItemFinanceInfo> configItemFinanceInfos = createCriteria(ConfigItemFinanceInfo.class).add(
						Restrictions.in("configItem", configItems)).list();
				List<ConfigItemExtendInfo> extendInfo = createCriteria(ConfigItemExtendInfo.class).add(
						Restrictions.in("configItem", configItems)).setFetchMode("systemMainTable", FetchMode.JOIN)
						.list();
				List<Object> extendObject = new ArrayList<Object>();
				for (ConfigItemExtendInfo info : extendInfo) {
						extendObject.add(get(Class.forName(info.getSystemMainTable().getClassName()), info
								.getExtendDataId()));
				}
				for (int ciCount = 0; ciCount < configItems.size(); ciCount++) {
					ConfigItem ci = configItems.get(ciCount);
					HSSFRow nextRow = sheet.createRow(ciCount + 1);
					nextRow.setHeight((short) 400);
					for (short cicolumnCount = 0; cicolumnCount < ciColumns.size(); cicolumnCount++) {
						SystemMainTableColumn smtc = ciColumns.get(cicolumnCount);
						HSSFCell cell = nextRow.createCell(cicolumnCount);
						cell.setCellStyle(cellStyle);
						String value=getPropertyExcelValue(smtc, ci);
						cell.setCellValue(new HSSFRichTextString(value));
					}
					ConfigItemFinanceInfo cif = null;
					for (ConfigItemFinanceInfo info : configItemFinanceInfos) {
						if (info.getConfigItem().getId().compareTo(ci.getId()) == 0) {
							cif = info;
							break;
						}
					}
					if (cif != null) {
						for (short cifcolumnCount = 0; cifcolumnCount < cifColumns.size(); cifcolumnCount++) {
							SystemMainTableColumn smtc = cifColumns.get(cifcolumnCount);
							HSSFCell cell = nextRow.createCell((short)(cifcolumnCount+ciColumns.size()));
							cell.setCellStyle(cellStyle);
							String value=getPropertyExcelValue(smtc, cif);
							cell.setCellValue(new HSSFRichTextString(value));
					}
					Object extend = null;
					for (ConfigItemExtendInfo info : extendInfo) {
						if (info.getConfigItem().getId().compareTo(ci.getId()) == 0) {
							for (Object obj : extendObject) {
								if (info.getExtendDataId().toString().equals(BeanUtils.getProperty(obj, "id"))) {
												extend = obj;
												break;
											}
										}
									}
								}
						if (extend != null) {
							for (short cieColumnCount = 0; cieColumnCount < cieColumns.size(); cieColumnCount++) {
								SystemMainTableColumn smtc = cieColumns.get(cieColumnCount);
								HSSFCell cell = nextRow.createCell((short) (cieColumnCount + ciColumns.size() + cifColumns.size()));
								cell.setCellStyle(cellStyle);
								String value=getPropertyExcelValue(smtc, extend);
								cell.setCellValue(new HSSFRichTextString(value));
							}
						}
					}
				}
			}
			return wb;
		} catch (Exception e) {
			throw new ServiceException();
		}
	}
	/**
	 * 将excel中的值赋到指定对象中的对应属性中
	 * @Methods Name SetPropertyFromExcelValue
	 * @Create In Feb 2, 2010 By duxh
	 * @param smtc
	 * @Return Object
	 */
	private void setPropertyFromExcelValue(SystemMainTableColumn smtc,Object obj,String excelValue){
		try {
			if(excelValue.trim().length()==0)
				return;
			String propertyName = smtc.getPropertyName();
			PropertyType pt = smtc.getPropertyType();
			String ptName = pt.getPropertyTypeName();
			String columnTypeName=smtc.getSystemMainTableColumnType().getColumnTypeName();
			Object value=null;
			if(ptName.equalsIgnoreCase("BaseObject")){
				SystemMainTable ftable = smtc.getForeignTable();
				String ftableClass = ftable.getClassName();
				String foreignPropertyName=smtc.getForeignTableValueColumn().getPropertyName();
				String foreignPropertyType=smtc.getForeignTableValueColumn().getPropertyType().getPropertyTypeName();
			    if(ftableClass.equalsIgnoreCase("com.digitalchina.info.framework.security.entity.UserInfo")){
			    	UserInfo user=findUniqueBy(UserInfo.class,"itcode", excelValue.toUpperCase());
			    	value=user;
				}
				else if(ftableClass.equalsIgnoreCase("com.digitalchina.info.framework.security.entity.Department")){
					Department department=findUniqueBy(Department.class,"departCode",Long.parseLong(excelValue));
					value=department;
				}
				else{
					Object temp=null;
					if(foreignPropertyType.equalsIgnoreCase("Long"))
						temp=Long.valueOf(excelValue);
					else if(foreignPropertyType.equalsIgnoreCase("Integer"))
						temp=Integer.valueOf(excelValue);
					else if(foreignPropertyType.equalsIgnoreCase("Double")){
						temp=Double.valueOf(excelValue);
					}
					else if(foreignPropertyType.equalsIgnoreCase("Date")){
						SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
						temp=sf.parse(excelValue);
					}else
						temp=excelValue;
					value=findUniqueBy(Class.forName(ftableClass), foreignPropertyName, temp);
				}
			}else if (ptName.equalsIgnoreCase("Integer")) {
				if (columnTypeName.equalsIgnoreCase("yesNoSelect")) {
					if(excelValue.equals("是"))
						value = Integer.valueOf("1");
					else
						value = Integer.valueOf("0");
				}
				else
					value=Integer.valueOf(excelValue);
			} else if (ptName.equalsIgnoreCase("Double")) {
				value = Double.valueOf(excelValue);
			}else if (ptName.equalsIgnoreCase("Date")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				value = df.parse(excelValue);
			} else {
				value =excelValue ;
			} 
			if(value!=null)
				PropertyUtils.setProperty(obj, propertyName, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	
	/**
	 * 获取属性对应的excel值
	 * @Methods Name getPropertyExcelValue
	 * @Create In Feb 2, 2010 By duxh
	 * @param smtc
	 * @param obj
	 * @Return String
	 */
	private String getPropertyExcelValue(SystemMainTableColumn smtc,Object obj){
		try {
			String propertyName = smtc.getPropertyName();
			PropertyType pt = smtc.getPropertyType();
			String ptName = pt.getPropertyTypeName();
			String columnTypeName=smtc.getSystemMainTableColumnType().getColumnTypeName();
			String value = "";
			Object temp=null;
			if(ptName.equalsIgnoreCase("BaseObject")){
				SystemMainTable ftable = smtc.getForeignTable();
				String ftableClass = ftable.getClassName();
				String foreignPropertyName=smtc.getForeignTableValueColumn().getPropertyName();
		        if(ftableClass.equalsIgnoreCase("com.digitalchina.info.framework.security.entity.UserInfo")){
					temp=PropertyUtils.getProperty(obj, propertyName);
					if(temp!=null){
						String itcode=((UserInfo)temp).getItcode();
						if(itcode!=null)
							value=itcode.toLowerCase();
					}
				}
				else if(ftableClass.equalsIgnoreCase("com.digitalchina.info.framework.security.entity.Department")){
					temp=PropertyUtils.getProperty(obj, propertyName);
					if(temp!=null){
						Long code=((Department)temp).getDepartCode();
						if(code!=null)
							value=code.toString();
					}
				}
				else{
					temp=PropertyUtils.getProperty(obj, propertyName);
					if(temp!=null){
						Object valueTemp=PropertyUtils.getProperty(temp,foreignPropertyName);
						if(valueTemp!=null){
							value=valueTemp.toString();
						}
					}
				}
			} else if (ptName.equalsIgnoreCase("Integer")) {
				temp=PropertyUtils.getProperty(obj,propertyName);
				if(temp!=null){
					if (columnTypeName.equalsIgnoreCase("yesNoSelect")) {
							if(((Integer)temp).compareTo(1)==0)
								value = "是";
							else
								value = "否";
					}else{
						value=temp.toString();
					}
				}
			} else if (ptName.equalsIgnoreCase("Double")) {
				temp = PropertyUtils.getProperty(obj, propertyName);
				//DecimalFormat df = new DecimalFormat("#.##");
				if (temp != null)
					//value = df.format((Double) temp);
					value = temp.toString();
			} else if (ptName.equalsIgnoreCase("Date")) {
				temp = PropertyUtils.getProperty(obj, propertyName);
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				if (temp != null)
					value = df.format((Date) temp);
			} else {
				value = BeanUtils.getProperty(obj, propertyName);
			}
			if (value == null)
				value="";
			return value;
		}catch (Exception e) {
			throw new ServiceException();
		}
	}
	public HSSFWorkbook getConfigItemTemplateExcel(SystemMainTable citypeTable) {
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 设置标题上下居中
			HSSFSheet sheet = wb.createSheet();
			sheet.setDefaultColumnWidth((short) 15);
			wb.setSheetName(0, citypeTable.getTableCnName());

			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 400);
			// 配置项基础信息
			List<SystemMainTableColumn> smtcs = getConfigItemColumns();
			int count = 0;
			for (int i = 0; i < smtcs.size(); i++) {
				SystemMainTableColumn smtc = smtcs.get(i);
				String columnCnName = smtc.getColumnCnName();
				HSSFCell cell = row.createCell((short) count);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new HSSFRichTextString("配置项." + columnCnName));
				count++;
			}
			// 财务
			smtcs = getConfigItemFinanceInfoColumns();
			
			for (int i = 0; i < smtcs.size(); i++) {
				SystemMainTableColumn smtc = smtcs.get(i);
				String columnCnName = smtc.getColumnCnName();
				HSSFCell cell = row.createCell((short) count);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new HSSFRichTextString("财务." + columnCnName));
				count++;
			}

			// 生成配置项类型特有的字段
			smtcs = getConfigItemExtendColumns(citypeTable);
			for (int i = 0; i < smtcs.size(); i++) {
				SystemMainTableColumn smtc = smtcs.get(i);
				String columnCnName = smtc.getColumnCnName();
				HSSFCell cell = row.createCell((short)count);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new HSSFRichTextString(citypeTable.getTableCnName() + "." + columnCnName));
				count++;
			}
			return wb;
		} catch (Exception e) {
			throw new ServiceException();
		}

	}


	// 是否需要增加新的属性进入持久化类
	private boolean needAddProperty2PersistClass(PersistentClass model, Column column) {
		boolean result = true; // 默认需要增加
		Iterator<Property> iter = model.getPropertyIterator();
		while (iter.hasNext()) {
			Property item = iter.next();
			String propertyName = item.getName();
			Object value = item.getValue();
			if (!column.getPropertyName().equalsIgnoreCase("id")) {
				if (column.getPropertyName().equalsIgnoreCase(propertyName)) { // 只有持久化类里有了，就不再加入
					result = false;
					return result;
				}
			}

		}
		return result;
	}

	/**
	 * 利用后台新增的主表生成实体和mapping更新sessionFactory 主要需要进一步完事
	 */
	public void saveTableEntity(String sourcePkg, String sourceClassName, String targetClass /*
																								 * Class
																								 * targetClass
																								 */) {
		SessionFactory ssf = super.getHibernateSessionFactory();
		Configuration config = super.getConfiguration();
		String newClassName = sourcePkg + "." + sourceClassName;
		// 通过类全路径获取持久化类
		PersistentClass model = config.getClassMapping(newClassName);
		if (model == null) {
			// 第一次发布主表，持久化类不存在，故新建持久化类
			String tablePrefix = PropertiesUtil.getProperties("system.config.citable.prefix", "itil_ci_");
			SystemMainTable smt = genPersistentClass(model, sourcePkg, sourceClassName, targetClass, tablePrefix);
			// 生成配置项类型所需的面板，与框架的生成持久化类方法分离
			this.saveConfigItemTablePanel(smt, 1);

		} else {
			Criteria c = super.getCriteria(SystemMainTable.class);
			c.add(Restrictions.ilike("tableName", sourceClassName, MatchMode.EXACT));
			List<SystemMainTable> list = c.list();
			if (!list.isEmpty()) {
				SystemMainTable result = list.iterator().next();
				this.saveConfigItemTablePanel(result, 1);
			}

			// 将发布后新增的字段保存到扩展字段里，并更新相关系统主表和面板
			saveExtendProps(sourceClassName, model);
		}

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

	public void saveExtendProps(String sourceClassName, PersistentClass model) {
		// 获取主表
		Criteria c = super.getCriteria(SystemMainTable.class);
		c.add(Restrictions.ilike("tableName", sourceClassName, MatchMode.EXACT));
		List list = c.list();
		SystemMainTable smt = null;
		Class clazz = null;
		if (!list.isEmpty()) {
			smt = (SystemMainTable) list.iterator().next();
			String className = smt.getClassName();
			clazz = this.getClass(className);
		}

		RenderClass rc = new RenderClass();
		ArrayList rcolumns = new ArrayList();

		// 获取主表的所有字段
		Criteria cc = super.getCriteria(SystemMainTableColumn.class);
		cc.add(Restrictions.eq("systemMainTable", smt));
		cc.setFetchMode("propertyType", FetchMode.JOIN);
		cc.addOrder(Order.asc("id"));
		List<SystemMainTableColumn> columns = cc.list();
		//		
		// //中间注释了将新增的字段加载到hibernate持久化类中的代码，因为没有成功
		// Criteria ccc = super.getCriteria(SystemMainTableColumn.class);
		// ccc.add(Restrictions.eq("systemMainTable", smt));
		// ccc.setFetchMode("propertyType", FetchMode.JOIN);
		// ccc.addOrder(Order.asc("id"));
		// List<SystemMainTableColumn> columnCcs = ccc.list();
		//		
		// for(SystemMainTableColumn smtc : columnCcs){
		// String propertyName = smtc.getPropertyName();
		//			
		// RenderProperty property = new RenderProperty();
		// property.setName(propertyName);
		// property.setField(propertyName);
		// //property.setLength(smtc.getLength());
		// if(propertyName.equalsIgnoreCase("id")){
		// property.setPrimary(true);
		// }
		// PropertyType pt = smtc.getPropertyType();
		// String ptClassName = pt.getPropertyTypeClass();
		// property.setType(ptClassName);
		// rcolumns.add(property);
		// }
		//		
		// try {
		// this.genPersistentClass(model, "", sourceClassName,
		// ConfigItem.class);
		// } catch (Exception e) {
		// System.out.println("发生重复添加mapping异常");
		// }
		// end
		for (SystemMainTableColumn smtc : columns) {
			System.out.println("smtc name: " + smtc.getPropertyName());

			// 判断当前遍历的字段是否需要增加到hibernate持久化上下文里面
			boolean needAddFlag = this.needAddProperty2PersistClass(model, smtc);
			if (needAddFlag) {

				// 定义新的字段
				// org.hibernate.mapping.Column column = new
				// org.hibernate.mapping.Column();
				// column.setName(smtc.getPropertyName());
				// if(smtc.getIsMustInput()!=null &&
				// smtc.getIsMustInput().intValue()==1){
				// column.setNullable(true);
				// }
				// if(smtc.getUniqueFlag()!=null &&
				// smtc.getUniqueFlag().intValue()==1){
				// column.setUnique(true);
				// }
				// model.getTable().addColumn(column);
				// //包装字段给一个VALUE
				// SimpleValue value = new SimpleValue();
				// value.setTable(model.getTable());
				// PropertyType ptt = smtc.getPropertyType();
				// String propertyTypeName = ptt.getPropertyTypeName();
				// if(!propertyTypeName.equalsIgnoreCase("BaseObject")){
				// value.setTypeName(propertyTypeName.toLowerCase());
				// }else{
				// SystemMainTable fsmt = smtc.getForeignTable();
				// String fclassName = fsmt.getClassName();
				// value.setTypeName(fclassName);
				// }
				// value.addColumn(column);
				// //定义一个新的属性
				// Property prop = new Property();
				// prop.setValue(value);
				// prop.setName(smtc.getPropertyName());
				// prop.setNodeName(prop.getName());
				// model.addProperty(prop);

			}// end need add

			// 更新sessionFactory
			System.out.println("smtc name: " + smtc.getPropertyName());
			// super.getSessionFactory().refreshPersistentClass(model,
			// super.getConfiguration().getMapping());
			// super.getConfiguration().buildSessionFactory();
			// end

			// 给系统可见字段增加字段
			Criteria cSysInput = super.getCriteria(SystemTableSetting.class);
			cSysInput.add(Restrictions.eq("systemMainTable", smt));
			cSysInput.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
			cSysInput.add(Restrictions.eq("mainTableColumn", smtc));

			cSysInput.setProjection(Projections.rowCount());
			Integer sSysInputCount = (Integer) cSysInput.uniqueResult();
			if (sSysInputCount.intValue() == 0) {
				cSysInput.setProjection(Projections.max("order"));
				Integer sysInputMaxOrder = (Integer) cSysInput.uniqueResult();
				if (sysInputMaxOrder == null)
					sysInputMaxOrder = 0;
				// 初始化输入系统可见字段
				SystemTableSetting stsInput = new SystemTableSetting();
				stsInput.setSettingType(SystemTableSetting.INPUT);
				stsInput.setSystemMainTable(smt);
				if (smtc instanceof SystemMainTableColumn) {
					stsInput.setMainTableColumn((SystemMainTableColumn) smtc);
				}
				stsInput.setIsDisplay(new Integer(1));
				stsInput.setOrder(sysInputMaxOrder + 1);
				this.save(stsInput);
				this.evict(stsInput);

			}

			Criteria cSysList = super.getCriteria(SystemTableSetting.class);
			cSysList.add(Restrictions.eq("systemMainTable", smt));
			cSysList.add(Restrictions.eq("settingType", SystemTableSetting.LIST));
			cSysList.add(Restrictions.eq("mainTableColumn", smtc));
			cSysList.setProjection(Projections.rowCount());

			Integer cSysListCount = (Integer) cSysList.uniqueResult();
			if (cSysListCount.intValue() == 0) {
				cSysList.setProjection(Projections.max("order"));
				Integer sysListMaxOrder = (Integer) cSysList.uniqueResult();
				if (sysListMaxOrder == null)
					sysListMaxOrder = 0;
				// 初始化输入系统可见字段
				SystemTableSetting stsList = new SystemTableSetting();
				stsList.setSettingType(SystemTableSetting.LIST);
				stsList.setSystemMainTable(smt);
				if (smtc instanceof SystemMainTableColumn) {
					stsList.setMainTableColumn((SystemMainTableColumn) smtc);
				}
				stsList.setIsDisplay(new Integer(1));
				stsList.setOrder(sysListMaxOrder + 1);
				this.save(stsList);
				this.evict(stsList);

			}

			// 给当前表的面板增加字段，默认可见
			Criteria cPanel = super.getCriteria(PagePanel.class);
			cPanel.add(Restrictions.eq("systemMainTable", smt));
			List<PagePanel> pagePanels = cPanel.list();
			if (pagePanels.isEmpty()) {
				// 如果没有面板，说明面板已经被删除，初始化
				// genTablePanels(smt);

			} else {
				for (PagePanel pItem : pagePanels) {
					Criteria cpc = super.getCriteria(PagePanelColumn.class);
					cpc.add(Restrictions.eq("pagePanel", pItem));
					cpc.add(Restrictions.eq("mainTableColumn", smtc));
					cpc.setProjection(Projections.rowCount());
					Integer cpcCount = (Integer) cpc.uniqueResult();
					if (cpcCount.intValue() == 0) {
						cpc.setProjection(Projections.max("order"));
						Integer maxPanelOrder = (Integer) cpc.uniqueResult();
						if (maxPanelOrder == null)
							maxPanelOrder = 0;
						PagePanelColumn ppc = new PagePanelColumn();
						ppc.setPagePanel(pItem);
						ppc.setSystemMainTable(smt);
						ppc.setMainTableColumn(smtc);
						ppc.setOrder(maxPanelOrder + 1);
						ppc.setIsDisplay(1);
						ppc.setIsMustInput(1);
						ppc.setMatchMode(null);
						super.save(ppc);

					}
				}
			}

		}// end
	}

	// 在配置项类型的面板没有的情况下，直接保存会默认生成
	private void genTablePanels(SystemMainTable smt) {
		PagePanel panel = new PagePanel();
		panel.setName("panel_" + smt.getTableCnName() + "Panel");
		panel.setTitle(smt.getTableCnName());
		panel.setDescn(smt.getTableCnName() + "面板，用户增加此类配置项时系统自动创建此面板");
		panel.setGroupFlag(0);
		PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
		panel.setXtype(ppt);
		panel.setSettingType(SystemTableSetting.INPUT); // form
		panel.setSystemMainTable(smt);
		Module module = super.findUniqueBy(Module.class, "name", "配置项管理");
		panel.setModule(module);
		super.save(panel);

		// 生成面板主表
		PagePanelTable pptble = new PagePanelTable();
		pptble.setPagePanel(panel);
		pptble.setSystemMainTable(smt);
		super.save(pptble);
	}

	/**
	 * 生成实体和对象关系映射文件
	 * 
	 * @Methods Name genEntityAndMapping
	 * @Create In 2009-1-10 By sa
	 * @param model
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass
	 *            目标类的名称，防止框架引用应用中的类路径名称，应该是用全类名称，使用属性文件 void
	 */
	public SystemMainTable genPersistentClass(PersistentClass model, String sourcePkg, String sourceClassName,
			String targetClass, String prefix) {
		SessionFactory ssf = super.getHibernateSessionFactory();
		Configuration config = super.getConfiguration();
		// PersistentClass model;
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
		// 持久类对象描述
		RenderClass rc = new RenderClass();
		ArrayList rcolumns = new ArrayList();
		if (!list.isEmpty()) {
			smt = (SystemMainTable) list.iterator().next();
			className = smt.getClassName();
			code.append(LSP);
			code.append("public class ").append(sourceClassName).append(" extends BaseObject {").append(LSP);
			// code.append(LSP);

			// begin 生成主键信息
			SystemMainTableColumn pkc = smt.getPrimaryKeyColumn();
			RenderProperty propertyPK = new RenderProperty();
			propertyPK.setName(pkc.getPropertyName());
			propertyPK.setField(pkc.getPropertyName());
			propertyPK.setPrimary(true);
			propertyPK.setType(pkc.getPropertyType().getPropertyTypeClass());
			rcolumns.add(propertyPK);
			// end

			Criteria cc = super.getCriteria(SystemMainTableColumn.class);
			cc.add(Restrictions.eq("systemMainTable", smt));
			cc.addOrder(Order.asc("id"));
			List<SystemMainTableColumn> listcc = cc.list();

			for (SystemMainTableColumn smtc : listcc) {
				// String columnName = smtc.getColumnName();
				String propertyName = smtc.getPropertyName();
				if (propertyName == null) {
					propertyName = smtc.getColumnName2();
				}
				String bPropertyName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

				PropertyType pt = smtc.getPropertyType();
				// String ptName = pt.getName();
				String ptClassName = pt.getPropertyTypeClass();
				// 增加字段
				if (!ptClassName.equals("com.digitalchina.info.framework.dao.BaseObject")) {
					RenderProperty property = new RenderProperty();
					property.setName(propertyName);
					property.setField(propertyName);
					// property.setLength(smtc.getLength());
					if (propertyName.equalsIgnoreCase("id")) {
						property.setPrimary(true);
					}
					property.setType(ptClassName);

					// begin
					if (ptClassName.equals("java.util.Set")) {
						SystemMainTable ftable = smtc.getForeignTable();
						SystemMainTableColumn fkey = smtc.getForeignTableKeyColumn();
						SystemMainTableColumn fvalue = smtc.getForeignTableValueColumn();
						String fclassName = ftable.getClassName();

						SystemMainTable refTable = smtc.getReferencedTable(); // 关联表userRole
						String refTableName = refTable.getTableName();
						Integer userListFlag = refTable.getUserListFlag();
						Integer userExtFlag = refTable.getUserExtFlag();
						Integer userScidFlag = refTable.getUserScidFlag();

						// 获取关联表名称并自动给表名称加前缀
						if (userListFlag != null && userListFlag.intValue() == 1) {
							String lstPrefix = PropertiesUtil
									.getProperties("system.userlist.table.prefix", "itil_lst_");
							refTableName = lstPrefix + refTableName;
						} else if (userExtFlag != null && userExtFlag.intValue() == 1) {
							String lstPrefix = PropertiesUtil.getProperties("system.config.citable.prefix", "itil_ci_");
							refTableName = lstPrefix + refTableName;
						} else if (userScidFlag != null && userScidFlag.intValue() == 1) {
							String lstPrefix = PropertiesUtil.getProperties("system.config.scitable.prefix",
									"itil_sci_");
							refTableName = lstPrefix + refTableName;
						}

						SystemMainTableColumn refTableVColumn = smtc.getReferencedTableValueColumn();// user_id
						SystemMainTableColumn refTablePColumn = smtc.getReferencedTableParentColumn(); // role_id

						property.setForeignClass(fclassName);// role class
						property.setRefTable(refTableName); // userRole
						property.setRefPColumn(refTablePColumn.getPropertyName()); // user_id
						property.setRefVColunn(refTableVColumn.getPropertyName()); // role_id
					}
					// end

					if (!propertyName.equalsIgnoreCase("id")) {
						rcolumns.add(property);
					}

					if (ptClassName.equals("java.util.Set")) {
						field.append("   private ").append(ptClassName).append(" ").append(propertyName).append(
								" = new java.util.HashSet();").append(LSP);
					} else {
						field.append("   private ").append(ptClassName).append(" ").append(propertyName).append(";")
								.append(LSP);
					}

					setter.append("   public void set").append(bPropertyName).append("(").append(ptClassName).append(
							" ").append(propertyName).append("){").append(LSP);
					setter.append("	     this.").append(propertyName).append("=").append(propertyName).append(";")
							.append(LSP);
					setter.append("   }").append(LSP);

					getter.append("   public ").append(ptClassName).append(" get").append(bPropertyName).append("(){")
							.append(LSP);
					getter.append("	     return this.").append(propertyName).append(";").append(LSP);
					getter.append("   }").append(LSP);

				} else {
					SystemMainTable ftable = smtc.getForeignTable();
					Integer userExtFlag = ftable.getUserExtFlag();
					Integer userListFlag = ftable.getUserListFlag();
					Integer deployFlag = ftable.getDeployFlag();

					if (userExtFlag != null && userExtFlag.intValue() == 1) {
						// 如果使用的表没有发布，先发布
						if (deployFlag == null || deployFlag != null && deployFlag.intValue() == 0) {
							this.saveSystemMainTableDeploy(ftable);
						}
					}
					SystemMainTableColumn fkey = smtc.getForeignTableKeyColumn();
					SystemMainTableColumn fvalue = smtc.getForeignTableValueColumn();
					String fclassName = ftable.getClassName();
					ptClassName = fclassName;
					RenderProperty property = new RenderProperty();
					property.setName(propertyName);
					property.setField(propertyName);
					// property.setLength(smtc.getLength());
					if (propertyName.equalsIgnoreCase("id")) {
						property.setPrimary(true);
					}
					property.setType(ptClassName);
					rcolumns.add(property);

					// 对象属性的setter和getter
					field.append("   private ").append(ptClassName).append(" ").append(propertyName).append(";")
							.append(LSP);

					setter.append("   public void set").append(bPropertyName).append("(").append(ptClassName).append(
							" ").append(propertyName).append("){").append(LSP);
					setter.append("	     this.").append(propertyName).append("=").append(propertyName).append(";")
							.append(LSP);
					setter.append("   }").append(LSP);

					getter.append("   public ").append(ptClassName).append(" get").append(bPropertyName).append("(){")
							.append(LSP);
					getter.append("	     return this.").append(propertyName).append(";").append(LSP);
					getter.append("   }").append(LSP);
				}

				super.evict(smtc);
			}
			rc.setProperties(rcolumns);

			// equals方法
			equals.append("   public boolean equals(Object obj) {").append(LSP);
			equals.append("		if (this == obj)").append(LSP);
			equals.append("   		return true;").append(LSP);
			equals.append("   	if (!super.equals(obj))").append(LSP);
			equals.append("   		return false;").append(LSP);
			equals.append("   	if (getClass() != obj.getClass())").append(LSP);
			equals.append("   		return false;").append(LSP);
			equals.append("   	final ").append(smt.getClassName()).append(" other = (").append(smt.getClassName())
					.append(") obj;").append(LSP);

			String pkcPropertyName = pkc.getPropertyName();
			equals.append("   	if (" + pkcPropertyName + " == null) {").append(LSP);
			;
			equals.append("   		if (other." + pkcPropertyName + " != null)").append(LSP);
			equals.append("   			return false;").append(LSP);
			equals.append("   	} else if (!" + pkcPropertyName + ".equals(other." + pkcPropertyName + "))").append(LSP);
			equals.append("   		return false;").append(LSP);
			equals.append("   	return true; ").append(LSP);
			equals.append("   }").append(LSP).append(LSP);

			// hashCode方法
			hashCode.append("   public int hashCode() {").append(LSP);
			hashCode.append("		final int prime = 31;").append(LSP);
			hashCode.append("   	int result = super.hashCode();").append(LSP);
			hashCode.append(
					"   	result = prime * result + ((" + pkcPropertyName + " == null) ? 0 : " + pkcPropertyName
							+ ".hashCode());").append(LSP);
			hashCode.append("   	return result;").append(LSP);
			hashCode.append("  	}").append(LSP);

			code.append(field).append(LSP).append(setter).append(LSP).append(getter).append(LSP).append(equals).append(
					LSP).append(hashCode);

			code.append("} ");

			if (!listcc.isEmpty()) {
				String targetDIR = null;
				try {
					targetDIR = PathUtil.getPkgPathFromClass(targetClass);
				} catch (Exception e) {
					e.printStackTrace();
				}

				String tablePrefix = prefix; // PropertiesUtil.getProperties("system.config.citable.prefix",
				// prefix);
				rc.setClassName(sourcePkg + "." + sourceClassName);
				rc.setTableName(tablePrefix + sourceClassName);
				String tmp = System.getProperty("java.class.path");
				String WEB_INF_CLASSES = this.getClass().getResource("/").toString().replace("/", "\\");
				int fileLength = "file:\\".length();
				WEB_INF_CLASSES = WEB_INF_CLASSES.substring(fileLength);

				// String targetClassName = ConfigItem.class.getName();
				RuntimeCode
						.genEntityAndClass(WEB_INF_CLASSES, sourcePkg, sourceClassName, code.toString(), targetClass);
				// 开始生成hbm.xml
				String hbmDIR = targetDIR + "map" + FSP + sourceClassName + ".hbm.xml";

				FreemarkerRender render = new FreemarkerRender();
				render.render(rc, Templates.TEMPLATE_HIBERNATE3, hbmDIR);
				// URL url = this.getClass().getResource(hbmdir);

				int classesIndex = hbmDIR.indexOf("classes");
				hbmDIR = hbmDIR.substring(classesIndex + 7).replace("\\", "/");
				URL url = this.getClass().getResource(hbmDIR);
				config.addURL(url);
				model = config.getClassMapping(className);
				try {
					ssf.addPersistentClass(model, config.getMapping());
				} catch (Exception e) {
					System.out.println("重复增加持久化类发生异常");
				}
				if (smt != null) {
					smt.setDeployFlag(1);
					super.save(smt);

					// this.saveConfigItemTablePanel(smt, 1);

					// FreemarkerRender render22 = new FreemarkerRender();
					// render22.render(rc, Templates.TEMPLATE_HIBERNATE3,
					// hbmDIR);
					// URL url = this.getClass().getResource(hbmdir);

				}
			}

		}
		return smt;
	}

	// 保存一个用户主表后自动生成对应的面板
	public PagePanel saveConfigItemTablePanel(SystemMainTable smt, int deployFlag) {
		String className = smt.getClassName();
		String tableName = smt.getTableName();
		String tableCnName = smt.getTableCnName();
		String ppcount = "select count(*) from PagePanel ppc where ppc.systemMainTable=?";
		Query ppQuery = super.createQuery(ppcount, smt);
		Long ppcRows = (Long) ppQuery.uniqueResult();

		PagePanel panel = null;

		if (ppcRows.intValue() == 0) {
			panel = new PagePanel();
			panel.setName("panel_" + tableName + "Panel");
			panel.setTitle(tableCnName);
			panel.setDescn(tableCnName + "面板，用户增加此类配置项时系统自动创建此面板");
			panel.setGroupFlag(0);
			PagePanelType ppt = super.findUniqueBy(PagePanelType.class, "name", "form");
			panel.setXtype(ppt);
			panel.setSettingType(SystemTableSetting.INPUT); // form
			panel.setSystemMainTable(smt);
			Module module = super.findUniqueBy(Module.class, "name", "配置项管理");
			panel.setModule(module);
			super.save(panel);

			// 生成面板主表
			PagePanelTable pptble = new PagePanelTable();
			pptble.setPagePanel(panel);
			pptble.setSystemMainTable(smt);
			super.save(pptble);

			// 取系统主表的所有字段，初始化系统可见字段
			int order = 1;
			List<Column> columns = systemColumnService.findSystemTableColumns(smt);
			for (Column column : columns) {
				// 初始化输入系统可见字段
				// 判断当前字段在系统可见字段里是否已经存在
				Criteria cSysInput = super.getCriteria(SystemTableSetting.class);
				cSysInput.add(Restrictions.eq("systemMainTable", smt));
				cSysInput.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
				// if(column instanceof SystemMainTableColumn){
				SystemMainTableColumn smtc = ((SystemMainTableColumn) column);
				cSysInput.add(Restrictions.eq("mainTableColumn", smtc));
				// }else if(column instanceof SystemMainTableExtColumn) {
				// SystemMainTableExtColumn smtec = ((SystemMainTableExtColumn)
				// column);
				// cSysInput.add(Restrictions.eq("extendTableColumn", smtec));
				// }
				cSysInput.setProjection(Projections.rowCount());

				Integer cSysInputCount = (Integer) cSysInput.uniqueResult();
				if (cSysInputCount.intValue() == 0) {
					cSysInput.setProjection(Projections.max("order"));
					Integer sysInputMaxOrder = (Integer) cSysInput.uniqueResult();
					if (sysInputMaxOrder == null)
						sysInputMaxOrder = 0;
					// begin
					SystemTableSetting stsInput = new SystemTableSetting();
					stsInput.setSettingType(SystemTableSetting.INPUT);
					stsInput.setSystemMainTable(smt);
					// if(column instanceof SystemMainTableColumn){
					stsInput.setMainTableColumn((SystemMainTableColumn) column);
					// }else if(column instanceof SystemMainTableExtColumn) {
					// stsInput.setExtendTableColumn((SystemMainTableExtColumn)
					// column);
					// }
					stsInput.setIsDisplay(new Integer(1));
					stsInput.setOrder(sysInputMaxOrder + 1);
					this.save(stsInput);
					this.evict(stsInput);
					// end
				}

				// 初始化列表页面的系统可见字段
				Criteria cSysList = super.getCriteria(SystemTableSetting.class);
				cSysList.add(Restrictions.eq("systemMainTable", smt));
				cSysList.add(Restrictions.eq("settingType", SystemTableSetting.LIST));
				// if(column instanceof SystemMainTableColumn){
				// SystemMainTableColumn smtc = ((SystemMainTableColumn)
				// column);
				cSysList.add(Restrictions.eq("mainTableColumn", smtc));
				// }else if(column instanceof SystemMainTableExtColumn) {
				// SystemMainTableExtColumn smtec = ((SystemMainTableExtColumn)
				// column);
				// cSysList.add(Restrictions.eq("extendTableColumn", smtec));
				// }
				cSysList.setProjection(Projections.rowCount());

				Integer cSysListCount = (Integer) cSysList.uniqueResult();
				if (cSysListCount.intValue() == 0) {
					cSysList.setProjection(Projections.max("order"));
					Integer sysListMaxOrder = (Integer) cSysList.uniqueResult();
					if (sysListMaxOrder == null)
						sysListMaxOrder = 0;

					SystemTableSetting stsList = new SystemTableSetting();
					stsList.setSettingType(SystemTableSetting.LIST);
					stsList.setSystemMainTable(smt);
					// if(column instanceof SystemMainTableColumn){
					stsList.setMainTableColumn((SystemMainTableColumn) column);
					// }else if(column instanceof SystemMainTableExtColumn) {
					// stsList.setExtendTableColumn((SystemMainTableExtColumn)
					// column);
					// }
					stsList.setIsDisplay(new Integer(1));
					stsList.setOrder(sysListMaxOrder + 1);
					this.save(stsList);
					this.evict(stsList);

				}

			}

			String ppccount = "select count(*) from PagePanelColumn ppc where ppc.pagePanel=?";
			Query query = super.createQuery(ppccount, panel);
			Long cunt = (Long) query.uniqueResult();
			if (cunt.intValue() == 0) {
				// 取所有的系统可见字段
				Criteria c = super.getCriteria(SystemTableSetting.class);
				c.add(Restrictions.eq("systemMainTable", smt));
				c.add(Restrictions.eq("settingType", SystemTableSetting.INPUT));
				c.addOrder(Order.asc("order"));
				List list = c.list();
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					SystemTableSetting uts = (SystemTableSetting) iter.next();
					Column column = uts.getColumn();
					// 遍历系统可见字段，生成面板字段

					// begin
					Criteria cpc = super.getCriteria(PagePanelColumn.class);
					cpc.add(Restrictions.eq("pagePanel", panel));
					// if(column instanceof SystemMainTableColumn){
					cpc.add(Restrictions.eq("mainTableColumn", (SystemMainTableColumn) column));
					// }else{
					// cpc.add(Restrictions.eq("extendTableColumn",
					// (SystemMainTableExtColumn) column));
					// }
					cpc.setProjection(Projections.rowCount());
					Integer cpcCount = (Integer) cpc.uniqueResult();
					if (cpcCount == null || cpcCount.intValue() == 0) {
						cpc.setProjection(Projections.max("order"));
						Integer maxPanelOrder = (Integer) cpc.uniqueResult();
						if (maxPanelOrder == null)
							maxPanelOrder = 0;
						// begin
						PagePanelColumn ppc = new PagePanelColumn();
						ppc.setPagePanel(panel);
						ppc.setSystemMainTable(smt);
						// if(column instanceof SystemMainTableColumn){
						ppc.setMainTableColumn((SystemMainTableColumn) column);
						// }else if(column instanceof SystemMainTableExtColumn)
						// {
						// ppc.setExtendTableColumn((SystemMainTableExtColumn)
						// column);
						// }
						ppc.setIsDisplay(uts.getIsDisplay());
						ppc.setOrder(maxPanelOrder + 1);
						ppc.setIsMustInput(column.getIsMustInput());
						super.save(ppc);
						super.evict(ppc);

					}// end
					// end

				}
			}

		} else {// 否则原来已经建好的面板无法用到当前CI类型表里
			Criteria cip = super.getCriteria(PagePanel.class);
			cip.add(Restrictions.eq("systemMainTable", smt));
			cip.add(Restrictions.eq("settingType", UserTableSetting.INPUT));
			List<PagePanel> list = cip.list();
			if (!list.isEmpty()) {
				panel = list.iterator().next();
			}

		}
		// 增加配置项类型，必须放在此位置否则对于已有的类无法发布，面板无法选中
		if (!smt.getClassName().endsWith("Event")) {
			ConfigItemType cit = this.findConfigItemTypeByTable(smt);
			if (cit == null) {
				cit = new ConfigItemType();
			}
			cit.setName(smt.getTableCnName());
			cit.setClassName(smt.getClassName());
			cit.setTableName(smt.getTableName());
			cit.setSystemMainTable(smt);
			cit.setPagePanel(panel);
			cit.setDeployFlag(deployFlag);
			super.save(cit);
		}
		return panel;

	}

	public Page findSystemMainTableByModule(Module module, String tableName, int pageNo, int pageSize) {
		Page page = null;
		// String hql ="";
		Criteria critera = super.createCriteria(SystemMainTable.class, "tableName", true);
		if (module != null) {
			critera.add(Restrictions.eq("module", module));
		}
		if (StringUtils.isNotBlank(tableName)) {
			critera.add(Restrictions.disjunction().add(Restrictions.ilike("tableName", tableName, MatchMode.ANYWHERE)) // 忽略大小写
					.add(Restrictions.ilike("tableCnName", tableName, MatchMode.ANYWHERE)));
		}
		critera.add(Restrictions.eq("userExtFlag", Integer.valueOf(1)));
		page = super.pagedQuery(critera, pageNo, pageSize);
		return page;
	}

	public void removeSystemMainTable(String[] smtIds) {
		if (smtIds == null || smtIds.length == 0) {
			throw new ServiceException("请选择要删除的系统主表");
		}
		for (int i = 0; i < smtIds.length; i++) {
			String smtId = smtIds[i];
			this.removeSystemMainTable(smtId);
		}

	}

	public void removeSystemMainTable(String smtId) {
		SystemMainTable smt = super.get(SystemMainTable.class, Long.valueOf(smtId));
		if (smt.getDeployFlag() != null && smt.getDeployFlag() == 1) {
			// throw new ServiceException("当前配置项类型处于发布状态不可以删除");
		}
		String hql = "select cit from ConfigItemType cit where cit.systemMainTable=?";
		List<ConfigItemType> citypes = super.find(hql, smt);
		try {
			for (ConfigItemType cit : citypes) {
				// 生成配置项类型对应的配置项数据
				Criteria c = super.getCriteria(ConfigItem.class);
				c.add(Restrictions.eq("configItemType", cit));
				c.setProjection(Projections.property("id"));
				List<Long> cis = c.list();
				for (Long ciId : cis) {
					super.executeUpdate("delete from CIRelationShipItem where configItem.id=?", new Object[] { ciId });
				}
				super.executeUpdate("delete from ConfigItem ci where ci.configItemType=?", cit);

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
		if (smtcIds == null || smtcIds.length == 0) {
			throw new ServiceException("请选择要删除的字段");
		}
		for (int i = 0; i < smtcIds.length; i++) {
			String smtcId = smtcIds[i];
			try {
				// 删除主字段//删除扩展字段还须删除相关数据
				this.removeSystemMainTableColumn(smtcId);
			} catch (ServiceException e) {
				// 删除扩展字段
				// SystemExtColumnServcie secs = (SystemExtColumnServcie)
				// ContextHolder.getBean("systemExtColumnService");
				// secs.removeSystemExtTableColumn(new String[]{smtcId});

			}
		}

	}

	/**
	 * 用户主表功能里删除字段，级联清空所有引用的字段，以及关联的可见字段
	 * 
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2009-1-12 By sa
	 * @param smtcId
	 *            void
	 */
	private void removeSystemMainTableColumn(String smtcId) {
		SystemMainTableColumn smtc = (SystemMainTableColumn) super.get(SystemMainTableColumn.class, Long
				.valueOf(smtcId));
		if (smtc != null) {
			// if(smtc.getIsExtColumn()==SystemMainTableColumn.isMain){
			SystemMainTable sysMainTable = smtc.getSystemMainTable();
			SystemMainTableColumn pkColumn = sysMainTable.getPrimaryKeyColumn();
			if (smtc == pkColumn) {
				sysMainTable.setPrimaryKeyColumn(null);
				super.save(sysMainTable);
			}
			super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableKeyColumn=null"
					+ " where foreignTableKeyColumn=?", smtc);
			super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableValueColumn=null"
					+ " where foreignTableValueColumn=?", smtc);
			super.executeUpdate("update SystemMainTableColumn smtc set smtc.foreignTableParentColumn=null"
					+ " where foreignTableParentColumn=?", smtc);

			super.executeUpdate("delete SystemTableSetting stqc where stqc.mainTableColumn=?", new Object[] { smtc });
			super.executeUpdate("delete UserTableSetting uts where uts.mainTableColumn=?", new Object[] { smtc });
			super.executeUpdate("delete SystemTableQueryColumn stqc where stqc.mainTableColumn=?",
					new Object[] { smtc });
			try {
				super.executeUpdate("delete PagePanelColumn ppc where ppc.mainTableColumn=?", new Object[] { smtc });
			} catch (Exception e) {
				e.printStackTrace();
			}
			// }else{
			// super.executeUpdate("delete SystemTableSetting stqc where
			// stqc.mainTableColumn=?", new Object[]{smtc});
			// super.executeUpdate("delete UserTableSetting uts where
			// uts.mainTableColumn=?", new Object[]{smtc});
			// super.executeUpdate("delete SystemTableQueryColumn stqc where
			// stqc.mainTableColumn=?", new Object[]{smtc});
			// }
			super.remove(smtc);
		} else {
			throw new ServiceException("删除的主字段不存在");
		}

	}

	public SystemMainTable findUserTableEvent(SystemMainTable smt) {
		// begin
		String eventClassName = smt.getClassName() + "Event";
		// Class clazz = this.getClass(eventClassName);
		Criteria cSmtEvent = super.getCriteria(SystemMainTable.class);
		cSmtEvent.add(Restrictions.eq("className", eventClassName));
		SystemMainTable smtEvent = null;
		List<SystemMainTable> smtEvents = cSmtEvent.list();
		if (!smtEvents.isEmpty()) {
			smtEvent = smtEvents.iterator().next();
		}
		// 遍历主表的所有字段
		// if(smtEvent==null){
		// //smtEvent = this.saveEventTableByMainTable(smt);
		// }
		// end

		return smtEvent;
	}

	/**
	 * 发布配置项类型的用户主表
	 */
	public void saveSystemMainTableDeploy(SystemMainTable smt) {
		SessionFactory sf = super.getHibernateSessionFactory();
		SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sf;
		Dialect dialect = sessionFactoryImpl.getDialect();
		StringBuffer buff = new StringBuffer();
		String dialectName = PropertiesUtil.getProperties("hibernate.dialect");

		// 发布主表时自动增加表名前缀
		String tablePrefix = PropertiesUtil.getProperties("system.config.citable.prefix", "itil_ci_");
		String tableName = smt.getTableName();
		if (smt.getTableName().indexOf(tablePrefix) == -1) {
			tableName = tablePrefix + smt.getTableName();
		}

		if (dialectName.equalsIgnoreCase("org.hibernate.dialect.SQLServerDialect")) {
			buff.append("CREATE TABLE ").append(tableName).append("(");
			buff.append(" ID ").append(dialect.getTypeName(Types.BIGINT)).append(" identity primary key");
			buff.append(");");
		} else if (dialectName.equalsIgnoreCase("org.hibernate.dialect.OracleDialect")) {
			buff.append("CREATE TABLE ").append(tableName).append("(");
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

		// 初始化创建人创建日期修改人和修改日期
		//addCreateUserDate(smt);

		// end
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt));
		c.addOrder(Order.asc("id"));
		List<SystemMainTableColumn> list = c.list();
		for (SystemMainTableColumn smtc : list) {

			String columnName = smtc.getColumnName();
			if (!pkcname.equalsIgnoreCase(columnName)) {
				SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
				PropertyType pt = smtc.getPropertyType();
				if (pt == null) {
					throw new ServiceException(smt.getTableCnName() + "的" + smtc.getPropertyName() + "字段的属性类型不可以为null");
				}
				String ptName = pt.getPropertyTypeName();

				String columnTypeName = smtcType.getColumnTypeName();
				Integer length = smtc.getLength();
				if (length == null || length.intValue() == 0) {
					length = 200;
				}

				String sqlColumnType = dialect.getTypeName(Types.VARCHAR);
				if (ptName.equalsIgnoreCase("BaseObject")) {
					sqlColumnType = dialect.getTypeName(Types.BIGINT);
				} else if (ptName.equalsIgnoreCase("Long")) {
					sqlColumnType = dialect.getTypeName(Types.BIGINT);
				} else if (ptName.equalsIgnoreCase("String")) {
					sqlColumnType = dialect.getTypeName(Types.VARCHAR, length, 10, 0);
				} else if (ptName.equalsIgnoreCase("Integer")) {
					sqlColumnType = dialect.getTypeName(Types.BIGINT);
				} else if (ptName.equalsIgnoreCase("Double")) {
					sqlColumnType = dialect.getTypeName(Types.DOUBLE);
				} else if (ptName.equalsIgnoreCase("Date")) {
					sqlColumnType = dialect.getTypeName(Types.DATE);
				}

				String hql = "ALTER TABLE " + tableName + " ADD " + columnName + " " + sqlColumnType;
				try {
					Connection conn = super.getSession().connection();
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(hql);
					conn.commit();
				} catch (Exception e) {
					System.out.println("增加的字段已经存在, 继续增加新的字段");
				}
			}

		}// 新增字段增加完毕

		String className = smt.getClassName();
		int lastDot = className.lastIndexOf(".");
		String sourcePkg = className.substring(0, lastDot);
		String sourceClass = className.substring(lastDot + 1);

		String targetClassName = null;
		if (sourcePkg.equalsIgnoreCase("com.digitalchina.itil.config.extci.entity")) {
			// targetClassName是指发布后生成的代码放到哪个位置，参考CITableTemplate的位置
			targetClassName = "com.digitalchina.itil.config.extci.entity.CITableTemplate";
		} else {
			targetClassName = className;
		}

		this.saveTableEntity(sourcePkg, sourceClass, targetClassName);

	}

	private void addCreateUserDate(SystemMainTable smt) {
		String userUniqueColumn = PropertiesUtil.getProperties("system.user.uniquecolumn", "userName");

		// 增加创建日期等字段
		String hqlMaxOrder = "select max(smtc.order) from SystemMainTableColumn smtc where smtc.systemMainTable=?";
		Query query = super.createQuery(hqlMaxOrder, smt);
		Integer count = (Integer) query.uniqueResult();
		if (count == null)
			count = 0;

		Criteria cUsr = super.getCriteria(SystemMainTableColumn.class);
		cUsr.add(Restrictions.eq("systemMainTable", smt));
		cUsr.add(Restrictions.eq("propertyName", "createUser"));
		cUsr.setProjection(Projections.rowCount());
		Integer countUser = (Integer) cUsr.uniqueResult();
		if (countUser == null)
			countUser = 0;
		if (countUser.intValue() == 0) {
			PropertyType pt = super.get(PropertyType.class, 1L); // baseObject属性类型
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); // 隐藏域

			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("创建人");
			smtc.setColumnName("createUser");
			smtc.setPropertyName("createUser");
			smtc.setDescn("发布主表时系统自动创建");
			smtc.setOrder(count + 1);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			smtc.setIsExtColumn(SystemMainTableColumn.isMain);

			SystemMainTable ftble = systemMainTableService.findSystemMainTableByClazz(UserInfo.class);
			smtc.setForeignTable(ftble);

			SystemMainTableColumn ftbcKey = systemMainColumnService
					.findSystemMainTableColumnByTableAndName(ftble, "id");
			smtc.setForeignTableKeyColumn(ftbcKey);

			SystemMainTableColumn ftbcValue = systemMainColumnService.findSystemMainTableColumnByTableAndName(ftble,
					userUniqueColumn);
			smtc.setForeignTableValueColumn(ftbcValue);

			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}

		Criteria ccreated = super.getCriteria(SystemMainTableColumn.class);
		ccreated.add(Restrictions.eq("systemMainTable", smt));
		ccreated.add(Restrictions.eq("propertyName", "createDate"));
		ccreated.setProjection(Projections.rowCount());
		Integer countcreated = (Integer) ccreated.uniqueResult();
		if (countcreated == null)
			countcreated = 0;
		if (countcreated.intValue() == 0) {
			PropertyType pt = super.get(PropertyType.class, 6L); // 日期属性类型
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); // 隐藏域

			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("创建日期");
			smtc.setColumnName("createDate");
			smtc.setPropertyName("createDate");
			smtc.setDescn("发布主表时系统自动创建");
			smtc.setOrder(count + 2);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			smtc.setIsExtColumn(SystemMainTableColumn.isMain);
			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}

		Criteria cmdUsr = super.getCriteria(SystemMainTableColumn.class);
		cmdUsr.add(Restrictions.eq("systemMainTable", smt));
		cmdUsr.add(Restrictions.eq("propertyName", "modifyUser"));
		cmdUsr.setProjection(Projections.rowCount());
		Integer countmdUsr = (Integer) cmdUsr.uniqueResult();
		if (countmdUsr == null)
			countmdUsr = 0;
		if (countmdUsr.intValue() == 0) {
			PropertyType pt = super.get(PropertyType.class, 1L); // baseObject属性类型
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); // 隐藏域

			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtct.setId(8L); // 隐藏域
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("最后修改人");
			smtc.setColumnName("modifyUser");
			smtc.setPropertyName("modifyUser");
			smtc.setDescn("发布主表时系统自动创建");
			smtc.setOrder(count + 3);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			smtc.setIsExtColumn(SystemMainTableColumn.isMain);
			SystemMainTable ftble = systemMainTableService.findSystemMainTableByClazz(UserInfo.class);
			smtc.setForeignTable(ftble);

			SystemMainTableColumn ftbcKey = systemMainColumnService
					.findSystemMainTableColumnByTableAndName(ftble, "id");
			smtc.setForeignTableKeyColumn(ftbcKey);

			SystemMainTableColumn ftbcValue = systemMainColumnService.findSystemMainTableColumnByTableAndName(ftble,
					userUniqueColumn);
			smtc.setForeignTableValueColumn(ftbcValue);

			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}

		Criteria cmodate = super.getCriteria(SystemMainTableColumn.class);
		cmodate.add(Restrictions.eq("systemMainTable", smt));
		cmodate.add(Restrictions.eq("propertyName", "modifyDate"));
		cmodate.setProjection(Projections.rowCount());
		Integer countmdate = (Integer) cmodate.uniqueResult();
		if (countmdate == null)
			countmdate = 0;
		if (countmdate.intValue() == 0) {
			PropertyType pt = super.get(PropertyType.class, 6L); // 日期属性类型
			SystemMainTableColumnType smtct = super.get(SystemMainTableColumnType.class, 8L); // 隐藏域

			SystemMainTableColumn smtc = new SystemMainTableColumn();
			smtct.setId(8L); // 隐藏域
			smtc.setPropertyType(pt);
			smtc.setSystemMainTableColumnType(smtct);
			smtc.setColumnCnName("最后修改日期");
			smtc.setColumnName("modifyDate");
			smtc.setPropertyName("modifyDate");
			smtc.setDescn("发布主表时系统自动创建");
			smtc.setOrder(count + 4);
			smtc.setSystemMainTable(smt);
			smtc.setIsHiddenItem(0);
			smtc.setIsSearchItem(0);
			smtc.setIsUpdateItem(0);
			smtc.setIsMustInput(1);
			smtc.setIsExtColumn(SystemMainTableColumn.isMain);
			systemMainColumnService.saveSystemMainTableColumn(smtc);
		}
	}

	public SystemMainTable saveEventTableByMainTable(SystemMainTable smt) {
		// begin save smt event
		SystemMainTable smtEvent = new SystemMainTable();
		try {
			// 新增和修改都需要时刻同步主表的属性
			smtEvent.setDeployFlag(smt.getDeployFlag());
			smtEvent.setDescn(smt.getDescn());
			smtEvent.setId(null);
			smtEvent.setModule(smt.getModule());
			smtEvent.setPrimaryKeyColumn(null);
			smtEvent.setTableName(smt.getTableName() + "Event");
			smtEvent.setTableCnName(smt.getTableCnName() + "历史");
			smtEvent.setClassName(smt.getClassName() + "Event");
			smtEvent.setUserExtFlag(null);

			super.save(smtEvent);// 保存主表的历史表

		} catch (Exception e) {
			e.printStackTrace();
		}
		// end save smt event

		// 配置项基础信息
		Criteria c = super.getCriteria(SystemMainTableColumn.class);
		c.add(Restrictions.eq("systemMainTable", smt)); // 获取主表的所有字段
		c.addOrder(Order.asc("id"));
		List<SystemMainTableColumn> smtcs = c.list();
		// int cicsize = 0;
		for (int i = 0; i < smtcs.size(); i++) {// 遍历主表的字段
			// 新建主表历史表的字段
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

		// 获取主键字段
		Criteria cPk = super.getCriteria(SystemMainTableColumn.class);
		cPk.add(Restrictions.eq("systemMainTable", smtEvent)); // 获取主表的所有字段
		cPk.add(Restrictions.ilike("propertyName", "id"));
		SystemMainTableColumn smtcPk = (SystemMainTableColumn) cPk.uniqueResult();
		smtEvent.setPrimaryKeyColumn(smtcPk);
		super.save(smtEvent);// 保存主表的历史表

		return smtEvent;
	}

	public SystemMainTable saveSystemMainTable(SystemMainTable smt) {

		// *****************************************************************************************************
		smt.setUserExtFlag(1);
		String tableName = smt.getTableName();
		tableName = StringUtils.capitalize(smt.getTableName());
		if (tableName.length() > 21) {
			throw new ServiceException("表名称不可以超过21个字符");
		}
		smt.setTableName(tableName);

		String configDefaultPkg = PropertiesUtil.getProperties("system.config.defaultpkg",
				"com.digitalchina.itil.config.extci.entity");
		if (smt.getClassName() == null) {
			String className = configDefaultPkg + "." + tableName;
			smt.setClassName(className);
		} else {
			// 如果已经发布了，不能修改表名
			if (smt.getDeployFlag() != null && smt.getDeployFlag().intValue() == 1) {
				SystemMainTable smtOld = super.get(SystemMainTable.class, smt.getId());
				smt.setTableName(smtOld.getTableName());
				String tableNameOld = smtOld.getTableName();
				if (!tableNameOld.equalsIgnoreCase(tableName)) {
					String className = configDefaultPkg + "." + tableName;
					smt.setClassName(className);
				}
				// 说明是想修改类名称，这个操作只可能是开发人员修改
				if (smtOld.getClassName().equalsIgnoreCase(smt.getClassName())) {
					System.out.print("dd");
				}
			}
		}

		boolean isInsert = smt.getId() == null;
		if (isInsert) { // 判断表名是否存在
			Criteria cSameName = super.getCriteria(SystemMainTable.class);
			cSameName.add(Restrictions.ilike("tableName", smt.getTableName(), MatchMode.EXACT));
			cSameName.setProjection(Projections.rowCount());
			Integer sameCount = (Integer) cSameName.uniqueResult();
			if (sameCount != null && sameCount.intValue() > 0) {
				throw new ServiceException("您创建的用户表已经存在, 请进入数据模型维护<br>页面查看已有主表信息");
			}
		}

		// begin save smt event
		SystemMainTable smtEvent = new SystemMainTable();
		try {
			// 新增和修改都需要时刻同步主表的属性
			BeanUtils.copyProperties(smtEvent, smt);
			smtEvent.setTableName(smt.getTableName() + "Event");
			smtEvent.setTableCnName(smt.getTableCnName() + "历史");
			smtEvent.setClassName(smt.getClassName() + "Event");
			smtEvent.setUserExtFlag(null);
			if (isInsert) { // 对于新增
				smtEvent.setId(null);
				super.save(smtEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// end savme smt event

		// save smt
		super.save(smt);
		// end save smt

		// save column
		SystemMainTableColumn smtc = null;
		if (isInsert) {
			PropertyType pt = new PropertyType();
			pt.setId(2L);
			smtc = new SystemMainTableColumn();
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
			// smt.setTableName("ci_ext"+tableName);
			super.save(smt);
			// begin_为历史表的主键字段初始化主键字段
			SystemMainTableColumn smtcEvent = new SystemMainTableColumn();
			try {
				BeanUtils.copyProperties(smtcEvent, smtc);
				smtcEvent.setSystemMainTable(smtEvent);
				smtcEvent.setId(null);
				super.save(smtcEvent);
				smtEvent.setPrimaryKeyColumn(smtcEvent);
				super.save(smtEvent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// end

			// 保存配置项类型草稿
			// ConfigItemType cit = new ConfigItemType();
			// cit.setName(smt.getTableCnName());
			// cit.setClassName(smt.getClassName());
			// cit.setTableName(smt.getTableName());
			// cit.setSystemMainTable(smt);
			// cit.setPagePanel(null);
			// cit.setDeployFlag(0);
			// super.save(cit);

		}

		return smt;
	}

	public SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn smtc) {

		// SessionFactory sf = super.getHibernateSessionFactory();
		// SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sf;
		// Dialect dialect = sessionFactoryImpl.getDialect();

		SystemMainTable smt = smtc.getSystemMainTable();
		smt = super.get(SystemMainTable.class, Long.valueOf(smt.getId()));

		boolean isInsert = false;
		if (smtc.getId() != null) {
			SystemMainTableColumn old = (SystemMainTableColumn) super.getObject(SystemMainTableColumn.class, smtc
					.getId(), true);
			boolean columnNameEqu = old.getColumnName().equals(smtc.getColumnName());
			boolean propertyTypeEqu = old.getPropertyType().getId().equals(smtc.getPropertyType().getId());
			SystemMainTableColumnType oldColumnType = old.getSystemMainTableColumnType();
			if (oldColumnType != null) {
				boolean columnTypeEqu = old.getSystemMainTableColumnType().getId().equals(
						smtc.getSystemMainTableColumnType().getId());
				boolean result = columnNameEqu && propertyTypeEqu && columnTypeEqu; //
				if (!result) {
					if (smt.getDeployFlag() != null && smt.getDeployFlag().intValue() == 1) {
						// throw new ServiceException("当前用户表已经发布，不可以修改字段名称或类型");
					}
				}
			}

		} else {
			isInsert = true;
		}

		SystemMainTableColumnType smtcType = smtc.getSystemMainTableColumnType();
		smtcType = super.get(SystemMainTableColumnType.class, Long.valueOf(smtcType.getId()));

		PropertyType propType = smtc.getPropertyType();
		propType = super.get(PropertyType.class, Long.valueOf(propType.getId()));

		String tableName = smt.getTableName();
		String columnName = smtc.getColumnName();
		columnName = Character.toLowerCase(columnName.charAt(0)) + columnName.substring(1);
		smtc.setColumnName(columnName);
		smtc.setPropertyName(columnName);
		if (this.existKeyword(columnName)) {
			throw new ServiceException(columnName + "为系统预留关键字，请更换其他字段名");
		}

		if (smtc.getId() == null) {

			if (smtc.getIsHiddenItem() == null) {
				smtc.setIsHiddenItem(0);
			}
			if (smtc.getIsSearchItem() == null) {
				smtc.setIsSearchItem(0);
			}
			if (smtc.getIsUpdateItem() == null) {
				smtc.setIsUpdateItem(1);
			}
			if (smtc.getIsExtItem() == null) {
				smtc.setIsExtItem(0);
			}
			if (smtc.getIsMustInput() == null) {
				smtc.setIsMustInput(0);
			}

			// 判断是否已经存在同名的字段
			Criteria c = super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.ilike("columnName", columnName));
			c.setProjection(Projections.rowCount());
			Integer cncount = (Integer) c.uniqueResult();
			if (cncount > 0) {
				throw new ServiceException("字段名称" + columnName + "已经存在，请更换其他名称");
			}

			c = super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smt));
			c.add(Restrictions.ilike("columnCnName", columnName));
			c.setProjection(Projections.rowCount());
			cncount = (Integer) c.uniqueResult();
			if (cncount > 0) {
				throw new ServiceException("字段中文名称" + columnName + "已经存在，请更换其他名称");
			}

		}

		super.save(smtc);

		// begin
		String eventClassName = smt.getClassName() + "Event";
		// Class clazz = this.getClass(eventClassName);
		Criteria cSmtEvent = super.getCriteria(SystemMainTable.class);
		cSmtEvent.add(Restrictions.eq("className", eventClassName));
		SystemMainTable smtEvent = null;
		List<SystemMainTable> smtEvents = cSmtEvent.list();
		if (!smtEvents.isEmpty()) {
			smtEvent = smtEvents.iterator().next();
		}

		if (isInsert) {
			SystemMainTableColumn smtcEvent = new SystemMainTableColumn();
			try {
				BeanUtils.copyProperties(smtcEvent, smtc);
				smtcEvent.setSystemMainTable(smtEvent);
				smtcEvent.setId(null);
				super.save(smtcEvent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Criteria c = super.getCriteria(SystemMainTableColumn.class);
			c.add(Restrictions.eq("systemMainTable", smtEvent));
			// 这里使用columnName可能出现null指针
			c.add(Restrictions.eq("propertyName", smtc.getPropertyName()));// columnName
			SystemMainTableColumn smtcEvent = (SystemMainTableColumn) c.uniqueResult();
			if (smtcEvent != null) {
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
		// end
		return smtc;
	}

	private boolean existKeyword(String keyword) {
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
		list.add("transient");// end
		list.add("import");
		list.add("package");
		if (list.contains(keyword)) {
			return true;
		}
		return false;

	}

	public void setSystemColumnService(SystemColumnService systemColumnService) {
		this.systemColumnService = systemColumnService;
	}

	public void setSystemMainColumnService(SystemMainColumnService systemMainColumnService) {
		this.systemMainColumnService = systemMainColumnService;
	}

	public void setSystemMainTableService(SystemMainTableService systemMainTableService) {
		this.systemMainTableService = systemMainTableService;
	}

}
