package com.xpsoft.oa.action.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.CompanyService;
import com.xpsoft.oa.service.system.DepartmentService;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class DepartmentAction extends BaseAction {
	private Department department;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private AppUserService appUserService;

	@Resource
	private CompanyService companyService;

	public Department getDepartment() {
		/* 30 */return this.department;
	}

	public void setDepartment(Department department) {
		/* 34 */this.department = department;
	}

	public String select() {
		/* 51 */String depId = getRequest().getParameter("depId");
		/* 52 */QueryFilter filter = new QueryFilter(getRequest());
		/* 53 */if ((StringUtils.isNotEmpty(depId)) && (!"0".equals(depId))) {
			/* 54 */this.department = ((Department) this.departmentService
					.get(new Long(depId)));
			/* 55 */filter.addFilter("Q_path_S_LFK", this.department.getPath());
		}

		/* 58 */filter.addSorted("path", "asc");
		/* 59 */List<Department> list = this.departmentService.getAll(filter);
		/* 60 */Type type = new TypeToken<List<Department>>() {
		}
		/* 60 */.getType();
		/* 61 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':").append(
				filter.getPagingBean().getTotalItems()).append(",result:");
		/* 62 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();
		/* 63 */buff.append(gson.toJson(list, type));
		/* 64 */buff.append("}");
		/* 65 */this.jsonString = buff.toString();

		/* 67 */return "success";
	}

	public String list() {
		/* 71 */String opt = getRequest().getParameter("opt");
		/* 72 */StringBuffer buff = new StringBuffer();
		/* 73 */if (StringUtils.isNotEmpty(opt))
			/* 74 */buff.append("[");
		else {
			/* 76 */buff.append("[{id:'0',text:'" + AppUtil.getCompanyName()
					+ "',expanded:true,children:[");
		}

		/* 79 */List<Department> listParent = this.departmentService
				.findByParentId(new Long(0L));
		/* 80 */for (Department dep : listParent) {
			/* 81 */buff.append("{id:'" + dep.getDepId() + "',text:'"
					+ dep.getDepName() + "',");
			/* 82 */buff.append(findChild(dep.getDepId()));
		}
		/* 84 */if (!listParent.isEmpty()) {
			/* 85 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 87 */if (StringUtils.isNotEmpty(opt))
			/* 88 */buff.append("]");
		else {
			/* 90 */buff.append("]}]");
		}
		/* 92 */setJsonString(buff.toString());
		/* 93 */return "success";
	}

	public String findChild(Long depId) {
		/* 99 */StringBuffer buff1 = new StringBuffer("");
		/* 100 */List<Department> list = this.departmentService
				.findByParentId(depId);
		/* 101 */if (list.size() == 0) {
			/* 102 */buff1.append("leaf:true},");
			/* 103 */return buff1.toString();
		}
		/* 105 */buff1.append("children:[");
		/* 106 */for (Department dep2 : list) {
			/* 107 */buff1.append("{id:'" + dep2.getDepId() + "',text:'"
					+ dep2.getDepName() + "',");
			/* 108 */buff1.append(findChild(dep2.getDepId()));
		}
		/* 110 */buff1.deleteCharAt(buff1.length() - 1);
		/* 111 */buff1.append("]},");
		/* 112 */return buff1.toString();
	}

	public String add() {
		/* 117 */Long parentId = this.department.getParentId();
		/* 118 */String depPath = "";
		/* 119 */int level = 0;
		/* 120 */if (parentId.longValue() < 1L) {
			/* 121 */parentId = new Long(0L);
			/* 122 */depPath = "0.";
		} else {
			/* 124 */depPath = ((Department) this.departmentService
					.get(parentId)).getPath();
			/* 125 */level = ((Department) this.departmentService.get(parentId))
					.getDepLevel().intValue();
		}
		/* 127 */if (level < 1) {
			/* 128 */level = 1;
		}
		/* 130 */this.department.setDepLevel(Integer.valueOf(level + 1));
		/* 131 */this.departmentService.save(this.department);
		/* 132 */if (this.department != null) {
			//modified by awen for change path setting logic on 2011-10-25 begin
				depPath = depPath + this.department.getDepId().toString()+ ".";			
			//depPath = depPath + this.department.getSort().toString()+ ".";
			//modified by awen for change path setting logic on 2011-10-25 end
			/* 134 */this.department.setPath(depPath);
			/* 135 */this.departmentService.save(this.department);
			/* 136 */setJsonString("{success:true}");
		} else {
			/* 138 */setJsonString("{success:false}");
		}
		/* 140 */return "success";
	}

	public String remove() {
		/* 144 */PagingBean pb = getInitPagingBean();
		/* 145 */Long depId = Long.valueOf(Long.parseLong(getRequest()
				.getParameter("depId")));
		/* 146 */Department department = (Department) this.departmentService
				.get(depId);
		/* 147 */List userList = this.appUserService.findByDepartment(
				department.getPath(), pb);
		/* 148 */if (userList.size() > 0) {
			/* 149 */setJsonString("{success:false,message:'该部门还有人员，请将人员转移后再删除部门!'}");
			/* 150 */return "success";
		}
		/* 152 */this.departmentService.remove(depId);
		/* 153 */List<Department> list = this.departmentService
				.findByParentId(depId);
		/* 154 */for (Department dep : list) {
			/* 155 */List users = this.appUserService.findByDepartment(
					dep.getPath(), pb);
			/* 156 */if (users.size() > 0) {
				/* 157 */setJsonString("{success:false,message:'该部门还有人员，请将人员转移后再删除部门!'}");
				/* 158 */return "success";
			}
			/* 160 */this.departmentService.remove(dep.getDepId());
		}
		/* 162 */setJsonString("{success:true}");
		/* 163 */return "success";
	}

	public String detail() {
		/* 167 */Long depId = Long.valueOf(Long.parseLong(getRequest()
				.getParameter("depId")));
		/* 168 */setDepartment((Department) this.departmentService.get(depId));
		/* 169 */Gson gson = new Gson();
		/* 170 */StringBuffer sb = new StringBuffer("{success:true,data:[");
		/* 171 */sb.append(gson.toJson(this.department));
		/* 172 */sb.append("]}");
		/* 173 */setJsonString(sb.toString());
		/* 174 */return "success";
	}
}
