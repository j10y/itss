package com.xpsoft.oa.imp;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.hrm.StandSalary;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.hrm.JobService;
import com.xpsoft.oa.service.hrm.StandSalaryService;
import com.xpsoft.oa.service.kpi.HrPaDatadictionaryService;
import com.xpsoft.oa.service.system.DepartmentService;

/**
 * 薪资标准导入方法
 * @author wchao
 *
 */
public class StandSalaryImport {
	
	private static ApplicationContext context;
	private static String fileName;
	private static JobService jobService;
	private static DepartmentService departmentService;
	private static HrPaDatadictionaryService hrPaDatadictionaryService;
	
	private static StandSalaryService standSalaryService;
	
	private static Map<Long, Department> tmpDeptMap = new HashMap<Long, Department>();
	private static Map<String, Long> deptMap = new HashMap<String, Long>();
	private static Map<String, Map<String, Long>> dataMap = new HashMap<String, Map<String, Long>>();
	
	
	/**
	 * 初始化方法<br>
	 * 1,Spring初始化<br>
	 * 2,系统初始化
	 */
	private static void init(String[] args){
		System.out.println("----------初始化开始-----------");
		context = new ClassPathXmlApplicationContext("conf/app-context.xml");
		if(args.length<=0){
			//throw new RuntimeException("Usage: please input the import template file name!");
			fileName = "C:/imp/jobImp.xls";
		}else{
			fileName = args[0];
		}
		standSalaryService = (StandSalaryService)context.getBean("standSalaryService");
		/*jobService = (JobService)context.getBean("jobService");
		hrPaDatadictionaryService = (HrPaDatadictionaryService)context.getBean("hrPaDatadictionaryService");
		departmentService = (DepartmentService)context.getBean("departmentService");
		
		List<HrPaDatadictionary> datas = hrPaDatadictionaryService.getAll();
		for(HrPaDatadictionary hrPaDatadictionary : datas){
			if(hrPaDatadictionary.getParentId()==null){
				continue;
			}
			
			if(hrPaDatadictionary.getParentId().longValue() == 18){//race
				if(dataMap.get("18")!=null){
					dataMap.get("18").put(hrPaDatadictionary.getName(), hrPaDatadictionary.getId());
				}else{
					dataMap.put("18", new HashMap<String, Long>());
					dataMap.get("18").put(hrPaDatadictionary.getName(), hrPaDatadictionary.getId());
				}
			}else if(hrPaDatadictionary.getParentId().longValue() == 19){//seq
				if(dataMap.get("19")!=null){
					dataMap.get("19").put(hrPaDatadictionary.getName(), hrPaDatadictionary.getId());
				}else{
					dataMap.put("19", new HashMap<String, Long>());
					dataMap.get("19").put(hrPaDatadictionary.getName(), hrPaDatadictionary.getId());
				}
			}else if(hrPaDatadictionary.getParentId().longValue() == 24){//band
				if(dataMap.get("24")!=null){
					dataMap.get("24").put(hrPaDatadictionary.getName(), hrPaDatadictionary.getId());
				}else{
					dataMap.put("24", new HashMap<String, Long>());
					dataMap.get("24").put(hrPaDatadictionary.getName(), hrPaDatadictionary.getId());
				}
			}
		}
		
		List<Department> depts = departmentService.getAll();
		
		for(Department dept : depts){
			tmpDeptMap.put(dept.getDepId(), dept);
		}
		
		for(Department dept : depts){
			if(dept.getDepLevel().intValue()!=1){
				if(dept.getDepLevel().intValue() == 2){//
					deptMap.put(dept.getDepDesc(), dept.getDepId());
				}else if(dept.getDepLevel().intValue() == 3){
					//格式：一级部门名称/二级部门名称
					deptMap.put(tmpDeptMap.get(dept.getParentId()).getDepName() + "/" + dept.getDepName(), dept.getDepId());
				}
			}
		}
		
		System.out.println("----------pring datas-----------");
		
		System.out.println(dataMap);
		
		System.out.println("----------pring deptMap-----------");
		
		System.out.println(deptMap);*/
		
		System.out.println("----------初始化完毕-----------");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		init(args);
		
		//process();

	}

	/**
	 * 导入StandSalary数据逻辑
	 */
	private static void process() {
		// TODO Auto-generated method stub
		Workbook book = null;
		try {
			try {
				book = Workbook.getWorkbook(new File(fileName));
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			Sheet sheet = book.getSheet(0);
			int count = sheet.getRows();
			if(count<=1){
				return;
			}else{
				for(int i=1; i < count; i++){

					Cell[] cells = sheet.getRow(i);
					String salaryName = null;
					if(cells[0]!=null){
						salaryName = cells[0].toString();//岗位名称
					}else{
						System.out.println("--------Row: " + i + " data not contains salaryName info");
						continue;
					}
					
					//固定薪资
					BigDecimal baseMoney = null;
					if(cells[1]!=null){
						baseMoney = new BigDecimal(cells[1].toString());
					}
					
					//绩效基数
					BigDecimal perCoefficient = null;
					if(cells[2]!=null){
						perCoefficient = new BigDecimal(cells[2].toString());
					}
					
					//月度薪酬总额
					BigDecimal totalMoney = null;
					if(cells[3]!=null){
						totalMoney = new BigDecimal(cells[3].toString());
					}
					
					String memo = null;
					if(cells[6]!=null){
						memo = cells[6].toString();//说明
					}
					
					StandSalary salary = new StandSalary();
					
					SimpleDateFormat date = new SimpleDateFormat(
					"yyyyMMddHHmmss-SSSS");
					String standardNo = date.format(new Date());
					salary.setStandardNo(standardNo);
					salary.setBaseMoney(baseMoney);
					salary.setPerCoefficient(perCoefficient);
					salary.setTotalMoney(totalMoney);
					
					salary.setStandardName(salaryName);
					
					salary.setMemo(memo);
					
					//执行insert
					standSalaryService.save(salary);
				}
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			book.close();
		}
		
	}

}
