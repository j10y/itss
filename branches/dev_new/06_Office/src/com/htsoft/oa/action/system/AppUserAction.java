package com.htsoft.oa.action.system;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;

import com.htsoft.core.Constants;

import com.htsoft.core.command.QueryFilter;

import com.htsoft.core.log.Action;

import com.htsoft.core.model.OnlineUser;

import com.htsoft.core.util.AppUtil;

import com.htsoft.core.util.BeanUtil;

import com.htsoft.core.util.ContextUtil;

import com.htsoft.core.util.JsonUtil;

import com.htsoft.core.util.StringUtil;

import com.htsoft.core.web.action.BaseAction;

import com.htsoft.core.web.paging.PagingBean;

import com.htsoft.oa.model.system.AppRole;

import com.htsoft.oa.model.system.AppUser;

import com.htsoft.oa.model.system.Department;

import com.htsoft.oa.model.system.IndexDisplay;

import com.htsoft.oa.model.system.PanelItem;

import com.htsoft.oa.service.system.AppRoleService;

import com.htsoft.oa.service.system.AppUserService;

import com.htsoft.oa.service.system.DepartmentService;

import com.htsoft.oa.service.system.IndexDisplayService;

import com.htsoft.oa.service.system.UserSubService;

import flexjson.DateTransformer;

import flexjson.JSONSerializer;

import java.lang.reflect.Type;

import java.util.ArrayList;

import java.util.Collection;

import java.util.HashMap;

import java.util.HashSet;

import java.util.Iterator;

import java.util.List;

import java.util.Map;

import java.util.Set;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.logging.Log;

public class AppUserAction extends BaseAction {
	/* 54 */private static Long SUPPER_MANAGER_ID = Long.valueOf(-1L);

	@Resource
	private AppUserService appUserService;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private AppRoleService appRoleService;

	@Resource
	private UserSubService userSubService;

	@Resource
	private IndexDisplayService indexDisplayService;
	private AppUser appUser;
	private Long userId;
	private Long depId;
	private Long roleId;

	/* 75 */public Long getDepId() {
		return this.depId;
	}

	public void setDepId(Long depId) {
		/* 79 */this.depId = depId;
	}

	public Long getRoleId() {
		/* 83 */return this.roleId;
	}

	public void setRoleId(Long roleId) {
		/* 87 */this.roleId = roleId;
	}

	public Long getUserId() {
		/* 91 */return this.userId;
	}

	public void setUserId(Long userId) {
		/* 95 */this.userId = userId;
	}

	public AppUser getAppUser() {
		/* 99 */return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		/* 103 */this.appUser = appUser;
	}

	public String getCurrent() {
		/* 112 */AppUser currentUser = ContextUtil.getCurrentUser();
		/* 113 */Department curDep = currentUser.getDepartment();
		/* 114 */if (curDep == null) {
			/* 115 */curDep = new Department();
			/* 116 */curDep.setDepId(Long.valueOf(0L));
			/* 117 */curDep.setDepName(AppUtil.getCompanyName());
		}
		/* 119 */Iterator publicIds = AppUtil.getPublicMenuIds().iterator();
		/* 120 */StringBuffer publicIdSb = new StringBuffer();

		/* 122 */while (publicIds.hasNext()) {
			/* 123 */publicIdSb.append(",").append((String) publicIds.next());
		}
		/* 125 */List<IndexDisplay> list = this.indexDisplayService
				.findByUser(currentUser.getUserId());
		/* 126 */List items = new ArrayList();
		/* 127 */for (IndexDisplay id : list) {
			/* 128 */PanelItem pi = new PanelItem();
			/* 129 */pi.setPanelId(id.getPortalId());
			/* 130 */pi.setColumn(id.getColNum().intValue());
			/* 131 */pi.setRow(id.getRowNum().intValue());
			/* 132 */items.add(pi);
		}
		/* 134 */StringBuffer sb = new StringBuffer();
		/* 135 */sb.append("{success:true,user:{userId:'").append(
		/* 136 */currentUser.getUserId()).append("',fullname:'").append(
		/* 137 */currentUser.getFullname()).append("',depId:'")
		/* 138 */.append(curDep.getDepId()).append("',depName:'")
		/* 139 */.append(curDep.getDepName()).append("',rights:'");
		/* 140 */sb.append(currentUser.getRights().toString().replace("[", "")
				.replace("]", ""));
		/* 141 */if ((!"".equals(currentUser.getRights()))
				&& (publicIdSb.length() > 0)) {
			/* 142 */sb.append(publicIdSb.toString());
		}
		/* 144 */Gson gson = new Gson();
		/* 145 */sb.append("',items:").append(gson.toJson(items).toString());
		/* 146 */sb.append("}}");
		/* 147 */setJsonString(sb.toString());
		/* 148 */return "success";
	}

