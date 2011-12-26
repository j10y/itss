package com.xpsoft.oa.action.system;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.MrbsRoom;
import com.xpsoft.oa.service.system.MrbsRoomService;

public class MrbsRoomAction extends BaseAction {

	@Resource
	private MrbsRoomService mrbsRoomService;
	private MrbsRoom mrbsArea;
	public MrbsRoomService getMrbsRoomService() {
		return mrbsRoomService;
	}

	public void setMrbsRoomService(MrbsRoomService mrbsRoomService) {
		this.mrbsRoomService = mrbsRoomService;
	}

	public MrbsRoom getMrbsRoom() {
		return mrbsArea;
	}

	public void setMrbsRoom(MrbsRoom mrbsArea) {
		this.mrbsArea = mrbsArea;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;

	

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<MrbsRoom> list = this.mrbsRoomService.getAll(filter);

		Type type = new TypeToken<List<MrbsRoom>>() {
		}
		.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		Gson gson = new Gson();
		toJson(list,buff);
		buff.append("}");

		this.jsonString = buff.toString();

		return "success";
	}

	public static void  toJson(List<MrbsRoom> list,StringBuffer sb){
		sb.append("[");
		for(MrbsRoom f:list){
			sb.append("{")
					.append("'id':'" + f.getId() + "',")
					.append("'roomName':'" + f.getRoomName() + "',")
					.append("'desc':'" + f.getDescription() + "',")
					.append("'capacity':'" + f.getCapacity() + "',")
					.append("'adminEmail':'" + f.getRoomAdminEmail() + "'}");
		}
		if(list.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		
	}
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		  if (ids != null) {
			 for (String id : ids) {
				this.mrbsRoomService.remove(new Long(id));
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}

	public String get() {
		MrbsRoom f = (MrbsRoom) this.mrbsRoomService
				.get(this.id);

		Gson gson = new Gson();

		StringBuffer sb = new StringBuffer("{success:true,data:");
		       sb.append("{")
		       		.append("'id':'" + f.getId() + "',")
					.append("'roomName':'" + f.getRoomName() + "',")
					.append("'desc':'" + f.getDescription() + "',")
					.append("'capacity':'" + f.getCapacity() + "',")
					.append("'adminEmail':'" + f.getRoomAdminEmail() + "'}");
		       
		sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}

	/*public String save() {
		String data = getRequest().getParameter("funUrls");
		String[] funUrls = data.split(",");
		this.mrbsRoomService.save(this.mrbsArea);
		if(this.mrbsArea.getId()!=null){
			this.mrbsRoomService.updateFunUrl(funUrls,this.mrbsArea.getId());
		}
		setJsonString("{success:true}");
		return "success";
	}*/
}
