package com.xpsoft.oa.action.bandpoor;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.model.bandpoor.BandChannel;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.model.bandpoor.BeElectedBandPoor;
import com.xpsoft.oa.model.bandpoor.BusinessArea;
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.model.bandpoor.MainPrice;
import com.xpsoft.oa.model.bandpoor.PictureOrdoc;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.model.bandpoor.SaleStore;
import com.xpsoft.oa.model.system.FileAttach;
import com.xpsoft.oa.service.bandpoor.BandChannelService;
import com.xpsoft.oa.service.bandpoor.BandService;
import com.xpsoft.oa.service.bandpoor.BandStyleService;
import com.xpsoft.oa.service.bandpoor.BeElectedBandPoorService;
import com.xpsoft.oa.service.bandpoor.BusinessAreaService;
import com.xpsoft.oa.service.bandpoor.FloorService;
import com.xpsoft.oa.service.bandpoor.MainPriceService;
import com.xpsoft.oa.service.bandpoor.PictureOrdocService;
import com.xpsoft.oa.service.bandpoor.ProClassService;
import com.xpsoft.oa.service.bandpoor.SaleStoreService;
import com.xpsoft.oa.service.bandpoor.ScoreManageService;
import com.xpsoft.oa.service.system.FileAttachService;

import flexjson.JSONSerializer;

public class AutoCollenctionInfoAction extends BaseAction{
	@Resource
	ScoreManageService scoreManageService;
	
	@Resource
	ProClassService proClassService;
	
	@Resource
	private FileAttachService fileAttachService;
	
	@Resource
	private PictureOrdocService pictureOrdocService;	
	
	private InfoPoor infoPoor;
			
	public String save(){
		HttpServletRequest request=getRequest();
		String bandId=request.getParameter("bandId");
		String bandName=request.getParameter("bandName");
		String proClassId=request.getParameter("proClassId");
		String proClassName=request.getParameter("proClassName");
		String mainProductName=request.getParameter("mainProductName");
		
		String webSite=request.getParameter("webSite");
		String mainPriceId=request.getParameter("mainPriceId");
		String mainPriceName=request.getParameter("mainPriceName");
		
		String contactUser=request.getParameter("contactUser");
		String saleStoreId=request.getParameter("saleStoreId");
		String saleStoreName=request.getParameter("saleStoreName");
		String saleSroteDesc=request.getParameter("saleSroteDesc");
		String bandChannelId=request.getParameter("bandChannelId");
		String bandChannelName=request.getParameter("bandChannelName");
		String contactPhone=request.getParameter("contactPhone");
		String companyAddress=request.getParameter("companyAddress");
		String companyNature=request.getParameter("companyNature");
		String productLine=request.getParameter("productLine");
		String bandDesc=request.getParameter("bandDesc");
		String picuurepathid=request.getParameter("picuurepathid");
		InfoPoor infoPoor=new InfoPoor();
		if(bandId!=null&&bandId.length()>0){
			Band band=new Band();
			band.setId(Long.parseLong(bandId));
			infoPoor.setBandId(band);
		}
		infoPoor.setBandName(bandName);
		if(proClassId!=null&&proClassId.length()>0){
			ProClass proClass=new ProClass();
			proClass.setId(Long.parseLong(proClassId));
			infoPoor.setProClassId(proClass);
		}
		infoPoor.setProClassName(proClassName);
		infoPoor.setMainProductName(mainProductName);
		infoPoor.setWebSite(webSite);
		if(mainPriceId!=null&&mainPriceId.length()>0){
			MainPrice mainPrice=new MainPrice();
			mainPrice.setId(Long.parseLong(mainPriceId));
			infoPoor.setMainPriceId(mainPrice);
		}
		infoPoor.setMainPriceName(mainPriceName);
		infoPoor.setContactUser(contactUser);
		infoPoor.setContactPhone(contactPhone);
		if(saleStoreId!=null&&saleStoreId.length()>0){
			SaleStore saleStore=new SaleStore();
			saleStore.setId(Long.parseLong(saleStoreId));
			infoPoor.setSaleStoreid(saleStore);
		}
		infoPoor.setSaleStoreName(saleStoreName);
		infoPoor.setSaleSroteDesc(saleSroteDesc);
		if(bandChannelId!=null&&bandChannelId.length()>0){
			BandChannel bandChannel=new BandChannel();
			bandChannel.setId(Long.parseLong(bandChannelId));
			infoPoor.setBandChannelID(bandChannel);
		}
		infoPoor.setBandChannelName(bandChannelName);
		infoPoor.setCompanyAddress(companyAddress);
		infoPoor.setCompanyNature(companyNature);
		infoPoor.setProductLine(productLine);
		infoPoor.setBandDesc(bandDesc);
		
		Map valmap = new HashMap();
		valmap.put("Q_id_L_NEQ", infoPoor.getId()!=null?infoPoor.getId()+"":"0");
		valmap.put("Q_infoType_N_EQ", InfoPoor.TYPE_SCORE+"");
		valmap.put("Q_saleStoreid.id_L_EQ", infoPoor.getSaleStoreid()!=null?infoPoor.getSaleStoreid().getId()+"":"0");
		valmap.put("Q_bandId.id_L_EQ", infoPoor.getBandId()!=null?infoPoor.getBandId().getId()+"":"0");
		valmap.put("Q_infoStatus_N_NEQ", InfoPoor.STATUS_DELETE+"");
		QueryFilter valfilter = new QueryFilter(valmap);
		List vallist=scoreManageService.getAll(valfilter);
//		if(vallist.size()>0){
//			this.jsonString = "{success:false,msg:'同一商场同一品牌已存在！'}";
//			return "success";
//		}
		if(infoPoor.getId()==null){
			infoPoor.setCreatDate(new Date());
			infoPoor.setCreatUser(ContextUtil.getCurrentUser());
			infoPoor.setInfoStatus(InfoPoor.STATUS_CREATE);
			infoPoor.setInfoSource(InfoPoor.SCOUCE_UNDIRCOLLECTION);
			if(infoPoor.getBandId()!=null&&infoPoor.getSaleStoreid()!=null&&vallist.size()==0){
				infoPoor.setInfoType(InfoPoor.TYPE_SCORE);
			}else if(infoPoor.getBandId()!=null&&infoPoor.getSaleStoreName().equals("其他")){
				infoPoor.setInfoType(InfoPoor.TYPE_UNSCORE);
			}else{
				infoPoor.setInfoType(InfoPoor.TYPE_UNKNOWN);
			}
			infoPoor=scoreManageService.save(infoPoor);
			if(picuurepathid!=null&&picuurepathid.length()>0){
				String[] fileid=picuurepathid.split(",");
				for(String fid:fileid){
					FileAttach f=fileAttachService.get(Long.parseLong(fid));
					PictureOrdoc pictureOrdoc=new PictureOrdoc();
					pictureOrdoc.setInfoPoorId(infoPoor);
					pictureOrdoc.setFileAttach(f);
					pictureOrdoc.setCreatetime(new Date());
					pictureOrdoc.setDocName(f.getFileName());
					pictureOrdoc.setDocPath(f.getFilePath());
					pictureOrdoc.setStatus(1);
					pictureOrdocService.save(pictureOrdoc);
				}
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}
}
