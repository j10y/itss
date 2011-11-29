package com.xpsoft.oa.imp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.hrm.JobService;
import com.xpsoft.oa.service.kpi.HrPaDatadictionaryService;
import com.xpsoft.oa.service.system.DepartmentService;

/**
 * 岗位导入方法
 * @author wchao
 *
 */
public class JobImport {
	
	private static ApplicationContext context;
	private static String fileName;
	private static JobService jobService;
	private static DepartmentService departmentService;
	private static HrPaDatadictionaryService hrPaDatadictionaryService;
	
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
			fileName = "C:/imp/岗位导入模板-王府井、行政部.xls";
		}else{
			fileName = args[0];
		}
		jobService = (JobService)context.getBean("jobService");
		hrPaDatadictionaryService = (HrPaDatadictionaryService)context.getBean("hrPaDatadictionaryService");
		departmentService = (DepartmentService)context.getBean("departmentService");
		
		List<HrPaDatadictionary> datas = hrPaDatadictionaryService.getAll();
		for(HrPaDatadictionary hrPaDatadictionary : datas){
			if(hrPaDatadictionary.getParentId()==null){
				continue;
			}
			
			if(hrPaDatadictionary.getParentId().longValue() == 18){//race
				if(dataMap.get("18")!=null){
					if(hrPaDatadictionary.getName().indexOf("-")!=-1)
						dataMap.get("18").put(hrPaDatadictionary.getName().substring(hrPaDatadictionary.getName().indexOf("-")+1), hrPaDatadictionary.getId());
					else
						dataMap.get("18").put(hrPaDatadictionary.getName(), hrPaDatadictionary.getId());
				}else{
					dataMap.put("18", new HashMap<String, Long>());
					if(hrPaDatadictionary.getName().indexOf("-")!=-1)
						dataMap.get("18").put(hrPaDatadictionary.getName().substring(hrPaDatadictionary.getName().indexOf("-")+1), hrPaDatadictionary.getId());
					else
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
		
		System.out.println(deptMap);
		
		System.out.println("----------初始化完毕-----------");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		init(args);
		
		process();

	}

	/**
	 * 导入Job数据逻辑
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
					Long depId = null;
					Long raceId = null;
					Long seqId = null;
					Long bandId = null;
					Cell[] cells = sheet.getRow(i);
					String jobName = null;
					if(cells[0]!=null&& !cells[0].getContents().trim().equals("")){
						jobName = cells[0].getContents().trim();//岗位名称
					}else{
						System.out.println("--------Row: " + i + " data not contains jobName info");
						continue;
					}
					
					String firstDept = null;
					if(cells[1]!=null&& !cells[1].getContents().trim().equals("")){
						firstDept = cells[1].getContents();//一级部门名称	
					}else{
						System.out.println("--------Row: " + i + " data not contains dept info");
						continue;
					}					
					String secondDept = null;
					
					if(cells[2]!=null && !cells[2].getContents().trim().equals("")){
						secondDept = cells[2].getContents();//二级部门名称
						//结合一、二级部门名称匹配部门信息
						depId = deptMap.get(firstDept + "/" + secondDept);
					}else{
						//根据一级部门名称匹配部门信息
						depId = deptMap.get(firstDept);
					}					
					String band = null;
					if(cells[3]!=null && !cells[3].getContents().trim().equals("")){
						band = cells[3].getContents().trim();//band
						//匹配band
						bandId = dataMap.get("24").get(band);
					}
					String race = null;
					if(cells[4]!=null && !cells[4].getContents().trim().equals("")){
						race = cells[4].getContents();//族群
						//匹配族群
						raceId = dataMap.get("18").get(race);
					}
					String seq = null;
					if(cells[5]!=null && !cells[5].getContents().trim().equals("")){
						seq = cells[5].getContents();//序列
						//匹配序列
						seqId = dataMap.get("19").get(seq);
					}
					
					String memo = null;
					if(cells[6]!=null && !cells[6].getContents().trim().equals("")){
						memo = cells[6].getContents();//说明
					}
					Job job = new Job();
					job.setJobName(jobName);
					Department dept = new Department();
					dept.setDepId(depId);
					job.setDepartment(dept);
					
					if(raceId != null){
						HrPaDatadictionary r = new HrPaDatadictionary();
						r.setId(raceId);
						job.setRace(r);
					}
					if (seqId != null) {
						HrPaDatadictionary s = new HrPaDatadictionary();
						s.setId(seqId);
						job.setSeq(s);
					}
					if (bandId != null) {
						HrPaDatadictionary b = new HrPaDatadictionary();
						b.setId(bandId);
						job.setBand(b);
					}
					job.setMemo(memo);
					job.setDelFlag((short)0);
					//执行insert
					System.out.println(job.toString());
					jobService.save(job);
				}
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			book.close();
		}
		
	}

}
