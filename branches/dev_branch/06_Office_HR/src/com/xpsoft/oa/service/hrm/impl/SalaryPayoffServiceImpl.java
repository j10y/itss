package com.xpsoft.oa.service.hrm.impl;

import java.io.File;
import java.io.FileOutputStream;
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
import com.xpsoft.oa.dao.hrm.SalaryPayoffDao;
import com.xpsoft.oa.model.hrm.Budget;
import com.xpsoft.oa.model.hrm.BudgetItem;
import com.xpsoft.oa.model.hrm.RealExecution;
import com.xpsoft.oa.model.hrm.SalaryPayoff;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.BudgetItemService;
import com.xpsoft.oa.service.hrm.BudgetService;
import com.xpsoft.oa.service.hrm.RealExecutionService;
import com.xpsoft.oa.service.hrm.SalaryPayoffService;
import com.xpsoft.oa.service.system.AppUserService;

public class SalaryPayoffServiceImpl extends BaseServiceImpl<SalaryPayoff>
		implements SalaryPayoffService {
	private SalaryPayoffDao dao;

	public SalaryPayoffServiceImpl(SalaryPayoffDao dao) {
		super(dao);
		this.dao = dao;
	}

	public String exportData(String fileRootPath, String sheetName,
			String filePrefix, String[] titles, String[] proNames,
			List dataList, String exportType) {
		// TODO Auto-generated method stub
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
		for (int ii = 0; ii < sheetSum; ii++) {
			HSSFSheet sheet = wb.createSheet();
			wb.setSheetName(ii, sheetName + (ii + 1));

			// 建立标题行，0行
			HSSFRow row = sheet.createRow(0);
			row.setHeight((short) 400);
			for (int iii = 0; iii < titles.length; iii++) {
				HSSFCell cell = row.createCell((short) iii); // 建立标题记录行，从第1行开始
																// *******
				cell.setCellStyle(cellStyle);
				cell.setCellValue(titles[iii]);
			}

			for (int rows = 0; rows < dataList.size(); rows++) {
				// System.out.println(rows);
				if (exportType.equals("maplist")) {
					Map npl = (Map) dataList.get(rows);
					HSSFRow rowj = null; // 建立当前记录的行 *******
					try {
						rowj = sheet.createRow((short) (rows + 1));
					} catch (Exception e) {
						e.printStackTrace();
					}
					for (int iii = 0; iii < proNames.length; iii++) {
						HSSFCell cellk = rowj.createCell((short) iii);
						Object ob = npl.get(proNames[iii]);
						cellk.setCellValue(ob == null ? "" : ob.toString());
					}
				} else if (exportType.equals("objlist")) {
					Object npl = (Object) dataList.get(rows);
					BeanWrapper bw = new BeanWrapperImpl(npl);
					HSSFRow rowj = null; // 建立当前记录的行 *******
					try {
						rowj = sheet.createRow((short) (rows + 1));
					} catch (Exception e) {
						e.printStackTrace();
					}
					for (int iii = 0; iii < proNames.length; iii++) {
						HSSFCell cellk = rowj.createCell((short) iii);
						Object ob = bw.getPropertyValue(proNames[iii]);
						cellk.setCellValue(ob == null ? "" : ob.toString());
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
	private String deleteFile(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			file.delete();
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			Date currDate = new Date();
			Long currDates = currDate.getTime();
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(path + "\\" + filelist[i]);
				Long lastModified = delfile.lastModified();
				// String
				// times=AppUtil..getProperties("system.removefiletime","5");
				// Integer time=Integer.parseInt(times);
				// if((currDates-lastModified)>time*60*1000){
				// delfile.delete();
				// }
			}
		}
		return null;
	}
	public boolean saveRealexecution(Long userId,Double amount,Integer month){
		AppUserService appUserService=(AppUserService) AppUtil.getBean("appUserService");
		AppUser appuser=appUserService.get(userId);
		Long depid=appuser.getDepartment().getDepId();
		BudgetService budgetService=(BudgetService) AppUtil.getBean("budgetService");
		Map map=new HashMap();
		map.put("Q_belongDept.depId_L_EQ", depid+"");
		map.put("Q_beginDate_D_LE", DateUtil.formatStringToDate(new Date())+"");
		map.put("Q_endDate_D_GE", DateUtil.formatStringToDate(new Date())+"");
		map.put("Q_publishStatus_N_EQ", "3");
		QueryFilter filter=new QueryFilter(map);
		List list=budgetService.getAll(filter);
		if(list.size()>0){
			Budget budget=(Budget) list.get(0);
			Long buid=budget.getBudgetId();
			BudgetItemService budgetItemService=(BudgetItemService) AppUtil.getBean("budgetItemService");
			Map bimap=new HashMap();
			bimap.put("Q_budget.budgetId_L_EQ", buid+"");
			bimap.put("Q_isDefault_N_EQ", "1");
			QueryFilter bifilter=new QueryFilter(bimap);
			List bilist=budgetItemService.getAll(bifilter);
			if(bilist!=null&&bilist.size()>0){
				BudgetItem budgetItem=(BudgetItem) bilist.get(0);
				Long biid=budgetItem.getBudgetItemId();
				RealExecutionService realExecutionService=(RealExecutionService) AppUtil.getBean("realExecutionService");
				Map remap=new HashMap();
				remap.put("Q_budgetItem.budgetItemId_L_EQ", biid+"");
				remap.put("Q_budget.budgetId_L_EQ", buid+"");
				remap.put("Q_month_N_EQ", month+"");
				QueryFilter refilter=new QueryFilter(remap);
				List<RealExecution> relist =realExecutionService.getAll(refilter);
				RealExecution re=null;
				if(relist!=null&&relist.size()>0){
					 re=relist.get(0);
					re.setRealValue(re.getRealValue()+amount);
				}else{
					re=new RealExecution();
					Budget bg=new Budget();
					bg.setBudgetId(buid);
					re.setBudget(bg);
					BudgetItem  bi=new BudgetItem();
					bi.setBudgetItemId(biid);
					re.setBudgetItem(bi);
					re.setRealValue(amount);
					re.setInputDate(new Date());
					re.setMonth(month);
					re.setRemark("薪资");
					re.setDeleteFlag(0);
				}
				realExecutionService.save(re);
			}
		}
		return true;
	}
}
