package com.xpsoft.oa.action.admin;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import com.xpsoft.core.command.QueryFilter;

import com.xpsoft.core.web.action.BaseAction;

import com.xpsoft.core.web.paging.PagingBean;

import com.xpsoft.oa.model.admin.AssetsType;

import com.xpsoft.oa.service.admin.AssetsTypeService;

import com.xpsoft.oa.service.admin.FixedAssetsService;

import java.lang.reflect.Type;

import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

public class AssetsTypeAction extends BaseAction {

	@Resource
	private AssetsTypeService assetsTypeService;
	private AssetsType assetsType;

	@Resource
	private FixedAssetsService fixedAssetsService;
	private Long assetsTypeId;

	public Long getAssetsTypeId() {
		/* 36 */return this.assetsTypeId;
	}

	public void setAssetsTypeId(Long assetsTypeId) {
		/* 40 */this.assetsTypeId = assetsTypeId;
	}

	public AssetsType getAssetsType() {
		/* 44 */return this.assetsType;
	}

	public void setAssetsType(AssetsType assetsType) {
		/* 48 */this.assetsType = assetsType;
	}

	public String list() {
		/* 56 */QueryFilter filter = new QueryFilter(getRequest());
		/* 57 */List<AssetsType> list = assetsTypeService.getAll(filter);

		Type type = new TypeToken<List<AssetsType>>() {
		}.getType();
		/* 60 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 61 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		/* 62 */Gson gson = new Gson();
		/* 63 */buff.append(gson.toJson(list, type));
		/* 64 */buff.append("}");
		/* 65 */this.jsonString = buff.toString();
		/* 66 */return "success";
	}

	public String tree() {
		/* 76 */List<AssetsType> list = this.assetsTypeService.getAll();
		/* 77 */StringBuffer sb = new StringBuffer(
				"[{id:'0',text:'资产类型',expanded:true,children:[");
		/* 78 */for (AssetsType type : list) {
			/* 79 */sb.append("{id:'" + type.getAssetsTypeId() + "',text:'"
					+ type.getTypeName() + "',leaf:true},");
		}
		/* 81 */if (list.size() > 0) {
			/* 82 */sb.deleteCharAt(sb.length() - 1);
		}
		/* 84 */sb.append("]}]");
		/* 85 */setJsonString(sb.toString());
		/* 86 */return "success";
	}

	public String multiDel() {
		/* 93 */String[] ids = getRequest().getParameterValues("ids");
		/* 94 */if (ids != null) {
			/* 95 */for (String id : ids) {
				/* 96 */QueryFilter filter = new QueryFilter(getRequest());
				/* 97 */filter.addFilter("Q_assetsType.assetsTypeId_L_EQ", id);
				/* 98 */List list = this.fixedAssetsService.getAll(filter);
				/* 99 */if (list.size() > 0) {
					/* 100 */this.jsonString = "{success:false,message:'该类型下还有资产，请将资产移走后再进行删除！'}";
					/* 101 */return "success";
				}
				/* 103 */this.assetsTypeService.remove(new Long(id));
			}
		}

		/* 107 */this.jsonString = "{success:true}";

		/* 109 */return "success";
	}

	public String get() {
		/* 117 */AssetsType assetsType = (AssetsType) this.assetsTypeService
				.get(this.assetsTypeId);
		/* 118 */Gson gson = new Gson();

		/* 120 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 121 */sb.append(gson.toJson(assetsType));
		/* 122 */sb.append("}");
		/* 123 */setJsonString(sb.toString());
		/* 124 */return "success";
	}

	public String save() {
		/* 130 */this.assetsTypeService.save(this.assetsType);
		/* 131 */setJsonString("{success:true}");
		/* 132 */return "success";
	}

	public String combox() {
		/* 138 */List<AssetsType> list = this.assetsTypeService.getAll();
		/* 139 */StringBuffer buff = new StringBuffer("[");
		/* 140 */for (AssetsType assetsType : list) {
			/* 141 */buff.append("['" + assetsType.getAssetsTypeId() + "','"
					+ assetsType.getTypeName() + "'],");
		}
		/* 143 */if (list.size() > 0) {
			/* 144 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 146 */buff.append("]");
		/* 147 */setJsonString(buff.toString());
		/* 148 */return "success";
	}

}
