package com.xpsoft.oa.action.kpi;

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
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;
import com.xpsoft.oa.model.kpi.HrPaAssessmenttasksassigned;
import com.xpsoft.oa.model.kpi.HrPaCategory;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.hrm.EmpProfileService;
import com.xpsoft.oa.service.kpi.HrPaAssessmentcriteriaService;
import com.xpsoft.oa.service.kpi.HrPaAssessmenttasksassignedService;
import com.xpsoft.oa.service.kpi.HrPaCategoryService;
import com.xpsoft.oa.service.system.AppUserService;

public class HrPaAssessmenttasksassignedAction extends BaseAction{
	@Resource
	private HrPaAssessmenttasksassignedService hrPaAssessmenttasksassignedService;
	private HrPaAssessmenttasksassigned hrPaAssessmenttasksassigned;
	private long id;
	
	public HrPaAssessmenttasksassignedService getHrPaAssessmenttasksassignedService() {
		return hrPaAssessmenttasksassignedService;
	}
	public void setHrPaAssessmenttasksassignedService(
			HrPaAssessmenttasksassignedService hrPaAssessmenttasksassignedService) {
		this.hrPaAssessmenttasksassignedService = hrPaAssessmenttasksassignedService;
	}
	public HrPaAssessmenttasksassigned getHrPaAssessmenttasksassigned() {
		return hrPaAssessmenttasksassigned;
	}
	public void setHrPaAssessmenttasksassigned(
			HrPaAssessmenttasksassigned hrPaAssessmenttasksassigned) {
		this.hrPaAssessmenttasksassigned = hrPaAssessmenttasksassigned;
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
		this.hrPaAssessmenttasksassignedService.save(this.hrPaAssessmenttasksassigned);
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
		String path = this.getRequest().getRealPath("/") + "attachFiles/kpiTasksassigned/" + 
				ContextUtil.getCurrentUser().getUsername() + yearMonth + "_target.xls";
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
		List<HrPaAssessmenttasksassigned> assignList = new ArrayList<HrPaAssessmenttasksassigned>();
		//modify by jack for FTP support at 2011-9-21 begin
		boolean isFtp = new Boolean(String.valueOf(AppUtil.getSysConfig().get("isFtp")));
		File file = null;
		if(isFtp){
			String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.sysprofix"));
			Ftp ftp = new Ftp(1, "fileUpload", String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.host")),
					new Integer(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.port"))), "", "");
			ftp.setUsername(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.user")));
			ftp.setPassword(String.valueOf(AppUtil.getSysConfig().get("file.upload.ftp.passwd")));
			ftp.setPath("");
			
			String fileP = filePath;
			fileP = fileP.substring(fileP.indexOf(defaultProfix));
			try {
				file = ftp.retrieve(fileP);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				this.logger.error("数据导入失败：原因：" + e);
				this.jsonString = "{success:true,'flag':'0'}";
			}
			
		}else{
			String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.default.perfix"));
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
							HrPaAssessmenttasksassigned assign = new HrPaAssessmenttasksassigned();
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
				this.hrPaAssessmenttasksassignedService.multiSave(assignList, templateId, deptId);
			} else if("2".equals(uploadFileType)) {//按人员导入数据
				for(int i = 3; i < col; i++) {
					for(int j = 2; j < row; j++) {
						if(sheet.getCell(i, j) != null && !"".equals(sheet.getCell(i, j).getContents().trim())) {
							HrPaAssessmenttasksassigned assign = new HrPaAssessmenttasksassigned();
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
				this.hrPaAssessmenttasksassignedService.multiSave(assignList, templateId, null);
			}
			this.jsonString = "{success:true,'flag':'1','count':'" + assignList.size() + 
					"','templateId':'" + templateId + "','uploadFileType':'" + uploadFileType + "'}";
		} catch(Exception e) {
			this.logger.error("数据导入失败，原因：" + e);
			this.jsonString = "{success:true,'flag':'0'}";
		} finally {
			if(file.exists()) {
				file.delete();
				file.getParentFile().delete();
			}
		}
		
		return "success";
	}
	
	public String getUploadResult() {
		String templateId = this.getRequest().getParameter("templateId");
		String uploadFileType = this.getRequest().getParameter("uploadFileType");
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if("1".equals(uploadFileType)) {
			String sql = "select d.depName, c.name, b.acName, a.target from hr_pa_assessmenttasksassigned a, hr_pa_assessmentcriteria b, hr_pa_category c, department d where " +
					"a.templateId = '" + templateId + "' and a.acId = b.id and a.category = c.id and a.deptId = d.depId order by a.id";
			resultList = this.hrPaAssessmenttasksassignedService.findDataList(sql);
		} else if("2".equals(uploadFileType)) {
			String sql = "select c.fullname, b.acName, a.target from hr_pa_assessmenttasksassigned a, hr_pa_assessmentcriteria b, app_user c where " +
					"a.templateId = '" + templateId + "' and a.acId = b.id and a.userId = c.userId order by a.id";
			resultList = this.hrPaAssessmenttasksassignedService.findDataList(sql);
		}
		this.getRequest().setAttribute("uploadFileType", uploadFileType);
		this.getRequest().setAttribute("resultList", resultList);
		
		return "showResult";
	}
}
