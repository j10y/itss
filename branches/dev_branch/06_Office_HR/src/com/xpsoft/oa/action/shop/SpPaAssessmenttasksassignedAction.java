package com.xpsoft.oa.action.shop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.biff.WritableFormattingRecords;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.model.Ftp;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.UUIDGenerator;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.EmpProfile;
import com.xpsoft.oa.model.shop.SpPaAssessmentcriteria;
import com.xpsoft.oa.model.shop.SpPaAssessmenttasksassigned;
import com.xpsoft.oa.model.shop.SpPaCategory;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.shop.SpPaAssessmentcriteriaService;
import com.xpsoft.oa.service.shop.SpPaAssessmenttasksassignedService;
import com.xpsoft.oa.service.shop.SpPaCategoryService;
import com.xpsoft.oa.service.system.AppUserService;

public class SpPaAssessmenttasksassignedAction extends BaseAction{
	@Resource
	private SpPaAssessmenttasksassignedService spPaAssessmenttasksassignedService;
	private SpPaAssessmenttasksassigned spPaAssessmenttasksassigned;
	private long id;
	
	public SpPaAssessmenttasksassignedService getSpPaAssessmenttasksassignedService() {
		return spPaAssessmenttasksassignedService;
	}
	public void setSpPaAssessmenttasksassignedService(
			SpPaAssessmenttasksassignedService spPaAssessmenttasksassignedService) {
		this.spPaAssessmenttasksassignedService = spPaAssessmenttasksassignedService;
	}
	public SpPaAssessmenttasksassigned getSpPaAssessmenttasksassigned() {
		return spPaAssessmenttasksassigned;
	}
	public void setSpPaAssessmenttasksassigned(
			SpPaAssessmenttasksassigned spPaAssessmenttasksassigned) {
		this.spPaAssessmenttasksassigned = spPaAssessmenttasksassigned;
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
		this.spPaAssessmenttasksassignedService.save(this.spPaAssessmenttasksassigned);
		this.jsonString = new String("{success:true}");
		return "success";
	}
	
