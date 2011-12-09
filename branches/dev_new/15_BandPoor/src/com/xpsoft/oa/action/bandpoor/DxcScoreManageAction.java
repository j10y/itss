package com.xpsoft.oa.action.bandpoor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BeElectedBandPoor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.service.bandpoor.BeElectedBandPoorService;

public class DxcScoreManageAction extends BaseAction{
	@Resource
	BeElectedBandPoorService beElectedBandPoorService;
	
	public String list(){
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_status_N_NEQ", BeElectedBandPoor.STATUS_DELETE+"");
		List<BeElectedBandPoor> list = this.beElectedBandPoorService.getAll(filter);

		Type type = new TypeToken<List<BeElectedBandPoor>>() {
		}
		.getType();
		
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(
				",result:[");;
		for(BeElectedBandPoor beElectedBandPoor:list){
			Set<InfoPoor> infopoors=beElectedBandPoor.getInfoPoors();
			buff.append("{id:'")
			.append(beElectedBandPoor.getId())
			.append("',bandName:'")
			.append(beElectedBandPoor.getBandName())
			.append("',saleStoreNum:'")
			.append(infopoors.size())
			.append("',bandScore:'")
			.append(beElectedBandPoor.getBandScore()!=null?beElectedBandPoor.getBandScore():"")
			.append("',status:'")
			.append(beElectedBandPoor.getStatus())
			.append("',infoType:'")
			.append(beElectedBandPoor.getInfoType())
			.append("',creatDate:'")
			.append(beElectedBandPoor.getCreatDate())
			.append("',createUser:'")
			.append(beElectedBandPoor.getCreateUser().getUsername());
			StringBuffer content = new StringBuffer();
			content.append("<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\">");
			content.append("<tr >");
			content.append("<th>序号</th>");
			content.append("<th>品类</th>");
			content.append("<th>风格</th>");
			content.append("<th>主力价格</th>");
			content.append("<th>销售场所</th>");
			content.append("<th>商圈</th>");
			content.append("</tr>");
			int i=0;
			for(InfoPoor infopoor:infopoors){
				i++;
				content.append("<tr>");
				content.append("<td>");
				content.append(i);
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
				content.append("<td>");
				content.append(infopoor.getSaleStoreName());
				content.append("</td>");
				content.append("<td>");
				content.append(infopoor.getBandBusinessAreaName());
				content.append("</td>");
				content.append("</tr>");
			}
			content.append("</table>");
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
