package com.xpsoft.oa.action.kpi;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.kpi.HrPaAcreached;
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;
import com.xpsoft.oa.model.kpi.HrPaCategory;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.kpi.HrPaAcreachedService;
import com.xpsoft.oa.service.kpi.HrPaAssessmentcriteriaService;
import com.xpsoft.oa.service.kpi.HrPaCategoryService;
import com.xpsoft.oa.service.system.AppUserService;

public class HrPaAcreachedAction extends BaseAction{
	@Resource
	private HrPaAcreachedService hrPaAcreachedService;
	private HrPaAcreached hrPaAcreached;
	private long id;
	
	public HrPaAcreachedService getHrPaAcreachedService() {
		return hrPaAcreachedService;
	}
	public void setHrPaAcreachedService(HrPaAcreachedService hrPaAcreachedService) {
		this.hrPaAcreachedService = hrPaAcreachedService;
	}
	public HrPaAcreached getHrPaAcreached() {
		return hrPaAcreached;
	}
	public void setHrPaAcreached(HrPaAcreached hrPaAcreached) {
		this.hrPaAcreached = hrPaAcreached;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list(){
		return "success";
	}
	
	public String get(){
		return "success";
	}
	
	public String save(){
		this.hrPaAcreachedService.save(this.hrPaAcreached);
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	
	public String createExcel() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date currentDate = new Date();
		String yearMonth = sdf.format(currentDate);
		//获取要导出的考核标准列表
		HrPaAssessmentcriteriaService hrPaAssessmentcriteriaService = (HrPaAssessmentcriteriaService)AppUtil.getBean("hrPaAssessmentcriteriaService");
		List<HrPaAssessmentcriteria> criteriaList = new ArrayList<HrPaAssessmentcriteria>();
		//取得考核标准ID数组，以”,“拆分
		String[] acIds = this.getRequest().getParameter("acIds").split(",");
		//取得考核标准List
		for(int m = 0; m < acIds.length; m++) {
			criteriaList.add(hrPaAssessmentcriteriaService.get(Long.parseLong(acIds[m])));
		}
		//定义excel模板文件名
		String path = this.getRequest().getRealPath("/") + "attachFiles/kpiReached/" + 
				ContextUtil.getCurrentUser().getUsername() + yearMonth + "_reach.xls";
		String downloadFileType = this.getRequest().getParameter("downloadFileType");
		if("1".equals(downloadFileType)) {
			//从品类表中取得所有品类
			HrPaCategoryService hrPaCategoryService = (HrPaCategoryService)AppUtil.getBean("hrPaCategoryService");
			List<HrPaCategory> categoryList = hrPaCategoryService.getAll();
			//开始创建excel模板
			try {
				File file = new File(path);
				//删除已存在的excel模板及所在文件夹
				if(!file.getParentFile().exists()) {
					if(!file.getParentFile().getParentFile().exists()) {
						file.getParentFile().getParentFile().mkdirs();
					}
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
				//创建excel模板详细内容
				WritableWorkbook book = Workbook.createWorkbook(file);
				WritableSheet sheet = book.createSheet("第一页", 0);
				for(int i = 0; i < criteriaList.size(); i++) {
					Label label1 = new Label(i+2, 0, String.valueOf(criteriaList.get(i).getId()));
					Label label2 = new Label(i+2, 1, criteriaList.get(i).getAcName());
					sheet.addCell(label1);
					sheet.addCell(label2);
				}
				for(int j = 0; j < categoryList.size(); j++) {
					Label label3 = new Label(0, j+2, String.valueOf(categoryList.get(j).getId()));
					Label label4 = new Label(1, j+2, categoryList.get(j).getName());
					sheet.addCell(label3);
					sheet.addCell(label4);
				}
				//设置excel标签页标题
				WritableFont font1 = new WritableFont(WritableFont.createFont("黑体_GB2312"), 13, WritableFont.NO_BOLD);
				WritableCellFormat format1 = new WritableCellFormat(font1);
				format1.setAlignment(Alignment.CENTRE);
				format1.setVerticalAlignment(VerticalAlignment.CENTRE);
				Label titleLabel = new Label(0, 0, "达成excel模板", format1);
				sheet.addCell(titleLabel);
				sheet.mergeCells(0, 0, 1, 1);
				book.write();
				book.close();
				this.jsonString = "{success:true,'filePath':'" + "/attachFiles/kpiReached/" + 
						ContextUtil.getCurrentUser().getUsername() + yearMonth + "_reach.xls" + "'}";
				System.out.println(this.jsonString);
			} catch(Exception e) {
				System.out.println("创建文件失败");
				e.printStackTrace();
			}
		} else if("2".equals(downloadFileType)) {
			//从档案表中取得所有UserId
			EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
			List<EmpProfile> empProfileList = empProfileService.getAll();
			//从用户表里边取得所有有档案的User
			AppUserService appUserService = (AppUserService)AppUtil.getBean("appUserService");
			List<AppUser> userList = new ArrayList<AppUser>();
			for(int i = 0; i < empProfileList.size(); i++) {
				userList.add(appUserService.get(empProfileList.get(i).getUserId()));
			}
			//开始创建excel模板
			try {
				File file = new File(path);
				//删除已存在的excel模板及所在文件夹
				if(!file.getParentFile().exists()) {
					if(!file.getParentFile().getParentFile().exists()) {
						file.getParentFile().getParentFile().mkdirs();
					}
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
				//创建excel模板详细内容
				WritableWorkbook book = Workbook.createWorkbook(file);
				WritableSheet sheet = book.createSheet("第一页", 0);
				for(int i = 0; i < criteriaList.size(); i++) {
					Label label1 = new Label(i+3, 0,  String.valueOf(criteriaList.get(i).getId()));
					Label label2 = new Label(i+3, 1, criteriaList.get(i).getAcName());
					sheet.addCell(label1);
					sheet.addCell(label2);
				}
				for(int j = 0; j < userList.size(); j++) {
					Label label3 = new Label(0, j+2, String.valueOf(userList.get(j).getUserId()));
					Label label4 = new Label(1, j+2, userList.get(j).getDepartment().getDepName());
					Label label5 = new Label(2, j+2, userList.get(j).getFullname());
					sheet.addCell(label3);
					sheet.addCell(label4);
					sheet.addCell(label5);
				}
				//设置excel标签页标题
				WritableFont font1 = new WritableFont(WritableFont.createFont("黑体_GB2312"), 13, WritableFont.NO_BOLD);
				WritableCellFormat format1 = new WritableCellFormat(font1);
				format1.setAlignment(Alignment.CENTRE);
				format1.setVerticalAlignment(VerticalAlignment.CENTRE);
				Label titleLabel = new Label(0, 0, "达成excel模板", format1);
				sheet.addCell(titleLabel);
				sheet.mergeCells(0, 0, 2, 1);
				book.write();
				book.close();
				this.jsonString = "{success:true,'filePath':'" + "/attachFiles/kpiReached/" + 
						ContextUtil.getCurrentUser().getUsername() + yearMonth + "_reach.xls" + "'}";
			} catch(Exception e) {
				
			}
		}
		
		return "success";
	}
	
	public String readExcel() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		String filePath = this.getRequest().getParameter("filePath");
		//用来存放读取出来的数据
		List<HrPaAcreached> reachList = new ArrayList<HrPaAcreached>();
		File file = new File(this.getRequest().getRealPath("/") + "attachFiles/" + filePath);
		String uploadFileType = this.getRequest().getParameter("uploadFileType");
		try {
			//取得要导入的excel文件
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			int col = sheet.getColumns();
			int row = sheet.getRows();
			if("1".equals(uploadFileType)) {//按品类导入
				for(int i = 2; i < col; i++) {
					for(int j = 2; j < row; j++) {
						if(sheet.getCell(i, j) != null && !"".equals(sheet.getCell(i, j).getContents().trim())) {
							HrPaAcreached reach = new HrPaAcreached();
							reach.setAcId(Long.parseLong(sheet.getCell(i, 0).getContents()));
							reach.setCategory(Long.parseLong(sheet.getCell(0, j).getContents()));
							reach.setResult(Double.parseDouble(sheet.getCell(i, j).getContents()));
							reach.setInputDate(currentDate);
							reach.setInputPerson(currentUser.getUserId());
							reachList.add(reach);
						}
					}
				}
				this.hrPaAcreachedService.multiSave(reachList, "category");
			} else if("2".equals(uploadFileType)) {//按人员导入
				for(int i = 3; i < col; i++) {
					for(int j = 2; j < row; j++) {
						if(sheet.getCell(i, j) != null && !"".equals(sheet.getCell(i, j).getContents().trim())) {
							HrPaAcreached reach = new HrPaAcreached();
							reach.setAcId(Long.parseLong(sheet.getCell(i, 0).getContents()));
							reach.setUserId(Long.parseLong(sheet.getCell(0, j).getContents()));
							reach.setResult(Double.parseDouble(sheet.getCell(i, j).getContents()));
							reach.setInputDate(currentDate);
							reach.setInputPerson(currentUser.getUserId());
							reachList.add(reach);
						}
					}
				}
				this.hrPaAcreachedService.multiSave(reachList, "user");
			}
			this.jsonString = "{success:true,'flag':'1','count':'" + reachList.size() + "'}";
		} catch(Exception e) {
			System.out.println("数据导入失败！");
			this.jsonString = "{success:true,'flag':'0'}";
			e.printStackTrace();
		} finally {
			if(file.exists()) {
				file.delete();
				file.getParentFile().delete();
			}
		}
		
		return "success";
	}
}
