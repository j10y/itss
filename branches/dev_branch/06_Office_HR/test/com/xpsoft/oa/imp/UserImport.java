package com.xpsoft.oa.imp;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xpsoft.core.util.StringUtil;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.hrm.JobService;
import com.xpsoft.oa.service.kpi.HrPaDatadictionaryService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.DepartmentService;

/**
 * 用户导入方法
 * @author wchao
 *
 */
public class UserImport {
	
	private static ApplicationContext context;
	private static String fileName;
	private static JobService jobService;
	private static AppUserService appUserService;
	private static DepartmentService departmentService;
	private static HrPaDatadictionaryService hrPaDatadictionaryService;
	
	
	private static Map<Long, Department> tmpDeptMap = new HashMap<Long, Department>();
	private static Map<String, Long> deptMap = new HashMap<String, Long>();
	private static Map<String, Map<String, Long>> dataMap = new HashMap<String, Map<String, Long>>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
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
			fileName = "C:/imp/用户导入模板-王府井、行政部.xls";
		}else{
			fileName = args[0];
		}
		jobService = (JobService)context.getBean("jobService");
		hrPaDatadictionaryService = (HrPaDatadictionaryService)context.getBean("hrPaDatadictionaryService");
		departmentService = (DepartmentService)context.getBean("departmentService");
		appUserService = (AppUserService)context.getBean("appUserService");
		
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
	 * 导入User数据逻辑
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
					
					String userName = null;
					if(cells[0]!=null && !cells[0].getContents().equals("")){
						userName = cells[0].getContents();
					}else{
						System.out.println("--------Row: " + i + " data not contains userName info");
						continue;
					}
					String fullName = null;
					if(cells[1]!=null && !cells[1].getContents().equals("")){
						fullName = cells[1].getContents();
					}else{
						//fullName = userName;
					}
					
					String title = null;
					if(cells[2]!=null && !cells[2].getContents().equals("")){
						title = cells[1].getContents().equals("男") ? "1" : "0";
					}else{
						title = "1";//男
						//fullName = userName;
					}
					
					String firstDept = null;
					if(cells[3]!=null&& !cells[3].getContents().equals("")){
						firstDept = cells[3].getContents();//一级部门名称	
					}else{
						System.out.println("--------Row: " + i + " data not contains dept info");
						continue;
					}					
					String secondDept = null;
					
					if(cells[4]!=null && !cells[4].getContents().equals("")){
						secondDept = cells[4].getContents();//二级部门名称
						//结合一、二级部门名称匹配部门信息
						depId = deptMap.get(firstDept + "/" + secondDept);
					}else{
						//根据一级部门名称匹配部门信息
						depId = deptMap.get(firstDept);
					}					
					Date accessionTime = null;
					if(cells[5]!=null && !cells[5].getContents().equals("")){
						try {
							accessionTime = sdf.parse(cells[5].getContents());//band
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							accessionTime = new Date();
							e.printStackTrace();
						}
					}else{
						accessionTime = new Date();
					}
					String job = null;
					if(cells[6]!=null){
						job = cells[6].getContents();//岗位
					}
									
					
					AppUser user = null;
					user = appUserService.getByUserName(userName);
					if(user==null){
						user = new AppUser();
						user.setUsername(userName);
						user.setPassword(StringUtil
								.encryptSha256("000000"));//默认000000
						if(fullName!=null && !fullName.equals("")){
							user.setFullname(fullName);
						}else{
							user.setFullname(userName);
						}
					}else{
						if(fullName!=null && !fullName.equals("")){
							user.setFullname(fullName);
						}//else使用原来的
					}
					user.setEmail(userName + "@shopin.cn");
					Department dept = new Department();
					dept.setDepId(depId);
					user.setTitle(Short.valueOf(title));
					user.setDepartment(dept);
					user.setDelFlag((short)0);
					user.setStatus((short)1);
					user.setAccessionTime(accessionTime);
					user.setPosition(job);
					
					appUserService.save(user);
				}
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} finally {
			book.close();
		}
		
	}

}
