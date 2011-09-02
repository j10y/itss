package com.xpsoft.oa.service.kpi.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.dao.kpi.HrPaAuthpbccitemDao;
import com.xpsoft.oa.model.kpi.HrPaAuthpbccitem;
import com.xpsoft.oa.service.kpi.HrPaAuthorizepbcService;
import com.xpsoft.oa.service.kpi.HrPaAuthpbccitemService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;

public class HrPaAuthpbccitemServiceImpl extends BaseServiceImpl<HrPaAuthpbccitem>
	implements HrPaAuthpbccitemService{
	private HrPaAuthpbccitemDao dao;
	
	public HrPaAuthpbccitemServiceImpl(HrPaAuthpbccitemDao dao){
		super(dao);
		this.dao = dao;
	}
	/*
	 * 批量保存数据
	 * */
	public void multiSave(List<HrPaAuthpbccitem> list) {
		DecimalFormat doubleFormat = new DecimalFormat();
		doubleFormat.applyPattern("###.00");
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				list.get(i).setResult(Double.parseDouble(doubleFormat.format(list.get(i).getResult())));
				this.save(list.get(i));
			}
		}
	}
	
	public void multiSave(List<HrPaAuthpbccitem> list, Long pbcId) {
		DecimalFormat doubleFormat = new DecimalFormat();
		doubleFormat.applyPattern("###.00");
		HrPaKpiPBC2UserService hrPaKpiPBC2UserService = (HrPaKpiPBC2UserService)AppUtil.getBean("hrPaKpiPBC2UserService");
		HrPaAuthorizepbcService hrPaAuthorizepbcService = (HrPaAuthorizepbcService)AppUtil.getBean("hrPaAuthorizepbcService");
		if(list.size() > 0) {
			//获取该PBC模板所属部门的部门负责人
			String sql = "select depId from emp_profile where userId = " + hrPaKpiPBC2UserService.get(pbcId).getBelongUser().getUserId();
			List<Map<String, Object>> mapList = hrPaAuthorizepbcService.findDataList(sql);
			Long depId = Long.parseLong(mapList.get(0).get("depId").toString());
			String chiefSql = "select deptUserId from arch_rec_user where depId = " + depId;
			List<Map<String, Object>> chiefList = hrPaKpiPBC2UserService.findDataList(chiefSql);
			Long chiefId = Long.parseLong(chiefList.get(0).get("deptUserId").toString());
			for(int i = 0; i < list.size(); i++) {
				//修改部门负责人权重
				String sql2 = "select a.id from hr_pa_authpbcitem a, hr_pa_authorizepbc b where " +
						"akpiItem2uId = " + list.get(i).getAkpiItem2uId() + " and a.apbcId = b.id and b.userId = " + chiefId;
				List<Map<String, Object>> mapList2 = this.findDataList(sql2);
				HrPaAuthpbccitem chiefItem = this.get(Long.parseLong(mapList2.get(0).get("id").toString()));
				//判断是新增还是修改
				if(list.get(i).getId() == null) {
					chiefItem.setWeight(chiefItem.getWeight() - list.get(i).getWeight());
				} else {
					String sql3 = "select weight from hr_pa_authpbcitem where id = " + list.get(i).getId();
					List<Map<String, Object>> mapList3 = this.findDataList(sql3);
					Double oldWeight = Double.parseDouble(mapList3.get(0).get("weight").toString());
					chiefItem.setWeight(chiefItem.getWeight() + oldWeight - list.get(i).getWeight());
				}
				list.get(i).setWeight(Double.parseDouble(doubleFormat.format(list.get(i).getWeight())));
				chiefItem.setWeight(Double.parseDouble(doubleFormat.format(chiefItem.getWeight())));
				this.save(list.get(i));
				this.save(chiefItem);
			}
		}
	}
}
