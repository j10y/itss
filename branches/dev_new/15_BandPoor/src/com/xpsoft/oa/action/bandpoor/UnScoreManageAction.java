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

public class UnScoreManageAction extends BaseAction{
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
	
	@Resource
	private BusinessAreaService businessAreaService;
	
	@Resource
	private FileAttachService fileAttachService;
	
	@Resource
	private PictureOrdocService pictureOrdocService;
	
	@Resource
	private SaleStoreService saleStoreServiece;
	
	@Resource
	private BandChannelService bandChannelService;
	
	@Resource
	private MainPriceService mainPriceService;
	
	@Resource
	private BeElectedBandPoorService beElectedBandPoorService;
	
	
	private InfoPoor infoPoor;
		
	
	public FloorService getFloorService() {
		return floorService;
	}

	public void setFloorService(FloorService floorService) {
		this.floorService = floorService;
	}

	public BandStyleService getBandStyleService() {
		return bandStyleService;
	}

	public void setBandStyleService(BandStyleService bandStyleService) {
		this.bandStyleService = bandStyleService;
	}

	public ProClassService getProClassService() {
		return proClassService;
	}

	public void setProClassService(ProClassService proClassService) {
		this.proClassService = proClassService;
	}

	public InfoPoor getInfoPoor() {
		return infoPoor;
	}

	public void setInfoPoor(InfoPoor infoPoor) {
		this.infoPoor = infoPoor;
	}

	public BandService getBandService() {
		return bandService;
	}

	public void setBandService(BandService bandService) {
		this.bandService = bandService;
	}

	public ScoreManageService getScoreManageService() {
		return scoreManageService;
	}

	public void setScoreManageService(ScoreManageService scoreManageService) {
		this.scoreManageService = scoreManageService;
	}
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_infoStatus_N_NEQ", "0");
		filter.addFilter("Q_infoType_N_EQ", "2");
		List<InfoPoor> list = this.scoreManageService.getAll(filter);

