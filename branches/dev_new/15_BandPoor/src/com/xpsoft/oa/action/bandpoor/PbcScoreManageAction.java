package com.xpsoft.oa.action.bandpoor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandPoor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.service.bandpoor.BandPoorService;

public class PbcScoreManageAction extends BaseAction{
	@Resource
	BandPoorService bandPoorService;
	
	public String scoreList(){
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_status_N_NEQ", BandPoor.STATUS_DELETE+"");
		filter.addFilter("Q_infoType_N_EQ", BandPoor.TYPE_SCORE+"");
		filter.addFilter("Q_bandPoorStatus_N_EQ", BandPoor.BANDSTATUS_YYC+"");
		List<BandPoor> list = this.bandPoorService.getAll(filter);
		Type type = new TypeToken<List<BandPoor>>() {
		}
		.getType();
		
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(
				",result:[");;
		for(BandPoor bandPoor:list){
			Set<InfoPoor> infopoors=bandPoor.getInfoPoors();
			buff.append("{id:'")
			.append(bandPoor.getId())
			.append("',bandName:'")
			.append(bandPoor.getBandName())
			.append("',saleStoreNum:'")
			.append(infopoors.size())
			.append("',bandScore:'")
			.append(bandPoor.getBandScore()!=null?bandPoor.getBandScore():"")
			.append("',status:'")
			.append(bandPoor.getStatus())
			.append("',infoType:'")
			.append(bandPoor.getInfoType())
			.append("',creatDate:'")
			.append(bandPoor.getCreatDate())
			.append("',createUser:'")
			.append(bandPoor.getCreateUser().getUsername());
			StringBuffer content = new StringBuffer();
			content.append("<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">");
			content.append("<tr >");
			content.append("<th>序号</th>");
			content.append("<th>销售场所</th>");
			content.append("<th>商圈</th>");
			content.append("<th>品类</th>");
			content.append("<th>风格</th>");
			content.append("<th>主力价格</th>");
			content.append("</tr>");
			int i=0;
			for(InfoPoor infopoor:infopoors){
				i++;
				content.append("<tr>");
				content.append("<td>");
				content.append(i);
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getSaleStoreName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getBandBusinessAreaName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getProClassName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getBandStyleName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getMainPriceName());
				content.append("</td>");
				content.append("</tr>");
			}
			content.append("</table>");
			buff.append("',bandrealScore:'");
			buff.append(bandPoor.getBandRealScore()!=null?bandPoor.getBandRealScore():"");
			buff.append("',year:'");
			buff.append(bandPoor.getYear());
			buff.append("',poorVersion:'");
			buff.append(bandPoor.getPoorVersion());
			buff.append("',content:'")
			.append(content)
			.append("'},");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		this.jsonString = buff.toString();
		return "success";
	}
	public String unScoreList(){
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_status_N_NEQ", BandPoor.STATUS_DELETE+"");
		filter.addFilter("Q_infoType_N_EQ", BandPoor.TYPE_SCORE+"");
		filter.addFilter("Q_bandPoorStatus_N_EQ", BandPoor.BANDSTATUS_BXC+"");
		List<BandPoor> list = this.bandPoorService.getAll(filter);
		Type type = new TypeToken<List<BandPoor>>() {
		}
		.getType();
		
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(
				",result:[");;
		for(BandPoor bandPoor:list){
			Set<InfoPoor> infopoors=bandPoor.getInfoPoors();
			buff.append("{id:'")
			.append(bandPoor.getId())
			.append("',bandName:'")
			.append(bandPoor.getBandName())
			.append("',saleStoreNum:'")
			.append(infopoors.size())
			.append("',bandScore:'")
			.append(bandPoor.getBandScore()!=null?bandPoor.getBandScore():"")
			.append("',status:'")
			.append(bandPoor.getStatus())
			.append("',infoType:'")
			.append(bandPoor.getInfoType())
			.append("',creatDate:'")
			.append(bandPoor.getCreatDate())
			.append("',createUser:'")
			.append(bandPoor.getCreateUser().getUsername());
			StringBuffer content = new StringBuffer();
			content.append("<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">");
			content.append("<tr >");
			content.append("<th>序号</th>");
			content.append("<th>销售场所</th>");
			content.append("<th>商圈</th>");
			content.append("<th>品类</th>");
			content.append("<th>风格</th>");
			content.append("<th>主力价格</th>");
			content.append("</tr>");
			int i=0;
			for(InfoPoor infopoor:infopoors){
				i++;
				content.append("<tr>");
				content.append("<td>");
				content.append(i);
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getSaleStoreName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getBandBusinessAreaName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getProClassName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getBandStyleName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getMainPriceName());
				content.append("</td>");
				content.append("</tr>");
			}
			content.append("</table>");
			buff.append("',bandrealScore:'");
			buff.append(bandPoor.getBandRealScore()!=null?bandPoor.getBandRealScore():"");
			buff.append("',year:'");
			buff.append(bandPoor.getYear());
			buff.append("',poorVersion:'");
			buff.append(bandPoor.getPoorVersion());
			buff.append("',content:'")
			.append(content)
			.append("'},");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		this.jsonString = buff.toString();
		return "success";
	}
}
