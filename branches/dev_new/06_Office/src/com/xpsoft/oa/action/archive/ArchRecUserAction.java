package com.xpsoft.oa.action.archive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.archive.ArchRecUser;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.personal.ErrandsRegister;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.archive.ArchRecUserService;
import com.xpsoft.oa.service.archive.ArchivesService;
import com.xpsoft.oa.service.personal.ErrandsRegisterService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.DepartmentService;

public class ArchRecUserAction extends BaseAction {

	@Resource
	private ArchRecUserService archRecUserService;
	private ArchRecUser archRecUser;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private AppUserService appUserService;
	private Long archRecId;

	public Long getArchRecId() {
		/* 42 */return this.archRecId;
	}

	public void setArchRecId(Long archRecId) {
		/* 46 */this.archRecId = archRecId;
	}

	public ArchRecUser getArchRecUser() {
		/* 50 */return this.archRecUser;
	}

	public void setArchRecUser(ArchRecUser archRecUser) {
		/* 54 */this.archRecUser = archRecUser;
	}

	public String list() {
		/* 62 */QueryFilter filter = new QueryFilter(getRequest());
		/* 63 */List<ArchRecUser> list = this.archRecUserService.getAll(filter);

		/* 65 */Type type = new TypeToken<List<ArchRecUser>>() {
		}
		/* 65 */.getType();
		/* 66 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 67 */.append(filter.getPagingBean().getTotalItems()).append(
				",result:");

		/* 69 */Gson gson = new Gson();
		/* 70 */buff.append(gson.toJson(list, type));
		/* 71 */buff.append("}");

		/* 73 */this.jsonString = buff.toString();

		/* 75 */return "success";
	}

	public String depList() {
		/* 79 */List list = this.archRecUserService.findDepAll();
		/* 80 */StringBuffer sb = new StringBuffer(
				"{success:true,'totalCounts':");
		/* 81 */sb.append(list.size()).append(",result:[");
		/* 82 */for (int i = 0; i < list.size(); i++) {
			/* 83 */if (i > 0) {
				/* 84 */sb.append(",");
			}
			/* 86 */ArchRecUser ar = (ArchRecUser) ((Object[]) list.get(i))[0];
			/* 87 */Department dep = (Department) ((Object[]) list.get(i))[1];
			/* 88 */sb.append("{'depId':'" + dep.getDepId() + "','depName':'"
					+ dep.getDepName() + "','depLevel':" + dep.getDepLevel()
					+ ",");
			/* 89 */if (ar != null)
				/* 90 */sb.append("'archRecId':'" + ar.getArchRecId()
						+ "','userId':'" + ar.getUserId() + "','fullname':'"
						+ ar.getFullname() + "'" 
						+ ",'leaderUserId':'" + ar.getLeaderUserId() + "','leaderFullname':'"
						+ ar.getLeaderFullname() + "'" 
						+ ",'deptUserId':'" + ar.getDeptUserId() + "','deptFullname':'"
						+ ar.getDeptFullname() + "'" +
						"}");
			else {
				/* 92 */sb.append("'archRecId':'','userId':'','fullname':''}");
			}
		}
		/* 95 */sb.append("]}");
		/* 96 */this.jsonString = sb.toString();
		/* 97 */return "success";
	}

	public String multiDel() {
		/* 106 */String[] ids = getRequest().getParameterValues("ids");
		/* 107 */if (ids != null) {
			/* 108 */for (String id : ids) {
				/* 109 */this.archRecUserService.remove(new Long(id));
			}
		}

		/* 113 */this.jsonString = "{success:true}";

		/* 115 */return "success";
	}

	public String get() {
		/* 123 */ArchRecUser archRecUser = (ArchRecUser) this.archRecUserService
				.get(this.archRecId);

		/* 125 */Gson gson = new Gson();

		/* 127 */StringBuffer sb = new StringBuffer("{success:true,data:");
		/* 128 */sb.append(gson.toJson(archRecUser));
		/* 129 */sb.append("}");
		/* 130 */setJsonString(sb.toString());

		/* 132 */return "success";
	}

	public String save() {
		/* 138 */String data = getRequest().getParameter("data");
		/* 139 */if (StringUtils.isNotEmpty(data)) {
			/* 140 */Gson gson = new Gson();
			/* 141 */ArchRecUser[] aru = gson.fromJson(data,
					new com.google.gson.reflect.TypeToken<ArchRecUser[]>() {
					}.getType());
			/* 142 */for (ArchRecUser archRecUser : aru) {
				/* 143 */if (archRecUser.getArchRecId().longValue() == -1L) {
					/* 144 */archRecUser.setArchRecId(null);
				}
				/* 146 */if (archRecUser.getDepId() != null) {
					/* 147 */Department department = (Department) this.departmentService
							.get(archRecUser.getDepId());
					/* 148 */archRecUser.setDepartment(department);
					/* 149 */this.archRecUserService.save(archRecUser);
				} else {
					/* 151 */setJsonString("{success:false}");
				}
			}
		}
		/* 155 */setJsonString("{success:true}");
		/* 156 */return "success";
	}

