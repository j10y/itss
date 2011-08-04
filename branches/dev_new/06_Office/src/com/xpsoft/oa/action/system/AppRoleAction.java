package com.xpsoft.oa.action.system;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.AppFunction;
import com.xpsoft.oa.model.system.AppRole;
import com.xpsoft.oa.service.system.AppFunctionService;
import com.xpsoft.oa.service.system.AppRoleService;

public class AppRoleAction extends BaseAction {

	@Resource
	private AppFunctionService appFunctionService;
	/* 34 */private static String IS_COPY = "1";

	@Resource
	private AppRoleService appRoleService;
	private AppRole appRole;
	private Long roleId;

	/* 44 */public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		/* 48 */this.roleId = roleId;
	}

	public AppRole getAppRole() {
		/* 52 */return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		/* 56 */this.appRole = appRole;
	}

	public String list() {
		/* 64 */QueryFilter filter = new QueryFilter(getRequest());
		/* 65 */List<AppRole> list = this.appRoleService.getAll(filter);

		Type type=new TypeToken<List<AppRole>>(){}.getType();
		/* 68 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 69 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		/* 70 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();
		/* 71 */buff.append(gson.toJson(list, type));
		/* 72 */buff.append("}");
		/* 73 */this.jsonString = buff.toString();

		/* 75 */return "success";
	}

	public String tree() {
		/* 84 */StringBuffer buff = new StringBuffer("[");
		/* 85 */List<AppRole> listRole = this.appRoleService.getAll();
		/* 86 */for (AppRole role : listRole) {
			/* 87 */buff.append("{id:'" + role.getRoleId() + "',text:'"
					+ role.getRoleName() + "',leaf:true},");
		}
		/* 89 */if (!listRole.isEmpty()) {
			/* 90 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 92 */buff.append("]");
		/* 93 */setJsonString(buff.toString());
		/* 94 */return "success";
	}

	public String multiDel() {
		/* 103 */String[] ids = getRequest().getParameterValues("ids");
		/* 104 */if (ids != null) {
			/* 105 */for (String id : ids) {
				/* 106 */AppRole appRole = (AppRole) this.appRoleService
						.get(new Long(id));
				/* 107 */appRole.getAppUsers().remove(appRole);
				/* 108 */appRole.getFunctions().remove(appRole);
				/* 109 */this.appRoleService.remove(appRole);
			}
		}

		/* 113 */this.jsonString = "{success:true}";

		/* 115 */return "success";
	}

	public String grant() {
		/* 123 */AppRole appRole = (AppRole) this.appRoleService
				.get(this.roleId);
		/* 124 */String rights = getRequest().getParameter("rights");

		/* 126 */if (rights == null) {
			/* 127 */rights = "";
		}

		/* 130 */if (!rights.equals(appRole.getRights())) {
			/* 131 */appRole.setRights(rights);

			/* 133 */appRole.getFunctions().clear();

			/* 135 */String[] funs = rights.split("[,]");

			/* 137 */for (int i = 0; i < funs.length; i++) {
				/* 138 */if (funs[i].startsWith("_")) {
					/* 139 */AppFunction af = this.appFunctionService
							.getByKey(funs[i]);
					/* 140 */if (af != null) {
						/* 141 */appRole.getFunctions().add(af);
					}
				}

			}

			/* 147 */this.appRoleService.save(appRole);

			/* 149 */AppUtil.reloadSecurityDataSource();
		}

		/* 152 */return "success";
	}

	public String grantXml() {
		/* 160 */Document grantMenuDoc = AppUtil.getGrantMenuDocument();
		/* 161 */setJsonString(grantMenuDoc.asXML());
		/* 162 */return "success";
	}

	public String get() {
		/* 170 */AppRole appRole = (AppRole) this.appRoleService
				.get(this.roleId);
		/* 171 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation().create();

		/* 173 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 174 */sb.append(gson.toJson(appRole));
		/* 175 */sb.append("}");
		/* 176 */setJsonString(sb.toString());

		/* 178 */return "success";
	}

	public String save() {
		/* 185 */String isCopy = getRequest().getParameter("isCopy");
		/* 186 */if ((StringUtils.isNotEmpty(isCopy))
				&& (IS_COPY.equals(isCopy))) {
			/* 187 */AppRole role = new AppRole();
			/* 188 */role.setIsDefaultIn((short) 0);
			/* 189 */role.setRoleDesc(this.appRole.getRoleDesc());
			/* 190 */role.setStatus(this.appRole.getStatus());
			/* 191 */role.setRoleName(this.appRole.getRoleName());
			/* 192 */this.appRole = ((AppRole) this.appRoleService
					.get(this.appRole.getRoleId()));
			/* 193 */Set set = new HashSet(this.appRole.getFunctions());
			/* 194 */role.setFunctions(set);
			/* 195 */role.setRights(this.appRole.getRights());
			/* 196 */this.appRoleService.save(role);
		} else {
			/* 198 */this.appRole.setIsDefaultIn((short) 0);
			/* 199 */this.appRoleService.save(this.appRole);
		}
		/* 201 */setJsonString("{success:true}");
		/* 202 */return "success";
	}

	public String check() {
		/* 206 */String roleName = getRequest().getParameter("roleName");
		/* 207 */AppRole appRole = this.appRoleService.getByRoleName(roleName);
		/* 208 */if (appRole == null)
			/* 209 */setJsonString("{success:true}");
		else {
			/* 211 */setJsonString("{success:false}");
		}
		/* 213 */return "success";
	}
	
	/**
	 * 查询指定局长或分局长Id
	 * @return
	 */
	public String getAssignId(){
		
		String type = getRequest().getParameter("type");
		String roleIds = "";
		if(type!=null && type.equals("1")){
			roleIds = AppUtil.getPropertity("role.leaderId");
		}else if(type!=null && type.equals("2")){
			roleIds = AppUtil.getPropertity("role.proxyLeaderId");
		}else{
			roleIds = AppUtil.getPropertity("role.leaderId") + "," + AppUtil.getPropertity("role.proxyLeaderId");
		}
		setJsonString("{success:true, roleIds:'"+ roleIds +"'}");
		return "success";
	}
}