	public String createExcel() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date currentDate = new Date();
		String yearMonth = sdf.format(currentDate);
		//获取要导出的考核标准列表
		SpPaAssessmentcriteriaService spPaAssessmentcriteriaService = (SpPaAssessmentcriteriaService)AppUtil.getBean("spPaAssessmentcriteriaService");
		List<SpPaAssessmentcriteria> criteriaList = new ArrayList<SpPaAssessmentcriteria>();
		//取得考核标准ID数组，以”,“拆分
		String[] acIds = this.getRequest().getParameter("acIds").split(",");
		//取得考核标准List
		for(int m = 0; m < acIds.length; m++) {
			criteriaList.add(spPaAssessmentcriteriaService.get(Long.parseLong(acIds[m])));
		}
		//定义excel模板文件名
		String path = this.getRequest().getRealPath("/") + "attachFiles/kpiTasksassigned/" + 
				ContextUtil.getCurrentUser().getUsername() + yearMonth + "_target.xls";
		String downloadFileType = this.getRequest().getParameter("downloadFileType");
		if("1".equals(downloadFileType)) {
			//从品类表中取得所有品类
			SpPaCategoryService spPaCategoryService = (SpPaCategoryService)AppUtil.getBean("spPaCategoryService");
			List<SpPaCategory> categoryList = spPaCategoryService.getAll();
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
					Label label1 = new Label(i+2, 0,  String.valueOf(criteriaList.get(i).getId()));
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
				//设置模板编号
				Label numberLabel = new Label(0, 0, UUIDGenerator.getUUID());
				sheet.addCell(numberLabel);
				sheet.mergeCells(0, 0, 1, 0);
				//设置excel标签页标题
				WritableFont font1 = new WritableFont(WritableFont.createFont("黑体_GB2312"), 13, WritableFont.NO_BOLD);
				WritableCellFormat format1 = new WritableCellFormat(font1);
				format1.setAlignment(Alignment.CENTRE);
				format1.setVerticalAlignment(VerticalAlignment.CENTRE);
				Label titleLabel = new Label(0, 1, "目标excel模板", format1);
				sheet.addCell(titleLabel);
				sheet.mergeCells(0, 1, 1, 1);
				book.write();
				book.close();
				this.jsonString = "{success:true,'filePath':'" + "/attachFiles/kpiTasksassigned/" + 
						ContextUtil.getCurrentUser().getUsername() + yearMonth + "_target.xls" + "'}";
			} catch(Exception e) {
				System.out.println("创建文件失败");
				e.printStackTrace();
			}
		} else if("2".equals(downloadFileType)) {
			//从档案表中取得所有UserId
			EmpProfileService empProfileService = (EmpProfileService)AppUtil.getBean("empProfileService");
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_delFlag_SN_EQ", "0");
			String depId = this.getRequest().getParameter("deptId");
			if(!"0".equals(depId)) {
				map.put("Q_depId_L_EQ", depId);
			}
			QueryFilter filter = new QueryFilter(map);
			List<EmpProfile> empProfileList = empProfileService.getAll(filter);
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
				//设置模板编号
				Label numberLabel = new Label(0, 0, UUIDGenerator.getUUID());
				sheet.addCell(numberLabel);
				sheet.mergeCells(0, 0, 2, 0);
				//设置excel标签页标题
				WritableFont font1 = new WritableFont(WritableFont.createFont("黑体_GB2312"), 13, WritableFont.NO_BOLD);
				WritableCellFormat format1 = new WritableCellFormat(font1);
				format1.setAlignment(Alignment.CENTRE);
				format1.setVerticalAlignment(VerticalAlignment.CENTRE);
				Label titleLabel = new Label(0, 1, "目标excel模板", format1);
				sheet.addCell(titleLabel);
				sheet.mergeCells(0, 1, 2, 1);
				book.write();
				book.close();
				this.jsonString = "{success:true,'filePath':'" + "/attachFiles/kpiTasksassigned/" + 
						ContextUtil.getCurrentUser().getUsername() + yearMonth + "_target.xls" + "'}";
			} catch(Exception e) {
				System.out.println("创建文件失败");
				e.printStackTrace();
			}
		}
		
		return "success";
	}
	
	public String readExcel() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		String filePath = this.getRequest().getParameter("filePath");
		//用来存放读取出的数据
		List<SpPaAssessmenttasksassigned> assignList = new ArrayList<SpPaAssessmenttasksassigned>();


		//modify by jack for FTP support at 2011-9-21 begin
		boolean isFtp = new Boolean(String.valueOf(AppUtil.getSysConfig().get("isFtp")));
		File file = null;
		if(isFtp){
			String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.pathpifx"));
			Ftp ftp = new Ftp(1, "fileUpload", String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.host")),
					new Integer(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.port"))), "", "");
			ftp.setUsername(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.user")));
			ftp.setPassword(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.passwd")));
			ftp.setPath("");
			
			String fileP = filePath;
			int index = defaultProfix.lastIndexOf("/");
			fileP = fileP.substring(fileP.indexOf(defaultProfix) + index);
			try {
				file = ftp.retrieve(fileP);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("数据导入失败！");
				this.jsonString = "{success:true,'flag':'0'}";
				e.printStackTrace();
			}
			
		}else{
			String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.default.perfix"));
			int len = defaultProfix.length();
			filePath = filePath.substring(filePath.indexOf(defaultProfix));
			file = new File(this.getRequest().getRealPath("/") + filePath);
		}
		//modify by jack for FTP support at 2011-9-21 end
		String uploadFileType = this.getRequest().getParameter("uploadFileType");
		try {
			//取得要导入的excel文件
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			//取得模板ID
			String templateId = sheet.getCell(0, 0).getContents();
			int col = sheet.getColumns();
			int row = sheet.getRows();
			if("1".equals(uploadFileType)) {//按品类导入数据
				Long deptId = Long.parseLong(this.getRequest().getParameter("deptId"));
				for(int i = 2; i < col; i++) {
					for(int j = 2; j < row; j++) {
						if(sheet.getCell(i, j) != null && !"".equals(sheet.getCell(i, j).getContents().trim())) {
							SpPaAssessmenttasksassigned assign = new SpPaAssessmenttasksassigned();
							assign.setAcId(Long.parseLong(sheet.getCell(i, 0).getContents()));
							assign.setCategory(Long.parseLong(sheet.getCell(0, j).getContents()));
							assign.setTarget(Double.parseDouble(sheet.getCell(i, j).getContents()));
							assign.setPublishDate(currentDate);
							assign.setPublishPerson(currentUser.getUserId());
							assign.setTemplateId(templateId);
							assign.setDeptId(deptId);
							assignList.add(assign);
						}
					}
				}
				this.spPaAssessmenttasksassignedService.multiSave(assignList, templateId, deptId);
			} else if("2".equals(uploadFileType)) {//按人员导入数据
				for(int i = 3; i < col; i++) {
					for(int j = 2; j < row; j++) {
						if(sheet.getCell(i, j) != null && !"".equals(sheet.getCell(i, j).getContents().trim())) {
							SpPaAssessmenttasksassigned assign = new SpPaAssessmenttasksassigned();
							assign.setAcId(Long.parseLong(sheet.getCell(i, 0).getContents()));
							assign.setUserId(Long.parseLong(sheet.getCell(0, j).getContents()));
							assign.setTarget(Double.parseDouble(sheet.getCell(i, j).getContents()));
							assign.setPublishDate(currentDate);
							assign.setPublishPerson(currentUser.getUserId());
							assign.setTemplateId(templateId);
							assignList.add(assign);
						}
					}
				}
				this.spPaAssessmenttasksassignedService.multiSave(assignList, templateId, null);
			}
			this.jsonString = "{success:true,'flag':'1','count':'" + assignList.size() + "'}";
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
