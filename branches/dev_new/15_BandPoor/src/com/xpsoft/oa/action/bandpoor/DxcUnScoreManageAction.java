package com.xpsoft.oa.action.bandpoor;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandPoor;
import com.xpsoft.oa.model.bandpoor.BeElectedBandPoor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.model.bandpoor.SaleAssessment;
import com.xpsoft.oa.model.bandpoor.SaleAssessmentForBP;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.bandpoor.BandPoorService;
import com.xpsoft.oa.service.bandpoor.BeElectedBandPoorService;
import com.xpsoft.oa.service.bandpoor.SaleAssessmentForBPService;
import com.xpsoft.oa.service.bandpoor.SaleAssessmentService;

public class DxcUnScoreManageAction extends BaseAction{
	@Resource
	BeElectedBandPoorService beElectedBandPoorService;
	@Resource
	BandPoorService bandPoorService;
	
	public String list() {
		String bandId = this.getRequest().getParameter("bandId") == null ? "" : this.getRequest().getParameter("bandId");
		QueryFilter filter = new QueryFilter(this.getRequest());
		String sql1 = "select count(*) as total from bp_beelectedbandpoor where " +
				"bp_beelectedbandpoor.status <> 0 and bp_beelectedbandpoor.infoType = 2";
		sql1 += "".equals(bandId) ? "" : " and bp_beelectedbandpoor.bandId = " + bandId;
		List<Map<String, Object>> mapList1 = this.bandPoorService.findDataList(sql1);
		String total = mapList1.get(0).get("total").toString();
		String sql2 = "select bp_beelectedbandpoor.id as id, bp_beelectedbandpoor.bandName as bandName, " +
				"bp_beelectedbandpoor.creatDate as createDate, app_user.fullname as createUser, " +
				"bp_saleassessment.targetValue as targetValue, bp_saleassessment.requireValue as requireValue, " +
				"bp_saleassessment.bandRankValue as bandRankValue, bp_saleassessment.selBandRankValue as selBandRankValue, " +
				"bp_saleassessment.status as status, bp_infopoor.saleStoreName as saleStoreName, " +
				"bp_infopoor.saleSroteDesc as saleStoreDesc, bp_infopoor.mainPriceName as mainPriceName, " +
				"bp_infopoor.proClassName as proClassName, bp_infopoor.bandStyleName as bandStyleName, " +
				"bp_infopoor.bandBusinessAreaName as bandBusinessAreaName from " +
				"app_user, bp_info_beelebandpoor, bp_infopoor, bp_beelectedbandpoor left join " +
				"bp_saleassessment on bp_beelectedbandpoor.id = bp_saleassessment.beElectedBPId where " +
				"bp_beelectedbandpoor.status <> 0 and bp_beelectedbandpoor.infoType = 2 and " +
				"bp_beelectedbandpoor.createUser = app_user.userId and " +
				"bp_beelectedbandpoor.id = bp_info_beelebandpoor.beElectedBPId and " +
				"bp_info_beelebandpoor.infoBPId = bp_infopoor.id";
		sql2 += "".equals(bandId) ? "" : " and bp_beelectedbandpoor.bandId = " + bandId;
		sql2 += " limit " + filter.getPagingBean().getFirstResult() + ", " + filter.getPagingBean().getPageSize();
		List<Map<String, Object>> mapList2 = this.bandPoorService.findDataList(sql2);
		
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:" + total + ",result:[");
		for(Map<String, Object> map : mapList2) {
			StringBuffer content = new StringBuffer();
			content.append("<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">");
			content.append("<tr >");
			content.append("<th>销售场所</th>");
			content.append("<th>销售场所描述</th>");
			content.append("<th>商圈</th>");
			content.append("<th>品类</th>");
			content.append("<th>风格</th>");
			content.append("<th>主力价格</th>");
			content.append("</tr>");
			content.append("<tr>");
			content.append("<td>");
			content.append(map.get("saleStoreName"));
			content.append("</td>");
			content.append("<td>");
			content.append(map.get("saleStoreDesc"));
			content.append("</td>");
			content.append("<td>");
			content.append(map.get("bandBusinessAreaName"));
			content.append("</td>");
			content.append("<td>");
			content.append(map.get("proClassName"));
			content.append("</td>");
			content.append("<td>");
			content.append(map.get("bandStyleName"));
			content.append("</td>");
			content.append("<td>");
			content.append(map.get("mainPriceName"));
			content.append("</td>");
			content.append("</tr>");
			content.append("</table>");
			buff.append("{'id':'" + map.get("id").toString() + "'")
					.append(",'bandName':'" + map.get("bandName").toString() + "'")
					.append(",'targetValue':'" + (map.get("targetValue") == null ? "" : map.get("targetValue").toString()) + "'")
					.append(",'requireValue':'" + (map.get("requireValue") == null ? "" : map.get("requireValue")) + "'")
					.append(",'bandRankValue':'" + (map.get("bandRankValue") == null ? "" : map.get("bandRankValue")) + "'")
					.append(",'selBandRankValue':'" + (map.get("selBandRankValue") == null ? "" : map.get("selBandRankValue")) + "'")
					.append(",'createDate':'" + map.get("createDate") + "'")
					.append(",'createUser':'" + map.get("createUser") + "'")
					.append(",'status':'" + (map.get("status") == null ? "1" : map.get("status")) + "'")
					.append(",'content':'" + content + "'},");
		}
		if(mapList2.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		this.jsonString = buff.toString();
		return "success";
	}
	
	public String check() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		SaleAssessmentService saleAssessmentService = (SaleAssessmentService)AppUtil.getBean("saleAssessmentService");
		BandPoorService bandPoorService = (BandPoorService)AppUtil.getBean("bandPoorService");
		SaleAssessmentForBPService saleAssessmentForBPService = (SaleAssessmentForBPService)AppUtil.getBean("saleAssessmentForBPService");
		try {
			String id = this.getRequest().getParameter("id");
			String method = this.getRequest().getParameter("method");
			Double targetValue = "".equals(this.getRequest().getParameter("targetValue")) ? null : Double.parseDouble(this.getRequest().getParameter("targetValue"));
			Double requireValue = "".equals(this.getRequest().getParameter("requireValue")) ? null : Double.parseDouble(this.getRequest().getParameter("requireValue"));
			Integer bandRankValue = "".equals(this.getRequest().getParameter("bandRankValue")) ? null : Integer.parseInt(this.getRequest().getParameter("bandRankValue"));
			Integer selBandRankValue = "".equals(this.getRequest().getParameter("selBandRankValue")) ? null : Integer.parseInt(this.getRequest().getParameter("selBandRankValue"));
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("Q_beElectedBPId.id_L_EQ", id);
			QueryFilter filter = new QueryFilter(map);
			List<SaleAssessment> list = saleAssessmentService.getAll(filter);
			SaleAssessment sa = new SaleAssessment();
			if(list.size() > 0) {
				sa = list.get(0);
			} else {
				sa.setBeElectedBPId(this.beElectedBandPoorService.get(Long.parseLong(id)));
				sa.setCreateDate(currentDate);
				sa.setCreateUser(currentUser);
			}
			sa.setStatus(SaleAssessment.CREATE);
			sa.setTargetValue(targetValue);
			sa.setRequireValue(requireValue);
			sa.setBandRankValue(bandRankValue);
			sa.setSelBandRankValue(selBandRankValue);
			sa = saleAssessmentService.save(sa);
			if("check".equals(method)) {
				Integer bandPoorStatus = BandPoor.BANDSTATUS_BXC;
				if(requireValue >= targetValue && selBandRankValue <= bandRankValue) {
					bandPoorStatus = BandPoor.BANDSTATUS_YYC;
					sa.setStatus(SaleAssessment.PASS);
				} else {
					sa.setStatus(SaleAssessment.UNPASS);
				}
				sa = saleAssessmentService.save(sa);
				BeElectedBandPoor bebp = this.beElectedBandPoorService.get(Long.parseLong(id));
				BandPoor bp = new BandPoor();
				SaleAssessmentForBP safbp = new SaleAssessmentForBP();	
				Set<InfoPoor> infopoors=bebp.getInfoPoors();
				Set<InfoPoor> bandpoors=new HashSet();
				for(InfoPoor infopoor:infopoors){
					bandpoors.add(infopoor);
				}
				bp.setInfoPoors(bandpoors);
				bp.setBandId(bebp.getBandId());
				bp.setBandName(bebp.getBandName());
				bp.setInfoType(bebp.getInfoType());
				bp.setBandPoorStatus(bandPoorStatus);
				bp.setStatus(BandPoor.STATUS_CREATE);
				bp.setCreatDate(currentDate);
				bp.setCreateUser(currentUser);
				bp.setModifyDate(currentDate);
				bp.setModifyUser(currentUser);
				bp = bandPoorService.save(bp);
				
				safbp.setBpId(bp);
				safbp.setTargetValue(sa.getTargetValue());
				safbp.setRequireValue(sa.getRequireValue());
				safbp.setBandRankValue(sa.getBandRankValue());
				safbp.setSelBandRankValue(sa.getSelBandRankValue());
				safbp.setStatus(sa.getStatus());
				safbp.setCreateDate(currentDate);
				safbp.setCreateUser(currentUser);
				safbp = saleAssessmentForBPService.save(safbp);
			}
			this.jsonString = "{success:true}";
		} catch(Exception e) {
			e.printStackTrace();
			this.jsonString = "{success:false}";
		}
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				BeElectedBandPoor bip=beElectedBandPoorService.get(new Long(id));
				bip.setStatus(BeElectedBandPoor.STATUS_DELETE);
				beElectedBandPoorService.save(bip);
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}
	
	public BeElectedBandPoorService getBeElectedBandPoorService() {
		return beElectedBandPoorService;
	}
	public void setBeElectedBandPoorService(
			BeElectedBandPoorService beElectedBandPoorService) {
		this.beElectedBandPoorService = beElectedBandPoorService;
	}
	public BandPoorService getBandPoorService() {
		return bandPoorService;
	}
	public void setBandPoorService(BandPoorService bandPoorService) {
		this.bandPoorService = bandPoorService;
	}
}
