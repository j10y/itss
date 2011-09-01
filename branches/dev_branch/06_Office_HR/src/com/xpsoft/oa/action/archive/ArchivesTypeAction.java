package com.xpsoft.oa.action.archive;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.archive.ArchRecUser;
import com.xpsoft.oa.model.archive.ArchivesType;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.archive.ArchivesTypeService;

import flexjson.JSONSerializer;

public class ArchivesTypeAction extends BaseAction {

	@Resource
	private ArchivesTypeService archivesTypeService;
	private ArchivesType archivesType;
	private Long typeId;

	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public ArchivesType getArchivesType() {
		return this.archivesType;
	}

	public void setArchivesType(ArchivesType archivesType) {
		this.archivesType = archivesType;
	}

	public String combo() {
		StringBuffer sb = new StringBuffer();

		List<ArchivesType> dutySectionList = this.archivesTypeService.getAll();
		//根据提交人部门获取部门负责人
		AppUser appUser= ContextUtil.getCurrentUser();
		String sql="select * from arch_rec_user where depId="+appUser.getDepartment().getDepId();
		List list=this.archivesTypeService.findDataList(sql);
		String charge="1";
		if(list!=null&&list.size()>0){
			charge=((Map)list.get(0)).get("deptUserId").toString();
		}
		sb.append("[");
		for (ArchivesType dutySection : dutySectionList) {
			sb.append("['").append(dutySection.getTypeId()).append("','")
					.append(dutySection.getTypeName()).append("','")
					.append(dutySection.getProcessDefId()!=null?dutySection.getProcessDefId():"0").append("','")
					.append(charge)
					.append("'],");
		}
		if (dutySectionList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		setJsonString(sb.toString());
		return "success";
	}

	public String tree() {
		List<ArchivesType> typeList = this.archivesTypeService.getAll();

		StringBuffer sb = new StringBuffer();
		sb.append("[{id:'0',text:'所有公文分类',expanded:true,children:[");
		for (ArchivesType type : typeList) {
			sb.append("{id:'" + type.getTypeId())
					.append("',text:'" + type.getTypeName())
					.append("',leaf:true,expanded:true},");
		}
		if (typeList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]}]");
		setJsonString(sb.toString());
		return "success";
	}

	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<ArchivesType> list = this.archivesTypeService.getAll(filter);

		Type type = new TypeToken<List<ArchivesType>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

//		Gson gson = new Gson();
//		buff.append(gson.toJson(list, type));
//		buff.append("}");
//		this.jsonString = buff.toString();
		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();

		return "success";
	}

	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				this.archivesTypeService.remove(new Long(id));
			}
		}

		this.jsonString = "{success:true}";

		return "success";
	}

	public String get() {
		ArchivesType archivesType = (ArchivesType) this.archivesTypeService
				.get(this.typeId);
		//根据提交人部门获取部门负责人
		AppUser appUser= ContextUtil.getCurrentUser();
		String sql="select * from arch_rec_user where depId="+appUser.getDepartment().getDepId();
		List list=this.archivesTypeService.findDataList(sql);
		String charge="1";
		if(list!=null&&list.size()>0){
			charge=((Map)list.get(0)).get("userId").toString();
		}
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(archivesType));
		sb.append(",charge:'"+charge+"'}");
		setJsonString(sb.toString());

		return "success";
	}

	public String save() {
		this.archivesTypeService.save(this.archivesType);
		setJsonString("{success:true}");
		return "success";
	}

	public String saveList() {
		String data = getRequest().getParameter("data");
		if (StringUtils.isNotEmpty(data)) {
			Gson gson = new Gson();
			ArchivesType[] atl = gson.fromJson(data,
					new com.google.gson.reflect.TypeToken<ArchivesType[]>() {
					}.getType());
			for (ArchivesType archivesType : atl) {
				if (archivesType.getTypeId().longValue() == -1L) {
					archivesType.setTypeId(null);
				}
				if (archivesType.getProcessDefId() != null) {
					this.archivesTypeService.save(archivesType);
				} else {
					setJsonString("{success:false}");
				}
			}
		}
		setJsonString("{success:true}");
		return "success";
	}

}
