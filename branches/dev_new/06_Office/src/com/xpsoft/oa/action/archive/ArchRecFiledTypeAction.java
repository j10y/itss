package com.xpsoft.oa.action.archive;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.archive.ArchRecFiledType;
import com.xpsoft.oa.service.archive.ArchRecFiledTypeService;

import flexjson.JSONSerializer;

/**
 * 收文归档类型
 * @author awen
 *
 */
public class ArchRecFiledTypeAction extends BaseAction {

	@Resource
	private ArchRecFiledTypeService archRecFiledTypeService;
	private ArchRecFiledType archRecFiledType;
	private Long recFiledTypeId;

	/**
	 * @return the recFiledTypeId
	 */
	public Long getRecFiledTypeId() {
		return recFiledTypeId;
	}

	/**
	 * @param recFiledTypeId the recFiledTypeId to set
	 */
	public void setRecFiledTypeId(Long recFiledTypeId) {
		this.recFiledTypeId = recFiledTypeId;
	}

	public ArchRecFiledType getArchRecFiledType() {
		/*  43 */return this.archRecFiledType;
	}

	public void setArchRecFiledType(ArchRecFiledType archRecFiledType) {
		/*  47 */this.archRecFiledType = archRecFiledType;
	}

	public String list() {
		/*  55 */QueryFilter filter = new QueryFilter(getRequest());
		/*  56 */List list = this.archRecFiledTypeService.getAll(filter);

		/*  59 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/*  60 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/*  64 */JSONSerializer serializer = new JSONSerializer();
		/*  65 */buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		/*  66 */buff.append("}");

		/*  68 */this.jsonString = buff.toString();

		/*  70 */return "success";
	}

	public String combo() {
		/*  76 */QueryFilter filter = new QueryFilter(getRequest());
		/*  77 */List<ArchRecFiledType> list = this.archRecFiledTypeService
				.getAll(filter);
		/*  78 */StringBuffer sb = new StringBuffer("[");
		/*  79 */for (ArchRecFiledType type : list) {
			/*  80 */sb.append("['").append(type.getRecFiledTypeId()).append(
					"','").append(type.getTypeName()).append("'],");
		}
		/*  82 */if (list.size() > 0) {
			/*  83 */sb.deleteCharAt(sb.length() - 1);
		}
		/*  85 */sb.append("]");
		/*  86 */setJsonString(sb.toString());
		/*  87 */return "success";
	}

	public String multiDel() {
		/*  96 */String[] ids = getRequest().getParameterValues("ids");
		/*  97 */if (ids != null) {
			/*  98 */for (String id : ids) {
				/*  99 */this.archRecFiledTypeService.remove(new Long(id));
			}
		}

		/* 103 */this.jsonString = "{success:true}";

		/* 105 */return "success";
	}

	public String get() {
		/* 113 */ArchRecFiledType archRecFiledType = (ArchRecFiledType) this.archRecFiledTypeService
				.get(this.recFiledTypeId);

		/* 117 */StringBuffer sb = new StringBuffer("{success:true,data:");

		/* 119 */JSONSerializer serializer = new JSONSerializer();
		/* 120 */sb.append(serializer.exclude(
				new String[] { "class", "department.class" }).serialize(
				archRecFiledType));
		/* 121 */sb.append("}");
		/* 122 */setJsonString(sb.toString());

		/* 124 */return "success";
	}

	public String save() {
		/* 130 */this.archRecFiledTypeService.save(this.archRecFiledType);
		/* 131 */setJsonString("{success:true}");
		/* 132 */return "success";
	}

}
