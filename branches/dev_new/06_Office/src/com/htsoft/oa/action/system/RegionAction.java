package com.htsoft.oa.action.system;

import com.google.gson.Gson;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.oa.model.system.Region;
import com.htsoft.oa.service.system.RegionService;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class RegionAction extends BaseAction {

	@Resource
	private RegionService regionService;
	private Region region;
	private Long regionId;

	public Long getRegionId() {
		/* 29 */return this.regionId;
	}

	public void setRegionId(Long regionId) {
		/* 33 */this.regionId = regionId;
	}

	public Region getRegion() {
		/* 37 */return this.region;
	}

	public void setRegion(Region region) {
		/* 41 */this.region = region;
	}

	public String list() {
		/* 49 */List<Region> list = null;
		/* 50 */StringBuffer buff = new StringBuffer("[");
		/* 51 */if (this.regionId == null) {
			/* 53 */list = this.regionService.getProvince();

			/* 55 */for (Region province : list)
				/* 56 */buff.append("['" + province.getRegionId() + "','"
						+ province.getRegionName() + "'],");
		} else {
			/* 60 */list = this.regionService.getCity(this.regionId);
			/* 61 */if (list.size() > 0) {
				/* 62 */for (Region city : list)
					/* 63 */buff.append("'" + city.getRegionName() + "',");
			} else {
				/* 66 */setRegion((Region) this.regionService
						.get(this.regionId));
				/* 67 */buff.append("'" + this.region.getRegionName() + "',");
			}
		}
		/* 70 */buff.deleteCharAt(buff.length() - 1);
		/* 71 */buff.append("]");
		/* 72 */setJsonString(buff.toString());
		/* 73 */return "success";
	}

	public String tree() {
		/* 81 */StringBuffer buff = new StringBuffer(
				"[{id:'0',text:'中国',expanded:true,children:[");
		/* 82 */List<Region> listProvince = this.regionService.getProvince();
		/* 83 */for (Region province : listProvince) {
			/* 84 */buff.append("{id:'" + province.getRegionId() + "',text:'"
					+ province.getRegionName() + "',");
			/* 85 */buff.append(findCity(province.getRegionId()));
		}
		/* 87 */if (!listProvince.isEmpty()) {
			/* 88 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 90 */buff.append("]}]");
		/* 91 */setJsonString(buff.toString());
		/* 92 */return "success";
	}

	public String findCity(Long regionId) {
		/* 100 */StringBuffer buff1 = new StringBuffer("");
		/* 101 */List<Region> listCity = this.regionService.getCity(regionId);
		/* 102 */if (listCity.size() == 0) {
			/* 103 */buff1.append("leaf:true},");
			/* 104 */return buff1.toString();
		}
		/* 106 */buff1.append("children:[");
		/* 107 */for (Region city : listCity) {
			/* 108 */buff1.append("{id:'" + city.getRegionId() + "',text:'"
					+ city.getRegionName() + "',leaf:true},");
		}

		/* 111 */buff1.deleteCharAt(buff1.length() - 1);
		/* 112 */buff1.append("]},");
		/* 113 */return buff1.toString();
	}

	public String multiDel() {
		/* 122 */String[] ids = getRequest().getParameterValues("ids");
		/* 123 */if (ids != null) {
			/* 124 */for (String id : ids) {
				/* 125 */this.regionService.remove(new Long(id));
			}
		}

		/* 129 */this.jsonString = "{success:true}";

		/* 131 */return "success";
	}

	public String get() {
		/* 139 */Region region = (Region) this.regionService.get(this.regionId);

		/* 141 */Gson gson = new Gson();

		/* 143 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 144 */sb.append(gson.toJson(region));
		/* 145 */sb.append("}");
		/* 146 */setJsonString(sb.toString());

		/* 148 */return "success";
	}

	public String save() {
		/* 154 */this.regionService.save(this.region);
		/* 155 */setJsonString("{success:true}");
		/* 156 */return "success";
	}
}
