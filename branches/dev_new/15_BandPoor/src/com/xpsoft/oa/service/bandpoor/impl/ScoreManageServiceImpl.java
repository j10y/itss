package com.xpsoft.oa.service.bandpoor.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.oa.dao.bandpoor.ScoreManageDao;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.model.bandpoor.BandChannel;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.model.bandpoor.PictureOrdoc;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.model.bandpoor.SaleStore;
import com.xpsoft.oa.model.system.FileAttach;
import com.xpsoft.oa.service.bandpoor.BandService;
import com.xpsoft.oa.service.bandpoor.BandStyleService;
import com.xpsoft.oa.service.bandpoor.FloorService;
import com.xpsoft.oa.service.bandpoor.ProClassService;
import com.xpsoft.oa.service.bandpoor.ScoreManageService;

public class ScoreManageServiceImpl extends BaseServiceImpl<InfoPoor> implements ScoreManageService{
	private ScoreManageDao dao;
	
	public ScoreManageServiceImpl(ScoreManageDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Resource
	ScoreManageService scoreManageService;
	
	@Resource
	private BandService bandService;
	
	@Resource
	ProClassService proClassService;
	
	@Resource
	private BandStyleService bandStyleService;
	
	@Resource
	private FloorService floorService;
	
	
	public ScoreManageDao getDao() {
		return dao;
	}

	public void setDao(ScoreManageDao dao) {
		this.dao = dao;
	}

	public ScoreManageService getScoreManageService() {
		return scoreManageService;
	}

	public void setScoreManageService(ScoreManageService scoreManageService) {
		this.scoreManageService = scoreManageService;
	}

	public BandService getBandService() {
		return bandService;
	}

	public void setBandService(BandService bandService) {
		this.bandService = bandService;
	}

	public ProClassService getProClassService() {
		return proClassService;
	}

	public void setProClassService(ProClassService proClassService) {
		this.proClassService = proClassService;
	}

	public BandStyleService getBandStyleService() {
		return bandStyleService;
	}

	public void setBandStyleService(BandStyleService bandStyleService) {
		this.bandStyleService = bandStyleService;
	}

	public FloorService getFloorService() {
		return floorService;
	}

	public void setFloorService(FloorService floorService) {
		this.floorService = floorService;
	}

	public boolean validateUnique(Map params) {
		// TODO Auto-generated method stub
		return false;
	}

	public void saveInfoPoor(List<InfoPoor> list) {
		// TODO Auto-generated method stub
		for(InfoPoor infoPoor:list){
			infoPoor.setMainPriceId(null);
			infoPoor.setMainPriceName(infoPoor.getMainPriceStart()+"~"+infoPoor.getMainPriceEnd());
			if(infoPoor.getId()==null){
				infoPoor.setCreatDate(new Date());
				infoPoor.setCreatUser(ContextUtil.getCurrentUser());
				infoPoor.setInfoStatus(InfoPoor.STATUS_CREATE);
				infoPoor.setInfoType(InfoPoor.TYPE_SCORE);
				infoPoor.setInfoSource(InfoPoor.SCOUCE_DIRCOLLECTION);
				infoPoor=this.save(infoPoor);
			}else{
				//InfoPoor ifp=scoreManageService.get(infoPoor.getId());
				infoPoor.setCreatDate(new Date());
				infoPoor.setCreatUser(ContextUtil.getCurrentUser());
				infoPoor.setInfoStatus(InfoPoor.STATUS_MODIFY);
				infoPoor.setInfoType(InfoPoor.TYPE_SCORE);
				infoPoor.setInfoSource(InfoPoor.SCOUCE_DIRCOLLECTION);
				infoPoor=this.save(infoPoor);
			}
		}
	}
	
	public Map checkBrand(int row,Sheet sheet,HttpServletRequest request,String saleStoreName,SaleStore saleStoreId,String checkUser,String checkDate){
		String jsonString="";
		List<InfoPoor> list = new ArrayList<InfoPoor>();
		Map map=new HashMap();
		map.put("result", "true");
		for(int i = 3; i < row; i++){
			InfoPoor ifpoor=new InfoPoor();
			ifpoor.setSaleStoreid(saleStoreId);
			ifpoor.setSaleStoreName(saleStoreName);
			ifpoor.setBandBusinessAreaId(saleStoreId.getAllowAreaId());
			ifpoor.setBandBusinessAreaName(saleStoreId.getAllowAreaId().getAreaName());
			String index=sheet.getCell(0, i).getContents();				
			String zhName=sheet.getCell(1, i).getContents();
			String enName=sheet.getCell(2, i).getContents();
//			zhName=replaceBlank(zhName);
//			enName=replaceBlank(enName);
			QueryFilter bandfilter = new QueryFilter(request);
			if(zhName!=null&&zhName.length()>0){
				bandfilter.addFilter("Q_bandChName_S_EQ", zhName);
			}
			if(enName!=null&&enName.length()>0){
				bandfilter.addFilter("Q_bandEnName_S_EQ", enName);
			}
//			bandfilter.addFilter("Q_bandChName_S_EQ", zhName);
//			bandfilter.addFilter("Q_bandEnName_S_EQ", enName);
			List bandlist=new ArrayList();
			try {
				bandlist = bandService.getAll(bandfilter);
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonString = "{success:true,flag:'0',msg:'excel中序号为【" + index + "】的数据中英文品牌出现异常，请核实！'}";
				map.put("jsonString", jsonString);
				map.put("result", "false");
				break;
			}
			if(bandlist.size()>0){
				Band bandId=(Band) bandlist.get(0);
				ifpoor.setBandId(bandId);
				ifpoor.setBandName((bandId.getBandChName()!=null?bandId.getBandChName():"")+"/"+(bandId.getBandEnName()!=null?bandId.getBandEnName():""));
			}else{
				jsonString = "{success:true,flag:'0',msg:'excel中序号为【" + index + "】的数据中英文品牌在已有品牌不存在，请核实！'}";
				map.put("jsonString", jsonString);
				map.put("result", "false");
				break;
			}

			Map valmap = new HashMap();
			valmap.put("Q_infoType_N_EQ", InfoPoor.TYPE_SCORE+"");
			valmap.put("Q_saleStoreid.id_L_EQ", ifpoor.getSaleStoreid().getId()+"");
			valmap.put("Q_bandId.id_L_EQ", ifpoor.getBandId().getId()+"");
			valmap.put("Q_infoStatus_N_NEQ", InfoPoor.STATUS_DELETE+"");
			QueryFilter valfilter = new QueryFilter(valmap);
			List vallist=scoreManageService.getAll(valfilter);
			if(vallist.size()>0){
				ifpoor=(InfoPoor) vallist.get(0);
			}
			ifpoor.setCheckUser(checkUser);
			ifpoor.setCheckDate(checkDate);
			String floorNum=sheet.getCell(3, i).getContents();
			QueryFilter floorfilter = new QueryFilter(request);
			floorfilter.addFilter("Q_floorName_S_EQ", floorNum);
			List floorlist=floorService.getAll(floorfilter);
			if(floorlist.size()>0){
				Floor floorNumId=(Floor) floorlist.get(0);
				ifpoor.setFloorNumId(floorNumId);
				ifpoor.setFloorNumName(floorNum);
			}else{
				jsonString = "{success:true,flag:'0',msg:'excel中序号为【" + index + "】的数据中楼层在已有楼层不存在，请核实！'}";
				map.put("jsonString", jsonString);
				map.put("result", "false");
				break;
			}
			String proClassName=sheet.getCell(4, i).getContents();
			QueryFilter proClassfilter = new QueryFilter(request);
			proClassfilter.addFilter("Q_proClassNum_S_EQ", replaceBlank(proClassName));
			List proClasslist=proClassService.getAll(proClassfilter);
			if(proClasslist.size()>0){
				ProClass proClassId=(ProClass) proClasslist.get(0);
				ifpoor.setProClassId(proClassId);
				ifpoor.setProClassName(proClassId.getProClassName());
			}else{
				jsonString = "{success:true,flag:'0',msg:'excel中序号为【" + index + "】的数据中品类在已有品类中不存在，请核实！'}";
				map.put("jsonString", jsonString);
				map.put("result", "false");
				break;
			}
			
			String mainStyleName=sheet.getCell(5, i).getContents();
			
			QueryFilter mainStylefilter = new QueryFilter(request);
			mainStylefilter.addFilter("Q_styleNum_S_EQ", replaceBlank(mainStyleName));
			mainStylefilter.addFilter("Q_proClassId.id_L_EQ", ifpoor.getProClassId().getId()+"");
			List mainStylelist=bandStyleService.getAll(mainStylefilter);
			if(mainStylelist.size()>0){
				BandStyle bandStyleId=(BandStyle) mainStylelist.get(0);
				ifpoor.setBandStyleId(bandStyleId);
				ifpoor.setBandStyleName(bandStyleId.getStyleName());
			}else{
				jsonString = "{success:true,flag:'0',msg:'excel中序号为【" + index + "】的数据中品牌风格在已有品牌风格中不存在，请核实！'}";
				map.put("jsonString", jsonString);
				map.put("result", "false");
				break;
			}
			String mainPriceName=sheet.getCell(6, i).getContents();
			String[] prices=mainPriceName.split("-");
			if(prices.length!=2){
				jsonString = "{success:true,flag:'0',msg:'excel中序号为【" + index + "】的数据中主力价格带填写不符合要求，请核实！'}";
				map.put("jsonString", jsonString);
				map.put("result", "false");
				break;
			}
			try {
				ifpoor.setMainPriceStart(Long.parseLong(replaceBlank(prices[0])));
				ifpoor.setMainPriceEnd(Long.parseLong(replaceBlank(prices[1])));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonString = "{success:true,flag:'0',msg:'excel中序号为【" + index + "】的数据中主力价格带填写不符合要求，需要是整数，请核实！'}";
				map.put("jsonString", jsonString);
				map.put("result", "false");
				break;
			}
			ifpoor.setMainPriceName(mainPriceName);
			String bandDesc=sheet.getCell(7, i).getContents();
			ifpoor.setBandDesc(bandDesc);
			ifpoor.setBandChannelName(AppUtil.getPropertity("bandChannelName"));
			BandChannel bandChannelID=new BandChannel();
			bandChannelID.setId(Long.parseLong(AppUtil.getPropertity("bandChannelId")));
			ifpoor.setBandChannelID(bandChannelID);
			list.add(ifpoor);
		}
		map.put("list", list);
		return map;
	}
	
	public String replaceBlank(String str) {
		 String dest ="";
		 if (str!=null) {
		 Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		 Matcher m = p.matcher(str);
		 dest = m.replaceAll("");
		 }
		 dest=dest.replaceAll("　",""); 
		 return dest;
	}
}
	 