package com.xpsoft.oa.action.bandpoor;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandLevel;
import com.xpsoft.oa.model.bandpoor.BandPoor;
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.model.bandpoor.SaleStore;
import com.xpsoft.oa.service.bandpoor.BandLevelService;
import com.xpsoft.oa.service.bandpoor.BandPoorService;

import flexjson.JSONSerializer;

public class PbcScoreManageAction extends BaseAction{
	@Resource
	BandPoorService bandPoorService;
	
	@Resource
	private BandLevelService bandLevelService;
	
	public String scoreList(){
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_status_N_NEQ", BandPoor.STATUS_DELETE+"");
		filter.addFilter("Q_infoType_N_EQ", BandPoor.TYPE_SCORE+"");
		filter.addFilter("Q_bandPoorStatus_N_EQ", BandPoor.BANDSTATUS_YYC+"");
		List<BandPoor> list = this.bandPoorService.getAll(filter);
		Type type = new TypeToken<List<BandPoor>>() {
		}
		.getType();
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("###.00");
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
			Long startprice=0l;
			Long endprice=0l;
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
				startprice+=infopoor.getMainPriceStart()!=null?infopoor.getMainPriceStart():0;
				endprice+=infopoor.getMainPriceEnd()!=null?infopoor.getMainPriceEnd():0;
			}
			content.append("</table>");
			buff.append("',bandrealScore:'");
			buff.append(bandPoor.getBandRealScore()!=null?bandPoor.getBandRealScore():"");
			buff.append("',year:'");
			buff.append(bandPoor.getYear());
			buff.append("',poorVersion:'");
			buff.append(bandPoor.getPoorVersion());
			buff.append("',mainprice:'");
			buff.append(myformat.format(startprice/i)+"~"+myformat.format(endprice/i));
			buff.append("',bandlevel:'");
			buff.append(bandPoor.getBandLevel()!=null?bandPoor.getBandLevel().getLevelName():"");
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
	//备选池
	public String bxcScoreList(){
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_status_N_NEQ", BandPoor.STATUS_DELETE+"");
		filter.addFilter("Q_infoType_N_EQ", BandPoor.TYPE_SCORE+"");
		filter.addFilter("Q_bandPoorStatus_N_EQ", BandPoor.BANDSTATUS_BXC+"");
		List<BandPoor> list = this.bandPoorService.getAll(filter);
		Type type = new TypeToken<List<BandPoor>>() {
		}
		.getType();
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("###.00");
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
			Long startprice=0l;
			Long endprice=0l;
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
				startprice+=infopoor.getMainPriceStart()!=null?infopoor.getMainPriceStart():0;
				endprice+=infopoor.getMainPriceEnd()!=null?infopoor.getMainPriceEnd():0;
			}
			content.append("</table>");
			buff.append("',bandrealScore:'");
			buff.append(bandPoor.getBandRealScore()!=null?bandPoor.getBandRealScore():"");
			buff.append("',year:'");
			buff.append(bandPoor.getYear());
			buff.append("',poorVersion:'");
			buff.append(bandPoor.getPoorVersion());
			buff.append("',mainprice:'");
			buff.append(myformat.format(startprice/i)+"~"+myformat.format(endprice/i));
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
	
	public String getLevel(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  BandLevel.CREATE+"");
		QueryFilter filter = new QueryFilter(map);
		List<BandLevel> list = this.bandLevelService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
	       for (BandLevel bandLevel : list) {
	         sb.append("['").append(bandLevel.getId()).append("','").append(bandLevel.getLevelName()).append("'],");
	       }
	       if (list.size() > 0) {
	         sb.deleteCharAt(sb.length() - 1);
	       }
	      sb.append("]");
	      setJsonString(sb.toString());
		return "success";
	}
	public String saveLevel(){
		String bandLevelId=getRequest().getParameter("bandPoor.bandLevelId");
		String[] ids = getRequest().getParameter("ids").split(",");
		BandLevel bl=new BandLevel();
		bl.setId(Long.parseLong(bandLevelId));
		if (ids != null) {
			for (String id : ids) {
				BandPoor bandPoor=bandPoorService.get(Long.parseLong(id));
				bandPoor.setBandLevel(bl);
				bandPoorService.save(bandPoor);
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}
}
