package com.xpsoft.oa.service.hrm.impl;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.DateUtil;
import com.xpsoft.oa.dao.hrm.ExportSalaryDao;
import com.xpsoft.oa.model.hrm.ExportSalary;
import com.xpsoft.oa.model.hrm.ExportSalaryItemOrder;
import com.xpsoft.oa.service.hrm.ExportSalaryItemService;
import com.xpsoft.oa.service.hrm.ExportSalaryService;


public class ExportSalaryServiceImpl extends BaseServiceImpl<ExportSalary>
		implements ExportSalaryService {
	private ExportSalaryDao dao;

	public ExportSalaryServiceImpl(ExportSalaryDao dao) {
		super(dao);
		this.dao = dao;
	}
	public String exportSalary(String fileRootPath, String sheetName,String filePrefix,String sql,String exportSaId ){
		Date curryDate=DateUtil.parseDate(DateUtil.convertDateToString(new Date()));
		List dataList=this.findDataList(sql);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 设置标题上下居中
		int totalCount = dataList.size();
		int pageSize = 5000;
		int sheetSum = 0;
		if (totalCount % pageSize == 0) {
			sheetSum = totalCount / pageSize;
		} else {
			sheetSum = totalCount / pageSize + 1;
		}
		ExportSalaryItemService exportSalaryItemService=(ExportSalaryItemService) AppUtil.getBean("exportSalaryItemService");
		Map map=new HashMap();
		map.put("Q_exportSalId.id_L_EQ", exportSaId);
		map.put("sort", "orderCol");
		map.put("dir", "asc");
		QueryFilter filter=new QueryFilter(map);
		List<ExportSalaryItemOrder> list=exportSalaryItemService.getAll(filter);
		for (int ii = 0; ii < sheetSum; ii++) {
			HSSFSheet sheet = wb.createSheet();
			wb.setSheetName(ii, sheetName + (ii + 1));
			// 建立标题行，0行
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 400);
			HSSFCell cell0 = row.createCell((short) 0); 
			cell0.setCellStyle(cellStyle);
			cell0.setCellValue("序号");
			int i=1;
			for (int iii = 0; iii < list.size(); iii++) {
				ExportSalaryItemOrder exportSalaryItemOrder=list.get(iii);
				if(exportSalaryItemOrder.getFromTable().equals("1")){
					HSSFCell cell = row.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
					cell.setCellStyle(cellStyle);
					cell.setCellValue(exportSalaryItemOrder.getExportName());
				}else if(exportSalaryItemOrder.getFromTable().equals("2")){
					HSSFCell cell = row.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
					cell.setCellStyle(cellStyle);
					cell.setCellValue(exportSalaryItemOrder.getExportName());
					sheet.setColumnWidth(i, 0);
					HSSFCell cell1 = row.createCell((short)i++ ); // 建立标题记录行，从第1行开始			// *******
					cell1.setCellStyle(cellStyle);
					cell1.setCellValue(exportSalaryItemOrder.getExportName()+"描述");
				}else if(exportSalaryItemOrder.getFromTable().equals("3")){
					HSSFCell cell = row.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
					cell.setCellStyle(cellStyle);
					cell.setCellValue(exportSalaryItemOrder.getExportName());
				}else if(exportSalaryItemOrder.getFromTable().equals("4")){
					HSSFCell cell = row.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
					cell.setCellStyle(cellStyle);
					cell.setCellValue(exportSalaryItemOrder.getExportName());
				}
			}

			for (int rows = 0; rows < dataList.size(); rows++) {
				HSSFRow rowj = null; // 建立当前记录的行 *******
				try {
					rowj = sheet.createRow((short) (rows + 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
				HSSFCell cell10 = rowj.createCell((short) 0); 
				cell10.setCellStyle(cellStyle);
				cell10.setCellValue(rows+1);
				
				Map npl = (Map) dataList.get(rows);
				String userId=npl.get("userId").toString();
				Date sdate=DateUtil.parseDate(npl.get("startTime").toString().substring(0,19));
				String sd=DateUtil.convertDateToString(DateUtil.addMonths(sdate, -1));
				String ed=DateUtil.convertDateToString(DateUtil.addMonths(sdate, 0));
				DecimalFormat myformat = new DecimalFormat();
				myformat.applyPattern("###.00");
				String rpsql="select hr_sr_rewardspunishmentstype.typeId,hr_sr_rewardspunishmentstype.typeName,hr_sr_rewardsPunishments.amount,hr_sr_rewardsPunishments.remark from hr_sr_rewardsPunishments,hr_sr_rewardspunishmentstype " +
				"where userId="+userId+" and hr_sr_rewardsPunishments.rpType=hr_sr_rewardspunishmentstype.typeId " +
				"and DATE_FORMAT(hr_sr_rewardsPunishments.createDate,'%Y-%m')>DATE_FORMAT('"+sd+"','%Y-%m') " +
				"and DATE_FORMAT(hr_sr_rewardsPunishments.createDate,'%Y-%m')<=DATE_FORMAT('"+ed+"','%Y-%m') " ;
				List<Map> rplist=super.findDataList(rpsql);
				Map rpmap=new HashMap();
				for(Map rp:rplist){
					String id=rp.get("typeId").toString();
					if(rpmap.get(id)!=null){
						Map mapi=(Map) rpmap.get(id);
						String m=myformat.format(Double.parseDouble(mapi.get("amount").toString())+Double.parseDouble(rp.get("amount").toString()));
						if(rp.get("remark")!=null&&rp.get("remark").toString().length()>0){
							String remark=mapi.get("remark").toString()+","+rp.get("remark").toString();
							mapi.put("remark", remark);
						}
						mapi.put("amount", m);
						rpmap.put(id,mapi);
					}else{
						String m=myformat.format(Double.parseDouble(rp.get("amount").toString()));
						String remark=rp.get("remark").toString();
						Map mapi=new HashMap();
						mapi.put("amount", m);
						mapi.put("remark", remark);
						rpmap.put(id,mapi);
					}
				}
				String standardItemSql="select salary_item.salaryItemId,salary_item.defaultVal from emp_profile,salary_item,stand_salary,stand_salary_item " +
						"where emp_profile.standardId=stand_salary.standardId " +
						"and stand_salary.standardId=stand_salary_item.standardId " +
						"and stand_salary_item.salaryItemId=salary_item.salaryItemId " +
						"and emp_profile.userId="+userId;
				List<Map> standardItemlist=super.findDataList(standardItemSql);
				Map simap=new HashMap();
				for(Map si:standardItemlist){
					String id=si.get("salaryItemId").toString();
					if(simap.get(id)!=null){
						String m=myformat.format(Double.parseDouble(simap.get(id).toString())+Double.parseDouble(si.get("defaultVal").toString()));
						simap.put(id, m);
					}else{
						String m=myformat.format(Double.parseDouble(si.get("defaultVal").toString()));
						simap.put(id, m);
					}
				}
				i=1;
				for (int iii = 0; iii < list.size(); iii++) {
					ExportSalaryItemOrder exportSalaryItemOrder=list.get(iii);
					if(exportSalaryItemOrder.getFromTable().equals("1")){//表示从薪标里获取数据
						HSSFCell cell = rowj.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
						cell.setCellStyle(cellStyle);
						Object ob=simap.get(exportSalaryItemOrder.getFromTableType().toString());
						cell.setCellValue(ob == null ? "" : ob.toString());
					}else if(exportSalaryItemOrder.getFromTable().equals("2")){//表示从奖惩里获取数据
						HSSFCell cell = rowj.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
						cell.setCellStyle(cellStyle);
						Object ob=rpmap.get(exportSalaryItemOrder.getFromTableType().toString());
						cell.setCellValue(ob == null ? "" : ((Map)ob).get("amount")+"");
						HSSFCell cell1 = rowj.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
						cell1.setCellStyle(cellStyle);
						cell1.setCellValue(ob == null ? "" :((Map)ob).get("remark")+"");
					}else if(exportSalaryItemOrder.getFromTable().equals("3")){//默认，从导出的sql语句里获取数据
						HSSFCell cell = rowj.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
						cell.setCellStyle(cellStyle);
						Object ob = npl.get(exportSalaryItemOrder.getFromTableName());
						cell.setCellValue(ob == null ? "" : ob.toString());
					}else if(exportSalaryItemOrder.getFromTable().equals("4")){//判断员工是否是正式员工
						HSSFCell cell = rowj.createCell((short) i++); // 建立标题记录行，从第1行开始			// *******
						cell.setCellStyle(cellStyle);
						Object ob = npl.get(exportSalaryItemOrder.getFromTableName());
						if(ob!=null){
							Date podate=DateUtil.parseDate(ob.toString().substring(0,19));
							if(curryDate.getTime()>=podate.getTime()){
								cell.setCellValue("正式员工");
							}else{
								cell.setCellValue("试用期");
							}
						}else
							cell.setCellValue("试用期");
					}
				}
			}
		}
		String excelFileName = null;
		final String FSP = System.getProperty("file.separator");
		try {
			// 获得Excel文件的真实路径
			//deleteFile(fileRootPath);
			excelFileName = filePrefix + "_" + System.currentTimeMillis()
					+ ".xls";
			String excelFullFileName = fileRootPath + FSP + excelFileName;
			// deleteFile(excelFullFileName);//先删除原文件
			FileOutputStream fileout = new FileOutputStream(excelFullFileName);
			wb.write(fileout);
			fileout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelFileName;
	}
}