	public String select() {
		/* 160 */String strDepId = getRequest().getParameter("depId");
		/* 161 */StringBuffer sb = new StringBuffer("[");
		/* 162 */if (StringUtils.isNotEmpty(strDepId)) {
			/* 163 */List<AppUser> list = this.appUserService
					.findByDepId(new Long(strDepId));
			/* 164 */for (AppUser appUser : list) {
				/* 165 */sb.append("['").append(appUser.getUserId())
						.append("','").append(appUser.getFullname())
						.append("'],");
			}
			/* 167 */if (list.size() > 0) {
				/* 168 */sb.deleteCharAt(sb.length() - 1);
			}
		}
		/* 171 */sb.append("]");
		/* 172 */setJsonString(sb.toString());
		/* 173 */return "success";
	}
	
	public String getByCurrentUser() {
 		Long deptId = ContextUtil.getCurrentUser().getDepartment().getDepId();
 		ArchRecUser archRecUser = (ArchRecUser) this.archRecUserService.getByDepId(deptId);
 		Gson gson = new Gson();
 		StringBuffer sb = new StringBuffer("{success:true,data:");
 			sb.append(gson.toJson(archRecUser));
 			sb.append("}");
		setJsonString(sb.toString());

		return "success";
	}
	public String getUserByArchid() {
		ArchivesService archivesService = (ArchivesService) AppUtil.getBean("archivesService");
		String id = getRequest().getParameter("archid");
		Archives archives1 = ((Archives) archivesService.get(Long.parseLong(id)));
		Long userid=archives1.getIssuerId();
		AppUser appUser =appUserService.get(userid);
		Long departid=appUser.getDepartment().getDepId();
		ArchRecUser archRecUser = (ArchRecUser) archRecUserService.getByDepId(departid);
 		Gson gson = new Gson();
 		StringBuffer sb = new StringBuffer("{success:true,data:");
 			sb.append(gson.toJson(archRecUser));
 			sb.append("}");
		setJsonString(sb.toString());
		return "success";
	}
	
	public String getUserByErrandId() {
		ErrandsRegisterService errandsRegisterService = (ErrandsRegisterService) AppUtil.getBean("errandsRegisterService");
		String id = getRequest().getParameter("errid");
		ErrandsRegister errandsRegister = ((ErrandsRegister) errandsRegisterService.get(Long.parseLong(id)));
		Long userid=errandsRegister.getApprovalId();
		AppUser appUser =appUserService.get(userid);
		Long departid=appUser.getDepartment().getDepId();
		ArchRecUser archRecUser = (ArchRecUser) archRecUserService.getByDepId(departid);
 		Gson gson = new Gson();
 		StringBuffer sb = new StringBuffer("{success:true,data:");
 			sb.append(gson.toJson(archRecUser));
 			sb.append("}");
		setJsonString(sb.toString());
		return "success";
	}

	/**
	 * checkValue {1:局长,2:分管局长,3:局长或分管局长}
	 * @return
	 */
	public String getUserByChcek() {
		String type = getRequest().getParameter("checkValue");
		String roleIds = "";
		List<AppUser> userList = new ArrayList();
		ArchRecUser archRecUser = null;
		if(type!=null && (type.equals("1")||type.equals("3"))){//局长
			roleIds = AppUtil.getPropertity("role.leaderId");
			String [] roles = roleIds.split(",");
			Long [] roleIdArray = new Long [roles.length]; 
			for(int i=0; i<roles.length; i++){
				roleIdArray  [i] = new Long(roles[i]); 
			}
			if (StringUtils.isNotEmpty(roleIds)) {
				userList = this.appUserService.findByRoleIds(roleIdArray);
			}
		}
		if(type!=null && (type.equals("2")||type.equals("3"))){
			ArchivesService archivesService = (ArchivesService) AppUtil.getBean("archivesService");
			String id = getRequest().getParameter("archid");
			Archives archives1 = ((Archives) archivesService.get(Long.parseLong(id)));
			Long userid=archives1.getIssuerId();
			AppUser appUser =appUserService.get(userid);
			Long departid=appUser.getDepartment().getDepId();
			archRecUser = (ArchRecUser) archRecUserService.getByDepId(departid);
		}
		StringBuffer sb = new StringBuffer("{success:true,userIds:'");
		for(int i=0; i<userList.size(); i++){
			if(i<userList.size()-1){
				sb.append(userList.get(i).getUserId()).append(",");
			}else{
				sb.append(userList.get(i).getUserId());
			}
		}
		if(archRecUser!=null){
			if(sb.toString().endsWith("'")){//说明前面没人
				sb.append(archRecUser.getLeaderUserId());
			}else{
				sb.append(",").append(archRecUser.getLeaderUserId());
			}
			
		}
		sb.append("'}");
		setJsonString(sb.toString());
		return "success";
	}
}