		Type type = new TypeToken<List<InfoPoor>>() {
		}
		.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		this.jsonString = buff.toString();
		return "success";
	}
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				InfoPoor ip=scoreManageService.get(new Long(id));
				Map map = new HashMap();
				map.put("Q_infoPoorId.id_L_EQ",  ip.getId()+"");
				QueryFilter filter = new QueryFilter(map);
				List<PictureOrdoc> list=pictureOrdocService.getAll(filter);
				for(PictureOrdoc po:list){
					po.setStatus(0);
					pictureOrdocService.save(po);
				}
				ip.setInfoStatus(InfoPoor.STATUS_DELETE);
				scoreManageService.save(ip);
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}
	public String save(){
		HttpServletRequest request=getRequest();
		Map valmap = new HashMap();
		valmap.put("Q_id_L_NEQ", infoPoor.getId()!=null?infoPoor.getId()+"":"0");
		valmap.put("Q_infoType_N_EQ", InfoPoor.TYPE_UNSCORE+"");
		valmap.put("Q_saleStoreid.id_L_EQ", infoPoor.getSaleStoreid().getId()+"");
		valmap.put("Q_bandId.id_L_EQ", infoPoor.getBandId().getId()+"");
		valmap.put("Q_infoStatus_N_NEQ", InfoPoor.STATUS_DELETE+"");
		QueryFilter valfilter = new QueryFilter(valmap);
		List vallist=scoreManageService.getAll(valfilter);
		if(vallist.size()>0){
			this.jsonString = "{success:false,msg:'同一商场同一品牌已存在！'}";
			return "success";
		}
		if(infoPoor.getId()==null){
			infoPoor.setCreatDate(new Date());
			infoPoor.setCreatUser(ContextUtil.getCurrentUser());
			infoPoor.setInfoStatus(InfoPoor.STATUS_CREATE);
			infoPoor.setInfoType(InfoPoor.TYPE_UNSCORE);
			infoPoor.setInfoSource(InfoPoor.SCOUCE_DIRCOLLECTION);
			infoPoor=scoreManageService.save(infoPoor);
			String fileids=request.getParameter("infoPoor.picuurepathid");
			if(fileids!=null&&fileids.length()>0){
				String[] fileid=fileids.split(",");
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
		}else{
			//InfoPoor ifp=scoreManageService.get(infoPoor.getId());
			infoPoor.setCreatDate(new Date());
			infoPoor.setCreatUser(ContextUtil.getCurrentUser());
			infoPoor.setInfoStatus(InfoPoor.STATUS_MODIFY);
			infoPoor.setInfoType(InfoPoor.TYPE_UNSCORE);
			infoPoor.setInfoSource(InfoPoor.SCOUCE_DIRCOLLECTION);
			infoPoor=scoreManageService.save(infoPoor);
			String fileids=request.getParameter("infoPoor.picuurepathid");
			Map map = new HashMap();
			map.put("Q_infoPoorId.id_L_EQ",  infoPoor.getId()+"");
			QueryFilter filter = new QueryFilter(map);
			List<PictureOrdoc> list=pictureOrdocService.getAll(filter);
			for(PictureOrdoc po:list){
				pictureOrdocService.remove(po.getId());
			}
			if(fileids!=null&&fileids.length()>0){
				String[] fileid=fileids.split(",");
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
	public String get(){
		String id=getRequest().getParameter("id");
		infoPoor=this.scoreManageService.get(Long.parseLong(id));
		Map map = new HashMap();
		map.put("Q_infoPoorId.id_L_EQ",  infoPoor.getId()+"");
		QueryFilter filter = new QueryFilter(map);
		List<PictureOrdoc> plist=pictureOrdocService.getAll(filter);
		JSONSerializer json = JsonUtil.getJSONSerializer(new String[] {});
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		sb.append(json.exclude(new String[] { "class"})
				.serialize(infoPoor));
		sb.append("],picture:");
		sb.append(json.exclude(new String[] { "class"})
				.serialize(plist));
		sb.append("}");
		setJsonString(sb.toString());
		return "success";
	}
	
	public String getBands(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  Band.CREATE+"");
		QueryFilter filter = new QueryFilter(map);
		List<Band> list=bandService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
	       for (Band band : list) {
	         sb.append("['").append(band.getId()).append("','").append(band.getBandChName()+"/"+band.getBandEnName()).append("'],");
	       }
	       if (list.size() > 0) {
	         sb.deleteCharAt(sb.length() - 1);
	       }
	      sb.append("]");
	      setJsonString(sb.toString());
		return "success";
	}
	
	public String getProClass(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  ProClass.CREATE+"");
		QueryFilter filter = new QueryFilter(map);
		List<ProClass> list=proClassService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
	       for (ProClass proClass : list) {
	         sb.append("['").append(proClass.getId()).append("','").append(proClass.getProClassName()).append("'],");
	       }
	       if (list.size() > 0) {
	         sb.deleteCharAt(sb.length() - 1);
	       }
	      sb.append("]");
	      setJsonString(sb.toString());
		return "success";
	}
	
	public String getBandStyle(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  BandStyle.CREATE+"");
		String proClassId=getRequest().getParameter("proClassId");
		map.put("Q_proClassId.id_L_EQ", proClassId);
		QueryFilter filter = new QueryFilter(map);
		List<BandStyle> list=bandStyleService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
	       for (BandStyle bandStyle : list) {
	         sb.append("['").append(bandStyle.getId()).append("','").append(bandStyle.getStyleName()).append("'],");
	       }
	       if (list.size() > 0) {
	         sb.deleteCharAt(sb.length() - 1);
	       }
	      sb.append("]");
	      setJsonString(sb.toString());
		return "success";
	}
	public String getFloor(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  Floor.CREATE+"");
		QueryFilter filter = new QueryFilter(map);
		List<Floor> list=floorService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
	       for (Floor floor : list) {
	         sb.append("['").append(floor.getId()).append("','").append(floor.getFloorName()).append("'],");
	       }
	       if (list.size() > 0) {
	         sb.deleteCharAt(sb.length() - 1);
	       }
	      sb.append("]");
	      setJsonString(sb.toString());
		return "success";
	}
	public String getBusinessArea(){
		String saleStoreid=getRequest().getParameter("saleStoreid");
		SaleStore saleStore =saleStoreServiece.get(Long.parseLong(saleStoreid));
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  BusinessArea.CREATE+"");
		map.put("Q_id_L_EQ",  saleStore.getAllowAreaId().getId()+"");
		QueryFilter filter = new QueryFilter(map);
		List<BusinessArea> list=businessAreaService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
	       for (BusinessArea businessArea : list) {
	         sb.append("['").append(businessArea.getId()).append("','").append(businessArea.getAreaName()).append("'],");
	       }
	       if (list.size() > 0) {
	         sb.deleteCharAt(sb.length() - 1);
	       }
	      sb.append("]");
	      setJsonString(sb.toString());
		return "success";
	}
	
	public String getMainPrice(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  Floor.CREATE+"");
		QueryFilter filter = new QueryFilter(map);
		List<MainPrice> list=mainPriceService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
       for (MainPrice mainPrice : list) {
         sb.append("['").append(mainPrice.getId()).append("','").append(mainPrice.getPriceName()).append("'],");
       }
       if (list.size() > 0) {
         sb.deleteCharAt(sb.length() - 1);
       }
      sb.append("]");
      setJsonString(sb.toString());
	return "success";
	}
	public String getSaleStore(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  Floor.CREATE+"");
		QueryFilter filter = new QueryFilter(map);
		List<SaleStore> list=saleStoreServiece.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
       for (SaleStore saleStore : list) {
         sb.append("['").append(saleStore.getId()).append("','").append(saleStore.getStoreName()).append("'],");
       }
       if (list.size() > 0) {
         sb.deleteCharAt(sb.length() - 1);
       }
      sb.append("]");
      setJsonString(sb.toString());
		return "success";
	}
	public String getChannel(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  Floor.CREATE+"");
		QueryFilter filter = new QueryFilter(map);
		List<BandChannel> list=bandChannelService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
       for (BandChannel bandChannel : list) {
         sb.append("['").append(bandChannel.getId()).append("','").append(bandChannel.getChannelName()).append("'],");
       }
       if (list.size() > 0) {
         sb.deleteCharAt(sb.length() - 1);
       }
      sb.append("]");
      setJsonString(sb.toString());
		return "success";
	}
	/**
	 * 审批信息
	 * @return
	 */
	public String applyRecord(){
		String[] ids = getRequest().getParameterValues("ids");
		String status=getRequest().getParameter("status");
		if (ids != null) {
			for (String id : ids) {
				InfoPoor ip=scoreManageService.get(new Long(id));
				if(status.equals(InfoPoor.STATUS_PASS+"")){
					Map map = new HashMap();
					map.put("Q_bandId.id_L_EQ",  ip.getBandId().getId()+"");
					map.put("Q_status_N_NEQ",  BeElectedBandPoor.STATUS_DELETE+"");
					QueryFilter filter = new QueryFilter(map);
					List beElectedBandPoorlist=beElectedBandPoorService.getAll(filter);
					if(beElectedBandPoorlist==null||beElectedBandPoorlist.size()==0){
						BeElectedBandPoor beElectedBandPoor=new BeElectedBandPoor();
						beElectedBandPoor.setBandId(ip.getBandId());
						beElectedBandPoor.setBandName(ip.getBandName());
						beElectedBandPoor.setCreatDate(new Date());
						beElectedBandPoor.setCreateUser(ContextUtil.getCurrentUser());
						beElectedBandPoor.setInfoType(BeElectedBandPoor.TYPE_SCORE);
						beElectedBandPoor.setStatus(BeElectedBandPoor.STATUS_CREATE);
						Set<InfoPoor> ips=beElectedBandPoor.getInfoPoors();
						ips.add(ip);
						beElectedBandPoor.setInfoPoors(ips);
						beElectedBandPoorService.save(beElectedBandPoor);
					}else{
						BeElectedBandPoor beElectedBandPoor=(BeElectedBandPoor) beElectedBandPoorlist.get(0);
						Set<InfoPoor> ips=beElectedBandPoor.getInfoPoors();
						ips.add(ip);
						beElectedBandPoor.setInfoPoors(ips);
						beElectedBandPoor.setModifyDate(new Date());
						beElectedBandPoor.setModifyUser(ContextUtil.getCurrentUser());
						beElectedBandPoorService.save(beElectedBandPoor);
					}
				}
				ip.setInfoStatus(Integer.parseInt(status));
				scoreManageService.save(ip);
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}
	public BusinessAreaService getBusinessAreaService() {
		return businessAreaService;
	}

	public void setBusinessAreaService(BusinessAreaService businessAreaService) {
		this.businessAreaService = businessAreaService;
	}

	public FileAttachService getFileAttachService() {
		return fileAttachService;
	}

	public void setFileAttachService(FileAttachService fileAttachService) {
		this.fileAttachService = fileAttachService;
	}

	public PictureOrdocService getPictureOrdocService() {
		return pictureOrdocService;
	}

	public void setPictureOrdocService(PictureOrdocService pictureOrdocService) {
		this.pictureOrdocService = pictureOrdocService;
	}

	public SaleStoreService getSaleStoreServiece() {
		return saleStoreServiece;
	}

	public void setSaleStoreServiece(SaleStoreService saleStoreServiece) {
		this.saleStoreServiece = saleStoreServiece;
	}

	public BandChannelService getBandChannelService() {
		return bandChannelService;
	}

	public void setBandChannelService(BandChannelService bandChannelService) {
		this.bandChannelService = bandChannelService;
	}

	public MainPriceService getMainPriceService() {
		return mainPriceService;
	}

	public void setMainPriceService(MainPriceService mainPriceService) {
		this.mainPriceService = mainPriceService;
	}

	public BeElectedBandPoorService getBeElectedBandPoorService() {
		return beElectedBandPoorService;
	}

	public void setBeElectedBandPoorService(
			BeElectedBandPoorService beElectedBandPoorService) {
		this.beElectedBandPoorService = beElectedBandPoorService;
	}	
}
