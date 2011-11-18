package com.xpsoft.oa.action.kpi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Sheet;
import jxl.Workbook;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.model.Ftp;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.kpi.HrPaAssessmentcriteriaService;
import com.xpsoft.oa.service.system.DepartmentService;

import flexjson.JSONSerializer;

public class HrPaAssessmentcriteriaAction extends BaseAction{
	@Resource
	private HrPaAssessmentcriteriaService hrPaAssessmentcriteriaService;
	private HrPaAssessmentcriteria hrPaAssessmentcriteria;
	private long id;
	
	public HrPaAssessmentcriteriaService getHrPaAssessmentcriteriaService() {
		return hrPaAssessmentcriteriaService;
	}
	public void setHrPaAssessmentcriteriaService(
			HrPaAssessmentcriteriaService hrPaAssessmentcriteriaService) {
		this.hrPaAssessmentcriteriaService = hrPaAssessmentcriteriaService;
	}
	public HrPaAssessmentcriteria getHrPaAssessmentcriteria() {
		return hrPaAssessmentcriteria;
	}
	public void setHrPaAssessmentcriteria(
			HrPaAssessmentcriteria hrPaAssessmentcriteria) {
		this.hrPaAssessmentcriteria = hrPaAssessmentcriteria;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaAssessmentcriteria> list = this.hrPaAssessmentcriteriaService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				HrPaAssessmentcriteria hpa = this.hrPaAssessmentcriteriaService.get(new Long(id));
				hpa.setPublishStatus(new Integer("2"));//置为已删除状态
				this.hrPaAssessmentcriteriaService.save(hpa);
			}
		}
		
		return "success";
	}
	
	public String get() {
		this.hrPaAssessmentcriteria = (HrPaAssessmentcriteria)this.hrPaAssessmentcriteriaService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.hrPaAssessmentcriteria));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	/*
	public String save2() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		//首先判断关键字是否重复
		if(this.hrPaAssessmentcriteriaService.checkKey(this.hrPaAssessmentcriteria.getAcKey())) {
			this.jsonString = "{msg:'关键字已存在，请重新输入！',failure:true}";
			return "success";
		}
		//新建一个HrPaAssessmentcriteria并为其赋值
		//1. 新增和修改有区别的项
		HrPaAssessmentcriteria hpa = new HrPaAssessmentcriteria();
		if(this.hrPaAssessmentcriteria.getId() == 0) {//新增
			hpa.setCreateDate(currentDate);
			hpa.setCreatePerson(currentUser.getUserId());
			hpa.setFromAc(new Long(0));
		} else {//修改
			hpa = this.hrPaAssessmentcriteriaService.get(this.hrPaAssessmentcriteria.getId());
			//判断是已发布的还是草稿
			if(hpa.getPublishStatus() == 3) {//已发布
				HrPaAssessmentcriteria hpaCopy = new HrPaAssessmentcriteria();
				hpaCopy.setCreateDate(currentDate);
				hpaCopy.setCreatePerson(currentUser.getUserId());
				hpaCopy.setFromAc(fromAc)
			} else {
				
			}
			hpa.setId(this.hrPaAssessmentcriteria.getId());
			Date createDate = new Date(Long.parseLong(this.getRequest().getParameter("hrPaAssessmentcriteria.createDate")));
			hpa.setCreateDate(createDate);
			hpa.setCreatePerson(this.hrPaAssessmentcriteria.getCreatePerson());
		}
		//2. 新增和修改没有区别的项
		hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
		hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
		if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
			hpa.setIsSalesAC(1);
		} else {
			hpa.setIsSalesAC(0);
		}
		hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
		hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
		hpa.setModifyDate(currentDate);
		hpa.setModifyPerson(ContextUtil.getCurrentUserId());
		//将数据插入数据库
		this.hrPaAssessmentcriteriaService.save(hpa);
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	*/
	
	public String save() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		//首先判断关键字是否重复
		if(this.hrPaAssessmentcriteriaService.checkKey(this.hrPaAssessmentcriteria.getAcKey(), this.hrPaAssessmentcriteria.getId())) {
			this.jsonString = "{msg:'关键字已存在，请重新输入！',failure:true}";
			return "success";
		}
		//新建一个HrPaAssessmentcriteria并为其赋值
		HrPaAssessmentcriteria hpa = new HrPaAssessmentcriteria();
		if(this.hrPaAssessmentcriteria.getPublishStatus() == 0) {//保存草稿
			//判断是新增还是修改
			if(this.hrPaAssessmentcriteria.getId() == 0) {//新增
				hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
				hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
				hpa.setBelongDept(this.hrPaAssessmentcriteria.getBelongDept());
				hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
				if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
					hpa.setIsSalesAC(1);
				} else {
					hpa.setIsSalesAC(0);
				}
				hpa.setCreateDate(currentDate);
				hpa.setCreatePerson(currentUser.getUserId());
				hpa.setModifyDate(currentDate);
				hpa.setModifyPerson(currentUser.getUserId());
				hpa.setFromAc(new Long(0));
				hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
				this.hrPaAssessmentcriteriaService.save(hpa);
			} else {//修改
				hpa = this.hrPaAssessmentcriteriaService.get(this.hrPaAssessmentcriteria.getId());
				if(hpa.getPublishStatus() == 3) {
					HrPaAssessmentcriteria hpaCopy = new HrPaAssessmentcriteria();
					hpaCopy.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
					hpaCopy.setAcName(this.hrPaAssessmentcriteria.getAcName());
					hpaCopy.setBelongDept(this.hrPaAssessmentcriteria.getBelongDept());
					hpaCopy.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
					if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpaCopy.setIsSalesAC(1);
					} else {
						hpaCopy.setIsSalesAC(0);
					}
					hpaCopy.setCreateDate(currentDate);
					hpaCopy.setCreatePerson(currentUser.getUserId());
					hpaCopy.setModifyDate(currentDate);
					hpaCopy.setModifyPerson(currentUser.getUserId());
					hpaCopy.setFromAc(this.hrPaAssessmentcriteria.getId());
					hpaCopy.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
					this.hrPaAssessmentcriteriaService.save(hpaCopy);
				} else {
					hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
					hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
					hpa.setBelongDept(this.hrPaAssessmentcriteria.getBelongDept());
					hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
					if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpa.setIsSalesAC(1);
					} else {
						hpa.setIsSalesAC(0);
					}
					hpa.setModifyDate(currentDate);
					hpa.setModifyPerson(currentUser.getUserId());
					hpa.setFromAc(this.hrPaAssessmentcriteria.getFromAc());
					hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
					this.hrPaAssessmentcriteriaService.save(hpa);
				}
			}
		} else if(this.hrPaAssessmentcriteria.getPublishStatus() == 3) {
			//判断是新增还是修改
			if(this.hrPaAssessmentcriteria.getId() == 0) {
				hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
				hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
				hpa.setBelongDept(this.hrPaAssessmentcriteria.getBelongDept());
				hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
				if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
					hpa.setIsSalesAC(1);
				} else {
					hpa.setIsSalesAC(0);
				}
				hpa.setCreateDate(currentDate);
				hpa.setCreatePerson(currentUser.getUserId());
				hpa.setModifyDate(currentDate);
				hpa.setModifyPerson(currentUser.getUserId());
				hpa.setFromAc(new Long(0));
				hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
				this.hrPaAssessmentcriteriaService.save(hpa);
			} else {
				hpa = this.hrPaAssessmentcriteriaService.get(this.hrPaAssessmentcriteria.getId());
				if(hpa.getFromAc() == 0) {
					hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
					hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
					hpa.setBelongDept(this.hrPaAssessmentcriteria.getBelongDept());
					hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
					if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpa.setIsSalesAC(1);
					} else {
						hpa.setIsSalesAC(0);
					}
					hpa.setModifyDate(currentDate);
					hpa.setModifyPerson(currentUser.getUserId());
					hpa.setFromAc(new Long(0));
					hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
					this.hrPaAssessmentcriteriaService.save(hpa);
				} else {
					HrPaAssessmentcriteria hpaOld = this.hrPaAssessmentcriteriaService.get(hpa.getFromAc());
					hpaOld.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
					hpaOld.setAcName(this.hrPaAssessmentcriteria.getAcName());
					hpaOld.setBelongDept(this.hrPaAssessmentcriteria.getBelongDept());
					hpaOld.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
					if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpaOld.setIsSalesAC(1);
					} else {
						hpaOld.setIsSalesAC(0);
					}
					hpaOld.setModifyDate(currentDate);
					hpaOld.setModifyPerson(currentUser.getUserId());
					hpaOld.setFromAc(new Long(0));
					hpaOld.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
					this.hrPaAssessmentcriteriaService.save(hpaOld);
					this.hrPaAssessmentcriteriaService.remove(hpa);
				}
			}
		} else {
			
		}
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	
	public String multiAudit() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				HrPaAssessmentcriteria hpa = this.hrPaAssessmentcriteriaService.get(new Long(id));
				hpa.setPublishStatus(new Integer("3"));
				this.hrPaAssessmentcriteriaService.save(hpa);
			}
		}
		
		return "success";
	}
	
	public String load() {
		Long depId = Long.parseLong(this.getRequest().getParameter("depId"));
		Map<String, String> map = this.hrPaAssessmentcriteriaService.getKeyAndName(depId);
		
		StringBuffer buff = new StringBuffer("[");
		for(Map.Entry<String, String> entry : map.entrySet()) {
			buff.append("['").append(entry.getKey()).append("',")
					.append("'").append(entry.getValue()).append("'],");
		}
		if(!map.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String uploadAc() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		DepartmentService departmentService = (DepartmentService)AppUtil.getBean("departmentService");
		String msg = "";
		//设置部门
		List<Department> depList = departmentService.getAll();
		Map<String, Department> depMap = new HashMap<String, Department>();
		for(Department dept : depList) {
			depMap.put(dept.getDepName(), dept);
		}
		//获取要导入的excel
		String filePath = this.getRequest().getParameter("filePath");
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
				this.logger.error("数据导入失败，原因：" + e);
				this.jsonString = "{success:true,'flag':'0','msg':'数据导入失败，请联系管理员！'}";
			}
			
		}else{
			String defaultProfix = String.valueOf(AppUtil.getSysConfig().get("file.upload.default.perfix"));
			int len = defaultProfix.length();
			filePath = filePath.substring(filePath.indexOf(defaultProfix));
			file = new File(this.getRequest().getRealPath("/") + filePath);
		}
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			int col = sheet.getColumns();
			int row = sheet.getRows();
			List<HrPaAssessmentcriteria> list = new ArrayList<HrPaAssessmentcriteria>();
			for(int i = 1; i < row; i++) {
				HrPaAssessmentcriteria ac = new HrPaAssessmentcriteria();
				String depName = sheet.getCell(0, i).getContents().trim();
				String acName = sheet.getCell(1, i).getContents().trim();
				String acKey = sheet.getCell(2, i).getContents().trim();
				//判断必填项是否非空
				if(depName == null || "".equals(depName) || acName == null || "".equals(acName) || 
						acKey == null || "".equals(acKey)) {
					msg = "第【" + (i + 1) + "】行考核标准信息有误，请核实！";
					this.logger.error(msg);
					this.jsonString = "{success:true,'flag':'0','msg':'" + msg + "'}";
					return "success";
				}
				//判断部门名称是否正确
				if(depMap.get(depName) == null) {
					msg = "第【" + (i + 1) + "】行考核标准部门信息【" + depName + "】有误，请核实！";
					this.logger.error(msg);
					this.jsonString = "{success:true,'flag':'0','msg':'" + msg + "'}";
					return "success";
				}
				ac.setAcName(acName);
				ac.setAcKey(acKey);
				ac.setIsSalesAC(0);
				ac.setAcDesc(sheet.getCell(3, i).getContents().trim());
				ac.setBelongDept(depMap.get(depName));
				ac.setPublishStatus(3);
				ac.setCreateDate(currentDate);
				ac.setCreatePerson(currentUser.getUserId());
				ac.setModifyDate(currentDate);
				ac.setModifyPerson(currentUser.getUserId());
				ac.setFromAc(new Long(0));
				list.add(ac);
			}
			//判断关键字是否有重复（excel内部重复、与数据库已存在标准重复）
			for(int i = 0; i < list.size(); i++) {
				HrPaAssessmentcriteria item = list.get(i);
				if(this.hrPaAssessmentcriteriaService.checkKey(item.getAcKey(), 0)) {
					msg = "关键字为【" + item.getAcKey() + "】的考核标准在数据库已存在，请核实！";
					this.logger.error(msg);
					this.jsonString = "{success:true,'flag':'0','msg':'" + msg + "'}";
					return "success";
				}
				for(int j = i + 1; j < list.size(); j++) {
					if(item.getAcKey().equals(list.get(j).getAcKey())) {
						msg = "文件中存在重复的考核标准关键字【" + item.getAcKey() + "】，请核实！";
						this.logger.error(msg);
						this.jsonString = "{success:true,'flag':'0','msg':'" + msg + "'}";
						return "success";
					}
				}
			}
			//存入数据库
			boolean result = this.hrPaAssessmentcriteriaService.multiSave(list);
			if(result) {
				System.out.println("导入成功，共导入【" + list.size() + "】条数据！");
				this.jsonString = "{success:true,'flag':'1','count':'" + list.size() + "'}";
			} else {
				this.jsonString = "{success:true,'flag':'0','msg':'导入出错，请联系管理员！'}";
			}
		} catch(Exception e) {
			this.logger.error("导入出错，原因：" + e);
			this.jsonString = "{success:true,'flag':'0','msg':'导入出错，请核实文件格式及内容！'}";
		}
		
		return "success";
	}
}
