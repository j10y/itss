package com.xpsoft.oa.action.kpi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.StringUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.hrm.Job;
import com.xpsoft.oa.model.kpi.HrPaDatadictionary;
import com.xpsoft.oa.model.kpi.HrPaKpiitem;
import com.xpsoft.oa.model.kpi.HrPaKpipbc;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.kpi.HrPaKpiitemService;
import com.xpsoft.oa.service.kpi.HrPaKpipbcService;

import flexjson.JSONSerializer;

public class HrPaKpipbcAction extends BaseAction{
	@Resource
	private HrPaKpipbcService hrPaKpipbcService;
	private HrPaKpipbc hrPaKpipbc;
	private long id;
	
	public HrPaKpipbcService getHrPaKpipbcService() {
		return hrPaKpipbcService;
	}
	public void setHrPaKpipbcService(HrPaKpipbcService hrPaKpipbcService) {
		this.hrPaKpipbcService = hrPaKpipbcService;
	}
	public HrPaKpipbc getHrPaKpipbc() {
		return hrPaKpipbc;
	}
	public void setHrPaKpipbc(HrPaKpipbc hrPaKpipbc) {
		this.hrPaKpipbc = hrPaKpipbc;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaKpipbc> list = this.hrPaKpipbcService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String get() {
		this.hrPaKpipbc = (HrPaKpipbc)this.hrPaKpipbcService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.hrPaKpipbc));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				HrPaKpipbc hpk = this.hrPaKpipbcService.get(new Long(id));
				hpk.setPublishStatus(new Integer("4"));//状态置为已删除
				this.hrPaKpipbcService.save(hpk);
			}
		}
		
		return "success";
	}
	
	public String save() {
		Date currentDate = new Date();
		HrPaKpiitemService hrPaKpiitemService = (HrPaKpiitemService)AppUtil.getBean("hrPaKpiitemService");
		//获取考核模板关联的考核项，并将所有考核项数据以” “拆分成单个考核项数组
		String[] hrPaKpiitems = this.getRequest().getParameter("hrPaKpiitems").trim().split(" ");
		//1. 新增PBC考核模板
		if(this.hrPaKpipbc.getId() == 0) {
			System.out.println("this.hrPaKpipbc.getId()=" + this.hrPaKpipbc.getId());
			//1.1. 保存PBC模板基本信息
			this.hrPaKpipbc.setCreateDate(currentDate);
			this.hrPaKpipbc.setCreatePerson(ContextUtil.getCurrentUser());
			this.hrPaKpipbc.setModifyDate(currentDate);
			this.hrPaKpipbc.setModifyPerson(ContextUtil.getCurrentUser());
			HrPaKpipbc pbcNew = this.hrPaKpipbcService.save(this.hrPaKpipbc);
			//1.2. 保存PBC模板关联的考核项
			if(!this.getRequest().getParameter("hrPaKpiitems").trim().equals("")) {
				for(int i = 0; i < hrPaKpiitems.length; i++) {
					//新建一个HrPaKpiitem并为其赋值
					HrPaKpiitem hrPaKpiitem = new HrPaKpiitem();
					HrPaPerformanceindex pi = new HrPaPerformanceindex();
					//将单个考核项数据已”,“拆分成数据字段数组
					String[] itemArray = hrPaKpiitems[i].split(",");
					hrPaKpiitem.setPbc(pbcNew);
					pi.setId(Long.parseLong(itemArray[1]));
					hrPaKpiitem.setPi(pi);
					hrPaKpiitem.setWeight(Double.parseDouble(itemArray[2]));
					hrPaKpiitem.setResult(0);//得分默认为零
					
					hrPaKpiitemService.save(hrPaKpiitem);
				}
			}
		} else {
		//2. 修改PBC考核模板
			//2.1. 获取原PBC考核模板
			HrPaKpipbc pbcOld = this.hrPaKpipbcService.get(this.hrPaKpipbc.getId());
			if(pbcOld.getPublishStatus() == 3 ) {//原PBC模板为已发布状态
				//2.1.1. 将原考核模板置为已删除状态
				this.hrPaKpipbcService.save(pbcOld);
				//2.1.2. 复制一份新的PBC考核模板
				HrPaKpipbc pbcOldCopy = new HrPaKpipbc();
				pbcOldCopy.setPbcName(this.hrPaKpipbc.getPbcName());
				pbcOldCopy.setBelongDept(this.hrPaKpipbc.getBelongDept());
				pbcOldCopy.setBelongPost(this.hrPaKpipbc.getBelongPost());
				pbcOldCopy.setFrequency(this.hrPaKpipbc.getFrequency());
				pbcOldCopy.setTotalScore(this.hrPaKpipbc.getTotalScore());
				pbcOldCopy.setCreateDate(pbcOld.getCreateDate());
				pbcOldCopy.setCreatePerson(pbcOld.getCreatePerson());
				pbcOldCopy.setPublishStatus(this.hrPaKpipbc.getPublishStatus());
				pbcOldCopy.setModifyDate(currentDate);
				pbcOldCopy.setModifyPerson(ContextUtil.getCurrentUser());
				HrPaKpipbc pbcNew = this.hrPaKpipbcService.save(pbcOldCopy);
				//2.1.3. 保存PBC模板关联的考核项
				if(!this.getRequest().getParameter("hrPaKpiitems").trim().equals("")) {
					for(int i = 0; i < hrPaKpiitems.length; i++) {
						//新建一个HrPaKpiitem并为其赋值
						HrPaKpiitem hrPaKpiitem = new HrPaKpiitem();
						HrPaPerformanceindex pi = new HrPaPerformanceindex();
						//将单个考核项数据已”,“拆分成数据字段数组
						String[] itemArray = hrPaKpiitems[i].split(",");
						hrPaKpiitem.setPbc(pbcNew);
						pi.setId(Long.parseLong(itemArray[1]));
						hrPaKpiitem.setPi(pi);
						hrPaKpiitem.setWeight(Double.parseDouble(itemArray[2]));
						hrPaKpiitem.setResult(0);//得分默认为零
						
						hrPaKpiitemService.save(hrPaKpiitem);
					}
				}
			} else if(pbcOld.getPublishStatus() == 0 || pbcOld.getPublishStatus() == 1 || pbcOld.getPublishStatus() == 2) {//原PBC模板为草稿、审核中、退回状态
				//2.2.1. 保存PBC考核模板基本信息
				this.hrPaKpipbc.setCreateDate(pbcOld.getCreateDate());
				this.hrPaKpipbc.setModifyDate(currentDate);
				this.hrPaKpipbc.setModifyPerson(ContextUtil.getCurrentUser());
				//使用BeanUtils避免出现“org.hibernate.NonUniqueObjectException”异常
				BeanUtils.copyProperties(this.hrPaKpipbc, pbcOld);
				HrPaKpipbc pbcNew = this.hrPaKpipbcService.save(pbcOld);
				//2.2.2. 删除考核模板关联的考核项
				Map<String, String> map = new HashMap<String, String>();
				map.put("Q_pbc.id_L_EQ", String.valueOf(this.hrPaKpipbc.getId()));
				QueryFilter filter = new QueryFilter(map);
				List<HrPaKpiitem> list = hrPaKpiitemService.getAll(filter);
				for(int i = 0; i < list.size(); i++) {
					hrPaKpiitemService.remove(list.get(i));
				}
				//2.2.3. 保存考核模板关联的考核项
				if(!this.getRequest().getParameter("hrPaKpiitems").trim().equals("")) {
					for(int i = 0; i < hrPaKpiitems.length; i++) {
						//新建一个HrPaKpiitem并为其赋值
						HrPaKpiitem hrPaKpiitem = new HrPaKpiitem();
						HrPaPerformanceindex pi = new HrPaPerformanceindex();
						//将单个考核项数据已”,“拆分成数据字段数组
						String[] itemArray = hrPaKpiitems[i].split(",");
						hrPaKpiitem.setPbc(pbcNew);
						pi.setId(Long.parseLong(itemArray[1]));
						hrPaKpiitem.setPi(pi);
						hrPaKpiitem.setWeight(Double.parseDouble(itemArray[2]));
						hrPaKpiitem.setResult(0);//得分默认为零
						
						hrPaKpiitemService.save(hrPaKpiitem);
					}
				}
			} else if(pbcOld.getPublishStatus() == 4) {//原PBC模板为已删除状态
				
			} else {
				
			}
		}
		
		this.jsonString = new String("{success:true}");
		return "success";
	}
}