	public String list() {
		/* 152 */QueryFilter filter = new QueryFilter(getRequest());
		/* 153 */filter.addFilter("Q_delFlag_SN_EQ", Constants.FLAG_UNDELETED
		/* 154 */.toString());
		/* 155 */List list = this.appUserService.getAll(filter);
		/* 156 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 157 */.append(filter.getPagingBean().getTotalItems()).append(
		/* 158 */",result:");
		/* 159 */JSONSerializer serializer = new JSONSerializer();
		/* 160 */serializer.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "accessionTime" });
		/* 161 */buff.append(serializer.exclude(new String[] { "password" })
				.serialize(list));
		/* 162 */buff.append("}");
		/* 163 */this.jsonString = buff.toString();
		/* 164 */return "success";
	}

	public String select() {
		/* 171 */PagingBean pb = getInitPagingBean();
		/* 172 */String strDepId = getRequest().getParameter("depId");

		/* 174 */String path = "0.";
		/* 175 */this.appUser = ContextUtil.getCurrentUser();
		/* 176 */if (StringUtils.isNotEmpty(strDepId)) {
			/* 177 */Long depId = Long.valueOf(Long.parseLong(strDepId));
			/* 178 */Department dep = (Department) this.departmentService
					.get(depId);
			/* 179 */if (dep != null)
				/* 180 */path = dep.getPath();
		} else {
			/* 183 */Department dep = this.appUser.getDepartment();
			/* 184 */if (dep != null) {
				/* 185 */path = dep.getPath();
			}
		}
		/* 188 */List list = this.appUserService.findByDepartment(path, pb);
		/* 189 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 190 */.append(pb.getTotalItems()).append(",result:");
		/* 191 */JSONSerializer serializer = new JSONSerializer();
		/* 192 */serializer.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "accessionTime" });
		/* 193 */buff.append(serializer.exclude(new String[] { "password" })
				.serialize(list));
		/* 194 */buff.append("}");

		/* 196 */this.jsonString = buff.toString();
		/* 197 */return "success";
	}

	public String online() {
		/* 206 */Map onlineUsers = new HashMap();
		/* 207 */Map onlineUsersByDep = new HashMap();
		/* 208 */Map onlineUsersByRole = new HashMap();

		/* 210 */onlineUsers = AppUtil.getOnlineUsers();

		if (this.depId != null) {
			Set<String> onlineUsersSet = onlineUsers.keySet();
			/* 214 */for (String sessionId : onlineUsersSet) {
				/* 215 */OnlineUser onlineUser = new OnlineUser();
				/* 216 */onlineUser = (OnlineUser) onlineUsers.get(sessionId);

				/* 218 */String path = "";
				/* 219 */if (!onlineUser.getUserId().equals(AppUser.SUPER_USER)) {
					/* 220 */path = onlineUser.getDepPath();
				}
				/* 222 */if (!this.depId.equals(new Long(0L))) {
					/* 223 */if (Pattern.compile("." + this.depId + ".")
							.matcher(path).find())
						/* 224 */onlineUsersByDep.put(sessionId, onlineUser);
				} else {
					/* 227 */onlineUsersByDep.put(sessionId, onlineUser);
				}
			}

		}

		if (this.roleId != null) {
			Set<String> onlineUsersSet = onlineUsers.keySet();
			for (String sessionId : onlineUsersSet) {
				OnlineUser onlineUser = new OnlineUser();
				onlineUser = (OnlineUser) onlineUsers.get(sessionId);

				if (Pattern.compile("," + this.roleId + ",").matcher(onlineUser.getRoleIds()).find()) {
					onlineUsersByRole.put(sessionId, onlineUser);
				}
			}
		}

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(onlineUsers.size()).append(",result:");

		Gson gson = new Gson();
		Type type = new TypeToken<Collection<OnlineUser>>() {}.getType();
		if (this.depId != null)
			buff.append(gson.toJson(onlineUsersByDep.values(), type));
		else if (this.roleId != null)
			buff.append(gson.toJson(onlineUsersByRole.values(), type));
		else {
			buff.append(gson.toJson(onlineUsers.values(), type));
		}
		
		buff.append("}");
		this.jsonString = buff.toString();
		return "success";
	}

	public String find() {
		String strRoleId = getRequest().getParameter("roleId");
		PagingBean pb = getInitPagingBean();
		if (StringUtils.isNotEmpty(strRoleId)) {
			List<AppUser> userList = this.appUserService.findByRole(Long.valueOf(Long.parseLong(strRoleId)), pb);
			Type type = new TypeToken<List<AppUser>>() {}.getType();

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			/* 274 */.append(pb.getTotalItems()).append(",result:");
			/* 275 */Gson gson = new GsonBuilder()
			/* 276 */.excludeFieldsWithoutExposeAnnotation().create();
			/* 277 */buff.append(gson.toJson(userList, type));
			/* 278 */buff.append("}");

			/* 280 */this.jsonString = buff.toString();
		} else {
			/* 282 */this.jsonString = "{success:false}";
		}
		/* 284 */return "success";
	}

	public String multiDel() {
		/* 294 */String[] ids = getRequest().getParameterValues("ids");
		/* 295 */StringBuffer buff = new StringBuffer("{success:true");
		/* 296 */if (ids != null) {
			/* 297 */buff.append(",msg:'");
			/* 298 */for (String id : ids) {
				/* 299 */AppUser delUser = (AppUser) this.appUserService
						.get(new Long(id));
				/* 300 */AppRole superManager = (AppRole) this.appRoleService
						.get(SUPPER_MANAGER_ID);
				/* 301 */if (delUser.getRoles().contains(superManager)) {
					/* 302 */buff.append("员工:").append(delUser.getUsername())
							.append(
							/* 303 */"是超级管理员,不能删除!<br><br/>");
					/* 304 */} else if (delUser.getUserId().equals(
				/* 305 */ContextUtil.getCurrentUserId())) {
					/* 306 */buff.append("不能删除自己!<br></br>");
				} else {
					/* 308 */delUser.setStatus(Constants.FLAG_DISABLE);
					/* 309 */delUser.setDelFlag(Constants.FLAG_DELETED);
					/* 310 */delUser.setUsername("__" + delUser.getUsername());
					/* 311 */this.appUserService.save(delUser);
				}
			}
			/* 314 */buff.append("'");
		}
		/* 316 */buff.append("}");
		/* 317 */setJsonString(buff.toString());
		/* 318 */return "success";
	}

	public String get() {
		/* 327 */AppUser appUser = null;
		/* 328 */JSONSerializer json =
		/* 329 */JsonUtil.getJSONSerializer(new String[] { "accessionTime" });
		/* 330 */if (this.userId != null) {
			/* 331 */appUser = (AppUser) this.appUserService.get(this.userId);
		} else {
			/* 333 */json.exclude(new String[] { "accessionTime", "department",
			/* 334 */"password", "status", "position" });
			/* 335 */appUser = ContextUtil.getCurrentUser();
		}

		/* 338 */StringBuffer sb = new StringBuffer(
				"{success:true,totalCounts:1,data:[");
		/* 339 */sb.append(JsonUtil.getJSONSerializer(
				new String[] { "accessionTime" })
		/* 340 */.serialize(appUser));
		/* 341 */sb.append("]}");
		/* 342 */setJsonString(sb.toString());

		/* 344 */return "success";
	}

	@Action(description = "添加或保存用户信息")
	public String save() {
		/* 352 */String rolesIds = getRequest().getParameter("AppUserRoles");
		/* 353 */String[] ids = rolesIds.split(",");
		/* 354 */Set roles = new HashSet();
		/* 355 */for (String id : ids) {
			/* 356 */if (!"".equals(id)) {
				/* 357 */AppRole role = (AppRole) this.appRoleService
						.get(new Long(id));
				/* 358 */roles.add(role);
			}
		}
		/* 361 */this.appUser.setRoles(roles);
		/* 362 */if (this.appUser.getUserId() != null) {
			/* 363 */AppUser old = (AppUser) this.appUserService
					.get(this.appUser.getUserId());
			/* 364 */this.appUser.setDelFlag(old.getDelFlag());
			/* 365 */this.appUser.setPassword(old.getPassword());
			/* 366 */this.appUserService.merge(this.appUser);
			/* 367 */setJsonString("{success:true}");
		}
		/* 369 */else if (this.appUserService.findByUserName(this.appUser
				.getUsername()) == null) {
			/* 370 */this.appUser.setDelFlag(Constants.FLAG_UNDELETED);
			/* 371 */this.appUser.setPassword(StringUtil
					.encryptSha256(this.appUser.getPassword()));
			/* 372 */this.appUserService.merge(this.appUser);
			/* 373 */setJsonString("{success:true}");
		} else {
			/* 375 */setJsonString("{success:false,msg:'用户登录账号:"
					+ this.appUser.getUsername() + "已存在,请重新输入账号.'}");
		}

		/* 378 */return "success";
	}

	public String selectedRoles() {
		/* 385 */if (this.userId != null) {
			/* 386 */setAppUser((AppUser) this.appUserService.get(this.userId));
			/* 387 */Set<AppRole> roles = this.appUser.getRoles();
			/* 388 */StringBuffer sb = new StringBuffer("[");
			/* 389 */for (AppRole role : roles) {
				/* 390 */sb.append("['" + role.getRoleId() + "','"
						+ role.getRoleName() +
						/* 391 */"'],");
			}
			/* 393 */sb.deleteCharAt(sb.length() - 1);
			/* 394 */sb.append("]");
			/* 395 */setJsonString(sb.toString());
		}
		/* 397 */return "success";
	}

	public String chooseRoles() {
		/* 406 */List<AppRole> chooseRoles = this.appRoleService.getAll();

		/* 408 */if (this.userId != null) {
			/* 409 */setAppUser((AppUser) this.appUserService.get(this.userId));
			/* 410 */Set<AppRole> selectedRoles = this.appUser.getRoles();
			/* 411 */for (AppRole role : selectedRoles) {
				/* 412 */chooseRoles.remove(role);
			}
		}
		/* 415 */StringBuffer sb = new StringBuffer("[");
		/* 416 */for (AppRole role : chooseRoles) {
			/* 417 */if (role.getStatus().shortValue() != 0) {
				/* 418 */sb.append("['" + role.getRoleId() + "','"
						+ role.getRoleName() +
						/* 419 */"'],");
			}
		}
		/* 422 */sb.deleteCharAt(sb.length() - 1);
		/* 423 */sb.append("]");
		/* 424 */setJsonString(sb.toString());
		/* 425 */return "success";
	}

	@Action(description = "修改密码")
	public String resetPassword() {
		/* 435 */String userId = getRequest().getParameter("appUserUserId");
		/* 436 */String oldPassword = StringUtil.encryptSha256(getRequest()
		/* 437 */.getParameter("oldPassword"));
		/* 438 */String newPassword = getRequest().getParameter("newPassword");
		/* 439 */String againPassword = getRequest().getParameter(
				"againPassword");
		/* 440 */if (StringUtils.isNotEmpty(userId))
			/* 441 */setAppUser((AppUser) this.appUserService.get(new Long(
					userId)));
		else {
			/* 443 */setAppUser(ContextUtil.getCurrentUser());
		}
		/* 445 */StringBuffer msg = new StringBuffer("{msg:'");
		/* 446 */boolean pass = false;
		/* 447 */if (oldPassword.equals(this.appUser.getPassword())) {
			/* 448 */if (newPassword.equals(againPassword))
				/* 449 */pass = true;
			else
				/* 451 */msg.append("两次输入不一致.'");
		}
		/* 453 */else
			msg.append("旧密码输入不正确.'");
		/* 454 */if (pass) {
			/* 455 */this.appUser.setPassword(StringUtil
					.encryptSha256(newPassword));
			/* 456 */this.appUserService.save(this.appUser);
			/* 457 */setJsonString("{success:true}");
		} else {
			/* 459 */msg.append(",failure:true}");
			/* 460 */setJsonString(msg.toString());
		}
		/* 462 */return "success";
	}

	public String photo() {
		/* 471 */setAppUser((AppUser) this.appUserService.get(getUserId()));
		/* 472 */this.appUser.setPhoto("");
		/* 473 */this.appUserService.save(this.appUser);
		/* 474 */return "success";
	}

	public String subAdepartment() {
		/* 483 */PagingBean pb = getInitPagingBean();
		/* 484 */String strDepId = getRequest().getParameter("depId");
		/* 485 */String path = "0.";
		/* 486 */AppUser user = ContextUtil.getCurrentUser();
		/* 487 */if (StringUtils.isNotEmpty(strDepId)) {
			/* 488 */Long depId = Long.valueOf(Long.parseLong(strDepId));
			/* 489 */Department dep = (Department) this.departmentService
					.get(depId);
			/* 490 */if (dep != null)
				/* 491 */path = dep.getPath();
		} else {
			/* 494 */Department dep = user.getDepartment();
			/* 495 */if (dep != null) {
				/* 496 */path = dep.getPath();
			}
		}
		/* 499 */if ("0.".equals(path)) {
			/* 500 */path = null;
		}
		/* 502 */Long uId = user.getUserId();
		/* 503 */Set userIds = this.userSubService.findAllUpUser(uId);
		/* 504 */List<Long> userIdsL = this.userSubService.subUsers(uId);
		/* 505 */userIds.add(uId);
		/* 506 */for (Long l : userIdsL) {
			/* 507 */userIds.add(l);
		}
		/* 509 */List<AppUser> list = this.appUserService.findSubAppUser(path, userIds,
				pb);
		/* 510 */Type type = new TypeToken<List<AppUser>>() {
			/* 511 */
		}.getType();
		/* 512 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 513 */.append(pb.getTotalItems()).append(",result:");
		/* 514 */Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				/* 515 */.create();
		/* 516 */buff.append(gson.toJson(list, type));
		/* 517 */buff.append("}");
		/* 518 */this.jsonString = buff.toString();
		/* 519 */return "success";
	}

	public String subArole() {
		/* 528 */String strRoleId = getRequest().getParameter("roleId");
		/* 529 */PagingBean pb = getInitPagingBean();
		/* 530 */AppUser user = ContextUtil.getCurrentUser();
		/* 531 */if (StringUtils.isNotEmpty(strRoleId)) {
			/* 532 */Long uId = user.getUserId();
			/* 533 */Set userIds = this.userSubService.findAllUpUser(uId);
			/* 534 */List<Long> userIdsL = this.userSubService.subUsers(uId);
			/* 535 */userIds.add(uId);
			/* 536 */for (Long l : userIdsL) {
				/* 537 */userIds.add(l);
			}
			/* 539 */List<AppUser> userList = this.appUserService.findSubAppUserByRole(new Long(strRoleId), userIds, pb);

			/* 544 */Type type = new TypeToken<List<AppUser>>() {
				/* 545 */
			}.getType();
			/* 546 */StringBuffer buff = new StringBuffer(
					"{success:true,'totalCounts':")
			/* 547 */.append(pb.getTotalItems()).append(",result:");
			/* 548 */Gson gson = new GsonBuilder()
			/* 549 */.excludeFieldsWithoutExposeAnnotation().create();
			/* 550 */buff.append(gson.toJson(userList, type));
			/* 551 */buff.append("}");
			/* 552 */this.jsonString = buff.toString();
		} else {
			/* 554 */this.jsonString = "{success:false}";
		}
		/* 556 */return "success";
	}

	public String onlineAsub() {
		/* 564 */Map onlineUsers = new HashMap();
		/* 565 */Map onlineUsersBySub = new HashMap();
		/* 566 */onlineUsers = AppUtil.getOnlineUsers();

		/* 568 */AppUser user = ContextUtil.getCurrentUser();
		/* 569 */Long uId = user.getUserId();
		/* 570 */Set userIds = this.userSubService.findAllUpUser(uId);
		/* 571 */userIds.add(uId);
		/* 572 */List<Long> userIdsL = this.userSubService.subUsers(uId);
		/* 573 */for (Long l : userIdsL) {
			/* 574 */userIds.add(l);
		}
		Set<String> online = onlineUsers.keySet();
		for (String sessionId : online) {
			OnlineUser onlineUser = new OnlineUser();
			onlineUser = (OnlineUser) onlineUsers.get(sessionId);
			if (!userIds.contains(onlineUser.getUserId())) {
				onlineUsersBySub.put(sessionId, onlineUser);
			}
		}
		/* 583 */Type type = new TypeToken<Collection<OnlineUser>>() {}.getType();
		/* 585 */StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		/* 586 */.append(onlineUsers.size()).append(",result:");
		/* 587 */Gson gson = new Gson();
		/* 588 */buff.append(gson.toJson(onlineUsersBySub.values(), type));
		/* 589 */buff.append("}");
		/* 590 */this.jsonString = buff.toString();
		/* 591 */return "success";
	}

	public String upUser() {
		/* 600 */List<Long> ids = this.userSubService.upUser(ContextUtil
				.getCurrentUserId());
		/* 601 */List<AppUser> list = new ArrayList();
		/* 602 */for (Long l : ids) {
			/* 603 */list.add((AppUser) this.appUserService.get(l));
		}
		/* 605 */StringBuffer buff = new StringBuffer("[");
		/* 606 */for (AppUser user : list) {
			/* 607 */buff.append("['" + user.getUserId().toString() + "','" +
			/* 608 */user.getFullname() + "'],");
		}
		/* 610 */if (list.size() > 0) {
			/* 611 */buff.deleteCharAt(buff.length() - 1);
		}
		/* 613 */buff.append("]");
		/* 614 */setJsonString(buff.toString());
		/* 615 */return "success";
	}

	@Action(description = "修改个人资料")
	public String profile() {
		/* 625 */AppUser old = ContextUtil.getCurrentUser();
		try {
			/* 627 */BeanUtil.copyNotNullProperties(old, this.appUser);
		} catch (Exception e) {
			/* 629 */this.logger.info(e);
		}
		/* 631 */this.appUserService.save(old);
		/* 632 */this.jsonString = "{success:true}";
		/* 633 */return "success";
	}

}
