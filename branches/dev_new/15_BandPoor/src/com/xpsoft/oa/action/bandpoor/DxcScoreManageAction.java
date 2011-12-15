package com.xpsoft.oa.action.bandpoor;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.BandLevel;
import com.xpsoft.oa.model.bandpoor.BandPoor;
import com.xpsoft.oa.model.bandpoor.BeElectedBandPoor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.model.bandpoor.PictureOrdoc;
import com.xpsoft.oa.service.bandpoor.BandLevelService;
import com.xpsoft.oa.service.bandpoor.BandPoorService;
import com.xpsoft.oa.service.bandpoor.BeElectedBandPoorService;

public class DxcScoreManageAction extends BaseAction{
	@Resource
	BeElectedBandPoorService beElectedBandPoorService;
	@Resource
	BandPoorService bandPoorService;
	
	@Resource
	private BandLevelService bandLevelService;
	
	public String list(){
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_status_N_NEQ", BeElectedBandPoor.STATUS_DELETE+"");
		filter.addFilter("Q_infoType_N_EQ", BeElectedBandPoor.TYPE_SCORE+"");
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
			content.append("<th>销售场所</th>");
			content.append("<th>商圈</th>");
			content.append("<th>品类</th>");
			content.append("<th>风格</th>");
			content.append("<th>主力价格</th>");
			content.append("</tr>");
			int i=0;
			Double scoreall=0d;
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
				scoreall+=infopoor.getSaleStoreid().getStoreScore()!=null?infopoor.getSaleStoreid().getStoreScore():0;
			}
			content.append("</table>");
			buff.append("',bandrealScore:'");
			buff.append(scoreall);
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
	public String saveScoreValue(){
		String bandScoreValue=getRequest().getParameter("bandScoreValue");
		String ids=getRequest().getParameter("ids");
		String settype=getRequest().getParameter("settype");
		String sql="update bp_beelectedbandpoor set bandScore="+bandScoreValue;
		if(settype.equals("1")){
			sql+=" where status!="+BeElectedBandPoor.STATUS_DELETE+" and infoType="+BeElectedBandPoor.TYPE_SCORE;
		}else if(settype.equals("2")){
			sql+=" where id in ("+ids+")";
		}
		this.beElectedBandPoorService.updateDatabySql(sql);
		this.jsonString = "{success:true}";
		return "success";
	}
	
	public String multiDel(){
		//String ids=getRequest().getParameter("ids");
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
	
	public String countScoreValue(){
		String year=getRequest().getParameter("beElectedBandPoor.year");
		String poorVersion=getRequest().getParameter("beElectedBandPoor.poorVersion");
		Map map=new HashMap();
		QueryFilter filter = new QueryFilter(map);
		filter.addFilter("Q_status_N_NEQ", BeElectedBandPoor.STATUS_DELETE+"");
		filter.addFilter("Q_infoType_N_EQ", BeElectedBandPoor.TYPE_SCORE+"");
		List<BeElectedBandPoor> list = this.beElectedBandPoorService.getAll(filter);
		for(BeElectedBandPoor bbp:list){
			Map valmap=new HashMap();
			valmap.put("Q_year_N_EQ", year);
			valmap.put("Q_poorVersion_N_EQ", poorVersion);
			valmap.put("Q_bandId.id_L_EQ", bbp.getBandId().getId()+"");
			QueryFilter valfilter = new QueryFilter(valmap);
			valfilter.addFilter("Q_status_N_NEQ", BeElectedBandPoor.STATUS_DELETE+"");
			valfilter.addFilter("Q_infoType_N_EQ", BeElectedBandPoor.TYPE_SCORE+"");
			List<BandPoor> vallist = this.bandPoorService.getAll(valfilter);
			BandPoor bp=new BandPoor();
			if(vallist!=null&&vallist.size()>0){
				bp=vallist.get(0);
				bp.setBandName(bbp.getBandName());
				bp.setModifyDate(new Date());
				bp.setModifyUser(ContextUtil.getCurrentUser());
				bp.setBandScore(bbp.getBandScore());
				bp.setInfoType(BandPoor.TYPE_SCORE);
				Set<InfoPoor> infopoors=bbp.getInfoPoors();
				Set<InfoPoor> bandpoors=new HashSet();
				Double scoreall=0d;
				for(InfoPoor infopoor:infopoors){
					scoreall+=infopoor.getSaleStoreid().getStoreScore()!=null?infopoor.getSaleStoreid().getStoreScore():0;
					bandpoors.add(infopoor);
				}
				bp.setInfoPoors(bandpoors);
				bp.setBandRealScore(scoreall);
				
				Map levelmap=new HashMap();
				QueryFilter levelfilter = new QueryFilter(levelmap);
				levelfilter.addFilter("Q_startValue_DB_LE", scoreall+"");
				levelfilter.addFilter("Q_endValue_DB_GT", scoreall+"");
				List<BandLevel> levellist=bandLevelService.getAll(levelfilter);
				if(levellist.size()>0){
					BandLevel bandLevel=levellist.get(0);
					bp.setBandLevel(bandLevel);
				}
				if(scoreall>=bbp.getBandScore()){
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_YYC);
				}else{
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_BXC);
				}
			}else{
				bp.setBandId(bbp.getBandId());
				bp.setBandName(bbp.getBandName());
				bp.setCreatDate(new Date());
				bp.setCreateUser(ContextUtil.getCurrentUser());
				bp.setYear(Integer.parseInt(year));
				bp.setPoorVersion(Integer.parseInt(poorVersion));
				bp.setBandScore(bbp.getBandScore());
				bp.setInfoType(BandPoor.TYPE_SCORE);
				Set<InfoPoor> infopoors=bbp.getInfoPoors();
				Set<InfoPoor> bandpoors=new HashSet();
				Double scoreall=0d;
				for(InfoPoor infopoor:infopoors){
					scoreall+=infopoor.getSaleStoreid().getStoreScore()!=null?infopoor.getSaleStoreid().getStoreScore():0;
					bandpoors.add(infopoor);
				}
				bp.setInfoPoors(bandpoors);
				bp.setBandRealScore(scoreall);
				Map levelmap=new HashMap();
				QueryFilter levelfilter = new QueryFilter(levelmap);
				levelfilter.addFilter("Q_startValue_DB_LE", scoreall+"");
				levelfilter.addFilter("Q_endValue_DB_GT", scoreall+"");
				List<BandLevel> levellist=bandLevelService.getAll(levelfilter);
				if(levellist.size()>0){
					BandLevel bandLevel=levellist.get(0);
					bp.setBandLevel(bandLevel);
				}
				if(scoreall>=bbp.getBandScore()){
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_YYC);
				}else{
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_BXC);
				}
				bp.setStatus(BandPoor.STATUS_CREATE);
			}
			bandPoorService.save(bp);
		}
		
		this.jsonString = "{success:true}";
		return "success";
	}
}
